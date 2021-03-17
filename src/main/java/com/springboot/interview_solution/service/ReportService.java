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



@AllArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private JdbcTemplate jdbcTemplate;

    public void modifyTitle(Report report, String title) {
        jdbcTemplate.update("update report set title=? where id=?", title, report.getId());
    }

    public void modifyShare(Report report) {
        jdbcTemplate.update("update report set share=? where id=?", !report.getShare(), report.getId());
    }

    public void modifyFeedback(Long id, FeedbackDto feedbackDto) {
        if(feedbackDto.getFeedback1() != null) {
            jdbcTemplate.update("update report set comment1=?, comment1writed_at=? where id=?", feedbackDto.getFeedback1(), LocalDateTime.now(), id);
        } else if(feedbackDto.getFeedback2() != null) {
            jdbcTemplate.update("update report set comment2=?, comment2writed_at=? where id=?", feedbackDto.getFeedback2(), LocalDateTime.now(), id);
        } else if(feedbackDto.getFeedback3() != null) {
            jdbcTemplate.update("update report set comment3=?, comment3writed_at=? where id=?", feedbackDto.getFeedback3(), LocalDateTime.now(), id);
        }
    }

    public void deleteFeedback(Report report, Integer number) {
        if(number == 1)
            jdbcTemplate.update("update report set comment1=NULL, comment1writed_at=? where id=?", LocalDateTime.now(), report.getId());
        else if(number == 2)
            jdbcTemplate.update("update report set comment2=NULL, comment2writed_at=? where id=?", LocalDateTime.now(), report.getId());
        else
            jdbcTemplate.update("update report set comment3=NULL, comment3writed_at=? where id=?", LocalDateTime.now(), report.getId());
    }

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

    public List<Report> getStudentReport(User user) throws Exception {
        return reportRepository.findReportsByTeacher(user).orElseGet(()-> null);
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
        Report report = reportRepository.findReportById(id).orElseThrow();
        if(report.getAudio1() != null) {
            ReportSTTDto reportStt1 = reportStt(report.getAudio1());
//            ReportSTTDto reportStt1 = reportStt(report.getAudio1(), report.getSpeed1()); 스피드 이미 저장되어 있는 거 가져와서 측정하기
            jdbcTemplate.update("update report set adverb1=?, repetition1=? where id=?", reportStt1.getAdverb(), reportStt1.getRepetition(), report.getId());
        }
        if(report.getAudio2() != null) {
            ReportSTTDto reportStt2 = reportStt(report.getAudio2());
//            ReportSTTDto reportStt2 = reportStt(report.getAudio2(), report.getSpeed2());
            jdbcTemplate.update("update report set adverb2=?, repetition2=? where id=?", reportStt2.getAdverb(), reportStt2.getRepetition(), report.getId());
        }
        if(report.getAudio3() != null) {
            ReportSTTDto reportStt3 = reportStt(report.getAudio3());
//            ReportSTTDto reportStt3 = reportStt(report.getAudio3(), report.getSpeed3());
            jdbcTemplate.update("update report set adverb3=?, repetition3=? where id=?", reportStt3.getAdverb(), reportStt3.getRepetition(), report.getId());
        }
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
