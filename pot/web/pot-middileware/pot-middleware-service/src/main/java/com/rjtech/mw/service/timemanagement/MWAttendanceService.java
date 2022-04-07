package com.rjtech.mw.service.timemanagement;

import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.timemanagement.attendance.req.DailyAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.EmpAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.EmpAttendanceSaveReq;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.attendance.req.PlantAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.PlantAttendanceSaveReq;
import com.rjtech.timemanagement.attendance.resp.AttendanceEmpRegResp;
import com.rjtech.timemanagement.attendance.resp.AttendancePlantRegResp;
import com.rjtech.timemanagement.attendance.resp.EmpAttendanceOnloadResp;
import com.rjtech.timemanagement.attendance.resp.EmpAttendanceResp;
import com.rjtech.timemanagement.attendance.resp.EmpAttendanceSheetResp;
import com.rjtech.timemanagement.attendance.resp.PlantAttendanceOnloadResp;
import com.rjtech.timemanagement.attendance.resp.PlantAttendanceResp;
import com.rjtech.timemanagement.attendance.resp.PlantAttendanceSheetResp;

public interface MWAttendanceService {

    public EmpAttendanceOnloadResp getEmpAttendance(EmpAttendanceGetReq empAttendanceGetReq);

    public AttendanceEmpRegResp getNonAttendanceEmpRegDetails(EmpAttendanceGetReq empAttendanceGetReq);

    public AttendanceEmpRegResp getEmpAttendanceSheets(EmpAttendanceGetReq empAttendanceGetReq);

    public EmpAttendanceResp getEmpAttendanceRecords(EmpAttendanceGetReq empAttendanceGetReq);

    public EmpAttendanceResp saveEmpAttendanceRecords(EmpAttendanceSaveReq empAttendenceSaveReq);

    public PlantAttendanceOnloadResp getPlantAttendance(PlantAttendanceGetReq plantAttendanceGetReq);

    public AttendancePlantRegResp getNonAttendancePlantRegDetails(PlantAttendanceGetReq plantAttendanceGetReq);

    public AttendancePlantRegResp getPlantAttendanceSheets(PlantAttendanceGetReq plantAttendanceGetReq);

    public PlantAttendanceResp getPlantAttendanceRecords(PlantAttendanceGetReq plantAttendanceGetReq);

    public PlantAttendanceResp savePlantAttendanceRecords(PlantAttendanceSaveReq plantAttendanceSaveReq);

    public EmpAttendanceResp addEmpToAttendanceRecord(EmpAttendanceSaveReq empAttendenceSaveReq);

    public PlantAttendanceResp addPlantToAttendanceRecord(PlantAttendanceSaveReq plantAttendanceSaveReq);

    public AttendanceEmpRegResp copyAttendanceEmpDetails(EmpAttendanceGetReq empAttendanceGetReq);

    public AttendancePlantRegResp copyAttendancePlantDetails(PlantAttendanceGetReq plantAttendanceGetReq);

    public EmpAttendanceOnloadResp getAttendanceDays(EmpAttendanceGetReq empAttendanceGetReq);
    
    public EmpAttendanceSheetResp getEmployeeAttendanceRecordSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
    public PlantAttendanceSheetResp getPlantAttendanceRecordSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
    /* Report Changes */
    public List<LabelKeyTO> getDailyEmpAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq);
    
    public List<LabelKeyTO> getEmpAttendanceRecordsByDate(DailyAttendanceGetReq dailyAttendanceGetReq);
    
    public List<LabelKeyTO> getDailyPlantAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq);
    
    public List<LabelKeyTO> getDailyEmpAttendanceReportBtwnDates(DailyAttendanceGetReq dailyAttendanceGetReq);
    
    public List<LabelKeyTO> getDailyPlantAttendanceReportBtwnDates(DailyAttendanceGetReq dailyAttendanceGetReq);
    
    public List<LabelKeyTO> getEmpDailyResourceStatus(DailyAttendanceGetReq dailyAttendanceGetReq);
    
    public List<LabelKeyTO> getPlantDailyResourceStatus(DailyAttendanceGetReq dailyAttendanceGetReq);
    
}
