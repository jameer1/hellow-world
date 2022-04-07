
package com.rjtech.mw.service.dashboards;

import com.rjtech.dashboards.req.DashBoardGetReq;
import com.rjtech.dashboards.resp.DashBoardsResp;

public interface MWDashBoardsEarnedValueService {

    DashBoardsResp getEarnedValueByCountry(DashBoardGetReq dashBoardReq);

    DashBoardsResp getEarnedValueByProvince(DashBoardGetReq dashBoardReq);

    DashBoardsResp getEarnedValueByProject(DashBoardGetReq dashBoardReq);

    DashBoardsResp getEarnedValueByProjectManager(DashBoardGetReq dashBoardReq);

}
