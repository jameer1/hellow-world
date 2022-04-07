
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AttendanceTypes;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.projectlib.model.ProjPlantClassMstrEntity;
//import com.rjtech.projectlib.model.ProjPlantClassMstrEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCrewRepositoryCopy;
import com.rjtech.projectlib.repository.ProjPlantClassRepositoryCopy;
//import com.rjtech.projschedule.model.ProjectPlantsDtlEntityCopy;
import com.rjtech.projschedule.model.ResourceAssignmentDataValueEntity;
import com.rjtech.projschedule.repository.ProjectPlantsRepository;
import com.rjtech.projschedule.repository.ResourceAssignmentDataValueRepositoryCopy;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.projsettings.model.ProjectPlantsDtlEntity;
import com.rjtech.register.plant.model.PlantRegProjEntity;
//import com.rjtech.register.plant.model.PlantRegProjEntityCopy;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
//import com.rjtech.register.plant.model.PlantRegisterDtlEntityCopy;
import com.rjtech.register.repository.plant.PlantRegProjRepositoryCopy;
import com.rjtech.register.repository.plant.PlantRegisterRepositoryCopy;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.timemanagement.attendance.dto.PlantAttendanceDtlTO;
import com.rjtech.timemanagement.attendance.dto.PlantAttendanceMstrTO;
import com.rjtech.timemanagement.attendance.dto.PlantAttendanceTO;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceDtlEntity;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceEntity;
import com.rjtech.timemanagement.attendance.model.PlantAttendanceDtlEntity;
import com.rjtech.timemanagement.attendance.model.PlantAttendanceEntity;
import com.rjtech.timemanagement.attendance.model.PlantAttendanceMstrEntity;
import com.rjtech.timemanagement.attendance.req.DailyAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.EmployeeAttendanceRecordSheetsSearchReq;
import com.rjtech.timemanagement.attendance.req.PlantAttendanceGetReq;
import com.rjtech.timemanagement.attendance.req.PlantAttendanceSaveReq;
import com.rjtech.timemanagement.attendance.resp.AttendancePlantRegResp;
import com.rjtech.timemanagement.attendance.resp.PlantAttendanceOnloadResp;
import com.rjtech.timemanagement.attendance.resp.PlantAttendanceResp;
import com.rjtech.timemanagement.attendance.resp.PlantAttendanceSheetResp;
import com.rjtech.timemanagement.attendance.service.EmpAttendanceService;
import com.rjtech.timemanagement.attendance.service.PlantAttendanceService;
import com.rjtech.timemanagement.attendence.repository.PlantAttendanceDtlRepository;
import com.rjtech.timemanagement.attendence.repository.PlantAttendanceMstrRepository;
import com.rjtech.timemanagement.attendence.repository.PlantAttendanceProcRepository;
import com.rjtech.timemanagement.attendence.repository.PlantAttendanceRepository;
import com.rjtech.timemanagement.attendence.service.handler.PlantAttendanceHandler;
//import com.rjtech.timemanagement.proj.settings.model.ProjGeneralMstrEntityCopy;
import com.rjtech.timemanagement.util.TimeManagementUtil;
import com.rjtech.timemanagement.workdairy.repository.ProjGeneralMstrRepositoryCopy;
import com.rjtech.projschedule.model.ResourceAssignmentDataEntity;
import com.rjtech.projschedule.repository.ResourceAssignmentDataRepository;

@Service(value = "plantAttendanceService")
@Transactional
public class PlantAttendanceServiceImpl implements PlantAttendanceService {

    private static final Logger log = LoggerFactory.getLogger(PlantAttendanceServiceImpl.class);

    @Autowired
    private PlantAttendanceMstrRepository plantAttendanceMstrRepository;

    @Autowired
    private PlantAttendanceRepository plantAttendaceRepository;

    @Autowired
    private PlantAttendanceProcRepository plantAttendanceProcRepository;

    @Autowired
    private ProjCrewRepositoryCopy projCrewRepository;

    @Autowired
    private PlantRegisterRepositoryCopy plantRegisterRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private EmpAttendanceService empAttendanceService;

    @Autowired
    private PlantRegProjRepositoryCopy plantRegProjRepository;

    @Autowired
    private PlantAttendanceDtlRepository plantAttendanceDtlRepository;

    @Autowired
    private ProjPlantClassRepositoryCopy projPlantClassRepository;
    
    @Autowired
    private ResourceAssignmentDataValueRepositoryCopy resourceAssignmentDataValueRepository;
    
    @Autowired
    private ProjectPlantsRepository projectPlantsRepository;
    
    @Autowired
    private ProjGeneralMstrRepositoryCopy projGeneralMstrRepository;
    
    @Autowired
    private ResourceAssignmentDataRepository resourceAssignmentDataRepository;

    public PlantAttendanceOnloadResp getPlantAttendance(PlantAttendanceGetReq plantAttendanceGetReq) {
        List<PlantAttendanceMstrEntity> plantAttendanceMstrEntities = plantAttendanceMstrRepository.findAttendance(
                plantAttendanceGetReq.getProjId(), plantAttendanceGetReq.getCrewId(),
                plantAttendanceGetReq.getAttendenceMonth(), plantAttendanceGetReq.getStatus());
        PlantAttendanceOnloadResp plantAttendanceOnloadResp = new PlantAttendanceOnloadResp();
        for (PlantAttendanceMstrEntity plantAttendanceMstrEntity : plantAttendanceMstrEntities) {
            plantAttendanceOnloadResp.setPlantAttendanceMstrTO(
                    PlantAttendanceHandler.convertMstrEntityToPOJO(plantAttendanceMstrEntity));
        }
        plantAttendanceOnloadResp.setAttendenceDays(
                TimeManagementUtil.getDaysByAttendanceMonth(plantAttendanceGetReq.getAttendenceMonth()));
        return plantAttendanceOnloadResp;
    }

