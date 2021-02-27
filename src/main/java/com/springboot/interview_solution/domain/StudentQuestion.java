package com.springboot.interview_solution.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class StudentQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String student;
    private Integer question;
    private Integer part;

    @Builder
    public StudentQuestion(String student, Integer question, Integer part){
        this.student = student;
        this.question = question;
        this.part = part;
    }

}
