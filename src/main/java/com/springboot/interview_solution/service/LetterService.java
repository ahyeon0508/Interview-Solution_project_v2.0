package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Letter;
import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.LetterDto;
import com.springboot.interview_solution.repository.LetterDao;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LetterService {

    private final LetterDao letterDao;

    public void setStudentLetter(LetterDto letterDto, User user) {
        if(letterDao.findLetterByUser(user).isPresent()) {
            letterDao.delete(letterDao.findLetterByUser(user).orElseThrow(() -> new UsernameNotFoundException(user.getUsername())));
        }
        letterDao.save(Letter.builder()
                .user(user)
                .content1(letterDto.getContent1())
                .content2(letterDto.getContent2())
                .content3(letterDto.getContent3())
                .question3(letterDto.getQuestion3()).build()
        );
    }

    public Letter getStudentLetter(User user){
        return letterDao.findLetterByUser(user).orElse(new Letter(user, null, null, null, null));
    }
}
