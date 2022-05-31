package com.rjtech.timemanagement.timesheet.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjtech.centrallib.repository.EmpWageRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.service.impl.CommonEmailServiceImpl;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.TimeManagentStatus;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.dto.TimeSheetNotificationsTO;
import com.rjtech.notification.model.TimeSheetNotificationsEntity;
import com.rjtech.notification.repository.TimeSheetNotificationsRepositoryCopy;
import com.rjtech.notification.service.handler.TimeSheetNotificationsHandlerCopy;
import com.rjtech.proj.settings.common.service.impl.copy.EmployeeChargeoutRatesService;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjCrewRepositoryCopy;
import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.projschedule.model.ResourceAssignmentDataValueEntity;
import com.rjtech.projschedule.repository.ResourceAssignmentDataValueRepositoryCopy;
import com.rjtech.projsettings.model.TimesheetNormalTimeEntity;
import com.rjtech.projsettings.repository.ProjTimeSheetRepository;
import com.rjtech.projsettings.repository.copy.ProjTimeSheetApprRepositoryCopy;
import com.rjtech.projsettings.repository.copy.ProjTimeSheetRepositoryCopy;
import com.rjtech.projsettings.repository.copy.ProjTimeSheetWeekRepositoryCopy;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpChargeOutRateRepositoryCopy;
import com.rjtech.register.repository.emp.EmpContactDtlRepositoryCopy;
import com.rjtech.register.repository.emp.EmpRegisterRepositoryCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceEntity;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceProcRepository;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceRepository;
import com.rjtech.timemanagement.manpower.dashboards.dto.ProjectEarnedValueDetails;
import com.rjtech.timemanagement.manpower.reports.dto.CurrentEmployeeDetails;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerActualHrsTO;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerCostCodeDailyReportTO;
import com.rjtech.timemanagement.manpower.reports.dto.ManPowerPlannedValuesTO;
import com.rjtech.timemanagement.manpower.reports.dto.ReportHoursTO;
import com.rjtech.timemanagement.manpower.reports.handler.ManPowerActualHrsReportHandler;
import com.rjtech.timemanagement.manpower.reports.handler.ManPowerCostWiseReportHandler;
import com.rjtech.timemanagement.manpower.reports.handler.ManPowerCurrentEmployeeHandler;
import com.rjtech.timemanagement.manpower.reports.handler.ManPowerPlannedValuesReportHandler;
import com.rjtech.timemanagement.manpower.reports.handler.ManPowerStandardHrsReportHandler;
import com.rjtech.timemanagement.manpower.reports.req.ManpowerPeroidicalHrsGetReq;
import com.rjtech.timemanagement.manpower.reports.req.ManpowerStandardHrsGetReq;
import com.rjtech.timemanagement.manpower.reports.resp.ManPowerStandardHrsResp;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpDtlTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpExpenseTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpRegTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpTaskTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetEmpWorkDtlTO;
import com.rjtech.timemanagement.timesheet.dto.TimeSheetTO;
import com.rjtech.timemanagement.timesheet.dto.TimesheetReportTO;
import com.rjtech.timemanagement.timesheet.model.TimeSheetAdditionalTimeEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpDtlEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpExpenseEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpTaskEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpWorkDtlEntity;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEntity;
import com.rjtech.timemanagement.timesheet.report.req.TimeSheetApprStatusGetReq;
import com.rjtech.timemanagement.timesheet.report.req.TimeSheetReqUserGetReq;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetEmpDtlRepository;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetEmpExpenseRepository;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetEmpRepository;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetEmpTaskRepository;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetProcRepository;
import com.rjtech.timemanagement.timesheet.repository.TimeSheetRepository;
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
import com.rjtech.timemanagement.timesheet.service.handler.TimeSheetEmpHandler;
import com.rjtech.timemanagement.util.TimeManagementUtil;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpWageEntity;
import com.rjtech.timemanagement.workdairy.repository.EmpCostWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.EmpWageWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.EmpWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.ProgressWorkDairyRepository;
import com.rjtech.timemanagement.workdairy.repository.ProjGeneralMstrRepositoryCopy;
import com.rjtech.user.repository.CommonUserProjectsRepository;

