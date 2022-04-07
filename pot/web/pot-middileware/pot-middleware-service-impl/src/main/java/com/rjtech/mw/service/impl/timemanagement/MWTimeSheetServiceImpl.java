package com.rjtech.mw.service.impl.timemanagement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.req.NotificationSaveReq;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.timemanagement.MWTimeSheetService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;
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

@Service(value = "mwTimeSheetService")
@RJSService(modulecode = "mwTimeSheetService")
@Transactional
public class MWTimeSheetServiceImpl extends RestConfigServiceImpl implements MWTimeSheetService {

    public TimeSheetResp getTimeSheetOnLoad(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_TIME_SHEET_ONLOAD);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetResp.class);
    }

    public TimeSheetResp getCrewTimeSheet(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_CREW_TIME_SHEET);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetResp.class);
    }

    public TimeSheetResp getIndividualTimeSheet(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetResp.class);
    }

    public TimeSheetResp getCrewTimeSheets(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_CREW_TIME_SHEETS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetResp.class);
    }

    public TimeSheetResp getIndividualTimeSheets(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEETS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetResp.class);
    }

    public TimeSheetResp getCrewTimeSheetForApproval(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_CREW_TIME_SHEET_FOR_APPROVAL);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetResp.class);
    }

    public TimeSheetResp getIndividualTimeSheetForApproval(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL
                        + TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET_FOR_APPROVAL);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetResp.class);
    }

    public TimeSheetEmpRegResp getIndividualsFromTimeSheet(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_INDIVIDUALS_FROM_TIME_SHEET);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetEmpRegResp.class);
    }

    public TimeSheetEmpRegResp getAllIndividualsFromTimeSheet(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_ALL_INDIVIDUALS_FROM_TIME_SHEET);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetEmpRegResp.class);
    }

    public TimeSheetDetailResp getCrewTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL
                        + TimeSheetURLConstants.GET_CREW_TIME_SHEET_DETAILS_FOR_SUBMISSION);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }
    
    public TimeSheetDetailResp getCopyCrewTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL
                        + TimeSheetURLConstants.GET_COPY_CREW_TIME_SHEET_DETAILS_FOR_SUBMISSION);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetDetailResp getCrewTimeSheetDetailsForApproval(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL
                        + TimeSheetURLConstants.GET_CREW_TIME_SHEET_DETAILS_FOR_APPROVAL);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetDetailResp getIndividualTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL
                        + TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET_DETAILS_FOR_SUBMISSION);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetDetailResp getIndividualTimeSheetDetailsForApproval(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL
                        + TimeSheetURLConstants.GET_INDIVIDUAL_TIME_SHEET_DETAILS_FOR_APPROVAL);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetEmpDetailResp getTimeSheetEmpDetails(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_TIME_SHEET_EMP_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetEmpDetailResp.class);
    }

    public TimeSheetDetailResp saveCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetDtlSaveReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.SAVE_CREW_TIME_SHEET_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetDetailResp saveApproveCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetDtlSaveReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.SAVE_APPROVE_CREW_TIME_SHEET_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetDetailResp submitCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetDtlSaveReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.SUBMIT_CREW_TIME_SHEET_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetDetailResp approveCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetDtlSaveReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.APPROVE_CREW_TIME_SHEET_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetDetailResp saveIndividualTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetDtlSaveReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.SAVE_INDIVIDUAL_TIME_SHEET_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetDetailResp submitIndividualTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetDtlSaveReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.SUBMIT_INDIVIDUAL_TIME_SHEET_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetDetailResp approveIndividualTimeSheetDetails(TimeSheetDtlSaveReq timeSheetDtlSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetDtlSaveReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL
                        + TimeSheetURLConstants.APPROVE_INDIVIDUAL_TIME_SHEET_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetEmpTaskResp saveTimeSheetEmpTasks(TimeSheetEmpTaskSaveReq timeSheetEmpTaskSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetEmpTaskSaveReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.SAVE_TIME_SHEET_EMP_TASKS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetEmpTaskResp.class);
    }

    public TimeSheetEmpExpenseResp saveTimeSheetExpenses(TimeSheetEmpExpenseSaveReq timeSheetEmpExpenseSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetEmpExpenseSaveReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.SAVE_TIMEE_SHEET_EMP_EXPENSES);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetEmpExpenseResp.class);
    }

    public TimeSheetProjSettingResp getProjSettingsForTimeSheet(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_PROJ_SETTINGS_FOR_TIME_SHEET);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetProjSettingResp.class);
    }

    public TimeSheetEmpRegResp getEmpRegDetails(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_EMP_REG_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetEmpRegResp.class);
    }

    public TimeSheetEmpRegResp getOtherCrewEmpAttendance(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL
                        + TimeSheetURLConstants.GET_OTHER_CREW_ATTENDACNE_FOR_TIME_SHEET);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetEmpRegResp.class);
    }

    public TimeSheetEmpRegResp getCrewAttendanceForIndividuals(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_CREW_ATTENDACNE_FOR_INDIVIDUALS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetEmpRegResp.class);
    }

    public TimeSheetDetailResp addEmpRegToTimeSheet(TimeSheetEmpRegSaveReq timeSheetEmpRegSaveReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetEmpRegSaveReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.ADD_EMP_REG_TO_TIME_SHEET);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetDetailResp.class);
    }

    public TimeSheetEmpRegResp copyTimeSheetEmpDetails(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.COPY_TIME_SHEET_EMP_REG_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetEmpRegResp.class);
    }

    public LabelKeyTOResp getProjSettingsTimeSheetDetails(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_PROJ_SETTINGS_TIMESHEET_DETAILS);
        return AppUtils.fromJson(strResponse.getBody(), LabelKeyTOResp.class);
    }

    public TimeSheetWageCostCodeMap timeSheetWageCodeMap(TimeSheetGetMapReq timeSheetGetMapReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetMapReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_TIME_SHEET_COSTCODE_WAGE_MAP);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetWageCostCodeMap.class);
    }

    public TimeSheetResp getTimeSheetOnLoadInddviduls(TimeSheetGetReq timeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetGetReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_TIME_SHEET_ONLOAD_INDIVIDUALS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetResp.class);
    }
    
    public TimeSheetResp getCreatedTimeSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(employeeAttendanceRecordSheetsSearchReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_CREATED_TIME_SHEETS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetResp.class);
    }
    
    public TimeSheetResp getSubmittedTimeSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(employeeAttendanceRecordSheetsSearchReq),
                TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_SUBMITTED_TIME_SHEETS);
        return AppUtils.fromJson(strResponse.getBody(), TimeSheetResp.class);
    }

	public List<LabelKeyTO> getTimeSheetReqUserReport(TimeSheetReqUserGetReq timeSheetReqUserGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetReqUserGetReq),
				TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_TIMESHEET_REQ_USERS);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<TimesheetReportTO> getTimeSheetApprStatusReport(TimeSheetApprStatusGetReq timeSheetApprStatusGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(timeSheetApprStatusGetReq),
				TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_TIMESHEET_APPROVESTATUS_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<ManPowerActualHrsTO> getManpowerPeriodicalReport(ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(manpowerPeroidicalHrsGetReq),
				TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_MANPOWER_PERIODICAL_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<ManPowerActualHrsTO> getManpowerDateWiseHrsReport(
			ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(manpowerPeroidicalHrsGetReq),
				TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_MANPOWER_DATE_WISE_HRS_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<ManPowerCostCodeDailyReportTO> getManpowerCostCodeWiseReport(
			ManpowerPeroidicalHrsGetReq manpowerCostCodeGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(manpowerCostCodeGetReq),
				TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_MANPOWER_COST_CODE_WISE_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public ManPowerStandardHrsResp getManpowerActualStandardReport(ManpowerStandardHrsGetReq manpowerStandardHrsGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(manpowerStandardHrsGetReq),
				TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_MANPOWER_ACTUAL_STANDARD_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), ManPowerStandardHrsResp.class);
	}

	@Override
	public List<ManPowerCostCodeDailyReportTO> getManpowerIdleHrsReport(
			ManpowerStandardHrsGetReq manpowerIdleHrsGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(manpowerIdleHrsGetReq),
				TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_MANPOWER_IDLE_HRS_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<CurrentEmployeeDetails> getManpowerCurrentEmployeeReport(
			ManpowerPeroidicalHrsGetReq manpowerCurrentEmpGetReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(manpowerCurrentEmpGetReq),
				TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_MANPOWER_CURRENT_EMPLOYEE_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<ManPowerPlannedValuesTO> getManpowerPlannedAndEarnedValues(
			ManpowerPeroidicalHrsGetReq manpowerPlannedValuesReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(manpowerPlannedValuesReq),
				TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_MANPOWER_ACTUAL_EARNED_REPORT);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

	@Override
	public List<ProjectEarnedValueDetails> getProjEarnedValues(ManpowerPeroidicalHrsGetReq manpowerActualValuesReq) {
		ResponseEntity<String> strResponse = null;
		strResponse = getTimeManagementPOSTRestTemplate(AppUtils.toJson(manpowerActualValuesReq),
				TimeSheetURLConstants.TIME_SHEET_PARH_URL + TimeSheetURLConstants.GET_PROJ_EARNED_VALUES);
		return AppUtils.fromJson(strResponse.getBody(), List.class);
	}

}