package com.rjtech.finance.repository;

import java.util.List;

import com.rjtech.finance.model.VendorPostInvoiceMaterialDeliveryDocketEntity;

public interface VendorPostInvoiceMaterialDeliveryDocketRepository extends FinanceBaseRepository<VendorPostInvoiceMaterialDeliveryDocketEntity, Long>{

	List<VendorPostInvoiceMaterialDeliveryDocketEntity> findByVendorPostInvocieId(long vendorPostInvocieId);
}
