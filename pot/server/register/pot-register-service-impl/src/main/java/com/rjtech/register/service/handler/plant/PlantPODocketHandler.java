package com.rjtech.register.service.handler.plant;

import java.math.BigDecimal;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.plant.dto.PlantPODocketDtlTO;
import com.rjtech.register.plant.model.PlantPODocketDtlEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepository;

public class PlantPODocketHandler {
    public static PlantPODocketDtlTO convertEntityToPOJO(PlantPODocketDtlEntity entity) {
        PlantPODocketDtlTO plantDocketDtlTO = new PlantPODocketDtlTO();

        plantDocketDtlTO.setId(entity.getId());
        plantDocketDtlTO.setStartDate(CommonUtil.convertDateToString(entity.getStartDate()));
        plantDocketDtlTO.setEndDate(CommonUtil.convertDateToString(entity.getEndDate()));
        plantDocketDtlTO.setDocketNum(entity.getDocketNum());
        plantDocketDtlTO.setDeliveryLocation(entity.getDeliveryLocation());
        if (CommonUtil.objectNotNull(entity.getReceivedBy())) {
            plantDocketDtlTO.setReceivedBy(String.valueOf(entity.getReceivedBy().getId()));
            plantDocketDtlTO.setReceivedCode(entity.getReceivedBy().getCode());
        }
        plantDocketDtlTO.setReceiverComments(entity.getReceiverComments());
        plantDocketDtlTO.setOdoMeter(entity.getOdoMeter());
        plantDocketDtlTO.setStatus(entity.getStatus());
        plantDocketDtlTO.setQuantity(entity.getReceivedQty());
        plantDocketDtlTO.setDeliveryType(entity.getDeliveryType());
        plantDocketDtlTO.setDocKey(entity.getDocKey());
        plantDocketDtlTO.setFileName(entity.getDocumentFileName());
        return plantDocketDtlTO;
    }

    public static BigDecimal getCumulateQty(List<PlantPODocketDtlEntity> plantDocketDtlEntityList) {
        BigDecimal cumulateQty = new BigDecimal(0);

        for (PlantPODocketDtlEntity plantDocketDtlEntity : plantDocketDtlEntityList) {
            BigDecimal sum = new BigDecimal(0);
            sum = cumulateQty.add(plantDocketDtlEntity.getReceivedQty());
            cumulateQty = sum;

        }
        return cumulateQty;
    }

    public static PlantPODocketDtlEntity convertPOJOToEntity(PlantPODocketDtlTO plantDocketDtlTO,
            EmpRegisterRepository empRegisterRepository) {
        PlantPODocketDtlEntity entity = new PlantPODocketDtlEntity();
        if (CommonUtil.isNonBlankLong(plantDocketDtlTO.getId())) {
            entity.setId(plantDocketDtlTO.getId());
        }
        if (CommonUtil.isNotBlankStr(plantDocketDtlTO.getStartDate())) {
            entity.setStartDate(CommonUtil.convertStringToDate(plantDocketDtlTO.getStartDate()));
        }
        if (CommonUtil.isNotBlankStr(plantDocketDtlTO.getEndDate())) {
            entity.setEndDate(CommonUtil.convertStringToDate(plantDocketDtlTO.getEndDate()));
        }
        entity.setDeliveryLocation(plantDocketDtlTO.getDeliveryLocation());
        entity.setDocketNum(plantDocketDtlTO.getDocketNum());
        entity.setReceivedBy((plantDocketDtlTO.getReceivedBy() != null)
                ? empRegisterRepository.findOne(Long.valueOf(plantDocketDtlTO.getReceivedBy()))
                : null);
        entity.setReceiverComments(plantDocketDtlTO.getReceiverComments());
        entity.setOdoMeter(plantDocketDtlTO.getOdoMeter());
        entity.setStatus(plantDocketDtlTO.getStatus());
        entity.setReceivedQty(plantDocketDtlTO.getQuantity());
        entity.setDeliveryType(plantDocketDtlTO.getDeliveryType());

        return entity;
    }
}
