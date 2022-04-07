package com.rjtech.timemanagement.constants;

import java.io.Serializable;

public class AttendanceURLConstants implements Serializable {

    private static final long serialVersionUID = -923474679747855169L;

    public final static String ATTENDANCE_PARH_URL = "/app/attendance/";
    public final static String GET_EMP_ATTENDANCE = "getEmpAttendance";
    public final static String GET_ATTENDANCE_DAYS = "getAttendanceDays";
    public final static String GET_EMP_ATTENDANCE_SHEETS = "getEmpAttendanceSheets";
    public final static String GET_EMP_ATTENDANCE_RECORDS = "getEmpAttendanceRecords";
    public final static String SAVE_EMP_ATTENDANCE_RECORD = "saveEmpAttendanceRecords";
    public final static String GET_PLANT_ATTENDANCE = "getPlantAttendance";

    public final static String GET_PLANT_ATTENDANCE_SHEETS = "getPlantAttendanceSheets";
    public final static String GET_PLANT_ATTENDANCE_RECORDS = "getPlantAttendanceRecords";
    public final static String SAVE_PLANT_ATTENDANCE_RECORD = "savePlantAttendanceRecords";

    public final static String ADD_EMP_TO_ATTENDANCE_RECORD = "addEmpToAttendanceRecord";
    public final static String ADD_PLANT_TO_ATTENDANCE_RECORD = "addPlantToAttendanceRecord";

    public final static String COPY_ATTENDANCE_EMP_DETAILS = "copyAttendanceEmpDetails";
    public final static String COPY_ATTENDANCE_PLANT_DETAILS = "copyAttendancePlantDetails";

    public final static String GET_NON_ATTENDANCE_EMP_REG_DETAILS = "getNonAttendanceEmpRegDetails";
    public final static String GET_NON_ATTENDANCE_PLANT_REG_DETAILS = "getNonAttendancePlantRegDetails";

    public final static String SAVE_ATTENDANCE_NOTIFICATIONS = "saveAttendanceNotifications";

    // Emp Reports
    public static final String GET_DAILY_EMP_ATTENDANCE_REPORT = "getDailyEmpAttendanceReport";
    public static final String GET_EMP_ATTENDANCE_RECORDS_BY_DATE = "getEmpAttendanceRecordsByDate";
    public static final String GET_DAILY_EMP_ATTENDANCE_REPORT_BTWN_DATES = "getDailyEmpAttendanceReportBtwnDates";
    public static final String GET_EMP_DAILY_RESOURCE_STATUS = "getEmpDailyResourceStatus";
    
    // Plant Reports
    public static final String GET_DAILY_PLANT_ATTENDANCE_REPORT = "getDailyPlantAttendanceReport";
    public static final String GET_DAILY_PLANT_ATTENDANCE_REPORT_BTWN_DATES = "getDailyPlantAttendanceReportBtwnDates";
    public static final String GET_PLANT_DAILY_RESOURCE_STATUS = "getPlantDailyResourceStatus";
    
    public static final String GET_EMPLOYEE_ATTENDANCE_RECORD_SHEETS = "getEmployeeAttendanceRecordSheets";
    public static final String GET_PLANT_ATTENDANCE_RECORD_SHEETS = "getPlantAttendanceRecordSheets";

}
