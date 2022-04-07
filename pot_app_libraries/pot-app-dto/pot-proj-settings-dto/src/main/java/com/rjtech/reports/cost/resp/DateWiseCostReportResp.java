package com.rjtech.reports.cost.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DateWiseCostReportResp implements Serializable {

    private static final long serialVersionUID = -1538667587112525880L;
    private List<CostReportTO> costReportResps = new ArrayList<>();

    public List<CostReportTO> getCostReportResps() {
        return costReportResps;
    }

    public void setCostReportResps(List<CostReportTO> costReportResps) {
        this.costReportResps = costReportResps;
    }

}
