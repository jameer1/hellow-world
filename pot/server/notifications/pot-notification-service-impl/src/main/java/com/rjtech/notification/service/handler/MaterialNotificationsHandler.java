package com.rjtech.notification.service.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodes;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.dto.MaterialNotificationsTO;
import com.rjtech.notification.model.MaterialNotificationsEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class MaterialNotificationsHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MaterialNotificationsHandler.class);

    public static MaterialNotificationsTO convertEntityToPOJO(MaterialNotificationsEntity materialNotificationsEntity) {
    	
        MaterialNotificationsTO materialNotificationsTO = new MaterialNotificationsTO();
        materialNotificationsTO.setId(materialNotificationsEntity.getId());
        ProjMstrEntity projMstrEntity = materialNotificationsEntity.getProjId();
        UserMstrEntity userApprMstrEntity = materialNotificationsEntity.getApprUserId();
        UserMstrEntity userReqMstrEntity = materialNotificationsEntity.getReqUserId();
        if (null != projMstrEntity) {
            materialNotificationsTO.setProjId(projMstrEntity.getProjectId());
        }
        
        if (CommonUtil.isNotBlankDate(materialNotificationsEntity.getDate())) {
            materialNotificationsTO.setDate(CommonUtil.convertDateToString(materialNotificationsEntity.getUpdatedOn()));
        }
        
        if (null != userApprMstrEntity) {
            materialNotificationsTO.setApprUserId(userApprMstrEntity.getUserId());
            materialNotificationsTO.setToUserName(materialNotificationsEntity.getApprUserId().getDisplayName());
        }
        
        if (null != userReqMstrEntity) {
            materialNotificationsTO.setReqUserId(userReqMstrEntity.getUserId());
            materialNotificationsTO.setFromUserName(materialNotificationsEntity.getReqUserId().getDisplayName());
        }
        UserMstrEntity req = materialNotificationsEntity.getReqUserId();
        if (req != null) {
            materialNotificationsTO.setReqId(req.getUserId());
        }
        materialNotificationsTO.setNotifyStatus(materialNotificationsEntity.getNotificationStatus());
        materialNotificationsTO.setType(materialNotificationsEntity.getType());
        materialNotificationsTO.setStatus(materialNotificationsEntity.getStatus());
        
        log.info("materialNotificationsEntity.getToProjId().getCode() " + materialNotificationsEntity.getToProjId());
        log.info("materialNotificationsEntity.getId() " + materialNotificationsEntity.getId());
        
        String requestCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                + ModuleCodesPrefixes.APPROVE_PREFIX.getDesc() + "-" + ModuleCodes.MATERIAL_TRANSFER.getDesc() + "-"
                + materialNotificationsEntity.getToProjId().getCode() + "-" + materialNotificationsEntity.getId();
        
        materialNotificationsTO.setRequistionCode(requestCode);
        
        String notifyCode = ModuleCodesPrefixes.MATERIAL_REG_PREFIX.getDesc() + "-"
                + ModuleCodesPrefixes.NOTIFICATION_PREFIX.getDesc() + "-"
                + materialNotificationsEntity.getToProjId().getCode() + "-" + materialNotificationsEntity.getId();
        
        materialNotificationsTO.setCode(notifyCode);
        return materialNotificationsTO;
    }

    public static List<MaterialNotificationsEntity> convertPOJOToEntity(
            List<MaterialNotificationsTO> materialNotificationsTOs, EPSProjRepository epsProjRepository, LoginRepository loginRepository,
            ClientRegRepository clientRegRepository) {
        List<MaterialNotificationsEntity> materialNotificationsEntites = new ArrayList<MaterialNotificationsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (MaterialNotificationsTO materialNotificationsTO : materialNotificationsTOs) {
            materialNotificationsEntites.add(convertPOJOToEntity(materialNotificationsTO, epsProjRepository, loginRepository, clientRegRepository));
        }
        return materialNotificationsEntites;
    }

    public static MaterialNotificationsEntity convertPOJOToEntity(MaterialNotificationsTO materialNotificationsTO,
    		EPSProjRepository epsProjRepository, LoginRepository loginRepository,
            ClientRegRepository clientRegRepository) {

        MaterialNotificationsEntity materialNotificationsEntity = new MaterialNotificationsEntity();
        if (CommonUtil.isNonBlankLong(materialNotificationsTO.getId())) {
            materialNotificationsEntity.setId(materialNotificationsTO.getId());
        } 

        if (CommonUtil.isNotBlankStr(materialNotificationsTO.getDate())) {
            materialNotificationsEntity.setDate(CommonUtil.convertStringToDate(materialNotificationsTO.getDate()));
        } else {
        	materialNotificationsEntity.setDate(new Date());
        }
        
        if (CommonUtil.isNonBlankLong(materialNotificationsTO.getForProjId())) {
        	ProjMstrEntity projMstrEntity = epsProjRepository.findOne(materialNotificationsTO.getForProjId());
        	materialNotificationsEntity.setToProjId(projMstrEntity);
        }
        
        if (CommonUtil.isNonBlankLong(materialNotificationsTO.getForProjId())) {
        	ProjMstrEntity projMstrEntity = epsProjRepository.findOne(materialNotificationsTO.getForProjId());
        	materialNotificationsEntity.setProjId(projMstrEntity);
        }
        
        if (CommonUtil.isNonBlankLong(materialNotificationsTO.getProjId())) {
        	ProjMstrEntity projMstrEntity = epsProjRepository.findOne(materialNotificationsTO.getProjId());
        	materialNotificationsEntity.setFromProjId(projMstrEntity);
        }
        
        if (CommonUtil.isNonBlankLong(materialNotificationsTO.getApprUserId())) {
        	UserMstrEntity userMstrEntity = loginRepository.findOne(materialNotificationsTO.getApprUserId());
        	materialNotificationsEntity.setApprUserId(userMstrEntity);
        }
        
        if (CommonUtil.isNonBlankLong(AppUserUtils.getUserId())) {
        	 UserMstrEntity userMstrEntity = loginRepository.findOne(AppUserUtils.getUserId());
        	 materialNotificationsEntity.setReqUserId(userMstrEntity);
        }
        
        if (CommonUtil.isNonBlankLong(AppUserUtils.getClientId())) {
            ClientRegEntity clientRegEntity = clientRegRepository.findOne(AppUserUtils.getClientId());
            materialNotificationsEntity.setClientId(clientRegEntity);
        }
        
        materialNotificationsEntity.setNotificationStatus(materialNotificationsTO.getNotifyStatus());
        UserMstrEntity req = materialNotificationsEntity.getReqUserId();
        if (req != null)
            materialNotificationsTO.setReqId(req.getUserId());
        
        log.info("materialNotificationsTO.getRequistionCode() " + materialNotificationsTO.getRequistionCode());
        log.info("materialNotificationsTO.getNotificationCode() " + materialNotificationsTO.getNotificationCode());
        materialNotificationsEntity.setStatus(materialNotificationsTO.getStatus());
        materialNotificationsEntity.setRequistionCode(materialNotificationsTO.getRequistionCode());
        materialNotificationsEntity.setIsLatest("Y");
        materialNotificationsEntity.setCode(materialNotificationsTO.getNotificationCode());
        return materialNotificationsEntity;
    }

}
