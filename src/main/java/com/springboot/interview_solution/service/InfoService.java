package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.repository.InfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class InfoService {

    private final InfoRepository infoRepository;

    public void setStudentGrade(ArrayList<Grade> gradeList, User user) {
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

    public ArrayList<Grade> getStudentGrade(User user){
        ArrayList<Grade> gradeList = new ArrayList<>();
        if(infoRepository.findGradeByUser(user).isPresent()) {
            gradeList.add(infoRepository.findGradeByUser(user).orElseThrow());
        }
        return gradeList;
    }
}
