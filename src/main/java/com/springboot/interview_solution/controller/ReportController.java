package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.repository.ReportRepository;
import com.springboot.interview_solution.service.InterviewService;
import com.springboot.interview_solution.service.ReportService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/wait", method = RequestMethod.GET)
    public ModelAndView getWait() {
        ModelAndView mv = new ModelAndView("wait");
        return mv;
    }

    @RequestMapping(value = "/wait", method = RequestMethod.POST)
    public String postWait(ReportDto reportDto) {
        return "redirect:/wait";
    }



}
