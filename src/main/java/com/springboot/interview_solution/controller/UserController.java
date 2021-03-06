package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.MyUserDto;
import com.springboot.interview_solution.dto.UserDto;
import com.springboot.interview_solution.service.ReportService;
import com.springboot.interview_solution.service.SchoolInfoService;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@AllArgsConstructor
@Controller
@SessionAttributes("user")
public class UserController {

    private final UserService userService;
    private final SchoolInfoService schoolInfoService;
    private final ReportService reportService;

    // main
    @GetMapping(value = "/")
    public String main(){
        return "index";
    }

    // student home
    @GetMapping(value = "/student")
    public String getStudentHome() {
        return "stuhome";
    }

    // teacher home
    @GetMapping(value = "/teacher")
    public ModelAndView getTeacherHome() throws Exception {
        ModelAndView mv = new ModelAndView("teahome");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Report> reports = reportService.getStudentReport(user);
        mv.addObject("reports", reports);
        return mv;
    }

    // signup
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

    public List<String> searchSchoolInfo(@RequestParam("term") String school){
        List<String> schoolInfo;
        //학교 정보 받아와서 SchoolInfo로 넣기
        schoolInfo = schoolInfoService.findAllByName(school);

        return schoolInfo;
    }

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

    @RequestMapping(value = "/resultSignin", method = RequestMethod.GET)
    public String resultStudentSignin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if(userService.loadIsTeacherByUserID(user.getUserID()))
            return "redirect:/teacher";
        else return "redirect:/student";
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

    // mypage
    @RequestMapping(value = "mypage", method = RequestMethod.GET)
    public String getMyPage(Authentication authentication, Model model) throws Exception {
        User user = (User) authentication.getPrincipal();
        String userID = user.getUserID();
        model.addAttribute("userOne", userService.loadUser(userID));
        return "mypage";
    }

    @RequestMapping(value = "mypage", method = RequestMethod.POST)
    public String postMyPage(Authentication authentication, MyUserDto param) throws Exception {
        User user = (User) authentication.getPrincipal();
        String userID = user.getUserID();
        param.setUserID(userID);
        User persistUser = userService.loadUserByUsername(userID);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String PW  = new BCryptPasswordEncoder().encode(param.getPassword());
        if(!encoder.matches(PW, persistUser.getPassword())) {
            if (param.getNewPassword().equals(param.getPasswordChk()))
                userService.modifyUser(param);
        }
        return "redirect:/mypage";
    }

    @RequestMapping(value = "secede", method = RequestMethod.DELETE)
    public String deleteUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String userID = user.getUserID();
        userService.deleteUser(userID);
        return "redirect:/";
    }
}