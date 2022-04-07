package com.rjtech.timemanagament.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.constants.TimeSheetURLConstants;
import com.rjtech.timemanagement.manpower.dashboards.dto.ProjectEarnedValueDetails;
import com.rjtech.timemanagement.manpower.reports.dto.CurrentEmployeeDetails;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerActualHrsTO;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerCostCodeDailyReportTO;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerPlannedValuesTO;
import com.rjtech.timemanagement.manpower.reports.req.ManpowerPeroidicalHrsGetReq;
import com.rjtech.timemanagement.manpower.reports.req.ManpowerStandardHrsGetReq;
import com.rjtech.timemanagement.manpower.reports.resp.ManPowerStandardHrsResp;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetTO;
import com.rjtech.timemanagement.timesheet.dto.TimesheetReportTO;
import com.rjtech.timemanagement.timesheet.report.req.TimeSheetApprStatusGetReq;
import com.rjtech.timemanagement.timesheet.report.req.TimeSheetReqUserGetReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetDtlSaveReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetEmpExpenseSaveReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetEmpRegSaveReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetEmpTaskSaveReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetGetMapReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetGetReq;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetDetailResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetEmpDetailResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetEmpExpenseResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetEmpRegResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetEmpTaskResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetProjSettingResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetWageCostCodeMap;
import com.rjtech.timemanagement.timesheet.service.TimeSheetService;

@RestController
@RequestMapping(TimeSheetURLConstants.TIME_SHEET_PARH_URL)
public class TimeSheetController {

    @Autowired
    private TimeSheetService timeSheetService;

