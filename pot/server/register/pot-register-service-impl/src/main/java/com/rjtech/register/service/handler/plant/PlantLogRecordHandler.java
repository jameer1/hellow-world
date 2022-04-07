package com.rjtech.register.service.handler.plant;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.plant.dto.PlantLogRecordsTO;
import com.rjtech.register.plant.model.PlantLogRecordEntity;
import com.rjtech.register.plant.model.PlantRegProjEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.repository.plant.PlantRegProjRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;

public class PlantLogRecordHandler {

    public static PlantLogRecordsTO convertEntityToPOJO(PlantLogRecordEntity entity) {

        PlantLogRecordsTO plantLogRecordsTO = new PlantLogRecordsTO();
        plantLogRecordsTO.setId(entity.getId());

        if (CommonUtil.isNotBlankDate(entity.getFromDate())) {
            plantLogRecordsTO.setFromDate(CommonUtil.convertDateToString(entity.getFromDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getToDate())) {
            plantLogRecordsTO.setToDate(CommonUtil.convertDateToString(entity.getToDate()));
        }
        plantLogRecordsTO.setStartMeter(entity.getStartMeter());
        plantLogRecordsTO.setEndMeter(entity.getEndMeter());
        plantLogRecordsTO.setNetUnits(entity.getNetUnits());
        plantLogRecordsTO.setComments(entity.getComments());

        if (CommonUtil.objectNotNull(entity.getEmpRegisterDtlEntity())) {
            LabelKeyTO empLabelKeyTO = new LabelKeyTO();
            empLabelKeyTO.setId(entity.getEmpRegisterDtlEntity().getId());
            empLabelKeyTO.setCode(entity.getEmpRegisterDtlEntity().getCode());
            empLabelKeyTO.setName(CommonUtil.appendTwoString(entity.getEmpRegisterDtlEntity().getFirstName(),
                    entity.getEmpRegisterDtlEntity().getLastName(), ","));
            plantLogRecordsTO.setEmpLabelKeyTO(empLabelKeyTO);
        }
        plantLogRecordsTO.setPlantRegProjTO(PlantRegProjDtlHandler.convertEntityToPOJO(entity.getPlantRegProjEntity()));
        plantLogRecordsTO.setLatest(entity.isLatest());
        return plantLogRecordsTO;
    }

    public static PlantLogRecordEntity convertPOJOToEntity(PlantLogRecordsTO plantLogRecordsTO,
            PlantRegisterRepository plantRegisterRepository, PlantRegProjRepository plantRegProjRepository,
            EmpRegisterRepository empRegisterRepository) {
        PlantLogRecordEntity entity = new PlantLogRecordEntity();

        if (CommonUtil.isNotBlankStr(plantLogRecordsTO.getFromDate())) {
            entity.setFromDate(CommonUtil.convertStringToDate(plantLogRecordsTO.getFromDate()));
        }
        if (CommonUtil.isNotBlankStr(plantLogRecordsTO.getToDate())) {
            entity.setToDate(CommonUtil.convertStringToDate(plantLogRecordsTO.getToDate()));
        }
        entity.setPlantId((plantLogRecordsTO.getPlantRegProjTO().getPlantId() != null)
                ? plantRegisterRepository.findOne(plantLogRecordsTO.getPlantRegProjTO().getPlantId())
                : null);
        EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                .findOne(plantLogRecordsTO.getEmpLabelKeyTO().getId());
        entity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        PlantRegProjEntity plantRegProjEntity = plantRegProjRepository
                .findOne(plantLogRecordsTO.getPlantRegProjTO().getId());
        entity.setPlantRegProjEntity(plantRegProjEntity);
        entity.setStartMeter(plantLogRecordsTO.getStartMeter());
        entity.setEndMeter(plantLogRecordsTO.getEndMeter());
        entity.setNetUnits(plantLogRecordsTO.getNetUnits());
        entity.setComments(plantLogRecordsTO.getComments());
        entity.setStatus(StatusCodes.ACTIVE.getValue());

        return entity;
    }
}
