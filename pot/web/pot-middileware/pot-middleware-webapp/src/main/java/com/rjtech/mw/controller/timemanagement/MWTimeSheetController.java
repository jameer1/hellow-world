package com.rjtech.mw.controller.timemanagement;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.req.EmpWagesFilterReq;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.mw.controller.central.handler.WagFactorHandler;
import com.rjtech.mw.projLib.handler.ProjLibCostItemHandler;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.timemanagement.MWTimeSheetService;
import com.rjtech.projectlib.req.ProjCostItemGetReq;
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
import com.rjtech.timemanagement.timesheet.req.TimeSheetDaysReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetDtlSaveReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetEmpExpenseSaveReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetEmpRegSaveReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetEmpTaskSaveReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetGetMapReq;
import com.rjtech.timemanagement.timesheet.req.TimeSheetGetReq;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetDaysResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetDetailResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetEmpDetailResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetEmpExpenseResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetEmpRegResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetProjSettingResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetResp;
import com.rjtech.timemanagement.timesheet.resp.TimeSheetWageCostCodeMap;
import com.rjtech.timemanagement.util.TimeManagementUtil;

@RestController
@RequestMapping(TimeSheetURLConstants.TIME_SHEET_PARH_URL)
public class MWTimeSheetController {

