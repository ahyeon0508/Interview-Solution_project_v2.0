package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDao extends JpaRepository<User, Long>{
    Optional<User> findByUserId(String userID);
}
