package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDao {
    // Sign up
    void save(Member member);
    Optional<Member> findByUserId(String userID);
}
