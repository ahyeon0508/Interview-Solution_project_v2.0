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
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@AllArgsConstructor
@Controller
public class ReportController {

    private final ReportService reportService;

    @RequestMapping(value = "wait/{id}", method = RequestMethod.GET)
    public ModelAndView getWait(@PathVariable Long id) throws Exception {
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

    @RequestMapping(value = "wait/share/{reportID}", method = RequestMethod.GET)
    public String waitSharePost(@PathVariable("reportID") Long id) throws Exception {
        reportService.modifyShare(id);
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

    @RequestMapping(value = "myVideo", method = RequestMethod.GET)
    public ModelAndView getMyReports() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ModelAndView mv = new ModelAndView("myVideo");
        List<Report> reports = reportService.getReports(user);
        mv.addObject("reports", reports);
        return mv;
    }

    @RequestMapping(value = "myVideo/{id}", method = RequestMethod.GET)
    public ModelAndView getMyReport(@PathVariable Long id) throws Exception {
        ModelAndView mv = new ModelAndView("report");
        Report report = reportService.getReport(id);
        mv.addObject("report", report);
        return mv;
    }

    @RequestMapping(value = "wait/eyeTracking/{id}", method = RequestMethod.POST)
    public String getEyeTrackingPost(@PathVariable("id") Long id, @RequestParam String position1, @RequestParam String position2, @RequestParam String position3){
        reportService.setEyeTracking(id,position1,1);
        reportService.setEyeTracking(id,position2,2);
        reportService.setEyeTracking(id,position3,3);
        return "redirect:/wait/" + id;
    }

    @RequestMapping(value = "myVideo/delete/{id}", method = RequestMethod.GET)
    public String deleteMyReport(@PathVariable Long id) throws Exception {
        reportService.deleteReport(id);
        return "redirect:/myVideo";
    }

    @RequestMapping(value = "myVideo/share/{reportID}", method = RequestMethod.GET)
    public String myReportSharePost(@PathVariable("reportID") Long id) throws Exception {
        System.out.println("A");
        reportService.modifyShare(id);
        return "redirect:/myVideo";
    }

    @GetMapping("/video/{fileName}")
    public StreamingResponseBody stream(HttpServletRequest req, @PathVariable("fileName") String fileName) throws Exception {
        File file = new File(System.getProperty("user.dir")+"/src/main/resources/video/" + fileName);
        final InputStream is = new FileInputStream(file);
        return os -> {
            readAndWrite(is, os);
        };
    }
    public void readAndWrite(final InputStream is, OutputStream os) throws IOException {
        byte[] data = new byte[2048];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
    }
}