    public AttendancePlantRegResp getNonAttendancePlantRegDetails(PlantAttendanceGetReq plantAttendanceGetReq) {
        List<Date> dates = TimeManagementUtil.getDatesByAttendanceMonth(plantAttendanceGetReq.getAttendenceMonth());
        long projId = plantAttendanceGetReq.getProjId();
        long crewId = plantAttendanceGetReq.getCrewId();
        Date startDate = dates.get(0);
        Date endDate = dates.get(1);

        List<PlantRegisterDtlEntity> plantRegisterDtlEntities = plantRegisterRepository
                .findAttendanceByProjIdCrewId(projId, crewId, startDate);
        List<Object[]> plantObjs;

        if (plantRegisterDtlEntities.isEmpty()) {
            plantObjs = plantRegProjRepository.findNonAttendancePlantRegDetails(projId, startDate, endDate);
        } else {
            plantObjs = plantRegProjRepository.findNonAttendancePlantRegDetails(projId, startDate, endDate,
                    plantRegisterDtlEntities);
        }

        AttendancePlantRegResp attendancePlantRegResp = new AttendancePlantRegResp();

        for (Object[] plantObj : plantObjs) {

            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(Long.valueOf(String.valueOf(plantObj[0])));
            labelKeyTO.setCode(String.valueOf(plantObj[1]));
            labelKeyTO.setName(String.valueOf(plantObj[2]));

            Map<String, String> displayMap = labelKeyTO.getDisplayNamesMap();
            displayMap.put(CommonConstants.PLANT_REG_NO, String.valueOf(plantObj[3]));
            displayMap.put(CommonConstants.PLANT_MANFACTURE, String.valueOf(plantObj[4]));
            displayMap.put(CommonConstants.PLANT_MODEL, String.valueOf(plantObj[5]));
            displayMap.put(CommonConstants.CLASS_TYPE, String.valueOf(plantObj[6]));
            displayMap.put(CommonConstants.PROCURE_CATG, String.valueOf(plantObj[7]));
            displayMap.put(CommonConstants.COMPANY_CATG_NAME, String.valueOf(plantObj[8]));

            labelKeyTO.setUnitOfMeasure(String.valueOf(plantObj[9]));
            attendancePlantRegResp.getPlantRegLabelKeyTOs().add(labelKeyTO);
        }

        return attendancePlantRegResp;

    }

    public AttendancePlantRegResp copyAttendancePlantDetails(PlantAttendanceGetReq plantAttendanceGetReq) {
    	
    	List<Date> dates = TimeManagementUtil.getDatesByAttendanceMonth(plantAttendanceGetReq.getAttendenceMonth());
        long projId = plantAttendanceGetReq.getProjId();
        long crewId = plantAttendanceGetReq.getCrewId();
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
        
        List<PlantRegisterDtlEntity> plantRegisterDtlEntities = plantRegisterRepository
                .findAttendanceForCopyByProjIdCrewId(projId, crewId, startDate);
        
        List<Object[]> plantObjs;
        
        if (plantRegisterDtlEntities.isEmpty()) {
        	plantObjs = null;
        } else {
        	plantObjs = plantRegProjRepository.findAttendancePlantForCopy(projId, 
                    plantRegisterDtlEntities, currDate);
        }
        
        AttendancePlantRegResp attendancePlantRegResp = new AttendancePlantRegResp();
        if(plantObjs != null) {
        	for (Object[] plantObj : plantObjs) {

                LabelKeyTO labelKeyTO = new LabelKeyTO();
                labelKeyTO.setId(Long.valueOf(String.valueOf(plantObj[0])));
                labelKeyTO.setCode(String.valueOf(plantObj[1]));
                labelKeyTO.setName(String.valueOf(plantObj[2]));

                Map<String, String> displayMap = labelKeyTO.getDisplayNamesMap();
                displayMap.put(CommonConstants.PLANT_REG_NO, String.valueOf(plantObj[3]));
                displayMap.put(CommonConstants.PLANT_MANFACTURE, String.valueOf(plantObj[4]));
                displayMap.put(CommonConstants.PLANT_MODEL, String.valueOf(plantObj[5]));
                displayMap.put(CommonConstants.CLASS_TYPE, String.valueOf(plantObj[6]));
                displayMap.put(CommonConstants.PROCURE_CATG, String.valueOf(plantObj[7]));
                displayMap.put(CommonConstants.COMPANY_CATG_NAME, String.valueOf(plantObj[8]));

                labelKeyTO.setUnitOfMeasure(String.valueOf(plantObj[9]));
                attendancePlantRegResp.getPlantRegLabelKeyTOs().add(labelKeyTO);
            }
        }
       
        return attendancePlantRegResp;
        
    	/*
        List<Date> dates = TimeManagementUtil.getDatesByAttendanceMonth(plantAttendanceGetReq.getAttendenceMonth());
        List<LabelKeyTO> plantRegDetails = plantAttendanceProcRepository.copyAttendancePlantDetails(
                plantAttendanceGetReq.getProjId(), plantAttendanceGetReq.getCrewId(),
                plantAttendanceGetReq.getAttendenceId(), dates.get(0), dates.get(1));
        AttendancePlantRegResp attendancePlantRegResp = new AttendancePlantRegResp();
        for (LabelKeyTO labelKeyTO : plantRegDetails) {
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.CREW_ID, plantAttendanceGetReq.getCrewId().toString());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.ATTENDANCE_ID,
                    plantAttendanceGetReq.getAttendenceId().toString());
            attendancePlantRegResp.getPlantRegLabelKeyTOs().add(labelKeyTO);
        }
        */
    }

    public AttendancePlantRegResp getPlantAttendanceSheets(PlantAttendanceGetReq plantAttendanceGetReq) {
        List<LabelKeyTO> labelKeyTOs = plantAttendanceProcRepository
                .getAttendanceSheets(plantAttendanceGetReq.getProjId(), plantAttendanceGetReq.getCrewId());
        AttendancePlantRegResp attendancePlantRegResp = new AttendancePlantRegResp();
        for (LabelKeyTO labelKeyTO : labelKeyTOs) {
            attendancePlantRegResp.getPlantRegLabelKeyTOs().add(labelKeyTO);
        }
        return attendancePlantRegResp;

    }

