package com.example.last.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("node")
public class node {
    @TableId(value = "idnode", type = IdType.AUTO)	//指定主键生成策略
    private Integer idnode;
    private Integer nodecode;
    private String nodeclass;
    private String nodename;
    private String content;
    private Integer chaptercode;
    private Integer classcode;
}
