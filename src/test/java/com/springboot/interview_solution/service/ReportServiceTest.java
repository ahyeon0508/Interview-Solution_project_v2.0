package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.repository.ReportRepository;
import com.springboot.interview_solution.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
public class ReportServiceTest {

    @Autowired
    ReportService reportService;

    @Autowired
    UserService userService;
    ReportRepository reportRepository;

    @Test
    public void setReport() {
        ReportDto reportDto = new ReportDto();
        reportDto.setTitle("제목");
        reportDto.setQuestion1("질문1");
        reportDto.setQuestion2("질문2");
        reportDto.setQuestion3("질문3");
        reportDto.setAudio1("/Users/yejin/Music/Music/Media.localized/Music/Unknown Artist/Unknown Album/부곡동-12.wav");
//        System.out.println(userRepository.findByUserID("potatoe").orElseThrow(() -> new UsernameNotFoundException("안녕하세")));
        reportDto.setStudent(userService.loadUserByUserName("potatoe"));
        reportDto.setTeacher(userService.loadUserByUserName("선생님"));
        reportRepository.save(Report.builder()
                .title(reportDto.getTitle())
                .question1(reportDto.getQuestion1())
                .student(reportDto.getStudent())
                .teacher(reportDto.getTeacher())
                .audio1(reportDto.getAudio1())
                .build());
    }
}
