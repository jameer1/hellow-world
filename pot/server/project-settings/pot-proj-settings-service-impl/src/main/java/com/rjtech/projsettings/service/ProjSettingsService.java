package com.rjtech.projsettings.service;

import java.util.List;
import java.util.Map;

import com.rjtech.calendar.dto.CalTO;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.common.dto.FinancePeriodPayCyclesTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.progress.reports.req.ProgressReportGetReq;
import com.rjtech.projsettings.dto.ProjGenCurrencyResp;
import com.rjtech.projsettings.model.ChangeOrderNormalTimeEntity;
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
import com.rjtech.projsettings.resp.ChangeOrderDetailsResp;
import com.rjtech.projsettings.resp.ProjAttendenceResp;
import com.rjtech.projsettings.resp.ProjCostCodeStatusResp;
import com.rjtech.projsettings.resp.ProjCostStaementsResp;
import com.rjtech.projsettings.resp.ProjCostStatementsSummaryResp;
import com.rjtech.projsettings.resp.ProjEmpTransResp;
import com.rjtech.projsettings.resp.ProjEstimateResp;
import com.rjtech.projsettings.resp.ProjGeneralsResp;
import com.rjtech.projsettings.resp.ProjLeaveRequestResp;
import com.rjtech.projsettings.resp.ProjManPowerResp;
import com.rjtech.projsettings.resp.ProjManPowerStatusResp;
import com.rjtech.projsettings.resp.ProjMaterialTransResp;
import com.rjtech.projsettings.resp.ProjMileStonesResp;
import com.rjtech.projsettings.resp.ProjNoteBookResp;
import com.rjtech.projsettings.resp.ProjPayRollCycleResp;
import com.rjtech.projsettings.resp.ProjPerformenceThresholdResp;
import com.rjtech.projsettings.resp.ProjPlannedValueResp;
import com.rjtech.projsettings.resp.ProjPlantTransResp;
import com.rjtech.projsettings.resp.ProjProcureResp;
import com.rjtech.projsettings.resp.ProjProgressClaimResp;
import com.rjtech.projsettings.resp.ProjProgressClaimePeroidResp;
import com.rjtech.projsettings.resp.ProjProgressResp;
import com.rjtech.projsettings.resp.ProjReportsResp;
import com.rjtech.projsettings.resp.ProjResourceCurveResp;
import com.rjtech.projsettings.resp.ProjStatusActualResp;
import com.rjtech.projsettings.resp.ProjStatusDatesResp;
import com.rjtech.projsettings.resp.ProjStatusResp;
import com.rjtech.projsettings.resp.ProjSummaryResp;
import com.rjtech.projsettings.resp.ProjTimeSheetResp;
import com.rjtech.projsettings.resp.ProjTimeSheetWeekResp;
import com.rjtech.projsettings.resp.ProjWorkDairyResp;
import com.rjtech.projsettings.resp.ProjectGanntChartResp;
import com.rjtech.projsettings.resp.ProjectMaterialsResp;
import com.rjtech.projsettings.resp.ProjectPlantsResp;
import com.rjtech.projsettings.resp.ProjectPlantsStatusResp;
import com.rjtech.projsettings.resp.ProjectTangibleResp;
import com.rjtech.reports.cost.req.CostReportReq;
import com.rjtech.reports.cost.resp.DateWiseCostReportResp;
import com.rjtech.reports.cost.resp.ProgressSCurveTOResp;
import com.rjtech.projsettings.resp.ProjBudgetResp;
import com.rjtech.projsettings.req.SchofEstimatesSaveReq;
import com.rjtech.projsettings.resp.ProjSchofEstimatesResp;
import com.rjtech.projsettings.req.SchofEstimatesGetReq;
import com.rjtech.projsettings.req.SchofRatesSaveReq;
import com.rjtech.projsettings.resp.ProjSchofRatesResp;
import com.rjtech.projsettings.req.SchofRatesGetReq;

import com.rjtech.projsettings.req.ResourceBudgetSaveReq;
import com.rjtech.projsettings.resp.ProjResourceBudgetResp;
import com.rjtech.projsettings.req.ResourceBudgetGetReq;
import com.rjtech.projsettings.req.ProjSoeApprSaveReq;

