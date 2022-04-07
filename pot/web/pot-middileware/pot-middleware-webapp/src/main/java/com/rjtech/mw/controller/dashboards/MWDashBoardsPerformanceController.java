package com.rjtech.mw.controller.dashboards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.dashboards.constants.DashBoardUrlConstants;
import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;
import com.rjtech.mw.service.dashboards.MWDashBoardsPerformanceService;

@RestController
@RequestMapping(DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS)
public class MWDashBoardsPerformanceController {

    @Autowired
    private MWDashBoardsPerformanceService mwDashBoardsPerformanceService;

    @RequestMapping(value = DashBoardUrlConstants.GET_PROGRESS_VARIANCE_AND_PERFORMANCE_INDICES, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getProgressVariancePerformanceIndices(
            @RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsPerformanceService.getProgressVariancePerformanceIndices(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_COST_SCHEDULE_PERFORMANCE, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getCostSchedulePerformance(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsPerformanceService.getCostSchedulePerformance(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_PERFORMANCE_COST_SCHEDULE_VARIANCE_UNITS, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getPerformanceCostsheduleVarianceUnits(
            @RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsPerformanceService.getPerformanceCostsheduleVarianceUnits(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_COST_SCHEDULE_VARIANCE_PERCENTAGE, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getCostScheduleVariancePercentage(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsPerformanceService.getCostScheduleVariancePercentage(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_COST_SCHEDULE_PERFORMANCE_INDICES, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getCostSchedulePerformanceIndices(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsPerformanceService.getCostSchedulePerformanceIndices(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_TO_COMPLETE_PERFORMANCE_INDEX, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getToCompletePerformanceIndex(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsPerformanceService.getToCompletePerformanceIndex(dashBoardReq), HttpStatus.OK);
    }

}
