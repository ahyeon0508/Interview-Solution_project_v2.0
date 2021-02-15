package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterDao extends JpaRepository<Letter, String> {
}
