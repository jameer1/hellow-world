package com.rjtech.finance.req;

import com.rjtech.common.dto.ClientTO;

public class GetVendorPostInvoiceRequest extends ClientTO {
	
	private int preContractId;
	private int projectId;
	private String procurementCat;
	private String invoiceNumber;
	
	
	public String getProcurementCat() {
		return procurementCat;
	}
	public void setProcurementCat(String procurementCat) {
		this.procurementCat = procurementCat;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public int getPreContractId() {
		return preContractId;
	}
	public void setPreContractId(int preContractId) {
		this.preContractId = preContractId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
}
