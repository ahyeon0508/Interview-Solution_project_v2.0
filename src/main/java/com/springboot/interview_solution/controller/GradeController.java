package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.domain.GradeList;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.GradeDto;
import com.springboot.interview_solution.service.GradeService;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@AllArgsConstructor
@Controller
public class GradeController {

    private final GradeService gradeService;
    private final UserService userService;
    private static ArrayList<Grade> gradeList = new ArrayList<Grade>();

    @RequestMapping(value = "/infoStudent", method = RequestMethod.GET)
    public ModelAndView getInfo() {
        GradeList gradeInfo = new GradeList();
        gradeInfo.setGrades(gradeList);
        return new ModelAndView("upload", "gradeInfo", gradeInfo);
    }

    @RequestMapping(value = "/infoStudent", method = RequestMethod.POST)
    public String postInfo(ArrayList<Grade> gradeInfo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        gradeService.setStudentGrade(gradeInfo, user);
        return "redirect:/infoStudent";
    }
}
