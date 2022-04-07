package com.rjtech.mw.service.impl.timemanagement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.timemanagement.MWAttendanceService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;
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
import com.rjtech.timemanagement.constants.AttendanceURLConstants;

@Service(value = "mwAttendenceService")
@RJSService(modulecode = "mwAttendenceService")
@Transactional
public class MWAttendanceServiceImpl extends RestConfigServiceImpl implements MWAttendanceService {
	
	private static final Logger log = LoggerFactory.getLogger(MWAttendanceServiceImpl.class);

    public EmpAttendanceOnloadResp getEmpAttendance(EmpAttendanceGetReq empAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(empAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_EMP_ATTENDANCE);
        return AppUtils.fromJson(strResponse.getBody(), EmpAttendanceOnloadResp.class);
    }

    public AttendanceEmpRegResp getNonAttendanceEmpRegDetails(EmpAttendanceGetReq empAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(empAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_NON_ATTENDANCE_EMP_REG_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), AttendanceEmpRegResp.class);
    }

    public EmpAttendanceOnloadResp getAttendanceDays(EmpAttendanceGetReq empAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(empAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_ATTENDANCE_DAYS);
        return AppUtils.fromJson(strResponse.getBody(), EmpAttendanceOnloadResp.class);
    }

    public AttendanceEmpRegResp getEmpAttendanceSheets(EmpAttendanceGetReq empAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(empAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_EMP_ATTENDANCE_SHEETS);
        return AppUtils.fromJson(strResponse.getBody(), AttendanceEmpRegResp.class);
    }

    public EmpAttendanceResp getEmpAttendanceRecords(EmpAttendanceGetReq empAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(empAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_EMP_ATTENDANCE_RECORDS);
        return AppUtils.fromJson(strResponse.getBody(), EmpAttendanceResp.class);
    }

    public EmpAttendanceResp saveEmpAttendanceRecords(EmpAttendanceSaveReq empAttendenceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(empAttendenceSaveReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.SAVE_EMP_ATTENDANCE_RECORD);
        return AppUtils.fromJson(strResponse.getBody(), EmpAttendanceResp.class);
    }

    public PlantAttendanceOnloadResp getPlantAttendance(PlantAttendanceGetReq plantAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_PLANT_ATTENDANCE);
        return AppUtils.fromJson(strResponse.getBody(), PlantAttendanceOnloadResp.class);
    }

    public AttendancePlantRegResp getNonAttendancePlantRegDetails(PlantAttendanceGetReq plantAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL
                        + AttendanceURLConstants.GET_NON_ATTENDANCE_PLANT_REG_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), AttendancePlantRegResp.class);
    }

    public AttendancePlantRegResp getPlantAttendanceSheets(PlantAttendanceGetReq plantAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_PLANT_ATTENDANCE_SHEETS);
        return AppUtils.fromJson(strResponse.getBody(), AttendancePlantRegResp.class);
    }

    public PlantAttendanceResp getPlantAttendanceRecords(PlantAttendanceGetReq plantAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_PLANT_ATTENDANCE_RECORDS);
        return AppUtils.fromJson(strResponse.getBody(), PlantAttendanceResp.class);
    }

    public PlantAttendanceResp savePlantAttendanceRecords(PlantAttendanceSaveReq plantAttendanceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantAttendanceSaveReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.SAVE_PLANT_ATTENDANCE_RECORD);
        return AppUtils.fromJson(strResponse.getBody(), PlantAttendanceResp.class);
    }

    public EmpAttendanceResp addEmpToAttendanceRecord(EmpAttendanceSaveReq empAttendenceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(empAttendenceSaveReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.ADD_EMP_TO_ATTENDANCE_RECORD);
        return AppUtils.fromJson(strResponse.getBody(), EmpAttendanceResp.class);
    }

    public PlantAttendanceResp addPlantToAttendanceRecord(PlantAttendanceSaveReq plantAttendanceSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantAttendanceSaveReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.ADD_PLANT_TO_ATTENDANCE_RECORD);
        return AppUtils.fromJson(strResponse.getBody(), PlantAttendanceResp.class);
    }

    public AttendanceEmpRegResp copyAttendanceEmpDetails(EmpAttendanceGetReq empAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(empAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.COPY_ATTENDANCE_EMP_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), AttendanceEmpRegResp.class);
    }

    public AttendancePlantRegResp copyAttendancePlantDetails(PlantAttendanceGetReq plantAttendanceGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(plantAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.COPY_ATTENDANCE_PLANT_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), AttendancePlantRegResp.class);
    }
    
    public EmpAttendanceSheetResp getEmployeeAttendanceRecordSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(employeeAttendanceRecordSheetsSearchReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_EMPLOYEE_ATTENDANCE_RECORD_SHEETS);
        return AppUtils.fromJson(strResponse.getBody(), EmpAttendanceSheetResp.class);
    }
    
    public PlantAttendanceSheetResp getPlantAttendanceRecordSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(employeeAttendanceRecordSheetsSearchReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_PLANT_ATTENDANCE_RECORD_SHEETS);
        return AppUtils.fromJson(strResponse.getBody(), PlantAttendanceSheetResp.class);
    }
    /* Report Changes */
    public List<LabelKeyTO> getDailyEmpAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq) {
    	ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(dailyAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_DAILY_EMP_ATTENDANCE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), List.class);
    }

    public List<LabelKeyTO> getEmpAttendanceRecordsByDate(DailyAttendanceGetReq dailyAttendanceGetReq) {
    	ResponseEntity<String> strResponse = null;
    	strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(dailyAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_EMP_ATTENDANCE_RECORDS_BY_DATE);
    	return AppUtils.fromJson(strResponse.getBody(), List.class);
    }

	public List<LabelKeyTO> getDailyPlantAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(dailyAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_DAILY_PLANT_ATTENDANCE_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}
	
	public List<LabelKeyTO> getDailyEmpAttendanceReportBtwnDates(DailyAttendanceGetReq dailyAttendanceGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(dailyAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_DAILY_EMP_ATTENDANCE_REPORT_BTWN_DATES);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}
	
	public List<LabelKeyTO> getDailyPlantAttendanceReportBtwnDates(DailyAttendanceGetReq dailyAttendanceGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(dailyAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_DAILY_PLANT_ATTENDANCE_REPORT_BTWN_DATES);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
    }
	
	public List<LabelKeyTO> getEmpDailyResourceStatus(DailyAttendanceGetReq dailyAttendanceGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(dailyAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_EMP_DAILY_RESOURCE_STATUS);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
    }

	public List<LabelKeyTO> getPlantDailyResourceStatus(DailyAttendanceGetReq dailyAttendanceGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(dailyAttendanceGetReq),
                AttendanceURLConstants.ATTENDANCE_PARH_URL + AttendanceURLConstants.GET_PLANT_DAILY_RESOURCE_STATUS);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}
}
