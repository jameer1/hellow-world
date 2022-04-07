package com.rjtech.finance.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rjtech.finance.model.VendorPostInvoiceManpowerDeliveryDocketEntity;

@Repository
public interface VendorPostInvoiceManpowerDeliveryDocketRepository extends FinanceBaseRepository<VendorPostInvoiceManpowerDeliveryDocketEntity, Long>{

	List<VendorPostInvoiceManpowerDeliveryDocketEntity> findByVendorPostInvocieId(long vendorPostInvocieId);
}
