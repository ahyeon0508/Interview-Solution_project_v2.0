package com.springboot.interview_solution.ai;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

public class EmotionRecognition {
    private static final String subscriptionKey = "1e2e253d-1074-4093-af35-251f61a837b8";
    private static final String endpoint = "https://resourse01.cognitiveservices.azure.com/";
    private static final String imageWithFaces = "{\"url\" : \"Users/yejin/IdeaProjects/Interview-Solution_project_v2.0/uploads/a.jpg\"}";
// </environment>

    public String detect() throws FrameGrabber.Exception {
        HttpClient httpclient = HttpClientBuilder.create().build();

        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber("/Users/yejin/Downloads/IMG_6444.mp4");
        frameGrabber.start();
        Frame f;

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
            Java2DFrameConverter c = new Java2DFrameConverter();
            f = frameGrabber.grab();
            BufferedImage bi = c.convert(f);
            ImageIO.write(bi,"jpg", new File("/Users/yejin/IdeaProjects/Interview-Solution_project_v2.0/uploads/a.jpg"));

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
                    System.out.println(value);

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

            frameGrabber.stop();
        }
        catch (Exception e)
        {
            // Display error message.
            System.out.println(e.getMessage());
        }
        return null;
    }
}

