package com.shimakaze.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shimakaze.springbootinit.model.dto.user.UserQueryRequest;
import com.shimakaze.springbootinit.model.entity.User;
import com.shimakaze.springbootinit.model.vo.UserVO;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userName
     * @param account
     * @param password
     * @param checkPassword
     * @return
     */
    String userRegister(String userName, String account, String password, String checkPassword);

    /**
     * 用户登录
     *
     * @param account
     * @param password
     * @return
     */
    Object userLogin(String account, String password);

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    User getLoginUser();

    /**
     * 获取当前登录用户脱敏信息
     *
     * @return
     */
    UserVO getLoginUserVO();

    /**
     * 是否为管理员
     *
     * @return
     */
    boolean isAdmin();

    /**
     * 用户登出
     *
     * @return
     */
    boolean userLogout();

    /**
     * 获取查询用户条件
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 获取脱敏用户信息
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);


    /**
     * 通过列表获取脱敏用户信息
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 分页获取用户脱敏信息
     *
     * @param userQueryRequest
     * @return
     */
    Page<UserVO> listUserByPage(UserQueryRequest userQueryRequest);

    /**
     * 修改用户头像
     *
     * @param avatar
     * @return
     */
    boolean updateAvatar(String avatar);

    /**
     * 修改用户名
     *
     * @param userName
     * @return
     */
    boolean updateUserName(String userName);
}
