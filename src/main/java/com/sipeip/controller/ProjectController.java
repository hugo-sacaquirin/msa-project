package com.sipeip.controller;


import com.sipeip.infrastructure.input.adapter.rest.ProjectsApi;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectResultResponse;
import com.sipeip.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectController implements ProjectsApi {
    private final ProjectService projectService;

    @Override
    public ResponseEntity<ProjectResultResponse> createProject(ProjectRequest projectRequest) {
        return new ResponseEntity<>(projectService.createProject(projectRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deactivateProject(Integer id) {
        projectService.deleteGoalById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ProjectPagedResponse> getPagedProjects(Integer page, Integer size) {
        return new ResponseEntity<>(projectService.getPagedProjects(page, size), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectPagedResponse> searchProjects(Integer page, Integer size, String name, String code, String type) {
        return new ResponseEntity<>(projectService.searchProjects(page, size, name, code, type), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectResultResponse> updateProject(Integer id, ProjectRequest projectRequest) {
        return new ResponseEntity<>(projectService.updateProject(id, projectRequest), HttpStatus.CREATED);
    }
}
