package com.springboot.interview_solution.controller;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.repository.ReportRepository;
import com.springboot.interview_solution.service.ReportService;
import com.springboot.interview_solution.service.UserService;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private UserService userService;
    private ReportRepository reportRepository;

    @RequestMapping(value = "wait/{id}", method = RequestMethod.GET)
    public ModelAndView getWait(Authentication authentication, @PathVariable("id") Long id) throws Exception {
        User user = (User) authentication.getPrincipal();
        ModelAndView mv = new ModelAndView("wait");
        /*
        ReportDto reportDto = new ReportDto();
        reportDto.setTitle("제목");
        reportDto.setQuestion1("질문1");
        reportDto.setQuestion2("질문2");
        reportDto.setQuestion3("질문3");
        reportDto.setAudio1("/Users/yejin/Music/Music/Media.localized/Music/Unknown Artist/Unknown Album/부곡동-12.wav");
//        System.out.println(userRepository.findByUserID("potatoe").orElseThrow(() -> new UsernameNotFoundException("안녕하세")));
        reportDto.setStudent(user);
        reportDto.setTeacher(userService.loadUserByUserName("선생님"));
        Report reportBefore = reportRepository.save(Report.builder()
                .title(reportDto.getTitle())
                .question1(reportDto.getQuestion1())
                .student(reportDto.getStudent())
                .teacher(reportDto.getTeacher())
                .audio1(reportDto.getAudio1())
                .build());
         */
        reportService.makeReport(id);
        Report report = reportService.getReport(id);
        System.out.println(report.getTitle());
        mv.addObject("report", report);
        return mv;
    }

    @RequestMapping(value = "wait/{id}", method = RequestMethod.POST)
    public String postWait(@RequestParam("title") String title, @PathVariable("id") Long id, Model model) throws Exception {
        Report report = reportService.getReport(id);
        reportService.modifyTitle(report, title);
        return "redirect:/wait/" + id;
    }
}
