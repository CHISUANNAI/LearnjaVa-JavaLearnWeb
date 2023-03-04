package com.example.last.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("javaclass")
public class javaclass {
    @TableId(value = "idjavaclass", type = IdType.AUTO)	//指定主键生成策略
    private Integer idjavaclass;
    private String name;
    private String teachid;
}
