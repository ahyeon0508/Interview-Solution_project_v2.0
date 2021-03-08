package com.springboot.interview_solution.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.interview_solution.domain.*;
import com.springboot.interview_solution.dto.GradeDto;
import com.springboot.interview_solution.dto.GradeListDto;
import com.springboot.interview_solution.dto.LetterDto;
import com.springboot.interview_solution.dto.TranscriptDto;
import com.springboot.interview_solution.service.InfoService;
import com.springboot.interview_solution.service.LetterService;
import com.springboot.interview_solution.service.TranscriptService;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
        Transcript transcript = transcriptService.getStudentTranscript(user);
        mv.addObject("transcript", transcript);
        Letter letter = letterService.getStudentLetter(user);
        if(letter.getQuestion3() != null && letter.getQuestion3().equals(""))
            letter.setQuestion3(null);
        mv.addObject("letter", letter);
        return mv;
    }
//
//    @RequestMapping(value = "/infoStudent/grade", method = {RequestMethod.GET, RequestMethod.POST})
//    public String postInfo(GradeListDto gradeInfo){
//        System.out.println(gradeInfo.getGrades());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
////        infoService.setStudentGrade(gradeInfo.getGrades(), user);
//        return "redirect:/infoStudent";
//    }


//    @RequestMapping(value = "/infoStudent/grade", method = {RequestMethod.GET, RequestMethod.POST})
//    public String postInfo(GradeListDto gradeInfo){
//        System.out.println(gradeInfo.getGrades());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
////        infoService.setStudentGrade(gradeInfo.getGrades(), user);
//        return "redirect:/infoStudent";
//    }

    @RequestMapping(value = "/infoStudent/grade", method = {RequestMethod.GET, RequestMethod.POST})
    public String postInfo(Object gradeInfo){
        System.out.println(gradeInfo.getClass());
        if(gradeInfo instanceof GradeDto) {
            System.out.println("A");
        } else if(gradeInfo instanceof GradeListDto) {
            System.out.println("B");
        } else if(gradeInfo instanceof JSONObject) {
            System.out.println("C");
        } else if(gradeInfo instanceof JSONArray) {
            System.out.println("D");
        } else if(gradeInfo instanceof String) {
            System.out.println("E");
        } else if(gradeInfo.equals(null)){
            System.out.println("F");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
//        infoService.setStudentGrade(gradeInfo.getGrades(), user);
        return "redirect:/infoStudent";
    }
//
//    @RequestMapping(value = "/infoStudent/grade", method = {RequestMethod.GET, RequestMethod.POST})
//    public String postInfo(String gradeInfo){
//        System.out.println(gradeInfo);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
////        infoService.setStudentGrade(gradeInfo.getGrades(), user);
//        return "redirect:/infoStudent";
//    }
//
//    @RequestMapping(value = "/infoStudent/grade", method = {RequestMethod.GET, RequestMethod.POST})
//    public String postInfo(JSONArray gradeInfo) {
//        System.out.println(gradeInfo);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
////        infoService.setStudentGrade(gradeInfo.getGrades(), user);
//        return "redirect:/infoStudent";
//    }

//    @RequestMapping(value = "/infoStudent/grade", method = {RequestMethod.GET, RequestMethod.POST})
//    public String postInfo(JSONObject gradeInfo) {
//        System.out.println(gradeInfo);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
////        infoService.setStudentGrade(gradeInfo.getGrades(), user);
//        return "redirect:/infoStudent";
//    }
//
//    @RequestMapping(value = "/infoStudent/grade", method = {RequestMethod.GET, RequestMethod.POST})
//    public String postInfo(ArrayList<GradeDto> gradeInfo){
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
