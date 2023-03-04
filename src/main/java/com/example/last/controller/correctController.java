package com.example.last.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.last.bean.charpcondition;
import com.example.last.bean.course;
import com.example.last.bean.exercisecondition;
import com.example.last.bean.exercises;
import com.example.last.service.charpconditionService;
import com.example.last.service.courseService;
import com.example.last.service.exerciseconditionService;
import com.example.last.service.exercisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class correctController {
    @Autowired
    exerciseconditionService exerciseconditionService;
    @Autowired
    charpconditionService charpconditionService;
    @Autowired
    courseService courseService;
    @Autowired
    exercisesService exercisesService;

    @PostMapping("/correctone")
    public String correctone(@RequestParam("score") Integer score,
                             @RequestParam("userid") Integer userid,
                             @RequestParam("charptercode") Integer charptercode,
                             @RequestParam("exerciseid") Integer exerciseid){
        exercisecondition excon=new exercisecondition();
        excon.setExerciseid(exerciseid);
        excon.setUserid(userid);
        excon.setGetscore(score);
        excon.setIscorrected(1);
        QueryWrapper<exercisecondition> qexcon=new QueryWrapper<>();
        qexcon.eq("exerciseid",exerciseid).eq("userid",userid);
        exerciseconditionService.update(excon,qexcon);
        QueryWrapper<course> qcourse=new QueryWrapper<>();
        qcourse.eq("chaptercode",charptercode);
        //isfinished
        QueryWrapper<exercises> qexer=new QueryWrapper<>();
        qexer.eq("charptercode",charptercode);
        List<exercises> exercisesList=exercisesService.list(qexer);
        List<exercisecondition> exerciseconditionList=new ArrayList<>();
        for(exercises ex:exercisesList){
            QueryWrapper<exercisecondition> qexercon=new QueryWrapper<>();
            qexercon.eq("exerciseid",ex.getIdexercises()).eq("userid",userid);
            exercisecondition excon1=exerciseconditionService.getOne(qexercon);
            exerciseconditionList.add(excon1);
        }
        course co=courseService.getOne(qcourse);
        Integer charpterid=co.getIdcourse();
        QueryWrapper<charpcondition> qcharpcon=new QueryWrapper<>();
        qcharpcon.eq("userid",userid).eq("charpterid",charpterid);
        charpcondition charpcon=charpconditionService.getOne(qcharpcon);
        int count=0;
        for(exercisecondition e:exerciseconditionList){
        if(e==null){
            continue;
        }
            if(e.getIscorrected()==0){
                count+=1;
            }
        }
        if(count==0){
            charpcon.setIsfinished(1);
        }
        charpcon.setQascore(charpcon.getQascore()+score);
        charpcon.setSumscore(charpcon.getJscore()+charpcon.getQascore()+charpcon.getOscore());
        charpconditionService.updateById(charpcon);
        return "redirect:/correct";
    }
}
