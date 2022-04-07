package com.rjtech.mw.service.impl.dashboards;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.dashboards.constants.DashBoardUrlConstants;
import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;
import com.rjtech.mw.service.dashboards.MWDashBoardsPerformanceService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwDashBoardsPerformanceService")
@RJSService(modulecode = "mwDashBoardsPerformanceService")
@Transactional
public class MWDashBoardsPerforanceServiceImpl extends RestConfigServiceImpl implements MWDashBoardsPerformanceService {

    public DashBoardsResp getProgressVariancePerformanceIndices(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_PROGRESS_VARIANCE_AND_PERFORMANCE_INDICES);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getCostSchedulePerformance(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS + DashBoardUrlConstants.GET_COST_SCHEDULE_PERFORMANCE);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getPerformanceCostsheduleVarianceUnits(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_PERFORMANCE_COST_SCHEDULE_VARIANCE_UNITS);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getCostScheduleVariancePercentage(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_COST_SCHEDULE_VARIANCE_PERCENTAGE);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getCostSchedulePerformanceIndices(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_COST_SCHEDULE_PERFORMANCE_INDICES);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

    public DashBoardsResp getToCompletePerformanceIndex(DashBoardGetReq dashBoardReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dashBoardReq),
                DashBoardUrlConstants.DASHBOARD_URL_CONSTANTS
                        + DashBoardUrlConstants.GET_TO_COMPLETE_PERFORMANCE_INDEX);
        return AppUtils.fromJson(strResponse.getBody(), DashBoardsResp.class);
    }

}
