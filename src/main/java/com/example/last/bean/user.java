package com.example.last.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class user {
    @TableId(value = "iduser", type = IdType.AUTO)	//指定主键生成策略
    private Integer iduser;
    private String name;
    private Integer usertype;
    private String password;
    private String email;
    private Integer activecode;
    private String progress;
    private Integer classid;
    private String lastlearn;
    @TableField(exist = false)  //当前属性表中不存在
    private Integer lastLearnc;
    @TableField(exist = false)  //当前属性表中不存在
    private Integer lastLearnca;
    @TableField(exist = false)  //当前属性表中不存在
    private Integer lastLearnn;
}
