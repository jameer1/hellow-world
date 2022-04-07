package com.rjtech.mw.service.impl.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.manpower.reports.req.ManpowerPeroidicalHrsGetReq;
import com.rjtech.manpower.reports.req.ManpowerPlanVsActVsEarnedGetReq;
import com.rjtech.manpower.reports.req.ManpowerActVsStandHrsGetReq;
import com.rjtech.manpower.reports.req.ManpowerCostCodeGetReq;
import com.rjtech.manpower.reports.req.ManpowerCurrentEmpGetReq;
import com.rjtech.manpower.reports.req.ManpowerGenderGetReq;
import com.rjtech.manpower.reports.req.ManpowerIdleHrsGetReq;
import com.rjtech.manpower.reports.req.ManpowerMobilisationGetReq;
import com.rjtech.mw.service.reports.MWReportsManpowerService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwReportsManpowerService")
@RJSService(modulecode = "mwReportsManpowerService")
@Transactional
public class MWReportsManpowerServiceImpl extends RestConfigServiceImpl implements MWReportsManpowerService {

    public ReportsResp getManpowerDateWiseHrsReport(ManpowerPeroidicalHrsGetReq manpowerDateWiseHrsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(manpowerDateWiseHrsGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MANPOWER_DATE_WISE_HRS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getManpowerGenderStatisticsReport(ManpowerGenderGetReq manpowerGenderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(manpowerGenderGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MANPOWER_GENDER_STATUSTICS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getManpowerIdleHrsReport(ManpowerIdleHrsGetReq manpowerIdleHrsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(manpowerIdleHrsGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MANPOWER_IDLE_HRS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getManpowerPeriodicalReport(ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(manpowerPeroidicalHrsGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MANPOWER_PERIODICAL_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getManpowerCostCodeWiseReport(ManpowerCostCodeGetReq manpowerCostCodeGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(manpowerCostCodeGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MANPOWER_COST_CODE_WISE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getManpowerActualStandardReport(ManpowerActVsStandHrsGetReq manpowerActVsStandHrsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(manpowerActVsStandHrsGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MANPOWER_ACTUAL_STANDARD_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getManpowerPeriodicalMobilisationReport(ManpowerMobilisationGetReq manpowerMobilisationGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(manpowerMobilisationGetReq),
                ReportsURLConstants.REPORTS_PARH_URL
                        + ReportsURLConstants.GET_MANPOWER_PERIODICAL_MOBILISATION_HRS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getManpowerCurrentEmployeeReport(ManpowerCurrentEmpGetReq manpowerCurrentEmpGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(manpowerCurrentEmpGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MANPOWER_CURRENT_EMPLOYEE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getManpowerPlanActualEarnedReport(
            ManpowerPlanVsActVsEarnedGetReq manpowerPlanVsActVsEarnedGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(manpowerPlanVsActVsEarnedGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_MANPOWER_PLAN_ACTUAL_EARNED_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

}
