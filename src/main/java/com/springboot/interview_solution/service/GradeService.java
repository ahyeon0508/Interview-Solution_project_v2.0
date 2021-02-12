package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.GradeDto;
import com.springboot.interview_solution.repository.GradeDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class GradeService {

    private final GradeDao gradeDao;

    public void setStudentGrade(ArrayList<Grade> gradeList, User user) {
        for (int i = 0; i < gradeList.size(); i++) {
            gradeDao.save(Grade.builder()
                    .grade(gradeList.get(i).getGrade())
                    .semester(gradeList.get(i).getSemester())
                    .user(user)
                    .subject(gradeList.get(i).getSubject())
                    .course(gradeList.get(i).getCourse())
                    .unitNumber(gradeList.get(i).getUnitNumber())
                    .ranking(gradeList.get(i).getRanking())
                    .rawScore(gradeList.get(i).getRawScore())
                    .subjectMean(gradeList.get(i).getSubjectMean())
                    .average(gradeList.get(i).getAverage())
                    .achievement(gradeList.get(i).getAchievement())
                    .numberOfStudents(gradeList.get(i).getNumberOfStudents()).build()
            );
        }
    }
}
