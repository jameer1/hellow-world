package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.req.EmpClassesSaveReq;
import com.rjtech.centrallib.resp.EmpClassesResp;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class EmpClassHandler {

    public static EmpClassesResp convertEntityToPOJO(List<EmpClassMstrEntity> entities) {
        EmpClassesResp empClassesResp = new EmpClassesResp();
        for (EmpClassMstrEntity entity : entities) {
            empClassesResp.getEmpClassTOs().add(convertEmpClassEntityToPOJO(entity));
        }
        return empClassesResp;
    }

    public static EmpClassTO convertEmpClassEntityToPOJO(EmpClassMstrEntity entity) {
        EmpClassTO empClassTO = new EmpClassTO();
        empClassTO.setId(entity.getId());
        empClassTO.setCode(entity.getCode());
        empClassTO.setName(entity.getName());
        empClassTO.setDispName(CommonUtil.concatCodeName(entity.getCode(), entity.getName()));
        if (CommonUtil.objectNotNull(entity.getClientId())) {
            empClassTO.setClientId(entity.getClientId().getClientId());
        }
        if (CommonUtil.objectNotNull(entity.getMeasurmentMstrEntity())) {
            empClassTO
                    .setMeasureUnitTO(MeasurementHandler.convertMeasurePOJOFromEnity(entity.getMeasurmentMstrEntity()));
        }
        empClassTO.setStatus(entity.getStatus());
        return empClassTO;
    }

    public static List<EmpClassMstrEntity> convertPOJOToEntity(EmpClassesSaveReq empClassesSaveReq,
            MeasurementRepository measurementRepository) {
        List<EmpClassMstrEntity> empClassMstrEntities = new ArrayList<EmpClassMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        EmpClassMstrEntity entity = null;
        for (EmpClassTO empClassTO : empClassesSaveReq.getEmpClassTOs()) {
            entity = new EmpClassMstrEntity();
            if (CommonUtil.isNonBlankLong(empClassTO.getId())) {
                entity.setId(empClassTO.getId());
            }
            entity.setCode(empClassTO.getCode());
            entity.setName(empClassTO.getName());

            if (CommonUtil.objectNotNull(empClassTO.getMeasureUnitTO())) {
                MeasurmentMstrEntity measurmentMstrEntity = measurementRepository
                        .findOne(empClassTO.getMeasureUnitTO().getId());
                entity.setMeasurmentMstrEntity(measurmentMstrEntity);
            }
            entity.setStatus(empClassTO.getStatus());
            empClassMstrEntities.add(entity);
        }
        return empClassMstrEntities;
    }

    public static LabelKeyTO convertEmpClassMstrEntityToLabelKeyTo(EmpClassMstrEntity empClassMstrEntity) {

        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(empClassMstrEntity.getId());
        labelKeyTO.setCode(empClassMstrEntity.getCode());
        labelKeyTO.setName(empClassMstrEntity.getName());
        if (CommonUtil.objectNotNull(empClassMstrEntity.getMeasurmentMstrEntity())) {
            labelKeyTO.setCode(empClassMstrEntity.getCode());
            labelKeyTO.setName(empClassMstrEntity.getName());
        }

        return labelKeyTO;
    }
}
