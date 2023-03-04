package com.example.last.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.last.bean.exercisecondition;
import com.example.last.mapper.exerciseconditionMapper;
import com.example.last.service.exerciseconditionService;
import org.springframework.stereotype.Service;

@Service
public class exerciseconditionServiceImpl extends ServiceImpl<exerciseconditionMapper, exercisecondition> implements exerciseconditionService{
}
