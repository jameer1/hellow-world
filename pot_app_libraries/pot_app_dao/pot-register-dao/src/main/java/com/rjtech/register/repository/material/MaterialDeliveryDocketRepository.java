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
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface MaterialDeliveryDocketRepository extends RegisterBaseRepository<MaterialPODeliveryDocketEntity, Long> {

    @Query("SELECT T FROM MaterialPODeliveryDocketEntity T WHERE  T.materialProjDtlEntity.id=:regMaterialId AND T.sourceType=:sourceType  and T.docketStatus!='C' AND T.status=:status")
    public List<MaterialPODeliveryDocketEntity> findMaterialDeliveryDocketsByType(
            @Param("regMaterialId") Long regMaterialId, @Param("sourceType") String sourceType,
            @Param("status") Integer status);

    @Query("SELECT T FROM MaterialPODeliveryDocketEntity T WHERE  T.materialProjDtlEntity.id=:regMaterialId   AND T.status=:status")
    public List<MaterialPODeliveryDocketEntity> findMaterialDeliveryDocketDetails(
            @Param("regMaterialId") Long regMaterialId, @Param("status") Integer status);

    @Query("SELECT MADD FROM MaterialPODeliveryDocketEntity MADD "
            + "WHERE MADD.materialProjDtlEntity.projId IN :projIds")
    public List<MaterialPODeliveryDocketEntity> getMaterialDeliveryDockets(
            @Param("projIds") List<ProjMstrEntity> projIds);
    
    @Query("SELECT MADD FROM MaterialPODeliveryDocketEntity MADD "
            + "WHERE MADD.materialProjDtlEntity.projId IN :projIds "
            + "AND MADD.docketDate BETWEEN :fromDate AND :toDate")
    public List<MaterialPODeliveryDocketEntity> getMaterialDeliveryDockets(
            @Param("projIds") List<ProjMstrEntity> projIds, @Param("fromDate") Date fromDate, 
            @Param("toDate") Date toDate);

    @Query("SELECT " + "MADD.materialProjDtlEntity.id," + "MADD.materialProjDtlEntity.preContractMterialId.id,"
            + "MADD.materialProjDtlEntity.preContractMaterialName,"
            + "MADD.materialProjDtlEntity.materialClassId.measurmentMstrEntity.code,"
            + "MADD.materialProjDtlEntity.materialClassId.code, "
            + "MADD.materialProjDtlEntity.companyMstrEntity.name, " + "MADD.materialProjDtlEntity.purchaseId.id, "
            + "MADD.docketNumber, MADD.docketDate, " + "MADD.receivedQty, MADD.defectComments, MADD.comments, MADD.id "
            + "FROM MaterialPODeliveryDocketEntity MADD " + "WHERE MADD.sourceType = 'WPO' "
            + "AND coalesce(MADD.docketStatus, 'N') != 'C' " + "AND MADD.materialProjDtlEntity.projId = :projId "
            + "AND MADD.status = 1")
    public List<Object[]> getMaterialProjectDockets(@Param("projId") ProjMstrEntity projId);

    @Query("SELECT " + "MADD.materialProjDtlEntity.id," + "MADD.materialProjDtlEntity.preContractMterialId.id,"
            + "MADD.materialProjDtlEntity.preContractMaterialName,"
            + "MADD.materialProjDtlEntity.materialClassId.measurmentMstrEntity.code,"
            + "MADD.materialProjDtlEntity.materialClassId.code, "
            + "MADD.materialProjDtlEntity.companyMstrEntity.name, " + "MADD.materialProjDtlEntity.purchaseId.id, "
            + "MADD.docketNumber, MADD.docketDate, " + "MADD.receivedQty, MADD.defectComments, MADD.comments, MADD.id, "
            + "MADD.materialProjDtlEntity.preContractsMaterialCmpEntity.recievedQty," + "MADD.transitQty, "
            + "MADD.materialProjDtlEntity.preContractsMaterialCmpEntity.rate, "
            + "MADD.materialProjDtlEntity.preContractsMaterialCmpEntity.id,"
            + "MADD.materialProjDtlEntity.companyMstrEntity.id," + "MADD.materialProjDtlEntity.materialClassId.id,"
            + "MADD.projDocketSchId " + "FROM MaterialPODeliveryDocketEntity MADD "
            + "WHERE MADD.supplierDocket = true " + "AND coalesce(MADD.docketStatus, 'N') != 'C' "
            + "AND MADD.materialProjDtlEntity.projId = :projId " + "AND MADD.status = 1")
    public List<Object[]> getMaterialSupplierDockets(@Param("projId") ProjMstrEntity projId);

    @Query("SELECT " + "MADD.materialProjDtlEntity.preContractMterialId.preContractEntity.currencyCode, "
            + "MADD.docketDate, " + "MADD.docketNumber, "
            + "MADD.materialProjDtlEntity.projId.parentProjectMstrEntity.projName, " + "MADD.receivedQty, "
            + "MADD.materialProjDtlEntity.projId.projName, " + "MADD.materialProjDtlEntity.preContractMterialId.id,"
            + "MADD.materialProjDtlEntity.projId.code, " + "MADD.materialProjDtlEntity.purchaseId.id, "
            + "MADD.materialProjDtlEntity.preContractsMaterialCmpEntity.rate, "
            + "MADD.materialProjDtlEntity.materialClassId.name, "
            + "MADD.materialProjDtlEntity.materialClassId.materialClassMstrEntity.name, " + "MADD.id, "
            + "MADD.materialProjDtlEntity.companyMstrEntity.name, "
            + "MADD.materialProjDtlEntity.materialClassId.measurmentMstrEntity.name, " + "MADD.supplierDocket, "
                    + "MADD.materialProjDtlEntity.materialClassId.id "
            + "FROM MaterialPODeliveryDocketEntity MADD " + "WHERE coalesce(MADD.docketStatus, 'N') != 'C' "
            + "AND MADD.materialProjDtlEntity.projId IN :projIds "
            + "AND MADD.docketDate BETWEEN :fromDate AND :toDate " + "AND MADD.status = 1")
    public List<Object[]> getSupplierDeliveryDockets(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT " + "MADD.materialProjDtlEntity.preContractMterialId.preContractEntity.currencyCode, "
            + "MADD.docketDate, " + "MADD.docketNumber, "
            + "MADD.materialProjDtlEntity.projId.parentProjectMstrEntity.projName, " + "MADD.receivedQty, "
            + "MADD.materialProjDtlEntity.projId.projName, " + "MADD.materialProjDtlEntity.preContractMterialId.id,"
            + "MADD.materialProjDtlEntity.projId.code, " + "MADD.materialProjDtlEntity.purchaseId.id, "
            + "MADD.materialProjDtlEntity.preContractsMaterialCmpEntity.rate, "
            + "MADD.materialProjDtlEntity.materialClassId.name, "
            + "MADD.materialProjDtlEntity.materialClassId.materialClassMstrEntity.name, " + "MADD.id, "
            + "MADD.materialProjDtlEntity.companyMstrEntity.name, "
            + "MADD.materialProjDtlEntity.materialClassId.measurmentMstrEntity.code, " + "MADD.supplierDocket, "
                    + "MADD.materialProjDtlEntity.materialClassId.id "
            + "FROM MaterialPODeliveryDocketEntity MADD " + "WHERE coalesce(MADD.docketStatus, 'N') != 'C' "
            + "AND MADD.materialProjDtlEntity.projId IN :projIds "
            + "AND MADD.docketDate BETWEEN :fromDate AND :toDate " + "AND MADD.status = 1 "
            + "AND MADD.materialProjDtlEntity.materialClassId IN :materials "
            + "AND (MADD.stockId IN :storeIds OR  MADD.projStockId IN :projStoreIds)")
    public List<Object[]> getDocketsForInventoryReport(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, 
            @Param("materials") List<MaterialClassMstrEntity> materials,
            @Param("storeIds") List<StockMstrEntity> storeIds,
            @Param("projStoreIds") List<ProjStoreStockMstrEntity> projStoreIds);
    
    @Query("SELECT " + "MADD.materialProjDtlEntity.preContractMterialId.preContractEntity.currencyCode, "
            + "MADD.docketDate, " + "MADD.docketNumber, "
            + "MADD.materialProjDtlEntity.projId.parentProjectMstrEntity.projName, " + "MADD.receivedQty, "
            + "MADD.materialProjDtlEntity.projId.projName, " + "MADD.materialProjDtlEntity.preContractMterialId.id,"
            + "MADD.materialProjDtlEntity.projId.code, " + "MADD.materialProjDtlEntity.purchaseId.id, "
            + "MADD.materialProjDtlEntity.preContractsMaterialCmpEntity.rate, "
            + "MADD.materialProjDtlEntity.materialClassId.name, "
            + "MADD.materialProjDtlEntity.materialClassId.materialClassMstrEntity.name, " + "MADD.id, "
            + "MADD.materialProjDtlEntity.companyMstrEntity.name, "
            + "MADD.materialProjDtlEntity.materialClassId.measurmentMstrEntity.code, " + "MADD.supplierDocket, "
                    + "MADD.materialProjDtlEntity.materialClassId.id "
            + "FROM MaterialPODeliveryDocketEntity MADD " + "WHERE coalesce(MADD.docketStatus, 'N') != 'C' "
            + "AND MADD.materialProjDtlEntity.projId IN :projIds "
            + "AND MADD.docketDate BETWEEN :fromDate AND :toDate " + "AND MADD.status = 1 "
            + "AND MADD.materialProjDtlEntity.materialClassId IN :materials "
            + "AND (MADD.stockId IN :storeIds)")
    public List<Object[]> getDocketsForStockReport(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, 
            @Param("materials") List<MaterialClassMstrEntity> materials,
            @Param("storeIds") List<StockMstrEntity> storeIds);
    
    @Query("SELECT " + "MADD.materialProjDtlEntity.preContractMterialId.preContractEntity.currencyCode, "
            + "MADD.docketDate, " + "MADD.docketNumber, "
            + "MADD.materialProjDtlEntity.projId.parentProjectMstrEntity.projName, " + "MADD.receivedQty, "
            + "MADD.materialProjDtlEntity.projId.projName, " + "MADD.materialProjDtlEntity.preContractMterialId.id,"
            + "MADD.materialProjDtlEntity.projId.code, " + "MADD.materialProjDtlEntity.purchaseId.id, "
            + "MADD.materialProjDtlEntity.preContractsMaterialCmpEntity.rate, "
            + "MADD.materialProjDtlEntity.materialClassId.name, "
            + "MADD.materialProjDtlEntity.materialClassId.materialClassMstrEntity.name, " + "MADD.id, "
            + "MADD.materialProjDtlEntity.companyMstrEntity.name, "
            + "MADD.materialProjDtlEntity.materialClassId.measurmentMstrEntity.code, " + "MADD.supplierDocket, "
                    + "MADD.materialProjDtlEntity.materialClassId.id "
            + "FROM MaterialPODeliveryDocketEntity MADD " + "WHERE coalesce(MADD.docketStatus, 'N') != 'C' "
            + "AND MADD.materialProjDtlEntity.projId IN :projIds "
            + "AND MADD.docketDate BETWEEN :fromDate AND :toDate " + "AND MADD.status = 1 "
            + "AND MADD.materialProjDtlEntity.materialClassId IN :materials "
            + "AND ( MADD.projStockId IN :projStoreIds)")
    public List<Object[]> getDocketsForStoreReport(@Param("projIds") List<ProjMstrEntity> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, 
            @Param("materials") List<MaterialClassMstrEntity> materials,
            @Param("projStoreIds") List<ProjStoreStockMstrEntity> projStoreIds);
    
    @Query("SELECT MADD FROM MaterialPODeliveryDocketEntity MADD "
            + "WHERE MADD.materialProjDtlEntity.id = :Id")
    public List<MaterialPODeliveryDocketEntity> getSupplier(@Param("Id") Long id );
    
}
