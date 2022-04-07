package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjCostSummaryBudgetTO extends ProjectTO {

    private static final long serialVersionUID = -6516553075633272144L;
    private Long budgetType;
    private Object cost;

    public Long getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(Long budgetType) {
        this.budgetType = budgetType;
    }

    public Object getCost() {
        return cost;
    }

    public void setCost(Object cost) {
        this.cost = cost;
    }

}
