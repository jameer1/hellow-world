package com.rjtech.timemanagement.workdairy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialCostEntity;

@Repository
public interface MaterialCostWorkDairyRepository extends JpaRepository<WorkDairyMaterialCostEntity, Long> {

    @Modifying
    @Query("DELETE FROM  WorkDairyMaterialCostEntity T  where T.workDairyId.id=:workDairyId AND  T.costId in :deleteIds")
    void deleteMaterialCostCodes(@Param("workDairyId") Long workDairyId, @Param("deleteIds") List<Long> deleteIds);

    @Query("SELECT MAP.preContractsMaterialCmpEntity.id, " + "MAP.preContractMterialId.id, " + "MAP.purchaseId.id, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.projectId, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.projName, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.id, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.reqUserId.empCode, "
            + "WDMS.workDairyMaterialDtlEntity.sourceType, "
            + "MAP.materialClassId.id, MAP.materialClassId.name, MAP.materialClassId.code, "
            + "MAP.companyMstrEntity.id, MAP.companyMstrEntity.code, " + "WDMS.workDairyMaterialDtlEntity.docketType, "
            + "WDMS.workDairyMaterialDtlEntity.docketNum, " + "WDMS.workDairyMaterialDtlEntity.docketDate, "
            + "MAP.rate, WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate, " + "WDMS.total, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.parentProjectMstrEntity.projName, "
            + "MAP.materialClassId.measurmentMstrEntity.code " + "FROM " + "WorkDairyMaterialStatusEntity WDMS "
            + "LEFT JOIN WDMS.workDairyMaterialDtlEntity.materialProjDtlEntity MAP "
            + "WHERE WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.projectId IN :projIds "
            + "AND WDMS.workDairyMaterialDtlEntity.workDairyEntity.reqUserId.status = 1 "
            + "AND (((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = true) "
            + "AND (WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Client Approved')) OR "
            + "((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = false) AND "
            + "(WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Approved'))) "
            + "AND WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate " + "BETWEEN :fromDate AND :toDate "
            + "ORDER BY MAP.preContractsMaterialCmpEntity.id, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate")
    List<Object[]> getMaterialConsumption(@Param("projIds") List<Long> projIds, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT " 
            + "MAP.preContractMterialId.preContractEntity.currencyCode, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate, "
            + "WDMS.workDairyMaterialDtlEntity.docketNum, " + "WDMS.workDairyMaterialDtlEntity.docketType, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.parentProjectMstrEntity.projName, "
            + "WDMS.workDairyMaterialDtlEntity.receivedQty, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.projName, " + "MAP.preContractMterialId.id, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.code, " + "MAP.purchaseId.id, " + "MAP.rate, "
            + "MAP.materialClassId.name, " + "MAP.materialClassId.materialClassMstrEntity.name, "
            + "MAP.companyMstrEntity.name, " + "MAP.materialClassId.measurmentMstrEntity.name, "
            + "WDMS.workDairyMaterialDtlEntity.closingStock, " + "WDMS.workDairyMaterialDtlEntity.openingStock, "
            + "WDMS.workDairyMaterialDtlEntity.consumptionQty, " + "WDMS.workDairyMaterialDtlEntity.id, MAP.id, "
            + "MAP.materialClassId.id " + "FROM " + "WorkDairyMaterialStatusEntity WDMS "
            + "LEFT JOIN WDMS.workDairyMaterialDtlEntity.materialProjDtlEntity MAP "
            + "WHERE WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId IN :projIds "
            + "AND WDMS.workDairyMaterialDtlEntity.workDairyEntity.reqUserId.status = 1 "
            + "AND (((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = true) "
            + "AND (WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Client Approved')) OR "
            + "((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = false) AND "
            + "(WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Approved'))) "
            + "AND WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate " + "BETWEEN :fromDate AND :toDate")
    List<Object[]> getWorkDairyMaterialsForLedger(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    
    @Query("SELECT " 
            + "MAP.preContractMterialId.preContractEntity.currencyCode, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate, "
            + "WDMS.workDairyMaterialDtlEntity.docketNum, " + "WDMS.workDairyMaterialDtlEntity.docketType, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.parentProjectMstrEntity.projName, "
            + "WDMS.workDairyMaterialDtlEntity.receivedQty, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.projName, " + "MAP.preContractMterialId.id, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.code, " + "MAP.purchaseId.id, " + "MAP.rate, "
            + "MAP.materialClassId.name, " + "MAP.materialClassId.materialClassMstrEntity.name, "
            + "MAP.companyMstrEntity.name, " + "MAP.materialClassId.measurmentMstrEntity.name, "
            + "WDMS.workDairyMaterialDtlEntity.closingStock, " + "WDMS.workDairyMaterialDtlEntity.openingStock, "
            + "WDMS.workDairyMaterialDtlEntity.consumptionQty, " + "WDMS.workDairyMaterialDtlEntity.id, MAP.id, "
            + "MAP.materialClassId.id " + "FROM " + "WorkDairyMaterialStatusEntity WDMS "
            + "LEFT JOIN WDMS.workDairyMaterialDtlEntity.materialProjDtlEntity MAP "
            + "LEFT JOIN WDMS.workDairyMaterialDtlEntity.materialPODeliveryDocketEntity MADD "
            + "WHERE WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId IN :projIds "
            + "AND WDMS.workDairyMaterialDtlEntity.workDairyEntity.reqUserId.status = 1 "
            + "AND (((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = true) "
            + "AND (WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Client Approved')) OR "
            + "((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = false) AND "
            + "(WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Approved'))) "
            + "AND WDMS.workDairyMaterialDtlEntity.docketType = 'Supplier Docket' "
            + "AND MAP.materialClassId IN :materialIds "
            + "AND (MADD.stockId IN :stockIds OR MADD.projStockId IN :projStockIds ) "
            + "AND WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate " + "BETWEEN :fromDate AND :toDate")
    List<Object[]> getInventoryReportsSupplierDockets(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("materialIds") List<MaterialClassMstrEntity> materialIds, 
            @Param("stockIds") List<StockMstrEntity> stockIds,
            @Param("projStockIds") List<ProjStoreStockMstrEntity> projStockIds);
    
    @Query("SELECT " 
            + "MAP.preContractMterialId.preContractEntity.currencyCode, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate, "
            + "WDMS.workDairyMaterialDtlEntity.docketNum, " + "WDMS.workDairyMaterialDtlEntity.docketType, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.parentProjectMstrEntity.projName, "
            + "WDMS.workDairyMaterialDtlEntity.receivedQty, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.projName, " + "MAP.preContractMterialId.id, "
            + "WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId.code, " + "MAP.purchaseId.id, " + "MAP.rate, "
            + "MAP.materialClassId.name, " + "MAP.materialClassId.materialClassMstrEntity.name, "
            + "MAP.companyMstrEntity.name, " + "MAP.materialClassId.measurmentMstrEntity.name, "
            + "WDMS.workDairyMaterialDtlEntity.closingStock, " + "WDMS.workDairyMaterialDtlEntity.openingStock, "
            + "WDMS.workDairyMaterialDtlEntity.consumptionQty, " + "WDMS.workDairyMaterialDtlEntity.id, MAP.id, "
            + "MAP.materialClassId.id " + "FROM " + "WorkDairyMaterialStatusEntity WDMS "
            + "LEFT JOIN WDMS.workDairyMaterialDtlEntity.materialProjDtlEntity MAP "
            + "LEFT JOIN WDMS.workDairyMaterialDtlEntity.projDocketSchId.materialProjDocketEntity MAPD "
            + "WHERE WDMS.workDairyMaterialDtlEntity.workDairyEntity.projId IN :projIds "
            + "AND WDMS.workDairyMaterialDtlEntity.workDairyEntity.reqUserId.status = 1 "
            + "AND (((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = true) "
            + "AND (WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Client Approved')) OR "
            + "((WDMS.workDairyMaterialDtlEntity.workDairyEntity.clientApproval = false) AND "
            + "(WDMS.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Approved'))) "
            + "AND WDMS.workDairyMaterialDtlEntity.docketType != 'Supplier Docket' "
            + "AND MAP.materialClassId IN :materialIds "
            + "AND (MAPD.originStockId IN :stockIds OR MAPD.originProjStockId IN :projStockIds ) "
            + "AND WDMS.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate " + "BETWEEN :fromDate AND :toDate")
    List<Object[]> getInventoryReportsDockets(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("materialIds") List<MaterialClassMstrEntity> materialIds, 
            @Param("stockIds") List<StockMstrEntity> stockIds,
            @Param("projStockIds") List<ProjStoreStockMstrEntity> projStockIds);

    @Query("SELECT "
            + "WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate, "
            + "WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.projId.parentProjectMstrEntity.projName, "
            + "WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.projId.projName, " 
            + "MAP.materialClassId.measurmentMstrEntity.name, MAP.materialClassId.name, " 
            + "WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.consumptionQty, "
            + "WDMC.costId.code, WDMC.costId.name, WDMC.costId.projCostItemEntity.name,"
            + "MAP.materialClassId.id, WDMC.costId.id, "
            + "WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.projId.parentProjectMstrEntity.projectId, "
            + "WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.projId.projectId " 
            + "FROM WorkDairyMaterialCostEntity WDMC "
            + "LEFT JOIN WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.materialProjDtlEntity MAP "
            + "WHERE WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.projId IN :projIds "
            + "AND WDMC.costId IN :costCodeIds "
            + "AND MAP.materialClassId IN :materialIds "
            + "AND WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.consumptionQty > 0 "
            + "AND (WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Approved' OR WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.apprStatus = 'Client Approved') "
            + "AND WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate < :toDate "
            + " ORDER BY "
            + " WDMC.workDairyMaterialStatusEntity.workDairyMaterialDtlEntity.workDairyEntity.workDairyDate")
    List<Object[]> getMaterialsForReports(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("costCodeIds") List<ProjCostItemEntity> costCodeIds,
            @Param("materialIds") List<MaterialClassMstrEntity> materialIds,@Param("toDate") Date toDate);

}
