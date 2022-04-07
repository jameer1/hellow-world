package com.rjtech.mw.service.impl.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.costperformance.reports.req.BudgetCostCodeGetReq;
import com.rjtech.costperformance.reports.req.CostSchedulePerformanceIndexReq;
import com.rjtech.costperformance.reports.req.CostScheduleVarianceReq;
import com.rjtech.costperformance.reports.req.DateWiseActualCostDetailsReq;
import com.rjtech.costperformance.reports.req.DateWisePlantActualDeatailsReq;
import com.rjtech.costperformance.reports.req.ItemWisePlantActualReq;
import com.rjtech.costperformance.reports.req.PeriodicalWisePlantActualReq;
import com.rjtech.mw.service.reports.MWReportsCostPerfomanceService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwReportsCostPerformanceService")
@RJSService(modulecode = "mwReportsCostPerformanceService")
@Transactional
public class MWReportsCostPerformanceServiceImpl extends RestConfigServiceImpl
        implements MWReportsCostPerfomanceService {

    public ReportsResp getDatewiseActualCostDetails(DateWiseActualCostDetailsReq actualCostDetailsReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(actualCostDetailsReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_CODE_DATE_WISE_ACTUAL_COST_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostCodePeriodicalPlantActualReport(
            PeriodicalWisePlantActualReq periodicalWisePlantActualReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(periodicalWisePlantActualReq),
                ReportsURLConstants.REPORTS_PARH_URL
                        + ReportsURLConstants.GET_COST_CODE_PERIODICAL_PLANT_ACTUAL_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getDatewisePlantActualCostDetails(
            DateWisePlantActualDeatailsReq dateWisePlantActualDeatailsReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dateWisePlantActualDeatailsReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_CODE_DATE_WISE_PLANT_ACTUAL_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostCodeCostScheduleVarianceReport(CostScheduleVarianceReq costScheduleVarianceReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(costScheduleVarianceReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_CODE_COST_SCHEDULE_VARIANCE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostSchedulePerformanceIndexReport(
            CostSchedulePerformanceIndexReq costSchedulePerformanceIndexReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(costSchedulePerformanceIndexReq),
                ReportsURLConstants.REPORTS_PARH_URL
                        + ReportsURLConstants.GET_COST_CODE_COST_SCHEDULE_PERFORMANCE_INDEX_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostCodeBudgetReport(BudgetCostCodeGetReq budgetCostCodeGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(budgetCostCodeGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_CODE_BUDGET_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostCodeItemWisePlantActualReport(ItemWisePlantActualReq itemWisePlantActualReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(itemWisePlantActualReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_CODE_ITEM_WISE_PLANT_ACTUAL_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

}
