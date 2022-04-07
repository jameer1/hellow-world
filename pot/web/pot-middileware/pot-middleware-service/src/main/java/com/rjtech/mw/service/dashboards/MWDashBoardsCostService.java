package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsCostService {

    DashBoardsResp getCostHealthCheck(DashBoardGetReq dashBoardReq);

    DashBoardsResp getCVandSVforCostBubbleChart(DashBoardGetReq dashBoardReq);

    DashBoardsResp getOriginalEstimateAtCompletionCost(DashBoardGetReq dashBoardReq);

    DashBoardsResp getPlanActualEarnedCost(DashBoardGetReq dashBoardReq);

    DashBoardsResp getCostWorksheet(DashBoardGetReq dashBoardReq);

}
