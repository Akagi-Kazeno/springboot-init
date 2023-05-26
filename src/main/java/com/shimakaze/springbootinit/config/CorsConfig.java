package com.shimakaze.springbootinit.config;

import com.shimakaze.springbootinit.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**
     * 头像上传目录
     */
    @Value("${prop.avatar-folder}")
    private String AVATAR_FOLDER;

    /**
     * 文件上传目录
     */
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    /**
     * 注册拦截器
     *
     * @param interceptorRegistry
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry interceptorRegistry) {
        WebMvcConfigurer.super.addInterceptors(interceptorRegistry);
        interceptorRegistry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/user/register");
//                .excludePathPatterns("/**");
    }

    /**
     * 跨域请求配置
     *
     * @param corsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        //覆盖所有请求
        corsRegistry.addMapping("/**")
                // 允许发送cookie
                .allowCredentials(true)
                // 放行的域名(必须使用patterns,否则*会和allowCredentials冲突)
                .allowedOriginPatterns("*")
                // .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    /**
     * 添加头像上传后返回头
     *
     * @param resourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry.addResourceHandler("/img/**").addResourceLocations("file:" + AVATAR_FOLDER);
        resourceHandlerRegistry.addResourceHandler("/file/**").addResourceLocations("file:" + UPLOAD_FOLDER);
    }
}
