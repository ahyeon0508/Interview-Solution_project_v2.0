package com.springboot.interview_solution.dto;

import com.springboot.interview_solution.domain.User;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ReportDto {
    private String title;
    private String question1;
    private String question2;
    private String question3;
    private String video1; //path
    private String video2; //path
    private String video3; //path
    private String audio1; //path
    private String audio2; //path
    private String audio3; //path
    private String script1;
    private String script2;
    private String script3;
    private String adverb1;
    private String adverb2;
    private String adverb3;
    private String repetition1;
    private String repetition2;
    private String repetition3;
    private Double speed1;
    private Double speed2;
    private Double speed3;
    private String sCorrect1;
    private String sCorrect2;
    private String sCorrect3;
    private String comment1;
    private String comment2;
    private String comment3;
    private User student;
    private User teacher;
    private Boolean share;
}
