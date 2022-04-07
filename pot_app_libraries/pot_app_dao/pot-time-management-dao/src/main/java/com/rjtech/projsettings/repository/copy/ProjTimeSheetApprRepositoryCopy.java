package com.rjtech.projsettings.repository.copy;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.TimeSheetAddtionalTimeApprEntity;

//import com.rjtech.timemanagement.proj.settings.model.TimeSheetAddtionalTimeApprEntityCopy;

public interface ProjTimeSheetApprRepositoryCopy extends JpaRepository<TimeSheetAddtionalTimeApprEntity, Long> {

    @Query("SELECT ta.timeSheetEndDate FROM TimeSheetAddtionalTimeApprEntity ta WHERE  ta.status=1 AND ta.latest=true AND "
            + "ta.timeSheetEndDate >= current_date() AND ta.projTimeSheetEntity.type=:submitType AND ta.projTimeSheetEntity.status=1 AND "
            + "ta.projTimeSheetEntity.projId.projectId=:projId ")
    public Date findTimesheetEndDate(@Param("projId") Long projId, @Param("submitType") String submitType);
}
