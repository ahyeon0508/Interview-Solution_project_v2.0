package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Member;
import com.springboot.interview_solution.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //student signup
    @RequestMapping(value = "/student/signup", method=RequestMethod.GET)
    public String getStudentSignup(){
        return "signup";
    }

    @RequestMapping(value = "/student/signup",method=RequestMethod.POST)
    public String postStudentSignup(Member student){
        student.setIsTeacher(false);
        memberService.signup(student);
        return "signup";
    }

    //teacher signup
    @GetMapping("/teacher/signup")
    public String getTeacherSignup(){
        return "signup";
    }

    @PostMapping("/teacher/signup")
    public String postTeacherSignup(Member teacher){
        teacher.setIsTeacher(true);
        memberService.signup(teacher);
        return "signup";
    }

    //signup ->  UserId validate duplicate
    @RequestMapping(params = "userIdValidation", method = RequestMethod.POST)
    public String validUserId(String userID){
        memberService.validateDuplicateUserId(userID);
        return "signup";
    }
}
