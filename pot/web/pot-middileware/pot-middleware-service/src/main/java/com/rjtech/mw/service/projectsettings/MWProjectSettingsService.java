package com.rjtech.mw.service.projectsettings;

import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.req.NotificationFilterReq;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.NotificationResp;
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
import com.rjtech.projsettings.resp.CalenderResp;
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
import com.rjtech.projsettings.resp.ProjBudgetResp;
import com.rjtech.reports.cost.req.CostReportReq;
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
public interface MWProjectSettingsService {

    AppResp saveProjAttendenceAppr(ProjAttendenceApprSaveReq projAttendenceApprSaveReq);

    ProjGeneralsResp getProjGenerals(ProjGeneralsGetReq projGeneralsGetReq);
    
    ProjGeneralsResp getMultiProjGenerals(ProjGeneralsGetReq projGeneralsGetReq);
    
    ProjCostStaementsResp getMultiProjCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjGeneralsResp saveProjGenerals(ProjGeneralSaveReq projGeneralSaveReq);

    AppResp saveWorkDairyAppr(ProjWorkDairyApprSaveReq projWorkDairyApprSaveReq);

    ProjAttendenceResp getProjAttendence(ProjAttendenceGetReq projAttendenceGetReq);

    ProjAttendenceResp saveProjAttendence(ProjAttendenceSaveReq projAttendenceSaveReq);

    ProjWorkDairyResp getWorkDairy(ProjWorkDairyGetReq projWorkDairyGetReq);

    ProjWorkDairyResp saveWorkDairy(ProjWorkDairySaveReq projWorkDairySaveReq);

    ProjTimeSheetResp getProjTimeSheet(ProjTimeSheetGetReq projTimeSheetGetReq);

    ProjTimeSheetResp saveProjTimeSheet(ProjTimeSheetSaveReq projTimeSheetSaveReq);

    AppResp saveProjTimeSheetAppr(ProjTimeSheetApprSaveReq projTimeSheetApprSaveReq);

    ProjProcureResp getProjProcure(ProjProcureGetReq projProcureGetReq);

    ProjProcureResp saveProjProcurement(ProjProcureSaveReq projProcureSaveReq);

    AppResp saveProjProcurementAppr(ProjProcureApprSaveReq projProcureApprSaveReq);

    ProjEmpTransResp getEmpTrans(ProjEmpTransGetReq projEmpTransGetReq);

    ProjEmpTransResp saveEmpTrans(ProjEmpTransSaveReq projEmpTransSaveReq);

    AppResp saveEmpTransAppr(ProjEmpTransApprSaveReq projEmpTransApprSaveReq);

    ProjPlantTransResp getProjPlantTrans(ProjPlantTransGetReq projPlantTransGetReq);

    AppResp saveEmpTransAppr(ProjPlantTransSaveReq projPlantTransSaveReq);

    ProjPlantTransResp saveProjPlantTrans(ProjPlantTransSaveReq projPlantTransSaveReq);

    AppResp saveProjPlantTransAppr(ProjPlantTransApprSaveReq projPlantTransApprSaveReq);

    ProjMaterialTransResp getProjMaterialTrans(ProjMaterialTransGetReq projMaterialTransGetReq);

    ProjMaterialTransResp saveProjMaterialTrans(ProjMaterialTransSaveReq projMaterialTransSaveReq);

    AppResp saveProjMaterialTransAppr(ProjMaterialTransApprSaveReq projMaterialTransApprSaveReq);

    ProjEstimateResp getProjEstimate(ProjEstimateGetReq projEstimateGetReq);

    ProjEstimateResp saveProjEstimate(ProjEstimateSaveReq projEstimateSaveReq);

    ProjectMaterialsResp getProjectMaterials(ProjectMaterialGetReq projectMaterialGetReq);

    ProjectMaterialsResp saveProjectMaterials(ProjectMaterialSaveReq projectMaterialSaveReq);

    ProjCostBudgetResp getProjCostBudgets(ProjCostBudgetGetReq projCostBudgetGetReq);

    AppResp saveProjCostBudgets(ProjCostBudgetSaveReq projCostBudgetSaveReq);

    ProjCostStaementsResp getProjCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjCostStaementsResp getProjExitCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjCostStaementsResp saveProjCostStatements(ProjCostStatementsSaveReq projCostStatementsSaveReq);

    ProjectPlantsResp getProjectPlants(ProjectPlantsGetReq projectPlantsGetReq);

    ProjectPlantsResp saveProjectPlants(ProjectPlantsSaveReq projectPlantsSaveReq);

