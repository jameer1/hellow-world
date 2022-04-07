package com.rjtech.timemanagement.attendance.service;

import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.timemanagement.attendance.req.DailyAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.EmpAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.EmpAttendanceSaveReq;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.attendance.resp.AttendanceEmpRegResp;
import com.rjtech.timemanagement.attendance.resp.EmpAttendanceOnloadResp;
import com.rjtech.timemanagement.attendance.resp.EmpAttendanceResp;
import com.rjtech.timemanagement.attendance.resp.EmpAttendanceSheetResp;
import com.rjtech.timemanagement.attendance.resp.PlantAttendanceSheetResp;

public interface EmpAttendanceService {

    public EmpAttendanceOnloadResp getEmpAttendance(EmpAttendanceGetReq empAttendenceGetReq);

    public AttendanceEmpRegResp getNonAttendanceEmpRegDetails(EmpAttendanceGetReq empAttendanceGetReq);

    public AttendanceEmpRegResp copyAttendanceEmpDetails(EmpAttendanceGetReq empAttendanceGetReq);

    public AttendanceEmpRegResp getEmpAttendanceSheets(EmpAttendanceGetReq empAttendenceGetReq);

    public EmpAttendanceResp getEmpAttendanceRecords(EmpAttendanceGetReq empAttendenceGetReq);

    public LabelKeyTO saveEmpAttendanceRecords(EmpAttendanceSaveReq empAttendenceSaveReq);

    public EmpAttendanceOnloadResp getAttendanceDays(EmpAttendanceGetReq empAttendanceGetReq);

    public EmpAttendanceResp addEmpToAttendanceRecord(EmpAttendanceSaveReq empAttendanceSaveReq,
            EmpAttendanceGetReq empAttendanceGetReq);

    Map<String, Boolean> enableAttendanceDays(long projId, long crewId, String type);

    List<LabelKeyTO> getDailyEmpAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq);

    List<LabelKeyTO> getEmpAttendanceRecordsByDate(DailyAttendanceGetReq dailyAttendanceGetReq);

    List<LabelKeyTO> getDailyEmpAttendanceReportBtwnDates(DailyAttendanceGetReq dailyAttendanceGetReq);

    List<LabelKeyTO> getEmpDailyResourceStatus(DailyAttendanceGetReq dailyAttendanceGetReq);

    public EmpAttendanceSheetResp getEmployeeAttendanceRecordSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
}
