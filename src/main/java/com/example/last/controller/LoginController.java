package com.example.last.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.last.bean.javaclass;
import com.example.last.bean.user;
import com.example.last.service.courseService;
import com.example.last.service.javaclassService;
import com.example.last.service.nodeService;
import com.example.last.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class LoginController {
    @Autowired
    userService userService;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    javaclassService javaclassService;
    @Autowired
    courseService courseService;
    @Autowired
    nodeService nodeService;
    @GetMapping(value = {"/", "/login"})
    public String getLogin(Model model) {
        return "login";
    }

    @PostMapping("/register")
    public String register(@RequestParam("Username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("usertype") Integer usertype,
                           @RequestParam("password") String password,
                           @RequestParam("conf_password") String conf_password,
                           Model model) {
        QueryWrapper<user> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        long n = userService.count(wrapper);
        if (n > 0) {
            model.addAttribute("msg", "注册失败，此邮箱已被注册");
            return "login";
        }
        if (!Objects.equals(password, conf_password)) {
            model.addAttribute("msg", "注册失败，两次密码不一致");
            return "login";
        }
        if (password.length() < 6) {
            model.addAttribute("msg", "注册失败，密码应大于6位");
            return "login";
        }
        user user = new user();
        user.setEmail(email);
        user.setUsertype(usertype);
        user.setName(username);
        user.setPassword(password);
        user.setClassid(0);
        userService.save(user);
        model.addAttribute("msg", "注册成功，请登录");
        return "redirect:/login";
    }

    @GetMapping("/index")
    public String getIndex(HttpServletRequest request,
                           Model model) {
        HttpSession session=request.getSession();
        List<String> lastli= Arrays.asList(session.getAttribute("lastLearn").toString().split(","));
        Integer lac=Integer.parseInt(lastli.get(0));
        Integer laca=Integer.parseInt(lastli.get(1));
        Integer lan=Integer.parseInt(lastli.get(2));
        String progress=session.getAttribute("progress").toString();
        user targetU=new user();
        targetU.setLastLearnc(lac);
        targetU.setLastLearnca(laca);
        targetU.setLastLearnn(lan);
        targetU.setProgress(progress);
        model.addAttribute("user",targetU);
        //学习进度
        Long n=courseService.count();
        String s=targetU.getProgress();
        String[] sl=s.split(",");
        Integer learned=sl.length-1;
        Long prgressrate=learned*100/n;
        model.addAttribute("prgressrate",prgressrate);
        return "index1";
    }

    @PostMapping("/userlogin")
    public String userlogin(@RequestParam("email") String email,
                            @RequestParam("password") String password,
//                            HttpServletRequest request,
//                            HttpServletResponse response,
                            HttpSession session,
                            Model model) {
        user user = new user();
        user.setEmail(email);
        user.setPassword(password);
        QueryWrapper<user> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        List<user> users=userService.list(wrapper);
        if(users.size()<1){
            model.addAttribute("msg", "登录失败，账号或密码错误");
            return "login";
        }
        user targetU = userService.getOne(wrapper);
        if (!Objects.equals(targetU.getPassword(), user.getPassword())) {
            model.addAttribute("msg", "登录失败，账号或密码错误");
            return "login";
        } else {
//            Cookie cookie = new Cookie("my_session_id","123456");
//            response.addCookie(cookie);
//            request.getSession().setAttribute("sessionid","88888888");
            session.setAttribute("useremail",email);
            session.setAttribute("name",targetU.getName());
            session.setAttribute("userid",targetU.getIduser());
            if(targetU.getUsertype()==0){
                session.setAttribute("usertype",true);
            }else {
                session.setAttribute("usertype",false);
            }
            session.setAttribute("userclassid",targetU.getClassid());
            session.setAttribute("progress",targetU.getProgress());
            session.setAttribute("lastLearn",targetU.getLastlearn());
            if(targetU.getClassid()!=null){
            javaclass jc=javaclassService.getById(targetU.getClassid());
            session.setAttribute("userclassname",jc.getName());}
            return "redirect:/index";
        }
    }

    @PostMapping("/findpwd")
    public String findpwd(@RequestParam("email") String email,
                          @RequestParam("activecode") Integer activecode,
                          @RequestParam("password") String password,
                          @RequestParam("confpassword") String confpassword,
                          Model model) {
        QueryWrapper<user> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        user targetU=userService.getOne(wrapper);
        if (!Objects.equals(targetU.getActivecode(), activecode)) {
            model.addAttribute("msg", "找回密码失败，动态码错误");
            return "login";
        }
        if (!Objects.equals(password, confpassword)) {
            model.addAttribute("msg", "找回密码失败，两次密码不一致");
            return "login";
        }
        targetU.setPassword(password);
        targetU.setActivecode(null);
        userService.update(targetU,wrapper);
        model.addAttribute("msg", "找回密码成功，请登录");
        return "login";
    }

    @ResponseBody
    @GetMapping("/getActive")
    public String getActive(@RequestParam("email") String email) {
// 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject("一封来自LearnjaVa的邮件");
        // 设置邮件发送者，这个跟application.yml中设置的要一致
        message.setFrom("813728594@qq.com");
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        // message.setTo("10*****16@qq.com","12****32*qq.com");
        message.setTo(email);
        // 设置邮件抄送人，可以有多个抄送人
        //message.setCc("12****32*qq.com");
        // 设置隐秘抄送人，可以有多个
        //message.setBcc("7******9@qq.com");
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        Random r = new Random();
        Integer active = r.nextInt(10000);
        String s = "您的动态码是";
        s = s + active;
        message.setText(s);
        // 发送邮件
        javaMailSender.send(message);
        //保存动态码
        QueryWrapper<user> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        user targetU = userService.getOne(wrapper);
        targetU.setActivecode(active);
        userService.update(targetU, wrapper);
        return email;
    }
}
