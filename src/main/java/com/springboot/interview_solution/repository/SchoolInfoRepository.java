package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.SchoolInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolInfoRepository extends JpaRepository<SchoolInfo, Long> {
    @Query(value = "SELECT name FROM schoolinfo", nativeQuery = true)
    List<String> findAllSchoolName();
}


