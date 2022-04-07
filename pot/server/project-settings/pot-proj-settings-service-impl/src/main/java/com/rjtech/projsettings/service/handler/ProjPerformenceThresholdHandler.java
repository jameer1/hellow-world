package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjPerformenceThresholdTO;
import com.rjtech.projsettings.model.ProjPerformenceThresholdEntity;

public class ProjPerformenceThresholdHandler {

    public static ProjPerformenceThresholdTO convertEntityToPOJO(ProjPerformenceThresholdEntity entity) {

        ProjPerformenceThresholdTO projPerformenceThresholdTO = new ProjPerformenceThresholdTO();

        projPerformenceThresholdTO.setId(entity.getId());
        projPerformenceThresholdTO.setProjId(entity.getProjMstrEntity().getProjectId());
        projPerformenceThresholdTO.setCategory(entity.getCategory());
        projPerformenceThresholdTO.setSvLowerLimit(entity.getSvLowerLimit());
        projPerformenceThresholdTO.setSvUpperLimit(entity.getSvUpperLimit());
        projPerformenceThresholdTO.setCvLowerLimit(entity.getCvLowerLimit());
        projPerformenceThresholdTO.setCvUpperLimit(entity.getCvUpperLimit());
        projPerformenceThresholdTO.setSpiLowerLimit(entity.getSpiLowerLimit());
        projPerformenceThresholdTO.setSpiUpperLimit(entity.getSpiUpperLimit());
        projPerformenceThresholdTO.setCpiLowerLimit(entity.getCpiLowerLimit());
        projPerformenceThresholdTO.setCpiUpperLimit(entity.getCpiUpperLimit());
        projPerformenceThresholdTO.setTcpiLowerLimit(entity.getTcpiLowerLimit());
        projPerformenceThresholdTO.setTcpiUpperLimit(entity.getTcpiUpperLimit());

        projPerformenceThresholdTO.setStatus(entity.getStatus());
        return projPerformenceThresholdTO;
    }

    public static List<ProjPerformenceThresholdEntity> convertPOJOsToEntitys(
            List<ProjPerformenceThresholdTO> projPerformenceThresholdTOs, EPSProjRepository epsProjRepository) {
        List<ProjPerformenceThresholdEntity> ProjPerformenceThresholdEnties = new ArrayList<ProjPerformenceThresholdEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        for (ProjPerformenceThresholdTO projPerformenceThresholdTO : projPerformenceThresholdTOs) {

            ProjPerformenceThresholdEnties
                    .add(convertOnePojpToOneEntity(projPerformenceThresholdTO, epsProjRepository));
        }
        return ProjPerformenceThresholdEnties;
    }

    public static ProjPerformenceThresholdEntity convertOnePojpToOneEntity(
            ProjPerformenceThresholdTO projPerformenceThresholdTO, EPSProjRepository epsProjRepository) {

        ProjPerformenceThresholdEntity entity = new ProjPerformenceThresholdEntity();
        if (CommonUtil.isNonBlankLong(projPerformenceThresholdTO.getId())) {
            entity.setId(projPerformenceThresholdTO.getId());
        }

        ProjMstrEntity projEntity = epsProjRepository.findOne(projPerformenceThresholdTO.getProjId());
        entity.setProjMstrEntity(projEntity);

        entity.setCategory(projPerformenceThresholdTO.getCategory());
        entity.setSvLowerLimit(projPerformenceThresholdTO.getSvLowerLimit());
        entity.setSvUpperLimit(projPerformenceThresholdTO.getSvUpperLimit());
        entity.setCvLowerLimit(projPerformenceThresholdTO.getCvLowerLimit());
        entity.setCvUpperLimit(projPerformenceThresholdTO.getCvUpperLimit());
        entity.setSpiLowerLimit(projPerformenceThresholdTO.getSpiLowerLimit());
        entity.setSpiUpperLimit(projPerformenceThresholdTO.getSpiUpperLimit());
        entity.setCpiLowerLimit(projPerformenceThresholdTO.getCpiLowerLimit());
        entity.setCpiUpperLimit(projPerformenceThresholdTO.getCpiUpperLimit());
        entity.setTcpiLowerLimit(projPerformenceThresholdTO.getTcpiLowerLimit());
        entity.setTcpiUpperLimit(projPerformenceThresholdTO.getTcpiUpperLimit());
        entity.setStatus(projPerformenceThresholdTO.getStatus());
        return entity;
    }
}
