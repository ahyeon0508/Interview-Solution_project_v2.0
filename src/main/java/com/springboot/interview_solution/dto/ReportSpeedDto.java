package com.springboot.interview_solution.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportSpeedDto {
    String sCorrect;

    public ReportSpeedDto(String speech_speed) {
        sCorrect = speech_speed;
    }
}
