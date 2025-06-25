package com.example.portfolioback.controller;

import com.example.portfolioback.dto.ProjectCreateRequestDTO;
import com.example.portfolioback.dto.ProjectEditWithPATCHRequest;
import com.example.portfolioback.dto.ProjectEditWithPUTRequest;
import com.example.portfolioback.service.ProjectService;
import org.springframework.web.bind.annotation.*;

@RestController //REST 컨트롤러임을 나타내는 애너테이션
@RequestMapping("/api/project") //모든 HTTP 메서드의 요청을 매핑하기 위한 애너테이션.
public class ProjectController {

    private final ProjectService projectService;

    //생성자 @RequiredArgsConstructor로 대신해도 됨. 근데 나중에 바꾸기!
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    //프로젝트 정보 생성
    @PostMapping("/create")
    public void createProject(@RequestBody ProjectCreateRequestDTO request){
        projectService.createProjectWithPart(request);
    }

    //프로젝트 정보 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
    }

    //프로젝트 정보 수정(PUT)
    @PutMapping("/edit/put/{id}")
    public void editProjectwithPUT(@PathVariable Long id, @RequestBody ProjectEditWithPUTRequest requestDto){
        projectService.editProjectwithPUT(id, requestDto);
    }

    //프로젝트 정보 수정(PATCH)
    @PatchMapping("/edit/patch/{id}")
    public void editProjectwithPATCH(@PathVariable Long id, @RequestBody ProjectEditWithPATCHRequest requestDto){
        projectService.editProjectwithPATCH(id, requestDto);
    }


}

