
package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.TangibleClassTO;
import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.model.TangibleClassificationEntity;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.resp.TangibleClassResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;

public class TangibleClassHandler {

    public static TangibleClassTO populateTangibleItems(TangibleClassificationEntity tangibleClassificationEntity,
            boolean addChild) {
    	TangibleClassTO tangibleClassTO = null;
        if (CommonUtil.objectNotNull(tangibleClassificationEntity)) {
        	tangibleClassTO = new TangibleClassTO();
            tangibleClassTO.setId(tangibleClassificationEntity.getId());
            tangibleClassTO.setCode(tangibleClassificationEntity.getCode());
            tangibleClassTO.setName(tangibleClassificationEntity.getName());

            if (CommonUtil.objectNotNull(tangibleClassificationEntity.getClientId())) {
            	tangibleClassTO.setClientId(tangibleClassificationEntity.getClientId().getClientId());
            }

            if (CommonUtil.objectNotNull(tangibleClassificationEntity.getMeasurmentMstrEntity())) {
            	tangibleClassTO.setMeasureId(tangibleClassificationEntity.getMeasurmentMstrEntity().getId());
            }
            if (CommonUtil.objectNotNull(tangibleClassificationEntity.getMeasurmentMstrEntity())) {
            	tangibleClassTO.setMeasureUnitTO(MeasurementHandler
                        .convertMeasurePOJOFromEnity(tangibleClassificationEntity.getMeasurmentMstrEntity()));
            }
            if (tangibleClassificationEntity.isItem()) {
            	tangibleClassTO.setItem(true);
            } else {
            	tangibleClassTO.setItem(false);
            }
            if (CommonUtil.objectNotNull(tangibleClassificationEntity.getTangibleClassificationEntity())) {
            	tangibleClassTO.setParentId(tangibleClassificationEntity.getTangibleClassificationEntity().getId());
            }
            if (CommonUtil.objectNotNull(tangibleClassificationEntity.getTangibleClassificationEntity())) {
            	tangibleClassTO.setParentName(tangibleClassificationEntity.getTangibleClassificationEntity().getName());
            }
            
            tangibleClassTO.setStatus(tangibleClassificationEntity.getStatus());

            if (addChild) {
                if (CommonUtil.objectNotNull(tangibleClassTO.getChildTangibleItemTOs())) {
                	tangibleClassTO.getChildTangibleItemTOs()
                            .addAll(addChilds(tangibleClassificationEntity, tangibleClassTO, addChild));
                }
            }
        }
        return tangibleClassTO;
    }

