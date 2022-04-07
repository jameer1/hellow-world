package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsActualCostService {

    DashBoardsResp getActualCostByCountry(DashBoardGetReq dashBoardReq);

    DashBoardsResp getActualCostByProvince(DashBoardGetReq dashBoardReq);

    DashBoardsResp getActualCostByProject(DashBoardGetReq dashBoardReq);

    DashBoardsResp getActualCostByProjectManager(DashBoardGetReq dashBoardReq);

}
