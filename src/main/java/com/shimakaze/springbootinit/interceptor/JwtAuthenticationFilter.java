package com.shimakaze.springbootinit.interceptor;

import com.shimakaze.springbootinit.common.ErrorCode;
import com.shimakaze.springbootinit.exception.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取当前请求的uri
        String uri = request.getRequestURI();
        if (uri.endsWith("/error") || uri.endsWith("/webjars/**") || uri.endsWith("/favicon.ico") || uri.endsWith("/img/**") || uri.endsWith("/user/login") || uri.endsWith("/user/register")) {
            filterChain.doFilter(request, response);
        }
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        
    }
}
