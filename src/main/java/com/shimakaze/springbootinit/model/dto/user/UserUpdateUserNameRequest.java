package com.shimakaze.springbootinit.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户修改用户名请求
 */
@Data
public class UserUpdateUserNameRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private String userName;
}
