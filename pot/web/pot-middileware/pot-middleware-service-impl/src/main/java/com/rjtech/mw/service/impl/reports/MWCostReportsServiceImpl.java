package com.rjtech.mw.service.impl.reports;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.reports.MWCostReportsService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.reports.constants.ReportsCostURLConstants;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.reports.cost.resp.PeriodCostTO;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwCostReportsService")
@RJSService(modulecode = "mwCostReportsService")
@Transactional
public class MWCostReportsServiceImpl extends RestConfigServiceImpl implements MWCostReportsService {

	private int Long;

	public String getDatewiseActualCostDetails(CostReportReq costReportReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(costReportReq),
				ReportsCostURLConstants.REPORTS_PARH_URL + ReportsCostURLConstants.GET_COST_CODE_DATE_WISE_ACTUAL_COST_REPORT);
		System.out.println("strResponse.getBody() " + strResponse.getBody());
		return strResponse.getBody();
	}

	@Override
	public List<PeriodCostTO> getPeriodicalWiseReport(CostReportReq costReportReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(costReportReq),
				ReportsCostURLConstants.REPORTS_PARH_URL + ReportsCostURLConstants.GET_PERIODICAL_WISE_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public String getDateWisePlanActualEarned(CostReportReq costReportReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(costReportReq),
				ReportsCostURLConstants.REPORTS_PARH_URL + ReportsCostURLConstants.GET_DATE_WISE_PLANNED_ACTUAL_EARNED_REPORT);
		System.out.println("strResponse.getBody() " + strResponse.getBody());
		return strResponse.getBody();
	}

	@Override
	public List<PeriodCostTO> getCostCodeWiseReport(CostReportReq costReportReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(costReportReq),
				ReportsCostURLConstants.REPORTS_PARH_URL + ReportsCostURLConstants.GET_COST_CODE_WISE_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public String getCostCodeBudgetReport(CostReportReq costReportReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(costReportReq),
				ReportsCostURLConstants.REPORTS_PARH_URL + ReportsCostURLConstants.GET_COST_CODE_BUDGET_REPORT);
		return strResponse.getBody();
	}

	@Override
	public String getDateProjWiseActualReport(CostReportReq costReportReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(costReportReq),
				ReportsCostURLConstants.REPORTS_PARH_URL + ReportsCostURLConstants.GET_DATE_PROJ_WISE_ACTUAL_REPORT);
		System.out.println("strResponse.getBody() " + strResponse.getBody());
		return strResponse.getBody();
	}

    

}
