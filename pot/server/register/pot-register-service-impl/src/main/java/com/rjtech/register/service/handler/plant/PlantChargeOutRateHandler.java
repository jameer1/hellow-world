package com.rjtech.register.service.handler.plant;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
import com.rjtech.register.plant.dto.PlantChargeOutRatesTO;
import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.model.PlantChargeOutRatesEntity;
import com.rjtech.register.plant.model.PlantRegProjEntity;

public class PlantChargeOutRateHandler {

    public static PlantChargeOutRatesTO convertEntityToPOJO(PlantChargeOutRatesEntity entity) {
        PlantChargeOutRatesTO plantChargeOutRatesTO = new PlantChargeOutRatesTO();
        plantChargeOutRatesTO.setId(entity.getId());
        plantChargeOutRatesTO.setRateWithFualNRShift(entity.getRateWithFualNRShift());
        plantChargeOutRatesTO.setRateWithOutFualNRShift(entity.getRateWithOutFualNRShift());
        plantChargeOutRatesTO.setChargeOutRates(entity.getChargeOutRates());
        ProjCostItemEntity projMobCostItem = entity.getProjMobCostItem();
        if (CommonUtil.objectNotNull(projMobCostItem)) {
            plantChargeOutRatesTO.setProjMobCostItemId(projMobCostItem.getId());
            plantChargeOutRatesTO.setProjMobCostItemCode(projMobCostItem.getCode());
        }
        ProjCostItemEntity projDemobCostItem = entity.getProjDemobCostItem();
        if (CommonUtil.objectNotNull(projDemobCostItem)) {
            plantChargeOutRatesTO.setProjDeMobCostItemId(projDemobCostItem.getId());
            plantChargeOutRatesTO.setProjDeMobCostItemCode(projDemobCostItem.getCode());
        }
        plantChargeOutRatesTO.setCommentes(entity.getCommentes());
        plantChargeOutRatesTO.setRateWithFualDBShift(entity.getRateWithFualDBShift());
        plantChargeOutRatesTO.setRateWithoutFualDBShift(entity.getRateWithoutFualDBShift());
        plantChargeOutRatesTO.setMobChargeOutRate(entity.getMobChargeOutRate());
        plantChargeOutRatesTO.setDeMobChargeOutRate(entity.getDeMobChargeOutRate());
        plantChargeOutRatesTO.setCategory(entity.getCategory());
        plantChargeOutRatesTO.setProjGenId((entity.getProjGenId() != null) ? entity.getProjGenId().getId() : null);
        plantChargeOutRatesTO.setIdleChargeOutRate(entity.getIdleChargeOutRate());
        plantChargeOutRatesTO.setLatest(entity.isLatest());
        PlantRegProjTO plantRegProjTO = PlantRegProjDtlHandler.convertEntityToPOJO(entity.getPlantRegProjEntity());
        plantChargeOutRatesTO.setPlantRegProjTO(plantRegProjTO);
        if (CommonUtil.isNotBlankDate(entity.getEffectiveFrom())) {
            plantChargeOutRatesTO.setEffectiveFrom(CommonUtil.convertDateToString(entity.getEffectiveFrom()));
        }
        if (CommonUtil.isNotBlankDate(entity.getEffectiveTo())) {
            plantChargeOutRatesTO.setEffectiveTO(CommonUtil.convertDateToString(entity.getEffectiveTo()));
        }

        plantChargeOutRatesTO.setStatus(entity.getStatus());

        return plantChargeOutRatesTO;
    }

    public static PlantChargeOutRatesEntity convertPOJOToEntity(PlantChargeOutRatesTO plantChargeOutRatesTO,
            ProjGeneralRepositoryCopy projGeneralRepository, ProjCostItemRepositoryCopy projCostItemRepository) {

        PlantChargeOutRatesEntity entity = new PlantChargeOutRatesEntity();
        entity.setRateWithFualNRShift(plantChargeOutRatesTO.getRateWithFualNRShift());
        entity.setRateWithOutFualNRShift(plantChargeOutRatesTO.getRateWithOutFualNRShift());
        entity.setChargeOutRates(plantChargeOutRatesTO.getChargeOutRates());
        if (CommonUtil.isNonBlankLong(plantChargeOutRatesTO.getProjMobCostItemId())) {
            ProjCostItemEntity projMob = projCostItemRepository
                    .findOne(plantChargeOutRatesTO.getProjMobCostItemId());
            entity.setProjMobCostItem(projMob);
        }
        if (CommonUtil.isNonBlankLong(plantChargeOutRatesTO.getProjDeMobCostItemId())) {
            ProjCostItemEntity projDemob = projCostItemRepository
                    .findOne(plantChargeOutRatesTO.getProjDeMobCostItemId());
            entity.setProjDemobCostItem(projDemob);
        }
        entity.setCommentes(plantChargeOutRatesTO.getCommentes());
        entity.setRateWithFualDBShift(plantChargeOutRatesTO.getRateWithFualDBShift());
        entity.setRateWithoutFualDBShift(plantChargeOutRatesTO.getRateWithoutFualDBShift());
        entity.setMobChargeOutRate(plantChargeOutRatesTO.getMobChargeOutRate());
        entity.setDeMobChargeOutRate(plantChargeOutRatesTO.getDeMobChargeOutRate());
        entity.setCategory(plantChargeOutRatesTO.getCategory());
        entity.setProjGenId((plantChargeOutRatesTO.getProjGenId() != null)
                ? projGeneralRepository.findOne(plantChargeOutRatesTO.getProjGenId())
                : null);
        entity.setIdleChargeOutRate(plantChargeOutRatesTO.getIdleChargeOutRate());
        PlantRegProjEntity plantRegProjEntity = new PlantRegProjEntity();
        plantRegProjEntity.setId(plantChargeOutRatesTO.getPlantRegProjTO().getId());
        entity.setPlantRegProjEntity(plantRegProjEntity);
        entity.setLatest(plantChargeOutRatesTO.isLatest());
        if (CommonUtil.isNotBlankStr(plantChargeOutRatesTO.getEffectiveFrom())) {
            entity.setEffectiveFrom(CommonUtil.convertStringToDate(plantChargeOutRatesTO.getEffectiveFrom()));
        }

        entity.setStatus(plantChargeOutRatesTO.getStatus());
        return entity;
    }

}
