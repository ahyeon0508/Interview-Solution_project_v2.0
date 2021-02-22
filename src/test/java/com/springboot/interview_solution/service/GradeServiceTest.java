package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.repository.InfoDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class GradeServiceTest {

    @Autowired
    InfoService infoService;
    @Autowired
    InfoDao infoDao;
    @Autowired
    UserService userService;

    @Test
    void setStudentGrade() {
        System.out.println(1);
        Grade grade = new Grade();
        grade.setGrade(2);
        grade.setSemester(1);
        grade.setUser(userService.loadUserByUserName("김감자"));
        grade.setSubject("국어");
        grade.setCourse("화법과 문법");
        grade.setUnitNumber(3);
        grade.setRanking(2);
        grade.setRawRanking(97);
        grade.setSubjectMean(50);
        grade.setAverage(40);
        grade.setAchievement("A");
        grade.setNumberOfStudents(30);

        ArrayList<Grade> gradeList = new ArrayList<>();
        gradeList.add(grade);
        infoService.setStudentGrade(gradeList, userService.loadUserByUserName("김감자"));
    }
}
