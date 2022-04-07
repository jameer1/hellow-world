package com.rjtech.register.service.handler.plant;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.PlantServiceClassificationMstrEntity;
import com.rjtech.centrallib.repository.PlantServiceClassRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.dto.PlantServiceHistoryTO;
import com.rjtech.register.plant.model.PlantServiceHistoryEntity;
import com.rjtech.register.repository.plant.PlantRegProjRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;

public class PlantServiceHistoryHandler {

    public static PlantServiceHistoryTO convertEntityToPOJO(PlantServiceHistoryEntity entity) {

        PlantServiceHistoryTO plantServiceHistoryTO = new PlantServiceHistoryTO();
        plantServiceHistoryTO.setId(entity.getId());
        PlantRegProjTO plantRegProjTO = new PlantRegProjTO();
        plantRegProjTO.setPlantId(entity.getPlantId().getId());
        plantRegProjTO.setId(entity.getPlantProjId().getId());
        plantServiceHistoryTO.setPlantRegProjTO(plantRegProjTO);
        if (CommonUtil.isNotBlankDate(entity.getDate())) {
            plantServiceHistoryTO.setDate(CommonUtil.convertDateToString(entity.getDate()));
        }
        PlantServiceClassificationMstrEntity currentPlant = entity.getCurrentPlantServiceId();
        if (CommonUtil.objectNotNull(currentPlant)) {
            plantServiceHistoryTO.setCurrentPlantServiceId(currentPlant.getId());
            plantServiceHistoryTO.setCurrentPlantServiceName(currentPlant.getName());
            PlantServiceClassificationMstrEntity parentCurrentPlant = currentPlant
                    .getPlantServiceClassificationMstrEntity();
            if (CommonUtil.objectNotNull(parentCurrentPlant))
                plantServiceHistoryTO.setCurrentPlantServiceParentName(parentCurrentPlant.getName());
        }
        PlantServiceClassificationMstrEntity prevPlant = entity.getPrevPlantServiceId();
        if (CommonUtil.objectNotNull(prevPlant)) {
            plantServiceHistoryTO.setPrevPlantServiceId(prevPlant.getId());
            plantServiceHistoryTO.setPrevPlantServiceName(prevPlant.getName());
            PlantServiceClassificationMstrEntity parentPrevPlant = prevPlant.getPlantServiceClassificationMstrEntity();
            if (CommonUtil.objectNotNull(parentPrevPlant))
                plantServiceHistoryTO.setPrevPlantServiceParentName(parentPrevPlant.getName());
        }
        plantServiceHistoryTO.setCurrentOdoMeter(entity.getCurrentOdoMeter());
        plantServiceHistoryTO.setPrevOdoMeter(entity.getPrevOdoMeter());
        plantServiceHistoryTO.setComments(entity.getComments());
        plantServiceHistoryTO.setStatus(entity.getStatus());
        return plantServiceHistoryTO;
    }

    public static List<PlantServiceHistoryEntity> populatePlantServiceHistoryEntities(
            List<PlantServiceHistoryTO> plantServiceHistoryTOs, PlantServiceClassRepository plantServiceClassRepository,
            PlantRegisterRepository plantRegisterRepository, PlantRegProjRepository plantRegProjRepository) {

        List<PlantServiceHistoryEntity> plantServiceHistoryEntites = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (PlantServiceHistoryTO plantServiceHistoryTO : plantServiceHistoryTOs) {
            plantServiceHistoryEntites.add(convertPOJOToEntity(plantServiceHistoryTO, plantServiceClassRepository,
                    plantRegisterRepository, plantRegProjRepository));
        }
        return plantServiceHistoryEntites;
    }

    public static PlantServiceHistoryEntity convertPOJOToEntity(PlantServiceHistoryTO plantServiceHistoryTO,
            PlantServiceClassRepository plantServiceClassRepository, PlantRegisterRepository plantRegisterRepository,
            PlantRegProjRepository plantRegProjRepository) {

        PlantServiceHistoryEntity entity = new PlantServiceHistoryEntity();

        if (CommonUtil.isNonBlankLong(plantServiceHistoryTO.getId())) {
            entity.setId(plantServiceHistoryTO.getId());
        }
        if (CommonUtil.isNotBlankStr(plantServiceHistoryTO.getDate())) {
            entity.setDate(CommonUtil.convertStringToDate(plantServiceHistoryTO.getDate()));
        }
        entity.setCurrentPlantServiceId((plantServiceHistoryTO.getCurrentPlantServiceId() != null)
                ? plantServiceClassRepository.findOne(plantServiceHistoryTO.getCurrentPlantServiceId())
                : null);
        entity.setPrevPlantServiceId((plantServiceHistoryTO.getPrevPlantServiceId() != null)
                ? plantServiceClassRepository.findOne(plantServiceHistoryTO.getPrevPlantServiceId())
                : null);
        entity.setCurrentOdoMeter(plantServiceHistoryTO.getCurrentOdoMeter());
        entity.setPrevOdoMeter(plantServiceHistoryTO.getPrevOdoMeter());
        entity.setComments(plantServiceHistoryTO.getComments());
        entity.setStatus(plantServiceHistoryTO.getStatus());

        if (CommonUtil.objectNotNull(plantServiceHistoryTO.getPlantRegProjTO())) {
            entity.setPlantId(plantRegisterRepository.findOne(plantServiceHistoryTO.getPlantRegProjTO().getPlantId()));
            entity.setPlantProjId(plantRegProjRepository.findOne(plantServiceHistoryTO.getPlantRegProjTO().getId()));
        }
        return entity;
    }

}
