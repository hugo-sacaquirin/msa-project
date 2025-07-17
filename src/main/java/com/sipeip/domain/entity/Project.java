package com.sipeip.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "proyectos")
@Builder // Lombok annotation to generate a builder for the class
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "codigo", length = 100, nullable = false, unique = true)
    private String code;

    @Column(name = "tipo_proyecto", length = 20, nullable = false)
    private String projectType;

    @Column(name = "periodo_ejecucion", length = 100, nullable = false)
    private String executionPeriod;

    @Column(name = "presupuesto_estimado", precision = 18, scale = 2, nullable = false)
    private BigDecimal estimatedBudget;

    @Column(name = "ubicacion_geografica", length = 255, nullable = false)
    private String geographicLocation;

    @Column(name = "estado", length = 10, nullable = false)
    private String status;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime updatedAt;
}
