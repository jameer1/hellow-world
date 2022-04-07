package com.rjtech.reports.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.projsettings.service.CostCodeActualDetailsService;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.CostItemActualReportResp;
import com.rjtech.reports.cost.resp.CostItemWiseReportResp;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.reports.cost.resp.PeriodCostTO;

@RestController
@RequestMapping("/app/costreports/")
public class CostReportsController {

    @Autowired
    private CostCodeActualDetailsService costCodeActualDetails;

    @PostMapping("getDatewiseActualCostDetails")
    public ResponseEntity<DateWiseCostReportResp> getDatewiseActualCostDetails(
            @RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(costCodeActualDetails.getDatewiseActualCostDetails(costReportReq), HttpStatus.OK);
    }

    @PostMapping("getCostCodeBudgetReport")
    public ResponseEntity<CostItemWiseReportResp> getCostCodeBudgetReport(@RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(costCodeActualDetails.getCostCodeBudgetReport(costReportReq.getProjIds()),
                HttpStatus.OK);
    }

    @PostMapping("getDateWisePlanActualEarned")
    public ResponseEntity<DateWiseCostReportResp> getDateWisePlanActualEarned(
            @RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(costCodeActualDetails.getDateWisePlanActualEarned(costReportReq), HttpStatus.OK);
    }

    @PostMapping("getPeriodicalWiseReport")
    public ResponseEntity<List<PeriodCostTO>> getPeriodicalWiseReport(@RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(costCodeActualDetails.getPeriodicalWiseReport(costReportReq), HttpStatus.OK);
    }

    @PostMapping("getCostCodeWiseReport")
    public ResponseEntity<List<PeriodCostTO>> getCostCodeWiseReport(@RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(costCodeActualDetails.getCostCodeWiseReport(costReportReq), HttpStatus.OK);
    }

    @PostMapping("getDateProjWiseActualReport")
    public ResponseEntity<CostItemActualReportResp> getDateProjWiseActualReport(
            @RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(costCodeActualDetails.getDateProjWiseActualReport(costReportReq), HttpStatus.OK);
    }
}
