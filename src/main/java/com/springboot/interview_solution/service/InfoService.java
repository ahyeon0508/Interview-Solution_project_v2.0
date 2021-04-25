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

    public void updateStudentGrade(Long id, GradeDto gradeDto) {
        Grade grade = infoRepository.findById(id).orElseThrow();
        grade.setGrade(gradeDto.getGrade());
        grade.setSemester(gradeDto.getSemester());
        grade.setSubject(gradeDto.getSubject());
        grade.setCourse(gradeDto.getCourse());
        grade.setUnitNumber(gradeDto.getUnitNumber());
        grade.setRanking(gradeDto.getRanking());
        grade.setRawRanking(gradeDto.getRawRanking());
        grade.setSubjectMean(gradeDto.getSubjectMean());
        grade.setAverage(gradeDto.getAverage());
        grade.setAchievement(gradeDto.getAchievement());
        grade.setNumberOfStudent(gradeDto.getNumberOfStudent());
        infoRepository.save(grade);
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
