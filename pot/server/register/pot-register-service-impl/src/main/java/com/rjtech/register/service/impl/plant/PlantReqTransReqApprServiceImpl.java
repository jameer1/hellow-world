package com.rjtech.register.service.impl.plant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.service.impl.CommonEmailServiceImpl;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.StatusType;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.plant.dto.PlantTransReqApprDtlTO;
import com.rjtech.register.plant.dto.PlantTransferReqApprTO;
import com.rjtech.register.plant.model.PlantNotificationsEntity;
import com.rjtech.register.plant.model.PlantRegProjEntity;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.register.plant.model.PlantTransReqApprDtlEntity;
import com.rjtech.register.plant.model.PlantTransferReqApprEntity;
import com.rjtech.register.plant.req.PlantTranferReqApprSaveReq;
import com.rjtech.register.plant.req.PlantTransReq;
import com.rjtech.register.plant.resp.PlantNotificationResp;
import com.rjtech.register.plant.resp.PlantTransferReqApprResp;
import com.rjtech.register.repository.material.UserMstrEntityRepository;
import com.rjtech.register.repository.plant.PlantNotificationRepository;
import com.rjtech.register.repository.plant.PlantRegProjRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;
import com.rjtech.register.repository.plant.PlantTransferReqApprRepository;
import com.rjtech.register.repository.plant.ProjSettingsPlantTransProcRepository;
import com.rjtech.register.service.handler.plant.PlantNotificationHandler;
import com.rjtech.register.service.handler.plant.PlantTransReqApprDtlHandler;
import com.rjtech.register.service.handler.plant.PlantTransferReqApprHandler;
import com.rjtech.register.service.plant.PlantReqTransReqApprService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.model.UserEntity;
import com.rjtech.user.repository.UserRepository;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;

@Service(value = "plantReqTransReqApprService")
@RJSService(modulecode = "plantReqTransReqApprService")
@Transactional
public class PlantReqTransReqApprServiceImpl implements PlantReqTransReqApprService {
	
	private static final Logger log = LoggerFactory.getLogger(PlantReqTransReqApprServiceImpl.class);
	
	private static String pot = "\"Project on Track\"";

    @Autowired
    private CommonEmailServiceImpl commonEmail;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMstrEntityRepository userMstrEntityRepository;

    @Autowired
    private PlantTransferReqApprRepository plantTransferReqApprRepository;

    @Autowired
    private PlantRegProjRepository plantRegProjRepository;

    @Autowired
    private PlantNotificationRepository plantNotificationsRepository;

    @Autowired
    private EPSProjService epsProjService;

    @Autowired
    private ProjSettingsPlantTransProcRepository projSettingsPlantTransProcRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private PlantRegisterRepository plantRegisterRepository;

    public PlantTransferReqApprResp getPlantTransfers(PlantTransReq plantTransReq) {
        PlantTransferReqApprResp plantTransferReqApprResp = new PlantTransferReqApprResp();
        Date fromDate = null;
        Date toDate = null;
        if (CommonUtil.isNotBlankStr(plantTransReq.getFromDate())
                && CommonUtil.isNotBlankStr(plantTransReq.getToDate())) {
            fromDate = CommonUtil.convertStringToDate(plantTransReq.getFromDate());
            toDate = CommonUtil.convertStringToDate(plantTransReq.getToDate());
        } else {
            toDate = new Date();
            fromDate = CommonUtil.substarctInputMonths(toDate, -1);
        }
        toDate = CommonUtil.addNumberOfDaysInputDate(toDate, 1);
        List<Long> projIds = epsProjService.getUserProjIds();
        if (projIds.isEmpty())
            return plantTransferReqApprResp;

        Long userId = null;
        if (plantTransReq.isLoginUser()) {
            userId = AppUserUtils.getUserId();
        }
        List<PlantTransferReqApprEntity> plantRequestForTransEntites = null;
        if (plantTransReq.isTransType()) {
            plantRequestForTransEntites = plantTransferReqApprRepository.findPlantReqTranfers(projIds, userId,
                    plantTransReq.getStatus(), fromDate, toDate);
        } else {
            if (RegisterConstants.ALL.equalsIgnoreCase(plantTransReq.getApprStatus())) {
                plantRequestForTransEntites = plantTransferReqApprRepository.findPlantAllTranfers(projIds, userId,
                        plantTransReq.getStatus(), fromDate, toDate);
            } else {
                plantRequestForTransEntites = plantTransferReqApprRepository.findPlantApprTranfers(projIds, userId,
                        plantTransReq.getApprStatus(), plantTransReq.getStatus(), fromDate, toDate);
            }
        }
        if (CommonUtil.isListHasData(plantRequestForTransEntites)) {
            for (PlantTransferReqApprEntity plantTransferReqApprEntity : plantRequestForTransEntites) {
                PlantTransferReqApprTO plantTransferReqApprTO = PlantTransferReqApprHandler
                        .convertEntityToPOJO(plantTransferReqApprEntity);
                for (PlantTransReqApprDtlEntity plantTransReqApprDtlEntity : plantTransferReqApprEntity
                        .getPlantTransReqApprDtlEntities()) {
                    plantTransferReqApprTO.getPlantTransReqApprDtlTOs()
                            .add(PlantTransReqApprDtlHandler.convertEntityToPOJO(plantTransReqApprDtlEntity));
                }
                plantTransferReqApprResp.getPlantTransferReqApprTOs().add(plantTransferReqApprTO);
            }
        }
        return plantTransferReqApprResp;
    }

