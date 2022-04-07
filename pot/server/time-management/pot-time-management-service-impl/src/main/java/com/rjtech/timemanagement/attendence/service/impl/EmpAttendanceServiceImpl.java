package com.rjtech.timemanagement.attendence.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AttendanceTypes;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.projschedule.model.ResourceAssignmentDataValueEntity;
import com.rjtech.projschedule.repository.ProjManpowerRepositoryCopy;
import com.rjtech.projschedule.repository.ResourceAssignmentDataValueRepositoryCopy;
import com.rjtech.projsettings.model.AttendanceAddtionalTimeApprEntity;
import com.rjtech.projsettings.model.AttendanceNormalTimeEntity;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.projsettings.model.ProjManpowerEntity;
import com.rjtech.projsettings.repository.ProjAttendenceApprRepository;
import com.rjtech.projsettings.repository.ProjAttendenceRepository;
import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpEnrollmentRepositoryCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timemanagement.attendance.dto.EmpAttendanceDtlTO;
import com.rjtech.timemanagement.attendance.dto.EmpAttendanceMstrTO;
import com.rjtech.timemanagement.attendance.dto.EmpAttendanceTO;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceDtlEntity;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceEntity;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceMstrEntity;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceReasonEntity;
import com.rjtech.timemanagement.attendance.model.ProjEmpClassMstrEntityCopy;
import com.rjtech.timemanagement.attendance.req.DailyAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.EmpAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.EmpAttendanceSaveReq;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.attendance.resp.AttendanceEmpRegResp;
import com.rjtech.timemanagement.attendance.resp.EmpAttendanceOnloadResp;
import com.rjtech.timemanagement.attendance.resp.EmpAttendanceResp;
import com.rjtech.timemanagement.attendance.resp.EmpAttendanceSheetResp;
import com.rjtech.timemanagement.attendance.service.EmpAttendanceService;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceDtlRepository;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceMstrRepository;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceProcRepository;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceReasonRepository;
import com.rjtech.timemanagement.attendence.repository.EmpAttendanceRepository;
import com.rjtech.timemanagement.attendence.service.handler.EmpAttendanceHandler;
//import com.rjtech.timemanagement.register.emp.model.EmpProjRigisterEntityCopy;
import com.rjtech.timemanagement.util.TimeManagementUtil;
import com.rjtech.timemanagement.workdairy.repository.EmpRegisterDtlRepository;
import com.rjtech.timemanagement.workdairy.repository.ProjCrewMstrRepository;
import com.rjtech.timemanagement.workdairy.repository.ProjGeneralMstrRepositoryCopy;

@Service(value = "empAttendanceService")
@RJSService(modulecode = "empAttendanceService")
@Transactional
public class EmpAttendanceServiceImpl implements EmpAttendanceService {

    private static final Logger log = LoggerFactory.getLogger(EmpAttendanceServiceImpl.class);

    @Autowired
    private EmpAttendanceMstrRepository empAttendanceMstrRepository;

    @Autowired
    private EmpAttendanceRepository empAttendanceRepository;

    @Autowired
    private EmpAttendanceReasonRepository empAttendanceReasonRepository;

    @Autowired
    private EmpAttendanceProcRepository empAttendanceProcRepository;

    @Autowired
    private ProjCrewMstrRepository projCrewMstrRepository;

    @Autowired
    private EmpRegisterDtlRepository empRegisterDtlRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private EmpEnrollmentRepositoryCopy empEnrollmentRepository;

    @Autowired
    private ProjAttendenceRepository projAttendenceRepository;

    @Autowired
    private ProjAttendenceApprRepository projAttendenceApprRepository;

    @Autowired
    private EmpAttendanceDtlRepository empAttendanceDtlRepository;

    @Autowired
    private ProjEmpClassRepositoryCopy projEmpClassRepository;
    
    @Autowired
    private ResourceAssignmentDataValueRepositoryCopy resourceAssignmentDataValueRepository;
    
    @Autowired
    private ProjManpowerRepositoryCopy projManpowerRepository;
    
    @Autowired
    private ProjGeneralMstrRepositoryCopy projGeneralMstrRepository;

    public EmpAttendanceOnloadResp getEmpAttendance(EmpAttendanceGetReq empAttendenceGetReq) {
        String attendenceMonth = empAttendenceGetReq.getAttendenceMonth();
        List<EmpAttendanceMstrEntity> empAttendanceEntities = new ArrayList<>();
        if (CommonUtil.isNotBlankStr(attendenceMonth)) {
            empAttendanceEntities = empAttendanceMstrRepository.findAttendance(empAttendenceGetReq.getProjId(),
                    empAttendenceGetReq.getCrewId(), attendenceMonth, empAttendenceGetReq.getStatus());
        }
        EmpAttendanceOnloadResp empAttendenceOnloadResp = new EmpAttendanceOnloadResp();
        for (EmpAttendanceMstrEntity empAttendanceEntity : empAttendanceEntities) {
            empAttendenceOnloadResp
                    .setEmpAttendanceMstrTO(EmpAttendanceHandler.convertEntityToPOJO(empAttendanceEntity));
        }
        empAttendenceOnloadResp.setAttendenceDays(
                TimeManagementUtil.getDaysByAttendanceMonth(empAttendenceGetReq.getAttendenceMonth()));
        return empAttendenceOnloadResp;
    }