    public PlantAttendanceResp getPlantAttendanceRecords(PlantAttendanceGetReq plantAttendenceGetReq) {
        List<PlantAttendanceEntity> plantAttendanceEntities = plantAttendaceRepository.findAttendanceRecords(
                plantAttendenceGetReq.getAttendenceId(), plantAttendenceGetReq.getProjId(),
                plantAttendenceGetReq.getStatus());

        PlantAttendanceResp plantAttendenceResp = new PlantAttendanceResp();

        long projId = plantAttendenceGetReq.getProjId();
        long crewId = plantAttendenceGetReq.getCrewId();
        String type = AttendanceTypes.PLANT.getName();
        Map<String, Boolean> enableAttendanceDaysMap = empAttendanceService.enableAttendanceDays(projId, crewId, type);

        List<PlantRegProjEntity> empProjDates = plantRegProjRepository
                .getEmpProjMobilizationDates(plantAttendenceGetReq.getAttendenceId());

        Map<String, Boolean> plantDemobilizationDateMap = getMobDates(empProjDates,
                plantAttendenceGetReq.getAttendenceMonth());

        Map<String, Boolean> daysMap = new HashMap<>();
        for (String day : TimeManagementUtil.getDaysByAttendanceMonth(plantAttendenceGetReq.getAttendenceMonth())) {
            daysMap.put(day, true);
        }

        Map<String, Boolean> existingDaysMap = new HashMap<>();
        for (PlantAttendanceEntity plantAttendanceEntity : plantAttendanceEntities) {
            PlantAttendanceTO attendenceTO = PlantAttendanceHandler.convertEntityToPOJO(plantAttendanceEntity);
            plantAttendenceResp.getPlantAttendenceTOs().add(attendenceTO);
            for (PlantAttendanceDtlEntity plantAttendanceDtlEntity : plantAttendanceEntity
                    .getPlantAttendanceDtlEntities()) {
                PlantAttendanceDtlTO attendenceDtlTO = PlantAttendanceHandler
                        .convertDtlEntityToPOJO(plantAttendanceDtlEntity);
                attendenceDtlTO.setAttendenceDay(
                        TimeManagementUtil.getDDMMMMYYYYFormat(plantAttendanceDtlEntity.getAttendanceDate()));
                if (!plantDemobilizationDateMap.containsKey(plantAttendanceEntity.getPlantRegisterDtlEntity().getId()
                        + "#" + attendenceDtlTO.getAttendenceDay())) {
                    attendenceDtlTO.setAttendenceFlag(false);
                } else {
                    attendenceDtlTO
                            .setAttendenceFlag(enableAttendanceDaysMap.containsKey(attendenceDtlTO.getAttendenceDay()));
                }
                existingDaysMap.put(plantAttendanceEntity.getPlantRegisterDtlEntity().getId() + "#"
                        + attendenceDtlTO.getAttendenceDay(), true);
                attendenceTO.getPlantAttendenceDtlMap().put(attendenceDtlTO.getAttendenceDay(), attendenceDtlTO);
            }
        }

        List<PlantAttendanceEntity> otherCrewAttendanceEntities = plantAttendaceRepository
                .findOtherCrewAttendanceRecords(plantAttendenceGetReq.getProjId(), plantAttendenceGetReq.getCrewId(),
                        plantAttendenceGetReq.getStatus());

        Map<String, Boolean> otherCrewExistingDaysMap = new HashMap<>();

        for (PlantAttendanceEntity plantAttendanceEntity : otherCrewAttendanceEntities) {
            for (PlantAttendanceDtlEntity plantAttendanceDtlEntity : plantAttendanceEntity
                    .getPlantAttendanceDtlEntities()) {
                PlantAttendanceDtlTO otherCrewAttendenceDtlTO = PlantAttendanceHandler
                        .convertDtlEntityToPOJO(plantAttendanceDtlEntity);
                otherCrewAttendenceDtlTO.setAttendenceDay(
                        TimeManagementUtil.getDDMMMMYYYYFormat(plantAttendanceDtlEntity.getAttendanceDate()));
                if (CommonUtil.isNotBlankStr(plantAttendanceDtlEntity.getProjAttdCode())) {
                    otherCrewExistingDaysMap.put(plantAttendanceEntity.getPlantRegisterDtlEntity().getId() + "#"
                            + otherCrewAttendenceDtlTO.getAttendenceDay(), true);
                }
            }
        }

        for (PlantAttendanceTO plantAttendanceTO : plantAttendenceResp.getPlantAttendenceTOs()) {
            for (Entry<String, Boolean> dayEntry : daysMap.entrySet()) {
                if (existingDaysMap.get(plantAttendanceTO.getPlantId() + "#" + dayEntry.getKey()) == null) {
                    PlantAttendanceDtlTO plantAttendanceDtlTO = new PlantAttendanceDtlTO();
                    plantAttendanceDtlTO.setPlantDtlId(plantAttendanceTO.getId());
                    if (!plantDemobilizationDateMap
                            .containsKey(plantAttendanceTO.getPlantId() + "#" + dayEntry.getKey())) {
                        // set as false, only when date is not in between mob and demob dates
                        plantAttendanceDtlTO.setAttendenceFlag(false);
                    } else if (otherCrewExistingDaysMap
                            .get(plantAttendanceTO.getPlantId() + "#" + dayEntry.getKey()) != null
                            || enableAttendanceDaysMap.get(dayEntry.getKey()) == null) {
                        // If user already has attendance in other crew, set this as false. So that user can not enter attendance again here.
                        plantAttendanceDtlTO.setAttendenceFlag(false);
                    } else if (enableAttendanceDaysMap.get(dayEntry.getKey()) != null) {
                        // From project settings, if user can enter attendance, then set this as true
                        plantAttendanceDtlTO.setAttendenceFlag(true);
                    }
                    plantAttendanceDtlTO.setAttendanceDate(CommonUtil.convertStringToDate(dayEntry.getKey()));
                    plantAttendanceDtlTO.setAttendenceDay(dayEntry.getKey());
                    plantAttendanceTO.getPlantAttendenceDtlMap().put(dayEntry.getKey(), plantAttendanceDtlTO);
                } else if (otherCrewExistingDaysMap
                        .get(plantAttendanceTO.getPlantId() + "#" + dayEntry.getKey()) != null) {
                    PlantAttendanceDtlTO plantAttendanceDtlTO = plantAttendanceTO.getPlantAttendenceDtlMap()
                            .get(dayEntry.getKey());
                    // If user already has attendance in other crew, set this as false
                    plantAttendanceDtlTO.setPlantDtlId(plantAttendanceTO.getId());
                    plantAttendanceDtlTO.setAttendenceFlag(false);
                    plantAttendanceDtlTO.setAttendanceDate(CommonUtil.convertStringToDate(dayEntry.getKey()));
                    plantAttendanceDtlTO.setAttendenceDay(dayEntry.getKey());
                    plantAttendanceTO.getPlantAttendenceDtlMap().put(dayEntry.getKey(), plantAttendanceDtlTO);
                }
            }

        }
        return plantAttendenceResp;

    }

