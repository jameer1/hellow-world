package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.service.handler.PlantClassHandler;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjPlantClassTO;
import com.rjtech.projectlib.model.ProjPlantClassMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;

public class ProjPlantClassHandler {

    public static ProjPlantClassTO populateProjPlants(PlantMstrEntity plantMstrEntity,
            Map<Long, ProjPlantClassMstrEntity> projPlantMap) {
        ProjPlantClassTO projPlantClassTO = new ProjPlantClassTO();
        ProjPlantClassMstrEntity projPlantClassMstrEntity = null;
        projPlantClassTO.setPlantClassTO(PlantClassHandler.converPlantClassEntityToPOJO(plantMstrEntity));
        if (projPlantMap.get(plantMstrEntity.getId()) != null) {
            projPlantClassMstrEntity = projPlantMap.get(plantMstrEntity.getId());
            projPlantClassTO.setId(projPlantClassMstrEntity.getId());
            ProjMstrEntity project = projPlantClassMstrEntity.getProjId();
            if (project != null)
                projPlantClassTO.setProjId(project.getProjectId());
            projPlantClassTO.setPlantContrName(projPlantClassMstrEntity.getPlantContrName());
            projPlantClassTO.setStatus(projPlantClassMstrEntity.getStatus());
        }

        return projPlantClassTO;
    }

    public static List<ProjPlantClassMstrEntity> convertPOJOToEntity(List<ProjPlantClassTO> projPlantClassTOs,
            Long projId, EPSProjRepository epsProjRepository, PlantClassRepository plantClassRepository) {
        List<ProjPlantClassMstrEntity> projPlantClassMstrEntities = new ArrayList<ProjPlantClassMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjPlantClassMstrEntity entity = null;
        for (ProjPlantClassTO projPlantClassTO : projPlantClassTOs) {
            entity = new ProjPlantClassMstrEntity();
            if (CommonUtil.isNonBlankLong(projPlantClassTO.getId())) {
                entity.setId(projPlantClassTO.getId());
            }
            entity.setPlantMstrEntity(plantClassRepository.findOne(projPlantClassTO.getPlantClassTO().getId()));
            entity.setProjId(epsProjRepository.findOne(projId));
            entity.setPlantContrName(projPlantClassTO.getPlantContrName());
            entity.setStatus(projPlantClassTO.getStatus());
            projPlantClassMstrEntities.add(entity);
        }
        return projPlantClassMstrEntities;
    }

    public static LabelKeyTO convertEntityTOLabelKeyTO(ProjPlantClassMstrEntity projPlantClassMstrEntity) {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        if (CommonUtil.objectNotNull(projPlantClassMstrEntity.getPlantMstrEntity())) {
            labelKeyTO.setId(projPlantClassMstrEntity.getPlantMstrEntity().getId());

        }
        labelKeyTO.setCode(projPlantClassMstrEntity.getPlantMstrEntity().getCode());
        labelKeyTO.setName(projPlantClassMstrEntity.getPlantContrName());
        if (CommonUtil.objectNotNull(projPlantClassMstrEntity.getPlantMstrEntity())
                && CommonUtil.objectNotNull(projPlantClassMstrEntity.getPlantMstrEntity().getMeasurmentMstrEntity())
                && CommonUtil.isNotBlankStr(
                        projPlantClassMstrEntity.getPlantMstrEntity().getMeasurmentMstrEntity().getName())) {
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.CLASSIFICATION_MEASURE_UNIT,
                    projPlantClassMstrEntity.getPlantMstrEntity().getMeasurmentMstrEntity().getName());
        }
        return labelKeyTO;
    }

}
