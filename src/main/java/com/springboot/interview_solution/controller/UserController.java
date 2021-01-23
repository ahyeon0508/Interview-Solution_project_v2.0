package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.dto.UserDto;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    //student signup
    @RequestMapping(value = "/student/signup", method=RequestMethod.GET)
    public String getStudentSignup(){
        return "signup";
    }

    @RequestMapping(value = "/student/signup",method=RequestMethod.POST)
    public String postStudentSignup(UserDto student){
        userService.signup(student);
        return "signup";
    }

    //teacher signup
    @GetMapping("/teacher/signup")
    public String getTeacherSignup(){
        return "signup";
    }

    @PostMapping("/teacher/signup")
    public String postTeacherSignup(UserDto teacher){
        userService.signup(teacher);
        return "signup";
    }

    //signup ->  UserId validate duplicate
    @RequestMapping(params = "userIdValidation", method = RequestMethod.POST)
    public String validUserId(String userID){
        userService.validateDuplicateUserId(userID);
        return "signup";
    }
}
