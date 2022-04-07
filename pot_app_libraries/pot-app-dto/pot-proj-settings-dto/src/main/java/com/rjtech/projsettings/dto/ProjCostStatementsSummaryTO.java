package com.rjtech.projsettings.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class ProjCostStatementsSummaryTO extends ProjectTO {

    private static final long serialVersionUID = -6516553075633272144L;
    private String catgType;
    private List<ProjCostSummaryBudgetTO> projCostBugetTOs = new ArrayList<ProjCostSummaryBudgetTO>();
    private ProjCostSummaryBudgetTO originalCostBudget;
    private ProjCostSummaryBudgetTO revisedCostBudget;
    private ProjCostSummaryBudgetTO actualCostBudget;
    private ProjCostSummaryBudgetTO estimateCompleteBudget;
    private String estimateType;
    private BigDecimal earnedValue;
    private Double estimateToComplete;
    private Double estimateAtCompletion;
    private Double completionVariance;

    public String getCatgType() {
        return catgType;
    }

    public void setCatgType(String catgType) {
        this.catgType = catgType;
    }

    public List<ProjCostSummaryBudgetTO> getProjCostBugetTOs() {
        return projCostBugetTOs;
    }

    public void setProjCostBugetTOs(List<ProjCostSummaryBudgetTO> projCostBugetTOs) {
        this.projCostBugetTOs = projCostBugetTOs;
    }

    public ProjCostSummaryBudgetTO getOriginalCostBudget() {
        return originalCostBudget;
    }

    public void setOriginalCostBudget(ProjCostSummaryBudgetTO originalCostBudget) {
        this.originalCostBudget = originalCostBudget;
    }

    public ProjCostSummaryBudgetTO getRevisedCostBudget() {
        return revisedCostBudget;
    }

    public void setRevisedCostBudget(ProjCostSummaryBudgetTO revisedCostBudget) {
        this.revisedCostBudget = revisedCostBudget;
    }

    public ProjCostSummaryBudgetTO getActualCostBudget() {
        return actualCostBudget;
    }

    public void setActualCostBudget(ProjCostSummaryBudgetTO actualCostBudget) {
        this.actualCostBudget = actualCostBudget;
    }

    public ProjCostSummaryBudgetTO getEstimateCompleteBudget() {
        return estimateCompleteBudget;
    }

    public void setEstimateCompleteBudget(ProjCostSummaryBudgetTO estimateCompleteBudget) {
        this.estimateCompleteBudget = estimateCompleteBudget;
    }

	public String getEstimateType() {
		return estimateType;
	}

	public void setEstimateType(String estimateType) {
		this.estimateType = estimateType;
	}

	public BigDecimal getEarnedValue() {
		return earnedValue;
	}

	public void setEarnedValue(BigDecimal earnedValue) {
		this.earnedValue = earnedValue;
	}

	public Double getEstimateToComplete() {
		return estimateToComplete;
	}

	public void setEstimateToComplete(Double estimateToComplete) {
		this.estimateToComplete = estimateToComplete;
	}

	public Double getEstimateAtCompletion() {
		return estimateAtCompletion;
	}

	public void setEstimateAtCompletion(Double estimateAtCompletion) {
		this.estimateAtCompletion = estimateAtCompletion;
	}

	public Double getCompletionVariance() {
		return completionVariance;
	}

	public void setCompletionVariance(Double completionVariance) {
		this.completionVariance = completionVariance;
	}

}
