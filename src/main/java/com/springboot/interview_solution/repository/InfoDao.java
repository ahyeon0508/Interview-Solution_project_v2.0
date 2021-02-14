package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoDao extends JpaRepository<Grade, String> {
}
