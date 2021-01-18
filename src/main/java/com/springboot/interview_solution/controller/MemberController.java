package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Member;
import com.springboot.interview_solution.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("/signin")
    public String signin(){ return "signin"; }

    @GetMapping("/signout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/signin";
    }
}
