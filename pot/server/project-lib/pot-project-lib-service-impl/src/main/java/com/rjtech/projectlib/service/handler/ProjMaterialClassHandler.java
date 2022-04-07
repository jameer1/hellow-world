package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjMaterialClassTO;
import com.rjtech.projectlib.model.ProjMaterialMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;

public class ProjMaterialClassHandler {

    private static final Logger log = LoggerFactory.getLogger(ProjMaterialClassHandler.class);

    public static ProjMaterialClassTO convertProjMaterialEntityToPOJO(ProjMaterialMstrEntity entity,
            ProjMaterialClassTO projMaterialClassTO) {
        projMaterialClassTO.setId(entity.getId());
        projMaterialClassTO.setExternalApproved(entity.getExtrlApproved());
        projMaterialClassTO.setInternalApproved(entity.getIntrlApproved());
        return projMaterialClassTO;
    }

    public static ProjMaterialClassTO convertEntityToPOJO(MaterialClassMstrEntity materialClassMstrEntity) {
        ProjMaterialClassTO projMaterialClassTO = new ProjMaterialClassTO();
        projMaterialClassTO.setGroupId(materialClassMstrEntity.getId());
        projMaterialClassTO.setName(materialClassMstrEntity.getName());
        projMaterialClassTO.setCode(materialClassMstrEntity.getCode());
        projMaterialClassTO.setStatus(materialClassMstrEntity.getStatus());
        if (CommonUtil.objectNotNull(materialClassMstrEntity.getMeasurmentMstrEntity())) {
            projMaterialClassTO.setMeasureUnitTO(
                    MeasurementHandler.convertMeasurePOJOFromEnity(materialClassMstrEntity.getMeasurmentMstrEntity()));
        }

        return projMaterialClassTO;
    }

    public static ProjMaterialMstrEntity convertPOJOToEntity(ProjMaterialClassTO projMaterialClassTO,
            EPSProjRepository epsProjRepository, MaterialClassRepository materialClassRepository) {
        ProjMaterialMstrEntity projMaterialMstrEntity = new ProjMaterialMstrEntity();
        if (CommonUtil.isNonBlankLong(projMaterialClassTO.getId())) {
            projMaterialMstrEntity.setId(projMaterialClassTO.getId());
        }
        projMaterialMstrEntity.setProjectId(epsProjRepository.findOne(projMaterialClassTO.getProjId()));
        projMaterialMstrEntity.setGroupId(materialClassRepository.findOne(projMaterialClassTO.getGroupId()));
        projMaterialMstrEntity.setIntrlApproved(projMaterialClassTO.getInternalApproved());
        projMaterialMstrEntity.setExtrlApproved(projMaterialClassTO.getExternalApproved());
        projMaterialMstrEntity.setStatus(projMaterialClassTO.getStatus());
        return projMaterialMstrEntity;
    }

    public static ProjMaterialClassTO populateProjMaterials(MaterialClassMstrEntity materialClassMstrEntity,
            Map<Long, ProjMaterialMstrEntity> projMaterialMap) {
        ProjMaterialClassTO projMaterialClassTO = null;
        ProjMaterialMstrEntity projMaterialMstrEntity = null;
        if (materialClassMstrEntity.isItem()) {
            if (StatusCodes.ACTIVE.getValue().intValue() == materialClassMstrEntity.getStatus().intValue()) {
                projMaterialClassTO = convertEntityToPOJO(materialClassMstrEntity);
                projMaterialClassTO.setItem(true);
                if (CommonUtil.objectNotNull(materialClassMstrEntity.getMaterialClassMstrEntity())) {
                    projMaterialClassTO.setParentId(materialClassMstrEntity.getMaterialClassMstrEntity().getId());
                }
                if (projMaterialMap.get(materialClassMstrEntity.getId()) != null) {
                    projMaterialMstrEntity = projMaterialMap.get(materialClassMstrEntity.getId());
                    ProjMstrEntity project = projMaterialMstrEntity.getProjectId();
                    if (project != null)
                        projMaterialClassTO.setProjId(project.getProjectId());
                    convertProjMaterialEntityToPOJO(projMaterialMstrEntity, projMaterialClassTO);
                }
            }
        } else {
            projMaterialClassTO = new ProjMaterialClassTO();
            projMaterialClassTO.setId(materialClassMstrEntity.getId());
            projMaterialClassTO.setName(materialClassMstrEntity.getName());
            projMaterialClassTO.setCode(materialClassMstrEntity.getCode());
            projMaterialClassTO.setItem(false);

            projMaterialClassTO.setStatus(materialClassMstrEntity.getStatus());
        }
        if (CommonUtil.objectNotNull(projMaterialClassTO)) {
            List<ProjMaterialClassTO> childTOs = addChilds(materialClassMstrEntity, projMaterialClassTO,
                    projMaterialMap);
            if (childTOs != null && childTOs.size() > 0) {
                projMaterialClassTO.getProjMaterialClassTOs().addAll(childTOs);
            }
        }
        return projMaterialClassTO;
    }

    public static List<ProjMaterialClassTO> addChilds(MaterialClassMstrEntity materialClassMstrEntity,
            ProjMaterialClassTO projectMaterialDtlTO, Map<Long, ProjMaterialMstrEntity> projCostStmtMap) {
        List<ProjMaterialClassTO> projectMaterialDtlTOs = new ArrayList<ProjMaterialClassTO>();
        ProjMaterialClassTO projectMaterialChildTO = null;
        if (CommonUtil.objectNullCheck(materialClassMstrEntity.getMaterialClassMstrEntity())) {
            for (MaterialClassMstrEntity childEntity : materialClassMstrEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    projectMaterialChildTO = populateProjMaterials(childEntity, projCostStmtMap);
                    if (CommonUtil.objectNotNull(projectMaterialChildTO)) {
                        projectMaterialDtlTOs.add(projectMaterialChildTO);
                    }
                }
            }
        } else {
            if (CommonUtil.objectNotNull(materialClassMstrEntity.getMaterialClassMstrEntity())) {
                projectMaterialDtlTO.setParentId(materialClassMstrEntity.getMaterialClassMstrEntity().getId());
            }
            for (MaterialClassMstrEntity childEntity : materialClassMstrEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    projectMaterialChildTO = populateProjMaterials(childEntity, projCostStmtMap);
                    if (CommonUtil.objectNotNull(projectMaterialChildTO)) {
                        projectMaterialDtlTOs.add(projectMaterialChildTO);
                    }
                }
            }
        }
        if (projectMaterialDtlTOs.size() > 0) {
            return projectMaterialDtlTOs;
        } else {
            return null;
        }
    }

}
