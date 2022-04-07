package com.rjtech.finance.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "FINANCE_VENDOR_POST_INVOICE_AMOUNT")
public class VendorInvoiceAmountEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "FIN_VENDOR_INVOICE_SEC_ID")
	private Long id;

	@Column(name = "NET_INVOICE_AMOUNT")
	private double netInvoiceAmount;
	@Column(name = "GST_INVOICE_AMOUNT")
	private double gstInvoiceAmount;
	@Column(name = "TOTAL_INVOICE_AMOUNT")
	private double totalInvoiceAmount;
	@Column(name = "EXPENDITURE_TYPE")
	private String expenditureType;
	@Column(name = "FIN_VENDOR_POST_INVOICE_ID")
	private long vendorPostInvocieId;
	@Column(name = "INVOICE_AMOUNT_TYPE")
	private String invoiceAmountType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getNetInvoiceAmount() {
		return netInvoiceAmount;
	}
	public void setNetInvoiceAmount(double netInvoiceAmount) {
		this.netInvoiceAmount = netInvoiceAmount;
	}
	public double getGstInvoiceAmount() {
		return gstInvoiceAmount;
	}
	public void setGstInvoiceAmount(double gstInvoiceAmount) {
		this.gstInvoiceAmount = gstInvoiceAmount;
	}
	public double getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}
	public void setTotalInvoiceAmount(double totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}
	public String getExpenditureType() {
		return expenditureType;
	}
	public void setExpenditureType(String expenditureType) {
		this.expenditureType = expenditureType;
	}
	public long getVendorPostInvocieId() {
		return vendorPostInvocieId;
	}
	public void setVendorPostInvocieId(long vendorPostInvocieId) {
		this.vendorPostInvocieId = vendorPostInvocieId;
	}
	public String getInvoiceAmountType() {
		return invoiceAmountType;
	}
	public void setInvoiceAmountType(String invoiceAmountType) {
		this.invoiceAmountType = invoiceAmountType;
	}
	
	
	
	
}
