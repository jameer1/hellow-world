package com.rjtech.register.service.handler.plant;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.PlantServiceClassificationMstrEntity;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.PlantServiceClassRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntity;
import com.rjtech.register.plant.dto.PlantRegProjTO;
import com.rjtech.register.plant.dto.PlantRepairsTO;
import com.rjtech.register.plant.model.PlantRepairsEntity;
import com.rjtech.register.repository.material.MaterialDockSchItemRepository;
import com.rjtech.register.repository.plant.PlantRegProjRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;

public class PlantRepairHandler {

    public static PlantRepairsTO convertEntityToPOJO(PlantRepairsEntity entity) {

        PlantRepairsTO plantRepairsTO = new PlantRepairsTO();

        plantRepairsTO.setId(entity.getId());
        plantRepairsTO.setPlantId(entity.getPlantId().getId());

        if (CommonUtil.isNotBlankDate(entity.getDate())) {
            plantRepairsTO.setDate(CommonUtil.convertDateToString(entity.getDate()));
        }
        PlantRegProjTO plantRegProjTO = new PlantRegProjTO();
        plantRegProjTO.setId(entity.getPlantProjId().getId());
        plantRepairsTO.setPlantRegProjTO(plantRegProjTO);
        plantRepairsTO.setOdoMeter(entity.getOdoMeter());
        PlantServiceClassificationMstrEntity servicePlant = entity.getPlantServiceClassificationMstrEntity();
        if (CommonUtil.objectNotNull(servicePlant)) {
            plantRepairsTO.setServiceId(servicePlant.getId());
            plantRepairsTO.setServiceName(servicePlant.getName());
            PlantServiceClassificationMstrEntity parent = servicePlant.getPlantServiceClassificationMstrEntity();
            if (parent != null)
                plantRepairsTO.setServiceParentName(parent.getName());
        }
        MaterialClassMstrEntity material = entity.getMaterialId();
        if (CommonUtil.objectNotNull(material)) {
            plantRepairsTO.setMaterialId(material.getId());
            plantRepairsTO.setMaterialName(material.getName());
            MeasurmentMstrEntity measure = material.getMeasurmentMstrEntity();
            if (measure != null)
                plantRepairsTO.setMaterialUnit(measure.getName());
        }
        plantRepairsTO.setQuantity(entity.getQuantity());
        plantRepairsTO.setProjDocket(entity.getProjDocket());
        MaterialProjDocketSchItemsEntity projDock = entity.getProjDockSchId();
        if (CommonUtil.objectNotNull(projDock)) {
            plantRepairsTO.setProjDockSchId(projDock.getId());
        }
        plantRepairsTO.setComments(entity.getComments());

        plantRepairsTO.setStatus(entity.getStatus());

        return plantRepairsTO;
    }

    public static List<PlantRepairsEntity> populatePlantRepairEntities(List<PlantRepairsTO> plantRepairsTOs,
            PlantRegisterRepository plantRegisterRepository, PlantServiceClassRepository plantServiceClassRepository,
            MaterialClassRepository materialClassRepository,
            MaterialDockSchItemRepository materialDockSchItemRepository,
            PlantRegProjRepository plantRegProjRepository) {
        List<PlantRepairsEntity> plantRepairsEntites = new ArrayList<PlantRepairsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (PlantRepairsTO plantRepairsTO : plantRepairsTOs) {
            plantRepairsEntites
                    .add(convertPOJOToEntity(plantRepairsTO, plantRegisterRepository, plantServiceClassRepository,
                            materialClassRepository, materialDockSchItemRepository, plantRegProjRepository));
        }
        return plantRepairsEntites;
    }

    public static PlantRepairsEntity convertPOJOToEntity(PlantRepairsTO plantRepairsTO,
            PlantRegisterRepository plantRegisterRepository, PlantServiceClassRepository plantServiceClassRepository,
            MaterialClassRepository materialClassRepository,
            MaterialDockSchItemRepository materialDockSchItemRepository,
            PlantRegProjRepository plantRegProjRepository) {
        PlantRepairsEntity entity = new PlantRepairsEntity();

        if (CommonUtil.isNonBlankLong(plantRepairsTO.getId())) {
            entity.setId(plantRepairsTO.getId());
        }

        entity.setPlantId(
                (plantRepairsTO.getPlantId() != null) ? plantRegisterRepository.findOne(plantRepairsTO.getPlantId())
                        : null);
        if (CommonUtil.isNotBlankStr(plantRepairsTO.getDate())) {
            entity.setDate(CommonUtil.convertStringToDate(plantRepairsTO.getDate()));
        }
        entity.setOdoMeter(plantRepairsTO.getOdoMeter());
        entity.setPlantServiceClassificationMstrEntity((plantRepairsTO.getServiceId() != null)
                ? plantServiceClassRepository.findOne(plantRepairsTO.getServiceId())
                : null);
        entity.setMaterialId((plantRepairsTO.getMaterialId() != null)
                ? materialClassRepository.findOne(plantRepairsTO.getMaterialId())
                : null);
        entity.setQuantity(plantRepairsTO.getQuantity());
        entity.setProjDocket(plantRepairsTO.getProjDocket());
        entity.setProjDockSchId((plantRepairsTO.getProjDockSchId() != null)
                ? materialDockSchItemRepository.findOne(plantRepairsTO.getProjDockSchId())
                : null);
        entity.setComments(plantRepairsTO.getComments());

        entity.setStatus(plantRepairsTO.getStatus());
        if (CommonUtil.objectNotNull(plantRepairsTO.getPlantRegProjTO())) {
            entity.setPlantProjId(plantRegProjRepository.findOne(plantRepairsTO.getPlantRegProjTO().getId()));
        }

        return entity;
    }

}
