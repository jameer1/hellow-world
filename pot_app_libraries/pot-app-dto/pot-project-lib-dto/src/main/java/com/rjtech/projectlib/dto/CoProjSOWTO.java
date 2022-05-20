package com.rjtech.projectlib.dto;

import com.rjtech.common.dto.ProjectTO;

public class CoProjSOWTO extends ProjectTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6457749390408694122L;
	
	private Long id;
	private String coSowItemId;
	private String coSowItemDescription;
	private String coSorItemId;
	private String unitOfMeasure;
	private Integer approvedSowQty;
	private Integer pendingSowQty;
	private Integer currentSowQty;
	private Integer cumulativeSowQty;
	private String  currency;
	private Double  approvedRates;
	private Double  newRates;
	private Double  newItemRate;
	
	private Double  prevApprovedAmt;
	private Double  pendingOrderAmt;
	private Double  currentAmt;
	private String  notes;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
