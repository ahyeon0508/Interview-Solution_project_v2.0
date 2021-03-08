package com.springboot.interview_solution.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    private String userID;

    @Column(length = 10)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 11)
    private String phone;

    @Column(length = 20)
    private String school;

    private Integer grade;
    private Integer sClass;
    private String teacher;
    private Boolean isTeacher; //teacher = true, student = false

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getSchool() {
        return school;
    }

    public Integer getGrade() {
        return grade;
    }

    public Integer getsClass() {
        return sClass;
    }

    public String getTeacher() {
        return teacher;
    }

    @Builder
    public User(String userID, String username,String password, String phone, String school, Integer grade, Integer sClass, Boolean isTeacher){
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.school = school;
        this.grade = grade;
        this.sClass = sClass;
        this.isTeacher = isTeacher;
        //학생일 경우 선생님 연결하는 코드 작성 필요
        this.teacher = null;
    }

    @Builder
    public User(String userID, String username,String password, String phone, String school, Integer grade, Integer sClass){
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.school = school;
        this.grade = grade;
        this.sClass = sClass;
        this.isTeacher = false;
        //학생일 경우 선생님 연결하는 코드 작성 필요
        this.teacher = null;
    }

    @Builder
    public User(String userID, String username,String password, String phone, String school, Integer grade, Integer sClass, Boolean isTeacher, String teacher){
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.school = school;
        this.grade = grade;
        this.sClass = sClass;
        this.isTeacher = isTeacher;
        //학생일 경우 선생님 연결하는 코드 작성 필요
        this.teacher = teacher;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Set<GrantedAuthority> roles = new HashSet<>();
        if(isTeacher){
            roles.add(new SimpleGrantedAuthority("Teacher"));
        }else{
            roles.add(new SimpleGrantedAuthority("Student"));
        }
        return roles;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}