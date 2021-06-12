package com.springboot.interview_solution;

import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterviewSolutionApplication {
//    static{
//        nu.pattern.OpenCV.loadShared();
//        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//    }
    public static void main(String[] args) {
        //OpenCV Library load
        SpringApplication.run(InterviewSolutionApplication.class, args);
    }

}
