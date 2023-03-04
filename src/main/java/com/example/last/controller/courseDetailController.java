package com.example.last.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.last.bean.*;
import com.example.last.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class courseDetailController {
    @Autowired
    userService userService;
    @Autowired
    courseService courseService;
    @Autowired
    nodeService nodeService;
    @Autowired
    exercisesService exercisesService;
    @Autowired
    exerciseconditionService exerciseconditionService;
    @Autowired
    charpconditionService charpconditionService;
    @Autowired
    wordService wordService;

    @GetMapping("/courseDetail")
    public String courseDetail(@RequestParam(value = "chapterid", defaultValue = "1") Integer chapterid,
                               @RequestParam(value = "classcode", defaultValue = "1") Integer classcode,
                               @RequestParam(value = "nodecode", defaultValue = "1") Integer nodecode,
                               HttpServletRequest request,
                               Model model) {
        course targcs = courseService.getById(chapterid);
        HttpSession session = request.getSession();
        Integer userid = Integer.parseInt(session.getAttribute("userid").toString());
        //章号
        Integer cscode = targcs.getChaptercode();
        model.addAttribute("chapter", targcs);
        QueryWrapper<node> wrapper = new QueryWrapper<>();
        wrapper.eq("chaptercode", cscode);
        List<node> nodes = nodeService.list(wrapper);
        List<String> chapterclasses = new ArrayList<>();
        List<nodeclass> nodeclasses = new ArrayList<>();
        for (node n : nodes) {
            if (!chapterclasses.contains(n.getNodeclass())) {
                chapterclasses.add(n.getNodeclass());
                nodeclass nc = new nodeclass();
                nc.setClassname(n.getNodeclass());
                nc.setClassnode(n.getClasscode());
                nodeclasses.add(nc);
            }
        }
        for (nodeclass nc : nodeclasses) {
            List<node> classn = new ArrayList<>();
            for (node n : nodes) {
                if (Objects.equals(nc.getClassname(), n.getNodeclass())) {
                    classn.add(n);
                }
            }
            nc.setClassnodes(classn);
        }
        model.addAttribute("nodeclasses", nodeclasses);
        model.addAttribute("nodes", nodes);
        nodeclass targnc = nodeclasses.get(classcode - 1);
        node targn = targnc.getClassnodes().get(nodecode - 1);
        if (classcode == nodeclasses.size() && nodecode == targnc.getClassnodes().size()) {
            model.addAttribute("exerciselock", true);
        } else {
            model.addAttribute("exerciselock", false);
        }
        model.addAttribute("targnc", targnc);
        model.addAttribute("targn", targn);
        //知识点
        int nodeid = targn.getIdnode();
        QueryWrapper<word> qwwords = new QueryWrapper<>();
        qwwords.eq("nodeid", nodeid);
        List<word> words = wordService.list(qwwords);
        model.addAttribute("words", words);
        //练习栏
        QueryWrapper<charpcondition> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("userid", userid).eq("charpterid", chapterid);
        charpcondition testcondition = new charpcondition();
        if (charpconditionService.list(wrapper2).size() == 0) {
            testcondition.setCharpterid(chapterid);
            testcondition.setUserid(userid);
            testcondition.setJscore(0);
            testcondition.setOscore(0);
            testcondition.setQascore(0);
            testcondition.setSumqascore(0);
            testcondition.setSumjscore(0);
            testcondition.setSumoscore(0);
            testcondition.setUserid(userid);
            testcondition.setSumtestscore(0);
            testcondition.setTrycount(0);
        } else {
            testcondition = charpconditionService.getOne(wrapper2);
            Integer hours = 0;
            Integer minite = 0;
            Integer seconds = 0;
            hours = (testcondition.getTesttime() % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            minite = (testcondition.getTesttime() % (1000 * 60 * 60)) / (1000 * 60);
            seconds = (testcondition.getTesttime() % (1000 * 60)) / 1000;
            testcondition.setTime_tran(hours.toString() + "小时" + minite.toString() + "分" + seconds.toString() + "秒");
        }
        //习题
        QueryWrapper<exercises> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("charptercode", cscode);
        List<exercises> exercisesList = exercisesService.list(wrapper1);
        List<exercises> judgeli = new ArrayList<>();
        List<exercises> optionli = new ArrayList<>();
        List<exercises> qali = new ArrayList<>();
        exercisecondition excon=new exercisecondition();
        for (exercises ex : exercisesList) {
            if (ex.getType() == 0) {
                List<String> opl = Arrays.asList(ex.getOptions().split("sss"));
                ex.setOptionsDi(opl);
                if (Objects.equals(ex.getRightanswer(), "0")) {
                    ex.setAnswershow("A");
                } else if (Objects.equals(ex.getRightanswer(), "1")) {
                    ex.setAnswershow("B");
                } else if (Objects.equals(ex.getRightanswer(), "2")) {
                    ex.setAnswershow("C");
                } else {
                    ex.setAnswershow("D");
                }
                if (testcondition.getTrycount() > 0) {
                    QueryWrapper<exercisecondition> qexcon = new QueryWrapper<>();
                    qexcon.eq("userid", userid).eq("exerciseid", ex.getIdexercises());
                    excon = exerciseconditionService.getOne(qexcon);
                    if(excon!=null){
                    if (Objects.equals(excon.getUseranser(), "0")) {
                        ex.setUseranwser("A");
                    } else if (Objects.equals(excon.getUseranser(), "1")) {
                        ex.setUseranwser("B");
                    } else if (Objects.equals(excon.getUseranser(), "2")) {
                        ex.setUseranwser("C");
                    } else {
                        ex.setUseranwser("D");
                    }}
                }
                optionli.add(ex);
            } else if (ex.getType() == 1) {
                List<String> opl = Arrays.asList(ex.getOptions().split("sss"));
                ex.setOptionsDi(opl);
                if (Objects.equals(ex.getRightanswer(), "0")) {
                    ex.setAnswershow("对");
                } else {
                    ex.setAnswershow("错");
                }
                if (testcondition.getTrycount() > 0) {
                    QueryWrapper<exercisecondition> qexcon = new QueryWrapper<>();
                    qexcon.eq("userid", userid).eq("exerciseid", ex.getIdexercises());
                    excon = exerciseconditionService.getOne(qexcon);
                    if(excon!=null) {
                        if (Objects.equals(excon.getUseranser(), "0")) {
                            ex.setUseranwser("对");
                        } else {
                            ex.setUseranwser("错");
                        }
                    }
                }
                judgeli.add(ex);
            } else {
                ex.setAnswershow(ex.getRightanswer());
                if (testcondition.getTrycount() > 0) {
                    QueryWrapper<exercisecondition> qexcon = new QueryWrapper<>();
                    qexcon.eq("userid", userid).eq("exerciseid", ex.getIdexercises());
                    excon = exerciseconditionService.getOne(qexcon);
                    System.out.println(excon);
                    if(excon!=null){
                    ex.setUseranwser(excon.getUseranser());}
                }
                qali.add(ex);
            }
        }
        model.addAttribute("testcondition", testcondition);
        model.addAttribute("judgeli", judgeli);
        model.addAttribute("optionli", optionli);
        model.addAttribute("qali", qali);
        //上次进度更新
        String lastLearn = cscode.toString() + "," + classcode + "," + nodecode;
        user targu = userService.getById(userid);
        targu.setLastlearn(lastLearn);
        userService.updateById(targu);
        session.setAttribute("lastLearn", lastLearn);
        return "course-details";
    }

    @PostMapping("/submitExercise")
    public String submitExercise(@RequestParam Map<String, String> map,
                                 Model model,
                                 HttpServletRequest request) {
        Integer Jscore = 0;
        Integer SJscore = 0;
        Integer Oscore = 0;
        Integer SOscore = 0;
        Integer SQAscore = 0;
        Integer charpterid = -1;
        Integer testtime = 0;
        HttpSession session = request.getSession();
        //userid,charpterid
        Integer userid = Integer.parseInt(session.getAttribute("userid").toString());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!Objects.equals(entry.getKey(), "charpterid") && !Objects.equals(entry.getKey(), "time-diff")) {
                String s = entry.getKey().substring(1);
                //exerciseid
                Integer id = Integer.parseInt(s);
                exercises ex = exercisesService.getById(id);
                exercisecondition excon = new exercisecondition();
                excon.setUserid(userid);
                excon.setExerciseid(id);
                excon.setUseranser(entry.getValue());
                if (entry.getKey().contains("j")) {
                    if (Integer.parseInt(entry.getValue()) == Integer.parseInt(ex.getRightanswer())) {
                        excon.setGetscore(ex.getScore());
                        Jscore += ex.getScore();
                        SJscore += ex.getScore();
                    } else {
                        SJscore += ex.getScore();
                        excon.setGetscore(0);
                    }
                    excon.setIscorrected(1);
                } else if (entry.getKey().contains("o")) {
                    if (Integer.parseInt(entry.getValue()) == Integer.parseInt(ex.getRightanswer())) {
                        excon.setGetscore(ex.getScore());
                        Oscore += ex.getScore();
                        SOscore += ex.getScore();
                    } else {
                        SOscore += ex.getScore();
                        excon.setGetscore(0);
                    }
                    excon.setIscorrected(1);
                } else if (entry.getKey().contains("q")) {
                    excon.setIscorrected(0);
                    SQAscore += ex.getScore();
                }
                QueryWrapper<exercisecondition> wrapper = new QueryWrapper<>();
                wrapper.eq("userid", userid).eq("exerciseid", id);
                if (exerciseconditionService.list(wrapper).size() == 0) {
                    exerciseconditionService.save(excon);
                } else {
//                List<exercisecondition> exerciseconditionList=exerciseconditionService.list(wrapper);
//                excon.setTryid(exerciseconditionList.get(0).getTryid());
                    exercisecondition e = exerciseconditionService.getOne(wrapper);
                    excon.setTryid(e.getTryid());
                    exerciseconditionService.updateById(excon);
                }
            } else if (Objects.equals(entry.getKey(), "charpterid")) {
                charpterid = Integer.parseInt(entry.getValue());
            } else {
                testtime = Integer.parseInt(entry.getValue());
            }
            //System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
        //进度
        String progress = session.getAttribute("progress").toString();
        Integer charpcode = courseService.getById(charpterid).getChaptercode();
        List<String> progressli = Arrays.asList(progress.split(","));
        if (!progressli.contains(charpcode.toString())) {
            progress = progress + "," + charpcode;
        }
        session.setAttribute("progress", progress);
        user u = userService.getById(userid);
        u.setProgress(progress);
        userService.updateById(u);
        //
        Integer Sumall = SJscore + SQAscore + SOscore;
        charpcondition charpcon = new charpcondition();
        charpcon.setCharpterid(charpterid);
        charpcon.setJscore(Jscore);
        charpcon.setOscore(Oscore);
        charpcon.setQascore(0);
        charpcon.setSumqascore(SQAscore);
        charpcon.setSumjscore(SJscore);
        charpcon.setSumoscore(SOscore);
        charpcon.setUserid(userid);
        charpcon.setSumscore(0);
        charpcon.setIsfinished(0);
        charpcon.setSumtestscore(Sumall);
        charpcon.setTesttime(testtime);
        QueryWrapper<charpcondition> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid).eq("charpterid", charpterid);
        if (charpconditionService.list(wrapper).size() == 0) {
            charpcon.setTrycount(1);
            charpconditionService.save(charpcon);
        } else {
            charpcondition c = charpconditionService.getOne(wrapper);
            charpcon.setTesttryid(c.getTesttryid());
            charpcon.setTrycount(c.getTrycount() + 1);
            charpconditionService.updateById(charpcon);
        }
        model.addAttribute("testCondition", charpcon);
        return "redirect:/courseDetail?chapterid=" + charpterid;
    }
}
