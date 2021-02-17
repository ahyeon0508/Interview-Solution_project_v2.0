package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfoDao extends JpaRepository<Grade, String> {
    Optional<Grade> findGradeByUser(User user);
}
