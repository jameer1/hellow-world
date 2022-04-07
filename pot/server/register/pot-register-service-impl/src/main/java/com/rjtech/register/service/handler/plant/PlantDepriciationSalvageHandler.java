package com.rjtech.register.service.handler.plant;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.plant.dto.PlantDepriciationSalvageTO;
import com.rjtech.register.plant.model.PlantDepriciationSalvageEntity;
import com.rjtech.register.plant.model.PlantRegProjEntity;
import com.rjtech.register.repository.plant.PlantLogRecordRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;

public class PlantDepriciationSalvageHandler {

    public static PlantDepriciationSalvageTO convertEntityToPOJO(PlantDepriciationSalvageEntity entity) {
        PlantDepriciationSalvageTO salvageTO = new PlantDepriciationSalvageTO();
        salvageTO.setId(entity.getId());

        if (CommonUtil.objectNotNull(entity.getPlantLogRecordsEntity())) {
            salvageTO.setPlantLogId(entity.getPlantLogRecordsEntity().getId());
            salvageTO.setEndMeterReading(entity.getPlantLogRecordsEntity().getEndMeter());
        }

        salvageTO.setPlantRegProjTO(PlantRegProjDtlHandler.convertEntityToPOJO(entity.getPlantRegProjEntity()));
        salvageTO.setProjGenId((entity.getProjGenId() != null) ? entity.getProjGenId().getId() : null);
        salvageTO.setEstimateLife(entity.getEstimateLife());
        salvageTO.setRemainingLife(entity.getRemainingLife());
        salvageTO.setOrginalValue(entity.getOrginalValue());
        salvageTO.setDepriciationValue(entity.getDepriciationValue());
        salvageTO.setRemaining(entity.getRemaining());
        salvageTO.setSalvageValue(entity.getSalvageValue());
        salvageTO.setComments(entity.getComments());
        salvageTO.setStatus(entity.getStatus());
        salvageTO.setLatest(entity.isLatest());
        if (CommonUtil.isNotBlankDate(entity.getEffectiveFrom())) {
            salvageTO.setEffectiveFrom(CommonUtil.convertDateToString(entity.getEffectiveFrom()));
        }
        if (CommonUtil.isNotBlankDate(entity.getEffectiveTo())) {
            salvageTO.setEffectiveTO(CommonUtil.convertDateToString(entity.getEffectiveTo()));
        }
        return salvageTO;

    }

    public static PlantDepriciationSalvageEntity convertPOJOToEntity(PlantDepriciationSalvageEntity entity,
            PlantDepriciationSalvageTO salvageTO, PlantRegisterRepository plantRegisterRepository,
            PlantLogRecordRepository plantLogRecordRepository, ProjGeneralRepositoryCopy projGeneralRepository) {

        if (CommonUtil.isNonBlankLong(salvageTO.getId())) {
            entity.setId(salvageTO.getId());
        }
        PlantRegProjEntity plantRegProjEntity = new PlantRegProjEntity();
        plantRegProjEntity.setId(salvageTO.getPlantRegProjTO().getId());
        entity.setPlantRegProjEntity(plantRegProjEntity);
        entity.setPlantId((salvageTO.getPlantRegProjTO().getPlantId() != null)
                ? plantRegisterRepository.findOne(salvageTO.getPlantRegProjTO().getPlantId())
                : null);
        entity.setPlantLogRecordsEntity(
                (salvageTO.getPlantLogId() != null) ? plantLogRecordRepository.findOne(salvageTO.getPlantLogId())
                        : null);
        entity.setProjGenId(
                (salvageTO.getProjGenId() != null) ? projGeneralRepository.findOne(salvageTO.getProjGenId()) : null);
        entity.setEstimateLife(salvageTO.getEstimateLife());
        entity.setRemainingLife(salvageTO.getRemainingLife());
        entity.setOrginalValue(salvageTO.getOrginalValue());
        entity.setDepriciationValue(salvageTO.getDepriciationValue());
        entity.setRemaining(salvageTO.getRemaining());
        entity.setSalvageValue(salvageTO.getSalvageValue());
        entity.setComments(salvageTO.getComments());
        entity.setStatus(StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isNotBlankStr(salvageTO.getEffectiveFrom())) {
            entity.setEffectiveFrom(CommonUtil.convertStringToDate(salvageTO.getEffectiveFrom()));
        }
        return entity;
    }
}
