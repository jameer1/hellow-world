package com.rjtech.notification.service.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.notification.dto.ProcurementNotificationsTO;
import com.rjtech.notification.dto.ReqApprNotificationTO;
//import com.rjtech.notification.model.ProcurementNotificationsEntity;
import com.rjtech.notification.model.ProcurementNotificationsEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class ProcurementNotificationsHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ProcurementNotificationsHandler.class);

    public static ProcurementNotificationsEntityCopy convertPOJOToEntity(
            ProcurementNotificationsTO procurementNotificationsTO, LoginRepository loginRepository,
            EPSProjRepository epsProjRepository) {
log.info("ProcurementNotificationsHandler of Procurement Module");
        ProcurementNotificationsEntityCopy procurementNotificationsEntity = new ProcurementNotificationsEntityCopy();
        log.info("procurementNotificationsTO.getId() " + procurementNotificationsTO.getId());
        log.info("procurementNotificationsTO.getCode() " + procurementNotificationsTO.getCode());
        log.info("procurementNotificationsTO.getDate() " + procurementNotificationsTO.getDate());
        log.info("procurementNotificationsTO.getModuleCode() " + procurementNotificationsTO.getModuleCode());
        log.info("procurementNotificationsTO.getNotifyRefId() " + procurementNotificationsTO.getNotifyRefId());
        log.info("procurementNotificationsTO.getProjId() " + procurementNotificationsTO.getProjId());
        log.info("procurementNotificationsTO.getToUserId() " + procurementNotificationsTO.getToUserId());
        log.info("procurementNotificationsTO.getProcureStage() " + procurementNotificationsTO.getProcureStage());
        log.info("procurementNotificationsTO.getNotificationStatus() " + procurementNotificationsTO.getNotificationStatus());
        log.info("procurementNotificationsTO.getStatus() " + procurementNotificationsTO.getStatus());
        if (CommonUtil.isNonBlankLong(procurementNotificationsTO.getId())) {
            procurementNotificationsEntity.setId(procurementNotificationsTO.getId());
        }
        log.info("procurementNotificationsTO.getDate() " + procurementNotificationsTO.getDate());
        procurementNotificationsEntity.setCode(procurementNotificationsTO.getCode());
        /*
        if (CommonUtil.isNotBlankStr(procurementNotificationsTO.getDate())) {
            procurementNotificationsEntity
                    .setDate(CommonUtil.convertStringToDate(procurementNotificationsTO.getDate()));
        }
        */
        procurementNotificationsEntity.setDate(new Date());
        procurementNotificationsEntity.setModuleCode(procurementNotificationsTO.getModuleCode());
        procurementNotificationsEntity.setNotifyRefId(procurementNotificationsTO.getNotifyRefId());
        procurementNotificationsEntity.setProjId(epsProjRepository.findOne(procurementNotificationsTO.getProjId()));
        procurementNotificationsEntity.setFromUserId(loginRepository.findOne(AppUserUtils.getUserId()));
        procurementNotificationsEntity.setToUserId(loginRepository.findOne(procurementNotificationsTO.getToUserId()));
        procurementNotificationsEntity.setProcureCatg(procurementNotificationsTO.getProcureCatg());
        procurementNotificationsEntity.setProcureStage(procurementNotificationsTO.getProcureStage());
        procurementNotificationsEntity.setNotificationStatus(procurementNotificationsTO.getNotificationStatus());
        procurementNotificationsEntity.setStatus(procurementNotificationsTO.getStatus());
        //Commented by dinesh // orginal code //
        //procurementNotificationsEntity.setPreContractId(procurementNotificationsTO.getPreContractId());
        procurementNotificationsEntity.setPreContractId(procurementNotificationsTO.getPreContractId());
        procurementNotificationsEntity.setApprStatus(procurementNotificationsTO.getApprStatus());
        procurementNotificationsEntity.setReqComments(procurementNotificationsTO.getReqComments());
        //procurementNotificationsEntity.setPreContractId(preContractEntity);
        return procurementNotificationsEntity;
    }

}
