package com.shimakaze.springbootinit.interceptor;

import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shimakaze.springbootinit.common.ErrorCode;
import com.shimakaze.springbootinit.exception.BusinessException;
import com.shimakaze.springbootinit.model.entity.User;
import com.shimakaze.springbootinit.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;

/**
 * JWT拦截器
 */
@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    /**
     * 拦截请求头
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        BusinessException businessException;
        String token = request.getHeader("Authorization");
        try {
            DecodedJWT verify = JwtUtils.verify(token);
            String userId = verify.getClaim("userId").asString();
            try (Jedis jedis = RedisDS.create().getJedis()) {
                User user = JSON.parseObject(jedis.get("token: " + userId), User.class);
                request.setAttribute("user", user);
                return true;
            }
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            log.error("无效签名", e);
            businessException = new BusinessException(ErrorCode.NO_AUTH_ERROR, "无效签名");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            log.error("token过期", e);
            businessException = new BusinessException(ErrorCode.NO_AUTH_ERROR, "token过期");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            log.error("token算法不一致", e);
            businessException = new BusinessException(ErrorCode.NO_AUTH_ERROR, "token算法不一致");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("token无效", e);
            businessException = new BusinessException(ErrorCode.NO_AUTH_ERROR, "token无效");
        }
        String json = new ObjectMapper().writeValueAsString(businessException);
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Disposition,Origin, X-Requested-With, Content-Type, Accept,Authorization,id_token");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT, OPTIONS");
        response.getWriter().write(json);
        return false;
    }
}
