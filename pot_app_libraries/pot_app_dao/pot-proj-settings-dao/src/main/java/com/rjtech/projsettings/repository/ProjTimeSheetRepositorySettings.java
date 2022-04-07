package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.TimesheetNormalTimeEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetAdditionalTimeEntity;

public interface ProjTimeSheetRepositorySettings extends ProjSettingsBaseRepository<TimesheetNormalTimeEntity, Long> {

    @Query("SELECT PTA FROM TimesheetNormalTimeEntity PTA WHERE (( :projId IS NULL AND PTA.projId IS NULL) OR PTA.projId.projectId=:projId ) AND PTA.status=:status ORDER BY  PTA.typeId ")
    public List<TimesheetNormalTimeEntity> findProjTimeSheet(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PTA FROM TimesheetNormalTimeEntity PTA WHERE PTA.isDefault='Y' AND PTA.projId IS NULL")
    public List<TimesheetNormalTimeEntity> findDefaultProjTimeSheet();
    
}
