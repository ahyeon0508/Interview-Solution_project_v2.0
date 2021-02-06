package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.dto.UserDto;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
    @ResponseBody
    @RequestMapping(value = "/userIdCheck", method = RequestMethod.POST)
    public HashMap<String,String> validUserId(@RequestBody String userID){
        HashMap responseMsg = new HashMap<String,String>();
        Boolean isNotValid = userService.validateDuplicateUserId(userID.replace("userID=",""));
        if(isNotValid){     //UserId is not valid
            responseMsg.put("data","exist");
        }else{
            responseMsg.put("data","notExist");
        }
        return responseMsg;
    }

}