    public LabelKeyTO savePlantAttendanceRecords(PlantAttendanceSaveReq plantAttendanceSaveReq) {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        PlantAttendanceMstrEntity plantAttendanceMstrEntity = new PlantAttendanceMstrEntity();
        PlantAttendanceEntity plantAttendanceEntity = null;
        if (CommonUtil.isBlankLong(plantAttendanceSaveReq.getAttendenceId())) {
            plantAttendanceMstrEntity = createAttendanceSheet(plantAttendanceSaveReq);
            labelKeyTO.setId(plantAttendanceMstrEntity.getId());
        } else {
            plantAttendanceMstrEntity.setId(plantAttendanceSaveReq.getAttendenceId());
        }
        List<PlantAttendanceEntity> plantAttendanceEntities = new ArrayList<>();
        for (PlantAttendanceTO plantAttendanceTO : plantAttendanceSaveReq.getPlantAttendenceTOs()) {
            plantAttendanceEntity = PlantAttendanceHandler.convertPOJOToEntity(plantAttendanceTO, projCrewRepository,
                    plantRegisterRepository);
            plantAttendanceEntity.setPlantAttendanceMstrEntity(plantAttendanceMstrEntity);
            for (Entry<String, PlantAttendanceDtlTO> entry : plantAttendanceTO.getPlantAttendenceDtlMap().entrySet()) {
                if (entry.getValue().isAttendenceFlag()) {
                    PlantAttendanceDtlTO value = entry.getValue();
                    PlantAttendanceDtlEntity plantAttendenceDtlEntity = null;
                    if (CommonUtil.isNonBlankLong(value.getId())) {
                        plantAttendenceDtlEntity = plantAttendanceDtlRepository.findOne(value.getId());
                    }
                    if (CommonUtil.objectNullCheck(plantAttendenceDtlEntity)) {
                        plantAttendenceDtlEntity = new PlantAttendanceDtlEntity();
                    }
                    PlantAttendanceHandler.convertDtlPOJOToEntity(plantAttendenceDtlEntity, entry.getValue());
                    plantAttendenceDtlEntity.setPlantAttendanceEntity(plantAttendanceEntity);
                    plantAttendanceEntity.getPlantAttendanceDtlEntities().add(plantAttendenceDtlEntity);
                }
            }
            plantAttendanceEntities.add(plantAttendanceEntity);
        }
        plantAttendaceRepository.save(plantAttendanceEntities);
        return labelKeyTO;
    }

    private PlantAttendanceMstrEntity createAttendanceSheet(PlantAttendanceSaveReq plantAttendenceSaveReq) {
        /*List<LabelKeyTO> attendanceCode = plantAttendanceProcRepository.generatePlanAttendanceSheet(
                AppUserUtils.getClientId(), plantAttendenceSaveReq.getProjId(), plantAttendenceSaveReq.getCrewId(),
                plantAttendenceSaveReq.getAttendenceMonth());*/
        PlantAttendanceMstrEntity plantAttendanceMstrEntity = new PlantAttendanceMstrEntity();
        /*if (CommonUtil.isListHasData(attendanceCode)) {
            plantAttendanceMstrEntity.setCode(attendanceCode.get(0).getCode());
        }*/
        Date convertedDate = null;
        convertedDate = CommonUtil.getMMMYYYDate(plantAttendenceSaveReq.getAttendenceMonth());
        plantAttendanceMstrEntity.setAttendanceMonth(convertedDate);
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(plantAttendenceSaveReq.getProjId());
        plantAttendanceMstrEntity.setProjMstrEntity(projMstrEntity);

        ProjCrewMstrEntity projCrewMstrEntity = projCrewRepository.findOne(plantAttendenceSaveReq.getCrewId());
        plantAttendanceMstrEntity.setProjCrewMstrEntity(projCrewMstrEntity);

        plantAttendanceMstrEntity.setStatus(StatusCodes.ACTIVE.getValue());

        plantAttendanceMstrRepository.save(plantAttendanceMstrEntity);
        
        if (CommonUtil.isBlankStr(plantAttendanceMstrEntity.getCode())) {
            String code = "PAR-" + plantAttendanceMstrEntity.getProjMstrEntity().getCode() + "-"
                    + plantAttendanceMstrEntity.getProjCrewMstrEntity().getCode() + "-" + plantAttendanceMstrEntity.getId();
            plantAttendanceMstrEntity.setCode(code);
        }

        return plantAttendanceMstrEntity;
    }

