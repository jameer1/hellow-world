package com.rjtech.procurement.repository;

import org.springframework.stereotype.Repository;

import com.rjtech.procurement.model.PlantInvoiceDocketItemEntity;

@Repository
public interface PlantInvoiceDocketRepository extends ProcurementBaseRepository<PlantInvoiceDocketItemEntity, Long> {

}
