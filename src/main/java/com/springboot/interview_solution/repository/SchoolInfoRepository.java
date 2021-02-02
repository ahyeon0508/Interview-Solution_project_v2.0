package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.SchoolInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolInfoRepository extends JpaRepository<SchoolInfo, Long> {
}
