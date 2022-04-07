package com.rjtech.timemanagement.workdairy.repository.copy;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialStatusEntity;
//import com.rjtech.workdairy.WorkDairyMaterialStatusEntityCopy;

@Repository
public interface MaterialStatusWorkDairyRepositoryCopy extends JpaRepository<WorkDairyMaterialStatusEntity, Long> {

    @Query("SELECT mpd.materialClassId.id, wdms.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate, SUM(wdms.total) FROM "
            + "WorkDairyMaterialStatusEntity wdms "
            + "LEFT JOIN wdms.workDairyMaterialDtlEntity.materialProjDtlEntity mpd "
            + "WHERE wdms.workDairyMaterialDtlEntity.workDairyEntity.projId.projectId=:projId "
            + "AND wdms.workDairyMaterialDtlEntity.workDairyEntity.reqUserId.status = 1 "
            + "AND (((wdms.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = true) "
            + "AND (wdms.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Client Approved')) OR "
            + "((wdms.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = false) AND "
            + "(wdms.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Approved'))) "
            + "GROUP BY mpd.materialClassId.id, wdms.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate "
            + "ORDER BY wdms.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate")
    public List<Object[]> getMaterialActualQtyForSchedules(@Param("projId") Long projId);
    
    @Query("SELECT WDMT.costId.id, SUM(MAP.rate * WDMT.quantity) FROM WorkDairyMaterialStatusEntity WDMS "
            + "LEFT JOIN WDMS.workDairyMaterialDtlEntity.materialProjDtlEntity MAP "
            + "JOIN WDMS.workDairyMaterialCostEntities WDMT "
            + "WHERE WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId = :projMstrEntity "
            + "AND (((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = true) "
            + "AND (WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Client Approved')) OR "
            + "((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = false) AND "
            + "(WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Approved'))) "
            + "group by WDMT.costId.id")
    List<Object[]> getWorkDairyActualAmount(@Param("projMstrEntity") ProjMstrEntity projMstrEntity);

    @Query("SELECT WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId, MAP.materialClassId,"
            + " WDMT.costId, MAP.rate, WDMT.quantity, PGV.currency, WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate "
            + " FROM WorkDairyMaterialStatusEntity WDMS "
            + " LEFT JOIN WDMS.workDairyMaterialDtlEntity.materialProjDtlEntity MAP "
            + " JOIN WDMS.workDairyMaterialCostEntities WDMT "
            + " LEFT JOIN ProjGeneralMstrEntity PGV ON PGV.projMstrEntity = WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId AND PGV.status=1 AND PGV.isLatest='Y' "
            + " WHERE WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.projectId in (:projIds) AND "
            + " WDMT.costId.id in (:costIds) AND MAP.companyMstrEntity.id in (:cmpIds) AND "
            + " WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate between :fromDate AND :toDate AND "
            + " (((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = true) "
            + " AND (WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Client Approved')) OR "
            + " ((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = false) AND "
            + " (WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Approved')))"
            + " order by WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate ")
    List<Object[]> getWorkDairyActualAmount(@Param("projIds") List<Long> projIds, @Param("cmpIds") List<Long> cmpIds,
            @Param("costIds") List<Long> costIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT mpd.materialClassId.id, wdms.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate, SUM(wdms.total) FROM "
            + "WorkDairyMaterialStatusEntity wdms "
            + "LEFT JOIN wdms.workDairyMaterialDtlEntity.materialProjDtlEntity mpd "
            + "WHERE wdms.workDairyMaterialDtlEntity.workDairyEntity.projId.projectId=:projId "
            + "AND wdms.workDairyMaterialDtlEntity.workDairyEntity.reqUserId.status = 1 "
            + "AND mpd.materialClassId.id in (:resIds) "
            + "AND (((wdms.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = true) "
            + "AND (wdms.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Client Approved')) OR "
            + "((wdms.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = false) AND "
            + "(wdms.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Approved'))) "
            + "GROUP BY mpd.materialClassId.id, wdms.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate "
            + "ORDER BY wdms.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate")
    public List<Object[]> getMaterialActualQtyForSchedules(@Param("projId") Long projId, @Param("resIds") List<Long> resIds);

}
