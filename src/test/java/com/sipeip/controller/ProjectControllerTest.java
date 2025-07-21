package com.sipeip.controller;

import com.sipeip.infrastructure.input.adapter.rest.models.ProjectPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectResultResponse;
import com.sipeip.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProjectShouldReturnCreatedResponse() {
        ProjectRequest request = new ProjectRequest();
        ProjectResultResponse result = new ProjectResultResponse();
        when(projectService.createProject(request)).thenReturn(result);

        ResponseEntity<ProjectResultResponse> response = projectController.createProject(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(result);
        verify(projectService).createProject(request);
    }

    @Test
    void deactivateProjectShouldReturnNoContentResponse() {
        Integer projectId = 1;

        ResponseEntity<Void> response = projectController.deactivateProject(projectId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(projectService).deleteGoalById(projectId);
    }

    @Test
    void getPagedProjectsShouldReturnOkResponse() {
        int page = 0;
        int size = 10;
        ProjectPagedResponse pagedResponse = new ProjectPagedResponse();
        when(projectService.getPagedProjects(page, size)).thenReturn(pagedResponse);

        ResponseEntity<ProjectPagedResponse> response = projectController.getPagedProjects(page, size);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(pagedResponse);
        verify(projectService).getPagedProjects(page, size);
    }

    @Test
    void searchProjectsShouldReturnOkResponse() {
        int page = 0;
        int size = 10;
        String name = "Test";
        String code = "Code";
        String type = "Type";
        ProjectPagedResponse pagedResponse = new ProjectPagedResponse();
        when(projectService.searchProjects(page, size, name, code, type)).thenReturn(pagedResponse);

        ResponseEntity<ProjectPagedResponse> response = projectController.searchProjects(page, size, name, code, type);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(pagedResponse);
        verify(projectService).searchProjects(page, size, name, code, type);
    }

    @Test
    void updateProjectShouldReturnCreatedResponse() {
        Integer id = 1;
        ProjectRequest request = new ProjectRequest();
        ProjectResultResponse result = new ProjectResultResponse();
        when(projectService.updateProject(id, request)).thenReturn(result);

        ResponseEntity<ProjectResultResponse> response = projectController.updateProject(id, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(result);
        verify(projectService).updateProject(id, request);
    }
}
