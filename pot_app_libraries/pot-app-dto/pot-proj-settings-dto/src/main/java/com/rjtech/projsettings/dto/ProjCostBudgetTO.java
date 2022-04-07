package com.rjtech.projsettings.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class ProjCostBudgetTO extends ProjectTO {

    private static final long serialVersionUID = 7456200141750576472L;
    private Long id;
    private Long costStmtId;
    private Long budgetType;
    private BigDecimal labourCost;
    private BigDecimal materialCost;
    private BigDecimal plantCost;
    private BigDecimal otherCost;
    private BigDecimal total;
    private BigDecimal budgetTotal;
    private BigDecimal revisedTotal;
    private BigDecimal actualTotal;
    private String estimateType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostStmtId() {
        return costStmtId;
    }

    public void setCostStmtId(Long costStmtId) {
        this.costStmtId = costStmtId;
    }

    public BigDecimal getRevisedTotal() {
        return revisedTotal;
    }

    public void setRevisedTotal(BigDecimal revisedTotal) {
        this.revisedTotal = revisedTotal;
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

    public BigDecimal getPlantCost() {
        return plantCost;
    }

    public void setPlantCost(BigDecimal plantCost) {
        this.plantCost = plantCost;
    }

    public BigDecimal getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(BigDecimal otherCost) {
        this.otherCost = otherCost;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getBudgetTotal() {
        return budgetTotal;
    }

    public void setBudgetTotal(BigDecimal budgetTotal) {
        this.budgetTotal = budgetTotal;
    }

    public BigDecimal getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(BigDecimal actualTotal) {
        this.actualTotal = actualTotal;
    }

    public String getEstimateType() {
        return estimateType;
    }

    public void setEstimateType(String estimateType) {
        this.estimateType = estimateType;
    }

}
