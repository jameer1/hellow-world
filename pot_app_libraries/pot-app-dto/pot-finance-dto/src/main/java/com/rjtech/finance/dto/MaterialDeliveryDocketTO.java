package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class MaterialDeliveryDocketTO extends ClientTO {
	
	private String docketNumber;
	private String docketDate;
	private String materialReleasedDate;
	private String scheduleItemId;
	private String scheduleItemDescription;
	private String unitOfMeasure;
	private String qtyAsPerContract;
	private String processQtyUptoInvoicePeriod;
	private String claimedQtyToPreviousPeriod;
	private String claimedQtyToCurrentPeriod;
	private double rateAmount;
	private String receiverComments;
	
	public String getDocketNumber() {
		return docketNumber;
	}
	public void setDocketNumber(String docketNumber) {
		this.docketNumber = docketNumber;
	}
	public String getDocketDate() {
		return docketDate;
	}
	public void setDocketDate(String docketDate) {
		this.docketDate = docketDate;
	}
	public String getMaterialReleasedDate() {
		return materialReleasedDate;
	}
	public void setMaterialReleasedDate(String materialReleasedDate) {
		this.materialReleasedDate = materialReleasedDate;
	}
	public String getScheduleItemId() {
		return scheduleItemId;
	}
	public void setScheduleItemId(String scheduleItemId) {
		this.scheduleItemId = scheduleItemId;
	}
	public String getScheduleItemDescription() {
		return scheduleItemDescription;
	}
	public void setScheduleItemDescription(String scheduleItemDescription) {
		this.scheduleItemDescription = scheduleItemDescription;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public String getQtyAsPerContract() {
		return qtyAsPerContract;
	}
	public void setQtyAsPerContract(String qtyAsPerContract) {
		this.qtyAsPerContract = qtyAsPerContract;
	}
	public String getProcessQtyUptoInvoicePeriod() {
		return processQtyUptoInvoicePeriod;
	}
	public void setProcessQtyUptoInvoicePeriod(String processQtyUptoInvoicePeriod) {
		this.processQtyUptoInvoicePeriod = processQtyUptoInvoicePeriod;
	}
	public String getClaimedQtyToPreviousPeriod() {
		return claimedQtyToPreviousPeriod;
	}
	public void setClaimedQtyToPreviousPeriod(String claimedQtyToPreviousPeriod) {
		this.claimedQtyToPreviousPeriod = claimedQtyToPreviousPeriod;
	}
	public String getClaimedQtyToCurrentPeriod() {
		return claimedQtyToCurrentPeriod;
	}
	public void setClaimedQtyToCurrentPeriod(String claimedQtyToCurrentPeriod) {
		this.claimedQtyToCurrentPeriod = claimedQtyToCurrentPeriod;
	}
	public double getRateAmount() {
		return rateAmount;
	}
	public void setRateAmount(double rateAmount) {
		this.rateAmount = rateAmount;
	}
	public String getReceiverComments() {
		return receiverComments;
	}
	public void setReceiverComments(String receiverComments) {
		this.receiverComments = receiverComments;
	}
	
}
