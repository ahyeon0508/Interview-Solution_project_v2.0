package com.springboot.interview_solution.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class StudentQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="teacher_id")
    private User teacher;

    @ManyToOne(targetEntity = Question.class)
    @JoinColumn(name="question_id")
    private Question question;

    private Integer part;

    @Builder
    public StudentQuestion(User student, User teacher, Question question, Integer part){
        this.user = student;
        this.teacher = teacher;
        this.question = question;
        this.part = part;
    }

}
