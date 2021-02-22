package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.repository.InfoDao;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class InfoService {

    private final InfoDao infoDao;

    public void setStudentGrade(ArrayList<Grade> gradeList, User user) {
        System.out.println(gradeList);
        for (int i = 0; i < gradeList.size(); i++) {
            infoDao.save(Grade.builder()
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
        if(infoDao.findGradeByUser(user).isPresent()) {
            gradeList.add(infoDao.findGradeByUser(user).orElseThrow());
        }
        return gradeList;
    }
}
