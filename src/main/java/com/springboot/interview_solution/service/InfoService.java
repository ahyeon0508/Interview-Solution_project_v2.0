package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.domain.GradeList;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.GradeDto;
import com.springboot.interview_solution.repository.InfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class InfoService {

    private final InfoRepository infoRepository;

    public void setStudentGrade(GradeDto grade, User user) {
        if(infoRepository.findByGradeAndSemesterAndUserAndCourse(grade.getGrade(), grade.getSemester(), user, grade.getCourse()).isPresent()){
            Grade putGrade = infoRepository.findByGradeAndSemesterAndUserAndCourse(grade.getGrade(), grade.getSemester(), user, grade.getCourse()).orElseThrow();
            putGrade.setGrade(grade.getGrade());
            putGrade.setSemester(grade.getSemester());
            putGrade.setUser(user);
            putGrade.setSubject(grade.getSubject());
            putGrade.setCourse(grade.getCourse());
            putGrade.setUnitNumber(grade.getUnitNumber());
            putGrade.setRanking(grade.getRanking());
            putGrade.setRawRanking(grade.getRawRanking());
            putGrade.setSubjectMean(grade.getSubjectMean());
            putGrade.setAverage(grade.getAverage());
            putGrade.setAchievement(grade.getAchievement());
            putGrade.setNumberOfStudents(grade.getNumberOfStudents());
            infoRepository.save(putGrade);
        } else {
            infoRepository.save(Grade.builder()
                    .grade(grade.getGrade())
                    .semester(grade.getSemester())
                    .user(user)
                    .subject(grade.getSubject())
                    .course(grade.getCourse())
                    .unitNumber(grade.getUnitNumber())
                    .ranking(grade.getRanking())
                    .rawRanking(grade.getRawRanking())
                    .subjectMean(grade.getSubjectMean())
                    .average(grade.getAverage())
                    .achievement(grade.getAchievement())
                    .numberOfStudents(grade.getNumberOfStudents()).build()
            );
        }
    }

    public void setStudentGrade(ArrayList<GradeDto> gradeList, User user) {
        for (int i = 0; i < gradeList.size(); i++) {
            infoRepository.save(Grade.builder()
                    .grade(gradeList.get(i).getGrade())
                    .semester(gradeList.get(i).getSemester())
                    .user(user)
                    .subject(gradeList.get(i).getSubject())
                    .course(gradeList.get(i).getCourse())
                    .unitNumber(gradeList.get(i).getUnitNumber())
                    .ranking(gradeList.get(i).getRanking())
                    .rawRanking(gradeList.get(i).getRawRanking())
                    .subjectMean(gradeList.get(i).getSubjectMean())
                    .average(gradeList.get(i).getAverage())
                    .achievement(gradeList.get(i).getAchievement())
                    .numberOfStudents(gradeList.get(i).getNumberOfStudents()).build()
            );
        }
    }

    public List<Grade> getStudentGrade(User user){
        List<Grade> gradeList = new ArrayList<>();
        gradeList = infoRepository.findGradeByUser(user);
        return gradeList;
    }
}
