package com.example.portfolioback.controller;

import com.example.portfolioback.dto.ProjectCreateRequestDTO;
import com.example.portfolioback.service.ProjectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //REST 컨트롤러임을 나타내는 애너테이션
@RequestMapping("/api/project") //모든 HTTP 메서드의 요청을 매핑하기 위한 애너테이션.
public class ProjectController {

    private final ProjectService projectService;

    //생성자 @RequiredArgsConstructor로 대신해도 됨. 근데 나중에 바꾸기!
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public void createProject(@RequestBody ProjectCreateRequestDTO request){
        projectService.createProjectWithPart(request);
    }
}
