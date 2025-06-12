package com.example.portfolioback.repository;

import com.example.portfolioback.entity.Part;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PartRepository {

    private final JdbcTemplate jdbcTemplate;

    public PartRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Part part){
        jdbcTemplate.update(
                "INSERT INTO part (project_id, title, description) VALUES (?, ?, ?)",
                part.getProjectId(), part.getTitle(), part.getDescription()
        );
    }

    //파트 삭제
    public void deleteByProjectId(Long projectId){
        String sql = "DELETE FROM part WHERE project_id = ?";
        jdbcTemplate.update(sql, projectId);
    }
}

