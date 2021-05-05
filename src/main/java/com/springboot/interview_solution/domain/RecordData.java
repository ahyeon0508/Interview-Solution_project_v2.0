package com.springboot.interview_solution.domain;


import lombok.NoArgsConstructor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

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

    private boolean isRunning;

    /**
     * Defines a default audio format used to record
     */
    AudioFormat getAudioFormat() {
        AudioFormat.Encoding defaultEncoding = AudioFormat.Encoding.PCM_SIGNED;
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 1;
        int frameSize = 2;
        float frameRate = 44100;
        boolean bigEndian = false;
        return new AudioFormat(defaultEncoding,sampleRate,sampleSizeInBits,channels,frameSize,frameRate,bigEndian);
    }

    /**
     * Start recording sound.
     * @throws LineUnavailableException if the system does not support the specified
     * audio format nor open the audio data line.
     */
    public void start(String path) throws LineUnavailableException {
        format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        VideoCapture capture = new VideoCapture(0);
        Mat img = new Mat();
        capture.read(img);
        video = new VideoWriter(path+"_video.avi",VideoWriter.fourcc('D','I','V','X'),20.0,img.size(),true);

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

            //eye tracking

        }
    }

    /**
     * Stop recording sound.
     * @throws IOException if any I/O error occurs.
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
     * Save recorded sound data into a .wav file format.
     * @param wavFile The file to be saved.
     * @throws IOException if any I/O error occurs.
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