package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportServiceTest {

    @Autowired
    QuestionService questionService;
    @Autowired
    ReportService reportService;

    @Test
    void getQuestion(){
        List<Question> allQuestionList =questionService.getAllQuestion();
        System.out.println(allQuestionList);
    }
}