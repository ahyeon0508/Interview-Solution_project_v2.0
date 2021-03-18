package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.FeedbackDto;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.repository.ReportRepository;
import com.springboot.interview_solution.service.ReportService;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@AllArgsConstructor
@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;
    private UserService userService;
    private ReportRepository reportRepository;

    @RequestMapping(value = "wait", method=RequestMethod.GET)
    public String wait(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        ReportDto reportDto = new ReportDto();
        reportDto.setTitle("제목");
        reportDto.setQuestion1("질문1");
        //  reportDto.setAudio1("/Users/yejin/Music/Music/Media.localized/Music/Unknown Artist/Unknown Album/부곡동-12.wav");
        reportDto.setAudio1("/Users/ahyeon/Desktop/3-2/캡스톤/구현/카카오 STT/audio.wav");
        reportDto.setStudent(user);
        reportDto.setTeacher(userService.loadUserByUsername("teacher")); // UserServiceTest가서 signup 한번 돌리고 실행해야함
        Report report = reportRepository.save(Report.builder()
                .title(reportDto.getTitle())
                .question1(reportDto.getQuestion1())
                .student(reportDto.getStudent())
                .teacher(reportDto.getTeacher())
                .audio1(reportDto.getAudio1())
                .build());

        return "redirect:/wait/" + report.getId();
    }

    @RequestMapping(value = "wait/{id}", method = RequestMethod.GET)
    public ModelAndView getWait(Authentication authentication, @PathVariable("id") Long id) throws Exception {
        ModelAndView mv = new ModelAndView("wait");
        reportService.makeReport(id);
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

    @RequestMapping(value = "/wait/share/{reportID}", method = RequestMethod.POST)
    public String sharePost(@PathVariable int reportID) throws Exception {
        System.out.println(reportID);
        Long id = Long.valueOf(reportID);
        reportService.modifyShare(id);
        return "redirect:/wait/" + id;
    }
}
