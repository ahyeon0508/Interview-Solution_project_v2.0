package com.springboot.interview_solution.domain;

import com.springboot.interview_solution.dto.GradeDto;

import java.util.ArrayList;

public class GradeList {
    private ArrayList<Grade> grades;

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }
}
