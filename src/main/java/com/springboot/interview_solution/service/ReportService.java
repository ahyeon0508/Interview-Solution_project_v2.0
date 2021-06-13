package com.springboot.interview_solution.service;

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
            teacher = null;
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

    public String readNumber(String num_string) {

        String[] han1 = {"", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
        String[] han2 = {"", "십", "백", "천"};
        String[] han3 = {"", "만", "억", "조", "경"};

        StringBuilder result = new StringBuilder();  // StringBuffer 선언

        int num_len = num_string.length();	     // num의 길이 측정

        String answer = "";

        for(int i=num_len-1; i>=0; i--){
            if(han1[Integer.parseInt(num_string.substring(num_len - i - 1, num_len - i))].equals("일") && num_len-i != num_len) {
                result.append(han1[0]);
            } else {
                result.append(han1[Integer.parseInt(num_string.substring(num_len-i-1, num_len-i))]);
            }

            if(Integer.parseInt(num_string.substring(num_len-i-1, num_len-i))>0);

            result.append(han2[i%4]);

            if(i%4==0)
                result.append(han3[i/3]);
        }
        answer = result.toString();
        return answer;
    }

    public void makeReport(Long id) {
        Report report = reportRepository.findById(id);
        if(report.getAudio1() != null) {
            ReportSTTDto reportStt1 = reportStt(report.getAudio1());
            report.setScript1(reportStt1.getScript());
            report.setAdverb1(reportStt1.getAdverb());
            report.setRepetition1(reportStt1.getRepetition());
            report.setSCorrect1(reportSpeed(reportStt1.getScript(), report.getSpeed1()).getSCorrect());
        }
        if(report.getAudio2() != null) {
            ReportSTTDto reportStt2 = reportStt(report.getAudio2());
            report.setScript2(reportStt2.getScript());
            report.setAdverb2(reportStt2.getAdverb());
            report.setRepetition2(reportStt2.getRepetition());
            report.setSCorrect1(reportSpeed(reportStt2.getScript(), report.getSpeed2()).getSCorrect());
        }
        if(report.getAudio3() != null) {
            ReportSTTDto reportStt3 = reportStt(report.getAudio3());
            report.setScript3(reportStt3.getScript());
            report.setAdverb3(reportStt3.getAdverb());
            report.setRepetition3(reportStt3.getRepetition());
            report.setSCorrect3(reportSpeed(reportStt3.getScript(), report.getSpeed3()).getSCorrect());
        }
        reportRepository.save(report);
    }

    public ReportSpeedDto reportSpeed(String script, Double speed) {
        int text_count;
        String character_extraction;
        StringBuilder remove_number;
        String num_string;
        String speech_speed = null;

        if(script.matches(".*[0-9].*")){
            character_extraction = script.replaceAll("\\s+", "");
            remove_number = new StringBuilder(character_extraction.replaceAll("[^0-9]", ""));
            for (int i = 0; i < character_extraction.length(); i++) {
                char ch = character_extraction.charAt(i);
                if (48 <= ch && ch <= 57) {
                    num_string = Character.toString(ch);
                    remove_number.append(readNumber(num_string));
                }
            }
            text_count = remove_number.length();
        } else {
            character_extraction = script.replaceAll("\\s+", "");
            text_count = character_extraction.length();
        }

        if ((text_count / speed) >= 4.5 && (text_count / speed) <= 5.5) {
            speech_speed = "적당한 속도입니다.";
        } else if (text_count / speed < 4.5) {
            speech_speed = "조금 느린 속도입니다.";
        } else {
            speech_speed = "조금 빠른 속도입니다.";
        }

        return new ReportSpeedDto(speech_speed);
    }

    public ReportSTTDto reportStt(String audioFilePath) {
        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseASR/Recognition";
        String openApiURL2 = "http://aiopen.etri.re.kr:8000/WiseNLU_spoken";
        String accessKey = "1a2937a3-caef-42ee-9b2d-4eadaf9c78c9";    // 발급받은 API Key
        String accessKey2 = "1a2937a3-caef-42ee-9b2d-4eadaf9c78c9";    // 발급받은 API Key
        String languageCode = "korean";     // 언어 코드
        String analysisCode = "morp";   // 언어 분석 코드
//        String audioFilePath = "/Users/yejin/Music/Music/Media.localized/Music/Unknown Artist/Unknown Album/부곡동.wav";  // 녹음된 음성 파일 경로
        String audioContents = null;

        Gson gson = new Gson();

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        String text = null;

        ReportSTTDto sttDto = new ReportSTTDto();

        try {
            Path path = Paths.get(audioFilePath);
            byte[] audioBytes = Files.readAllBytes(path);
            audioContents = Base64.getEncoder().encodeToString(audioBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        argument.put("language_code", languageCode);
        argument.put("audio", audioContents);

        request.put("access_key", accessKey);
        request.put("argument", argument);

        URL url;
        Integer responseCode = null;
        String responBody = null;
        try {
            url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(gson.toJson(request).getBytes("UTF-8"));
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            byte[] buffer = new byte[is.available()];
            int byteRead = is.read(buffer);
            responBody = new String(buffer);
            JSONObject jObject = new JSONObject(responBody);
            JSONObject return_object = jObject.getJSONObject("return_object");
            text = return_object.getString("recognized");

            sttDto.setScript(text);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        request = new HashMap<>();
        argument = new HashMap<>();

        argument.put("analysis_code", analysisCode);
        argument.put("text", text);

        request.put("access_key", accessKey);
        request.put("argument", argument);

        Map<String, Integer> IC = new HashMap<>();
        Map<String, Integer> NOUN = new HashMap<>();

        try {
            url = new URL(openApiURL2);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(gson.toJson(request).getBytes("UTF-8"));
            wr.flush();
            wr.close();

            InputStream is = con.getInputStream();
            byte[] buffer = new byte[is.available()];
            int byteRead = is.read(buffer);
            responBody = new String(buffer);

            JSONObject jObject = new JSONObject(responBody);
            JSONObject return_object = jObject.getJSONObject("return_object");
            JSONArray sentence = return_object.getJSONArray("sentence");
            for (int i = 0 ; i < sentence.length() ; i++ ){
                JSONObject obj = sentence.getJSONObject(i);
                JSONArray array = obj.getJSONArray("morp");

                for(int j = 0 ; j < array.length() ; j++) {
                    JSONObject morp = array.getJSONObject(j);
                    String type = morp.getString("type");

                    if(type.equals("IC")) {
                        if(IC.containsKey(morp.getString("lemma"))) {
                            IC.replace(morp.getString("lemma"), IC.get(morp.getString("lemma")) + 1);
                        } else {
                            IC.put(morp.getString("lemma"), 1);
                        }
                    } else if (type.equals("SL") || type.equals("NNG") || type.equals("NNP") || type.equals("NP") || type.equals("NR")) {
                        if(NOUN.containsKey(morp.getString("lemma"))) {
                            NOUN.replace(morp.getString("lemma"), NOUN.get(morp.getString("lemma")) + 1);
                        } else {
                            NOUN.put(morp.getString("lemma"), 1);
                        }
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if(!IC.isEmpty()){
            StringBuilder IC_sentence = new StringBuilder();
            Iterator<Map.Entry<String, Integer>> iteratorIC = IC.entrySet().iterator();
            while(iteratorIC.hasNext()) {
                Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iteratorIC.next();
                IC_sentence.append('"'+entry.getKey()).append("\":").append(entry.getValue()).append(",");
            }
            IC_sentence.deleteCharAt(IC_sentence.lastIndexOf(","));


            sttDto.setAdverb(
                    "{" +
                            IC_sentence
                            + "}"
            );
        }
        else {
            sttDto.setAdverb(
                    "{}"
            );
        }

        if(!NOUN.isEmpty()){
            StringBuilder NOUN_sentence = new StringBuilder();
            Iterator<Map.Entry<String, Integer>> iteratorNOUN = NOUN.entrySet().iterator();
            while(iteratorNOUN.hasNext()) {
                Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iteratorNOUN.next();
                NOUN_sentence.append('"'+entry.getKey()).append("\":").append('"').append(entry.getValue()).append('"').append(",");
            }
            NOUN_sentence.deleteCharAt(NOUN_sentence.lastIndexOf(","));

            sttDto.setRepetition(
                    "{" +
                            NOUN_sentence
                            + "}"
            );
        }

        else {
            sttDto.setRepetition(
                    "{}"
            );
        }
        return sttDto;
    }
}