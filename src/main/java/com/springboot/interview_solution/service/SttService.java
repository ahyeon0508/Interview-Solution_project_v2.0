package com.springboot.interview_solution.service;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class SttService {

    public void kakaoStt() throws MalformedURLException {
        final String openApiUrl = "https://kakaoi-newtone-openapi.kakao.com/v1/recognize";
        final String key = "3a27a613233efd4a36b3e1eea4d9580c";
        String languageCode = "korean";
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        String audioFilePath = "/Users/yejin/Music/Music/Media.localized/Music/Unknown Artist/Unknown Album/부곡동.wav";
        String audioContents = null;

        Gson gson = new Gson();

        try {
            Path path = Paths.get(audioFilePath);
            byte[] audioBytes = Files.readAllBytes(path);
            audioContents = Base64.getEncoder().encodeToString(audioBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        headers.add("Transfer-Encoding", "chunked");
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Authorization", "KakaoAK " + key);

        argument.put("language_code", languageCode);
        argument.put("audio", audioContents);

        request.put("access_key", key);
        request.put("argument", argument);

        URL url = new URL(openApiUrl);

        String responBody;

        try {
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("Transfer-Encoding: chunked\"");
            sb.append("Content-Type: application/octet-stream");  //본인이 발급받은 key
            sb.append("Authorization: KakaoAK "+ key);     // 본인이 설정해 놓은 경로
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            int responseCode = con.getResponseCode();
            System.out.println("responseCode : " + responseCode);
//            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//            wr.write(gson.toJson(headers).getBytes("UTF-8"));
//            wr.flush();
//            wr.close();

            InputStream is = con.getInputStream();
            byte[] buffer = new byte[is.available()];
            int byteRead = is.read(buffer);
            responBody = new String(buffer);
            JSONObject jObject = new JSONObject(responBody);
            System.out.println(jObject);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
