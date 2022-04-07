package com.rjtech.timemanagement.timesheet.service;

import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.manpower.dashboards.dto.ProjectEarnedValueDetails;
import com.rjtech.timemanagement.manpower.reports.dto.CurrentEmployeeDetails;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerActualHrsTO;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerCostCodeDailyReportTO;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerPlannedValuesTO;
import com.rjtech.timemanagement.manpower.reports.req.ManpowerPeroidicalHrsGetReq;
import com.rjtech.timemanagement.manpower.reports.req.ManpowerStandardHrsGetReq;
import com.rjtech.timemanagement.manpower.reports.resp.ManPowerStandardHrsResp;
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

public interface TimeSheetService {

    public TimeSheetResp getTimeSheetOnLoad(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getCrewTimeSheet(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getIndividualTimeSheet(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getCrewTimeSheets(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getIndividualTimeSheets(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getCrewTimeSheetForApproval(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getIndividualTimeSheetForApproval(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getTimeSheetById(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp getIndividualsFromTimeSheet(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp getAllIndividualsFromTimeSheet(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetDetailResp getCrewTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetDetailResp getIndividualTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetProjSettingResp getProjSettingsForTimeSheet(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp getEmpRegDetails(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp getOtherCrewEmpAttendance(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp getCrewAttendanceForIndividuals(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp copyTimeSheetEmpDetails(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetDetailResp addEmpRegToTimeSheet(TimeSheetEmpRegSaveReq timeSheetEmpRegSaveReq);

    public TimeSheetEmpDetailResp getTimeSheetEmpDetails(TimeSheetGetReq timeSheetGetReq);

    public Map<Long, Boolean> saveCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq);

    public Map<Long, Boolean> saveApproveCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq);

    public Map<Long, Boolean> saveIndividualTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq);

    public void submitCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq);

    public void submitIndividualTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq);

    public void approveTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq);

    public void saveTimeSheetEmpTasks(TimeSheetEmpTaskSaveReq timeSheetEmpTaskSaveReq);

    public void saveTimeSheetEmpExpenses(TimeSheetEmpExpenseSaveReq timeSheetEmpExpenseSaveReq);

    public TimeSheetEmpTaskResp getTimeSheetEmpTasks(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpExpenseResp getTimeSheetEmpExpenses(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetDetailResp getCrewTimeSheetDetailsForApproval(TimeSheetGetReq timeSheetGetReq, boolean forReports);

    public TimeSheetDetailResp getIndividualTimeSheetDetailsForApproval(TimeSheetGetReq timeSheetGetReq);

    public LabelKeyTOResp getProjSettingsTimeSheetDetails(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetWageCostCodeMap timeSheetWageCodeMap(TimeSheetGetMapReq timeSheetGetMapReq);

    public TimeSheetResp getTimeSheetOnLoadInddviduls(TimeSheetGetReq timeSheetGetReq);

    public List<LabelKeyTO> getTimeSheetReqUserReport(TimeSheetReqUserGetReq timeSheetReqUserGetReq);

    public List<TimesheetReportTO> getTimeSheetApprStatusReport(TimeSheetApprStatusGetReq timeSheetApprStatusGetReq);

    public List<ManPowerActualHrsTO> getManpowerPeriodicalReport(
            ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq);

    public List<ManPowerActualHrsTO> getManpowerDateWiseHrsReport(
            ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq);

    public List<ManPowerCostCodeDailyReportTO> getManpowerCostCodeWiseReport(
            ManpowerPeroidicalHrsGetReq manpowerCostCodeGetReq);

    public ManPowerStandardHrsResp getManpowerActualStandardReport(ManpowerStandardHrsGetReq manpowerStandardHrsGetReq);

    public List<ManPowerCostCodeDailyReportTO> getManpowerIdleHrsReport(
            ManpowerStandardHrsGetReq manpowerIdleHrsGetReq);

    public List<CurrentEmployeeDetails> getManpowerCurrentEmployeeReport(
            ManpowerPeroidicalHrsGetReq manpowerCurrentEmpGetReq);

    public List<ManPowerPlannedValuesTO> getManpowerPlannedAndEarnedValues(
            ManpowerPeroidicalHrsGetReq manpowerPlannedValuesReq);

    public List<ProjectEarnedValueDetails> getProjEarnedValues(ManpowerPeroidicalHrsGetReq manpowerActualValuesReq);
    
    public TimeSheetResp getCreatedTimeSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
    public TimeSheetResp getSubmittedTimeSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
    public TimeSheetDetailResp getCopyCrewTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq);
}