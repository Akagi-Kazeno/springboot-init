package com.shimakaze.springbootinit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    /**
     * 任意角色
     *
     * @return
     */
    String[] anyRole() default "";

    /**
     * 必须是某个角色
     *
     * @return
     */
    String mustRole() default "";
}