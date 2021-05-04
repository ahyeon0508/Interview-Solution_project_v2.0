package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.FeedbackDto;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.dto.ReportSTTDto;
import com.springboot.interview_solution.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
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
        return reportRepository.findById(id);
    }

    public List<Report> getStudentReport(User user) throws Exception {
        return reportRepository.findReportsByTeacherAndShare(user, true);
    }

    public String readNumber(String num_string) {

        String[] han1 = {"", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
        String[] han2 = {"", "십", "백", "천"};
        String[] han3 = {"", "만", "억", "조", "경"};

        StringBuffer result = new StringBuffer();  // StringBuffer 선언

        int num_len = num_string.length();	     // num의 길이 측정

        String answer = "";

        for(int i=num_len-1; i>=0; i--){
            if(han1[Integer.parseInt(num_string.substring(num_len-i-1, num_len-i))]=="일" && num_len-i != num_len) {
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
//            ReportSTTDto reportStt1 = reportStt(report.getAudio1(), report.getSpeed1()); 스피드 이미 저장되어 있는 거 가져와서 측정하기
            report.setAdverb1(reportStt1.getAdverb());
            report.setRepetition1(reportStt1.getRepetition());
        }
        if(report.getAudio2() != null) {
            ReportSTTDto reportStt2 = reportStt(report.getAudio2());
//            ReportSTTDto reportStt2 = reportStt(report.getAudio2(), report.getSpeed2());
            report.setAdverb1(reportStt2.getAdverb());
            report.setRepetition1(reportStt2.getRepetition());
        }
        if(report.getAudio3() != null) {
            ReportSTTDto reportStt3 = reportStt(report.getAudio3());
//            ReportSTTDto reportStt3 = reportStt(report.getAudio3(), report.getSpeed3());
            report.setAdverb1(reportStt3.getAdverb());
            report.setRepetition1(reportStt3.getRepetition());
        }
        reportRepository.save(report);
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

            System.out.println(text);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

            System.out.println("responseBody : " + responBody);
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder IC_sentence = new StringBuilder();
        Iterator<Map.Entry<String, Integer>> iteratorIC = IC.entrySet().iterator();
        while(iteratorIC.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iteratorIC.next();
            IC_sentence.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        IC_sentence.deleteCharAt(IC_sentence.lastIndexOf(","));
        System.out.println(IC_sentence);

        StringBuilder NOUN_sentence = new StringBuilder();
        Iterator<Map.Entry<String, Integer>> iteratorNOUN = NOUN.entrySet().iterator();
        while(iteratorNOUN.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iteratorNOUN.next();
            NOUN_sentence.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        NOUN_sentence.deleteCharAt(NOUN_sentence.lastIndexOf(","));
        System.out.println(NOUN_sentence);

        ReportSTTDto sttDto = new ReportSTTDto();
        sttDto.setAdverb(
                "{" +
                        IC_sentence
                        + "}"
        );
        sttDto.setRepetition(
                "{" +
                        NOUN_sentence
                        + "}"
        );

        return sttDto;
    }
}