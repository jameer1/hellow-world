package com.rjtech.notification.service.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.notification.dto.SoeNotificationsTO;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.notification.model.ProjSOEItemEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.notification.model.SoeNotificationsEntity;
public class SoeNotificationsHandler {

    private SoeNotificationsHandler() {
    }

    public static SoeNotificationsTO convertEntityToPOJO(
    		SoeNotificationsEntity soeNotificationsEntity) {
    	SoeNotificationsTO soeNotificationsTO = new SoeNotificationsTO();
        if (null != soeNotificationsEntity) {
        	ProjSOEItemEntity projSOEItemEntity = soeNotificationsEntity.getProjSOEItemEntity();
            if (null != projSOEItemEntity) {
                if (null != projSOEItemEntity.getProjMstrEntity()) {
                	soeNotificationsTO.setProjId(projSOEItemEntity.getProjMstrEntity().getProjectId());
                	soeNotificationsTO.setProjName(projSOEItemEntity.getProjMstrEntity().getProjName());
                	soeNotificationsTO
                            .setEpsName(projSOEItemEntity.getProjMstrEntity().getParentProjectMstrEntity().getProjName());
                	soeNotificationsTO.setId(projSOEItemEntity.getId());
                	soeNotificationsTO
      			  .setNotificationNumber(ModuleCodesPrefixes.SOE_PREFIX.getDesc().concat("-")
      					  .concat(projSOEItemEntity.getProjMstrEntity().getCode()).concat("-")
      					  .concat(String.valueOf(projSOEItemEntity.getId())));
                	soeNotificationsTO.setSoeNumber(ModuleCodesPrefixes.SOE_PREFIX.getDesc().concat("-")
      					  .concat(projSOEItemEntity.getProjMstrEntity().getCode()).concat("-")
      					  .concat(String.valueOf(projSOEItemEntity.getId())));
                }
				
				  if (CommonUtil.isNotBlankDate(soeNotificationsEntity.getDate()))
				  soeNotificationsTO
				  .setDate(CommonUtil.convertDateToString(soeNotificationsEntity.getDate()));
				  ClientRegEntity clientRegEntity = soeNotificationsEntity.getClientId(); if
				  (null != clientRegEntity) {
				  soeNotificationsTO.setClientId(clientRegEntity.getClientId()); } 
				  if (null != projSOEItemEntity.getInternalApproverUserId()) {
				  soeNotificationsTO.setFromUserName(projSOEItemEntity.getInternalApproverUserId(
						  ).getDisplayName()); } 
				  if (null != projSOEItemEntity.getInternalApproverUserId()) {
				  soeNotificationsTO.setToUserName(AppUserUtils.getName()); }
				 
                soeNotificationsTO
                        .setWeeakCommencingDay(CommonUtil.convertDateToString(projSOEItemEntity.getUpdatedOn()));
                soeNotificationsTO.setReqComments(projSOEItemEntity.getInternalApproverComments());
                soeNotificationsTO.setApprComments(projSOEItemEntity.getExternalApproverComments());
            }
            
			
			  
			 
            soeNotificationsTO.setNotificationStatus(soeNotificationsEntity.getNotificationStatus());
            soeNotificationsTO.setNotificationMsg(soeNotificationsEntity.getNotificationMsg());
            soeNotificationsTO.setStatus(soeNotificationsEntity.getStatus());
        }

        return soeNotificationsTO;
    }
      
}
