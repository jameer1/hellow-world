package com.rjtech.mw.controller.timemanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.common.constants.EmpLeaveType;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.mw.service.timemanagement.MWAttendanceService;
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

@RestController
@RequestMapping(AttendanceURLConstants.ATTENDANCE_PARH_URL)
public class MWAttendanceController {
	
	private static final Logger log = LoggerFactory.getLogger(MWAttendanceController.class);

    @Autowired
    private MWAttendanceService mwAttendenceService;

    @RequestMapping(value = AttendanceURLConstants.GET_EMP_ATTENDANCE, method = RequestMethod.POST)
    public ResponseEntity<EmpAttendanceOnloadResp> getEmpAttendance(
            @RequestBody EmpAttendanceGetReq empAttendanceGetReq) {
        EmpAttendanceOnloadResp empAttendanceOnloadResp = mwAttendenceService.getEmpAttendance(empAttendanceGetReq);
        for (String day : empAttendanceOnloadResp.getAttendenceDays()) {
            empAttendanceOnloadResp.getAttendenceDayMap().put(day, true);
        }
        empAttendanceOnloadResp.setEmpLeaveTypeMap(populateEmpAttendanceMap());
        return new ResponseEntity<EmpAttendanceOnloadResp>(empAttendanceOnloadResp, HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.GET_NON_ATTENDANCE_EMP_REG_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<AttendanceEmpRegResp> getNonAttendanceEmpRegDetails(
            @RequestBody EmpAttendanceGetReq empAttendanceGetReq) {
        return new ResponseEntity<AttendanceEmpRegResp>(
                mwAttendenceService.getNonAttendanceEmpRegDetails(empAttendanceGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.COPY_ATTENDANCE_EMP_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<AttendanceEmpRegResp> copyAttendanceEmpDetails(
            @RequestBody EmpAttendanceGetReq empAttendanceGetReq) {
        return new ResponseEntity<AttendanceEmpRegResp>(
                mwAttendenceService.copyAttendanceEmpDetails(empAttendanceGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.GET_ATTENDANCE_DAYS, method = RequestMethod.POST)
    public ResponseEntity<EmpAttendanceOnloadResp> getEmpAttendanceDays(
            @RequestBody EmpAttendanceGetReq empAttendanceGetReq) {
        return new ResponseEntity<EmpAttendanceOnloadResp>(mwAttendenceService.getAttendanceDays(empAttendanceGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.GET_EMP_ATTENDANCE_SHEETS, method = RequestMethod.POST)
    public ResponseEntity<AttendanceEmpRegResp> getEmpAttendanceSheets(
            @RequestBody EmpAttendanceGetReq empAttendanceGetReq) {
        return new ResponseEntity<AttendanceEmpRegResp>(mwAttendenceService.getEmpAttendanceSheets(empAttendanceGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.GET_EMP_ATTENDANCE_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<EmpAttendanceResp> getEmpAttendanceRecords(
            @RequestBody EmpAttendanceGetReq empAttendanceGetReq) {
        EmpAttendanceResp empAttendanceResp = mwAttendenceService.getEmpAttendanceRecords(empAttendanceGetReq);
        return new ResponseEntity<EmpAttendanceResp>(empAttendanceResp, HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.ADD_EMP_TO_ATTENDANCE_RECORD, method = RequestMethod.POST)
    public ResponseEntity<EmpAttendanceResp> addEmpRegToAttendanceRecord(
            @RequestBody EmpAttendanceSaveReq empAttendenceSaveReq) {
        EmpAttendanceResp empAttendanceResp = mwAttendenceService.addEmpToAttendanceRecord(empAttendenceSaveReq);
        return new ResponseEntity<EmpAttendanceResp>(empAttendanceResp, HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.SAVE_EMP_ATTENDANCE_RECORD, method = RequestMethod.POST)
    public ResponseEntity<EmpAttendanceResp> saveEmpAttendanceRecords(
            @RequestBody EmpAttendanceSaveReq empAttendenceSaveReq) {
        EmpAttendanceResp empAttendanceResp = mwAttendenceService.saveEmpAttendanceRecords(empAttendenceSaveReq);
        return new ResponseEntity<EmpAttendanceResp>(empAttendanceResp, HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.GET_PLANT_ATTENDANCE, method = RequestMethod.POST)
    public ResponseEntity<PlantAttendanceOnloadResp> getPlantAttendance(
            @RequestBody PlantAttendanceGetReq plantAttendanceGetReq) {
        PlantAttendanceOnloadResp plantAttendanceOnloadResp = mwAttendenceService
                .getPlantAttendance(plantAttendanceGetReq);
        for (String day : plantAttendanceOnloadResp.getAttendenceDays()) {
            plantAttendanceOnloadResp.getAttendenceDayMap().put(day, true);
        }
        // TODO - commented during AttendancePlantStatusMstrEntity.java cleanup, if needed then uncomment
        //plantAttendanceOnloadResp.setPlantAttendanceMap(populatePlantAttendanceMap());
        return new ResponseEntity<PlantAttendanceOnloadResp>(plantAttendanceOnloadResp, HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.GET_NON_ATTENDANCE_PLANT_REG_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<AttendancePlantRegResp> getNonAttendancePlantRegDetails(
            @RequestBody PlantAttendanceGetReq plantAttendanceGetReq) {
        return new ResponseEntity<AttendancePlantRegResp>(
                mwAttendenceService.getNonAttendancePlantRegDetails(plantAttendanceGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.COPY_ATTENDANCE_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<AttendancePlantRegResp> copyAttendancePlantDetails(
            @RequestBody PlantAttendanceGetReq plantAttendanceGetReq) {
        return new ResponseEntity<AttendancePlantRegResp>(
                mwAttendenceService.copyAttendancePlantDetails(plantAttendanceGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.ADD_PLANT_TO_ATTENDANCE_RECORD, method = RequestMethod.POST)
    public ResponseEntity<PlantAttendanceResp> addPlantToAttendanceRecord(
            @RequestBody PlantAttendanceSaveReq plantAttendanceSaveReq) {
        PlantAttendanceResp plantAttendanceResp = mwAttendenceService
                .addPlantToAttendanceRecord(plantAttendanceSaveReq);
        return new ResponseEntity<PlantAttendanceResp>(plantAttendanceResp, HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.GET_PLANT_ATTENDANCE_SHEETS, method = RequestMethod.POST)
    public ResponseEntity<AttendancePlantRegResp> getPlantAttendanceSheets(
            @RequestBody PlantAttendanceGetReq plantAttendanceGetReq) {
        return new ResponseEntity<AttendancePlantRegResp>(
                mwAttendenceService.getPlantAttendanceSheets(plantAttendanceGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.GET_PLANT_ATTENDANCE_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<PlantAttendanceResp> getPlantAttendanceRecords(
            @RequestBody PlantAttendanceGetReq plantAttendanceGetReq) {
        PlantAttendanceResp plantAttendanceResp = mwAttendenceService.getPlantAttendanceRecords(plantAttendanceGetReq);
        return new ResponseEntity<PlantAttendanceResp>(plantAttendanceResp, HttpStatus.OK);
    }

    @RequestMapping(value = AttendanceURLConstants.SAVE_PLANT_ATTENDANCE_RECORD, method = RequestMethod.POST)
    public ResponseEntity<PlantAttendanceResp> savePlantAttendanceRecords(
            @RequestBody PlantAttendanceSaveReq plantAttendanceSaveReq) {
        PlantAttendanceResp plantAttendanceResp = mwAttendenceService
                .savePlantAttendanceRecords(plantAttendanceSaveReq);
        return new ResponseEntity<PlantAttendanceResp>(plantAttendanceResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = AttendanceURLConstants.GET_EMPLOYEE_ATTENDANCE_RECORD_SHEETS, method = RequestMethod.POST)
    public ResponseEntity<EmpAttendanceSheetResp> getEmployeeAttendanceRecordSheets(
            @RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	EmpAttendanceSheetResp empAttendanceSheetResp = mwAttendenceService.getEmployeeAttendanceRecordSheets(employeeAttendanceRecordSheetsSearchReq);
        return new ResponseEntity<EmpAttendanceSheetResp>(empAttendanceSheetResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = AttendanceURLConstants.GET_PLANT_ATTENDANCE_RECORD_SHEETS, method = RequestMethod.POST)
    public ResponseEntity<PlantAttendanceSheetResp> getPlantAttendanceRecordSheets(
            @RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	PlantAttendanceSheetResp plantAttendanceSheetResp = mwAttendenceService.getPlantAttendanceRecordSheets(employeeAttendanceRecordSheetsSearchReq);
        return new ResponseEntity<PlantAttendanceSheetResp>(plantAttendanceSheetResp, HttpStatus.OK);
    }

    private Map<String, LabelKeyTO> populateEmpAttendanceMap() {
        UserReq userReq = new UserReq();
        userReq.setStatus(StatusCodes.ACTIVE.getValue());
        Map<String, LabelKeyTO> empAttendanceMap = new HashMap<String, LabelKeyTO>();

        LabelKeyTO leaveTypeLableKeyTo = null;
        for (EmpLeaveType leaveType : EmpLeaveType.values()) {
            leaveTypeLableKeyTo = new LabelKeyTO();
            leaveTypeLableKeyTo.setCode(leaveType.name());
            leaveTypeLableKeyTo.setName(leaveType.getDesc());
            empAttendanceMap.put(leaveType.name(), leaveTypeLableKeyTo);
        }
        return empAttendanceMap;
    }
    /* Reports Changes - 03-06-2020 */
    @PostMapping(value = AttendanceURLConstants.GET_DAILY_EMP_ATTENDANCE_REPORT)
    public ResponseEntity<List<LabelKeyTO>> getDailyEmpAttendanceReport(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = mwAttendenceService.getDailyEmpAttendanceReport(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @PostMapping(value = AttendanceURLConstants.GET_EMP_ATTENDANCE_RECORDS_BY_DATE)
    public ResponseEntity<List<LabelKeyTO>> getEmpAttendanceRecordsByDate(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = mwAttendenceService.getEmpAttendanceRecordsByDate(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @PostMapping(value = AttendanceURLConstants.GET_DAILY_PLANT_ATTENDANCE_REPORT)
    public ResponseEntity<List<LabelKeyTO>> getDailyPlantAttendanceReport(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = mwAttendenceService.getDailyPlantAttendanceReport(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @PostMapping(value = AttendanceURLConstants.GET_DAILY_EMP_ATTENDANCE_REPORT_BTWN_DATES)
    public ResponseEntity<List<LabelKeyTO>> getDailyEmpAttendanceReportBtwnDates(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = mwAttendenceService.getDailyEmpAttendanceReportBtwnDates(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @PostMapping(value = AttendanceURLConstants.GET_DAILY_PLANT_ATTENDANCE_REPORT_BTWN_DATES)
    public ResponseEntity<List<LabelKeyTO>> getDailyPlantAttendanceReportBtwnDates(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = mwAttendenceService.getDailyPlantAttendanceReportBtwnDates(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @PostMapping(value = AttendanceURLConstants.GET_EMP_DAILY_RESOURCE_STATUS)
    public ResponseEntity<List<LabelKeyTO>> getEmpDailyResourceStatus(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
    	System.out.println("getEmpDailyResourceStatus MW Controller");
        List<LabelKeyTO> resp = mwAttendenceService.getEmpDailyResourceStatus(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
     
    @PostMapping(value = AttendanceURLConstants.GET_PLANT_DAILY_RESOURCE_STATUS)
    public ResponseEntity<List<LabelKeyTO>> getPlantDailyResourceStatus(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = mwAttendenceService.getPlantDailyResourceStatus(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
