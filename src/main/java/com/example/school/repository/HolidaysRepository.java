package com.example.school.repository;

import com.example.school.model.HoliDay;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HolidaysRepository {

    private final JdbcTemplate jdbcTemplate;
    public HolidaysRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<HoliDay> findAllHolidays(){
        String sql="SELECT * FROM holidays";
        var rowMapper= BeanPropertyRowMapper.newInstance(HoliDay.class);
        return jdbcTemplate.query(sql,rowMapper);
    }

}
