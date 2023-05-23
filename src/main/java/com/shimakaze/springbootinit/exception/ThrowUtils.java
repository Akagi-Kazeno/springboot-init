package com.shimakaze.springbootinit.exception;

import com.shimakaze.springbootinit.common.ErrorCode;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

/**
 * 抛出异常工具类
 */
@UtilityClass
public class ThrowUtils {
    /**
     * 条件成立抛出异常
     *
     * @param condition
     * @param runtimeException
     */
    @SneakyThrows
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立抛出异常
     *
     * @param condition
     * @param errorCode
     */
    @SneakyThrows
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立抛出异常
     *
     * @param condition
     * @param errorCode
     * @param message
     */
    @SneakyThrows
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
