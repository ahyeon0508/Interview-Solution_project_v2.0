package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.repository.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private UserDao userDao;

    // signup
    @Transactional
    public void signup(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);
    }

    //validate duplication UserId
    public void validateDuplicateUserId(String userID){
        userDao.findByUserId(userID).ifPresent(member -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

}
