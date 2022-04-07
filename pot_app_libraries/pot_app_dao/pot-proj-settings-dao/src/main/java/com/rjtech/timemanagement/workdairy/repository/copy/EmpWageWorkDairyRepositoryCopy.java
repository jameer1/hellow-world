package com.rjtech.timemanagement.workdairy.repository.copy;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpWageEntity;

//import com.rjtech.workdairy.WorkDairyEmpWageEntityCopy;

@Repository
public interface EmpWageWorkDairyRepositoryCopy extends JpaRepository<WorkDairyEmpWageEntity, Long> {

    @Query("SELECT wmtc.costId.id,to_char(wmtc.workDairyId.workDairyDate,'DD-MM-YYYY'), "
            + "SUM(COALESCE(wdmw.idleTotal,0) + COALESCE(wdmw.usedTotal,0)) FROM "
            + "WorkDairyEmpWageEntity wdmw JOIN wdmw.workDairyEmpCostEntities wmtc WHERE wmtc.workDairyId.projId.projectId=:projId "
            + "AND ((lower(wmtc.workDairyId.apprStatus) = 'approved' AND wmtc.workDairyId.clientApproval=0) OR "
            + "(lower(wmtc.workDairyId.apprStatus) = 'client approved' AND wmtc.workDairyId.clientApproval=1)) "
            + "GROUP BY wmtc.costId.id,wmtc.workDairyId.workDairyDate")
    public List<Object[]> getManPowerCostDetails(@Param("projId") Long projId);

    @Query("SELECT wdmw.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id, wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate, "
            + "SUM(COALESCE(wdmw.idleTotal,0) + COALESCE(wdmw.usedTotal,0)) FROM WorkDairyEmpWageEntity wdmw WHERE "
            + "wdmw.workDairyEmpDtlEntity.empRegId.projMstrEntity.projectId=:projId AND ((lower(wdmw.apprStatus)='approved' AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 ) OR (lower(wdmw.apprStatus)='client approved' AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1)) GROUP BY wdmw.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id,"
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate ORDER BY wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate")
    public List<Object[]> getManPowerActualHrs(@Param("projId") Long projId);
    
    @Query("SELECT wdmw.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id, wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate, "
            + "SUM(COALESCE(wdmw.idleTotal,0) + COALESCE(wdmw.usedTotal,0)) FROM WorkDairyEmpWageEntity wdmw WHERE "
            + "wdmw.workDairyEmpDtlEntity.empRegId.projMstrEntity.projectId=:projId AND ((lower(wdmw.apprStatus)='approved' AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 ) OR (lower(wdmw.apprStatus)='client approved' AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1)) "
            + "AND wdmw.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id in (:resIds) "
            + "GROUP BY wdmw.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id,"
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate ORDER BY wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate")
    public List<Object[]> getManPowerActualHrs(@Param("projId") Long projId, @Param("resIds") List<Long> resIds);

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw WHERE wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate BETWEEN :fromDate AND :toDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerDateWiseActualHrs(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
