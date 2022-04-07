package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.VendorPostInvoiceAssignCastCodesEntity;

@Repository
public interface VendorPostInvoiceAssignCastCodesRepository extends FinanceBaseRepository<VendorPostInvoiceAssignCastCodesEntity, Long>{

	List<VendorPostInvoiceAssignCastCodesEntity> findByVendorPostInvocieId(long vendorPostInvocieId);
}
