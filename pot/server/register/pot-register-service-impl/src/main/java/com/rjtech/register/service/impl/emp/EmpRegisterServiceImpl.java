package com.rjtech.register.service.impl.emp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.EmployeeServiceStatus;
import com.rjtech.common.utils.EmployeeType;
import com.rjtech.common.utils.Gender;
import com.rjtech.common.utils.Locality;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjEmpClassMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.register.emp.dto.EmpRegisterDropDownTO;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.model.EmpContactDtlEntity;
import com.rjtech.register.emp.model.EmpEnrollmentDtlEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.emp.req.EmpRegDeactivateReq;
import com.rjtech.register.emp.req.EmpRegisterGetReq;
import com.rjtech.register.emp.req.EmpRegisterSaveReq;
import com.rjtech.register.emp.req.ManPowerGenderStatisticsReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpRegResp;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.register.emp.resp.EmpUniqueCodeMapRep;
import com.rjtech.register.emp.resp.ProjEmpRegLabelKeyTOMapResp;
import com.rjtech.register.emp.resp.ProjEmpRegLabelKeyTOResp;
import com.rjtech.register.manpower.reports.dto.ManPowerGenderStatistics;
import com.rjtech.register.manpower.reports.dto.ManPowerGenderStatisticsEmpDetails;
import com.rjtech.register.manpower.reports.dto.ManPowerMobilizationStatistics;
import com.rjtech.register.plant.req.PlantRegisterGetReq;
import com.rjtech.register.repository.emp.EmpContactDtlRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpRegisterService;
import com.rjtech.register.service.handler.emp.EmpEnrollmentDtlHandler;
import com.rjtech.register.service.handler.emp.EmpRegisterDtlHandler;
import com.rjtech.register.service.manpower.reports.handler.ManpowerReportsHandler;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.register.emp.dto.MasterEmployeeDetailsTO;
import com.rjtech.register.emp.req.ManpowerEmployeeDetailsGetReq;
//import com.rjtech.projectlib.model.ProjEmpClassMstrEntityCopy;

@Service(value = "empRegisterService")
@RJSService(modulecode = "empRegisterService")
@Transactional
public class EmpRegisterServiceImpl implements EmpRegisterService {
   
	@Autowired
    private EPSProjService epsProjService;
    
    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private EmpProjRegisterRepository projEmpRegisterRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmpClassRepository empClassRepository;

    @Autowired
    private ProcureCatgRepository procureCatgRepository;

    @Autowired
    private ClientRegRepository clientRegRepository;

    @Autowired
    private EmpContactDtlRepository empContactDtlRepository;

    @Autowired
    private ProjEmpClassRepositoryCopy projEmpClassRepositoryCopy;

    public EmpRegisterResp getEmpRegisters(EmpRegisterGetReq empRegisterGetReq) {
        EmpRegisterResp empRegisterResp = new EmpRegisterResp();
        List<EmpRegisterDtlEntity> empRegisterDtlEntities = null;
		/*
		 * if (CommonUtil.isNonBlankLong(empRegisterGetReq.getProjId())) {
		 * empRegisterDtlEntities =
		 * empRegisterRepository.findLatestEmployeesByProject(empRegisterGetReq.
		 * getProjId(), empRegisterGetReq.getStatus()); } else { empRegisterDtlEntities
		 * = empRegisterRepository.findEmployeesByClient(AppUserUtils.getClientId(),
		 * empRegisterGetReq.getStatus()); }
		 */
         if (CommonUtil.objectNotNull(empRegisterGetReq.getProjIds())) {
       	 List<Long> projIds = null; 
       	 if (CommonUtil.isListHasData(empRegisterGetReq.getProjIds())) {
    			projIds = empRegisterGetReq.getProjIds();
    		} else {
    			projIds = epsProjService.getUserProjIds();
    		}		
			  if(projIds.size() > 0) {
				  empRegisterDtlEntities = empRegisterRepository.findLatestEmployeesByProject(projIds,
		                   empRegisterGetReq.getStatus());
			  }else {
				  
			  }
 
       } else {
           empRegisterDtlEntities = empRegisterRepository.findEmployeesByClient(AppUserUtils.getClientId(),
                   empRegisterGetReq.getStatus());
       }
        if (CommonUtil.isListHasData(empRegisterDtlEntities)) {
            for (EmpRegisterDtlEntity empRegisterDtlEntity : empRegisterDtlEntities) {
                EmpRegisterDtlTO registerDtlTO = EmpRegisterDtlHandler.convertEntityToPOJO(empRegisterDtlEntity);
                if (CommonUtil.isListHasData(empRegisterDtlEntity.getProjEmpRigisterEntities())) {
                    for (EmpProjRigisterEntity projEmpEntity : empRegisterDtlEntity.getProjEmpRigisterEntities()) {
                        if (projEmpEntity.getIsLatest().equals("Y")) {
                            ProjEmpRegisterTO projEmpRegisterTO = EmpEnrollmentDtlHandler
                                    .convertMobilizationDateEntityTO(projEmpEntity);
                            for (EmpEnrollmentDtlEntity empEnrollmentDtlEntity : empRegisterDtlEntity
                                    .getEmpEnrollmentDtlEntities()) {
                                if (empEnrollmentDtlEntity.getIsLatest().equals("Y")) {
                                    projEmpRegisterTO.setEnrollmentDate(
                                            CommonUtil.convertDateToString(empEnrollmentDtlEntity.getEffectiveFrom()));
                                }
                            }
                            registerDtlTO.setProjEmpRegisterTO(projEmpRegisterTO);
                        }
                    }
                }
                empRegisterResp.getEmpRegisterDtlTOs().add(registerDtlTO);
            }
        }

        return empRegisterResp;
    }

    public void saveEmpRegisters(EmpRegisterSaveReq empRegisterSaveReq) {
        List<EmpRegisterDtlEntity> list = new ArrayList<>();
        if (CommonUtil.isListHasData(empRegisterSaveReq.getEmpRegisterTOs())) {
            for (EmpRegisterDtlTO registerDTO : empRegisterSaveReq.getEmpRegisterTOs()) {
                EmpRegisterDtlEntity entity = null;
                if (CommonUtil.isNonBlankLong(registerDTO.getId())) {
                    entity = empRegisterRepository.findOne(registerDTO.getId());
                }
                if (CommonUtil.objectNullCheck(entity)) {
                    entity = new EmpRegisterDtlEntity();
                }
                EmpRegisterDtlHandler.convertPOJOToEntity(entity, registerDTO, epsProjRepository, companyRepository,
                        empClassRepository, procureCatgRepository);
                list.add(entity);
            }
        }
        empRegisterRepository.save(list);
    }

