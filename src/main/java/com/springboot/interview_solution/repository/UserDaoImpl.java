//package com.springboot.interview_solution.repository;
//
//import com.springboot.interview_solution.domain.User;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//public class UserDaoImpl implements UserDao {
//    private JdbcTemplate jdbcTemplate;
//
//    public UserDaoImpl(DataSource dataSource){
//        jdbcTemplate = new JdbcTemplate(dataSource);
//    }
//
//    @Override
//    public void save(User user){
//        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//        jdbcInsert.withTableName("user");
//
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("userID", user.getUserId());
//        parameters.put("username", user.getUsername());
//        parameters.put("password", user.getPassword());
//        parameters.put("phone", user.getPhone());
//        parameters.put("school", user.getSchool());
//        parameters.put("grade", user.getGrade());
//        parameters.put("sClass", user.getsClass());
//        parameters.put("isTeacher", user.getIsTeacher());
//
//        jdbcInsert.execute(new MapSqlParameterSource(parameters));
//    }
//
//    @Override
//    public Optional<User> findByUserID(String userID){
//        List<User> result = jdbcTemplate.query("select * from user where userID= ?", memberRowMapper(), userID);
//        return result.stream().findAny();
//    }
//
//    private RowMapper<User> memberRowMapper(){
//        return (rs, rowNum) ->{
//            User user = new User();
//            user.setUserId(rs.getString("userID"));
//            user.setUsername(rs.getString("username"));
//            user.setPassword(rs.getString("password"));
//            user.setPhone(rs.getString("phone"));
//            user.setSchool(rs.getString("school"));
//            user.setGrade(rs.getInt("grade"));
//            user.setsClass(rs.getInt("sClass"));
//            user.setIsTeacher(rs.getBoolean("isTeacher"));
//            return user;
//        };
//    }
//}
