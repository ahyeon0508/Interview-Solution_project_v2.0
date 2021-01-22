package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.User;

import java.util.Optional;

public interface UserDao {
    // Sign up
    void save(User user);
    Optional<User> findByUserId(String userID);

}
