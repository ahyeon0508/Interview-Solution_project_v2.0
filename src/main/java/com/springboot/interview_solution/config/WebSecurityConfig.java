package com.springboot.interview_solution.config;

import com.springboot.interview_solution.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() { // 4
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) { // 5
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 6
        http
                .authorizeRequests()
                .antMatchers("/signin", "/signup").permitAll() // 누구나 접근 허용
                .antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
                .antMatchers("/main")
                .permitAll()
                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                .formLogin() // 8
                .loginPage("/signin") // 로그인 페이지 링크
                .defaultSuccessUrl("/main") // 로그인 성공 후 리다이렉트 주소
                .and()
                .logout() // 9
                .logoutSuccessUrl("/signin") // 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true);// 세션 날리기
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder()); // 해당 서비스(userService)에서는 UserDetailsService를 implements해서 loadUserByUsername() 구현해야함 (서비스 참고)
    }
}