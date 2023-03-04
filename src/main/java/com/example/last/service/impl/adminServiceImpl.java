package com.example.last.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.last.bean.admin;
import com.example.last.mapper.adminMapper;
import com.example.last.service.adminService;
import org.springframework.stereotype.Service;

@Service
public class adminServiceImpl extends ServiceImpl<adminMapper, admin> implements adminService {
}
