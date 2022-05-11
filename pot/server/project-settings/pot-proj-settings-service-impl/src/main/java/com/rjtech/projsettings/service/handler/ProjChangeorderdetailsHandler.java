package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjChangeOrderDetailsTO;
import com.rjtech.projsettings.dto.SchofEstimatesApprTO;
import com.rjtech.projsettings.model.ChangeOrderNormalTimeEntity;
import com.rjtech.projsettings.model.SchofEstimateNormalTimeEntity;

public class ProjChangeorderdetailsHandler {



	 public static ProjChangeOrderDetailsTO convertEntityToPOJO(ChangeOrderNormalTimeEntity entity) {
		 ProjChangeOrderDetailsTO  projChangeOrderDetailsTO= new ProjChangeOrderDetailsTO();

		 projChangeOrderDetailsTO.setId(entity.getId());
		 projChangeOrderDetailsTO.setCutOffDays(entity.getCutOffDays());
		 if(null != entity.getCutOffMinutes())
		 {
			 projChangeOrderDetailsTO.setCutOffTime(entity.getCutOffTime());
		 }
		 
		 projChangeOrderDetailsTO.setDefaultStatus(entity.getDefaultStatus());
        ProjMstrEntity projMstrEntity = entity.getProjId();
        if (null != projMstrEntity) {
        	projChangeOrderDetailsTO.setProjId(projMstrEntity.getProjectId());
        }
        projChangeOrderDetailsTO.setCutOffHours(entity.getCutOffHours());
        projChangeOrderDetailsTO.setCutOffMinutes(entity.getCutOffMinutes());
        
        projChangeOrderDetailsTO.setType(entity.getApprType());
        projChangeOrderDetailsTO.setStatus(entity.getStatus());
        projChangeOrderDetailsTO.setApprTypeId(entity.getApprTypeId());
      

        return projChangeOrderDetailsTO;

    }
	 
	 public static ChangeOrderNormalTimeEntity convertPOJOToEntity(ProjChangeOrderDetailsTO projChangeOrderDetailsTO, 
				EPSProjRepository epsProjRepository) {
			
		    ChangeOrderNormalTimeEntity entity = new ChangeOrderNormalTimeEntity();
			
			if(CommonUtil.isNonBlankLong(projChangeOrderDetailsTO.getId())) {
				entity.setId(projChangeOrderDetailsTO.getId());
			}
			
			ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projChangeOrderDetailsTO.getProjId());
			
			if(null != projMstrEntity) {
				entity.setProjId(projMstrEntity);
			}
			if(CommonUtil.isNonBlankInteger(projChangeOrderDetailsTO.getCutOffDays())) {
				entity.setCutOffDays(projChangeOrderDetailsTO.getCutOffDays());
			}else {
				entity.setCutOffDays(0);
			}
		//	if(CommonUtil.isNonBlankInteger(schofEstimatesApprTO.getCutOffTime())) {
		//		entity.setCutOffTime(schofEstimatesApprTO.getCutOffTime());
		//	}else {
		//		entity.setCutOffTime(0);
		//	}
			entity.setCutOffTime(projChangeOrderDetailsTO.getCutOffTime());
			entity.setApprType(projChangeOrderDetailsTO.getType());
		    entity.setApprTypeId(projChangeOrderDetailsTO.getApprTypeId());
		    entity.setIsDefault(projChangeOrderDetailsTO.getDefaultStatus());
			if(CommonUtil.isNonBlankInteger(projChangeOrderDetailsTO.getCutOffHours())) {
				entity.setCutOffHours(projChangeOrderDetailsTO.getCutOffHours());
			}else {
				entity.setCutOffHours(0);
			}
			if(CommonUtil.isNonBlankInteger(projChangeOrderDetailsTO.getCutOffMinutes())) {
				entity.setCutOffMinutes(projChangeOrderDetailsTO.getCutOffMinutes());
			}else {
				entity.setCutOffMinutes(0);
			}
			entity.setStatus(projChangeOrderDetailsTO.getStatus());
			return entity;
		}
	 
	 
	 public static List<ChangeOrderNormalTimeEntity> convertPOJOToEntity(List<ProjChangeOrderDetailsTO> changeOrderDetailsTos, EPSProjRepository epsProjRepository){
			List<ChangeOrderNormalTimeEntity> changeOrderEntity = new ArrayList<ChangeOrderNormalTimeEntity>(
	                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
			ChangeOrderNormalTimeEntity entity = null;
			for(ProjChangeOrderDetailsTO changeOrderDetailsTo : changeOrderDetailsTos) {
				entity = convertPOJOToEntity(changeOrderDetailsTo, epsProjRepository);
				changeOrderEntity.add(entity);
			}
			return changeOrderEntity;
		}
	
}
