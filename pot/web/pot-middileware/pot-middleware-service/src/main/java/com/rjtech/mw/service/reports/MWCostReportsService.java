package com.rjtech.mw.service.reports;

import java.util.List;

import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.CostItemActualReportResp;
import com.rjtech.reports.cost.resp.CostItemWiseReportResp;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.reports.cost.resp.PeriodCostTO;

public interface MWCostReportsService {

    /* reports */
    String getDatewiseActualCostDetails(CostReportReq costReportReq);
    
    List<PeriodCostTO> getPeriodicalWiseReport(CostReportReq costReportReq);
    
    String getDateWisePlanActualEarned(CostReportReq costReportReq);
    
    List<PeriodCostTO> getCostCodeWiseReport(CostReportReq costReportReq);
    
    String getCostCodeBudgetReport(CostReportReq costReportReq);
    
    String getDateProjWiseActualReport(CostReportReq costReportReq);
}
