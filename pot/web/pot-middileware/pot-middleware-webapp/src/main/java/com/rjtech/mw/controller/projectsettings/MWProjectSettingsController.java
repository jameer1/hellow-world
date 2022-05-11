package com.rjtech.mw.controller.projectsettings;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.req.CompanyFilterReq;
import com.rjtech.centrallib.req.ProcureCatgFilterReq;
import com.rjtech.centrallib.resp.CompanyResp;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.centrallib.resp.ProcureCatgResp;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.req.UserGetReq;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.UserModulePermissionResp;
import com.rjtech.common.utils.ActionCodes;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodes;
import com.rjtech.finance.req.ProfitCentreGetReq;
import com.rjtech.finance.resp.ProfitCentreResp;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.finance.MWFinanceMasterService;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsService;
import com.rjtech.mw.service.user.MWUserService;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projsettings.constans.ProjSettingsURLConstants;
import com.rjtech.projsettings.dto.ProjGenCurrencyResp;
import com.rjtech.projsettings.req.ChangeOrderDetailsGetReq;
import com.rjtech.projsettings.req.ChangeOrderDetailsSaveReq;
import com.rjtech.projsettings.req.ProjAttendenceApprSaveReq;
import com.rjtech.projsettings.req.ProjAttendenceGetReq;
import com.rjtech.projsettings.req.ProjAttendenceSaveReq;
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
import com.rjtech.projsettings.resp.ChangeOrderDetailsResp;
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
import com.rjtech.projsettings.resp.ProjBudgetResp;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.projsettings.req.SchofEstimatesSaveReq;
import com.rjtech.projsettings.dto.SchofEstimatesApprTO;
import com.rjtech.projsettings.req.SchofEstimatesGetReq;
import com.rjtech.projsettings.resp.ProjSchofEstimatesResp;
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

@RestController
@RequestMapping(ProjSettingsURLConstants.PROJ_SETTINGS_PARH_URL)
public class MWProjectSettingsController {

    @Autowired
    private MWProjectSettingsService mwProjectSettingsService;

    @Autowired
    private MWUserService mwUserService;

