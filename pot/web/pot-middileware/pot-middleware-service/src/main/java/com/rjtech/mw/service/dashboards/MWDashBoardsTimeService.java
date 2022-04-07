package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsTimeService {

    DashBoardsResp getCostSchedulevarianceBubbleChart(DashBoardGetReq dashBoardReq);

    DashBoardsResp getCurrentDateProgressPercentage(DashBoardGetReq dashBoardReq);

    DashBoardsResp getTimeCostScheduleVarianceUnits(DashBoardGetReq dashBoardReq);

    DashBoardsResp getProjectGranttChart(DashBoardGetReq dashBoardReq);

}
