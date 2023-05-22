package com.shimakaze.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@TableName("\"user\"")
@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class User implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId(value = "user_id")
    private String userId;
    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * 账号
     */
    @TableField(value = "account")
    private String account;
    /**
     * 密码
     */
    @TableField(value = "password", select = false)
    private String password;
    /**
     * 用户头像url
     */
    @TableField(value = "avatar")
    private String avatar;
    /**
     * 权限
     */
    @TableField(value = "role")
    private String role;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private OffsetDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private OffsetDateTime updateTime;
    /**
     * 上次登录时间
     */
    @TableField(value = "last_login_time")
    private OffsetDateTime lastLoginTime;
    /**
     * 上次登录ip
     */
    @TableField(value = "last_login_ip")
    private String lastLoginIp;
    /**
     * 是否删除
     */
    @TableLogic
    private String isDelete;
}
