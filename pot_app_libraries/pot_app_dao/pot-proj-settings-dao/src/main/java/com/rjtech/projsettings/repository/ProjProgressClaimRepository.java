package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProgressClaimNormalTimeEntity;

public interface ProjProgressClaimRepository extends ProjSettingsBaseRepository<ProgressClaimNormalTimeEntity, Long> {

    @Query("SELECT PGC FROM ProgressClaimNormalTimeEntity PGC WHERE  PGC.projId.projectId=:projId AND PGC.status=:status")
    public List<ProgressClaimNormalTimeEntity> findProjProgressClaim(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PGC FROM com.rjtech.projsettings.model.ProgressClaimNormalTimeEntity PGC WHERE  PGC.isDefault='Y' AND PGC.projId IS NULL")
    public List<ProgressClaimNormalTimeEntity> findDefaultProjProgressClaim();

}
