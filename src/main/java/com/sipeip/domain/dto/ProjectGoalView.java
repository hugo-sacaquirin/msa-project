package com.sipeip.domain.dto;
public interface ProjectGoalView {
    Integer getProjectId();
    String getProjectName();
    String getProjectCode();
    String getProjectType();
    String getExecutionPeriod();
    java.math.BigDecimal getEstimatedBudget();
    String getGeographicLocation();
    String getProjectStatus();

    Integer getGoalId();
    String getGoalName();
    String getGoalDescription();
    String getGoalPeriod();
    String getGoalUnitOfMeasure();
    java.math.BigDecimal getGoalExpectedValue();
    String getGoalResponsible();
    String getGoalStatus();
}
