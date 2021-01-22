package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.User;
import com.springboot.interview_solution.repository.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

public class MemberService implements UserDetailsService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(MemberDao memberDao){
        this.memberDao = memberDao;
    }

    // signup
    public void signup(User member){
        memberDao.save(member);
    }

    //validate duplication UserId
    public void validateDuplicateUserId(String userID){
        memberDao.findByUserId(userID).ifPresent(member -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserDetails user = (UserDetails) memberDao.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
        return (UserDetails) new User("user.getUserID()", "user.getPassword()");
    }
}
