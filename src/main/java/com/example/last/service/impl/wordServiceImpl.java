package com.example.last.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.last.bean.word;
import com.example.last.mapper.wordMapper;
import com.example.last.service.wordService;
import org.springframework.stereotype.Service;

@Service
public class wordServiceImpl extends ServiceImpl<wordMapper, word> implements wordService {
}
