package com.rjtech.procurement.repository;

import com.rjtech.procurement.model.PurchaseOrderRepeatEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseOrderRepeatRepository extends ProcurementBaseRepository<PurchaseOrderRepeatEntity, Long> {

    @Query("SELECT PCS FROM PurchaseOrderRepeatEntity PCS WHERE PCS.parentPOId=:parentPOId and repeatPOId=null")
    List<PurchaseOrderRepeatEntity> findPurchaseOrderRepeatByPO(@Param("parentPOId") Long parentPOId);

    @Query("SELECT PCS FROM PurchaseOrderRepeatEntity PCS WHERE " +
            "PCS.contractItemType=:contractItemType" +
            //" and PCS.parentPOId=:parentPOId" +
            //" and PCS.projId=:projId" +
            " and PCS.contractId=:contractId" +
            " and PCS.contractCmpId=:contractCmpId" +
            " and PCS.contractItemId=:contractItemId" +
            " and PCS.contractItemDetailId=:contractItemDetailId and repeatPOId=null")
    List<PurchaseOrderRepeatEntity> findRepeatPO(@Param("contractItemType") String contractItemType,
                                                 //@Param("parentPOId") Long parentPOId,
                                                 //@Param("projId") Long projId,
                                                 @Param("contractId") Long contractId,
                                                 @Param("contractCmpId") Long contractCmpId,
                                                 @Param("contractItemId") Long contractItemId,
                                                 @Param("contractItemDetailId") Long contractItemDetailId);

    @Query("SELECT PCS FROM PurchaseOrderRepeatEntity PCS WHERE " +
            //"PCS.parentPOId=:parentPOId" +
            " PCS.projId=:projId" +
            " and PCS.contractId=:contractId and repeatPOId=null")
    List<PurchaseOrderRepeatEntity> findRepeatPOByContractId(//@Param("parentPOId") Long parentPOId,
                                                             @Param("projId") Long projId,
                                                             @Param("contractId") Long contractId);
}