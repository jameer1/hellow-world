package com.rjtech.timemanagement.workdairy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projsettings.model.WorkDairyAddtionalTimeApprEntity;
//import com.rjtech.timemanagement.proj.settings.model.WorkDairyAddtionalTimeApprEntityCopy;

public interface ProjWorkDairyApprRepositoryCopy
        extends JpaRepository<WorkDairyAddtionalTimeApprEntity, Long> {
    
    @Query("SELECT WA FROM WorkDairyAddtionalTimeApprEntity WA "
            + "WHERE WA.status = 1 "
            + "AND WA.projWorkDairyMstrEntity.apprType = :apprType "
            + "AND WA.projWorkDairyMstrEntity.status = 1 "
            + "AND WA.projWorkDairyMstrEntity.projId = :projId "
            + "AND WA.latest = true "
            + "AND WA.toDate >= trunc(sysdate)")
    public WorkDairyAddtionalTimeApprEntity getProjWorkDairyApprStatus(@Param("projId") ProjMstrEntity projId,
            @Param("apprType") String apprType);
}
