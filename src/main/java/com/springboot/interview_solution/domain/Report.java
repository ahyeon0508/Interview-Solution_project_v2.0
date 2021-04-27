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
import java.util.List;

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
    public Report(String title, String question1, String question2, String question3, String video1, String video2, String video3,
                  String audio1, String audio2, String audio3, String script1, String script2, String script3, String adverb1, String adverb2, String adverb3,
                  String repetition1, String repetition2, String repetition3, Double speed1, Double speed2, Double speed3,
                  String sCorrect1, String sCorrect2, String sCorrect3, String comment1, String comment2, String comment3,
                  User student, User teacher, Boolean share, LocalDateTime createdAt, LocalDateTime comment1WritedAt, LocalDateTime comment2WritedAt, LocalDateTime comment3WritedAt) {
        this.title = title;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.video1 = video1;
        this.video2 = video2;
        this.video3 = video3;
        this.audio1 = audio1;
        this.audio2 = audio2;
        this.audio3 = audio3;
        this.script1 = script1;
        this.script2 = script2;
        this.script3 = script3;
        this.adverb1 = adverb1;
        this.adverb2 = adverb2;
        this.adverb3 = adverb3;
        this.repetition1 = repetition1;
        this.repetition2 = repetition2;
        this.repetition3 = repetition3;
        this.speed1 = speed1;
        this.speed2 = speed2;
        this.speed3 = speed3;
        this.sCorrect1 = sCorrect1;
        this.sCorrect2 = sCorrect2;
        this.sCorrect3 = sCorrect3;
        this.comment1 = comment1;
        this.comment2 = comment2;
        this.comment3 = comment3;
        this.student = student;
        this.teacher = teacher;
        this.share = share;
        this.createdAt = createdAt;
        this.comment1WritedAt = comment1WritedAt;
        this.comment2WritedAt = comment2WritedAt;
        this.comment3WritedAt = comment3WritedAt;
    }
}