    public AttendanceEmpRegResp getNonAttendanceEmpRegDetails(EmpAttendanceGetReq empAttendanceGetReq) {
        List<Date> dates = TimeManagementUtil.getDatesByAttendanceMonth(empAttendanceGetReq.getAttendenceMonth());
        long projId = empAttendanceGetReq.getProjId();
        long crewId = empAttendanceGetReq.getCrewId();
        Date startDate = dates.get(0);
        Date endDate = dates.get(1);
        List<EmpRegisterDtlEntity> empRegisterDtlEntities = empAttendanceRepository
                .findAttendanceByProjIdCrewId(projId, crewId, startDate);
        List<Object[]> empObjs;
        if (empRegisterDtlEntities.isEmpty()) {
            empObjs = empEnrollmentRepository.findNonAttendanceEmployees(projId, startDate, endDate);
        } else {
            empObjs = empEnrollmentRepository.findNonAttendanceEmployees(projId, startDate, endDate,
                    empRegisterDtlEntities);
        }
        AttendanceEmpRegResp attendanceEmpRegResp = new AttendanceEmpRegResp();
        for (Object[] object : empObjs) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(Long.valueOf(object[0].toString().trim()));
            labelKeyTO.setCode(String.valueOf(object[1]));
            Map<String, String> displayMap = labelKeyTO.getDisplayNamesMap();
            displayMap.put(CommonConstants.FIRST_NAME, String.valueOf(object[2]));
            displayMap.put(CommonConstants.LAST_NAME, String.valueOf(object[3]));
            displayMap.put(CommonConstants.GENDER, String.valueOf(object[4]));
            displayMap.put(CommonConstants.CLASS_TYPE, String.valueOf(object[5]));
            displayMap.put(CommonConstants.COMPANY_CATG_NAME, String.valueOf(object[6]));
            attendanceEmpRegResp.getEmpRegLabelKeyTOs().add(labelKeyTO);
        }
        return attendanceEmpRegResp;
    }

    public AttendanceEmpRegResp copyAttendanceEmpDetails(EmpAttendanceGetReq empAttendanceGetReq) {
    	
    	List<Date> dates = TimeManagementUtil.getDatesByAttendanceMonth(empAttendanceGetReq.getAttendenceMonth());
        long projId = empAttendanceGetReq.getProjId();
        long crewId = empAttendanceGetReq.getCrewId();
        Date startDate = dates.get(0);
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	LocalDate localDate = LocalDate.now();
    	String currDt = dtf.format(localDate);
    	SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy");
    	Date currDate = null;
		try {
			currDate = formatter1.parse(currDt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        List<EmpRegisterDtlEntity> empRegisterDtlEntities = empAttendanceRepository
                .findAttendanceForByProjIdCrewId(projId, crewId, startDate);

        List<Object[]> empObjs;

        if (empRegisterDtlEntities.isEmpty()) {
            empObjs = null;
        } else {
            empObjs = empEnrollmentRepository.findAttendanceEmployeesFor(projId,
                    empRegisterDtlEntities, currDate);
        }
       
        AttendanceEmpRegResp attendanceEmpRegResp = new AttendanceEmpRegResp();
        if(empObjs != null) {
        	for (Object[] object : empObjs) {
                LabelKeyTO labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(Long.valueOf(object[0].toString().trim()));
                labelKeyTO.setCode(String.valueOf(object[1]));
                Map<String, String> displayMap = labelKeyTO.getDisplayNamesMap();
                displayMap.put(CommonConstants.FIRST_NAME, String.valueOf(object[2]));
                displayMap.put(CommonConstants.LAST_NAME, String.valueOf(object[3]));
                displayMap.put(CommonConstants.GENDER, String.valueOf(object[4]));
                displayMap.put(CommonConstants.CLASS_TYPE, String.valueOf(object[5]));
                displayMap.put(CommonConstants.COMPANY_CATG_NAME, String.valueOf(object[6]));
                attendanceEmpRegResp.getEmpRegLabelKeyTOs().add(labelKeyTO);
            }
        }
        
        return attendanceEmpRegResp;
    	/*
        List<Date> dates = TimeManagementUtil.getDatesByAttendanceMonth(empAttendanceGetReq.getAttendenceMonth());
        List<LabelKeyTO> empRegDetails = empAttendanceProcRepository.copyAttendanceEmpDetails(
                empAttendanceGetReq.getProjId(), empAttendanceGetReq.getCrewId(), empAttendanceGetReq.getAttendenceId(),
                dates.get(0), dates.get(1));
        AttendanceEmpRegResp attendanceEmpRegResp = new AttendanceEmpRegResp();
        for (LabelKeyTO labelKeyTO : empRegDetails) {
            attendanceEmpRegResp.getEmpRegLabelKeyTOs().add(labelKeyTO);
        }
        return attendanceEmpRegResp;
		*/
    }

    public AttendanceEmpRegResp getEmpAttendanceSheets(EmpAttendanceGetReq empAttendenceGetReq) {
        List<LabelKeyTO> labelKeyTOs = empAttendanceProcRepository.findAttendanceSheets(empAttendenceGetReq.getProjId(),
                empAttendenceGetReq.getCrewId());
        AttendanceEmpRegResp attendanceEmpRegResp = new AttendanceEmpRegResp();
        for (LabelKeyTO labelKeyTO : labelKeyTOs) {
            attendanceEmpRegResp.getEmpRegLabelKeyTOs().add(labelKeyTO);
        }
        return attendanceEmpRegResp;

    }

    public EmpAttendanceOnloadResp getAttendanceDays(EmpAttendanceGetReq empAttendenceGetReq) {
        EmpAttendanceOnloadResp empAttendanceOnloadResp = new EmpAttendanceOnloadResp();
        empAttendanceOnloadResp.getAttendenceDays()
                .addAll(TimeManagementUtil.getDaysByAttendanceMonth(empAttendenceGetReq.getAttendenceMonth()));
        return empAttendanceOnloadResp;
    }

    public EmpAttendanceResp getEmpAttendanceRecords(EmpAttendanceGetReq empAttendenceGetReq) {
        List<EmpAttendanceEntity> empAttendanceEntities = empAttendanceRepository.findAttendanceRecords(
                empAttendenceGetReq.getAttendenceId(), empAttendenceGetReq.getProjId(),
                empAttendenceGetReq.getStatus());

        EmpAttendanceResp empAttendenceResp = new EmpAttendanceResp();

        long projId = empAttendenceGetReq.getProjId();
        long crewId = empAttendenceGetReq.getCrewId();

        Map<String, Boolean> enableAttendanceDaysMap = enableAttendanceDays(projId, crewId,
                AttendanceTypes.EMP.getName());

        List<EmpProjRigisterEntity> empProjDates = empAttendanceRepository
                .getEmpProjMobilizationDates(empAttendenceGetReq.getProjId(), empAttendenceGetReq.getAttendenceId());

        Map<String, Boolean> empDemobilizationDateMap = getMobDates(empProjDates,
                empAttendenceGetReq.getAttendenceMonth());

        List<String> totalDays = TimeManagementUtil.getDaysByAttendanceMonth(empAttendenceGetReq.getAttendenceMonth());
        Map<String, Boolean> daysMap = new HashMap<>();
        for (String day : totalDays) {
            daysMap.put(day, true);
        }

        Map<String, Boolean> existingDaysMap = new HashMap<>();
        EmpAttendanceDtlTO attendenceDtlTO = null;
        for (EmpAttendanceEntity empAttendanceEntity : empAttendanceEntities) {
            EmpAttendanceTO attendenceTO = EmpAttendanceHandler.convertAttandanceEntityToPOJO(empAttendanceEntity);
            EmpAttendanceReasonEntity empAttendanceReasonEntity = empAttendanceReasonRepository.findAttendanceRecords(
                    empAttendenceGetReq.getProjId(), empAttendenceGetReq.getCrewId(),
                    empAttendenceGetReq.getAttendenceId(), attendenceTO.getEmpId(),
                    empAttendenceGetReq.getAttendenceMonth());
            if (empAttendanceReasonEntity != null) {
                attendenceTO.setReason(empAttendanceReasonEntity.getReason());
            }

            empAttendenceResp.getEmpAttendenceTOs().add(attendenceTO);
            for (EmpAttendanceDtlEntity attendanceDtlEntity : empAttendanceEntity.getEmpAttendanceDtlEntities()) {
                attendenceDtlTO = EmpAttendanceHandler.convertDtlEntityToPOJO(attendanceDtlEntity);
                attendenceDtlTO.setAttendenceDay(
                        TimeManagementUtil.getDDMMMMYYYYFormat(attendanceDtlEntity.getAttendanceDate()));
                if (!empDemobilizationDateMap.containsKey(
                        empAttendanceEntity.getEmpId().getId() + "#" + attendenceDtlTO.getAttendenceDay())) {
                    attendenceDtlTO.setAttendenceFlag(false);
                } else {
                    attendenceDtlTO
                            .setAttendenceFlag(enableAttendanceDaysMap.containsKey(attendenceDtlTO.getAttendenceDay()));
                }
                existingDaysMap.put(empAttendanceEntity.getEmpId().getId() + "#" + attendenceDtlTO.getAttendenceDay(),
                        true);
                attendenceTO.getEmpAttendenceDtlMap().put(attendenceDtlTO.getAttendenceDay(), attendenceDtlTO);
            }
        }

        List<EmpAttendanceEntity> otherCrewAttendanceEntities = empAttendanceRepository.findOtherCrewAttendanceRecords(
                empAttendenceGetReq.getProjId(), empAttendenceGetReq.getCrewId(), empAttendenceGetReq.getStatus(),
                empAttendenceGetReq.getAttendenceMonth());
        Map<String, Boolean> otherCrewExistingDaysMap = new HashMap<>();

        for (EmpAttendanceEntity empAttendanceEntity : otherCrewAttendanceEntities) {
            for (EmpAttendanceDtlEntity attendanceDtlEntity : empAttendanceEntity.getEmpAttendanceDtlEntities()) {
                EmpAttendanceDtlTO otherCrewAttendenceDtlTO = EmpAttendanceHandler
                        .convertDtlEntityToPOJO(attendanceDtlEntity);
                otherCrewAttendenceDtlTO.setAttendenceDay(
                        TimeManagementUtil.getDDMMMMYYYYFormat(attendanceDtlEntity.getAttendanceDate()));
                if (CommonUtil.isNotBlankStr(attendanceDtlEntity.getProjAttdCode())) {
                    otherCrewExistingDaysMap.put(
                            empAttendanceEntity.getEmpId().getId() + "#" + otherCrewAttendenceDtlTO.getAttendenceDay(),
                            true);
                }
            }
        }

        for (EmpAttendanceTO empAttendanceTO : empAttendenceResp.getEmpAttendenceTOs()) {
            for (Entry<String, Boolean> dayEntry : daysMap.entrySet()) {
                if (existingDaysMap.get(empAttendanceTO.getEmpId() + "#" + dayEntry.getKey()) == null) {
                    EmpAttendanceDtlTO empAttendanceDtlTO = new EmpAttendanceDtlTO();

                    empAttendanceDtlTO.setEmpDtlId(empAttendanceTO.getId());
                    if (!empDemobilizationDateMap.containsKey(empAttendanceTO.getEmpId() + "#" + dayEntry.getKey())) {
                        // set as false, only when date is not in between mob and demob dates
                        empAttendanceDtlTO.setAttendenceFlag(false);
                    } else if (otherCrewExistingDaysMap
                            .get(empAttendanceTO.getEmpId() + "#" + dayEntry.getKey()) != null
                            || enableAttendanceDaysMap.get(dayEntry.getKey()) == null) {
                        // If user already has attendance in other crew, set this as false. So that user can not enter attendance again here.
                        empAttendanceDtlTO.setAttendenceFlag(false);
                    } else if (enableAttendanceDaysMap.get(dayEntry.getKey()) != null) {
                        // From project settings, if user can enter attendance, then set this as true
                        empAttendanceDtlTO.setAttendenceFlag(true);
                    }
                    empAttendanceDtlTO.setAttendenceDate(CommonUtil.convertStringToDate(dayEntry.getKey()));
                    empAttendanceDtlTO.setAttendenceDay(dayEntry.getKey());
                    empAttendanceTO.getEmpAttendenceDtlMap().put(dayEntry.getKey(), empAttendanceDtlTO);
                } else if (otherCrewExistingDaysMap.get(empAttendanceTO.getEmpId() + "#" + dayEntry.getKey()) != null) {
                    EmpAttendanceDtlTO empAttendanceDtlTO = empAttendanceTO.getEmpAttendenceDtlMap()
                            .get(dayEntry.getKey());
                    // If user already has attendance in other crew, set this as false
                    empAttendanceDtlTO.setEmpDtlId(empAttendanceTO.getId());
                    empAttendanceDtlTO.setAttendenceFlag(false);
                    empAttendanceDtlTO.setAttendenceDate(CommonUtil.convertStringToDate(dayEntry.getKey()));
                    empAttendanceDtlTO.setAttendenceDay(dayEntry.getKey());
                    empAttendanceTO.getEmpAttendenceDtlMap().put(dayEntry.getKey(), empAttendanceDtlTO);
                }
            }

        }
        return empAttendenceResp;

    }

    public LabelKeyTO saveEmpAttendanceRecords(EmpAttendanceSaveReq empAttendenceSaveReq) {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        EmpAttendanceMstrEntity empAttendanceMstrEntity = new EmpAttendanceMstrEntity();
        EmpAttendanceEntity empAttendanceEntity = null;
        if (CommonUtil.isBlankLong(empAttendenceSaveReq.getAttendenceId())) {
            empAttendanceMstrEntity = createAttendanceSheet(empAttendenceSaveReq);
            labelKeyTO.setId(empAttendanceMstrEntity.getId());
            labelKeyTO.setCode(empAttendanceMstrEntity.getCode());

        } else {
            empAttendanceMstrEntity.setId(empAttendenceSaveReq.getAttendenceId());
        }
        List<EmpAttendanceEntity> empAttendanceEntities = new ArrayList<>();
        for (EmpAttendanceTO empAttendanceTO : empAttendenceSaveReq.getEmpAttendenceTOs()) {
            empAttendanceEntity = EmpAttendanceHandler.convertAttandancePOJOToEntity(empAttendanceTO,
                    projCrewMstrRepository, empRegisterDtlRepository);
            empAttendanceEntity.setEmpAttendanceMstrEntity(empAttendanceMstrEntity);
            for (Entry<String, EmpAttendanceDtlTO> entry : empAttendanceTO.getEmpAttendenceDtlMap().entrySet()) {
                if (entry.getValue().isAttendenceFlag()) {
                    EmpAttendanceDtlTO value = entry.getValue();
                    EmpAttendanceDtlEntity empAttendenceDtlEntity = null;
                    if (CommonUtil.isNonBlankLong(value.getId())) {
                        empAttendenceDtlEntity = empAttendanceDtlRepository.findOne(value.getId());
                    }
                    if (CommonUtil.objectNullCheck(empAttendenceDtlEntity)) {
                        empAttendenceDtlEntity = new EmpAttendanceDtlEntity();
                    }
                    empAttendenceDtlEntity = EmpAttendanceHandler.convertDtlPOJOToEntity(empAttendenceDtlEntity, value,
                            empAttendanceRepository);
                    empAttendenceDtlEntity.setEmpAttendanceEntity(empAttendanceEntity);
                    empAttendanceEntity.getEmpAttendanceDtlEntities().add(empAttendenceDtlEntity);
                }
            }
            empAttendanceEntities.add(empAttendanceEntity);
        }
        empAttendanceRepository.save(empAttendanceEntities);
        EmpAttendanceReasonEntity empAttendanceReasonEntity = null;

        for (EmpAttendanceTO attendanceTO : empAttendenceSaveReq.getEmpAttendenceTOs()) {

            if (!CommonUtil.isBlankStr(attendanceTO.getReason())) {

                empAttendanceReasonEntity = empAttendanceReasonRepository.findAttendanceRecords(
                        empAttendenceSaveReq.getProjId(), attendanceTO.getCrewId(), attendanceTO.getAttandanceId(),
                        attendanceTO.getEmpId(), empAttendenceSaveReq.getAttendenceMonth());

                if (empAttendanceReasonEntity == null) {
                    empAttendanceReasonEntity = new EmpAttendanceReasonEntity();
                    empAttendanceReasonEntity.setReason(attendanceTO.getReason());
                    empAttendanceReasonEntity.setAttendanceId(attendanceTO.getAttandanceId());
                    empAttendanceReasonEntity.setAttendanceMonth(empAttendenceSaveReq.getAttendenceMonth());
                    empAttendanceReasonEntity.setEmpId(attendanceTO.getEmpId());
                    ProjCrewMstrEntity projCrewMstrEntity = projCrewMstrRepository
                            .findOne(attendanceTO.getCrewId());
                    empAttendanceReasonEntity.setProjCrewMstrEntity(projCrewMstrEntity);
                    ProjMstrEntity projMstrEntity = epsProjRepository.findOne(empAttendenceSaveReq.getProjId());
                    empAttendanceReasonEntity.setProjMstrEntity(projMstrEntity);
                    empAttendanceReasonRepository.save(empAttendanceReasonEntity);
                } else {
                    empAttendanceReasonEntity.setReason(attendanceTO.getReason());
                    empAttendanceReasonRepository.save(empAttendanceReasonEntity);
                }

            }

        }

        return labelKeyTO;
    }

    private EmpAttendanceMstrEntity createAttendanceSheet(EmpAttendanceSaveReq empAttendenceSaveReq) {
        EmpAttendanceMstrEntity empAttendanceMstrEntity = new EmpAttendanceMstrEntity();
        Date convertedDate = null;
        convertedDate = CommonUtil.getMMMYYYDate(empAttendenceSaveReq.getAttendenceMonth());

        empAttendanceMstrEntity.setAttendenceMonth(convertedDate);
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(empAttendenceSaveReq.getProjId());
        if (null != projMstrEntity) {
            empAttendanceMstrEntity.setProjId(projMstrEntity);
        }
        ProjCrewMstrEntity projCrewMstrEntity = projCrewMstrRepository.findOne(empAttendenceSaveReq.getCrewId());
        if (null != projCrewMstrEntity) {
            empAttendanceMstrEntity.setCrewId(projCrewMstrEntity);
        }
        empAttendanceMstrEntity.setStatus(StatusCodes.ACTIVE.getValue());

        empAttendanceMstrEntity = empAttendanceMstrRepository.save(empAttendanceMstrEntity);

        if (CommonUtil.isBlankStr(empAttendanceMstrEntity.getCode())) {
            String code = "EAR-" + empAttendanceMstrEntity.getProjId().getCode() + "-"
                    + empAttendanceMstrEntity.getCrewId().getCode() + "-" + empAttendanceMstrEntity.getId();
            empAttendanceMstrEntity.setCode(code);
        }
        return empAttendanceMstrEntity;
    }

    public EmpAttendanceResp addEmpToAttendanceRecord(EmpAttendanceSaveReq empAttendanceSaveReq,
            EmpAttendanceGetReq empAttendanceGetReq) {

        EmpAttendanceResp empAttendenceResp = new EmpAttendanceResp();

        long projId = empAttendanceGetReq.getProjId();
        long crewId = empAttendanceGetReq.getCrewId();
        
        Map<String, Boolean> enableAttendanceDaysMap = enableAttendanceDays(projId, crewId,
                AttendanceTypes.EMP.getName());

        List<String> totalDays = TimeManagementUtil.getDaysByAttendanceMonth(empAttendanceGetReq.getAttendenceMonth());
        Map<String, Boolean> daysMap = new HashMap<>();
        for (String day : totalDays) {
            daysMap.put(day, true);
        }

        Map<String, Boolean> existingDaysMap = new HashMap<>();
        List<Long> empIds = new ArrayList<>();
        for (EmpAttendanceTO attendanceTO : empAttendanceSaveReq.getEmpAttendenceTOs()) {
            empIds.add(attendanceTO.getEmpId());
            empAttendenceResp.getEmpAttendenceTOs().add(attendanceTO);
        }

        List<EmpAttendanceEntity> otherCrewAttendanceEntities = empAttendanceRepository.findOtherCrewAttendanceRecords(
                empAttendanceGetReq.getProjId(), empAttendanceGetReq.getCrewId(), empAttendanceGetReq.getStatus(),
                empAttendanceGetReq.getAttendenceMonth());

        Map<String, Boolean> otherCrewExistingDaysMap = new HashMap<>();
        EmpAttendanceDtlTO otherCrewAttendenceDtlTO = null;

        for (EmpAttendanceEntity empAttendanceEntity : otherCrewAttendanceEntities) {
            for (EmpAttendanceDtlEntity attendanceDtlEntity : empAttendanceEntity.getEmpAttendanceDtlEntities()) {
                otherCrewAttendenceDtlTO = EmpAttendanceHandler.convertDtlEntityToPOJO(attendanceDtlEntity);
                otherCrewAttendenceDtlTO.setAttendenceDay(
                        TimeManagementUtil.getDDMMMMYYYYFormat(attendanceDtlEntity.getAttendanceDate()));

                if (CommonUtil.isNotBlankStr(attendanceDtlEntity.getProjAttdCode())) {
                    otherCrewExistingDaysMap.put(
                            empAttendanceEntity.getEmpId().getId() + "#" + otherCrewAttendenceDtlTO.getAttendenceDay(),
                            true);
                }
            }
        }

        List<EmpProjRigisterEntity> empProjDates = empAttendanceRepository.findByEmpId(empIds);

        Map<String, Boolean> empDemobilizationDateMap = getMobDates(empProjDates,
                empAttendanceGetReq.getAttendenceMonth());

        for (EmpAttendanceTO empAttendanceTO : empAttendenceResp.getEmpAttendenceTOs()) {
            for (Entry<String, Boolean> dayEntry : daysMap.entrySet()) {
                if (existingDaysMap.get(empAttendanceTO.getEmpId() + "#" + dayEntry.getKey()) == null) {
                    EmpAttendanceDtlTO empAttendanceDtlTO = new EmpAttendanceDtlTO();
                    empAttendanceDtlTO.setEmpDtlId(empAttendanceTO.getId());

                    if (!empDemobilizationDateMap.containsKey(empAttendanceTO.getEmpId() + "#" + dayEntry.getKey())) {
                        // set as false, only when date is not in between mob and demob dates
                        empAttendanceDtlTO.setAttendenceFlag(false);
                    } else if (otherCrewExistingDaysMap
                            .get(empAttendanceTO.getEmpId() + "#" + dayEntry.getKey()) != null
                            || enableAttendanceDaysMap.get(dayEntry.getKey()) == null) {
                        // If user already has attendance in other crew, set this as false. So that user can not enter attendance again here.
                        empAttendanceDtlTO.setAttendenceFlag(false);
                    } else if (enableAttendanceDaysMap.get(dayEntry.getKey()) != null) {
                        // From project settings, if user can enter attendance, then set this as true
                        empAttendanceDtlTO.setAttendenceFlag(true);
                    }
                    empAttendanceDtlTO.setAttendenceDate(CommonUtil.convertStringToDate(dayEntry.getKey()));
                    empAttendanceDtlTO.setAttendenceDay(dayEntry.getKey());
                    empAttendanceTO.getEmpAttendenceDtlMap().put(dayEntry.getKey(), empAttendanceDtlTO);
                } else if (otherCrewExistingDaysMap.get(empAttendanceTO.getEmpId() + "#" + dayEntry.getKey()) != null) {
                    EmpAttendanceDtlTO empAttendanceDtlTO = empAttendanceTO.getEmpAttendenceDtlMap()
                            .get(dayEntry.getKey());
                    // If user already has attendance in other crew, set this as false
                    empAttendanceDtlTO.setEmpDtlId(empAttendanceTO.getId());
                    empAttendanceDtlTO.setAttendenceFlag(false);
                    empAttendanceDtlTO.setAttendenceDate(CommonUtil.convertStringToDate(dayEntry.getKey()));
                    empAttendanceDtlTO.setAttendenceDay(dayEntry.getKey());
                    empAttendanceTO.getEmpAttendenceDtlMap().put(dayEntry.getKey(), empAttendanceDtlTO);
                }
            }

        }
        return empAttendenceResp;
    }

    /**
     * 
     * This method will return enabled attendances days for proj and respective crew
     * 
     * @param projId
     * @param crewId
     * @return
     */
    public Map<String, Boolean> enableAttendanceDays(long projId, long crewId, String type) {
        Map<String, Boolean> enableAttendanceDaysMap = new HashMap<>();
        List<String> enableDates = new ArrayList<>();

        Calendar todayCal = Calendar.getInstance();
        Calendar nrmlCalLastDay = (Calendar) todayCal.clone();

        // Calculating days from normal time
        AttendanceNormalTimeEntity nrmlAtt = projAttendenceRepository.findProjAttendence(projId, type, 1);
        if (CommonUtil.objectNotNull(nrmlAtt)) {
            nrmlCalLastDay.add(Calendar.DATE, -nrmlAtt.getCutOffDays());
            nrmlCalLastDay.add(Calendar.HOUR, -nrmlAtt.getCutOffHours());
            nrmlCalLastDay.add(Calendar.MINUTE, -nrmlAtt.getCutOffMinutes());
        }
        enableDates.addAll(getDaysBetweenDates(nrmlCalLastDay.getTime(), todayCal.getTime()));

        // Calculating days from additional time requests
        List<AttendanceAddtionalTimeApprEntity> addtlEntites = projAttendenceApprRepository
                .findAddtionalTimeForProjCrew(projId, crewId, todayCal.getTime(), type);

        for (AttendanceAddtionalTimeApprEntity entity : addtlEntites) {
        	//if updated date time + cutoff time less than current date time - enable
        	Calendar additionalRequestValidity = Calendar.getInstance();
        	additionalRequestValidity.setTime(entity.getUpdatedOn());

        	additionalRequestValidity.set(Calendar.HOUR_OF_DAY, 0);
        	additionalRequestValidity.set(Calendar.MINUTE, 0);
        	additionalRequestValidity.set(Calendar.SECOND, 0);
        	additionalRequestValidity.set(Calendar.MILLISECOND, 0);
        	additionalRequestValidity.add(Calendar.DAY_OF_MONTH, 1);
        	additionalRequestValidity.add(Calendar.DATE, entity.getCutOffDays());
        	additionalRequestValidity.add(Calendar.HOUR, entity.getCutOffHours());
        	additionalRequestValidity.add(Calendar.MINUTE, entity.getCutOffMinutes());
        	
        	if (additionalRequestValidity.after(todayCal)) 
        		enableDates.addAll(getDaysBetweenDates(entity.getFromDate(), entity.getToDate()));
        }

        // Add final list to Map
        for (String enableDate : enableDates) {
            enableAttendanceDaysMap.put(enableDate, true);
        }
        return enableAttendanceDaysMap;

    }

    private List<String> getDaysBetweenDates(Date startdate, Date enddate) {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime((Date) startdate.clone());
        while (cal.getTime().before(enddate)) {
            Date result = cal.getTime();
            dates.add(CommonUtil.convertDateToString(result));
            cal.add(Calendar.DATE, 1);
        }
        dates.add(CommonUtil.convertDateToString(enddate));
        return dates;
    }

    private Map<String, Boolean> getMobDates(List<EmpProjRigisterEntity> empProjDates, String attdMonth) {
        Map<String, Boolean> empDemobilizationDateMap = new HashMap<>();
        for (EmpProjRigisterEntity empRegCopy : empProjDates) {
            Date startDate = null;
            Date endDate = null;
            Date convertedDate = null;
            convertedDate = CommonUtil.getMMMYYYDate(attdMonth);
            Calendar c = Calendar.getInstance();
            c.setTime(convertedDate);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
            startDate = c.getTime();

            c = Calendar.getInstance();
            c.setTime(convertedDate);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            endDate = c.getTime();

            startDate = empRegCopy.getMobilaizationDate().before(startDate) ? startDate
                    : empRegCopy.getMobilaizationDate();
            endDate = (empRegCopy.getDeMobilaizationDate() != null
                    && empRegCopy.getDeMobilaizationDate().before(endDate)) ? empRegCopy.getDeMobilaizationDate()
                            : endDate;
            while (startDate.getTime() <= endDate.getTime()) {
                empDemobilizationDateMap.put(
                        empRegCopy.getEmpRegisterDtlEntity().getId() + "#" + CommonUtil.convertDateToString(startDate),
                        true);
                c = Calendar.getInstance();
                c.setTime(startDate);
                c.add(Calendar.DATE, 1);
                startDate = c.getTime();
            }
        }
        return empDemobilizationDateMap;
    }

    public List<LabelKeyTO> getDailyEmpAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq) {
        String attendenceMonth = dailyAttendanceGetReq.getMonth() + "-" + dailyAttendanceGetReq.getYear();
        List<EmpAttendanceEntity> empAttendanceEntities = empAttendanceRepository.findAttendanceRecords(
                dailyAttendanceGetReq.getProjIds(), dailyAttendanceGetReq.getCrewIds(), attendenceMonth);
        return processEmpAttResponseForReport(empAttendanceEntities, false);
    }

    public List<LabelKeyTO> getEmpAttendanceRecordsByDate(DailyAttendanceGetReq dailyAttendanceGetReq) {
        List<EmpAttendanceEntity> empAttendanceEntities = empAttendanceRepository.findAttendanceRecordsByDate(
                dailyAttendanceGetReq.getProjIds(), dailyAttendanceGetReq.getCrewIds(),
                dailyAttendanceGetReq.getDate());
        return processEmpAttResponseForReport(empAttendanceEntities, true);
    }

    public List<LabelKeyTO> getDailyEmpAttendanceReportBtwnDates(DailyAttendanceGetReq dailyAttendanceGetReq) {
        Date fromDate = CommonUtil.getMMMYYYDate(dailyAttendanceGetReq.getFromDate());
        Date toDate = CommonUtil.getMMMYYYDate(dailyAttendanceGetReq.getToDate());
        List<EmpAttendanceEntity> empAttendanceEntities = empAttendanceRepository.findAttendanceRecordsBtwnDates(
                dailyAttendanceGetReq.getProjIds(), dailyAttendanceGetReq.getCrewIds(), fromDate, toDate);
        return processEmpAttResponseByMonth(empAttendanceEntities);
    }

    private List<LabelKeyTO> processEmpAttResponseByMonth(List<EmpAttendanceEntity> empAttendanceEntities) {
        List<LabelKeyTO> labelKeyTOs = new ArrayList<>();
        for (EmpAttendanceEntity empAttendanceEntity : empAttendanceEntities) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            Map<String, String> namesMap = labelKeyTO.getDisplayNamesMap();
            EmpAttendanceMstrEntity empAttdMstr = empAttendanceEntity.getEmpAttendanceMstrEntity();
            if (empAttdMstr != null) {
                ProjMstrEntity projMstr = empAttdMstr.getProjId();
                if (projMstr != null) {
                    namesMap.put(CommonConstants.PROJ_ID, String.valueOf(projMstr.getProjectId()));
                    namesMap.put("projName", projMstr.getProjName());
                    ProjMstrEntity projParent = projMstr.getParentProjectMstrEntity();
                    if (projParent != null) {
                        namesMap.put("epsId", String.valueOf(projParent.getProjectId()));
                        namesMap.put("epsName", projParent.getProjName());
                    }
                    EmpRegisterDtlEntity empDtl = empAttendanceEntity.getEmpId();
                    if (empDtl != null) {
                        namesMap.put("empId", String.valueOf(empDtl.getId()));
                        namesMap.put("empCode", empDtl.getCode());
                        namesMap.put("empFirstName", empDtl.getFirstName());
                        namesMap.put("empLastName", empDtl.getLastName());
                        ProjEmpClassMstrEntityCopy projEmpClassMstrEntity = projEmpClassRepository
                                .getUserProjEmpClasses(projMstr.getProjectId(), empDtl.getEmpClassMstrEntity().getId(),
                                        StatusCodes.ACTIVE.getValue());
                        namesMap.put("tradeName", empDtl.getEmpClassMstrEntity().getName());
                        if (CommonUtil.objectNotNull(projEmpClassMstrEntity)) {
                            namesMap.put("empClassId", String.valueOf(projEmpClassMstrEntity.getId()));
                            namesMap.put("empCategory", projEmpClassMstrEntity.getProjEmpCategory());
                        }
                        CompanyMstrEntity company = empDtl.getCompanyMstrEntity();
                        if (company != null) {
                            namesMap.put("companyId", String.valueOf(company.getId()));
                            namesMap.put("companyName", company.getName());
                        }
                    }
                }
                ProjCrewMstrEntity crew = empAttdMstr.getCrewId();
                if (crew != null) {
                    namesMap.put("crewId", String.valueOf(crew.getId()));
                    namesMap.put("crewName", crew.getDesc());
                }
            }
            Map<String, Map<String, String>> attdByMonth = new HashMap<>();
            for (EmpAttendanceDtlEntity empAttdDtl : empAttendanceEntity.getEmpAttendanceDtlEntities()) {
                if (CommonUtil.isNotBlankStr(empAttdDtl.getProjAttdCode())) {
                    String key = String.valueOf(empAttendanceEntity.getId())
                            + CommonUtil.getMMMYYYFormat(empAttdDtl.getAttendanceDate());
                    Map<String, String> attdCodeCount = attdByMonth.get(key);
                    if (attdCodeCount == null) {
                        attdCodeCount = new HashMap<>();
                        attdCodeCount.put("month", CommonUtil.getMMMYYYFormat(empAttdDtl.getAttendanceDate()));
                    }
                    if (attdCodeCount.get(empAttdDtl.getProjAttdCode()) == null) {
                        attdCodeCount.put(empAttdDtl.getProjAttdCode(), "1");
                    } else {
                        String count = attdCodeCount.get(empAttdDtl.getProjAttdCode());
                        attdCodeCount.put(empAttdDtl.getProjAttdCode(), String.valueOf(Long.valueOf(count) + 1L));
                    }
                    attdByMonth.put(key, attdCodeCount);
                }
            }
            if (!attdByMonth.isEmpty()) {
                for (Entry<String, Map<String, String>> entry : attdByMonth.entrySet()) {
                    Map<String, String> attCounts = entry.getValue();
                    if (!attCounts.isEmpty()) {
                        LabelKeyTO newLabel = new LabelKeyTO();
                        newLabel.getDisplayNamesMap().putAll(labelKeyTO.getDisplayNamesMap());
                        newLabel.getDisplayNamesMap().putAll(attCounts);
                        labelKeyTOs.add(newLabel);
                    }
                }
            }
        }
        return labelKeyTOs;
    }

    private List<LabelKeyTO> processEmpAttResponseForReport(List<EmpAttendanceEntity> empAttendanceEntities,
            boolean needMobDate) {
        List<LabelKeyTO> labelKeyTOs = new ArrayList<>();
        for (EmpAttendanceEntity empAttendanceEntity : empAttendanceEntities) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            Map<String, String> namesMap = labelKeyTO.getDisplayNamesMap();
            EmpAttendanceMstrEntity empAttdMstr = empAttendanceEntity.getEmpAttendanceMstrEntity();
            if (empAttdMstr != null) {
                int total = 0;
                for (EmpAttendanceDtlEntity empAttdDtl : empAttendanceEntity.getEmpAttendanceDtlEntities()) {
                    if (CommonUtil.isNotBlankStr(empAttdDtl.getProjAttdCode())) {
                        if (empAttdDtl.getProjAttdCode().equalsIgnoreCase("P")) {
                            total++;
                        }
                        for (EmpProjRigisterEntity empProj : empAttendanceEntity.getEmpId()
                                .getProjEmpRigisterEntities()) {
                            if (empProj.getProjMstrEntity().getProjectId() == empAttdMstr.getProjId().getProjectId()
                                    && empProj.getMobilaizationDate() != null) {
                                List<EmpChargeOutRateEntity> mappedDate = empProj.getEmpchargeOutRateEntities()
                                        .stream()
                                        .filter(d -> d.getFromDate().getTime() == empAttdDtl.getAttendanceDate()
                                                .getTime() || d.getFromDate().before(empAttdDtl.getAttendanceDate()))
                                        .collect(Collectors.toList());
                                if (!mappedDate.isEmpty()) {
                                    EmpChargeOutRateEntity leaveEffectiveDate = mappedDate
                                            .get(mappedDate.size() - 1);
                                    namesMap.put("empChargeRate",
                                            leaveEffectiveDate.getNormalRate() != null
                                                    ? leaveEffectiveDate.getNormalRate().toString()
                                                    : "0");
                                }
                            }
                        }
                        namesMap.put(TimeManagementUtil.getDDMMMMYYYYFormat(empAttdDtl.getAttendanceDate()),
                                empAttdDtl.getProjAttdCode());
                    }
                }
                if (namesMap.keySet().isEmpty())
                    continue;
                namesMap.put("totalPresent", String.valueOf(total));
                ProjMstrEntity projMstr = empAttdMstr.getProjId();
                if (projMstr != null) {
                    namesMap.put(CommonConstants.PROJ_ID, String.valueOf(projMstr.getProjectId()));
                    namesMap.put("projName", projMstr.getProjName());
                    ProjMstrEntity projParent = projMstr.getParentProjectMstrEntity();
                    if (projParent != null) {
                        namesMap.put("epsId", String.valueOf(projParent.getProjectId()));
                        namesMap.put("epsName", projParent.getProjName());
                    }
                    EmpRegisterDtlEntity empDtl = empAttendanceEntity.getEmpId();
                    if (empDtl != null) {
                        namesMap.put("empId", String.valueOf(empDtl.getId()));
                        namesMap.put("empCode", empDtl.getCode());
                        namesMap.put("empFirstName", empDtl.getFirstName());
                        namesMap.put("empLastName", empDtl.getLastName());
                        namesMap.put("empGender", empDtl.getGender());
                        namesMap.put("empLocType", empDtl.getLocation());
                        ProjEmpClassMstrEntityCopy projEmpClassMstrEntity = projEmpClassRepository
                                .getUserProjEmpClasses(projMstr.getProjectId(), empDtl.getEmpClassMstrEntity().getId(),
                                        StatusCodes.ACTIVE.getValue());
                        namesMap.put("empClassId", String.valueOf(empDtl.getEmpClassMstrEntity().getId()));
                        namesMap.put("tradeName", empDtl.getEmpClassMstrEntity().getName());
                        if (CommonUtil.objectNotNull(projEmpClassMstrEntity)) {
                            namesMap.put("empCategory", projEmpClassMstrEntity.getProjEmpCategory());
                        }
                        CompanyMstrEntity company = empDtl.getCompanyMstrEntity();
                        if (company != null) {
                            namesMap.put("companyId", String.valueOf(company.getId()));
                            namesMap.put("companyName", company.getName());
                        }
                        if (needMobDate) {
                            for (EmpProjRigisterEntity empProj : empDtl.getProjEmpRigisterEntities()) {
                                if (empProj.getProjMstrEntity().getProjectId() == projMstr.getProjectId()
                                        && empProj.getMobilaizationDate() != null) {
                                    namesMap.put("empMobDate",
                                            CommonUtil.convertDateToDDMMYYYYString(empProj.getMobilaizationDate()));
                                    break;
                                }
                            }
                        }
                    }
                }
                ProjCrewMstrEntity crew = empAttdMstr.getCrewId();
                if (crew != null) {
                    namesMap.put("crewId", String.valueOf(crew.getId()));
                    namesMap.put("crewName", crew.getDesc());
                }
                labelKeyTOs.add(labelKeyTO);
            }
        }
        return labelKeyTOs;
    }

    @Override
    public List<LabelKeyTO> getEmpDailyResourceStatus(DailyAttendanceGetReq dailyAttendanceGetReq) {
    	List<ResourceAssignmentDataValueEntity> resourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllBaselines(dailyAttendanceGetReq.getProjIds(), CommonUtil.convertStringToDate(dailyAttendanceGetReq.getDate()), CommonUtil.convertStringToDate(dailyAttendanceGetReq.getDate()));
        List<EmpAttendanceEntity> empAttendanceEntities = empAttendanceRepository.findDailyResourceStatus(dailyAttendanceGetReq.getProjIds(), dailyAttendanceGetReq.getDate());
    	
    	Map<String, LabelKeyTO> labelMap = new HashMap<>();
    	for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataValueEntities) {
    		if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals("POT_EMPLOYEE")) continue;
    		List<ProjManpowerEntity> projManpowerEntity = projManpowerRepository.findbyIds(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId());
    		for(ProjManpowerEntity projManpowerEntiti: projManpowerEntity) {
    		if(projManpowerEntiti.getItemStatus().equals("APPROVED")) {
    		LabelKeyTO labelKeyTO = toLabelKeyTO(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity(), projManpowerEntiti.getEmpClassMstrEntity(), resourceAssignmentDataValueEntity.getBudgetUnits(), 0);
    		
    		if (dailyAttendanceGetReq.getEmpCats().contains(labelKeyTO.getDisplayNamesMap().get("empCategory")))
    			labelMap.put(labelKeyTO.getDisplayNamesMap().get(CommonConstants.PROJ_ID) + labelKeyTO.getDisplayNamesMap().get("empClassId") + labelKeyTO.getDisplayNamesMap().get("projEmpClassId"), labelKeyTO);
    		}
    	}
    	}
    	
    	
		for (EmpAttendanceEntity empAttendanceEntity : empAttendanceEntities) {
			int total = 0;
			for (EmpAttendanceDtlEntity empAttdDtl : empAttendanceEntity.getEmpAttendanceDtlEntities()) {
                if (CommonUtil.isNotBlankStr(empAttdDtl.getProjAttdCode()) && empAttdDtl.getProjAttdCode().equalsIgnoreCase("P")) {
                    total++;
                }
        	}
			
			LabelKeyTO labelKeyTO = toLabelKeyTO(empAttendanceEntity.getEmpAttendanceMstrEntity().getProjId(), empAttendanceEntity.getEmpId().getEmpClassMstrEntity(), 0D, total);
			if (dailyAttendanceGetReq.getEmpCats().contains(labelKeyTO.getDisplayNamesMap().get("empCategory"))) {
				if (labelMap.containsKey(labelKeyTO.getDisplayNamesMap().get(CommonConstants.PROJ_ID) + labelKeyTO.getDisplayNamesMap().get("empClassId") + labelKeyTO.getDisplayNamesMap().get("projEmpClassId"))) {
					labelMap.get(labelKeyTO.getDisplayNamesMap().get(CommonConstants.PROJ_ID) + labelKeyTO.getDisplayNamesMap().get("empClassId") + labelKeyTO.getDisplayNamesMap().get("projEmpClassId")).getDisplayNamesMap().replace("totalPresent", String.valueOf(total));
				} else {
					labelMap.put(labelKeyTO.getDisplayNamesMap().get(CommonConstants.PROJ_ID) + labelKeyTO.getDisplayNamesMap().get("empClassId") + labelKeyTO.getDisplayNamesMap().get("projEmpClassId"), labelKeyTO);
				}
			}
        }
    	
    	return new ArrayList<LabelKeyTO>(labelMap.values());
    }
    
    private LabelKeyTO toLabelKeyTO(ProjMstrEntity projMstrEntity, EmpClassMstrEntity empClassMstrEntity, double budgetUnits, int total) {
    	LabelKeyTO labelKeyTO = new LabelKeyTO();
		labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJ_ID, String.valueOf(projMstrEntity.getProjectId()));
		labelKeyTO.getDisplayNamesMap().put("projName", projMstrEntity.getProjName());
		labelKeyTO.getDisplayNamesMap().put("epsId", String.valueOf(projMstrEntity.getParentProjectMstrEntity().getProjectId()));
		labelKeyTO.getDisplayNamesMap().put("epsName", projMstrEntity.getParentProjectMstrEntity().getProjName());
		
		labelKeyTO.getDisplayNamesMap().put("empClassId", String.valueOf(empClassMstrEntity.getId()));
		labelKeyTO.getDisplayNamesMap().put("tradeName", empClassMstrEntity.getName());
		
		ProjEmpClassMstrEntityCopy projEmpClassMstrEntity = projEmpClassRepository.getUserProjEmpClasses(projMstrEntity.getProjectId(), empClassMstrEntity.getId(), StatusCodes.ACTIVE.getValue());
		labelKeyTO.getDisplayNamesMap().put("projEmpClassId", String.valueOf(projEmpClassMstrEntity.getId()));
		labelKeyTO.getDisplayNamesMap().put("empCategory", projEmpClassMstrEntity.getProjEmpCategory());

		ProjGeneralMstrEntity projGeneralMstrEntity = projGeneralMstrRepository.findProjGenerals(projMstrEntity.getProjectId(), 1);
		labelKeyTO.getDisplayNamesMap().put("totalPresent", String.valueOf(total));
		labelKeyTO.getDisplayNamesMap().put("plannedValue", String.valueOf((budgetUnits / projGeneralMstrEntity.getDefualtHrs())));
		labelKeyTO.getDisplayNamesMap().put("variance", String.valueOf((budgetUnits / projGeneralMstrEntity.getDefualtHrs()) - total));

		return labelKeyTO;
    }

    public EmpAttendanceSheetResp getEmployeeAttendanceRecordSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	List<EmpAttendanceMstrEntity> empAttendanceMstrEntityList = null;
    	List<Date> fromDates = TimeManagementUtil.getDatesByAttendanceMonth(employeeAttendanceRecordSheetsSearchReq.getFromDate());
    	List<Date> toDates = TimeManagementUtil.getDatesByAttendanceMonth(employeeAttendanceRecordSheetsSearchReq.getToDate());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		empAttendanceMstrEntityList = empAttendanceMstrRepository.findAll(AppUserUtils.getUserId(), fromDates.get(0), toDates.get(1),AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		empAttendanceMstrEntityList = empAttendanceMstrRepository.findAll(fromDates.get(0), toDates.get(1),AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		empAttendanceMstrEntityList = empAttendanceMstrRepository.findAll(AppUserUtils.getUserId(), fromDates.get(0), toDates.get(1), employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		empAttendanceMstrEntityList = empAttendanceMstrRepository.findAll(fromDates.get(0), toDates.get(1), employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());
    		
    	List<EmpAttendanceMstrTO> empAttendanceMstrTOList = new ArrayList<EmpAttendanceMstrTO>();
    	for (EmpAttendanceMstrEntity empAttendanceMstrEntity : empAttendanceMstrEntityList)
    		empAttendanceMstrTOList.add(EmpAttendanceHandler.convertEntityToPOJO(empAttendanceMstrEntity)); 
    	EmpAttendanceSheetResp empAttendanceSheetResp = new EmpAttendanceSheetResp();
    	empAttendanceSheetResp.setEmpAttendanceMstrTOs(empAttendanceMstrTOList);
    	return empAttendanceSheetResp;
    	
    }
}
