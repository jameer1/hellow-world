package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.InvoiceParticularsEntity;

@Repository
public interface InvoiceParticularsRepository extends ProcurementBaseRepository<InvoiceParticularsEntity, Long> {

    @Query("SELECT T FROM InvoiceParticularsEntity T  WHERE T.purId.id=:purId  AND  T.status=:status")
    List<InvoiceParticularsEntity> findInvoiceParticulars(@Param("purId") Long purId, @Param("status") Integer status);

}
