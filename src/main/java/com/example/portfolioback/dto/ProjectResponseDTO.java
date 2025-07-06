package com.example.portfolioback.dto;

import java.time.LocalDate;
import java.util.List;

public class ProjectResponseDTO {
    private Long projectId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<PartDTO> parts;

    public static class PartDTO {
        private String title;
        private String description;

        public PartDTO(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() { return title; }
        public String getDescription() { return description; }
    }

    // 생성자
    public ProjectResponseDTO(Long projectId, String title, String description, LocalDate startDate, LocalDate endDate, List<PartDTO> parts) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.parts = parts;
    }

    // getter
    public Long getProjectId() { return projectId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public List<PartDTO> getParts() { return parts; }
}
