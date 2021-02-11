package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.GradeDto;
import com.springboot.interview_solution.repository.GradeDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GradeService {

    private final GradeDao gradeDao;

    public void setStudentGrade(GradeDto gradeDto) {
        gradeDao.save(Grade.builder()
                .grade(gradeDto.getGrade())
                .semester(gradeDto.getSemester())
                .user(gradeDto.getUser())
                .subject(gradeDto.getSubject())
                .course(gradeDto.getCourse())
                .unitNumber(gradeDto.getUnitNumber())
                .ranking(gradeDto.getRanking())
                .rawScore(gradeDto.getRawScore())
                .subjectMean(gradeDto.getSubjectMean())
                .average(gradeDto.getAverage())
                .achievement(gradeDto.getAchievement())
                .numberOfStudents(gradeDto.getNumberOfStudents()).build()
        );
    }
}
