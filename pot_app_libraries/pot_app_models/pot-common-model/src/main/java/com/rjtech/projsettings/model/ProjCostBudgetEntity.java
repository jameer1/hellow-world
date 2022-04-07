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

@Entity
@Table(name = "project_cost_budget")
public class ProjCostBudgetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCB_ID")
    private Long id;

    @Column(name = "PCB_BUDGET_TYPE")
    private Long budgetType;

    @Column(name = "PCB_LABOUR")
    private BigDecimal labourCost;

    @Column(name = "PCB_MATERIAL")
    private BigDecimal materialCost;

    @Column(name = "PCB_OTHER")
    private BigDecimal otherCost;

    @Column(name = "PCB_PLANT")
    private BigDecimal plantCost;

    @Column(name = "PCB_TOTAL")
    private BigDecimal total;

    @Column(name = "PCB_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PCB_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCB_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PCB_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCB_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PCB_PJCS_ID")
    private ProjCostStmtDtlEntity projCostStmtDtlEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(Long budgetType) {
        this.budgetType = budgetType;
    }

    public BigDecimal getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(BigDecimal labourCost) {
        this.labourCost = labourCost;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(BigDecimal otherCost) {
        this.otherCost = otherCost;
    }

    public BigDecimal getPlantCost() {
        return plantCost;
    }

    public void setPlantCost(BigDecimal plantCost) {
        this.plantCost = plantCost;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public ProjCostStmtDtlEntity getProjCostStmtDtlEntity() {
        return projCostStmtDtlEntity;
    }

    public void setProjCostStmtDtlEntity(ProjCostStmtDtlEntity projCostStmtDtlEntity) {
        this.projCostStmtDtlEntity = projCostStmtDtlEntity;
    }

}
