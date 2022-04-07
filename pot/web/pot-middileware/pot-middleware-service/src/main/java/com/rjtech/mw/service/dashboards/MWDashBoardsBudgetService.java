package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsBudgetService {

    DashBoardsResp getBudgetByCountry(DashBoardGetReq dashBoardReq);

    DashBoardsResp getBudgetByProvince(DashBoardGetReq dashBoardReq);

    DashBoardsResp getBudgetByProject(DashBoardGetReq dashBoardReq);

    DashBoardsResp getBudgetByProjectManager(DashBoardGetReq dashBoardReq);

}
