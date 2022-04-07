package com.rjtech.procurement.repository;

import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.SowInvoiceDocketItemEntity;

@Repository
public interface SowInvoiceDocketRepository extends ProcurementBaseRepository<SowInvoiceDocketItemEntity, Long> {

}