@Service(value = "timeSheetService")
@RJSService(modulecode = "timeSheetService")
@Transactional
public class TimeSheetServiceImpl implements TimeSheetService {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetServiceImpl.class);

    @Autowired
    private CommonEmailServiceImpl commonEmail;

    @Autowired
    private TimeSheetNotificationsRepositoryCopy timeSheetNotificationsRepository;

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private TimeSheetEmpRepository timeSheetEmpRepository;

    @Autowired
    private TimeSheetEmpDtlRepository timeSheetEmpDtlRepository;

    @Autowired
    private EmpRegisterRepositoryCopy empRegisterRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private ProjCrewRepositoryCopy projCrewRepository;

    @Autowired
    private ProjTimeSheetWeekRepositoryCopy projTimeSheetWeekRepositoryCopy;

    @Autowired
    private EmpWorkDairyRepository empWorkDairyRepository;
    
    @Autowired
    private ProjTimeSheetRepository projTimeSheetRepository;
    
    @Autowired
    private ResourceAssignmentDataValueRepositoryCopy resourceAssignmentDataValueRepository;

    String epsName = null;
    String projName = null;
    String ccEmail;
    String toEmail;
    String toSubject;
    String text;

    @Autowired
    private TimeSheetEmpTaskRepository timeSheetEmpTaskRepository;

    @Autowired
    private TimeSheetEmpExpenseRepository timeSheetEmpExpenseRepository;

    @Autowired
    private TimeSheetProcRepository timeSheetProcRepository;

    @Autowired
    private EmpAttendanceProcRepository empAttendanceProcRepository;

    @Autowired
    private ProjCostItemRepositoryCopy projCostItemRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private EmpWageRepository empWageRepository;

    @Autowired
    private ProjGeneralMstrRepositoryCopy projGeneralRepositoryCopy;

    @Autowired
    private EmpAttendanceRepository empAttendanceRepository;

    @Autowired
    private ProjTimeSheetRepositoryCopy projTimeSheetRepositoryCopy;

    @Autowired
    private ProjTimeSheetApprRepositoryCopy projTimeSheetApprRepositoryCopy;

    @Autowired
    private ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy;

    @Autowired
    private EmpWageWorkDairyRepository empWageWorkDairyRepository;

    @Autowired
    private EmpChargeOutRateRepositoryCopy empChargeOutRateRepositoryCopy;

    @Autowired
    private EmpCostWorkDairyRepository empCostWorkDairyRepository;

    @Autowired
    private EmpContactDtlRepositoryCopy empContactDtlRepositoryCopy;

    @Autowired
    private ProgressWorkDairyRepository progressWorkDairyRepository;

    @Autowired
    private EmployeeChargeoutRatesService employeeChargeoutRatesService;
    
    @Autowired
    private CommonUserProjectsRepository userProjectsRepository;

    public TimeSheetResp getTimeSheetOnLoad(TimeSheetGetReq timeSheetGetReq) {
        TimeSheetResp timeSheetResp = new TimeSheetResp();
        Calendar c = Calendar.getInstance();
        Integer differncdeDays = 0;
        if (timeSheetGetReq.getAdditional() != null && timeSheetGetReq.getAdditional().intValue() == 1) {
            differncdeDays = 7;
        } else {
            differncdeDays = TimeManagementUtil.getDateDiffernce(timeSheetGetReq.getWeekStartDate(),
                    timeSheetGetReq.getWeekEndDate());
        }
        for (int i = 0; i <= differncdeDays; i++) {
            c.setTime(timeSheetGetReq.getWeekStartDate());
            c.add(Calendar.DATE, i);
            timeSheetResp.getTimeSheetDays().add(TimeManagementUtil.getDayWithDDMMMYYYYFormat(c.getTime()));
        }
        return timeSheetResp;
    }

    public TimeSheetResp getCrewTimeSheet(TimeSheetGetReq timeSheetGetReq) {
        List<TimeSheetEntity> timeSheetEntities = timeSheetRepository.findCrewTimeSheet(timeSheetGetReq.getProjId(),
                timeSheetGetReq.getCrewId(), timeSheetGetReq.getAdditional(), timeSheetGetReq.getStatus(),
                timeSheetGetReq.getWeekCommenceDay());
        TimeSheetResp timeSheetResp = populateTimeSheet(timeSheetGetReq, timeSheetEntities);
        return timeSheetResp;
    }

    public TimeSheetResp getIndividualTimeSheet(TimeSheetGetReq timeSheetGetReq) {
        List<TimeSheetEntity> timeSheetEntities = timeSheetRepository.findIndividualTimeSheet(
                timeSheetGetReq.getProjId(), timeSheetGetReq.getEmpId(), timeSheetGetReq.getAdditional(),
                timeSheetGetReq.getStatus(), timeSheetGetReq.getWeekCommenceDay());
        TimeSheetResp timeSheetResp = populateTimeSheet(timeSheetGetReq, timeSheetEntities);
        return timeSheetResp;
    }

    public TimeSheetResp getCrewTimeSheets(TimeSheetGetReq timeSheetGetReq) {
        List<TimeSheetEntity> timeSheetEntities = timeSheetRepository.findCrewTimeSheets(timeSheetGetReq.getProjId(),
                timeSheetGetReq.getCrewId(), timeSheetGetReq.getStatus(), timeSheetGetReq.getWeekCommenceDay());
        TimeSheetResp timeSheetResp = populateTimeSheet(timeSheetGetReq, timeSheetEntities);
        return timeSheetResp;
    }

    public TimeSheetResp getIndividualTimeSheets(TimeSheetGetReq timeSheetGetReq) {
    	log.info("------------------------------ timeSheetGetReq.getProjId() " + timeSheetGetReq.getProjId());
    	log.info("------------------------------ timeSheetGetReq.getEmpId() " + timeSheetGetReq.getEmpId());
    	log.info("------------------------------ timeSheetGetReq.getStatus() " + timeSheetGetReq.getStatus());
    	log.info("------------------------------ timeSheetGetReq.getWeekCommenceDay() " + timeSheetGetReq.getWeekCommenceDay());
        List<TimeSheetEntity> timeSheetEntities = timeSheetRepository.findIndividualTimeSheets(
                timeSheetGetReq.getProjId(), timeSheetGetReq.getEmpId(), timeSheetGetReq.getStatus(),
                timeSheetGetReq.getWeekCommenceDay());
        TimeSheetResp timeSheetResp = populateTimeSheet(timeSheetGetReq, timeSheetEntities);
        return timeSheetResp;
    }

    public TimeSheetResp getCrewTimeSheetForApproval(TimeSheetGetReq timeSheetGetReq) {
        List<TimeSheetEntity> timeSheetEntities = timeSheetRepository.findCrewTimeSheetForApproval(
                timeSheetGetReq.getProjId(), timeSheetGetReq.getCrewId(), timeSheetGetReq.getAdditional(),
                timeSheetGetReq.getStatus(), timeSheetGetReq.getWeekCommenceDay());
        TimeSheetResp timeSheetResp = populateTimeSheet(timeSheetGetReq, timeSheetEntities);
        return timeSheetResp;
    }

    public TimeSheetResp getIndividualTimeSheetForApproval(TimeSheetGetReq timeSheetGetReq) {
        List<TimeSheetEntity> timeSheetEntities = timeSheetRepository.findIndividualTimeSheetForApproval(
                timeSheetGetReq.getProjId(), timeSheetGetReq.getEmpId(), timeSheetGetReq.getAdditional(),
                timeSheetGetReq.getStatus(), timeSheetGetReq.getWeekCommenceDay());
        TimeSheetResp timeSheetResp = populateTimeSheet(timeSheetGetReq, timeSheetEntities);
        return timeSheetResp;
    }

    public TimeSheetResp getTimeSheetById(TimeSheetGetReq timeSheetGetReq) {
        TimeSheetEntity timeSheetEntity = timeSheetRepository.findTimeSheetById(timeSheetGetReq.getTimeSheetId(),
                timeSheetGetReq.getStatus());
        TimeSheetResp timeSheetResp = new TimeSheetResp();
        timeSheetResp.setTimeSheetTO(TimeSheetEmpHandler.convertEntityToPOJO(timeSheetEntity));
        return timeSheetResp;
    }

    public TimeSheetProjSettingResp getProjSettingsForTimeSheet(TimeSheetGetReq timeSheetGetReq) {
        Long projId = timeSheetGetReq.getProjId();

        Object[] timsSheetDetails = projTimeSheetWeekRepositoryCopy.getTimesheetWeekDetails(projId);
        TimeSheetProjSettingResp timeSheetResp = new TimeSheetProjSettingResp();
        if (timsSheetDetails != null && timsSheetDetails.length > 0) {
            Object[] result = (Object[]) timsSheetDetails[0];
            Integer maxHrs = projGeneralRepositoryCopy.getMaxHrsOfProject(projId);
            timeSheetResp.setWeekSeqId((Long) result[0]);
            timeSheetResp.setWeeekStartDay(Integer.valueOf((String) result[1]));
            timeSheetResp.setWeeekEndDay(Integer.valueOf((String) result[2]));
            timeSheetResp.setMaxHrs(maxHrs);

        }

        return timeSheetResp;
    }

    public TimeSheetEmpRegResp getIndividualsFromTimeSheet(TimeSheetGetReq timeSheetGetReq) {
        List<Long> empList = timeSheetRepository.getIndividualsFromTimeSheet(timeSheetGetReq.getProjId(),
                timeSheetGetReq.getWeekStartDate(), timeSheetGetReq.getAdditional());
        TimeSheetEmpRegResp timeSheetEmpRegResp = new TimeSheetEmpRegResp();
        for (Long empId : empList) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(empId);
            timeSheetEmpRegResp.getEmpRegLabelKeyTOs().add(labelKeyTO);
        }
        return timeSheetEmpRegResp;
    }

    public TimeSheetEmpRegResp getAllIndividualsFromTimeSheet(TimeSheetGetReq timeSheetGetReq) {

        List<Object[]> employeeDetails = empAttendanceRepository.findEmpDetailsForTimeSheet(timeSheetGetReq.getProjId());
        TimeSheetEmpRegResp timeSheetEmpRegResp = new TimeSheetEmpRegResp();
        for (Object[] emp : employeeDetails) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId((Long) emp[0]);
            labelKeyTO.setCode((String) emp[1]);
            Map<String, String> displayMap = new HashMap<>();
            displayMap.put(CommonConstants.FIRST_NAME, (String) emp[2]);
            displayMap.put(CommonConstants.LAST_NAME, (String) emp[3]);
            displayMap.put(CommonConstants.GENDER, (String) emp[4]);
            displayMap.put(CommonConstants.CLASS_TYPE, (String) emp[5]);
            //  displayMap.put(CommonConstants.PROCURE_CATG, rs.getString(7));
            displayMap.put(CommonConstants.COMPANY_CATG_NAME, (String) emp[6]);
            labelKeyTO.setDisplayNamesMap(displayMap);

            timeSheetEmpRegResp.getEmpRegLabelKeyTOs().add(labelKeyTO);
        }
        return timeSheetEmpRegResp;
    }

    public TimeSheetDetailResp getCrewTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq) {
        List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities = new ArrayList<>();
        if (CommonUtil.isNonBlankLong(timeSheetGetReq.getTimeSheetId())) {
            timeSheetEmpDtlEntities = timeSheetEmpRepository.findTimeSheetDetails(timeSheetGetReq.getTimeSheetId(),
                    timeSheetGetReq.getStatus());
        }
        List<TimeSheetEntity> timeSheetEntities = timeSheetRepository.findCrewTimeSheet(timeSheetGetReq.getProjId(),
                timeSheetGetReq.getCrewId(), timeSheetGetReq.getAdditional(), timeSheetGetReq.getStatus(),
                timeSheetGetReq.getWeekCommenceDay());
        TimeSheetDetailResp timeSheetDetailResp = populateTimeSheetDetailsTOs(timeSheetGetReq, timeSheetEmpDtlEntities,
                false);
        if (CommonUtil.isListHasData(timeSheetEntities)) {
            timeSheetDetailResp.setTimeSheetTO(TimeSheetEmpHandler.convertEntityToPOJO(timeSheetEntities.get(0)));
        }
        ObjectMapper om = new ObjectMapper();
        String jsonString = null;
		try {
			jsonString = om.writeValueAsString(timeSheetDetailResp);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("=======================================");
        log.info(jsonString);
        log.info("=======================================");
        return timeSheetDetailResp;
    }
    
    public TimeSheetDetailResp getCopyCrewTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq) {
    	System.out.println("timeSheetGetReq.getTimeSheetId()"+timeSheetGetReq.getTimeSheetId());
        List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities = new ArrayList<>();
        if (CommonUtil.isNonBlankLong(timeSheetGetReq.getTimeSheetId())) {
            timeSheetEmpDtlEntities = timeSheetEmpRepository.findTimeSheetDetails(timeSheetGetReq.getTimeSheetId(),
                    timeSheetGetReq.getStatus());
        }
        List<TimeSheetEntity> timeSheetEntities = timeSheetRepository.findCrewTimeSheet(timeSheetGetReq.getProjId(),
                timeSheetGetReq.getCrewId(), timeSheetGetReq.getAdditional(), timeSheetGetReq.getStatus(),
                timeSheetGetReq.getWeekCommenceDay());
        TimeSheetDetailResp timeSheetDetailResp = populateCopyTimeSheetDetailsTOs(timeSheetGetReq, timeSheetEmpDtlEntities,
                false);
        if (CommonUtil.isListHasData(timeSheetEntities)) {
            timeSheetDetailResp.setTimeSheetTO(TimeSheetEmpHandler.convertEntityToPOJO(timeSheetEntities.get(0)));
        }
        ObjectMapper om = new ObjectMapper();
        String jsonString = null;
		try {
			jsonString = om.writeValueAsString(timeSheetDetailResp);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("=======================================1111");
        log.info(jsonString);
        log.info("=======================================1111");
        return timeSheetDetailResp;
    }

    public TimeSheetDetailResp getIndividualTimeSheetDetailsForSubmission(TimeSheetGetReq timeSheetGetReq) {
        List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities = new ArrayList<TimeSheetEmpDtlEntity>();
        if (CommonUtil.isNonBlankLong(timeSheetGetReq.getTimeSheetId())) {
            timeSheetEmpDtlEntities = timeSheetEmpRepository.findTimeSheetDetails(timeSheetGetReq.getTimeSheetId(),
                    timeSheetGetReq.getStatus());
        }
        List<TimeSheetEntity> timeSheetEntities = timeSheetRepository.findIndividualTimeSheet(
                timeSheetGetReq.getProjId(), timeSheetGetReq.getEmpId(), timeSheetGetReq.getAdditional(),
                timeSheetGetReq.getStatus(), timeSheetGetReq.getWeekCommenceDay());
        TimeSheetDetailResp timeSheetDetailResp = populateTimeSheetDetailsTOs(timeSheetGetReq, timeSheetEmpDtlEntities,
                false);
        if (CommonUtil.isListHasData(timeSheetEntities)) {
            timeSheetDetailResp.setTimeSheetTO(TimeSheetEmpHandler.convertEntityToPOJO(timeSheetEntities.get(0)));
        }
        return timeSheetDetailResp;
    }

    public TimeSheetDetailResp getCrewTimeSheetDetailsForApproval(TimeSheetGetReq timeSheetGetReq, boolean forReports) {
        List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities = timeSheetEmpRepository
                .findTimeSheetDetails(timeSheetGetReq.getTimeSheetId(), timeSheetGetReq.getStatus());
        TimeSheetDetailResp timeSheetDetailResp = populateTimeSheetDetailsTOs(timeSheetGetReq, timeSheetEmpDtlEntities,
                forReports);

        return timeSheetDetailResp;
    }

    public TimeSheetDetailResp getIndividualTimeSheetDetailsForApproval(TimeSheetGetReq timeSheetGetReq) {
        List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities = timeSheetEmpRepository
                .findTimeSheetDetails(timeSheetGetReq.getTimeSheetId(), timeSheetGetReq.getStatus());
        TimeSheetDetailResp timeSheetDetailResp = populateTimeSheetDetailsTOs(timeSheetGetReq, timeSheetEmpDtlEntities,
                false);

        return timeSheetDetailResp;
    }
    
    private TimeSheetDetailResp populateTimeSheetDetailsTOs(TimeSheetGetReq timeSheetGetReq,
            List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities, boolean forReports) {
        Map<String, String> timeSheetDays = getTimeSheetDays(timeSheetGetReq);
        Map<String, List<Long>> attendanceDayMap = getAttendanceDayMap(timeSheetGetReq, timeSheetDays);

        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();

        TimeSheetEmpDtlTO timeSheetEmpDtlTO;
        TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO = null;
        if (CommonUtil.isBlankLong(timeSheetGetReq.getTimeSheetId())) {
            timeSheetDetailResp.getTimeSheetEmpDtlTOs()
                    .addAll(addEmpRegDetails(timeSheetGetReq, attendanceDayMap).getTimeSheetEmpDtlTOs());
        }
        for (TimeSheetEmpDtlEntity timeSheetEmpDtlEntity : timeSheetEmpDtlEntities) {
            timeSheetEmpDtlTO = TimeSheetEmpHandler.convertEmpDtlEntityToPOJO(timeSheetEmpDtlEntity);
            if (CommonUtil.isListHasData(timeSheetEmpDtlEntity.getTimeSheetEmpWorkDtlEntities())) {
                for (TimeSheetEmpWorkDtlEntity timeSheetEmpWorkDtlEntity : timeSheetEmpDtlEntity
                        .getTimeSheetEmpWorkDtlEntities()) {
                    if (!forReports || timeSheetEmpWorkDtlEntity.getApprStatus() != null) {
                        timeSheetEmpWorkDtlTO = TimeSheetEmpHandler
                                .convertEmpWorkDtlEntityToPOJO(timeSheetEmpWorkDtlEntity, forReports);
                        if(timeSheetEmpWorkDtlTO.getDay1Hrs() != null) {
                        	TimeSheetEmpHandler.enableTimeSheet1(attendanceDayMap, timeSheetEmpWorkDtlTO,
                                    timeSheetEmpDtlEntity.getEmpRegisterDtlEntity().getId());
                        }else
                        TimeSheetEmpHandler.enableTimeSheet(attendanceDayMap, timeSheetEmpWorkDtlTO,
                                timeSheetEmpDtlEntity.getEmpRegisterDtlEntity().getId());
                        timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs().add(timeSheetEmpWorkDtlTO);
                    }

                }
            } else {
                timeSheetEmpWorkDtlTO = new TimeSheetEmpWorkDtlTO();
                timeSheetEmpWorkDtlTO.setEmpDtlId(timeSheetEmpDtlTO.getId());
                TimeSheetEmpHandler.enableTimeSheet(attendanceDayMap, timeSheetEmpWorkDtlTO,
                        timeSheetEmpDtlTO.getEmpRegId());
                timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs().add(timeSheetEmpWorkDtlTO);

            }
            timeSheetDetailResp.getTimeSheetEmpDtlTOs().add(timeSheetEmpDtlTO);
        }
        return timeSheetDetailResp;
    }
    
    private TimeSheetDetailResp populateCopyTimeSheetDetailsTOs(TimeSheetGetReq timeSheetGetReq,
            List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities, boolean forReports) {
        Map<String, String> timeSheetDays = getTimeSheetDays(timeSheetGetReq);
        Map<String, List<Long>> attendanceDayMap = getAttendanceDayMap(timeSheetGetReq, timeSheetDays);
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();

        TimeSheetEmpDtlTO timeSheetEmpDtlTO;
        TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO = null;
        if (CommonUtil.isBlankLong(timeSheetGetReq.getTimeSheetId())) {
            timeSheetDetailResp.getTimeSheetEmpDtlTOs()
                    .addAll(addEmpRegDetails(timeSheetGetReq, attendanceDayMap).getTimeSheetEmpDtlTOs());
        }
        for (TimeSheetEmpDtlEntity timeSheetEmpDtlEntity : timeSheetEmpDtlEntities) {
            timeSheetEmpDtlTO = TimeSheetEmpHandler.convertEmpDtlEntityToPOJO(timeSheetEmpDtlEntity);
          List<TimeSheetEmpWorkDtlEntity> timeSheetEmpWorkDtlEntitys = timeSheetEmpDtlEntity.getTimeSheetEmpWorkDtlEntities().stream().filter(timeSheetEmpWorkDtlEntities -> { return timeSheetEmpWorkDtlEntities.getApprStatus() == null ;}).collect(Collectors.toList());
          if (CommonUtil.isListHasData(timeSheetEmpWorkDtlEntitys)) {
        	  for(TimeSheetEmpWorkDtlEntity timeSheetEmpWorkDtlEntity :timeSheetEmpWorkDtlEntitys) {
                              timeSheetEmpWorkDtlTO = TimeSheetEmpHandler
                                      .convertEmpWorkDtlEntityToPOJO(timeSheetEmpWorkDtlEntity, forReports);
                              TimeSheetEmpHandler.enableTimeSheet1(attendanceDayMap, timeSheetEmpWorkDtlTO,
                                      timeSheetEmpDtlEntity.getEmpRegisterDtlEntity().getId());
                              timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs().add(timeSheetEmpWorkDtlTO);

                      } 
              } else {
                  timeSheetEmpWorkDtlTO = new TimeSheetEmpWorkDtlTO();
                  timeSheetEmpWorkDtlTO.setEmpDtlId(timeSheetEmpDtlTO.getId());
                  TimeSheetEmpHandler.enableTimeSheet1(attendanceDayMap, timeSheetEmpWorkDtlTO,
                          timeSheetEmpDtlTO.getEmpRegId());
                  timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs().add(timeSheetEmpWorkDtlTO);

              }
           timeSheetDetailResp.getTimeSheetEmpDtlTOs().add(timeSheetEmpDtlTO);
          }	  
          
          
            
        return timeSheetDetailResp;
    }
    
    private Map<String, String> getTimeSheetDays(TimeSheetGetReq timeSheetGetReq) {
        Integer differncdeDays = TimeManagementUtil.getDateDiffernce(timeSheetGetReq.getWeekStartDate(),
                timeSheetGetReq.getWeekEndDate());
        Calendar c = Calendar.getInstance();
        Map<String, String> timeSheetDays = new HashMap<>();
        int count = 0;
        for (int i = 0; i <= differncdeDays; i++) {
            if (timeSheetGetReq.getWeekStartDate() != null) {
                c.setTime(timeSheetGetReq.getWeekStartDate());
            }
            c.add(Calendar.DATE, i);
            timeSheetDays.put(TimeManagementUtil.getDayWithDDMMMYYYYFormat(c.getTime()), "day" + ++count + "Hrs");
        }
        return timeSheetDays;
    }

    private Map<String, List<Long>> getAttendanceDayMap(TimeSheetGetReq timeSheetGetReq,
            Map<String, String> timeSheetDays) {
    	List<Object[]> attendanceDays;
    	if (timeSheetGetReq.getEmpId() == null) {
    		attendanceDays = empAttendanceRepository.getAttendanceDetailsForTimeSheet(
                    timeSheetGetReq.getProjId(), timeSheetGetReq.getWeekStartDate(), timeSheetGetReq.getWeekEndDate());
    	} else {
    		attendanceDays = empAttendanceRepository.getAttendanceDetailsForIndTimeSheet(
                    timeSheetGetReq.getProjId(), timeSheetGetReq.getEmpId(), timeSheetGetReq.getWeekStartDate(), timeSheetGetReq.getWeekEndDate());
    	}
        Map<String, List<Long>> attendanceDayMap = new HashMap<>();

        for (Object[] attendance : attendanceDays) {
            Long empId = (Long) attendance[0];
            String attendanceDate = (String) attendance[1];
            if (attendanceDayMap.get(timeSheetDays.get(attendanceDate)) != null) {
                if (timeSheetDays.get(attendanceDate) != null) {
                    attendanceDayMap.get(timeSheetDays.get(attendanceDate)).add(empId);
                }
            } else {
                if (timeSheetDays.get(attendanceDate) != null) {
                    List<Long> empIds = new ArrayList<>();
                    empIds.add(empId);
                    attendanceDayMap.put(timeSheetDays.get(attendanceDate), empIds);
                }

            }
        }
        return attendanceDayMap;
    }
    
    public TimeSheetDetailResp addEmpRegDetails(TimeSheetGetReq timeSheetGetReq,
            Map<String, List<Long>> attendanceDayMap) {
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();
        
        List<Object[]> empRegList;
        if (timeSheetGetReq.getEmpId() == null) {
        	empRegList = empAttendanceRepository.findEmpRegDetailsForTimeSheet(timeSheetGetReq.getProjId(),
                    timeSheetGetReq.getCrewId(), timeSheetGetReq.getAdditional(), timeSheetGetReq.getWeekStartDate(),
                    timeSheetGetReq.getWeekEndDate());
        } else {
        	empRegList = empAttendanceRepository.findEmpRegDetailsForIndTimeSheet(timeSheetGetReq.getProjId(),
                    timeSheetGetReq.getEmpId(), timeSheetGetReq.getAdditional(), timeSheetGetReq.getWeekStartDate(),
                    timeSheetGetReq.getWeekEndDate());
        }
        
        TimeSheetEmpDtlTO timeSheetEmpDtlTO = null;
        TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO = null;
        
        
		for (Object[] emp : empRegList) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId((Long) emp[0]);
            labelKeyTO.setCode((String) emp[1]);
            Map<String, String> displayMap = new HashMap<>();
            displayMap.put(CommonConstants.FIRST_NAME, (String) emp[2]);
            displayMap.put(CommonConstants.LAST_NAME, (String) emp[3]);
            displayMap.put(CommonConstants.GENDER, (String) emp[4]);
            displayMap.put(CommonConstants.CLASS_TYPE, (String) emp[5]);
            displayMap.put(CommonConstants.COMPANY_CATG_NAME, (String) emp[6]);
            labelKeyTO.setDisplayNamesMap(displayMap);

            timeSheetEmpDtlTO = new TimeSheetEmpDtlTO();
            timeSheetEmpDtlTO.setEmpRegId(labelKeyTO.getId());
            timeSheetEmpDtlTO.setEmpDetailLabelKeyTO(labelKeyTO);
            timeSheetEmpWorkDtlTO = new TimeSheetEmpWorkDtlTO();
            TimeSheetEmpHandler.enableTimeSheet(attendanceDayMap, timeSheetEmpWorkDtlTO,
                    timeSheetEmpDtlTO.getEmpRegId());
            timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs().add(timeSheetEmpWorkDtlTO);
            timeSheetDetailResp.getTimeSheetEmpDtlTOs().add(timeSheetEmpDtlTO);
        }
        return timeSheetDetailResp;
    }

    public TimeSheetEmpDetailResp getTimeSheetEmpDetails(TimeSheetGetReq timeSheetGetReq) {
        TimeSheetEmpDtlTO timeSheetEmpDtlTO = new TimeSheetEmpDtlTO();

        TimeSheetEmpTaskResp timeSheetEmpTaskResp = getTimeSheetEmpTasks(timeSheetGetReq);
        timeSheetEmpDtlTO.getTimeSheetEmpTaskMap().putAll(timeSheetEmpTaskResp.getTimeSheetEmpTaskMap());
        timeSheetEmpDtlTO.getTimeSheetEmpExpenseTOs()
                .addAll(getTimeSheetEmpExpenses(timeSheetGetReq).getTimeSheetEmpExpenseTOs());

        TimeSheetEmpDetailResp timeSheetEmpDetailResp = new TimeSheetEmpDetailResp();
        timeSheetEmpDetailResp.setTimeSheetEmpDtlTO(timeSheetEmpDtlTO);
        timeSheetEmpDetailResp.setTimeSheetDays(timeSheetEmpTaskResp.getTimeSheetDays());
        return timeSheetEmpDetailResp;
    }

    public TimeSheetEmpRegResp getEmpRegDetails(TimeSheetGetReq timeSheetGetReq) {
        /* List<LabelKeyTO> empRegLabelKeyTOs = timeSheetProcRepository.getEmpRegDetails(AppUserUtils.getClientId(),
                timeSheetGetReq.getProjId(), timeSheetGetReq.getCrewId(), timeSheetGetReq.getAdditional(),
                timeSheetGetReq.getWeekStartDate(), timeSheetGetReq.getWeekEndDate());*/
        TimeSheetEmpRegResp timeSheetEmpRegResp = new TimeSheetEmpRegResp();
        //timeSheetEmpRegResp.setEmpRegLabelKeyTOs(empRegLabelKeyTOs);
        return timeSheetEmpRegResp;
    }

    public TimeSheetEmpRegResp getOtherCrewEmpAttendance(TimeSheetGetReq timeSheetGetReq) {
        List<Object[]> empRegList = empAttendanceRepository.findOtherCrewEmpRegDetailsForTimeSheet(
                timeSheetGetReq.getProjId(), timeSheetGetReq.getCrewId(), timeSheetGetReq.getFromCrewId(),
                timeSheetGetReq.getWeekStartDate(), timeSheetGetReq.getWeekEndDate());
        UserMstrEntity userMstrEntity = loginRepository.findOne(AppUserUtils.getUserId());
        TimeSheetEmpRegResp timeSheetEmpRegResp = new TimeSheetEmpRegResp();
        List<LabelKeyTO> empRegLabelKeyTOs = new ArrayList<>();
        for (Object[] emp : empRegList) {
        	if(userMstrEntity.getEmpCode().equals(emp[1])) {
        		LabelKeyTO labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId((Long) emp[0]);
                labelKeyTO.setCode((String) emp[1]);
                Map<String, String> displayMap = new HashMap<>();
                displayMap.put(CommonConstants.FIRST_NAME, (String) emp[2]);
                displayMap.put(CommonConstants.LAST_NAME, (String) emp[3]);
                displayMap.put(CommonConstants.GENDER, (String) emp[4]);
                displayMap.put(CommonConstants.CLASS_TYPE, (String) emp[5]);
                displayMap.put(CommonConstants.CLASS_TYPE, (String) emp[5]);
                //  displayMap.put(CommonConstants.PROCURE_CATG, rs.getString(7));
                displayMap.put(CommonConstants.COMPANY_CATG_NAME, (String) emp[6]);
                labelKeyTO.setDisplayNamesMap(displayMap);
                empRegLabelKeyTOs.add(labelKeyTO);
            }      
        }
        timeSheetEmpRegResp.setEmpRegLabelKeyTOs(empRegLabelKeyTOs);
        return timeSheetEmpRegResp;
    }

    public TimeSheetEmpRegResp getCrewAttendanceForIndividuals(TimeSheetGetReq timeSheetGetReq) {
        List<LabelKeyTO> empRegLabelKeyTOs = timeSheetProcRepository.getAttendanceForIndividuals(
                AppUserUtils.getClientId(), timeSheetGetReq.getProjId(), timeSheetGetReq.getCrewId(),
                timeSheetGetReq.getWeekStartDate(), timeSheetGetReq.getWeekEndDate());
        TimeSheetEmpRegResp timeSheetEmpRegResp = new TimeSheetEmpRegResp();
        timeSheetEmpRegResp.setEmpRegLabelKeyTOs(empRegLabelKeyTOs);
        return timeSheetEmpRegResp;
    }

    public TimeSheetDetailResp addEmpRegToTimeSheet(TimeSheetEmpRegSaveReq timeSheetEmpRegSaveReq) {
        TimeSheetGetReq timeSheetGetReq = new TimeSheetGetReq();
        TimeSheetTO timeSheetTO = timeSheetEmpRegSaveReq.getTimeSheetTO();
        timeSheetGetReq.setProjId(timeSheetTO.getProjId());
        timeSheetGetReq.setCrewId(timeSheetTO.getCrewId());
        timeSheetGetReq.setWeekStartDate(timeSheetTO.getWeekStartDate());
        timeSheetGetReq.setWeekEndDate(timeSheetTO.getWeekEndDate());
        Map<String, String> timeSheetDays = getTimeSheetDays(timeSheetGetReq);
        Map<String, List<Long>> attendanceDayMap = getAttendanceDayMap(timeSheetGetReq, timeSheetDays);
        TimeSheetEmpDtlTO timeSheetEmpDtlTO = null;
        TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO = null;
        TimeSheetDetailResp timeSheetDetailResp = new TimeSheetDetailResp();
        for (TimeSheetEmpRegTO timeSheetEmpRegTO : timeSheetEmpRegSaveReq.getTimeSheetEmpRegTOs()) {
            timeSheetEmpDtlTO = new TimeSheetEmpDtlTO();
            timeSheetEmpDtlTO.setEmpRegId(timeSheetEmpRegTO.getId());
            timeSheetEmpWorkDtlTO = new TimeSheetEmpWorkDtlTO();
            TimeSheetEmpHandler.enableTimeSheet(attendanceDayMap, timeSheetEmpWorkDtlTO,
                    timeSheetEmpDtlTO.getEmpRegId());
            timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs().add(timeSheetEmpWorkDtlTO);
            timeSheetDetailResp.getTimeSheetEmpDtlTOs().add(timeSheetEmpDtlTO);
            
        }
        return timeSheetDetailResp;

    }

    public TimeSheetEmpRegResp copyTimeSheetEmpDetails(TimeSheetGetReq timeSheetGetReq) {
        List<LabelKeyTO> empRegDetails = timeSheetProcRepository.copyTimeSheetEmpDetails(timeSheetGetReq.getProjId(),
                timeSheetGetReq.getCrewId(), timeSheetGetReq.getFromCrewId(), timeSheetGetReq.getWeekStartDate(),
                timeSheetGetReq.getWeekEndDate(), timeSheetGetReq.getAdditional());
        TimeSheetEmpRegResp timeSheetEmpRegResp = new TimeSheetEmpRegResp();
        for (LabelKeyTO labelKeyTO : empRegDetails) {
            timeSheetEmpRegResp.getEmpRegLabelKeyTOs().add(labelKeyTO);
        }
        return timeSheetEmpRegResp;

    }

    private void sendCreateTimeSheetNotifications(TimeSheetNotificationsEntity savedEntity) {
        String code = null;
        String apprName = null;
        String text = null;
        String toEmail = null;
        String ccEmail = "";
        String epsName = null;
        String projName = null;
        String timeSheetNumber = null;
        String timeSheetEntityNumber = null;
        TimeSheetEntity timeSheetEntity = new TimeSheetEntity();
        if (null != savedEntity) {
            timeSheetEntity = savedEntity.getTimeSheetEntity();
            if (timeSheetEntity != null) {
                if (timeSheetEntity.getApprUserMstrEntity() != null)
                    apprName = timeSheetEntity.getApprUserMstrEntity().getUserName();
                code = TimeSheetNotificationsHandlerCopy.generateCode(savedEntity);
                if (timeSheetEntity.getProjMstrEntity() != null)
                    epsName = timeSheetEntity.getProjMstrEntity().getProjName();
                if (timeSheetEntity.getProjMstrEntity() != null)
                    projName = timeSheetEntity.getProjMstrEntity().getProjName();
                if (timeSheetEntity.getReqUserMstrEntity() != null)
                    toEmail = timeSheetEntity.getReqUserMstrEntity().getEmail();
                timeSheetNumber = TimeSheetEmpHandler.generateTimeSheetCode(timeSheetEntity);
            }
            timeSheetEntityNumber = TimeSheetNotificationsHandlerCopy.generateCode(savedEntity);
        }
        toSubject = "Request for Approval of Time Sheet -" + code + " for   Week Commencing "
                + timeSheetEntity.getWeekStartDate();
        text = "<html><body><p>" + apprName + ",</p>"
                + "<p>I have submitted time sheet through Project On Track, as per details mentioned here below.</p>"
                + "<table border='1'><tr><td>EPS </td><td>" + epsName + "</td></tr><tr><td>Project </td><td>"
                + projName + "</td></tr><tr><td>Time Sheet Number : </td><td>" + timeSheetNumber
                + "</td></tr><tr><td>Week Commencing </td><td>" + timeSheetEntity.getWeekStartDate()
                + "</td></tr><tr><td>WorkdairyNumber</td><td>" + timeSheetEntityNumber
                + "</td></tr></table><br>This is for your approval please." + "<p>Regards,</p>" + "<p>"
                + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
        commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
    }

    public Map<Long, Boolean> saveApproveCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
    	log.info("public Map<Long, Boolean> saveApproveCrewTimeSheetDetails");
        List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities = new ArrayList<TimeSheetEmpDtlEntity>();
        TimeSheetTO timeSheetTO = timeSheetEmpDtlSaveReq.getTimeSheetTO();
        Integer maxHrs = timeSheetTO.getMaxHrs();
        TimeSheetGetReq timeSheetGetReq = new TimeSheetGetReq();
        timeSheetGetReq.setTimeSheetId(timeSheetTO.getId());
        timeSheetGetReq.setProjId(timeSheetTO.getProjId());
        timeSheetGetReq.setCrewId(timeSheetTO.getCrewId());
        timeSheetGetReq.setWeekStartDate(timeSheetTO.getWeekStartDate());
        timeSheetGetReq.setWeekEndDate(timeSheetTO.getWeekEndDate());
        TimeSheetEntity timeSheetEntity = populateTimeSheetMstr(timeSheetTO);
        if (CommonUtil.isNonBlankLong(timeSheetTO.getCrewId())) {
            ProjCrewMstrEntity crewMstrEntity = projCrewRepository.findOne(timeSheetTO.getCrewId());
            timeSheetEntity.setProjCrewMstrEntity(crewMstrEntity);
        }
        Map<Long, Map<String, Double>> bookedHrsMap = getTimeSheetBookedHrs(timeSheetGetReq);
        Map<Long, Boolean> empMaxHrsBookedMap = populateApproveTimesheetEmpDetails(timeSheetEmpDtlSaveReq,
                timeSheetEmpDtlEntities, timeSheetTO, maxHrs, bookedHrsMap, timeSheetEntity);
        if (CommonUtil.objectNotNull(empMaxHrsBookedMap) && CommonUtil.isSetHasData(empMaxHrsBookedMap.keySet())) {
            return empMaxHrsBookedMap;
        }
        
        if (CommonUtil.isNonBlankLong(timeSheetEmpDtlSaveReq.getTimeSheetTO().getId())) {
            Long empId = null;
            List<TimeSheetEmpWorkDtlEntity> empWorkDtlEntities = null;
            List<TimeSheetEmpWorkDtlEntity> copyEmpWorkDtlEntities = new ArrayList<TimeSheetEmpWorkDtlEntity>();
            for (TimeSheetEmpDtlTO empDtlTO : timeSheetEmpDtlSaveReq.getTimeSheetEmpDtlTOs()) {
                for (TimeSheetEmpWorkDtlTO empWorkDtlTO : empDtlTO.getTimeSheetEmpWorkDtlTOs()) {
                    if (CommonUtil.isNonBlankLong(empWorkDtlTO.getEmpDtlId())) {
                        empId = empWorkDtlTO.getEmpDtlId();
                        empWorkDtlEntities = timeSheetEmpDtlRepository.findLatestEmpDetails(empId);
                        if (CommonUtil.isListHasData(empWorkDtlEntities)) {
                            for (TimeSheetEmpWorkDtlEntity workDtlEntity : empWorkDtlEntities) {
                                workDtlEntity.setLatest(false);
                                copyEmpWorkDtlEntities.add(workDtlEntity);
                            }
                            timeSheetEmpDtlRepository.save(copyEmpWorkDtlEntities);
                        }
                    }
                }
            }
        }
        TimeSheetNotificationsEntity timeSheetNotificationsEntity = timeSheetNotificationsRepository
                .findTimeSheetNotificationsByMsg(timeSheetTO.getId(), "Pending", "Request for Approval");
        if (timeSheetNotificationsEntity != null)
            timeSheetNotificationsEntity.setNotificationStatus("Approved");

        timeSheetRepository.save(timeSheetEntity);
        timeSheetEmpRepository.save(timeSheetEmpDtlEntities);
        return empMaxHrsBookedMap;
    }
    
    public Map<Long, Boolean> saveCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities = new ArrayList<TimeSheetEmpDtlEntity>();
        TimeSheetTO timeSheetTO = timeSheetEmpDtlSaveReq.getTimeSheetTO();
        Integer maxHrs = timeSheetTO.getMaxHrs();
        TimeSheetGetReq timeSheetGetReq = new TimeSheetGetReq();
        timeSheetGetReq.setTimeSheetId(timeSheetTO.getId());
        timeSheetGetReq.setProjId(timeSheetTO.getProjId());
        timeSheetGetReq.setCrewId(timeSheetTO.getCrewId());
        timeSheetGetReq.setWeekStartDate(timeSheetTO.getWeekStartDate());
        timeSheetGetReq.setWeekEndDate(timeSheetTO.getWeekEndDate());

        TimeSheetEntity timeSheetEntity = populateTimeSheetMstr(timeSheetTO);
        if (CommonUtil.isNonBlankLong(timeSheetTO.getCrewId())) {
            ProjCrewMstrEntity crewMstrEntity = projCrewRepository.findOne(timeSheetTO.getCrewId());
            timeSheetEntity.setProjCrewMstrEntity(crewMstrEntity);
        }
        Map<Long, Map<String, Double>> bookedHrsMap = getTimeSheetBookedHrs(timeSheetGetReq);
        Map<Long, Boolean> empMaxHrsBookedMap = populateTimesheetEmpDetails(timeSheetEmpDtlSaveReq,
                timeSheetEmpDtlEntities, maxHrs, bookedHrsMap, timeSheetEntity);
        if (CommonUtil.objectNotNull(empMaxHrsBookedMap) && CommonUtil.isSetHasData(empMaxHrsBookedMap.keySet())) {
            return empMaxHrsBookedMap;
        }

        if (CommonUtil.isNonBlankLong(timeSheetEmpDtlSaveReq.getTimeSheetTO().getId())) {
            Long empId = null;
            List<TimeSheetEmpWorkDtlEntity> empWorkDtlEntities = null;
            List<TimeSheetEmpWorkDtlEntity> copyEmpWorkDtlEntities = new ArrayList<TimeSheetEmpWorkDtlEntity>();
            for (TimeSheetEmpDtlTO empDtlTO : timeSheetEmpDtlSaveReq.getTimeSheetEmpDtlTOs()) {
                for (TimeSheetEmpWorkDtlTO empWorkDtlTO : empDtlTO.getTimeSheetEmpWorkDtlTOs()) {
                    if (CommonUtil.isNonBlankLong(empWorkDtlTO.getEmpDtlId())) {
                        empId = empWorkDtlTO.getEmpDtlId();
                        empWorkDtlEntities = timeSheetEmpDtlRepository.findLatestEmpDetails(empId);
                        if (CommonUtil.isListHasData(empWorkDtlEntities)) {
                            for (TimeSheetEmpWorkDtlEntity workDtlEntity : empWorkDtlEntities) {
                                workDtlEntity.setLatest(false);
                                copyEmpWorkDtlEntities.add(workDtlEntity);
                            }
                            timeSheetEmpDtlRepository.save(copyEmpWorkDtlEntities);
                        }
                    }
                }

            }
        }

        timeSheetRepository.save(timeSheetEntity);
        timeSheetEmpRepository.save(timeSheetEmpDtlEntities);

        if (CommonUtil.isNotBlankStr(timeSheetTO.getApprStatus())) {
        	log.info("++++++++++++++++++++++++++++++++++ time_sheet_notifications To be updated... " + timeSheetTO.getId());
        	log.info("timeSheetTO.getNotificationStatus() " + timeSheetTO.getNotificationStatus());
            TimeSheetNotificationsEntity timeSheetNotificationsEntity = timeSheetNotificationsRepository
                    .findTimeSheetNotificationsByMsg(timeSheetTO.getId(), "Approved", "Request for Additional Time");
            if (timeSheetNotificationsEntity != null) {
            	log.info("timeSheetTO.getNotificationStatus() " + timeSheetNotificationsEntity.getId());
                timeSheetNotificationsEntity.setNotificationStatus(timeSheetTO.getNotificationStatus());
                timeSheetNotificationsEntity.setNotificationMsg(timeSheetTO.getNotificationMsg());
                timeSheetNotificationsEntity.setStatus(null);
            } else {
            	log.info("++++++++++++++++++++++++++++++++++ time_sheet_notifications To be Inserted..." + timeSheetTO.getId());
            	TimeSheetNotificationsTO timeSheetNotificationsTO = new TimeSheetNotificationsTO();
                timeSheetNotificationsTO.setNotificationStatus(timeSheetTO.getNotificationStatus());
                timeSheetNotificationsTO.setNotificationMsg(timeSheetTO.getNotificationMsg());
                timeSheetNotificationsTO.setStatus(timeSheetTO.getStatus());
                timeSheetNotificationsTO.setTimeSheetId(timeSheetTO.getId());
                timeSheetNotificationsTO.setClientCode(AppUserUtils.getClientCode());

                timeSheetNotificationsTO.setProjId(timeSheetTO.getProjId());
                TimeSheetNotificationsEntity timeSheetNotificationsEntity1 = TimeSheetNotificationsHandlerCopy
                        .convertPOJOToEntity(timeSheetNotificationsTO, timeSheetRepository);
                TimeSheetNotificationsEntity savedEntity = timeSheetNotificationsRepository
                        .save(timeSheetNotificationsEntity1);
            }
          //sending Email
            sendMailForSubmitTimeSheetDetails(timeSheetNotificationsEntity);
        } else {
        	log.info("++++++++++++++++++++++++++++++++++ time_sheet_notifications To be Inserted..." + timeSheetTO.getId());
        	TimeSheetNotificationsTO timeSheetNotificationsTO = new TimeSheetNotificationsTO();
            timeSheetNotificationsTO.setNotificationStatus(timeSheetTO.getNotificationStatus());
            timeSheetNotificationsTO.setNotificationMsg(timeSheetTO.getNotificationMsg());
            timeSheetNotificationsTO.setStatus(timeSheetTO.getStatus());
            timeSheetNotificationsTO.setTimeSheetId(timeSheetTO.getId());
            timeSheetNotificationsTO.setClientCode(AppUserUtils.getClientCode());

            timeSheetNotificationsTO.setProjId(timeSheetTO.getProjId());
            TimeSheetNotificationsEntity timeSheetNotificationsEntity = TimeSheetNotificationsHandlerCopy
                    .convertPOJOToEntity(timeSheetNotificationsTO, timeSheetRepository);
            TimeSheetNotificationsEntity savedEntity = timeSheetNotificationsRepository
                    .save(timeSheetNotificationsEntity);
        }
        
        //   sendCreateTimeSheetNotifications(savedEntity);
        return empMaxHrsBookedMap;
    }

    public Map<Long, Boolean> saveIndividualTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities = new ArrayList<TimeSheetEmpDtlEntity>();
        TimeSheetTO timeSheetTO = timeSheetEmpDtlSaveReq.getTimeSheetTO();
        Integer maxHrs = timeSheetTO.getMaxHrs();
        TimeSheetGetReq timeSheetGetReq = new TimeSheetGetReq();
        timeSheetGetReq.setTimeSheetId(timeSheetTO.getId());
        timeSheetGetReq.setProjId(timeSheetTO.getProjId());
        timeSheetGetReq.setEmpId(timeSheetTO.getEmpId());
        timeSheetGetReq.setWeekStartDate(timeSheetTO.getWeekStartDate());
        timeSheetGetReq.setWeekEndDate(timeSheetTO.getWeekEndDate());
        TimeSheetEntity timeSheetEntity = populateTimeSheetMstr(timeSheetTO);
        Map<Long, Map<String, Double>> bookedHrsMap = getTimeSheetBookedHrs(timeSheetGetReq);
        Map<Long, Boolean> empMaxHrsBookedMap = populateTimesheetEmpDetails(timeSheetEmpDtlSaveReq,
                timeSheetEmpDtlEntities, maxHrs, bookedHrsMap, timeSheetEntity);
        if (CommonUtil.objectNotNull(empMaxHrsBookedMap) && CommonUtil.isSetHasData(empMaxHrsBookedMap.keySet())) {
            return empMaxHrsBookedMap;
        }
        if (CommonUtil.isNonBlankLong(timeSheetTO.getId())) {
            timeSheetEntity.setId(timeSheetTO.getId());
        }
        timeSheetRepository.save(timeSheetEntity);
        timeSheetEmpRepository.save(timeSheetEmpDtlEntities);
        return empMaxHrsBookedMap;
    }

    private TimeSheetEntity populateTimeSheetMstr(TimeSheetTO timeSheetTO) {
        TimeSheetEntity timeSheetEntity = new TimeSheetEntity();
        if (CommonUtil.isNonBlankLong(timeSheetTO.getId())) {
            timeSheetEntity.setId(timeSheetTO.getId());
        }
        if (CommonUtil.isNonBlankLong(timeSheetTO.getProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(timeSheetTO.getProjId());
            timeSheetEntity.setProjMstrEntity(projMstrEntity);
        }
        timeSheetEntity.setWeekStartDate(timeSheetTO.getWeekStartDate());
        timeSheetEntity.setWeekEndDate(timeSheetTO.getWeekEndDate());
        timeSheetEntity.setAdditional(timeSheetTO.getAdditional());
        if (CommonUtil.isNonBlankLong(timeSheetTO.getEmpId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository.findOne(timeSheetTO.getEmpId());
            timeSheetEntity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        timeSheetEntity.setStatus(StatusCodes.ACTIVE.getValue());
        UserMstrEntity userMstrEntity = loginRepository.findOne(AppUserUtils.getUserId());
        timeSheetEntity.setReqUserMstrEntity(userMstrEntity);
        if (CommonUtil.isNotBlankStr(timeSheetTO.getApprStatus())) {
            timeSheetEntity.setApprStatus(timeSheetTO.getApprStatus());
            if (timeSheetTO.getApprUserId() != null) {
                UserMstrEntity apprEntity = loginRepository.findOne(timeSheetTO.getApprUserId());
                timeSheetEntity.setApprUserMstrEntity(apprEntity);
            }
            timeSheetEntity.setReqComments(timeSheetTO.getReqComments());
        } else {
            timeSheetEntity.setApprStatus(TimeManagentStatus.UNDER_PREPARATION.getName());
        }
        return timeSheetEntity;
    }

    private Map<Long, Boolean> populateTimesheetEmpDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq,
            List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities, Integer maxHrs,
            Map<Long, Map<String, Double>> bookedHrsMap, TimeSheetEntity timeSheetEntity) {
        TimeSheetEmpDtlEntity timeSheetEmpDtlEntity;
        TimeSheetEmpWorkDtlEntity timeSheetEmpWorkDtlEntity;
        Map<String, Double> empBookedHrs;
        Map<Long, Boolean> empMaxHrsBookedMap = new HashMap<>();
        for (TimeSheetEmpDtlTO timeSheetEmpDtlTO : timeSheetEmpDtlSaveReq.getTimeSheetEmpDtlTOs()) {
            timeSheetEmpDtlEntity = TimeSheetEmpHandler.convertEmpDtlPOJOToEntity(timeSheetEmpDtlTO,
                    empRegisterRepository);
            if( CommonUtil.isNonBlankLong( timeSheetEmpDtlSaveReq.getTimeSheetTO().getId() ) ) {
            	System.out.println("populateTimesheetEmpDetails function if condition"+timeSheetEmpDtlSaveReq.getTimeSheetTO().getId());
            	TimeSheetEntity tempTimeSheetEntity = new TimeSheetEntity();
            	tempTimeSheetEntity.setId( timeSheetEmpDtlSaveReq.getTimeSheetTO().getId() );
            	timeSheetEmpDtlEntity.setTimeSheetEntity( tempTimeSheetEntity );
            }
            timeSheetEmpDtlEntity.setTimeSheetEntity(timeSheetEntity);
            empBookedHrs = bookedHrsMap.get(timeSheetEmpDtlEntity.getEmpRegisterDtlEntity().getId());
            Map<String, Double> hrsMap = new HashMap<>();
            TimeSheetEmpHandler.populateCurrentBookedHrs(hrsMap, timeSheetEmpDtlTO);
            if (TimeSheetEmpHandler.validateTimeSheetHrs(maxHrs, hrsMap, empBookedHrs)) {
                empMaxHrsBookedMap.put(timeSheetEmpDtlEntity.getEmpRegisterDtlEntity().getId(), true);
            }
        }
        if (CommonUtil.isSetHasData(empMaxHrsBookedMap.keySet())) {
            return empMaxHrsBookedMap;
        }
        for (TimeSheetEmpDtlTO timeSheetEmpDtlTO : timeSheetEmpDtlSaveReq.getTimeSheetEmpDtlTOs()) {
            timeSheetEmpDtlEntity = TimeSheetEmpHandler.convertEmpDtlPOJOToEntity(timeSheetEmpDtlTO,
                    empRegisterRepository);
            timeSheetEmpDtlEntity.setTimeSheetEntity(timeSheetEntity);
            empBookedHrs = bookedHrsMap.get(timeSheetEmpDtlEntity.getEmpRegisterDtlEntity().getId());
            for (TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO : timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs()) {
                timeSheetEmpWorkDtlTO.setApprComments(timeSheetEmpDtlSaveReq.getTimeSheetTO().getApprComments());
                /*
                 * timeSheetEmpWorkDtlTO.setApprStatus(timeSheetEmpDtlSaveReq.
                 * getTimeSheetTO().getApprStatus());
                 */
                timeSheetEmpWorkDtlTO.setApprUsrId(timeSheetEmpDtlSaveReq.getTimeSheetTO().getApprUserId());
                timeSheetEmpWorkDtlEntity = TimeSheetEmpHandler.convertEmpWorkDtlPOJOToEntity(timeSheetEmpWorkDtlTO,
                        projCostItemRepository, empWageRepository, timeSheetEmpDtlRepository, loginRepository);
                timeSheetEmpWorkDtlEntity.setTimeSheetEmpDtlEntity(timeSheetEmpDtlEntity);
                timeSheetEmpDtlEntity.getTimeSheetEmpWorkDtlEntities().add(timeSheetEmpWorkDtlEntity);
            }
            timeSheetEmpDtlEntities.add(timeSheetEmpDtlEntity);
        }
        return empMaxHrsBookedMap;
    }

    private Map<Long, Boolean> populateApproveTimesheetEmpDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq,
            List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities, TimeSheetTO timeSheetTO, Integer maxHrs,
            Map<Long, Map<String, Double>> bookedHrsMap, TimeSheetEntity timeSheetEntity) {
        TimeSheetEmpDtlEntity timeSheetEmpDtlEntity;
        TimeSheetEmpWorkDtlEntity timeSheetEmpWorkDtlEntity = new TimeSheetEmpWorkDtlEntity();
        Map<String, Double> empBookedHrs;
        Map<Long, Boolean> empMaxHrsBookedMap = new HashMap<Long, Boolean>();
        for (TimeSheetEmpDtlTO timeSheetEmpDtlTO : timeSheetEmpDtlSaveReq.getTimeSheetEmpDtlTOs()) {
            timeSheetEmpDtlEntity = TimeSheetEmpHandler.convertEmpDtlPOJOToEntity(timeSheetEmpDtlTO,
                    empRegisterRepository);
            timeSheetEmpDtlEntity.setTimeSheetEntity(timeSheetEntity);
            Long key = timeSheetEmpDtlEntity.getEmpRegisterDtlEntity().getId();
            empBookedHrs = bookedHrsMap.get(key);
            Map<String, Double> hrsMap = new HashMap<String, Double>();
            if (CommonUtil.isNotBlankStr(timeSheetEmpDtlTO.getApprStatus())) {
                TimeSheetEmpHandler.populateCurrentBookedHrs(hrsMap, timeSheetEmpDtlTO);
            }
            if (TimeSheetEmpHandler.validateTimeSheetHrs(maxHrs, hrsMap, empBookedHrs)) {
                empMaxHrsBookedMap.put(key, true);
            }
        }
        if (CommonUtil.isSetHasData(empMaxHrsBookedMap.keySet())) {
            return empMaxHrsBookedMap;
        }
        for (TimeSheetEmpDtlTO timeSheetEmpDtlTO : timeSheetEmpDtlSaveReq.getTimeSheetEmpDtlTOs()) {
            timeSheetEmpDtlEntity = TimeSheetEmpHandler.convertEmpDtlPOJOToEntity(timeSheetEmpDtlTO,
                    empRegisterRepository);
            timeSheetEmpDtlEntity.setTimeSheetEntity(timeSheetEntity);
            empBookedHrs = bookedHrsMap.get(timeSheetEmpDtlEntity.getEmpRegisterDtlEntity().getId());
            for (TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO : timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs()) {
                timeSheetEmpWorkDtlTO.setApprComments(timeSheetEmpDtlSaveReq.getTimeSheetTO().getApprComments());
                /*
                 * timeSheetEmpWorkDtlTO.setApprStatus(timeSheetEmpDtlSaveReq.
                 * getTimeSheetTO().getApprStatus());
                 */
                timeSheetEmpWorkDtlTO.setApprUsrId(timeSheetEmpDtlSaveReq.getTimeSheetTO().getApprUserId());
                if (TimeManagentStatus.SUBMITTED.getName().equalsIgnoreCase(timeSheetEmpWorkDtlTO.getApprStatus())) {
                    timeSheetEmpWorkDtlEntity = TimeSheetEmpHandler.convertEmpWorkDtlPOJOToEntity(timeSheetEmpWorkDtlTO,
                            projCostItemRepository, empWageRepository, timeSheetEmpDtlRepository, loginRepository);
                    timeSheetEmpWorkDtlEntity.setTimeSheetEmpDtlEntity(timeSheetEmpDtlEntity);
                    timeSheetEmpDtlEntity.getTimeSheetEmpWorkDtlEntities().add(timeSheetEmpWorkDtlEntity);
                }

            }
            timeSheetEmpDtlEntities.add(timeSheetEmpDtlEntity);
        }
        return empMaxHrsBookedMap;
    }

    public void submitCrewTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        saveCrewTimeSheetDetails(timeSheetEmpDtlSaveReq);
        createTimeSheetWorkDetails(timeSheetEmpDtlSaveReq);
        createTimeSheetTasksAndExpenses(timeSheetEmpDtlSaveReq.getTimeSheetTO().getId());
    }

    private void createTimeSheetWorkDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        List<TimeSheetEmpWorkDtlEntity> timeSheetEmpWorkDtlEntities = new ArrayList<TimeSheetEmpWorkDtlEntity>();
        TimeSheetEmpDtlEntity timeSheetEmpDtlEntity = null;
        TimeSheetEmpWorkDtlEntity timeSheetEmpWorkDtlEntity = null;

        if (CommonUtil.isNonBlankLong(timeSheetEmpDtlSaveReq.getTimeSheetTO().getId())) {
            Long empId = null;
            List<TimeSheetEmpWorkDtlEntity> empWorkDtlEntities = null;
            List<TimeSheetEmpWorkDtlEntity> copyEmpWorkDtlEntities = new ArrayList<TimeSheetEmpWorkDtlEntity>();
            for (TimeSheetEmpDtlTO empDtlTO : timeSheetEmpDtlSaveReq.getTimeSheetEmpDtlTOs()) {
                for (TimeSheetEmpWorkDtlTO empWorkDtlTO : empDtlTO.getTimeSheetEmpWorkDtlTOs()) {
                    if (CommonUtil.isNonBlankLong(empWorkDtlTO.getEmpDtlId())) {
                        empId = empWorkDtlTO.getEmpDtlId();
                        empWorkDtlEntities = timeSheetEmpDtlRepository.findLatestEmpDetails(empId);
                        if (CommonUtil.isListHasData(empWorkDtlEntities)) {
                            for (TimeSheetEmpWorkDtlEntity workDtlEntity : empWorkDtlEntities) {
                                workDtlEntity.setLatest(false);
                                copyEmpWorkDtlEntities.add(workDtlEntity);
                            }
                            timeSheetEmpDtlRepository.save(copyEmpWorkDtlEntities);
                        }
                    }
                }
            }

        }

        for (TimeSheetEmpDtlTO timeSheetEmpDtlTO : timeSheetEmpDtlSaveReq.getTimeSheetEmpDtlTOs()) {
            for (TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO : timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs()) {
                timeSheetEmpWorkDtlEntity = TimeSheetEmpHandler.convertEmpWorkDtlPOJOToEntity(timeSheetEmpWorkDtlTO,
                        projCostItemRepository, empWageRepository, timeSheetEmpDtlRepository, loginRepository);
                timeSheetEmpWorkDtlEntity.setId(null);
                timeSheetEmpWorkDtlEntity.setApprStatus(TimeManagentStatus.SUBMITTED.getName());
                timeSheetEmpDtlEntity = new TimeSheetEmpDtlEntity();
                timeSheetEmpDtlEntity.setId(timeSheetEmpDtlTO.getId());
                timeSheetEmpWorkDtlEntity.setTimeSheetEmpDtlEntity(timeSheetEmpDtlEntity);
                timeSheetEmpWorkDtlEntities.add(timeSheetEmpWorkDtlEntity);
            }
        }
        timeSheetEmpDtlRepository.save(timeSheetEmpWorkDtlEntities);
    }

    private void createTimeSheetTasksAndExpenses(Long timeSheetId) {
        List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities = timeSheetEmpRepository
                .findTimeSheetTasksAndExpenses(timeSheetId, StatusCodes.ACTIVE.getValue());
        List<TimeSheetEmpExpenseEntity> expenseEntities = new ArrayList<TimeSheetEmpExpenseEntity>();
        List<TimeSheetEmpTaskEntity> empTaskEntities = new ArrayList<TimeSheetEmpTaskEntity>();
        TimeSheetEmpDtlEntity timeSheetEmpDtlEntity = null;
        TimeSheetEmpTaskEntity timeSheetEmpTaskEntity = null;
        TimeSheetEmpExpenseEntity timeSheetEmpExpenseEntity = null;
        for (TimeSheetEmpDtlEntity empDtlEntity : timeSheetEmpDtlEntities) {
            timeSheetEmpDtlEntity = new TimeSheetEmpDtlEntity();
            timeSheetEmpDtlEntity.setId(empDtlEntity.getId());
            for (TimeSheetEmpExpenseEntity empExpenseEntity : empDtlEntity.getTimeSheetEmpExpenseEntities()) {
                timeSheetEmpExpenseEntity = new TimeSheetEmpExpenseEntity();
                BeanUtils.copyProperties(empExpenseEntity, timeSheetEmpExpenseEntity);
                timeSheetEmpExpenseEntity.setId(null);
                timeSheetEmpExpenseEntity.setTimeSheetEmpDtlEntity(timeSheetEmpDtlEntity);
                timeSheetEmpExpenseEntity.setApprStatus(TimeManagentStatus.SUBMITTED.getName());
                expenseEntities.add(timeSheetEmpExpenseEntity);
            }
            for (TimeSheetEmpTaskEntity empTaskEntity : empDtlEntity.getTimeSheetEmpTaskEntities()) {
                timeSheetEmpTaskEntity = new TimeSheetEmpTaskEntity();
                BeanUtils.copyProperties(empTaskEntity, timeSheetEmpTaskEntity);
                timeSheetEmpTaskEntity.setId(null);
                timeSheetEmpTaskEntity.setTimeSheetEmpDtlEntity(timeSheetEmpDtlEntity);
                timeSheetEmpTaskEntity.setApprStatus(TimeManagentStatus.SUBMITTED.getName());
                empTaskEntities.add(timeSheetEmpTaskEntity);
            }
        }
        timeSheetEmpTaskRepository.save(empTaskEntities);
        timeSheetEmpExpenseRepository.save(expenseEntities);

    }

    public void submitIndividualTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
        saveIndividualTimeSheetDetails(timeSheetEmpDtlSaveReq);
        createTimeSheetWorkDetails(timeSheetEmpDtlSaveReq);
        createTimeSheetTasksAndExpenses(timeSheetEmpDtlSaveReq.getTimeSheetTO().getId());
    }

    public void approveTimeSheetDetails(TimeSheetDtlSaveReq timeSheetEmpDtlSaveReq) {
    	log.info("public void approveTimeSheetDetails");
        List<TimeSheetEmpDtlEntity> timeSheetEmpDtlEntities = new ArrayList<TimeSheetEmpDtlEntity>();
        TimeSheetEmpDtlEntity timeSheetEmpDtlEntity = null;
        TimeSheetEmpWorkDtlEntity timeSheetEmpWorkDtlEntity = null;

        if (CommonUtil.isNonBlankLong(timeSheetEmpDtlSaveReq.getTimeSheetTO().getId())) {
            Long empId = null;
            List<TimeSheetEmpWorkDtlEntity> empWorkDtlEntities = null;
            List<TimeSheetEmpWorkDtlEntity> copyEmpWorkDtlEntities = new ArrayList<TimeSheetEmpWorkDtlEntity>();
            for (TimeSheetEmpDtlTO empDtlTO : timeSheetEmpDtlSaveReq.getTimeSheetEmpDtlTOs()) {
                for (TimeSheetEmpWorkDtlTO empWorkDtlTO : empDtlTO.getTimeSheetEmpWorkDtlTOs()) {
                    if (CommonUtil.isNonBlankLong(empWorkDtlTO.getEmpDtlId())) {
                        empId = empWorkDtlTO.getEmpDtlId();
                        empWorkDtlEntities = timeSheetEmpDtlRepository.findLatestEmpDetails(empId);
                        if (CommonUtil.isListHasData(empWorkDtlEntities)) {
                            for (TimeSheetEmpWorkDtlEntity workDtlEntity : empWorkDtlEntities) {
                                workDtlEntity.setLatest(false);
                                copyEmpWorkDtlEntities.add(workDtlEntity);
                            }
                            timeSheetEmpDtlRepository.save(copyEmpWorkDtlEntities);
                        }
                    }
                }
            }

        }

        for (TimeSheetEmpDtlTO timeSheetEmpDtlTO : timeSheetEmpDtlSaveReq.getTimeSheetEmpDtlTOs()) {
            timeSheetEmpDtlEntity = TimeSheetEmpHandler.convertEmpDtlPOJOToEntity(timeSheetEmpDtlTO,
                    empRegisterRepository);
            for (TimeSheetEmpWorkDtlTO timeSheetEmpWorkDtlTO : timeSheetEmpDtlTO.getTimeSheetEmpWorkDtlTOs()) {
                if (TimeManagentStatus.SUBMITTED.getName().equalsIgnoreCase(timeSheetEmpWorkDtlTO.getApprStatus())) {
                    timeSheetEmpWorkDtlEntity = TimeSheetEmpHandler.convertEmpWorkDtlPOJOToEntity(timeSheetEmpWorkDtlTO,
                            projCostItemRepository, empWageRepository, timeSheetEmpDtlRepository, loginRepository);
                }
                if (TimeManagentStatus.SUBMITTED.getName().equalsIgnoreCase(timeSheetEmpWorkDtlTO.getApprStatus())
                        && TimeManagentStatus.APPROVED.getName()
                                .equalsIgnoreCase(timeSheetEmpDtlSaveReq.getTimeSheetTO().getApprStatus())) {
                    timeSheetEmpWorkDtlEntity.setApprStatus(TimeManagentStatus.APPROVED.getName());
                }
                timeSheetEmpDtlEntity.getTimeSheetEmpWorkDtlEntities().add(timeSheetEmpWorkDtlEntity);
            }
            timeSheetEmpDtlEntities.add(timeSheetEmpDtlEntity);
        }
        TimeSheetTO timeSheetTO = timeSheetEmpDtlSaveReq.getTimeSheetTO();
        TimeSheetEntity timeSheetEntity = timeSheetRepository.findOne(timeSheetEmpDtlSaveReq.getTimeSheetTO().getId());
        timeSheetEntity.setApprComments(timeSheetEmpDtlSaveReq.getTimeSheetTO().getApprComments());
        timeSheetEntity.setApprStatus(TimeManagentStatus.APPROVED.getName());
        timeSheetRepository.save(timeSheetEntity);
        timeSheetEmpRepository.save(timeSheetEmpDtlEntities);

        //updating notification repository
        TimeSheetNotificationsEntity timeSheetNotificationsEntity = timeSheetNotificationsRepository
                .findTimeSheetNotificationsByMsg(timeSheetTO.getId(), "Pending", "Request for Approval");
        if (timeSheetNotificationsEntity != null)
            timeSheetNotificationsEntity.setNotificationStatus(timeSheetTO.getNotificationStatus());

        //sending Email
        log.info("BEFORE SENDING EMAIL ON SUBMIT TIMESHEET");
        sendMailForApproveTimeSheetDetails(timeSheetNotificationsEntity);
    }
    
    private void sendMailForSubmitTimeSheetDetails(TimeSheetNotificationsEntity timeSheetNotificationsEntity) {
    	log.info("sendMailForSubmitTimeSheetDetails METHOD");
        String apprName = "";
        String epsName = "";
        String projName = "";
        String code = "";
        Date startDate = null;
        String toEmail = null;
        String timeSheetNumber = "";
        if (null != timeSheetNotificationsEntity) {
            TimeSheetEntity entity = timeSheetNotificationsEntity.getTimeSheetEntity();
            if (entity != null) {
                if (entity.getApprUserMstrEntity() != null)
                    apprName = entity.getApprUserMstrEntity().getUserName();
                if (entity.getProjMstrEntity().getProjectId() != null)
                    epsName = entity.getProjMstrEntity().getParentProjectMstrEntity().getProjName();
                if (entity.getProjMstrEntity() != null)
                    projName = entity.getProjMstrEntity().getProjName();
                if (timeSheetNotificationsEntity.getTimeSheetEntity().getProjMstrEntity() != null)
                    code = TimeSheetNotificationsHandlerCopy.generateCode(timeSheetNotificationsEntity);
                startDate = timeSheetNotificationsEntity.getTimeSheetEntity().getWeekStartDate();
                if (null != entity.getReqUserMstrEntity().getEmail())
                    toEmail = entity.getReqUserMstrEntity().getEmail();
            }
            timeSheetNumber = TimeSheetEmpHandler.generateTimeSheetCode(timeSheetNotificationsEntity.getTimeSheetEntity());
        }

        toSubject = "Submission of Time Sheet -" + code + " for Week Commencing " + startDate;
        text = "<html><body><p>" + apprName + ",</p>"
                + "<p>I have approved time sheet through Project On Track, as per details mentioned here below.</p>"
                + "<table border='1'><tr><td>EPS </td><td>" + epsName + "</td></tr><tr><td>Project </td><td>"
                + projName + "</td></tr><tr><td>Time sheet  number : </td><td>" + timeSheetNumber
                + "</td></tr><tr><td>Week Commencing </td><td>" + startDate
                + "</td></tr></table><br>This is for your Information please." + "<p>Regards,</p>" + "<p>"
                + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
        if (CommonUtil.isNotBlankStr(toEmail))
            commonEmail.sendEmailNotification(toEmail, "", toSubject, text);

    }

    private void sendMailForApproveTimeSheetDetails(TimeSheetNotificationsEntity timeSheetNotificationsEntity) {

        String apprName = "";
        String epsName = "";
        String projName = "";
        String code = "";
        Date startDate = null;
        String toEmail = null;
        String timeSheetNumber = "";
        if (null != timeSheetNotificationsEntity) {
            TimeSheetEntity entity = timeSheetNotificationsEntity.getTimeSheetEntity();
            if (entity != null) {
                if (entity.getApprUserMstrEntity() != null)
                    apprName = entity.getApprUserMstrEntity().getUserName();
                if (entity.getProjMstrEntity().getProjectId() != null)
                    epsName = entity.getProjMstrEntity().getParentProjectMstrEntity().getProjName();
                if (entity.getProjMstrEntity() != null)
                    projName = entity.getProjMstrEntity().getProjName();
                if (timeSheetNotificationsEntity.getTimeSheetEntity().getProjMstrEntity() != null)
                    code = TimeSheetNotificationsHandlerCopy.generateCode(timeSheetNotificationsEntity);
                startDate = timeSheetNotificationsEntity.getTimeSheetEntity().getWeekStartDate();
                if (null != entity.getReqUserMstrEntity().getEmail())
                    toEmail = entity.getReqUserMstrEntity().getEmail();
            }
            timeSheetNumber = TimeSheetEmpHandler.generateTimeSheetCode(timeSheetNotificationsEntity.getTimeSheetEntity());
        }

        toSubject = "Approval of Time Sheet -" + code + " for   Week Commencing " + startDate;
        text = "<html><body><p>" + apprName + ",</p>"
                + "<p>I have approved time sheet through Project On Track, as per details mentioned here below.</p>"
                + "<table border='1'><tr><td>EPS </td><td>" + epsName + "</td></tr><tr><td>Project </td><td>"
                + projName + "</td></tr><tr><td>Time sheet  number : </td><td>" + timeSheetNumber
                + "</td></tr><tr><td>Week Commencing </td><td>" + startDate
                + "</td></tr></table><br>This is for your Information please." + "<p>Regards,</p>" + "<p>"
                + AppUserUtils.getUserName() + "<br/>" + AppUserUtils.getDisplayRole() + "</p></body></html>";
        if (CommonUtil.isNotBlankStr(toEmail))
            commonEmail.sendEmailNotification(toEmail, "", toSubject, text);

    }

    public void saveTimeSheetEmpTasks(TimeSheetEmpTaskSaveReq timeSheetEmpTaskSaveReq) {
        List<TimeSheetEmpTaskEntity> taskEntities = new ArrayList<>();
        TimeSheetEmpTaskEntity timeSheetEmpTaskEntity = null;
        for (Entry<String, TimeSheetEmpTaskTO> entry : timeSheetEmpTaskSaveReq.getTimeSheetEmpTaskMap().entrySet()) {
            timeSheetEmpTaskEntity = TimeSheetEmpHandler.convertEmpTaskPOJOToEntity(entry.getValue(), loginRepository,
                    timeSheetEmpTaskRepository, timeSheetEmpRepository);
            taskEntities.add(timeSheetEmpTaskEntity);
        }
        timeSheetEmpTaskRepository.save(taskEntities);

    }

    public void saveTimeSheetEmpExpenses(TimeSheetEmpExpenseSaveReq timeSheetEmpExpenseSaveReq) {
        List<TimeSheetEmpExpenseEntity> timeSheetEmpExpenseEntities = new ArrayList<TimeSheetEmpExpenseEntity>();
        TimeSheetEmpExpenseEntity timeSheetEmpExpenseEntity = null;
        for (TimeSheetEmpExpenseTO timeSheetEmpExpenseTO : timeSheetEmpExpenseSaveReq.getTimeSheetEmpExpenseTOs()) {
            timeSheetEmpExpenseEntity = TimeSheetEmpHandler.convertEmpExpensePOJOToEntity(timeSheetEmpExpenseTO,
                    projCostItemRepository, timeSheetEmpExpenseRepository, loginRepository);
            timeSheetEmpExpenseEntities.add(timeSheetEmpExpenseEntity);
        }
        timeSheetEmpExpenseRepository.save(timeSheetEmpExpenseEntities);
    }

    public TimeSheetEmpTaskResp getTimeSheetEmpTasks(TimeSheetGetReq timeSheetGetReq) {
        List<TimeSheetEmpTaskEntity> timeSheetEmpTaskEntities = new ArrayList<TimeSheetEmpTaskEntity>();
        timeSheetEmpTaskEntities = timeSheetEmpTaskRepository.findTimeSheetEmpTasks(timeSheetGetReq.getTimeSheetEmpId(),
                timeSheetGetReq.getApprStatus(), timeSheetGetReq.getStatus());
        TimeSheetEntity timeSheetEntity = timeSheetRepository.findTimeSheetById(timeSheetGetReq.getTimeSheetId(),
                timeSheetGetReq.getStatus());

        Integer differncdeDays = TimeManagementUtil.getDateDiffernce(timeSheetEntity.getWeekStartDate(),
                timeSheetEntity.getWeekEndDate());
        Calendar c = Calendar.getInstance();
        Map<String, Boolean> timeSheetDaysMap = new HashMap<String, Boolean>();
        List<String> timeSheetDays = new ArrayList<String>();

        Map<String, TimeSheetEmpTaskTO> timeSheetEmpTaskMap = new HashMap<String, TimeSheetEmpTaskTO>();

        for (int i = 0; i <= differncdeDays; i++) {
            c.setTime(timeSheetEntity.getWeekStartDate());
            c.add(Calendar.DATE, i);
            timeSheetDaysMap.put(TimeManagementUtil.getDateWithDDMMMYYYYFormat(c.getTime()), true);
            timeSheetDays.add(TimeManagementUtil.getDateWithDDMMMYYYYFormat(c.getTime()));
        }

        TimeSheetEmpTaskTO timeSheetEmpTaskTO = null;
        Map<String, Boolean> existingTasksMap = new HashMap<String, Boolean>();
        for (TimeSheetEmpTaskEntity timeSheetEmpTaskEntity : timeSheetEmpTaskEntities) {
            timeSheetEmpTaskTO = TimeSheetEmpHandler.convertEmpTaskEntityToPOJO(timeSheetEmpTaskEntity);
            existingTasksMap.put(timeSheetEmpTaskTO.getDate(), true);
            timeSheetEmpTaskMap.put(timeSheetEmpTaskTO.getDate(), timeSheetEmpTaskTO);
        }
        for (Entry<String, Boolean> entry : timeSheetDaysMap.entrySet()) {
            if (existingTasksMap.get(entry.getKey()) == null) {
                timeSheetEmpTaskTO = new TimeSheetEmpTaskTO();
                timeSheetEmpTaskTO.setEmpDtlId(timeSheetGetReq.getTimeSheetEmpId());
                timeSheetEmpTaskTO.setDate(entry.getKey());
                timeSheetEmpTaskTO.setStatus(StatusCodes.ACTIVE.getValue());
                timeSheetEmpTaskMap.put(timeSheetEmpTaskTO.getDate(), timeSheetEmpTaskTO);
            }
        }
        TimeSheetEmpTaskResp timeSheetEmpTaskResp = new TimeSheetEmpTaskResp();
        timeSheetEmpTaskResp.setTimeSheetEmpTaskMap(timeSheetEmpTaskMap);
        timeSheetEmpTaskResp.setTimeSheetDays(timeSheetDays);
        return timeSheetEmpTaskResp;
    }

    public TimeSheetEmpExpenseResp getTimeSheetEmpExpenses(TimeSheetGetReq timeSheetGetReq) {
        List<TimeSheetEmpExpenseEntity> timeSheetEmpExpenseEntities = new ArrayList<TimeSheetEmpExpenseEntity>();
        timeSheetEmpExpenseEntities = timeSheetEmpExpenseRepository.findTimeSheetEmpExpenses(
                timeSheetGetReq.getTimeSheetEmpId(), timeSheetGetReq.getApprStatus(), timeSheetGetReq.getStatus());
        TimeSheetEmpExpenseResp timeSheetEmpExpenseResp = new TimeSheetEmpExpenseResp();
        for (TimeSheetEmpExpenseEntity timeSheetEmpExpenseEntity : timeSheetEmpExpenseEntities) {
            timeSheetEmpExpenseResp.getTimeSheetEmpExpenseTOs()
                    .add(TimeSheetEmpHandler.convertEmpExpenseEntityToPOJO(timeSheetEmpExpenseEntity));
        }
        return timeSheetEmpExpenseResp;
    }

    private Map<Long, Map<String, Double>> getTimeSheetBookedHrs(TimeSheetGetReq timeSheetGetReq) {

        Map<String, String> timeSheetDays = getTimeSheetDays(timeSheetGetReq);

        List<Object[]> workDairyBookedDays = empWorkDairyRepository.workDairyBookedHrsForTimeSheet(
                timeSheetGetReq.getProjId(), timeSheetGetReq.getWeekStartDate(), timeSheetGetReq.getWeekEndDate());

        List<Object[]> timesheetBookedHrsForOtherCrew = timeSheetEmpDtlRepository.timeSheetBookedHrsForOtherCrew(
                timeSheetGetReq.getProjId(), timeSheetGetReq.getTimeSheetId(), timeSheetGetReq.getWeekStartDate());

        List<Object[]> timesheetBookedHrsForSameCrewForAdditional = timeSheetEmpDtlRepository
                .timeSheetBookedHrsForSameCrew(timeSheetGetReq.getProjId(), timeSheetGetReq.getCrewId(),
                        timeSheetGetReq.getWeekStartDate());

        Map<Long, Map<String, Double>> bookedHrsMap = new HashMap<>();
        Long empId;
        String day;
        Double totalHrs;
        for (Object[] value : workDairyBookedDays) {
            empId = (Long) value[0];
            day = (String) value[1];
            totalHrs = (Double) value[2];
            if (bookedHrsMap.get(empId) != null) {
                if (timeSheetDays.get(day) != null) {
                    double bookedHrs = 0;
                    if (totalHrs != null) {
                        bookedHrs = totalHrs;
                    }
                    bookedHrsMap.get(empId).put(timeSheetDays.get(day), bookedHrs);
                }
            } else {
                if (timeSheetDays.get(day) != null) {
                    double bookedHrs = 0;
                    if (totalHrs != null) {
                        bookedHrs = totalHrs;
                    }
                    Map<String, Double> daysMap = new HashMap<>();
                    daysMap.put(timeSheetDays.get(day), bookedHrs);
                    bookedHrsMap.put(empId, daysMap);
                }
            }
        }

        for (Object[] otherBooked : timesheetBookedHrsForOtherCrew) {
            LabelKeyTO labelKeyTO = getLabelKeyFromCrewBookedHrs(otherBooked);

            if (bookedHrsMap.get(labelKeyTO.getId()) != null) {
                TimeSheetEmpHandler.populateBookedHrs(bookedHrsMap.get(labelKeyTO.getId()), labelKeyTO);
            } else {
                Map<String, Double> daysMap = new HashMap<>();
                bookedHrsMap.put(labelKeyTO.getId(), daysMap);
                TimeSheetEmpHandler.populateBookedHrs(bookedHrsMap.get(labelKeyTO.getId()), labelKeyTO);
            }
        }

        for (Object[] samebooked : timesheetBookedHrsForSameCrewForAdditional) {
            LabelKeyTO labelKeyTO = getLabelKeyFromCrewBookedHrs(samebooked);
            if (bookedHrsMap.get(labelKeyTO.getId()) != null) {
                TimeSheetEmpHandler.populateBookedHrs(bookedHrsMap.get(labelKeyTO.getId()), labelKeyTO);
            } else {
                Map<String, Double> daysMap = new HashMap<>();
                bookedHrsMap.put(labelKeyTO.getId(), daysMap);
                TimeSheetEmpHandler.populateBookedHrs(bookedHrsMap.get(labelKeyTO.getId()), labelKeyTO);
            }
        }
        return bookedHrsMap;

    }

    private TimeSheetResp populateTimeSheet(TimeSheetGetReq timeSheetGetReq, List<TimeSheetEntity> timeSheetEntities) {
        TimeSheetResp timeSheetResp = new TimeSheetResp();
        if (CommonUtil.isListHasData(timeSheetEntities)) {
            for (TimeSheetEntity timeSheetEntity : timeSheetEntities) {
                timeSheetResp.getTimeSheetTOs().add(TimeSheetEmpHandler.convertEntityToPOJO(timeSheetEntity));
            }
        }
        return timeSheetResp;
    }

    public LabelKeyTOResp getProjSettingsTimeSheetDetails(TimeSheetGetReq timeSheetGetReq) {
    	LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        log.info("CommonUtil.isNonBlankLong(timeSheetGetReq.getProjId()) " + CommonUtil.isNonBlankLong(timeSheetGetReq.getProjId()));
        log.info("timeSheetGetReq.getProjId() " + timeSheetGetReq.getProjId());
        log.info("CommonUtil.isNotBlankStr(timeSheetGetReq.getApprStatus()) " + CommonUtil.isNotBlankStr(timeSheetGetReq.getApprStatus()));
        log.info("timeSheetGetReq.getApprStatus() " + timeSheetGetReq.getApprStatus());
        if (CommonUtil.isNonBlankLong(timeSheetGetReq.getProjId())
                && CommonUtil.isNotBlankStr(timeSheetGetReq.getApprStatus())) {
        	TimeSheetEntity timsheet = timeSheetRepository.findOne(timeSheetGetReq.getTimeSheetId());
        	log.info("timsheet.getId() " + timsheet.getId());
        	List<TimeSheetAdditionalTimeEntity> timeSheetAdditionalTime = new ArrayList<>();
        	log.info("///////////////////////////////////////////////////////");
        	timeSheetAdditionalTime = projTimeSheetRepository.findAdditionalTimeAvailable(timeSheetGetReq.getProjId(), timeSheetGetReq.getTimeSheetId());
        	log.info("///////////////////////////////////////////////////////");
        	log.info("timeSheetGetReq.getProjId() " + timeSheetGetReq.getProjId());
        	log.info("timeSheetGetReq.getTimeSheetId() " + timeSheetGetReq.getTimeSheetId());
            log.info("timeSheetAdditionalTime.size() " + timeSheetAdditionalTime.size());
        	if (timeSheetAdditionalTime.size() == 0) {
            	TimesheetNormalTimeEntity cutOffEntity = projTimeSheetRepositoryCopy
                        .findCutOffDaysForProject(timeSheetGetReq.getProjId(), timeSheetGetReq.getApprStatus());
            	log.info("--------------------------------------------------------- NORMAL TIME");
            	
            	Date weekCommencingdDt = timsheet.getWeekStartDate();

            	LocalDate localDate = ((java.sql.Date) weekCommencingdDt).toLocalDate();
            	
            	LocalDateTime localdateTime = localDate.atStartOfDay();
            	// Cut Off Time in Days from week  commencing  day - Raju Document
            	localdateTime = localdateTime.plusDays(1);
            	//--------------------------------------------------------------
            	log.info("localdateTime " + localdateTime);
                localdateTime = localdateTime.plusDays(cutOffEntity.getCutOffDays());
                localdateTime = localdateTime.plusHours(cutOffEntity.getCutOffHours());
                localdateTime = localdateTime.plusMinutes(cutOffEntity.getCutOffMinutes());
            	log.info("localdateTime " + localdateTime);
                if (localdateTime.isAfter(Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
                    labelKeyTO.setId(1L);
                } else {
                    Date endDate = projTimeSheetApprRepositoryCopy.findTimesheetEndDate(timeSheetGetReq.getProjId(),
                            timeSheetGetReq.getApprStatus());
                    if (endDate != null && (weekCommencingdDt.after(endDate) || weekCommencingdDt.equals(endDate))) {
                        labelKeyTO.setId(1L);
                    }
                }
            } else {
            	
            	log.info("--------------------------------------------------------- ADDITIONAL TIME");
            	LocalDateTime DBUpdatedDateTimeTomorrowWithCutoff = LocalDateTime.of(1900, 01, 01, 0, 0, 00, 00000);

                Date startDate = timeSheetGetReq.getWeekStartDate();
                Date endDate = timeSheetGetReq.getWeekEndDate();
                
                Boolean submitFlag = false;
                
                LocalDate weekStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate weekEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                if (timeSheetGetReq.getCrewId() != null) {
                    timeSheetAdditionalTime = projTimeSheetRepository.findCutOffDaysForCrew(timeSheetGetReq.getProjId(), timeSheetGetReq.getCrewId());
                } else if (timeSheetGetReq.getCrewId() == null) {
                	timeSheetAdditionalTime = projTimeSheetRepository.findCutOffDaysForIndividual(timeSheetGetReq.getProjId(), timeSheetGetReq.getEmpId());
                }
                log.info("timeSheetAdditionalTime size is " + timeSheetAdditionalTime.size());
                Date weekCommencingdDt = timsheet.getWeekStartDate();
                if (CommonUtil.isListHasData(timeSheetAdditionalTime)) {
                	for (TimeSheetAdditionalTimeEntity timeSheetAdditionalTimeEntity : timeSheetAdditionalTime) {
                		
                		
                		Date reqFromDate = timeSheetAdditionalTimeEntity.getFromDate();
                		Date reqToDate = timeSheetAdditionalTimeEntity.getToDate();
                		Date updatedDate = timeSheetAdditionalTimeEntity.getUpdatedOn();
                		
                		LocalDate localReqFromDate = reqFromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                		LocalDate localReqToDate = reqToDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                		LocalDate localDBUpdatedDate = updatedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                		
                		// a.compareTo(d) * d.compareTo(b) >= 0;
                		
                		 if((localReqFromDate.compareTo(weekStartDate) * weekStartDate.compareTo(localReqToDate) >= 0)  && 
                		 		(localReqFromDate.compareTo(weekEndDate) * weekEndDate.compareTo(localReqToDate)) >= 0) {
                			 submitFlag = true;
                			 log.info("submitFlag is true");
                		 } else {
                			 log.info(weekStartDate + " and " + weekEndDate + " does not lies between " + localReqFromDate + " - " + localReqToDate);
                		 }
                		 if (timeSheetAdditionalTimeEntity.getTimeSheetId().equals(timeSheetGetReq.getTimeSheetId()) && timeSheetAdditionalTimeEntity.getNotificationStatus().equals("Approved") &&timeSheetAdditionalTimeEntity.getType().equals(timeSheetGetReq.getApprStatus()))
                         	labelKeyTO.setId(1L);
                		 
                		 LocalDateTime DBUpdatedDateTime = localDBUpdatedDate.atStartOfDay();
                         LocalDateTime DBUpdatedDateTimeTomorrow = DBUpdatedDateTime.plusDays(1);
                         DBUpdatedDateTimeTomorrowWithCutoff = DBUpdatedDateTimeTomorrow.plusHours(timeSheetAdditionalTimeEntity.getGrantHrs());
                         log.info("DBUpdatedDateTimeTomorrowWithCutoff is " + DBUpdatedDateTimeTomorrowWithCutoff);
                	}
                }
                
                //if (localdateTime.isAfter(Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
                if (submitFlag && DBUpdatedDateTimeTomorrowWithCutoff.isAfter(Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
                	log.info("inside if");
                    labelKeyTO.setId(1L);
                } else {
                	log.info("inside else");
                    Date endDate1 = projTimeSheetApprRepositoryCopy.findTimesheetEndDate(timeSheetGetReq.getProjId(),
                            timeSheetGetReq.getApprStatus());
                    if (endDate1 != null && (weekCommencingdDt.after(endDate1) || weekCommencingdDt.equals(endDate1))) {
                        labelKeyTO.setId(1L);
                    }
                }
            }
            labelKeyTOResp.getLabelKeyTOs().add(labelKeyTO);
        }
        return labelKeyTOResp;
    }

    public TimeSheetWageCostCodeMap timeSheetWageCodeMap(TimeSheetGetMapReq timeSheetGetMapReq) {
        TimeSheetWageCostCodeMap timeSheetWageCostCodeMap = new TimeSheetWageCostCodeMap();
        List<LabelKeyTO> labelKeyTOs = timeSheetProcRepository.getAllCostCodeWageMap(timeSheetGetMapReq.getProjId(),
                timeSheetGetMapReq.getWeekStartDate(), timeSheetGetMapReq.getTimeSheetId(),
                timeSheetGetMapReq.getCrewId());
        if (CommonUtil.isListHasData(labelKeyTOs)) {
            for (LabelKeyTO labelKeyTO : labelKeyTOs) {
                timeSheetWageCostCodeMap.getLabelKeyTOList().put(
                        labelKeyTO.getCode() + "-" + labelKeyTO.getName() + "-" + labelKeyTO.getEmail(),
                        labelKeyTO.getId());

            }
        }
        return timeSheetWageCostCodeMap;
    }

    public TimeSheetResp getTimeSheetOnLoadInddviduls(TimeSheetGetReq timeSheetGetReq) {
        TimeSheetResp timeSheetResp = new TimeSheetResp();
        Calendar c = Calendar.getInstance();
        Integer differncdeDays = 0;
        if (timeSheetGetReq.getAdditional() != null && timeSheetGetReq.getAdditional().intValue() == 1) {
            differncdeDays = 7;
        } else {
            differncdeDays = TimeManagementUtil.getDateDiffernce(timeSheetGetReq.getWeekStartDate(),
                    timeSheetGetReq.getWeekEndDate());
        }
        for (int i = 0; i <= differncdeDays; i++) {
            c.setTime(timeSheetGetReq.getWeekStartDate());
            c.add(Calendar.DATE, i);
            timeSheetResp.getTimeSheetDays().add(TimeManagementUtil.getDayWithDDMMMYYYYFormat(c.getTime()));
        }
        List<LabelKeyTO> empRegDetails = empAttendanceProcRepository.findEmpRegDetailsInvidiual(
                timeSheetGetReq.getProjId(), timeSheetGetReq.getWeekStartDate(), timeSheetGetReq.getWeekEndDate(),
                AppUserUtils.getClientId(), AppUserUtils.getUserId());
        for (LabelKeyTO labelKeyTO : empRegDetails) {
            timeSheetResp.getEmpRegMap().put(labelKeyTO.getId(), labelKeyTO);
        }
        return timeSheetResp;
    }

    @Override
    public List<LabelKeyTO> getTimeSheetReqUserReport(TimeSheetReqUserGetReq timeSheetReqUserGetReq) {
        List<Long> projIds = timeSheetReqUserGetReq.getProjIds();
        List<LabelKeyTO> empDetails = new ArrayList<>();
        if (!projIds.isEmpty()) {
            List<Object[]> empList = timeSheetRepository.timeSheetReqUserDetails(projIds);
            for (Object[] emp : empList) {
                LabelKeyTO labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId((Long) emp[0]);
                labelKeyTO.setCode((String) emp[1]);
                labelKeyTO.setName((String) emp[2]);
                empDetails.add(labelKeyTO);
            }

        }
        return empDetails;
    }

    @Override
    public List<TimesheetReportTO> getTimeSheetApprStatusReport(TimeSheetApprStatusGetReq timeSheetApprStatusGetReq) {

        List<TimeSheetEntity> timesheetList = null;
        List<Long> userIds = timeSheetApprStatusGetReq.getSupervisorIds();
        if (userIds == null || userIds.isEmpty()) {
            timesheetList = timeSheetRepository.timeSheetApprovalStatusReport(timeSheetApprStatusGetReq.getProjIds(),
                    CommonUtil.convertStringToDate(timeSheetApprStatusGetReq.getFromDate()),
                    CommonUtil.convertStringToDate(timeSheetApprStatusGetReq.getToDate()),
                    timeSheetApprStatusGetReq.getApprStatus());
        }

        else {
            timesheetList = timeSheetRepository.timeSheetApprovalStatusReport(timeSheetApprStatusGetReq.getProjIds(),
                    userIds, CommonUtil.convertStringToDate(timeSheetApprStatusGetReq.getFromDate()),
                    CommonUtil.convertStringToDate(timeSheetApprStatusGetReq.getToDate()),
                    timeSheetApprStatusGetReq.getApprStatus());
        }
        List<TimesheetReportTO> timesheets = new ArrayList<>();
        for (TimeSheetEntity timeSheetEntity : timesheetList) {
            TimesheetReportTO timesheetReportTO = new TimesheetReportTO();

            ProjMstrEntity proj = timeSheetEntity.getProjMstrEntity();
            ProjectTO projectTO = new ProjectTO();
            timesheetReportTO.setProjId(proj.getProjectId());
            projectTO.setName(proj.getProjName());
            projectTO.setCode(proj.getCode());
            ProjMstrEntity projParent = proj.getParentProjectMstrEntity();
            if (projParent != null) {
                projectTO.setParentName(projParent.getProjName());
                timesheetReportTO.setParentProjId(projParent.getProjectId());
                projectTO.setParentCode(projParent.getCode());
            }
            timesheetReportTO.setProjectTO(projectTO);
            timesheetReportTO.setWeekCommenceDay(CommonUtil.convertDateToString(timeSheetEntity.getWeekStartDate()));
            ProjCrewMstrEntity crew = timeSheetEntity.getProjCrewMstrEntity();
            if (crew != null) {
                timesheetReportTO.setCrewId(crew.getId());
                timesheetReportTO.setCrewCode(crew.getCode());
                timesheetReportTO.setCrewName(crew.getDesc());
            }
            timesheetReportTO.setCode(TimeSheetEmpHandler.generateTimeSheetCode(timeSheetEntity));
            UserMstrEntity reqUser = timeSheetEntity.getReqUserMstrEntity();
            if (reqUser != null) {
                timesheetReportTO.setReqUserId(reqUser.getUserId());
                timesheetReportTO.setReqUserName(reqUser.getDisplayName());
                timesheetReportTO.setReqUserCode(reqUser.getEmpCode());
            }
            UserMstrEntity apprUser = timeSheetEntity.getApprUserMstrEntity();
            if (apprUser != null) {
                timesheetReportTO.setApprUserId(apprUser.getUserId());
                timesheetReportTO.setApprUserName(apprUser.getDisplayName());
                timesheetReportTO.setApprUserCode(apprUser.getEmpCode());
            }
            timesheetReportTO.setApprStatus(timeSheetEntity.getApprStatus());
            timesheets.add(timesheetReportTO);
        }
        return timesheets;

    }

    @Override
    public List<ManPowerActualHrsTO> getManpowerPeriodicalReport(
            ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq) {
        List<Long> projIds = manpowerPeroidicalHrsGetReq.getProjIds();
        List<Long> companyIds = manpowerPeroidicalHrsGetReq.getCmpIds();
        List<Long> empClassList = null;
        Date fromDate = CommonUtil.convertStringToDate(manpowerPeroidicalHrsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(manpowerPeroidicalHrsGetReq.getToDate());
        List<TimeSheetEmpWorkDtlEntity> timeSheetWorkHrs;
        List<WorkDairyEmpWageEntity> workDairyHrs;
        if (manpowerPeroidicalHrsGetReq.getCategory() != null) {
            empClassList = projEmpClassRepositoryCopy.getEmpClassByEmpCategoryName(projIds,
                    manpowerPeroidicalHrsGetReq.getCategory());
            // If No employee class found for given category then return
            if (empClassList.isEmpty()) {
                return new ArrayList<>();
            } else {
                timeSheetWorkHrs = timeSheetEmpDtlRepository.getManpowerActualHrsForReport(projIds, companyIds, toDate,
                        empClassList);
                workDairyHrs = empWageWorkDairyRepository.getWorkDairyManPowerActualHrs(projIds, companyIds, toDate,
                        empClassList);
            }
        } else {
            timeSheetWorkHrs = timeSheetEmpDtlRepository.getManpowerActualHrsForReport(projIds, companyIds, toDate);
            workDairyHrs = empWageWorkDairyRepository.getWorkDairyManPowerActualHrs(projIds, companyIds, toDate);
        }

        Map<ManPowerActualHrsTO, List<ReportHoursTO>> manPowerHrsMap = new HashMap<>();

        ManPowerActualHrsReportHandler manPowerReportHandler = new ManPowerActualHrsReportHandler();
        manPowerReportHandler.getTimesheetHrs(timeSheetWorkHrs, manPowerHrsMap, null);
        manPowerReportHandler.getWorkDairyHrs(workDairyHrs, manPowerHrsMap, null, empCostWorkDairyRepository);

        // Calculate prev and reporting time values
        Set<ManPowerActualHrsTO> manPowerKeySet = manPowerHrsMap.keySet();
        ReportHoursTO hr;
        for (ManPowerActualHrsTO key : manPowerKeySet) {
            List<ReportHoursTO> hrsList = manPowerHrsMap.get(key);
            for (Iterator<ReportHoursTO> hrsIterator = hrsList.iterator(); hrsIterator.hasNext();) {
                hr = hrsIterator.next();
                if (hr.getDate().before(fromDate)) {
                    key.addPrevHrs(hr);
                    hrsIterator.remove();
                } else if (hr.getDate().before(toDate) || hr.getDate().equals(toDate)) {
                    key.addCurrentHrs(hr);
                } else {
                    hrsIterator.remove();
                }
            }
        }

        manPowerReportHandler.setHrsList(manPowerHrsMap);
        return manPowerKeySet.stream().collect(Collectors.toList());

    }

    @Override
    public List<ManPowerActualHrsTO> getManpowerDateWiseHrsReport(
            ManpowerPeroidicalHrsGetReq manpowerPeroidicalHrsGetReq) {

        List<Long> projIds = manpowerPeroidicalHrsGetReq.getProjIds();
        List<Long> companyIds = manpowerPeroidicalHrsGetReq.getCmpIds();
        List<Long> empClassList = null;
        Date fromDate = CommonUtil.convertStringToDate(manpowerPeroidicalHrsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(manpowerPeroidicalHrsGetReq.getToDate());
        List<TimeSheetEmpWorkDtlEntity> timeSheetWorkHrs;
        List<WorkDairyEmpWageEntity> workDairyHrs;
        if (manpowerPeroidicalHrsGetReq.getCategory() != null) {
            empClassList = projEmpClassRepositoryCopy.getEmpClassByEmpCategoryName(projIds,
                    manpowerPeroidicalHrsGetReq.getCategory());
            // If No employee class found for given category then return
            if (empClassList.isEmpty()) {
                return new ArrayList<>();
            } else {
                timeSheetWorkHrs = timeSheetEmpDtlRepository.getManpowerActualHrsDateWiseReport(projIds, companyIds,
                        fromDate, toDate, empClassList);
                workDairyHrs = empWageWorkDairyRepository.getWorkDairyManPowerDateWiseActualHrs(projIds, companyIds,
                        fromDate, toDate, empClassList);
            }
        } else {
            timeSheetWorkHrs = timeSheetEmpDtlRepository.getManpowerActualHrsDateWiseReport(projIds, companyIds,
                    fromDate, toDate);
            workDairyHrs = empWageWorkDairyRepository.getWorkDairyManPowerDateWiseActualHrs(projIds, companyIds,
                    fromDate, toDate);
        }

        Map<ManPowerActualHrsTO, List<ReportHoursTO>> manPowerHrsMap = new HashMap<>();

        ManPowerActualHrsReportHandler manPowerReportHandler = new ManPowerActualHrsReportHandler();
        manPowerReportHandler.getTimesheetHrs(timeSheetWorkHrs, manPowerHrsMap, projEmpClassRepositoryCopy);
        manPowerReportHandler.removeTimeSheetDays(fromDate, toDate, manPowerHrsMap);
        manPowerReportHandler.getWorkDairyHrs(workDairyHrs, manPowerHrsMap, projEmpClassRepositoryCopy,
                empCostWorkDairyRepository);
        manPowerReportHandler.setHrsList(manPowerHrsMap);

        return manPowerHrsMap.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public List<ManPowerCostCodeDailyReportTO> getManpowerCostCodeWiseReport(
            ManpowerPeroidicalHrsGetReq manpowerCostCodeGetReq) {
        List<Long> projIds = manpowerCostCodeGetReq.getProjIds();
        Date workDate = CommonUtil.convertStringToDate(manpowerCostCodeGetReq.getFromDate());
        List<TimeSheetEmpWorkDtlEntity> timeSheetHrs = timeSheetEmpDtlRepository.getManpowerActualHrsByDate(projIds,
                workDate);
        List<WorkDairyEmpWageEntity> workDairyHrs = empWageWorkDairyRepository
                .getWorkDairyManPowerActualHrsByDate(projIds, workDate);

        ManPowerCostWiseReportHandler manPowerCostWiseReportHandler = new ManPowerCostWiseReportHandler();
        List<ManPowerCostCodeDailyReportTO> reportData = manPowerCostWiseReportHandler.getTimeSheetHrs(timeSheetHrs,
                projEmpClassRepositoryCopy, workDate, employeeChargeoutRatesService);
        reportData.addAll(manPowerCostWiseReportHandler.getWkorDairyHrs(workDairyHrs, projEmpClassRepositoryCopy,
                employeeChargeoutRatesService, empCostWorkDairyRepository, false));

        return reportData;
    }

    @Override
    public ManPowerStandardHrsResp getManpowerActualStandardReport(
            ManpowerStandardHrsGetReq manpowerStandardHrsGetReq) {

        List<Long> projIds = manpowerStandardHrsGetReq.getProjIds();
        List<Long> companyIds = manpowerStandardHrsGetReq.getCmpIds();
        List<Long> costIds = manpowerStandardHrsGetReq.getCostCodeIds();
        List<Long> crewIds = manpowerStandardHrsGetReq.getCrewIds();

        List<Long> empClassList = null;
        Date fromDate = CommonUtil.convertStringToDate(manpowerStandardHrsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(manpowerStandardHrsGetReq.getToDate());

        List<TimeSheetEmpWorkDtlEntity> timeSheetWorkHrs;
        List<WorkDairyEmpWageEntity> workDairyHrs;

        if (manpowerStandardHrsGetReq.getCategory() != null) {
            empClassList = projEmpClassRepositoryCopy.getEmpClassByEmpCategoryName(projIds,
                    manpowerStandardHrsGetReq.getCategory());
            // If No employee class found for given category then return
            if (empClassList.isEmpty()) {
                return new ManPowerStandardHrsResp();
            } else {
                timeSheetWorkHrs = timeSheetEmpDtlRepository.getManpowerActualHrsDateWiseReport(projIds, companyIds,
                        costIds, crewIds, fromDate, toDate, empClassList);
                workDairyHrs = empWageWorkDairyRepository.getWorkDairyManPowerDateWiseActualHrs(projIds, companyIds,
                        costIds, crewIds, fromDate, toDate, empClassList);
            }
        } else {
            timeSheetWorkHrs = timeSheetEmpDtlRepository.getManpowerActualHrsDateWiseReport(projIds, companyIds,
                    costIds, crewIds, fromDate, toDate);
            workDairyHrs = empWageWorkDairyRepository.getWorkDairyManPowerDateWiseActualHrs(projIds, companyIds,
                    costIds, crewIds, fromDate, toDate);
        }

        Map<ManPowerActualHrsTO, List<ReportHoursTO>> manPowerHrsMap = new HashMap<>();
        ManPowerActualHrsReportHandler manPowerReportHandler = new ManPowerActualHrsReportHandler();
        manPowerReportHandler.getTimesheetHrs(timeSheetWorkHrs, manPowerHrsMap, projEmpClassRepositoryCopy);
        manPowerReportHandler.removeTimeSheetDays(fromDate, toDate, manPowerHrsMap);
        new ManPowerStandardHrsReportHandler().getWorkDairyHrs(workDairyHrs, manPowerHrsMap, projEmpClassRepositoryCopy,
                empCostWorkDairyRepository, costIds);
        manPowerReportHandler.setHrsList(manPowerHrsMap);

        ManPowerStandardHrsResp manPowerStandardHrsResp = new ManPowerStandardHrsResp();
        manPowerStandardHrsResp.setManPowerActualHrsTOs(manPowerHrsMap.keySet().stream().collect(Collectors.toList()));
        manPowerStandardHrsResp.setStandardHrsMap(projGeneralRepositoryCopy.getStandardHrsOfProjects(projIds).stream()
                .collect(Collectors.toMap(o -> o[0], o -> o[1])));
        return manPowerStandardHrsResp;

    }

    @Override
    public List<ManPowerCostCodeDailyReportTO> getManpowerIdleHrsReport(
            ManpowerStandardHrsGetReq manpowerIdleHrsGetReq) {

        List<Long> projIds = manpowerIdleHrsGetReq.getProjIds();
        List<Long> crewIds = manpowerIdleHrsGetReq.getCrewIds();
        List<Long> empClassList = null;
        Date fromDate = CommonUtil.convertStringToDate(manpowerIdleHrsGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(manpowerIdleHrsGetReq.getToDate());

        List<WorkDairyEmpWageEntity> workDairyHrs;
        if (manpowerIdleHrsGetReq.getCategory() != null) {
            empClassList = projEmpClassRepositoryCopy.getEmpClassByEmpCategoryName(projIds,
                    manpowerIdleHrsGetReq.getCategory());
            // If No employee class found for given category then return
            if (empClassList.isEmpty()) {
                return new ArrayList<>();
            } else {
                workDairyHrs = empWageWorkDairyRepository.getWorkDairyManPowerDateWiseIdleHrs(projIds, crewIds,
                        fromDate, toDate, empClassList);
            }
        } else {
            workDairyHrs = empWageWorkDairyRepository.getWorkDairyManPowerDateWiseIdleHrs(projIds, crewIds, fromDate,
                    toDate);
        }

        return new ManPowerCostWiseReportHandler().getWkorDairyHrs(workDairyHrs, projEmpClassRepositoryCopy,
                employeeChargeoutRatesService, empCostWorkDairyRepository, true);
    }

    @Override
    public List<CurrentEmployeeDetails> getManpowerCurrentEmployeeReport(
            ManpowerPeroidicalHrsGetReq manpowerCurrentEmpGetReq) {
        List<Long> projIds = manpowerCurrentEmpGetReq.getProjIds();
        List<Long> companyIds = manpowerCurrentEmpGetReq.getCmpIds();
        List<Long> empClassList = null;
        List<EmpAttendanceEntity> empAttendanceList = null;
        if (manpowerCurrentEmpGetReq.getCategory() != null) {
            empClassList = projEmpClassRepositoryCopy.getEmpClassByEmpCategoryName(projIds,
                    manpowerCurrentEmpGetReq.getCategory());
            // If No employee class found for given category then return
            if (empClassList.isEmpty()) {
                return new ArrayList<>();
            } else {
                empAttendanceList = empAttendanceRepository.getCurrentEmployeeList(projIds, companyIds, empClassList);
            }
        } else {
            empAttendanceList = empAttendanceRepository.getCurrentEmployeeList(projIds, companyIds);
        }

        return new ManPowerCurrentEmployeeHandler().getCurrentEmployeeList(empAttendanceList,
                projEmpClassRepositoryCopy, empChargeOutRateRepositoryCopy, empContactDtlRepositoryCopy);
    }

    @Override
    public List<ManPowerPlannedValuesTO> getManpowerPlannedAndEarnedValues(
            ManpowerPeroidicalHrsGetReq manpowerPlannedValuesReq) {

        List<Long> projIds = manpowerPlannedValuesReq.getProjIds();
        Date fromDate = CommonUtil.convertStringToDate(manpowerPlannedValuesReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(manpowerPlannedValuesReq.getToDate());
        List<TimeSheetEmpWorkDtlEntity> timeSheetWorkHrs = timeSheetEmpDtlRepository
                .getManpowerActualHrsDateWise(projIds, fromDate, toDate);
        List<WorkDairyEmpWageEntity> workDairyHrs = empWageWorkDairyRepository
                .getWorkDairyManPowerDateWiseActualHrs(projIds, fromDate, toDate);
        List<Object[]> earnedValuesList = progressWorkDairyRepository.getManpowerEarnedValues(projIds);
        List<ResourceAssignmentDataValueEntity> resourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllBaselines(projIds, fromDate, toDate);

        List<ManPowerPlannedValuesTO> manPowerPlannedValuesTOList = new ArrayList<>();
        ManPowerPlannedValuesReportHandler manPowerPlannedValuesReportHandler = new ManPowerPlannedValuesReportHandler();
        manPowerPlannedValuesReportHandler.getTimesheetActualHrsHrs(timeSheetWorkHrs, manPowerPlannedValuesTOList);
        manPowerPlannedValuesReportHandler.addPlanValues(resourceAssignmentDataValueEntities, manPowerPlannedValuesTOList);
        manPowerPlannedValuesReportHandler.removeTimeSheetDays(fromDate, toDate, manPowerPlannedValuesTOList);
        manPowerPlannedValuesReportHandler.getWorkDairyActualHrs(workDairyHrs, manPowerPlannedValuesTOList,
                empCostWorkDairyRepository);
        manPowerPlannedValuesReportHandler.setEarnedValues(earnedValuesList, manPowerPlannedValuesTOList);

        // sort list based on projId and date
        manPowerPlannedValuesTOList.sort(Comparator.comparing(ManPowerPlannedValuesTO::getProjId)
                .thenComparing(ManPowerPlannedValuesTO::getDate));
        return manPowerPlannedValuesTOList;
    }

    @Override
    public List<ProjectEarnedValueDetails> getProjEarnedValues(ManpowerPeroidicalHrsGetReq manpowerActualValuesReq) {
        List<Long> projIds = manpowerActualValuesReq.getProjIds();

        List<ProjectEarnedValueDetails> earnedValuesTOs = new ArrayList<>();

        List<Object[]> earnedValuesList = progressWorkDairyRepository.getManpowerEarnedValuesByProj(projIds);

        for (Object[] earnedValue : earnedValuesList) {

            ProjectEarnedValueDetails earnedValueTO = new ProjectEarnedValueDetails();

            earnedValueTO.setProjId((Long) earnedValue[0]);
            earnedValueTO.setProjName((String) earnedValue[1]);
            earnedValueTO.setParentProjId((Long) earnedValue[2]);
            earnedValueTO.setParentProjName((String) earnedValue[3]);
            earnedValueTO.setEarnedHrs((Double) earnedValue[4]);
            earnedValueTO.setEarnedAmount((Double) earnedValue[5]);
            if (earnedValueTO.getEarnedAmount() == 0) earnedValueTO.setEarnedAmount((Double) earnedValue[6]);
            
            earnedValuesTOs.add(earnedValueTO);
        }

        return earnedValuesTOs;

    }

    private LabelKeyTO getLabelKeyFromCrewBookedHrs(Object[] bookedhrsArray) {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId((Long) bookedhrsArray[0]);
        Map<String, String> displayMap = new HashMap<>();

        displayMap.put(CommonConstants.DAY1_BOOKED_HRS,
                bookedhrsArray[1] == null ? null : String.valueOf((Double) bookedhrsArray[1]));
        displayMap.put(CommonConstants.DAY2_BOOKED_HRS,
                bookedhrsArray[2] == null ? null : String.valueOf((Double) bookedhrsArray[2]));
        displayMap.put(CommonConstants.DAY3_BOOKED_HRS,
                bookedhrsArray[3] == null ? null : String.valueOf((Double) bookedhrsArray[3]));
        displayMap.put(CommonConstants.DAY4_BOOKED_HRS,
                bookedhrsArray[4] == null ? null : String.valueOf((Double) bookedhrsArray[4]));
        displayMap.put(CommonConstants.DAY5_BOOKED_HRS,
                bookedhrsArray[5] == null ? null : String.valueOf((Double) bookedhrsArray[5]));
        displayMap.put(CommonConstants.DAY6_BOOKED_HRS,
                bookedhrsArray[6] == null ? null : String.valueOf((Double) bookedhrsArray[6]));
        displayMap.put(CommonConstants.DAY7_BOOKED_HRS,
                bookedhrsArray[7] == null ? null : String.valueOf((Double) bookedhrsArray[7]));
        labelKeyTO.setDisplayNamesMap(displayMap);
        return labelKeyTO;
    }
    
    @Override
	public TimeSheetResp getCreatedTimeSheets(
			EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
		List<TimeSheetEntity> timeSheetEntityList = null;
		List<Long> userProjectsEntities = null;
    	Date fromDate = CommonUtil.convertStringToDate(employeeAttendanceRecordSheetsSearchReq.getFromDate());
    	Date toDate = CommonUtil.convertStringToDate(employeeAttendanceRecordSheetsSearchReq.getToDate());

    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		timeSheetEntityList = timeSheetRepository.findAll(AppUserUtils.getUserId(), fromDate, toDate,AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		timeSheetEntityList = timeSheetRepository.findAll(fromDate, toDate,AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		timeSheetEntityList = timeSheetRepository.findAll(AppUserUtils.getUserId(), fromDate, toDate, employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		timeSheetEntityList = timeSheetRepository.findAll(fromDate, toDate, employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());
    	userProjectsEntities = userProjectsRepository
                .findUserProjIds(AppUserUtils.getUserId(), 1);
    	List<TimeSheetTO> timeSheetTOList = new ArrayList<TimeSheetTO>();
    	for (TimeSheetEntity timeSheetEntity : timeSheetEntityList)
    		timeSheetTOList.add(TimeSheetEmpHandler.convertEntityToPOJO1(timeSheetEntity,userProjectsEntities)); 
    	TimeSheetResp timeSheetResp = new TimeSheetResp();
    	timeSheetResp.setTimeSheetTOs(timeSheetTOList);
    	return timeSheetResp;
	}
    
    @Override
	public TimeSheetResp getSubmittedTimeSheets(
			EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
		List<TimeSheetEntity> timeSheetEntityList = null;
		List<Long> userProjectsEntities = null;
    	Date fromDate = CommonUtil.convertStringToDate(employeeAttendanceRecordSheetsSearchReq.getFromDate());
    	Date toDate = CommonUtil.convertStringToDate(employeeAttendanceRecordSheetsSearchReq.getToDate());

    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		timeSheetEntityList = timeSheetRepository.findAllSubmitted(AppUserUtils.getUserId(), fromDate, toDate,AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		timeSheetEntityList = timeSheetRepository.findAllSubmitted(fromDate, toDate,AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		timeSheetEntityList = timeSheetRepository.findAllSubmitted(AppUserUtils.getUserId(), fromDate, toDate, employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		timeSheetEntityList = timeSheetRepository.findAllSubmitted(fromDate, toDate, employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());
    	userProjectsEntities = userProjectsRepository
                .findUserProjIds(AppUserUtils.getUserId(), 1);
    	List<TimeSheetTO> timeSheetTOList = new ArrayList<TimeSheetTO>();
    	for (TimeSheetEntity timeSheetEntity : timeSheetEntityList)
    		timeSheetTOList.add(TimeSheetEmpHandler.convertEntityToPOJO1(timeSheetEntity,userProjectsEntities)); 
    	TimeSheetResp timeSheetResp = new TimeSheetResp();
    	timeSheetResp.setTimeSheetTOs(timeSheetTOList);
    	return timeSheetResp;
	}
}
