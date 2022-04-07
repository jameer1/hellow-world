package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.InvoiceVendorBankEntity;

@Repository
public interface InvoiceBankAcoountDetailsRepository extends ProcurementBaseRepository<InvoiceVendorBankEntity, Long> {

    @Query("SELECT T FROM InvoiceVendorBankEntity T  WHERE T.purId.id =:purId  AND  T.status=:status")
    List<InvoiceVendorBankEntity> findInvoiceBankDtls(@Param("purId") Long purId, @Param("status") Integer status);
}
