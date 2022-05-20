package com.rjtech.projectlib.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CoProjCostTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4447331902282231973L;
	
	private Long id;

	private String coCostCode;
	private String coCostCodeDescription ;
	private String currency;

	private BigDecimal approvedLabourCost;
	private BigDecimal approvedPlantCost;
	private BigDecimal approvedMaterialsCost;
	private BigDecimal approvedOtherCost;
	private BigDecimal approvedTotalCost;

	private BigDecimal pendingLabourCost;
	private BigDecimal pendingPlantCost;
	private BigDecimal pendingMaterialsCost;
	private BigDecimal pendingOtherCost;
	private BigDecimal pendingTotalCost;

	private BigDecimal currentLabourCost;
	private BigDecimal currentMaterialsCost;
	private BigDecimal currentPlantCost;
	private BigDecimal currentOtherCost;
	private BigDecimal currentTotalCost;

	private BigDecimal cumulativeLabourCost;
	private BigDecimal cumulativeMaterialsCost;
	private BigDecimal cumulativePlantCost;
	private BigDecimal cumulativeOtherCost;
	private BigDecimal cumulativeTotalCost;
	
	private String status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCoCostCode() {
		return coCostCode;
	}
	public void setCoCostCode(String coCostCode) {
		this.coCostCode = coCostCode;
	}
	public String getCoCostCodeDescription() {
		return coCostCodeDescription;
	}
	public void setCoCostCodeDescription(String coCostCodeDescription) {
		this.coCostCodeDescription = coCostCodeDescription;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getApprovedLabourCost() {
		return approvedLabourCost;
	}
	public void setApprovedLabourCost(BigDecimal approvedLabourCost) {
		this.approvedLabourCost = approvedLabourCost;
	}
	public BigDecimal getApprovedPlantCost() {
		return approvedPlantCost;
	}
	public void setApprovedPlantCost(BigDecimal approvedPlantCost) {
		this.approvedPlantCost = approvedPlantCost;
	}
	public BigDecimal getApprovedOtherCost() {
		return approvedOtherCost;
	}
	public void setApprovedOtherCost(BigDecimal approvedOtherCost) {
		this.approvedOtherCost = approvedOtherCost;
	}
	public BigDecimal getApprovedTotalCost() {
		return approvedTotalCost;
	}
	public void setApprovedTotalCost(BigDecimal approvedTotalCost) {
		this.approvedTotalCost = approvedTotalCost;
	}
	public BigDecimal getPendingLabourCost() {
		return pendingLabourCost;
	}
	public void setPendingLabourCost(BigDecimal pendingLabourCost) {
		this.pendingLabourCost = pendingLabourCost;
	}
	public BigDecimal getPendingPlantCost() {
		return pendingPlantCost;
	}
	public void setPendingPlantCost(BigDecimal pendingPlantCost) {
		this.pendingPlantCost = pendingPlantCost;
	}
	public BigDecimal getPendingOtherCost() {
		return pendingOtherCost;
	}
	public void setPendingOtherCost(BigDecimal pendingOtherCost) {
		this.pendingOtherCost = pendingOtherCost;
	}
	public BigDecimal getPendingTotalCost() {
		return pendingTotalCost;
	}
	public void setPendingTotalCost(BigDecimal pendingTotalCost) {
		this.pendingTotalCost = pendingTotalCost;
	}
	public BigDecimal getCurrentLabourCost() {
		return currentLabourCost;
	}
	public void setCurrentLabourCost(BigDecimal currentLabourCost) {
		this.currentLabourCost = currentLabourCost;
	}
	public BigDecimal getCurrentMaterialsCost() {
		return currentMaterialsCost;
	}
	public void setCurrentMaterialsCost(BigDecimal currentMaterialsCost) {
		this.currentMaterialsCost = currentMaterialsCost;
	}
	public BigDecimal getCurrentPlantCost() {
		return currentPlantCost;
	}
	public void setCurrentPlantCost(BigDecimal currentPlantCost) {
		this.currentPlantCost = currentPlantCost;
	}
	public BigDecimal getCurrentOtherCost() {
		return currentOtherCost;
	}
	public void setCurrentOtherCost(BigDecimal currentOtherCost) {
		this.currentOtherCost = currentOtherCost;
	}
	public BigDecimal getCurrentTotalCost() {
		return currentTotalCost;
	}
	public void setCurrentTotalCost(BigDecimal currentTotalCost) {
		this.currentTotalCost = currentTotalCost;
	}
	public BigDecimal getCumulativeLabourCost() {
		return cumulativeLabourCost;
	}
	public void setCumulativeLabourCost(BigDecimal cumulativeLabourCost) {
		this.cumulativeLabourCost = cumulativeLabourCost;
	}
	public BigDecimal getCumulativeMaterialsCost() {
		return cumulativeMaterialsCost;
	}
	public void setCumulativeMaterialsCost(BigDecimal cumulativeMaterialsCost) {
		this.cumulativeMaterialsCost = cumulativeMaterialsCost;
	}
	public BigDecimal getCumulativePlantCost() {
		return cumulativePlantCost;
	}
	public void setCumulativePlantCost(BigDecimal cumulativePlantCost) {
		this.cumulativePlantCost = cumulativePlantCost;
	}
	public BigDecimal getCumulativeOtherCost() {
		return cumulativeOtherCost;
	}
	public void setCumulativeOtherCost(BigDecimal cumulativeOtherCost) {
		this.cumulativeOtherCost = cumulativeOtherCost;
	}
	public BigDecimal getCumulativeTotalCost() {
		return cumulativeTotalCost;
	}
	public void setCumulativeTotalCost(BigDecimal cumulativeTotalCost) {
		this.cumulativeTotalCost = cumulativeTotalCost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getApprovedMaterialsCost() {
		return approvedMaterialsCost;
	}
	public void setApprovedMaterialsCost(BigDecimal approvedMaterialsCost) {
		this.approvedMaterialsCost = approvedMaterialsCost;
	}
	public BigDecimal getPendingMaterialsCost() {
		return pendingMaterialsCost;
	}
	public void setPendingMaterialsCost(BigDecimal pendingMaterialsCost) {
		this.pendingMaterialsCost = pendingMaterialsCost;
	}

}
