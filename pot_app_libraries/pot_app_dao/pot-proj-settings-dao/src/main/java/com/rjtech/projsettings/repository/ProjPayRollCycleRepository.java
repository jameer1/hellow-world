package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjPayRollCycleEntity;

public interface ProjPayRollCycleRepository extends ProjSettingsBaseRepository<ProjPayRollCycleEntity, Long> {

    @Query("SELECT PJI FROM ProjPayRollCycleEntity PJI WHERE PJI.projMstrEntity.projectId=:projId AND PJI.status=:status")
    public List<ProjPayRollCycleEntity> findProjPayRollCycle(@Param("projId") Long projId,
            @Param("status") Integer status);
}
