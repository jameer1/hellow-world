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
import com.rjtech.mw.service.dashboards.MWDashBoardsProgressService;

@RestController
@RequestMapping(DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS)
public class MWDashBoardProgressController {

    @Autowired
    private MWDashBoardsProgressService mwDashBoardsProgressService;

    @RequestMapping(value = DashBoardUrlConstants.GET_PLAN_VS_ACTUAL_PROGRESS, method = RequestMethod.POST)
    public ResponseEntity<DashBoardsResp> getPlanVsActualProgress(@RequestBody DashBoardGetReq dashBoardReq) {
        return new ResponseEntity<DashBoardsResp>(mwDashBoardsProgressService.getPlanVsActualProgress(dashBoardReq),
                HttpStatus.OK);
    }

}
