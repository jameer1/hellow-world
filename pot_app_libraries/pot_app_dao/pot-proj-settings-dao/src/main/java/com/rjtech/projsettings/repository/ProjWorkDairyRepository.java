package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.WorkDairyNormalTimeEntity;

public interface ProjWorkDairyRepository extends ProjSettingsBaseRepository<WorkDairyNormalTimeEntity, Long> {

    @Query("SELECT PJA FROM WorkDairyNormalTimeEntity PJA WHERE  (( :projId IS NULL AND PJA.projId IS NULL) OR PJA.projId.projectId=:projId ) "
            + "AND PJA.status=:status ORDER BY PJA.apprTypeId")
    public List<WorkDairyNormalTimeEntity> findProjWorkDairy(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PJA FROM WorkDairyNormalTimeEntity PJA WHERE PJA.isDefault='Y' AND PJA.projId IS NULL ")
    public List<WorkDairyNormalTimeEntity> findDefaultProjWorkDairy();

}
