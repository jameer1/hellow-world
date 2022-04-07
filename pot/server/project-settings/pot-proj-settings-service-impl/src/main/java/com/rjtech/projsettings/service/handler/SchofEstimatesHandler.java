package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.repository.SchofEstimatesRepository;
import com.rjtech.projsettings.req.SchofEstimatesSaveReq;
import com.rjtech.projsettings.dto.SchofEstimatesApprTO;
import com.rjtech.projsettings.model.SchofEstimateNormalTimeEntity;

public class SchofEstimatesHandler {
	
	public static SchofEstimateNormalTimeEntity convertPOJOToEntity(SchofEstimatesApprTO schofEstimatesApprTO, 
			EPSProjRepository epsProjRepository) {
		
		SchofEstimateNormalTimeEntity entity = new SchofEstimateNormalTimeEntity();
		
		if(CommonUtil.isNonBlankLong(schofEstimatesApprTO.getId())) {
			entity.setId(schofEstimatesApprTO.getId());
		}
		
		ProjMstrEntity projMstrEntity = epsProjRepository.findOne(schofEstimatesApprTO.getProjId());
		
		if(null != projMstrEntity) {
			entity.setProjId(projMstrEntity);
		}
		if(CommonUtil.isNonBlankInteger(schofEstimatesApprTO.getCutOffDays())) {
			entity.setCutOffDays(schofEstimatesApprTO.getCutOffDays());
		}else {
			entity.setCutOffDays(0);
		}
	//	if(CommonUtil.isNonBlankInteger(schofEstimatesApprTO.getCutOffTime())) {
	//		entity.setCutOffTime(schofEstimatesApprTO.getCutOffTime());
	//	}else {
	//		entity.setCutOffTime(0);
	//	}
		entity.setCutOffTime(schofEstimatesApprTO.getCutOffTime());
		entity.setApprType(schofEstimatesApprTO.getApprType());
		entity.setApprTypeId(schofEstimatesApprTO.getApprTypeId());
		entity.setDefaultStatus(schofEstimatesApprTO.getDefaultStatus());
		if(CommonUtil.isNonBlankInteger(schofEstimatesApprTO.getCutOffHours())) {
			entity.setCutOffHours(schofEstimatesApprTO.getCutOffHours());
		}else {
			entity.setCutOffHours(0);
		}
		if(CommonUtil.isNonBlankInteger(schofEstimatesApprTO.getCutOffMinutes())) {
			entity.setCutOffMinutes(schofEstimatesApprTO.getCutOffMinutes());
		}else {
			entity.setCutOffMinutes(0);
		}
		entity.setStatus(schofEstimatesApprTO.getStatus());
		return entity;
	}
	
	public static SchofEstimatesApprTO convertEntityToPOJO(SchofEstimateNormalTimeEntity entity) {
		SchofEstimatesApprTO schofEstimatesApprTOs = new SchofEstimatesApprTO();
		
		ProjMstrEntity projMstrEntity = entity.getProjId();
		schofEstimatesApprTOs.setId(entity.getId());
		if(null != projMstrEntity) {
			schofEstimatesApprTOs.setProjId(projMstrEntity.getProjectId());
		}
		schofEstimatesApprTOs.setCutOffDays(entity.getCutOffDays());
		schofEstimatesApprTOs.setCutOffTime(entity.getCutOffTime());
		schofEstimatesApprTOs.setApprType(entity.getApprType());
		schofEstimatesApprTOs.setApprTypeId(entity.getApprTypeId());
		schofEstimatesApprTOs.setDefaultStatus(entity.getDefaultStatus());
		schofEstimatesApprTOs.setCutOffHours(entity.getCutOffHours());
		schofEstimatesApprTOs.setCutOffMinutes(entity.getCutOffMinutes());
		
		schofEstimatesApprTOs.setStatus(entity.getStatus());
		
		return schofEstimatesApprTOs;
	}
	
	public static List<SchofEstimateNormalTimeEntity> convertPOJOToEntity(List<SchofEstimatesApprTO> schofEstimatesTos, EPSProjRepository epsProjRepository){
		List<SchofEstimateNormalTimeEntity> schofEstimates = new ArrayList<SchofEstimateNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		SchofEstimateNormalTimeEntity entity = null;
		for(SchofEstimatesApprTO schofEstimatesApptTO : schofEstimatesTos) {
			entity = convertPOJOToEntity(schofEstimatesApptTO, epsProjRepository);
			schofEstimates.add(entity);
		}
		return schofEstimates;
	}

}