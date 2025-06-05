package com.example.portfolioback.service;

import com.example.portfolioback.dto.ProjectCreateRequestDTO;
import com.example.portfolioback.entity.Part;
import com.example.portfolioback.entity.Project;
import com.example.portfolioback.repository.PartRepository;
import com.example.portfolioback.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service //@Service 애너테이션으로 비즈니스 로직을 처리하는 서비스임을 나타내는 애너테이션이다.
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PartRepository partRepository;

    public ProjectService(ProjectRepository projectRepository, PartRepository partRepository){
        this.projectRepository = projectRepository;
        this.partRepository = partRepository;
    }

    @Transactional
    public void createProjectWithPart(ProjectCreateRequestDTO request){
        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());

        Long projectId = projectRepository.saveAndReturnId(project);

        for(ProjectCreateRequestDTO.PartDTO partDto : request.getParts()){
            Part part = new Part();
            part.setProjectId(projectId);
            part.setTitle(partDto.getTitle());
            part.setDescription(partDto.getDescription());
            partRepository.save(part);
        }
    }

}