    public PlantAttendanceResp addPlantToAttendanceRecord(PlantAttendanceSaveReq plantAttendanceSaveReq,
            PlantAttendanceGetReq plantAttendanceGetReq) {

        PlantAttendanceResp plantAttendenceResp = new PlantAttendanceResp();

        long projId = plantAttendanceSaveReq.getProjId();
        long crewId = plantAttendanceSaveReq.getCrewId();
        String type = AttendanceTypes.PLANT.getName();
        Map<String, Boolean> enableAttendanceDaysMap = empAttendanceService.enableAttendanceDays(projId, crewId, type);

        List<String> totalDays = TimeManagementUtil
                .getDaysByAttendanceMonth(plantAttendanceGetReq.getAttendenceMonth());
        Map<String, Boolean> daysMap = new HashMap<>();
        for (String day : totalDays) {
            daysMap.put(day, true);
        }

        Map<String, Boolean> existingDaysMap = new HashMap<>();
        List<Long> plantIds = new ArrayList<>();
        for (PlantAttendanceTO attendanceTO : plantAttendanceSaveReq.getPlantAttendenceTOs()) {
            plantIds.add(attendanceTO.getPlantId());
            plantAttendenceResp.getPlantAttendenceTOs().add(attendanceTO);
        }

        List<PlantAttendanceEntity> otherCrewAttendanceEntities = plantAttendaceRepository
                .findOtherCrewAttendanceRecords(plantAttendanceGetReq.getProjId(), plantAttendanceGetReq.getCrewId(),
                        plantAttendanceGetReq.getStatus());

        Map<String, Boolean> otherCrewExistingDaysMap = new HashMap<>();
        PlantAttendanceDtlTO otherCrewAttendenceDtlTO = null;

        for (PlantAttendanceEntity plantAttendanceEntity : otherCrewAttendanceEntities) {
            for (PlantAttendanceDtlEntity attendanceDtlEntity : plantAttendanceEntity.getPlantAttendanceDtlEntities()) {
                otherCrewAttendenceDtlTO = PlantAttendanceHandler.convertDtlEntityToPOJO(attendanceDtlEntity);
                otherCrewAttendenceDtlTO.setAttendenceDay(
                        TimeManagementUtil.getDDMMMMYYYYFormat(attendanceDtlEntity.getAttendanceDate()));
                if (CommonUtil.isNotBlankStr(attendanceDtlEntity.getProjAttdCode())) {
                    otherCrewExistingDaysMap.put(plantAttendanceEntity.getPlantRegisterDtlEntity().getId() + "#"
                            + otherCrewAttendenceDtlTO.getAttendenceDay(), true);
                }
            }
        }

        List<PlantRegProjEntity> empProjDates = plantRegProjRepository.findByPlantId(plantIds);
        Map<String, Boolean> plantDemobilizationDateMap = getMobDates(empProjDates,
                plantAttendanceGetReq.getAttendenceMonth());

        for (PlantAttendanceTO plantAttendanceTO : plantAttendenceResp.getPlantAttendenceTOs()) {
            for (Entry<String, Boolean> dayEntry : daysMap.entrySet()) {
                if (existingDaysMap.get(plantAttendanceTO.getPlantId() + "#" + dayEntry.getKey()) == null) {
                    PlantAttendanceDtlTO plantAttendanceDtlTO = new PlantAttendanceDtlTO();
                    plantAttendanceDtlTO.setPlantDtlId(plantAttendanceTO.getId());
                    if (!plantDemobilizationDateMap
                            .containsKey(plantAttendanceTO.getPlantId() + "#" + dayEntry.getKey())) {
                        // set as false, only when date is not in between mob and demob dates
                        plantAttendanceDtlTO.setAttendenceFlag(false);
                    } else if (otherCrewExistingDaysMap
                            .get(plantAttendanceTO.getPlantId() + "#" + dayEntry.getKey()) != null
                            || enableAttendanceDaysMap.get(dayEntry.getKey()) == null) {
                        // If user already has attendance in other crew, set this as false. So that user can not enter attendance again here.
                        plantAttendanceDtlTO.setAttendenceFlag(false);
                    } else if (enableAttendanceDaysMap.get(dayEntry.getKey()) != null) {
                        // From project settings, if user can enter attendance, then set this as true
                        plantAttendanceDtlTO.setAttendenceFlag(true);
                    }
                    plantAttendanceDtlTO.setAttendanceDate(CommonUtil.convertStringToDate(dayEntry.getKey()));
                    plantAttendanceDtlTO.setAttendenceDay(dayEntry.getKey());
                    plantAttendanceTO.getPlantAttendenceDtlMap().put(dayEntry.getKey(), plantAttendanceDtlTO);
                } else if (otherCrewExistingDaysMap
                        .get(plantAttendanceTO.getPlantId() + "#" + dayEntry.getKey()) != null) {
                    PlantAttendanceDtlTO plantAttendanceDtlTO = plantAttendanceTO.getPlantAttendenceDtlMap()
                            .get(dayEntry.getKey());
                    // If user already has attendance in other crew, set this as false
                    plantAttendanceDtlTO.setPlantDtlId(plantAttendanceTO.getId());
                    plantAttendanceDtlTO.setAttendenceFlag(false);
                    plantAttendanceDtlTO.setAttendanceDate(CommonUtil.convertStringToDate(dayEntry.getKey()));
                    plantAttendanceDtlTO.setAttendenceDay(dayEntry.getKey());
                    plantAttendanceTO.getPlantAttendenceDtlMap().put(dayEntry.getKey(), plantAttendanceDtlTO);
                }
            }

        }
        return plantAttendenceResp;
    }

    private Map<String, Boolean> getMobDates(List<PlantRegProjEntity> empProjDates, String attdMonth) {
        Map<String, Boolean> empDemobilizationDateMap = new HashMap<>();
        for (PlantRegProjEntity empRegCopy : empProjDates) {
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

            startDate = empRegCopy.getMobDate().before(startDate) ? startDate : empRegCopy.getMobDate();
            endDate = (empRegCopy.getDeMobDate() != null && empRegCopy.getDeMobDate().before(endDate))
                    ? empRegCopy.getDeMobDate()
                    : endDate;
            while (startDate.getTime() <= endDate.getTime()) {
                empDemobilizationDateMap.put(empRegCopy.getPlantRegisterDtlEntity().getId() + "#"
                        + CommonUtil.convertDateToString(startDate), true);
                c = Calendar.getInstance();
                c.setTime(startDate);
                c.add(Calendar.DATE, 1);
                startDate = c.getTime();
            }
        }
        return empDemobilizationDateMap;
    }

