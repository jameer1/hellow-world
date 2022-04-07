package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.InvoiceAmountEntity;

@Repository
public interface InvoiceAmountRepository extends ProcurementBaseRepository<InvoiceAmountEntity, Long> {

    @Query("SELECT T FROM InvoiceAmountEntity T  WHERE T.purId.id =:purId  AND  T.status=:status")
    List<InvoiceAmountEntity> findInvoiceAmount(@Param("purId") Long purId, @Param("status") Integer status);
}
