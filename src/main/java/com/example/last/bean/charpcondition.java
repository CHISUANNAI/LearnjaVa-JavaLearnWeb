package com.example.last.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("charpcondition")
public class charpcondition {
    @TableId(value = "testtryid", type = IdType.AUTO)	//指定主键生成策略
    private Integer testtryid;
    private Integer userid;
    private Integer charpterid;
    private Integer sumscore;
    private Integer jscore;
    private Integer oscore;
    private Integer qascore;
    private Integer sumjscore;
    private Integer sumoscore;
    private Integer sumqascore;
    private Integer trycount;
    private Integer sumtestscore;
    private Integer isfinished;
    private Integer testtime;
    @TableField(exist = false)  //当前属性表中不存在
    private String time_tran;
}
