package com.shimakaze.springbootinit.aop;

import cn.hutool.core.util.IdUtil;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * 请求响应日志AOP
 */
@Aspect
@Component
@Slf4j
public class LogInterceptor {
    /**
     * 执行拦截
     */
    @Around("execution(* com.shimakaze.springbootinit.controller.*.*(..))")
    @Timed
    public Object doInterceptor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取请求路径
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 生成请求uuid
        String requestId = IdUtil.simpleUUID();
        String url = httpServletRequest.getRequestURI();
        // 获取请求参数
        Object[] args = proceedingJoinPoint.getArgs();
        // String requestParam = "[" + StringUtils.join(args, ", ") + "]";
        String requestParam = Arrays.toString(args);
        // 输出请求日志
        log.info("request start. id: {}, path: {}, ip: {}, params: {}", requestId, url, httpServletRequest.getRemoteHost(), requestParam);
        // 执行原方法
        Object result = proceedingJoinPoint.proceed();
        // 输出响应日志
        stopWatch.stop();
        long totalTimeMills = stopWatch.getTotalTimeMillis();
        log.info("request end. id: {}, cost: {}ms", requestId, totalTimeMills);
        return result;
    }
}
