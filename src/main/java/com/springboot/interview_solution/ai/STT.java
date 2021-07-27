package com.springboot.interview_solution.ai;

import com.google.gson.Gson;
import com.springboot.interview_solution.dto.ReportSTTDto;
import com.springboot.interview_solution.dto.ReportSpeedDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class STT {
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
