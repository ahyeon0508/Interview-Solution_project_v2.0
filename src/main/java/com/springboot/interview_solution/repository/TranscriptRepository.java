package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Transcript;
import com.springboot.interview_solution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, String> {
    Optional<Transcript> findTranscriptByUser(User user);
}
