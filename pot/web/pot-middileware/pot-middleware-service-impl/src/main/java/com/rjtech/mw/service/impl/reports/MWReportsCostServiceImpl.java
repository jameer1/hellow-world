package com.rjtech.mw.service.impl.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.costperformance.reports.req.BudgetCostCodeGetReq;
import com.rjtech.costperformance.reports.req.CostSchedulePerformanceIndexReq;
import com.rjtech.costperformance.reports.req.CostScheduleVarianceReq;
import com.rjtech.costperformance.reports.req.DateWiseActualCostDetailsReq;
import com.rjtech.costperformance.reports.req.DateWisePlantActualDeatailsReq;
import com.rjtech.costperformance.reports.req.ItemWisePlantActualReq;
import com.rjtech.costperformance.reports.req.PeriodicalWisePlantActualReq;
import com.rjtech.mw.service.reports.MWReportsCostPerfomanceService;
import com.rjtech.mw.service.reports.MWReportsCostService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwReportsCostService")
public class MWReportsCostServiceImpl extends RestConfigServiceImpl
        implements MWReportsCostService {

	@Override
	public ReportsResp getDatewiseActualCostDetails(DateWiseActualCostDetailsReq actualCostDetailsReq) {
		ResponseEntity<String> strResponse = null;
        System.out.println("MWReportsCostPerformanceServiceImpl > getDatewiseActualCostDetails");
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(actualCostDetailsReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_COST_CODE_DATE_WISE_ACTUAL_COST_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
	}

	@Override
	public ReportsResp getCostCodePeriodicalPlantActualReport(
			PeriodicalWisePlantActualReq periodicalWisePlantActualReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportsResp getDatewisePlantActualCostDetails(
			DateWisePlantActualDeatailsReq dateWisePlantActualDeatailsReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportsResp getCostCodeCostScheduleVarianceReport(CostScheduleVarianceReq costScheduleVarianceReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportsResp getCostSchedulePerformanceIndexReport(
			CostSchedulePerformanceIndexReq costSchedulePerformanceIndexReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportsResp getCostCodeBudgetReport(BudgetCostCodeGetReq budgetCostCodeGetReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportsResp getCostCodeItemWisePlantActualReport(ItemWisePlantActualReq itemWisePlantActualReq) {
		// TODO Auto-generated method stub
		return null;
	}

}
