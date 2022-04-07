package com.rjtech.reports.cost.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CostItemActualReportResp implements Serializable {

    private static final long serialVersionUID = 3028081766733081040L;

    private List<CostItemActualReportTO> costItemActualReportTOs = new ArrayList<>();

    private List<CostBudgetRatioTO> ratioTOs = new ArrayList<>();

    public List<CostItemActualReportTO> getCostItemActualReportTOs() {
        return costItemActualReportTOs;
    }

    public void setCostItemActualReportTOs(List<CostItemActualReportTO> costItemActualReportTOs) {
        this.costItemActualReportTOs = costItemActualReportTOs;
    }

    public List<CostBudgetRatioTO> getRatioTOs() {
        return ratioTOs;
    }

    public void setRatioTOs(List<CostBudgetRatioTO> ratioTOs) {
        this.ratioTOs = ratioTOs;
    }

}
