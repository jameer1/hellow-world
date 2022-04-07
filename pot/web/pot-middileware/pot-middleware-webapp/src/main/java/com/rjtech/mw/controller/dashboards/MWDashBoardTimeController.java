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
import com.rjtech.mw.service.dashboards.MWDashBoardsTimeService;

@RestController
@RequestMapping(DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS)
public class MWDashBoardTimeController {

    @Autowired
    private MWDashBoardsTimeService mwDashBoardsTimeService;

    @RequestMapping(value = DashBoardUrlConstants.GET_COST_SCHEDULE_VARIANCE_BUBBLE_CHART, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getCostSchedulevarianceBubbleChart(
            @RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsTimeService.getCostSchedulevarianceBubbleChart(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_CURRENT_DATE_PROGRESS_PERCENTAGE, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getCurrentDateProgressPercentage(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsTimeService.getCurrentDateProgressPercentage(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_TIME_COST_SCHEDULE_VARIANCE_UNITS, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getTimeCostScheduleVarianceUnits(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsTimeService.getTimeCostScheduleVarianceUnits(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_PROJ_GRANTT_CHART, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getProjectGranttChart(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsTimeService.getProjectGranttChart(dashBoardReq),
                HttpStatus.OK);
    }

}
