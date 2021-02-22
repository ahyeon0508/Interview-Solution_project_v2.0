package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public void setReport(ReportDto report, User student, User teacher) {
        reportRepository.save(Report.builder()
                .title(report.getTitle())
                .question1(report.getQuestion1())
                .question2(report.getQuestion2())
                .question3(report.getQuestion3())
                .video1(report.getVideo1())
                .video2(report.getVideo2())
                .video3(report.getVideo3())
                .audio1(report.getAudio1())
                .audio2(report.getAudio2())
                .audio3(report.getAudio3())
                .script1(report.getScript1())
                .script2(report.getScript2())
                .script3(report.getScript3())
                .adverb1(report.getAdverb1())
                .adverb2(report.getAdverb2())
                .adverb3(report.getAdverb3())
                .repetition1(report.getRepetition1())
                .repetition2(report.getRepetition2())
                .repetition3(report.getRepetition3())
                .speed1(report.getSpeed1())
                .speed2(report.getSpeed2())
                .speed3(report.getSpeed3())
                .sCorrect1(report.getSCorrect1())
                .sCorrect2(report.getSCorrect2())
                .sCorrect3(report.getSCorrect3())
                .comment1(report.getComment1())
                .comment2(report.getComment2())
                .comment3(report.getComment3())
                .student(student)
                .teacher(teacher)
                .share(report.getShare())
                .build()
        );
    }

    public Report getReport(Long id) throws Exception {
        return reportRepository.findReportById(id).orElseThrow(()-> new Exception());
    }
}
