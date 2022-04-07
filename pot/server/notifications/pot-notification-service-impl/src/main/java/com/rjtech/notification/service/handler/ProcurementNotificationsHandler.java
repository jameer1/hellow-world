package com.rjtech.notification.service.handler;

import java.util.ArrayList;
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
import com.rjtech.notification.model.ProcurementNotificationsEntity;
import com.rjtech.notification.model.ReqApprNotificationEntity;
//import com.rjtech.notification.model.WorkDairyEntity;
import com.rjtech.notification.model.PreContractEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class ProcurementNotificationsHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ProcurementNotificationsHandler.class);

	
    public static ReqApprNotificationTO convertEntityToPOJO(ReqApprNotificationEntity reqApprNotificationEntity) {
    	log.info("convert entity to pojo for additional time get");
        ReqApprNotificationTO reqApprNotificationTO = new ReqApprNotificationTO();
        reqApprNotificationTO.setId(reqApprNotificationEntity.getId());
        reqApprNotificationTO.setReqCode(reqApprNotificationEntity.getReqCode());
        reqApprNotificationTO.setNotifyCode(reqApprNotificationEntity.getNotifyCode());
        UserMstrEntity apprUser = reqApprNotificationEntity.getApprUserId();
        if (apprUser != null)
            reqApprNotificationTO.setApprUserId(apprUser.getUserId());
        ProjMstrEntity proj = reqApprNotificationEntity.getProjId();
        if (proj != null)
            reqApprNotificationTO.setProjId(proj.getProjectId());
        UserMstrEntity reqUser = reqApprNotificationEntity.getReqUserId();
        if (reqUser != null)
            reqApprNotificationTO.setReqUserId(reqUser.getUserId());
        reqApprNotificationTO.setStage(reqApprNotificationEntity.getStage());
        reqApprNotificationTO.setNotificationStatus(reqApprNotificationEntity.getNotificationStatus());
        return reqApprNotificationTO;
    }
    
    public static ProcurementNotificationsTO convertEntityToPOJO(ProcurementNotificationsEntity procurementNotificationsEntity) {
    	ProcurementNotificationsTO procurementNotificationsTO = new ProcurementNotificationsTO();
    	String prString = "";
    	String notNumber = "";
    	String approverDecision = "";
    	
    	procurementNotificationsTO.setId(procurementNotificationsEntity.getId());
		if (CommonUtil.isNotBlankDate(procurementNotificationsEntity.getDate())) {
			procurementNotificationsTO.setDate(CommonUtil.convertDateToString(procurementNotificationsEntity.getDate()));
		}
		PreContractEntity preContractEntity = procurementNotificationsEntity.getPreContractEntity();
		if (null != preContractEntity) {
			ProjMstrEntity projMstrEntity = preContractEntity.getProjId();
            if (null != projMstrEntity) {
            	procurementNotificationsTO.setProjId(projMstrEntity.getProjectId());
            }
            if (preContractEntity.getProjId() != null) {
            	procurementNotificationsTO.setParentName(preContractEntity.getProjId().getProjName());
            	procurementNotificationsTO.setParentCode(preContractEntity.getProjId().getParentProjectMstrEntity().getProjName());
            }
            
            procurementNotificationsTO.setFromUserName(procurementNotificationsEntity.getFromUserId().getDisplayName());
            
            procurementNotificationsTO.setToUserName(procurementNotificationsEntity.getToUserId().getDisplayName());
            
            if(procurementNotificationsEntity.getProcureStage().equalsIgnoreCase("Stage 1 Request")) { 
            	prString = "PRS1";
            } else if (procurementNotificationsEntity.getProcureStage().equalsIgnoreCase("Stage 1 Approval")) {
            	prString = "PAS1";
            } else if (procurementNotificationsEntity.getProcureStage().equalsIgnoreCase("Stage 2 Request")) {
            	prString = "PRS2";
            } else if (procurementNotificationsEntity.getProcureStage().equalsIgnoreCase("Stage 2 Approval")) {
            	prString = "PAS2";
            }
            
            if (procurementNotificationsEntity.getApprStatus() == 3) {
        		approverDecision = ": Sent Back To Requestor";
        	} else if (procurementNotificationsEntity.getApprStatus() == 4) {
        		approverDecision = ": On Hold";
        	} else if (procurementNotificationsEntity.getApprStatus() == 5) {
        		approverDecision = ": Approved";
        	} else if (procurementNotificationsEntity.getApprStatus() == 6) {
        		approverDecision = ": Rejected";
        	}
            
            notNumber = prString + "-" + procurementNotificationsEntity.getId();
            
            procurementNotificationsTO.setCode(notNumber);
			procurementNotificationsTO.setModuleCode(procurementNotificationsEntity.getModuleCode());
			procurementNotificationsTO.setNotifyRefId(procurementNotificationsEntity.getNotifyRefId());
			procurementNotificationsTO.setProjId(procurementNotificationsEntity.getProjId().getProjectId());
			procurementNotificationsTO.setProcureCatg(procurementNotificationsEntity.getProcureCatg());
			procurementNotificationsTO.setProcureStage(procurementNotificationsEntity.getProcureStage());
			procurementNotificationsTO.setNotificationStatus(procurementNotificationsEntity.getNotificationStatus()+""+approverDecision);
			procurementNotificationsTO.setStatus(procurementNotificationsEntity.getStatus());
			procurementNotificationsTO.setPreContractId(procurementNotificationsEntity.getPreContractEntity().getId());
			procurementNotificationsTO.setToUserId(procurementNotificationsEntity.getToUserId().getUserId());
			procurementNotificationsTO.setApprStatus(procurementNotificationsEntity.getApprStatus());
			procurementNotificationsTO.setReqComments(procurementNotificationsEntity.getReqComments());
		}
		return procurementNotificationsTO;
}

    public static List<ProcurementNotificationsEntity> convertPOJOToEntity(
            List<ProcurementNotificationsTO> procurementNotificationsTOs, LoginRepository loginRepository,
            EPSProjRepository epsProjRepository) {
        List<ProcurementNotificationsEntity> procurementNotificationsEntites = new ArrayList<ProcurementNotificationsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProcurementNotificationsTO procurementNotificationsTO : procurementNotificationsTOs) {

            procurementNotificationsEntites
                    .add(convertPOJOToEntity(procurementNotificationsTO, loginRepository, epsProjRepository));
        }
        return procurementNotificationsEntites;
    }

    public static ProcurementNotificationsEntity convertPOJOToEntity(
            ProcurementNotificationsTO procurementNotificationsTO, LoginRepository loginRepository,
            EPSProjRepository epsProjRepository) {

        ProcurementNotificationsEntity procurementNotificationsEntity = new ProcurementNotificationsEntity();

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
        procurementNotificationsEntity.setModuleCode(procurementNotificationsTO.getModuleCode());
        procurementNotificationsEntity.setNotifyRefId(procurementNotificationsTO.getNotifyRefId());
        procurementNotificationsEntity.setProjId(epsProjRepository.findOne(procurementNotificationsTO.getProjId()));
        procurementNotificationsEntity.setFromUserId(loginRepository.findOne(AppUserUtils.getUserId()));
        procurementNotificationsEntity.setToUserId(loginRepository.findOne(procurementNotificationsTO.getToUserId()));
        //procurementNotificationsEntity.setProcureCatg(procurementNotificationsTO.getProcureCatg());
        procurementNotificationsEntity.setProcureCatg("Plants");
        procurementNotificationsEntity.setProcureStage(procurementNotificationsTO.getProcureStage());
        procurementNotificationsEntity.setNotificationStatus(procurementNotificationsTO.getNotificationStatus());
        procurementNotificationsEntity.setStatus(procurementNotificationsTO.getStatus());
        //procurementNotificationsEntity.setPreContractId(procurementNotificationsTO.getPreContractId());
        return procurementNotificationsEntity;
    }

}