    @Autowired
    private MWTimeSheetService mwTimeSheetService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @RequestMapping(value = TimeSheetURLConstants.GET_TIME_SHEET_DAYS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDaysResp> getTimeSheetDays(@RequestBody TimeSheetDaysReq timeSheetDaysReq) {
        TimeSheetDaysResp timeSheetDaysResp = getTimeSheetDaysByWeekCommenceDay(timeSheetDaysReq);
        return new ResponseEntity<TimeSheetDaysResp>(timeSheetDaysResp, HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_TIME_SHEET_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetResp> getTimeSheetOnLoad(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetResp timeSheetResp = mwTimeSheetService.getTimeSheetOnLoad(timeSheetGetReq);
        populateTimesheetResp(timeSheetGetReq, timeSheetResp);
        return new ResponseEntity<TimeSheetResp>(timeSheetResp, HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_CREW_TIME_SHEET, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetResp> getCrewTimeSheet(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetResp timeSheetResp = mwTimeSheetService.getCrewTimeSheet(timeSheetGetReq);
        return new ResponseEntity<TimeSheetResp>(timeSheetResp, HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetResp> getIndividualTimeSheet(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetResp timeSheetResp = mwTimeSheetService.getIndividualTimeSheet(timeSheetGetReq);
        return new ResponseEntity<TimeSheetResp>(timeSheetResp, HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_CREW_TIME_SHEETS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetResp> getCrewTimeSheets(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetResp timeSheetResp = mwTimeSheetService.getCrewTimeSheets(timeSheetGetReq);
        return new ResponseEntity<TimeSheetResp>(timeSheetResp, HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEETS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetResp> getIndividualTimeSheets(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetResp timeSheetResp = mwTimeSheetService.getIndividualTimeSheets(timeSheetGetReq);
        return new ResponseEntity<TimeSheetResp>(timeSheetResp, HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_CREW_TIME_SHEET_FOR_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetResp> getCrewTimeSheetForApproval(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<TimeSheetResp>(mwTimeSheetService.getCrewTimeSheetForApproval(timeSheetGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET_FOR_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetResp> getIndividualTimeSheetForApproval(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<TimeSheetResp>(mwTimeSheetService.getIndividualTimeSheetForApproval(timeSheetGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_INDIVIDUALS_FROM_TIME_SHEET, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetEmpRegResp> getIndividualsFromTimeSheet(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<TimeSheetEmpRegResp>(mwTimeSheetService.getIndividualsFromTimeSheet(timeSheetGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_ALL_INDIVIDUALS_FROM_TIME_SHEET, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetEmpRegResp> getAllIndividualsFromTimeSheet(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<TimeSheetEmpRegResp>(
                mwTimeSheetService.getAllIndividualsFromTimeSheet(timeSheetGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_CREW_TIME_SHEET_DETAILS_FOR_SUBMISSION, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> getCrewTimeSheetDetailsForSubmission(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetDetailResp timeSheetDetailResp = mwTimeSheetService
                .getCrewTimeSheetDetailsForSubmission(timeSheetGetReq);
        return new ResponseEntity<TimeSheetDetailResp>(timeSheetDetailResp, HttpStatus.OK);

    }
    
    @RequestMapping(value = TimeSheetURLConstants.GET_COPY_CREW_TIME_SHEET_DETAILS_FOR_SUBMISSION, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> getCopyCrewTimeSheetDetailsForSubmission(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetDetailResp timeSheetDetailResp = mwTimeSheetService
                .getCopyCrewTimeSheetDetailsForSubmission(timeSheetGetReq);
        return new ResponseEntity<TimeSheetDetailResp>(timeSheetDetailResp, HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.GET_CREW_TIME_SHEET_DETAILS_FOR_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> getCrewTimeSheetDetailsForApproval(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetDetailResp timeSheetDetailResp = mwTimeSheetService
                .getCrewTimeSheetDetailsForApproval(timeSheetGetReq);
        return new ResponseEntity<TimeSheetDetailResp>(timeSheetDetailResp, HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET_DETAILS_FOR_SUBMISSION, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> getIndividualTimeSheetDetailsForSubmission(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetDetailResp timeSheetDetailResp = mwTimeSheetService
                .getIndividualTimeSheetDetailsForSubmission(timeSheetGetReq);
        return new ResponseEntity<TimeSheetDetailResp>(timeSheetDetailResp, HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET_DETAILS_FOR_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> getIndividualTimeSheetDetailsForApproval(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetDetailResp timeSheetDetailResp = mwTimeSheetService
                .getIndividualTimeSheetDetailsForApproval(timeSheetGetReq);
        return new ResponseEntity<TimeSheetDetailResp>(timeSheetDetailResp, HttpStatus.OK);

    }

    private void populateTimesheetResp(TimeSheetGetReq timeSheetGetReq, TimeSheetResp timeSheetResp) {
        Map<Long, LabelKeyTO> costCodeMap = new HashMap<Long, LabelKeyTO>();
        Map<Long, LabelKeyTO> empWageFactorMap = new HashMap<Long, LabelKeyTO>();

        ProjCostItemGetReq projCostItemGetReq = new ProjCostItemGetReq();
        projCostItemGetReq.setProjId(timeSheetGetReq.getProjId());
        projCostItemGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        costCodeMap = ProjLibCostItemHandler
                .getLableKeyTO(mwProjLibService.getProjCostItemsOnly(projCostItemGetReq).getProjCostItemTOs());

        EmpWagesFilterReq empWagesFilterReq = new EmpWagesFilterReq();
        empWagesFilterReq.setStatus(StatusCodes.ACTIVE.getValue());
        empWageFactorMap = WagFactorHandler
                .getLableKeyTOs(mwCentralLiblService.getEmpWages(empWagesFilterReq).getEmployeeWageRateTOs());
        timeSheetResp.setCostCodeMap(costCodeMap);
        timeSheetResp.setEmpWageFactorMap(empWageFactorMap);
    }

    private TimeSheetDaysResp getTimeSheetDaysByWeekCommenceDay(TimeSheetDaysReq timeSheetDaysReq) {
        TimeSheetDaysResp timeSheetDaysResp = new TimeSheetDaysResp();
        TimeSheetTO timeSheetTO = new TimeSheetTO();
        Date weekStartDate = TimeManagementUtil.getDateDDMMMYYYYFormat(timeSheetDaysReq.getWeekCommenceDay());
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(weekStartDate);
        cal.add(Calendar.DATE, timeSheetDaysReq.getWeekEndNo());
        timeSheetTO.setWeekCommenceDay(timeSheetDaysReq.getWeekCommenceDay());
        timeSheetTO.setWeekStartDate(weekStartDate);
        timeSheetTO.setWeekEndDate(cal.getTime());
        timeSheetDaysResp.setTimeSheetTO(timeSheetTO);
        return timeSheetDaysResp;
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_TIME_SHEET_EMP_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetEmpDetailResp> getTimeSheetEmpDetails(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<TimeSheetEmpDetailResp>(mwTimeSheetService.getTimeSheetEmpDetails(timeSheetGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.SAVE_CREW_TIME_SHEET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> saveCrewTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        return new ResponseEntity<TimeSheetDetailResp>(mwTimeSheetService.saveCrewTimeSheetDetails(timeSheetDtlSaveReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.SAVE_APPROVE_CREW_TIME_SHEET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> saveApproveCrewTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        return new ResponseEntity<TimeSheetDetailResp>(
                mwTimeSheetService.saveApproveCrewTimeSheetDetails(timeSheetDtlSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.SUBMIT_CREW_TIME_SHEET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> submitTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        return new ResponseEntity<TimeSheetDetailResp>(
                mwTimeSheetService.submitCrewTimeSheetDetails(timeSheetDtlSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.APPROVE_CREW_TIME_SHEET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> approveTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        return new ResponseEntity<TimeSheetDetailResp>(
                mwTimeSheetService.approveCrewTimeSheetDetails(timeSheetDtlSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.SAVE_INDIVIDUAL_TIME_SHEET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> saveIndividualTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        return new ResponseEntity<TimeSheetDetailResp>(
                mwTimeSheetService.saveIndividualTimeSheetDetails(timeSheetDtlSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.SUBMIT_INDIVIDUAL_TIME_SHEET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> submitIndividualTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        return new ResponseEntity<TimeSheetDetailResp>(
                mwTimeSheetService.submitIndividualTimeSheetDetails(timeSheetDtlSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.APPROVE_INDIVIDUAL_TIME_SHEET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> approveIndividualTimeSheetDetails(
            @RequestBody TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        return new ResponseEntity<TimeSheetDetailResp>(
                mwTimeSheetService.approveIndividualTimeSheetDetails(timeSheetDtlSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.SAVE_TIME_SHEET_EMP_TASKS, method = RequestMethod.POST)
    public ResponseEntity<String> saveTimeSheetEmpTasks(@RequestBody TimeSheetEmpTaskSaveReq timeSheetEmpTaskSaveReq) {
        String resultData = AppUtils.toJson(mwTimeSheetService.saveTimeSheetEmpTasks(timeSheetEmpTaskSaveReq));
        return new ResponseEntity<String>(resultData, HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.SAVE_TIMEE_SHEET_EMP_EXPENSES, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetEmpExpenseResp> saveTimeSheetExpenses(
            @RequestBody TimeSheetEmpExpenseSaveReq timeSheetEmpExpenseSaveReq) {
        return new ResponseEntity<TimeSheetEmpExpenseResp>(
                mwTimeSheetService.saveTimeSheetExpenses(timeSheetEmpExpenseSaveReq), HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.GET_PROJ_SETTINGS_FOR_TIME_SHEET, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetProjSettingResp> getProjSettingsForTimeSheet(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<TimeSheetProjSettingResp>(
                mwTimeSheetService.getProjSettingsForTimeSheet(timeSheetGetReq), HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.GET_EMP_REG_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetEmpRegResp> getEmpRegDetails(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<TimeSheetEmpRegResp>(mwTimeSheetService.getEmpRegDetails(timeSheetGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.GET_OTHER_CREW_ATTENDACNE_FOR_TIME_SHEET, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetEmpRegResp> getOtherCrewEmpAttendance(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<TimeSheetEmpRegResp>(mwTimeSheetService.getOtherCrewEmpAttendance(timeSheetGetReq),
                HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.GET_CREW_ATTENDACNE_FOR_INDIVIDUALS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetEmpRegResp> getCrewAttendanceForIndividuals(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<TimeSheetEmpRegResp>(
                mwTimeSheetService.getCrewAttendanceForIndividuals(timeSheetGetReq), HttpStatus.OK);

    }

    @RequestMapping(value = TimeSheetURLConstants.COPY_TIME_SHEET_EMP_REG_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetEmpRegResp> copyTimeSheetEmpDetails(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<TimeSheetEmpRegResp>(mwTimeSheetService.copyTimeSheetEmpDetails(timeSheetGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.ADD_EMP_REG_TO_TIME_SHEET, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetDetailResp> addEmpRegToTimeSheet(
            @RequestBody TimeSheetEmpRegSaveReq timeSheetEmpRegSaveReq) {
        return new ResponseEntity<TimeSheetDetailResp>(mwTimeSheetService.addEmpRegToTimeSheet(timeSheetEmpRegSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_PROJ_SETTINGS_TIMESHEET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getProjSettingsTimeSheetDetails(
            @RequestBody TimeSheetGetReq timeSheetGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(mwTimeSheetService.getProjSettingsTimeSheetDetails(timeSheetGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_TIME_SHEET_COSTCODE_WAGE_MAP, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetWageCostCodeMap> timeSheetWageCodeMap(
            @RequestBody TimeSheetGetMapReq timeSheetGetMapReq) {
        return new ResponseEntity<TimeSheetWageCostCodeMap>(mwTimeSheetService.timeSheetWageCodeMap(timeSheetGetMapReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_TIME_SHEET_ONLOAD_INDIVIDUALS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetResp> getTimeSheetOnLoadIndividuals(@RequestBody TimeSheetGetReq timeSheetGetReq) {
        TimeSheetResp timeSheetResp = mwTimeSheetService.getTimeSheetOnLoadInddviduls(timeSheetGetReq);
        populateTimesheetResp(timeSheetGetReq, timeSheetResp);
        return new ResponseEntity<TimeSheetResp>(timeSheetResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = TimeSheetURLConstants.GET_CREATED_TIME_SHEETS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetResp> getCreatedTimeSheets(@RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
        TimeSheetResp timeSheetResp = mwTimeSheetService.getCreatedTimeSheets(employeeAttendanceRecordSheetsSearchReq);
        //populateTimesheetResp(timeSheetGetReq, timeSheetResp);
        return new ResponseEntity<TimeSheetResp>(timeSheetResp, HttpStatus.OK);
    }

    @RequestMapping(value = TimeSheetURLConstants.GET_SUBMITTED_TIME_SHEETS, method = RequestMethod.POST)
    public ResponseEntity<TimeSheetResp> getSubmittedTimeSheets(@RequestBody EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
        TimeSheetResp timeSheetResp = mwTimeSheetService.getSubmittedTimeSheets(employeeAttendanceRecordSheetsSearchReq);
        return new ResponseEntity<TimeSheetResp>(timeSheetResp, HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_TIMESHEET_DAILY_REPORT)
    public ResponseEntity<TimeSheetDetailResp> getTimeSheetDailyReport(@RequestBody TimeSheetGetReq timeSheetGetReq) {
    	TimeSheetDetailResp timeSheetDetailResp = mwTimeSheetService.getCrewTimeSheetDetailsForApproval(timeSheetGetReq);
        return new ResponseEntity<TimeSheetDetailResp>(timeSheetDetailResp, HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_TIMESHEET_REQ_USERS)
    public ResponseEntity<List<LabelKeyTO>> getTimeSheetReqUserReport(
            @RequestBody TimeSheetReqUserGetReq timeSheetReqUserGetReq) {
        return new ResponseEntity<>(mwTimeSheetService.getTimeSheetReqUserReport(timeSheetReqUserGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_TIMESHEET_APPROVESTATUS_REPORT)
    public ResponseEntity<List<TimesheetReportTO>> getTimeSheetApprStatusReport(
            @RequestBody TimeSheetApprStatusGetReq timeSheetApprStatusGetReq) {
        return new ResponseEntity<>(mwTimeSheetService.getTimeSheetApprStatusReport(timeSheetApprStatusGetReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_PERIODICAL_REPORT)
    public ResponseEntity<List<ManPowerActualHrsTO>> getManpowerPeriodicalReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq) {
        return new ResponseEntity<>(mwTimeSheetService.getManpowerPeriodicalReport(manpowerPeroidicalHrsGetReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_DATE_WISE_HRS_REPORT)
    public ResponseEntity<List<ManPowerActualHrsTO>> getManpowerDateWiseActualHrsReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerDateWiseHrsGetReq) {
        return new ResponseEntity<>(mwTimeSheetService.getManpowerDateWiseHrsReport(manpowerDateWiseHrsGetReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_COST_CODE_WISE_REPORT)
    public ResponseEntity<List<ManPowerCostCodeDailyReportTO>> getManpowerCostCodeWiseReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerCostCodeGetReq) {
        return new ResponseEntity<>(mwTimeSheetService.getManpowerCostCodeWiseReport(manpowerCostCodeGetReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_ACTUAL_STANDARD_REPORT)
    public ResponseEntity<ManPowerStandardHrsResp> getManpowerActualStandardReport(
            @RequestBody ManpowerStandardHrsGetReq manpowerStandardHrsGetReq) {
        return new ResponseEntity<>(mwTimeSheetService.getManpowerActualStandardReport(manpowerStandardHrsGetReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_IDLE_HRS_REPORT)
    public ResponseEntity<List<ManPowerCostCodeDailyReportTO>> getPlantUtilisationRecordsReport(
            @RequestBody ManpowerStandardHrsGetReq manpowerIdleHrsGetReq) {
        return new ResponseEntity<>(mwTimeSheetService.getManpowerIdleHrsReport(manpowerIdleHrsGetReq), HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_CURRENT_EMPLOYEE_REPORT)
    public ResponseEntity<List<CurrentEmployeeDetails>> getManpowerCurrentEmployeeReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerCurrentEmpGetReq) {
        return new ResponseEntity<>(mwTimeSheetService.getManpowerCurrentEmployeeReport(manpowerCurrentEmpGetReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_MANPOWER_ACTUAL_EARNED_REPORT)
    public ResponseEntity<List<ManPowerPlannedValuesTO>> getManpowerActualAndEarnedValuesReport(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerCurrentEmpGetReq) {
        return new ResponseEntity<>(mwTimeSheetService.getManpowerPlannedAndEarnedValues(manpowerCurrentEmpGetReq),
                HttpStatus.OK);
    }
    
    @PostMapping(value = TimeSheetURLConstants.GET_PROJ_EARNED_VALUES)
    public ResponseEntity<List<ProjectEarnedValueDetails>> getProjEarnedValues(
            @RequestBody ManpowerPeroidicalHrsGetReq manpowerCurrentEmpGetReq) {
        return new ResponseEntity<>(mwTimeSheetService.getProjEarnedValues(manpowerCurrentEmpGetReq),
                HttpStatus.OK);
    }
}
