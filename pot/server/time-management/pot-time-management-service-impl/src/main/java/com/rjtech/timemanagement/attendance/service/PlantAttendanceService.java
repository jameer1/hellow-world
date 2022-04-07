package com.rjtech.timemanagement.attendance.service;

import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.timemanagement.attendance.req.DailyAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.attendance.req.PlantAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.PlantAttendanceSaveReq;
import com.rjtech.timemanagement.attendance.resp.AttendancePlantRegResp;
import com.rjtech.timemanagement.attendance.resp.PlantAttendanceOnloadResp;
import com.rjtech.timemanagement.attendance.resp.PlantAttendanceResp;
import com.rjtech.timemanagement.attendance.resp.PlantAttendanceSheetResp;

public interface PlantAttendanceService {

    public PlantAttendanceOnloadResp getPlantAttendance(PlantAttendanceGetReq plantAttendanceGetReq);

    public AttendancePlantRegResp getNonAttendancePlantRegDetails(PlantAttendanceGetReq plantAttendanceGetReq);

    public AttendancePlantRegResp copyAttendancePlantDetails(PlantAttendanceGetReq plantAttendanceGetReq);

    public AttendancePlantRegResp getPlantAttendanceSheets(PlantAttendanceGetReq plantAttendanceGetReq);

    public PlantAttendanceResp getPlantAttendanceRecords(PlantAttendanceGetReq plantAttendanceGetReq);

    public LabelKeyTO savePlantAttendanceRecords(PlantAttendanceSaveReq plantAttendanceSaveReq);

    public PlantAttendanceResp addPlantToAttendanceRecord(PlantAttendanceSaveReq plantAttendanceSaveReq,
            PlantAttendanceGetReq plantAttendanceGetReq);

    List<LabelKeyTO> getDailyPlantAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq);

    List<LabelKeyTO> getDailyPlantAttendanceReportBtwnDates(DailyAttendanceGetReq dailyAttendanceGetReq);

    List<LabelKeyTO> getPlantDailyResourceStatus(DailyAttendanceGetReq dailyAttendanceGetReq);
    
    public PlantAttendanceSheetResp getPlantAttendanceRecordSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
}
