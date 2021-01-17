package com.springboot.interview_solution.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @GetMapping("/student/signup")
    public String studnetSignup(){
        return "signup";
    }

    @GetMapping("/teacher/signup")
    public String teacherSignup(){
        return "signup";
    }
}
