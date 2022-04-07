package com.rjtech.mw.service.reports;

import com.rjtech.material.reports.req.MaterialConsumptionGetReq;
import com.rjtech.material.reports.req.MaterialInventoryGetReq;
import com.rjtech.material.reports.req.MaterialReportGetReq;
import com.rjtech.reports.resp.ReportsResp;

public interface MWReportsMaterialService {

    ReportsResp getMaterialDeliverySupplyReport(MaterialReportGetReq materialReportGetReq);

    ReportsResp getMaterialDailyIssueReport(MaterialReportGetReq materialReportGetReq);

    ReportsResp getMaterialStockBalInTransitReport(MaterialReportGetReq materialReportGetReq);

    ReportsResp getMaterialStockPilesReport(MaterialReportGetReq materialReportGetReq);

    ReportsResp getMaterialDateWiseConsuReport(MaterialConsumptionGetReq materialConsumptionGetReq);

    ReportsResp getMaterialPeriodicalConsuReport(MaterialConsumptionGetReq materialConsumptionGetReq);

    ReportsResp getMaterialInventoryReport(MaterialInventoryGetReq materialInventoryGetReq);

}
