package com.springboot.interview_solution.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ", initialValue = 1, allocationSize = 1)
public class OCR {
    @Id
    private Long id;
    private String ocr;

    public OCR(String ocr){
        this.ocr = ocr;
    }
}
