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
@Table(name = "FINANCE_VENDOR_POST_INVOICE_SUB_CONTRACT_DELIVERY_DOCKET")
public class VendorPostInvoiceSubDeliveryDocketEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FIN_VEN_INV_SUB_DEL_DOCKET_ID")
	private Long id;
	
	@Column(name = "SOW_ITEM_ID")
	private String sowItemId;
	
	@Column(name = "SOW_ITEM_DESC")
	private String sowItemIdDescription;
	
	@Column(name = "MEASURING_UNIT")
	private String measuringUnit;
	
	@Column(name = "CONTRACT_QTY")
	private String contractQTY;
	
	@Column(name = "CUMULATIVE_PROGRESS_QTY_TO_INVOICE_PERIOD")
	private String cumulativeProgressQTYToInvoicePeriod;
	
	@Column(name = "QTY_CLAIMED_FOR_PREVIOUS_PERIOD")
	private String qtyClaimedForPreviousPeriod;
	
	@Column(name = "NET_PROGRESS_QTY")
	private String netProgressQTY;
	
	@Column(name = "RATE")
	private double rate;
	
	@Column(name = "AMOUNT")
	private double amount;
	
	@Column(name = "COMMENTS")
	private String comments;
	
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
	
	
}
