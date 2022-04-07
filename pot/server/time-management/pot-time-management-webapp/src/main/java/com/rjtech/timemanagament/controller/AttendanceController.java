package com.rjtech.timemanagament.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
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
import com.rjtech.timemanagement.attendance.service.EmpAttendanceService;
import com.rjtech.timemanagement.attendance.service.PlantAttendanceService;
import com.rjtech.timemanagement.constants.AttendanceURLConstants;

@RestController
@RequestMapping(AttendanceURLConstants.ATTENDANCE_PARH_URL)
public class AttendanceController {
	private static final Logger log = LoggerFactory.getLogger(AttendanceController.class);
    @Autowired
    private EmpAttendanceService empAttendenceService;

    @Autowired
    private PlantAttendanceService plantAttendenceService;

    @PostMapping(value = AttendanceURLConstants.GET_EMP_ATTENDANCE)
    public ResponseEntity<EmpAttendanceOnloadResp> getEmpAttendance(
            @RequestBody EmpAttendanceGetReq empAttendenceGetReq) {
        return new ResponseEntity<>(empAttendenceService.getEmpAttendance(empAttendenceGetReq), HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_NON_ATTENDANCE_EMP_REG_DETAILS)
    public ResponseEntity<AttendanceEmpRegResp> getNonAttendanceEmpRegDetails(
            @RequestBody EmpAttendanceGetReq empAttendanceGetReq) {
        return new ResponseEntity<>(empAttendenceService.getNonAttendanceEmpRegDetails(empAttendanceGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.COPY_ATTENDANCE_EMP_DETAILS)
    public ResponseEntity<AttendanceEmpRegResp> copyAttendanceEmpDetails(
            @RequestBody EmpAttendanceGetReq empAttendanceGetReq) {
        return new ResponseEntity<>(empAttendenceService.copyAttendanceEmpDetails(empAttendanceGetReq), HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.ADD_EMP_TO_ATTENDANCE_RECORD)
    public ResponseEntity<EmpAttendanceResp> addEmpToAttendanceRecord(
            @RequestBody EmpAttendanceSaveReq empAttendanceSaveReq) {
        EmpAttendanceGetReq empAttendanceGetReq = new EmpAttendanceGetReq();
        empAttendanceGetReq.setAttendenceId(empAttendanceSaveReq.getAttendenceId());
        empAttendanceGetReq.setAttendenceMonth(empAttendanceSaveReq.getAttendenceMonth());
        empAttendanceGetReq.setCrewId(empAttendanceSaveReq.getCrewId());
        empAttendanceGetReq.setProjId(empAttendanceSaveReq.getProjId());
        empAttendanceGetReq.setStatus(empAttendanceSaveReq.getStatus());
        EmpAttendanceResp empAttendanceResp = empAttendenceService.addEmpToAttendanceRecord(empAttendanceSaveReq,
                empAttendanceGetReq);

        return new ResponseEntity<>(empAttendanceResp, HttpStatus.OK);

    }

    @PostMapping(value = AttendanceURLConstants.GET_EMP_ATTENDANCE_SHEETS)
    public ResponseEntity<AttendanceEmpRegResp> getEmpAttendanceSheets(
            @RequestBody EmpAttendanceGetReq empAttendanceGetReq) {
        return new ResponseEntity<>(empAttendenceService.getEmpAttendanceSheets(empAttendanceGetReq), HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_ATTENDANCE_DAYS)
    public ResponseEntity<EmpAttendanceOnloadResp> getEmpAttendanceDays(
            @RequestBody EmpAttendanceGetReq empAttendanceGetReq) {
        return new ResponseEntity<>(empAttendenceService.getAttendanceDays(empAttendanceGetReq), HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_EMP_ATTENDANCE_RECORDS)
    public ResponseEntity<EmpAttendanceResp> getEmpAttendenceRecords(
            @RequestBody EmpAttendanceGetReq empAttendenceGetReq) {
        return new ResponseEntity<>(empAttendenceService.getEmpAttendanceRecords(empAttendenceGetReq), HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.SAVE_EMP_ATTENDANCE_RECORD)
    public ResponseEntity<EmpAttendanceResp> saveEmpAttendanceRecords(
            @RequestBody EmpAttendanceSaveReq empAttendenceSaveReq) {
        LabelKeyTO labelKeyTO = empAttendenceService.saveEmpAttendanceRecords(empAttendenceSaveReq);
        if (CommonUtil.isNonBlankLong(empAttendenceSaveReq.getAttendenceId())) {
            labelKeyTO.setId(empAttendenceSaveReq.getAttendenceId());
        }
        EmpAttendanceGetReq empAttendanceGetReq = new EmpAttendanceGetReq();
        empAttendanceGetReq.setProjId(empAttendenceSaveReq.getProjId());
        empAttendanceGetReq.setCrewId(empAttendenceSaveReq.getCrewId());
        empAttendanceGetReq.setAttendenceMonth(empAttendenceSaveReq.getAttendenceMonth());
        empAttendanceGetReq.setAttendenceId(labelKeyTO.getId());
        empAttendanceGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        EmpAttendanceResp empAttendanceResp = empAttendenceService.getEmpAttendanceRecords(empAttendanceGetReq);
        empAttendanceResp.setLabelKeyTO(labelKeyTO);
        return new ResponseEntity<>(empAttendanceResp, HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_PLANT_ATTENDANCE)
    public ResponseEntity<PlantAttendanceOnloadResp> getPlantAttendance(
            @RequestBody PlantAttendanceGetReq plantAttendanceGetReq) {
        return new ResponseEntity<>(plantAttendenceService.getPlantAttendance(plantAttendanceGetReq), HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_NON_ATTENDANCE_PLANT_REG_DETAILS)
    public ResponseEntity<AttendancePlantRegResp> getNonAttendancePlantRegDetails(
            @RequestBody PlantAttendanceGetReq plantAttendanceGetReq) {
        return new ResponseEntity<>(plantAttendenceService.getNonAttendancePlantRegDetails(plantAttendanceGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.COPY_ATTENDANCE_PLANT_DETAILS)
    public ResponseEntity<AttendancePlantRegResp> copyAttendancePlantDetails(
            @RequestBody PlantAttendanceGetReq plantAttendanceGetReq) {
        return new ResponseEntity<>(plantAttendenceService.copyAttendancePlantDetails(plantAttendanceGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.ADD_PLANT_TO_ATTENDANCE_RECORD)
    public ResponseEntity<PlantAttendanceResp> addPlantToAttendanceRecord(
            @RequestBody PlantAttendanceSaveReq plantAttendanceSaveReq) {
        PlantAttendanceGetReq plantAttendanceGetReq = new PlantAttendanceGetReq();
        plantAttendanceGetReq.setAttendenceId(plantAttendanceSaveReq.getAttendenceId());
        plantAttendanceGetReq.setAttendenceMonth(plantAttendanceSaveReq.getAttendenceMonth());
        plantAttendanceGetReq.setCrewId(plantAttendanceSaveReq.getCrewId());
        plantAttendanceGetReq.setProjId(plantAttendanceSaveReq.getProjId());
        plantAttendanceGetReq.setStatus(plantAttendanceSaveReq.getStatus());
        PlantAttendanceResp plantAttendanceResp = plantAttendenceService
                .addPlantToAttendanceRecord(plantAttendanceSaveReq, plantAttendanceGetReq);

        return new ResponseEntity<>(plantAttendanceResp, HttpStatus.OK);

    }

    @PostMapping(value = AttendanceURLConstants.GET_PLANT_ATTENDANCE_SHEETS)
    public ResponseEntity<AttendancePlantRegResp> getPlantAttendanceSheets(
            @RequestBody PlantAttendanceGetReq plantAttendanceGetReq) {
        return new ResponseEntity<>(plantAttendenceService.getPlantAttendanceSheets(plantAttendanceGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_PLANT_ATTENDANCE_RECORDS)
    public ResponseEntity<PlantAttendanceResp> getPlantAttendanceRecords(
            @RequestBody PlantAttendanceGetReq plantAttendanceGetReq) {
        return new ResponseEntity<>(plantAttendenceService.getPlantAttendanceRecords(plantAttendanceGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.SAVE_PLANT_ATTENDANCE_RECORD)
    public ResponseEntity<PlantAttendanceResp> savePlantAttendanceRecords(
            @RequestBody PlantAttendanceSaveReq plantAttendanceSaveReq) {
        LabelKeyTO labelKeyTO = plantAttendenceService.savePlantAttendanceRecords(plantAttendanceSaveReq);
        if (CommonUtil.isNonBlankLong(plantAttendanceSaveReq.getAttendenceId())) {
            labelKeyTO.setId(plantAttendanceSaveReq.getAttendenceId());
        }
        PlantAttendanceGetReq plantAttendanceGetReq = new PlantAttendanceGetReq();
        plantAttendanceGetReq.setProjId(plantAttendanceSaveReq.getProjId());
        plantAttendanceGetReq.setCrewId(plantAttendanceSaveReq.getCrewId());
        plantAttendanceGetReq.setAttendenceMonth(plantAttendanceSaveReq.getAttendenceMonth());
        plantAttendanceGetReq.setAttendenceId(labelKeyTO.getId());
        plantAttendanceGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        PlantAttendanceResp plantAttendanceResp = plantAttendenceService
                .getPlantAttendanceRecords(plantAttendanceGetReq);
        plantAttendanceResp.setLabelKeyTO(labelKeyTO);
        return new ResponseEntity<>(plantAttendanceResp, HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_DAILY_EMP_ATTENDANCE_REPORT)
    public ResponseEntity<List<LabelKeyTO>> getDailyEmpAttendanceReport(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = empAttendenceService.getDailyEmpAttendanceReport(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_EMP_ATTENDANCE_RECORDS_BY_DATE)
    public ResponseEntity<List<LabelKeyTO>> getEmpAttendanceRecordsByDate(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = empAttendenceService.getEmpAttendanceRecordsByDate(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_DAILY_EMP_ATTENDANCE_REPORT_BTWN_DATES)
    public ResponseEntity<List<LabelKeyTO>> getDailyEmpAttendanceReportBtwnDates(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = empAttendenceService.getDailyEmpAttendanceReportBtwnDates(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_DAILY_PLANT_ATTENDANCE_REPORT)
    public ResponseEntity<List<LabelKeyTO>> getDailyPlantAttendanceReport(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = plantAttendenceService.getDailyPlantAttendanceReport(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_DAILY_PLANT_ATTENDANCE_REPORT_BTWN_DATES)
    public ResponseEntity<List<LabelKeyTO>> getDailyPlantAttendanceReportBtwnDates(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = plantAttendenceService.getDailyPlantAttendanceReportBtwnDates(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_EMP_DAILY_RESOURCE_STATUS)
    public ResponseEntity<List<LabelKeyTO>> getEmpDailyResourceStatus(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = empAttendenceService.getEmpDailyResourceStatus(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_PLANT_DAILY_RESOURCE_STATUS)
    public ResponseEntity<List<LabelKeyTO>> getPlantDailyResourceStatus(
            @RequestBody DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<LabelKeyTO> resp = plantAttendenceService.getPlantDailyResourceStatus(dailyAttendanceGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = AttendanceURLConstants.GET_EMPLOYEE_ATTENDANCE_RECORD_SHEETS)
    public ResponseEntity<EmpAttendanceSheetResp> getEmployeeAttendanceRecordSheets(
            @RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
        return new ResponseEntity<>(empAttendenceService.getEmployeeAttendanceRecordSheets(employeeAttendanceRecordSheetsSearchReq), HttpStatus.OK);
    }
    
    @PostMapping(value = AttendanceURLConstants.GET_PLANT_ATTENDANCE_RECORD_SHEETS)
    public ResponseEntity<PlantAttendanceSheetResp> getPlantAttendanceRecordSheets(
            @RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
        return new ResponseEntity<>(plantAttendenceService.getPlantAttendanceRecordSheets(employeeAttendanceRecordSheetsSearchReq), HttpStatus.OK);
    }
}
