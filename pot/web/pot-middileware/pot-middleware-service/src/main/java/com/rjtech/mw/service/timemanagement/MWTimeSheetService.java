package com.rjtech.mw.service.timemanagement;

import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.req.NotificationSaveReq;
import com.rjtech.common.resp.AppResp;
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

public interface MWTimeSheetService {

    public TimeSheetResp getTimeSheetOnLoad(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getCrewTimeSheet(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getIndividualTimeSheet(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getCrewTimeSheets(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getIndividualTimeSheets(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getCrewTimeSheetForApproval(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetResp getIndividualTimeSheetForApproval(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp getIndividualsFromTimeSheet(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp getAllIndividualsFromTimeSheet(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetDetailResp getCrewTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetDetailResp getCrewTimeSheetDetailsForApproval(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetDetailResp getIndividualTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetDetailResp getIndividualTimeSheetDetailsForApproval(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpDetailResp getTimeSheetEmpDetails(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetDetailResp saveCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq);

    public TimeSheetDetailResp saveApproveCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq);

    public TimeSheetDetailResp submitCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq);

    public TimeSheetDetailResp approveCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq);

    public TimeSheetDetailResp saveIndividualTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq);

    public TimeSheetDetailResp submitIndividualTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq);

    public TimeSheetDetailResp approveIndividualTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq);

    public TimeSheetEmpTaskResp saveTimeSheetEmpTasks(TimeSheetEmpTaskSaveReq timeSheetEmpTaskSaveReq);

    public TimeSheetEmpExpenseResp saveTimeSheetExpenses(TimeSheetEmpExpenseSaveReq timeSheetEmpExpenseSaveReq);

    public TimeSheetProjSettingResp getProjSettingsForTimeSheet(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp getEmpRegDetails(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp getOtherCrewEmpAttendance(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetEmpRegResp getCrewAttendanceForIndividuals(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetDetailResp addEmpRegToTimeSheet(TimeSheetEmpRegSaveReq timeSheetEmpRegSaveReq);

    public TimeSheetEmpRegResp copyTimeSheetEmpDetails(TimeSheetGetReq timeSheetGetReq);

    public LabelKeyTOResp getProjSettingsTimeSheetDetails(TimeSheetGetReq timeSheetGetReq);

    public TimeSheetWageCostCodeMap timeSheetWageCodeMap(TimeSheetGetMapReq timeSheetGetMapReq);

    public TimeSheetResp getTimeSheetOnLoadInddviduls(TimeSheetGetReq timeSheetGetReq);
    
    public TimeSheetResp getCreatedTimeSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
    
    public TimeSheetResp getSubmittedTimeSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq);
    
    public List<LabelKeyTO> getTimeSheetReqUserReport(TimeSheetReqUserGetReq timeSheetReqUserGetReq);
    
    public List<TimesheetReportTO> getTimeSheetApprStatusReport(TimeSheetApprStatusGetReq timeSheetApprStatusGetReq);
    
    public List<ManPowerActualHrsTO> getManpowerPeriodicalReport(ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq);
    
    public List<ManPowerActualHrsTO> getManpowerDateWiseHrsReport(ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq);
   
    public List<ManPowerCostCodeDailyReportTO> getManpowerCostCodeWiseReport(ManpowerPeroidicalHrsGetReq manpowerCostCodeGetReq);
    
    public ManPowerStandardHrsResp getManpowerActualStandardReport(ManpowerStandardHrsGetReq manpowerStandardHrsGetReq);

    public List<ManPowerCostCodeDailyReportTO> getManpowerIdleHrsReport(ManpowerStandardHrsGetReq manpowerIdleHrsGetReq);
    
    public List<CurrentEmployeeDetails> getManpowerCurrentEmployeeReport(ManpowerPeroidicalHrsGetReq manpowerCurrentEmpGetReq);
    
    public List<ManPowerPlannedValuesTO> getManpowerPlannedAndEarnedValues(ManpowerPeroidicalHrsGetReq manpowerPlannedValuesReq);
    
    public List<ProjectEarnedValueDetails> getProjEarnedValues(ManpowerPeroidicalHrsGetReq manpowerActualValuesReq);
    
    public TimeSheetDetailResp getCopyCrewTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq);
}
