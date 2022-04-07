package com.rjtech.register.service.handler.plant;

import java.util.Date;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.plant.dto.PlantTransferReqApprTO;
import com.rjtech.register.plant.model.PlantNotificationsEntity;
import com.rjtech.register.plant.model.PlantTransferReqApprEntity;
import com.rjtech.register.repository.material.UserMstrEntityRepository;
import com.rjtech.register.repository.plant.PlantNotificationRepository;
import com.rjtech.register.service.handler.emp.EmpRegisterDtlHandler;

public class PlantTransferReqApprHandler {
    private PlantTransferReqApprHandler() {
    }

    public static PlantTransferReqApprTO convertEntityToPOJO(PlantTransferReqApprEntity entity) {

        PlantTransferReqApprTO plantTransferReqApprTO = new PlantTransferReqApprTO();
        plantTransferReqApprTO.setId(entity.getId());
        ProjMstrEntity fromProj = entity.getFromProjId();
        if (fromProj != null) {
            plantTransferReqApprTO.setFromProjId(fromProj.getProjectId());
            plantTransferReqApprTO.setFromProjName(fromProj.getProjName());
        }
        ProjMstrEntity toProj = entity.getToProjId();
        if (toProj != null) {
            plantTransferReqApprTO.setToProjId(toProj.getProjectId());
            plantTransferReqApprTO.setToProjName(toProj.getProjName());
        }

        if (CommonUtil.isNotBlankDate(entity.getReqDate())) {
            plantTransferReqApprTO.setReqDate(CommonUtil.convertDateToString(entity.getReqDate()));
        }

        if (CommonUtil.isNotBlankDate(entity.getApprDate())) {
            plantTransferReqApprTO.setApprDate(CommonUtil.convertDateToString(entity.getApprDate()));
        }
        plantTransferReqApprTO.setReqCode(generateReqCode(entity));
        plantTransferReqApprTO.setApprCode(generateApprCode(entity));
        plantTransferReqApprTO.setApprCurrentProj(entity.getApprCurrentProj());
        plantTransferReqApprTO.setReqCurrentProj(entity.getReqCurrentProj());

        UserMstrEntity reqUser = entity.getReqUserMstrEnitty();
        if (CommonUtil.objectNotNull(reqUser)) {
            plantTransferReqApprTO.setReqUserTO(EmpRegisterDtlHandler.convertUserEntityToPOJO(reqUser));
        }

        UserMstrEntity apprUser = entity.getApprUserMstrEntity();
        if (CommonUtil.objectNotNull(apprUser)) {
            plantTransferReqApprTO.setApprUserTO(EmpRegisterDtlHandler.convertUserEntityToPOJO(apprUser));
        }
        PlantNotificationsEntity notifyEntity = entity.getPlantNotificationsEntity();
        if (notifyEntity != null) {
            plantTransferReqApprTO.setNotifyId(notifyEntity.getId());
            plantTransferReqApprTO.setNotifyCode(PlantNotificationHandler.generateNotifyCode(notifyEntity));
            plantTransferReqApprTO.setNotifyDate(CommonUtil.convertDateToString(entity.getPlantNotificationsEntity().getDate()));
            plantTransferReqApprTO.setNotifyMsg(entity.getPlantNotificationsEntity().getNotificationMsg());
            if(!entity.getReqDate().equals(entity.getPlantNotificationsEntity().getDate()) 
            		&& entity.getPlantNotificationsEntity().getNotificationMsg().equalsIgnoreCase("Additional Time for Request") 
            		&& entity.getPlantNotificationsEntity().getNotificationStatus().equalsIgnoreCase("Approved")) {
            	plantTransferReqApprTO.setAddlTimeFlag(true);
            } else {
            	plantTransferReqApprTO.setAddlTimeFlag(false);
            }
            if(notifyEntity.getId().equals(entity.getPlantNotificationsEntity().getId()) && entity.getPlantNotificationsEntity().getNotificationStatus().equalsIgnoreCase("Approved")) {
            	plantTransferReqApprTO.setAddlTimeFlag(true);
            }
        }
        plantTransferReqApprTO.setApprStatus(entity.getApprStatus());

        return plantTransferReqApprTO;
    }

