package com.springboot.interview_solution.domain;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ", initialValue = 1, allocationSize = 1)
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    private Long id;

    private String title;
    private String question1;
    private String question2;
    private String question3;
    private String video1; //path
    private String video2; //path
    private String video3; //path
    private String audio1; //path
    private String audio2; //path
    private String audio3; //path
    private String script1;
    private String script2;
    private String script3;
    //    private JsonData adverb1; : 문자열을 JSON으로 읽을 수 있는지 확인
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String adverb1;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String adverb2;
    //    private String adverb2;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String adverb3;
    //    private String adverb3;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String repetition1;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String repetition2;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String repetition3;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String eyeTrack1;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String eyeTrack2;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String eyeTrack3;
    private Double speed1;
    private Double speed2;
    private Double speed3;
    private String sCorrect1;
    private String sCorrect2;
    private String sCorrect3;
    private String comment1;
    private String comment2;
    private String comment3;
    private LocalDateTime createdAt;
    private LocalDateTime comment1WritedAt;
    private LocalDateTime comment2WritedAt;
    private LocalDateTime comment3WritedAt;

    @ManyToOne
    private User student;

    @ManyToOne
    private User teacher;

    @ColumnDefault("false")
    private Boolean share;

    @Builder
    public Report(String title, String question1, String question2, String question3, User student, User teacher, LocalDateTime createdAt) {
        this.title = title;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.student = student;
        this.teacher = teacher;
        this.createdAt = createdAt;
        this.share = false;
    }
}