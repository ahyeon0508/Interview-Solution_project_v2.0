package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.dto.UserDto;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    //student signup
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getStudentSignup(){
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String postStudentSignup(UserDto student){
        userService.signup(student);
        return "redirect:/";
    }
    //school information
    /*@RequestMapping(value = "/searchSchool",method = RequestMethod.POST)
    @ResponseBody
    public String searchSchoolInfo(@RequestParam("school") String school, HttpServletRequest response){
        String schoolInfo;
        if(school != null){
            //학교 정보 받아와서 SchoolInfo로 넣기
        }
    }*/

    //UserId validate duplicate
    @RequestMapping(value = "/userIdCheck", method = RequestMethod.GET)
    public Map validUserId(@RequestParam("userID") String userID){
        Map responseMsg = new HashMap<String,Object>();
        Boolean isNotValid = userService.validateDuplicateUserId(userID);
        responseMsg.put("result","success");
        if(isNotValid){     //UserId is not valid
            responseMsg.put("data","notExist");
        }else{
            responseMsg.put("data","exist");
        }
        return responseMsg;
    }
}
