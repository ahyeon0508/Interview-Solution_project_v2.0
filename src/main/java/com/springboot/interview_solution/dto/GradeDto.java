package com.springboot.interview_solution.dto;

import com.springboot.interview_solution.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
public class GradeDto implements Serializable {

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

    public GradeDto() {}

    public GradeDto(Integer grade, Integer semester, String subject, String course, Integer unitNumber, Integer ranking, Integer rawScore, Double subjectMean,
                    Double average, String achievement, Integer numberOfStudents) {System.out.println("HELLO");}
}
