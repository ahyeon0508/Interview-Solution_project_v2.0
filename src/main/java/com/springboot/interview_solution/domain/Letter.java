package com.springboot.interview_solution.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Data
@Entity
public class Letter {

    @ManyToOne
    @Id
    private User user;

    @Column(length = 1500)
    private String content1;

    @Column(length = 800)
    private String content2;

    @Column(length = 1500)
    private String question3;

    @Column(length = 800)
    private String content3;

    @Builder
    public Letter (User user, String content1, String content2, String content3, String question3) {
        this.user = user;
        this.content1 = content1;
        this.content2 = content2;
        this.content3 = content3;
        this.question3 = question3;
    }

    public Letter(){
    }
}
