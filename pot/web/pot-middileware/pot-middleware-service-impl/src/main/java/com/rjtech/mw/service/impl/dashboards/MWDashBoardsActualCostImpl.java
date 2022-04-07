package com.rjtech.mw.service.impl.dashboards;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.dashboards.constants.DashBoardUrlConstants;
import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;
import com.rjtech.mw.service.dashboards.MWDashBoardsActualCostService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwDashBoardsActualCostService")
@RJSService(modulecode = "mwDashBoardsActualCostService")
@Transactional
public class MWDashBoardsActualCostImpl extends RestConfigServiceImpl implements MWDashBoardsActualCostService {

    public DashBoardsResp getActualCostByCountry(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS + DashBoardUrlConstants.GET_ACTUAL_COST_BY_COUNTRY);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getActualCostByProvince(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS + DashBoardUrlConstants.GET_ACTUAL_COST_BY_PROVINCE);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getActualCostByProject(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS + DashBoardUrlConstants.GET_ACTUAL_COST_BY_PROJECT);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getActualCostByProjectManager(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_ACTUAL_COST_BY_PROJECTMANAGER);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

}
