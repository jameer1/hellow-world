package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.VendorInvoiceAmountEntity;

@Repository
public interface VendorInvoiceAmountRepository extends FinanceBaseRepository<VendorInvoiceAmountEntity, Long>{
	
	List<VendorInvoiceAmountEntity> findByVendorPostInvocieId(long vendorPostInvocieId);

}
