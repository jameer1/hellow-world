package com.rjtech.mw.service.reports;

import com.rjtech.manpower.reports.req.ManpowerActVsStandHrsGetReq;
import com.rjtech.manpower.reports.req.ManpowerCostCodeGetReq;
import com.rjtech.manpower.reports.req.ManpowerCurrentEmpGetReq;
import com.rjtech.manpower.reports.req.ManpowerGenderGetReq;
import com.rjtech.manpower.reports.req.ManpowerIdleHrsGetReq;
import com.rjtech.manpower.reports.req.ManpowerMobilisationGetReq;
import com.rjtech.manpower.reports.req.ManpowerPeroidicalHrsGetReq;
import com.rjtech.manpower.reports.req.ManpowerPlanVsActVsEarnedGetReq;
import com.rjtech.reports.resp.ReportsResp;

public interface MWReportsManpowerService {

    ReportsResp getManpowerDateWiseHrsReport(ManpowerPeroidicalHrsGetReq manpowerDateWiseHrsGetReq);

    ReportsResp getManpowerGenderStatisticsReport(ManpowerGenderGetReq manpowerGenderGetReq);

    ReportsResp getManpowerIdleHrsReport(ManpowerIdleHrsGetReq manpowerIdleHrsGetReq);

    ReportsResp getManpowerPeriodicalReport(ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq);

    ReportsResp getManpowerCostCodeWiseReport(ManpowerCostCodeGetReq manpowerCostCodeGetReq);

    ReportsResp getManpowerActualStandardReport(ManpowerActVsStandHrsGetReq manpowerActVsStandHrsGetReq);

    ReportsResp getManpowerPeriodicalMobilisationReport(ManpowerMobilisationGetReq manpowerMobilisationGetReq);

    ReportsResp getManpowerCurrentEmployeeReport(ManpowerCurrentEmpGetReq manpowerCurrentEmpGetReq);

    ReportsResp getManpowerPlanActualEarnedReport(ManpowerPlanVsActVsEarnedGetReq manpowerPlanVsActVsEarnedGetReq);

}
