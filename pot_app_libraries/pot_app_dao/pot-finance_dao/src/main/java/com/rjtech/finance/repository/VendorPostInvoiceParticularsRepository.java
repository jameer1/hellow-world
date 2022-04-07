package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.VendorPostInvoiceEntity;

@Repository
public interface VendorPostInvoiceParticularsRepository extends FinanceBaseRepository<VendorPostInvoiceEntity, Long> {

	List<VendorPostInvoiceEntity> findByPreContractId(int preContractId);
	
}
