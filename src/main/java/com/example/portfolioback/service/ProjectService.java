package com.example.portfolioback.service;

import com.example.portfolioback.dto.ProjectCreateRequestDTO;
import com.example.portfolioback.dto.ProjectEditWithPATCHRequest;
import com.example.portfolioback.dto.ProjectEditWithPUTRequest;
import com.example.portfolioback.dto.ProjectResponseDTO;
import com.example.portfolioback.entity.Part;
import com.example.portfolioback.entity.Project;
import com.example.portfolioback.repository.PartRepository;
import com.example.portfolioback.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //@Service 애너테이션으로 비즈니스 로직을 처리하는 서비스임을 나타내는 애너테이션이다.
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PartRepository partRepository;

    //생성자
    public ProjectService(ProjectRepository projectRepository, PartRepository partRepository){
        this.projectRepository = projectRepository;
        this.partRepository = partRepository;
    }

    @Transactional //이 메서드 전체가 하나의 트랜잭션으로 이루어진다.
    public void createProjectWithPart(ProjectCreateRequestDTO request){
        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
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

    @Transactional
    public void deleteProject(Long id){
        projectRepository.deleteById(id);

        partRepository.deleteByProjectId(id);
    }

    //전체 수정 with PUT
    @Transactional
    public void editProjectwithPUT(Long projectId, ProjectEditWithPUTRequest requestDto){
        projectRepository.updateByPut(
                projectId,
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getStartDate(),
                requestDto.getEndDate()
        );
    }

    //부분 수정 with PATCH
    @Transactional
    public void editProjectwithPATCH(Long projectId, ProjectEditWithPATCHRequest requestDto){
        projectRepository.updateByPatch(
                projectId,
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getStartDate(),
                requestDto.getEndDate()
        );
    }

    //프로젝트 가져오기
    @Transactional(readOnly = true)
    public ProjectResponseDTO fetchProject(Long id) {
        Project project = projectRepository.findById(id);
        List<Part> parts = partRepository.findByProjectId(id);

        List<ProjectResponseDTO.PartDTO> partDTOs = parts.stream()
                .map(part -> new ProjectResponseDTO.PartDTO(part.getTitle(), part.getDescription()))
                .toList();

        return new ProjectResponseDTO(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                partDTOs
        );
    }

}