    @Override
    public List<LabelKeyTO> getDailyPlantAttendanceReport(DailyAttendanceGetReq dailyAttendanceGetReq) {
        String attendenceMonth = dailyAttendanceGetReq.getMonth() + "-" + dailyAttendanceGetReq.getYear();
        List<PlantAttendanceEntity> plantAttendanceEntities = plantAttendaceRepository.findAttendanceRecords(
                dailyAttendanceGetReq.getProjIds(), dailyAttendanceGetReq.getCrewIds(), attendenceMonth);
        return processEmpAttResponseForReport(plantAttendanceEntities);
    }

    @Override
    public List<LabelKeyTO> getDailyPlantAttendanceReportBtwnDates(DailyAttendanceGetReq dailyAttendanceGetReq) {
        Date fromDate = CommonUtil.getMMMYYYDate(dailyAttendanceGetReq.getFromDate());
        Date toDate = CommonUtil.getMMMYYYDate(dailyAttendanceGetReq.getToDate());
        List<PlantAttendanceEntity> plantAttendanceEntities = plantAttendaceRepository.findAttendanceRecordsBtwnDates(
                dailyAttendanceGetReq.getProjIds(), dailyAttendanceGetReq.getCrewIds(), fromDate, toDate);
        return processPlantAttResponseByMonth(plantAttendanceEntities);
    }

