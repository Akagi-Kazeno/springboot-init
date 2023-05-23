package com.shimakaze.springbootinit.model.vo;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 账号
     */
    private String account;
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 权限
     */
    private String role;
    /**
     * 创建时间
     */
    private OffsetDateTime createTime;
    /**
     * 更新时间
     */
    private OffsetDateTime updateTime;
    /**
     * 上次登录时间
     */
    private OffsetDateTime lastLoginTime;
    /**
     * 上次登录ip
     */
    private String lastLoginIp;
}
