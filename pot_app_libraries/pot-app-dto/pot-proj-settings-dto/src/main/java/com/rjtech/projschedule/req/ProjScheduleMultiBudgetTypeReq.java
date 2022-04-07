package com.rjtech.projschedule.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjScheduleMultiBudgetTypeReq implements Serializable {

    private static final long serialVersionUID = -8317917316366973302L;

    private List<Long> projIds = new ArrayList<>();

    private List<String> budgetTypes = new ArrayList<>();

    public List<Long> getProjIds() {
        return projIds;
    }

    public void setProjIds(List<Long> projIds) {
        this.projIds = projIds;
    }

    public List<String> getBudgetTypes() {
        return budgetTypes;
    }

    public void setBudgetTypes(List<String> budgetTypes) {
        this.budgetTypes = budgetTypes;
    }

}
