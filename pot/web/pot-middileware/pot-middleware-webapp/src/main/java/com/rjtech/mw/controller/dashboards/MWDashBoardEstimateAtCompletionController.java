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
import com.rjtech.mw.service.dashboards.MWDashBoardsEstimateAtCompletionService;

@RestController
@RequestMapping(DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS)
public class MWDashBoardEstimateAtCompletionController {

    @Autowired
    private MWDashBoardsEstimateAtCompletionService mwDashBoardsEstimateAtCompletionService;

    @RequestMapping(value = DashBoardUrlConstants.GET_ESTIAMTE_AT_COMLETION_BY_COUNTRY, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEstimateAtCompletionByCountry(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsEstimateAtCompletionService.getEstimateAtCompletionByCountry(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ESTIAMTE_AT_COMLETION_BY_PROVINCE, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEstimateAtCompletionByProvince(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsEstimateAtCompletionService.getEstimateAtCompletionByProvince(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ESTIAMTE_AT_COMLETION_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEstimateAtCompletionByProject(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsEstimateAtCompletionService.getEstimateAtCompletionByProject(dashBoardReq), HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ESTIAMTE_AT_COMLETION_BY_PROJECTMANAGER, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEstimateAtCompletionByProjectManager(
            @RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsEstimateAtCompletionService.getEstimateAtCompletionByProjectManager(dashBoardReq),
                HttpStatus.OK);
    }

}
