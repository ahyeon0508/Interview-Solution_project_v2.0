package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.*;
import com.springboot.interview_solution.dto.GradeListDto;
import com.springboot.interview_solution.dto.LetterDto;
import com.springboot.interview_solution.dto.TranscriptDto;
import com.springboot.interview_solution.service.InfoService;
import com.springboot.interview_solution.service.LetterService;
import com.springboot.interview_solution.service.TranscriptService;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;

@AllArgsConstructor
@Controller
public class InfoController {

    @Autowired
    private final InfoService infoService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final TranscriptService transcriptService;
    @Autowired
    private final LetterService letterService;

    private static ArrayList<Grade> gradeList = new ArrayList<Grade>();
    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @RequestMapping(value = "/infoStudent", method = RequestMethod.GET)
    public ModelAndView getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ModelAndView mv = new ModelAndView("upload");
        GradeList gradeInfo = new GradeList();
        gradeList = infoService.getStudentGrade(user);
        gradeInfo.setGrades(gradeList);
        mv.addObject("gradeInfo", gradeInfo);
        Transcript transcript = new Transcript();
        transcript = transcriptService.getStudentTranscript(user);
        mv.addObject("transcript", transcript);
        Letter letter = letterService.getStudentLetter(user);
        if(letter.getQuestion3() != null && letter.getQuestion3().equals(""))
            letter.setQuestion3(null);
        mv.addObject("letter", letter);
        return mv;
    }

    @RequestMapping(value = "/infoStudent/grade", method = RequestMethod.POST)
    public String postInfo(@ModelAttribute("gradeReport") GradeListDto gradeInfo){
        System.out.println(gradeInfo.getGrades());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
//        infoService.setStudentGrade(gradeInfo.getGrades(), user);
        return "redirect:/infoStudent";
    }


//    @RequestMapping(value = "/infoStudent/grade", method = RequestMethod.POST)
//    public String postInfo(@ModelAttribute ArrayList<Grade> gradeInfo){
//        System.out.println(gradeInfo);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
////        infoService.setStudentGrade(gradeInfo.getGrades(), user);
//        return "redirect:/infoStudent";
//    }
//    @RequestMapping(value = "/infoStudent/grade", method = RequestMethod.POST)
//    public String postInfo(@ModelAttribute("gradeReport") ArrayList<Grade> gradeInfo){
//        System.out.println(gradeInfo);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
////        infoService.setStudentGrade(gradeInfo, user);
//        return "redirect:/infoStudent";
//    }

    @RequestMapping(value = "/infoStudent/letter", method = RequestMethod.POST)
    public String postInfo(LetterDto letter){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        letterService.setStudentLetter(letter, user);
        return "redirect:/infoStudent";
    }

    @RequestMapping(value = "infoStudent/transcript", method = RequestMethod.POST)
    public String postInfo(TranscriptDto transcriptDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        transcriptService.setStudentTranscript(transcriptDto, user);
        return "redirect:/infoStudent";
    }
}