    @Autowired
    private MWFinanceMasterService mwFinanceMasterService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_ATTENDENCEAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjAttendenceAppr(
            @RequestBody ProjAttendenceApprSaveReq projAttendenceApprSaveReq) {
        return new ResponseEntity<AppResp>(mwProjectSettingsService.saveProjAttendenceAppr(projAttendenceApprSaveReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = ProjSettingsURLConstants.GET_MULTI_PROJ_GENERALS)
    public ResponseEntity<ProjGeneralsResp> getMultiProjGenerals(@RequestBody ProjGeneralsGetReq projGeneralsGetReq) {
        return new ResponseEntity<>(mwProjectSettingsService.getMultiProjGenerals(projGeneralsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = ProjSettingsURLConstants.GET_MULTI_PROJ_COSTSTATEMENTS)
    public ResponseEntity<ProjCostStaementsResp> getMultiProjCostStatements(@RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
    	System.out.println("MWController > getMultiProjCostStatements ");
        return new ResponseEntity<>(mwProjectSettingsService.getMultiProjCostStatements(projCostStatementsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_GENERALS, method = RequestMethod.POST)
    public ResponseEntity<ProjGeneralsResp> getProjGenerals(@RequestBody ProjGeneralsGetReq projGeneralsGetReq) {
        return new ResponseEntity<ProjGeneralsResp>(mwProjectSettingsService.getProjGenerals(projGeneralsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_GENERALS, method = RequestMethod.POST)
    public ResponseEntity<ProjGeneralsResp> saveProjGenerals(@RequestBody ProjGeneralSaveReq projGeneralSaveReq) {

        return new ResponseEntity<ProjGeneralsResp>(mwProjectSettingsService.saveProjGenerals(projGeneralSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_WORKDAIRYAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveWorkDairyAppr(@RequestBody ProjWorkDairyApprSaveReq projWorkDairyApprSaveReq) {

        return new ResponseEntity<AppResp>(mwProjectSettingsService.saveWorkDairyAppr(projWorkDairyApprSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_ATTENDENCE, method = RequestMethod.POST)
    public ResponseEntity<ProjAttendenceResp> getProjAttendence(
            @RequestBody ProjAttendenceGetReq projAttendenceGetReq) {
        return new ResponseEntity<ProjAttendenceResp>(mwProjectSettingsService.getProjAttendence(projAttendenceGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_ATTENDENCE, method = RequestMethod.POST)
    public ResponseEntity<ProjAttendenceResp> saveProjAttendence(
            @RequestBody ProjAttendenceSaveReq projAttendenceSaveReq) {
        return new ResponseEntity<ProjAttendenceResp>(
                mwProjectSettingsService.saveProjAttendence(projAttendenceSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_WORKDAIRY, method = RequestMethod.POST)
    public ResponseEntity<ProjWorkDairyResp> getWorkDairy(@RequestBody ProjWorkDairyGetReq projWorkDairyGetReq) {
        return new ResponseEntity<ProjWorkDairyResp>(mwProjectSettingsService.getWorkDairy(projWorkDairyGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_WORKDAIRY, method = RequestMethod.POST)
    public ResponseEntity<ProjWorkDairyResp> saveWorkDairy(@RequestBody ProjWorkDairySaveReq projWorkDairySaveReq) {
        return new ResponseEntity<ProjWorkDairyResp>(mwProjectSettingsService.saveWorkDairy(projWorkDairySaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_TIMESHEET, method = RequestMethod.POST)
    public ResponseEntity<ProjTimeSheetResp> getProjTimeSheet(@RequestBody ProjTimeSheetGetReq projTimeSheetGetReq) {

        return new ResponseEntity<ProjTimeSheetResp>(mwProjectSettingsService.getProjTimeSheet(projTimeSheetGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_TIMESHEET, method = RequestMethod.POST)
    public ResponseEntity<ProjTimeSheetResp> saveProjTimeSheet(@RequestBody ProjTimeSheetSaveReq projTimeSheetSaveReq) {

        return new ResponseEntity<ProjTimeSheetResp>(mwProjectSettingsService.saveProjTimeSheet(projTimeSheetSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_TIMESHEETAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjTimeSheetAppr(
            @RequestBody ProjTimeSheetApprSaveReq projTimeSheetApprSaveReq) {

        return new ResponseEntity<AppResp>(mwProjectSettingsService.saveProjTimeSheetAppr(projTimeSheetApprSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PROCURE, method = RequestMethod.POST)
    public ResponseEntity<ProjProcureResp> getProjProcure(@RequestBody ProjProcureGetReq projProcureGetReq) {
        return new ResponseEntity<ProjProcureResp>(mwProjectSettingsService.getProjProcure(projProcureGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROCURE, method = RequestMethod.POST)
    public ResponseEntity<ProjProcureResp> saveProjProcurement(@RequestBody ProjProcureSaveReq projProcureSaveReq) {

        return new ResponseEntity<ProjProcureResp>(mwProjectSettingsService.saveProjProcurement(projProcureSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROCUREAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjProcurementAppr(@RequestBody ProjProcureApprSaveReq projProcureApprSaveReq) {

        return new ResponseEntity<AppResp>(mwProjectSettingsService.saveProjProcurementAppr(projProcureApprSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EMPTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpTransResp> getEmpTrans(@RequestBody ProjEmpTransGetReq projEmpTransGetReq) {
        return new ResponseEntity<ProjEmpTransResp>(mwProjectSettingsService.getEmpTrans(projEmpTransGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_EMPTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjEmpTransResp> saveProjEmpTrans(@RequestBody ProjEmpTransSaveReq projEmpTransSaveReq) {

        return new ResponseEntity<ProjEmpTransResp>(mwProjectSettingsService.saveEmpTrans(projEmpTransSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_EMPTRANSAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveEmpTransAppr(@RequestBody ProjEmpTransApprSaveReq projEmpTransApprSaveReq) {

        return new ResponseEntity<AppResp>(mwProjectSettingsService.saveEmpTransAppr(projEmpTransApprSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PLANTTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjPlantTransResp> getProjPlantTrans(
            @RequestBody ProjPlantTransGetReq projPlantTransGetReq) {
        return new ResponseEntity<ProjPlantTransResp>(mwProjectSettingsService.getProjPlantTrans(projPlantTransGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PLANTTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjPlantTransResp> saveProjPlantTrans(
            @RequestBody ProjPlantTransSaveReq projPlantTransSaveReq) {

        return new ResponseEntity<ProjPlantTransResp>(
                mwProjectSettingsService.saveProjPlantTrans(projPlantTransSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PLANTTRANSAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjPlantTransAppr(
            @RequestBody ProjPlantTransApprSaveReq projPlantTransApprSaveReq) {

        return new ResponseEntity<AppResp>(mwProjectSettingsService.saveProjPlantTransAppr(projPlantTransApprSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_MATERIALTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjMaterialTransResp> getProjMaterialTrans(
            @RequestBody ProjMaterialTransGetReq projMaterialTransGetReq) {
        return new ResponseEntity<ProjMaterialTransResp>(
                mwProjectSettingsService.getProjMaterialTrans(projMaterialTransGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_MATERIALTRANS, method = RequestMethod.POST)
    public ResponseEntity<ProjMaterialTransResp> saveProjMaterialTrans(
            @RequestBody ProjMaterialTransSaveReq projMaterialTransSaveReq) {

        return new ResponseEntity<ProjMaterialTransResp>(
                mwProjectSettingsService.saveProjMaterialTrans(projMaterialTransSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_MATERIALTRANSAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjMaterialTransAppr(
            @RequestBody ProjMaterialTransApprSaveReq projMaterialTransApprSaveReq) {

        return new ResponseEntity<AppResp>(
                mwProjectSettingsService.saveProjMaterialTransAppr(projMaterialTransApprSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_ESTIMATE, method = RequestMethod.POST)
    public ResponseEntity<ProjEstimateResp> getProjEstimate(@RequestBody ProjEstimateGetReq projEstimateGetReq) {
        return new ResponseEntity<ProjEstimateResp>(mwProjectSettingsService.getProjEstimate(projEstimateGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_ESTIMATE, method = RequestMethod.POST)
    public ResponseEntity<ProjEstimateResp> saveProjEstimate(@RequestBody ProjEstimateSaveReq projEstimateSaveReq) {

        return new ResponseEntity<ProjEstimateResp>(mwProjectSettingsService.saveProjEstimate(projEstimateSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJOJECT_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<ProjectMaterialsResp> getProjectMaterials(
            @RequestBody ProjectMaterialGetReq projectMaterialGetReq) {
        return new ResponseEntity<ProjectMaterialsResp>(
                mwProjectSettingsService.getProjectMaterials(projectMaterialGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJOJECT_MATERIALS, method = RequestMethod.POST)
    public ResponseEntity<ProjectMaterialsResp> saveProjectMaterials(
            @RequestBody ProjectMaterialSaveReq projectMaterialSaveReq) {

        return new ResponseEntity<ProjectMaterialsResp>(
                mwProjectSettingsService.saveProjectMaterials(projectMaterialSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_COSTBUDGETS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostBudgetResp> getProjCostBudgets(
            @RequestBody ProjCostBudgetGetReq projCostBudgetGetReq) {
        return new ResponseEntity<ProjCostBudgetResp>(mwProjectSettingsService.getProjCostBudgets(projCostBudgetGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_COSTBUDGETS, method = RequestMethod.POST)
    public void saveProjCostBudgets(@RequestBody ProjCostBudgetSaveReq projCostBudgetSaveReq) {
        mwProjectSettingsService.saveProjCostBudgets(projCostBudgetSaveReq);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        System.out.println("MWProjectSettingsController:getProjCostStatements 33");
        return new ResponseEntity<ProjCostStaementsResp>(
                mwProjectSettingsService.getProjCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EXIT_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjExitCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        return new ResponseEntity<ProjCostStaementsResp>(
                mwProjectSettingsService.getProjExitCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    //
    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EXIT_MANPOWER_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjExitManpowerCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        return new ResponseEntity<ProjCostStaementsResp>(
                mwProjectSettingsService.getProjExitManpowerCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EXIT_MATERIAL_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjExitMaterialCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        return new ResponseEntity<ProjCostStaementsResp>(
                mwProjectSettingsService.getProjExitMaterialCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EXIT_PLANT_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjExitPlantCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        return new ResponseEntity<ProjCostStaementsResp>(
                mwProjectSettingsService.getProjExitPlantCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_EXIT_SERVICES_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjExitServiceCostStatements(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
        return new ResponseEntity<ProjCostStaementsResp>(
                mwProjectSettingsService.getProjExitServiceCostStatements(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_COSTSTATEMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> saveProjCostStatements(
            @RequestBody ProjCostStatementsSaveReq projCostStatementsSaveReq) {

        return new ResponseEntity<ProjCostStaementsResp>(
                mwProjectSettingsService.saveProjCostStatements(projCostStatementsSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJOJECT_PLANTS, method = RequestMethod.POST)
    public ResponseEntity<ProjectPlantsResp> getProjectPlants(@RequestBody ProjectPlantsGetReq projectPlantsGetReq) {
        return new ResponseEntity<ProjectPlantsResp>(mwProjectSettingsService.getProjectPlants(projectPlantsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJOJECT_PLANTS, method = RequestMethod.POST)
    public ResponseEntity<ProjectPlantsResp> saveProjectPlants(@RequestBody ProjectPlantsSaveReq projectPlantsSaveReq) {

        return new ResponseEntity<ProjectPlantsResp>(mwProjectSettingsService.saveProjectPlants(projectPlantsSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_REPORTS, method = RequestMethod.POST)
    public ResponseEntity<ProjReportsResp> getProjReports(@RequestBody ProjReportsGetReq projReportsGetReq) {
        return new ResponseEntity<ProjReportsResp>(mwProjectSettingsService.getProjReports(projReportsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_REPORTS, method = RequestMethod.POST)
    public ResponseEntity<ProjReportsResp> saveProjReports(@RequestBody ProjReportsSaveReq projReportsSaveReq) {

        return new ResponseEntity<ProjReportsResp>(mwProjectSettingsService.saveProjReports(projReportsSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_MANPOWERS, method = RequestMethod.POST)
    public ResponseEntity<ProjManPowerResp> getProjManPowers(@RequestBody ProjManpowerGetReq projManpowerGetReq) {
        return new ResponseEntity<ProjManPowerResp>(mwProjectSettingsService.getProjManPowers(projManpowerGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_MANPOWERS, method = RequestMethod.POST)
    public ResponseEntity<ProjManPowerResp> saveProjManPowers(@RequestBody ProjManpowerSaveReq projManpowerSaveReq) {

        return new ResponseEntity<ProjManPowerResp>(mwProjectSettingsService.saveProjManPowers(projManpowerSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PROGRESS, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressResp> getProjProgress(@RequestBody ProjProgressGetReq projProgressGetReq) {
        return new ResponseEntity<ProjProgressResp>(mwProjectSettingsService.getProjProgress(projProgressGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROGRESS, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressResp> saveProjProgress(@RequestBody ProjProgressSaveReq projProgressSaveReq) {
        return new ResponseEntity<ProjProgressResp>(mwProjectSettingsService.saveProjProgress(projProgressSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.PROJ_GENERALS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjGeneralOnLoadResp> projGeneralOnLoadResps(
            @RequestBody ProjGeneralOnLoadReq projGeneralOnLoadReq) {
        ProjGeneralOnLoadResp projGeneralOnLoadResp = mwProjectSettingsService
                .projGeneralOnLoadResps(projGeneralOnLoadReq);

        CompanyFilterReq companyFilterReq = new CompanyFilterReq();
        companyFilterReq.setStatus(projGeneralOnLoadReq.getStatus());
        CompanyResp CompanyResp = mwCentralLiblService.getCompanies(companyFilterReq);
        projGeneralOnLoadResp.setCompanyTOs(CompanyResp.getCompanyTOs());

        ProfitCentreGetReq profitCentreGetReq = new ProfitCentreGetReq();
        companyFilterReq.setStatus(projGeneralOnLoadReq.getStatus());
        ProfitCentreResp profitCentreResp = mwFinanceMasterService.getProfitCentres(profitCentreGetReq);
        projGeneralOnLoadResp.setProfitCentreTOs(profitCentreResp.getProfitCentreTOs());

        UserGetReq userGetReq = new UserGetReq();
        userGetReq.setModuleCode(ModuleCodes.GENERAL_VALUES.getDesc());
        userGetReq.setActionCode(ActionCodes.APPROVE.getDesc());
        userGetReq.setClientId(AppUserUtils.getClientId());
        UserModulePermissionResp userModulePermissionResp = mwUserService.getUsersByModulePermission(userGetReq);
        projGeneralOnLoadResp.setUsers(userModulePermissionResp.getUsers());

        if (CommonUtil.isListHasData(projGeneralOnLoadResp.getUsers())) {
            for (LabelKeyTO labelKeyTO : projGeneralOnLoadResp.getUsers()) {
                if (labelKeyTO.getId().equals(projGeneralOnLoadResp.getProjGeneralMstrTO().getUserId())) {
                    projGeneralOnLoadResp.getProjGeneralMstrTO().setUserLabelKeyTO(labelKeyTO);
                }
            }
        }
        return new ResponseEntity(AppUtils.toJson(projGeneralOnLoadResp), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.PROJ_TIMESHEET_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjTimeSheetOnLoadResp> projTimeSheetOnLoadResps(
            @RequestBody ProjTimeSheetGetReq projTimeSheetGetReq) {

        ProjTimeSheetOnLoadResp projTimeSheetOnLoadResp = mwProjectSettingsService
                .projTimeSheetOnLoadResps(projTimeSheetGetReq);
        return new ResponseEntity<ProjTimeSheetOnLoadResp>(projTimeSheetOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_COSTCODESTATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostCodeStatusResp> getProjCostCodeStatus(
            @RequestBody ProjCostCodeStatusGetReq projCostCodeStatusGetReq) {
        return new ResponseEntity<ProjCostCodeStatusResp>(
                mwProjectSettingsService.getProjCostCodeStatus(projCostCodeStatusGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_SUMMARY, method = RequestMethod.POST)
    public ResponseEntity<ProjSummaryResp> getProjSummary(@RequestBody ProjSummaryGetReq projSummaryGetReq) {
        return new ResponseEntity<ProjSummaryResp>(mwProjectSettingsService.getProjSummary(projSummaryGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_STATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjStatusResp> getProjStatus(@RequestBody ProjStatusGetReq projStatusGetReq) {
        return new ResponseEntity<ProjStatusResp>(mwProjectSettingsService.getProjStatus(projStatusGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PROGRESSCLAIM, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressClaimResp> getProjProgressClaim(
            @RequestBody ProjProgressClaimGetReq projProgressClaimGetReq) {

        return new ResponseEntity<ProjProgressClaimResp>(
                mwProjectSettingsService.getProjProgressClaim(projProgressClaimGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROGRESSCLAIM, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressClaimResp> saveProjProgressClaim(
            @RequestBody ProjProgressClaimSaveReq projProgressClaimSaveReq) {

        return new ResponseEntity<ProjProgressClaimResp>(
                mwProjectSettingsService.saveProjProgressClaim(projProgressClaimSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROGRESSCLAIMAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjProgressClaimAppr(
            @RequestBody ProjProgressClaimApprSaveReq projProgressClaimApprSaveReq) {

        return new ResponseEntity<AppResp>(
                mwProjectSettingsService.saveProjProgressClaimAppr(projProgressClaimApprSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PROGRESS_CLAIM_PERIOD, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressClaimePeroidResp> getProjProgressClaimePeriodCycle(
            @RequestBody ProjProgressClaimGetReq projProgressClaimGetReq) {

        return new ResponseEntity<ProjProgressClaimePeroidResp>(
                mwProjectSettingsService.getProjProgressClaimePeriodCycle(projProgressClaimGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PROGRESS_CLAIM_PERIOD, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressClaimePeroidResp> saveProjProgressClaimePeriodCycle(
            @RequestBody ProjProgressClaimePeroidSaveReq projProgressClaimePeroidSaveReq) {

        return new ResponseEntity<ProjProgressClaimePeroidResp>(
                mwProjectSettingsService.saveProjProgressClaimePeriodCycle(projProgressClaimePeroidSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.PROJ_PROGRESS_CLAIM_PERIOD_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressClaimePeriodOnLoadResp> getProjProgressClaimePeriodCycleOnload(
            @RequestBody ProjProgressClaimGetReq projProgressClaimGetReq) {

        ProjProgressClaimePeriodOnLoadResp projProgressClaimePeriodOnLoadResp = mwProjectSettingsService
                .getProjProgressClaimePeriodCycleOnload(projProgressClaimGetReq);
        return new ResponseEntity<ProjProgressClaimePeriodOnLoadResp>(projProgressClaimePeriodOnLoadResp,
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PAYROLLCYCLE, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjPayRollCycle(@RequestBody ProjPayRollCycleSaveReq projPayRollCycleSaveReq) {

        return new ResponseEntity<AppResp>(mwProjectSettingsService.saveProjPayRollCycle(projPayRollCycleSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.PROJ_PAYROLLCYCLE_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjPayRollCycleOnLoadResp> projPayRollCycleOnLoadResps(
            @RequestBody ProjPayRollCycleGetReq projPayRollCycleGetReq) {
        ProjPayRollCycleOnLoadResp projPayRollCycleOnLoadResp = mwProjectSettingsService
                .projPayRollCycleOnLoadResps(projPayRollCycleGetReq);

        ProcureCatgFilterReq procureCatgFilterReq = new ProcureCatgFilterReq();
        procureCatgFilterReq.setStatus(projPayRollCycleGetReq.getStatus());
        procureCatgFilterReq.setClientId(projPayRollCycleGetReq.getClientId());
        ProcureCatgResp ProcureCatgResp = mwCentralLiblService.getProcureCatgs(procureCatgFilterReq);
        projPayRollCycleOnLoadResp.setProcureMentCatgTOs(ProcureCatgResp.getProcureMentCatgTOs());

        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setStatus(projPayRollCycleGetReq.getStatus());
        projGetReq.setProjId(projPayRollCycleGetReq.getProjId());
        //ProjEmpTypeResp projEmpTypeResp = mwProjLibService.getProjEmpTypes(projGetReq);
        //projPayRollCycleOnLoadResp.setProjEmpCatgTOs(projEmpTypeResp.getProjEmpCatgTOs());
        return new ResponseEntity<ProjPayRollCycleOnLoadResp>(projPayRollCycleOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.PROJ_REPORTS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<ProjReportsOnLoadResp> projReportsOnLoadResps(
            @RequestBody ProjReportsGetReq projReportsGetReq) {

        ProjReportsOnLoadResp projReportsOnLoadResp = mwProjectSettingsService
                .projReportsOnLoadResps(projReportsGetReq);
        return new ResponseEntity<ProjReportsOnLoadResp>(projReportsOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_TIMESHEET_WEEK, method = RequestMethod.POST)
    public ResponseEntity<ProjTimeSheetWeekResp> getProjTimeSheetWeek(
            @RequestBody ProjTimeSheetGetReq projTimeSheetGetReq) {

        return new ResponseEntity<ProjTimeSheetWeekResp>(
                mwProjectSettingsService.getProjTimeSheetWeek(projTimeSheetGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_TIMESHEET_WEEK, method = RequestMethod.POST)
    public ResponseEntity<ProjTimeSheetWeekResp> saveProjTimeSheetWeek(
            @RequestBody ProjTimeSheetWeekSaveReq projTimeSheetWeekSaveReq) {

        return new ResponseEntity<ProjTimeSheetWeekResp>(
                mwProjectSettingsService.saveProjTimeSheetWeek(projTimeSheetWeekSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_COSTCODES, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> saveProjCostCodes(
            @RequestBody ProjCostCodesSaveReq projCostCodesSaveReq) {

        return new ResponseEntity<ProjCostStaementsResp>(
                mwProjectSettingsService.saveProjCostCodes(projCostCodesSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_SOWS, method = RequestMethod.POST)
    public ResponseEntity<ProjProgressResp> saveProjSows(@RequestBody ProjSowsSaveReq ProjSowsSaveReq) {

        return new ResponseEntity<ProjProgressResp>(mwProjectSettingsService.saveProjSows(ProjSowsSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_COSTCODESTMTS, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStaementsResp> getProjCostCodeStmts(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {

        return new ResponseEntity<ProjCostStaementsResp>(
                mwProjectSettingsService.getProjCostCodeStmts(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_MANPOWERSTATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjManPowerStatusResp> getProjManPowerstatus(
            @RequestBody ProjManpowerGetReq projManpowerGetReq) {

        return new ResponseEntity<ProjManPowerStatusResp>(
                mwProjectSettingsService.getProjManPowerstatus(projManpowerGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJECT_PLANTSSTATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjectPlantsStatusResp> getProjectPlantsStatus(
            @RequestBody ProjectPlantsGetReq projectPlantsGetReq) {

        return new ResponseEntity<ProjectPlantsStatusResp>(
                mwProjectSettingsService.getProjectPlantsStatus(projectPlantsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJECT_COSTSTATEMENTSSUMMARY, method = RequestMethod.POST)
    public ResponseEntity<ProjCostStatementsSummaryResp> getProjCostStatusSummary(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {

        return new ResponseEntity<ProjCostStatementsSummaryResp>(
                mwProjectSettingsService.getProjCostStatusSummary(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJECT_STATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjectStatusResp> getProjectStatus(
            @RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {

        return new ResponseEntity<ProjectStatusResp>(
                mwProjectSettingsService.getProjectStatus(projCostStatementsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_NOTE_BOOK, method = RequestMethod.POST)
    public ResponseEntity<ProjNoteBookResp> getProjNoteBook(@RequestBody ProjNoteBookGetReq projNoteBookGetReq) {

        return new ResponseEntity<ProjNoteBookResp>(mwProjectSettingsService.getProjNoteBook(projNoteBookGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_NOTE_BOOK, method = RequestMethod.POST)
    public ResponseEntity<ProjNoteBookResp> saveProjNoteBook(@RequestBody ProjNoteBookSaveReq projNoteBookSaveReq) {

        return new ResponseEntity<ProjNoteBookResp>(mwProjectSettingsService.saveProjNoteBook(projNoteBookSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_DEFAULT_SETTINGS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjectDefaultSettinges(
            @RequestBody ProjectDefaultSaveReq projectDefaultSaveReq) {

        return new ResponseEntity<AppResp>(
                mwProjectSettingsService.saveProjectDefaultGenerialSettinges(projectDefaultSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_GENERALS_CURRENCYS, method = RequestMethod.POST)
    public ResponseEntity<ProjGenCurrencyResp> getProjGeneralsCurrencys(
            @RequestBody ProjGeneralsGetReq projGeneralsGetReq) {

        return new ResponseEntity<ProjGenCurrencyResp>(
                mwProjectSettingsService.getProjGeneralsCurrencys(projGeneralsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_PERFORMENCE_THRESHOLD, method = RequestMethod.POST)
    public ResponseEntity<ProjPerformenceThresholdResp> getProjPerformenceThreshold(
            @RequestBody ProjPerformenceThresholdGetReq projPerformenceThresholdGetReq) {

        return new ResponseEntity<ProjPerformenceThresholdResp>(
                mwProjectSettingsService.getProjPerformenceThreshold(projPerformenceThresholdGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PERFORMENCE_THRESHOLD, method = RequestMethod.POST)
    public ResponseEntity<ProjPerformenceThresholdResp> saveProjPerformenceThreshold(
            @RequestBody ProjPerformenceThresholdSaveReq projPerformenceThresholdSaveReq) {

        return new ResponseEntity<ProjPerformenceThresholdResp>(
                mwProjectSettingsService.saveProjPerformenceThreshold(projPerformenceThresholdSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_LEAVE_REQUEST, method = RequestMethod.POST)
    public ResponseEntity<ProjLeaveRequestResp> getProjLeaveRequest(
            @RequestBody ProjLeaveRequestGetReq projLeaveRequestGetReq) {
        return new ResponseEntity<ProjLeaveRequestResp>(
                mwProjectSettingsService.getProjLeaveRequest(projLeaveRequestGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_LEAVE_REQUEST, method = RequestMethod.POST)
    public ResponseEntity<ProjLeaveRequestResp> saveProjLeaveRequest(
            @RequestBody ProjLeaveRequestSaveReq leaveRequestSaveReq) {

        return new ResponseEntity<ProjLeaveRequestResp>(
                mwProjectSettingsService.saveProjLeaveRequest(leaveRequestSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_LEAVE_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjLeaveApproval(@RequestBody ProjLeaveApprSaveReq projLeaveApprSaveReq) {

        return new ResponseEntity<AppResp>(mwProjectSettingsService.saveProjLeaveApproval(projLeaveApprSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_PERFOMANCE_DEFAULT_SETTINGS, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveProjPerfomanceDefaultSettings(
            @RequestBody ProjPerfamanceDefaultSaveReq projPerfamanceDefaultSaveReq) {

        return new ResponseEntity<AppResp>(
                mwProjectSettingsService.saveProjPerfomanceDefaultSettings(projPerfamanceDefaultSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.DELETE_PROJ_PERFORMENCE_THRESHOLD, method = RequestMethod.POST)
    public ResponseEntity<AppResp> deleteProjPerformenceThreshold(
            @RequestBody ProjPerfomanceDelReq projPerfomanceDelReq) {

        return new ResponseEntity<AppResp>(
                mwProjectSettingsService.deleteProjPerformenceThreshold(projPerfomanceDelReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_STATUS_DATES, method = RequestMethod.POST)
    public ResponseEntity<ProjStatusDatesResp> getProjStatusDates(
            @RequestBody ProjSettingsFilterReq projSettingsFilterReq) {

        return new ResponseEntity<ProjStatusDatesResp>(
                mwProjectSettingsService.getProjStatusDates(projSettingsFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_STATUS_DATES, method = RequestMethod.POST)
    public ResponseEntity<ProjStatusDatesResp> saveProjStatusDates(
            @RequestBody ProjStatusDatesSaveReq projStatusDatesSaveReq) {

        return new ResponseEntity<ProjStatusDatesResp>(
                mwProjectSettingsService.saveProjStatusDates(projStatusDatesSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_STATUS_ACTUAL_QTY, method = RequestMethod.POST)
    public ProjStatusActualResp getProjStatusActualQty(@RequestBody ProjStatusActualReq actualReq) {
        return mwProjectSettingsService.getProjStatusActualQty(actualReq);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_DURATION_STATUS, method = RequestMethod.POST)
    public ResponseEntity<ProjStatusDatesResp> saveProjDurationStatus(
            @RequestBody ProjStatusDatesSaveReq projStatusDatesSaveReq) {

        return new ResponseEntity<ProjStatusDatesResp>(
                mwProjectSettingsService.saveProjDurationStatus(projStatusDatesSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_STATUS_MILESTONES, method = RequestMethod.POST)
    public ResponseEntity<ProjMileStonesResp> saveProjStatusMileStones(
            @RequestBody ProjMileStonesDateSaveReq projMileStonesDateSaveReq) {

        return new ResponseEntity<ProjMileStonesResp>(
                mwProjectSettingsService.saveProjStatusMileStones(projMileStonesDateSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.GET_PROJ_STATUS_MILESTONES, method = RequestMethod.POST)
    public ResponseEntity<ProjMileStonesResp> getProjStatusMileStones(
            @RequestBody ProjSettingsFilterReq projSettingsFilterReq) {

        return new ResponseEntity<ProjMileStonesResp>(
                mwProjectSettingsService.getProjStatusMileStones(projSettingsFilterReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjSettingsURLConstants.DELETE_PROJ_STATUS_MILESTONES, method = RequestMethod.POST)
    public ResponseEntity<ProjMileStonesResp> deleteProjStatusMileStones(
            @RequestBody ProjMileStonesDateSaveReq projMileStonesDateSaveReq) {

        return new ResponseEntity<ProjMileStonesResp>(
                mwProjectSettingsService.deleteProjStatusMileStones(projMileStonesDateSaveReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.FIND_EMP_TRANS_NORMAL_TIME)
    public ResponseEntity<ProjEmpTransResp> findEmpTransNormalTime(@RequestBody ProjEmpTransGetReq empTransGetReq) {
        ProjEmpTransResp resp = mwProjectSettingsService.findEmpTransNormalTime(empTransGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjSettingsURLConstants.GET_MATERIAL_FOR_BUDGETS, method = RequestMethod.POST)
    public ResponseEntity<MaterialClassResp> getMaterialGroups(@RequestBody ProjManpowerGetReq projManpowerGetReq) {
        return new ResponseEntity<MaterialClassResp>(mwProjectSettingsService.getMaterialGroups(projManpowerGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjSettingsURLConstants.GET_EMP_FOR_BUDGETS, method = RequestMethod.POST)
    public ResponseEntity<EmpClassesResp> getEmpClasses(@RequestBody ProjManpowerGetReq projManpowerGetReq) {
        return new ResponseEntity<EmpClassesResp>(mwProjectSettingsService.getEmpClasses(projManpowerGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjSettingsURLConstants.GET_PLANT_FOR_BUDGETS, method = RequestMethod.POST)
    public ResponseEntity<PlantClassResp> getPlantClasses(@RequestBody ProjManpowerGetReq projManpowerGetReq) {
        return new ResponseEntity<PlantClassResp>(mwProjectSettingsService.getPlantClasses(projManpowerGetReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = ProjSettingsURLConstants.GET_PROJ_COSTCODE_ACTUAL_QTY)
    public ResponseEntity<String> getCostCodeActualQty(@RequestBody ProjManpowerGetReq projManpowerGetReq) {
        return new ResponseEntity<>(mwProjectSettingsService.getCostCodeActualQty(projManpowerGetReq), HttpStatus.OK);
    }
    
    /* Dashboard Cost and Performance > Date Wise Actual Cost Details */
    @PostMapping(value = ProjSettingsURLConstants.GET_ACTUAL_COST_DETAIL)
    public ResponseEntity<String> getActualCostDetails(
            @RequestBody CostReportReq costReportReq) {
    	System.out.println("getActualCostDetails Dashboard controller");
    	String resp = mwProjectSettingsService.getActualCostDetails(costReportReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    /*----------------------------------------------------------------*/
    /* Dashboard Cost > Cost Work Sheet */
    @RequestMapping(value = ProjSettingsURLConstants.GET_PLANNED_ACTUAL_EARNED_DASHBOARD, method = RequestMethod.POST)
    public ResponseEntity<String> getPlanActualEarned(
            @RequestBody CostReportReq costReportReq) {
    	String resp = mwProjectSettingsService.getPlanActualEarned(costReportReq);
    	System.out.println("Planned Actual Earned Response " + resp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    /*----------------------------------------------------------------*/
    
    @RequestMapping(value = ProjSettingsURLConstants.GET_PLANNED_VALUE, method = RequestMethod.POST)
    public ResponseEntity<ProjPlannedValueResp> getProjPlannedValue (@RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
    	return new ResponseEntity<>(mwProjectSettingsService.getProjPlannedValue(projCostStatementsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.GET_MULTI_PROJ_STATUS_DATES)
    public ResponseEntity<ProjStatusDatesResp> getMultiProjStatusDates(@RequestBody ProjSettingsFilterReq projSettingsFilterReq) {
    	return new ResponseEntity<>(mwProjectSettingsService.getMultiProjStatusDates(projSettingsFilterReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.GET_MULTI_PROJECT_COSTSTATEMENTS_SUMMARY)
    public ResponseEntity<ProjCostStatementsSummaryResp> getMultiProjCostStatusSummary(@RequestBody ProjCostStatementsGetReq projCostStatementsGetReq) {
    	return new ResponseEntity<>(mwProjectSettingsService.getMultiProjCostStatusSummary(projCostStatementsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.GET_MANPOWER_PRODUCTIVITY_ANALYSIS_REPORT_DATA)
    public ResponseEntity<ProjectTangibleResp> getManpowerProductivityAnalysisReportData(@RequestBody ProjectTangibleReq projectTangibleReq) {
    	return new ResponseEntity<>(mwProjectSettingsService.getManpowerProductivityAnalysisReportData(projectTangibleReq), HttpStatus.OK);
    }

    @PostMapping(value = ProjSettingsURLConstants.GET_PROJECTS_GANTT_CHART_REPORT_DATA)
    public ResponseEntity<ProjectGanntChartResp> getProjectsGanttChartReportData(@RequestBody ProjGeneralsGetReq projGeneralsGetReq) {
        return new ResponseEntity<>(mwProjectSettingsService.getProjectsGanttChartReportData(projGeneralsGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjSettingsURLConstants.BUDGET_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<ProjBudgetResp> projBudgetApproval( @RequestBody ProjGeneralsGetReq projGeneralsGetReq ) {
    	System.out.println("projBudgetApproval function of MWProjectSettingsController class");
        return new ResponseEntity<ProjBudgetResp>( mwProjectSettingsService.projBudgetApproval( projGeneralsGetReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjSettingsURLConstants.BUDGET_RETURN_WITH_COMMENTS, method = RequestMethod.POST)
    public ResponseEntity<ProjBudgetResp> projBudgetReturn( @RequestBody ProjGeneralsGetReq projGeneralsGetReq ) {
    	System.out.println("projBudgetReturn function of MWProjectSettingsController class");
        return new ResponseEntity<ProjBudgetResp>( mwProjectSettingsService.projBudgetReturn( projGeneralsGetReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjSettingsURLConstants.SAVE_SCH_OF_ESTIMATES, method = RequestMethod.POST)
    public ResponseEntity<ProjSchofEstimatesResp> saveProjSchofEstimates(@RequestBody SchofEstimatesSaveReq schofEstimatesSaveReq){
    	return new ResponseEntity<ProjSchofEstimatesResp>(mwProjectSettingsService.saveProjSchofEstimates(schofEstimatesSaveReq), HttpStatus.OK);
    }
    
    @RequestMapping(value= ProjSettingsURLConstants.GET_SCH_OF_ESTIMATES, method=RequestMethod.POST)
    public ResponseEntity<ProjSchofEstimatesResp> getProjSchofEstimates(@RequestBody SchofEstimatesGetReq schofEstimateGetReq){
    	return new ResponseEntity<ProjSchofEstimatesResp>(mwProjectSettingsService.getProjSchofEstimates(schofEstimateGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjSettingsURLConstants.SAVE_PROJ_SOEAPPR, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveSoeAppr(
            @RequestBody ProjSoeApprSaveReq projSoeApprSaveReq) {

        return new ResponseEntity<AppResp>(mwProjectSettingsService.saveSoeAppr(projSoeApprSaveReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value= ProjSettingsURLConstants.SAVE_SCH_OF_RATES, method = RequestMethod.POST)
    public ResponseEntity<ProjSchofRatesResp> saveProjSchofRates(@RequestBody SchofRatesSaveReq schofRatesSaveReq){
    	return new ResponseEntity<ProjSchofRatesResp>(mwProjectSettingsService.saveProjSchofRates(schofRatesSaveReq), HttpStatus.OK);
    }
    
    @RequestMapping(value=ProjSettingsURLConstants.GET_SCH_OF_RATES, method= RequestMethod.POST)
    public ResponseEntity<ProjSchofRatesResp> getProjSchofRates(@RequestBody SchofRatesGetReq schofRatesGetReq){
    	return new ResponseEntity<ProjSchofRatesResp>(mwProjectSettingsService.getProjSchofRates(schofRatesGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value=ProjSettingsURLConstants.SAVE_RESOUCE_BUDGET, method= RequestMethod.POST)
    public ResponseEntity<ProjResourceBudgetResp>  saveProjResBudget(@RequestBody ResourceBudgetSaveReq resBudgetSaveReq){
    	return new ResponseEntity<ProjResourceBudgetResp>(mwProjectSettingsService.saveProjResBudget(resBudgetSaveReq), HttpStatus.OK);
    }
    
    @RequestMapping(value=ProjSettingsURLConstants.GET_RESOURCE_BUDGET, method=RequestMethod.POST)
    public ResponseEntity<ProjResourceBudgetResp> getProjResBudget(@RequestBody ResourceBudgetGetReq resBudgetGetReq){
    	return new ResponseEntity<ProjResourceBudgetResp>(mwProjectSettingsService.getProjResBudget(resBudgetGetReq), HttpStatus.OK);
    }
    
    @RequestMapping(value=ProjSettingsURLConstants.GET_CHANGE_ORDER_ONLOAD, method=RequestMethod.POST)
    public ResponseEntity<ChangeOrderDetailsResp> getProjChangeOrderDetail(@RequestBody ChangeOrderDetailsGetReq changeOrderDetailsGetReq){
    	return new ResponseEntity<ChangeOrderDetailsResp>(mwProjectSettingsService.getProjChangeOrderDetail(changeOrderDetailsGetReq), HttpStatus.OK);
    }
    
    
    @RequestMapping(value=ProjSettingsURLConstants.SAVE_CHANGE_ORDER_ONLOAD, method= RequestMethod.POST)
    public ResponseEntity<ChangeOrderDetailsResp>  saveProjChangeOrderDetail(@RequestBody ChangeOrderDetailsSaveReq changeOrderDetailsSaveReq){
    	return new ResponseEntity<ChangeOrderDetailsResp>(mwProjectSettingsService.saveProjChangeOrderDetail(changeOrderDetailsSaveReq), HttpStatus.OK);
    }
    
    
}
