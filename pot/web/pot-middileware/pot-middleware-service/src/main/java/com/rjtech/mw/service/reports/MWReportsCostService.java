package com.rjtech.mw.service.reports;

import com.rjtech.costperformance.reports.req.BudgetCostCodeGetReq;
import com.rjtech.costperformance.reports.req.CostSchedulePerformanceIndexReq;
import com.rjtech.costperformance.reports.req.CostScheduleVarianceReq;
import com.rjtech.costperformance.reports.req.DateWiseActualCostDetailsReq;
import com.rjtech.costperformance.reports.req.DateWisePlantActualDeatailsReq;
import com.rjtech.costperformance.reports.req.ItemWisePlantActualReq;
import com.rjtech.costperformance.reports.req.PeriodicalWisePlantActualReq;
import com.rjtech.reports.resp.ReportsResp;

public interface MWReportsCostService {

    ReportsResp getDatewiseActualCostDetails(DateWiseActualCostDetailsReq actualCostDetailsReq);

    ReportsResp getCostCodePeriodicalPlantActualReport(PeriodicalWisePlantActualReq periodicalWisePlantActualReq);

    ReportsResp getDatewisePlantActualCostDetails(DateWisePlantActualDeatailsReq dateWisePlantActualDeatailsReq);

    ReportsResp getCostCodeCostScheduleVarianceReport(CostScheduleVarianceReq costScheduleVarianceReq);

    ReportsResp getCostSchedulePerformanceIndexReport(CostSchedulePerformanceIndexReq costSchedulePerformanceIndexReq);

    ReportsResp getCostCodeBudgetReport(BudgetCostCodeGetReq budgetCostCodeGetReq);

    ReportsResp getCostCodeItemWisePlantActualReport(ItemWisePlantActualReq itemWisePlantActualReq);

}
