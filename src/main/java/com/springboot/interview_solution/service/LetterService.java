package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Letter;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.LetterDto;
import com.springboot.interview_solution.repository.LetterRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;

    public void setStudentLetter(LetterDto letterDto, User user) {
        if(letterRepository.findLetterByUser(user).isPresent()) {
            letterRepository.delete(letterRepository.findLetterByUser(user).orElseThrow(() -> new UsernameNotFoundException(user.getUsername())));
        }
        letterRepository.save(Letter.builder()
                .user(user)
                .content1(letterDto.getContent1())
                .content2(letterDto.getContent2())
                .content3(letterDto.getContent3())
                .question3(letterDto.getQuestion3()).build()
        );
    }

    public Letter getStudentLetter(User user){
        return letterRepository.findLetterByUser(user).orElse(new Letter(user, null, null, null, null));
    }
}
