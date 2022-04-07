package com.rjtech.reports.cost.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CostItemWiseReportResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4048023872521575673L;
    private List<CostItemReportTO> costReportResps = new ArrayList<>();

    public List<CostItemReportTO> getCostReportResps() {
        return costReportResps;
    }

    public void setCostReportResps(List<CostItemReportTO> costReportResps) {
        this.costReportResps = costReportResps;
    }

}
