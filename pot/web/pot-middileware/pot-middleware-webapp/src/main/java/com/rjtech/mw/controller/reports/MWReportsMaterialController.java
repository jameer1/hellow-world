package com.rjtech.mw.controller.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.material.reports.req.MaterialConsumptionGetReq;
import com.rjtech.material.reports.req.MaterialInventoryGetReq;
import com.rjtech.material.reports.req.MaterialReportGetReq;
import com.rjtech.mw.service.reports.MWReportsMaterialService;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;

@RestController
@RequestMapping(ReportsURLConstants.REPORTS_PARH_URL)
public class MWReportsMaterialController {

    @Autowired
    private MWReportsMaterialService mwReportsMaterialService;

    @RequestMapping(value = ReportsURLConstants.GET_MATERIAL_DELIVERY_SUPPLY_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMaterialDeliverySupplyReport(
            @RequestBody MaterialReportGetReq materialReportGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsMaterialService.getMaterialDeliverySupplyReport(materialReportGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MATERIAL_DAILY_ISSUE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMaterialDailyIssueReport(
            @RequestBody MaterialReportGetReq materialReportGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsMaterialService.getMaterialDailyIssueReport(materialReportGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MATERIAL_STOCK_BAL_IN_TRANSIT_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMaterialStockBalInTransitReport(
            @RequestBody MaterialReportGetReq materialReportGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsMaterialService.getMaterialStockBalInTransitReport(materialReportGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MATERIAL_STOCK_PILES_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMaterialStockPilesReport(
            @RequestBody MaterialReportGetReq materialReportGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsMaterialService.getMaterialStockPilesReport(materialReportGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MATERIAL_DATE_WISE_CONSU_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMaterialDateWiseConsuReport(
            @RequestBody MaterialConsumptionGetReq materialConsumptionGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsMaterialService.getMaterialDateWiseConsuReport(materialConsumptionGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MATERIAL_PERIODICAL_CONSU_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMaterialPeriodicalConsuReport(
            @RequestBody MaterialConsumptionGetReq materialConsumptionGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsMaterialService.getMaterialPeriodicalConsuReport(materialConsumptionGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MATERIAL_INVENTORY_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getMaterialInventoryReport(
            @RequestBody MaterialInventoryGetReq materialInventoryGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsMaterialService.getMaterialInventoryReport(materialInventoryGetReq), HttpStatus.OK);
    }

}
