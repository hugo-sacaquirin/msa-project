package com.sipeip.services;

import com.sipeip.domain.dto.ProjectGoalView;
import com.sipeip.domain.entity.Project;
import com.sipeip.infrastructure.input.adapter.rest.models.GoalResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectResponse;
import com.sipeip.repository.ProjectGoalsRepository;
import com.sipeip.service.mapper.ProjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProjectMapperTest {

    @Mock
    private ProjectGoalsRepository projectGoalsRepository;

    @InjectMocks
    private ProjectMapper projectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toGoalResponseReturnsNullWhenProjectIsNull() {
        ProjectResponse response = projectMapper.toGoalResponse(null);
        assertThat(response).isNull();
    }

    @Test
    void toGoalResponseReturnsCorrectResponse() {
        Project project = Project.builder()
                .id(1)
                .name("Project A")
                .code("P001")
                .projectType("Type1")
                .executionPeriod("2024-2025")
                .estimatedBudget(BigDecimal.valueOf(1000))
                .geographicLocation("Loc1")
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ProjectGoalView view1 = mock(ProjectGoalView.class);
        when(view1.getGoalId()).thenReturn(10);
        when(view1.getGoalName()).thenReturn("Goal X");
        when(view1.getGoalDescription()).thenReturn("Description X");

        ProjectGoalView view2 = mock(ProjectGoalView.class);
        when(view2.getGoalId()).thenReturn(11);
        when(view2.getGoalName()).thenReturn("Goal Y");
        when(view2.getGoalDescription()).thenReturn("Description Y");

        List<ProjectGoalView> goalViews = Arrays.asList(view1, view2);

        when(projectGoalsRepository.findProjectWithGoalsByProjectId(1)).thenReturn(goalViews);

        ProjectResponse response = projectMapper.toGoalResponse(project);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getName()).isEqualTo("Project A");
        assertThat(response.getGoals()).hasSize(2);

        GoalResponse goal1 = response.getGoals().get(0);
        assertThat(goal1.getId()).isEqualTo(10);
        assertThat(goal1.getName()).isEqualTo("Goal X");
        assertThat(goal1.getDescription()).isEqualTo("Description X");

        GoalResponse goal2 = response.getGoals().get(1);
        assertThat(goal2.getId()).isEqualTo(11);
        assertThat(goal2.getName()).isEqualTo("Goal Y");
        assertThat(goal2.getDescription()).isEqualTo("Description Y");

        verify(projectGoalsRepository).findProjectWithGoalsByProjectId(1);
    }

    @Test
    void toGoalResponseHandlesNoGoals() {
        Project project = Project.builder().id(2).build();
        when(projectGoalsRepository.findProjectWithGoalsByProjectId(2)).thenReturn(Collections.emptyList());

        ProjectResponse response = projectMapper.toGoalResponse(project);

        assertThat(response).isNotNull();
        assertThat(response.getGoals()).isEmpty();
        verify(projectGoalsRepository).findProjectWithGoalsByProjectId(2);
    }

    @Test
    void toGoalResponseFromGoalMapsAllProjects() {
        Project project1 = Project.builder().id(1).name("A").build();
        Project project2 = Project.builder().id(2).name("B").build();

        when(projectGoalsRepository.findProjectWithGoalsByProjectId(anyInt())).thenReturn(Collections.emptyList());

        List<ProjectResponse> responses = projectMapper.toGoalResponseFromGoal(Arrays.asList(project1, project2));

        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getName()).isEqualTo("A");
        assertThat(responses.get(1).getName()).isEqualTo("B");
    }
}
