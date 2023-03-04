package com.example.last.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.last.bean.exercises;
import com.example.last.mapper.exercisesMapper;
import com.example.last.service.exercisesService;
import org.springframework.stereotype.Service;

@Service
public class exercisesServiceImpl extends ServiceImpl<exercisesMapper, exercises> implements exercisesService {
}
