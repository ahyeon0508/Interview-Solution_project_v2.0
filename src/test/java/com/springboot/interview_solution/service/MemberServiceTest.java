package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Member;
import com.springboot.interview_solution.repository.MemberDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberDao memberDao;

    @Test
    void signup() {
        Member member = new Member();
        member.setUsername("김감자");
        member.setUserId("potatoe");
        member.setPassword("impotatoe1234");
        member.setPhone("01012345678");
        member.setSchool("분당대진고등학교");
        member.setGrade(1);
        member.setsClass(2);
        member.setIsTeacher(false);

        memberService.signup(member);
    }

    @Test
    void validateDuplicateUserId() {
        Member member1= new Member();
        member1.setUsername("고구마");
        member1.setUserId("sweetpt");
        member1.setPassword("impotatoe1234");
        member1.setPhone("01012345678");
        member1.setSchool("송림고등학교");
        member1.setGrade(1);
        member1.setsClass(2);
        member1.setIsTeacher(false);
        String member2UserId = "sweetpt";

        memberService.signup(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, ()->memberService.validateDuplicateUserId(member2UserId));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}