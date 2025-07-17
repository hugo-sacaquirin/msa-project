package com.sipeip.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proyecto_metas")
@Builder // Lombok annotation to generate a builder for the class
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGoals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto_metas")
    private Integer id;
    @Column(name = "id_proyecto")
    private Integer projectId;
    @Column(name = "id_meta")
    private Integer goalId;
}