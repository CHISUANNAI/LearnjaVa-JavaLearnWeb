package com.example.last.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.last.bean.course;
import com.example.last.service.courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class courseController {
    @Autowired
    courseService courseService;
    @GetMapping("/course")
    public String course(@RequestParam(value="pn",defaultValue = "1") Integer pn,
                         HttpServletRequest request,
                         Model model){
        Page<course> chapterpage=new Page<>(pn,20);
        Page<course> page=courseService.page(chapterpage,null);
        HttpSession session=request.getSession();
        String progress=session.getAttribute("progress").toString();
        String[] progressli=progress.split(",");
        List<String> progressLi=Arrays.asList(progressli);
        for(course cs:page.getRecords()){
            String[] li=cs.getDependency().split(",");
            cs.setDependency_show(li);
            int c=0;
            for(String s:li){
                if(progressLi.contains(s)){
                    c+=1;
                }
            }
            if(c!=li.length){
                cs.setIslocked(true);
            }
        }
        model.addAttribute("page",page);
        return "courses";
    }
}
