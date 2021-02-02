package com.springboot.interview_solution.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

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
    private Boolean isTeacher; //teacher = true, student = false

    public Boolean getIsTeacher() {
        if (isTeacher == null)
            return false;
        return isTeacher;
    }

    public void setIsTeacher(String isTeacher) {
        if(isTeacher.equals("Student")){
            this.isTeacher = false;
        } else this.isTeacher = true;
    }

    public void setIsTeacher(Boolean isTeacher) {
       this.isTeacher = isTeacher;
    }
}
