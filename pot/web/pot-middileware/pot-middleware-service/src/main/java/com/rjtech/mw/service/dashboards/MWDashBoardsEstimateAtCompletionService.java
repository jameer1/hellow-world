package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsEstimateAtCompletionService {

    DashBoardsResp getEstimateAtCompletionByCountry(DashBoardGetReq dashBoardReq);

    DashBoardsResp getEstimateAtCompletionByProvince(DashBoardGetReq dashBoardReq);

    DashBoardsResp getEstimateAtCompletionByProject(DashBoardGetReq dashBoardReq);

    DashBoardsResp getEstimateAtCompletionByProjectManager(DashBoardGetReq dashBoardReq);

}
