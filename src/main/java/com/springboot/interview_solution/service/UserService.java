package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.UserDto;
import com.springboot.interview_solution.repository.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    //Spring security 필수 구현 method
    @Override
    public User loadUserByUsername(String userID) throws UsernameNotFoundException{
        return userDao.findByUserID(userID).orElseThrow(()-> new UsernameNotFoundException(userID));
    }

    // signup
    public void signup(UserDto userDto){
        Boolean isTeacher = false;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        if(userDto.getIsTeacher().equals("teacher")){
            isTeacher=true;
        }
        userDao.save(User.builder()
                .userID(userDto.getUserID())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .phone(userDto.getPhone())
                .school(userDto.getSchool())
                .grade(userDto.getGrade())
                .sClass(userDto.getSClass())
                .isTeacher(isTeacher).build());
    }

    //validate duplication UserId
    public Boolean validateDuplicateUserId(String userID){
        return userDao.findByUserID(userID).isPresent();
    }

    // signin
    public Boolean signin(UserDto userDto) {
        String userID = userDto.getUserID();
        Boolean validate = validateDuplicateUserId(userID);
        if (validate = true) {
            return true;
        } else {
            return false;
        }
    }
}
