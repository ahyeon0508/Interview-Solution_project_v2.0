package com.springboot.interview_solution.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userID;
    private String username;
    private String password;
    private String phone;
    private String school;
    private Integer grade;
    private Integer sClass;
    private String isTeacher; //teacher = true, student = false
}
