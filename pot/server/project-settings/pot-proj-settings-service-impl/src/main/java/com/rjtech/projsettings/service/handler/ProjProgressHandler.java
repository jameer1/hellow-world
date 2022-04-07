package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjSOWItemTO;
import com.rjtech.projectlib.model.ProjSOEItemEntity;
//import com.rjtech.projectlib.model.ProjSOEItemEntityCopy;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;
import com.rjtech.projsettings.dto.ProjProgressTO;
import com.rjtech.projsettings.model.ProjProgressEntity;
import com.rjtech.projsettings.req.ProjSowsSaveReq;

public class ProjProgressHandler {

    public static ProjProgressTO populateProjProgress(ProjSOEItemEntity projSOEItemEntity,
            Map<Long, ProjProgressEntity> projProgressMap) {
        ProjProgressTO projProgressTO = null;
        ProjProgressEntity projProgressEntity = null;
        if (projSOEItemEntity.isItem()) {
            if (projProgressMap.get(projSOEItemEntity.getId()) != null) {

                projProgressEntity = projProgressMap.get(projSOEItemEntity.getId());
                if (StatusCodes.ACTIVE.getValue().intValue() == projProgressEntity.getStatus().intValue()) {
                    projProgressTO = new ProjProgressTO();
                    projProgressTO.setId(projProgressEntity.getId());
                    projProgressTO.setItem(true);

                    if (CommonUtil.objectNotNull(projProgressEntity.getProjMstrEntity())) {
                        projProgressTO.setProjId(projProgressEntity.getProjMstrEntity().getProjectId());
                    }
                    if (CommonUtil.isNotBlankDate(projProgressEntity.getStartDate())) {
                        projProgressTO.setStartDate(CommonUtil.convertDateToString(projProgressEntity.getStartDate()));
                    }
                    if (CommonUtil.isNotBlankDate(projProgressEntity.getFinishDate())) {
                        projProgressTO
                                .setFinishDate(CommonUtil.convertDateToString(projProgressEntity.getFinishDate()));
                    }
                    projProgressTO.setSowId(projProgressEntity.getProjSOWItemEntity().getId());
                    projProgressTO.setName(projProgressEntity.getProjSOWItemEntity().getName());
                    projProgressTO.setCode(projProgressEntity.getProjSOWItemEntity().getCode());
                    projProgressTO.setOriginalQuantity(projProgressEntity.getOriginalQuantity());
                    projProgressTO.setRevicedquantity(projProgressEntity.getRevicedquantity());
                    if (CommonUtil.objectNotNull(projProgressEntity.getProjSOWItemEntity().getProjSOEItemEntity()
                            .getMeasurmentMstrEntity())) {
                        projProgressTO.setUnitofmeasure(projProgressEntity.getProjSOWItemEntity().getProjSOEItemEntity()
                                .getMeasurmentMstrEntity().getName());
                    }

                    projProgressTO.setStatus(projProgressEntity.getStatus());

                }

            }
        } else {
            projProgressTO = new ProjProgressTO();
            projProgressTO.setName(projSOEItemEntity.getName());
            projProgressTO.setItem(false);
            projProgressTO.setId(projSOEItemEntity.getId());
            projProgressTO.setCode(projSOEItemEntity.getCode());

            projProgressTO.setStatus(projSOEItemEntity.getStatus());

        }
        if (CommonUtil.objectNotNull(projProgressTO)) {
            List<ProjProgressTO> childTOs = addChilds(projSOEItemEntity, projProgressTO, projProgressMap);
            if (childTOs != null && childTOs.size() > 0) {
                projProgressTO.getChildProjProgressTOs().addAll(childTOs);
            }
        }
        return projProgressTO;
    }

