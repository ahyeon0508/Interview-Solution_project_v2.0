package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.dto.UserDto;
import com.springboot.interview_solution.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private JdbcTemplate jdbcTemplate;

    //Spring security 필수 구현 method
    @Override
    public User loadUserByUsername(String userID) throws UsernameNotFoundException{
        return userRepository.findByUserID(userID).orElseThrow(()-> new UsernameNotFoundException(userID));
    }

    // signup
    public void signup(UserDto userDto){
        Boolean isTeacher = false;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        if(userDto.getIsTeacher().equals("teacher")){
            isTeacher=true;
        }
        userRepository.save(User.builder()
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
        return userRepository.findByUserID(userID).isPresent();
    }

    // signin
    public Boolean signin(UserDto userDto) {
        String userID = userDto.getUserID();
        UserDetails user = userRepository.findByUserID(userID).orElseThrow(()-> new UsernameNotFoundException(userID));
        if (user != null){
            return true;
        } else return false;
    }

    public Boolean loadIsTeacherByUserID(String userID){
        Boolean isTeacher = jdbcTemplate.queryForObject("select is_teacher from user where userID=?", Boolean.class, userID);
        return isTeacher;
    }

    // findID
    public User loadUserByUserName(String username) throws UsernameNotFoundException{
        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
    }

    // changePW
    public void modifyPW(String userID, String password) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPW = encoder.encode(password);
        jdbcTemplate.update("update user set password=? where userID=?", new Object[]{newPW, userID});
    }
}
