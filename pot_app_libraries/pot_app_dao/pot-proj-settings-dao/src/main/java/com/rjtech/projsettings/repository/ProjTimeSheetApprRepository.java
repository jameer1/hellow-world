package com.rjtech.projsettings.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.TimeSheetAddtionalTimeApprEntity;

public interface ProjTimeSheetApprRepository
        extends ProjSettingsBaseRepository<TimeSheetAddtionalTimeApprEntity, Long> {

    @Query("SELECT T FROM TimeSheetAddtionalTimeApprEntity T WHERE  T.status=1 AND T.latest=true AND T.projTimeSheetEntity.id=:typeId")
    public TimeSheetAddtionalTimeApprEntity findLatestApproval(@Param("typeId") Long typeId);

}
