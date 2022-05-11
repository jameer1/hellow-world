package com.rjtech.projsettings.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.calendar.req.CalGetReq;
import com.rjtech.calendar.resp.CalendarResp;
import com.rjtech.calendar.service.GlobalCalendarService;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.req.CountryGetReq;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.CliamePeriodOnLoadResp;
import com.rjtech.common.utils.ClaimePeriod;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.progress.reports.req.ProgressReportGetReq;
import com.rjtech.projsettings.constans.ProjSettingsURLConstants;
import com.rjtech.projsettings.dto.ProjGenCurrencyResp;
import com.rjtech.projsettings.dto.ProjGeneralMstrTO;
import com.rjtech.projsettings.req.ChangeOrderDetailsGetReq;
import com.rjtech.projsettings.req.ChangeOrderDetailsSaveReq;
import com.rjtech.projsettings.req.ProjAttendenceApprSaveReq;
import com.rjtech.projsettings.req.ProjAttendenceGetReq;
import com.rjtech.projsettings.req.ProjAttendenceSaveReq;
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
import com.rjtech.projsettings.req.ProjTimeSheetOnLoadReq;
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
import com.rjtech.projsettings.resp.ChangeOrderDetailsResp;
import com.rjtech.projsettings.resp.ProjAttendenceResp;
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
import com.rjtech.projsettings.resp.ProjPayRollCycleResp;
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
import com.rjtech.projsettings.resp.ProjWorkDairyResp;
import com.rjtech.projsettings.resp.ProjectGanntChartResp;
import com.rjtech.projsettings.resp.ProjectMaterialsResp;
import com.rjtech.projsettings.resp.ProjectPlantsResp;
import com.rjtech.projsettings.resp.ProjectPlantsStatusResp;
import com.rjtech.projsettings.resp.ProjectStatusResp;
import com.rjtech.projsettings.resp.ProjectTangibleResp;
import com.rjtech.projsettings.service.ProjSettingsService;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.reports.cost.resp.ProgressSCurveTOResp;
import com.rjtech.projsettings.resp.ProjBudgetResp;
import com.rjtech.projsettings.repository.SchofEstimatesRepository;
import com.rjtech.projsettings.req.SchofEstimatesSaveReq;
import com.rjtech.projsettings.dto.SchofEstimatesApprTO;
import com.rjtech.projsettings.service.handler.SchofEstimatesHandler;
import com.rjtech.projsettings.req.SchofEstimatesGetReq;
import com.rjtech.projsettings.resp.ProjSchofEstimatesResp;
import com.rjtech.projsettings.model.ChangeOrderNormalTimeEntity;
import com.rjtech.projsettings.model.SchofEstimateNormalTimeEntity;
import com.rjtech.projsettings.req.SchofEstimatesGetReq;

import com.rjtech.projsettings.repository.SchofRatesRepository;
import com.rjtech.projsettings.req.SchofRatesSaveReq;
import com.rjtech.projsettings.dto.SchofRatesApprTO;
import com.rjtech.projsettings.service.handler.SchofRatesHandler;
import com.rjtech.projsettings.req.SchofRatesGetReq;
import com.rjtech.projsettings.resp.ProjSchofRatesResp;
import com.rjtech.projsettings.model.SchofRatesNormalTimeEntity;

import com.rjtech.projsettings.req.ResourceBudgetSaveReq;
import com.rjtech.projsettings.resp.ProjResourceBudgetResp;
import com.rjtech.projsettings.req.ResourceBudgetGetReq;
import com.rjtech.projsettings.req.ProjSoeApprSaveReq;
@RestController
@RequestMapping(ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL)
public class ProjSettingsController {

    @Autowired
    private ProjSettingsService projSettingsService;

    @Autowired
    private GlobalCalendarService globalCalendarService;

