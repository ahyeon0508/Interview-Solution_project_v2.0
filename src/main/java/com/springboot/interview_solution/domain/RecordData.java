package com.springboot.interview_solution.domain;

import lombok.NoArgsConstructor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import lombok.NoArgsConstructor;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@NoArgsConstructor
public class RecordData {
    //Audio
    private static final int BUFFER_SIZE = 4096;
    private ByteArrayOutputStream recordBytes;
    private TargetDataLine audioLine;
    private AudioFormat format;

    //Video
    private VideoWriter video;

    // 녹화 진행 여부
    private boolean isRunning;

    /**
     * audio format
     */
    AudioFormat getAudioFormat() {
        AudioFormat.Encoding defaultEncoding = AudioFormat.Encoding.PCM_SIGNED;
        float sampleRate = 16000;
        int sampleSizeInBits = 16;
        int channels = 1;
        int frameSize = 2;
        float frameRate = 16000;
        boolean bigEndian = false;
        return new AudioFormat(defaultEncoding,sampleRate,sampleSizeInBits,channels,frameSize,frameRate,bigEndian);
    }

    /**
     * start recording audio and video
     */
    public void start(String path) throws LineUnavailableException {
        format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        VideoCapture capture = new VideoCapture(0);
        Mat img = new Mat();
        capture.read(img);
        video = new VideoWriter(path + "_video.avi", VideoWriter.fourcc('D', 'I', 'V', 'X'), 8.0, img.size(), true);

        // checks if system supports the data line
        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException(
                    "The system does not support the specified format.");
        }

        audioLine = AudioSystem.getTargetDataLine(format);

        audioLine.open(format);
        audioLine.start();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;

        recordBytes = new ByteArrayOutputStream();
        isRunning = true;

        while (isRunning) {
            bytesRead = audioLine.read(buffer, 0, buffer.length);
            recordBytes.write(buffer, 0, bytesRead);
            capture.read(img);
            video.write(img);

        }
    }

    /**
     * Stop recording audio and video
     */
    public void stop() throws IOException {
        isRunning = false;

        if (audioLine != null) {
            audioLine.drain();
            audioLine.close();
            video.release();
        }
    }

    /**
     * save audio and video files
     */
    public void save(File wavFile) throws IOException {
        byte[] audioData = recordBytes.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format,
                audioData.length / format.getFrameSize());

        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, wavFile);

        audioInputStream.close();
        recordBytes.close();
    }
}
