package com.rjtech.register.repository.material;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface MaterialDockSchItemRepository extends RegisterBaseRepository<MaterialProjDocketSchItemsEntity, Long> {

    @Query("SELECT T FROM MaterialProjDocketSchItemsEntity T WHERE T.materialProjDocketEntity.id=:projDocketId  AND T.status=:status")
    public List<MaterialProjDocketSchItemsEntity> findMaterialDocSchItemsByDocket(
            @Param("projDocketId") Long projDocketId, @Param("status") Integer status);

    @Query("SELECT MPSC.materialProjDtlEntity.preContractsMaterialCmpEntity.id, "
            + "MPSC.materialProjDtlEntity.preContractMterialId.id, " + "MPSC.materialProjDtlEntity.purchaseId.id, "
            + "MPSC.materialProjDocketEntity.fromProjId.projectId, "
            + "MPSC.materialProjDocketEntity.toProjId.projectId, "
            + "coalesce(MPSC.materialProjDocketEntity.originStockCode, MPSC.materialProjDocketEntity.originProjStockCode), "
            + "coalesce(MPSC.materialProjDocketEntity.toStockCode, MPSC.materialProjDocketEntity.toProjStockCode), "
            + "MPSC.materialProjDocketEntity.issuedEmpEntity.code, "
            + "concat(MPSC.materialProjDocketEntity.issuedEmpEntity.firstName, MPSC.materialProjDocketEntity.issuedEmpEntity.lastName) as issuedEmp ,"
            + "MPSC.materialProjDocketEntity.receivedEmpEntity.code, "
            + "concat(MPSC.materialProjDocketEntity.receivedEmpEntity.firstName, MPSC.materialProjDocketEntity.receivedEmpEntity.lastName) as receivedEmp, "
            + "MPSC.materialProjDtlEntity.materialClassId.id, " + "MPSC.materialProjDtlEntity.companyMstrEntity.id, "
            + "MPSC.materialProjDocketEntity.id, " + "MPSC.materialProjDocketEntity.docketDate, "
            + "MPSC.materialProjDtlEntity.rate, MPSC.qty, MPSC.createdOn, "
            + "MPSC.materialProjDocketEntity.notifyCode, " + "MPSC.comments, "
            + "MPSC.materialProjDtlEntity.materialClassId.name, " + "MPSC.materialProjDtlEntity.materialClassId.code, "
            + "MPSC.materialProjDocketEntity.fromProjId.projName, "
            + "MPSC.materialProjDocketEntity.toProjId.projName, "
            + "MPSC.materialProjDtlEntity.companyMstrEntity.name, "
            + "MPSC.materialProjDtlEntity.materialClassId.name, "
            + "MPSC.materialProjDocketEntity.fromProjId.code, "
            + "MPSC.materialProjDocketEntity.toProjId.code, "
            + "MPSC.materialProjDtlEntity.materialClassId.measurmentMstrEntity.name, " + "MPSC.transitQty "
            + "FROM MaterialProjDocketSchItemsEntity MPSC "
            + "WHERE MPSC.materialProjDocketEntity.issuedEmpEntity.status = 1 "
            + "AND MPSC.materialProjDocketEntity.receivedEmpEntity.status = 1 "
            + "AND MPSC.materialProjDocketEntity.fromProjId.projectId IN :projIds "
            + "AND MPSC.materialProjDocketEntity.apprStatus != 'Draft' "
            + "AND MPSC.materialProjDocketEntity.docketDate BETWEEN :fromDate AND :toDate "
            + "ORDER BY MPSC.materialProjDtlEntity.preContractsMaterialCmpEntity.id")
    public List<Object[]> getMaterialDailyIssueSchItems(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT MPSC.materialProjDtlEntity.preContractMterialId.preContractEntity.currencyCode, "
            + "MPSC.materialProjDocketEntity.docketDate, "
            + "MPSC.materialProjDtlEntity.projId.parentProjectMstrEntity.projName, " + "MPSC.qty, "
            + "MPSC.materialProjDtlEntity.projId.projName, " + "MPSC.materialProjDtlEntity.preContractMterialId.id, "
            + "MPSC.materialProjDtlEntity.projId.code, " + "MPSC.materialProjDtlEntity.purchaseId.id, "
            + "MPSC.materialProjDtlEntity.rate, " + "MPSC.materialProjDtlEntity.materialClassId.name, "
            + "MPSC.materialProjDtlEntity.materialClassId.materialClassMstrEntity.name, "
            + "MPSC.materialProjDocketEntity.id, " + "MPSC.materialProjDtlEntity.companyMstrEntity.name, "
            + "MPSC.materialProjDtlEntity.materialClassId.measurmentMstrEntity.name, "
            + "MPSC.materialProjDtlEntity.materialClassId.id, "
            + "MPSC.openingStock, MPSC.closingStock, " + "MPSC.materialProjDtlEntity.id " + "FROM MaterialProjDocketSchItemsEntity MPSC "
            + "WHERE MPSC.materialProjDocketEntity.issuedEmpEntity.status = 1 "
            + "AND MPSC.materialProjDocketEntity.receivedEmpEntity.status = 1 "
            + "AND MPSC.materialProjDocketEntity.fromProjId IN :projIds "
            + "AND MPSC.materialProjDocketEntity.apprStatus != 'Draft' "
            + "AND MPSC.materialProjDocketEntity.docketDate BETWEEN :fromDate AND :toDate ")
    public List<Object[]> getMaterialDeliveryDockets(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT MPSC.materialProjDtlEntity.preContractMterialId.preContractEntity.currencyCode, "
            + "MPSC.materialProjDocketEntity.docketDate, "
            + "MPSC.materialProjDtlEntity.projId.parentProjectMstrEntity.projName, " + "MPSC.qty, "
            + "MPSC.materialProjDtlEntity.projId.projName, " + "MPSC.materialProjDtlEntity.preContractMterialId.id, "
            + "MPSC.materialProjDtlEntity.projId.code, " + "MPSC.materialProjDtlEntity.purchaseId.id, "
            + "MPSC.materialProjDtlEntity.rate, " + "MPSC.materialProjDtlEntity.materialClassId.name, "
            + "MPSC.materialProjDtlEntity.materialClassId.materialClassMstrEntity.name, "
            + "MPSC.materialProjDocketEntity.id, " + "MPSC.materialProjDtlEntity.companyMstrEntity.name, "
            + "MPSC.materialProjDtlEntity.materialClassId.measurmentMstrEntity.code, "
            + "MPSC.materialProjDtlEntity.materialClassId.id, "
            + "MPSC.openingStock, MPSC.closingStock " + "FROM MaterialProjDocketSchItemsEntity MPSC "
            + "WHERE MPSC.materialProjDocketEntity.issuedEmpEntity.status = 1 "
            + "AND MPSC.materialProjDocketEntity.receivedEmpEntity.status = 1 "
            + "AND MPSC.materialProjDocketEntity.fromProjId IN :projIds "
            + "AND MPSC.materialProjDocketEntity.apprStatus != 'Draft' "
            + "AND MPSC.materialProjDtlEntity.materialClassId IN :materials "
            + "AND (MPSC.materialProjDocketEntity.toStockId IN :storeIds OR "
            + "MPSC.materialProjDocketEntity.toProjStockId IN :projStoreIds) "
            + "AND MPSC.materialProjDocketEntity.docketDate BETWEEN :fromDate AND :toDate ")
    public List<Object[]> getDocketsForInventoryReports(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, 
            @Param("materials") List<MaterialClassMstrEntity> materials,
            @Param("storeIds") List<StockMstrEntity> storeIds,
            @Param("projStoreIds") List<ProjStoreStockMstrEntity> projStoreIds);
    
    @Query("SELECT MPSC.materialProjDtlEntity.preContractMterialId.preContractEntity.currencyCode, "
            + "MPSC.materialProjDocketEntity.docketDate, "
            + "MPSC.materialProjDtlEntity.projId.parentProjectMstrEntity.projName, " + "MPSC.qty, "
            + "MPSC.materialProjDtlEntity.projId.projName, " + "MPSC.materialProjDtlEntity.preContractMterialId.id, "
            + "MPSC.materialProjDtlEntity.projId.code, " + "MPSC.materialProjDtlEntity.purchaseId.id, "
            + "MPSC.materialProjDtlEntity.rate, " + "MPSC.materialProjDtlEntity.materialClassId.name, "
            + "MPSC.materialProjDtlEntity.materialClassId.materialClassMstrEntity.name, "
            + "MPSC.materialProjDocketEntity.id, " + "MPSC.materialProjDtlEntity.companyMstrEntity.name, "
            + "MPSC.materialProjDtlEntity.materialClassId.measurmentMstrEntity.code, "
            + "MPSC.materialProjDtlEntity.materialClassId.id, "
            + "MPSC.openingStock, MPSC.closingStock " + "FROM MaterialProjDocketSchItemsEntity MPSC "
            + "WHERE MPSC.materialProjDocketEntity.issuedEmpEntity.status = 1 "
            + "AND MPSC.materialProjDocketEntity.receivedEmpEntity.status = 1 "
            + "AND MPSC.materialProjDocketEntity.fromProjId IN :projIds "
            + "AND MPSC.materialProjDocketEntity.apprStatus != 'Draft' "
            + "AND MPSC.materialProjDtlEntity.materialClassId IN :materials "
            + "AND (MPSC.materialProjDocketEntity.toStockId IN :storeIds ) "
          //  + "MPSC.materialProjDocketEntity.toProjStockId IN :projStoreIds) "
            + "AND MPSC.materialProjDocketEntity.docketDate BETWEEN :fromDate AND :toDate ")
    public List<Object[]> getDocketsForInventoryStockReports(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, 
            @Param("materials") List<MaterialClassMstrEntity> materials,
            @Param("storeIds") List<StockMstrEntity> storeIds);
    
    @Query("SELECT MPSC.materialProjDtlEntity.preContractMterialId.preContractEntity.currencyCode, "
            + "MPSC.materialProjDocketEntity.docketDate, "
            + "MPSC.materialProjDtlEntity.projId.parentProjectMstrEntity.projName, " + "MPSC.qty, "
            + "MPSC.materialProjDtlEntity.projId.projName, " + "MPSC.materialProjDtlEntity.preContractMterialId.id, "
            + "MPSC.materialProjDtlEntity.projId.code, " + "MPSC.materialProjDtlEntity.purchaseId.id, "
            + "MPSC.materialProjDtlEntity.rate, " + "MPSC.materialProjDtlEntity.materialClassId.name, "
            + "MPSC.materialProjDtlEntity.materialClassId.materialClassMstrEntity.name, "
            + "MPSC.materialProjDocketEntity.id, " + "MPSC.materialProjDtlEntity.companyMstrEntity.name, "
            + "MPSC.materialProjDtlEntity.materialClassId.measurmentMstrEntity.code, "
            + "MPSC.materialProjDtlEntity.materialClassId.id, "
            + "MPSC.openingStock, MPSC.closingStock " + "FROM MaterialProjDocketSchItemsEntity MPSC "
            + "WHERE MPSC.materialProjDocketEntity.issuedEmpEntity.status = 1 "
            + "AND MPSC.materialProjDocketEntity.receivedEmpEntity.status = 1 "
            + "AND MPSC.materialProjDocketEntity.fromProjId IN :projIds "
            + "AND MPSC.materialProjDocketEntity.apprStatus != 'Draft' "
            + "AND MPSC.materialProjDtlEntity.materialClassId IN :materials "
           // + "AND (MPSC.materialProjDocketEntity.toStockId IN :storeIds OR "
            + "AND (MPSC.materialProjDocketEntity.toProjStockId IN :projStoreIds) "
            + "AND MPSC.materialProjDocketEntity.docketDate BETWEEN :fromDate AND :toDate ")
    public List<Object[]> getDocketsForInventoryStoreReports(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, 
            @Param("materials") List<MaterialClassMstrEntity> materials,
            @Param("projStoreIds") List<ProjStoreStockMstrEntity> projStoreIds); 
    
    

}
