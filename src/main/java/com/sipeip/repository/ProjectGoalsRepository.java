package com.sipeip.repository;

import com.sipeip.domain.dto.ProjectGoalView;
import com.sipeip.domain.entity.ProjectGoals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectGoalsRepository extends JpaRepository<ProjectGoals, Integer> {
    @Query(value = """
    SELECT
        p.id_proyecto AS projectId,
        p.nombre AS projectName,
        p.codigo AS projectCode,
        p.tipo_proyecto AS projectType,
        p.periodo_ejecucion AS executionPeriod,
        p.presupuesto_estimado AS estimatedBudget,
        p.ubicacion_geografica AS geographicLocation,
        p.estado AS projectStatus,
        m.id_meta AS goalId,
        m.nombre AS goalName,
        m.descripcion AS goalDescription,
        m.periodo AS goalPeriod,
        m.unidad_medida AS goalUnitOfMeasure,
        m.valor_esperado AS goalExpectedValue,
        m.responsable AS goalResponsible,
        m.estado AS goalStatus
    FROM proyectos p
    JOIN proyecto_metas pm ON p.id_proyecto = pm.id_proyecto
    JOIN metas m ON pm.id_meta = m.id_meta
    WHERE p.id_proyecto = :projectId
    ORDER BY m.nombre ASC
    """, nativeQuery = true)
    List<ProjectGoalView> findProjectWithGoalsByProjectId(@Param("projectId") Integer projectId);
}