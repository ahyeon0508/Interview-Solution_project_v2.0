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
import java.util.List;

@AllArgsConstructor
@Controller
@SessionAttributes("user")
public class UserController {

    private final UserService userService;
    private final SchoolInfoService schoolInfoService;
    private final ReportService reportService;

    /* 메인 페이지 */
    @GetMapping(value = "/")
    public String main(){
        return "index";
    }

    /* 학생 메인 페이지 */
    @GetMapping(value = "/student")
    public String getStudentHome() {
        return "stuhome";
    }


    /* 교사 메인 페이지 */
    @GetMapping(value = "/teacher")
    public ModelAndView getTeacherHome() throws Exception {
        ModelAndView mv = new ModelAndView("teahome");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Report> reports = reportService.getStudentReport(user);
        mv.addObject("reports", reports);
        return mv;
    }

    /* 회원가입 페이지 연동 */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getStudentSignup(){
        return "signup";
    }

    /* 회원가입 */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String postStudentSignup(UserDto student){
        userService.signup(student);
        return "redirect:/signin";
    }

    /* 학교 정보 검색 */
    @RequestMapping(value = "/searchSchool",method = RequestMethod.GET)
    @ResponseBody
    public List<String> searchSchoolInfo(@RequestParam("term") String school){
        List<String> schoolInfo;
        //학교 정보 받아와서 SchoolInfo로 넣기
        schoolInfo = schoolInfoService.findAllByName(school);

        return schoolInfo;
    }

    /* 아이디 유효성 확인 */
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

    /* 로그인 페이지 연동 */
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String getStudentSignin() {
        return "signin";
    }

    /* 로그인 결과 (회원 정보에 따라 학생 메인 페이지 또는 교사 메인 페이지로 이동) */
    @RequestMapping(value = "/resultSignin", method = RequestMethod.GET)
    public String resultStudentSignin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if(userService.loadIsTeacherByUserID(user.getUserID()))
            return "redirect:/teacher";
        else return "redirect:/student";
    }

    /* 로그아웃 */
    @GetMapping(value = "/signout")
    public String signout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/signin";
    }

    /* 아이디 찾기 페이지 연동 */
    @RequestMapping(value = "/findid", method = RequestMethod.GET)
    public String getFindID() {
        return "findID";
    }

    /* 아이디 찾기 */
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

    /* 비밀번호 찾기 페이지 연동 */
    @RequestMapping(value = "findpw", method = RequestMethod.GET)
    public String getFindPW() {
        return "findPW";
    }

    /* 비밀번호 찾기 */
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

    /* 비밀번호 변경 페이지 연동 */
    @RequestMapping(value = "resultpw/{userid}", method = RequestMethod.GET)
    public String getChangePW(@PathVariable String userid) {
        return "resultPW";
    }

    /* 비밀번호 변경 */
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

    /* 마이 페이지 */
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