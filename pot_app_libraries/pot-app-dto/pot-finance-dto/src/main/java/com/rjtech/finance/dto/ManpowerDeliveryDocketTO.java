package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class ManpowerDeliveryDocketTO extends ClientTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String documentType;
	private String documentNumber;
	private String documentPeriod;
	private String employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String trade;
	private String dateOfEnrollment;
	private String dateOfMobilisation;
	private String ActualDateOfDeMobilisation;
	private String scheduleItemId;
	private String scheduleItemDescription;
	private String unitOfMeasure;
	private String wageFactor;
	private String grossUtilisationRatio;
	private String netUtilisationRaio;
	private double qty;
	private String presentAndPaidLeaveDays;
	private String absentAndUnpaidLeaveDays;
	private String nonworkingDays;
	private String grossBillablePeriodInDays;
	private String nonBillablePeriodInDays;
	private String calendarDaysCountAsInvoiceperiod;
	private double rate;
	private double amount;
	private String comments;
	private String typeOfSection;
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getDateOfEnrollment() {
		return dateOfEnrollment;
	}
	public void setDateOfEnrollment(String dateOfEnrollment) {
		this.dateOfEnrollment = dateOfEnrollment;
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
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
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
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getDocumentPeriod() {
		return documentPeriod;
	}
	public void setDocumentPeriod(String documentPeriod) {
		this.documentPeriod = documentPeriod;
	}
	public String getDateOfMobilisation() {
		return dateOfMobilisation;
	}
	public void setDateOfMobilisation(String dateOfMobilisation) {
		this.dateOfMobilisation = dateOfMobilisation;
	}
	public String getActualDateOfDeMobilisation() {
		return ActualDateOfDeMobilisation;
	}
	public void setActualDateOfDeMobilisation(String actualDateOfDeMobilisation) {
		ActualDateOfDeMobilisation = actualDateOfDeMobilisation;
	}
	public String getWageFactor() {
		return wageFactor;
	}
	public void setWageFactor(String wageFactor) {
		this.wageFactor = wageFactor;
	}
	public String getGrossUtilisationRatio() {
		return grossUtilisationRatio;
	}
	public void setGrossUtilisationRatio(String grossUtilisationRatio) {
		this.grossUtilisationRatio = grossUtilisationRatio;
	}
	public String getNetUtilisationRaio() {
		return netUtilisationRaio;
	}
	public void setNetUtilisationRaio(String netUtilisationRaio) {
		this.netUtilisationRaio = netUtilisationRaio;
	}
	public String getPresentAndPaidLeaveDays() {
		return presentAndPaidLeaveDays;
	}
	public void setPresentAndPaidLeaveDays(String presentAndPaidLeaveDays) {
		this.presentAndPaidLeaveDays = presentAndPaidLeaveDays;
	}
	public String getAbsentAndUnpaidLeaveDays() {
		return absentAndUnpaidLeaveDays;
	}
	public void setAbsentAndUnpaidLeaveDays(String absentAndUnpaidLeaveDays) {
		this.absentAndUnpaidLeaveDays = absentAndUnpaidLeaveDays;
	}
	public String getNonworkingDays() {
		return nonworkingDays;
	}
	public void setNonworkingDays(String nonworkingDays) {
		this.nonworkingDays = nonworkingDays;
	}
	public String getGrossBillablePeriodInDays() {
		return grossBillablePeriodInDays;
	}
	public void setGrossBillablePeriodInDays(String grossBillablePeriodInDays) {
		this.grossBillablePeriodInDays = grossBillablePeriodInDays;
	}
	public String getNonBillablePeriodInDays() {
		return nonBillablePeriodInDays;
	}
	public void setNonBillablePeriodInDays(String nonBillablePeriodInDays) {
		this.nonBillablePeriodInDays = nonBillablePeriodInDays;
	}
	public String getCalendarDaysCountAsInvoiceperiod() {
		return calendarDaysCountAsInvoiceperiod;
	}
	public void setCalendarDaysCountAsInvoiceperiod(String calendarDaysCountAsInvoiceperiod) {
		this.calendarDaysCountAsInvoiceperiod = calendarDaysCountAsInvoiceperiod;
	}
	public String getTypeOfSection() {
		return typeOfSection;
	}
	public void setTypeOfSection(String typeOfSection) {
		this.typeOfSection = typeOfSection;
	}
	
}
