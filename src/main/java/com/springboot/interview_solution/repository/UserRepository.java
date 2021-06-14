package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserID(String userID);
    Optional<User> findByUsername(String username);
    @Query(value = "SELECT userid FROM user where is_teacher=true and school=?1 and grade=?2 and s_class=?3", nativeQuery = true)
    String findMyTeacher(String school, Integer grade, Integer sClass);
    User findUserByUserID(String name);
}
