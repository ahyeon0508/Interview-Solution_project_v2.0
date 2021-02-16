package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.UserDto;
import com.springboot.interview_solution.service.SchoolInfoService;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Controller
@SessionAttributes("user")
public class UserController {

    private final UserService userService;
    private final SchoolInfoService schoolInfoService;

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
    @RequestMapping(value = "/searchSchool",method = RequestMethod.GET)
    @ResponseBody
    public List<String> searchSchoolInfo(@RequestParam("term") String school){
        System.out.println(school);
        List<String> schoolInfo;
        //학교 정보 받아와서 SchoolInfo로 넣기
        schoolInfo = schoolInfoService.findAllByName(school);

        return schoolInfo;
    }

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
        return "findPW";
    }

    @RequestMapping(value = "findpw", method = RequestMethod.POST)
    public String postFindPW(Model model, @RequestParam("username") String username,
                             @RequestParam("userID") String userID, @RequestParam("phone") String phone) {
        if (userService.loadUserByUsername(userID) != null) {
            User user = (User) userService.loadUserByUsername(userID);
            if(user.getUsername().equals(username) && user.getPhone().equals(phone)) {
                model.addAttribute("user", user.getUserID());
                return "redirect:/resultpw/"+userID;
            }
            else return "redirect:/findpw";
        }
        else
            return "redirect:/findpw";
    }

    @RequestMapping(value = "resultpw/{userid}", method = RequestMethod.GET)
    public String getChangePW(@PathVariable String userid) {
        return "resultPW";
    }

    @RequestMapping(value = "resultpw/{userid}", method = RequestMethod.POST)
    public String postChangePW(@PathVariable String userid, @RequestParam("password") String password,
                             @RequestParam("passwordChk") String passwordChk) throws Exception {
        if (password.equals(passwordChk)){
            userService.modifyPW(userid, password);
            return "redirect:/signin";
        } else {
            return "redirect:/resultpw/"+userid;
        }
    }
}
