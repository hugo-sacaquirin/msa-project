package com.sipeip.service.impl;

import com.sipeip.domain.entity.Project;
import com.sipeip.domain.entity.ProjectGoals;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectResultResponse;
import com.sipeip.repository.ProjectGoalsRepository;
import com.sipeip.repository.ProjectRepository;
import com.sipeip.service.ProjectService;
import com.sipeip.service.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static com.sipeip.util.StaticValues.CREATED;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectGoalsRepository projectGoalsRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResultResponse createProject(ProjectRequest request) {
        Project project = projectRepository.save(Project.builder()
                .name(request.getName())
                .code(request.getCode())
                .projectType(request.getProjectType())
                .executionPeriod(request.getExecutionPeriod())
                .estimatedBudget(BigDecimal.valueOf(request.getEstimatedBudget()))
                .geographicLocation(request.getGeographicLocation())
                .status(request.getStatus())
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .build());
        if (project.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating project");
        }
        for (Integer idGoals : request.getGoalIds()) {
            projectGoalsRepository.save(ProjectGoals.builder().projectId(project.getId()).goalId(idGoals).build());
        }
        return getGoalResultResponse("Project created successfully");
    }

    @Override
    public ProjectResultResponse updateProject(Integer id, ProjectRequest request) {
        Project project = projectRepository.save(Project.builder()
                .id(id)
                .name(request.getName())
                .code(request.getCode())
                .projectType(request.getProjectType())
                .executionPeriod(request.getExecutionPeriod())
                .estimatedBudget(BigDecimal.valueOf(request.getEstimatedBudget()))
                .geographicLocation(request.getGeographicLocation())
                .status(request.getStatus())
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .build());
        if (project.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating project");
        }
        return getGoalResultResponse("Project updated successfully");
    }

    private static ProjectResultResponse getGoalResultResponse(String message) {
        ProjectResultResponse entityResultResponse = new ProjectResultResponse();
        entityResultResponse.setCode(CREATED);
        entityResultResponse.setResult(message);
        return entityResultResponse;
    }

    @Override
    public void deleteGoalById(Integer id) {
        projectRepository.deleteById(id);
        if (projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error deleting entity");
        }
    }

    @Override
    public ProjectPagedResponse getPagedProjects(Integer page, Integer size) {
        ProjectPagedResponse entityPagedResponse = new ProjectPagedResponse();
        entityPagedResponse.setContent(projectMapper.toGoalResponseFromGoal(projectRepository.findAll()));
        return entityPagedResponse;
    }

    @Override
    public ProjectPagedResponse searchProjects(Integer page, Integer size, String name, String code, String type) {
        ProjectPagedResponse entityPagedResponse = new ProjectPagedResponse();
        if (type.equals("0")) {
            entityPagedResponse.setContent(projectMapper.toGoalResponseFromGoal(projectRepository.findByName(name)));
        } else {
            entityPagedResponse.setContent(projectMapper.toGoalResponseFromGoal(projectRepository.findByCode(code)));

        }
        return entityPagedResponse;
    }
}
