package com.example.last.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.last.bean.admin;
import com.example.last.bean.exercises;
import com.example.last.bean.node;
import com.example.last.bean.word;
import com.example.last.service.adminService;
import com.example.last.service.exercisesService;
import com.example.last.service.nodeService;
import com.example.last.service.wordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Objects;

@Controller
public class adminController {
    @Autowired
    exercisesService exercisesService;
    @Autowired
    wordService wordService;
    @Autowired
    nodeService nodeService;
    @Autowired
    adminService adminService;
    @GetMapping("/admin")
    public String admin(Model model) {
        String msg="";
        model.addAttribute("msg",msg);
        return "adminlogin";
    }

    @GetMapping("/addchoices")
    public String addchoices() {
        return "choices";
    }

    @GetMapping("/addjudges")
    public String addjudges() {
        return "judges";
    }

    @GetMapping("/addqa")
    public String addqa() {
        return "qas";
    }

    @GetMapping("/addword")
    public String addword() {
        return "word";
    }

    @PostMapping("/loginadmin")
    public String loginadmin(@RequestParam("email") String email,
                             @RequestParam("password") String password,
                             Model model) {
        QueryWrapper<admin> qad=new QueryWrapper<>();
        qad.eq("email",email);
        String msg;
        admin taradmin;
        if(adminService.getOne(qad)!=null){
        taradmin=adminService.getOne(qad);}
        else {
            msg="密码或用户名错误，请重新登录！";
            model.addAttribute("msg",msg);
            return "adminlogin";
        }
        if(!Objects.equals(taradmin.getPassword(), password)){
            msg="密码或用户名错误，请重新登录！";
            model.addAttribute("msg",msg);
            return "adminlogin";
        }
        return "redirect:/addchoices";
    }

    @PostMapping("/addchoices")
    public String addchoices(@RequestParam("exercisecontent") String exercisecontent,
                             @RequestParam("chartered") String chartered,
                             @RequestParam(value = "answer", defaultValue = "0") String answer,
                             @RequestParam("choice") String[] choice,
                             @RequestParam("score") String score,
                             @RequestParam("Parsing") String Parsing
    ) {
        String longchoice = "A、" + choice[0] + "sssB、" + choice[1] + "sss C、" + choice[2] + "sss D、" + choice[3];
        exercises ex = new exercises();
        ex.setCharptercode(Integer.parseInt(chartered));
        ex.setType(0);
        ex.setScore(Integer.parseInt(score));
        ex.setExercisecontent(exercisecontent);
        ex.setRightanswer(answer);
        ex.setOptions(longchoice);
        ex.setParsing(Parsing);
        exercisesService.save(ex);
        return "redirect:/addchoices";
    }

    @PostMapping("/addjudges")
    public String addjudges(@RequestParam("exercisecontent") String exercisecontent,
                            @RequestParam("chartered") String chartered,
                            @RequestParam(value = "answer", defaultValue = "0") String answer,
                            @RequestParam("score") String score,
                            @RequestParam("Parsing") String Parsing
    ) {
        String longchoice = "对、sss错、";
        exercises ex = new exercises();
        ex.setCharptercode(Integer.parseInt(chartered));
        ex.setType(1);
        ex.setScore(Integer.parseInt(score));
        ex.setExercisecontent(exercisecontent);
        ex.setRightanswer(answer);
        ex.setOptions(longchoice);
        ex.setParsing(Parsing);
        exercisesService.save(ex);
        return "redirect:/addjudges";
    }

    @PostMapping("/addqa")
    public String addqa(@RequestParam("exercisecontent") String exercisecontent,
                        @RequestParam("chartered") String chartered,
                        @RequestParam("score") String score,
                        @RequestParam("Parsing") String Parsing
    ) {
        exercises ex = new exercises();
        ex.setCharptercode(Integer.parseInt(chartered));
        ex.setType(2);
        ex.setScore(Integer.parseInt(score));
        ex.setExercisecontent(exercisecontent);
        ex.setParsing(Parsing);
        ex.setRightanswer(Parsing);
        exercisesService.save(ex);
        return "redirect:/addqa";
    }

    @PostMapping("/addword")
    public String addword(@RequestParam("chartercode") Integer chartercode,
                          @RequestParam("classcode") Integer classcode,
                          @RequestParam("nodecode") Integer nodecode,
                          @RequestParam("content") String content,
                          @RequestParam("name") String name) {
        QueryWrapper<node> qwnode = new QueryWrapper<>();
        qwnode.eq("chaptercode", chartercode).eq("classcode", classcode).eq("nodecode", nodecode);
        node tarn = nodeService.getOne(qwnode);
        Integer nodeid = tarn.getIdnode();
        word wordtoadd = new word();
        wordtoadd.setName(name);
        wordtoadd.setContent(content);
        wordtoadd.setNodeid(nodeid);
        wordService.saveOrUpdate(wordtoadd);
        return "redirect:/addword";
    }
}
