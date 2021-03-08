package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.dto.GradeDto;
import com.springboot.interview_solution.repository.InfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GradeServiceTest {

    @Autowired
    InfoService infoService;
    @Autowired
    InfoRepository infoRepository;
    @Autowired
    UserService userService;

    @Test
    void setStudentGrade() {
        System.out.println(1);
        GradeDto grade = new GradeDto();
        grade.setGrade(2);
        grade.setSemester(1);
        grade.setUser(userService.loadUserByUsername("potatoe"));
        grade.setSubject("국어");
        grade.setCourse("화법과 문법");
        grade.setUnitNumber(3);
        grade.setRanking(2);
        grade.setRawRanking(97);
        grade.setSubjectMean(40.8);
        grade.setAverage(15.4);
        grade.setAchievement("A");
        grade.setNumberOfStudents(30);

        ArrayList<GradeDto> gradeList = new ArrayList<>();
        gradeList.add(grade);

        grade = new GradeDto();
        grade.setGrade(1);
        grade.setSemester(1);
        grade.setUser(userService.loadUserByUsername("potatoe"));
        grade.setSubject("국어");
        grade.setCourse("국어1");
        grade.setUnitNumber(3);
        grade.setRanking(2);
        grade.setRawRanking(79);
        grade.setSubjectMean(60.3);
        grade.setAverage(16.7);
        grade.setAchievement("A");
        grade.setNumberOfStudents(30);

        gradeList.add(grade);

        grade = new GradeDto();
        grade.setGrade(1);
        grade.setSemester(2);
        grade.setUser(userService.loadUserByUsername("potatoe"));
        grade.setSubject("국어");
        grade.setCourse("국어2");
        grade.setUnitNumber(3);
        grade.setRanking(2);
        grade.setRawRanking(80);
        grade.setSubjectMean(77.5);
        grade.setAverage(16.5);
        grade.setAchievement("A");
        grade.setNumberOfStudents(30);

        gradeList.add(grade);
        infoService.setStudentGrade(gradeList, userService.loadUserByUsername("potatoe"));
        List<Grade> list = infoService.getStudentGrade(userService.loadUserByUsername("potatoe"));
        for (int i = 0 ; i < list.size() ; i++) {
            System.out.println(list.get(i));
        }
    }
}
