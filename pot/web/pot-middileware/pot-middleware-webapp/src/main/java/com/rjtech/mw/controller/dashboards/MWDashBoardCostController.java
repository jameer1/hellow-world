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
import com.rjtech.mw.service.dashboards.MWDashBoardsCostService;

@RestController
@RequestMapping(DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS)
public class MWDashBoardCostController {

    @Autowired
    private MWDashBoardsCostService mwDashBoardsCostService;

    @RequestMapping(value = DashBoardUrlConstants.GET_COST_HEALTH_CHECK, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getCostHealthCheck(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsCostService.getCostHealthCheck(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_CV_AND_SV_FOR_COST_BUBBLE_CHART, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getCVandSVforCostBubbleChart(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsCostService.getCVandSVforCostBubbleChart(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ORIGINAL_AND_ESTIMATE_AT_COMPLETION_COST, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getOriginalEstimateAtCompletionCost(
            @RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsCostService.getOriginalEstimateAtCompletionCost(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_PLAN_VS_ACTUAL_VS_EARNED_COST, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getPlanActualEarnedCost(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsCostService.getPlanActualEarnedCost(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_COST_WORKSHEET, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getCostWorksheet(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsCostService.getCostWorksheet(dashBoardReq),
                HttpStatus.OK);
    }

}
