package com.springboot.interview_solution.dto;

import com.springboot.interview_solution.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranscriptDto {
    private User user;

    private Integer grade;

    private String club;
    private String dacs;//Detailed capabilities and specialties
    private String overallOpinion;
}