    private List<LabelKeyTO> processPlantAttResponseByMonth(List<PlantAttendanceEntity> plantAttendanceEntities) {
        List<LabelKeyTO> labelKeyTOs = new ArrayList<>();
        for (PlantAttendanceEntity plantAttendanceEntity : plantAttendanceEntities) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            Map<String, String> namesMap = labelKeyTO.getDisplayNamesMap();
            PlantAttendanceMstrEntity mstrEntity = plantAttendanceEntity.getPlantAttendanceMstrEntity();
            if (mstrEntity != null) {
                ProjMstrEntity projMstr = mstrEntity.getProjMstrEntity();
                if (projMstr != null) {
                    namesMap.put(CommonConstants.PROJ_ID, String.valueOf(projMstr.getProjectId()));
                    namesMap.put("projName", projMstr.getProjName());
                    ProjMstrEntity projParent = projMstr.getParentProjectMstrEntity();
                    if (projParent != null) {
                        namesMap.put("epsId", String.valueOf(projParent.getProjectId()));
                        namesMap.put("epsName", projParent.getProjName());
                    }
                    PlantRegisterDtlEntity plantDtl = plantAttendanceEntity.getPlantRegisterDtlEntity();
                    if (plantDtl != null) {
                        namesMap.put("plantId", String.valueOf(plantDtl.getId()));
                        namesMap.put("plantDesc", plantDtl.getDesc());
                        namesMap.put("plantCode", plantDtl.getAssertId());
                        namesMap.put("plantMake", plantDtl.getManfacture());
                        namesMap.put("plantModel", plantDtl.getModel());
                        ProjPlantClassMstrEntity plantClassMstrEntityCopy = projPlantClassRepository
                                .getUserProjPlantClasses(projMstr.getProjectId(),
                                        plantDtl.getPlantClassMstrId().getId(), StatusCodes.ACTIVE.getValue());
                        if (CommonUtil.objectNotNull(plantClassMstrEntityCopy)) {
                            namesMap.put("plantClassId", String.valueOf(plantClassMstrEntityCopy.getId()));
                            namesMap.put("plantContrName", plantClassMstrEntityCopy.getPlantContrName());
                        }
                        CompanyMstrEntity company = plantDtl.getCmpId();
                        if (company != null) {
                            namesMap.put("companyId", String.valueOf(company.getId()));
                            namesMap.put("companyName", company.getName());
                        }
                    }
                }
                ProjCrewMstrEntity crew = mstrEntity.getProjCrewMstrEntity();
                if (crew != null) {
                    namesMap.put("crewId", String.valueOf(crew.getId()));
                    namesMap.put("crewName", crew.getDesc());
                }
            }
            Map<String, Map<String, String>> attdByMonth = new HashMap<>();
            for (PlantAttendanceDtlEntity dtlEntity : plantAttendanceEntity.getPlantAttendanceDtlEntities()) {
                if (CommonUtil.isNotBlankStr(dtlEntity.getProjAttdCode())) {
                    String key = String.valueOf(plantAttendanceEntity.getId())
                            + CommonUtil.getMMMYYYFormat(dtlEntity.getAttendanceDate());
                    Map<String, String> attdCodeCount = attdByMonth.get(key);
                    if (attdCodeCount == null) {
                        attdCodeCount = new HashMap<>();
                        attdCodeCount.put("month", CommonUtil.getMMMYYYFormat(dtlEntity.getAttendanceDate()));
                    }
                    if (attdCodeCount.get(dtlEntity.getProjAttdCode()) == null) {
                        attdCodeCount.put(dtlEntity.getProjAttdCode(), "1");
                    } else {
                        String count = attdCodeCount.get(dtlEntity.getProjAttdCode());
                        attdCodeCount.put(dtlEntity.getProjAttdCode(), String.valueOf(Long.valueOf(count) + 1L));
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

    private List<LabelKeyTO> processEmpAttResponseForReport(List<PlantAttendanceEntity> plantAttendanceEntities) {
        List<LabelKeyTO> labelKeyTOs = new ArrayList<>();
        for (PlantAttendanceEntity attendanceEntity : plantAttendanceEntities) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            Map<String, String> namesMap = labelKeyTO.getDisplayNamesMap();

            int total = 0;
            for (PlantAttendanceDtlEntity attdDtl : attendanceEntity.getPlantAttendanceDtlEntities()) {
                if (CommonUtil.isNotBlankStr(attdDtl.getProjAttdCode())) {
                    if (attdDtl.getProjAttdCode().equalsIgnoreCase("W")) {
                        total++;
                    }
                    namesMap.put(TimeManagementUtil.getDDMMMMYYYYFormat(attdDtl.getAttendanceDate()),
                            attdDtl.getProjAttdCode());
                }
            }
            namesMap.put("totalWorking", String.valueOf(total));

            PlantAttendanceMstrEntity plantAttdMstr = attendanceEntity.getPlantAttendanceMstrEntity();
            if (plantAttdMstr != null) {
                ProjMstrEntity projMstr = plantAttdMstr.getProjMstrEntity();
                if (projMstr != null) {
                    namesMap.put(CommonConstants.PROJ_ID, String.valueOf(projMstr.getProjectId()));
                    namesMap.put("projName", projMstr.getProjName());
                    ProjMstrEntity projParent = projMstr.getParentProjectMstrEntity();
                    if (projParent != null) {
                        namesMap.put("epsId", String.valueOf(projParent.getProjectId()));
                        namesMap.put("epsName", projParent.getProjName());
                    }
                    PlantRegisterDtlEntity plantDtl = attendanceEntity.getPlantRegisterDtlEntity();
                    if (plantDtl != null) {
                        namesMap.put("plantId", String.valueOf(plantDtl.getId()));
                        namesMap.put("plantDesc", plantDtl.getDesc());
                        namesMap.put("plantCode", plantDtl.getAssertId());
                        namesMap.put("plantMake", plantDtl.getManfacture());
                        namesMap.put("plantModel", plantDtl.getModel());
                        CompanyMstrEntity company = plantDtl.getCmpId();
                        if (company != null) {
                            namesMap.put("companyId", String.valueOf(company.getId()));
                            namesMap.put("companyName", company.getName());
                        }
                    }
                }
                ProjCrewMstrEntity crew = plantAttdMstr.getProjCrewMstrEntity();
                if (crew != null) {
                    namesMap.put("crewId", String.valueOf(crew.getId()));
                    namesMap.put("crewName", crew.getDesc());
                }
            }

            labelKeyTOs.add(labelKeyTO);
        }
        return labelKeyTOs;
    }

    @Override
    public List<LabelKeyTO> getPlantDailyResourceStatus(DailyAttendanceGetReq dailyAttendanceGetReq) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			List<ResourceAssignmentDataValueEntity> resourceAssignmentDataValueEntities = resourceAssignmentDataValueRepository.findAllBaselines(dailyAttendanceGetReq.getProjIds(), CommonUtil.convertStringToDate(dailyAttendanceGetReq.getDate()), CommonUtil.convertStringToDate(dailyAttendanceGetReq.getDate()));
			List<ResourceAssignmentDataEntity> resourceAssignmentDataValueEntityes = resourceAssignmentDataRepository.findAllBaselines(dailyAttendanceGetReq.getProjIds());
			List<PlantAttendanceEntity> plantAttendanceEntities = plantAttendaceRepository.findDailyResourceStatus(dailyAttendanceGetReq.getProjIds(), dailyAttendanceGetReq.getDate());
        Map<String, LabelKeyTO> labelMap = new HashMap<>();
        if(resourceAssignmentDataValueEntities.size() == 0) {
        	
        	for (ResourceAssignmentDataEntity resourceAssignmentDataValueEntity : resourceAssignmentDataValueEntityes) {
        		if (!resourceAssignmentDataValueEntity.getReferenceType().equals("POT_PLANT")) continue;
        		
        		LabelKeyTO labelKeyTO = new LabelKeyTO();
        		labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJ_ID, String.valueOf(resourceAssignmentDataValueEntity.getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId()));
        		labelKeyTO.getDisplayNamesMap().put("projName", resourceAssignmentDataValueEntity.getScheduleActivityDataSetEntity().getProjMstrEntity().getProjName());
        		labelKeyTO.getDisplayNamesMap().put("epsId", String.valueOf(resourceAssignmentDataValueEntity.getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjectId()));
        		labelKeyTO.getDisplayNamesMap().put("epsName", resourceAssignmentDataValueEntity.getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjName());
        		List<ProjectPlantsDtlEntity> projectPlantsDtlEntity = projectPlantsRepository.findOneId(resourceAssignmentDataValueEntity.getReferenceId());
        		for(ProjectPlantsDtlEntity projectPlantsDtlEntiti: projectPlantsDtlEntity) {
        		labelKeyTO.getDisplayNamesMap().put("plantClassId", String.valueOf(projectPlantsDtlEntiti.getPlantMstrEntity().getId()));
        		labelKeyTO.getDisplayNamesMap().put("plantResourceName", String.valueOf(projectPlantsDtlEntiti.getPlantMstrEntity().getName()));
                
        		ProjPlantClassMstrEntity plantClassMstrEntityCopy = projPlantClassRepository.getUserProjPlantClasses(resourceAssignmentDataValueEntity.getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId(), projectPlantsDtlEntiti.getPlantMstrEntity().getId(), StatusCodes.ACTIVE.getValue());
        		labelKeyTO.getDisplayNamesMap().put("projPlantClassId", String.valueOf(plantClassMstrEntityCopy.getId()));
        		labelKeyTO.getDisplayNamesMap().put("tradeName", plantClassMstrEntityCopy.getPlantContrName());

        		int total = 0;
        		for (PlantAttendanceEntity plantAttendanceEntity : plantAttendanceEntities) {
        			if (plantAttendanceEntity.getPlantRegisterDtlEntity().getPlantClassMstrId().getId().equals(projectPlantsDtlEntiti.getPlantMstrEntity().getId())) {
        				for (PlantAttendanceDtlEntity plantAttdDtl : plantAttendanceEntity.getPlantAttendanceDtlEntities()) {
        	                if (CommonUtil.isNotBlankStr(plantAttdDtl.getProjAttdCode())
        	                        && plantAttdDtl.getProjAttdCode().equalsIgnoreCase("W")) {
        	                    total++;
        	                }
        	            }
        			}
                }
        		labelKeyTO.getDisplayNamesMap().put("totalWorking", String.valueOf(total));
        		labelKeyTO.getDisplayNamesMap().put("plannedValue", String.valueOf((0)));
        		labelKeyTO.getDisplayNamesMap().put("variance", String.valueOf((0)));
        		
        		labelMap.put(String.valueOf(resourceAssignmentDataValueEntity.getId()), labelKeyTO);
        		}
        	}
 
        }
    	for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataValueEntities) {
    		if (!resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceType().equals("POT_PLANT")) continue;
    		LabelKeyTO labelKeyTO = new LabelKeyTO();
    		labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJ_ID, String.valueOf(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId()));
    		labelKeyTO.getDisplayNamesMap().put("projName", resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjName());
    		labelKeyTO.getDisplayNamesMap().put("epsId", String.valueOf(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjectId()));
    		labelKeyTO.getDisplayNamesMap().put("epsName", resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getParentProjectMstrEntity().getProjName());
    		List<ProjectPlantsDtlEntity> projectPlantsDtlEntity = projectPlantsRepository.findOneId(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getReferenceId());
    		for(ProjectPlantsDtlEntity projectPlantsDtlEntiti: projectPlantsDtlEntity) {
    		labelKeyTO.getDisplayNamesMap().put("plantClassId", String.valueOf(projectPlantsDtlEntiti.getPlantMstrEntity().getId()));
    		labelKeyTO.getDisplayNamesMap().put("plantResourceName", String.valueOf(projectPlantsDtlEntiti.getPlantMstrEntity().getName()));
            
    		ProjPlantClassMstrEntity plantClassMstrEntityCopy = projPlantClassRepository.getUserProjPlantClasses(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId(), projectPlantsDtlEntiti.getPlantMstrEntity().getId(), StatusCodes.ACTIVE.getValue());
    		labelKeyTO.getDisplayNamesMap().put("projPlantClassId", String.valueOf(plantClassMstrEntityCopy.getId()));
    		labelKeyTO.getDisplayNamesMap().put("tradeName", plantClassMstrEntityCopy.getPlantContrName());

    		int total = 0;
    		for (PlantAttendanceEntity plantAttendanceEntity : plantAttendanceEntities) {
    			if (plantAttendanceEntity.getPlantRegisterDtlEntity().getPlantClassMstrId().getId().equals(projectPlantsDtlEntiti.getPlantMstrEntity().getId())) {
    				for (PlantAttendanceDtlEntity plantAttdDtl : plantAttendanceEntity.getPlantAttendanceDtlEntities()) {
    	                if (CommonUtil.isNotBlankStr(plantAttdDtl.getProjAttdCode())
    	                        && plantAttdDtl.getProjAttdCode().equalsIgnoreCase("W")) {
    	                    total++;
    	                }
    	            }
    			}
            }
    		ProjGeneralMstrEntity projGeneralMstrEntity = projGeneralMstrRepository.findProjGenerals(resourceAssignmentDataValueEntity.getResourceAssignmentDataEntity().getScheduleActivityDataSetEntity().getProjMstrEntity().getProjectId(), 1);
    		labelKeyTO.getDisplayNamesMap().put("totalWorking", String.valueOf(total));
    		labelKeyTO.getDisplayNamesMap().put("plannedValue", String.valueOf((resourceAssignmentDataValueEntity.getBudgetUnits() / projGeneralMstrEntity.getDefualtHrs())));
    		labelKeyTO.getDisplayNamesMap().put("variance", String.valueOf((resourceAssignmentDataValueEntity.getBudgetUnits() / projGeneralMstrEntity.getDefualtHrs()) - total));
    		
    		labelMap.put(String.valueOf(resourceAssignmentDataValueEntity.getId()), labelKeyTO);
    		}
    	}
    	