    public static List<ProjProgressTO> addChilds(ProjSOEItemEntity projSOEItemEntity, ProjProgressTO projProgressTO,
            Map<Long, ProjProgressEntity> projProgressMap) {
        List<ProjProgressTO> projProgressChilds = new ArrayList<ProjProgressTO>();
        ProjProgressTO projProgressChildTO = null;
        if (projSOEItemEntity.getProjSOEItemEntity() == null) {
            if (projSOEItemEntity.getChildEntities() != null & projSOEItemEntity.getChildEntities().size() > 0) {
                for (ProjSOEItemEntity childEntity : projSOEItemEntity.getChildEntities()) {
                    if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                        projProgressChildTO = populateProjProgress(childEntity, projProgressMap);
                        if (CommonUtil.objectNotNull(projProgressChildTO)) {
                            projProgressChilds.add(projProgressChildTO);
                        }
                    }
                }
            }
        } else {
            projProgressTO.setParentId(projSOEItemEntity.getProjSOEItemEntity().getId());
            if (projSOEItemEntity.getChildEntities() != null & projSOEItemEntity.getChildEntities().size() > 0) {
                for (ProjSOEItemEntity childEntity : projSOEItemEntity.getChildEntities()) {
                    if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                        projProgressChildTO = populateProjProgress(childEntity, projProgressMap);
                        if (CommonUtil.objectNotNull(projProgressChildTO)) {
                            projProgressChilds.add(projProgressChildTO);
                        }
                    }
                }
            }
        }
        if (projProgressChilds.size() > 0) {
            return projProgressChilds;
        } else {
            return null;
        }
    }

    public static List<ProjProgressEntity> populateSowEnties(ProjSowsSaveReq projSowsSaveReq,
            EPSProjRepository epsProjRepository, ProjSOWItemRepositoryCopy projSOWItemRepository) {
        List<ProjProgressEntity> entities = new ArrayList<ProjProgressEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        for (ProjSOWItemTO projSOWItemTO : projSowsSaveReq.getProjSOWItemTOs()) {
            entities.add(convertSowPOJOToEntity(projSOWItemTO, epsProjRepository, projSOWItemRepository));
        }
        return entities;
    }

    public static ProjProgressEntity convertSowPOJOToEntity(ProjSOWItemTO projSOWItemTO,
            EPSProjRepository epsProjRepository, ProjSOWItemRepositoryCopy projSOWItemRepository) {
        ProjProgressEntity entity = new ProjProgressEntity();
        if (CommonUtil.isNonBlankLong(projSOWItemTO.getId())) {
            entity.setId(projSOWItemTO.getId());
        }
        if (CommonUtil.isNonBlankLong(projSOWItemTO.getProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projSOWItemTO.getProjId());
            entity.setProjMstrEntity(projMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(projSOWItemTO.getId())) {
            ProjSOWItemEntity projSOWItemEntity = projSOWItemRepository.findOne(projSOWItemTO.getId());
            entity.setProjSOWItemEntity(projSOWItemEntity);
        }

        entity.setStatus(projSOWItemTO.getStatus());

        return entity;
    }

    public static List<ProjProgressEntity> papulateProjProgressEntities(List<ProjProgressTO> projProgressTOs,
            EPSProjRepository epsProjRepository, ProjSOWItemRepositoryCopy projSOWItemRepository) {
        List<ProjProgressEntity> projProgressEntites = new ArrayList<ProjProgressEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjProgressTO projProgressTO : projProgressTOs) {
            convertProjProgressPOJOToEntity(projProgressEntites, projProgressTO, epsProjRepository,
                    projSOWItemRepository);
        }
        return projProgressEntites;
    }

    public static void convertProjProgressPOJOToEntity(List<ProjProgressEntity> projProgressEntites,
            ProjProgressTO projProgressTO, EPSProjRepository epsProjRepository,
            ProjSOWItemRepositoryCopy projSOWItemRepository) {
        if (projProgressTO.isItem()) {
            projProgressEntites.add(convertPojoToEntity(projProgressTO, epsProjRepository, projSOWItemRepository));
        }
        for (ProjProgressTO childTO : projProgressTO.getChildProjProgressTOs()) {
            convertProjProgressPOJOToEntity(projProgressEntites, childTO, epsProjRepository, projSOWItemRepository);
        }
    }

    public static ProjProgressEntity convertPojoToEntity(ProjProgressTO projProgressTO,
            EPSProjRepository epsProjRepository, ProjSOWItemRepositoryCopy projSOWItemRepository) {
        ProjProgressEntity entity = new ProjProgressEntity();

        if (CommonUtil.isNonBlankLong(projProgressTO.getId())) {
            entity.setId(projProgressTO.getId());
        }
        if (CommonUtil.isNonBlankLong(projProgressTO.getProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projProgressTO.getProjId());
            entity.setProjMstrEntity(projMstrEntity);
        }

        if (CommonUtil.isNotBlankStr(projProgressTO.getStartDate())) {
            entity.setStartDate(CommonUtil.convertStringToDate(projProgressTO.getStartDate()));
        }
        if (CommonUtil.isNotBlankStr(projProgressTO.getFinishDate())) {
            entity.setFinishDate(CommonUtil.convertStringToDate(projProgressTO.getFinishDate()));
        }
        if (CommonUtil.isNonBlankLong(projProgressTO.getSowId())) {
            ProjSOWItemEntity projSOWItemEntity = projSOWItemRepository.findOne(projProgressTO.getSowId());
            entity.setProjSOWItemEntity(projSOWItemEntity);
        }
        entity.setOriginalQuantity(projProgressTO.getOriginalQuantity());
        entity.setRevicedquantity(projProgressTO.getRevicedquantity());

        entity.setStatus(projProgressTO.getStatus());
        return entity;
    }

    public static ProjProgressTO convertEntityToPOJO(ProjProgressEntity projProgressEntity) {

        ProjProgressTO projProgressTO = new ProjProgressTO();
        projProgressTO.setId(projProgressEntity.getId());
        projProgressTO.setItem(true);

        if (CommonUtil.objectNotNull(projProgressEntity.getProjMstrEntity())) {
            projProgressTO.setProjId(projProgressEntity.getProjMstrEntity().getProjectId());
        }
        if (CommonUtil.isNotBlankDate(projProgressEntity.getStartDate())) {
            projProgressTO.setStartDate(CommonUtil.convertDateToString(projProgressEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projProgressEntity.getFinishDate())) {
            projProgressTO.setFinishDate(CommonUtil.convertDateToString(projProgressEntity.getFinishDate()));
        }
        projProgressTO.setSowId(projProgressEntity.getProjSOWItemEntity().getId());
        projProgressTO.setName(projProgressEntity.getProjSOWItemEntity().getName());
        projProgressTO.setCode(projProgressEntity.getProjSOWItemEntity().getCode());
        projProgressTO.setOriginalQuantity(projProgressEntity.getOriginalQuantity());
        projProgressTO.setRevicedquantity(projProgressEntity.getRevicedquantity());
        if (CommonUtil.objectNotNull(
                projProgressEntity.getProjSOWItemEntity().getProjSOEItemEntity().getMeasurmentMstrEntity())) {
            projProgressTO.setUnitofmeasure(projProgressEntity.getProjSOWItemEntity().getProjSOEItemEntity()
                    .getMeasurmentMstrEntity().getName());
        }

        projProgressTO.setStatus(projProgressEntity.getStatus());
        return projProgressTO;
    }
}
