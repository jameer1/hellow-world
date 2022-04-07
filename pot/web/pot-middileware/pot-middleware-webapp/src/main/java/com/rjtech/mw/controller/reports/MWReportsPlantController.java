package com.rjtech.mw.controller.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.service.reports.MWReportsPlantService;
import com.rjtech.plant.reports.req.CurrentPlantsReportGetReq;
import com.rjtech.plant.reports.req.PlantCostCodeWiseGetReq;
import com.rjtech.plant.reports.req.PlantDateWiseAcutalHrsGetReq;
import com.rjtech.plant.reports.req.PlantIdleHrsRecordsGetReq;
import com.rjtech.plant.reports.req.PlantPeriodicalAcutalHrsGetReq;
import com.rjtech.plant.reports.req.PlantUtilizationRecordsGetReq;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;

@RestController
@RequestMapping(ReportsURLConstants.REPORTS_PARH_URL)
public class MWReportsPlantController {

    @Autowired
    private MWReportsPlantService mwReportsPlantService;

    @RequestMapping(value = ReportsURLConstants.GET_PLANT_DATE_WISE_ACTUAL_HRS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getPlantDateWiseActualHrsReport(
            @RequestBody PlantDateWiseAcutalHrsGetReq plantDateWiseAcutalHrsGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsPlantService.getPlantDateWiseActualHrsReport(plantDateWiseAcutalHrsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_PLANT_PERIODICAL_ACTUAL_HOURS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getPlantPeriodicalActualHrsReport(
            @RequestBody PlantPeriodicalAcutalHrsGetReq plantPeriodicalAcutalHrsGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsPlantService.getPlantPeriodicalActualHrsReport(plantPeriodicalAcutalHrsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_PLANT_UTILISATION_RECORDS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getPlantUtilisationRecordsReport(
            @RequestBody PlantUtilizationRecordsGetReq plantUtilizationRecordsGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsPlantService.getPlantUtilisationRecordsReport(plantUtilizationRecordsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_CURRENT_PLANTS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getCurrentPlantsReport(
            @RequestBody CurrentPlantsReportGetReq currentPlantsReportGetReq) {
        return new ResponseEntity<ReportsResp>(mwReportsPlantService.getCurrentPlantsReport(currentPlantsReportGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_PLANT_COST_CODE_WISE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getPlantCostCodeWiseReport(
            @RequestBody PlantCostCodeWiseGetReq plantCostCodeWiseGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsPlantService.getPlantCostCodeWiseReport(plantCostCodeWiseGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_PLANT_IDLE_HOURS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getPlantIdleHoursReport(
            @RequestBody PlantIdleHrsRecordsGetReq plantIdleHrsRecordsGetReq) {
        return new ResponseEntity<ReportsResp>(mwReportsPlantService.getPlantIdleHoursReport(plantIdleHrsRecordsGetReq),
                HttpStatus.OK);
    }
}
