package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.RecordData;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InterviewServiceTest {

    @Autowired
    InterviewService interviewService;
    @Value("${file.upload.dir}")
    String uploadDir;


    @Test
    void recordVideo() {
        String dir = "/Users/hyewonjin/Interview-Solution_project_v2.0/src/main/resources/video/";
        //String dir = uploadDir+"audio.wav";
        System.out.println(dir);
        RecordData recordData = new RecordData();
        interviewService.recordingVideo(dir,recordData);
        interviewService.stopVideo(dir,recordData);
    }

    @Test
    void makeFinalVideo(){
        interviewService.makeFinalVideo("0","1");
    }
}