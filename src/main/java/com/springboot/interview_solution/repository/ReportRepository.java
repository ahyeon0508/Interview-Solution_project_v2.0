package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    Optional<Report> findReportById(Long id);
}