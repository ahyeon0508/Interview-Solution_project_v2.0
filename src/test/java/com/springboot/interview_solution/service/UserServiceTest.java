package com.springboot.interview_solution.service;

import com.springboot.interview_solution.dto.UserDto;
import com.springboot.interview_solution.repository.UserRepository;
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
    UserRepository userRepository;

    @Test
    void signup() {
        System.out.println(1);
        UserDto user = new UserDto();
        user.setUsername("김감자123");
        user.setUserID("potatoe1234");
        user.setPassword("impotatoe1234");
        user.setPhone("01012345678");
        user.setSchoolName("분당대진고등학교");
        user.setGrade(1);
        user.setSClass(2);
        user.setIsTeacher("student");

        userService.signup(user);
    }

    @Test
    void validateDuplicateUserId() {
        UserDto user1 = new UserDto();
        user1.setUsername("고구마");
        user1.setUserID("sweetpt");
        user1.setPassword("impotatoe1234");
        user1.setPhone("01012345678");
        user1.setSchoolName("송림고등학교");
        user1.setGrade(1);
        user1.setSClass(2);
        String member2UserId = "sweetpt";
        user1.setIsTeacher("Student");

        userService.signup(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, ()-> userService.validateDuplicateUserId(member2UserId));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}