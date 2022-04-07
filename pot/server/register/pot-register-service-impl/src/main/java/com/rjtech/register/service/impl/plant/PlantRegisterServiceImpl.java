package com.rjtech.register.service.impl.plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.PlantassertType;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.plant.dto.PlantLogRecordsTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.dto.PlantRegisterDtlTO;
import com.rjtech.register.plant.model.PlantRegProjEntity;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.req.PlantRegisterDeactivateReq;
import com.rjtech.register.plant.req.PlantRegisterDtlSaveReq;
import com.rjtech.register.plant.req.PlantRegisterGetReq;
import com.rjtech.register.plant.resp.PlantCurrentStatusResp;
import com.rjtech.register.plant.resp.PlantRegisterDtlResp;
import com.rjtech.register.plant.resp.ProjPlantRegMapResp;
import com.rjtech.register.repository.plant.PlantRegProjRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;
import com.rjtech.register.repository.plant.PlantTransferReqApprRepository;
import com.rjtech.register.service.handler.plant.PlantRegProjDtlHandler;
import com.rjtech.register.service.handler.plant.PlantRegisterDtlHandler;
import com.rjtech.register.service.plant.PlantLogRecordService;
import com.rjtech.register.service.plant.PlantRegisterService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timemanagement.attendence.repository.PlantAttendanceDtlRepositoryCopy;

@Service(value = "plantRegisterService")
@RJSService(modulecode = "plantRegisterService")
@Transactional
public class PlantRegisterServiceImpl implements PlantRegisterService {
	
	@Autowired
    private EPSProjService epsProjService;
	
    @Autowired
    private PlantRegisterRepository plantRegisterDtlRepository;

    @Autowired
    private PlantTransferReqApprRepository plantReqForTransRepository;

    @Autowired
    private PlantRegProjRepository plantProjDtlRepository;

    @Autowired
    private PlantLogRecordService plantLogRecordsService;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private ClientRegRepository clientRegRepository;

    @Autowired
    private PlantClassRepository plantClassRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProcureCatgRepository procureCatgRepository;

    @Autowired
    private PlantAttendanceDtlRepositoryCopy plantAttendanceDtlRepository;

