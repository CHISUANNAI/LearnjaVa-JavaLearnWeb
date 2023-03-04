package com.example.last.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.last.bean.node;
import com.example.last.mapper.nodeMapper;
import com.example.last.service.nodeService;
import org.springframework.stereotype.Service;

@Service
public class nodeServiceImpl extends ServiceImpl<nodeMapper, node> implements nodeService {
}
