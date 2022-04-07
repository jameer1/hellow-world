package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;

//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;

@Repository
public interface ProjCostItemRepositoryCopy extends JpaRepository<ProjCostItemEntity, Long> {

    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE projCost.projId.projectId =:projId AND projCost.projCostItemEntity IS NULL AND projCost.status=:status  ORDER BY  projCost.code")
    public List<ProjCostItemEntity> findCostDetails(@Param("projId") Long projId, @Param("status") Integer status);
    
    @Query("SELECT projCost FROM ProjCostItemEntity projCost WHERE  projCost.projId.projectId =:projId AND projCost.status=:status AND projCost.workDairyStatus IS TRUE  ORDER BY  projCost.code")
    public List<ProjCostItemEntity> findCostDetailsWithWorkDairyEntry(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT WMTC.costId.id, SUM(COALESCE(WMTC.usedTime, 0) + COALESCE(WMTC.idleTime, 0)) "
            + "FROM WorkDairyEmpCostEntity WMTC " + "Where WMTC.workDairyId.projId = :projMstrEntity "
            + "AND ((WMTC.apprStatus = 'Approved' and WMTC.workDairyId.clientApproval = 0) "
            + "OR (WMTC.apprStatus = 'Client Approved' and WMTC.workDairyId.clientApproval = 1)) "
            + "GROUP BY WMTC.costId.id")
    public List<Object[]> getEmpWorkdairyActualHrs(@Param("projMstrEntity") ProjMstrEntity projMstrEntity);

    @Query("SELECT TSWD.projCostItemEntity.id, SUM(COALESCE(TSWD.day1, 0) + " + "COALESCE(TSWD.day2, 0) + "
            + "COALESCE(TSWD.day3, 0) + " + "COALESCE(TSWD.day4, 0) + " + "COALESCE(TSWD.day5, 0) + "
            + "COALESCE(TSWD.day6, 0) + " + "COALESCE(TSWD.day7, 0)) " + "FROM TimeSheetEmpWorkDtlEntity TSWD "
            + "WHERE TSWD.apprStatus = 'Approved' AND TSWD.projCostItemEntity.item = 1 "
            + "AND TSWD.projCostItemEntity.projId = :projMstrEntity " + "GROUP BY TSWD.projCostItemEntity.id")
    public List<Object[]> getEmpTimesheetActualHrs(@Param("projMstrEntity") ProjMstrEntity projMstrEntity);

    @Query("SELECT WDMT.costId.id, SUM(WDMT.quantity) FROM WorkDairyMaterialCostEntity WDMT "
            + "WHERE ((WDMT.workDairyMaterialStatusEntity.apprStatus  = 'Approved' "
            + "AND WDMT.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = 0) "
            + "OR (WDMT.workDairyMaterialStatusEntity.apprStatus = 'Client Approved' "
            + "AND WDMT.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = 1)) "
            + "AND WDMT.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.projId = :projMstrEntity "
            + "GROUP BY WDMT.costId.id")
    public List<Object[]> getMaterialWorkDairyActualHrs(@Param("projMstrEntity") ProjMstrEntity projMstrEntity);

    @Query("SELECT WPTC.costId.id, SUM(WPTC.usedTime + WPTC.idleTime) FROM WorkDairyPlantCostEntity WPTC "
            + "WHERE (( WPTC.workDairyPlantStatusEntity.apprStatus  = 'Approved' and "
            + "WPTC.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.clientApproval = 0) "
            + "OR (WPTC.workDairyPlantStatusEntity.apprStatus = 'Client Approved' and "
            + "WPTC.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.clientApproval = 1)) "
            + "AND WPTC.workDairyPlantStatusEntity.workDairyPlantDtlEntity.workDairyEntity.projId = :projMstrEntity "
            + "GROUP By WPTC.costId.id")
    public List<Object[]> getMaterialPlantActualHrs(@Param("projMstrEntity") ProjMstrEntity projMstrEntity);

    @Query("SELECT CCS.id FROM ProjCostItemEntity CCS WHERE  CCS.projId.projectId in :projIds AND CCS.item=1 AND CCS.status=1")
    public List<Long> findMultiProjCostIds(@Param("projIds") List<Long> projIds);
    
    @Query("SELECT T FROM ProjCostItemEntity T WHERE T.projId.projectId=:projectId AND T.code=:code")
    public ProjCostItemEntity findBy(@Param("projectId") Long projectId, @Param("code") String code);
    
    @Query("SELECT T FROM ProjCostItemEntity T WHERE T.id=:resId")
    public ProjCostItemEntity findBy(@Param("resId") Long resId);

    @Modifying
    @Query("UPDATE ProjCostItemEntity PCI SET PCI.itemStatus=:itemStatus,status=:status WHERE PCI.id in :costItemIds")
    public void updateProjCostItemStatus( @Param("costItemIds") List<Long> costItemIds, @Param("itemStatus") String itemStatus, @Param("status") Integer status );
    
    @Modifying
    @Query("UPDATE ProjCostItemEntity PCI SET PCI.itemStatus=:itemStatus,PCI.status=:status WHERE PCI.id=:costItemId")
    public void updateReturnCostItemStatusById( @Param("costItemId") Long costItemId, @Param("itemStatus") String itemStatus, @Param("status") Integer status );
    
    @Modifying
    @Query("UPDATE ProjCostItemEntity PCI SET PCI.itemStatus=:itemStatus WHERE PCI.id=:costItemId")
    public void updateReturnCostItemStatusById( @Param("costItemId") Long costItemId, @Param("itemStatus") String itemStatus );
    
    @Query("SELECT PCI FROM ProjCostItemEntity PCI WHERE PCI.projId.projectId =:projId AND PCI.projCostItemEntity IS NULL AND PCI.status IN (0,1) ORDER BY PCI.code")
    public List<ProjCostItemEntity> findCostDetailsByProjId( @Param("projId") Long projId );

}
