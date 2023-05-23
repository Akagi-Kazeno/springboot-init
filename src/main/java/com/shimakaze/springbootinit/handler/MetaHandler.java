package com.shimakaze.springbootinit.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

/**
 * 序列化Mybatis-Plus插入时间
 */
@Slf4j
@Component
public class MetaHandler implements MetaObjectHandler {
    /**
     * 插入时间
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("create_time", OffsetDateTime.now(), metaObject);
        this.setFieldValByName("update_time", OffsetDateTime.now(), metaObject);
    }

    /**
     * 更新时间
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("update_time", OffsetDateTime.now(), metaObject);
    }
}