    public static List<TangibleClassTO> addChilds(TangibleClassificationEntity tangibleClassificationEntity,
    		TangibleClassTO tangibleClassTO, boolean addChild) {
        List<TangibleClassTO> childMaterialTOs = new ArrayList<TangibleClassTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        if (CommonUtil.objectNotNull(tangibleClassificationEntity.getTangibleClassificationEntity())) {
            for (TangibleClassificationEntity childEntity : tangibleClassificationEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childMaterialTOs.add(populateTangibleItems(childEntity, addChild));
                }
            }
        } else {
            if (CommonUtil.objectNotNull(tangibleClassificationEntity.getTangibleClassificationEntity())) {
                tangibleClassTO.setParentId(tangibleClassificationEntity.getTangibleClassificationEntity().getId());
            }
            for (TangibleClassificationEntity childEntity : tangibleClassificationEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childMaterialTOs.add(populateTangibleItems(childEntity, addChild));
                }
            }
        }
        return childMaterialTOs;
    }

    public static List<TangibleClassificationEntity> populateEntitiesFromPOJO(Long clientId,
            List<TangibleClassTO> tangibleClassTOs, MeasurementRepository measurementRepository) {
        List<TangibleClassificationEntity> tangibleClassificationEntities = new ArrayList<TangibleClassificationEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        TangibleClassificationEntity entity = null;

        for (TangibleClassTO tangibleClassTO : tangibleClassTOs) {
            entity = new TangibleClassificationEntity();
            tangibleClassTO.setClientId(clientId);
            convertPOJOToEntity(entity, tangibleClassTO, measurementRepository);
            tangibleClassificationEntities.add(entity);
        }
        return tangibleClassificationEntities;
    }

    public static TangibleClassificationEntity convertPOJOToEntity(TangibleClassificationEntity tangibleClassificationEntity,
            TangibleClassTO tangibleClassTO, MeasurementRepository measurementRepository) {

        if (CommonUtil.isNonBlankLong(tangibleClassTO.getId())) {
            tangibleClassificationEntity.setId(tangibleClassTO.getId());
        }
        tangibleClassificationEntity.setCode(tangibleClassTO.getCode());
        tangibleClassificationEntity.setName(tangibleClassTO.getName());
        if (CommonUtil.isNonBlankLong(tangibleClassTO.getMeasureId())) {
            MeasurmentMstrEntity measurmentMstrEntity = measurementRepository.findOne(tangibleClassTO.getMeasureId());
            tangibleClassificationEntity.setMeasurmentMstrEntity(measurmentMstrEntity);
        }
        tangibleClassificationEntity.setItem(tangibleClassTO.isItem());
        if (CommonUtil.isNonBlankLong(tangibleClassTO.getParentId())) {
            TangibleClassificationEntity parentEntity = new TangibleClassificationEntity();
            parentEntity.setId(tangibleClassTO.getParentId());
            tangibleClassificationEntity.setTangibleClassificationEntity(parentEntity);
        }
        tangibleClassificationEntity.setStatus(tangibleClassTO.getStatus());
        TangibleClassificationEntity childEntity = null;

        for (TangibleClassTO childTO : tangibleClassTO.getChildTangibleItemTOs()) {
            childEntity = new TangibleClassificationEntity();
            childEntity.setTangibleClassificationEntity(tangibleClassificationEntity);
            
            tangibleClassificationEntity.getChildEntities()
                    .add(convertPOJOToEntity(childEntity, childTO, measurementRepository));
        }
        return tangibleClassificationEntity;
    }

    public static TangibleClassResp convertEntityToPOJO(List<TangibleClassificationEntity> entities) {
    	TangibleClassResp measuringUnitResp = new TangibleClassResp();
        TangibleClassTO tangibleClassTO = null;
        boolean addChild = false;
        for (TangibleClassificationEntity TangibleMstrEntity : entities) {
            tangibleClassTO = populateTangibleItems(TangibleMstrEntity, addChild);
            measuringUnitResp.getTangibleClassTOs().add(tangibleClassTO);
        }
        return measuringUnitResp;
    }

    public static TangibleClassTO convertEntityToPOJO(TangibleClassificationEntity tangibleClassificationEntity) {
        TangibleClassTO projTangibleClassTO = new TangibleClassTO();
        // projTangibleClassTO.setGroupId(tangibleClassificationEntity.getId());
        projTangibleClassTO.setId(tangibleClassificationEntity.getId());
        projTangibleClassTO.setName(tangibleClassificationEntity.getName());
        projTangibleClassTO.setCode(tangibleClassificationEntity.getCode());
        projTangibleClassTO.setStatus(tangibleClassificationEntity.getStatus());
      
        if (CommonUtil.objectNotNull(tangibleClassificationEntity.getMeasurmentMstrEntity())) {
        	projTangibleClassTO.setMeasureUnitTO(
                    MeasurementHandler.convertMeasurePOJOFromEnity(tangibleClassificationEntity.getMeasurmentMstrEntity()));
        }
        return projTangibleClassTO;
    }

    public static TangibleClassTO populateCentralLibTangible(TangibleClassificationEntity tangibleClassificationEntity,
            Map<Long, TangibleClassificationEntity> projTangibleMap, MeasurementRepository measurementRepository) {
        TangibleClassTO projTangibleClassTO = null;
        TangibleClassificationEntity projMaterialMstrEntity = null;
        if (tangibleClassificationEntity.isItem()) {
            if (StatusCodes.ACTIVE.getValue().intValue() == tangibleClassificationEntity.getStatus().intValue()) {
                projTangibleClassTO = convertEntityToPOJO(tangibleClassificationEntity);
                projTangibleClassTO.setItem(true);
                if (CommonUtil.objectNotNull(tangibleClassificationEntity.getTangibleClassificationEntity())) {
                    projTangibleClassTO.setParentId(tangibleClassificationEntity.getTangibleClassificationEntity().getId());
                }
                if (projTangibleMap.get(tangibleClassificationEntity.getId()) != null) {
                    if (CommonUtil.objectNotNull(tangibleClassificationEntity.getTangibleClassificationEntity())) {
                        projMaterialMstrEntity = projTangibleMap.get(tangibleClassificationEntity.getId());
                        projTangibleClassTO.setParentId(projMaterialMstrEntity.getTangibleClassificationEntity().getId());
                    }
                    convertPOJOToEntity(projMaterialMstrEntity, projTangibleClassTO,measurementRepository);
                }
            }
        } else {
            projTangibleClassTO = new TangibleClassTO();
            projTangibleClassTO.setId(tangibleClassificationEntity.getId());
            projTangibleClassTO.setName(tangibleClassificationEntity.getName());
            projTangibleClassTO.setCode(tangibleClassificationEntity.getCode());
            projTangibleClassTO.setItem(false);

            projTangibleClassTO.setStatus(tangibleClassificationEntity.getStatus());
        }
        if (CommonUtil.objectNotNull(projTangibleClassTO)) {
            List<TangibleClassTO> childTOs = addChilds(tangibleClassificationEntity, projTangibleClassTO, projTangibleMap,measurementRepository);
            if (childTOs != null && childTOs.size() > 0) {
                projTangibleClassTO.getChildTangibleItemTOs().addAll(childTOs);

            }
        }
        return projTangibleClassTO;
    }

    public static List<TangibleClassTO> addChilds(TangibleClassificationEntity tangibleClassificationEntity,
            TangibleClassTO projectMaterialDtlTO, Map<Long, TangibleClassificationEntity> projCostStmtMap,MeasurementRepository measurementRepository) {
        List<TangibleClassTO> projectTangibleDtlTOs = new ArrayList<TangibleClassTO>();
        TangibleClassTO projectTangibleChildTO = null;
        if (tangibleClassificationEntity.getTangibleClassificationEntity().getId() == null) {
            for (TangibleClassificationEntity childEntity : tangibleClassificationEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                	projectTangibleChildTO = populateCentralLibTangible(childEntity, projCostStmtMap,measurementRepository);
                    if (CommonUtil.objectNotNull(projectTangibleChildTO)) {
                        projectTangibleDtlTOs.add(projectTangibleChildTO);
                    }
                }
            }
        } else {
            if (CommonUtil.objectNotNull(tangibleClassificationEntity.getTangibleClassificationEntity())) {
                projectMaterialDtlTO.setParentId(tangibleClassificationEntity.getTangibleClassificationEntity().getId());
            }
            for (TangibleClassificationEntity childEntity : tangibleClassificationEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                	projectTangibleChildTO = populateCentralLibTangible(childEntity, projCostStmtMap,measurementRepository);
                    if (CommonUtil.objectNotNull(projectTangibleChildTO)) {
                        projectTangibleDtlTOs.add(projectTangibleChildTO);
                    }
                }
            }
        }
        if (projectTangibleDtlTOs.size() > 0) {
            return projectTangibleDtlTOs;
        } else {
            return null;
        }
    }

}