    // =============PROJGENERALS============================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_GENERALS, method = RequestMethod.POST)
    public ResponseEntity<ProjGeneralsResp> getProjGenerals(@RequestBody ProjGeneralsGetReq projGeneralsGetReq) {

        return new ResponseEntity<ProjGeneralsResp>(projSettingsService.getProjGenerals(projGeneralsGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.GET_MULTI_PROJ_GENERALS)
    public ResponseEntity<ProjGeneralsResp> getMultiProjGenerals(@RequestBody ProjGeneralsGetReq projGeneralsGetReq) {
        return new ResponseEntity<>(projSettingsService.getMultiProjGenerals(projGeneralsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.PROJ_GENERALS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjGeneralOnLoadResp> projGeneralOnLoad(
            @RequestBody ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        ProjGeneralOnLoadResp projGeneralOnLoadResp = new ProjGeneralOnLoadResp();

        ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setProjId(projGeneralOnLoadReq.getProjId());
        projGeneralsGetReq.setStatus(projGeneralOnLoadReq.getStatus());
        ProjGeneralsResp projGeneralsResp = projSettingsService.getProjGenerals(projGeneralsGetReq);
        if (projGeneralsResp != null && projGeneralsResp.getProjGeneralMstrTO() != null
                && projGeneralsResp.getProjGeneralMstrTO().getCalenderTO().getCode() == null) {
            ProjGeneralMstrTO projGeneralMstrTo = projGeneralsResp.getProjGeneralMstrTO();
            projGeneralMstrTo.setCalenderTO(projSettingsService.getGlobalCalendar());
        }
        projGeneralOnLoadResp.setProjGeneralMstrTO(projGeneralsResp.getProjGeneralMstrTO());

        CountryGetReq countryGetReq = new CountryGetReq();
        countryGetReq.setStatus(projGeneralOnLoadReq.getStatus());

        CalendarResp calendarResp = new CalendarResp();
        CalGetReq calGetReq = new CalGetReq();
        calGetReq.setStatus(projGeneralOnLoadReq.getStatus());
        calGetReq.setClientId(projGeneralOnLoadReq.getClientId());
        calendarResp = globalCalendarService.getGlobalCalendarById(calGetReq);
        projGeneralOnLoadResp.setCalenderTOs(calendarResp.getCalenderTOs());

        calGetReq.setStatus(projGeneralOnLoadReq.getStatus());
        calGetReq.setClientId(projGeneralOnLoadReq.getClientId());
        calGetReq.setProjId(projGeneralOnLoadReq.getProjId());
        calendarResp = globalCalendarService.getProjCalendarsByProject(calGetReq);
        projGeneralOnLoadResp.setCalenderTOs(calendarResp.getCalenderTOs());

        ProjResourceCurveGetReq projResourceCurveGetReq = new ProjResourceCurveGetReq();
        projResourceCurveGetReq.setStatus(projGeneralOnLoadReq.getStatus());
        projResourceCurveGetReq.setClientId(projGeneralOnLoadReq.getClientId());
        ProjResourceCurveResp projResourceCurveResp = projSettingsService.getResourceCurves(projResourceCurveGetReq);
        projGeneralOnLoadResp.setProjresourceCurveTOs(projResourceCurveResp.getProjResourceCurveTOs());

        return new ResponseEntity<ProjGeneralOnLoadResp>(projGeneralOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_GENERALS, method = RequestMethod.POST)
    public ResponseEntity<ProjGeneralsResp> saveProjGenerals(@RequestBody ProjGeneralSaveReq projGeneralSaveReq) {
        projSettingsService.saveProjGenerals(projGeneralSaveReq);

        ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setProjId(projGeneralSaveReq.getProjId());
        projGeneralsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjGeneralsResp projGeneralsResp = projSettingsService.getProjGenerals(projGeneralsGetReq);
        projGeneralsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjGeneralsResp>(projGeneralsResp, HttpStatus.OK);
    }

    // ==================================PROJGENERALSCURRENCYS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_GENERALS_CURRENCYS, method = RequestMethod.POST)
    public ResponseEntity<ProjGenCurrencyResp> getProjGeneralsCurrencys(
            @RequestBody ProjGeneralsGetReq projGeneralsGetReq) {
        return new ResponseEntity<ProjGenCurrencyResp>(projSettingsService.getProjGeneralsCurrencys(projGeneralsGetReq),
                HttpStatus.OK);
    }

    // ==============================PROJATTENDENCE=================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_ATTENDENCE, method = RequestMethod.POST)
    public ResponseEntity<ProjAttendenceResp> getProjAttendence(
            @RequestBody ProjAttendenceGetReq projAttendenceGetReq) {
        return new ResponseEntity<ProjAttendenceResp>(projSettingsService.getProjAttendence(projAttendenceGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_ATTENDENCE, method = RequestMethod.POST)
    public ResponseEntity<ProjAttendenceResp> saveProjAttendence(
            @RequestBody ProjAttendenceSaveReq projAttendenceSaveReq) {
        projSettingsService.saveProjAttendence(projAttendenceSaveReq);

        ProjAttendenceGetReq projAttendenceGetReq = new ProjAttendenceGetReq();
        projAttendenceGetReq.setProjId(projAttendenceSaveReq.getProjId());
        projAttendenceGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjAttendenceResp projAttendenceResp = projSettingsService.getProjAttendence(projAttendenceGetReq);
        projAttendenceResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjAttendenceResp>(projAttendenceResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_ATTENDENCEAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjAttendenceAppr(
            @RequestBody ProjAttendenceApprSaveReq projAttendenceApprSaveReq) {
        projSettingsService.saveProjAttendenceAppr(projAttendenceApprSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    // ====================================PROJWORKDAIRY====================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_WORKDAIRY, method = RequestMethod.POST)
    public ResponseEntity<ProjWorkDairyResp> getProjWorkDairy(@RequestBody ProjWorkDairyGetReq projWorkDairyGetReq) {
        return new ResponseEntity<ProjWorkDairyResp>(projSettingsService.getWorkDairy(projWorkDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_WORKDAIRY, method = RequestMethod.POST)
    public ResponseEntity<ProjWorkDairyResp> saveWorkDairy(@RequestBody ProjWorkDairySaveReq projWorkDairySaveReq) {
        projSettingsService.saveWorkDairy(projWorkDairySaveReq);

        ProjWorkDairyGetReq projWorkDairyGetReq = new ProjWorkDairyGetReq();
        projWorkDairyGetReq.setProjId(projWorkDairySaveReq.getProjId());
        projWorkDairyGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjWorkDairyResp projWorkDairyResp = projSettingsService.getWorkDairy(projWorkDairyGetReq);
        projWorkDairyResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjWorkDairyResp>(projWorkDairyResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_WORKDAIRYAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveWorkDairyAppr(@RequestBody ProjWorkDairyApprSaveReq projWorkDairyApprSaveReq) {
        projSettingsService.saveWorkDairyAppr(projWorkDairyApprSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    // ====================================PROJTIMESHEET====================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_TIMESHEET, method = RequestMethod.POST)
    public ResponseEntity<ProjTimeSheetResp> getProjTimeSheet(@RequestBody ProjTimeSheetGetReq projTimeSheetGetReq) {

        return new ResponseEntity<ProjTimeSheetResp>(projSettingsService.getProjTimeSheet(projTimeSheetGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.PROJ_TIMESHEET_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjTimeSheetOnLoadResp> projTimeSheetOnLoad(
            @RequestBody ProjTimeSheetOnLoadReq projTimeSheetOnLoadReq) {
        ProjTimeSheetOnLoadResp projTimeSheetOnLoadResp = new ProjTimeSheetOnLoadResp();

        ProjTimeSheetGetReq projTimeSheetGetReq = new ProjTimeSheetGetReq();
        projTimeSheetGetReq.setProjId(projTimeSheetOnLoadReq.getProjId());
        projTimeSheetGetReq.setStatus(projTimeSheetOnLoadReq.getStatus());
        ProjTimeSheetResp projTimeSheetResp = projSettingsService.getProjTimeSheet(projTimeSheetGetReq);
        List<String> weeakDays = projSettingsService.getWeekDays();
        projTimeSheetOnLoadResp.setWeekDays(weeakDays);
        projTimeSheetOnLoadResp.setProjTimeSheetResp(projTimeSheetResp);
        return new ResponseEntity<ProjTimeSheetOnLoadResp>(projTimeSheetOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_TIMESHEET, method = RequestMethod.POST)
    public ResponseEntity<ProjTimeSheetResp> saveProjTimeSheet(@RequestBody ProjTimeSheetSaveReq projTimeSheetSaveReq) {
        projSettingsService.saveProjTimeSheet(projTimeSheetSaveReq);

        ProjTimeSheetGetReq projTimeSheetGetReq = new ProjTimeSheetGetReq();
        projTimeSheetGetReq.setProjId(projTimeSheetSaveReq.getProjId());
        projTimeSheetGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjTimeSheetResp projTimeSheetResp = projSettingsService.getProjTimeSheet(projTimeSheetGetReq);
        projTimeSheetResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjTimeSheetResp>(projTimeSheetResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_TIMESHEETAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjTimeSheetAppr(
            @RequestBody ProjTimeSheetApprSaveReq projTimeSheetApprSaveReq) {
        projSettingsService.saveProjTimeSheetAppr(projTimeSheetApprSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_TIMESHEET_WEEK, method = RequestMethod.POST)
    public ResponseEntity<ProjTimeSheetWeekResp> getProjTimeSheetWeek(
            @RequestBody ProjTimeSheetGetReq projTimeSheetGetReq) {
        return new ResponseEntity<ProjTimeSheetWeekResp>(projSettingsService.getProjTimeSheetWeek(projTimeSheetGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_TIMESHEET_WEEK, method = RequestMethod.POST)
    public ResponseEntity<ProjTimeSheetWeekResp> saveProjTimeSheetWeek(
            @RequestBody ProjTimeSheetWeekSaveReq projTimeSheetWeekSaveReq) {
        projSettingsService.saveProjTimeSheetWeek(projTimeSheetWeekSaveReq);

        ProjTimeSheetGetReq projTimeSheetGetReq = new ProjTimeSheetGetReq();
        projTimeSheetGetReq.setProjId(projTimeSheetWeekSaveReq.getProjId());
        projTimeSheetGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjTimeSheetWeekResp projTimeSheetWeekResp = projSettingsService.getProjTimeSheetWeek(projTimeSheetGetReq);
        projTimeSheetWeekResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjTimeSheetWeekResp>(projTimeSheetWeekResp, HttpStatus.OK);
    }

    // ======================================PROJPROCURE================================================================

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PROCURE, method = RequestMethod.POST)
    public ResponseEntity<ProjProcureResp> getProjProcurement(@RequestBody ProjProcureGetReq projProcureGetReq) {
        return new ResponseEntity<ProjProcureResp>(projSettingsService.getProjProcurement(projProcureGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROCURE, method = RequestMethod.POST)
    public ResponseEntity<ProjProcureResp> saveProjProcurement(@RequestBody ProjProcureSaveReq projProcureSaveReq) {
        projSettingsService.saveProjProcurement(projProcureSaveReq);

        ProjProcureGetReq projProcureGetReq = new ProjProcureGetReq();
        projProcureGetReq.setProjId(projProcureSaveReq.getProjId());
        projProcureGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjProcureResp projProcureResp = projSettingsService.getProjProcurement(projProcureGetReq);
        projProcureResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjProcureResp>(projProcureResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROCUREAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjProcurementAppr(@RequestBody ProjProcureApprSaveReq projProcureApprSaveReq) {
        projSettingsService.saveProjProcurementAppr(projProcureApprSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    // ==================================PROJEMPTRANS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EMPTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpTransResp> getProjEmpTrans(@RequestBody ProjEmpTransGetReq projEmpTransGetReq) {
        return new ResponseEntity<ProjEmpTransResp>(projSettingsService.getEmpTrans(projEmpTransGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_EMPTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpTransResp> saveProjEmpTrans(@RequestBody ProjEmpTransSaveReq projEmpTransSaveReq) {
        ProjEmpTransResp projEmpTransResp = projSettingsService.saveEmpTrans(projEmpTransSaveReq);
        projEmpTransResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjEmpTransResp>(projEmpTransResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_EMPTRANSAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveEmpTransAppr(@RequestBody ProjEmpTransApprSaveReq projEmpTransApprSaveReq) {
        projSettingsService.saveEmpTransAppr(projEmpTransApprSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    // ==================================PROJPLANTS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PLANTTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjPlantTransResp> getProjPlantTrans(
            @RequestBody ProjPlantTransGetReq projPlantTransGetReq) {
        return new ResponseEntity<ProjPlantTransResp>(projSettingsService.getProjPlantTrans(projPlantTransGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PLANTTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjPlantTransResp> saveProjPlantTrans(
            @RequestBody ProjPlantTransSaveReq projPlantTransSaveReq) {
        projSettingsService.saveProjPlantTrans(projPlantTransSaveReq);

        ProjPlantTransGetReq projPlantTransGetReq = new ProjPlantTransGetReq();
        projPlantTransGetReq.setProjId(projPlantTransSaveReq.getProjId());
        projPlantTransGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjPlantTransResp projPlantTransResp = projSettingsService.getProjPlantTrans(projPlantTransGetReq);

        projPlantTransResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjPlantTransResp>(projPlantTransResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PLANTTRANSAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjPlantTransAppr(
            @RequestBody ProjPlantTransApprSaveReq projPlantTransApprSaveReq) {
        projSettingsService.saveProjPlantTransAppr(projPlantTransApprSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    // ==================================PROJMATERIALS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_MATERIALTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjMaterialTransResp> getProjMaterialTrans(
            @RequestBody ProjMaterialTransGetReq projMaterialTransGetReq) {
        return new ResponseEntity<ProjMaterialTransResp>(
                projSettingsService.getProjMaterialTrans(projMaterialTransGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_MATERIALTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjMaterialTransResp> saveProjMaterialTrans(
            @RequestBody ProjMaterialTransSaveReq projMaterialTransSaveReq) {
        projSettingsService.saveProjMaterialTrans(projMaterialTransSaveReq);

        ProjMaterialTransGetReq projMaterialTransGetReq = new ProjMaterialTransGetReq();
        projMaterialTransGetReq.setProjId(projMaterialTransSaveReq.getProjId());
        projMaterialTransGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjMaterialTransResp projMaterialTransResp = projSettingsService.getProjMaterialTrans(projMaterialTransGetReq);
        projMaterialTransResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjMaterialTransResp>(projMaterialTransResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_MATERIALTRANSAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjMaterialTransAppr(
            @RequestBody ProjMaterialTransApprSaveReq projMaterialTransApprSaveReq) {
        projSettingsService.saveProjMaterialTransAppr(projMaterialTransApprSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    // ==================================PROJESTIMATE=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_ESTIMATE, method = RequestMethod.POST)
    public ResponseEntity<ProjEstimateResp> getProjEstimate(@RequestBody ProjEstimateGetReq projEstimateGetReq) {
        return new ResponseEntity<ProjEstimateResp>(projSettingsService.getProjEstimate(projEstimateGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_ESTIMATE, method = RequestMethod.POST)
    public ResponseEntity<ProjEstimateResp> saveProjEstimate(@RequestBody ProjEstimateSaveReq projEstimateSaveReq) {
        projSettingsService.saveProjEstimate(projEstimateSaveReq);

        ProjEstimateGetReq projEstimateGetReq = new ProjEstimateGetReq();
        projEstimateGetReq.setProjId(projEstimateSaveReq.getProjId());
        projEstimateGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjEstimateResp projEstimateResp = projSettingsService.getProjEstimate(projEstimateGetReq);

        projEstimateResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjEstimateResp>(projEstimateResp, HttpStatus.OK);
    }

    // ==================================PROJPROGRESSCLAIM=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PROGRESSCLAIM, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressClaimResp> getProjProgressClaim(
            @RequestBody ProjProgressClaimGetReq projProgressClaimGetReq) {
        return new ResponseEntity<ProjProgressClaimResp>(
                projSettingsService.getProjProgressClaim(projProgressClaimGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROGRESSCLAIM, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressClaimResp> saveProjProgressClaim(
            @RequestBody ProjProgressClaimSaveReq projProgressClaimSaveReq) {
        projSettingsService.saveProjProgressClaim(projProgressClaimSaveReq);

        ProjProgressClaimGetReq projProgressClaimGetReq = new ProjProgressClaimGetReq();
        projProgressClaimGetReq.setProjId(projProgressClaimSaveReq.getProjId());
        projProgressClaimGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjProgressClaimResp projProgressClaimResp = projSettingsService.getProjProgressClaim(projProgressClaimGetReq);
        projProgressClaimResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjProgressClaimResp>(projProgressClaimResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROGRESSCLAIMAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjProgressClaimAppr(
            @RequestBody ProjProgressClaimApprSaveReq projProgressClaimApprSaveReq) {
        projSettingsService.saveProjProgressClaimAppr(projProgressClaimApprSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PROGRESS_CLAIM_PERIOD, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressClaimePeroidResp> getProjProgressClaimePeriodCycle(
            @RequestBody ProjProgressClaimGetReq projProgressClaimGetReq) {
        return new ResponseEntity<ProjProgressClaimePeroidResp>(
                projSettingsService.getProjProgressClaimePeriodCycle(projProgressClaimGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROGRESS_CLAIM_PERIOD, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressClaimePeroidResp> saveProjProgressClaimePeriodCycle(
            @RequestBody ProjProgressClaimePeroidSaveReq projProgressClaimePeroidSaveReq) {
        projSettingsService.saveProjProgressClaimePeriodCycle(projProgressClaimePeroidSaveReq);

        ProjProgressClaimGetReq projProgressClaimGetReq = new ProjProgressClaimGetReq();
        projProgressClaimGetReq.setProjId(projProgressClaimePeroidSaveReq.getProjId());
        projProgressClaimGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjProgressClaimePeroidResp projProgressClaimePeroidResp = projSettingsService
                .getProjProgressClaimePeriodCycle(projProgressClaimGetReq);
        projProgressClaimePeroidResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjProgressClaimePeroidResp>(projProgressClaimePeroidResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.PROJ_PROGRESS_CLAIM_PERIOD_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressClaimePeriodOnLoadResp> ProjProgressClaimePeriodCycleOnload(
            @RequestBody ProjProgressClaimGetReq projProgressClaimGetReq) {
        ProjProgressClaimePeriodOnLoadResp projProgressClaimePeriodOnLoadResp = new ProjProgressClaimePeriodOnLoadResp();
        ProjProgressClaimePeroidResp projProgressClaimePeroidResp = projSettingsService
                .getProjProgressClaimePeriodCycle(projProgressClaimGetReq);
        projProgressClaimePeriodOnLoadResp
                .setProjProgressClaimePeriodTOs(projProgressClaimePeroidResp.getProjProgressClaimePeriodTOs());

        return new ResponseEntity<ProjProgressClaimePeriodOnLoadResp>(projProgressClaimePeriodOnLoadResp,
                HttpStatus.OK);
    }

    // ==================================PROJREPORTS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_REPORTS, method = RequestMethod.POST)
    public ResponseEntity<ProjReportsResp> getProjReports(@RequestBody ProjReportsGetReq projReportsGetReq) {

        return new ResponseEntity<ProjReportsResp>(projSettingsService.getProjReports(projReportsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_REPORTS, method = RequestMethod.POST)
    public ResponseEntity<ProjReportsResp> saveProjReports(@RequestBody ProjReportsSaveReq projReportsSaveReq) {
        projSettingsService.saveProjReports(projReportsSaveReq);

        ProjReportsGetReq projReportsGetReq = new ProjReportsGetReq();
        projReportsGetReq.setProjId(projReportsSaveReq.getProjId());
        projReportsGetReq.setStatus(projReportsSaveReq.getStatus());
        ProjReportsResp projReportsResp = projSettingsService.getProjReports(projReportsGetReq);
        projReportsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjReportsResp>(projReportsResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.PROJ_REPORTS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjReportsOnLoadResp> projReportsOnLoad(@RequestBody ProjReportsGetReq projReportsGetReq) {
        ProjReportsOnLoadResp projReportsOnLoadResp = new ProjReportsOnLoadResp();
        ProjReportsResp projReportsResp = projSettingsService.getProjReports(projReportsGetReq);

        projReportsOnLoadResp.setProjectReportsTOs(projReportsResp.getProjectReportsTOs());

        return new ResponseEntity<ProjReportsOnLoadResp>(projReportsOnLoadResp, HttpStatus.OK);
    }

    // ==================================PROJMANPOWER=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_MANPOWERS, method = RequestMethod.POST)
    public ResponseEntity<ProjManPowerResp> getProjManPowers(@RequestBody ProjManpowerGetReq projManpowerGetReq) {

        return new ResponseEntity<ProjManPowerResp>(projSettingsService.getProjManPowers(projManpowerGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_MANPOWERS, method = RequestMethod.POST)
    public ResponseEntity<ProjManPowerResp> saveProjManPowers(@RequestBody ProjManpowerSaveReq projManpowerSaveReq) {
        projSettingsService.saveProjManPowers(projManpowerSaveReq);

        ProjManpowerGetReq projManpowerGetReq = new ProjManpowerGetReq();
        projManpowerGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projManpowerGetReq.setProjId(projManpowerSaveReq.getProjId());
        ProjManPowerResp projManPowerResp = projSettingsService.getProjManPowers(projManpowerGetReq);
        projManPowerResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjManPowerResp>(projManPowerResp, HttpStatus.OK);
    }

    // ==================================PROJECTPLANTS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJOJECT_PLANTS, method = RequestMethod.POST)
    public ResponseEntity<ProjectPlantsResp> getProjectPlants(@RequestBody ProjectPlantsGetReq projectPlantsGetReq) {

        return new ResponseEntity<ProjectPlantsResp>(projSettingsService.getProjectPlants(projectPlantsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJOJECT_PLANTS, method = RequestMethod.POST)
    public ResponseEntity<ProjectPlantsResp> saveProjectPlants(@RequestBody ProjectPlantsSaveReq projectPlantsSaveReq) {
        projSettingsService.saveProjectPlants(projectPlantsSaveReq);

        ProjectPlantsGetReq projectPlantsGetReq = new ProjectPlantsGetReq();
        projectPlantsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projectPlantsGetReq.setProjId(projectPlantsSaveReq.getProjId());
        ProjectPlantsResp projectPlantsResp = projSettingsService.getProjectPlants(projectPlantsGetReq);
        projectPlantsResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjectPlantsResp>(projectPlantsResp, HttpStatus.OK);
    }

    // ==================================PROJECTMATERIALS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJOJECT_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<ProjectMaterialsResp> getProjectMaterials(
            @RequestBody ProjectMaterialGetReq projectMaterialGetReq) {

        return new ResponseEntity<ProjectMaterialsResp>(projSettingsService.getProjectMaterials(projectMaterialGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJOJECT_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<ProjectMaterialsResp> saveProjectMaterials(
            @RequestBody ProjectMaterialSaveReq projectMaterialSaveReq) {
        projSettingsService.saveProjectMaterials(projectMaterialSaveReq);

        ProjectMaterialGetReq projectMaterialGetReq = new ProjectMaterialGetReq();
        projectMaterialGetReq.setProjId(projectMaterialSaveReq.getProjId());
        projectMaterialGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjectMaterialsResp projectMaterialsResp = projSettingsService.getProjectMaterials(projectMaterialGetReq);

        projectMaterialsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjectMaterialsResp>(projectMaterialsResp, HttpStatus.OK);
    }

    // ==================================PROJCOSTCODESTATEMENTS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        System.out.println("ProjectSettingsController:getProjCostStatements 44");
        return new ResponseEntity<ProjCostStaementsResp>(
                projSettingsService.getProjCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.GET_MULTI_PROJ_COSTSTATEMENTS)
    public ResponseEntity<ProjCostStaementsResp> getMultiProjCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
    	System.out.println("ProjSettingsController > getMultiProjCostStatements ");
        return new ResponseEntity<>(projSettingsService.getMultiProjCostStatements(projCostStatementsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EXIT_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjExitCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        return new ResponseEntity<ProjCostStaementsResp>(
                projSettingsService.getProjExitCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EXIT_MANPOWER_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjExitManpowerCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        return new ResponseEntity<ProjCostStaementsResp>(
                projSettingsService.getProjExitManpowerCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EXIT_MATERIAL_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjExitMaterialCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        return new ResponseEntity<ProjCostStaementsResp>(
                projSettingsService.getProjExitMaterialCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EXIT_PLANT_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjExitPlantCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        return new ResponseEntity<ProjCostStaementsResp>(
                projSettingsService.getProjExitPlantCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EXIT_SERVICES_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjExitServiceCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        return new ResponseEntity<ProjCostStaementsResp>(
                projSettingsService.getProjExitServiceCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_COSTCODESTMTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjCostCodeStmts(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {

        return new ResponseEntity<ProjCostStaementsResp>(
                projSettingsService.getProjCostCodeStmts(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> saveProjCostStatements(
            @RequestBody ProjCostStatementsSaveReq projCostStatementsSaveReq) {
        projSettingsService.saveProjCostStatements(projCostStatementsSaveReq);

        ProjCostStatementsGetReq projCostStatementsGetReq = new ProjCostStatementsGetReq();
        projCostStatementsGetReq.setProjId(projCostStatementsSaveReq.getProjId());
        projCostStatementsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjCostStaementsResp projCostStaementsResp = projSettingsService
                .getProjCostStatements(projCostStatementsGetReq);
        projCostStaementsResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<ProjCostStaementsResp>(projCostStaementsResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_COSTCODES, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> saveProjCostCodes(
            @RequestBody ProjCostCodesSaveReq projCostCodesSaveReq) {
        projSettingsService.saveProjCostCodes(projCostCodesSaveReq);

        ProjCostStatementsGetReq projCostStatementsGetReq = new ProjCostStatementsGetReq();
        projCostStatementsGetReq.setProjId(projCostCodesSaveReq.getProjId());
        projCostStatementsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjCostStaementsResp projCostStaementsResp = projSettingsService
                .getProjCostStatements(projCostStatementsGetReq);
        projCostStaementsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjCostStaementsResp>(projCostStaementsResp, HttpStatus.OK);
    }

    // ==================================PROJPROGRESS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PROGRESS, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressResp> getProjProgress(@RequestBody ProjProgressGetReq projProgressGetReq) {

        return new ResponseEntity<ProjProgressResp>(projSettingsService.getProjProgress(projProgressGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROGRESS, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressResp> saveProjProgress(@RequestBody ProjProgressSaveReq projProgressSaveReq) {
        projSettingsService.saveProjProgress(projProgressSaveReq);

        ProjProgressGetReq projProgressGetReq = new ProjProgressGetReq();
        projProgressGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projProgressGetReq.setProjId(projProgressSaveReq.getProjId());
        ProjProgressResp projProgressResp = projSettingsService.getProjProgress(projProgressGetReq);
        projProgressResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjProgressResp>(projProgressResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_SOWS, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressResp> saveProjSows(@RequestBody ProjSowsSaveReq ProjSowsSaveReq) {

        projSettingsService.saveProjSows(ProjSowsSaveReq);

        ProjProgressGetReq ProjProgressGetReq = new ProjProgressGetReq();
        ProjProgressGetReq.setProjId(ProjSowsSaveReq.getProjId());
        ProjProgressGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjProgressResp projProgressResp = projSettingsService.getProjProgress(ProjProgressGetReq);
        projProgressResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjProgressResp>(projProgressResp, HttpStatus.OK);
    }

    // ==================================PROJCOSTCODESTATUS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_COSTCODESTATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostCodeStatusResp> getProjCostCodeStatus(
            @RequestBody ProjCostCodeStatusGetReq projCostCodeStatusGetReq) {

        ProjCostStatementsGetReq projCostStatementsGetReq = new ProjCostStatementsGetReq();
        projCostStatementsGetReq.setProjId(projCostCodeStatusGetReq.getClientId());
        projCostStatementsGetReq.setStatus(projCostCodeStatusGetReq.getStatus());
        projSettingsService.getProjCostStatements(projCostStatementsGetReq);
        return new ResponseEntity<ProjCostCodeStatusResp>(
                projSettingsService.getProjCostCodeStatus(projCostCodeStatusGetReq), HttpStatus.OK);
    }

    // ==================================PROJSUMMARY=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_SUMMARY, method = RequestMethod.POST)
    public ResponseEntity<ProjSummaryResp> getProjSummary(@RequestBody ProjSummaryGetReq projSummaryGetReq) {

        return new ResponseEntity<ProjSummaryResp>(projSettingsService.getProjSummary(projSummaryGetReq),
                HttpStatus.OK);
    }

    // ==================================PROJSTATUS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_STATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjStatusResp> getProjStatus(@RequestBody ProjStatusGetReq projStatusGetReq) {

        return new ResponseEntity<ProjStatusResp>(projSettingsService.getProjStatus(projStatusGetReq), HttpStatus.OK);
    }

    // ==================================PROJNOTEBOOK=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_NOTE_BOOK, method = RequestMethod.POST)
    public ResponseEntity<ProjNoteBookResp> getProjNoteBook(@RequestBody ProjNoteBookGetReq projNoteBookGetReq) {

        return new ResponseEntity<ProjNoteBookResp>(projSettingsService.getProjNoteBook(projNoteBookGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_NOTE_BOOK, method = RequestMethod.POST)
    public ResponseEntity<ProjNoteBookResp> saveProjNoteBook(@RequestBody ProjNoteBookSaveReq projNoteBookSaveReq) {
        projSettingsService.saveProjNoteBook(projNoteBookSaveReq);

        ProjNoteBookGetReq projNoteBookGetReq = new ProjNoteBookGetReq();
        projNoteBookGetReq.setProjId(projNoteBookSaveReq.getProjId());
        projNoteBookGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjNoteBookResp projNoteBookResp = projSettingsService.getProjNoteBook(projNoteBookGetReq);
        projNoteBookResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjNoteBookResp>(projNoteBookResp, HttpStatus.OK);
    }

    // ==================================SAVEPROJPAYROLECYCLE=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PAYROLLCYCLE, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjPayRollCycle(@RequestBody ProjPayRollCycleSaveReq projPayRollCycleSaveReq) {
        projSettingsService.saveProjPayRollCycle(projPayRollCycleSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.PROJ_PAYROLLCYCLE_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjPayRollCycleOnLoadResp> ProjPayRollCycleOnLoad(
            @RequestBody ProjPayRollCycleGetReq projPayRollCycleGetReq) {
        ProjPayRollCycleOnLoadResp projPayRollCycleOnLoadResp = new ProjPayRollCycleOnLoadResp();

        ProjPayRollCycleResp projPayRollCycleResp = projSettingsService.getprojPayRollCycle(projPayRollCycleGetReq);
        projPayRollCycleOnLoadResp.setProjPayRollCycleTOs(projPayRollCycleResp.getProjPayRollCycleTOs());

        return new ResponseEntity<ProjPayRollCycleOnLoadResp>(projPayRollCycleOnLoadResp, HttpStatus.OK);
    }

    // ==================================PROJMANPOWERSSTATUS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_MANPOWERSTATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjManPowerStatusResp> getProjManPowerstatus(
            @RequestBody ProjManpowerGetReq projManpowerGetReq) {

        return new ResponseEntity<ProjManPowerStatusResp>(projSettingsService.getProjManPowerstatus(projManpowerGetReq),
                HttpStatus.OK);
    }

    // ==================================PROJPLANTSSTATUS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJECT_PLANTSSTATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjectPlantsStatusResp> getProjectPlantsStatus(
            @RequestBody ProjectPlantsGetReq projectPlantsGetReq) {

        return new ResponseEntity<ProjectPlantsStatusResp>(
                projSettingsService.getProjectPlantsStatus(projectPlantsGetReq), HttpStatus.OK);
    }

    // ==================================PROJCOSTSTATEMENTSSUMMARY=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJECT_COSTSTATEMENTSSUMMARY, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStatementsSummaryResp> getProjCostStatusSummary(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {

        return new ResponseEntity<ProjCostStatementsSummaryResp>(
                projSettingsService.getProjCostStatusSummary(projCostStatementsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.GET_MULTI_PROJECT_COSTSTATEMENTS_SUMMARY)
    public ResponseEntity<ProjCostStatementsSummaryResp> getMultiProjCostStatusSummary(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {

        return new ResponseEntity<>(projSettingsService.getMultiProjCostStatusSummary(projCostStatementsGetReq),
                HttpStatus.OK);
    }

    // ==================================PROJECTSTATUS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJECT_STATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjectStatusResp> getProjectStatus(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        ProjectStatusResp ProjectStatusResp = new ProjectStatusResp();
        ProjCostStatementsSummaryResp projCostStatementsSummaryResp = projSettingsService
                .getProjCostStatusSummary(projCostStatementsGetReq);
        ProjectStatusResp
                .setProjCostStatementsSummaryTOs(projCostStatementsSummaryResp.getProjCostStatementsSummaryTOs());

        ProjManpowerGetReq projManpowerGetReq = new ProjManpowerGetReq();
        projManpowerGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projManpowerGetReq.setProjId(projCostStatementsGetReq.getProjId());
        ProjManPowerStatusResp projManPowerStatusResp = projSettingsService.getProjManPowerstatus(projManpowerGetReq);
        ProjectStatusResp.setProjManPowerStatusTOs(projManPowerStatusResp.getProjManPowerStatusTOs());
        return new ResponseEntity<ProjectStatusResp>(ProjectStatusResp, HttpStatus.OK);
    }

    // ==================================PROJDEFAULTSETTINGS=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_DEFAULT_SETTINGS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjectDefaultSettinges(
            @RequestBody ProjectDefaultSaveReq projectDefaultSaveReq) {
        projSettingsService.saveProjectDefaultSettinges(projectDefaultSaveReq);

        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PERFOMANCE_DEFAULT_SETTINGS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjPerfomanceDefaultSettings(
            @RequestBody ProjPerfamanceDefaultSaveReq projPerfamanceDefaultSaveReq) {
        projSettingsService.saveProjPerfomanceDefaultSettings(projPerfamanceDefaultSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    // ==================================PROJPERFORMENCETHRESHOLD=========================================================
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PERFORMENCE_THRESHOLD, method = RequestMethod.POST)
    public ResponseEntity<ProjPerformenceThresholdResp> getProjPerformenceThreshold(
            @RequestBody ProjPerformenceThresholdGetReq projPerformenceThresholdGetReq) {

        return new ResponseEntity<ProjPerformenceThresholdResp>(
                projSettingsService.getProjPerformenceThreshold(projPerformenceThresholdGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PERFORMENCE_THRESHOLD, method = RequestMethod.POST)
    public ResponseEntity<ProjPerformenceThresholdResp> saveProjPerformenceThreshold(
            @RequestBody ProjPerformenceThresholdSaveReq projPerformenceThresholdSaveReq) {
        projSettingsService.saveProjPerformenceThreshold(projPerformenceThresholdSaveReq);

        ProjPerformenceThresholdGetReq projPerformenceThresholdGetReq = new ProjPerformenceThresholdGetReq();
        projPerformenceThresholdGetReq.setStatus(projPerformenceThresholdSaveReq.getStatus());
        projPerformenceThresholdGetReq.setProjId(projPerformenceThresholdSaveReq.getProjId());
        ProjPerformenceThresholdResp projPerformenceThresholdResp = projSettingsService
                .getProjPerformenceThreshold(projPerformenceThresholdGetReq);
        projPerformenceThresholdResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjPerformenceThresholdResp>(projPerformenceThresholdResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.ONLOAD_DATA_FOR_CLAIME_PERIOD, method = RequestMethod.POST)
    public ResponseEntity<CliamePeriodOnLoadResp> projClaimePeriodOnload(
            @RequestBody ProjPayRollCycleGetReq projPayRollCycleGetReq) {
        CliamePeriodOnLoadResp cliamePeriodOnLoadResp = new CliamePeriodOnLoadResp();
        List<String> claimPeriod = new ArrayList<String>();
        for (ClaimePeriod claimePeriod : ClaimePeriod.values()) {
            claimPeriod.add(claimePeriod.getClaimeperiod());
        }
        cliamePeriodOnLoadResp.setClaimPeriod(claimPeriod);
        return new ResponseEntity<CliamePeriodOnLoadResp>(cliamePeriodOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.DELETE_PROJ_PERFORMENCE_THRESHOLD, method = RequestMethod.POST)
    public ResponseEntity<ProjPerformenceThresholdResp> deleteProjPerformenceThreshold(
            @RequestBody ProjPerfomanceDelReq projPerfomanceDelReq) {
        projSettingsService.deleteProjPerformenceThreshold(projPerfomanceDelReq);

        ProjPerformenceThresholdGetReq projPerformenceThresholdGetReq = new ProjPerformenceThresholdGetReq();
        projPerformenceThresholdGetReq.setProjId(projPerfomanceDelReq.getProjId());

        ProjPerformenceThresholdResp projPerformenceThresholdResp = projSettingsService
                .getProjPerformenceThreshold(projPerformenceThresholdGetReq);
        projPerformenceThresholdResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<ProjPerformenceThresholdResp>(projPerformenceThresholdResp, HttpStatus.OK);

    }

    // ==================================PROJLEAVEAPPROVAL=========================================================

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_LEAVE_REQUEST, method = RequestMethod.POST)
    public ResponseEntity<ProjLeaveRequestResp> getProjLeaveRequest(
            @RequestBody ProjLeaveRequestGetReq projLeaveRequestGetReq) {
        return new ResponseEntity<ProjLeaveRequestResp>(projSettingsService.getProjLeaveRequest(projLeaveRequestGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_LEAVE_REQUEST, method = RequestMethod.POST)
    public ResponseEntity<ProjLeaveRequestResp> saveProjLeaveRequest(
            @RequestBody ProjLeaveRequestSaveReq projLeaveRequestSaveReq) {
        ProjLeaveRequestResp projLeaveApprovalResp = projSettingsService.saveProjLeaveRequest(projLeaveRequestSaveReq);
        projLeaveApprovalResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjLeaveRequestResp>(projLeaveApprovalResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_LEAVE_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjLeaveApproval(@RequestBody ProjLeaveApprSaveReq projLeaveApprSaveReq) {
        projSettingsService.saveProjLeaveApproval(projLeaveApprSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }

    // ==================================PROJECT STATUS DATES=========================================================

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_STATUS_DATES, method = RequestMethod.POST)
    public ResponseEntity<ProjStatusDatesResp> getProjStatusDates(
            @RequestBody ProjSettingsFilterReq projSettingsFilterReq) {
        return new ResponseEntity<ProjStatusDatesResp>(projSettingsService.getProjStatusDates(projSettingsFilterReq),
                HttpStatus.OK);

    }

    @PostMapping(value = ProjSettingsURLConstants.GET_MULTI_PROJ_STATUS_DATES)
    public ResponseEntity<ProjStatusDatesResp> getMultiProjStatusDates(
            @RequestBody ProjSettingsFilterReq projSettingsFilterReq) {
        return new ResponseEntity<>(projSettingsService.getMultiProjStatusDates(projSettingsFilterReq), HttpStatus.OK);

    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_STATUS_DATES, method = RequestMethod.POST)
    public ResponseEntity<ProjStatusDatesResp> saveProjStatusDates(
            @RequestBody ProjStatusDatesSaveReq projStatusDatesSaveReq) {
        projSettingsService.saveProjStatusDates(projStatusDatesSaveReq);
        ProjStatusDatesResp projStatusDatesResp = new ProjStatusDatesResp();
        projStatusDatesResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjStatusDatesResp>(projStatusDatesResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_STATUS_ACTUAL_QTY, method = RequestMethod.POST)
    public ProjStatusActualResp getProjStatusActualQty(@RequestBody ProjStatusActualReq projStatusActualReq) {
        return projSettingsService.getProjStatusActualQty(projStatusActualReq);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_DURATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjStatusDatesResp> saveProjDurationStatus(
            @RequestBody ProjStatusDatesSaveReq projStatusDatesSaveReq) {
        projSettingsService.saveProjDurationStatus(projStatusDatesSaveReq);
        ProjStatusDatesResp projStatusDatesResp = new ProjStatusDatesResp();
        projStatusDatesResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjStatusDatesResp>(projStatusDatesResp, HttpStatus.OK);

    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_STATUS_MILESTONES, method = RequestMethod.POST)
    public ResponseEntity<ProjMileStonesResp> saveProjStatusMileStones(
            @RequestBody ProjMileStonesDateSaveReq projMileStonesDateSaveReq) {
        projSettingsService.saveProjStatusMileStones(projMileStonesDateSaveReq);
        ProjMileStonesResp projMileStonesResp = new ProjMileStonesResp();

        projMileStonesResp.cloneAppResp(CommonUtil.getSaveAppResp());
        ProjSettingsFilterReq projSettingsFilterReq = new ProjSettingsFilterReq();
        projSettingsFilterReq.setProjId(projMileStonesDateSaveReq.getProjId());
        projSettingsFilterReq.setStatus(projMileStonesDateSaveReq.getStatus());

        ProjMileStonesResp resp = projSettingsService.getProjStatusMileStones(projSettingsFilterReq);

        return new ResponseEntity<ProjMileStonesResp>(resp, HttpStatus.OK);

    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_STATUS_MILESTONES, method = RequestMethod.POST)
    public ResponseEntity<ProjMileStonesResp> getProjStatusMileStones(
            @RequestBody ProjSettingsFilterReq projSettingsFilterReq) {
        return new ResponseEntity<ProjMileStonesResp>(
                projSettingsService.getProjStatusMileStones(projSettingsFilterReq), HttpStatus.OK);

    }

    @RequestMapping(value = ProjSettingsURLConstants.DELETE_PROJ_STATUS_MILESTONES, method = RequestMethod.POST)
    public ResponseEntity<ProjMileStonesResp> deleteProjStatusMileStones(
            @RequestBody ProjMileStonesDateSaveReq projMileStonesDateSaveReq) {
        projSettingsService.deleteProjStatusMileStones(projMileStonesDateSaveReq);
        ProjMileStonesResp projMileStonesResp = new ProjMileStonesResp();
        projMileStonesResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjMileStonesResp>(projMileStonesResp, HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.FIND_EMP_TRANS_NORMAL_TIME)
    public ResponseEntity<ProjEmpTransResp> findEmpTransNormalTime(@RequestBody ProjEmpTransGetReq empTransGetReq) {
        ProjEmpTransResp resp = projSettingsService.findEmpTransNormalTime(empTransGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_EMP_FOR_BUDGETS, method = RequestMethod.POST)
    public ResponseEntity<EmpClassesResp> getEmpClasses(@RequestBody ProjManpowerGetReq projManpowerGetReq) {
        return new ResponseEntity<EmpClassesResp>(projSettingsService.getEmpClasses(projManpowerGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PLANT_FOR_BUDGETS, method = RequestMethod.POST)
    public ResponseEntity<PlantClassResp> getPlantClasses(@RequestBody ProjManpowerGetReq projManpowerGetReq) {
        return new ResponseEntity<PlantClassResp>(projSettingsService.getPlantClasses(projManpowerGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_MATERIAL_FOR_BUDGETS, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> getMaterialGroups(@RequestBody ProjManpowerGetReq projManpowerGetReq) {
        return new ResponseEntity<MaterialClassResp>(projSettingsService.getMaterialGroups(projManpowerGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.GET_PROJ_COSTCODE_ACTUAL_QTY)
    public ResponseEntity<Map<Long, Double>> getCostCodeActualQty(@RequestBody ProjManpowerGetReq projManpowerGetReq) {
        return new ResponseEntity<>(projSettingsService.getCostCodeActualQty(projManpowerGetReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.GET_ACTUAL_COST_DETAIL)
    public ResponseEntity<DateWiseCostReportResp> getDatewiseActualCostDetails(
            @RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(projSettingsService.getActualCostDetails(costReportReq), HttpStatus.OK);
    }
    
    @PostMapping(value = ProjSettingsURLConstants.GET_PLANNED_ACTUAL_EARNED_DASHBOARD)
    public ResponseEntity<DateWiseCostReportResp> getPlanActualEarned(
            @RequestBody CostReportReq costReportReq) {
        return new ResponseEntity<>(projSettingsService.getPlanActualEarned(costReportReq), HttpStatus.OK);
    }
    
    @PostMapping(value = "getProjectsDatesForProgressSCurveReport")
    public ResponseEntity<ProjStatusDatesResp> getProjectsDatesForProgressSCurveReport(
            @RequestBody ProjSettingsFilterReq projSettingsFilterReq) {
        return new ResponseEntity<>(projSettingsService.getProjectsDatesForProgressSCurveReport(projSettingsFilterReq), HttpStatus.OK);
    }
    
    @PostMapping(value = "getProgressSCurveReportData")
    public ResponseEntity<ProgressSCurveTOResp> getProgressSCurveReportData(
            @RequestBody ProgressReportGetReq progressReportGetReq) {
        return new ResponseEntity<>(projSettingsService.getProgressSCurveReportData(progressReportGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = "getProgressSCurveManpowerReportData")
    public ResponseEntity<ProgressSCurveTOResp> getProgressSCurveManpowerReportData(
            @RequestBody ProgressReportGetReq progressReportGetReq) {
        return new ResponseEntity<>(projSettingsService.getProgressSCurveManpowerReportData(progressReportGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = ProjSettingsURLConstants.GET_PLANNED_VALUE)
    public ResponseEntity<ProjPlannedValueResp> getProjPlannedValue(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
    	return new ResponseEntity<>(projSettingsService.getProjPlannedValue(projCostStatementsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = CommonConstants.GET_TANGIBLES_OF_PROJECTS)
    public ResponseEntity<ProjectTangibleResp> getTangiblesOfProjects(@RequestBody ProjectTangibleReq projectTangibleReq) {
        return new ResponseEntity<ProjectTangibleResp>(projSettingsService.getTangiblesOfProjects(projectTangibleReq), HttpStatus.OK);
    }
    
    @PostMapping(value = ProjSettingsURLConstants.GET_MANPOWER_PRODUCTIVITY_ANALYSIS_REPORT_DATA)
    public ResponseEntity<ProjectTangibleResp> getManpowerProductivityAnalysisReportData(@RequestBody ProjectTangibleReq projectTangibleReq) {
    	return new ResponseEntity<>(projSettingsService.getManpowerProductivityAnalysisReportData(projectTangibleReq), HttpStatus.OK);
    }
    
    @PostMapping(value = ProjSettingsURLConstants.GET_PROJECTS_GANTT_CHART_REPORT_DATA)
    public ResponseEntity<ProjectGanntChartResp> getProjectsGanttChartReportData(@RequestBody ProjGeneralsGetReq projGeneralsGetReq) {
        return new ResponseEntity<>(projSettingsService.getProjectsGanttChartReportData(projGeneralsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = ProjSettingsURLConstants.BUDGET_APPROVAL)
    public ResponseEntity<ProjBudgetResp> projectBudgetApproval( @RequestBody ProjGeneralsGetReq projGeneralsGetReq ) {
    	System.out.println("projectBudgetApproval function of ProjSettingsController class");
        return new ResponseEntity<ProjBudgetResp>( projSettingsService.projectBudgetApproval( projGeneralsGetReq ), HttpStatus.OK );
    }
    
    @PostMapping(value = ProjSettingsURLConstants.BUDGET_RETURN_WITH_COMMENTS)
    public ResponseEntity<ProjBudgetResp> projectBudgetReturn( @RequestBody ProjGeneralsGetReq projGeneralsGetReq ) {
    	System.out.println("projectBudgetReturn function of ProjSettingsController class");
        return new ResponseEntity<ProjBudgetResp>( projSettingsService.projectBudgetReturn( projGeneralsGetReq ), HttpStatus.OK );
    }
    
    //=============================Schedule of Estimates======================================================//
    @RequestMapping(value = ProjSettingsURLConstants.SAVE_SCH_OF_ESTIMATES, method = RequestMethod.POST)
    public ResponseEntity<ProjSchofEstimatesResp> saveProjSchofEstimates(@RequestBody SchofEstimatesSaveReq schofEstimatesSaveReq){
    	projSettingsService.saveProjSchofEstimates(schofEstimatesSaveReq);
    	
    	SchofEstimatesGetReq schofEstimatesGetReq = new SchofEstimatesGetReq();
    	schofEstimatesGetReq.setProjId(schofEstimatesSaveReq.getProjId());
    	schofEstimatesGetReq.setStatus(StatusCodes.ACTIVE.getValue());
    	
    	ProjSchofEstimatesResp  projSchofEstimatesResp  = projSettingsService.getProjSchofEstimates(schofEstimatesGetReq);
    	projSchofEstimatesResp.cloneAppResp(CommonUtil.getSaveAppResp());
    	return new ResponseEntity<ProjSchofEstimatesResp>(projSchofEstimatesResp, HttpStatus.OK);
    }
    
    @RequestMapping(value= ProjSettingsURLConstants.GET_SCH_OF_ESTIMATES, method=RequestMethod.POST)
    public ResponseEntity<ProjSchofEstimatesResp> getProjSchofEstimates(@RequestBody SchofEstimatesGetReq schofEstimateGetReq){
    	return new ResponseEntity<ProjSchofEstimatesResp>(projSettingsService.getProjSchofEstimates(schofEstimateGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_SOEAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveSoeAppr(@RequestBody ProjSoeApprSaveReq projSoeApprSaveReq) {
        projSettingsService.saveSoeAppr(projSoeApprSaveReq);
        AppResp appResp = new AppResp();
        appResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);
    }
    
    //======================Schedule of Rates==================================================//
    @RequestMapping(value= ProjSettingsURLConstants.SAVE_SCH_OF_RATES, method= RequestMethod.POST)
    public ResponseEntity<ProjSchofRatesResp> saveProjSchofRates(@RequestBody SchofRatesSaveReq schofRatesSaveReq){
    	projSettingsService.saveProjSchofRates(schofRatesSaveReq);
    	
    	SchofRatesGetReq schofRatesGetReq = new SchofRatesGetReq();
    	schofRatesGetReq.setProjId(schofRatesSaveReq.getProjId());
    	schofRatesGetReq.setStatus(StatusCodes.ACTIVE.getValue());
    	
    	ProjSchofRatesResp projSchofRatesResp = projSettingsService.getProjSchofRates(schofRatesGetReq);
    	projSchofRatesResp.cloneAppResp(CommonUtil.getSaveAppResp());
    	return new ResponseEntity<ProjSchofRatesResp>(projSchofRatesResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjSettingsURLConstants.GET_SCH_OF_RATES, method=RequestMethod.POST)
    public ResponseEntity<ProjSchofRatesResp> getProjSchofRates(@RequestBody SchofRatesGetReq schofRatesGetReq){
    	return new ResponseEntity<ProjSchofRatesResp>(projSettingsService.getProjSchofRates(schofRatesGetReq), HttpStatus.OK);
    }
    
   //=========================Resource Budget==================================================//
    @RequestMapping(value= ProjSettingsURLConstants.SAVE_RESOUCE_BUDGET, method= RequestMethod.POST)
    public ResponseEntity<ProjResourceBudgetResp> saveProjResBudget(@RequestBody ResourceBudgetSaveReq resBudgetSaveReq){
    	projSettingsService.saveProjResBudget(resBudgetSaveReq);
    	
    	ResourceBudgetGetReq resBudgetGetReq = new ResourceBudgetGetReq();
    	resBudgetGetReq.setProjId(resBudgetSaveReq.getProjId());
    	resBudgetGetReq.setStatus(StatusCodes.ACTIVE.getValue());
    	
    	ProjResourceBudgetResp projResourceBudgetResp = projSettingsService.getProjResBudget(resBudgetGetReq);
    	projResourceBudgetResp.cloneAppResp(CommonUtil.getSaveAppResp());
    	return new ResponseEntity<ProjResourceBudgetResp>(projResourceBudgetResp, HttpStatus.OK);
    }
    
    @RequestMapping(value= ProjSettingsURLConstants.GET_RESOURCE_BUDGET, method=RequestMethod.POST)
    public ResponseEntity<ProjResourceBudgetResp> getProjResBudget(@RequestBody ResourceBudgetGetReq resBudgetGetReq){
    	return new ResponseEntity<ProjResourceBudgetResp>(projSettingsService.getProjResBudget(resBudgetGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value= ProjSettingsURLConstants.GET_CHANGE_ORDER_ONLOAD, method=RequestMethod.POST)
    public ResponseEntity<ChangeOrderDetailsResp> getProjChangeOrderDetail(@RequestBody ChangeOrderDetailsGetReq changeOrderDetailsGetReq){
    	System.out.println("ChangeOrderDetailsGetReq1239 "+changeOrderDetailsGetReq.getProjId());
    	return new ResponseEntity<ChangeOrderDetailsResp>(projSettingsService.getProjChangeOrderDetail(changeOrderDetailsGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjSettingsURLConstants.SAVE_CHANGE_ORDER_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderDetailsResp> saveProjChangeOrderDetail(@RequestBody ChangeOrderDetailsSaveReq changeOrderDetailsSaveReq){
    	projSettingsService.saveProjChangeOrderDetail(changeOrderDetailsSaveReq);
    	
    	ChangeOrderDetailsGetReq changeOrderDetailsGetReq = new ChangeOrderDetailsGetReq();
    	changeOrderDetailsGetReq.setProjId(changeOrderDetailsSaveReq.getProjId());
    	changeOrderDetailsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
    	
    	ChangeOrderDetailsResp  changeOrderDetailsResp  = projSettingsService.getProjChangeOrderDetail(changeOrderDetailsGetReq);
    	changeOrderDetailsResp.cloneAppResp(CommonUtil.getSaveAppResp());
    	return new ResponseEntity<ChangeOrderDetailsResp>(changeOrderDetailsResp, HttpStatus.OK);
    }
    
    
}