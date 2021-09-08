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
    private Integer grade; // 학년

    @Column(name = "semester")
    private Integer semester; // 학기

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    private String subject; // 교과

    @Column(name = "course")
    private String course; // 과목
    private Integer unitNumber; // 단위수
    private Integer ranking; // 석차등급
    private Integer rawRanking; // 원점수
    private Double subjectMean; // 과목평균
    private Double average; // 표준편차
    private String achievement; // 성취도
    private Long numberOfStudent; // 학생 수

    @Builder
    public Grade (Integer grade, Integer semester, User user, String subject, String course,
                  Integer unitNumber, Integer ranking, Integer rawRanking, Double subjectMean,
                  Double average, String achievement, Long numberOfStudent) {
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
        this.numberOfStudent = numberOfStudent;
    }

    public Grade(){

    }
}