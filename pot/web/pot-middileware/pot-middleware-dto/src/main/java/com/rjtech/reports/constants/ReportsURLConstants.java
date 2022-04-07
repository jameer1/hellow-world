package com.rjtech.reports.constants;

import java.io.Serializable;

public class ReportsURLConstants implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8829565164077299317L;

    public final static String REPORTS_PARH_URL = "/app/reports/";

    /*==============ATTENDANCE REPORTS ========================*/
    public final static String GET_DAILY_RESOURCE_ATTENDANCE_REPORT = "getDailyResourceAttendanceReport";
    public final static String GET_DAILY_EMP_ATTENDANCE_REPORT = "getDailyEmpAttendanceReport";
    public final static String GET_DAILY_PLANT_ATTENDANCE_REPORT = "getDailyPlantAttendanceReport";
    public final static String GET_MONTHLY_EMP_ATTENDANCE_REPORT = "getMonthlyEmpAttendanceReport";
    public final static String GET_MONTHLY_PLANT_ATTENDANCE_REPORT = "getMonthlyPlantAttendanceReport";
    public final static String GET_CREW_WISE_ATTENDANCE_REPORT = "getCrewWiseAttendanceReport";

    /*==============TIMESHEET REPORTS ========================*/
    public final static String GET_TIMESHEET_DAILY_REPORT = "getTimeSheetDailyReport";
    public final static String GET_TIMESHEET_APPROVESTATUS_REPORT = "getTimeSheetApprStatusReport";
    public final static String GET_TIMESHEET_REQ_USERS = "getTimeSheetReqUsers";

    /*==============PLANT REPORTS ========================*/
    public final static String GET_PLANT_DATE_WISE_ACTUAL_HRS_REPORT = "getPlantDateWiseActualHrsReport";
    public final static String GET_PLANT_PERIODICAL_ACTUAL_HOURS_REPORT = "getPlantPeriodicalActualHrsReport";
    public final static String GET_PLANT_UTILISATION_RECORDS_REPORT = "getPlantUtilisationRecordsReport";
    public final static String GET_PLANT_COST_CODE_WISE_REPORT = "getPlantCostCodeWiseReport";
    public final static String GET_CURRENT_PLANTS_REPORT = "getCurrentPlantsReport";
    public final static String GET_PLANT_IDLE_HOURS_REPORT = "getPlantIdleHoursReport";

    /*==============MANPOWER REPORTS ========================*/
    public final static String GET_MANPOWER_DATE_WISE_HRS_REPORT = "getManpowerDateWiseHrsReport";
    public final static String GET_MANPOWER_GENDER_STATUSTICS_REPORT = "getManpowerGenderStatisticsReport";
    public final static String GET_MANPOWER_IDLE_HRS_REPORT = "getManpowerIdleHrsReport";
    public final static String GET_MANPOWER_PERIODICAL_REPORT = "getManpowerPeriodicalReport";
    public final static String GET_MANPOWER_COST_CODE_WISE_REPORT = "getManpowerCostCodeWiseReport";
    public final static String GET_MANPOWER_ACTUAL_STANDARD_REPORT = "getManpowerActualStandardReport";
    public final static String GET_MANPOWER_PERIODICAL_MOBILISATION_HRS_REPORT = "getManpowerPeriodicalMobilisationReport";
    public final static String GET_MANPOWER_CURRENT_EMPLOYEE_REPORT = "getManpowerCurrentEmployeeReport";
    public final static String GET_MANPOWER_PLAN_ACTUAL_EARNED_REPORT = "getManpowerPlanActualEarnedReport";

    /*==============WORKDIARY REPORTS ========================*/
    public final static String GET_WORKDAIRY_DAILY_MANPOWER_REPORT = "getWorkDairyDailyManpowerReport";
    public final static String GET_WORKDAIRY_DAILY_MATERIAL_REPORT = "getWorkDairyDailyMaterialReport";
    public final static String GET_WORKDAIRY_DAILY_PLANT_REPORT = "getWorkDairyDailyPlantReport";
    public final static String GET_WORKDAIRY_DAILY_PROGRESS_REPORT = "getWorkDairyDailyProgressReport";
    public final static String GET_WORKDAIRY_APPROVESTATUS_REPORT = "getWorkDairyApprStatusReport";

    /*==============PROGRESS REPORTS ========================*/
    public final static String GET_PERIODICAL_PROGRESS_REPORT = "getPeriodicalRecordsReport";
    public final static String GET_DATE_WISE_PROGRESS_REPORT = "getDateWiseProgressRecordsReport";
    public final static String GET_PLANNED_VS_ACTUAL_PROGRESS_REPORT = "getPlannedVsActualProgressDetails";
    public final static String GET_PROGRESS_CLAIM_REPORT = "getProgressClaimRecordsReport";
    public final static String GET_PROJECTS_DATES_FOR_PROGRESS_S_CURVE_REPORT = "getProjectsDatesForProgressSCurveReport";
    public final static String GET_PROGRESS_S_CURVE_REPORT_DATA = "getProgressSCurveReportData";
    public final static String GET_PROGRESS_S_CURVE_MANPOWER_REPORT_DATA = "getProgressSCurveManpowerReportData";

    /*==============MATERIAL REPORTS ========================*/
    public final static String GET_MATERIAL_DELIVERY_SUPPLY_REPORT = "getMaterialDeliverySupplyReport";
    public final static String GET_MATERIAL_DAILY_ISSUE_REPORT = "getMaterialDailyIssueReport";
    public final static String GET_MATERIAL_STOCK_BAL_IN_TRANSIT_REPORT = "getMaterialStockBalInTransitReport";
    public final static String GET_MATERIAL_STOCK_PILES_REPORT = "getMaterialStockPilesReport";
    public final static String GET_MATERIAL_DATE_WISE_CONSU_REPORT = "getMaterialDateWiseConsuReport";
    public final static String GET_MATERIAL_PERIODICAL_CONSU_REPORT = "getMaterialPeriodicalConsuReport";
    public final static String GET_MATERIAL_INVENTORY_REPORT = "getMaterialInventoryReport";

    /*==============COSTCODE REPORTS ========================*/
    public final static String GET_COST_DATE_WISE_ACTUAL_REPORT = "getCostDateWiseActualReport";
    public final static String GET_COST_DATE_WISE_PLANNED_REPORT = "getCostDateWisePlannedReport";
    public final static String GET_COST_PERIODICAL_PLANNED_REPORT = "getCostPeriodicalPlannedReport";
    public final static String GET_COST_CODE_VARIENCE_REPORT = "getCostCodeVarienceReport";
    public final static String GET_COST_ITEM_BUDGET_REPORT = "getCostItemBudgetReport";
    public final static String GET_COST_ITEM_WISE_PLAN_VS_ACTUAL_REPORT = "getCostItemWisePlanVsActualReport";
    public final static String GET_COST_PERFORMANCE_INDEX_REPORT = "getCostPerformanceIndexReport";

    public final static String GET_COST_CODE_DATE_WISE_ACTUAL_COST_REPORT = "getDatewiseActualCostDetails";
    public final static String GET_COST_CODE_PERIODICAL_PLANT_ACTUAL_REPORT = "getCostCodePeriodicalPlantActualReport";
    public final static String GET_COST_CODE_DATE_WISE_PLANT_ACTUAL_REPORT = "getDatewisePlantActualCostDetails";
    public final static String GET_COST_CODE_COST_SCHEDULE_VARIANCE_REPORT = "getCostCodeCostScheduleVarianceReport";
    public final static String GET_COST_CODE_COST_SCHEDULE_PERFORMANCE_INDEX_REPORT = "getCostSchedulePerformanceIndexReport";
    public final static String GET_COST_CODE_BUDGET_REPORT = "getCostCodeBudgetReport";
    public final static String GET_COST_CODE_ITEM_WISE_PLANT_ACTUAL_REPORT = "getCostCodeItemWisePlantActualReport";

}
