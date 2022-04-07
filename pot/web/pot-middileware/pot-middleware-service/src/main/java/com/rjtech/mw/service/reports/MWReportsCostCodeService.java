package com.rjtech.mw.service.reports;

import com.rjtech.costcode.reports.req.CostCodeActualGetReq;
import com.rjtech.costcode.reports.req.CostCodeItemGetReq;
import com.rjtech.costcode.reports.req.CostCodeReportGetReq;
import com.rjtech.reports.resp.ReportsResp;

public interface MWReportsCostCodeService {

    ReportsResp getCostDateWiseActualReport(CostCodeActualGetReq costCodeActualGetReq);

    ReportsResp getCostDateWisePlannedReport(CostCodeReportGetReq costCodeReportGetReq);

    ReportsResp getCostPeriodicalPlannedReport(CostCodeReportGetReq costCodeReportGetReq);

    ReportsResp getCostCodeVarienceReport(CostCodeReportGetReq costCodeReportGetReq);

    ReportsResp getCostItemBudgetReport(CostCodeItemGetReq costCodeItemGetReq);

    ReportsResp getCostItemWisePlanVsActualReport(CostCodeReportGetReq costCodeReportGetReq);

    ReportsResp getCostPerformanceIndexReport(CostCodeReportGetReq costCodeReportGetReq);

}
