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
@Table(name = "FINANCE_VENDOR_POST_INVOICE_PLANTS_DELIVERY_DOCKET")
public class VendorPostInvoicePlantsDeliveryDocketEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FIN_VEN_INV_PLANTS_DEL_DOCKET_ID")
	private Long id;
	
	@Column(name = "WORK_DAIRY_NUMBER")
	private String workDairyNumber;
	
	@Column(name = "WORK_DAIRY_DATE")
	private String WorkDairyDate;
	
	@Column(name = "PLANT_WORK_STATUS_RECORD_ID")
	private String plantWorkStatusRecordId;
	
	@Column(name = "PLANT_WORK_STATUS_RECORD_MONTH")
	private String plantWorkStatusRecordMonth;
	
	@Column(name = "PLANT_ID")
	private String plantId;
	
	@Column(name = "PALNT_RECEIVED_DATE")
	private String plantsReceivedDate;
	
	@Column(name = "DOCKET_NUMBER")
	private String docketNumber;
	
	@Column(name = "DOCKET_DATE")
	private String docketDate;
	
	@Column(name = "PLANT_OWNER")
	private String plantOwner;
	
	@Column(name = "WORK_STATUS_CATEGORY")
	private String workStatusCategory;
	
	@Column(name = "SHIFT_CATEGORY")
	private String shiftCategory;
	
	@Column(name = "UTILISATION_CATEGORY")
	private String utilisationCategory;
	
	@Column(name = "MOBILISATION")
	private String mobilisation;
	
	@Column(name = "ACTUAL_DATE_OF_DE_MOBILISATION")
	private String actualDateOfDeMobilisation;
	
	@Column(name = "PLANT_DESCRIPTION")
	private String plantDescription;
	
		
	@Column(name = "SCHEDULE_ITEM_ID")
	private String scheduleItemId;
	
	@Column(name = "SCHEDULE_ITEM_DESCRIPTION")
	private String scheduleItemDescription;
	
	@Column(name = "UNIT_OF_MEASURE")
	private String unitOfMeasure;
	
	@Column(name = "QTY")
	private double qty;
			
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

	public String getWorkDairyNumber() {
		return workDairyNumber;
	}

	public void setWorkDairyNumber(String workDairyNumber) {
		this.workDairyNumber = workDairyNumber;
	}

	public String getWorkDairyDate() {
		return WorkDairyDate;
	}

	public void setWorkDairyDate(String workDairyDate) {
		WorkDairyDate = workDairyDate;
	}

	public String getPlantWorkStatusRecordId() {
		return plantWorkStatusRecordId;
	}

	public void setPlantWorkStatusRecordId(String plantWorkStatusRecordId) {
		this.plantWorkStatusRecordId = plantWorkStatusRecordId;
	}

	public String getPlantWorkStatusRecordMonth() {
		return plantWorkStatusRecordMonth;
	}

	public void setPlantWorkStatusRecordMonth(String plantWorkStatusRecordMonth) {
		this.plantWorkStatusRecordMonth = plantWorkStatusRecordMonth;
	}

	public String getPlantId() {
		return plantId;
	}

	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}

	public String getPlantsReceivedDate() {
		return plantsReceivedDate;
	}

	public void setPlantsReceivedDate(String plantsReceivedDate) {
		this.plantsReceivedDate = plantsReceivedDate;
	}

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

	public String getPlantOwner() {
		return plantOwner;
	}

	public void setPlantOwner(String plantOwner) {
		this.plantOwner = plantOwner;
	}

	public String getWorkStatusCategory() {
		return workStatusCategory;
	}

	public void setWorkStatusCategory(String workStatusCategory) {
		this.workStatusCategory = workStatusCategory;
	}

	public String getShiftCategory() {
		return shiftCategory;
	}

	public void setShiftCategory(String shiftCategory) {
		this.shiftCategory = shiftCategory;
	}

	public String getUtilisationCategory() {
		return utilisationCategory;
	}

	public void setUtilisationCategory(String utilisationCategory) {
		this.utilisationCategory = utilisationCategory;
	}

	public String getMobilisation() {
		return mobilisation;
	}

	public void setMobilisation(String mobilisation) {
		this.mobilisation = mobilisation;
	}

	public String getActualDateOfDeMobilisation() {
		return actualDateOfDeMobilisation;
	}

	public void setActualDateOfDeMobilisation(String actualDateOfDeMobilisation) {
		this.actualDateOfDeMobilisation = actualDateOfDeMobilisation;
	}

	public String getPlantDescription() {
		return plantDescription;
	}

	public void setPlantDescription(String plantDescription) {
		this.plantDescription = plantDescription;
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
	
	
}
