package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Question;
import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.ReportDto;
import com.springboot.interview_solution.repository.ReportRepository;
import com.springboot.interview_solution.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import org.thymeleaf.util.StringUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;



@AllArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

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

    public Report getReport(Long id) throws Exception {
        return reportRepository.findReportById(id).orElseThrow(()-> new Exception());
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

    public Double get_time(String audioFilePath) {
        Path path = Paths.get(audioFilePath);

        Double time = 0.4;
        return time;
    }

    public void makeReport(String audioFilePath) {
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

        List<String> IC = new ArrayList<>();
        List<String> SL = new ArrayList<>();
        List<String> NOUN = new ArrayList<>();

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
                        IC.add(morp.getString("lemma"));
                        System.out.println(morp.getString("lemma"));
                    } else if (type.equals("SL")) {
                        SL.add(morp.getString("lemma"));
                        System.out.println(morp.getString("lemma"));
                    } else if (type.equals("NNG") || type.equals("NNP") || type.equals("NP") || type.equals("NR")) {
                        NOUN.add(morp.getString("lemma"));
                        System.out.println(morp.getString("lemma"));
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
