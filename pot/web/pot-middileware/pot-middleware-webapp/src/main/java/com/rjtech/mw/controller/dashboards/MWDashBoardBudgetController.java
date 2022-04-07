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
import com.rjtech.mw.service.dashboards.MWDashBoardsBudgetService;

@RestController
@RequestMapping(DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS)
public class MWDashBoardBudgetController {

    @Autowired
    private MWDashBoardsBudgetService mwDashBoardsBudgetService;

    @RequestMapping(value = DashBoardUrlConstants.GET_BUDGET_BY_COUNTRY, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getBudgetByCountry(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsBudgetService.getBudgetByCountry(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_BUDGET_BY_PROVINCE, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getBudgetByProvince(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsBudgetService.getBudgetByProvince(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_BUDGET_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getBudgetByProject(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsBudgetService.getBudgetByProject(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_BUDGET_BY_PROJECTMANAGER, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getBudgetByProjectManager(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsBudgetService.getBudgetByProjectManager(dashBoardReq),
                HttpStatus.OK);
    }

}
