package com.springboot.interview_solution.service;

import com.springboot.interview_solution.ai.EmotionRecognition;
import com.springboot.interview_solution.ai.STT;
import com.springboot.interview_solution.domain.Question;
import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.FeedbackDto;
import com.springboot.interview_solution.dto.ReportSTTDto;
import com.springboot.interview_solution.dto.ReportSpeedDto;
import com.springboot.interview_solution.repository.ReportRepository;
import com.springboot.interview_solution.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Autowired
    ReportService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public Long setReport(User student, List<Question> questions) {
        //get user teacher
        User teacher;
        if(StringUtils.isEmpty(student.getTeacher())){
            String teacherID = userRepository.findMyTeacher(student.getSchool(), student.getGrade(), student.getsClass());
            if(teacherID.isEmpty()){
                teacher = null;
            }else{
                student.setTeacher(teacherID);
                userRepository.save(student);
                teacher = userRepository.findByUserID(teacherID).orElseThrow();
            }

        }else{
            teacher = userRepository.findByUserID(student.getTeacher()).orElseThrow();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd hh::mm");
        Date date = new Date();
        LocalDateTime currentDateTime = LocalDateTime.now();

        String title = simpleDateFormat.format(date)+" "+student.getUsername()+"의 면접영상";
        Report report = reportRepository.save(Report.builder()
                .title(title)
                .student(student)
                .teacher(teacher)
                .question1(questions.get(0).getQuestion())
                .question2(questions.get(1).getQuestion())
                .question3(questions.get(2).getQuestion())
                .createdAt(currentDateTime)
                .build()
        );

        return report.getId();
    }

    public void setEyeTracking(Long id, String eyeTrack, int question){
        Report report = reportRepository.findById(id);
        if(!eyeTrack.isEmpty()){
            if(question == 1){
                report.setEyeTrack1(eyeTrack);
            }else if(question ==2){
                report.setEyeTrack2(eyeTrack);
            }else{
                report.setEyeTrack3(eyeTrack);
            }
        }
        reportRepository.save(report);
    }

    public void modifyTitle(Long id, String title) {
        Report report = reportRepository.findById(id);
        report.setTitle(title);
        reportRepository.save(report);
    }

    public void modifyShare(Long id) {
        Report report = reportRepository.findById(id);
        report.setShare(!report.getShare());
        reportRepository.save(report);
    }

    public void modifyFeedback(Long id, FeedbackDto feedbackDto) {
        Report report = reportRepository.findById(id);
        if(feedbackDto.getFeedback1() != null) {
            report.setComment1(feedbackDto.getFeedback1());
            report.setComment1WritedAt(LocalDateTime.now());
        } else if(feedbackDto.getFeedback2() != null) {
            report.setComment2(feedbackDto.getFeedback2());
            report.setComment2WritedAt(LocalDateTime.now());
        } else if(feedbackDto.getFeedback3() != null) {
            report.setComment3(feedbackDto.getFeedback3());
            report.setComment3WritedAt(LocalDateTime.now());
        }
        reportRepository.save(report);
    }

    public void deleteFeedback(Report report, Integer number) {
        if(number == 1) {
            report.setComment1(null);
            report.setComment1WritedAt(LocalDateTime.now());
        } else if(number == 2) {
            report.setComment2(null);
            report.setComment2WritedAt(LocalDateTime.now());
        } else if(number == 3) {
            report.setComment3(null);
            report.setComment3WritedAt(LocalDateTime.now());
        }
        reportRepository.save(report);
    }

    public List<Report> getReports(User user) throws Exception {
        return reportRepository.findByStudent(user);
    }

    public Report getReport(Long id) throws Exception {
        return reportRepository.findReportById(id);
    }

    @Transactional
    public void deleteReport(Long id) throws Exception{
        reportRepository.deleteById(id);
    }

    public List<Report> getStudentReport(User user) throws Exception {
        return reportRepository.findReportsByTeacherAndShare(user, true);
    }

    public void makeReport(Long id) {
        Report report = reportRepository.findById(id);
        STT stt = new STT();
        if(report.getAudio1() != null) {
            ReportSTTDto reportStt1 = stt.reportStt(report.getAudio1());
            report.setScript1(reportStt1.getScript());
            report.setAdverb1(reportStt1.getAdverb());
            report.setRepetition1(reportStt1.getRepetition());
            report.setSCorrect1(stt.reportSpeed(reportStt1.getScript(), report.getSpeed1()).getSCorrect());
        }
        if(report.getAudio2() != null) {
            ReportSTTDto reportStt2 = stt.reportStt(report.getAudio2());
            report.setScript2(reportStt2.getScript());
            report.setAdverb2(reportStt2.getAdverb());
            report.setRepetition2(reportStt2.getRepetition());
            report.setSCorrect1(stt.reportSpeed(reportStt2.getScript(), report.getSpeed2()).getSCorrect());
        }
        if(report.getAudio3() != null) {
            ReportSTTDto reportStt3 = stt.reportStt(report.getAudio3());
            report.setScript3(reportStt3.getScript());
            report.setAdverb3(reportStt3.getAdverb());
            report.setRepetition3(reportStt3.getRepetition());
            report.setSCorrect3(stt.reportSpeed(reportStt3.getScript(), report.getSpeed3()).getSCorrect());
        }
        reportRepository.save(report);
    }

    public void emotion() {
        EmotionRecognition emotionRecognition = new EmotionRecognition();
        System.out.println(emotionRecognition.detect());
    }
}