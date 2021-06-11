package com.springboot.interview_solution.service;

import com.springboot.interview_solution.dto.LetterDto;
import com.springboot.interview_solution.repository.LetterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LetterServiceTest {

    @Autowired
    LetterService letterService;
    @Autowired
    LetterRepository letterRepository;
    @Autowired
    UserService userService;

    @Test
    void setStudentLetter() {
        System.out.println("Letter");
        LetterDto letter = new LetterDto();
        letter.setUser(userService.loadUserByUserName("김감자"));
        letter.setContent1("저는 세계적인 마케터가 되는 것이 꿈입니다. 먼저 영어 공부를 매일 하고, 영어 뉴스를 읽고 생각을 나누는 시간을 가졌으며, 광고 홍보 동아리에서 여러 마케팅을 기획하고 실행하였습니다.");
        letter.setContent2("장애인 복지관에서 봉사자로 활동하였습니다.");
        letter.setContent3("가장 기억에 남는 책은 어린 왕자입니다.");
        letter.setQuestion3("가장 인상깊게 읽었던 책에 대해서 소개하시오");

        letterService.setStudentLetter(letter, userService.loadUserByUserName("김감자"));
    }

}
