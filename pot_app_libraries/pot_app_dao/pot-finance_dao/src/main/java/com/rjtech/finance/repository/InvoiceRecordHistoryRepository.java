package com.rjtech.finance.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.finance.model.InvoiceRecordsHistory;

public interface InvoiceRecordHistoryRepository extends FinanceBaseRepository<InvoiceRecordsHistory, Serializable>{

	@Query( "select inr from InvoiceRecordsHistory inr where vendorPostInvoiceId.invoiceNumber=:invoiceNumber")
	List<InvoiceRecordsHistory> findByVendorPostInvocieId(@Param("invoiceNumber") String vendorPostInvocieId);
}