    public void empRegistersDeactivate(EmpRegDeactivateReq empRegDeactivateReq) {
        empRegisterRepository.deactivateEmpRegisters(empRegDeactivateReq.getEmpRegIds(),
                empRegDeactivateReq.getStatus());
    }

    public ProjEmpRegLabelKeyTOResp getNonAttendenceEmpRegisters(ProjEmpRegisterGetReq projEmpRegisterGetReq) {

        ProjEmpRegLabelKeyTOResp projEmpRegLabelKeyTOResp = new ProjEmpRegLabelKeyTOResp();
        List<EmpProjRigisterEntity> projEmpRigisterEntites = projEmpRegisterRepository.findNonAttendenceEmpRegisters(
                projEmpRegisterGetReq.getEmpIds(), projEmpRegisterGetReq.getProjId(),
                projEmpRegisterGetReq.getStatus());

        LabelKeyTO projEmpRegLabelKeyTO = null;
        Map<String, String> displayNamesMap = null;
        for (EmpProjRigisterEntity empProjRigisterEntity : projEmpRigisterEntites) {
            projEmpRegLabelKeyTO = new LabelKeyTO();
            projEmpRegLabelKeyTO.setId(empProjRigisterEntity.getId());
            if (CommonUtil.objectNotNull((empProjRigisterEntity.getEmpRegisterDtlEntity()))) {
                displayNamesMap = new HashMap<String, String>();
                EmpRegisterDtlEntity empRegisterDtlEntity = empProjRigisterEntity.getEmpRegisterDtlEntity();
                displayNamesMap.put(CommonConstants.FIRST_NAME, empRegisterDtlEntity.getFirstName());
                displayNamesMap.put(CommonConstants.LAST_NAME, empRegisterDtlEntity.getLastName());
                displayNamesMap.put(CommonConstants.GENDER, empRegisterDtlEntity.getGender());
                if (CommonUtil.objectNotNull(empRegisterDtlEntity.getCompanyMstrEntity())) {
                    displayNamesMap.put(CommonConstants.DESIGNATION,
                            empRegisterDtlEntity.getCompanyMstrEntity().getId().toString());
                }
                if (CommonUtil.objectNotNull(empRegisterDtlEntity.getProcureCatgDtlEntity())) {
                    displayNamesMap.put(CommonConstants.PROCURE_CATG,
                            empRegisterDtlEntity.getProcureCatgDtlEntity().getId().toString());
                }
                projEmpRegLabelKeyTO.setDisplayNamesMap(displayNamesMap);
                projEmpRegLabelKeyTOResp.getProjEmpReglabelKeyTOs().add(projEmpRegLabelKeyTO);
            }

        }

        return projEmpRegLabelKeyTOResp;
    }

    public List<String> getGender() {
        List<String> genders = new ArrayList<String>();

        for (Gender gender : Gender.values()) {
            genders.add(gender.getName());
        }

        return genders;
    }

    public List<String> getLocality() {
        List<String> localities = new ArrayList<String>();

        for (Locality locality : Locality.values()) {
            localities.add(locality.getName());
        }
        return localities;
    }

    public List<String> getEmployeeType() {
        List<String> empTypes = new ArrayList<String>();
        for (EmployeeType employeeType : EmployeeType.values()) {
            empTypes.add(employeeType.getName());
        }
        return empTypes;
    }

    public List<String> getEmployeeServiceType() {
        List<String> empTypes = new ArrayList<String>();
        for (EmployeeServiceStatus employeeServiceStatus : EmployeeServiceStatus.values()) {
            empTypes.add(employeeServiceStatus.getName());
        }
        return empTypes;
    }

    public ProjEmpRegLabelKeyTOMapResp getAttendenceEmpRegisters(ProjEmpRegisterGetReq projEmpRegisterGetReq) {

        ProjEmpRegLabelKeyTOMapResp projEmpRegLabelKeyTOMapResp = new ProjEmpRegLabelKeyTOMapResp();
        List<EmpProjRigisterEntity> projEmpRigisterEntites = projEmpRegisterRepository.findAttendenceEmpRegisters(
                projEmpRegisterGetReq.getEmpIds(), projEmpRegisterGetReq.getProjId(),
                projEmpRegisterGetReq.getStatus());
        LabelKeyTO projEmpRegLabelKeyTO = null;
        Map<String, String> displayNamesMap = null;
        for (EmpProjRigisterEntity empProjRigisterEntity : projEmpRigisterEntites) {
            projEmpRegLabelKeyTO = new LabelKeyTO();
            projEmpRegLabelKeyTO.setId(empProjRigisterEntity.getId());
            if (CommonUtil.objectNotNull((empProjRigisterEntity.getEmpRegisterDtlEntity()))) {
                displayNamesMap = new HashMap<String, String>();
                EmpRegisterDtlEntity empRegisterDtlEntity = empProjRigisterEntity.getEmpRegisterDtlEntity();
                displayNamesMap.put(CommonConstants.FIRST_NAME, empRegisterDtlEntity.getFirstName());
                displayNamesMap.put(CommonConstants.LAST_NAME, empRegisterDtlEntity.getLastName());
                displayNamesMap.put(CommonConstants.GENDER, empRegisterDtlEntity.getGender());
                if (CommonUtil.objectNotNull(empRegisterDtlEntity.getEmpClassMstrEntity())) {
                    displayNamesMap.put(CommonConstants.DESIGNATION,
                            empRegisterDtlEntity.getEmpClassMstrEntity().getId().toString());
                }
                if (CommonUtil.objectNotNull(empRegisterDtlEntity.getProcureCatgDtlEntity())) {
                    displayNamesMap.put(CommonConstants.PROCURE_CATG,
                            empRegisterDtlEntity.getProcureCatgDtlEntity().getId().toString());
                }
                projEmpRegLabelKeyTO.setDisplayNamesMap(displayNamesMap);
                projEmpRegLabelKeyTOMapResp.getProjEmpRegMap().put(projEmpRegLabelKeyTO.getId(), projEmpRegLabelKeyTO);
            }
        }
        return projEmpRegLabelKeyTOMapResp;
    }

    public EmpRegisterDropDownTO getEmpRegisterDropDown() {
        EmpRegisterDropDownTO dropDownTO = new EmpRegisterDropDownTO();
        dropDownTO.setGender(getGender());
        dropDownTO.setLocality(getLocality());
        dropDownTO.setEmployeeType(getEmployeeType());

        return dropDownTO;
    }

    public EmpRegisterResp getEmpsNotInUserProjects(EmpRegisterGetReq empRegisterGetReq) {
        EmpRegisterResp empRegisterResp = new EmpRegisterResp();
        List<EmpRegisterDtlEntity> empRegisterDtlEntities = empRegisterRepository.findEmpsNotInUserProjects(
                AppUserUtils.getClientId(), empRegisterGetReq.getProjIds(), StatusCodes.ACTIVE.getValue());
        for (EmpRegisterDtlEntity empRegisterDtlEntity : empRegisterDtlEntities) {
            empRegisterResp.getEmpRegisterDtlTOs()
                    .add(EmpRegisterDtlHandler.convertEmpRegEntityToPOJO(empRegisterDtlEntity));
        }
        return empRegisterResp;
    }

