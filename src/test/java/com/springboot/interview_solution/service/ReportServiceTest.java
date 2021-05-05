package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Question;
import com.springboot.interview_solution.domain.RecordData;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.repository.ReportRepository;
import com.springboot.interview_solution.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ReportServiceTest {

    @Autowired
    ReportService reportService;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionService questionService;


    @Test
    public void setReport() throws Exception {
        User student = userRepository.findUserByUserID("potato");

        List<Question> questionList = new ArrayList<Question>();
        List<Question> allQuestionList = questionService.getAllQuestion();
        for(int i=0;i<3;i++){
            questionList.add(allQuestionList.get(i));
        }
        Long report = reportService.setReport(student, questionList);

        //record Audio
        File audio= new File(System.getProperty("user.dir")+"/src/main/resources/video/potato_"+report.toString()+"_1");
        RecordData recordData = new RecordData();
        recordData.start();
        recordData.stop();
        recordData.save(audio);


        reportService.getReport(report).setAudio1(audio.getParent());
    }
}
