package com.rjtech.projectlib.dto;

import com.rjtech.common.dto.ProjectTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProjCostStmtDtlTOCopy extends ProjectTO {

    private static final long serialVersionUID = 6L;
    private Long id;
    private Long costId;
    private String name;
    private String code;
    private boolean item;
    private boolean expand = true;
    private BigDecimal earnedValue;
    private String notes;
    private Long parentId;
    private String actualStartDate;
    private String actualFinishDate;
    private String startDate;
    private String finishDate;
    private String costClassId;
    private String costClassName;
    private List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs = new ArrayList<>();
    private List<ProjCostBudgetTOCopy> projCostBudgetTOs = new ArrayList<>();
    private ProjCostBudgetTOCopy originalCostBudget = new ProjCostBudgetTOCopy();
    private ProjCostBudgetTOCopy revisedCostBudget = new ProjCostBudgetTOCopy();
    private ProjCostBudgetTOCopy actualCostBudget = new ProjCostBudgetTOCopy();
    private ProjCostBudgetTOCopy estimateCompleteBudget = new ProjCostBudgetTOCopy();
    private PeriodCostTO actualCostPrevPeriod = new PeriodCostTO();
    private PeriodCostTO actualCostReportPeriod = new PeriodCostTO();
    private PeriodCostTO actualCostUpToDatePeriod = new PeriodCostTO();

    private BigDecimal spentCost;
    private BigDecimal workProgress;
    private BigDecimal productivityFactor;
    private String estimateType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public BigDecimal getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(BigDecimal earnedValue) {
        this.earnedValue = earnedValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<ProjCostBudgetTOCopy> getProjCostBudgetTOCopys() {
        return projCostBudgetTOs;
    }

    public void setProjCostBudgetTOCopys(List<ProjCostBudgetTOCopy> projCostBudgetTOs) {
        this.projCostBudgetTOs = projCostBudgetTOs;
    }

    public List<ProjCostStmtDtlTOCopy> getProjCostStmtDtlTOCopys() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOCopys(List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs) {
        this.projCostStmtDtlTOs = projCostStmtDtlTOs;
    }

    public boolean isItem() {
        return item;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(String actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public String getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(String actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getCostClassId() {
        return costClassId;
    }

    public void setCostClassId(String costClassId) {
        this.costClassId = costClassId;
    }

    public String getCostClassName() {
        return costClassName;
    }

    public void setCostClassName(String costClassName) {
        this.costClassName = costClassName;
    }

    public ProjCostBudgetTOCopy getOriginalCostBudget() {
        return originalCostBudget;
    }

    public void setOriginalCostBudget(ProjCostBudgetTOCopy originalCostBudget) {
        this.originalCostBudget = originalCostBudget;
    }

    public ProjCostBudgetTOCopy getRevisedCostBudget() {
        return revisedCostBudget;
    }

    public void setRevisedCostBudget(ProjCostBudgetTOCopy revisedCostBudget) {
        this.revisedCostBudget = revisedCostBudget;
    }

    public ProjCostBudgetTOCopy getActualCostBudget() {
        return actualCostBudget;
    }

    public void setActualCostBudget(ProjCostBudgetTOCopy actualCostBudget) {
        this.actualCostBudget = actualCostBudget;
    }

    public ProjCostBudgetTOCopy getEstimateCompleteBudget() {
        return estimateCompleteBudget;
    }

    public void setEstimateCompleteBudget(ProjCostBudgetTOCopy estimateCompleteBudget) {
        this.estimateCompleteBudget = estimateCompleteBudget;
    }

    public BigDecimal getSpentCost() {
        return spentCost;
    }

    public void setSpentCost(BigDecimal spentCost) {
        this.spentCost = spentCost;
    }

    public BigDecimal getWorkProgress() {
        return workProgress;
    }

    public void setWorkProgress(BigDecimal workProgress) {
        this.workProgress = workProgress;
    }

    public BigDecimal getProductivityFactor() {
        return productivityFactor;
    }

    public void setProductivityFactor(BigDecimal productivityFactor) {
        this.productivityFactor = productivityFactor;
    }

    public String getEstimateType() {
        return estimateType;
    }

    public void setEstimateType(String estimateType) {
        this.estimateType = estimateType;
    }

    public List<ProjCostStmtDtlTOCopy> getProjCostStmtDtlTOs() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOs(List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs) {
        this.projCostStmtDtlTOs = projCostStmtDtlTOs;
    }

    public List<ProjCostBudgetTOCopy> getProjCostBudgetTOs() {
        return projCostBudgetTOs;
    }

    public void setProjCostBudgetTOs(List<ProjCostBudgetTOCopy> projCostBudgetTOs) {
        this.projCostBudgetTOs = projCostBudgetTOs;
    }

    public PeriodCostTO getActualCostPrevPeriod() {
        return actualCostPrevPeriod;
    }

    public void setActualCostPrevPeriod(PeriodCostTO actualCostPrevPeriod) {
        this.actualCostPrevPeriod = actualCostPrevPeriod;
    }

    public PeriodCostTO getActualCostReportPeriod() {
        return actualCostReportPeriod;
    }

    public void setActualCostReportPeriod(PeriodCostTO actualCostReportPeriod) {
        this.actualCostReportPeriod = actualCostReportPeriod;
    }

    public PeriodCostTO getActualCostUpToDatePeriod() {
        return actualCostUpToDatePeriod;
    }

    public void setActualCostUpToDatePeriod(PeriodCostTO actualCostUpToDatePeriod) {
        this.actualCostUpToDatePeriod = actualCostUpToDatePeriod;
    }
}

