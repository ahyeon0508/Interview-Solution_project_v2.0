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

    /* 리포트 결과 생성 */
    @RequestMapping(value = "wait/{id}", method = RequestMethod.GET)
    public ModelAndView getWait(@PathVariable Long id) throws Exception {
        ModelAndView mv = new ModelAndView("wait");
        reportService.makeReport(id);
        Report report = reportService.getReport(id);
        mv.addObject("report", report);
        return mv;
    }

    /* 리포트 제목 변경 */
    @RequestMapping(value = "wait/{id}", method = RequestMethod.POST)
    public String postWait(@RequestParam("title") String title, @PathVariable("id") Long id) throws Exception {
        reportService.modifyTitle(id, title);
        return "redirect:/wait/" + id;
    }

    /* 리포트 공유 변경 */
    @RequestMapping(value = "wait/share/{reportID}", method = RequestMethod.GET)
    public String waitSharePost(@PathVariable("reportID") Long id) throws Exception {
        reportService.modifyShare(id);
        return "redirect:/wait/" + id;
    }

    /* 반 학생들 영상 가져오기 */
    @GetMapping(value = "classVideo/{id}")
    public ModelAndView getClassStudentVideo(@PathVariable Long id) throws Exception {
        ModelAndView mv = new ModelAndView("studentVideo");
        Report report = reportService.getReport(id);
        mv.addObject("report", report);
        return mv;
    }

    /* 영상 피드백 보내기 */
    @PostMapping(value = "classVideo/{id}")
    public String postClassStudentVideo(@PathVariable("id") Long id, FeedbackDto feedbackDto) throws Exception {
        reportService.modifyFeedback(id, feedbackDto);
        return "redirect:/classVideo/" + id;
    }

    /* 영상 피드백 삭제 */
    @GetMapping(value = "classVideo/{id}/delete1")
    public String deleteClassStudentFeedback1(@PathVariable("id") Long id) throws Exception {
        Report report = reportService.getReport(id);
        reportService.deleteFeedback(report, 1);
        return "redirect:/classVideo/" + id;
    }

    /* 영상 피드백2 삭제 */
    @PostMapping(value = "classVideo/{id}/delete2")
    public String deleteClassStudentFeedback2(@PathVariable("id") Long id) throws Exception {
        Report report = reportService.getReport(id);
        reportService.deleteFeedback(report, 2);
        return "redirect:/classVideo/" + id;
    }

    /* 영상 피드백3 삭제 */
    @PostMapping(value = "classVideo/{id}/delete3")
    public String deleteClassStudentFeedback3(@PathVariable("id") Long id) throws Exception {
        Report report = reportService.getReport(id);
        reportService.deleteFeedback(report, 3);
        return "redirect:/classVideo/" + id;
    }

    /* 내 리포트 가져오기 */
    @RequestMapping(value = "myVideo", method = RequestMethod.GET)
    public ModelAndView getMyReports() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ModelAndView mv = new ModelAndView("myVideo");
        List<Report> reports = reportService.getReports(user);
        mv.addObject("reports", reports);
        return mv;
    }

    /* 내 리포트 상세 보기 */
    @RequestMapping(value = "myVideo/{id}", method = RequestMethod.GET)
    public ModelAndView getMyReport(@PathVariable Long id) throws Exception {
        ModelAndView mv = new ModelAndView("report");
        Report report = reportService.getReport(id);
        mv.addObject("report", report);
        return mv;
    }

    /* 내 리포트 삭제 */
    @RequestMapping(value = "myVideo/delete/{id}", method = RequestMethod.GET)
    public String deleteMyReport(@PathVariable Long id) throws Exception {
        reportService.deleteReport(id);
        return "redirect:/myVideo";
    }

    /* 내 리포트 공유 변경 */
    @RequestMapping(value = "myVideo/share/{reportID}", method = RequestMethod.GET)
    public String myReportSharePost(@PathVariable("reportID") Long id) throws Exception {
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
