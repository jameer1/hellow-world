package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projsettings.dto.ProjEstimateTO;
import com.rjtech.projsettings.model.ProjEstimateEntity;

public class ProjEstimateHandler {

    private static final Logger log = LoggerFactory.getLogger(ProjEstimateHandler.class);

    public static List<ProjEstimateTO> convertEntitesToPOJOs(List<ProjEstimateEntity> entities) {
        List<ProjEstimateTO> estimateTOs = new ArrayList<ProjEstimateTO>();
        for (ProjEstimateEntity entity : entities) {
            estimateTOs.add(convertEntityToPOJO(entity));
        }
        return estimateTOs;
    }

    private static ProjEstimateTO convertEntityToPOJO(ProjEstimateEntity entity) {
        ProjEstimateTO projEstimateTO = new ProjEstimateTO();
        projEstimateTO.setId(entity.getId());
        projEstimateTO.setProjId(entity.getProjMstrEntity().getProjectId());
        projEstimateTO.setFormulaType(entity.getFormulaType());
        projEstimateTO.setResourceType(entity.getResourceType());
        return projEstimateTO;
    }

    public static List<ProjEstimateEntity> convertPOJOToEntity(List<ProjEstimateTO> projEstimateTOs,
            EPSProjRepository epsProjRepository) {
        List<ProjEstimateEntity> ProjEstimateEntites = new ArrayList<ProjEstimateEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjEstimateTO projEstimateTO : projEstimateTOs) {
            ProjEstimateEntity entity = new ProjEstimateEntity();

            if (CommonUtil.isNonBlankLong(projEstimateTO.getId())) {
                entity.setId(projEstimateTO.getId());
            }
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projEstimateTO.getProjId());
            if (projMstrEntity == null) {
                log.info("projMstrEntity is null for proj id {} " + projEstimateTO.getProjId());
            }
            entity.setProjMstrEntity(projMstrEntity);
            entity.setFormulaType(projEstimateTO.getFormulaType());
            entity.setResourceType(projEstimateTO.getResourceType());
            ProjEstimateEntites.add(entity);
        }
        return ProjEstimateEntites;
    }
}
