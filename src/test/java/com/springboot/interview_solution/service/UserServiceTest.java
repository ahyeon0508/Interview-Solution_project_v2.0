package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.repository.UserDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    @Test
    void signup() {
        User user = new User();
        user.setUsername("김감자");
        user.setUserId("potatoe");
        user.setPassword("impotatoe1234");
        user.setPhone("01012345678");
        user.setSchool("분당대진고등학교");
        user.setGrade(1);
        user.setsClass(2);
        user.setIsTeacher(false);

        userService.signup(user);
    }

    @Test
    void validateDuplicateUserId() {
        User user1 = new User();
        user1.setUsername("고구마");
        user1.setUserId("sweetpt");
        user1.setPassword("impotatoe1234");
        user1.setPhone("01012345678");
        user1.setSchool("송림고등학교");
        user1.setGrade(1);
        user1.setsClass(2);
        user1.setIsTeacher(false);
        String member2UserId = "sweetpt";

        userService.signup(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, ()-> userService.validateDuplicateUserId(member2UserId));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}