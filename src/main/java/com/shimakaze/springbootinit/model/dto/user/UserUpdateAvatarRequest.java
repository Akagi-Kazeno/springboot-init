package com.shimakaze.springbootinit.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户修改头像请求
 */
@Data
public class UserUpdateAvatarRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 头像url
     */
    private String avatar;
}