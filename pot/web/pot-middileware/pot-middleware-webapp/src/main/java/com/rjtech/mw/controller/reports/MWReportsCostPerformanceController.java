package com.rjtech.mw.controller.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.costperformance.reports.req.BudgetCostCodeGetReq;
import com.rjtech.costperformance.reports.req.CostSchedulePerformanceIndexReq;
import com.rjtech.costperformance.reports.req.CostScheduleVarianceReq;
import com.rjtech.costperformance.reports.req.DateWiseActualCostDetailsReq;
import com.rjtech.costperformance.reports.req.DateWisePlantActualDeatailsReq;
import com.rjtech.costperformance.reports.req.ItemWisePlantActualReq;
import com.rjtech.costperformance.reports.req.PeriodicalWisePlantActualReq;
import com.rjtech.mw.service.reports.MWReportsCostPerfomanceService;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;

@RestController
@RequestMapping(ReportsURLConstants.REPORTS_PARH_URL)
public class MWReportsCostPerformanceController {
    @Autowired
    private MWReportsCostPerfomanceService mwReportsCostPerfomanceService;

    @RequestMapping(value = ReportsURLConstants.GET_COST_CODE_DATE_WISE_ACTUAL_COST_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getDatewiseActualCostDetails(
            @RequestBody DateWiseActualCostDetailsReq actualCostDetailsReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostPerfomanceService.getDatewiseActualCostDetails(actualCostDetailsReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_CODE_PERIODICAL_PLANT_ACTUAL_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostCodePeriodicalPlantActualReport(
            @RequestBody PeriodicalWisePlantActualReq periodicalWisePlantActualReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostPerfomanceService.getCostCodePeriodicalPlantActualReport(periodicalWisePlantActualReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_CODE_DATE_WISE_PLANT_ACTUAL_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getDatewisePlantActualCostDetails(
            @RequestBody DateWisePlantActualDeatailsReq dateWisePlantActualDeatailsReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostPerfomanceService.getDatewisePlantActualCostDetails(dateWisePlantActualDeatailsReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_CODE_COST_SCHEDULE_VARIANCE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostCodeCostScheduleVarianceReport(
            @RequestBody CostScheduleVarianceReq costScheduleVarianceReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostPerfomanceService.getCostCodeCostScheduleVarianceReport(costScheduleVarianceReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_CODE_COST_SCHEDULE_PERFORMANCE_INDEX_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostSchedulePerformanceIndexReport(
            @RequestBody CostSchedulePerformanceIndexReq costSchedulePerformanceIndexReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostPerfomanceService.getCostSchedulePerformanceIndexReport(costSchedulePerformanceIndexReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_CODE_BUDGET_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostCodeBudgetReport(@RequestBody BudgetCostCodeGetReq budgetCostCodeGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostPerfomanceService.getCostCodeBudgetReport(budgetCostCodeGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_CODE_ITEM_WISE_PLANT_ACTUAL_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostCodeItemWisePlantActualReport(
            @RequestBody ItemWisePlantActualReq itemWisePlantActualReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostPerfomanceService.getCostCodeItemWisePlantActualReport(itemWisePlantActualReq),
                HttpStatus.OK);
    }

}
