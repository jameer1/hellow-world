package com.rjtech.timemanagement.workdairy.repository.copy;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpCostEntity;

//import com.rjtech.workdairy.WorkDairyEmpCostEntityCopy;

@Repository
public interface EmpCostWorkDairyRepositoryCopy extends JpaRepository<WorkDairyEmpCostEntity, Long> {

    @Query("SELECT WMTC.workDairyEmpWageEntity.workDairyEmpDtlEntity.empRegId.id, WMTC.costId.id, "
            + " WMTC.workDairyEmpWageEntity.wageId.wageFactor, WMTC.usedTime, WMTC.idleTime, WMTC.workDairyId.workDairyDate"
            + " FROM WorkDairyEmpCostEntity WMTC "
            + " WHERE WMTC.workDairyEmpWageEntity.workDairyEmpDtlEntity.empRegId.id in (:empIds) and "
            + " ((WMTC.workDairyEmpWageEntity.apprStatus = 'Approved' AND WMTC.workDairyId.clientApproval = 0) "
            + " OR (WMTC.workDairyEmpWageEntity.apprStatus = 'Client Approved' and WMTC.workDairyId.clientApproval=1))")
    public List<Object[]> getManpowerWorkDiaryActualHrs(@Param("empIds") Set<Long> empIds);

    @Query("SELECT WMTC.workDairyEmpWageEntity.workDairyEmpDtlEntity.empRegId, WMTC.costId, WMTC.workDairyEmpWageEntity.wageId.wageFactor, "
            + " WMTC.usedTime, WMTC.idleTime, WMTC.workDairyId.workDairyDate, WMTC.workDairyId.projId, PEC.projEmpCategory "
            + " FROM WorkDairyEmpCostEntity WMTC "
            + " LEFT JOIN ProjEmpClassMstrEntity PEC ON PEC.empClassMstrEntity = WMTC.workDairyEmpWageEntity.workDairyEmpDtlEntity.empRegId.empClassMstrEntity "
            + " AND PEC.projectId = WMTC.workDairyId.projId "
            + " WHERE WMTC.workDairyId.projId.projectId in (:projIds) AND lower(PEC.projEmpCategory) in (:empCats) AND "
            + " WMTC.workDairyEmpWageEntity.workDairyEmpDtlEntity.empRegId.companyMstrEntity.id in (:cmpIds) AND "
            + " WMTC.costId.id in (:costIds) AND WMTC.workDairyId.workDairyDate BETWEEN :fromDate and :toDate AND "
            + " ((WMTC.workDairyEmpWageEntity.apprStatus = 'Approved' AND WMTC.workDairyId.clientApproval = 0) "
            + " OR (WMTC.workDairyEmpWageEntity.apprStatus = 'Client Approved' and WMTC.workDairyId.clientApproval=1)) order by WMTC.workDairyId.workDairyDate ")
    public List<Object[]> getManpowerWorkDiaryActualHrs(@Param("projIds") List<Long> projIds,
            @Param("empCats") List<String> empCats, @Param("cmpIds") List<Long> cmpIds,
            @Param("costIds") List<Long> costIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT T FROM WorkDairyEmpCostEntity T WHERE T.workDairyEmpWageEntity.id = :parentId "
            + "AND T.isLatest= true AND ((T.workDairyId.clientApproval=0 AND lower(T.apprStatus)='approved' AND "
            + "lower(T.workDairyId.apprStatus)='approved') OR "
            + "(T.workDairyId.clientApproval=1 AND lower(T.apprStatus)='client approved' AND "
            + "lower(T.workDairyId.apprStatus)='client approved'))")
    List<WorkDairyEmpCostEntity> getApprovedWorkDairyEmpTime(@Param("parentId") Long workDairyEmpWageId);
    
    @Query("SELECT T FROM WorkDairyEmpCostEntity T WHERE T.workDairyId.id = :wdid AND T.costId.id =:costId  ")
    List<WorkDairyEmpCostEntity> findcostcodes(@Param("wdid") Long wdid, @Param("costId") Long costId);

}