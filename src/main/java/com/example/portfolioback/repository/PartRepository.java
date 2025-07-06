package com.example.portfolioback.repository;

import com.example.portfolioback.entity.Part;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    //projectId로 파트 찾기
    public List<Part> findByProjectId(Long projectId) {
        String sql = "SELECT * FROM part WHERE project_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Part part = new Part();
            part.setProjectId(rs.getLong("project_id"));
            part.setTitle(rs.getString("title"));
            part.setDescription(rs.getString("description"));
            return part;
        }, projectId);
    }

}

