package com.rjtech.mw.service.impl.dashboards;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.dashboards.constants.DashBoardUrlConstants;
import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;
import com.rjtech.mw.service.dashboards.MWDashBoardsLabourService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwDashBoardsLabourService")
@RJSService(modulecode = "mwDashBoardsLabourService")
@Transactional
public class MWDashBoardLabourServiceImpl extends RestConfigServiceImpl implements MWDashBoardsLabourService {

    public DashBoardsResp getLabourHealthCheck(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS + DashBoardUrlConstants.GET_LABOUR_HOURS_HEALTH_CHECK);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getCVandSVforLabourBubbleChart(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_CV_AND_SV_FOR_LABOUR_HOURS_BUBBLE_CHART);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getOriginalEstimateAtCompletionManhrs(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_ORIGINAL_AND_ESTIMATE_AT_COMPLETION_MANHOURS);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getPlanActualEarnedManhrs(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_PLAN_VS_ACTUAL_VS_EARNED_DIRECT_MANHOURS);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

}
