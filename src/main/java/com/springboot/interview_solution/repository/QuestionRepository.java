package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByDepartment(Integer department);
    boolean existsByQuestion(String question);
    Question findByQuestion(String question);
}
