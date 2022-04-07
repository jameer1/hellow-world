package com.rjtech.projectlib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.common.model.ProjStatusDatesEntityCopy;




public interface ProjStatusDatesRepositoryCopy extends JpaRepository<ProjStatusDatesEntityCopy, Long> {
    
    @Query("SELECT T FROM ProjStatusDatesEntityCopy T WHERE T.projMstrEntity.projectId=:projId AND T.status=:status")
    public ProjStatusDatesEntityCopy findProjStatusDatesById(@Param("projId") Long projId,
            @Param("status") Integer status);
}
