package com.springboot.interview_solution.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ", initialValue = 1, allocationSize = 1)
public class Transcript {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    private Long id;

    @ManyToOne
    private User user;

    private Integer grade;

    private String club;
    private String dacs;//Detailed capabilities and specialties
    private String overallOpinion;

    @Builder
    public Transcript (User user, Integer grade, String club, String dacs, String overallOpinion) {
        this.user = user;
        this.grade = grade;
        this.club = club;
        this.dacs = dacs;
        this.overallOpinion = overallOpinion;
    }
}
