package com.example.portfolioback.repository;

import com.example.portfolioback.entity.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository //이 클래스는 db에 접근할 것이다~
public class ProjectRepository {

    //JdbcTemplate을 주입해서 DB 작업을 할 수 있게 한다
    private final JdbcTemplate jdbcTemplate;

    //생성자
    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long saveAndReturnId(Project project){
        String sql = "INSERT INTO project (title, start_date, end_date) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, project.getTitle());
        ps.setDate(2, java.sql.Date.valueOf(project.getStartDate()));
        if(project.getEndDate() != null) {
            ps.setDate(3, java.sql.Date.valueOf(project.getEndDate()));
        } else {
            ps.setNull(3, java.sql.Types.DATE);
        }

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
