package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.RecordData;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import javax.swing.filechooser.FileSystemView;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InterviewServiceTest {

    @Autowired
    InterviewService interviewService;


    @Test
    void recordVideo() {
        RecordData recordData = new RecordData();
        interviewService.recordingVideo("potato","1","1",recordData);
        interviewService.stopVideo("potato","1","1",recordData);
    }

    @Test
    void makeFinalVideo(){
        interviewService.makeFinalVideo("potato","1","1");
    }
}