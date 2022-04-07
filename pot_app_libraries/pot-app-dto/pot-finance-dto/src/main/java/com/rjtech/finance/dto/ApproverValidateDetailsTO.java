package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class ApproverValidateDetailsTO extends ClientTO { 

	private String approverId;
	private String approverName;
	private boolean isSubmitForApprover;
	private boolean isAccountDetailsVerified;
	
	public String getApproverId() {
		return approverId;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public boolean isSubmitForApprover() {
		return isSubmitForApprover;
	}
	public void setSubmitForApprover(boolean isSubmitForApprover) {
		this.isSubmitForApprover = isSubmitForApprover;
	}
	public boolean isAccountDetailsVerified() {
		return isAccountDetailsVerified;
	}
	public void setAccountDetailsVerified(boolean isAccountDetailsVerified) {
		this.isAccountDetailsVerified = isAccountDetailsVerified;
	}
	
	
}
