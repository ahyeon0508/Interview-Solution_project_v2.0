package com.springboot.interview_solution.config;

import com.springboot.interview_solution.repository.MemberDao;
import com.springboot.interview_solution.repository.MemberDaoImpl;
import com.springboot.interview_solution.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberDao());
    }

    @Bean
    public MemberDao memberDao(){
        return new MemberDaoImpl(dataSource);
    }
}
