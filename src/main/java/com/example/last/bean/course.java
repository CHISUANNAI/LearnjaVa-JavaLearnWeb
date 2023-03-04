package com.example.last.bean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("course")
public class course {
    @TableId(value = "idcourse", type = IdType.AUTO)	//指定主键生成策略
    private Integer idcourse;
    private String chaptername;
    private Integer chaptercode;
    private String dependency;
    @TableField(exist = false)  //当前属性表中不存在
    private Boolean islocked;
    @TableField(exist = false)  //当前属性表中不存在
    private String[] dependency_show;
}
