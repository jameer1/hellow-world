package com.rjtech.mw.controller.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.manpower.reports.req.ManpowerActVsStandHrsGetReq;
import com.rjtech.manpower.reports.req.ManpowerCostCodeGetReq;
import com.rjtech.manpower.reports.req.ManpowerCurrentEmpGetReq;
import com.rjtech.manpower.reports.req.ManpowerGenderGetReq;
import com.rjtech.manpower.reports.req.ManpowerIdleHrsGetReq;
import com.rjtech.manpower.reports.req.ManpowerMobilisationGetReq;
import com.rjtech.manpower.reports.req.ManpowerPeroidicalHrsGetReq;
import com.rjtech.manpower.reports.req.ManpowerPlanVsActVsEarnedGetReq;
import com.rjtech.mw.service.reports.MWReportsManpowerService;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;

@RestController
@RequestMapping(ReportsURLConstants.REPORTS_PARH_URL)
public class MWReportsManpowerController {

    @Autowired
    private MWReportsManpowerService mwReportsManpowerService;

    @RequestMapping(value = ReportsURLConstants.GET_MANPOWER_DATE_WISE_HRS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getManpowerDateWiseActualHrsReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerDateWiseHrsGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsManpowerService.getManpowerDateWiseHrsReport(manpowerDateWiseHrsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MANPOWER_GENDER_STATUSTICS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getPlantPeriodicalActualHrsReport(
            @RequestBody ManpowerGenderGetReq manpowerGenderGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsManpowerService.getManpowerGenderStatisticsReport(manpowerGenderGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MANPOWER_IDLE_HRS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getPlantUtilisationRecordsReport(
            @RequestBody ManpowerIdleHrsGetReq manpowerIdleHrsGetReq) {
        return new ResponseEntity<ReportsResp>(mwReportsManpowerService.getManpowerIdleHrsReport(manpowerIdleHrsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MANPOWER_PERIODICAL_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getManpowerPeriodicalReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsManpowerService.getManpowerPeriodicalReport(manpowerPeroidicalHrsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MANPOWER_COST_CODE_WISE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getManpowerCostCodeWiseReport(
            @RequestBody ManpowerCostCodeGetReq manpowerCostCodeGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsManpowerService.getManpowerCostCodeWiseReport(manpowerCostCodeGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MANPOWER_ACTUAL_STANDARD_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getManpowerActualStandardReport(
            @RequestBody ManpowerActVsStandHrsGetReq manpowerActVsStandHrsGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsManpowerService.getManpowerActualStandardReport(manpowerActVsStandHrsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MANPOWER_PERIODICAL_MOBILISATION_HRS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getManpowerPeriodicalMobilisationReport(
            @RequestBody ManpowerMobilisationGetReq manpowerMobilisationGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsManpowerService.getManpowerPeriodicalMobilisationReport(manpowerMobilisationGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MANPOWER_CURRENT_EMPLOYEE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getManpowerCurrentEmployeeReport(
            @RequestBody ManpowerCurrentEmpGetReq manpowerCurrentEmpGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsManpowerService.getManpowerCurrentEmployeeReport(manpowerCurrentEmpGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_MANPOWER_PLAN_ACTUAL_EARNED_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getManpowerPlanActualEarnedReport(
            @RequestBody ManpowerPlanVsActVsEarnedGetReq manpowerPlanVsActVsEarnedGetReq) {
        return new ResponseEntity<ReportsResp>(
                mwReportsManpowerService.getManpowerPlanActualEarnedReport(manpowerPlanVsActVsEarnedGetReq),
                HttpStatus.OK);
    }

}
