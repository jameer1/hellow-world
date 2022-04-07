package com.rjtech.mw.service.impl.dashboards;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.dashboards.constants.DashBoardUrlConstants;
import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;
import com.rjtech.mw.service.dashboards.MWDashBoardsCostService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwDashBoardsCostService")
@RJSService(modulecode = "mwDashBoardsCostService")
@Transactional
public class MWDashBoardsCostServiceImpl extends RestConfigServiceImpl implements MWDashBoardsCostService {

    public DashBoardsResp getCostHealthCheck(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS + DashBoardUrlConstants.GET_COST_HEALTH_CHECK);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getCVandSVforCostBubbleChart(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_CV_AND_SV_FOR_COST_BUBBLE_CHART);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getOriginalEstimateAtCompletionCost(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_ORIGINAL_AND_ESTIMATE_AT_COMPLETION_COST);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getPlanActualEarnedCost(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_PLAN_VS_ACTUAL_VS_EARNED_COST);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getCostWorksheet(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS + DashBoardUrlConstants.GET_COST_WORKSHEET);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

}