public interface ProjSettingsService {

    void saveProjAttendenceAppr(ProjAttendenceApprSaveReq projAttendenceApprSaveReq);

    ProjGeneralsResp getProjGenerals(ProjGeneralsGetReq projGeneralsGetReq);
    
    ProjGeneralsResp getMultiProjGenerals(ProjGeneralsGetReq projGeneralsGetReq);

    void saveProjGenerals(ProjGeneralSaveReq projGeneralSaveReq);

    void saveWorkDairyAppr(ProjWorkDairyApprSaveReq projWorkDairyApprSaveReq);

    ProjAttendenceResp getProjAttendence(ProjAttendenceGetReq projAttendenceGetReq);

    void saveProjAttendence(ProjAttendenceSaveReq projAttendenceSaveReq);

    ProjWorkDairyResp getWorkDairy(ProjWorkDairyGetReq projWorkDairyGetReq);

    void saveWorkDairy(ProjWorkDairySaveReq projWorkDairySaveReq);

    ProjTimeSheetResp getProjTimeSheet(ProjTimeSheetGetReq projTimeSheetGetReq);

    void saveProjTimeSheet(ProjTimeSheetSaveReq projTimeSheetSaveReq);

    void saveProjTimeSheetAppr(ProjTimeSheetApprSaveReq projTimeSheetApprSaveReq);

    ProjProcureResp getProjProcurement(ProjProcureGetReq projProcureGetReq);

    void saveProjProcurement(ProjProcureSaveReq projProcureSaveReq);

    void saveProjProcurementAppr(ProjProcureApprSaveReq projProcureApprSaveReq);

    ProjEmpTransResp getEmpTrans(ProjEmpTransGetReq projEmpTransGetReq);

    ProjEmpTransResp saveEmpTrans(ProjEmpTransSaveReq projEmpTransSaveReq);

    void saveEmpTransAppr(ProjEmpTransApprSaveReq projEmpTransApprSaveReq);

    ProjPlantTransResp getProjPlantTrans(ProjPlantTransGetReq projPlantTransGetReq);

    void saveProjPlantTrans(ProjPlantTransSaveReq projPlantTransSaveReq);

    void saveProjPlantTransAppr(ProjPlantTransApprSaveReq projPlantTransApprSaveReq);

    ProjMaterialTransResp getProjMaterialTrans(ProjMaterialTransGetReq projMaterialTransGetReq);

    void saveProjMaterialTrans(ProjMaterialTransSaveReq projMaterialTransSaveReq);

    void saveProjMaterialTransAppr(ProjMaterialTransApprSaveReq projMaterialTransApprSaveReq);

    ProjEstimateResp getProjEstimate(ProjEstimateGetReq projEstimateGetReq);

    void saveProjEstimate(ProjEstimateSaveReq projEstimateSaveReq);

    ProjectMaterialsResp getProjectMaterials(ProjectMaterialGetReq projectMaterialGetReq);

    void saveProjectMaterials(ProjectMaterialSaveReq projectMaterialSaveReq);

