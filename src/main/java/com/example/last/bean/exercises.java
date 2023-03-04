package com.example.last.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("exercises")
public class exercises {
    @TableId(value = "idexercises", type = IdType.AUTO)	//指定主键生成策略
    private Integer idexercises;
    private Integer type;
    private Integer score;
    private Integer charptercode;
    private String exercisecontent;
    private String rightanswer;
    private String options;
    private String Parsing;
    @TableField(exist = false)  //当前属性表中不存在
    private List<String> optionsDi;
    @TableField(exist = false)  //当前属性表中不存在
    private String answershow;
    @TableField(exist = false)  //当前属性表中不存在
    private String useranwser=" ";
}
