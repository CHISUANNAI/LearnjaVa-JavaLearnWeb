package com.example.last.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("word")
public class word {
    @TableId(value = "idword", type = IdType.AUTO)	//指定主键生成策略
    private Integer idword;
    private Integer nodeid;
    private String name;
    private String content;
}
