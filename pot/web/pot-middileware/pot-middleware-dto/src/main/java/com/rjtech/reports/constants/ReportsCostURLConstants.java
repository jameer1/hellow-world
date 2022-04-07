package com.rjtech.reports.constants;

import java.io.Serializable;

public class ReportsCostURLConstants implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8829565164077299318L;

    public final static String REPORTS_PARH_URL = "/app/costreports/";

    /*==============COSTCODE REPORTS ========================*/
    public final static String GET_COST_CODE_DATE_WISE_ACTUAL_COST_REPORT = "getDatewiseActualCostDetails";
    
    public final static String GET_PERIODICAL_WISE_REPORT = "getPeriodicalWiseReport";
    
    public final static String GET_DATE_WISE_PLANNED_ACTUAL_EARNED_REPORT = "getDateWisePlanActualEarned";
    
    public final static String GET_COST_CODE_WISE_REPORT = "getCostCodeWiseReport";
    
    public final static String GET_COST_CODE_BUDGET_REPORT = "getCostCodeBudgetReport";
    
    public final static String GET_DATE_PROJ_WISE_ACTUAL_REPORT = "getDateProjWiseActualReport";

}
