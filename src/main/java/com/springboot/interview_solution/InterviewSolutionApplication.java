package com.springboot.interview_solution;

import com.springboot.interview_solution.property.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileUploadProperties.class
})

public class InterviewSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewSolutionApplication.class, args);
    }

}
