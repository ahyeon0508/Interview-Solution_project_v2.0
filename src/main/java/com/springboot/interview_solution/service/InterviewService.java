package com.springboot.interview_solution.service;
import com.springboot.interview_solution.domain.RecordData;
import com.springboot.interview_solution.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@AllArgsConstructor
@Service
public class InterviewService {

    private final ReportRepository reportRepository;

    //private Boolean stopButton;

    public void recordingVideo(RecordData recordData){
        //60 seconds
        final int RECORD_TIME = 60000;

        Thread recordThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Start recording...");
                    recordData.start();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                    System.exit(-1);
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
        // Insert audio file path
        File audioFile = new File(path);

        try {
            recordData.stop();
            recordData.save(audioFile);
            System.out.println("STOPPED");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("DONE");

    }
}
