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
@Table(name = "FINANCE_VENDOR_POST_INVOICE_MATERIAL_DELIVERY_DOCKET")
public class VendorPostInvoiceMaterialDeliveryDocketEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "FIN_VEN_INV_MAT_DEL_DOCKET_ID")
	private Long id;
	
	@Column(name = "DOCKET_NUMBER")
	private String docketNumber;
	
	@Column(name = "DOCKET_DATE")
	private String docketDate;
	
	@Column(name = "MATERIAL_REL_DATE")
	private String materialReleasedDate;
	
	@Column(name = "SCHEDULE_ITEM_ID")
	private String scheduleItemId;
	
	@Column(name = "SCHEDULE_ITEM_DESC")
	private String scheduleItemDescription;
	
	@Column(name = "UNIT_OF_MEASURE")
	private String unitOfMeasure;
	
	@Column(name = "QTY_AS_PER_CONTRACT")
	private String qtyAsPerContract;
	
	@Column(name = "PROCESS_QTY_UPTO_INVOICE_PERIOD")
	private String processQtyUptoInvoicePeriod;
	
	@Column(name = "CLAIMED_QTY_PREVIOUS_PERIOD")
	private String claimedQtyToPreviousPeriod;
	
	@Column(name = "CLAIMED_QTY_CURRENT_PERIOD")
	private String claimedQtyToCurrentPeriod;
	
	@Column(name = "RATE_AMOUNT")
	private double rateAmount;
	
	@Column(name = "RECEIVER_COMMENTS")
	private String receiverComments;
	
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

	public String getClaimedQtyToPreviousPeriod() {
		return claimedQtyToPreviousPeriod;
	}

	public void setClaimedQtyToPreviousPeriod(String claimedQtyToPreviousPeriod) {
		this.claimedQtyToPreviousPeriod = claimedQtyToPreviousPeriod;
	}

	public long getVendorPostInvocieId() {
		return vendorPostInvocieId;
	}

	public void setVendorPostInvocieId(long vendorPostInvocieId) {
		this.vendorPostInvocieId = vendorPostInvocieId;
	}
	
	

}
