package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProcurementApprTO;
import com.rjtech.projsettings.model.ProcurementAddtionalTimeApprEntity;
import com.rjtech.projsettings.model.ProcurementNormalTimeEntity;
import com.rjtech.projsettings.model.ProcurementNotificationsEntity;
import com.rjtech.projsettings.repository.ProjProcureRepository;
import com.rjtech.common.repository.CommonRepository;

public class ProjProcurementApprHandler {


	private static final Logger log = LoggerFactory.getLogger(ProjProcurementApprHandler.class);
	
    public static List<ProcurementAddtionalTimeApprEntity> convertPOJOToEntity(
            List<ProjProcurementApprTO> projProcurementApprTOs, ProjProcureRepository projProcureRepository, CommonRepository commonRepository) {
        List<ProcurementAddtionalTimeApprEntity> ProjProcurementApprEntites = new ArrayList<ProcurementAddtionalTimeApprEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
   
        for (ProjProcurementApprTO projProcurementApprTO : projProcurementApprTOs) {
        	ProcurementAddtionalTimeApprEntity procurementAddtionalTimeApprEntity = new ProcurementAddtionalTimeApprEntity();
            log.info("procurementAddtionalTimeApprEntity.getProcureStage() " + procurementAddtionalTimeApprEntity.getProcureStage());
            if (CommonUtil.isNonBlankLong(projProcurementApprTO.getId())) {
            	log.info("Additional Time Entity Id is " + projProcurementApprTO.getId());
            	
                procurementAddtionalTimeApprEntity.setId(projProcurementApprTO.getId());
                log.info("procurementAddtionalTimeApprEntity.getId() is " + procurementAddtionalTimeApprEntity.getId());
            }
            log.info("projProcurementApprTO.getProcurementId() " + projProcurementApprTO.getNotificationId());
            ProcurementNormalTimeEntity procurementNormalTimeEntity = projProcureRepository
                    .findOne(projProcurementApprTO.getNotifyRefId());
            procurementAddtionalTimeApprEntity.setProjProcurementEntity(procurementNormalTimeEntity);
            if (CommonUtil.isNotBlankStr(projProcurementApprTO.getRequisitionDate())) {
                procurementAddtionalTimeApprEntity.setRequisitionDate(CommonUtil.convertStringToDate(projProcurementApprTO.getRequisitionDate()));
            }
            procurementAddtionalTimeApprEntity.setLatest(true);
            procurementAddtionalTimeApprEntity.setStatus(projProcurementApprTO.getStatus());
            procurementAddtionalTimeApprEntity.setNotificationStatus("Approved");
            procurementAddtionalTimeApprEntity.setProcureStage(projProcurementApprTO.getStage());
            procurementAddtionalTimeApprEntity.setApprUser(commonRepository.findOne(projProcurementApprTO.getApprUserId()));
            
            

            ProjProcurementApprEntites.add(procurementAddtionalTimeApprEntity);
        }
        return ProjProcurementApprEntites;
    }
    /*
    public static List<ProcurementNotificationsEntity> convertPOJOToEntity1(
            List<ProjProcurementApprTO> projProcurementApprTOs, ProjProcureRepository projProcureRepository, CommonRepository commonRepository) {
    	List<ProcurementNotificationsEntity> procurementNotificationsEntity = new ArrayList<ProcurementNotificationsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    	for (ProjProcurementApprTO projProcurementApprTO : projProcurementApprTOs) {
    		ProcurementNotificationsEntity procurNotificationsEntity = new ProcurementNotificationsEntity();
    		if (CommonUtil.isNonBlankLong(projProcurementApprTO.getNotificationId())) {
    			procurNotificationsEntity.setId(projProcurementApprTO.getNotificationId());
    		}
    		procurNotificationsEntity.setApprStatus(2);
    		if(projProcurementApprTO.getStage().equalsIgnoreCase("Stage 1 Internal Approval")) {
    			procurNotificationsEntity.setNotificationStatus("Request for Stage 1 Procurement Approval");
    		} else {
    			procurNotificationsEntity.setNotificationStatus("Request for Stage 2 Procurement Approval");
    		}
    		procurNotificationsEntity.setNotificationStatus("Request for Stage 1 Procurement Approval");
    		procurNotificationsEntity.setReqComments("Additional Time Approved");
    		
    		procurementNotificationsEntity.add(procurNotificationsEntity);
    	}
    	return procurementNotificationsEntity;
    }
	*/
}
