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
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    private Long id;

    private Integer grade;
    private Integer semester;

    @ManyToOne
    private User user;

    private String subject;

    private String course;
    private Integer unitNumber;
    private Integer ranking;
    private Integer rawRanking;
    private Double subjectMean;
    private Double average;
    private String achievement;
    private Integer numberOfStudents;

    @Builder
    public Grade (Integer grade, Integer semester, User user, String subject, String course,
                  Integer unitNumber, Integer ranking, Integer rawRanking, Double subjectMean,
                  Double average, String achievement, Integer numberOfStudents) {
        this.grade = grade;
        this.semester = semester;
        this.user = user;
        this.subject = subject;
        this.course = course;
        this.unitNumber = unitNumber;
        this.ranking = ranking;
        this.rawRanking = rawRanking;
        this.subjectMean = subjectMean;
        this.average = average;
        this.achievement = achievement;
        this.numberOfStudents = numberOfStudents;
    }

    public Grade(){

    }
}
