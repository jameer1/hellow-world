package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class SubDeliveryDocketTO extends ClientTO {
	
	private String sowItemId;
	private String sowItemIdDescription;
	private String measuringUnit;
	private String contractQTY;
	private String cumulativeProgressQTYToInvoicePeriod;
	private String qtyClaimedForPreviousPeriod;
	private String netProgressQTY;
	private double rate;   
	private double amount;
	private String comments;
	public String getSowItemId() {
		return sowItemId;
	}
	public void setSowItemId(String sowItemId) {
		this.sowItemId = sowItemId;
	}
	public String getSowItemIdDescription() {
		return sowItemIdDescription;
	}
	public void setSowItemIdDescription(String sowItemIdDescription) {
		this.sowItemIdDescription = sowItemIdDescription;
	}
	public String getMeasuringUnit() {
		return measuringUnit;
	}
	public void setMeasuringUnit(String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}
	public String getContractQTY() {
		return contractQTY;
	}
	public void setContractQTY(String contractQTY) {
		this.contractQTY = contractQTY;
	}
	public String getCumulativeProgressQTYToInvoicePeriod() {
		return cumulativeProgressQTYToInvoicePeriod;
	}
	public void setCumulativeProgressQTYToInvoicePeriod(String cumulativeProgressQTYToInvoicePeriod) {
		this.cumulativeProgressQTYToInvoicePeriod = cumulativeProgressQTYToInvoicePeriod;
	}
	public String getQtyClaimedForPreviousPeriod() {
		return qtyClaimedForPreviousPeriod;
	}
	public void setQtyClaimedForPreviousPeriod(String qtyClaimedForPreviousPeriod) {
		this.qtyClaimedForPreviousPeriod = qtyClaimedForPreviousPeriod;
	}
	public String getNetProgressQTY() {
		return netProgressQTY;
	}
	public void setNetProgressQTY(String netProgressQTY) {
		this.netProgressQTY = netProgressQTY;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
	
}
