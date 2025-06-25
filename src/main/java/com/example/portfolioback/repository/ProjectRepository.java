package com.example.portfolioback.repository;

import com.example.portfolioback.entity.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository //이 클래스는 db에 접근할 것이다~
public class ProjectRepository {

    //JdbcTemplate을 주입해서 DB 작업을 할 수 있게 한다
    private final JdbcTemplate jdbcTemplate;

    //생성자
    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //프로젝트 생성
    public Long saveAndReturnId(Project project){
        String sql = "INSERT INTO project (title, description, start_date, end_date) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, project.getTitle());
        ps.setString(2, project.getDescription());
        ps.setDate(3, java.sql.Date.valueOf(project.getStartDate()));
        if(project.getEndDate() != null) {
            ps.setDate(4, java.sql.Date.valueOf(project.getEndDate()));
        } else {
            ps.setNull(4, java.sql.Types.DATE);
        }

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    //프로젝트 삭제
    public void deleteById(Long projectId){
        String sql = "DELETE FROM project WHERE project_id = ?";
        jdbcTemplate.update(sql, projectId);
    }

    //프로젝트 수정(PUT - 전체 필드 수정)
    public void updateByPut(Long projectId, String title, String description, LocalDate startDate, LocalDate endDate) {
        String sql = "UPDATE project SET title = ?, description = ?, start_date = ?, end_date = ? WHERE project_id = ?";
        jdbcTemplate.update(sql,
                title,
                description,
                Date.valueOf(startDate),
                endDate !=null ? Date.valueOf(endDate): null,
                projectId
        );
    }

    //프로젝트 수정(PATCH - 필드 값이 null이 아닌 것만 업데이트하기)
    public void updateByPatch(Long projectId, String title, String description, LocalDate startDate, LocalDate endDate) {
        StringBuilder sql = new StringBuilder("UPDATE project SET ");
        List<Object> params = new ArrayList<>();
        boolean hasField = false;

        if(title !=null){
            sql.append("title = ?");
            params.add(title);
            hasField = true;
        }

        if(description != null){
            if(hasField) sql.append(", ");
            sql.append("description = ?");
            params.add(description);
            hasField = true;
        }

        if(startDate != null){
            if(hasField) sql.append(", ");
            sql.append("start_date = ?");
            params.add(Date.valueOf(startDate));
            hasField = true;
        }

        if(endDate != null){
            if(hasField) sql.append(", ");
            sql.append("end_date =?");
            params.add(Date.valueOf(endDate));
            hasField = true;
        }

        sql.append(" WHERE project_id = ?");
        params.add(projectId);

        if(hasField){
            jdbcTemplate.update(sql.toString(), params.toArray());
        }
    }


}
