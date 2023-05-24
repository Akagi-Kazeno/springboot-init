package com.shimakaze.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shimakaze.springbootinit.mapper.UserMapper;
import com.shimakaze.springbootinit.model.entity.User;
import com.shimakaze.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
