package com.rjtech.finance.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "FINANCE_VENDOR_POST_INVOICE")
public class VendorPostInvoiceEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "FIN_VENDOR_POST_INVOICE_ID")
	private Long id;

	@Column(name = "EPS_NAME")
	private String epsName;
	@Column(name = "EPS_ID")
	private String epsId;
	@Column(name = "PROJECT_ID")
	private String projectId;
	@Column(name = "PROJECT_NAME")
	private String projectName;
	@Column(name = "PO_NUMBER")
	private String poNumber;
	@Column(name = "PO_DESCRIPTION")
	private String poDescription;
	@Column(name = "VENDOR_NAME")
	private String vendorName;
	@Column(name = "VENDOR_ID")
	private String vendorId;
	@Column(name = "ORIGINATOR_EMPLOYEE_ID")
	private String originatorEmployeeId;
	@Column(name = "ORIGINATOR_EMPLOYEE_NAME")
	private String originatorEmployeeName;
	@Column(name = "INVOICE_DATE")
	private String invocieDate;
	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;
	@Column(name = "INVOICE_RECEIVED_DATE")
	private String invoiceReceivedDate;
	@Column(name = "INVOICE_PERIOD_FROM_DATE")
	private String invoicePeriodFromDate;
	@Column(name = "INVOICE_PERIOD_TO_DATE")
	private String invoicePeriodToDate;
	@Column(name = "PAYMENT_DUE_DATE")
	private String paymentDueDate;
	@Column(name = "PROPOSED_PAYMENT_DATE")
	private String proposedPaymentDate;
	
	@Column(name = "EXPENDITURE_TYPE")
	private String expenditureType;

	@Column(name = "APPROVER_ID")
	private String approverId;

	@Column(name = "APPROVER_NAME")
	private String approverName;

	@Column(name = "IS_SUBMIT_APPROVER")
	private boolean isSubmitForApprover;

	@Column(name = "IS_ACCOUNT_VERIFIED")
	private boolean isAccountDetailsVerified;

	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "ACCOUNT_NAME")
	private String accountName;

	@Column(name = "ACCOUNT_NUMBER")
	private Long accountNumber;

	@Column(name = "BANK_CODE")
	private String bankCode;

	@Column(name = "SWIFT_CODE")
	private String swiftCode;
	
	@Column(name = "PRE_CONTRACT_ID")
	private int preContractId;

	@ManyToOne
	@JoinColumn(name = "FPD_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;

	@ManyToOne
	@JoinColumn(name = "FPD_UPDATED_BY")
	private UserMstrEntity updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FPD_CREATED_ON", updatable = false)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FPD_UPDATED_ON")
	private Date updatedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEpsName() {
		return epsName;
	}

	public void setEpsName(String epsName) {
		this.epsName = epsName;
	}

	public String getEpsId() {
		return epsId;
	}

	public void setEpsId(String epsId) {
		this.epsId = epsId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getPoDescription() {
		return poDescription;
	}

	public void setPoDescription(String poDescription) {
		this.poDescription = poDescription;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	

	public String getOriginatorEmployeeId() {
		return originatorEmployeeId;
	}

	public void setOriginatorEmployeeId(String originatorEmployeeId) {
		this.originatorEmployeeId = originatorEmployeeId;
	}

	public String getOriginatorEmployeeName() {
		return originatorEmployeeName;
	}

	public void setOriginatorEmployeeName(String originatorEmployeeName) {
		this.originatorEmployeeName = originatorEmployeeName;
	}

	public String getInvocieDate() {
		return invocieDate;
	}

	public void setInvocieDate(String invocieDate) {
		this.invocieDate = invocieDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceReceivedDate() {
		return invoiceReceivedDate;
	}

	public void setInvoiceReceivedDate(String invoiceReceivedDate) {
		this.invoiceReceivedDate = invoiceReceivedDate;
	}

	public String getInvoicePeriodFromDate() {
		return invoicePeriodFromDate;
	}

	public void setInvoicePeriodFromDate(String invoicePeriodFromDate) {
		this.invoicePeriodFromDate = invoicePeriodFromDate;
	}

	public String getInvoicePeriodToDate() {
		return invoicePeriodToDate;
	}

	public void setInvoicePeriodToDate(String invoicePeriodToDate) {
		this.invoicePeriodToDate = invoicePeriodToDate;
	}

	public String getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public String getProposedPaymentDate() {
		return proposedPaymentDate;
	}

	public void setProposedPaymentDate(String proposedPaymentDate) {
		this.proposedPaymentDate = proposedPaymentDate;
	}

	

	public String getExpenditureType() {
		return expenditureType;
	}

	public void setExpenditureType(String expenditureType) {
		this.expenditureType = expenditureType;
	}

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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public UserMstrEntity getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserMstrEntity createdBy) {
		this.createdBy = createdBy;
	}

	public UserMstrEntity getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(UserMstrEntity updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getPreContractId() {
		return preContractId;
	}

	public void setPreContractId(int preContractId) {
		this.preContractId = preContractId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	
	

}
