package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.PurchaseOrderInvoiceDtlEntity;

@Repository
public interface ApproveInvoiceRepository extends ProcurementBaseRepository<PurchaseOrderInvoiceDtlEntity, Long> {

    @Query("SELECT P FROM PurchaseOrderInvoiceDtlEntity P WHERE P.status=:status AND ((:reqUserId IS NOT NULL AND P.reqUserId=:reqUserId ) OR :reqUserId IS NULL) "
            + " AND ( :currentStatus IS NOT NULL AND P.currentStatus=:currentStatus ) AND  P.projId.projectId=:projId ")

    List<PurchaseOrderInvoiceDtlEntity> findPostInvoices(@Param("status") Integer status,
            @Param("reqUserId") Long reqUserId, @Param("currentStatus") String currentStatus,
            @Param("projId") Long projId);

    @Query("SELECT P FROM PurchaseOrderInvoiceDtlEntity P WHERE P.status=:status AND ((:reqUserId IS NOT NULL AND P.reqUserId=:reqUserId ) OR :reqUserId IS NULL) "
            + " AND ( :currentStatus IS NOT NULL AND P.currentStatus=:currentStatus ) AND ( :paymentStatus IS NOT NULL AND P.paymentStatus=:paymentStatus ) AND  P.projId.projectId=:projId ")

    List<PurchaseOrderInvoiceDtlEntity> findReleasePaymentInvoices(@Param("status") Integer status,
            @Param("reqUserId") Long reqUserId, @Param("currentStatus") String currentStatus,
            @Param("paymentStatus") String paymentStatus, @Param("projId") Long projId);
}
