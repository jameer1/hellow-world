package com.rjtech.register.service.handler.plant;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.plant.dto.PlantTransReqApprDtlTO;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.register.plant.model.PlantTransReqApprDtlEntity;
import com.rjtech.register.repository.plant.PlantRegisterRepository;

public class PlantTransReqApprDtlHandler {

    public static PlantTransReqApprDtlTO convertEntityToPOJO(PlantTransReqApprDtlEntity entity) {
        PlantTransReqApprDtlTO plantTransReqApprDtlTO = new PlantTransReqApprDtlTO();
        plantTransReqApprDtlTO.setId(entity.getId());
        PlantRegisterDtlEntity plant = entity.getPlantId();
        if (plant != null) {
            plantTransReqApprDtlTO.setPlantId(plant.getId());
            plantTransReqApprDtlTO.setPlantAssetId(plant.getAssertId());
            plantTransReqApprDtlTO.setPlantDesc(plant.getDesc());
            plantTransReqApprDtlTO.setPlantManfature(plant.getManfacture());
            plantTransReqApprDtlTO.setPlantModel(plant.getModel());
            plantTransReqApprDtlTO.setPlantRegNo(plant.getRegNumber());
        }
        if (entity.getProjId() != null)
            plantTransReqApprDtlTO.setProjId(entity.getProjId().getProjectId());
        plantTransReqApprDtlTO.setPlantTransId(entity.getPlantTransferReqApprEntity().getId());
        if (entity.getReceivedBy() != null)
            plantTransReqApprDtlTO.setReceivedBy(entity.getReceivedBy().getFirstName());
        plantTransReqApprDtlTO.setTransDate(CommonUtil.convertDateToString(entity.getTransDate()));
        plantTransReqApprDtlTO.setExpectedTransDate(CommonUtil.convertDateToString(entity.getExpectedTransDate()));
        plantTransReqApprDtlTO.setPlantTransDate(CommonUtil.convertDateToString(entity.getPlantTransDate()));
        plantTransReqApprDtlTO.setActualDeliveryDate(CommonUtil.convertDateToString(entity.getActualDeliveryDate()));
        plantTransReqApprDtlTO.setPlantTransComments(entity.getPlantTransComments());
        plantTransReqApprDtlTO.setComments(entity.getComments());
        plantTransReqApprDtlTO.setApprStatus(entity.getApprStatus());
        plantTransReqApprDtlTO.setStatus(entity.getStatus());
        return plantTransReqApprDtlTO;
    }

    public static PlantTransReqApprDtlEntity convertPOJOToEntity(PlantTransReqApprDtlTO plantTransReqApprDtlTO,
            PlantRegisterRepository plantRegisterRepository, EPSProjRepository epsProjRepository) {
        PlantTransReqApprDtlEntity entity = new PlantTransReqApprDtlEntity();
        if (CommonUtil.isNonBlankLong(plantTransReqApprDtlTO.getId())) {
            entity.setId(plantTransReqApprDtlTO.getId());
        }
        entity.setPlantId((plantTransReqApprDtlTO.getPlantId() != null)
                ? plantRegisterRepository.findOne(plantTransReqApprDtlTO.getPlantId())
                : null);
        entity.setProjId((plantTransReqApprDtlTO.getProjId() != null)
                ? epsProjRepository.findOne(plantTransReqApprDtlTO.getProjId())
                : null);
        entity.setTransDate(CommonUtil.convertStringToDate(plantTransReqApprDtlTO.getTransDate()));
        entity.setExpectedTransDate(CommonUtil.convertStringToDate(plantTransReqApprDtlTO.getExpectedTransDate()));
        entity.setPlantTransDate(CommonUtil.convertStringToDate(plantTransReqApprDtlTO.getPlantTransDate()));
        entity.setActualDeliveryDate(CommonUtil.convertStringToDate(plantTransReqApprDtlTO.getActualDeliveryDate()));
        entity.setPlantTransComments(plantTransReqApprDtlTO.getPlantTransComments());
        entity.setComments(plantTransReqApprDtlTO.getComments());
        entity.setApprStatus(plantTransReqApprDtlTO.getApprStatus());

        entity.setStatus(plantTransReqApprDtlTO.getStatus());
        return entity;
    }
}
