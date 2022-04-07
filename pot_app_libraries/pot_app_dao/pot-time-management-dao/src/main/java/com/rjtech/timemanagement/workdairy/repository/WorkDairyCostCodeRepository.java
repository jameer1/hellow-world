package com.rjtech.timemanagement.workdairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.timemanagement.workdairy.model.WorkDairyCostCodeEntity;

@Repository
public interface WorkDairyCostCodeRepository extends JpaRepository<WorkDairyCostCodeEntity, Long> {

    @Query("SELECT T FROM WorkDairyCostCodeEntity T  WHERE T.workDairyEntity.id=:workDairyId AND T.status=:status")
    List<WorkDairyCostCodeEntity> findWorkDairyCostCodes(@Param("workDairyId") Long workDairyId,
            @Param("status") Integer status);
    
    @Query("SELECT T FROM WorkDairyCostCodeEntity T  WHERE T.workDairyEntity.id=:workDairyId AND "
            + "T.status = 1 AND T.costId.id = :costCodeId")
    WorkDairyCostCodeEntity findWorkDairyCode(@Param("workDairyId") Long workDairyId, 
            @Param("costCodeId") Long costCodeId);
    
    @Query("SELECT T FROM WorkDairyCostCodeEntity T  WHERE T.projId = :projId AND T.crewId = :crew "
            + "AND T.workDairyEntity.workDairyDate = (SELECT MAX(WD.workDairyDate) FROM WorkDairyEntity WD)")
    List<WorkDairyCostCodeEntity> findWorkDairyCostCodesByCrewAndProj(@Param("projId") ProjMstrEntity projId,
            @Param("crew") ProjCrewMstrEntity crew);

    @Modifying
    @Query("DELETE FROM  WorkDairyCostCodeEntity T  where T.workDairyEntity.id=:workDairyId AND T.costId.id in :deleteIds")
    void deleteWorkdairyCostCodes(@Param("workDairyId") Long workDairyId, @Param("deleteIds") List<Long> deleteIds);

}