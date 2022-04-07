package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsEstimateToCompletionService {

    DashBoardsResp getEstimateToCompleteByCountry(DashBoardGetReq dashBoardReq);

    DashBoardsResp getEstimateToCompleteByProvince(DashBoardGetReq dashBoardReq);

    DashBoardsResp getEstimateToCompleteByProject(DashBoardGetReq dashBoardReq);

    DashBoardsResp getEstimateToCompleteByProjectManager(DashBoardGetReq dashBoardReq);

}