    public PlantTransferReqApprResp getPlantTransferDetails(PlantTransReq plantTransReq) {
        PlantTransferReqApprResp plantTransferReqApprResp = new PlantTransferReqApprResp();
        PlantTransferReqApprEntity plantTransferReqApprEntity = plantTransferReqApprRepository
                .findOne(plantTransReq.getId());
        PlantTransferReqApprTO plantTransferReqApprTO = PlantTransferReqApprHandler
                .convertEntityToPOJO(plantTransferReqApprEntity);
        for (PlantTransReqApprDtlEntity plantTransReqApprDtlEntity : plantTransferReqApprEntity
                .getPlantTransReqApprDtlEntities()) {
            plantTransferReqApprTO.getPlantTransReqApprDtlTOs()
                    .add(PlantTransReqApprDtlHandler.convertEntityToPOJO(plantTransReqApprDtlEntity));
        }
        plantTransferReqApprResp.getPlantTransferReqApprTOs().add(plantTransferReqApprTO);
        return plantTransferReqApprResp;
    }

    public PlantTransferReqApprResp savePlantTransfers(PlantTranferReqApprSaveReq plantReqForTransSaveReq) {
        List<PlantTransferReqApprEntity> plantTransferReqApprEntities = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (PlantTransferReqApprTO plantTransferReqTO : plantReqForTransSaveReq.getPlantTransferReqApprTOs()) {
            PlantTransferReqApprEntity plantTransferReqEntity = PlantTransferReqApprHandler.convertPOJOToEntity(
                    plantTransferReqTO, epsProjRepository, userMstrEntityRepository, plantNotificationsRepository);
            if (CommonUtil.isBlankLong(plantTransferReqEntity.getId())) {
                plantTransferReqEntity.setApprStatus(RegisterConstants.PENDING_FOR_APPROVAL);
            }
            PlantNotificationsEntity plantNotificationsEntity = populateNotificationEntity(plantTransferReqEntity);
            //plantNotificationsEntity.setNotificationStatus(plantTransferReqTO.getNotificationStatus());
            plantNotificationsEntity.setNotificationMsg("Request for Approval");
            PlantNotificationsEntity savedPlantNotfEntity = plantNotificationsRepository.save(plantNotificationsEntity);
            plantTransferReqEntity.setPlantNotificationsEntity(savedPlantNotfEntity);
            PlantTransferReqApprEntity savedEntity = plantTransferReqApprRepository.save(plantTransferReqEntity);
            plantTransferReqApprEntities.add(savedEntity);

            for (PlantTransReqApprDtlTO plantTransReqApprDtlTO : plantTransferReqTO.getPlantTransReqApprDtlTOs()) {
                // plantTransferReqTO.getPlantTransReqApprDtlTOs() is number of plants under this request
                PlantTransReqApprDtlEntity plantTransReqApprDtlEntity = PlantTransReqApprDtlHandler
                        .convertPOJOToEntity(plantTransReqApprDtlTO, plantRegisterRepository, epsProjRepository);
                plantTransReqApprDtlEntity.setPlantTransferReqApprEntity(plantTransferReqEntity);
                if (CommonUtil.isBlankLong(plantTransferReqEntity.getId())) {
                    plantTransReqApprDtlEntity.setApprStatus(RegisterConstants.PENDING_FOR_APPROVAL);
                }
                if (RegisterConstants.APPROVED.equalsIgnoreCase(plantTransferReqTO.getApprStatus())
                        && plantTransReqApprDtlEntity.getReceivedBy() == null) {
                    PlantRegProjEntity plantRegProjEntity = plantRegProjRepository
                            .findLatestPlantDeployment(plantTransReqApprDtlTO.getPlantId());
                    plantRegProjEntity.setIsLatest(RegisterConstants.UPDATE_IS_LATEST);
                    clonePlantToNewProj(plantRegProjEntity, plantTransReqApprDtlTO, plantTransferReqTO);
                }
                plantTransferReqEntity.getPlantTransReqApprDtlEntities().add(plantTransReqApprDtlEntity);
            }
            if (CommonUtil.isBlankLong(plantTransferReqTO.getId())) {
                sendEmailNotification(savedPlantNotfEntity);
            }
            plantTransferReqApprEntities.add(plantTransferReqEntity);
        }
        List<PlantTransferReqApprEntity> savedPlantTransfers = plantTransferReqApprRepository
                .save(plantTransferReqApprEntities);

        PlantTransferReqApprResp resp = new PlantTransferReqApprResp();
        for (PlantTransferReqApprEntity plantTransferReqApprEntity : savedPlantTransfers) {
            PlantTransferReqApprTO plantTransferReqApprTO = PlantTransferReqApprHandler
                    .convertEntityToPOJO(plantTransferReqApprEntity);
            for (PlantTransReqApprDtlEntity plantTransReqApprDtlEntity : plantTransferReqApprEntity
                    .getPlantTransReqApprDtlEntities()) {
                plantTransferReqApprTO.getPlantTransReqApprDtlTOs()
                        .add(PlantTransReqApprDtlHandler.convertEntityToPOJO(plantTransReqApprDtlEntity));
            }
            resp.getPlantTransferReqApprTOs().add(plantTransferReqApprTO);
        }

        return resp;
    }

