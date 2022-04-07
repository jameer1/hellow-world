package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsRemainingBudgetService {

    DashBoardsResp getRemainingBudgetByCountry(DashBoardGetReq dashBoardReq);

    DashBoardsResp getRemainingBudgetByProvince(DashBoardGetReq dashBoardReq);

    DashBoardsResp getRemainingBudgetByProject(DashBoardGetReq dashBoardReq);

    DashBoardsResp getRemainingBudgetByProjectManager(DashBoardGetReq dashBoardReq);

}
