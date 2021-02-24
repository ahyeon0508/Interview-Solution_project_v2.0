package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Letter;
import com.springboot.interview_solution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LetterRepository extends JpaRepository<Letter, String> {
    Optional<Letter> findLetterByUser(User user);
}
