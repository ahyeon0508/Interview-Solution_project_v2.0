package com.springboot.interview_solution.dto;

import com.springboot.interview_solution.domain.Grade;

import java.util.ArrayList;

public class GradeListDto {

    private ArrayList<GradeDto> grades;

    public ArrayList<GradeDto> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<GradeDto> grades) {
        this.grades = grades;
    }
}
