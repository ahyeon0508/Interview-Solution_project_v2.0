package com.springboot.interview_solution.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name="schoolinfo")
@NoArgsConstructor
public class SchoolInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 학교 이름 */
    @Column(length = 100)
    private String name;

    @Builder
    public SchoolInfo(String name){
        this.name = name;
    }

    interface SchoolName{
        String getName();
    }

}
