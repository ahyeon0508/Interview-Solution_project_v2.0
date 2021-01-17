package com.springboot.interview_solution.service;

import com.springboot.interview_solution.domain.Member;
import com.springboot.interview_solution.repository.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(MemberDao memberDao){
        this.memberDao = memberDao;
    }

    // signup
    public void signup(Member member){
        memberDao.save(member);
    }

    //validate duplication UserId
    public void validateDuplicateUserId(String userID){
        memberDao.findByUserId(userID).ifPresent(member -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

}
