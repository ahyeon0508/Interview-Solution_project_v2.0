package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserID(String userID);
    Optional<User> findByUsername(String username);
    User findUserByUserID(String name);
}
