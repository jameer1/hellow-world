package com.rjtech.mw.service.impl.projectsettings;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.req.NotificationFilterReq;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.NotificationResp;
import com.rjtech.common.resp.ProvisionResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.projsettings.constans.ProjSettingsURLConstants;
import com.rjtech.projsettings.dto.ProjGenCurrencyResp;
import com.rjtech.projsettings.req.ChangeOrderDetailsGetReq;
import com.rjtech.projsettings.req.ChangeOrderDetailsSaveReq;
import com.rjtech.projsettings.req.ProjAttendenceApprSaveReq;
import com.rjtech.projsettings.req.ProjAttendenceGetReq;
import com.rjtech.projsettings.req.ProjAttendenceSaveReq;
import com.rjtech.projsettings.req.ProjCalenderGetReq;
import com.rjtech.projsettings.req.ProjCostBudgetGetReq;
import com.rjtech.projsettings.req.ProjCostBudgetSaveReq;
import com.rjtech.projsettings.req.ProjCostCodeStatusGetReq;
import com.rjtech.projsettings.req.ProjCostCodesSaveReq;
import com.rjtech.projsettings.req.ProjCostStatementsGetReq;
import com.rjtech.projsettings.req.ProjCostStatementsSaveReq;
import com.rjtech.projsettings.req.ProjEmpTransApprSaveReq;
import com.rjtech.projsettings.req.ProjEmpTransGetReq;
import com.rjtech.projsettings.req.ProjEmpTransSaveReq;
import com.rjtech.projsettings.req.ProjEstimateGetReq;
import com.rjtech.projsettings.req.ProjEstimateSaveReq;
import com.rjtech.projsettings.req.ProjGeneralOnLoadReq;
import com.rjtech.projsettings.req.ProjGeneralSaveReq;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.projsettings.req.ProjLeaveApprSaveReq;
import com.rjtech.projsettings.req.ProjLeaveRequestGetReq;
import com.rjtech.projsettings.req.ProjLeaveRequestSaveReq;
import com.rjtech.projsettings.req.ProjManpowerGetReq;
import com.rjtech.projsettings.req.ProjManpowerSaveReq;
import com.rjtech.projsettings.req.ProjMaterialTransApprSaveReq;
import com.rjtech.projsettings.req.ProjMaterialTransGetReq;
import com.rjtech.projsettings.req.ProjMaterialTransSaveReq;
import com.rjtech.projsettings.req.ProjMileStonesDateSaveReq;
import com.rjtech.projsettings.req.ProjNoteBookGetReq;
import com.rjtech.projsettings.req.ProjNoteBookSaveReq;
import com.rjtech.projsettings.req.ProjPayRollCycleGetReq;
import com.rjtech.projsettings.req.ProjPayRollCycleSaveReq;
import com.rjtech.projsettings.req.ProjPerfamanceDefaultSaveReq;
import com.rjtech.projsettings.req.ProjPerfomanceDelReq;
import com.rjtech.projsettings.req.ProjPerformenceThresholdGetReq;
import com.rjtech.projsettings.req.ProjPerformenceThresholdSaveReq;
import com.rjtech.projsettings.req.ProjPlantTransApprSaveReq;
import com.rjtech.projsettings.req.ProjPlantTransGetReq;
import com.rjtech.projsettings.req.ProjPlantTransSaveReq;
import com.rjtech.projsettings.req.ProjProcureApprSaveReq;
import com.rjtech.projsettings.req.ProjProcureGetReq;
import com.rjtech.projsettings.req.ProjProcureSaveReq;
import com.rjtech.projsettings.req.ProjProgressClaimApprSaveReq;
import com.rjtech.projsettings.req.ProjProgressClaimGetReq;
import com.rjtech.projsettings.req.ProjProgressClaimSaveReq;
import com.rjtech.projsettings.req.ProjProgressClaimePeroidSaveReq;
import com.rjtech.projsettings.req.ProjProgressGetReq;
import com.rjtech.projsettings.req.ProjProgressSaveReq;
import com.rjtech.projsettings.req.ProjReportsGetReq;
import com.rjtech.projsettings.req.ProjReportsSaveReq;
import com.rjtech.projsettings.req.ProjResourceCurveGetReq;
import com.rjtech.projsettings.req.ProjSettingsFilterReq;
import com.rjtech.projsettings.req.ProjSowsSaveReq;
import com.rjtech.projsettings.req.ProjStatusActualReq;
import com.rjtech.projsettings.req.ProjStatusDatesSaveReq;
import com.rjtech.projsettings.req.ProjStatusGetReq;
import com.rjtech.projsettings.req.ProjSummaryGetReq;
import com.rjtech.projsettings.req.ProjTimeSheetApprSaveReq;
import com.rjtech.projsettings.req.ProjTimeSheetGetReq;
import com.rjtech.projsettings.req.ProjTimeSheetSaveReq;
import com.rjtech.projsettings.req.ProjTimeSheetWeekSaveReq;
import com.rjtech.projsettings.req.ProjWorkDairyApprSaveReq;
import com.rjtech.projsettings.req.ProjWorkDairyGetReq;
import com.rjtech.projsettings.req.ProjWorkDairySaveReq;
import com.rjtech.projsettings.req.ProjectDefaultSaveReq;
import com.rjtech.projsettings.req.ProjectMaterialGetReq;
import com.rjtech.projsettings.req.ProjectMaterialSaveReq;
import com.rjtech.projsettings.req.ProjectPlantsGetReq;
import com.rjtech.projsettings.req.ProjectPlantsSaveReq;
import com.rjtech.projsettings.req.ProjectTangibleReq;
import com.rjtech.projsettings.req.ProvinenceGetReq;
import com.rjtech.projsettings.resp.CalenderResp;
import com.rjtech.projsettings.resp.ChangeOrderDetailsResp;
import com.rjtech.projsettings.resp.ProjAttendenceOnLoadResp;
import com.rjtech.projsettings.resp.ProjAttendenceResp;
import com.rjtech.projsettings.resp.ProjCostBudgetResp;
import com.rjtech.projsettings.resp.ProjCostCodeStatusResp;
import com.rjtech.projsettings.resp.ProjCostStaementsResp;
import com.rjtech.projsettings.resp.ProjCostStatementsSummaryResp;
import com.rjtech.projsettings.resp.ProjEmpTransResp;
import com.rjtech.projsettings.resp.ProjEstimateResp;
import com.rjtech.projsettings.resp.ProjGeneralOnLoadResp;
import com.rjtech.projsettings.resp.ProjGeneralsResp;
import com.rjtech.projsettings.resp.ProjLeaveRequestResp;
import com.rjtech.projsettings.resp.ProjManPowerResp;
import com.rjtech.projsettings.resp.ProjManPowerStatusResp;
import com.rjtech.projsettings.resp.ProjMaterialTransResp;
import com.rjtech.projsettings.resp.ProjMileStonesResp;
import com.rjtech.projsettings.resp.ProjNoteBookResp;
import com.rjtech.projsettings.resp.ProjPayRollCycleOnLoadResp;
import com.rjtech.projsettings.resp.ProjPerformenceThresholdResp;
import com.rjtech.projsettings.resp.ProjPlannedValueResp;
import com.rjtech.projsettings.resp.ProjPlantTransResp;
import com.rjtech.projsettings.resp.ProjProcureResp;
import com.rjtech.projsettings.resp.ProjProgressClaimResp;
import com.rjtech.projsettings.resp.ProjProgressClaimePeriodOnLoadResp;
import com.rjtech.projsettings.resp.ProjProgressClaimePeroidResp;
import com.rjtech.projsettings.resp.ProjProgressResp;
import com.rjtech.projsettings.resp.ProjReportsOnLoadResp;
import com.rjtech.projsettings.resp.ProjReportsResp;
import com.rjtech.projsettings.resp.ProjResourceCurveResp;
import com.rjtech.projsettings.resp.ProjStatusActualResp;
import com.rjtech.projsettings.resp.ProjStatusDatesResp;
import com.rjtech.projsettings.resp.ProjStatusResp;
import com.rjtech.projsettings.resp.ProjSummaryResp;
import com.rjtech.projsettings.resp.ProjTimeSheetOnLoadResp;
import com.rjtech.projsettings.resp.ProjTimeSheetResp;
import com.rjtech.projsettings.resp.ProjTimeSheetWeekResp;
import com.rjtech.projsettings.resp.ProjWorkDairyApprResp;
import com.rjtech.projsettings.resp.ProjWorkDairyOnLoadResp;
import com.rjtech.projsettings.resp.ProjWorkDairyResp;
import com.rjtech.projsettings.resp.ProjectGanntChartResp;
import com.rjtech.projsettings.resp.ProjectMaterialsResp;
import com.rjtech.projsettings.resp.ProjectPlantsResp;
import com.rjtech.projsettings.resp.ProjectPlantsStatusResp;
import com.rjtech.projsettings.resp.ProjectStatusResp;
import com.rjtech.projsettings.resp.ProjectTangibleResp;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.projsettings.resp.ProjBudgetResp;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.projsettings.req.SchofEstimatesSaveReq;
import com.rjtech.projsettings.dto.SchofEstimatesApprTO;
import com.rjtech.projsettings.req.SchofEstimatesGetReq;
import com.rjtech.projsettings.resp.ProjSchofEstimatesResp;