    public static PlantTransferReqApprEntity convertPOJOToEntity(PlantTransferReqApprTO plantTransferReqApprTO,
            EPSProjRepository epsProjRepository, UserMstrEntityRepository userMstrEntityRepository,
            PlantNotificationRepository plantNotificationsRepository) {
        PlantTransferReqApprEntity entity = new PlantTransferReqApprEntity();
        if (CommonUtil.isNonBlankLong(plantTransferReqApprTO.getId())) {
            entity.setId(plantTransferReqApprTO.getId());
        }
        entity.setFromProjId((plantTransferReqApprTO.getFromProjId() != null)
                ? epsProjRepository.findOne(plantTransferReqApprTO.getFromProjId()) : null);
        entity.setToProjId((plantTransferReqApprTO.getToProjId() != null)
                ? epsProjRepository.findOne(plantTransferReqApprTO.getToProjId()) : null);
        if (CommonUtil.isBlankStr(plantTransferReqApprTO.getReqDate())) {
            entity.setReqDate(new Date());
        } else {
            entity.setReqDate(CommonUtil.convertStringToDate(plantTransferReqApprTO.getReqDate()));
        }
        if (CommonUtil.isNotBlankStr(plantTransferReqApprTO.getApprDate())) {
            entity.setApprDate(CommonUtil.convertStringToDate(plantTransferReqApprTO.getApprDate()));
        }
        if (CommonUtil.objectNotNull(plantTransferReqApprTO.getReqUserTO())
                && CommonUtil.isNonBlankLong(plantTransferReqApprTO.getReqUserTO().getUserId())) {
            UserMstrEntity reqUserMstrEnitty = userMstrEntityRepository
                    .findOne(plantTransferReqApprTO.getReqUserTO().getUserId());
            entity.setReqUserMstrEnitty(reqUserMstrEnitty);
        }
        if (CommonUtil.objectNotNull(plantTransferReqApprTO.getApprUserTO())
                && CommonUtil.isNonBlankLong(plantTransferReqApprTO.getApprUserTO().getUserId())) {
            UserMstrEntity apprUserMstrEntity = userMstrEntityRepository
                    .findOne(plantTransferReqApprTO.getApprUserTO().getUserId());
            entity.setApprUserMstrEntity(apprUserMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(plantTransferReqApprTO.getNotifyId())) {
            PlantNotificationsEntity plantNotificationsEntity = plantNotificationsRepository
                    .findOne(plantTransferReqApprTO.getNotifyId());
            entity.setPlantNotificationsEntity(plantNotificationsEntity);
        }
        entity.setNotificationStatus(plantTransferReqApprTO.getApprStatus());
        entity.setApprStatus(plantTransferReqApprTO.getApprStatus());
        entity.setApprCurrentProj(plantTransferReqApprTO.getApprCurrentProj());
        entity.setReqCurrentProj(plantTransferReqApprTO.getReqCurrentProj());
        entity.setStatus(plantTransferReqApprTO.getStatus());

        return entity;
    }

    private static String generateReqCode(PlantTransferReqApprEntity entity) {
        return ModuleCodesPrefixes.PLANT_REG_PREFIX.getDesc().concat("-")
                .concat(ModuleCodesPrefixes.REQUEST_PREFIX.getDesc()).concat("-").concat(entity.getToProjId().getCode())
                .concat("-").concat(String.valueOf(entity.getId()));
    }

    private static String generateApprCode(PlantTransferReqApprEntity entity) {
        return ModuleCodesPrefixes.PLANT_REG_PREFIX.getDesc().concat("-")
                .concat(ModuleCodesPrefixes.APPROVE_PREFIX.getDesc()).concat("-").concat(entity.getToProjId().getCode())
                .concat("-").concat(String.valueOf(entity.getId()));
    }

}
