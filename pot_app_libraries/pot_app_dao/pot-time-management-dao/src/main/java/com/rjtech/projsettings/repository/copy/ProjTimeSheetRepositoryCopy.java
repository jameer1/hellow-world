package com.rjtech.projsettings.repository.copy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.TimesheetNormalTimeEntity;

//import com.rjtech.timemanagement.proj.settings.model.TimesheetNormalTimeEntityCopy;

public interface ProjTimeSheetRepositoryCopy extends JpaRepository<TimesheetNormalTimeEntity, Long> {

    @Query("SELECT pta FROM TimesheetNormalTimeEntity pta WHERE pta.type=:type AND pta.projId.projectId=:projId AND pta.status=1")
    public TimesheetNormalTimeEntity findCutOffDaysForProject(@Param("projId") Long projId,
            @Param("type") String submitType);
}
