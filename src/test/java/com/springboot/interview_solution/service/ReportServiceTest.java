package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.repository.ReportRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReportServiceTest {

    @Autowired
    ReportService reportService;
    @Autowired
    ReportRepository reportRepository;

    @Test
    public void setReport() {
        Report report = new Report("제목", "질문1", "/Users/yejin/Music/Music/Media.localized/Music/Unknown Artist/Unknown Album/부곡동.wav");
//        reportRepository.save(report);
//        reportService.makeReport(report.getId());
    }
}
