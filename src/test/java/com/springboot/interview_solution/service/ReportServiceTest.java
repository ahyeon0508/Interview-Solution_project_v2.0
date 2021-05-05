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

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

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
        File audio= new File(System.getProperty("user.dir")+"/src/main/resources/video/potato_"+"1"+"_1.wav");
        RecordData recordData = new RecordData();
        Thread recordThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Start recording...");
                    recordData.start();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                    exit(-1);
                }
            }
        });
        recordThread.start();
        try{
            Thread.sleep(30000);
        } catch (InterruptedException ex) {
            System.out.println("MY STOPPED");
            ex.printStackTrace();
        }

        recordData.stop();
        recordData.save(audio);


        reportService.getReport(report).setAudio1(audio.getParent());
    }
}
