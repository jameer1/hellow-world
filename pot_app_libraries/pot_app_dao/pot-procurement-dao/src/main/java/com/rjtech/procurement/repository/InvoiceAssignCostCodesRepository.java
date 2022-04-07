package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.InvoiceAssignCostCodesEntity;

@Repository
public interface InvoiceAssignCostCodesRepository
        extends ProcurementBaseRepository<InvoiceAssignCostCodesEntity, Long> {

    @Query("SELECT T FROM InvoiceAssignCostCodesEntity T  WHERE T.purId.id =:purId  AND  T.status=:status")
    List<InvoiceAssignCostCodesEntity> findInvoiceCostVodes(@Param("purId") Long purId,
            @Param("status") Integer status);
}
