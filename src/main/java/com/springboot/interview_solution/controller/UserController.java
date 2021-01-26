package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.UserDto;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.junit.runner.Request;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Controller
@SessionAttributes("user")
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
        return "redirect:/signin";
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

    // signin
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String getStudentSignin() {
        return "signin";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String postStudentSignin(UserDto student) {
        if (userService.signin(student) == true) {
            return "redirect:/main";
        } else {
            return "redirect:/signin";
        }
    }

    @GetMapping(value = "/main")
    public String main(){
        return "main";
    }

    @GetMapping(value = "/signout")
    public String signout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/signin";
    }

    @RequestMapping(value = "/findid", method = RequestMethod.GET)
    public String getFindID() {
        return "findID";
    }

    @RequestMapping(value = "/findid", method = RequestMethod.POST)
    public String postFindID(Model model, @RequestParam("username") String username,
                             @RequestParam("phone") String phone) {
        if (userService.loadUserByUserName(username) != null) {
            User user = userService.loadUserByUserName(username);
            if(user.getPhone().equals(phone)) {
                model.addAttribute("user", user.getUserID());
                return "resultID";
            }
            else return "redirect:/findID";
        }
        else
            return "redirect:/findID";
    }

    @RequestMapping(value = "findpw", method = RequestMethod.GET)
    public String getFindPW() {
        return "/findPW";
    }

    @RequestMapping(value = "findpw", method = RequestMethod.POST)
    public String postFindPW(Model model, HttpServletResponse response, @RequestParam("userID") String userID,
                             @RequestParam("username") String username, @RequestParam("phone") String phone) {
        if (userService.loadUserByUsername(userID) != null && userService.loadUserByUserName(username) != null) {
            User user = userService.loadUserByUserName(username);
            if(user.getPhone().equals(phone)) {
                model.addAttribute("user", user.getUserID());
                return "resultPW";
            }
            else return "redirect:/findPW";
        }
        else
            return "redirect:/findPW";
    }
}
