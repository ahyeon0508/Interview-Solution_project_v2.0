package com.springboot.interview_solution.dto;

import com.springboot.interview_solution.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class GradeDto {

    private Integer grade;
    private Integer semester;
    private User user;
    private String subject;
    private String course;
    private Integer unitNumber;
    private Integer ranking;
    private Integer rawRanking;
    private Double subjectMean;
    private Double average;
    private String achievement;
    private Long numberOfStudent;
}
