package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsPerformanceService {

    DashBoardsResp getProgressVariancePerformanceIndices(DashBoardGetReq dashBoardReq);

    DashBoardsResp getCostSchedulePerformance(DashBoardGetReq dashBoardReq);

    DashBoardsResp getPerformanceCostsheduleVarianceUnits(DashBoardGetReq dashBoardReq);

    DashBoardsResp getCostScheduleVariancePercentage(DashBoardGetReq dashBoardReq);

    DashBoardsResp getCostSchedulePerformanceIndices(DashBoardGetReq dashBoardReq);

    DashBoardsResp getToCompletePerformanceIndex(DashBoardGetReq dashBoardReq);

}
