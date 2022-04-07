package com.rjtech.finance.req;

import com.rjtech.common.dto.ClientTO;

public class GetVendorPostInvoiceRequest extends ClientTO {
	
	private int preContractId;
	private int projectId;
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
