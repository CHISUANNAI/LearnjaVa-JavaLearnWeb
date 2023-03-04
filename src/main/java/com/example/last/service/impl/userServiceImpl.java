package com.example.last.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.last.bean.user;
import com.example.last.mapper.userMapper;
import com.example.last.service.userService;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl extends ServiceImpl<userMapper, user> implements userService {
}