    	return new ArrayList<LabelKeyTO>(labelMap.values());
    }
    
    public PlantAttendanceSheetResp getPlantAttendanceRecordSheets(EmployeeAttendanceRecordSheetsSearchReq employeeAttendanceRecordSheetsSearchReq) {
    	List<PlantAttendanceMstrEntity> plantAttendanceMstrEntityList = null;
    	List<Date> fromDates = TimeManagementUtil.getDatesByAttendanceMonth(employeeAttendanceRecordSheetsSearchReq.getFromDate());
    	List<Date> toDates = TimeManagementUtil.getDatesByAttendanceMonth(employeeAttendanceRecordSheetsSearchReq.getToDate());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjId() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		plantAttendanceMstrEntityList = plantAttendanceMstrRepository.findAll(AppUserUtils.getUserId(), fromDates.get(0), toDates.get(1),AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjId() == null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		plantAttendanceMstrEntityList = plantAttendanceMstrRepository.findAll(fromDates.get(0), toDates.get(1),AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("1"))
    		plantAttendanceMstrEntityList = plantAttendanceMstrRepository.findAll(AppUserUtils.getUserId(), fromDates.get(0), toDates.get(1), employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());
    	if (employeeAttendanceRecordSheetsSearchReq.getProjIds() != null && employeeAttendanceRecordSheetsSearchReq.getUserType().equals("2"))
    		plantAttendanceMstrEntityList = plantAttendanceMstrRepository.findAll(fromDates.get(0), toDates.get(1), employeeAttendanceRecordSheetsSearchReq.getProjIds(),AppUserUtils.getClientId());
    		
    	List<PlantAttendanceMstrTO> plantAttendanceMstrTOList = new ArrayList<PlantAttendanceMstrTO>();
    	for (PlantAttendanceMstrEntity plantAttendanceMstrEntity : plantAttendanceMstrEntityList)
    		plantAttendanceMstrTOList.add(PlantAttendanceHandler.convertMstrEntityToPOJO(plantAttendanceMstrEntity)); 
    	PlantAttendanceSheetResp plantAttendanceSheetResp = new PlantAttendanceSheetResp();
    	plantAttendanceSheetResp.setPlantAttendanceMstrTOs(plantAttendanceMstrTOList);
    	return plantAttendanceSheetResp;
    }

}
