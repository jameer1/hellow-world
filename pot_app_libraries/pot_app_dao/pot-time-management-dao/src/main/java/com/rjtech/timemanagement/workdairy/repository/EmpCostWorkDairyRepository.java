package com.rjtech.timemanagement.workdairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpCostEntity;

@Repository
public interface EmpCostWorkDairyRepository extends JpaRepository<WorkDairyEmpCostEntity, Long> {

    @Modifying
    @Query("DELETE FROM  WorkDairyEmpCostEntity T  where T.workDairyId.id=:workDairyId AND T.costId.id in :deleteIds")
    void deleteEmpCostCodes(@Param("workDairyId") Long workDairyId, @Param("deleteIds") List<Long> deleteIds);

    @Query("SELECT T FROM WorkDairyEmpCostEntity T  WHERE T.workDairyId.id=:workDairyId AND T.status=:status and T.isLatest= true")
    List<WorkDairyEmpCostEntity> findWorkDairyEmpTimeCost(@Param("workDairyId") Long workDairyId,
            @Param("status") Integer status);

    @Query("SELECT T FROM WorkDairyEmpCostEntity T  WHERE T.costId.id IN :costIds AND T.workDairyEmpWageEntity.id = :parentId "
            + "AND T.isLatest= true AND ((T.workDairyId.clientApproval=0 AND lower(T.apprStatus)='approved' AND "
            + "lower(T.workDairyId.apprStatus)='approved') OR "
            + "(T.workDairyId.clientApproval=1 AND lower(T.apprStatus)='client approved' AND "
            + "lower(T.workDairyId.apprStatus)='client approved'))")
    List<WorkDairyEmpCostEntity> getApprovedWorkDairyEmpTimeCostByCostId(@Param("costIds") List<Long> costIds,
            @Param("parentId") Long workDairyEmpWageId);

    @Query("SELECT T FROM WorkDairyEmpCostEntity T WHERE T.workDairyEmpWageEntity.id = :parentId "
            + "AND T.isLatest= true AND ((T.workDairyId.clientApproval=0 AND lower(T.apprStatus)='approved' AND "
            + "lower(T.workDairyId.apprStatus)='approved') OR "
            + "(T.workDairyId.clientApproval=1 AND lower(T.apprStatus)='client approved' AND "
            + "lower(T.workDairyId.apprStatus)='client approved'))")
    List<WorkDairyEmpCostEntity> getApprovedWorkDairyEmpTime(@Param("parentId") Long workDairyEmpWageId);
}
