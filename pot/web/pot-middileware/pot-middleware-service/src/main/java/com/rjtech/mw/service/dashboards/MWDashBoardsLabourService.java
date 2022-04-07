package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsLabourService {

    DashBoardsResp getLabourHealthCheck(DashBoardGetReq dashBoardReq);

    DashBoardsResp getCVandSVforLabourBubbleChart(DashBoardGetReq dashBoardReq);

    DashBoardsResp getOriginalEstimateAtCompletionManhrs(DashBoardGetReq dashBoardReq);

    DashBoardsResp getPlanActualEarnedManhrs(DashBoardGetReq dashBoardReq);

}
