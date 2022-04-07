package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.VendorPostInvoiceSubDeliveryDocketEntity;

@Repository
public interface VendorPostInvoiceSubDeliveryDocketRepository extends FinanceBaseRepository<VendorPostInvoiceSubDeliveryDocketEntity, Long>{
	List<VendorPostInvoiceSubDeliveryDocketEntity> findByVendorPostInvocieId(long vendorPostInvocieId);
}
