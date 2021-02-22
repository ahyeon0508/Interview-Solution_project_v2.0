package com.springboot.interview_solution.service;

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
    void setReport() {
        ReportDto reportDto = new ReportDto();
        reportDto.setTitle("제목");
        reportDto.setQuestion1("질문1");
        reportDto.setQuestion2("질문2");
        reportDto.setQuestion3("질문3");
        reportDto.setVideo1("video URL1");
        reportDto.setVideo2("video URL2");
    }
}
