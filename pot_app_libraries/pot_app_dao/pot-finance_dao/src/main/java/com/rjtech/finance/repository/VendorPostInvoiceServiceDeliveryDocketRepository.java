package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.VendorPostInvoiceServiceDeliveryDocketEntity;

@Repository
public interface VendorPostInvoiceServiceDeliveryDocketRepository extends FinanceBaseRepository<VendorPostInvoiceServiceDeliveryDocketEntity, Long>{

	List<VendorPostInvoiceServiceDeliveryDocketEntity> findByVendorPostInvocieId(long vendorPostInvocieId);
}
