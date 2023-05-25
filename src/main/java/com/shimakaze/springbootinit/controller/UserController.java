package com.shimakaze.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shimakaze.springbootinit.annotation.AuthCheck;
import com.shimakaze.springbootinit.common.BaseResponse;
import com.shimakaze.springbootinit.common.ErrorCode;
import com.shimakaze.springbootinit.common.ResultUtils;
import com.shimakaze.springbootinit.exception.BusinessException;
import com.shimakaze.springbootinit.exception.ThrowUtils;
import com.shimakaze.springbootinit.model.dto.user.*;
import com.shimakaze.springbootinit.model.entity.User;
import com.shimakaze.springbootinit.model.vo.UserVO;
import com.shimakaze.springbootinit.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shimakaze.springbootinit.constant.UserConstant.ADMIN_ROLE;

@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=utf-8")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<String> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String account = userRegisterRequest.getAccount();
        String userName = userRegisterRequest.getUserName();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(account, password, checkPassword)) {
            return null;
        }
        String userId = userService.userRegister(account, userName, password, checkPassword);
        return ResultUtils.success(userId);
    }

    @PostMapping("/login")
    public BaseResponse<Object> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String account = userLoginRequest.getAccount();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(account, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Object token = userService.userLogin(account, password);
        return ResultUtils.success(token);
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout() {
        boolean logout = userService.userLogout();
        return ResultUtils.success(logout);
    }

    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginUserVO() {
        return ResultUtils.success(userService.getLoginUserVO());
    }

    @PostMapping("/update/avatar")
    public BaseResponse<Boolean> updateAvatar(@RequestBody UserUpdateAvatarRequest userUpdateAvatarRequest) {
        if (userUpdateAvatarRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String avatar = userUpdateAvatarRequest.getAvatar();
        boolean updateAvatar = userService.updateAvatar(avatar);
        return ResultUtils.success(updateAvatar);
    }

    @PostMapping("/update/username")
    public BaseResponse<Boolean> updateUsername(@RequestBody UserUpdateUserNameRequest userUpdateUserNameRequest) {
        if (userUpdateUserNameRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userUpdateUserNameRequest.getUserName();
        boolean updateUsername = userService.updateUserName(username);
        return ResultUtils.success(updateUsername);
    }

    @GetMapping("/get/id")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<User> getUserById(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(userId);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    @GetMapping("/get/vo")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<UserVO> getUserVOById(String userId) {
        BaseResponse<User> userBaseResponse = getUserById(userId);
        User user = userBaseResponse.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }

    @PostMapping("/list/page")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, pageSize), userService.getQueryWrapper(userQueryRequest));
        return ResultUtils.success(userPage);
    }

    @PostMapping("/list/page/vo")
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = userQueryRequest.getCurrent();
        long pageSize = userQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        Page<User> userPage = userService.page(new Page<>(current, pageSize), userService.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }
}
