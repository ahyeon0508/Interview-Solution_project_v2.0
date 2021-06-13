package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.FeedbackDto;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@AllArgsConstructor
@Controller
public class ReportController {

    private final ReportService reportService;

    @RequestMapping(value = "/wait/{id}", method = RequestMethod.GET)
    public ModelAndView getWait(@PathVariable Long id) throws Exception {
        ModelAndView mv = new ModelAndView("wait");
        Report report = reportService.getReport(id);
        mv.addObject("report", report);
        return mv;
    }

    @RequestMapping(value = "wait/{id}", method = RequestMethod.POST)
    public String postWait(@RequestParam("title") String title, @PathVariable("id") Long id) throws Exception {
        reportService.modifyTitle(id, title);
        return "redirect:/wait/" + id;
    }

    @GetMapping(value = "classVideo/{id}")
    public ModelAndView getClassStudentVideo(@PathVariable Long id) throws Exception {
        ModelAndView mv = new ModelAndView("studentVideo");
        Report report = reportService.getReport(id);
        mv.addObject("report", report);
        return mv;
    }

    @PostMapping(value = "classVideo/{id}")
    public String postClassStudentVideo(@PathVariable("id") Long id, FeedbackDto feedbackDto) throws Exception {
        reportService.modifyFeedback(id, feedbackDto);
        return "redirect:/classVideo/" + id;
    }

    @GetMapping(value = "classVideo/{id}/delete1")
    public String deleteClassStudentFeedback1(@PathVariable("id") Long id) throws Exception {
        Report report = reportService.getReport(id);
        reportService.deleteFeedback(report, 1);
        return "redirect:/classVideo/" + id;
    }

    @PostMapping(value = "classVideo/{id}/delete2")
    public String deleteClassStudentFeedback2(@PathVariable("id") Long id) throws Exception {
        Report report = reportService.getReport(id);
        reportService.deleteFeedback(report, 2);
        return "redirect:/classVideo/" + id;
    }

    @PostMapping(value = "classVideo/{id}/delete3")
    public String deleteClassStudentFeedback3(@PathVariable("id") Long id) throws Exception {
        Report report = reportService.getReport(id);
        reportService.deleteFeedback(report, 3);
        return "redirect:/classVideo/" + id;
    }

    @RequestMapping(value = "/wait/share/{reportID}", method = RequestMethod.GET)
    public String sharePost(@PathVariable("reportID") Long id) throws Exception {
        reportService.modifyShare(id);
        return "redirect:/wait/" + id;
    }

    @RequestMapping(value = "/myVideo", method = RequestMethod.GET)
    public ModelAndView getMyReports() throws Exception {
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
