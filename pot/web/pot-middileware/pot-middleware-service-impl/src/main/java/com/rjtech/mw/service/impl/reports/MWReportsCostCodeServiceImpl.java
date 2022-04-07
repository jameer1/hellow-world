package com.rjtech.mw.service.impl.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.costcode.reports.req.CostCodeActualGetReq;
import com.rjtech.costcode.reports.req.CostCodeItemGetReq;
import com.rjtech.costcode.reports.req.CostCodeReportGetReq;
import com.rjtech.material.reports.req.MaterialConsumptionGetReq;
import com.rjtech.material.reports.req.MaterialInventoryGetReq;
import com.rjtech.material.reports.req.MaterialReportGetReq;
import com.rjtech.mw.service.reports.MWReportsCostCodeService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.reports.constants.ReportsCostURLConstants;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwReportsCostCodeService")
@RJSService(modulecode = "mwReportsCostCodeService")
@Transactional
public class MWReportsCostCodeServiceImpl extends RestConfigServiceImpl implements MWReportsCostCodeService {

    public ReportsResp getCostDateWiseActualReport(CostCodeActualGetReq costCodeActualGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(costCodeActualGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_DATE_WISE_ACTUAL_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostDateWisePlannedReport(CostCodeReportGetReq costCodeReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(costCodeReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_DATE_WISE_PLANNED_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostPeriodicalPlannedReport(CostCodeReportGetReq costCodeReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(costCodeReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_PERIODICAL_PLANNED_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostCodeVarienceReport(CostCodeReportGetReq costCodeReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(costCodeReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_CODE_VARIENCE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostItemBudgetReport(CostCodeItemGetReq costCodeItemGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(costCodeItemGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_ITEM_BUDGET_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostItemWisePlanVsActualReport(CostCodeReportGetReq costCodeReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(costCodeReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_ITEM_WISE_PLAN_VS_ACTUAL_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCostPerformanceIndexReport(CostCodeReportGetReq costCodeReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(costCodeReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_PERFORMANCE_INDEX_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }
    
    public DateWiseCostReportResp getDatewiseActualCostDetails(CostReportReq costReportReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(costReportReq),
				ReportsCostURLConstants.REPORTS_PARH_URL + ReportsCostURLConstants.GET_COST_CODE_DATE_WISE_ACTUAL_COST_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), DateWiseCostReportResp.class);
	}

}
