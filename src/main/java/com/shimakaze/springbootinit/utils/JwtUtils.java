package com.shimakaze.springbootinit.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.experimental.UtilityClass;

import java.util.Calendar;
import java.util.Map;

/**
 * JWT工具类
 */
@UtilityClass
public class JwtUtils {
    /**
     * token保存时间(ms)
     */
    private static final Integer EXPIRE_TIME = 15 * 60 * 1000;
    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "99468b8e80bc47629c5ace6f10828fd1";

    /**
     * 生成JWT
     *
     * @param map
     * @return
     */
    public static String createToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, EXPIRE_TIME);
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(TOKEN_SECRET));
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build().verify(token);
    }
}
