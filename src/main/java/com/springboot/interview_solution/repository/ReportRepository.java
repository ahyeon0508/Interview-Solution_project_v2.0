package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Report;
import com.springboot.interview_solution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends CrudRepository<Report, String> {
    Report findReportById(Long id);
    List<Report> findReportsByTeacherAndShare(User user,Boolean share);
}