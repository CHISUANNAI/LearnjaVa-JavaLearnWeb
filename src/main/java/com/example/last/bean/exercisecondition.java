package com.example.last.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("exercisecondition")
public class exercisecondition {
    @TableId(value = "tryid", type = IdType.AUTO)	//指定主键生成策略
    private Integer tryid;
    private Integer exerciseid;
    private Integer userid;
    private Integer getscore;
    private String useranser;
    private Integer iscorrected;
}
