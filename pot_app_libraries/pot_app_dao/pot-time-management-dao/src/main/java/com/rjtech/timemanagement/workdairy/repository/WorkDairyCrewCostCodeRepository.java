package com.rjtech.timemanagement.workdairy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyCrewCostCodeEntity;

@Repository
public interface WorkDairyCrewCostCodeRepository extends JpaRepository<WorkDairyCrewCostCodeEntity, Long> {

    @Query("SELECT T FROM WorkDairyCrewCostCodeEntity T  WHERE T.projId.projectId =:projId AND T.crewId.id =:crewId AND T.status=:status")
    List<WorkDairyCrewCostCodeEntity> findWorkDairyCostCodes(@Param("projId") Long projId, @Param("crewId") Long crewId,
            @Param("status") Integer status);

    @Query("SELECT T FROM WorkDairyCrewCostCodeEntity T  WHERE T.projId.projectId =:projId AND T.crewId.id =:crewId AND T.status=:status AND T.createdOn= "
            + "(SELECT  MAX(cc.createdOn) from WorkDairyCrewCostCodeEntity cc WHERE cc.projId.projectId=:projId AND cc.crewId.id=:crewId)")
    List<WorkDairyCrewCostCodeEntity> findRecentWorkDairyCostCodes(@Param("projId") Long projId,
            @Param("crewId") Long crewId, @Param("status") Integer status);

    @Query("SELECT T FROM WorkDairyCrewCostCodeEntity T  WHERE T.projId.projectId=:projId AND T.crewId.id=:crewId AND T.status=:status AND "
            + "T.createdOn= :searchDate")
    List<WorkDairyCrewCostCodeEntity> findWorkDairyCostCodesByDate(@Param("projId") Long projId,
            @Param("crewId") Long crewId, @Param("status") Integer status, @Param("searchDate") Date searchDate);

    void deleteByIdIn(List<Long> ids);
}