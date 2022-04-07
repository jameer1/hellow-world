package com.rjtech.projsettings.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.WorkDairyAddtionalTimeApprEntity;

public interface ProjWorkDairyApprRepository
        extends ProjSettingsBaseRepository<WorkDairyAddtionalTimeApprEntity, Long> {

    @Query("SELECT T FROM WorkDairyAddtionalTimeApprEntity T WHERE  T.status=1 AND  T.latest=true AND T.projWorkDairyMstrEntity=:typeId")
    public WorkDairyAddtionalTimeApprEntity findLatestApproval(@Param("typeId") Long typeId);

}
