package com.rjtech.projectlib.model;

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
@Table(name = "co_scope_of_work")
public class ChangeOrderSOWEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7992431890687991562L;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_SOW_ID")
    private Long id;
	
	@Column(name = "co_sow_item_id")
	private String coSowItemId;
	@Column(name = "co_sow_item_desc")
	private String coSowItemDescription;
	@Column(name = "CO_SOR_ITEM_ID")
	private String coSorItemId;
	@Column(name = "unit_of_measures")
	private String unitOfMeasure;
	@Column(name = "approved_sow_qty")
	private Integer approvedSowQty;
	@Column(name = "pending_sow_qty")
	private Integer pendingSowQty;
	@Column(name = "current_sow_qty")
	private Integer currentSowQty;
	@Column(name = "cumulative_sow_qty")
	private Integer cumulativeSowQty;
	@Column(name = "currency")
	private String  currency;
	@Column(name = "apprvd_contrct_rates")
	private Double  approvedRates;
	@Column(name = "praposed_rates")
	private Double  newRates;
	@Column(name = "new_item_rate")
	private Double  newItemRate;
	@Column(name = "prev_approved_amt")
	private Double  prevApprovedAmt;
	@Column(name = "pend_order_amt")
	private Double  pendingOrderAmt;
	@Column(name = "current_amt")
	private Double  currentAmt;

	@Column(name = "cumulative_amt")
	private Double cumulativeAmt;

	@Column(name = "notes")
	private String notes;

	@Column(name = "STATUS")
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON", updatable = false)
	private Date createdOn;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private UserMstrEntity updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_ON")
	private Date updatedOn;
	
	@ManyToOne
	@JoinColumn(name = "CO_ID", updatable = false)
	private ChangeOrderMstrEntity changeOrderMstr;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCoSowItemId() {
		return coSowItemId;
	}


	public void setCoSowItemId(String coSowItemId) {
		this.coSowItemId = coSowItemId;
	}


	public String getCoSowItemDescription() {
		return coSowItemDescription;
	}


	public void setCoSowItemDescription(String coSowItemDescription) {
		this.coSowItemDescription = coSowItemDescription;
	}


	public String getCoSorItemId() {
		return coSorItemId;
	}


	public void setCoSorItemId(String coSorItemId) {
		this.coSorItemId = coSorItemId;
	}


	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}


	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}


	public Integer getApprovedSowQty() {
		return approvedSowQty;
	}


	public void setApprovedSowQty(Integer approvedSowQty) {
		this.approvedSowQty = approvedSowQty;
	}


	public Integer getPendingSowQty() {
		return pendingSowQty;
	}


	public void setPendingSowQty(Integer pendingSowQty) {
		this.pendingSowQty = pendingSowQty;
	}


	public Integer getCurrentSowQty() {
		return currentSowQty;
	}


	public void setCurrentSowQty(Integer currentSowQty) {
		this.currentSowQty = currentSowQty;
	}


	public Integer getCumulativeSowQty() {
		return cumulativeSowQty;
	}


	public void setCumulativeSowQty(Integer cumulativeSowQty) {
		this.cumulativeSowQty = cumulativeSowQty;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public Double getApprovedRates() {
		return approvedRates;
	}


	public void setApprovedRates(Double approvedRates) {
		this.approvedRates = approvedRates;
	}


	public Double getNewRates() {
		return newRates;
	}


	public void setNewRates(Double newRates) {
		this.newRates = newRates;
	}


	public Double getNewItemRate() {
		return newItemRate;
	}


	public void setNewItemRate(Double newItemRate) {
		this.newItemRate = newItemRate;
	}


	public Double getPrevApprovedAmt() {
		return prevApprovedAmt;
	}


	public void setPrevApprovedAmt(Double prevApprovedAmt) {
		this.prevApprovedAmt = prevApprovedAmt;
	}


	public Double getPendingOrderAmt() {
		return pendingOrderAmt;
	}


	public void setPendingOrderAmt(Double pendingOrderAmt) {
		this.pendingOrderAmt = pendingOrderAmt;
	}


	public Double getCurrentAmt() {
		return currentAmt;
	}


	public void setCurrentAmt(Double currentAmt) {
		this.currentAmt = currentAmt;
	}


	public Double getCumulativeAmt() {
		return cumulativeAmt;
	}


	public void setCumulativeAmt(Double cumulativeAmt) {
		this.cumulativeAmt = cumulativeAmt;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public UserMstrEntity getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(UserMstrEntity createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public UserMstrEntity getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(UserMstrEntity updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Date getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}


	public ChangeOrderMstrEntity getChangeOrderMstr() {
		return changeOrderMstr;
	}


	public void setChangeOrderMstr(ChangeOrderMstrEntity changeOrderMstr) {
		this.changeOrderMstr = changeOrderMstr;
	}
	
	
}
