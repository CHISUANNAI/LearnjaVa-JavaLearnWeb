package com.example.last.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.last.bean.*;
import com.example.last.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class indexController {
    @Autowired
    userService userService;
    @Autowired
    javaclassService javaclassService;
    @Autowired
    exerciseconditionService exerciseconditionService;
    @Autowired
    exercisesService exercisesService;
    @Autowired
    courseService courseService;
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        HttpSession session=request.getSession();
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/abab")
    public String abab(){

        return "exercisecondition";
    }
    @ResponseBody
    @GetMapping("/addClass")
    public Integer addClass(@RequestParam("classname") String name,
                            HttpServletRequest request){
        QueryWrapper<javaclass> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        Long targetJC = javaclassService.count(wrapper);
        if(targetJC!=0){
            return 0;
        }else {
            HttpSession session=request.getSession();
            String id=session.getAttribute("userid").toString();
            javaclass JC=new javaclass();
            JC.setName(name);
            JC.setTeachid(id);
            javaclassService.save(JC);
            javaclass newjc=javaclassService.getOne(wrapper);
            user teacher=userService.getById(id);
            teacher.setClassid(newjc.getIdjavaclass());
            userService.updateById(teacher);
            session.setAttribute("classid",newjc.getIdjavaclass());
            return 1;
        }
    }
    @ResponseBody
    @PostMapping("/joinClass")
    public Integer joinClass(@RequestParam("classname") String name,
                             HttpServletRequest request){
        QueryWrapper<javaclass> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        Long targetJC = javaclassService.count(wrapper);
        HttpSession session=request.getSession();
        String id=session.getAttribute("userid").toString();
        user stu=userService.getById(id);
        System.out.println(stu.getClassid());
        if(targetJC==0){
            return 0;
        }else {
            if(stu.getClassid()!=6){
                return 1;
            }
            javaclass jc=javaclassService.getOne(wrapper);
            stu.setClassid(jc.getIdjavaclass());
            userService.updateById(stu);
            session.setAttribute("userclassid",jc.getIdjavaclass());
            session.setAttribute("userclassname",jc.getName());
            return 2;
        }
    }

    @GetMapping("/correct")
    public String correct(Model model,
                          HttpServletRequest request){
        HttpSession session=request.getSession();
        Integer userid=Integer.parseInt(session.getAttribute("userid").toString());
        Integer classid=Integer.parseInt(session.getAttribute("userclassid").toString());
        //查询班级
        QueryWrapper<user> quser=new QueryWrapper<>();
        quser.eq("classid",classid).eq("usertype",0);
        List<user> stuli=userService.list(quser);
        //查询做题情况
//        Page<course> chapterpage=new Page<>(pn,5);
//        Page<course> page=courseService.page(chapterpage,null);
        List<exercisecondition> exconli=new ArrayList<>();
        List<correctview> correctviews=new ArrayList<>();
        for(user u:stuli){
            QueryWrapper<exercisecondition> qexcon=new QueryWrapper<>();
            qexcon.eq("userid",u.getIduser()).eq("iscorrected",0);
            List<exercisecondition> eli=exerciseconditionService.list(qexcon);
            exconli.addAll(eli);
        }
        if(exconli.size()==0){
            correctview c=new correctview();
            model.addAttribute("msg","没有需要批改的学生");
            model.addAttribute("targeEx",c);
            return "nothingtocorrect";
        }
        for(exercisecondition e:exconli){
            correctview corv=new correctview();
            corv.setExerciseid(e.getExerciseid());
            corv.setStuid(e.getUserid());
            corv.setAnswer(e.getUseranser());
            correctviews.add(corv);
        }
        for(correctview c:correctviews){
            user u=userService.getById(c.getStuid());
            exercises e=exercisesService.getById(c.getExerciseid());
            c.setCharptercode(e.getCharptercode());
            QueryWrapper<course> qcourse=new QueryWrapper<>();
            qcourse.eq("chaptercode",c.getCharptercode());
            course course1=courseService.getOne(qcourse);
            c.setScore(e.getScore().toString());
            c.setCharptername(course1.getChaptername());
            c.setExcontent(e.getExercisecontent());
            c.setStuname(u.getName());
        }
        model.addAttribute("targeEx",correctviews.get(0));
        model.addAttribute("unconum",correctviews.size());
        return "correct";
    }
    //查看做题情况
    @GetMapping("/exercisesCondition")
    public String exercisesCondition(){
        return "exercisecondition";
    }

}
