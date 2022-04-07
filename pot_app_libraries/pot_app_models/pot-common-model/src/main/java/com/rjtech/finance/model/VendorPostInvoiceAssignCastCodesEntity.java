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
@Table(name = "FINANCE_VENDOR_POST_INVOICE_COST_CODE")
public class VendorPostInvoiceAssignCastCodesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "FIN_VEN_INV_ASSIGN_COST_CODE_ID")
	private Long id;
	
	@Column(name = "COST_CODE_ID")
	private long costCodeId;
	
	@Column(name = "COST_CODE_DESC")
	private String costCodeDescription;
	
	@Column(name = "COST_CODE_PERCENTAGE")
	private String costCodePercentage;
	
	@Column(name = "COST_CODE_AMOUNT")
	private String costCodeAmount;
	
	@Column(name="FIN_VENDOR_POST_INVOICE_ID")
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

	public long getCostCodeId() {
		return costCodeId;
	}

	public void setCostCodeId(long costCodeId) {
		this.costCodeId = costCodeId;
	}

	public String getCostCodeDescription() {
		return costCodeDescription;
	}

	public void setCostCodeDescription(String costCodeDescription) {
		this.costCodeDescription = costCodeDescription;
	}

	public String getCostCodePercentage() {
		return costCodePercentage;
	}

	public void setCostCodePercentage(String costCodePercentage) {
		this.costCodePercentage = costCodePercentage;
	}

	public String getCostCodeAmount() {
		return costCodeAmount;
	}

	public void setCostCodeAmount(String costCodeAmount) {
		this.costCodeAmount = costCodeAmount;
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

	public long getVendorPostInvocieId() {
		return vendorPostInvocieId;
	}

	public void setVendorPostInvocieId(long vendorPostInvocieId) {
		this.vendorPostInvocieId = vendorPostInvocieId;
	}
	
	
}
