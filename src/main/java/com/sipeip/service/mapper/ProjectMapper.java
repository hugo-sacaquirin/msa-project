package com.sipeip.service.mapper;


import com.sipeip.domain.dto.ProjectGoalView;
import com.sipeip.domain.entity.Project;
import com.sipeip.infrastructure.input.adapter.rest.models.GoalResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectResponse;
import com.sipeip.repository.ProjectGoalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectMapper {
    private final ProjectGoalsRepository projectGoalsRepository;

    public ProjectResponse toGoalResponse(Project project) {
        if (project == null) {
            return null;
        }
        ProjectResponse goalResponse = new ProjectResponse();
        goalResponse.setId(project.getId());
        goalResponse.setName(project.getName());
        goalResponse.setCode(project.getCode());
        goalResponse.setProjectType(project.getProjectType());
        goalResponse.setExecutionPeriod(project.getExecutionPeriod());
        goalResponse.setEstimatedBudget(project.getEstimatedBudget());
        goalResponse.setGeographicLocation(project.getGeographicLocation());
        goalResponse.setStatus(project.getStatus());
        goalResponse.setCreatedAt(String.valueOf(project.getCreatedAt()));
        goalResponse.setUpdatedAt(String.valueOf(project.getUpdatedAt()));
        List<GoalResponse> goalResponseList = new ArrayList<>();
        for (ProjectGoalView projectGoalView : projectGoalsRepository.findProjectWithGoalsByProjectId(goalResponse.getId())) {
            GoalResponse response = new GoalResponse();
            response.setId(projectGoalView.getGoalId());
            response.setName(projectGoalView.getGoalName());
            response.setDescription(projectGoalView.getGoalDescription());
            goalResponseList.add(response);
        }
        goalResponse.setGoals(goalResponseList);
        return goalResponse;
    }

    public List<ProjectResponse> toGoalResponseFromGoal(List<Project> entitiesList) {
        return entitiesList.stream()
                .map(this::toGoalResponse)
                .toList();
    }
}