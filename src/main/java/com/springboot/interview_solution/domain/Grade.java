package com.springboot.interview_solution.domain;

import lombok.*;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"grade","semester", "user", "course"}
                )
        }
)

// 오류나도 unique 잘 되니까 상관없음

@Data
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    private Long id;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "semester")
    private Integer semester;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    private String subject;

    @Column(name = "course")
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