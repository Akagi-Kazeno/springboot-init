package com.shimakaze.springbootinit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shimakaze.springbootinit.common.ErrorCode;
import com.shimakaze.springbootinit.exception.BusinessException;
import com.shimakaze.springbootinit.mapper.UserMapper;
import com.shimakaze.springbootinit.model.dto.user.UserQueryRequest;
import com.shimakaze.springbootinit.model.entity.User;
import com.shimakaze.springbootinit.model.vo.UserVO;
import com.shimakaze.springbootinit.service.UserService;
import com.shimakaze.springbootinit.utils.JwtUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.shimakaze.springbootinit.constant.SortConstant.SORT_ORDER_ASC;
import static com.shimakaze.springbootinit.constant.UserConstant.*;
import static com.shimakaze.springbootinit.utils.NetUtils.getIpAddress;
import static com.shimakaze.springbootinit.utils.RequestUtils.getRequest;
import static com.shimakaze.springbootinit.utils.SqlUtils.validSortField;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * SALT
     */
    private static final String SALT = "0c9312739ad64baa85914d49ed6c551c";
    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册方法
     *
     * @param userName
     * @param account
     * @param password
     * @param checkPassword
     * @return
     */
    @Override
    public String userRegister(String userName, String account, String password, String checkPassword) {
        // 校验
        if (StringUtils.isAnyBlank(userName, account, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (account.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度应不小于4个字符");
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度应不小于8个字符");
        }
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (account.intern()) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", account);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            // 插入数据
            User user = new User();
            user.setUserId(IdUtil.simpleUUID());
            user.setUserName(userName);
            user.setAccount(account);
            user.setPassword(encryptPassword);
            user.setCreateTime(OffsetDateTime.now());
            user.setRole(DEFAULT_ROLE);
            user.setIsDelete(NOT_DELETE);
            boolean createUser = this.save(user);
            if (!createUser) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库错误");
            }
            return user.getUserId();
        }
    }

    /**
     * 用户登录方法
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public Object userLogin(String account, String password) {
        // 校验
        if (StringUtils.isAnyBlank(account, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (account.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("password", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user: {} login failed, account: {} cannot match password: {}", account, account, password);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        // 更新用户登录时间,ip
        OffsetDateTime lastLoginTime = OffsetDateTime.now();
        String loginIp = getIpAddress(getRequest());
        user.setLastLoginTime(lastLoginTime);
        user.setLastLoginIp(loginIp);
        userMapper.updateById(user);
        // 获取token
        Jedis jedis = RedisDS.create().getJedis();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        jedis.set("token: " + userVO.getUserId(), JSON.toJSONString(userVO));
        jedis.expire("token: " + userVO.getUserId(), 60 * 60 * 24 * 7);
        Map<String, String> map = new HashMap<>();
        map.put("userId", userVO.getUserId());
        String token = JwtUtils.createToken(map);
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("token", token);
        return returnMap;
    }

    /**
     * 获取当前登录用户信息方法
     *
     * @return
     */
    @Override
    public User getLoginUser() {
        Object userObj = getRequest().getAttribute("user");
        User user = (User) userObj;
        if (user == null || user.getUserId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return user;
    }

    /**
     * 获取当前登录用户脱敏信息方法
     *
     * @return
     */
    @Override
    public UserVO getLoginUserVO() {
        Object userObj = getRequest().getAttribute("user");
        User user = (User) userObj;
        if (user == null || user.getUserId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 判断当前登录用户是否为管理员方法
     *
     * @return
     */
    @Override
    public boolean isAdmin() {
        UserVO loginUser = getLoginUserVO();
        return ADMIN_ROLE.equals(loginUser.getRole());
    }

    /**
     * 用户登出方法
     *
     * @return
     */
    @Override
    public boolean userLogout() {
        UserVO loginUser = getLoginUserVO();
        Jedis jedis = RedisDS.create().getJedis();
        jedis.del("token: " + loginUser.getUserId());
        return true;
    }

    /**
     * 查询用户方法
     *
     * @param userQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        String userId = userQueryRequest.getUserId();
        String userName = userQueryRequest.getUserName();
        String account = userQueryRequest.getAccount();
        String role = userQueryRequest.getRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userId), "user_id", userId);
        queryWrapper.eq(StringUtils.isNotBlank(role), "role", role);
        queryWrapper.like(StringUtils.isNotBlank(account), "account", account);
        queryWrapper.like(StringUtils.isNotBlank(userName), "user_name", userName);
        queryWrapper.orderBy(validSortField(sortField), sortOrder.equals(SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    /**
     * 脱敏用户信息方法
     *
     * @param user
     * @return
     */
    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 根据列表获取脱敏用户信息方法
     *
     * @param userList
     * @return
     */
    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    /**
     * 分页获取用户脱敏信息方法
     *
     * @param userQueryRequest
     * @return
     */
    @Override
    public Page<UserVO> listUserByPage(UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = this.page(new Page<>(current, pageSize), this.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> userVOList = this.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return userVOPage;
    }

    /**
     * 更新用户头像方法
     *
     * @param avatar
     * @return
     */
    @Override
    public boolean updateAvatar(String avatar) {
        UserVO loginUser = getLoginUserVO();
        if (StringUtils.isBlank(avatar)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        User updateUser = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", loginUser.getUserId()));
        updateUser.setUpdateTime(OffsetDateTime.now());
        updateUser.setAvatar(avatar);
        return update(loginUser, updateUser);
    }

    /**
     * 更新用户名方法
     *
     * @param userName
     * @return
     */
    @Override
    public boolean updateUserName(String userName) {
        UserVO loginUser = getLoginUserVO();
        if (StringUtils.isBlank(userName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        User updateUser = userMapper.selectOne(new QueryWrapper<User>().eq("user_id", loginUser.getUserId()));
        updateUser.setUpdateTime(OffsetDateTime.now());
        updateUser.setUserName(userName);
        return update(loginUser, updateUser);
    }

    /**
     * 更新用户方法并更新redis缓存
     *
     * @param userVO
     * @param loginUser
     * @return
     */
    private boolean update(UserVO userVO, User loginUser) {
        int update = userMapper.updateById(loginUser);
        if (update == 1) {
            Jedis jedis = RedisDS.create().getJedis();
            BeanUtil.copyProperties(loginUser, userVO);
            jedis.set("token: " + loginUser.getUserId(), JSON.toJSONString(userVO));
            return true;
        }
        return false;
    }
}
