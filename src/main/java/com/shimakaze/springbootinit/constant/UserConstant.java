package com.shimakaze.springbootinit.constant;

/**
 * 用户常量
 */
public interface UserConstant {
    /**
     * 用户登录状态
     */
    String USER_LOGIN_STATE = "user_login";

    /**
     * 默认权限
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员权限
     */
    String ADMIN_ROLE = "admin";

    /**
     * ban
     */
    String BAN_ROLE = "ban";

    /**
     * 是否删除
     */
    String ALREADY_DELETE = "1";
    String NOT_DELETE = "0";
}
