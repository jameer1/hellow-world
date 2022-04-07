package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.VendorPostInvoicePlantsDeliveryDocketEntity;

@Repository
public interface VendorPostInvoicePlantsDeliveryDocketRepository extends FinanceBaseRepository<VendorPostInvoicePlantsDeliveryDocketEntity, Long>{

	List<VendorPostInvoicePlantsDeliveryDocketEntity> findByVendorPostInvocieId(long vendorPostInvocieId);
}
