package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.SchoolInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SchoolInfoServiceTest {

    @Autowired
    SchoolInfoService schoolInfoService;

    @Test
    void collectInfo() {
        schoolInfoService.collectInfo();
    }
}