	public PlantRegisterDtlResp getPlantRegisterDtls(PlantRegisterGetReq plantRegisterGetReq) {
		
		PlantRegisterDtlResp plantRegisterDtlResp = new PlantRegisterDtlResp();
		List<PlantRegisterDtlEntity> plantRegisterDtlEntites = null;
        System.out.println("plantRegisterGetReq$$$$$$$1212"+plantRegisterGetReq.getPlantCurrentStatus());
        System.out.println("plantRegisterGetReq.getProjIds()$$$$$$$"+plantRegisterGetReq.getProjIds());
		  List<Long> companyIds = null; 
		  List<Long> plantIds = null;
		  List<Long> procurecatgIds = null;
			/*
			 * Date purchaseCommissionefromDate = null; Date purchaseCommissionetoDate =
			 * null; Date actualMobilisationfromDate = null; Date actualMobilisationtoDate =
			 * null; Date deMobilisationfromDate = null; Date deMobilisationtoDate = null;
			 */
			Date purchaseCommissionefromDate = CommonUtil.convertStringToDate(plantRegisterGetReq.getPurchaseCommissionefromDate());
			Date purchaseCommissionetoDate = CommonUtil.convertStringToDate(plantRegisterGetReq.getPurchaseCommissionetoDate());
			Date actualMobilisationfromDate = CommonUtil.convertStringToDate(plantRegisterGetReq.getActualMobilisationfromDate());
			Date actualMobilisationtoDate = CommonUtil.convertStringToDate(plantRegisterGetReq.getActualMobilisationtoDate());
			Date deMobilisationfromDate = CommonUtil.convertStringToDate(plantRegisterGetReq.getDeMobilisationfromDate());
			Date deMobilisationtoDate = CommonUtil.convertStringToDate(plantRegisterGetReq.getDeMobilisationtoDate());
        	companyIds = plantRegisterGetReq.getCompanyNameDisplay();
        	plantIds = plantRegisterGetReq.getPlantNameDisplay();
        	procurecatgIds = plantRegisterGetReq.getProcurecatgId();
        	
        	if (CommonUtil.objectNotNull(plantRegisterGetReq.getProjIds())) {
           	 List<Long> projIds = null; 
           	 if (CommonUtil.isListHasData(plantRegisterGetReq.getProjIds())) {
        			projIds = plantRegisterGetReq.getProjIds();
        		} else {
        			projIds = epsProjService.getUserProjIds();
        		}
               plantRegisterDtlEntites = plantRegisterDtlRepository
                            .findLatestPlantsByProject(projIds, plantRegisterGetReq.getStatus());
           } else {
               plantRegisterDtlEntites = plantRegisterDtlRepository.findNewPlants(AppUserUtils.getClientId(),plantRegisterGetReq.getStatus());
           }
			/*
			 * if (CommonUtil.objectNotNull(plantRegisterGetReq.getProjId())) { projIds =
			 * plantRegisterGetReq.getProjId(); plantRegisterDtlEntites =
			 * plantRegisterDtlRepository
			 * .findLatestPlantsByProject(plantRegisterGetReq.getProjId(),
			 * plantRegisterGetReq.getStatus()); } else { plantRegisterDtlEntites =
			 * plantRegisterDtlRepository.findNewPlants(AppUserUtils.getClientId(),
			 * plantRegisterGetReq.getStatus()); }
			 */
			/**/ 
        	
       if(CommonUtil.objectNotNull(plantRegisterGetReq.getPurchaseCommissionefromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("On Transfer")) {
        	plantRegisterDtlEntites = plantRegisterDtlRepository
					.findMasterPlantList(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), companyIds, plantIds, procurecatgIds, plantRegisterGetReq.getPlantCurrentStatus(),purchaseCommissionefromDate, purchaseCommissionetoDate, 
							actualMobilisationfromDate, actualMobilisationtoDate, deMobilisationfromDate, deMobilisationtoDate);        	
        }
        if(CommonUtil.isNotBlankStr(plantRegisterGetReq.getPurchaseCommissionefromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("On Transfer")) {
       	 plantRegisterDtlEntites = plantRegisterDtlRepository
   					.findMasterPurchaseList(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), plantRegisterGetReq.getPlantCurrentStatus(), companyIds, plantIds, purchaseCommissionefromDate, purchaseCommissionetoDate);
       	}
        if(CommonUtil.isNotBlankStr(plantRegisterGetReq.getActualMobilisationfromDate()) && CommonUtil.isNotBlankStr(plantRegisterGetReq.getPurchaseCommissionefromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("On Transfer")) {
          	 plantRegisterDtlEntites = plantRegisterDtlRepository
      					.findMasterPurchaseActualMobList(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), plantRegisterGetReq.getPlantCurrentStatus(), companyIds, plantIds, purchaseCommissionefromDate, purchaseCommissionetoDate, actualMobilisationfromDate, actualMobilisationtoDate);
          	}
        if(CommonUtil.isNotBlankStr(plantRegisterGetReq.getActualMobilisationfromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("On Transfer")) {
         	 plantRegisterDtlEntites = plantRegisterDtlRepository
     					.findMasterActualMobList(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), plantRegisterGetReq.getPlantCurrentStatus(), companyIds, plantIds, actualMobilisationfromDate, actualMobilisationtoDate);
         	}
        
        if(CommonUtil.objectNotNull(plantRegisterGetReq.getPurchaseCommissionefromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("Return To Supplier")) {    
         	plantRegisterDtlEntites = plantRegisterDtlRepository.findMasterPlantList1(plantRegisterGetReq.getProjIds(),plantRegisterGetReq.getStatus(), purchaseCommissionefromDate, purchaseCommissionetoDate, 
 					actualMobilisationfromDate, actualMobilisationtoDate, deMobilisationfromDate, deMobilisationtoDate,plantRegisterGetReq.getPlantCurrentStatus(),plantIds, procurecatgIds);
        }
        if(CommonUtil.isNotBlankStr(plantRegisterGetReq.getPurchaseCommissionefromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("Return To Supplier")) {
          	 plantRegisterDtlEntites = plantRegisterDtlRepository
      					.findMasterPurchaseList1(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), plantRegisterGetReq.getPlantCurrentStatus(), plantIds, purchaseCommissionefromDate, purchaseCommissionetoDate);
          	}
           if(CommonUtil.isNotBlankStr(plantRegisterGetReq.getActualMobilisationfromDate()) && CommonUtil.isNotBlankStr(plantRegisterGetReq.getPurchaseCommissionefromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("Return To Supplier")) {
             	 plantRegisterDtlEntites = plantRegisterDtlRepository
         					.findMasterPurchaseActualMobList1(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), plantRegisterGetReq.getPlantCurrentStatus(), plantIds, purchaseCommissionefromDate, purchaseCommissionetoDate, actualMobilisationfromDate, actualMobilisationtoDate);
             	}
           if(CommonUtil.isNotBlankStr(plantRegisterGetReq.getActualMobilisationfromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("Return To Supplier")) {
            	 plantRegisterDtlEntites = plantRegisterDtlRepository
        					.findMasterActualMobList1(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), plantRegisterGetReq.getPlantCurrentStatus(), plantIds, actualMobilisationfromDate, actualMobilisationtoDate);
            	}
        if(CommonUtil.objectNotNull(plantRegisterGetReq.getPurchaseCommissionefromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("Salveged")) {    
         	plantRegisterDtlEntites = plantRegisterDtlRepository.findMasterPlantList1(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), purchaseCommissionefromDate, purchaseCommissionetoDate, 
 					actualMobilisationfromDate, actualMobilisationtoDate, deMobilisationfromDate, deMobilisationtoDate,plantRegisterGetReq.getPlantCurrentStatus(),plantIds, procurecatgIds);
        }
        if(CommonUtil.isNotBlankStr(plantRegisterGetReq.getPurchaseCommissionefromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("Salveged")) {
         	 plantRegisterDtlEntites = plantRegisterDtlRepository
     					.findMasterPurchaseList1(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), plantRegisterGetReq.getPlantCurrentStatus(), plantIds, purchaseCommissionefromDate, purchaseCommissionetoDate);
         	}
          if(CommonUtil.isNotBlankStr(plantRegisterGetReq.getActualMobilisationfromDate()) && CommonUtil.isNotBlankStr(plantRegisterGetReq.getPurchaseCommissionefromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("Salveged")) {
            	 plantRegisterDtlEntites = plantRegisterDtlRepository
        					.findMasterPurchaseActualMobList1(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), plantRegisterGetReq.getPlantCurrentStatus(), plantIds, purchaseCommissionefromDate, purchaseCommissionetoDate, actualMobilisationfromDate, actualMobilisationtoDate);
            	}
          if(CommonUtil.isNotBlankStr(plantRegisterGetReq.getActualMobilisationfromDate()) && plantRegisterGetReq.getPlantCurrentStatus().equals("Salveged")) {
           	 plantRegisterDtlEntites = plantRegisterDtlRepository
       					.findMasterActualMobList1(plantRegisterGetReq.getProjIds(), plantRegisterGetReq.getStatus(), plantRegisterGetReq.getPlantCurrentStatus(), plantIds, actualMobilisationfromDate, actualMobilisationtoDate);
           	}
		if (CommonUtil.isListHasData(plantRegisterDtlEntites)) {
			for (PlantRegisterDtlEntity plantRegisterDtlEntity : plantRegisterDtlEntites) {
				PlantRegisterDtlTO plantRegisterDtlTO = PlantRegisterDtlHandler
						.convertEntityToPOJO(plantRegisterDtlEntity);
				List<PlantRegProjEntity> plantRegProjEntities = plantRegisterDtlEntity.getPlantRegProjEntities();
				for (PlantRegProjEntity entity : plantRegProjEntities) {
					if (entity.getIsLatest().equalsIgnoreCase("Y")) {
						PlantRegProjTO plantRegProjTO = PlantRegProjDtlHandler.convertEntityToPOJO(entity);
						plantRegisterDtlTO.setPlantRegProjTO(plantRegProjTO);
						PlantLogRecordsTO logRecord = plantLogRecordsService
								.getMaxOdoMeterReadingByPlantProjId(plantRegProjTO.getId());
						if (CommonUtil.objectNotNull(logRecord)) {
							plantRegisterDtlTO.setCurrentOdoMeter(logRecord.getEndMeter());
						}
						break;
					}
				}
				plantRegisterDtlResp.getPlantRegisterDtlTOs().add(plantRegisterDtlTO);
			}
		}
		return plantRegisterDtlResp;
	}

	public void savePlantRegisterDtls(PlantRegisterDtlSaveReq plantRegisterDtlSaveReq) {
		plantRegisterDtlRepository.save(PlantRegisterDtlHandler.populatePlantRegisterEntities(
				plantRegisterDtlSaveReq.getPlantRegisterDtlTOs(), epsProjRepository, clientRegRepository,
				plantClassRepository, companyRepository, procureCatgRepository));

	}

	public void plantRegistersDeactivate(PlantRegisterDeactivateReq plantRegisterDeactivateReq) {
		plantRegisterDtlRepository.deactivatePlantRegisters(plantRegisterDeactivateReq.getPlantRegIds(),
				plantRegisterDeactivateReq.getStatus());

	}

	public List<String> getPlantAssertType() {
		List<String> assertTypes = new ArrayList<>();
		for (PlantassertType plantassertType : PlantassertType.values()) {
			assertTypes.add(plantassertType.getName());
		}
		return assertTypes;
	}

	public void deletePlantReqForTrans(Long plantReqForTransId) {
		plantReqForTransRepository.delete(plantReqForTransId);

	}

    public PlantCurrentStatusResp getPlantCurrentStatus(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantCurrentStatusResp resp = new PlantCurrentStatusResp();
        PlantRegProjEntity plantRegProjEntity = plantProjDtlRepository
                .findLatestPlantDeployment(plantProjectDtlGetReq.getPlantId());
        if (CommonUtil.objectNotNull(plantRegProjEntity)) {
            PlantRegProjTO plantProjectPOTO = new PlantRegProjTO();
            plantProjectPOTO.setId(plantRegProjEntity.getId());
            plantProjectPOTO.setProjId(plantRegProjEntity.getProjId().getProjectId());
            plantProjectPOTO.setCommissionDate(CommonUtil.convertDateToString(plantRegProjEntity.getCommissionDate()));
            plantProjectPOTO.setMobDate(CommonUtil.convertDateToString(plantRegProjEntity.getMobDate()));
            plantProjectPOTO.setAnticipatedDeMobDate(
                    CommonUtil.convertDateToString(plantRegProjEntity.getAnticipatedDeMobDate()));
            plantProjectPOTO.setDeMobDate(CommonUtil.convertDateToString(plantRegProjEntity.getDeMobDate()));
            PlantLogRecordsTO logRecord = plantLogRecordsService
                    .getMaxOdoMeterReadingByPlantProjId(plantRegProjEntity.getId());
            if (CommonUtil.objectNotNull(logRecord)) {
                plantProjectPOTO.setOdoMeter(logRecord.getEndMeter());
            }
            plantProjectPOTO.setPostDeMobStatus(plantRegProjEntity.getPostDeMobStatus());
            resp.getPlantRegProjTOs().add(plantProjectPOTO);
        }
        return resp;
    }

	public PlantRegisterDtlResp getPlantsNotInUserProjects(PlantRegisterGetReq plantRegisterGetReq) {
		PlantRegisterDtlResp plantRegisterDtlResp = new PlantRegisterDtlResp();
		List<PlantRegisterDtlEntity> plantRegisterDtlEntites = plantRegisterDtlRepository.findPlantsNotInUserProjects(
				AppUserUtils.getClientId(), plantRegisterGetReq.getProjIds(), StatusCodes.ACTIVE.getValue());
		if (CommonUtil.isListHasData(plantRegisterDtlEntites)) {
			for (PlantRegisterDtlEntity plantRegisterDtlEntity : plantRegisterDtlEntites) {
				plantRegisterDtlResp.getPlantRegisterDtlTOs()
						.add(PlantRegisterDtlHandler.convertEntityToPOJO(plantRegisterDtlEntity));
			}
		}
		return plantRegisterDtlResp;
	}

	public PlantRegisterDtlResp getPlantsInUserProjects(PlantRegisterGetReq plantRegisterGetReq) {
		PlantRegisterDtlResp plantRegisterDtlResp = new PlantRegisterDtlResp();
		List<PlantRegisterDtlEntity> plantRegisterDtlEntites = plantRegisterDtlRepository.findPlantsInUserProjects(
				AppUserUtils.getClientId(), plantRegisterGetReq.getProjIds(), StatusCodes.ACTIVE.getValue());
		if (CommonUtil.isListHasData(plantRegisterDtlEntites)) {
			for (PlantRegisterDtlEntity plantRegisterDtlEntity : plantRegisterDtlEntites) {
				plantRegisterDtlResp.getPlantRegisterDtlTOs()
						.add(PlantRegisterDtlHandler.convertEntityToPOJO(plantRegisterDtlEntity));
			}
		}
		return plantRegisterDtlResp;
	}

	public LabelKeyTOResp getPlantAttendence(PlantProjectDtlGetReq plantProjectDtlGetReq) {
		LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
		Long plantId = plantProjectDtlGetReq.getPlantId();
		List<Integer> years = CommonUtil.getPrecedingYears(2);
		List<Object[]> plantAttds = plantAttendanceDtlRepository.getPlantLeaveAttendanceYearWise(plantId, years);

		Map<String, LabelKeyTO> yearLabelMap = new HashMap<>();
		for (Object[] object : plantAttds) {
			String year = String.valueOf(object[0]);
			LabelKeyTO labelKeyTO;
			if (yearLabelMap.containsKey(year)) {
				labelKeyTO = yearLabelMap.get(year);
			} else {
				labelKeyTO = new LabelKeyTO();
				labelKeyTO.getDisplayNamesMap().put(CommonConstants.PLANT_YEAR, String.valueOf(year));
				yearLabelMap.put(year, labelKeyTO);
			}
			Map<String, String> displayMap = labelKeyTO.getDisplayNamesMap();

			if (displayMap.get(CommonConstants.IDLE_DAYS) != null) {
				Long existing = Long.valueOf(displayMap.get(CommonConstants.IDLE_DAYS));
				displayMap.put(CommonConstants.IDLE_DAYS, String.valueOf(existing + (Long) object[1]));
			} else {
				displayMap.put(CommonConstants.IDLE_DAYS, String.valueOf(object[1]));
			}

			if (displayMap.get(CommonConstants.NONWORKING_DAYS) != null) {
				Long existing = Long.valueOf(displayMap.get(CommonConstants.NONWORKING_DAYS));
				displayMap.put(CommonConstants.NONWORKING_DAYS, String.valueOf(existing + (Long) object[2]));
			} else {
				displayMap.put(CommonConstants.NONWORKING_DAYS, String.valueOf(object[2]));
			}

			if (displayMap.get(CommonConstants.WORKING_DAYS) != null) {
				Long existing = Long.valueOf(displayMap.get(CommonConstants.WORKING_DAYS));
				displayMap.put(CommonConstants.WORKING_DAYS, String.valueOf(existing + (Long) object[3]));
			} else {
				displayMap.put(CommonConstants.WORKING_DAYS, String.valueOf(object[3]));
			}

		}
		labelKeyTOResp.setLabelKeyTOs(new ArrayList<LabelKeyTO>(yearLabelMap.values()));
		return labelKeyTOResp;
	}

	public ProjPlantRegMapResp getMultiProjPlantListMap(PlantRegisterGetReq plantRegisterGetReq) {
		ProjPlantRegMapResp projPlantRegMapResp = new ProjPlantRegMapResp();
		List<PlantRegisterDtlEntity> plantRegisterDtlEntites = null;
		plantRegisterDtlEntites = plantRegisterDtlRepository.findMultiProjPlantDetails(plantRegisterGetReq.getProjIds(),
				plantRegisterGetReq.getStatus());
		PlantRegisterDtlTO plantRegisterDtlTO = null;
		Map<Long, PlantRegisterDtlTO> plantLatestProjMap = new HashMap<>();
		for (PlantRegisterDtlEntity plantRegisterDtlEntity : plantRegisterDtlEntites) {
			plantRegisterDtlTO = PlantRegisterDtlHandler.convertEntityToPOJO(plantRegisterDtlEntity);
			plantLatestProjMap.put(plantRegisterDtlEntity.getId(), plantRegisterDtlTO);
		}
		projPlantRegMapResp.setProjPlantRegMap(plantLatestProjMap);
		return projPlantRegMapResp;
	}

	@Override
	public boolean isPlantCodeUnique(String assertId) {
		return plantRegisterDtlRepository
				.findByAssertIdAndClientId(assertId, clientRegRepository.findOne(AppUserUtils.getClientId())).isEmpty();
	}
}
