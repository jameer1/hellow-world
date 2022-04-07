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
import com.rjtech.mw.service.dashboards.MWDashBoardsEarnedValueService;

@RestController
@RequestMapping(DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS)
public class MWDashBoardEarnedValueController {

    @Autowired
    private MWDashBoardsEarnedValueService mwDashBoardsEarnedValueService;

    @RequestMapping(value = DashBoardUrlConstants.GET_EARNED_VALUE_BY_COUNTRY, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEarnedValueByCountry(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsEarnedValueService.getEarnedValueByCountry(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_EARNED_VALUE_BY_PROVINCE, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEarnedValueByProvince(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsEarnedValueService.getEarnedValueByProvince(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_EARNED_VALUE_BY_PROJECT, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEarnedValueByProject(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsEarnedValueService.getEarnedValueByProject(dashBoardReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = DashBoardUrlConstants.GET_EARNED_VALUE_BY_PROJECTMANAGER, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getEarnedValueByProjectManager(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(
                mwDashBoardsEarnedValueService.getEarnedValueByProjectManager(dashBoardReq), HttpStatus.OK);
    }

}
