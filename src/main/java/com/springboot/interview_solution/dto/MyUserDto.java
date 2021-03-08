package com.springboot.interview_solution.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyUserDto {
    private String userID;
    private String username;
    private String password;
    private String newPassword;
    private String passwordChk;
    private String phone;
    private String school;
    private Integer grade;
    private Integer sClass;
}
