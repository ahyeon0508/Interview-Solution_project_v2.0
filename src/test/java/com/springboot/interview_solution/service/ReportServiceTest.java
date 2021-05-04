package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Question;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.repository.ReportRepository;
import com.springboot.interview_solution.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {
    @Autowired
    ReportService reportService;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionService questionService;

    @Test
    void setReport() throws Exception {
        User student = userRepository.findUserByUserID("potato");

        List<Question> questionList = new ArrayList<Question>();
        List<Question> allQuestionList = questionService.getAllQuestion();
        for(int i=0;i<3;i++){
            questionList.add(allQuestionList.get(i));
        }
        Long report = reportService.setReport(student, questionList);
        String uploadPath = System.getProperty("user.dir")+"/src/main/resources/video/potato_"+report.toString()+"_1";
        reportService.getReport(report).setAudio1(uploadPath);
    }
}