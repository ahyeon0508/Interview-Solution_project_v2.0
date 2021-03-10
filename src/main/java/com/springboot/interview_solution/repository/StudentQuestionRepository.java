package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Question;
import com.springboot.interview_solution.domain.StudentQuestion;
import com.springboot.interview_solution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentQuestionRepository extends JpaRepository<StudentQuestion,Long> {
    @Query(value = "select question from StudentQuestion where student = ?1")
    List<Question> findAllQuestionByUser(User user);
    @Query(value = "select question from StudentQuestion where student = ?1 and question = ?2")
    Question findQuestionByUserAAndQuestion(User user,Question question);
}