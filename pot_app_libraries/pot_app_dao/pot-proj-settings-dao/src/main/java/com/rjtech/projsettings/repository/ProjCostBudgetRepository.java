package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjCostBudgetEntity;
import com.rjtech.projsettings.model.ProjectPlantsDtlEntity;

public interface ProjCostBudgetRepository extends ProjSettingsBaseRepository<ProjCostBudgetEntity, Long> {

    @Query("SELECT PJP FROM ProjectPlantsDtlEntity PJP WHERE  PJP.projMstrEntity.projectId=:projId AND PJP.status=:status")
    public List<ProjectPlantsDtlEntity> findProjectCostCPlants(@Param("projId") Long projId,
            @Param("status") Integer status);

}
