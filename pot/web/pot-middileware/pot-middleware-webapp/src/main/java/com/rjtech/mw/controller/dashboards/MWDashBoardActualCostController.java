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
import com.rjtech.mw.service.dashboards.MWDashBoardsActualCostService;

@RestController
@RequestMapping(DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS)
public class MWDashBoardActualCostController {

    @Autowired
    private MWDashBoardsActualCostService mwDashBoardsActualCostService;

    @RequestMapping(value = DashBoardUrlConstants.GET_ACTUAL_COST_BY_COUNTRY, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getActualCostByCountry(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsActualCostService.getActualCostByCountry(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ACTUAL_COST_BY_PROVINCE, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getActualCostByProvince(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsActualCostService.getActualCostByProvince(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ACTUAL_COST_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getActualCostByProject(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsActualCostService.getActualCostByProject(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_ACTUAL_COST_BY_PROJECTMANAGER, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getActualCostByProjectManager(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsActualCostService.getActualCostByProjectManager(dashBoardReq), HttpStatus.OK);
    }
}
