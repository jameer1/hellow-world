package com.rjtech.mw.service.impl.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.material.reports.req.MaterialConsumptionGetReq;
import com.rjtech.material.reports.req.MaterialInventoryGetReq;
import com.rjtech.material.reports.req.MaterialReportGetReq;
import com.rjtech.mw.service.reports.MWReportsMaterialService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwReportsMaterialService")
@RJSService(modulecode = "mwReportsMaterialService")
@Transactional
public class MWReportsMaterialServiceImpl extends RestConfigServiceImpl implements MWReportsMaterialService {

    public ReportsResp getMaterialDeliverySupplyReport(MaterialReportGetReq materialReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(materialReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MATERIAL_DELIVERY_SUPPLY_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getMaterialDailyIssueReport(MaterialReportGetReq materialReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(materialReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MATERIAL_DAILY_ISSUE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getMaterialStockBalInTransitReport(MaterialReportGetReq materialReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(materialReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MATERIAL_STOCK_BAL_IN_TRANSIT_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getMaterialStockPilesReport(MaterialReportGetReq materialReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(materialReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MATERIAL_STOCK_PILES_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getMaterialDateWiseConsuReport(MaterialConsumptionGetReq materialConsumptionGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(materialConsumptionGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MATERIAL_DATE_WISE_CONSU_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getMaterialPeriodicalConsuReport(MaterialConsumptionGetReq materialConsumptionGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(materialConsumptionGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MATERIAL_PERIODICAL_CONSU_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getMaterialInventoryReport(MaterialInventoryGetReq materialInventoryGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(materialInventoryGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MATERIAL_INVENTORY_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

}
