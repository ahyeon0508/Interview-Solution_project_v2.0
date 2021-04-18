package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.domain.GradeList;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.GradeDto;
import com.springboot.interview_solution.repository.InfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class InfoService {

    private final InfoRepository infoRepository;

    // 성적 입력 저장
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
            putGrade.setNumberOfStudent(grade.getNumberOfStudent());
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
                    .numberOfStudent(grade.getNumberOfStudent()).build()
            );
        }
    }

    // 성적 가져오기
    public List<Grade> getStudentGrade(User user){
        List<Grade> gradeList = infoRepository.findByUserOrderByGradeAscSemesterAsc(user);
        return gradeList;
    }

    public List<Grade> getStudentGradeBySubject(User user, String subject){
        List<Grade> gradeList = infoRepository.findByUserAndAndSubjectOrderByGradeAscSemesterAsc(user, subject);
        return gradeList;
    }

    // 학년 별로 성적 가져오기
    public List<Grade> getStudentGradeByGradeAndSemester(Integer grade,Integer semester,User user) {
        List<Grade> gradeList = infoRepository.findByGradeAndSemesterAndUser(grade,semester,user);
        return gradeList;
    }

    // 성적 삭제
    @Transactional
    public void deleteStudentGrade(Long id) {
        infoRepository.deleteById(id);
    }
}
