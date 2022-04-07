package com.rjtech.timemanagement.workdairy.repository.copy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.timemanagement.workdairy.model.WorkDairyProgressDtlEntity;

//import com.rjtech.workdairy.WorkDairyProgressDtlEntityCopy;

public interface ProgressWorkDairyRepositoryCopy extends JpaRepository<WorkDairyProgressDtlEntity, Long>{
   
    @Query("SELECT T.workDairyId.projId.projectId , T.workDairyId.workDairyDate, "
            + "SUM((T.sowId.projSORItemEntity.manPowerHrs / T.sowId.projSORItemEntity.quantity) * T.value) FROM "
            + "WorkDairyProgressDtlEntity T WHERE lower(T.apprStatus) ='approved' AND T.workDairyId.projId.projectId IN :projIds "
            + " AND T.sowId.projSORItemEntity.quantity !=0 "
            + "GROUP BY T.workDairyId.projId.projectId , T.workDairyId.workDairyDate")
    List<Object[]> getManpowerEarnedValues(@Param("projIds") List<Long> projIds);
    
    @Query("SELECT T FROM WorkDairyProgressDtlEntity T WHERE T.workDairyId.id=:workDairyId AND T.status=:status")
    List<WorkDairyProgressDtlEntity> findWorkDairyProgressDetails(@Param("workDairyId") Long workDairyId, @Param("status") Integer status);
    
    @Query("SELECT T FROM WorkDairyProgressDtlEntity T WHERE "
    		+ "T.workDairyId.projId.projectId IN :projIds "
    		+ "AND T.sowId.tangibleClassificationEntity.id IS NOT NULL")
    List<WorkDairyProgressDtlEntity> findAllTangibles(@Param("projIds") List<Long> projIds);
}
