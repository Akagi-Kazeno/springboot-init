package com.shimakaze.springbootinit.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * 请求工具类
 */
@UtilityClass
public class RequestUtils {
    @SneakyThrows
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}