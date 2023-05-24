package com.shimakaze.springbootinit.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;

public class NetUtils {
    // 常量
    private static final String UNKNOWN = "unknown";
    // 可能的请求头
    private static final String[] HEADERS = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP"};

    /**
     * 获取客户端IP地址
     *
     * @param httpServletRequest
     * @return
     */
    public static String getIpAddress(HttpServletRequest httpServletRequest) {
        String ip = null;
        // 遍历请求头
        for (String header : HEADERS) {
            ip = httpServletRequest.getHeader(header);
            if (!StringUtils.isBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                break;
            }
        }
        if (StringUtils.isBlank(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }
        // 多个代理的情况,第一个IP为客户端真实IP,多个IP按照','分割
        ip = StringUtils.substringBefore(ip, ",");
        if (StringUtils.isBlank(ip)) {
            // 获取本机IP地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ip;
    }
}
