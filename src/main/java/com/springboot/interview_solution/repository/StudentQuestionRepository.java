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
    @Query(value = "select question from StudentQuestion where user_id = ?1")
    List<Question> findAllQuestionByUser(User user);
    StudentQuestion findByUserAndQuestion(User user,Question question);
    void deleteById(Long id);
    List<StudentQuestion> findAllByUser(User user);
    List<StudentQuestion> findAllByUserAndPart(User user,Integer part);
}
