package com.springboot.interview_solution.service;
import com.springboot.interview_solution.domain.RecordData;
import com.springboot.interview_solution.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.bytedeco.javacpp.Loader;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

@AllArgsConstructor
@Service
public class InterviewService {

    private final ReportRepository reportRepository;

    //private Boolean stopButton;

    public void recordingVideo(String path, RecordData recordData){
        //60 seconds
        final int RECORD_TIME = 60000;

        Thread recordThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Start recording...");
                    recordData.start(path);
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                    exit(-1);
                }
            }
        });

        recordThread.start();

        try {
            Thread.sleep(RECORD_TIME);
        } catch (InterruptedException ex) {
            System.out.println("MY STOPPED");
            ex.printStackTrace();
        }

    }


    public void stopVideo(String path,RecordData recordData){
        // Insert audio file path (reportID+'_'+question_num)
        File audioFile = new File(path+"audio.wav");
        try {
            recordData.stop();
            recordData.save(audioFile);
            System.out.println("STOPPED");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("DONE");

    }

    public void makeFinalVideo(String reportID, String questionNum) {

//        String videoPath = "video" + reportID + "_" + questionNum + ".avi";
//        String audioPath = "audio" + reportID + "_" + questionNum + ".wav";

        String videoPath = "/Users/hyewonjin/Interview-Solution_project_v2.0/src/main/resources/video/video.avi";
        String audioPath = "/Users/hyewonjin/Interview-Solution_project_v2.0/src/main/resources/video/audio.wav";
        String outputPath = "/Users/hyewonjin/Interview-Solution_project_v2.0/src/main/resources/video/output.mp4";

        String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    ffmpeg,
                    "-i",
                    audioPath,
                    "-i",
                    videoPath,
                    "-c:a",
                    "aac",
                    "-c:v",
                    "copy",
                    outputPath
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }


    // select Question before interview
    public void selectQuestion(){

    }
}