    ProjReportsResp getProjReports(ProjReportsGetReq projReportsGetReq);

    ProjReportsResp saveProjReports(ProjReportsSaveReq projReportsSaveReq);

    ProjManPowerResp getProjManPowers(ProjManpowerGetReq projManpowerGetReq);

    ProjManPowerResp saveProjManPowers(ProjManpowerSaveReq projManpowerSaveReq);

    ProjProgressResp getProjProgress(ProjProgressGetReq projProgressGetReq);

    ProjProgressResp saveProjProgress(ProjProgressSaveReq projProgressSaveReq);

    ProjGeneralOnLoadResp projGeneralOnLoadResps(ProjGeneralOnLoadReq projGeneralOnLoadReq);

    ProjTimeSheetOnLoadResp projTimeSheetOnLoadResps(ProjTimeSheetGetReq projTimeSheetGetReq);

    ProjCostCodeStatusResp getProjCostCodeStatus(ProjCostCodeStatusGetReq projCostCodeStatusGetReq);

    ProjSummaryResp getProjSummary(ProjSummaryGetReq projSummaryGetReq);

    ProjStatusResp getProjStatus(ProjStatusGetReq projStatusGetReq);

    List<LabelKeyTO> getWeekDays();

    CalenderResp getCalenders(ProjCalenderGetReq ProjCalenderGetReq);

    ProjResourceCurveResp getResourceCurves(ProjResourceCurveGetReq projResourceCurveGetReq);

    ProjProgressClaimResp getProjProgressClaim(ProjProgressClaimGetReq projProgressClaimGetReq);

    ProjProgressClaimResp saveProjProgressClaim(ProjProgressClaimSaveReq projProgressClaimSaveReq);

    ProjProgressClaimePeroidResp getProjProgressClaimePeriodCycle(ProjProgressClaimGetReq projProgressClaimGetReq);

    ProjProgressClaimePeroidResp saveProjProgressClaimePeriodCycle(
            ProjProgressClaimePeroidSaveReq projProgressClaimePeroidSaveReq);

    ProjProgressClaimePeriodOnLoadResp getProjProgressClaimePeriodCycleOnload(
            ProjProgressClaimGetReq projProgressClaimGetReq);

    AppResp saveProjProgressClaimAppr(ProjProgressClaimApprSaveReq projProgressClaimApprSaveReq);

    AppResp saveProjPayRollCycle(ProjPayRollCycleSaveReq projPayRollCycleSaveReq);

    ProjPayRollCycleOnLoadResp projPayRollCycleOnLoadResps(ProjPayRollCycleGetReq projPayRollCycleGetReq);

    ProjReportsOnLoadResp projReportsOnLoadResps(ProjReportsGetReq projReportsGetReq);

    ProjTimeSheetWeekResp getProjTimeSheetWeek(ProjTimeSheetGetReq projTimeSheetGetReq);

    ProjTimeSheetWeekResp saveProjTimeSheetWeek(ProjTimeSheetWeekSaveReq projTimeSheetWeekSaveReq);

    ProjCostStaementsResp saveProjCostCodes(ProjCostCodesSaveReq projCostCodesSaveReq);

