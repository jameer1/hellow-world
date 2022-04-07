package com.rjtech.mw.service.reports;

import com.rjtech.progress.reports.req.ProgressReportGetReq;
import com.rjtech.projsettings.req.ProjSettingsFilterReq;
import com.rjtech.projsettings.resp.ProjStatusDatesResp;
import com.rjtech.reports.cost.resp.ProgressSCurveTOResp;
import com.rjtech.reports.resp.PlannedValuesReportResp;
import com.rjtech.reports.resp.ReportsResp;

public interface MWReportsProgressService {

    ReportsResp getPeriodicalReport(ProgressReportGetReq progressReportGetReq);

    ReportsResp getDateWiseProgressReport(ProgressReportGetReq progressReportGetReq);

    PlannedValuesReportResp getPlannedVsActualDetails(ProgressReportGetReq progressReportGetReq);

    ReportsResp getProgressClaimReport(ProgressReportGetReq progressReportGetReq);
    ProjStatusDatesResp getProjectsDatesForProgressSCurveReport(ProjSettingsFilterReq projSettingsFilterReq);
    ProgressSCurveTOResp getProgressSCurveReportData(ProgressReportGetReq progressReportGetReq);
    ProgressSCurveTOResp getProgressSCurveManpowerReportData(ProgressReportGetReq progressReportGetReq);

}
