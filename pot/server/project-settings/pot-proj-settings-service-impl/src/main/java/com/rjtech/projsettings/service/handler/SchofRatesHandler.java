package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.repository.SchofRatesRepository;
import com.rjtech.projsettings.req.SchofRatesSaveReq;
import com.rjtech.projsettings.dto.SchofRatesApprTO;
import com.rjtech.projsettings.model.SchofRatesNormalTimeEntity;

public class SchofRatesHandler {
	
	public static SchofRatesNormalTimeEntity convertPOJOToEntity(SchofRatesApprTO schofRatesApprTOs, 
			EPSProjRepository epsProjRepository) {
		
		SchofRatesNormalTimeEntity entity = new SchofRatesNormalTimeEntity();
		
		if(CommonUtil.isNonBlankLong(schofRatesApprTOs.getId())) {
			entity.setId(schofRatesApprTOs.getId());
		}
		
		ProjMstrEntity projMstrEntity = epsProjRepository.findOne(schofRatesApprTOs.getProjId());
		
		if(null != projMstrEntity) {
			entity.setProjId(projMstrEntity);
		}
		if(CommonUtil.isNonBlankInteger(schofRatesApprTOs.getCutOffDays())) {
			entity.setCutOffDays(schofRatesApprTOs.getCutOffDays());
		}else {
			entity.setCutOffDays(0);
		}
	//	if(CommonUtil.isNonBlankInteger(schofEstimatesApprTO.getCutOffTime())) {
	//		entity.setCutOffTime(schofEstimatesApprTO.getCutOffTime());
	//	}else {
	//		entity.setCutOffTime(0);
	//	}
		entity.setCutOffTime(schofRatesApprTOs.getCutOffTime());
		entity.setApprType(schofRatesApprTOs.getApprType());
		entity.setApprTypeId(schofRatesApprTOs.getApprTypeId());
		entity.setDefaultStatus(schofRatesApprTOs.getDefaultStatus());
		if(CommonUtil.isNonBlankInteger(schofRatesApprTOs.getCutOffHours())) {
			entity.setCutOffHours(schofRatesApprTOs.getCutOffHours());
		}else {
			entity.setCutOffHours(0);
		}
		if(CommonUtil.isNonBlankInteger(schofRatesApprTOs.getCutOffMinutes())) {
			entity.setCutOffMinutes(schofRatesApprTOs.getCutOffMinutes());
		}else {
			entity.setCutOffMinutes(0);
		}
		entity.setStatus(schofRatesApprTOs.getStatus());
		return entity;
	}
	
	public static SchofRatesApprTO convertEntityToPOJO(SchofRatesNormalTimeEntity entity) {
		SchofRatesApprTO schofRatesApprTOs = new SchofRatesApprTO();
		
		ProjMstrEntity projMstrEntity = entity.getProjId();
		schofRatesApprTOs.setId(entity.getId());
		if(null != projMstrEntity) {
			schofRatesApprTOs.setProjId(projMstrEntity.getProjectId());
		}
		schofRatesApprTOs.setCutOffDays(entity.getCutOffDays());
		schofRatesApprTOs.setCutOffTime(entity.getCutOffTime());
		schofRatesApprTOs.setApprType(entity.getApprType());
		schofRatesApprTOs.setApprTypeId(entity.getApprTypeId());
		schofRatesApprTOs.setDefaultStatus(entity.getDefaultStatus());
		schofRatesApprTOs.setCutOffHours(entity.getCutOffHours());
		schofRatesApprTOs.setCutOffMinutes(entity.getCutOffMinutes());
		
		schofRatesApprTOs.setStatus(entity.getStatus());
		
		return schofRatesApprTOs;
	}
	
	public static List<SchofRatesNormalTimeEntity> convertPOJOToEntity(List<SchofRatesApprTO> schofRatesTos, EPSProjRepository epsProjRepository){
		List<SchofRatesNormalTimeEntity> schofRates = new ArrayList<SchofRatesNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
		SchofRatesNormalTimeEntity entity = null;
		for(SchofRatesApprTO schofRatesApptTO : schofRatesTos) {
			entity = convertPOJOToEntity(schofRatesApptTO, epsProjRepository);
			schofRates.add(entity);
		}
		return schofRates;
	}

}