    public ProjEmpRegLabelKeyTOMapResp getMultiProjEmpListMap(PlantRegisterGetReq plantRegisterGetReq) {
        ProjEmpRegLabelKeyTOMapResp projEmpRegLabelKeyTOMapResp = new ProjEmpRegLabelKeyTOMapResp();
        List<EmpRegisterDtlEntity> empRegisterDtlEntities = empRegisterRepository
                .findMultiProjEmpDetails(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus());
        if (CommonUtil.isListHasData(empRegisterDtlEntities)) {
            LabelKeyTO projEmpRegLabelKeyTO = null;
            Map<String, String> displayNamesMap = null;
            for (EmpRegisterDtlEntity empRegisterDtlEntity : empRegisterDtlEntities) {
                projEmpRegLabelKeyTO = new LabelKeyTO();
                projEmpRegLabelKeyTO.setId(empRegisterDtlEntity.getId());
                displayNamesMap = new HashMap<String, String>();
                displayNamesMap.put(CommonConstants.DISPLAY_NAME, empRegisterDtlEntity.getCode());
                displayNamesMap.put(CommonConstants.FIRST_NAME, empRegisterDtlEntity.getFirstName());
                displayNamesMap.put(CommonConstants.LAST_NAME, empRegisterDtlEntity.getLastName());
                displayNamesMap.put(CommonConstants.GENDER, empRegisterDtlEntity.getGender());
                if (CommonUtil.objectNotNull(empRegisterDtlEntity.getEmpClassMstrEntity())) {
                    displayNamesMap.put(CommonConstants.DESIGNATION,
                            empRegisterDtlEntity.getEmpClassMstrEntity().getId().toString());
                }
                projEmpRegLabelKeyTO.setDisplayNamesMap(displayNamesMap);
                projEmpRegLabelKeyTOMapResp.getProjEmpRegMap().put(projEmpRegLabelKeyTO.getId(), projEmpRegLabelKeyTO);
            }
        }
        return projEmpRegLabelKeyTOMapResp;
    }

    public EmpRegResp getAllRegEmp(EmpRegisterGetReq empRegisterGetReq) {
        EmpRegResp empRegResp = new EmpRegResp();
        List<EmpRegisterDtlEntity> empRegisterDtlEntities = null;
        empRegisterDtlEntities = empRegisterRepository.findAllRegEmployees(AppUserUtils.getClientId(),
                empRegisterGetReq.getStatus());
        if (CommonUtil.isListHasData(empRegisterDtlEntities)) {
            for (EmpRegisterDtlEntity empRegisterDtlEntity : empRegisterDtlEntities) {
                EmpRegisterDtlTO empRegisterDtlTO = EmpRegisterDtlHandler.convertEntityToPOJO(empRegisterDtlEntity);
                empRegResp.getEmpRegisterDtlTOs().add(empRegisterDtlTO);
            }
        }
        return empRegResp;
    }

    public EmpUniqueCodeMapRep isEmpCodeUnique(EmpRegisterGetReq empRegisterGetReq) {
        Long clientId = AppUserUtils.getClientId();
        ClientRegEntity clientRegEntity = clientRegRepository.findOne(clientId);
        List<EmpRegisterDtlEntity> empRegisterDtlEntities = empRegisterRepository
                .findByCodeAndClientId(empRegisterGetReq.getCode(), clientRegEntity);
        EmpUniqueCodeMapRep resp = new EmpUniqueCodeMapRep();
        resp.setUnique(empRegisterDtlEntities.isEmpty());
        return resp;
    }

    public EmpRegisterResp getEmployees(EmpRegisterGetReq empRegisterGetReq) {
        EmpRegisterResp empRegisterResp = new EmpRegisterResp();
        List<EmpRegisterDtlEntity> empRegisterDtlEntities = null;
        if (CommonUtil.objectNotNull(empRegisterGetReq.getProjIds())) {
            empRegisterDtlEntities = empRegisterRepository.findLatestEmployeesByProject(empRegisterGetReq.getProjIds(),
                    empRegisterGetReq.getStatus());
        } else {
            empRegisterDtlEntities = empRegisterRepository.findEmployeesByClient(AppUserUtils.getClientId(),
                    empRegisterGetReq.getStatus());
        }

        for (EmpRegisterDtlEntity empRegisterDtlEntity : empRegisterDtlEntities) {
            EmpRegisterDtlTO registerDtlTO = EmpRegisterDtlHandler.convertEntityToPOJO(empRegisterDtlEntity);

            if (CommonUtil.isListHasData(empRegisterDtlEntity.getProjEmpRigisterEntities())) {
                EmpProjRigisterEntity projEmpEntity = empRegisterDtlEntity.getProjEmpRigisterEntities().get(0);
                if (projEmpEntity.getIsLatest().equals("Y")) {
                    ProjEmpRegisterTO projEmpRegisterTO = EmpEnrollmentDtlHandler
                            .convertMobilizationDateEntityTO(projEmpEntity);
                    if (CommonUtil.isListHasData(empRegisterDtlEntity.getProjEmpRigisterEntities())) {
                        for (EmpEnrollmentDtlEntity empEnrollmentDtlEntity : empRegisterDtlEntity
                                .getEmpEnrollmentDtlEntities()) {
                            if (empEnrollmentDtlEntity.getIsLatest().equals("Y")) {
                                projEmpRegisterTO.setEnrollmentDate(
                                        CommonUtil.convertDateToString(empEnrollmentDtlEntity.getEffectiveFrom()));
                            }
                        }
                    }
                    registerDtlTO.setProjEmpRegisterTO(projEmpRegisterTO);
                }
            }
            empRegisterResp.getEmpRegisterDtlTOs().add(registerDtlTO);
        }

        return empRegisterResp;
    }

    @Override
    public EmpRegisterResp getProjEmployees(Long projId) {
        EmpRegisterResp empRegisterResp = new EmpRegisterResp();
        List<EmpRegisterDtlEntity> empRegisterDtlEntities = empRegisterRepository.findEmployeesByProject(projId, 1);
        for (EmpRegisterDtlEntity empRegisterDtlEntity : empRegisterDtlEntities) {
            EmpRegisterDtlTO empRegisterDtlTO = EmpRegisterDtlHandler.convertEntityToPOJO(empRegisterDtlEntity);
            EmpContactDtlEntity contactDtlEntity = empContactDtlRepository.findEmpContacts(empRegisterDtlEntity.getId(),
                    1);
            if (CommonUtil.objectNotNull(contactDtlEntity)) {
                empRegisterDtlTO.setPhoneNumber(contactDtlEntity.getPhoneNumber());
                empRegisterDtlTO.setEmail(contactDtlEntity.getEmail());
            }
            empRegisterResp.getEmpRegisterDtlTOs().add(empRegisterDtlTO);
        }
        return empRegisterResp;
    }