    ProjCostStaementsResp getProjCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);
    
    ProjCostStaementsResp getMultiProjCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjCostStaementsResp getProjExitCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    void saveProjCostCodes(ProjCostCodesSaveReq projCostCodesSaveReq);

    void saveProjCostStatements(ProjCostStatementsSaveReq projCostStatementsSaveReq);

    ProjectPlantsResp getProjectPlants(ProjectPlantsGetReq projectPlantsGetReq);

    void saveProjectPlants(ProjectPlantsSaveReq projectPlantsSaveReq);

    ProjReportsResp getProjReports(ProjReportsGetReq projReportsGetReq);

    void saveProjReports(ProjReportsSaveReq projReportsSaveReq);

    ProjManPowerResp getProjManPowers(ProjManpowerGetReq projManpowerGetReq);

    void saveProjManPowers(ProjManpowerSaveReq projManpowerSaveReq);

    ProjProgressResp getProjProgress(ProjProgressGetReq projProgressGetReq);

    void saveProjProgress(ProjProgressSaveReq projProgressSaveReq);

    ProjCostCodeStatusResp getProjCostCodeStatus(ProjCostCodeStatusGetReq projCostCodeStatusGetReq);

    ProjSummaryResp getProjSummary(ProjSummaryGetReq projSummaryGetReq);

    ProjStatusResp getProjStatus(ProjStatusGetReq projStatusGetReq);

    List<String> getWeekDays(List<FinancePeriodPayCyclesTO> list);

    List<String> getYears(List<FinancePeriodPayCyclesTO> list);

    ProjResourceCurveResp getResourceCurves(ProjResourceCurveGetReq projResourceCurveGetReq);

    ProjProgressClaimResp getProjProgressClaim(ProjProgressClaimGetReq projProgressClaimGetReq);

    void saveProjProgressClaim(ProjProgressClaimSaveReq projProgressClaimSaveReq);

    void saveProjProgressClaimAppr(ProjProgressClaimApprSaveReq projProgressClaimApprSaveReq);

    ProjProgressClaimePeroidResp getProjProgressClaimePeriodCycle(ProjProgressClaimGetReq projProgressClaimGetReq);

    void saveProjProgressClaimePeriodCycle(ProjProgressClaimePeroidSaveReq projProgressClaimePeroidSaveReq);

    void saveProjPayRollCycle(ProjPayRollCycleSaveReq projPayRollCycleSaveReq);

    ProjTimeSheetWeekResp getProjTimeSheetWeek(ProjTimeSheetGetReq projTimeSheetGetReq);

    void saveProjTimeSheetWeek(ProjTimeSheetWeekSaveReq projTimeSheetWeekSaveReq);

    void saveProjSows(ProjSowsSaveReq projSowsSaveReq);

    ProjCostStaementsResp getProjCostCodeStmts(ProjCostStatementsGetReq projCostStatementsGetReq);

    List<LabelKeyTO> getBudgets();

    ProjManPowerStatusResp getProjManPowerstatus(ProjManpowerGetReq projManpowerGetReq);

    ProjectPlantsStatusResp getProjectPlantsStatus(ProjectPlantsGetReq projectPlantsGetReq);

    ProjCostStatementsSummaryResp getProjCostStatusSummary(ProjCostStatementsGetReq projCostStatementsGetReq);
    
    ProjCostStatementsSummaryResp getMultiProjCostStatusSummary(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjNoteBookResp getProjNoteBook(ProjNoteBookGetReq projNoteBookGetReq);

    void saveProjNoteBook(ProjNoteBookSaveReq projNoteBookSaveReq);

    void saveProjectDefaultSettinges(ProjectDefaultSaveReq projectDefaultSaveReq);

    ProjGenCurrencyResp getProjGeneralsCurrencys(ProjGeneralsGetReq projGeneralsGetReq);

    ProjPerformenceThresholdResp getProjPerformenceThreshold(
            ProjPerformenceThresholdGetReq projPerformenceThresholdGetReq);

    void saveProjPerformenceThreshold(ProjPerformenceThresholdSaveReq projPerformenceThresholdSaveReq);

    public void deleteProjPerformenceThreshold(ProjPerfomanceDelReq projPerfomanceDelReq);

    List<String> getpayPeriodCycles();

    List<String> getMonths(List<FinancePeriodPayCyclesTO> financePeriodPayCyclesTOs);

    ProjPayRollCycleResp getprojPayRollCycle(ProjPayRollCycleGetReq projPayRollCycleGetReq);

    ProjLeaveRequestResp getProjLeaveRequest(ProjLeaveRequestGetReq projLeaveRequestGetReq);

    ProjLeaveRequestResp saveProjLeaveRequest(ProjLeaveRequestSaveReq projLeaveRequestSaveReq);

    void saveProjLeaveApproval(ProjLeaveApprSaveReq projLeaveApprSaveReq);

    void saveProjPerfomanceDefaultSettings(ProjPerfamanceDefaultSaveReq projPerfamanceDefaultSaveReq);

    ProjStatusDatesResp getProjStatusDates(ProjSettingsFilterReq projSettingsFilterReq);
    
    ProjStatusDatesResp getMultiProjStatusDates(ProjSettingsFilterReq projSettingsFilterReq);
    ProjStatusDatesResp getProjectsDatesForProgressSCurveReport(ProjSettingsFilterReq projSettingsFilterReq);
    ProgressSCurveTOResp getProgressSCurveReportData(ProgressReportGetReq progressReportGetReq);
    ProgressSCurveTOResp getProgressSCurveManpowerReportData(ProgressReportGetReq progressReportGetReq);

    void saveProjStatusDates(ProjStatusDatesSaveReq projStatusDatesSaveReq);

    List<String> getWeekDays();

    CalTO getGlobalCalendar();

    ProjCostStaementsResp getProjExitMaterialCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjCostStaementsResp getProjExitPlantCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjCostStaementsResp getProjExitManpowerCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjCostStaementsResp getProjExitServiceCostStatements(ProjCostStatementsGetReq projCostStatementsGetReq);

    ProjStatusActualResp getProjStatusActualQty(ProjStatusActualReq projStatusActualReq);

    void saveProjDurationStatus(ProjStatusDatesSaveReq projStatusDatesSaveReq);

    void saveProjStatusMileStones(ProjMileStonesDateSaveReq projMileStonesDateSaveReq);

    ProjMileStonesResp getProjStatusMileStones(ProjSettingsFilterReq projSettingsFilterReq);

    void deleteProjStatusMileStones(ProjMileStonesDateSaveReq projMileStonesDateSaveReq);

    ProjEmpTransResp findEmpTransNormalTime(ProjEmpTransGetReq empTransGetReq);

    EmpClassesResp getEmpClasses(ProjManpowerGetReq projManpowerGetReq);

    PlantClassResp getPlantClasses(ProjManpowerGetReq projManpowerGetReq);

    MaterialClassResp getMaterialGroups(ProjManpowerGetReq projManpowerGetReq);

    Map<Long, Double> getCostCodeActualQty(ProjManpowerGetReq projManpowerGetReq);

	DateWiseCostReportResp getActualCostDetails(CostReportReq costReportReq);

	DateWiseCostReportResp getPlanActualEarned(CostReportReq costReportReq);
	
	ProjPlannedValueResp getProjPlannedValue(ProjCostStatementsGetReq projCostStatementsGetReq);
	ProjectTangibleResp getTangiblesOfProjects(ProjectTangibleReq projectTangibleReq);
	ProjectTangibleResp getManpowerProductivityAnalysisReportData(ProjectTangibleReq projectTangibleReq);
	ProjectGanntChartResp getProjectsGanttChartReportData(ProjGeneralsGetReq projGeneralsGetReq);
	
	ProjBudgetResp projectBudgetApproval( ProjGeneralsGetReq projGeneralsGetReq );
	
	ProjBudgetResp projectBudgetReturn( ProjGeneralsGetReq projGeneralsGetReq );
	
	void saveProjSchofEstimates(SchofEstimatesSaveReq schofEstimatesSaveReq);
	
	ProjSchofEstimatesResp getProjSchofEstimates(SchofEstimatesGetReq schofEstimatesGetReq);
	
	void saveProjSchofRates(SchofRatesSaveReq schofRatesSaveReq);
	
	ProjSchofRatesResp getProjSchofRates(SchofRatesGetReq schofRatesGetReq);
	
	void saveProjResBudget(ResourceBudgetSaveReq resBudgetSaveReq);
	
	ProjResourceBudgetResp getProjResBudget(ResourceBudgetGetReq resBudgetGetReq);
	
	void saveSoeAppr(ProjSoeApprSaveReq projSoeApprSaveReq);

	ChangeOrderDetailsResp getProjChangeOrderDetail(ChangeOrderDetailsGetReq changeOrderDetailsGetReq);
	
	void saveProjChangeOrderDetail(ChangeOrderDetailsSaveReq changeOrderDetailsSaveReq);
}