package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;


public class InvoiceSectionTO extends ClientTO { 

	private double netInvoiceAmount;
	private double gstInvoiceAmount;
	private double totalInvoiceAmount;
	private String expenditureType;
	
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
		
}
