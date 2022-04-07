
package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.rjtech.centrallib.dto.MaterialClassTO;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.resp.MaterialClassResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;

public class MaterialClassHandler {

    public static MaterialClassTO populateMaterialItems(MaterialClassMstrEntity materialClassMstrEntity,
            boolean addChild) {
        MaterialClassTO materialClassTO = null;
        if (CommonUtil.objectNotNull(materialClassMstrEntity)) {
            materialClassTO = new MaterialClassTO();
            materialClassTO.setId(materialClassMstrEntity.getId());
            materialClassTO.setCode(materialClassMstrEntity.getCode());
            materialClassTO.setName(materialClassMstrEntity.getName());

            if (CommonUtil.objectNotNull(materialClassMstrEntity.getClientId())) {
                materialClassTO.setClientId(materialClassMstrEntity.getClientId().getClientId());
            }

            if (CommonUtil.objectNotNull(materialClassMstrEntity.getMeasurmentMstrEntity())) {
                materialClassTO.setMeasureId(materialClassMstrEntity.getMeasurmentMstrEntity().getId());
            }
            if (CommonUtil.objectNotNull(materialClassMstrEntity.getMeasurmentMstrEntity())) {
                materialClassTO.setMeasureUnitTO(MeasurementHandler
                        .convertMeasurePOJOFromEnity(materialClassMstrEntity.getMeasurmentMstrEntity()));
            }
            if (materialClassMstrEntity.isItem()) {
                materialClassTO.setItem(true);
            } else {
                materialClassTO.setItem(false);
            }
            if (CommonUtil.objectNotNull(materialClassMstrEntity.getMaterialClassMstrEntity())) {
                materialClassTO.setParentId(materialClassMstrEntity.getMaterialClassMstrEntity().getId());
            }
            if (CommonUtil.objectNotNull(materialClassMstrEntity.getMaterialClassMstrEntity())) {
                materialClassTO.setParentName(materialClassMstrEntity.getMaterialClassMstrEntity().getName());
            }
            materialClassTO.setStatus(materialClassMstrEntity.getStatus());

            if (addChild) {
                if (CommonUtil.objectNotNull(materialClassTO.getChildMaterialItemTOs())) {
                    materialClassTO.getChildMaterialItemTOs()
                            .addAll(addChilds(materialClassMstrEntity, materialClassTO, addChild));
                }
            }
        }
        return materialClassTO;
    }

