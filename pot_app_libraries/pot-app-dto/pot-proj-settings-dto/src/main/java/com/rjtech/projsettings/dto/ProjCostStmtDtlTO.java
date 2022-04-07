package com.rjtech.projsettings.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class ProjCostStmtDtlTO extends ProjectTO {

    private static final long serialVersionUID = -4296212457667046049L;
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
    private List<ProjCostStmtDtlTO> projCostStmtDtlTOs = new ArrayList<>();
    private List<ProjCostBudgetTO> projCostBudgetTOs = new ArrayList<>();
    private ProjCostBudgetTO originalCostBudget = new ProjCostBudgetTO();
    private ProjCostBudgetTO revisedCostBudget = new ProjCostBudgetTO();
    private ProjCostBudgetTO actualCostBudget = new ProjCostBudgetTO();
    private ProjCostBudgetTO estimateCompleteBudget = new ProjCostBudgetTO();
    private BigDecimal spentCost;
    private BigDecimal workProgress;
    private BigDecimal productivityFactor;
    private String estimateType;
    private Date minStartDateOfBaseline;
    private Date maxFinishDateOfBaseline;
    private String itemStatus;
    private Long originatorUserId;    
    private Integer isItemReturned;
    private Long approverUserId;
    private String approverComments;
    private Long returnedByUserId;
    private String returnedComments;

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

    public List<ProjCostBudgetTO> getProjCostBudgetTOs() {
        return projCostBudgetTOs;
    }

    public void setProjCostBudgetTOs(List<ProjCostBudgetTO> projCostBudgetTOs) {
        this.projCostBudgetTOs = projCostBudgetTOs;
    }

    public List<ProjCostStmtDtlTO> getProjCostStmtDtlTOs() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOs(List<ProjCostStmtDtlTO> projCostStmtDtlTOs) {
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

    public ProjCostBudgetTO getOriginalCostBudget() {
        return originalCostBudget;
    }

    public void setOriginalCostBudget(ProjCostBudgetTO originalCostBudget) {
        this.originalCostBudget = originalCostBudget;
    }

    public ProjCostBudgetTO getRevisedCostBudget() {
        return revisedCostBudget;
    }

    public void setRevisedCostBudget(ProjCostBudgetTO revisedCostBudget) {
        this.revisedCostBudget = revisedCostBudget;
    }

    public ProjCostBudgetTO getActualCostBudget() {
        return actualCostBudget;
    }

    public void setActualCostBudget(ProjCostBudgetTO actualCostBudget) {
        this.actualCostBudget = actualCostBudget;
    }

    public ProjCostBudgetTO getEstimateCompleteBudget() {
        return estimateCompleteBudget;
    }

    public void setEstimateCompleteBudget(ProjCostBudgetTO estimateCompleteBudget) {
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

	public Date getMinStartDateOfBaseline() {
		return minStartDateOfBaseline;
	}

	public void setMinStartDateOfBaseline(Date minStartDateOfBaseline) {
		this.minStartDateOfBaseline = minStartDateOfBaseline;
	}

	public Date getMaxFinishDateOfBaseline() {
		return maxFinishDateOfBaseline;
	}

	public void setMaxFinishDateOfBaseline(Date maxFinishDateOfBaseline) {
		this.maxFinishDateOfBaseline = maxFinishDateOfBaseline;
	}
	
	public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Long getOriginatorUserId() {
        return originatorUserId;
    }

    public void setOriginatorUserId( Long originatorUserId ) {
        this.originatorUserId = originatorUserId;
    }
    
    public Integer getIsItemReturned() {
        return isItemReturned;
    }

    public void setIsItemReturned(Integer isItemReturned) {
        this.isItemReturned = isItemReturned;
    }
    
    public Long getApproverUserId() {
        return approverUserId;
    }

    public void setApproverUserId( Long approverUserId ) {
        this.approverUserId = approverUserId;
    }
    
    public String getApproverComments() {
        return approverComments;
    }

    public void setApproverComments( String approverComments ) {
        this.approverComments = approverComments;
    }
    
    public String getReturnedComments() {
        return returnedComments;
    }

    public void setReturnedComments( String returnedComments ) {
        this.returnedComments = returnedComments;
    }
    
    public Long getReturnedByUserId() {
        return returnedByUserId;
    }

    public void setReturnedByUserId( Long returnedByUserId ) {
        this.returnedByUserId = returnedByUserId;
    }
}
