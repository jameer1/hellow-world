package com.rjtech.mw.controller.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.costcode.reports.req.CostCodeActualGetReq;
import com.rjtech.costcode.reports.req.CostCodeItemGetReq;
import com.rjtech.costcode.reports.req.CostCodeReportGetReq;
import com.rjtech.mw.service.reports.MWReportsCostCodeService;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;

@RestController
@RequestMapping(ReportsURLConstants.REPORTS_PARH_URL)
public class MWReportsCostCodeController {

    @Autowired
    private MWReportsCostCodeService mwReportsCostCodeService;

    @RequestMapping(value = ReportsURLConstants.GET_COST_DATE_WISE_ACTUAL_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostDateWiseActualReport(
            @RequestBody CostCodeActualGetReq costCodeActualGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostCodeService.getCostDateWiseActualReport(costCodeActualGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_DATE_WISE_PLANNED_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostDateWisePlannedReport(
            @RequestBody CostCodeReportGetReq costCodeReportGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostCodeService.getCostDateWisePlannedReport(costCodeReportGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_PERIODICAL_PLANNED_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostPeriodicalPlannedReport(
            @RequestBody CostCodeReportGetReq costCodeReportGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostCodeService.getCostPeriodicalPlannedReport(costCodeReportGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_CODE_VARIENCE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostCodeVarienceReport(
            @RequestBody CostCodeReportGetReq costCodeReportGetReq) {
        return new ResponseEntity<ReportsResp>(mwReportsCostCodeService.getCostCodeVarienceReport(costCodeReportGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_ITEM_BUDGET_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostItemBudgetReport(@RequestBody CostCodeItemGetReq costCodeItemGetReq) {
        return new ResponseEntity<ReportsResp>(mwReportsCostCodeService.getCostItemBudgetReport(costCodeItemGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_ITEM_WISE_PLAN_VS_ACTUAL_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostItemWisePlanVsActualReport(
            @RequestBody CostCodeReportGetReq costCodeReportGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostCodeService.getCostItemWisePlanVsActualReport(costCodeReportGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_COST_PERFORMANCE_INDEX_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCostPerformanceIndexReport(
            @RequestBody CostCodeReportGetReq costCodeReportGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsCostCodeService.getCostPerformanceIndexReport(costCodeReportGetReq), HttpStatus.OK);
    }

}
