package com.springboot.interview_solution.ai;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmotionRecognition {
    private static final String subscriptionKey = "aeb12dbaeb714ad8814eab1f46997ef2";
    private static final String endpoint = "https://resourse01.cognitiveservices.azure.com/";
// </environment>

    public String detect(String video1) {
        HttpClient httpclient = HttpClientBuilder.create().build();

        String imageWithFaces= "{\"url\":\"" + video1 + "\"}";

        try
        {
            URIBuilder builder = new URIBuilder(endpoint + "/face/v1.0/detect");

            // Request parameters. All of them are optional.
            builder.setParameter("detectionModel", "detection_01");
            builder.setParameter("returnFaceId", "true");
            builder.setParameter("returnFaceLandmarks","false");
            builder.setParameter("returnFaceAttributes","emotion");

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            // Request body.
            StringEntity reqEntity = new StringEntity(imageWithFaces);
            request.setEntity(reqEntity);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
// </main>

// <print>
            if (entity != null)
            {
                String jsonString = EntityUtils.toString(entity).trim();
                if (jsonString.charAt(0) == '[') {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    JSONObject faceAttributes = jsonArray.getJSONObject(0);
                    JSONObject emotion = faceAttributes.getJSONObject("faceAttributes");
                    String value = emotion.getString("emotion");

                    return value;
                }
                else if (jsonString.charAt(0) == '{') {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject emotion = jsonObject.getJSONObject("faceAttributes");
                    String value = emotion.getString("emotion");
                    return value;
                } else {
                    return jsonString;
                }
            }
        }
        catch (Exception e)
        {
            // Display error message.
            System.out.println(e.getMessage());
        }
        return null;
    }
}

