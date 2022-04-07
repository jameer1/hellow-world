package com.rjtech.projsettings.service;

import java.util.List;

import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.CostItemActualReportResp;
import com.rjtech.reports.cost.resp.CostItemActualReportTO;
import com.rjtech.reports.cost.resp.CostItemWiseReportResp;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.reports.cost.resp.PeriodCostTO;

public interface CostCodeActualDetailsService {

    /**
     * Fetch date wise cost code detail report
     * 
     * @param costReportReq
     * @return
     */
    DateWiseCostReportResp getDatewiseActualCostDetails(CostReportReq costReportReq);

    /**
     * Fetch item wise budget (Revised/Original)
     * 
     * @param projIds
     * @return
     */
    CostItemWiseReportResp getCostCodeBudgetReport(List<Long> projIds);

    /**
     * Date wise report with earned values
     * 
     * @param costReportReq
     * @return
     */
    DateWiseCostReportResp getDateWisePlanActualEarned(CostReportReq costReportReq);

    /**
     * Periodical cost report, group by costitem
     * 
     * @param costReportReq
     * @return
     */
    List<PeriodCostTO> getPeriodicalWiseReport(CostReportReq costReportReq);

    /**
     * Get total actual and earnedValues per costitem and costitem contains reported
     * period date wise actual and earnedValues
     * 
     * @param costReportReq
     * @return
     */
    List<PeriodCostTO> getCostCodeWiseReport(CostReportReq costReportReq);

    /**
     * Return actual value for date + proj with given period AND ratio of budget
     * investment for labor, plant, mat, others
     * 
     * @param costReportReq
     * @return
     */
    CostItemActualReportResp getDateProjWiseActualReport(CostReportReq costReportReq);

}
