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
import com.rjtech.mw.service.dashboards.MWDashBoardsLabourService;

@RestController
@RequestMapping(DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS)
public class MWDashBoardLabourController {

    @Autowired
    private MWDashBoardsLabourService mwDashBoardsLabourService;

    @RequestMapping(value = DashBoardUrlConstants.GET_LABOUR_HOURS_HEALTH_CHECK, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getLabourHealthCheck(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsLabourService.getLabourHealthCheck(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_CV_AND_SV_FOR_LABOUR_HOURS_BUBBLE_CHART, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getCVandSVforLabourBubbleChart(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsLabourService.getCVandSVforLabourBubbleChart(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ORIGINAL_AND_ESTIMATE_AT_COMPLETION_MANHOURS, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getOriginalEstimateAtCompletionManhrs(
            @RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsLabourService.getOriginalEstimateAtCompletionManhrs(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_PLAN_VS_ACTUAL_VS_EARNED_DIRECT_MANHOURS, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getPlanActualEarnedManhrs(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsLabourService.getPlanActualEarnedManhrs(dashBoardReq),
                HttpStatus.OK);
    }

}
