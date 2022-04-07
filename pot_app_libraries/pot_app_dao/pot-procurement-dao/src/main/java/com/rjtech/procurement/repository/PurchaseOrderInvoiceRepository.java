package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PurchaseOrderInvoiceDtlEntity;

public interface PurchaseOrderInvoiceRepository extends ProcurementBaseRepository<PurchaseOrderInvoiceDtlEntity, Long> {

    @Query("SELECT T FROM PurchaseOrderInvoiceDtlEntity T  WHERE T.purId =:purId AND T.projId = :projId  AND  T.status=:status")
    List<PurchaseOrderInvoiceDtlEntity> findPurchaseOrderInvoices(@Param("purId") Long purId,
            @Param("projId") Long projId, @Param("status") Integer status);

}
