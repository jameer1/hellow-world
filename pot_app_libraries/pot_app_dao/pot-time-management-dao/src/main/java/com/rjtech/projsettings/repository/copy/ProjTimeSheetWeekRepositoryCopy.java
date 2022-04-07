package com.rjtech.projsettings.repository.copy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjTimeSheetWeekDtlEntity;

//import com.rjtech.timemanagement.proj.settings.model.ProjTimeSheetWeekDtlEntityCopy;

public interface ProjTimeSheetWeekRepositoryCopy extends JpaRepository<ProjTimeSheetWeekDtlEntity, Long> {

    @Query("SELECT DISTINCT TS.id, TS.weekStartDay, TS.weekEndDay  FROM ProjTimeSheetWeekDtlEntity TS "
            + "WHERE TS.projMstrEntity.projectId = :projId AND TS.isLatest='Y' AND TS.status= 1")
    Object[] getTimesheetWeekDetails(@Param("projId") Long projId);

}
