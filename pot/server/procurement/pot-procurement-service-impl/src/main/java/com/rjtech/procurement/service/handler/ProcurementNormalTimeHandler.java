package com.rjtech.procurement.service.handler;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rjtech.common.constants.AwsS3FileKeyConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.service.handler.ProjDocFileHandler;
import com.rjtech.notification.model.ProcurementNormalTimeEntity;
import com.rjtech.procurement.dto.PreContractDocsTO;
import com.rjtech.procurement.dto.ProcurementNormalTimeTO;
import com.rjtech.procurement.model.PreContractDocEntity;
//import com.rjtech.procurement.model.ProcurementNormalTimeEntity;
import com.rjtech.procurement.repository.PrecontractDocRepository;
import com.rjtech.procurement.repository.PrecontractRepository;

@Component
public class ProcurementNormalTimeHandler {

    @Autowired
    private ProjDocFileHandler projDocFileHandler;

    private static final Logger log = LoggerFactory.getLogger(ProcurementNormalTimeHandler.class);

    public static ProcurementNormalTimeTO convertEntityToTO(ProcurementNormalTimeEntity procurementNormalTime, Date reqDate) {
    	
    	log.info("Inside ProcurementNormalTimeHandler " + reqDate);
    	Boolean approveFlag = false;

    	//LocalDate localDate = ((java.sql.Date) reqDate).toLocalDate();
    	//LocalDate localDate = reqDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(5);
    	LocalDate localDate = reqDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	log.info("Date of Request: " + localDate);
    	
    	LocalDateTime localdateTime = localDate.atStartOfDay();
    	// Cut Off Time in Days from week  commencing  day - Raju Document
    	localdateTime = localdateTime.plusDays(1);
    	//--------------------------------------------------------------
    	log.info("Stage # Internal Approval Cut off Days: " + procurementNormalTime.getCutOffDays());
    	log.info("Stage # Internal Approval Cut off Hours: " + procurementNormalTime.getCutOffHours());
    	log.info("Stage # Internal Approval Cut off Minutes: " + procurementNormalTime.getCutOffMinutes());
    	log.info("---------------------------------------------------------------");
    	log.info("Date of Request with effect from midnight: " + localdateTime);
        localdateTime = localdateTime.plusDays(procurementNormalTime.getCutOffDays());
        log.info("Adding Days with Date of Request: " + localdateTime);
        localdateTime = localdateTime.plusHours(procurementNormalTime.getCutOffHours());
        log.info("Adding Days + Hours with Date of Request: " + localdateTime);
        localdateTime = localdateTime.plusMinutes(procurementNormalTime.getCutOffMinutes());
        log.info("Adding Days + Hours + Minutes with Date of Request: " + localdateTime);
        log.info("Current Local Time: " + Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime());
        if (localdateTime.isAfter(Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
        	log.info("Requestor Date is AFTER Current Date ");
        	approveFlag = false;
        } else {
        	log.info("Requestor Date is BEFORE Current Date ");
        	approveFlag = true;
        }
        log.info("approveFlag is " + approveFlag);
        
    	ProcurementNormalTimeTO procurementNormalTimeTO = new ProcurementNormalTimeTO();
    	procurementNormalTimeTO.setId(procurementNormalTime.getId());
    	procurementNormalTimeTO.setCutOffDays(procurementNormalTime.getCutOffDays());
    	procurementNormalTimeTO.setCutOffHours(procurementNormalTime.getCutOffHours());
    	procurementNormalTimeTO.setCutOffMinutes(procurementNormalTime.getCutOffMinutes());
    	procurementNormalTimeTO.setCutOffTime(procurementNormalTime.getCutOffTime());
    	procurementNormalTimeTO.setDefaultStatus(procurementNormalTime.getDefaultStatus());
    	procurementNormalTimeTO.setType(procurementNormalTime.getProcureType());
    	procurementNormalTimeTO.setStatus(procurementNormalTime.getStatus());
    	procurementNormalTimeTO.setTypeId(procurementNormalTime.getTypeId());
    	procurementNormalTimeTO.setNormalTimeFlag(approveFlag);
    	
    	return procurementNormalTimeTO;
    }

    public PreContractDocEntity convertDocsPOJOToEntity(PreContractDocsTO preContractDocsTO,
            PrecontractDocRepository precontractDocRepository, PrecontractRepository precontractRepository) {
        PreContractDocEntity contractDocEntity = null;
        if (CommonUtil.isNonBlankLong(preContractDocsTO.getId())) {
            contractDocEntity = precontractDocRepository.findOne(preContractDocsTO.getId());
        } else {
            contractDocEntity = new PreContractDocEntity();
        }
        if (preContractDocsTO.getProjDocFileTO() != null) {
            ProjDocFileEntity projDocFile = projDocFileHandler
                    .convertPOJOToEntity(preContractDocsTO.getProjDocFileTO());
            contractDocEntity.setProjDocFileEntity(projDocFile);
        }
        contractDocEntity.setStatus(preContractDocsTO.getStatus());
        contractDocEntity.setPreContractId(precontractRepository.findOne(preContractDocsTO.getPreContractId()));
        return contractDocEntity;
    }

    public void uploadPreconreactDocsToAwsS3(PreContractDocEntity preContractDocEntity,
            PreContractDocsTO preContractDocsTO) throws IOException {
        String uniqueKey = preContractDocsTO.getUniqueKey();
        ProjDocFileTO docFileTo = preContractDocsTO.getProjDocFileTO();
        if (docFileTo != null) {
            if (uniqueKey != null) {
                projDocFileHandler.updateExistingFileToAwsS3(uniqueKey, docFileTo.getMultipartFile());
            } else {
                projDocFileHandler.uploadFileToAwsS3(preContractDocEntity.getProjDocFileEntity(),
                        docFileTo.getMultipartFile(), AwsS3FileKeyConstants.PRECONTRACT_REFERENCE_DOCUMENT);
            }
        }

    }

}