import com.rjtech.projsettings.req.SchofRatesSaveReq;
import com.rjtech.projsettings.resp.ProjSchofRatesResp;
import com.rjtech.projsettings.req.SchofRatesGetReq;

import com.rjtech.projsettings.req.ResourceBudgetSaveReq;
import com.rjtech.projsettings.resp.ProjResourceBudgetResp;
import com.rjtech.projsettings.req.ResourceBudgetGetReq;
import com.rjtech.projsettings.req.ProjSoeApprSaveReq;

@Service(value = "mwProjectSettingsService")
@RJSService(modulecode = "mwProjectSettingsService")
@Transactional
public class MWProjectSettingsServiceImpl extends RestConfigServiceImpl implements MWProjectSettingsService {

    public ProjGeneralOnLoadResp projGeneralOnLoadResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.PROJ_GENERALS_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), ProjGeneralOnLoadResp.class);
    }
    
    public ProjGeneralsResp getMultiProjGenerals(ProjGeneralsGetReq projGeneralsGetReq) {
    	ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projGeneralsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_MULTI_PROJ_GENERALS);
        return AppUtils.fromJson(strResponse.getBody(), ProjGeneralsResp.class);
    }
    
    public ProjCostStaementsResp getMultiProjCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
    	ResponseEntity<String> strResponse = null;
    	strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_MULTI_PROJ_COSTSTATEMENTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);
    }

    public ProjTimeSheetOnLoadResp projTimeSheetOnLoadResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.PROJ_TIMESHEET_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), ProjTimeSheetOnLoadResp.class);
    }

    public ProjGeneralsResp projGeneralsResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_GENERALS);
        return AppUtils.fromJson(strResponse.getBody(), ProjGeneralsResp.class);
    }

    public ProjWorkDairyApprResp projWorkDairyApprResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_WORKDAIRYAPPR);
        return AppUtils.fromJson(strResponse.getBody(), ProjWorkDairyApprResp.class);
    }

    public ProjAttendenceResp projAttendenceResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_ATTENDENCE);
        return AppUtils.fromJson(strResponse.getBody(), ProjAttendenceResp.class);
    }

    public ProjWorkDairyResp projWorkDairyResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_WORKDAIRY);
        return AppUtils.fromJson(strResponse.getBody(), ProjWorkDairyResp.class);
    }

    public ProjTimeSheetResp projTimeSheetResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_TIMESHEET);
        return AppUtils.fromJson(strResponse.getBody(), ProjTimeSheetResp.class);
    }

    public ProjProcureResp projProcureResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_PROCURE);
        return AppUtils.fromJson(strResponse.getBody(), ProjProcureResp.class);
    }

    public ProjEmpTransResp projEmpTransResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_EMPTRANS);
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpTransResp.class);
    }

    public ProjPlantTransResp projPlantTransResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_PLANTTRANS);
        return AppUtils.fromJson(strResponse.getBody(), ProjPlantTransResp.class);
    }

    public ProjMaterialTransResp projMaterialTransResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_MATERIALTRANS);
        return AppUtils.fromJson(strResponse.getBody(), ProjMaterialTransResp.class);
    }

    public ProjEstimateResp projEstimateResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_ESTIMATE);
        return AppUtils.fromJson(strResponse.getBody(), ProjEstimateResp.class);
    }

    public ProjCostBudgetResp projCostBudgetResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_COSTBUDGETS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostBudgetResp.class);
    }

    public ProjCostStaementsResp projCostStaementsResp(ProjSettingsFilterReq projSettingsFilterReq) {
        System.out.println("MWProjectSettingsServiceImpl:projCostStaementsResp");
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_COSTSTATEMENTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);
    }

    public ProjectPlantsResp projectPlantsResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJOJECT_PLANTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjectPlantsResp.class);
    }

    public ProjReportsResp projReportsResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_REPORTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjReportsResp.class);
    }

    public ProjManPowerResp projManPowerResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_MANPOWERS);
        return AppUtils.fromJson(strResponse.getBody(), ProjManPowerResp.class);
    }

    public ProjProgressResp projProgressResp(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_PROGRESS);
        return AppUtils.fromJson(strResponse.getBody(), ProjProgressResp.class);
    }

    public AppResp saveProjAttendenceAppr(ProjAttendenceApprSaveReq projAttendenceApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projAttendenceApprSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_ATTENDENCEAPPR);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjGeneralsResp getProjGenerals(ProjGeneralsGetReq projGeneralsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projGeneralsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_GENERALS);
        return AppUtils.fromJson(strResponse.getBody(), ProjGeneralsResp.class);
    }

    public ProjGeneralsResp saveProjGenerals(ProjGeneralSaveReq projGeneralSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projGeneralSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_GENERALS);
        return AppUtils.fromJson(strResponse.getBody(), ProjGeneralsResp.class);
    }

    public AppResp saveWorkDairyAppr(ProjWorkDairyApprSaveReq projWorkDairyApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projWorkDairyApprSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_WORKDAIRYAPPR);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjAttendenceResp getProjAttendence(ProjAttendenceGetReq projAttendenceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projAttendenceGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_ATTENDENCE);
        return AppUtils.fromJson(strResponse.getBody(), ProjAttendenceResp.class);
    }

    public ProjAttendenceResp saveProjAttendence(ProjAttendenceSaveReq projAttendenceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projAttendenceSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_ATTENDENCE);
        return AppUtils.fromJson(strResponse.getBody(), ProjAttendenceResp.class);
    }

    public ProjWorkDairyResp getWorkDairy(ProjWorkDairyGetReq projWorkDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projWorkDairyGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_WORKDAIRY);
        return AppUtils.fromJson(strResponse.getBody(), ProjWorkDairyResp.class);
    }

    public ProjWorkDairyResp saveWorkDairy(ProjWorkDairySaveReq projWorkDairySaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projWorkDairySaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_WORKDAIRY);
        return AppUtils.fromJson(strResponse.getBody(), ProjWorkDairyResp.class);
    }

    public ProjTimeSheetResp getProjTimeSheet(ProjTimeSheetGetReq projTimeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projTimeSheetGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_TIMESHEET);

        return AppUtils.fromJson(strResponse.getBody(), ProjTimeSheetResp.class);
    }

    public ProjTimeSheetResp saveProjTimeSheet(ProjTimeSheetSaveReq projTimeSheetSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projTimeSheetSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_TIMESHEET);
        return AppUtils.fromJson(strResponse.getBody(), ProjTimeSheetResp.class);
    }

    public AppResp saveProjTimeSheetAppr(ProjTimeSheetApprSaveReq projTimeSheetApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projTimeSheetApprSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_TIMESHEETAPPR);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjProcureResp getProjProcure(ProjProcureGetReq projProcureGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProcureGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_PROCURE);
        return AppUtils.fromJson(strResponse.getBody(), ProjProcureResp.class);
    }

    public ProjProcureResp saveProjProcurement(ProjProcureSaveReq projProcureSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProcureSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_PROCURE);
        return AppUtils.fromJson(strResponse.getBody(), ProjProcureResp.class);
    }

    public AppResp saveProjProcurementAppr(ProjProcureApprSaveReq projProcureApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProcureApprSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_PROCUREAPPR);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjEmpTransResp getEmpTrans(ProjEmpTransGetReq projEmpTransGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projEmpTransGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_EMPTRANS);
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpTransResp.class);
    }

    public ProjEmpTransResp saveEmpTrans(ProjEmpTransSaveReq projEmpTransSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projEmpTransSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_EMPTRANS);
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpTransResp.class);
    }

    public AppResp saveEmpTransAppr(ProjEmpTransApprSaveReq projEmpTransApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projEmpTransApprSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_EMPTRANSAPPR);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjPlantTransResp getProjPlantTrans(ProjPlantTransGetReq projPlantTransGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projPlantTransGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_PLANTTRANS);
        return AppUtils.fromJson(strResponse.getBody(), ProjPlantTransResp.class);
    }

    public AppResp saveEmpTransAppr(ProjPlantTransSaveReq projPlantTransSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projPlantTransSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_PLANTTRANS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjPlantTransResp saveProjPlantTrans(ProjPlantTransSaveReq projPlantTransSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projPlantTransSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_PLANTTRANS);
        return AppUtils.fromJson(strResponse.getBody(), ProjPlantTransResp.class);
    }

    public AppResp saveProjPlantTransAppr(ProjPlantTransApprSaveReq projPlantTransApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projPlantTransApprSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_PLANTTRANSAPPR);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjMaterialTransResp getProjMaterialTrans(ProjMaterialTransGetReq projMaterialTransGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projMaterialTransGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_MATERIALTRANS);
        return AppUtils.fromJson(strResponse.getBody(), ProjMaterialTransResp.class);
    }

    public ProjMaterialTransResp saveProjMaterialTrans(ProjMaterialTransSaveReq projMaterialTransSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projMaterialTransSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_MATERIALTRANS);
        return AppUtils.fromJson(strResponse.getBody(), ProjMaterialTransResp.class);
    }

    public AppResp saveProjMaterialTransAppr(ProjMaterialTransApprSaveReq projMaterialTransApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projMaterialTransApprSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_MATERIALTRANSAPPR);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjEstimateResp getProjEstimate(ProjEstimateGetReq projEstimateGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projEstimateGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_ESTIMATE);
        return AppUtils.fromJson(strResponse.getBody(), ProjEstimateResp.class);
    }

    public ProjEstimateResp saveProjEstimate(ProjEstimateSaveReq projEstimateSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projEstimateSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_ESTIMATE);
        return AppUtils.fromJson(strResponse.getBody(), ProjEstimateResp.class);
    }

    public ProjectMaterialsResp getProjectMaterials(ProjectMaterialGetReq projectMaterialGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projectMaterialGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJOJECT_MATERIALS);
        return AppUtils.fromJson(strResponse.getBody(), ProjectMaterialsResp.class);
    }

    public ProjectMaterialsResp saveProjectMaterials(ProjectMaterialSaveReq projectMaterialSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projectMaterialSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJOJECT_MATERIALS);
        return AppUtils.fromJson(strResponse.getBody(), ProjectMaterialsResp.class);
    }

    public ProjCostBudgetResp getProjCostBudgets(ProjCostBudgetGetReq projCostBudgetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostBudgetGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_COSTBUDGETS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostBudgetResp.class);
    }

    public AppResp saveProjCostBudgets(ProjCostBudgetSaveReq projCostBudgetSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostBudgetSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_COSTBUDGETS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjCostStaementsResp getProjCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
        System.out.println("MWProjectSettingsServiceImpl:projCostStaementsResp 22");
        ProjCostStaementsResp projCostStaementsResp =null;
        try{


            ResponseEntity<String> strResponse = null;
            strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                    ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_COSTSTATEMENTS);
            projCostStaementsResp = AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);

        }catch(Exception exp)
        {
            System.out.println("Got Error at MWProjectSettingsServiceImpl:projCostStaementsResp");
            exp.printStackTrace();
        }
        return projCostStaementsResp;
    }

    public ProjCostStaementsResp getProjExitCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.GET_PROJ_EXIT_COSTSTATEMENTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);
    }

    public ProjCostStaementsResp saveProjCostStatements(ProjCostStatementsSaveReq projCostStatementsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_COSTSTATEMENTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);
    }

    public ProjectPlantsResp getProjectPlants(ProjectPlantsGetReq projectPlantsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projectPlantsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJOJECT_PLANTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjectPlantsResp.class);
    }

    public ProjectPlantsResp saveProjectPlants(ProjectPlantsSaveReq projectPlantsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projectPlantsSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJOJECT_PLANTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjectPlantsResp.class);
    }

    public ProjReportsResp getProjReports(ProjReportsGetReq projReportsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projReportsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_REPORTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjReportsResp.class);
    }

    public ProjReportsResp saveProjReports(ProjReportsSaveReq projReportsSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projReportsSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_REPORTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjReportsResp.class);
    }

    public ProjManPowerResp getProjManPowers(ProjManpowerGetReq projManpowerGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projManpowerGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_MANPOWERS);
        return AppUtils.fromJson(strResponse.getBody(), ProjManPowerResp.class);
    }

    public ProjManPowerResp saveProjManPowers(ProjManpowerSaveReq projManpowerSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projManpowerSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_MANPOWERS);
        return AppUtils.fromJson(strResponse.getBody(), ProjManPowerResp.class);
    }

    public ProjProgressResp getProjProgress(ProjProgressGetReq projProgressGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProgressGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_PROGRESS);
        return AppUtils.fromJson(strResponse.getBody(), ProjProgressResp.class);
    }

    public ProjGeneralOnLoadResp projGeneralOnLoadResps(ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projGeneralOnLoadReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.PROJ_GENERALS_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), ProjGeneralOnLoadResp.class);
    }

    public ProjAttendenceOnLoadResp projAttendenceOnLoadResps(ProjAttendenceGetReq projAttendenceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projAttendenceGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.PROJ_ATTENDENCE_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), ProjAttendenceOnLoadResp.class);
    }

    public ProjWorkDairyOnLoadResp projWorkDairyOnLoadResps(ProjWorkDairyGetReq projWorkDairyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projWorkDairyGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.PROJ_WORKDAIRY_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), ProjWorkDairyOnLoadResp.class);
    }

    public ProjTimeSheetOnLoadResp projTimeSheetOnLoadResps(ProjTimeSheetGetReq projTimeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projTimeSheetGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.PROJ_TIMESHEET_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), ProjTimeSheetOnLoadResp.class);
    }

    public ProjCostCodeStatusResp getProjCostCodeStatus(ProjCostCodeStatusGetReq projCostCodeStatusGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostCodeStatusGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_COSTCODESTATUS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostCodeStatusResp.class);
    }

    public ProjSummaryResp getProjSummary(ProjSummaryGetReq projSummaryGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSummaryGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_SUMMARY);
        return AppUtils.fromJson(strResponse.getBody(), ProjSummaryResp.class);
    }

    public ProjStatusResp getProjStatus(ProjStatusGetReq projStatusGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projStatusGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), ProjStatusResp.class);
    }

    public List<LabelKeyTO> getWeekDays() {
        // TODO Auto-generated method stub
        return null;
    }

    public CalenderResp getCalenders(ProjCalenderGetReq projCalenderGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCalenderGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_CALENDERS);
        return AppUtils.fromJson(strResponse.getBody(), CalenderResp.class);

    }

    public ProjResourceCurveResp getResourceCurves(ProjResourceCurveGetReq projResourceCurveGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projResourceCurveGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_RESOURCE_CURVES);
        return AppUtils.fromJson(strResponse.getBody(), ProjResourceCurveResp.class);

    }

    public ProjProgressClaimResp getProjProgressClaim(ProjProgressClaimGetReq projProgressClaimGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProgressClaimGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_PROGRESSCLAIM);
        return AppUtils.fromJson(strResponse.getBody(), ProjProgressClaimResp.class);
    }

    public ProjProgressClaimResp saveProjProgressClaim(ProjProgressClaimSaveReq projProgressClaimSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProgressClaimSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_PROGRESSCLAIM);
        return AppUtils.fromJson(strResponse.getBody(), ProjProgressClaimResp.class);

    }

    public AppResp saveProjProgressClaimAppr(ProjProgressClaimApprSaveReq projProgressClaimApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProgressClaimApprSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_PROGRESSCLAIMAPPR);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);

    }

    public ProjProgressClaimePeroidResp getProjProgressClaimePeriodCycle(
            ProjProgressClaimGetReq projProgressClaimGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProgressClaimGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.GET_PROJ_PROGRESS_CLAIM_PERIOD);
        return AppUtils.fromJson(strResponse.getBody(), ProjProgressClaimePeroidResp.class);
    }

    public ProjProgressClaimePeroidResp saveProjProgressClaimePeriodCycle(
            ProjProgressClaimePeroidSaveReq projProgressClaimePeroidSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProgressClaimePeroidSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.SAVE_PROJ_PROGRESS_CLAIM_PERIOD);
        return AppUtils.fromJson(strResponse.getBody(), ProjProgressClaimePeroidResp.class);
    }

    public ProjProgressClaimePeriodOnLoadResp getProjProgressClaimePeriodCycleOnload(
            ProjProgressClaimGetReq projProgressClaimGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProgressClaimGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.PROJ_PROGRESS_CLAIM_PERIOD_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), ProjProgressClaimePeriodOnLoadResp.class);
    }

    public AppResp saveProjPayRollCycle(ProjPayRollCycleSaveReq projPayRollCycleSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projPayRollCycleSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_PAYROLLCYCLE);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjPayRollCycleOnLoadResp projPayRollCycleOnLoadResps(ProjPayRollCycleGetReq projPayRollCycleGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projPayRollCycleGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.PROJ_PAYROLLCYCLE_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), ProjPayRollCycleOnLoadResp.class);
    }

    public ProjReportsOnLoadResp projReportsOnLoadResps(ProjReportsGetReq projReportsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projReportsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.PROJ_REPORTS_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), ProjReportsOnLoadResp.class);
    }

    public ProjTimeSheetWeekResp getProjTimeSheetWeek(ProjTimeSheetGetReq projTimeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projTimeSheetGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_TIMESHEET_WEEK);
        return AppUtils.fromJson(strResponse.getBody(), ProjTimeSheetWeekResp.class);
    }

    public ProjTimeSheetWeekResp saveProjTimeSheetWeek(ProjTimeSheetWeekSaveReq projTimeSheetWeekSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projTimeSheetWeekSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_TIMESHEET_WEEK);
        return AppUtils.fromJson(strResponse.getBody(), ProjTimeSheetWeekResp.class);
    }

    public ProjCostStaementsResp saveProjCostCodes(ProjCostCodesSaveReq projCostCodesSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostCodesSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_COSTCODES);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);
    }

    public ProjCostStaementsResp getProjCostCodeStmts(ProjCostStatementsGetReq projCostStatementsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_COSTCODESTMTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);
    }

    public ProjProgressResp saveProjProgress(ProjProgressSaveReq projProgressSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projProgressSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_PROGRESS);
        return AppUtils.fromJson(strResponse.getBody(), ProjProgressResp.class);
    }

    public ProjManPowerStatusResp getProjManPowerstatus(ProjManpowerGetReq projManpowerGetReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projManpowerGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_MANPOWERSTATUS);
        return AppUtils.fromJson(strResponse.getBody(), ProjManPowerStatusResp.class);
    }

    public ProjectPlantsStatusResp getProjectPlantsStatus(ProjectPlantsGetReq projectPlantsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projectPlantsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJECT_PLANTSSTATUS);
        return AppUtils.fromJson(strResponse.getBody(), ProjectPlantsStatusResp.class);
    }

    public ProjCostStatementsSummaryResp getProjCostStatusSummary(ProjCostStatementsGetReq projCostStatementsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.GET_PROJECT_COSTSTATEMENTSSUMMARY);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStatementsSummaryResp.class);
    }

    public ProjectStatusResp getProjectStatus(ProjCostStatementsGetReq projCostStatementsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJECT_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), ProjectStatusResp.class);
    }

    public ProjNoteBookResp saveProjNoteBook(ProjNoteBookSaveReq projNoteBookSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projNoteBookSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_NOTE_BOOK);
        return AppUtils.fromJson(strResponse.getBody(), ProjNoteBookResp.class);
    }

    public ProjNoteBookResp getProjNoteBook(ProjNoteBookGetReq projNoteBookGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projNoteBookGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_NOTE_BOOK);
        return AppUtils.fromJson(strResponse.getBody(), ProjNoteBookResp.class);
    }

    public AppResp saveProjectDefaultGenerialSettinges(ProjectDefaultSaveReq projectDefaultSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projectDefaultSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_DEFAULT_SETTINGS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProvisionResp getProvinenceByCountryId(ProvinenceGetReq provinenceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(provinenceGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_PROVIENCE);
        return AppUtils.fromJson(strResponse.getBody(), ProvisionResp.class);
    }

    public NotificationResp getPreContractNotifications(NotificationFilterReq notificationFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(notificationFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.GET_PRECONTRACT_NOTIFICATIONS);
        return AppUtils.fromJson(strResponse.getBody(), NotificationResp.class);

    }

    public ProjGenCurrencyResp getProjGeneralsCurrencys(ProjGeneralsGetReq projGeneralsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projGeneralsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_GENERALS_CURRENCYS);
        return AppUtils.fromJson(strResponse.getBody(), ProjGenCurrencyResp.class);
    }

    public ProjProgressResp saveProjSows(ProjSowsSaveReq projSowsSaveReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSowsSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_SOWS);
        return AppUtils.fromJson(strResponse.getBody(), ProjProgressResp.class);
    }

    public ProjPerformenceThresholdResp getProjPerformenceThreshold(
            ProjPerformenceThresholdGetReq projPerformenceThresholdGetReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projPerformenceThresholdGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.GET_PROJ_PERFORMENCE_THRESHOLD);
        return AppUtils.fromJson(strResponse.getBody(), ProjPerformenceThresholdResp.class);
    }

    public ProjPerformenceThresholdResp saveProjPerformenceThreshold(
            ProjPerformenceThresholdSaveReq projPerformenceThresholdSaveReq) {

        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projPerformenceThresholdSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.SAVE_PROJ_PERFORMENCE_THRESHOLD);
        return AppUtils.fromJson(strResponse.getBody(), ProjPerformenceThresholdResp.class);
    }

    public LabelKeyTO getProjGeneralsCurrencys(Long projId) {
        ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setStatus(ApplicationConstants.STATUS_ACTIVE);
        projGeneralsGetReq.setProjId(projId);
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projGeneralsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_GENERALS_CURRENCYS);
        ProjGenCurrencyResp projGenCurrencyResp = AppUtils.fromJson(strResponse.getBody(), ProjGenCurrencyResp.class);
        return projGenCurrencyResp.getLabelKeyTO();
    }

    public ProjLeaveRequestResp getProjLeaveRequest(ProjLeaveRequestGetReq projLeaveRequestGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projLeaveRequestGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_LEAVE_REQUEST);
        return AppUtils.fromJson(strResponse.getBody(), ProjLeaveRequestResp.class);
    }

    public ProjLeaveRequestResp saveProjLeaveRequest(ProjLeaveRequestSaveReq projLeaveRequestSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projLeaveRequestSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_LEAVE_REQUEST);
        return AppUtils.fromJson(strResponse.getBody(), ProjLeaveRequestResp.class);
    }

    public AppResp saveProjLeaveApproval(ProjLeaveApprSaveReq projLeaveApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projLeaveApprSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_LEAVE_APPROVAL);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public AppResp saveProjPerfomanceDefaultSettings(ProjPerfamanceDefaultSaveReq projPerfamanceDefaultSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projPerfamanceDefaultSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.SAVE_PROJ_PERFOMANCE_DEFAULT_SETTINGS);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public ProjPerformenceThresholdResp deleteProjPerformenceThreshold(ProjPerfomanceDelReq projPerfomanceDelReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projPerfomanceDelReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.DELETE_PROJ_PERFORMENCE_THRESHOLD);
        return AppUtils.fromJson(strResponse.getBody(), ProjPerformenceThresholdResp.class);
    }

    public ProjStatusDatesResp getProjStatusDates(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_STATUS_DATES);
        return AppUtils.fromJson(strResponse.getBody(), ProjStatusDatesResp.class);
    }

    public ProjStatusDatesResp saveProjStatusDates(ProjStatusDatesSaveReq projStatusDatesSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projStatusDatesSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_STATUS_DATES);
        return AppUtils.fromJson(strResponse.getBody(), ProjStatusDatesResp.class);
    }

    public ProjCostStaementsResp getProjExitMaterialCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.GET_PROJ_EXIT_MATERIAL_COSTSTATEMENTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);
    }

    public ProjCostStaementsResp getProjExitPlantCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.GET_PROJ_EXIT_PLANT_COSTSTATEMENTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);
    }

    public ProjCostStaementsResp getProjExitManpowerCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.GET_PROJ_EXIT_MANPOWER_COSTSTATEMENTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);
    }

    public ProjCostStaementsResp getProjExitServiceCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.GET_PROJ_EXIT_SERVICES_COSTSTATEMENTS);
        return AppUtils.fromJson(strResponse.getBody(), ProjCostStaementsResp.class);
    }

    public ProjStatusActualResp getProjStatusActualQty(ProjStatusActualReq actualReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(actualReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_STATUS_ACTUAL_QTY);
        return AppUtils.fromJson(strResponse.getBody(), ProjStatusActualResp.class);
    }

    public ProjStatusDatesResp saveProjDurationStatus(ProjStatusDatesSaveReq projStatusDatesSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projStatusDatesSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_DURATION_STATUS);
        return AppUtils.fromJson(strResponse.getBody(), ProjStatusDatesResp.class);
    }

    public ProjMileStonesResp saveProjStatusMileStones(ProjMileStonesDateSaveReq projMileStonesDateSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projMileStonesDateSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_STATUS_MILESTONES);
        return AppUtils.fromJson(strResponse.getBody(), ProjMileStonesResp.class);
    }

    public ProjMileStonesResp getProjStatusMileStones(ProjSettingsFilterReq projSettingsFilterReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_STATUS_MILESTONES);
        return AppUtils.fromJson(strResponse.getBody(), ProjMileStonesResp.class);
    }

    public ProjMileStonesResp deleteProjStatusMileStones(ProjMileStonesDateSaveReq projMileStonesDateSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projMileStonesDateSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL
                        + ProjSettingsURLConstants.DELETE_PROJ_STATUS_MILESTONES);
        return AppUtils.fromJson(strResponse.getBody(), ProjMileStonesResp.class);
    }

    @Override
    public ProjEmpTransResp findEmpTransNormalTime(ProjEmpTransGetReq empTransGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(empTransGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.FIND_EMP_TRANS_NORMAL_TIME);
        return AppUtils.fromJson(strResponse.getBody(), ProjEmpTransResp.class);
    }
    
    public MaterialClassResp getMaterialGroups(ProjManpowerGetReq projManpowerGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projManpowerGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_MATERIAL_FOR_BUDGETS);
        return AppUtils.fromJson(strResponse.getBody(), MaterialClassResp.class);
    }
    
    public EmpClassesResp getEmpClasses(ProjManpowerGetReq projManpowerGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projManpowerGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_EMP_FOR_BUDGETS);
        return AppUtils.fromJson(strResponse.getBody(), EmpClassesResp.class);
    }
    
    public PlantClassResp getPlantClasses(ProjManpowerGetReq projManpowerGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projManpowerGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PLANT_FOR_BUDGETS);
        return AppUtils.fromJson(strResponse.getBody(), PlantClassResp.class);
    }

	@Override
	public String getCostCodeActualQty(ProjManpowerGetReq projManpowerGetReq) {
		ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projManpowerGetReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJ_COSTCODE_ACTUAL_QTY);
        return strResponse.getBody();
	}

	/* Dashboard Cost and Performance > Date Wise Actual Cost Details */
	@Override
	public String getActualCostDetails(CostReportReq costReportReq) {
		ResponseEntity<String> strResponse = null;
		System.out.println("getActualCostDetails Dashboard Service Impl");
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(costReportReq),
				ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_ACTUAL_COST_DETAIL);
		System.out.println("strResponse " + strResponse);
		return strResponse.getBody();
	}

	/* Dashboard Cost > Cost Work Sheet */
	@Override
	public String getPlanActualEarned(CostReportReq costReportReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(costReportReq),
				ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PLANNED_ACTUAL_EARNED_DASHBOARD);
		return strResponse.getBody();
	}
	
	@Override
	public ProjPlannedValueResp getProjPlannedValue(ProjCostStatementsGetReq projCostStatementsGetReq) {
       ResponseEntity<String> strResponse = null;
       strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
                                     ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PLANNED_VALUE);
       return AppUtils.fromJson(strResponse.getBody(), ProjPlannedValueResp.class);
	}

	@Override
	public ProjStatusDatesResp getMultiProjStatusDates(ProjSettingsFilterReq projSettingsFilterReq) {
       ResponseEntity<String> strResponse = null;
       strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSettingsFilterReq),
                                     ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_MULTI_PROJ_STATUS_DATES);
       return AppUtils.fromJson(strResponse.getBody(), ProjStatusDatesResp.class);
	}

	@Override
	public ProjCostStatementsSummaryResp getMultiProjCostStatusSummary(ProjCostStatementsGetReq projCostStatementsGetReq) {
	   ResponseEntity<String> strResponse = null;
	   strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projCostStatementsGetReq),
	                                 ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_MULTI_PROJECT_COSTSTATEMENTS_SUMMARY);
	   return AppUtils.fromJson(strResponse.getBody(), ProjCostStatementsSummaryResp.class);
	}
	
	@Override
	public ProjectTangibleResp getManpowerProductivityAnalysisReportData(ProjectTangibleReq projectTangibleReq) {
	   ResponseEntity<String> strResponse = null;
	   strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projectTangibleReq),
	                                 ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_MANPOWER_PRODUCTIVITY_ANALYSIS_REPORT_DATA);
	   return AppUtils.fromJson(strResponse.getBody(), ProjectTangibleResp.class);
	}
	
	@Override
	public ProjectGanntChartResp getProjectsGanttChartReportData(ProjGeneralsGetReq projGeneralsGetReq) {
	   ResponseEntity<String> strResponse = null;
	   strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projGeneralsGetReq),
	                                 ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_PROJECTS_GANTT_CHART_REPORT_DATA);
	   return AppUtils.fromJson(strResponse.getBody(), ProjectGanntChartResp.class);
	}

	public ProjBudgetResp projBudgetApproval( ProjGeneralsGetReq projGeneralsGetReq ) {
		System.out.println("projBudgetApproval function of MWProjectSettingsServiceImpl class");
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate( AppUtils.toJson( projGeneralsGetReq ),
	                                 ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.BUDGET_APPROVAL );
		System.out.println(strResponse);
		return AppUtils.fromJson( strResponse.getBody(), ProjBudgetResp.class );
	}
	
	public ProjBudgetResp projBudgetReturn( ProjGeneralsGetReq projGeneralsGetReq ) {
		System.out.println("projBudgetReturn function of MWProjectSettingsServiceImpl class");
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate( AppUtils.toJson( projGeneralsGetReq ),
	                                 ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.BUDGET_RETURN_WITH_COMMENTS );
		return AppUtils.fromJson( strResponse.getBody(), ProjBudgetResp.class );
	}
	
	public ProjSchofEstimatesResp saveProjSchofEstimates(SchofEstimatesSaveReq schofEstimatesSaveReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(schofEstimatesSaveReq),
				ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_SCH_OF_ESTIMATES);
		return AppUtils.fromJson(strResponse.getBody(), ProjSchofEstimatesResp.class);
	}
	
	public ProjSchofEstimatesResp getProjSchofEstimates(SchofEstimatesGetReq schofEstimatesGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(schofEstimatesGetReq),
				ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_SCH_OF_ESTIMATES);
		return AppUtils.fromJson(strResponse.getBody(), ProjSchofEstimatesResp.class);
	}
	
	public ProjSchofRatesResp saveProjSchofRates(SchofRatesSaveReq schofRatesSaveReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(schofRatesSaveReq),
				ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_SCH_OF_RATES);
		return AppUtils.fromJson(strResponse.getBody(), ProjSchofRatesResp.class);
	}
	
	public ProjSchofRatesResp getProjSchofRates(SchofRatesGetReq schofRatesGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(schofRatesGetReq),
				ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_SCH_OF_RATES);
		return AppUtils.fromJson(strResponse.getBody(), ProjSchofRatesResp.class);
	}
	
	public ProjResourceBudgetResp saveProjResBudget(ResourceBudgetSaveReq resBudgetSaveReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(resBudgetSaveReq),
				ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_RESOUCE_BUDGET);
		return AppUtils.fromJson(strResponse.getBody(), ProjResourceBudgetResp.class);
	}
	
	public ProjResourceBudgetResp getProjResBudget(ResourceBudgetGetReq resBudgetGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(resBudgetGetReq),
				ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_RESOURCE_BUDGET);
		return AppUtils.fromJson(strResponse.getBody(), ProjResourceBudgetResp.class);
	}
	
	public AppResp saveSoeAppr(ProjSoeApprSaveReq projSoeApprSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(projSoeApprSaveReq),
                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_PROJ_SOEAPPR);
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }
	
	public ChangeOrderDetailsResp getProjChangeOrderDetail(ChangeOrderDetailsGetReq changeOrderDetailsGetReq) {
		System.out.println("ChangeOrderDetailsGetReq1170 "+changeOrderDetailsGetReq.getProjId());
		  ResponseEntity<String> strResponse = null;
	        strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(changeOrderDetailsGetReq),
	                ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.GET_CHANGE_ORDER_ONLOAD);
		
		return AppUtils.fromJson(strResponse.getBody(), ChangeOrderDetailsResp.class);
			}
	
	public ChangeOrderDetailsResp saveProjChangeOrderDetail(ChangeOrderDetailsSaveReq changeOrderDetailsSaveReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getProjectSettingsPOSTRestTemplate(AppUtils.toJson(changeOrderDetailsSaveReq),
				ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL + ProjSettingsURLConstants.SAVE_CHANGE_ORDER_ONLOAD);
		return AppUtils.fromJson(strResponse.getBody(), ChangeOrderDetailsResp.class);
	}
}
