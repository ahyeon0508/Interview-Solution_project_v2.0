package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Question;
import com.springboot.interview_solution.domain.RecordData;
import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.repository.ReportRepository;
import com.springboot.interview_solution.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

@SpringBootTest
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
        User student = userRepository.findUserByUserID("potatoe");

        List<Question> questionList = new ArrayList<Question>();
        List<Question> allQuestionList = questionService.getAllQuestion();
        for(int i=0;i<3;i++){
            questionList.add(allQuestionList.get(i));
        }
        Long report = reportService.setReport(student, questionList);
        Report reportObject = reportRepository.findById(report);
        //record Audio
//        File audio= new File(System.getProperty("user.dir")+"/src/main/resources/video/potato_"+"1"+"_1.wav");
//        RecordData recordData = new RecordData();
//        Thread recordThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    System.out.println("Start recording...");
//                    recordData.start();
//                } catch (LineUnavailableException ex) {
//                    ex.printStackTrace();
//                    exit(-1);
//                }
//            }
//        });
//        recordThread.start();
//        try{
//            Thread.sleep(30000);
//        } catch (InterruptedException ex) {
//            System.out.println("MY STOPPED");
//            ex.printStackTrace();
//        }
//
//        recordData.stop();
//        recordData.save(audio);
//
        reportObject.setAudio1("/Users/yejin/Music/Music/Media.localized/Music/Unknown Artist/Unknown Album/부곡동.wav");
        reportRepository.save(reportObject);
        reportService.makeReport(report);
    }

    @Test
    void getReport() throws Exception {
        System.out.println(reportService.getReport(33L).getRepetition1());
    }
}
