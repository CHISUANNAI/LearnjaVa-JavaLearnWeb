package com.example.last.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admin")
public class admin {
    @TableId(value = "idadmin", type = IdType.AUTO)	//指定主键生成策略
    private Integer idadmin;
    private String email;
    private String password;
}
