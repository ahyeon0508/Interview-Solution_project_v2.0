package com.springboot.interview_solution.dto;

import com.springboot.interview_solution.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class GradeDto {

    private Integer grade;
    private Integer semester;
    private User user;
    private String subject;
    private String course;
    private Integer unitNumber;
    private Integer ranking;
    private Integer rawScore;
    private Double subjectMean;
    private Double average;
    private String achievement;
    private Integer numberOfStudents;

}
