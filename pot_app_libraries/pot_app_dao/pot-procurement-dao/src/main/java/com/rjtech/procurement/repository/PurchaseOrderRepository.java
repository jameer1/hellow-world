package com.rjtech.procurement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;

public interface PurchaseOrderRepository extends ProcurementBaseRepository<PurchaseOrderEntity, Long> {

    @Query("SELECT PUR FROM PurchaseOrderEntity PUR  WHERE PUR.projId.projectId in (:projIds) AND PUR.status=:status")
    List<PurchaseOrderEntity> findPurchaseOrders(@Param("projIds") List<Long> projIds, @Param("status") Integer status);
    
    @Query("SELECT PUR FROM PurchaseOrderEntity PUR  WHERE PUR.projId.projectId in (:projIds)")
    List<PurchaseOrderEntity>  findAllPurchaseOrders(@Param("projIds") List<Long> projIds);

    @Query("SELECT PUR FROM PurchaseOrderEntity PUR  WHERE PUR.projId.projectId in ( :projIds ) "
            + "  AND  PUR.status=:status AND  PUR.procureType like :procureType  ORDER BY PUR.id")
    List<PurchaseOrderEntity> findPOByProcureType(@Param("projIds") List<Long> projIds, @Param("status") Integer status,
            @Param("procureType") String procureType);

    @Query("SELECT PUR FROM PurchaseOrderEntity PUR  WHERE PUR.projId.projectId in ( :projIds ) "
            + "AND :workDairyDate >= PUR.startDate " + "AND PUR.status=:status "
                    + "AND  PUR.procureType like :procureType " + "ORDER BY PUR.id")
    List<PurchaseOrderEntity> findPOByProcureType(@Param("projIds") List<Long> projIds, @Param("status") Integer status,
            @Param("procureType") String procureType, @Param("workDairyDate") Date workDairyDate);

    @Query("SELECT PUR FROM PurchaseOrderEntity PUR  WHERE PUR.projId.projectId = :projId AND PUR.preContractsCmpEntity.id in ( :preContractCmpIds ) AND PUR.status=:status")
    List<PurchaseOrderEntity> findPOByPrecontractCmpIdAndProjId(@Param("projId") Long projId,
            @Param("preContractCmpIds") List<Long> preContractCmpIds, @Param("status") Integer status);

    @Query("SELECT PUR FROM PurchaseOrderEntity PUR  WHERE PUR.preContractsCmpEntity.preContractEntity.id = :preContractId")
    List<PurchaseOrderEntity> getPurchaseOrdersByPrecontract(@Param("preContractId") Long preContractId);

    @Query("SELECT po.preContractsCmpEntity FROM PurchaseOrderEntity po WHERE po.id = :poId")
    PreContractsCmpEntity getPurchaseOrderPrecontractCmp(@Param("poId") Long poId);

    @Query("SELECT PUR FROM PurchaseOrderEntity PUR  WHERE PUR.projId.projectId in ( :projIds ) "
            + "AND  PUR.procureType IN :procureTypes  ORDER BY PUR.id")
    List<PurchaseOrderEntity> findPOByProcureTypes(@Param("projIds") List<Long> projIds,
            @Param("procureTypes") String... procureTypes);
    
    PurchaseOrderEntity findByPreContractsCmpEntity(
            @Param("preContractsCmpEntity") PreContractsCmpEntity preContractsCmpEntity);
    
    @Query("SELECT PUR FROM PurchaseOrderEntity PUR  WHERE PUR.projId.projectId in ( :projIds ) "
            + " AND PUR.status=:status AND  PUR.preContractsCmpEntity.preContractEntity.preContractType = :preContractType " + " ORDER BY PUR.id")
    List<PurchaseOrderEntity> findPOByPreContractType(@Param("projIds") List<Long> projIds, @Param("status") Integer status,
            @Param("preContractType") String preContractType);

}
