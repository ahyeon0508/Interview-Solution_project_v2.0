package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.SchoolInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SchoolInfoServiceTest {

    @Autowired
    SchoolInfoService schoolInfoService;

    @Test
    void collectInfo() {
        schoolInfoService.collectInfo();
    }

    @Test
    void findAllByName(){
        List<String> names = schoolInfoService.findAllByName("대진");
        for(int i =0; i<names.size();i++){
            System.out.println(names.get(i));
        }

    }
}