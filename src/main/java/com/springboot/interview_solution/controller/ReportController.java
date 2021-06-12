package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@AllArgsConstructor
@Controller
public class ReportController {

    private final ReportService reportService;

    @RequestMapping(value = "/wait", method = RequestMethod.GET)
    public ModelAndView getWait() {
        ModelAndView mv = new ModelAndView("wait");
        return mv;
    }

    @RequestMapping(value = "/wait", method = RequestMethod.POST)
    public String postWait(ReportDto reportDto) {
        return "redirect:/wait";
    }

    @RequestMapping(value = "/myVideo", method = RequestMethod.GET)
    public ModelAndView getMyReport() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ModelAndView mv = new ModelAndView("myVideo");
        List<Report> reports = reportService.getReports(user);
        mv.addObject("reports", reports);
        return mv;
    }

    @RequestMapping(value = "/myVideo/{id}", method = RequestMethod.GET)
    public ModelAndView getMyReport(@PathVariable Long id) throws Exception {
        ModelAndView mv = new ModelAndView("report");
        Report report = reportService.getReport(id);
        mv.addObject("report", report);
        return mv;
    }
}
