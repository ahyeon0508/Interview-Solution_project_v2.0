package com.springboot.interview_solution.service;

import com.springboot.interview_solution.dto.GradeDto;
import com.springboot.interview_solution.dto.UserDto;
import com.springboot.interview_solution.repository.GradeDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GradeServiceTest {

    @Autowired
    GradeService gradeService;
    @Autowired
    GradeDao gradeDao;
    @Autowired
    UserService userService;

    @Test
    void setStudentGrade() {
        System.out.println(1);
        GradeDto grade = new GradeDto();
        grade.setGrade(2);
        grade.setSemester(1);
        grade.setUser(userService.loadUserByUserName("김감자"));
        grade.setSubject("국어");
        grade.setCourse("화법과 문법");
        grade.setUnitNumber(3);
        grade.setRanking(2);
        grade.setRawScore(97);
        grade.setSubjectMean(50);
        grade.setAverage(40);
        grade.setAchievement("A");
        grade.setNumberOfStudents(30);

        gradeService.setStudentGrade(grade);
    }
}
