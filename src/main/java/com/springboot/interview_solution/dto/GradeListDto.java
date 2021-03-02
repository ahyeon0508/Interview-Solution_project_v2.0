package com.springboot.interview_solution.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class GradeListDto {
    private List<GradeDto> grades;
}
