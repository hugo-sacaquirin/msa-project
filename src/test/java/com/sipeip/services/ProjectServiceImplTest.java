package com.sipeip.services;

import com.sipeip.domain.entity.Project;
import com.sipeip.domain.entity.ProjectGoals;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectResultResponse;
import com.sipeip.repository.ProjectGoalsRepository;
import com.sipeip.repository.ProjectRepository;
import com.sipeip.service.impl.ProjectServiceImpl;
import com.sipeip.service.mapper.ProjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectGoalsRepository projectGoalsRepository;
    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProjectShouldReturnSuccessResponse() {
        ProjectRequest request = new ProjectRequest();
        request.setName("Project A");
        request.setCode("CODE1");
        request.setProjectType("Type1");
        request.setExecutionPeriod("2025");
        request.setEstimatedBudget(1000.0);
        request.setGeographicLocation("Location");
        request.setStatus("ACTIVE");
        request.setGoalIds(List.of(1, 2));

        Project savedProject = Project.builder()
                .id(10)
                .name(request.getName())
                .code(request.getCode())
                .projectType(request.getProjectType())
                .executionPeriod(request.getExecutionPeriod())
                .estimatedBudget(BigDecimal.valueOf(request.getEstimatedBudget()))
                .geographicLocation(request.getGeographicLocation())
                .status(request.getStatus())
                .build();

        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        ProjectResultResponse response = projectService.createProject(request);

        assertThat(response.getResult()).isEqualTo("Project created successfully");
        verify(projectRepository).save(any(Project.class));
        verify(projectGoalsRepository, times(2)).save(any(ProjectGoals.class));
    }

    @Test
    void createProjectShouldThrowExceptionIfIdIsNull() {
        ProjectRequest request = new ProjectRequest();
        request.setEstimatedBudget(200.0);
        when(projectRepository.save(any(Project.class))).thenReturn(Project.builder().id(null).build());

        assertThatThrownBy(() -> projectService.createProject(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Error creating project");
    }

    @Test
    void updateProjectShouldReturnSuccessResponse() {
        ProjectRequest request = new ProjectRequest();
        request.setName("Updated");
        request.setCode("UPD");
        request.setProjectType("TypeX");
        request.setExecutionPeriod("2026");
        request.setEstimatedBudget(2000.0);
        request.setGeographicLocation("Loc2");
        request.setStatus("ACTIVE");

        Project savedProject = Project.builder().id(1).build();
        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        ProjectResultResponse response = projectService.updateProject(1, request);

        assertThat(response.getResult()).isEqualTo("Project updated successfully");
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void updateProjectShouldThrowExceptionIfIdIsNull() {
        ProjectRequest request = new ProjectRequest();
        request.setEstimatedBudget(200.0);
        when(projectRepository.save(any(Project.class))).thenReturn(Project.builder().id(null).build());

        assertThatThrownBy(() -> projectService.updateProject(1, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Error updating project");
    }

    @Test
    void deleteGoalByIdShouldDeleteProject() {
        Integer id = 100;
        doNothing().when(projectRepository).deleteById(id);
        when(projectRepository.existsById(id)).thenReturn(false);

        projectService.deleteGoalById(id);

        verify(projectRepository).deleteById(id);
        verify(projectRepository).existsById(id);
    }

    @Test
    void deleteGoalByIdShouldThrowExceptionIfStillExists() {
        Integer id = 200;
        doNothing().when(projectRepository).deleteById(id);
        when(projectRepository.existsById(id)).thenReturn(true);

        assertThatThrownBy(() -> projectService.deleteGoalById(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Error deleting entity");
    }

    @Test
    void getPagedProjectsShouldReturnPagedResponse() {
        when(projectRepository.findAll()).thenReturn(List.of());
        when(projectMapper.toGoalResponseFromGoal(anyList())).thenReturn(List.of());

        ProjectPagedResponse response = projectService.getPagedProjects(0, 10);

        assertThat(response).isNotNull();
        verify(projectRepository).findAll();
        verify(projectMapper).toGoalResponseFromGoal(anyList());
    }

    @Test
    void searchProjectsShouldReturnByNameWhenTypeIsZero() {
        when(projectRepository.findByName("Test")).thenReturn(List.of());
        when(projectMapper.toGoalResponseFromGoal(anyList())).thenReturn(List.of());

        ProjectPagedResponse response = projectService.searchProjects(0, 10, "Test", "C", "0");

        assertThat(response).isNotNull();
        verify(projectRepository).findByName("Test");
    }

    @Test
    void searchProjectsShouldReturnByCodeWhenTypeIsNotZero() {
        when(projectRepository.findByCode("C")).thenReturn(List.of());
        when(projectMapper.toGoalResponseFromGoal(anyList())).thenReturn(List.of());

        ProjectPagedResponse response = projectService.searchProjects(0, 10, "Test", "C", "1");

        assertThat(response).isNotNull();
        verify(projectRepository).findByCode("C");
    }
}
