package com.rjtech.timemanagement.constants;

import java.io.Serializable;

public class TimeSheetURLConstants implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -923474679747855169L;

    public final static String TIME_SHEET_PARH_URL = "/app/timesheet/";

    public final static String GET_TIME_SHEET_DAYS = "getTimeSheetDays";

    public final static String GET_TIME_SHEET_ONLOAD = "getTimeSheetOnload";

    public final static String GET_TIME_SHEET_ONLOAD_INDIVIDUALS = "getTimeSheetOnloadIndividuals";

    public final static String GET_CREW_TIME_SHEETS = "getCrewTimeSheets";
    public final static String GET_INDIVIDUAL_TIME_SHEETS = "getIndividualTimeSheets";

    public final static String GET_CREW_TIME_SHEET = "getCrewTimeSheet";
    public final static String GET_INDIVIDUAL_TIME_SHEET = "getIndividualTimeSheet";

    public final static String GET_CREW_TIME_SHEET_FOR_APPROVAL = "getCrewTimeSheetForApproval";
    public final static String GET_INDIVIDUAL_TIME_SHEET_FOR_APPROVAL = "getIndividualTimeSheetForApproval";

    public final static String GET_TIME_SHEET_BY_ID = "getTimeSheetById";

    public final static String GET_INDIVIDUALS_FROM_TIME_SHEET = "getIndividualsFromTimeSheet";
    public final static String GET_ALL_INDIVIDUALS_FROM_TIME_SHEET = "getAllIndividualsFromTimeSheet";

    public final static String GET_PROJ_SETTINGS_FOR_TIME_SHEET = "getProjSettingsForTimeSheet";

    // TODO: remove below GET_EMP_REG_DETAILS service
    public final static String GET_EMP_REG_DETAILS = "getEmpRegDetails";
    public final static String GET_OTHER_CREW_ATTENDACNE_FOR_TIME_SHEET = "getOtherCrewEmpAttendance";
    public final static String GET_CREW_ATTENDACNE_FOR_INDIVIDUALS = "getCrewAttendanceForIndividuals";

    public final static String COPY_TIME_SHEET_EMP_REG_DETAILS = "copyTimeSheetEmpDetails";

    public final static String ADD_EMP_REG_TO_TIME_SHEET = "addEmpRegToTimeSheet";
    //invidual
    public final static String ADD_EMP_REG_TO_TIME_SHEET_INDIVIDUALS = "addEmpRegToTimeSheetIndividual";

    public final static String GET_TIME_SHEET_EMP_DETAILS = "getTimeSheetEmpDetails";
    public final static String GET_TIME_SHEET_DETAILS = "getTimeSheetDetails";
    public final static String GET_TIME_SHEET_COSTCODE_WAGE_MAP = "timeSheetWageCodeMap";

    public final static String GET_CREW_TIME_SHEET_DETAILS_FOR_APPROVAL = "getCrewTimeSheetDetailForApproval";
    public final static String GET_INDIVIDUAL_TIME_SHEET_DETAILS_FOR_APPROVAL = "getIndividualTimeSheetDetailForApproval";
    public final static String GET_CREW_TIME_SHEET_DETAILS_FOR_SUBMISSION = "getCrewTimeSheetDetailsForSubmission";
    public final static String GET_INDIVIDUAL_TIME_SHEET_DETAILS_FOR_SUBMISSION = "getIndividualTimeSheetDetailsForSubmission";
    public final static String GET_COPY_CREW_TIME_SHEET_DETAILS_FOR_SUBMISSION = "getCopyCrewTimeSheetDetailsForSubmission";

    public final static String SAVE_CREW_TIME_SHEET_DETAILS = "saveCrewTimeSheetDetails";
    public final static String SAVE_APPROVE_CREW_TIME_SHEET_DETAILS = "saveApproveCrewTimeSheetDetails";
    public final static String SAVE_INDIVIDUAL_TIME_SHEET_DETAILS = "saveIndividualTimeSheetDetails";
    public final static String SUBMIT_CREW_TIME_SHEET_DETAILS = "submitCrewTimeSheetDetails";
    public final static String SUBMIT_INDIVIDUAL_TIME_SHEET_DETAILS = "submitIndividualTimeSheetDetails";
    public final static String APPROVE_CREW_TIME_SHEET_DETAILS = "approveCrewTimeSheetDetails";
    public final static String APPROVE_INDIVIDUAL_TIME_SHEET_DETAILS = "approveIndividualTimeSheetDetails";

    public final static String GET_TIME_SHEET_EMP_TASKS = "getTimeSheeteEmpTasks";
    public final static String SAVE_TIME_SHEET_EMP_TASKS = "saveTimeSheeteEmpTasks";

    public final static String GET_TIMEE_SHEET_EMP_DETAILS = "getTimeSheetEmpDetails";
    public final static String SAVE_TIMEE_SHEET_EMP_DETAILS = "saveTimeSheetEmpDetails";

    public final static String GET_TIMEE_SHEET_EMP_EXPENSES = "getTimeSheetEmpExpenses";
    public final static String SAVE_TIMEE_SHEET_EMP_EXPENSES = "saveTimeSheetEmpExpenses";

    public final static String GET_PROJ_SETTINGS_TIMESHEET_DETAILS = "getProjSettingsTimeSheetDetails";

    public final static String TIMESHEET_EMP_ID = "empId";
    public final static String TIMESHEET_WAGEID = "wageId";

    public final static String TIMESHEET_COSTCODE_ID = "costCodeId";
    public final static String TIME_SHEET_STATUS = "status";

    public static final String GET_TIMESHEET_DAILY_REPORT = "getTimeSheetDailyReport";

    public static final String GET_TIMESHEET_APPROVESTATUS_REPORT = "getTimeSheetApprStatusReport";

    public static final String GET_TIMESHEET_REQ_USERS = "getTimeSheetReqUsers";

    public static final String GET_MANPOWER_PERIODICAL_REPORT = "getManpowerPeriodicalReport";

    public static final String GET_MANPOWER_DATE_WISE_HRS_REPORT = "getManpowerDateWiseHrsReport";

    public static final String GET_MANPOWER_COST_CODE_WISE_REPORT = "getManpowerCostCodeWiseReport";

    public static final String GET_MANPOWER_ACTUAL_STANDARD_REPORT = "getManpowerActualStandardReport";

    public static final String GET_MANPOWER_IDLE_HRS_REPORT = "getManpowerIdleHrsReport";

    public static final String GET_MANPOWER_CURRENT_EMPLOYEE_REPORT = "getManpowerCurrentEmployeeReport";

    public static final String GET_MANPOWER_ACTUAL_EARNED_REPORT = "getManpowerPlanActualEarnedReport";

    public static final String GET_PROJ_EARNED_VALUES = "getProjEarnedValues";
    public static final String GET_CREATED_TIME_SHEETS = "getCreatedTimeSheets";
    public static final String GET_SUBMITTED_TIME_SHEETS = "getSubmittedTimeSheets";

}