    public static List<MaterialClassTO> addChilds(MaterialClassMstrEntity materialClassMstrEntity,
            MaterialClassTO materialClassTO, boolean addChild) {
        List<MaterialClassTO> childMaterialTOs = new ArrayList<MaterialClassTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        if (CommonUtil.objectNotNull(materialClassMstrEntity.getMaterialClassMstrEntity())) {
            for (MaterialClassMstrEntity childEntity : materialClassMstrEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childMaterialTOs.add(populateMaterialItems(childEntity, addChild));
                }
            }
        } else {
            if (CommonUtil.objectNotNull(materialClassMstrEntity.getMaterialClassMstrEntity())) {
                materialClassTO.setParentId(materialClassMstrEntity.getMaterialClassMstrEntity().getId());
            }
            for (MaterialClassMstrEntity childEntity : materialClassMstrEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childMaterialTOs.add(populateMaterialItems(childEntity, addChild));
                }
            }
        }
        return childMaterialTOs;
    }

    public static List<MaterialClassMstrEntity> populateEntitiesFromPOJO(Long clientId,
            List<MaterialClassTO> materialClassTOs, MeasurementRepository measurementRepository) {
        List<MaterialClassMstrEntity> MaterialClassMstrEntities = new ArrayList<MaterialClassMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        MaterialClassMstrEntity entity = null;

        for (MaterialClassTO materialClassTO : materialClassTOs) {
            entity = new MaterialClassMstrEntity();
            materialClassTO.setClientId(clientId);
            convertPOJOToEntity(entity, materialClassTO, measurementRepository);
            MaterialClassMstrEntities.add(entity);
        }
        return MaterialClassMstrEntities;
    }

    public static MaterialClassMstrEntity convertPOJOToEntity(MaterialClassMstrEntity materialClassMstrEntity,
            MaterialClassTO materialClassTO, MeasurementRepository measurementRepository) {

        if (CommonUtil.isNonBlankLong(materialClassTO.getId())) {
            materialClassMstrEntity.setId(materialClassTO.getId());
        }
        materialClassMstrEntity.setCode(materialClassTO.getCode());
        materialClassMstrEntity.setName(materialClassTO.getName());
        if (CommonUtil.isNonBlankLong(materialClassTO.getMeasureId())) {
            MeasurmentMstrEntity measurmentMstrEntity = measurementRepository.findOne(materialClassTO.getMeasureId());
            materialClassMstrEntity.setMeasurmentMstrEntity(measurmentMstrEntity);
        }
        materialClassMstrEntity.setItem(materialClassTO.isItem());
        if (CommonUtil.isNonBlankLong(materialClassTO.getParentId())) {
            MaterialClassMstrEntity parentEntity = new MaterialClassMstrEntity();
            parentEntity.setId(materialClassTO.getParentId());
            materialClassMstrEntity.setMaterialClassMstrEntity(parentEntity);
        }
        materialClassMstrEntity.setStatus(materialClassTO.getStatus());
        MaterialClassMstrEntity childEntity = null;

        for (MaterialClassTO childTO : materialClassTO.getChildMaterialItemTOs()) {
            childEntity = new MaterialClassMstrEntity();
            childEntity.setMaterialClassMstrEntity(materialClassMstrEntity);
            if (childTO.isItem()) {
                List<ProcureCatgDtlEntity> procureCatgDtlEntities = new ArrayList<ProcureCatgDtlEntity>();
                ProcureCatgDtlEntity procureCatgDtlEntity = new ProcureCatgDtlEntity();
                procureCatgDtlEntity.setCode(childTO.getCode());
                procureCatgDtlEntity.setName(childTO.getName());
                procureCatgDtlEntity.setProcureType("Materials");
                procureCatgDtlEntity.setStatus(childTO.getStatus());
                procureCatgDtlEntities.add(procureCatgDtlEntity);
                materialClassMstrEntity.setProcureCatgDtlEntities(procureCatgDtlEntities);
            }
            materialClassMstrEntity.getChildEntities()
                    .add(convertPOJOToEntity(childEntity, childTO, measurementRepository));
        }
        return materialClassMstrEntity;
    }

    public static MaterialClassResp convertEntityToPOJO(List<MaterialClassMstrEntity> entities) {
        MaterialClassResp measuringUnitResp = new MaterialClassResp();
        MaterialClassTO measurementTO = null;
        boolean addChild = false;
        for (MaterialClassMstrEntity measurmentMstrEntity : entities) {
            measurementTO = populateMaterialItems(measurmentMstrEntity, addChild);
            measuringUnitResp.getMaterialClassTOs().add(measurementTO);
        }
        return measuringUnitResp;
    }

    public static MaterialClassTO convertEntityToPOJO(MaterialClassMstrEntity materialClassMstrEntity) {
        MaterialClassTO projMaterialClassTO = new MaterialClassTO();
        // projMaterialClassTO.setGroupId(materialClassMstrEntity.getId());
        projMaterialClassTO.setName(materialClassMstrEntity.getName());
        projMaterialClassTO.setCode(materialClassMstrEntity.getCode());
        projMaterialClassTO.setStatus(materialClassMstrEntity.getStatus());
        if (CommonUtil.objectNotNull(materialClassMstrEntity.getMeasurmentMstrEntity())) {
            projMaterialClassTO.setMeasureUnitTO(
                    MeasurementHandler.convertMeasurePOJOFromEnity(materialClassMstrEntity.getMeasurmentMstrEntity()));
        }

        return projMaterialClassTO;
    }

    public static MaterialClassTO populateCentralLibMaterials(MaterialClassMstrEntity materialClassMstrEntity,
            Map<Long, MaterialClassMstrEntity> projMaterialMap, MeasurementRepository measurementRepository) {
        MaterialClassTO projMaterialClassTO = null;
        MaterialClassMstrEntity projMaterialMstrEntity = null;
        if (materialClassMstrEntity.isItem()) {
            if (StatusCodes.ACTIVE.getValue().intValue() == materialClassMstrEntity.getStatus().intValue()) {
                projMaterialClassTO = convertEntityToPOJO(materialClassMstrEntity);
                projMaterialClassTO.setItem(true);
                if (CommonUtil.objectNotNull(materialClassMstrEntity.getMeasurmentMstrEntity())) {
                    projMaterialClassTO.setParentId(materialClassMstrEntity.getMaterialClassMstrEntity().getId());
                }
                if (projMaterialMap.get(materialClassMstrEntity.getId()) != null) {
                    if (CommonUtil.objectNotNull(materialClassMstrEntity.getMeasurmentMstrEntity())) {
                        projMaterialMstrEntity = projMaterialMap.get(materialClassMstrEntity.getId());
                        projMaterialClassTO.setParentId(projMaterialMstrEntity.getMaterialClassMstrEntity().getId());
                    }
                    convertPOJOToEntity(projMaterialMstrEntity, projMaterialClassTO, measurementRepository);
                }
            }
        } else {
            projMaterialClassTO = new MaterialClassTO();
            projMaterialClassTO.setId(materialClassMstrEntity.getId());
            projMaterialClassTO.setName(materialClassMstrEntity.getName());
            projMaterialClassTO.setCode(materialClassMstrEntity.getCode());
            projMaterialClassTO.setItem(false);

            projMaterialClassTO.setStatus(materialClassMstrEntity.getStatus());
        }
        if (CommonUtil.objectNotNull(projMaterialClassTO)) {
            List<MaterialClassTO> childTOs = addChilds(materialClassMstrEntity, projMaterialClassTO, projMaterialMap,
                    measurementRepository);
            if (childTOs != null && childTOs.size() > 0) {
                projMaterialClassTO.getChildMaterialItemTOs().addAll(childTOs);

            }
        }
        return projMaterialClassTO;
    }

    public static List<MaterialClassTO> addChilds(MaterialClassMstrEntity materialClassMstrEntity,
            MaterialClassTO projectMaterialDtlTO, Map<Long, MaterialClassMstrEntity> projCostStmtMap,
            MeasurementRepository measurementRepository) {
        List<MaterialClassTO> projectMaterialDtlTOs = new ArrayList<MaterialClassTO>();
        MaterialClassTO projectMaterialChildTO = null;
        if (materialClassMstrEntity.getMaterialClassMstrEntity().getId() == null) {
            for (MaterialClassMstrEntity childEntity : materialClassMstrEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    projectMaterialChildTO = populateCentralLibMaterials(childEntity, projCostStmtMap,
                            measurementRepository);
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
                    projectMaterialChildTO = populateCentralLibMaterials(childEntity, projCostStmtMap,
                            measurementRepository);
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