    @Override
    public List<ManPowerGenderStatistics> getManpowerGenderStatisticsReport(
            ManPowerGenderStatisticsReq manPowerGenderStatisticsReq) {

        List<Long> projIds = manPowerGenderStatisticsReq.getProjIds();
        List<Long> companyIds = manPowerGenderStatisticsReq.getCmpIds();
        List<Long> empClassList = null;
        Date reportDate = CommonUtil.convertStringToDate(manPowerGenderStatisticsReq.getDate());
        List<EmpRegisterDtlEntity> emRegList;
        List<ManPowerGenderStatistics> reportData = new ArrayList<>();
        if (manPowerGenderStatisticsReq.getCategory() != null) {
            empClassList = projEmpClassRepositoryCopy.getEmpClassByEmpCategoryName(projIds,
                    manPowerGenderStatisticsReq.getCategory());
            // If No employee class found for given category then return
            if (empClassList.isEmpty()) {
                return new ArrayList<>();
            } else {
                emRegList = empRegisterRepository.manpowerGenderStatisticsReport(projIds, companyIds, reportDate,
                        empClassList);
            }
        } else {
            emRegList = empRegisterRepository.manpowerGenderStatisticsReport(projIds, companyIds, reportDate);
        }
        ManpowerReportsHandler manpowerReportsHandler = new ManpowerReportsHandler();
        for (EmpRegisterDtlEntity empReg : emRegList) {

            ManPowerGenderStatistics manPowerGenderStatistics = new ManPowerGenderStatistics();
            manpowerReportsHandler.setEmpRegDetails(empReg, manPowerGenderStatistics, projEmpClassRepositoryCopy);
            ProjMstrEntity project = empReg.getProjMstrEntity();
            EmpClassMstrEntity empClass = empReg.getEmpClassMstrEntity();
            if (empClass != null && project != null) {
                manPowerGenderStatistics.setEmpCategoryName(projEmpClassRepositoryCopy
                        .getEmpCategoryNameByEmpClassId(empClass.getId(), project.getProjectId()));
            }
            ManPowerGenderStatisticsEmpDetails empDetails = new ManPowerGenderStatisticsEmpDetails();
            empDetails.setUserId(empReg.getId());
            empDetails.setGender(empReg.getGender());
            if (Locality.LOCALEMPLOYEE.getName().equals(empReg.getLocation())) {
                empDetails.setLocalEmployee(true);
            }
            // Check if employee already exists in list
            int empIndex = reportData.indexOf(manPowerGenderStatistics);
            if (empIndex != -1) {
                reportData.get(empIndex).getEmpDetails().add(empDetails);
            } else {
                manPowerGenderStatistics.getEmpDetails().add(empDetails);
                reportData.add(manPowerGenderStatistics);
            }
        }

        return reportData;
    }

