package com.sipeip.service;

import com.sipeip.infrastructure.input.adapter.rest.models.ProjectPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectResultResponse;

public interface ProjectService {
    ProjectResultResponse createProject(ProjectRequest request);

    ProjectResultResponse updateProject(Integer id, ProjectRequest request);

    void deleteGoalById(Integer id);

    ProjectPagedResponse getPagedProjects(Integer page, Integer size);

    ProjectPagedResponse searchProjects(Integer page, Integer size, String name, String code, String type);
}
