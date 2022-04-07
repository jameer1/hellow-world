package com.rjtech.dashboards.constants;

import java.io.Serializable;

public class DashBoardUrlConstants implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4316005105217478254L;

    public final static String DASHBOARD_URL_CONSTANTS = "/app/dashboards/";

    /* Master DashBoards*/
    public final static String GET_MASTER_DASH_BOARD = "getMasterDashBoard";

    /*=============DASH BOARD PERFORMANCE ========================*/
    public final static String GET_PROGRESS_VARIANCE_AND_PERFORMANCE_INDICES = "getProgressVariancePerformanceIndices";
    public final static String GET_COST_SCHEDULE_PERFORMANCE = "getCostSchedulePerformance";
    public final static String GET_PERFORMANCE_COST_SCHEDULE_VARIANCE_UNITS = "getPerformanceCostsheduleVarianceUnits";
    public final static String GET_COST_SCHEDULE_VARIANCE_PERCENTAGE = "getCostScheduleVariancePercentage";
    public final static String GET_COST_SCHEDULE_PERFORMANCE_INDICES = "getCostSchedulePerformanceIndices";
    public final static String GET_TO_COMPLETE_PERFORMANCE_INDEX = "getToCompletePerformanceIndex";

    /*==============PROGRESS DASHBOARDS========================*/
    public final static String GET_PLAN_VS_ACTUAL_PROGRESS = "getPlanVsActualProgress";

    /*==============TIME DASHBOARDS ========================*/
    public final static String GET_COST_SCHEDULE_VARIANCE_BUBBLE_CHART = "getCostSchedulevarianceBubbleChart";
    public final static String GET_CURRENT_DATE_PROGRESS_PERCENTAGE = "getCurrentDateProgressPercentage";
    public final static String GET_TIME_COST_SCHEDULE_VARIANCE_UNITS = "getTimeCostScheduleVarianceUnits";
    public final static String GET_PROJ_GRANTT_CHART = "getProjectGranttChart";

    /*==============BUDGET DASHBOARDS ========================*/
    public final static String GET_BUDGET_BY_COUNTRY = "getBudgetByCountry";
    public final static String GET_BUDGET_BY_PROVINCE = "getBudgetByProvince";
    public final static String GET_BUDGET_BY_PROJECT = "getBudgetByProject";
    public final static String GET_BUDGET_BY_PROJECTMANAGER = "getBudgetByProjectManager";

    /*==============COST DASHBOARDS ========================*/
    public final static String GET_COST_HEALTH_CHECK = "getCostHealthCheck";
    public final static String GET_CV_AND_SV_FOR_COST_BUBBLE_CHART = "getCVandSVforCostBubbleChart";
    public final static String GET_ORIGINAL_AND_ESTIMATE_AT_COMPLETION_COST = "getOriginalEstimateAtCompletionCost";
    public final static String GET_PLAN_VS_ACTUAL_VS_EARNED_COST = "getPlanActualEarnedCost";
    public final static String GET_COST_WORKSHEET = "getCostWorksheet";

    /*==============LABOUR DASHBOARDS========================*/
    public final static String GET_LABOUR_HOURS_HEALTH_CHECK = "getLabourHealthCheck";
    public final static String GET_CV_AND_SV_FOR_LABOUR_HOURS_BUBBLE_CHART = "getCVandSVforLabourBubbleChart";
    public final static String GET_ORIGINAL_AND_ESTIMATE_AT_COMPLETION_MANHOURS = "getOriginalEstimateAtCompletionManhrs";
    public final static String GET_PLAN_VS_ACTUAL_VS_EARNED_DIRECT_MANHOURS = "getPlanActualEarnedManhrs";

    /*==============ACTUAL COST DASHBOARDS ========================*/
    public final static String GET_ACTUAL_COST_BY_COUNTRY = "getActualCostByCountry";
    public final static String GET_ACTUAL_COST_BY_PROVINCE = "getActualCostByProvince";
    public final static String GET_ACTUAL_COST_BY_PROJECT = "getActualCostByProject";
    public final static String GET_ACTUAL_COST_BY_PROJECTMANAGER = "getActualCostByProjectManager";

    /*==============EARNED VALUE DASHBOARDS ========================*/
    public final static String GET_EARNED_VALUE_BY_COUNTRY = "getEarnedValueByCountry";
    public final static String GET_EARNED_VALUE_BY_PROVINCE = "getEarnedValueByProvince";
    public final static String GET_EARNED_VALUE_BY_PROJECT = "getEarnedValueByProject";
    public final static String GET_EARNED_VALUE_BY_PROJECTMANAGER = "getEarnedValueByProjectManager";

    /*==============REMAINING_BUDGET DASHBOARDS ========================*/
    public final static String GET_REMAINING_BUDGET_BY_COUNTRY = "getRemainingBudgetByCountry";
    public final static String GET_REMAINING_BUDGET_BY_PROVINCE = "getRemainingBudgetByProvince";
    public final static String GET_REMAINING_BUDGET_BY_PROJECT = "getRemainingBudgetByProject";
    public final static String GET_REMAINING_BUDGET_BY_PROJECTMANAGER = "getRemainingBudgetByProjectManager";

    /*==============ESTIMATE_TO_COMPLETE DASHBOARDS ========================*/
    public final static String GET_ESTIMATE_TO_COMPLETE_BY_COUNTRY = "getEstimateToCompleteByCountry";
    public final static String GET_ESTIMATE_TO_COMPLETE_BY_PROVINCE = "getEstimateToCompleteByProvince";
    public final static String GET_ESTIMATE_TO_COMPLETE_BY_PROJECT = "getEstimateToCompleteByProject";
    public final static String GET_ESTIMATE_TO_COMPLETE_BY_PROJECTMANAGER = "getEstimateToCompleteByProjectManager";

    /*==============ESTIAMTE_AT_COMLETION DASHBOARDS ========================*/
    public final static String GET_ESTIAMTE_AT_COMLETION_BY_COUNTRY = "getEstimateAtCompletionByCountry";
    public final static String GET_ESTIAMTE_AT_COMLETION_BY_PROVINCE = "getEstimateAtCompletionByProvince";
    public final static String GET_ESTIAMTE_AT_COMLETION_BY_PROJECT = "getEstimateAtCompletionByProject";
    public final static String GET_ESTIAMTE_AT_COMLETION_BY_PROJECTMANAGER = "getEstimateAtCompletionByProjectManager";
}