    @Override
    public List<ManPowerMobilizationStatistics> getManpowerPeriodicalMobilisationReport(
            ManPowerGenderStatisticsReq manpowerMobilisationGetReq) {

        List<Long> projIds = manpowerMobilisationGetReq.getProjIds();
        List<Long> companyIds = manpowerMobilisationGetReq.getCmpIds();
        Date fromDate = CommonUtil.convertStringToDate(manpowerMobilisationGetReq.getFromDate());
        Date toDate = CommonUtil.convertStringToDate(manpowerMobilisationGetReq.getToDate());
        List<ManPowerMobilizationStatistics> mobilizationStatisticsList = new ArrayList<>();

        List<EmpRegisterDtlEntity> emRegList = empRegisterRepository.manpowerPeriodicalMobilizationStatistics(projIds,
                companyIds, fromDate, toDate);
        ManpowerReportsHandler manpowerReportsHandler = new ManpowerReportsHandler();
        for (EmpRegisterDtlEntity empReg : emRegList) {
            manpowerReportsHandler.getManPowerMobilizationStatistics(empReg, mobilizationStatisticsList,
                    projEmpClassRepositoryCopy, projEmpRegisterRepository, fromDate, toDate);
        }

        return mobilizationStatisticsList;
    }    
    public List<MasterEmployeeDetailsTO> getManpowerEmployeeDetail(ManpowerEmployeeDetailsGetReq manpowerEmployeeDetailsGetReq){
    	
      List<Long> projIds = manpowerEmployeeDetailsGetReq.getProjIds();
  	  List<Long> companyIds = manpowerEmployeeDetailsGetReq.getCmpIds();
  	  List<MasterEmployeeDetailsTO> masterEmployeeDetail = new ArrayList<>();
  	  List<EmpRegisterDtlEntity> empRegisterDtlEntity = new ArrayList<>();
  	  Date fromDate = CommonUtil.convertStringToDate(manpowerEmployeeDetailsGetReq.getEnrollFromDate()); 
  	  Date toDate = CommonUtil.convertStringToDate(manpowerEmployeeDetailsGetReq.getEnrollToDate());
  	  
  	  Date expecMobfromDate = CommonUtil.convertStringToDate(manpowerEmployeeDetailsGetReq.getExpectedDeMobFromDate());
  	  Date expecMobToDate = CommonUtil.convertStringToDate(manpowerEmployeeDetailsGetReq.getExpectedDeMobToDate());
  	  
  	  Date actualDeMobfromDate = CommonUtil.convertStringToDate(manpowerEmployeeDetailsGetReq.getActualDeMobFromDate());
  	  Date actualDeMobToDate = CommonUtil.convertStringToDate(manpowerEmployeeDetailsGetReq.getExpectedDeMobToDate());//manpowerEmployeeDetailsGetReq.getActualDeMobToDate()
  	  String genderType = manpowerEmployeeDetailsGetReq.getGender();
  	  String currStatus  = manpowerEmployeeDetailsGetReq.getCurrentStatus();
  	  String empTypes = manpowerEmployeeDetailsGetReq.getEmploymentType();
  	  
  	if(companyIds.size() == 0) {
  		if(companyIds.size() == 0 && genderType == null && currStatus == null && CommonUtil.isNotBlankDate(fromDate)) {
  			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType == null && currStatus == null && CommonUtil.isNotBlankDate(fromDate)) {
  				List<String> currStat = Arrays.asList("On Transfer", "Relived");
  		  		List<String> genders = Arrays.asList("Male","Female"); 
  			empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithEnrollDate(projIds, genders, empTypes, currStat, fromDate, toDate);
  	  		}
  		}
  		
  		
		if(companyIds.size() == 0 && fromDate == null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType == null && currStatus == null) {
				List<String> currStat = Arrays.asList("On Transfer", "Relived");
		  		List<String> genders = Arrays.asList("Male","Female"); 
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutEnrollDate(projIds, genders, empTypes, currStat);	
			}
		}		
		if(companyIds.size() == 0 && fromDate == null && currStatus != null && genderType != null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType.equals("Male") || genderType.equals("Female") && currStatus.equals("On Transfer") || currStatus.equals("Relived")) {
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutcurrStatus(projIds, genderType, empTypes, currStatus);	
			}
		}
		
		if(companyIds.size() == 0 && fromDate == null && currStatus != null && genderType == null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType == null && currStatus.equals("On Transfer") || currStatus.equals("Relived")) {
				List<String> genders = Arrays.asList("Male","Female"); 
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutgenderTypeNull(projIds, genders, empTypes, currStatus);	
			}
		}
		
		if(companyIds.size() == 0 && fromDate == null && currStatus == null && genderType != null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && currStatus == null && genderType.equals("Male") || genderType.equals("Female")) {
				List<String> currStat = Arrays.asList("On Transfer", "Relived");
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCurrStatusNull(projIds, genderType, empTypes, currStat);	
			}
		}
		

		if(companyIds.size() == 0 && actualDeMobfromDate != null && currStatus != null && genderType != null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType.equals("Male") || genderType.equals("Female") && currStatus.equals("On Transfer") || currStatus.equals("Relived")) {
			empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCmpActual(projIds, genderType, empTypes, currStatus, actualDeMobfromDate, actualDeMobToDate);	
			}
		}
		
		if(companyIds.size() == 0 && actualDeMobfromDate != null && currStatus != null && genderType == null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType == null && currStatus.equals("On Transfer") || currStatus.equals("Relived")) {
				List<String> genders = Arrays.asList("Male","Female"); 
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCmpActualgenderTypeNull(projIds, genders, empTypes, currStatus, actualDeMobfromDate, actualDeMobToDate);	
			}
		}
		
		if(companyIds.size() == 0 && actualDeMobfromDate != null && currStatus == null && genderType != null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && currStatus == null && genderType.equals("Male") || genderType.equals("Female")) {
				List<String> currStat = Arrays.asList("On Transfer", "Relived");
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCmpActualCurrStatusNull(projIds, genderType, empTypes, currStat, actualDeMobfromDate, actualDeMobToDate);	
			}
		}
		
		if(companyIds.size() == 0 && expecMobfromDate != null && currStatus != null && genderType != null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType.equals("Male") || genderType.equals("Female") && currStatus.equals("On Transfer") || currStatus.equals("Relived")) {
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCmpExcepted(projIds, genderType, empTypes, currStatus, expecMobfromDate, expecMobToDate);	
			}
		}
		
		if(companyIds.size() == 0 && expecMobfromDate != null && currStatus != null && genderType == null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType == null && currStatus.equals("On Transfer") || currStatus.equals("Relived")) {
				List<String> genders = Arrays.asList("Male","Female"); 
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCmpExceptedgenderTypeNull(projIds, genders, empTypes, currStatus, expecMobfromDate, expecMobToDate);	
			}
		}
		
		if(companyIds.size() == 0 && expecMobfromDate != null && currStatus == null && genderType != null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && currStatus == null && genderType.equals("Male") || genderType.equals("Female")) {
				List<String> currStat = Arrays.asList("On Transfer", "Relived");
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCmpExceptedCurrStatusNull(projIds, genderType, empTypes, currStat, expecMobfromDate, expecMobToDate);	
			}
		}
		
		if(companyIds.size() == 0 && actualDeMobfromDate != null && fromDate != null && expecMobfromDate != null && currStatus != null && genderType != null) {
			System.out.println("1");
			if(companyIds.size() >0 && empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType.equals("Male") || genderType.equals("Female") && currStatus.equals("On Transfer") || currStatus.equals("Relived") && actualDeMobfromDate != null && fromDate != null && expecMobfromDate != null) {
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCompDtls(projIds, genderType, empTypes, currStatus, fromDate, toDate, actualDeMobfromDate, actualDeMobToDate, expecMobfromDate, expecMobToDate);	
			}
		}
		
		if(companyIds.size() == 0 && actualDeMobfromDate != null && fromDate != null && expecMobfromDate != null && currStatus != null && genderType == null) {
			System.out.println("2");
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType == null && currStatus.equals("On Transfer") || currStatus.equals("Relived")) {
				List<String> genders = Arrays.asList("Male","Female"); 
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCompDtlsGenderTypeNull(projIds, genders, empTypes, currStatus, fromDate, toDate, actualDeMobfromDate, actualDeMobToDate, expecMobfromDate, expecMobToDate);	
			}
		}
		
		if(companyIds.size() == 0 && actualDeMobfromDate != null && fromDate != null && expecMobfromDate != null && currStatus == null && genderType != null ) {
			System.out.println("3");
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && currStatus == null && genderType.equals("Male") || genderType.equals("Female")) {
				List<String> currStat = Arrays.asList("On Transfer", "Relived");
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCompDtlsCurrStatusNull(projIds, genderType, empTypes, currStat, fromDate, toDate, actualDeMobfromDate, actualDeMobToDate, expecMobfromDate, expecMobToDate);	
			}
		}
		
  	}
  	if(companyIds.size() >0) {
		 
		if(companyIds.size() > 0 && genderType == null && fromDate != null) {
			 if(companyIds.size() > 0 && empTypes.equals("Full Time") || empTypes.equals("Part Time") || empTypes.equals("Causal") || empTypes.equals("Contract") && genderType == null && currStatus == null && fromDate != null) {
				List<String> currStat = Arrays.asList("On Transfer", "Relived"); 
				List<String> genders = Arrays.asList("Male","Female");
				empRegisterDtlEntity = empRegisterRepository.findEmpMasterDtlswithMale(projIds, genders, empTypes, companyIds, currStat, fromDate, toDate); 				
			 }
		}
		
		 if(companyIds.size() > 0 && genderType == null && currStatus == null && fromDate == null && actualDeMobfromDate == null) {
			 if(companyIds.size() > 0 && empTypes.equals("Part Time") && genderType == null && currStatus == null && fromDate == null) {
					List<String> currStat = Arrays.asList("On Transfer", "Relived");
			  		List<String> genders = Arrays.asList("Male","Female"); 
			  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny(projIds, genders, companyIds, empTypes, currStat);
				}else if(companyIds.size() > 0 && empTypes.equals("Casual") && genderType == null && currStatus == null && fromDate == null) {
					List<String> currStat = Arrays.asList("On Transfer", "Relived");
			  		List<String> genders = Arrays.asList("Male","Female"); 
			  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny(projIds, genders, companyIds, empTypes, currStat);
				}else if(companyIds.size() > 0 && empTypes.equals("Contract") && genderType == null && currStatus == null && fromDate == null) {
					List<String> currStat = Arrays.asList("On Transfer", "Relived");
			  		List<String> genders = Arrays.asList("Male","Female"); 
			  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny(projIds, genders, companyIds, empTypes, currStat);
				}else if(companyIds.size() > 0 && empTypes.equals("Full Time") && genderType == null && currStatus == null && fromDate == null) {
					List<String> currStat = Arrays.asList("On Transfer", "Relived");
			  		List<String> genders = Arrays.asList("Male","Female"); 
			  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny(projIds, genders, companyIds, empTypes, currStat);
				}
		 }

		        if(companyIds.size() > 0 && empTypes.equals("Contract") && genderType != null && currStatus != null && actualDeMobfromDate == null && expecMobfromDate == null) {
		        	if(companyIds.size() > 0 && empTypes.equals("Contract") && genderType.equals("Male") && currStatus.equals("On Transfer") && fromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Contract") && genderType.equals("Male") && currStatus.equals("Relived") && fromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Contract") && genderType.equals("Female") && currStatus.equals("On Transfer") && fromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Contract") && genderType.equals("Female") && currStatus.equals("Relived") && fromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}
		        }
		        
		        if(companyIds.size() > 0 && empTypes.equals("Full Time") && genderType != null && currStatus != null && actualDeMobfromDate == null && expecMobfromDate == null) {
		        	if(companyIds.size() > 0 && empTypes.equals("Full Time") && genderType.equals("Male") && currStatus.equals("On Transfer") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) { 
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Full Time") && genderType.equals("Male") && currStatus.equals("Relived") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Full Time") && genderType.equals("Female") && currStatus.equals("On Transfer") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Full Time") && genderType.equals("Female") && currStatus.equals("Relived") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}
		        }
				
		        if(companyIds.size() > 0 && empTypes.equals("Part Time") && genderType != null && currStatus != null && actualDeMobfromDate == null && expecMobfromDate == null ) {
		        	if(companyIds.size() > 0 && empTypes.equals("Part Time") && genderType.equals("Male") && currStatus.equals("On Transfer") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Part Time") && genderType.equals("Male") && currStatus.equals("Relived") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Part Time") && genderType.equals("Female") && currStatus.equals("On Transfer") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Part Time") && genderType.equals("Female") && currStatus.equals("Relived") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}
		        }
				
		        if(companyIds.size() > 0 && empTypes.equals("Casual") && genderType != null && currStatus != null && actualDeMobfromDate == null && expecMobfromDate == null) {
		        	if(companyIds.size() > 0 && empTypes.equals("Casual") && genderType.equals("Male") && currStatus.equals("On Transfer") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Casual") && genderType.equals("Male") && currStatus.equals("Relived") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Casual") && genderType.equals("Female") && currStatus.equals("On Transfer") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}else if(companyIds.size() > 0 && empTypes.equals("Casual") && genderType.equals("Female") && currStatus.equals("Relived") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
				  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCmpny1(projIds,  genderType, companyIds, empTypes, currStatus);
					}
		        }
				
		        
		       
		 
		         if(genderType == null && companyIds.size() > 0 && fromDate == null && currStatus != null && actualDeMobfromDate == null && expecMobfromDate == null) {		        	
		        	 if(companyIds.size() > 0 && empTypes.equals("Casual") && genderType == null && currStatus.equals("On Transfer") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
					  		List<String> genders = Arrays.asList("Male","Female");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithGenderType(projIds,  genders, companyIds, empTypes, currStatus);
						}else if(companyIds.size() > 0 && empTypes.equals("Casual") && genderType == null && currStatus.equals("Relived") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
					  		List<String> genders = Arrays.asList("Male","Female");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithGenderType(projIds,  genders, companyIds, empTypes, currStatus);
					  		
						}
				        
						else if(companyIds.size() > 0 && empTypes.equals("Part Time") && genderType == null && currStatus.equals("On Transfer") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
					  		List<String> genders = Arrays.asList("Male","Female");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithGenderType(projIds,  genders, companyIds, empTypes, currStatus);
						}else if(companyIds.size() > 0 && empTypes.equals("Part Time") && genderType == null && currStatus.equals("Relived") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
					  		List<String> genders = Arrays.asList("Male","Female");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithGenderType(projIds,  genders, companyIds, empTypes, currStatus);
					  		
						}
				        
						else if(companyIds.size() > 0 && empTypes.equals("Full Time") && genderType == null && currStatus.equals("On Transfer") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
					  		List<String> genders = Arrays.asList("Male","Female");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithGenderType(projIds,  genders, companyIds, empTypes, currStatus);
						}else if(companyIds.size() > 0 && empTypes.equals("Full Time") && genderType == null && currStatus.equals("Relived") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
					  		List<String> genders = Arrays.asList("Male","Female");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithGenderType(projIds,  genders, companyIds, empTypes, currStatus);
						}
				        
						else if(companyIds.size() > 0 && empTypes.equals("Contract") && genderType == null && currStatus.equals("On Transfer") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
					  		List<String> genders = Arrays.asList("Male","Female");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithGenderType(projIds,  genders, companyIds, empTypes, currStatus);
						}else if(companyIds.size() > 0 && empTypes.equals("Contract") && genderType == null && currStatus.equals("Relived") && fromDate == null && actualDeMobfromDate == null && expecMobfromDate == null) {
					  		List<String> genders = Arrays.asList("Male","Female");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithGenderType(projIds,  genders, companyIds, empTypes, currStatus);
					  		
						}
		        	 
		        	 
		         }
		 
		         
		         if(currStatus == null && companyIds.size() > 0 && fromDate == null && genderType != null && actualDeMobfromDate == null && expecMobfromDate == null) {		        	
		        	 if(companyIds.size() > 0 && empTypes.equals("Casual") && currStatus == null && genderType.equals("Male") && fromDate == null) {
					  		List<String> currStat = Arrays.asList("On Transfer", "Relived");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCurrntStatus(projIds, genderType, companyIds, empTypes, currStat);
						}else if(companyIds.size() > 0 && empTypes.equals("Casual") && currStatus == null && genderType.equals("Female") && fromDate == null) {
					  		List<String> currStat = Arrays.asList("On Transfer", "Relived");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCurrntStatus(projIds, genderType, companyIds, empTypes, currStat);
					  		
						}
				        
						else if(companyIds.size() > 0 && empTypes.equals("Part Time") && currStatus == null && genderType.equals("Male") && fromDate == null) {
					  		List<String> currStat = Arrays.asList("On Transfer", "Relived");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCurrntStatus(projIds, genderType, companyIds, empTypes, currStat);
						}else if(companyIds.size() > 0 && empTypes.equals("Part Time") && currStatus == null && genderType.equals("Female") && fromDate == null) {
					  		List<String> currStat = Arrays.asList("On Transfer", "Relived");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCurrntStatus(projIds, genderType, companyIds, empTypes, currStat);
					  		
						}
				        
						else if(companyIds.size() > 0 && empTypes.equals("Full Time") && currStatus == null && genderType.equals("Male") && fromDate == null) {
					  		List<String> currStat = Arrays.asList("On Transfer", "Relived");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCurrntStatus(projIds, genderType, companyIds, empTypes, currStat);
						}else if(companyIds.size() > 0 && empTypes.equals("Full Time") && currStatus == null && genderType.equals("Female") && fromDate == null) {
					  		List<String> currStat = Arrays.asList("On Transfer", "Relived");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCurrntStatus(projIds, genderType, companyIds, empTypes, currStat);
						}
				        
						else if(companyIds.size() > 0 && empTypes.equals("Contract") && genderType == null && genderType.equals("Male") && fromDate == null) {
					  		List<String> currStat = Arrays.asList("On Transfer", "Relived");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCurrntStatus(projIds, genderType, companyIds, empTypes, currStat);
						}else if(companyIds.size() > 0 && empTypes.equals("Contract") && genderType == null && genderType.equals("Female") && fromDate == null) {
					  		List<String> currStat = Arrays.asList("On Transfer","Relived");
					  		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCurrntStatus(projIds, genderType, companyIds, empTypes, currStat);
					  		
						}
		        	 
		        	 
		         }
		 
		 
		    if(companyIds.size()>0 && genderType != null && currStatus != null && fromDate != null) {
		    	if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType.equals("Male") || genderType.equals("Female") && currStatus.equals("On Transfer") || currStatus.equals("Relived") && fromDate != null) {
		    		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCompGenderTypeCurrStatusNull(projIds, genderType, companyIds, empTypes, currStatus, fromDate, toDate);
		    	}
		    }
		 
		    if(companyIds.size()>0 && genderType == null && currStatus != null && fromDate != null) {
		    	if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType == null && currStatus.equals("On Transfer") || currStatus.equals("Relived") && fromDate != null) {
		    		List<String> genders = Arrays.asList("Male","Female");
		    		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCompGenderTypeNull(projIds, genders, companyIds, empTypes, currStatus, fromDate, toDate);
		    	}
		    }
		 
		    if(companyIds.size()>0 && genderType != null && currStatus == null && fromDate != null) {
		    	if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType.equals("Male") || genderType.equals("Female") && currStatus == null && fromDate != null) {
		    		List<String> currStat = Arrays.asList("On Transfer","Relived");
		    		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCompCurrStatusNull(projIds, genderType, companyIds, empTypes, currStat, fromDate, toDate);
		    	}
		    }
		     
		    if(companyIds.size()>0 && genderType != null && currStatus != null && expecMobfromDate!= null) {
		    	if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType.equals("Male") || genderType.equals("Female") && currStatus.equals("On Transfer") || currStatus.equals("Relived") && expecMobfromDate != null) {
		    		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCompExcepMobDate(projIds, genderType, companyIds, empTypes, currStatus, expecMobfromDate, expecMobToDate);
		    	}
		    }
		 
		    if(companyIds.size()>0 && genderType == null && currStatus != null && expecMobfromDate != null) {
		    	if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType == null && currStatus.equals("On Transfer") || currStatus.equals("Relived") && expecMobfromDate != null) {
		    		List<String> genders = Arrays.asList("Male","Female");
		    		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCompExcepMobDateGenderTypeNull(projIds, genders, companyIds, empTypes, currStatus, expecMobfromDate, expecMobToDate);
		    	}
		    }
		 
		    if(companyIds.size()>0 && genderType != null && currStatus == null && expecMobfromDate != null) {
		    	if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType.equals("Male") || genderType.equals("Female") && currStatus == null && expecMobfromDate != null) {
		    		List<String> currStat = Arrays.asList("On Transfer","Relived");
		    		empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCompExcepMobDateCurrStatusNull(projIds, genderType, companyIds, empTypes, currStat, expecMobfromDate, expecMobToDate);
		    	}
		    }
  	}	
	if(companyIds.size() >0 && actualDeMobfromDate != null && fromDate == null && expecMobfromDate == null) {		
		if(companyIds.size() >0 && actualDeMobfromDate != null && currStatus != null && genderType != null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType.equals("Male") || genderType.equals("Female") && currStatus.equals("On Transfer") || currStatus.equals("Relived") && actualDeMobfromDate != null) {
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCompActualMobDate(projIds, genderType, companyIds, empTypes, currStatus, actualDeMobfromDate, actualDeMobToDate);	
			}
		}
		
		if(companyIds.size() > 0 && actualDeMobfromDate != null && currStatus != null && genderType == null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType == null && currStatus.equals("On Transfer") || currStatus.equals("Relived") && actualDeMobfromDate != null) {
				List<String> genders = Arrays.asList("Male","Female"); 
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCompActualMobDateGenderTypeNull(projIds, genders, companyIds, empTypes, currStatus, actualDeMobfromDate, actualDeMobToDate);	
			}
		}
		
		if(companyIds.size() > 0 && actualDeMobfromDate != null && currStatus == null && genderType != null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && currStatus == null && genderType.equals("Male") || genderType.equals("Female")) {
				List<String> currStat = Arrays.asList("On Transfer", "Relived");
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithCompActualMobDateCurrStatusNull(projIds, genderType, companyIds, empTypes, currStat, actualDeMobfromDate, actualDeMobToDate);	
			}
		}
	}
	
  	if(companyIds.size() >0) {
  		
  		if(companyIds.size() >0 && actualDeMobfromDate != null && fromDate != null && expecMobfromDate != null && currStatus != null && genderType != null) {
			if(companyIds.size() >0 && empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType.equals("Male") || genderType.equals("Female") && currStatus.equals("On Transfer") || currStatus.equals("Relived") && actualDeMobfromDate != null && fromDate != null && expecMobfromDate != null) {
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCompDtls(projIds, genderType, empTypes, currStatus, fromDate, toDate, actualDeMobfromDate, actualDeMobToDate, expecMobfromDate, expecMobToDate);	
			}
		}
		
		if(companyIds.size() > 0 && actualDeMobfromDate != null && fromDate != null && expecMobfromDate != null && currStatus != null && genderType == null) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && genderType == null && currStatus.equals("On Transfer") || currStatus.equals("Relived")) {
				List<String> genders = Arrays.asList("Male","Female"); 
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCompDtlsGenderTypeNull(projIds, genders, empTypes, currStatus, fromDate, toDate, actualDeMobfromDate, actualDeMobToDate, expecMobfromDate, expecMobToDate);	
			}
		}
		
		if(companyIds.size() > 0 && actualDeMobfromDate != null && fromDate != null && expecMobfromDate != null && currStatus == null && genderType != null ) {
			if(empTypes.equals("Full Time")|| empTypes.equals("Part Time") || empTypes.equals("Casual") || empTypes.equals("Contract") && currStatus == null && genderType.equals("Male") || genderType.equals("Female")) {
				List<String> currStat = Arrays.asList("On Transfer", "Relived");
				empRegisterDtlEntity =  empRegisterRepository.findEmpMasterDtlswithOutCompDtlsCurrStatusNull(projIds, genderType, empTypes, currStat, fromDate, toDate, actualDeMobfromDate, actualDeMobToDate, expecMobfromDate, expecMobToDate);	
			}
		}
  	}
	 for(EmpRegisterDtlEntity empRegisterDtlEntities : empRegisterDtlEntity ) {
		 MasterEmployeeDetailsTO masterEmployeeDetailsTO = new MasterEmployeeDetailsTO();
		
		 masterEmployeeDetailsTO.setId(empRegisterDtlEntities.getId());
		 masterEmployeeDetailsTO.setFirstName(empRegisterDtlEntities.getFirstName());
		 masterEmployeeDetailsTO.setLastName(empRegisterDtlEntities.getLastName());
		 masterEmployeeDetailsTO.setCmpId(empRegisterDtlEntities.getCompanyMstrEntity().getId());
		 masterEmployeeDetailsTO.setEmpCode(empRegisterDtlEntities.getCode());
		 masterEmployeeDetailsTO.setCmpCode(empRegisterDtlEntities.getCompanyMstrEntity().getCode());
		 masterEmployeeDetailsTO.setCmpName(empRegisterDtlEntities.getCompanyMstrEntity().getName());
		 masterEmployeeDetailsTO.setGender(empRegisterDtlEntities.getGender());
		 masterEmployeeDetailsTO.setDob(CommonUtil.convertDateToString(empRegisterDtlEntities.getDob()));
		 masterEmployeeDetailsTO.setEmpClassId(empRegisterDtlEntities.getEmpClassMstrEntity().getId());
		 masterEmployeeDetailsTO.setEmpClassName(empRegisterDtlEntities.getEmpClassMstrEntity().getName());
		 masterEmployeeDetailsTO.setEmpClassCode(empRegisterDtlEntities.getEmpClassMstrEntity().getCode());
		 masterEmployeeDetailsTO.setLocation(empRegisterDtlEntities.getLocation());
		 masterEmployeeDetailsTO.setEmpStatus(empRegisterDtlEntities.getEmpStatus());
		 masterEmployeeDetailsTO.setProjId(empRegisterDtlEntities.getProjMstrEntity().getProjectId());
		 masterEmployeeDetailsTO.setParentName(empRegisterDtlEntities.getProjMstrEntity().getParentProjectMstrEntity().getProjName());
		 masterEmployeeDetailsTO.setParentProjId(empRegisterDtlEntities.getProjMstrEntity().getParentProjectMstrEntity().getProjectId());
		 masterEmployeeDetailsTO.setParentCode(empRegisterDtlEntities.getProjMstrEntity().getParentProjectMstrEntity().getCode());
		 masterEmployeeDetailsTO.setName(empRegisterDtlEntities.getProjMstrEntity().getProjName());
		 masterEmployeeDetailsTO.setProcurecatgId(empRegisterDtlEntities.getProcureCatgDtlEntity().getId());
		 masterEmployeeDetailsTO.setProcurecatgName(empRegisterDtlEntities.getProcureCatgDtlEntity().getName());
		 
		 
		 if (CommonUtil.isListHasData(empRegisterDtlEntities.getProjEmpRigisterEntities())) {
             for (EmpProjRigisterEntity projEmpEntity : empRegisterDtlEntities.getProjEmpRigisterEntities()) {
            	 masterEmployeeDetailsTO.setCurrentStatus(projEmpEntity.getDemobilizationStatus());
            	 masterEmployeeDetailsTO.setEmpEnrollment(projEmpEntity.getEnrollmentDate());
            	 masterEmployeeDetailsTO.setMobilaizationDate(projEmpEntity.getMobilaizationDate());
            	 masterEmployeeDetailsTO.setExpectedDeMobDate(projEmpEntity.getDeMobilaizationDate());
            	 masterEmployeeDetailsTO.setActualDeMobDate(projEmpEntity.getExpectedDemobilaizationDate());
            	
                 if (projEmpEntity.getIsLatest().equals("Y")) {
                     ProjEmpRegisterTO projEmpRegisterTO = EmpEnrollmentDtlHandler.convertMobilizationDateEntityTO(projEmpEntity);
                    
                     if(CommonUtil.objectNotNull(projEmpEntity.getProjMstrEntity()) && CommonUtil.objectNotNull(projEmpEntity.getEmpClassMstrEntity())) {
                    	 ProjEmpClassMstrEntity projEmpClassMstrEntity = projEmpClassRepositoryCopy.getUserProjEmpClasses(projEmpEntity.getProjMstrEntity().getProjectId(), projEmpEntity.getEmpClassMstrEntity().getId(), StatusCodes.ACTIVE.getValue());
                    	 if(CommonUtil.objectNotNull(projEmpClassMstrEntity)) {
                    		 masterEmployeeDetailsTO.setProjEmpClassUnion(projEmpClassMstrEntity.getUnionName());
                    		 masterEmployeeDetailsTO.setProjEmpClassCatg(projEmpClassMstrEntity.getProjEmpCategory());
                    		 masterEmployeeDetailsTO.setProjEmpClassId(projEmpClassMstrEntity.getId());
                    		 masterEmployeeDetailsTO.setProjEmpClassTrade(projEmpClassMstrEntity.getTradeContrName());
                    	 }
                     }
                     for (EmpEnrollmentDtlEntity empEnrollmentDtlEntity : empRegisterDtlEntities.getEmpEnrollmentDtlEntities()) {
               //     	 System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$: "+empEnrollmentDtlEntity.size());
                         if (empEnrollmentDtlEntity.getIsLatest().equals("Y")) {
                             projEmpRegisterTO.setEnrollmentDate(CommonUtil.convertDateToString(empEnrollmentDtlEntity.getEffectiveFrom()));
                             masterEmployeeDetailsTO.setEnrollmentDate(empEnrollmentDtlEntity.getEffectiveFrom());
                         }
                     }
             //        registerDtlTO.setProjEmpRegisterTO(projEmpRegisterTO);
                 }
             }
         }
		 masterEmployeeDetail.add(masterEmployeeDetailsTO);
	 }
		  
	  return masterEmployeeDetail;

    }

}