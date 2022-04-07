package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.resp.MeasureUnitResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class TangibleHandler {

    public static MeasureUnitResp convertEntityToPOJO(List<MeasurmentMstrEntity> entities) {
        MeasureUnitResp measuringUnitResp = new MeasureUnitResp();
        MeasureUnitTO measurementTO = null;
        for (MeasurmentMstrEntity measurmentMstrEntity : entities) {
            measurementTO = convertMeasurePOJOFromEnity(measurmentMstrEntity);
            measuringUnitResp.getMeasureUnitTOs().add(measurementTO);
        }
        return measuringUnitResp;
    }

    public static MeasureUnitTO convertMeasurePOJOFromEnity(MeasurmentMstrEntity measurmentMstrEntity) {

        MeasureUnitTO measurementTO = new MeasureUnitTO();
        if (CommonUtil.objectNotNull(measurmentMstrEntity)) {
            measurementTO.setId(measurmentMstrEntity.getId());
            measurementTO.setCode(measurmentMstrEntity.getCode());
            measurementTO.setName(measurmentMstrEntity.getName());
            measurementTO.setDispName(
                    CommonUtil.concatCodeName(measurmentMstrEntity.getCode(), measurmentMstrEntity.getName()));
            if (CommonUtil.objectNotNull(measurmentMstrEntity.getClientId())) {
                measurementTO.setClientId(measurmentMstrEntity.getClientId().getClientId());
            }
            measurementTO.setProcureClassName(measurmentMstrEntity.getProcureClassName());
            measurementTO.setStatus(measurmentMstrEntity.getStatus());
        }
        return measurementTO;
    }

    public static List<MeasurmentMstrEntity> convertPOJOToEntity(List<MeasureUnitTO> measuringUnitTOs) {
        List<MeasurmentMstrEntity> measurmentMstrEntities = new ArrayList<MeasurmentMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        MeasurmentMstrEntity measurmentMstrEntity = null;
        for (MeasureUnitTO measuringUnitTO : measuringUnitTOs) {
            measurmentMstrEntity = new MeasurmentMstrEntity();
            if (CommonUtil.isNonBlankLong(measuringUnitTO.getId())) {
                measurmentMstrEntity.setId(measuringUnitTO.getId());
            }
            measurmentMstrEntity.setCode(measuringUnitTO.getCode());
            measurmentMstrEntity.setName(measuringUnitTO.getName());
            if (CommonUtil.objectNotNull(measuringUnitTO.getProcurementTO())) {
                measurmentMstrEntity.setProcureClassName(measuringUnitTO.getProcurementTO().getName());
            }
            measurmentMstrEntity.setStatus(measuringUnitTO.getStatus());
            measurmentMstrEntities.add(measurmentMstrEntity);
        }
        return measurmentMstrEntities;
    }

}
