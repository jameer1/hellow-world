package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
import com.rjtech.projectlib.model.ChangeOrderMstrEntity;

@Entity
@Table(name = "CO_PROJECT_COST_BUDGET")
public class COProjCostBudgetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_pmb_id")
    private Long id;
    
    @Column(name = "cc_code")
    private String coCostCode;
    @Column(name = "cc_code_desc")
    private String coCostCodeDescription;
    @Column(name = "currency")
    private String currency;

    @Column(name = "apprvd_pcb_labour")
	private BigDecimal approvedLabourCost;
    @Column(name = "apprvd_pcb_plant")
	private BigDecimal approvedPlantCost;
    @Column(name = "apprvd_pcb_materials")
	private BigDecimal approvedMaterialsCost;
    @Column(name = "apprvd_pcb_other")
	private BigDecimal approvedOtherCost;
    @Column(name = "apprvd_pcb_total")
	private BigDecimal approvedTotalCost;

    @Column(name = "pend_pcb_labour")
	private BigDecimal pendingLabourCost;
    @Column(name = "pend_pcb_plant")
	private BigDecimal pendingPlantCost;
    @Column(name = "pend_pcb_materials")
	private BigDecimal pendingMaterialsCost;
    @Column(name = "pend_pcb_other")
	private BigDecimal pendingOtherCost;
    @Column(name = "pend_pcb_total")
	private BigDecimal pendingTotalCost;

    @Column(name = "current_pcb_labour")
	private BigDecimal currentLabourCost;
    @Column(name = "current_pcb_materials")
	private BigDecimal currentMaterialsCost;
    @Column(name = "current_pcb_plant")
	private BigDecimal currentPlantCost;
    @Column(name = "current_pcb_other")
	private BigDecimal currentOtherCost;
    @Column(name = "current_pcb_total")
	private BigDecimal currentTotalCost;

    @Column(name = "cmltv_pcb_labour")
	private BigDecimal cumulativeLabourCost;
    @Column(name = "cmltv_pcb_materials")
	private BigDecimal cumulativeMaterialsCost;
    @Column(name = "cmltv_pcb_plant")
	private BigDecimal cumulativePlantCost;
    @Column(name = "cmltv_pcb_other")
	private BigDecimal cumulativeOtherCost;
    @Column(name = "cmltv_pcb_total")
	private BigDecimal cumulativeTotalCost;
   
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

	public BigDecimal getApprovedMaterialsCost() {
		return approvedMaterialsCost;
	}

	public void setApprovedMaterialsCost(BigDecimal approvedMaterialsCost) {
		this.approvedMaterialsCost = approvedMaterialsCost;
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

	public BigDecimal getPendingMaterialsCost() {
		return pendingMaterialsCost;
	}

	public void setPendingMaterialsCost(BigDecimal pendingMaterialsCost) {
		this.pendingMaterialsCost = pendingMaterialsCost;
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