    public PlantNotificationsEntity populateNotificationEntity(PlantTransferReqApprEntity plantTransferReqEntity) {
        PlantNotificationsEntity plantNotificationsEntity = null;
        if (CommonUtil.isBlankLong(plantTransferReqEntity.getId())) {
        	log.info("Insert.....plantTransferReqEntity.getId() " + plantTransferReqEntity.getId());
            plantNotificationsEntity = new PlantNotificationsEntity();
            if (RegisterConstants.PENDING_FOR_APPROVAL.equalsIgnoreCase(plantTransferReqEntity.getApprStatus())) {
                plantNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_PENDING);
            }
            plantNotificationsEntity.setProjMstrEntity(plantTransferReqEntity.getFromProjId());
            plantNotificationsEntity.setType("Request for Plants transfer to Project - " + plantTransferReqEntity.getToProjId().getProjName());
            plantNotificationsEntity.setForProject(plantTransferReqEntity.getToProjId().getProjectId());
            plantNotificationsEntity.setDate(new Date());
            plantNotificationsEntity.setReqUserId(userMstrEntityRepository.findOne(plantTransferReqEntity.getReqUserMstrEnitty().getUserId()));
            plantNotificationsEntity.setApprUserId(userMstrEntityRepository.findOne(plantTransferReqEntity.getApprUserMstrEntity().getUserId()));
            plantNotificationsEntity.setStatus(StatusCodes.ACTIVE.getValue());
        } else {
            plantNotificationsEntity = plantNotificationsRepository
                    .findOne(plantTransferReqEntity.getPlantNotificationsEntity().getId());
            log.info("update..... plantTransferReqEntity.getApprStatus() " + plantTransferReqEntity.getId());
            log.info("plantTransferReqEntity.getApprStatus() " + plantTransferReqEntity.getApprStatus());
            if (RegisterConstants.APPROVED.equalsIgnoreCase(plantTransferReqEntity.getApprStatus())) {
            	log.info("plantNotificationsEntity.getNotificationStatus() 1 - APP - " + plantNotificationsEntity.getNotificationStatus());
            	plantNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_APPROVED);
            	log.info("plantNotificationsEntity.getNotificationStatus() 2 - APP - " + plantNotificationsEntity.getNotificationStatus());
            } else if (RegisterConstants.REJECTED.equalsIgnoreCase(plantTransferReqEntity.getApprStatus())) {
            	log.info("plantNotificationsEntity.getNotificationStatus() 1 - REJ - " + plantNotificationsEntity.getNotificationStatus());
                plantNotificationsEntity.setNotificationStatus(CommonConstants.APPR_STATUS_REJECTED);
                log.info("plantNotificationsEntity.getNotificationStatus() 2 - REJ - " + plantNotificationsEntity.getNotificationStatus());
            }
            sendApprovedEmailNotification(plantNotificationsEntity);
        }
        log.info("plantNotificationsEntity.getNotificationStatus() 8 - REJ - " + plantNotificationsEntity.getNotificationStatus());
        return plantNotificationsEntity;
    }

    public PlantNotificationResp getPlantNotifications(NotificationsGetReq notificationsGetReq) {
        PlantNotificationResp plantNotificationResp = new PlantNotificationResp();
        List<PlantNotificationsEntity> plantNotificationsEntities = plantNotificationsRepository.findPlantNotification(
                notificationsGetReq.getId(), notificationsGetReq.getProjId(), notificationsGetReq.getStatus());
        for (PlantNotificationsEntity plantNotificationsEntity : plantNotificationsEntities) {
            plantNotificationResp.getPlantNotificationsTOs()
                    .add(PlantNotificationHandler.convertEntityToPOJO(plantNotificationsEntity));
        }
        return plantNotificationResp;
    }

    public LabelKeyTOResp getPlantTransferReqDetails(PlantTransReq plantTransReq) {
    	Long ProjId = plantTransReq.getProjId();
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        List<LabelKeyTO> labelKeyTOs = labelKeyTOResp.getLabelKeyTOs();
        List<Object[]> transferPlants = plantRegProjRepository.findNewRequestTransferPlants(AppUserUtils.getClientId(),
                plantTransReq.getProjId());
        for (Object[] transferPlant : transferPlants) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId((Long) transferPlant[0]);
            labelKeyTO.setCode(String.valueOf(transferPlant[1]));
            labelKeyTO.setName(String.valueOf(transferPlant[2]));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PLANT_REG_NO, String.valueOf(transferPlant[3]));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PLANT_MANFACTURE, String.valueOf(transferPlant[4]));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PLANT_MODEL, String.valueOf(transferPlant[5]));
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.EXPECTED_DATE,
                    CommonUtil.getDDMMMYYYFormat((Date) transferPlant[6]));
            Date DemobDate = plantRegProjRepository.findDemobDate(ProjId, (Long) transferPlant[0]);
            labelKeyTO.setDemobDate(DemobDate);
            labelKeyTOs.add(labelKeyTO);
        }
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }

    public List<LabelKeyTO> getStatusTypes() {
        List<LabelKeyTO> statusTypLabelKeyTOs = new ArrayList<>();
        LabelKeyTO statusTypLabelKeyTO = new LabelKeyTO();
        for (StatusType statusType : StatusType.values()) {
            statusTypLabelKeyTO.setId(statusType.getValue());
            statusTypLabelKeyTO.setName(statusType.getName());
        }
        return statusTypLabelKeyTOs;
    }

    public LabelKeyTOResp getProjSettingsPlantTransferCheck(PlantTransReq plantTransReq) {
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        if (CommonUtil.isNonBlankLong(plantTransReq.getProjId())
                && CommonUtil.isNotBlankStr(plantTransReq.getApprStatus())) {
            List<LabelKeyTO> labelKeyTOs = projSettingsPlantTransProcRepository.getProSettingsForPlantTransCheck(
                    plantTransReq.getProjId(), plantTransReq.getId(), plantTransReq.getApprStatus());
            labelKeyTOResp.getLabelKeyTOs().addAll(labelKeyTOs);

        }
        return labelKeyTOResp;
    }

    private void clonePlantToNewProj(PlantRegProjEntity oldEntity, PlantTransReqApprDtlTO plantTransReqApprDtlTO,
            PlantTransferReqApprTO plantTransferReqTO) {
        PlantRegProjEntity latestPlantProjRigisterEntity = new PlantRegProjEntity();
        PlantRegisterDtlEntity plantDtl = plantRegisterRepository.findOne(plantTransReqApprDtlTO.getPlantId());
        latestPlantProjRigisterEntity.setPlantRegisterDtlEntity(oldEntity.getPlantRegisterDtlEntity());
        ProjMstrEntity projMstr = epsProjRepository.findOne(plantTransferReqTO.getToProjId());
        plantDtl.setProjMstrEntity(projMstr);
        latestPlantProjRigisterEntity.setProjId(projMstr);
        latestPlantProjRigisterEntity.setCommissionDate(oldEntity.getCommissionDate());
        latestPlantProjRigisterEntity.setMobDate(CommonUtil.convertStringToDate(plantTransReqApprDtlTO.getTransDate()));
        if (CommonUtil.isNonBlankLong(oldEntity.getDeploymentId())) {
            latestPlantProjRigisterEntity.setDeploymentId(oldEntity.getDeploymentId() + 1);
        }
        latestPlantProjRigisterEntity.setStatus(StatusCodes.ACTIVE.getValue());
        latestPlantProjRigisterEntity.setIsLatest(RegisterConstants.SET_IS_LATEST);
        plantRegProjRepository.save(latestPlantProjRigisterEntity);
    }

    private void sendEmailNotification(PlantNotificationsEntity savedPlantNotfEntity) {
    	
    	String epsName = null;
        String projName = null;
        String toepsName = null;
        String toprojName = null;
        
    	UserProjGetReq userProjGetReq = new UserProjGetReq();
        UserProjResp userProjResp = epsProjService.getAllUserProjects(userProjGetReq);
        Map<Long, LabelKeyTO> userProjMap = new HashMap<>();
        LabelKeyTO userProjLabelKeyTO = null;
        for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
            userProjLabelKeyTO = new LabelKeyTO();
            userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
            userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
            userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
            userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
        }
        for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
        	if (CommonUtil.objectNotNull(savedPlantNotfEntity.getProjMstrEntity())
                    && savedPlantNotfEntity.getProjMstrEntity().getProjectId().equals(entry.getKey())) {
                epsName = entry.getValue().getCode();
                projName = entry.getValue().getName();
            }
        	if (savedPlantNotfEntity.getForProject().equals(entry.getKey())) {
                toepsName = entry.getValue().getCode();
                toprojName = entry.getValue().getName();
            }

        }
        
        UserEntity userMstr = userRepository.findOne(savedPlantNotfEntity.getReqUserId().getUserId());
        UserEntity userMstr1 = userRepository.findOne(savedPlantNotfEntity.getApprUserId().getUserId());
        String toEmail = userMstr1.getEmail();
        String ccEmail = userMstr.getEmail();
        String code = PlantNotificationHandler.generateNotifyCode(savedPlantNotfEntity);
        String reqCode = PlantNotificationHandler.generateNotifyReqCode(savedPlantNotfEntity);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String apprDate = dateFormat.format(savedPlantNotfEntity.getDate());
        
        String toSubject = "Request for Plant Transfer to the Project - " + toprojName;
        String text = "<html><body><p>" + userMstr.getDisplayName() + ",</p>"
                + "<p>I have submitted my request for Plant Transfer through " + pot + ", as per details mentioned here below.</p>"
                + "<table border='1'>"
                + "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
                + "<tr><td>From Project </td><td>" + projName + "</td></tr>"
                + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
                + "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
                + "<tr><td>Requisition ID</td><td>" + reqCode + "</td></tr>"
                + "<tr><td>Notification ID</td><td>" + code + "</td></tr>"
                + "</table>"
                + "<p>This is for your Approval Please.</p>" 
                + "<p>Regards,</p>" 
                + "<p>"+ AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "<br/>" + apprDate + "</p>"
                + "</body></html>";
        commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
    }

    private void sendApprovedEmailNotification(PlantNotificationsEntity plantNotificationsEntity) {
    	String epsName = null;
        String projName = null;
        String toepsName = null;
        String toprojName = null;
        log.info("plantNotificationsEntity.getNotificationStatus() 3 - REJ - " + plantNotificationsEntity.getNotificationStatus());
    	UserProjGetReq userProjGetReq = new UserProjGetReq();
        UserProjResp userProjResp = epsProjService.getAllUserProjects(userProjGetReq);
        Map<Long, LabelKeyTO> userProjMap = new HashMap<>();
        LabelKeyTO userProjLabelKeyTO = null;
        for (UserProjDetailsTO userProjDetailsTO : userProjResp.getUserProjDetailsTOs()) {
            userProjLabelKeyTO = new LabelKeyTO();
            userProjLabelKeyTO.setId(userProjDetailsTO.getProjId());
            userProjLabelKeyTO.setCode(userProjDetailsTO.getParentName());
            userProjLabelKeyTO.setName(userProjDetailsTO.getProjName());
            userProjMap.put(userProjDetailsTO.getProjId(), userProjLabelKeyTO);
        }
        for (Map.Entry<Long, LabelKeyTO> entry : userProjMap.entrySet()) {
        	if (CommonUtil.objectNotNull(plantNotificationsEntity.getProjMstrEntity())
                    && plantNotificationsEntity.getProjMstrEntity().getProjectId().equals(entry.getKey())) {
        		epsName = entry.getValue().getCode();
        		projName = entry.getValue().getName();
            }
            if (plantNotificationsEntity.getForProject().equals(entry.getKey())) {
            	toepsName = entry.getValue().getCode();
            	toprojName = entry.getValue().getName();
            }
            
        }

        log.info("plantNotificationsEntity.getNotificationStatus() 4 - REJ - " + plantNotificationsEntity.getNotificationStatus());
        plantNotificationsEntity.setType("Plants transfe to Project -" + toprojName);
        //approved Email Notification
        UserEntity userMstr1 = userRepository.findOne(plantNotificationsEntity.getReqUserId().getUserId());
        UserEntity userMstr = userRepository.findOne(plantNotificationsEntity.getApprUserId().getUserId());
        String toEmail = userMstr1.getEmail();
        String ccEmail = userMstr.getEmail();
        
        String apprDec = plantNotificationsEntity.getNotificationStatus();
        String apprDecision =apprDec.substring(0, 1).toUpperCase() + apprDec.substring(1).toLowerCase();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String apprDate = dateFormat.format(new Date());
        log.info("plantNotificationsEntity.getNotificationStatus() 5 - REJ - " + plantNotificationsEntity.getNotificationStatus());
        String notifyCode = PlantNotificationHandler.generateNotifyCode(plantNotificationsEntity);
        String toSubject = "Approver decision for Plant Transfer to the Project - " + toprojName;
        String text = "<html><body><p>" + userMstr1.getDisplayName() + ",</p>"
        		+ "<p>Reference Notification ID "+ PlantNotificationHandler.generateNotifyReqCode(plantNotificationsEntity) + "</p>"
                + "<p>I have transmitted my decision for Plant Transfer through " + pot + ", as per details mentioned here below.</p>"
                + "<table border='1'>"
                + "<tr><td>From EPS </td><td>" + epsName + "</td></tr>"
                + "<tr><td>From Project </td><td>" + projName + "</td></tr>"
                + "<tr><td>To EPS </td><td>" + toepsName + "</td></tr>"
                + "<tr><td>To Project </td><td>" + toprojName + "</td></tr>"
                + "<tr><td>Requisition ID</td><td>" + PlantNotificationHandler.generateNotifyReqCode(plantNotificationsEntity) + "</td></tr>"
                + "<tr><td>Notification ID</td><td>" + notifyCode + "</td></tr>"
                 + "<tr><td>Approver Decision</td><td>" + apprDecision + "</td></tr>"
                + "</table>"
                + "<p>This is for your information please.</p>" 
                + "<p>Regards,</p>"  
                + "<p>" + AppUserUtils.getName() + "<br/>" + AppUserUtils.getDisplayRole() + "<br/>" + apprDate + "</p></body></html>";
        log.info("plantNotificationsEntity.getNotificationStatus() 6 - REJ - " + plantNotificationsEntity.getNotificationStatus());
        commonEmail.sendEmailNotification(toEmail, ccEmail, toSubject, text);
        log.info("plantNotificationsEntity.getNotificationStatus() 7 - REJ - " + plantNotificationsEntity.getNotificationStatus());
    }

}
