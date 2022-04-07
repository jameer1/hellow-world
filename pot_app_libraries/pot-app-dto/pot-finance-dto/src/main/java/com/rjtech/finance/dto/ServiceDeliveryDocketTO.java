package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class ServiceDeliveryDocketTO extends ClientTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String scheduleItemId;
	private String scheduleItemDescription;
	private String measuringUnit;
	private String agreementQTY;
	private String cumulativeProgressQTYToInvoicePeriod;
	private String qtyCumulativeClaimedForperviousPeriod;
	private String netProgressQTY;
	private double rate;
	private double amount;
	private String comments;
	
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
	public String getMeasuringUnit() {
		return measuringUnit;
	}
	public void setMeasuringUnit(String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}
	public String getAgreementQTY() {
		return agreementQTY;
	}
	public void setAgreementQTY(String agreementQTY) {
		this.agreementQTY = agreementQTY;
	}
	public String getCumulativeProgressQTYToInvoicePeriod() {
		return cumulativeProgressQTYToInvoicePeriod;
	}
	public void setCumulativeProgressQTYToInvoicePeriod(String cumulativeProgressQTYToInvoicePeriod) {
		this.cumulativeProgressQTYToInvoicePeriod = cumulativeProgressQTYToInvoicePeriod;
	}
	public String getQtyCumulativeClaimedForperviousPeriod() {
		return qtyCumulativeClaimedForperviousPeriod;
	}
	public void setQtyCumulativeClaimedForperviousPeriod(String qtyCumulativeClaimedForperviousPeriod) {
		this.qtyCumulativeClaimedForperviousPeriod = qtyCumulativeClaimedForperviousPeriod;
	}
	public String getNetProgressQTY() {
		return netProgressQTY;
	}
	public void setNetProgressQTY(String netProgressQTY) {
		this.netProgressQTY = netProgressQTY;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
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
	
	
	
}
