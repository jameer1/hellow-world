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
import com.rjtech.mw.service.dashboards.MWDashBoardsEstimateToCompletionService;

@RestController
@RequestMapping(DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS)
public class MWDashBoardEstimateToCompletionController {

    @Autowired
    private MWDashBoardsEstimateToCompletionService mwDashBoardsEstimateToCompletionService;

    @RequestMapping(value = DashBoardUrlConstants.GET_ESTIMATE_TO_COMPLETE_BY_COUNTRY, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEstimateToCompleteByCountry(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsEstimateToCompletionService.getEstimateToCompleteByCountry(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ESTIMATE_TO_COMPLETE_BY_PROVINCE, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEstimateToCompleteByProvince(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsEstimateToCompletionService.getEstimateToCompleteByProvince(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ESTIMATE_TO_COMPLETE_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEstimateToCompleteByProject(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsEstimateToCompletionService.getEstimateToCompleteByProject(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ESTIMATE_TO_COMPLETE_BY_PROJECTMANAGER, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEstimateToCompleteByProjectManager(
            @RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsEstimateToCompletionService.getEstimateToCompleteByProjectManager(dashBoardReq),
                HttpStatus.OK);
    }

}
