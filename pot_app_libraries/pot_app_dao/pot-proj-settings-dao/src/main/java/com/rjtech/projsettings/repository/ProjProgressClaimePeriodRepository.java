package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProgressClaimPeriodCycleEntity;

public interface ProjProgressClaimePeriodRepository
        extends ProjSettingsBaseRepository<ProgressClaimPeriodCycleEntity, Long> {

    @Query("SELECT PCP FROM ProgressClaimPeriodCycleEntity PCP WHERE  PCP.projId.projectId=:projId AND PCP.status=:status")
    public List<ProgressClaimPeriodCycleEntity> findProjProgressClaimePeroid(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PCP FROM \r\n"
    		+ "com.rjtech.projsettings.model.ProgressClaimPeriodCycleEntity PCP WHERE  PCP.isDefault='Y' AND PCP.projId IS NULL ")
    public List<ProgressClaimPeriodCycleEntity> findDefaultProjProgressClaimePeriods();

}
