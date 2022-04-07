package com.rjtech.procurement.repository;

import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.MapowerInvoiceDocketItemEntity;

@Repository
public interface ManpowerInvoiceDocketRepository
        extends ProcurementBaseRepository<MapowerInvoiceDocketItemEntity, Long> {

}
