package com.shimakaze.springbootinit.common;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

/**
 * 返回工具类
 */
@UtilityClass
public class ResultUtils {
    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, "OK", data);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    @SneakyThrows
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    @SneakyThrows
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, message, null);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @param message
     * @return
     */
    @SneakyThrows
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), message, null);
    }
}
