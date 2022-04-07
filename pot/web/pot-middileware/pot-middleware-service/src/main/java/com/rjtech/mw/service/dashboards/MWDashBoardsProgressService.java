package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsProgressService {

    DashBoardsResp getPlanVsActualProgress(DashBoardGetReq dashBoardReq);

}
