package com.shimakaze.springbootinit.aop;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.shimakaze.springbootinit.annotation.AuthCheck;
import com.shimakaze.springbootinit.common.ErrorCode;
import com.shimakaze.springbootinit.exception.BusinessException;
import com.shimakaze.springbootinit.model.vo.UserVO;
import com.shimakaze.springbootinit.service.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限校验AOP
 */
@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param proceedingJoinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint proceedingJoinPoint, AuthCheck authCheck) throws Throwable {
        List<String> anyRole = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNoneBlank).collect(Collectors.toList());
        String mustRole = authCheck.mustRole();
        //当前登录用户
        UserVO userVO = userService.getLoginUserVO();
        //任意权限通过
        if (CollectionUtils.isNotEmpty(anyRole)) {
            String userRole = userVO.getRole();
            if (!anyRole.contains(userRole)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
        //具有某权限通过
        if (StringUtils.isNotBlank(mustRole)) {
            String userRole = userVO.getRole();
            if (!mustRole.equals(userRole)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
        //通过校验,放行
        return proceedingJoinPoint.proceed();
    }
}
