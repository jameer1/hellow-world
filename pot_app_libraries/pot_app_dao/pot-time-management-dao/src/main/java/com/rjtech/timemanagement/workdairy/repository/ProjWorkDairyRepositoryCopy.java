package com.rjtech.timemanagement.workdairy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projsettings.model.WorkDairyNormalTimeEntity;
//import com.rjtech.timemanagement.proj.settings.model.WorkDairyNormalTimeEntityCopy;

public interface ProjWorkDairyRepositoryCopy extends JpaRepository<WorkDairyNormalTimeEntity, Long> {
    
    @Query("SELECT WD FROM WorkDairyNormalTimeEntity WD "
            + "WHERE WD.projId = :projId "
            + "AND status = 1 "
            + "AND apprType = :apprType")
    public WorkDairyNormalTimeEntity getWorkDairyProjSettings(@Param("projId") ProjMstrEntity projId,
            @Param("apprType") String apprType);
}
