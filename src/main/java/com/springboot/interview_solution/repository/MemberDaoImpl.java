package com.springboot.interview_solution.repository;

import com.springboot.interview_solution.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public User save(User member){
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
        return null;
    }

    @Override
    public Optional<User> findByUserId(String userID){
        List<User> result = jdbcTemplate.query("select * from User where userID= ?", memberRowMapper(), userID);
        System.out.println(result);
        return result.stream().findAny();
    }

    private RowMapper<User> memberRowMapper(){
        return (rs, rowNum) ->{
            User member = new User();
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

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<User> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }
}
