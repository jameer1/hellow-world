package com.rjtech.mw.controller.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.service.projlib.MWProjLibMapService;
import com.rjtech.mw.service.reports.MWReportsProgressService;
import com.rjtech.progress.reports.req.ProgressReportGetReq;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projsettings.req.ProjSettingsFilterReq;
import com.rjtech.projsettings.resp.ProjStatusDatesResp;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.cost.resp.ProgressSCurveTOResp;
import com.rjtech.reports.resp.PlannedValuesReportResp;
import com.rjtech.reports.resp.ReportsResp;

@RestController
@RequestMapping(ReportsURLConstants.REPORTS_PARH_URL)
public class MWReportsProgressController {

    @Autowired
    private MWReportsProgressService mwReportsProgressService;

    @Autowired
    private MWProjLibMapService mwProjLibMapService;

    @RequestMapping(value = ReportsURLConstants.GET_PERIODICAL_PROGRESS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getPeriodicalRecordsReport(
            @RequestBody ProgressReportGetReq progressReportGetReq) {
        ReportsResp reportsResp = mwReportsProgressService.getPeriodicalReport(progressReportGetReq);
        setSOWReportMaps(progressReportGetReq.getProjIds(), reportsResp);
        return new ResponseEntity<ReportsResp>(reportsResp, HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_DATE_WISE_PROGRESS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getDateWiseProgressRecordsReport(
            @RequestBody ProgressReportGetReq progressReportGetReq) {
        ReportsResp reportsResp = mwReportsProgressService.getDateWiseProgressReport(progressReportGetReq);
        setSOWReportMaps(progressReportGetReq.getProjIds(), reportsResp);
        return new ResponseEntity<ReportsResp>(reportsResp, HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_PLANNED_VS_ACTUAL_PROGRESS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<PlannedValuesReportResp> getPlannedVsActualProgressDetails(
            @RequestBody ProgressReportGetReq progressReportGetReq) {
        PlannedValuesReportResp reportsResp = mwReportsProgressService.getPlannedVsActualDetails(progressReportGetReq);
        setSOWReportMaps(progressReportGetReq.getProjIds(), reportsResp.getReportsResp());
        return new ResponseEntity<PlannedValuesReportResp>(reportsResp, HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_PROGRESS_CLAIM_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getProgressClaimRecordsReport(
            @RequestBody ProgressReportGetReq progressReportGetReq) {
        ReportsResp reportsResp = mwReportsProgressService.getProgressClaimReport(progressReportGetReq);
        setSORReportMaps(progressReportGetReq.getProjIds(), reportsResp);
        return new ResponseEntity<ReportsResp>(reportsResp, HttpStatus.OK);
    }

    private void setSOWReportMaps(List<Long> projIds, ReportsResp reportsResp) {
        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setProjIds(projIds);
        reportsResp.setSowItemMap(mwProjLibMapService.getMultiProjSOWItemMap(projGetReq).getLabelKeyTOList());
        reportsResp.setCostCodeItemMap(mwProjLibMapService.getMultiProjCostCodeMap(projGetReq).getLabelKeyTOList());
        reportsResp.setUserProjMap(mwProjLibMapService.getMultiProjCodeMap(projGetReq).getLabelKeyTOList());
        reportsResp.setUserEpsMap(mwProjLibMapService.getMultiEPSProjCodeMap(projGetReq).getLabelKeyTOList());
    }

    private void setSORReportMaps(List<Long> projIds, ReportsResp reportsResp) {
        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setProjIds(projIds);
        reportsResp.setSowItemMap(mwProjLibMapService.getMultiProjSORItemMap(projGetReq).getLabelKeyTOList());
        reportsResp.setCostCodeItemMap(mwProjLibMapService.getMultiProjCostCodeMap(projGetReq).getLabelKeyTOList());
        reportsResp.setUserProjMap(mwProjLibMapService.getMultiProjCodeMap(projGetReq).getLabelKeyTOList());
        reportsResp.setUserEpsMap(mwProjLibMapService.getMultiEPSProjCodeMap(projGetReq).getLabelKeyTOList());
    }
    
    @RequestMapping(value = ReportsURLConstants.GET_PROJECTS_DATES_FOR_PROGRESS_S_CURVE_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ProjStatusDatesResp> getProjectsDatesForProgressSCurveReport(@RequestBody ProjSettingsFilterReq projSettingsFilterReq) {
        return new ResponseEntity<ProjStatusDatesResp>(mwReportsProgressService.getProjectsDatesForProgressSCurveReport(projSettingsFilterReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ReportsURLConstants.GET_PROGRESS_S_CURVE_REPORT_DATA, method = RequestMethod.POST)
    public ResponseEntity<ProgressSCurveTOResp> getProgressSCurveReportData(@RequestBody ProgressReportGetReq progressReportGetReq) {
        return new ResponseEntity<ProgressSCurveTOResp>(mwReportsProgressService.getProgressSCurveReportData(progressReportGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ReportsURLConstants.GET_PROGRESS_S_CURVE_MANPOWER_REPORT_DATA, method = RequestMethod.POST)
    public ResponseEntity<ProgressSCurveTOResp> getProgressSCurveManpowerReportData(@RequestBody ProgressReportGetReq progressReportGetReq) {
        return new ResponseEntity<ProgressSCurveTOResp>(mwReportsProgressService.getProgressSCurveManpowerReportData(progressReportGetReq), HttpStatus.OK);
    }
}