    ProjCostStaementsResp getProjCostCodeStmts(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjManPowerStatusResp getProjManPowerstatus(ProjManpowerGetReq projManpowerGetReq);

    ProjectPlantsStatusResp getProjectPlantsStatus(ProjectPlantsGetReq projectPlantsGetReq);

    ProjCostStatementsSummaryResp getProjCostStatusSummary(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjectStatusResp getProjectStatus(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjNoteBookResp saveProjNoteBook(ProjNoteBookSaveReq projNoteBookSaveReq);

    ProjNoteBookResp getProjNoteBook(ProjNoteBookGetReq projNoteBookGetReq);

    AppResp saveProjectDefaultGenerialSettinges(ProjectDefaultSaveReq projectDefaultSaveReq);

    NotificationResp getPreContractNotifications(NotificationFilterReq notificationFilterReq);

    public AppResp saveProjPerfomanceDefaultSettings(ProjPerfamanceDefaultSaveReq projPerfamanceDefaultSaveReq);

    ProjGenCurrencyResp getProjGeneralsCurrencys(ProjGeneralsGetReq projGeneralsGetReq);

    ProjProgressResp saveProjSows(ProjSowsSaveReq projSowsSaveReq);

    ProjPerformenceThresholdResp getProjPerformenceThreshold(
            ProjPerformenceThresholdGetReq projPerformenceThresholdGetReq);

    ProjPerformenceThresholdResp saveProjPerformenceThreshold(
            ProjPerformenceThresholdSaveReq projPerformenceThresholdSaveReq);

    ProjPerformenceThresholdResp deleteProjPerformenceThreshold(ProjPerfomanceDelReq projPerfomanceDelReq);

    ProjLeaveRequestResp getProjLeaveRequest(ProjLeaveRequestGetReq projLeaveRequestGetReq);

    ProjLeaveRequestResp saveProjLeaveRequest(ProjLeaveRequestSaveReq projLeaveRequestSaveReq);

    LabelKeyTO getProjGeneralsCurrencys(Long projId);

    AppResp saveProjLeaveApproval(ProjLeaveApprSaveReq projLeaveApprSaveReq);

    ProjStatusDatesResp getProjStatusDates(ProjSettingsFilterReq projSettingsFilterReq);

    ProjStatusDatesResp saveProjStatusDates(ProjStatusDatesSaveReq projStatusDatesSaveReq);

    ProjCostStaementsResp getProjExitMaterialCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjCostStaementsResp getProjExitPlantCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjCostStaementsResp getProjExitManpowerCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjCostStaementsResp getProjExitServiceCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjStatusActualResp getProjStatusActualQty(ProjStatusActualReq actualReq);

    ProjStatusDatesResp saveProjDurationStatus(ProjStatusDatesSaveReq projStatusDatesSaveReq);

    ProjMileStonesResp saveProjStatusMileStones(ProjMileStonesDateSaveReq projMileStonesDateSaveReq);

    ProjMileStonesResp getProjStatusMileStones(ProjSettingsFilterReq projSettingsFilterReq);

    ProjMileStonesResp deleteProjStatusMileStones(ProjMileStonesDateSaveReq projMileStonesDateSaveReq);

    ProjEmpTransResp findEmpTransNormalTime(ProjEmpTransGetReq empTransGetReq);
    
    MaterialClassResp getMaterialGroups(ProjManpowerGetReq projManpowerGetReq);
    
    EmpClassesResp getEmpClasses(ProjManpowerGetReq projManpowerGetReq);
    
    PlantClassResp getPlantClasses(ProjManpowerGetReq projManpowerGetReq);
    
    String getCostCodeActualQty(ProjManpowerGetReq projManpowerGetReq);
    
    /* Dashboard Cost and Performance > Date Wise Actual Cost Details */
    String getActualCostDetails(CostReportReq costReportReq);
    
    /* Dashboard Cost > Cost Work Sheet */
    String getPlanActualEarned(CostReportReq costReportReq);
    
    ProjPlannedValueResp getProjPlannedValue(ProjCostStatementsGetReq projCostStatementsGetReq);
    
    ProjStatusDatesResp getMultiProjStatusDates(ProjSettingsFilterReq projSettingsFilterReq);

    ProjCostStatementsSummaryResp getMultiProjCostStatusSummary(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjectTangibleResp getManpowerProductivityAnalysisReportData(ProjectTangibleReq projectTangibleReq);
    ProjectGanntChartResp getProjectsGanttChartReportData(ProjGeneralsGetReq projGeneralsGetReq);
        
    ProjBudgetResp projBudgetApproval( ProjGeneralsGetReq projGeneralsGetReq );
    
    ProjBudgetResp projBudgetReturn( ProjGeneralsGetReq projGeneralsGetReq );
    
    ProjSchofEstimatesResp saveProjSchofEstimates(SchofEstimatesSaveReq schofEstimatesSaveReq);
    
    ProjSchofEstimatesResp getProjSchofEstimates(SchofEstimatesGetReq schofEstimateGetReq);
    
    ProjSchofRatesResp saveProjSchofRates(SchofRatesSaveReq schofRatesSaveReq);
    
    ProjSchofRatesResp getProjSchofRates(SchofRatesGetReq schofRatesGetReq);
    
    ProjResourceBudgetResp saveProjResBudget(ResourceBudgetSaveReq resBudgetSaveReq); 
    
    ProjResourceBudgetResp getProjResBudget(ResourceBudgetGetReq resBudgetGetReq);
    
    AppResp saveSoeAppr(ProjSoeApprSaveReq projSoeApprSaveReq);
    
    ChangeOrderDetailsResp getProjChangeOrderDetail(ChangeOrderDetailsGetReq changeOrderDetailsGetReq);

	ChangeOrderDetailsResp saveProjChangeOrderDetail(ChangeOrderDetailsSaveReq changeOrderDetailsSaveReq);
    
    
}
