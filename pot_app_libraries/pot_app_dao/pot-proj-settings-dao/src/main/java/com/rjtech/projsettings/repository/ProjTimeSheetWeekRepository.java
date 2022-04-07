package com.rjtech.projsettings.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjTimeSheetWeekDtlEntity;

public interface ProjTimeSheetWeekRepository extends ProjSettingsBaseRepository<ProjTimeSheetWeekDtlEntity, Long> {

    @Query("SELECT TWD FROM ProjTimeSheetWeekDtlEntity TWD WHERE   TWD.projMstrEntity.projectId=:projId  "
            + "AND TWD.status=:status AND TWD.isLatest='Y'")
    public ProjTimeSheetWeekDtlEntity findProjTimeSheetWeekDtl(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT TWD FROM ProjTimeSheetWeekDtlEntity TWD WHERE TWD.isDefault='Y' AND TWD.projMstrEntity IS NULL ")
    public ProjTimeSheetWeekDtlEntity findDefaultProjTimeSheetWeek();

}
