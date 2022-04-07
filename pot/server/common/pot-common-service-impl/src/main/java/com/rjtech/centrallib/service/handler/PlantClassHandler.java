package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.PlantClassTO;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.req.PlantClassSavereq;
import com.rjtech.centrallib.resp.PlantClassResp;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class PlantClassHandler {

    public static PlantClassResp convertEntityToPOJO(List<PlantMstrEntity> entities) {
        PlantClassResp plantClassResp = new PlantClassResp();
        PlantClassTO classificationTO = null;
        for (PlantMstrEntity entity : entities) {
            classificationTO = converPlantClassEntityToPOJO(entity);
            plantClassResp.getPlantClassTOs().add(classificationTO);
        }
        return plantClassResp;
    }

    public static PlantClassTO converPlantClassEntityToPOJO(PlantMstrEntity entity) {
        PlantClassTO classificationTO = new PlantClassTO();
        classificationTO.setId(entity.getId());
        classificationTO.setCode(entity.getCode());
        classificationTO.setName(entity.getName());
        classificationTO.setDispName(CommonUtil.concatCodeName(entity.getCode(), entity.getName()));
        classificationTO.setDispName(CommonUtil.concatCodeName(entity.getCode(), entity.getName()));
        classificationTO
                .setMeasureUnitTO(MeasurementHandler.convertMeasurePOJOFromEnity(entity.getMeasurmentMstrEntity()));
        if (null != entity.getClientId()) {
            classificationTO.setClientId(entity.getClientId().getClientId());
        }
        classificationTO.setStatus(entity.getStatus());
        return classificationTO;
    }

    public static List<PlantMstrEntity> convertPOJOToEntity(PlantClassSavereq plantClassSavereq,
            MeasurementRepository measurementRepository) {
        List<PlantMstrEntity> plantMstrEntities = new ArrayList<PlantMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        PlantMstrEntity entity = null;
        for (PlantClassTO classificationTO : plantClassSavereq.getPlantClassTOs()) {
            entity = new PlantMstrEntity();
            if (CommonUtil.isNonBlankLong(classificationTO.getId())) {
                entity.setId(classificationTO.getId());
            }
            entity.setCode(classificationTO.getCode());
            entity.setName(classificationTO.getName());

            if (CommonUtil.objectNotNull(classificationTO.getMeasureUnitTO())) {
                MeasurmentMstrEntity measurmentMstrEntity = measurementRepository
                        .findOne(classificationTO.getMeasureUnitTO().getId());
                entity.setMeasurmentMstrEntity(measurmentMstrEntity);
            }
            entity.setStatus(classificationTO.getStatus());
            plantMstrEntities.add(entity);
        }
        return plantMstrEntities;
    }

    public static LabelKeyTO converPlantClassEntityLabelKeyTO(PlantMstrEntity entity) {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getMeasurmentMstrEntity())) {
            labelKeyTO.setCode(entity.getCode());
            labelKeyTO.setName(entity.getName());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.CLASSIFICATION_MEASURE_UNIT,
                    entity.getMeasurmentMstrEntity().getName());
        }
        return labelKeyTO;
    }
}
