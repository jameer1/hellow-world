package com.rjtech.projschedule.service.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.common.dto.CostActualHoursTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.repository.ResourceCurveRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.model.ProjSORItemEntity;
//import com.rjtech.projectlib.model.ProjSORItemEntityCopy;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjSOEItemRepositoryCopy;
import com.rjtech.projschedule.dto.ResourceAssignmentDataTO;
import com.rjtech.projschedule.dto.ResourceAssignmentDataValueTO;
import com.rjtech.projschedule.dto.ScheduleActivityDataSetTO;
import com.rjtech.projschedule.model.ProjScheduleBaseLineEntity;
import com.rjtech.projschedule.model.ProjScheduleCostCodeEntity;
import com.rjtech.projschedule.model.ProjScheduleManPowerEntity;
import com.rjtech.projschedule.model.ProjScheduleMaterialEntity;
import com.rjtech.projschedule.model.ProjSchedulePlantEntity;
import com.rjtech.projschedule.model.ProjScheduleSOWEntity;
import com.rjtech.projschedule.model.ResourceAssignmentDataEntity;
import com.rjtech.projschedule.model.ResourceAssignmentDataValueEntity;
import com.rjtech.projschedule.repository.ProjectPlantsRepository;
import com.rjtech.projsettings.dto.ProjCostStmtDtlTO;
import com.rjtech.projsettings.dto.ProjManpowerTO;
import com.rjtech.projsettings.dto.ProjectMaterialDtlTO;
import com.rjtech.projsettings.dto.ProjectPlantsDtlTO;
import com.rjtech.projsettings.model.ProjManpowerEntity;
import com.rjtech.projsettings.model.ProjectMaterialBudgetEntity;
import com.rjtech.projsettings.model.ProjectPlantsDtlEntity;
import com.rjtech.projsettings.repository.ProjManpowerRepository;
import com.rjtech.projsettings.repository.ProjectMaterialRepository;
//import com.rjtech.projsettings.repository.ProjectPlantsRepository;
import com.rjtech.projsettings.resp.ProjGeneralsResp;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class ResourceAssignmentDataHandler {
	
	public static String POT_EMPLOYEE = "POT_EMPLOYEE";
	public static String POT_PLANT = "POT_PLANT";
	public static String POT_MATERIAL = "POT_MATERIAL";
	public static String POT_COST = "POT_COST";
	public static String POT_TANGIBLE = "POT_TANGIBLE";
	public static String POT_SOR = "POT_SOR";

	public static ResourceAssignmentDataTO convertEntityToPOJO(ResourceAssignmentDataEntity resourceAssignmentDataEntity) {
		ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();
		resourceAssignmentDataTO.setId(resourceAssignmentDataEntity.getId());
		resourceAssignmentDataTO.setActivityCode(resourceAssignmentDataEntity.getActivityCode());
		resourceAssignmentDataTO.setActivityName(resourceAssignmentDataEntity.getActivityName());
		resourceAssignmentDataTO.setBudgetUnits(resourceAssignmentDataEntity.getBudgetUnits());
		resourceAssignmentDataTO.setCalendar(resourceAssignmentDataEntity.getCalendar());
		resourceAssignmentDataTO.setCode(resourceAssignmentDataEntity.getCode());
		resourceAssignmentDataTO.setCurve(resourceAssignmentDataEntity.getCurve());
		resourceAssignmentDataTO.setFinishDate(resourceAssignmentDataEntity.getFinishDate());
		resourceAssignmentDataTO.setFinishDateFinal(resourceAssignmentDataEntity.getFinishDateFlag().equalsIgnoreCase("Y") ? true : false);
		resourceAssignmentDataTO.setReferenceId(resourceAssignmentDataEntity.getReferenceId());
		resourceAssignmentDataTO.setReferenceType(resourceAssignmentDataEntity.getReferenceType());
		if (resourceAssignmentDataEntity.getSoe() != null) {
			resourceAssignmentDataTO.setSoeCode(resourceAssignmentDataEntity.getSoe().getCode());
			resourceAssignmentDataTO.setSoeId(resourceAssignmentDataEntity.getSoe().getId());
		}
		resourceAssignmentDataTO.setSpreadsheetField(resourceAssignmentDataEntity.getSpreadsheetField());
		resourceAssignmentDataTO.setStartDate(resourceAssignmentDataEntity.getStartDate());
		resourceAssignmentDataTO.setStartDateFinal(resourceAssignmentDataEntity.getStartDateFlag().equalsIgnoreCase("Y") ? true : false);
		resourceAssignmentDataTO.setStatus(resourceAssignmentDataEntity.getStatus());
		resourceAssignmentDataTO.setType(resourceAssignmentDataEntity.getType());
		resourceAssignmentDataTO.setUnitOfMeasure(resourceAssignmentDataEntity.getUnitOfMeasure());
		resourceAssignmentDataTO.setWbsCode(resourceAssignmentDataEntity.getWbsCode());
		resourceAssignmentDataTO.setWbsName(resourceAssignmentDataEntity.getWbsName());
		resourceAssignmentDataTO.setName(resourceAssignmentDataEntity.getName());
		
		List<ResourceAssignmentDataValueTO> resourceAssignmentDataValueTOs = new ArrayList<ResourceAssignmentDataValueTO>();
		for (ResourceAssignmentDataValueEntity resourceAssignmentDataValueEntity : resourceAssignmentDataEntity.getResourceAssignmentDataValueEntities())
			resourceAssignmentDataValueTOs.add(ResourceAssignmentDataValueHandler.convertEntityToPOJO(resourceAssignmentDataValueEntity));
		resourceAssignmentDataValueTOs.sort(Comparator.comparing(ResourceAssignmentDataValueTO::getForecastDate));
		resourceAssignmentDataTO.setResourceAssignmentDataValueTOs(resourceAssignmentDataValueTOs);
		
		return resourceAssignmentDataTO;
	}
	
	public static ResourceAssignmentDataEntity convertPOJOToEntity(ResourceAssignmentDataTO resourceAssignmentDataTO, Long projectId, ProjSOEItemRepositoryCopy projSOEItemRepositoryCopy, LoginRepository loginRepository) {
		ResourceAssignmentDataEntity resourceAssignmentDataEntity = new ResourceAssignmentDataEntity();
		resourceAssignmentDataEntity.setId(resourceAssignmentDataTO.getId());
		resourceAssignmentDataEntity.setActivityCode(resourceAssignmentDataTO.getActivityCode());
		resourceAssignmentDataEntity.setActivityName(resourceAssignmentDataTO.getActivityName());
		resourceAssignmentDataEntity.setBudgetUnits(resourceAssignmentDataTO.getBudgetUnits());
		resourceAssignmentDataEntity.setCalendar(resourceAssignmentDataTO.getCalendar());
		resourceAssignmentDataEntity.setCode(resourceAssignmentDataTO.getCode());
		resourceAssignmentDataEntity.setCurve(resourceAssignmentDataTO.getCurve());
		resourceAssignmentDataEntity.setFinishDate(resourceAssignmentDataTO.getFinishDate());
		resourceAssignmentDataEntity.setFinishDateFlag(resourceAssignmentDataTO.isFinishDateFinal() ? "Y" : "N");
		resourceAssignmentDataEntity.setReferenceId(resourceAssignmentDataTO.getReferenceId());
		resourceAssignmentDataEntity.setReferenceType(resourceAssignmentDataTO.getReferenceType());
		if (resourceAssignmentDataTO.getSoeCode() != null)
			resourceAssignmentDataEntity.setSoe(projSOEItemRepositoryCopy.findBy(projectId, resourceAssignmentDataTO.getSoeCode()));
		resourceAssignmentDataEntity.setSpreadsheetField(resourceAssignmentDataTO.getSpreadsheetField());
		resourceAssignmentDataEntity.setStartDate(resourceAssignmentDataTO.getStartDate());
		resourceAssignmentDataEntity.setStartDateFlag(resourceAssignmentDataTO.isStartDateFinal() ? "Y" : "N");
		resourceAssignmentDataEntity.setStatus(resourceAssignmentDataTO.getStatus());
		resourceAssignmentDataEntity.setType(resourceAssignmentDataTO.getType());
		resourceAssignmentDataEntity.setUnitOfMeasure(resourceAssignmentDataTO.getUnitOfMeasure());
		resourceAssignmentDataEntity.setWbsCode(resourceAssignmentDataTO.getWbsCode());
		resourceAssignmentDataEntity.setWbsName(resourceAssignmentDataTO.getWbsName());
		resourceAssignmentDataEntity.setCreatedBy(loginRepository.findOne(AppUserUtils.getUserId()));
		resourceAssignmentDataEntity.setUpdatedBy(loginRepository.findOne(AppUserUtils.getUserId()));
		resourceAssignmentDataEntity.setUpdatedOn(new Date());
		resourceAssignmentDataEntity.setName(resourceAssignmentDataTO.getName());
		
		List<ResourceAssignmentDataValueEntity> resourceAssignmentDataValueEntities = new ArrayList<ResourceAssignmentDataValueEntity>();
		for (ResourceAssignmentDataValueTO resourceAssignmentDataValueTO : resourceAssignmentDataTO.getResourceAssignmentDataValueTOs())
			resourceAssignmentDataValueEntities.add(ResourceAssignmentDataValueHandler.convertPOJOToEntity(resourceAssignmentDataValueTO, resourceAssignmentDataEntity));
		resourceAssignmentDataEntity.setResourceAssignmentDataValueEntities(resourceAssignmentDataValueEntities);
		
		return resourceAssignmentDataEntity;
	}
	
	public static ResourceAssignmentDataTO fromProjManpowerTO(ProjManpowerTO projManpowerTO, ProjGeneralsResp projGeneralsResp) {
		ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();
		resourceAssignmentDataTO.setValid(true);
		resourceAssignmentDataTO.setReferenceId(projManpowerTO.getEmpClassId());
		resourceAssignmentDataTO.setCode(projManpowerTO.getEmpClassTO().getCode());
		resourceAssignmentDataTO.setName(projManpowerTO.getEmpClassTO().getName());
		resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_EMPLOYEE);
		resourceAssignmentDataTO.setType("labor");
		resourceAssignmentDataTO.setActivityCode("ACT-" + projManpowerTO.getEmpClassTO().getCode() + "-" + projManpowerTO.getId());
		resourceAssignmentDataTO.setActivityName("ACT-" + projManpowerTO.getEmpClassTO().getName());
		resourceAssignmentDataTO.setWbsCode("WBS-" + projManpowerTO.getEmpClassTO().getCode() + "-" + projManpowerTO.getId());
		resourceAssignmentDataTO.setWbsName("WBS-" + projManpowerTO.getEmpClassTO().getName());
		resourceAssignmentDataTO.setStartDate(CommonUtil.convertStringToDate(projManpowerTO.getStartDate()));
		resourceAssignmentDataTO.setFinishDate(CommonUtil.convertStringToDate(projManpowerTO.getFinishDate()));
		resourceAssignmentDataTO.setUnitOfMeasure(projManpowerTO.getMeasureUnitTO().getName());
		resourceAssignmentDataTO.setBudgetUnits(Math.round((projManpowerTO.getOriginalQty())));
		if (projManpowerTO.getRevisedQty() != null && projManpowerTO.getRevisedQty() > 0)
			resourceAssignmentDataTO.setBudgetUnits(Math.round(projManpowerTO.getRevisedQty()));
		resourceAssignmentDataTO.setActualUnits(Math.round(projManpowerTO.getActualQty()));
		resourceAssignmentDataTO.setCalendar(projGeneralsResp.getProjGeneralMstrTO().getCalenderTO().getCode());
		
		return resourceAssignmentDataTO;
	}
	
	public static ResourceAssignmentDataTO fromProjectPlantsDtlTO(ProjectPlantsDtlTO projectPlantsDtlTO, ProjGeneralsResp projGeneralsResp) {
		ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();
		resourceAssignmentDataTO.setValid(true);
		resourceAssignmentDataTO.setReferenceId(projectPlantsDtlTO.getPlantClassId());
		resourceAssignmentDataTO.setCode(projectPlantsDtlTO.getPlantClassTO().getCode());
		resourceAssignmentDataTO.setName(projectPlantsDtlTO.getPlantClassTO().getName());
		resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_PLANT);
		resourceAssignmentDataTO.setType("nonlabor");
		resourceAssignmentDataTO.setActivityCode("ACT-" + projectPlantsDtlTO.getPlantClassTO().getCode() + "-" + projectPlantsDtlTO.getId());
		resourceAssignmentDataTO.setActivityName("ACT-" + projectPlantsDtlTO.getPlantClassTO().getName());
		resourceAssignmentDataTO.setWbsCode("WBS-" + projectPlantsDtlTO.getPlantClassTO().getCode() + "-" + projectPlantsDtlTO.getId());
		resourceAssignmentDataTO.setWbsName("WBS-" + projectPlantsDtlTO.getPlantClassTO().getName());
		resourceAssignmentDataTO.setStartDate(CommonUtil.convertStringToDate(projectPlantsDtlTO.getStartDate()));
		resourceAssignmentDataTO.setFinishDate(CommonUtil.convertStringToDate(projectPlantsDtlTO.getFinishDate()));
		resourceAssignmentDataTO.setUnitOfMeasure(projectPlantsDtlTO.getMeasureUnitTO().getName());
		resourceAssignmentDataTO.setBudgetUnits((projectPlantsDtlTO.getOriginalQty().longValue()));
		if (projectPlantsDtlTO.getRevisedQty() != null && projectPlantsDtlTO.getRevisedQty().compareTo(BigDecimal.ZERO) > 0)
			resourceAssignmentDataTO.setBudgetUnits(projectPlantsDtlTO.getRevisedQty().longValue());
		resourceAssignmentDataTO.setActualUnits(0L);
		if (projectPlantsDtlTO.getActualQty() != null && projectPlantsDtlTO.getActualQty().compareTo(BigDecimal.ZERO) > 0)
			resourceAssignmentDataTO.setActualUnits(projectPlantsDtlTO.getActualQty().longValue());
		resourceAssignmentDataTO.setCalendar(projGeneralsResp.getProjGeneralMstrTO().getCalenderTO().getCode());
		
		return resourceAssignmentDataTO;
	}
	
	public static ResourceAssignmentDataTO fromProjectMaterialDtlTO(ProjectMaterialDtlTO projectMaterialDtlTO, ProjGeneralsResp projGeneralsResp) {
		ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();
		resourceAssignmentDataTO.setValid(true);
		resourceAssignmentDataTO.setReferenceId(projectMaterialDtlTO.getMaterialClassId());
		resourceAssignmentDataTO.setCode(projectMaterialDtlTO.getCode());
		resourceAssignmentDataTO.setName(projectMaterialDtlTO.getName());
		resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_MATERIAL);
		resourceAssignmentDataTO.setType("material");
		resourceAssignmentDataTO.setActivityCode("ACT-" + projectMaterialDtlTO.getCode() + "-" + projectMaterialDtlTO.getId());
		resourceAssignmentDataTO.setActivityName("ACT-" + projectMaterialDtlTO.getName());
		resourceAssignmentDataTO.setWbsCode("WBS-" + projectMaterialDtlTO.getCode() + "-" + projectMaterialDtlTO.getId());
		resourceAssignmentDataTO.setWbsName("WBS-" + projectMaterialDtlTO.getName());
		resourceAssignmentDataTO.setStartDate(CommonUtil.convertStringToDate(projectMaterialDtlTO.getStartDate()));
		resourceAssignmentDataTO.setFinishDate(CommonUtil.convertStringToDate(projectMaterialDtlTO.getFinishDate()));
		resourceAssignmentDataTO.setUnitOfMeasure(projectMaterialDtlTO.getMaterialClassTO().getMeasureUnitTO().getName());
		resourceAssignmentDataTO.setBudgetUnits(projectMaterialDtlTO.getOriginalQty().longValue());
		if (projectMaterialDtlTO.getRevisedQty() != null && projectMaterialDtlTO.getRevisedQty().compareTo(BigDecimal.ZERO) > 0)
			resourceAssignmentDataTO.setBudgetUnits(projectMaterialDtlTO.getRevisedQty().longValue());
		resourceAssignmentDataTO.setActualUnits(0L);
		if (projectMaterialDtlTO.getActualQty() != null && projectMaterialDtlTO.getActualQty().compareTo(BigDecimal.ZERO) > 0)
			resourceAssignmentDataTO.setActualUnits(projectMaterialDtlTO.getActualQty().longValue());
		resourceAssignmentDataTO.setCalendar(projGeneralsResp.getProjGeneralMstrTO().getCalenderTO().getCode());
		
		return resourceAssignmentDataTO;
	}
	
	public static ResourceAssignmentDataTO fromProjCostStmtDtlTO(ProjCostStmtDtlTO projCostStmtDtlTO, ProjGeneralsResp projGeneralsResp, List<ProjSOWItemEntity> projectSOWItemEntities) {
		ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();
		resourceAssignmentDataTO.setValid(true);
		resourceAssignmentDataTO.setReferenceId(projCostStmtDtlTO.getCostId());
		resourceAssignmentDataTO.setCode(projCostStmtDtlTO.getCode());
		resourceAssignmentDataTO.setName(projCostStmtDtlTO.getName());
		resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_COST);
		resourceAssignmentDataTO.setType("nonlabor");
		resourceAssignmentDataTO.setActivityCode("ACT-" + projCostStmtDtlTO.getCode() + "-" + projCostStmtDtlTO.getCostId());
		resourceAssignmentDataTO.setActivityName("ACT-" + projCostStmtDtlTO.getName());
		resourceAssignmentDataTO.setWbsCode("WBS-" + projCostStmtDtlTO.getCode() + "-" + projCostStmtDtlTO.getCostId());
		resourceAssignmentDataTO.setWbsName("WBS-" + projCostStmtDtlTO.getName());
		resourceAssignmentDataTO.setStartDate(CommonUtil.convertStringToDate(projCostStmtDtlTO.getStartDate()));
		resourceAssignmentDataTO.setFinishDate(CommonUtil.convertStringToDate(projCostStmtDtlTO.getFinishDate()));
		resourceAssignmentDataTO.setUnitOfMeasure(projGeneralsResp.getProjGeneralMstrTO().getCurrency());
		resourceAssignmentDataTO.setBudgetUnits(0L);
		if (projCostStmtDtlTO.getOriginalCostBudget() != null && projCostStmtDtlTO.getOriginalCostBudget().getTotal() != null && projCostStmtDtlTO.getOriginalCostBudget().getTotal().compareTo(BigDecimal.ZERO) > 0)
			resourceAssignmentDataTO.setBudgetUnits(projCostStmtDtlTO.getOriginalCostBudget().getTotal().longValue());
		if (projCostStmtDtlTO.getRevisedCostBudget() != null && projCostStmtDtlTO.getRevisedCostBudget().getTotal() != null && projCostStmtDtlTO.getRevisedCostBudget().getTotal().compareTo(BigDecimal.ZERO) > 0)
			resourceAssignmentDataTO.setBudgetUnits(projCostStmtDtlTO.getRevisedCostBudget().getTotal().longValue());
		resourceAssignmentDataTO.setActualUnits(0L);
		if (projCostStmtDtlTO.getActualCostBudget() != null && projCostStmtDtlTO.getActualCostBudget().getTotal() != null && projCostStmtDtlTO.getActualCostBudget().getTotal().compareTo(BigDecimal.ZERO) > 0)
			resourceAssignmentDataTO.setActualUnits(projCostStmtDtlTO.getActualCostBudget().getTotal().longValue());
		resourceAssignmentDataTO.setCalendar(projGeneralsResp.getProjGeneralMstrTO().getCalenderTO().getCode());
		
		if (projCostStmtDtlTO.getCostId() == null) {
			resourceAssignmentDataTO.setValid(false);
			resourceAssignmentDataTO.addValidationMessage("Please add to Scope of Work.");
		}
		
		return resourceAssignmentDataTO;
	}
	
	public static ResourceAssignmentDataTO fromProjSORItemEntityCopy(ProjSORItemEntity projSORItemEntityCopy, ProjGeneralsResp projGeneralsResp, List<ProjSOWItemEntity> projectSOWItemEntities) {
		ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();
		resourceAssignmentDataTO.setValid(true);
		resourceAssignmentDataTO.setReferenceId(projSORItemEntityCopy.getId());
		resourceAssignmentDataTO.setCode(projSORItemEntityCopy.getCode());
		resourceAssignmentDataTO.setName(projSORItemEntityCopy.getName());
		resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_SOR);
		resourceAssignmentDataTO.setType("nonlabor");
		resourceAssignmentDataTO.setActivityCode("ACT-" + projSORItemEntityCopy.getCode() + "-" + projSORItemEntityCopy.getId());
		resourceAssignmentDataTO.setActivityName("ACT-" + projSORItemEntityCopy.getName());
		resourceAssignmentDataTO.setWbsCode("WBS-" + projSORItemEntityCopy.getCode() + "-" + projSORItemEntityCopy.getId());
		resourceAssignmentDataTO.setWbsName("WBS-" + projSORItemEntityCopy.getName());
		Date startDate = null, finishDate = null;
		String uom = null;
		Long budgetUnits = 0L, actualUnits = 0L;
		for (ProjSOWItemEntity sow : projectSOWItemEntities) {
			if (sow.getProjSORItemEntity() != null && sow.getProjSORItemEntity().getId().equals(projSORItemEntityCopy.getId())) {
				if (startDate == null) startDate = sow.getStartDate();
				if (finishDate == null) finishDate = sow.getFinishDate();
				if (sow.getStartDate() != null && startDate.after(sow.getStartDate())) startDate = sow.getStartDate();
				if (sow.getFinishDate() != null && finishDate.before(sow.getFinishDate())) finishDate = sow.getFinishDate();
				uom = sow.getProjSOEItemEntity().getMeasurmentMstrEntity().getName();
				budgetUnits += sow.getProjSOEItemEntity().getQuantity().longValue();
				if (sow.getActualQty() != null) actualUnits += sow.getActualQty().longValue();
			}
		}
		resourceAssignmentDataTO.setStartDate(startDate);
		resourceAssignmentDataTO.setFinishDate(finishDate);
		resourceAssignmentDataTO.setUnitOfMeasure(uom);
		resourceAssignmentDataTO.setBudgetUnits(budgetUnits);
		resourceAssignmentDataTO.setActualUnits(actualUnits);
		resourceAssignmentDataTO.setCalendar(projGeneralsResp.getProjGeneralMstrTO().getCalenderTO().getCode());
		resourceAssignmentDataTO.setCurve(projGeneralsResp.getProjGeneralMstrTO().getResourceCurveTO().getCurveType());
		
		if (startDate == null) {
			resourceAssignmentDataTO.setValid(false);
			resourceAssignmentDataTO.addValidationMessage("Start Date is invalid. Please add to Scope of Work.");
		}
		if (finishDate == null) {
			resourceAssignmentDataTO.setValid(false);
			resourceAssignmentDataTO.addValidationMessage("Finish Date is invalid. Please add to Scope of Work.");
		}
		
		return resourceAssignmentDataTO;
	}
	
	public static ResourceAssignmentDataTO fromProjSOWItemEntityCopy(ProjSOWItemEntity projSOWItemEntityCopy, ProjGeneralsResp projGeneralsResp) {
		ResourceAssignmentDataTO resourceAssignmentDataTO = new ResourceAssignmentDataTO();
		resourceAssignmentDataTO.setValid(true);
		resourceAssignmentDataTO.setReferenceId(projSOWItemEntityCopy.getTangibleClassificationEntity().getId());
		resourceAssignmentDataTO.setCode(projSOWItemEntityCopy.getTangibleClassificationEntity().getCode());
		resourceAssignmentDataTO.setName(projSOWItemEntityCopy.getTangibleClassificationEntity().getName());
		resourceAssignmentDataTO.setReferenceType(ResourceAssignmentDataHandler.POT_TANGIBLE);
		resourceAssignmentDataTO.setType("material");
		resourceAssignmentDataTO.setActivityCode("ACT-" + projSOWItemEntityCopy.getTangibleClassificationEntity().getCode() + "-" + projSOWItemEntityCopy.getTangibleClassificationEntity().getId());
		resourceAssignmentDataTO.setActivityName("ACT-" + projSOWItemEntityCopy.getTangibleClassificationEntity().getName());
		resourceAssignmentDataTO.setWbsCode("WBS-" + projSOWItemEntityCopy.getTangibleClassificationEntity().getCode() + "-" + projSOWItemEntityCopy.getTangibleClassificationEntity().getId());
		resourceAssignmentDataTO.setWbsName("WBS-" + projSOWItemEntityCopy.getTangibleClassificationEntity().getName());
		resourceAssignmentDataTO.setStartDate(projSOWItemEntityCopy.getStartDate());
		resourceAssignmentDataTO.setFinishDate(projSOWItemEntityCopy.getFinishDate());
		resourceAssignmentDataTO.setUnitOfMeasure(projSOWItemEntityCopy.getTangibleClassificationEntity().getMeasurmentMstrEntity().getName());
		resourceAssignmentDataTO.setBudgetUnits(0L);
		if (projSOWItemEntityCopy.getProjSOEItemEntity().getQuantity() != null && projSOWItemEntityCopy.getProjSOEItemEntity().getQuantity().compareTo(BigDecimal.ZERO) > 0)
			resourceAssignmentDataTO.setBudgetUnits(projSOWItemEntityCopy.getProjSOEItemEntity().getQuantity().longValue());
		if (projSOWItemEntityCopy.getRevisedQty() != null && projSOWItemEntityCopy.getRevisedQty().compareTo(BigDecimal.ZERO) > 0)
			resourceAssignmentDataTO.setBudgetUnits(projSOWItemEntityCopy.getRevisedQty().longValue());
		resourceAssignmentDataTO.setActualUnits(0L);
		if (projSOWItemEntityCopy.getActualQty() != null && projSOWItemEntityCopy.getActualQty().compareTo(BigDecimal.ZERO) > 0)
			resourceAssignmentDataTO.setActualUnits(projSOWItemEntityCopy.getActualQty().longValue());
		resourceAssignmentDataTO.setCalendar(projGeneralsResp.getProjGeneralMstrTO().getCalenderTO().getCode());
		resourceAssignmentDataTO.setCurve(projGeneralsResp.getProjGeneralMstrTO().getResourceCurveTO().getCurveType());
		
		if (projSOWItemEntityCopy.getStartDate() == null) {
			resourceAssignmentDataTO.setValid(false);
			resourceAssignmentDataTO.addValidationMessage("Start Date is invalid. Please add to Scope of Work.");
		}
		if (projSOWItemEntityCopy.getFinishDate() == null) {
			resourceAssignmentDataTO.setValid(false);
			resourceAssignmentDataTO.addValidationMessage("Finish Date is invalid. Please add to Scope of Work.");
		}
		
		return resourceAssignmentDataTO;
	}
	
	public static ProjScheduleBaseLineEntity toProjScheduleBaseLineEntity(ScheduleActivityDataSetTO scheduleActivityDataSetTO, ResourceAssignmentDataTO resourceAssignmentDataTO, EPSProjRepository epsProjRepository,  String itemType) {
		ProjScheduleBaseLineEntity projScheduleBaseLineEntity = new ProjScheduleBaseLineEntity();
		projScheduleBaseLineEntity.setDate(new Date());
		projScheduleBaseLineEntity.setName(scheduleActivityDataSetTO.getDatasetName());
		projScheduleBaseLineEntity.setScheduleItemType(itemType);
		projScheduleBaseLineEntity.setScheduleType("Planned");
		projScheduleBaseLineEntity.setStatus(1);
		projScheduleBaseLineEntity.setTimeScale("Weekly");
		projScheduleBaseLineEntity.setProjId(epsProjRepository.findOne(scheduleActivityDataSetTO.getProjId()));
		return projScheduleBaseLineEntity;
	}
	
	public static ProjScheduleManPowerEntity toProjScheduleManPowerEntity(ResourceAssignmentDataTO resourceAssignmentDataTO, ProjManpowerRepository projManpowerRepository, ResourceCurveRepository resourceCurveRepository, ProjScheduleBaseLineEntity projScheduleBaseLineEntity, Map<Long, LabelKeyTO> actualHrs, EmpClassRepository empClassRepository, LoginRepository loginRepository, Long projectId) {
		EmpClassMstrEntity empClassMstrEntity = empClassRepository.findBy(loginRepository.findOne(AppUserUtils.getUserId()).getClientRegEntity().getClientId(), resourceAssignmentDataTO.getCode(), 1);
		ProjManpowerEntity projManpowerEntity = projManpowerRepository.findBy(projectId, empClassMstrEntity.getId());
		ProjScheduleManPowerEntity projScheduleManPowerEntity = new ProjScheduleManPowerEntity();
		BigDecimal actual = new BigDecimal(0);
		if (actualHrs.get(projManpowerEntity.getEmpClassMstrEntity().getId()) != null) actual = BigDecimal.valueOf(new Double(actualHrs.get(projManpowerEntity.getEmpClassMstrEntity().getId()).getCode()));
		projScheduleManPowerEntity.setActualQty(actual);
		projScheduleManPowerEntity.setOriginalQty(BigDecimal.valueOf(projManpowerEntity.getOriginalQty()));
		projScheduleManPowerEntity.setRemainingQty(projScheduleManPowerEntity.getOriginalQty().subtract(projScheduleManPowerEntity.getActualQty()));
		if (projManpowerEntity.getRevisedQty() != null) projScheduleManPowerEntity.setRemainingQty(BigDecimal.valueOf(projManpowerEntity.getRevisedQty()).subtract(projScheduleManPowerEntity.getActualQty()));
		if (projManpowerEntity.getRevisedQty() != null) projScheduleManPowerEntity.setRevisedQty(BigDecimal.valueOf(projManpowerEntity.getRevisedQty()));
		projScheduleManPowerEntity.setEstimateComplete(projScheduleManPowerEntity.getOriginalQty().subtract(projScheduleManPowerEntity.getActualQty()));
		if (projScheduleManPowerEntity.getRevisedQty() != null) projScheduleManPowerEntity.setEstimateComplete(projScheduleManPowerEntity.getRevisedQty().subtract(projScheduleManPowerEntity.getActualQty()));
	    projScheduleManPowerEntity.setStartDate(projManpowerEntity.getStartDate());
	    projScheduleManPowerEntity.setFinishDate(projManpowerEntity.getFinishDate());
		projScheduleManPowerEntity.setEmpClassId(projManpowerEntity.getEmpClassMstrEntity());
		projScheduleManPowerEntity.setResourceCurveId(resourceCurveRepository.findBy(resourceAssignmentDataTO.getCurve()));
		projScheduleManPowerEntity.setStatus(1);
		projScheduleManPowerEntity.setEstimateCompletion(projScheduleManPowerEntity.getActualQty().add(projScheduleManPowerEntity.getEstimateComplete()));
		projScheduleManPowerEntity.setProjScheduleBaseLineEntity(projScheduleBaseLineEntity);
		return projScheduleManPowerEntity;
	}
	
	public static ProjSchedulePlantEntity toProjSchedulePlantEntity(ResourceAssignmentDataTO resourceAssignmentDataTO, ProjectPlantsRepository projectPlantsRepository, ResourceCurveRepository resourceCurveRepository, ProjScheduleBaseLineEntity projScheduleBaseLineEntity, Map<Long, LabelKeyTO> plantActuals, PlantClassRepository plantClassRepository, LoginRepository loginRepository, Long projectId) {
		PlantMstrEntity plantMstrEntity = plantClassRepository.findBy(loginRepository.findOne(AppUserUtils.getUserId()).getClientRegEntity().getClientId(), resourceAssignmentDataTO.getCode(),1);
		ProjectPlantsDtlEntity projectPlantsDtlEntity = projectPlantsRepository.findBy(projectId, plantMstrEntity.getId());
		ProjSchedulePlantEntity projSchedulePlantEntity = new ProjSchedulePlantEntity();
		BigDecimal actual = new BigDecimal(0);
		if (plantActuals.get(projectPlantsDtlEntity.getPlantMstrEntity().getId()) != null) actual = new BigDecimal(plantActuals.get(projectPlantsDtlEntity.getPlantMstrEntity().getId()).getCode());
		projSchedulePlantEntity.setActualQty(actual);
		projSchedulePlantEntity.setEstimateComplete(projectPlantsDtlEntity.getOriginalQty().subtract(projSchedulePlantEntity.getActualQty()));
		if (projectPlantsDtlEntity.getRevisedQty() != null) projSchedulePlantEntity.setEstimateComplete(projectPlantsDtlEntity.getRevisedQty().subtract(projSchedulePlantEntity.getActualQty()));
		projSchedulePlantEntity.setEstimateCompletion(projSchedulePlantEntity.getActualQty().add(projSchedulePlantEntity.getEstimateComplete()));
		projSchedulePlantEntity.setFinishDate(projectPlantsDtlEntity.getFinishDate());
		projSchedulePlantEntity.setOriginalQty(projectPlantsDtlEntity.getOriginalQty());
		projSchedulePlantEntity.setRemainingQty(projSchedulePlantEntity.getOriginalQty().subtract(projSchedulePlantEntity.getActualQty()));
		if (projectPlantsDtlEntity.getRevisedQty() != null) projSchedulePlantEntity.setRemainingQty(projectPlantsDtlEntity.getRevisedQty().subtract(projSchedulePlantEntity.getActualQty()));
		projSchedulePlantEntity.setRevisedQty(projectPlantsDtlEntity.getRevisedQty());
		projSchedulePlantEntity.setStartDate(projectPlantsDtlEntity.getStartDate());
		projSchedulePlantEntity.setStatus(projectPlantsDtlEntity.getStatus());
		projSchedulePlantEntity.setPlantClassId(projectPlantsDtlEntity.getPlantMstrEntity());
		projSchedulePlantEntity.setProjScheduleBaseLineEntity(projScheduleBaseLineEntity);
		projSchedulePlantEntity.setResourceCurveId(resourceCurveRepository.findBy(resourceAssignmentDataTO.getCurve()));
		return projSchedulePlantEntity;
	}
	
	public static ProjScheduleMaterialEntity toProjScheduleMaterialEntity(ResourceAssignmentDataTO resourceAssignmentDataTO, ProjectMaterialRepository projectMaterialRepository, ResourceCurveRepository resourceCurveRepository, ProjScheduleBaseLineEntity projScheduleBaseLineEntity, Map<Long, LabelKeyTO> materialActuals, MaterialClassRepository materialClassRepository, LoginRepository loginRepository, Long projectId) {
		System.out.println(loginRepository.findOne(AppUserUtils.getUserId()).getClientRegEntity().getClientId()+"->"+resourceAssignmentDataTO.getCode());
		MaterialClassMstrEntity materialClassMstrEntity = materialClassRepository.findBy(loginRepository.findOne(AppUserUtils.getUserId()).getClientRegEntity().getClientId(), resourceAssignmentDataTO.getCode(), 1);
		ProjectMaterialBudgetEntity projectMaterialBudgetEntity = projectMaterialRepository.findBy(projectId, materialClassMstrEntity.getId());
		ProjScheduleMaterialEntity projScheduleMaterialEntity = new ProjScheduleMaterialEntity();
		BigDecimal actual = new BigDecimal(0);
		if (materialActuals.get(projectMaterialBudgetEntity.getMaterialClassMstrEntity().getId()) != null) actual = new BigDecimal(materialActuals.get(projectMaterialBudgetEntity.getMaterialClassMstrEntity().getId()).getCode());
		projScheduleMaterialEntity.setActualQty(actual);
		projScheduleMaterialEntity.setEstimateComplete(projectMaterialBudgetEntity.getOriginalQty().subtract(projScheduleMaterialEntity.getActualQty()));
		if (projectMaterialBudgetEntity.getRevisedQty() != null) projScheduleMaterialEntity.setEstimateComplete(projectMaterialBudgetEntity.getRevisedQty().subtract(projScheduleMaterialEntity.getActualQty()));
		projScheduleMaterialEntity.setEstimateCompletion(projScheduleMaterialEntity.getActualQty().add(projScheduleMaterialEntity.getEstimateComplete()));
		projScheduleMaterialEntity.setFinishDate(projectMaterialBudgetEntity.getFinishDate());
		projScheduleMaterialEntity.setOriginalQty(projectMaterialBudgetEntity.getOriginalQty());
		projScheduleMaterialEntity.setRemainingQty(projScheduleMaterialEntity.getOriginalQty().subtract(projScheduleMaterialEntity.getActualQty()));
		if (projectMaterialBudgetEntity.getRevisedQty() != null) projScheduleMaterialEntity.setRemainingQty(projectMaterialBudgetEntity.getRevisedQty().subtract(projScheduleMaterialEntity.getActualQty()));
		projScheduleMaterialEntity.setRevisedQty(projectMaterialBudgetEntity.getRevisedQty());
		projScheduleMaterialEntity.setStartDate(projectMaterialBudgetEntity.getStartDate());
		projScheduleMaterialEntity.setStatus(projectMaterialBudgetEntity.getStatus());
		projScheduleMaterialEntity.setMaterialClassId(projectMaterialBudgetEntity.getMaterialClassMstrEntity());
		projScheduleMaterialEntity.setProjScheduleBaseLineEntity(projScheduleBaseLineEntity);
		projScheduleMaterialEntity.setResourceCurveId(resourceCurveRepository.findBy(resourceAssignmentDataTO.getCurve()));
		return projScheduleMaterialEntity;
	}
	
	public static ProjScheduleCostCodeEntity toProjScheduleCostCodeEntity(ResourceAssignmentDataTO resourceAssignmentDataTO, ProjCostItemRepositoryCopy projCostItemRepository, ProjCostStmtDtlTO projCostStmtDtlTO, ResourceCurveRepository resourceCurveRepository, ProjScheduleBaseLineEntity projScheduleBaseLineEntity, Map<Long, CostActualHoursTO> costActuals) {
		ProjCostItemEntity projCostItemEntity = projCostItemRepository.findOne(projCostStmtDtlTO.getCostId());
		ProjScheduleCostCodeEntity projScheduleCostCodeEntity = new ProjScheduleCostCodeEntity();
		if (projCostStmtDtlTO.getActualCostBudget() != null && projCostStmtDtlTO.getActualCostBudget().getTotal() != null && projCostStmtDtlTO.getActualCostBudget().getTotal().compareTo(BigDecimal.ZERO) > 0)
			projScheduleCostCodeEntity.setActualQty(projCostStmtDtlTO.getActualCostBudget().getTotal());
		else
			projScheduleCostCodeEntity.setActualQty(BigDecimal.valueOf(0));
		projScheduleCostCodeEntity.setFinishDate(CommonUtil.convertStringToDate(projCostStmtDtlTO.getFinishDate()));
		if (projCostStmtDtlTO.getOriginalCostBudget() != null && projCostStmtDtlTO.getOriginalCostBudget().getTotal() != null && projCostStmtDtlTO.getOriginalCostBudget().getTotal().compareTo(BigDecimal.ZERO) > 0)
			projScheduleCostCodeEntity.setOriginalQty(projCostStmtDtlTO.getOriginalCostBudget().getTotal());
		else
			projScheduleCostCodeEntity.setOriginalQty(BigDecimal.valueOf(0));
		projScheduleCostCodeEntity.setRemainingQty(projScheduleCostCodeEntity.getOriginalQty().subtract(projScheduleCostCodeEntity.getActualQty()));
		if (projCostStmtDtlTO.getRevisedCostBudget() != null && projCostStmtDtlTO.getRevisedCostBudget().getTotal() != null && projCostStmtDtlTO.getRevisedCostBudget().getTotal().compareTo(BigDecimal.ZERO) > 0)
			projScheduleCostCodeEntity.setRevisedQty(projCostStmtDtlTO.getRevisedCostBudget().getTotal());
		projScheduleCostCodeEntity.setStartDate(CommonUtil.convertStringToDate(projCostStmtDtlTO.getStartDate()));
		projScheduleCostCodeEntity.setStatus(projCostStmtDtlTO.getStatus());
		projScheduleCostCodeEntity.setCostCodeId(projCostItemEntity);
		projScheduleCostCodeEntity.setProjScheduleBaseLineEntity(projScheduleBaseLineEntity);
		projScheduleCostCodeEntity.setResourceCurveId(resourceCurveRepository.findBy(resourceAssignmentDataTO.getCurve()));
		projScheduleCostCodeEntity.setEstimateComplete(projScheduleCostCodeEntity.getOriginalQty().subtract(projScheduleCostCodeEntity.getActualQty()));
		projScheduleCostCodeEntity.setEstimateCompletion(projScheduleCostCodeEntity.getActualQty().add(projScheduleCostCodeEntity.getEstimateComplete()));
		return projScheduleCostCodeEntity;
	}
	
	public static ProjScheduleSOWEntity toProjScheduleSOWEntity(ResourceAssignmentDataTO resourceAssignmentDataTO, ResourceCurveRepository resourceCurveRepository, ProjScheduleBaseLineEntity projScheduleBaseLineEntity) {
		ProjScheduleSOWEntity projScheduleSOWEntity = new ProjScheduleSOWEntity();
		//SHSB_ACTUAL_QTY
		//SHSB_EST_COMPLETE_QTY
		//SHSB_EST_COMPLETION_QTY
		//SHSB_FINISH_DATE
		//SHSB_ORIGINAL_QTY
		//SHSB_REMAINING_QTY
		//SHSB_REVISED_QTY
		//SHSB_START_DATE
		//SHSB_STATUS
		//SHSB_SCHB_ID
		//SHSB_RC_ID
		//SHSB_SOW_ID
		return projScheduleSOWEntity;
	}
}
