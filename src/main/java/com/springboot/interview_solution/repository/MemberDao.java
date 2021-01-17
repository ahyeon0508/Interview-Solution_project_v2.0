package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Member;

import java.util.Optional;

public interface MemberDao {
    // Sign up
    void save(Member member);
    Optional<Member> findByUserId(String userID);

}
