package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjectPlantsDtlEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

//import com.rjtech.projectlib.model.ProjectPlantsDtlEntityCopy;

//This is the copy of ProjectPlantsRepository present in projectsettings
public interface ProjectPlantsRepositoryCopy extends JpaRepository<ProjectPlantsDtlEntity, Long> {

}
