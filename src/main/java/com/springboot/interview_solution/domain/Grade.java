package com.springboot.interview_solution.domain;

import lombok.*;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Data
@IdClass(GradePK.class)
public class Grade {

    private Integer grade;
    private Integer semester;

    @ManyToOne
    @Id
    private User user;

    private String subject;

    @Id
    private String course;
    private Integer unitNumber;
    private Integer ranking;
    private Integer rawScore;
    private Integer subjectMean;
    private Integer average;
    private String achievement;
    private Integer numberOfStudents;

    @Builder
    public Grade (Integer grade, Integer semester, User user, String subject, String course,
                  Integer unitNumber, Integer ranking, Integer rawScore, Integer subjectMean,
                  Integer average, String achievement, Integer numberOfStudents) {
        this.grade = grade;
        this.semester = semester;
        this.user = user;
        this.subject = subject;
        this.course = course;
        this.unitNumber = unitNumber;
        this.ranking = ranking;
        this.rawScore = rawScore;
        this.subjectMean = subjectMean;
        this.average = average;
        this.achievement = achievement;
        this.numberOfStudents = numberOfStudents;
    }

    public Grade(){

    }
}

class GradePK implements Serializable{
    Integer grade;
    Integer semester;
    private User user;
}
