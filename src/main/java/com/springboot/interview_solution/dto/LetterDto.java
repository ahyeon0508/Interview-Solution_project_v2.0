package com.springboot.interview_solution.dto;

import com.springboot.interview_solution.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LetterDto {

    private User user;
    private String content1;
    private String content2;
    private String question3;
    private String content3;

}
