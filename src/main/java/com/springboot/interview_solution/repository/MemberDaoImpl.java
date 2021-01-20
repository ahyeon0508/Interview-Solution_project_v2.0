package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemberDaoImpl implements MemberDao{
    private JdbcTemplate jdbcTemplate;

    public MemberDaoImpl(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Member member){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userID",member.getUserId());
        parameters.put("username",member.getUsername());
        parameters.put("password",member.getPassword());
        parameters.put("phone",member.getPhone());
        parameters.put("school",member.getSchool());
        parameters.put("grade", member.getGrade());
        parameters.put("sClass",member.getsClass());
        parameters.put("isTeacher",member.getIsTeacher());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    @Override
    public Optional<Member> findByUserId(String userID){
        List<Member> result = jdbcTemplate.query("select * from user where userID= ?", memberRowMapper(), userID);
        return result.stream().findAny();
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) ->{
            Member member = new Member();
            member.setUserId(rs.getString("userID"));
            member.setUsername(rs.getString("username"));
            member.setPassword(rs.getString("password"));
            member.setPhone(rs.getString("phone"));
            member.setSchool(rs.getString("school"));
            member.setGrade(rs.getInt("grade"));
            member.setsClass(rs.getInt("sClass"));
            member.setIsTeacher(rs.getBoolean("isTeacher"));
            return member;
        };
    }
}
