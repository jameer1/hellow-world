package com.rjtech.finance.resp;

import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.finance.dto.ApproverValidateDetailsTO;
import com.rjtech.finance.dto.AssignCostCodesTO;
import com.rjtech.finance.dto.InvoiceSectionTO;
import com.rjtech.finance.dto.InvoiceparticularsTO;
import com.rjtech.finance.dto.ManpowerDeliveryDocketTO;
import com.rjtech.finance.dto.MaterialDeliveryDocketTO;
import com.rjtech.finance.dto.PlantsDeliveryDocketTO;
import com.rjtech.finance.dto.ServiceDeliveryDocketTO;
import com.rjtech.finance.dto.SubDeliveryDocketTO;
import com.rjtech.finance.dto.VendorBankAccountDetailsTO;

public class VendorInvocieRecordResponse extends AppResp {
  
	private String date;
	private String invoiceStatus;
	private String completedBy;
	private String ResponisblePersonForPendingSteps;
	private String Comments;
	private String invoiceNumber;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public String getCompletedBy() {
		return completedBy;
	}
	public void setCompletedBy(String completedBy) {
		this.completedBy = completedBy;
	}
	public String getResponisblePersonForPendingSteps() {
		return ResponisblePersonForPendingSteps;
	}
	public void setResponisblePersonForPendingSteps(String responisblePersonForPendingSteps) {
		ResponisblePersonForPendingSteps = responisblePersonForPendingSteps;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	
	
	
}
