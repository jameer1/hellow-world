package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.repository.ResourceBudgetRepository;
import com.rjtech.projsettings.req.ResourceBudgetSaveReq;
import com.rjtech.projsettings.dto.ResourceBudgetTO;
import com.rjtech.projsettings.model.ResourceBudgetNormalTimeEntity;

public class ResourceBudgetHandler {
	
	public static ResourceBudgetNormalTimeEntity convertPOJOToEntity(ResourceBudgetTO resourceBudgetTOs, 
			EPSProjRepository epsProjRepository) {
		
		ResourceBudgetNormalTimeEntity entity = new ResourceBudgetNormalTimeEntity();
		
		if(CommonUtil.isNonBlankLong(resourceBudgetTOs.getId())) {
			entity.setId(resourceBudgetTOs.getId());
		}
		
		ProjMstrEntity projMstrEntity = epsProjRepository.findOne(resourceBudgetTOs.getProjId());
		
		if(null != projMstrEntity) {
			entity.setProjId(projMstrEntity);
		}
		if(CommonUtil.isNonBlankInteger(resourceBudgetTOs.getCutOffDays())) {
			entity.setCutOffDays(resourceBudgetTOs.getCutOffDays());
		}else {
			entity.setCutOffDays(0);
		}
	//	if(CommonUtil.isNonBlankInteger(schofEstimatesApprTO.getCutOffTime())) {
	//		entity.setCutOffTime(schofEstimatesApprTO.getCutOffTime());
	//	}else {
	//		entity.setCutOffTime(0);
	//	}
		entity.setCutOffTime(resourceBudgetTOs.getCutOffTime());
		entity.setApprType(resourceBudgetTOs.getApprType());
		entity.setApprTypeId(resourceBudgetTOs.getApprTypeId());
		entity.setDefaultStatus(resourceBudgetTOs.getDefaultStatus());
		if(CommonUtil.isNonBlankInteger(resourceBudgetTOs.getCutOffHours())) {
			entity.setCutOffHours(resourceBudgetTOs.getCutOffHours());
		}else {
			entity.setCutOffHours(0);
		}
		if(CommonUtil.isNonBlankInteger(resourceBudgetTOs.getCutOffMinutes())) {
			entity.setCutOffMinutes(resourceBudgetTOs.getCutOffMinutes());
		}else {
			entity.setCutOffMinutes(0);
		}
		entity.setStatus(resourceBudgetTOs.getStatus());
		return entity; 
	}
	
	public static ResourceBudgetTO convertEntityToPOJO(ResourceBudgetNormalTimeEntity entity) {
		ResourceBudgetTO resourceBudgetTOs = new ResourceBudgetTO();
		
		ProjMstrEntity projMstrEntity = entity.getProjId();
		resourceBudgetTOs.setId(entity.getId());
		if(null != projMstrEntity) {
			resourceBudgetTOs.setProjId(projMstrEntity.getProjectId());
		}
		resourceBudgetTOs.setCutOffDays(entity.getCutOffDays());
		resourceBudgetTOs.setCutOffTime(entity.getCutOffTime());
		resourceBudgetTOs.setApprType(entity.getApprType());
		resourceBudgetTOs.setApprTypeId(entity.getApprTypeId());
		resourceBudgetTOs.setDefaultStatus(entity.getDefaultStatus());
		resourceBudgetTOs.setCutOffHours(entity.getCutOffHours());
		resourceBudgetTOs.setCutOffMinutes(entity.getCutOffMinutes());
		
		resourceBudgetTOs.setStatus(entity.getStatus());
		
		return resourceBudgetTOs;
	}
	
	public static List<ResourceBudgetNormalTimeEntity> convertPOJOToEntity(List<ResourceBudgetTO> resourceBudgetTOs, EPSProjRepository epsProjRepository){
		List<ResourceBudgetNormalTimeEntity> resourceBudgets = new ArrayList<ResourceBudgetNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		ResourceBudgetNormalTimeEntity entity = null;
		for(ResourceBudgetTO resBudget : resourceBudgetTOs) {
			entity = convertPOJOToEntity(resBudget, epsProjRepository);
			resourceBudgets.add(entity);
		}
		return resourceBudgets;
	}

}