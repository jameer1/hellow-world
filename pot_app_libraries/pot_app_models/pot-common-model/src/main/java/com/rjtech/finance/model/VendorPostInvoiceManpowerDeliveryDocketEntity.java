package com.rjtech.finance.model;

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
@Table(name = "FINANCE_VENDOR_POST_INVOICE_MANPOWER_DELIVERY_DOCKET")
public class VendorPostInvoiceManpowerDeliveryDocketEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FIN_VEN_INV_MAN_DEL_DOCKET_ID")
	private Long id;
	
	@Column(name = "DOCUMENT_NUMBER")
	private String documentNumber;
	
	@Column(name = "DOCUMENT_TYPE")
	private String documentType;
	
	@Column(name = "DOCUMENT_PERIOD")
	private String documentPeriod;
	
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name = "EMPLOYEE_FIRST_NAME")
	private String employeeFirstName;
	
	@Column(name = "EMPLOYEE_LAST_NAME")
	private String employeeLastName;
	
	@Column(name = "TRADE")
	private String trade;
	
	@Column(name = "DATE_OF_ENROLLMENT")
	private String dateOfEntrollment;
	
	@Column(name = "DATE_OF_MOBILISATION")
	private String dateOfMobilisation;
	
	@Column(name = "ACTUAL_DATE_OF_DEMOBILISATION")
	private String ActualDateOfDeMobilisation;
	
	@Column(name = "SCHEDULE_ITEM_ID")
	private String scheduleItemId;
	
	@Column(name = "SCHEDULE_ITEM_DESCRIPTION")
	private String scheduleItemDescription;
	
	@Column(name = "UNIT_OF_MEASURE")
	private String unitOfMeasure;
	
	@Column(name = "WAGE_FACTOR")
	private String wageFactor;
	
	@Column(name = "GROSS_UTILISATION_RATIO")
	private String grossUtilisationRatio;
		
	@Column(name = "NET_UTILISATION_RATIO")
	private String netUtilisationRaio;
	
	@Column(name = "QTY")
	private double qty;
		
	@Column(name = "PRESENT_AND_PAID_LEAVE_DAYS")
	private String presentAndPaidLeaveDays;
	
	@Column(name = "ABSENT_AND_UNPAID_LEAVE_DAYS")
	private String absentAndUnpaidLeaveDays;
	
	@Column(name = "NON_WORKING_DAYS")
	private String nonworkingDays;
		
	@Column(name = "GROSS_BILLABLE_PERIOD_IN_DAYS")
	private String grossBillablePeriodInDays;
	
	@Column(name = "NON_BILLABLE_PERIOD_IN_DAYS")
	private String nonBillablePeriodInDays;
	
	@Column(name = "CALENDAR_DAYS_COUNT_AS_INVOICE_PERIOD")
	private String calendarDaysCountAsInvoiceperiod;
	
	@Column(name = "RATE")
	private double rate;
	
	@Column(name = "AMOUNT")
	private double amount;
	
	@Column(name = "COMMENTS")
	private String comments;
	
	@Column(name = "TYPE_OF_SECTION")
	private String typeOfSection;
	
	@Column(name = "FIN_VENDOR_POST_INVOICE_ID")
	private long vendorPostInvocieId;
	
	
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

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentPeriod() {
		return documentPeriod;
	}

	public void setDocumentPeriod(String documentPeriod) {
		this.documentPeriod = documentPeriod;
	}

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

	public String getDateOfEntrollment() {
		return dateOfEntrollment;
	}

	public void setDateOfEntrollment(String dateOfEntrollment) {
		this.dateOfEntrollment = dateOfEntrollment;
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

	

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTypeOfSection() {
		return typeOfSection;
	}

	public void setTypeOfSection(String typeOfSection) {
		this.typeOfSection = typeOfSection;
	}

	public long getVendorPostInvocieId() {
		return vendorPostInvocieId;
	}

	public void setVendorPostInvocieId(long vendorPostInvocieId) {
		this.vendorPostInvocieId = vendorPostInvocieId;
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

	public String getWageFactor() {
		return wageFactor;
	}

	public void setWageFactor(String wageFactor) {
		this.wageFactor = wageFactor;
	}
	
	
}
