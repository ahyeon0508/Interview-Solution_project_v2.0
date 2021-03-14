package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.dto.FeedbackDto;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.repository.ReportRepository;
import com.springboot.interview_solution.service.ReportService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@AllArgsConstructor
@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/wait", method = RequestMethod.GET)
    public ModelAndView getWait() {
        ModelAndView mv = new ModelAndView("wait");
//        Report report = reportService.makeReport(id); // 앞 단계에서 받아오기
//        mv.addObject(report);
        return mv;
    }

    @RequestMapping(value = "/wait", method = RequestMethod.POST)
    public String postWait(ReportDto reportDto) {
        return "redirect:/wait";
    }

    @GetMapping(value = "/classVideo/{id}")
    public ModelAndView getClassStudentVideo(@PathVariable Long id) throws Exception {
        ModelAndView mv = new ModelAndView("studentVideo");
        Report report = reportService.getReport(id);
        mv.addObject("report", report);
        return mv;
    }

    @PostMapping(value = "classVideo/{id}")
    public String postClassStudentVideo(@PathVariable Long id, FeedbackDto feedbackDto) throws Exception {
        Report report = reportService.getReport(id);
        if(!feedbackDto.getFeedback1().isEmpty() || feedbackDto.getFeedback1() == null) {
            report.setComment1(feedbackDto.getFeedback1());
            report.setComment1WritedAt(LocalDateTime.now());
            return "redirect:/classVideo/" + id;
        } else if(!feedbackDto.getFeedback2().isEmpty() || feedbackDto.getFeedback2() == null) {
            report.setComment2(feedbackDto.getFeedback2());
            report.setComment2WritedAt(LocalDateTime.now());
            return "redirect:/classVideo/" + id;
        } else if(!feedbackDto.getFeedback3().isEmpty() || feedbackDto.getFeedback3() == null) {
            report.setComment3(feedbackDto.getFeedback3());
            report.setComment3WritedAt(LocalDateTime.now());
            return "redirect:/classVideo/" + id;
        }
        return "studentVideo";
    }

    @PostMapping(value = "classVideo/{id}/delete1")
    public String deleteClassStudentFeedback1(@PathVariable Long id) throws Exception {
        Report report = reportService.getReport(id);
        report.setComment1(null);
        return "redirect:/classVideo/" + id;
    }

    @PostMapping(value = "classVideo/{id}/delete2")
    public String deleteClassStudentFeedback2(@PathVariable Long id) throws Exception {
        Report report = reportService.getReport(id);
        report.setComment2(null);
        return "redirect:/classVideo/" + id;
    }

    @PostMapping(value = "classVideo/{id}/delete3")
    public String deleteClassStudentFeedback3(@PathVariable Long id) throws Exception {
        Report report = reportService.getReport(id);
        report.setComment3(null);
        return "redirect:/classVideo/" + id;
    }

    @GetMapping(value = "questionSend/{id}")
    public ModelAndView getQuestionSend(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("questionSend");
        return mv;
    }

    @PostMapping(value = "questionSend/{id}")
    public String PostQuestionSend(@PathVariable Long id) {
        return "redirect:/classVideo/" + id;
    }
}
