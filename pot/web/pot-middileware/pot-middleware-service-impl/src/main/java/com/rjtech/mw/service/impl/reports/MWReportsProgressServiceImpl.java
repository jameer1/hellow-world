package com.rjtech.mw.service.impl.reports;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.reports.MWReportsProgressService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.progress.reports.req.ProgressReportGetReq;
import com.rjtech.projschedule.constans.ProjScheduleURLConstants;
import com.rjtech.projschedule.resp.ProjScheduleSOWItemResp;
import com.rjtech.projsettings.constans.ProjSettingsURLConstants;
import com.rjtech.projsettings.req.ProjSettingsFilterReq;
import com.rjtech.projsettings.resp.ProjStatusDatesResp;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.cost.resp.ProgressSCurveTOResp;
import com.rjtech.reports.resp.PlannedValuesReportResp;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwReportsProgressService")
@RJSService(modulecode = "mwReportsProgressService")
@Transactional
public class MWReportsProgressServiceImpl extends RestConfigServiceImpl implements MWReportsProgressService {

    public ReportsResp getPeriodicalReport(ProgressReportGetReq progressReportGetReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(progressReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_PERIODICAL_PROGRESS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getDateWiseProgressReport(ProgressReportGetReq progressReportGetReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(progressReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_DATE_WISE_PROGRESS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public PlannedValuesReportResp getPlannedVsActualDetails(ProgressReportGetReq progressReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(progressReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_PLANNED_VS_ACTUAL_PROGRESS_REPORT);
        //ProjScheduleSowForReportsGetReq projScheduleSowForReportsGetReq = new ProjScheduleSowForReportsGetReq();
        ResponseEntity<String> sowResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(progressReportGetReq),
                ProjScheduleURLConstants.PROJ_SCHEDULE_PARH_URL + ProjScheduleURLConstants.GET_SOW_FOR_REPORTS);

        List<ProjScheduleSOWItemResp> sowList = AppUtils.fromJson(sowResponse.getBody(), List.class);
        ReportsResp reportsResp = AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);

        PlannedValuesReportResp combinedResponse = new PlannedValuesReportResp();
        combinedResponse.setReportsResp(reportsResp);
        combinedResponse.setSowList(sowList);
        return combinedResponse;
    }

    public ReportsResp getProgressClaimReport(ProgressReportGetReq progressReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(progressReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_PROGRESS_CLAIM_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }
    
    public ProjStatusDatesResp getProjectsDatesForProgressSCurveReport(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ReportsURLConstants.GET_PROJECTS_DATES_FOR_PROGRESS_S_CURVE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ProjStatusDatesResp.class);
    }

	@Override
	public ProgressSCurveTOResp getProgressSCurveReportData(ProgressReportGetReq progressReportGetReq) {
		ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(progressReportGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ReportsURLConstants.GET_PROGRESS_S_CURVE_REPORT_DATA);
        return AppUtils.fromJson(strResponse.getBody(), ProgressSCurveTOResp.class);
	}
	
	@Override
	public ProgressSCurveTOResp getProgressSCurveManpowerReportData(ProgressReportGetReq progressReportGetReq) {
		ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(progressReportGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ReportsURLConstants.GET_PROGRESS_S_CURVE_MANPOWER_REPORT_DATA);
        return AppUtils.fromJson(strResponse.getBody(), ProgressSCurveTOResp.class);
	}
}
