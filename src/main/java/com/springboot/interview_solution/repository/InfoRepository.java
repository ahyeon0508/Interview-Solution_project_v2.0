package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Grade;
import com.springboot.interview_solution.domain.GradeList;
import com.springboot.interview_solution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InfoRepository extends JpaRepository<Grade, String> {
    List<Grade> findGradeByUser(User user);
}
