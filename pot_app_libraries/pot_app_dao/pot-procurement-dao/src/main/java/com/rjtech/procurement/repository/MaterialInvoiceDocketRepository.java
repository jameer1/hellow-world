package com.rjtech.procurement.repository;

import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.MaterialInvoiceDocketItemEntity;

@Repository
public interface MaterialInvoiceDocketRepository
        extends ProcurementBaseRepository<MaterialInvoiceDocketItemEntity, Long> {

}
