package com.example.last.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.last.bean.course;
import com.example.last.mapper.courseMapper;
import com.example.last.service.courseService;
import org.springframework.stereotype.Service;

@Service
public class courseServiceImpl extends ServiceImpl<courseMapper, course> implements courseService {
}
