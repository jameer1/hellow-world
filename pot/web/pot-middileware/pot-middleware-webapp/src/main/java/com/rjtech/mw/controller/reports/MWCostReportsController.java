package com.rjtech.mw.controller.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.service.reports.MWCostReportsService;
import com.rjtech.reports.constants.ReportsCostURLConstants;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.CostItemActualReportResp;
import com.rjtech.reports.cost.resp.CostItemWiseReportResp;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.reports.cost.resp.PeriodCostTO;

@RestController
@RequestMapping(ReportsCostURLConstants.REPORTS_PARH_URL)
public class MWCostReportsController {
    
    @Autowired
    private MWCostReportsService mwCostReportsService;

    //@PostMapping("getDatewiseActualCostDetails")
    @RequestMapping(value = ReportsCostURLConstants.GET_COST_CODE_DATE_WISE_ACTUAL_COST_REPORT, method = RequestMethod.POST)
    public ResponseEntity<String> getDatewiseActualCostDetails(
            @RequestBody CostReportReq costReportReq) {
    	System.out.println("projectsettings > MWCostController > getDatewiseActualCostDetails ");
    	String resp = mwCostReportsService.getDatewiseActualCostDetails(costReportReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    //@PostMapping("getPeriodicalWiseReport")
    @RequestMapping(value = ReportsCostURLConstants.GET_PERIODICAL_WISE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<List<PeriodCostTO>> getPeriodicalWiseReport(@RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(mwCostReportsService.getPeriodicalWiseReport(costReportReq), HttpStatus.OK);
    }
    
    //@PostMapping("getDateWisePlanActualEarned")
    @RequestMapping(value = ReportsCostURLConstants.GET_DATE_WISE_PLANNED_ACTUAL_EARNED_REPORT, method = RequestMethod.POST)
    public ResponseEntity<String> getDateWisePlanActualEarned(
            @RequestBody CostReportReq costReportReq) {
    	String resp = mwCostReportsService.getDateWisePlanActualEarned(costReportReq);
    	System.out.println("Resp in MWWWWWWWWWWWcontroller " + resp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    //@PostMapping("getCostCodeWiseReport")
    @RequestMapping(value = ReportsCostURLConstants.GET_COST_CODE_WISE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<List<PeriodCostTO>> getCostCodeWiseReport(@RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(mwCostReportsService.getCostCodeWiseReport(costReportReq), HttpStatus.OK);
    }
    
    //@PostMapping("getCostCodeBudgetReport")
    @RequestMapping(value = ReportsCostURLConstants.GET_COST_CODE_BUDGET_REPORT, method = RequestMethod.POST)
    public ResponseEntity<String> getCostCodeBudgetReport(@RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(mwCostReportsService.getCostCodeBudgetReport(costReportReq),
                HttpStatus.OK);
    }
    
    //@PostMapping("getDateProjWiseActualReport")
    @RequestMapping(value = ReportsCostURLConstants.GET_DATE_PROJ_WISE_ACTUAL_REPORT, method = RequestMethod.POST)
    public ResponseEntity<String> getDateProjWiseActualReport(
            @RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(mwCostReportsService.getDateProjWiseActualReport(costReportReq), HttpStatus.OK);
    }
}