    @PostMapping(value = TimeSheetURLConstants.GET_TIME_SHEET_ONLOAD)
    public ResponseEntity<TimeSheetResp> getTimeSheetOnLoad(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getTimeSheetOnLoad(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_TIME_SHEET_ONLOAD_INDIVIDUALS)
    public ResponseEntity<TimeSheetResp> getTimeSheetOnLoadIndividuals(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getTimeSheetOnLoadInddviduls(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_CREW_TIME_SHEET)
    public ResponseEntity<TimeSheetResp> getCrewTimeSheet(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getCrewTimeSheet(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET)
    public ResponseEntity<TimeSheetResp> getIndividualTimeSheet(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getIndividualTimeSheet(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_CREW_TIME_SHEETS)
    public ResponseEntity<TimeSheetResp> getCrewTimeSheets(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getCrewTimeSheets(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEETS)
    public ResponseEntity<TimeSheetResp> getIndividualTimeSheets(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getIndividualTimeSheets(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_CREW_TIME_SHEET_FOR_APPROVAL)
    public ResponseEntity<TimeSheetResp> getCrewTimeSheetForApproval(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getCrewTimeSheetForApproval(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET_FOR_APPROVAL)
    public ResponseEntity<TimeSheetResp> getIndividualTimeSheetForApproval(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getIndividualTimeSheetForApproval(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_TIME_SHEET_BY_ID)
    public ResponseEntity<TimeSheetResp> getTimeSheetById(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getTimeSheetById(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_INDIVIDUALS_FROM_TIME_SHEET)
    public ResponseEntity<TimeSheetEmpRegResp> getIndividualsFromTimeSheet(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getIndividualsFromTimeSheet(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_ALL_INDIVIDUALS_FROM_TIME_SHEET)
    public ResponseEntity<TimeSheetEmpRegResp> getAllIndividualsFromTimeSheet(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getAllIndividualsFromTimeSheet(timeSheetGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_CREW_TIME_SHEET_DETAILS_FOR_SUBMISSION)
    public ResponseEntity<TimeSheetDetailResp> getCrewTimeSheetDetailsForSubmission(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getCrewTimeSheetDetailsForSubmission(timeSheetGetReq),
                HttpStatus.OK);

    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_COPY_CREW_TIME_SHEET_DETAILS_FOR_SUBMISSION)
    public ResponseEntity<TimeSheetDetailResp> getCopyCrewTimeSheetDetailsForSubmission(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getCopyCrewTimeSheetDetailsForSubmission(timeSheetGetReq),
                HttpStatus.OK);

    }

    @PostMapping(value = TimeSheetURLConstants.GET_CREW_TIME_SHEET_DETAILS_FOR_APPROVAL)
    public ResponseEntity<TimeSheetDetailResp> getCrewTimeSheetDetailsForApproval(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getCrewTimeSheetDetailsForApproval(timeSheetGetReq, false),
                HttpStatus.OK);

    }

    @PostMapping(value = TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET_DETAILS_FOR_SUBMISSION)
    public ResponseEntity<TimeSheetDetailResp> getIndividualTimeSheetDetailsForSubmission(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getIndividualTimeSheetDetailsForSubmission(timeSheetGetReq),
                HttpStatus.OK);

    }

    @PostMapping(value = TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET_DETAILS_FOR_APPROVAL)
    public ResponseEntity<TimeSheetDetailResp> getTimeSheetDetailsForApproval(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getIndividualTimeSheetDetailsForApproval(timeSheetGetReq),
                HttpStatus.OK);

    }

    @PostMapping(value = TimeSheetURLConstants.GET_TIME_SHEET_EMP_DETAILS)
    public ResponseEntity<TimeSheetEmpDetailResp> getTimeSheetEmpDetails(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getTimeSheetEmpDetails(timeSheetGetReq), HttpStatus.OK);

    }

    @PostMapping(value = TimeSheetURLConstants.SAVE_CREW_TIME_SHEET_DETAILS)
    public ResponseEntity<TimeSheetDetailResp> saveCrewTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        Map<Long, Boolean> empMaxHrsBookedMap = timeSheetService.saveCrewTimeSheetDetails(timeSheetEmpDtlSaveReq);
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();
        if (!CommonUtil.isSetHasData(empMaxHrsBookedMap.keySet())) {
            TimeSheetGetReq timeSheetGetReq = constructGetReq(timeSheetEmpDtlSaveReq);
            TimeSheetResp timeSheetResp = timeSheetService.getCrewTimeSheet(timeSheetGetReq);
            TimeSheetTO timeSheetTO = timeSheetResp.getTimeSheetTOs().get(0);
            timeSheetGetReq.setTimeSheetId(timeSheetTO.getId());
            timeSheetDetailResp = timeSheetService.getCrewTimeSheetDetailsForSubmission(timeSheetGetReq);
            timeSheetDetailResp.setTimeSheetTO(timeSheetTO);
        } else {
            timeSheetDetailResp.setEmpMaxHrsBookedMap(empMaxHrsBookedMap);
        }
        return new ResponseEntity<>(timeSheetDetailResp, HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.SAVE_APPROVE_CREW_TIME_SHEET_DETAILS)
    public ResponseEntity<TimeSheetDetailResp> saveApproveCrewTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        Map<Long, Boolean> empMaxHrsBookedMap = timeSheetService
                .saveApproveCrewTimeSheetDetails(timeSheetEmpDtlSaveReq);
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();
        if (!CommonUtil.isSetHasData(empMaxHrsBookedMap.keySet())) {
            TimeSheetGetReq timeSheetGetReq = constructGetReq(timeSheetEmpDtlSaveReq);
            TimeSheetResp timeSheetResp = timeSheetService.getCrewTimeSheet(timeSheetGetReq);
            TimeSheetTO timeSheetTO = timeSheetResp.getTimeSheetTOs().get(0);
            timeSheetGetReq.setTimeSheetId(timeSheetTO.getId());
            timeSheetDetailResp = timeSheetService.getCrewTimeSheetDetailsForSubmission(timeSheetGetReq);
            timeSheetDetailResp.setTimeSheetTO(timeSheetTO);
        } else {
            timeSheetDetailResp.setEmpMaxHrsBookedMap(empMaxHrsBookedMap);
        }
        return new ResponseEntity<>(timeSheetDetailResp, HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.SAVE_INDIVIDUAL_TIME_SHEET_DETAILS)
    public ResponseEntity<TimeSheetDetailResp> saveIndividualTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        Map<Long, Boolean> empMaxHrsBookedMap = timeSheetService.saveIndividualTimeSheetDetails(timeSheetEmpDtlSaveReq);
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();
        if (!CommonUtil.isSetHasData(empMaxHrsBookedMap.keySet())) {
            TimeSheetGetReq timeSheetGetReq = constructGetReq(timeSheetEmpDtlSaveReq);
            TimeSheetResp timeSheetResp = timeSheetService.getIndividualTimeSheet(timeSheetGetReq);

            TimeSheetTO timeSheetTO = timeSheetResp.getTimeSheetTOs().get(0);
            timeSheetGetReq.setTimeSheetId(timeSheetTO.getId());
            timeSheetDetailResp = timeSheetService.getIndividualTimeSheetDetailsForSubmission(timeSheetGetReq);
            timeSheetDetailResp.setTimeSheetTO(timeSheetTO);
        } else {
            timeSheetDetailResp.setEmpMaxHrsBookedMap(empMaxHrsBookedMap);
        }
        return new ResponseEntity<>(timeSheetDetailResp, HttpStatus.OK);
    }

    private TimeSheetGetReq constructGetReq(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        TimeSheetTO timeSheetTO = timeSheetEmpDtlSaveReq.getTimeSheetTO();
        TimeSheetGetReq timeSheetGetReq = new TimeSheetGetReq();
        timeSheetGetReq.setTimeSheetId(timeSheetTO.getId());
        timeSheetGetReq.setProjId(timeSheetTO.getProjId());
        timeSheetGetReq.setCrewId(timeSheetTO.getCrewId());
        timeSheetGetReq.setEmpId(timeSheetTO.getEmpId());
        timeSheetGetReq.setAdditional(timeSheetTO.getAdditional());
        timeSheetGetReq.setWeekStartDate(timeSheetTO.getWeekStartDate());
        timeSheetGetReq.setWeekEndDate(timeSheetTO.getWeekEndDate());
        timeSheetGetReq.setWeekCommenceDay(timeSheetTO.getWeekCommenceDay());
        timeSheetGetReq.setStatus(timeSheetTO.getStatus());
        timeSheetGetReq.setApprStatus(timeSheetTO.getApprStatus());
        return timeSheetGetReq;
    }

    @PostMapping(value = TimeSheetURLConstants.SUBMIT_CREW_TIME_SHEET_DETAILS)
    public ResponseEntity<TimeSheetDetailResp> submitCrewTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        timeSheetService.submitCrewTimeSheetDetails(timeSheetEmpDtlSaveReq);
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();
        TimeSheetGetReq timeSheetGetReq = constructGetReq(timeSheetEmpDtlSaveReq);
        timeSheetDetailResp = timeSheetService.getCrewTimeSheetDetailsForSubmission(timeSheetGetReq);
        return new ResponseEntity<>(timeSheetDetailResp, HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.SUBMIT_INDIVIDUAL_TIME_SHEET_DETAILS)
    public ResponseEntity<TimeSheetDetailResp> submitIndividualTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        timeSheetService.submitIndividualTimeSheetDetails(timeSheetEmpDtlSaveReq);
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();
        TimeSheetGetReq timeSheetGetReq = constructGetReq(timeSheetEmpDtlSaveReq);
        timeSheetDetailResp = timeSheetService.getIndividualTimeSheetDetailsForSubmission(timeSheetGetReq);
        return new ResponseEntity<>(timeSheetDetailResp, HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.APPROVE_CREW_TIME_SHEET_DETAILS)
    public ResponseEntity<TimeSheetDetailResp> approveCrewTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        timeSheetService.approveTimeSheetDetails(timeSheetEmpDtlSaveReq);
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();
        TimeSheetGetReq timeSheetGetReq = constructGetReq(timeSheetEmpDtlSaveReq);
        timeSheetDetailResp = timeSheetService.getCrewTimeSheetDetailsForSubmission(timeSheetGetReq);
        return new ResponseEntity<>(timeSheetDetailResp, HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.APPROVE_INDIVIDUAL_TIME_SHEET_DETAILS)
    public ResponseEntity<TimeSheetDetailResp> approveTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        timeSheetService.approveTimeSheetDetails(timeSheetEmpDtlSaveReq);
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();
        TimeSheetGetReq timeSheetGetReq = constructGetReq(timeSheetEmpDtlSaveReq);
        timeSheetDetailResp = timeSheetService.getIndividualTimeSheetDetailsForSubmission(timeSheetGetReq);
        return new ResponseEntity<>(timeSheetDetailResp, HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.SAVE_TIME_SHEET_EMP_TASKS)
    public ResponseEntity<TimeSheetEmpTaskResp> saveTimeSheetEmpTasks(
            @RequestBody TimeSheetEmpTaskSaveReq timeSheetEmpTaskSaveReq) {
        timeSheetService.saveTimeSheetEmpTasks(timeSheetEmpTaskSaveReq);
        TimeSheetEmpTaskResp timeSheetEmpTaskResp = new TimeSheetEmpTaskResp();
        TimeSheetGetReq timeSheetGetReq = new TimeSheetGetReq();
        timeSheetGetReq.setTimeSheetEmpId(timeSheetEmpTaskSaveReq.getTimeSheetEmpId());
        timeSheetGetReq.setTimeSheetId(timeSheetEmpTaskSaveReq.getTimeSheetId());
        timeSheetGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        timeSheetEmpTaskResp = timeSheetService.getTimeSheetEmpTasks(timeSheetGetReq);
        return new ResponseEntity<>(timeSheetEmpTaskResp, HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.SAVE_TIMEE_SHEET_EMP_EXPENSES)
    public ResponseEntity<TimeSheetEmpExpenseResp> saveTimeSheetExpenses(
            @RequestBody TimeSheetEmpExpenseSaveReq timeSheetEmpExpenseSaveReq) {
        timeSheetService.saveTimeSheetEmpExpenses(timeSheetEmpExpenseSaveReq);
        TimeSheetEmpExpenseResp timeSheetEmpExpenseResp = new TimeSheetEmpExpenseResp();
        TimeSheetGetReq timeSheetGetReq = new TimeSheetGetReq();
        timeSheetGetReq.setTimeSheetId(timeSheetEmpExpenseSaveReq.getTimeSheetId());
        timeSheetGetReq.setTimeSheetEmpId(timeSheetEmpExpenseSaveReq.getTimeSheetEmpId());
        timeSheetGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        timeSheetEmpExpenseResp = timeSheetService.getTimeSheetEmpExpenses(timeSheetGetReq);
        return new ResponseEntity<>(timeSheetEmpExpenseResp, HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_PROJ_SETTINGS_FOR_TIME_SHEET)
    public ResponseEntity<TimeSheetProjSettingResp> getProjSettingsForTimeSheet(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getProjSettingsForTimeSheet(timeSheetGetReq), HttpStatus.OK);

    }

    @PostMapping(value = TimeSheetURLConstants.GET_EMP_REG_DETAILS)
    public ResponseEntity<TimeSheetEmpRegResp> getEmpRegDetails(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getEmpRegDetails(timeSheetGetReq), HttpStatus.OK);

    }

    @PostMapping(value = TimeSheetURLConstants.GET_OTHER_CREW_ATTENDACNE_FOR_TIME_SHEET)
    public ResponseEntity<TimeSheetEmpRegResp> getOtherCrewEmpAttendance(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getOtherCrewEmpAttendance(timeSheetGetReq), HttpStatus.OK);

    }

    @PostMapping(value = TimeSheetURLConstants.GET_CREW_ATTENDACNE_FOR_INDIVIDUALS)
    public ResponseEntity<TimeSheetEmpRegResp> getCrewAttendanceForIndividuals(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getCrewAttendanceForIndividuals(timeSheetGetReq), HttpStatus.OK);

    }

    @PostMapping(value = TimeSheetURLConstants.COPY_TIME_SHEET_EMP_REG_DETAILS)
    public ResponseEntity<TimeSheetEmpRegResp> copyTimeSheetEmpDetails(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.copyTimeSheetEmpDetails(timeSheetGetReq), HttpStatus.OK);
    }

    //ganga
    @PostMapping(value = TimeSheetURLConstants.ADD_EMP_REG_TO_TIME_SHEET)
    public ResponseEntity<TimeSheetDetailResp> addEmpRegToTimeSheet(
            @RequestBody TimeSheetEmpRegSaveReq timeSheetEmpRegSaveReq) {
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();
        timeSheetDetailResp = timeSheetService.addEmpRegToTimeSheet(timeSheetEmpRegSaveReq);
        return new ResponseEntity<>(timeSheetDetailResp, HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_PROJ_SETTINGS_TIMESHEET_DETAILS)
    public ResponseEntity<LabelKeyTOResp> getProjSettingsTimeSheetDetails(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        labelKeyTOResp = timeSheetService.getProjSettingsTimeSheetDetails(timeSheetGetReq);
        return new ResponseEntity<>(labelKeyTOResp, HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_TIME_SHEET_COSTCODE_WAGE_MAP)
    public TimeSheetWageCostCodeMap timeSheetWageCodeMap(@RequestBody TimeSheetGetMapReq timeSheetGetMapReq) {
        TimeSheetWageCostCodeMap timeSheetMap = new TimeSheetWageCostCodeMap();
        timeSheetMap = timeSheetService.timeSheetWageCodeMap(timeSheetGetMapReq);
        return timeSheetMap;
    }

    @PostMapping(value = TimeSheetURLConstants.GET_TIMESHEET_DAILY_REPORT)
    public ResponseEntity<TimeSheetDetailResp> getTimeSheetDailyReport(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<>(timeSheetService.getCrewTimeSheetDetailsForApproval(timeSheetGetReq, true),
                HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_TIMESHEET_REQ_USERS)
    public ResponseEntity<List<LabelKeyTO>> getTimeSheetReqUserReport(
            @RequestBody TimeSheetReqUserGetReq timeSheetReqUserGetReq) {
        return new ResponseEntity<>(timeSheetService.getTimeSheetReqUserReport(timeSheetReqUserGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_TIMESHEET_APPROVESTATUS_REPORT)
    public ResponseEntity<List<TimesheetReportTO>> getTimeSheetApprStatusReport(
            @RequestBody TimeSheetApprStatusGetReq timeSheetApprStatusGetReq) {
        return new ResponseEntity<>(timeSheetService.getTimeSheetApprStatusReport(timeSheetApprStatusGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_PERIODICAL_REPORT)
    public ResponseEntity<List<ManPowerActualHrsTO>> getManpowerPeriodicalReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq) {
        return new ResponseEntity<>(timeSheetService.getManpowerPeriodicalReport(manpowerPeroidicalHrsGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_DATE_WISE_HRS_REPORT)
    public ResponseEntity<List<ManPowerActualHrsTO>> getManpowerDateWiseActualHrsReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerDateWiseHrsGetReq) {
        return new ResponseEntity<>(timeSheetService.getManpowerDateWiseHrsReport(manpowerDateWiseHrsGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_COST_CODE_WISE_REPORT)
    public ResponseEntity<List<ManPowerCostCodeDailyReportTO>> getManpowerCostCodeWiseReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerCostCodeGetReq) {
        return new ResponseEntity<>(timeSheetService.getManpowerCostCodeWiseReport(manpowerCostCodeGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_ACTUAL_STANDARD_REPORT)
    public ResponseEntity<ManPowerStandardHrsResp> getManpowerActualStandardReport(
            @RequestBody ManpowerStandardHrsGetReq manpowerStandardHrsGetReq) {
        return new ResponseEntity<>(timeSheetService.getManpowerActualStandardReport(manpowerStandardHrsGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_IDLE_HRS_REPORT)
    public ResponseEntity<List<ManPowerCostCodeDailyReportTO>> getPlantUtilisationRecordsReport(
            @RequestBody ManpowerStandardHrsGetReq manpowerIdleHrsGetReq) {
        return new ResponseEntity<>(timeSheetService.getManpowerIdleHrsReport(manpowerIdleHrsGetReq), HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_CURRENT_EMPLOYEE_REPORT)
    public ResponseEntity<List<CurrentEmployeeDetails>> getManpowerCurrentEmployeeReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerCurrentEmpGetReq) {
        return new ResponseEntity<>(timeSheetService.getManpowerCurrentEmployeeReport(manpowerCurrentEmpGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_ACTUAL_EARNED_REPORT)
    public ResponseEntity<List<ManPowerPlannedValuesTO>> getManpowerActualAndEarnedValuesReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerCurrentEmpGetReq) {
        return new ResponseEntity<>(timeSheetService.getManpowerPlannedAndEarnedValues(manpowerCurrentEmpGetReq),
                HttpStatus.OK);
    }

    @PostMapping(value = TimeSheetURLConstants.GET_PROJ_EARNED_VALUES)
    public ResponseEntity<List<ProjectEarnedValueDetails>> getProjEarnedValues(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerCurrentEmpGetReq) {
        return new ResponseEntity<>(timeSheetService.getProjEarnedValues(manpowerCurrentEmpGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = TimeSheetURLConstants.GET_CREATED_TIME_SHEETS)
    public ResponseEntity<TimeSheetResp> getCreatedTimeSheets(@RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
        TimeSheetResp timeSheetResp = timeSheetService.getCreatedTimeSheets(employeeAttendanceRecordSheetsSearchReq);
        return new ResponseEntity<TimeSheetResp>(timeSheetResp, HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_SUBMITTED_TIME_SHEETS)
    public ResponseEntity<TimeSheetResp> getSubmittedTimeSheets(@RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
        TimeSheetResp timeSheetResp = timeSheetService.getSubmittedTimeSheets(employeeAttendanceRecordSheetsSearchReq);
        return new ResponseEntity<TimeSheetResp>(timeSheetResp, HttpStatus.OK);
    }
}