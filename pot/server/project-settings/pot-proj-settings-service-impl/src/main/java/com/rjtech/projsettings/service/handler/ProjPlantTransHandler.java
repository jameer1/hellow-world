package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjPlantTransTO;
import com.rjtech.projsettings.model.PlantTransNormalTimeEntity;

public class ProjPlantTransHandler {

    public static ProjPlantTransTO convertEntityToPOJO(PlantTransNormalTimeEntity entity) {
        ProjPlantTransTO projPlantTransTO = new ProjPlantTransTO();

        projPlantTransTO.setId(entity.getId());

        ProjMstrEntity projMstrEntity = entity.getProjId();

        if (null != projMstrEntity) {
            projPlantTransTO.setProjId(projMstrEntity.getProjectId());
        }
        projPlantTransTO.setPlantType(entity.getPlantType());
        projPlantTransTO.setCutOffDays(entity.getCutOffDays());
        projPlantTransTO.setCutOffTime(entity.getCutOffTime());
        projPlantTransTO.setDefaultStatus(entity.getDefaultStatus());
        projPlantTransTO.setCutOffHours(entity.getCutOffHours());
        projPlantTransTO.setCutOffMinutes(entity.getCutOffMinutes());

        projPlantTransTO.setStatus(entity.getStatus());
        return projPlantTransTO;

    }

    public static List<PlantTransNormalTimeEntity> convertPOJOToEntity(List<ProjPlantTransTO> projPlantTransTOs,
            EPSProjRepository epsProjRepository) {
        List<PlantTransNormalTimeEntity> projPlantTransEntites = new ArrayList<PlantTransNormalTimeEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        for (ProjPlantTransTO projPlantTransTO : projPlantTransTOs) {

            projPlantTransEntites.add(convertOnePojoToOneEntity(projPlantTransTO, epsProjRepository));
        }
        return projPlantTransEntites;
    }

    public static PlantTransNormalTimeEntity convertOnePojoToOneEntity(ProjPlantTransTO projPlantTransTO,
            EPSProjRepository epsProjRepository) {
        PlantTransNormalTimeEntity entity = new PlantTransNormalTimeEntity();

        if (CommonUtil.isNonBlankLong(projPlantTransTO.getId())) {
            entity.setId(projPlantTransTO.getId());
        }
        entity.setId(projPlantTransTO.getId());
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projPlantTransTO.getProjId());
        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }

        entity.setPlantType(projPlantTransTO.getPlantType());
        if (CommonUtil.isNonBlankInteger(projPlantTransTO.getCutOffDays())) {
            entity.setCutOffDays(projPlantTransTO.getCutOffDays());
        } else {
            entity.setCutOffDays(0);
        }
        entity.setCutOffTime(projPlantTransTO.getCutOffTime());
        entity.setDefaultStatus(projPlantTransTO.getDefaultStatus());
        if (CommonUtil.isNonBlankInteger(projPlantTransTO.getCutOffHours())) {
            entity.setCutOffHours(projPlantTransTO.getCutOffHours());
        } else {
            entity.setCutOffHours(0);
        }
        if (CommonUtil.isNonBlankInteger(projPlantTransTO.getCutOffMinutes())) {
            entity.setCutOffMinutes(projPlantTransTO.getCutOffMinutes());
        } else {
            entity.setCutOffMinutes(0);
        }

        entity.setStatus(projPlantTransTO.getStatus());

        return entity;
    }

}
