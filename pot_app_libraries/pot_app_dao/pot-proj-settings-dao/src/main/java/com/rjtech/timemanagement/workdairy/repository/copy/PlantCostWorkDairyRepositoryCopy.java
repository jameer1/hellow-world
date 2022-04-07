package com.rjtech.timemanagement.workdairy.repository.copy;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantCostEntity;

//import com.rjtech.workdairy.WorkDairyPlantCostEntityCopy;

@Repository
public interface PlantCostWorkDairyRepositoryCopy extends JpaRepository<WorkDairyPlantCostEntity, Long> {

    @Query("SELECT wptc.costId.id, to_char(wptc.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.workDairyDate,'DD-MM-YYYY'),"
            + "SUM(wptc.usedTime + wptc.idleTime) FROM WorkDairyPlantCostEntity wptc WHERE "
            + "wptc.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.projId.projectId=:projId AND "
            + "((wptc.workDairyPlantStatusEntity.apprStatus='approved' AND "
            + "wptc.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.clientApproval=0) OR "
            + "(wptc.workDairyPlantStatusEntity.apprStatus='client approved' AND "
            + "wptc.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.clientApproval=1)) "
            + "GROUP BY wptc.costId.id, wptc.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.workDairyDate")
    public List<Object[]> getPlantCostDetails(@Param("projId") Long projId);

    @Query("SELECT WPTC.workDairyId.workDairyDate, "
            + " WPTC.workDairyPlantStatusEntity.workDairyPlantDtlEntity.plantRegId.id, "
            + " WPTC.costId.id, WPTC.workDairyPlantStatusEntity.workDairyPlantDtlEntity.shiftName, "
            + " WPTC.usedTime, WPTC.idleTime FROM WorkDairyPlantCostEntity WPTC "
            + " WHERE WPTC.workDairyPlantStatusEntity.workDairyPlantDtlEntity.plantRegId.id in (:plantIds) AND "
            + " ((WPTC.workDairyPlantStatusEntity.apprStatus = 'Approved' AND WPTC.workDairyId.clientApproval = 0) "
            + " OR (WPTC.workDairyPlantStatusEntity.apprStatus = 'Client Approved' and WPTC.workDairyId.clientApproval=1))")
    public List<Object[]> getPlantWorkDiaryActualHrs(@Param("plantIds") Set<Long> plantIds);

    @Query("SELECT WPTC.workDairyId.projId, WPTC.workDairyId.workDairyDate, WPTC.workDairyPlantStatusEntity.workDairyPlantDtlEntity, "
            + " WPTC.costId, WPTC.usedTime, WPTC.idleTime, PGV.currency FROM WorkDairyPlantCostEntity WPTC "
            + " LEFT JOIN ProjGeneralMstrEntity PGV ON PGV.projMstrEntity = WPTC.workDairyId.projId.projectId AND PGV.status=1 AND PGV.isLatest='Y' "
            + " WHERE WPTC.workDairyId.projId.projectId in (:projIds) AND WPTC.costId.id in (:costIds) AND"
            + " WPTC.workDairyPlantStatusEntity.workDairyPlantDtlEntity.plantRegId.cmpId.id in (:cmpIds) AND "
            + " WPTC.workDairyId.workDairyDate between :fromDate AND :toDate AND "
            + " ((WPTC.workDairyPlantStatusEntity.apprStatus = 'Approved' AND WPTC.workDairyId.clientApproval = 0) "
            + " OR (WPTC.workDairyPlantStatusEntity.apprStatus = 'Client Approved' and WPTC.workDairyId.clientApproval=1))"
            + " order by WPTC.workDairyId.workDairyDate ")
    public List<Object[]> getPlantWorkDiaryActualHrs(@Param("projIds") List<Long> projIds,
            @Param("cmpIds") List<Long> cmpIds, @Param("costIds") List<Long> costIds, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);
    
    @Query("SELECT wptc.costId.id, wptc.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.workDairyDate, "
            + "SUM(wptc.usedTime + wptc.idleTime) FROM WorkDairyPlantCostEntity wptc WHERE "
            + "wptc.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.projId.projectId=:projId "
            + "AND wptc.costId.id in (:resIds) "
            + "AND ((wptc.workDairyPlantStatusEntity.apprStatus='approved' AND "
            + "wptc.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.clientApproval=0) OR "
            + "(wptc.workDairyPlantStatusEntity.apprStatus='client approved' AND "
            + "wptc.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.clientApproval=1)) "
            + "GROUP BY wptc.costId.id, wptc.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.workDairyDate")
    public List<Object[]> getPlantCostDetails(@Param("projId") Long projId, @Param("resIds") List<Long> resIds);

}
