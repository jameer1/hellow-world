package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjCostBudgetTOCopy;
import com.rjtech.projectlib.dto.ProjCostStmtDtlTOCopy;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projectlib.req.ProjCostStatementsSaveReqCopy;
import com.rjtech.projsettings.model.ProjCostBudgetEntity;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;

public class ProjCostStmtDtlHandlerCopy {

    public static ProjCostStmtDtlTOCopy convertCostEntityToPOJO(ProjCostStmtDtlEntity projCostStmtDtlEntity,
                                                                ProjCostStmtDtlTOCopy projCostStmtDtlTO) {
        projCostStmtDtlTO.setId(projCostStmtDtlEntity.getId());

        if (CommonUtil.objectNotNull(projCostStmtDtlEntity.getProjCostItemEntity())) {
            projCostStmtDtlTO.setCostId(projCostStmtDtlEntity.getProjCostItemEntity().getId());
            projCostStmtDtlTO.setCode(projCostStmtDtlEntity.getProjCostItemEntity().getCode());
            projCostStmtDtlTO.setName(projCostStmtDtlEntity.getProjCostItemEntity().getName());
            projCostStmtDtlTO
                    .setCostClassId(projCostStmtDtlEntity.getProjCostItemEntity().getCostMstrEntity().getCode());
            projCostStmtDtlTO
                    .setCostClassName(projCostStmtDtlEntity.getProjCostItemEntity().getCostMstrEntity().getName());

            if (CommonUtil.isNotBlankDate(projCostStmtDtlEntity.getProjCostItemEntity().getStartDate())) {
                projCostStmtDtlTO.setStartDate(
                        CommonUtil.convertDateToString(projCostStmtDtlEntity.getProjCostItemEntity().getStartDate()));
            }

            if (CommonUtil.isNotBlankDate(projCostStmtDtlEntity.getProjCostItemEntity().getFinishDate())) {
                projCostStmtDtlTO.setFinishDate(
                        CommonUtil.convertDateToString(projCostStmtDtlEntity.getProjCostItemEntity().getFinishDate()));
            }
        }

        if (CommonUtil.objectNotNull(projCostStmtDtlEntity.getProjMstrEntity())) {
            projCostStmtDtlTO.setProjId(projCostStmtDtlEntity.getProjMstrEntity().getProjectId());
        }

        if (CommonUtil.isListHasData(projCostStmtDtlEntity.getProjCostBudgetEntites())) {
            System.out.println("ProjCostBudgetEntites size :"+projCostStmtDtlEntity.getProjCostBudgetEntites().size());
            for (ProjCostBudgetEntity projCostBudgetEntity : projCostStmtDtlEntity.getProjCostBudgetEntites()) {
                ProjCostBudgetTOCopy costBudget = ProjCostBudgetHandlerCopy.convertEntityToPOJO(projCostBudgetEntity);
                Long budgetType = projCostBudgetEntity.getBudgetType();
                System.out.println("budgetType :"+budgetType);
                if (budgetType != null) {
                    if (budgetType == 1L) {
                        projCostStmtDtlTO.setOriginalCostBudget(costBudget);
                    } else if (budgetType == 2L) {
                        projCostStmtDtlTO.setRevisedCostBudget(costBudget);
                    } else if (budgetType == 3L) {
                        projCostStmtDtlTO.setActualCostBudget(costBudget);
                    } else if (budgetType == 4L) {
                        projCostStmtDtlTO.setEstimateCompleteBudget(costBudget);
                    }
                }

                projCostStmtDtlTO.getProjCostBudgetTOCopys()
                        .add(ProjCostBudgetHandlerCopy.convertEntityToPOJO(projCostBudgetEntity));
            }
        }
        if (CommonUtil.isNotBlankDate(projCostStmtDtlEntity.getActualStartDate())) {
            projCostStmtDtlTO
                    .setActualStartDate(CommonUtil.convertDateToString(projCostStmtDtlEntity.getActualStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projCostStmtDtlEntity.getActualFinishDate())) {
            projCostStmtDtlTO
                    .setActualFinishDate(CommonUtil.convertDateToString(projCostStmtDtlEntity.getActualFinishDate()));
        }
        projCostStmtDtlTO.setNotes(projCostStmtDtlEntity.getNotes());
        projCostStmtDtlTO.setNotes(projCostStmtDtlEntity.getNotes());
        projCostStmtDtlTO.setEarnedValue(projCostStmtDtlEntity.getEarnedValue());
        projCostStmtDtlTO.setStatus(projCostStmtDtlEntity.getStatus());

        return projCostStmtDtlTO;
    }

    public static ProjCostStmtDtlTOCopy convertEntityToPOJO(ProjCostStmtDtlEntity projCostStmtDtlEntity) {
        ProjCostStmtDtlTOCopy projCostStmtDtlTO = new ProjCostStmtDtlTOCopy();
        projCostStmtDtlTO.setId(projCostStmtDtlEntity.getId());
        if (CommonUtil.objectNotNull(projCostStmtDtlEntity.getProjCostItemEntity())) {
            projCostStmtDtlTO.setCostId(projCostStmtDtlEntity.getProjCostItemEntity().getId());
        }
        if (CommonUtil.objectNotNull(projCostStmtDtlEntity.getProjMstrEntity())) {
            projCostStmtDtlTO.setProjId(projCostStmtDtlEntity.getProjMstrEntity().getProjectId());
        }
        projCostStmtDtlTO.setCode(projCostStmtDtlEntity.getProjCostItemEntity().getCode());
        projCostStmtDtlTO.setName(projCostStmtDtlEntity.getProjCostItemEntity().getName());
        projCostStmtDtlTO.setStatus(projCostStmtDtlEntity.getStatus());
        return projCostStmtDtlTO;
    }

    public static ProjCostStmtDtlTOCopy convertEntityToPOJO(ProjCostItemEntity projCostItemEntity) {
        ProjCostStmtDtlTOCopy projCostStmtDtlTO = new ProjCostStmtDtlTOCopy();
        projCostStmtDtlTO.setCostId(projCostItemEntity.getId());
        ProjMstrEntity project = projCostItemEntity.getProjId();
        if (project != null)
            projCostStmtDtlTO.setProjId(project.getProjectId());
        projCostStmtDtlTO.setName(projCostItemEntity.getName());
        projCostStmtDtlTO.setCode(projCostItemEntity.getCode());
        projCostStmtDtlTO.setStatus(projCostItemEntity.getStatus());

        if (CommonUtil.isNotBlankDate(projCostItemEntity.getStartDate())) {
            projCostStmtDtlTO.setStartDate(CommonUtil.convertDateToString(projCostItemEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projCostItemEntity.getFinishDate())) {
            projCostStmtDtlTO.setFinishDate(CommonUtil.convertDateToString(projCostItemEntity.getFinishDate()));
        }
        if (CommonUtil.objectNotNull(projCostItemEntity.getCostMstrEntity())) {
            projCostStmtDtlTO.setCostClassId(projCostItemEntity.getCostMstrEntity().getCode());
        }
        if (CommonUtil.objectNotNull(projCostItemEntity.getCostMstrEntity())) {
            projCostStmtDtlTO.setCostClassName(projCostItemEntity.getCostMstrEntity().getName());

        }
        return projCostStmtDtlTO;
    }
    
    // not using this function anywhere in this application
    public static List<ProjCostStmtDtlEntity> populateCostStatementEntities(
            ProjCostStatementsSaveReqCopy projCostStatementsSaveReq, ProjCostItemRepositoryCopy projCostItemRepository,
            EPSProjRepository epsProjRepository) {
        List<ProjCostStmtDtlEntity> entities = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjCostStmtDtlTOCopy projCostStmtDtlTO : projCostStatementsSaveReq.getProjCostStmtDtlTOs()) {
            convertCostPOJOToEntity(entities, projCostStmtDtlTO, projCostItemRepository, epsProjRepository);
        }
        return entities;
    }

    public static void convertCostPOJOToEntity(List<ProjCostStmtDtlEntity> projCostStmtDtlEntities,
                                               ProjCostStmtDtlTOCopy projCostStmtDtlTO, ProjCostItemRepositoryCopy projCostItemRepository,
                                               EPSProjRepository epsProjRepository) {
        if (projCostStmtDtlTO.isItem()) {
            projCostStmtDtlEntities
                    .add(convertPOJOToEntity(projCostStmtDtlTO, projCostItemRepository, epsProjRepository));
        }
        for (ProjCostStmtDtlTOCopy childTO : projCostStmtDtlTO.getProjCostStmtDtlTOCopys()) {
            convertCostPOJOToEntity(projCostStmtDtlEntities, childTO, projCostItemRepository, epsProjRepository);
        }
    }

    public static ProjCostStmtDtlEntity convertPOJOToEntity(ProjCostStmtDtlTOCopy projCostStmtDtlTO,
                                                            ProjCostItemRepositoryCopy projCostItemRepository, EPSProjRepository epsProjRepository) {
        ProjCostStmtDtlEntity projCostStmtDtlEntity = new ProjCostStmtDtlEntity();
        if (CommonUtil.isNonBlankLong(projCostStmtDtlTO.getId())) {
            projCostStmtDtlEntity.setId(projCostStmtDtlTO.getId());
        }
        if (CommonUtil.isNotBlankStr(projCostStmtDtlTO.getActualStartDate())) {
            projCostStmtDtlEntity
                    .setActualStartDate(CommonUtil.convertStringToDate(projCostStmtDtlTO.getActualStartDate()));
        }
        if (CommonUtil.isNotBlankStr(projCostStmtDtlTO.getActualFinishDate())) {
            projCostStmtDtlEntity
                    .setActualFinishDate(CommonUtil.convertStringToDate(projCostStmtDtlTO.getActualFinishDate()));
        }
        if (CommonUtil.isNonBlankLong(projCostStmtDtlTO.getCostId())) {
            ProjCostItemEntity projCostItemEntity = projCostItemRepository.findOne(projCostStmtDtlTO.getCostId());
            projCostStmtDtlEntity.setProjCostItemEntity(projCostItemEntity);
        }
        if (CommonUtil.isNonBlankLong(projCostStmtDtlTO.getProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(projCostStmtDtlTO.getProjId());
            projCostStmtDtlEntity.setProjMstrEntity(projMstrEntity);
        }
        projCostStmtDtlEntity.setEarnedValue(projCostStmtDtlTO.getEarnedValue());
        projCostStmtDtlEntity.setStatus(projCostStmtDtlTO.getStatus());
        projCostStmtDtlEntity.setNotes(projCostStmtDtlTO.getNotes());

        projCostStmtDtlEntity.setProjCostBudgetEntites(
                ProjCostBudgetHandlerCopy.populateCostBudgetEntities(projCostStmtDtlTO, projCostStmtDtlEntity));
        return projCostStmtDtlEntity;
    }

    public static ProjCostStmtDtlTOCopy populateProjCostStmts(ProjCostItemEntity projCostItemEntity,
                                                          Map<Long, ProjCostStmtDtlEntity> projCostStmtMap) {
        ProjCostStmtDtlTOCopy projCostStmtDtlTO = null;
        ProjMstrEntity project = projCostItemEntity.getProjId();
        //System.out.println("Handler:populateProjCostStmts:CostItemEntity Item:"+projCostItemEntity.getItem());
        //System.out.println("Handler:populateProjCostStmts:CostItemEntity getId:"+projCostItemEntity.getId());
        if (StatusCodes.ACTIVE.getValue().equals(projCostItemEntity.getItem())) {

            if (StatusCodes.ACTIVE.getValue().intValue() == projCostItemEntity.getStatus().intValue()) {
                projCostStmtDtlTO = convertEntityToPOJO(projCostItemEntity);
                projCostStmtDtlTO.setItem(true);
                ProjCostItemEntity parent = projCostItemEntity.getProjCostItemEntity();
                if (parent != null)
                    projCostStmtDtlTO.setParentId(parent.getId());
                if (project != null)
                    projCostStmtDtlTO.setProjId(project.getProjectId());
                if (projCostStmtMap.get(projCostItemEntity.getId()) != null) {
                    ProjCostStmtDtlEntity projCostStmtDtlEntity = projCostStmtMap.get(projCostItemEntity.getId());
                    convertCostEntityToPOJO(projCostStmtDtlEntity, projCostStmtDtlTO);
                } else {
                    projCostStmtDtlTO.setCostId(projCostItemEntity.getId());
                    projCostStmtDtlTO.setName(projCostItemEntity.getName());
                    projCostStmtDtlTO.setCode(projCostItemEntity.getCode());
                    if (CommonUtil.objectNotNull(projCostItemEntity.getCostMstrEntity())) {
                        projCostStmtDtlTO.setCostClassId(projCostItemEntity.getCostMstrEntity().getCode());
                    }
                    if (CommonUtil.objectNotNull(projCostItemEntity.getCostMstrEntity())) {
                        projCostStmtDtlTO.setCostClassName(projCostItemEntity.getCostMstrEntity().getName());
                    }
                    if (CommonUtil.isNotBlankDate(projCostItemEntity.getStartDate())) {
                        projCostStmtDtlTO
                                .setStartDate(CommonUtil.convertDateToString(projCostItemEntity.getStartDate()));
                    }

                    if (CommonUtil.isNotBlankDate(projCostItemEntity.getFinishDate())) {
                        projCostStmtDtlTO
                                .setFinishDate(CommonUtil.convertDateToString(projCostItemEntity.getFinishDate()));
                    }
                    projCostStmtDtlTO.setItem(true);

                    projCostStmtDtlTO.setStatus(projCostItemEntity.getStatus());
                }
            }
        } else {
            projCostStmtDtlTO = new ProjCostStmtDtlTOCopy();
            projCostStmtDtlTO.setId(projCostItemEntity.getId());
            if (project != null)
                projCostStmtDtlTO.setProjId(project.getProjectId());
            projCostStmtDtlTO.setName(projCostItemEntity.getName());
            projCostStmtDtlTO.setCode(projCostItemEntity.getCode());
            projCostStmtDtlTO.setItem(false);

            projCostStmtDtlTO.setStatus(projCostItemEntity.getStatus());
        }
        if (CommonUtil.objectNotNull(projCostStmtDtlTO)) {
            List<ProjCostStmtDtlTOCopy> childTOs = addChilds(projCostItemEntity, projCostStmtDtlTO, projCostStmtMap);
            if (childTOs != null && childTOs.size() > 0) {
                projCostStmtDtlTO.getProjCostStmtDtlTOCopys().addAll(childTOs);
            }
        }
        return projCostStmtDtlTO;
    }

    public static ProjCostStmtDtlTOCopy populateProjExitCostStmts(ProjCostItemEntity projCostItemEntity,
                                                              Map<Long, ProjCostStmtDtlEntity> projCostStmtMap) {
        ProjCostStmtDtlTOCopy projCostStmtDtlTO = null;
        ProjCostStmtDtlEntity projCostStmtDtlEntity = null;
        ProjMstrEntity project = projCostItemEntity.getProjId();
        if (StatusCodes.ACTIVE.getValue().equals(projCostItemEntity.getItem())) {
            if (StatusCodes.ACTIVE.getValue().intValue() == projCostItemEntity.getStatus().intValue()
                    && projCostStmtMap.get(projCostItemEntity.getId()) != null) {
                projCostStmtDtlEntity = projCostStmtMap.get(projCostItemEntity.getId());
                projCostStmtDtlTO = convertEntityToPOJO(projCostStmtDtlEntity);
                projCostStmtDtlTO.setCode(projCostItemEntity.getCode());
                projCostStmtDtlTO.setName(projCostItemEntity.getName());
                projCostStmtDtlTO.setItem(true);
                if (project != null)
                    projCostStmtDtlTO.setProjId(project.getProjectId());
                ProjCostItemEntity parent = projCostItemEntity.getProjCostItemEntity();
                if (parent != null)
                    projCostStmtDtlTO.setParentId(parent.getId());
            }
        } else {
            projCostStmtDtlTO = new ProjCostStmtDtlTOCopy();
            projCostStmtDtlTO.setId(projCostItemEntity.getId());
            projCostStmtDtlTO.setName(projCostItemEntity.getName());
            projCostStmtDtlTO.setCode(projCostItemEntity.getCode());
            if (project != null)
                projCostStmtDtlTO.setProjId(project.getProjectId());
            projCostStmtDtlTO.setItem(false);

            projCostStmtDtlTO.setStatus(projCostItemEntity.getStatus());
        }
        if (CommonUtil.objectNotNull(projCostStmtDtlTO)) {
            List<ProjCostStmtDtlTOCopy> childTOs = addExistingCostChilds(projCostItemEntity, projCostStmtDtlTO,
                    projCostStmtMap);
            if (childTOs != null && childTOs.size() > 0) {
                projCostStmtDtlTO.getProjCostStmtDtlTOCopys().addAll(childTOs);
            }
        }
        return projCostStmtDtlTO;
    }

    public static List<ProjCostStmtDtlTOCopy> addChilds(ProjCostItemEntity projCostItemEntity,
                                                    ProjCostStmtDtlTOCopy projCostStmtDtlTO, Map<Long, ProjCostStmtDtlEntity> projCostStmtMap) {
        List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs = new ArrayList<ProjCostStmtDtlTOCopy>();
        ProjCostStmtDtlTOCopy projectCoststmtChildTO = null;
        if (projCostItemEntity.getProjCostItemEntity() == null) {
            for (ProjCostItemEntity childEntity : projCostItemEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    projectCoststmtChildTO = populateProjCostStmts(childEntity, projCostStmtMap);
                    if (CommonUtil.objectNotNull(projectCoststmtChildTO)) {
                        projCostStmtDtlTOs.add(projectCoststmtChildTO);
                    }
                }
            }
        } else {
            projCostStmtDtlTO.setParentId(projCostItemEntity.getProjCostItemEntity().getId());
            for (ProjCostItemEntity childEntity : projCostItemEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    projectCoststmtChildTO = populateProjCostStmts(childEntity, projCostStmtMap);
                    if (CommonUtil.objectNotNull(projectCoststmtChildTO)) {
                        projCostStmtDtlTOs.add(projectCoststmtChildTO);
                    }
                }
            }
        }
        if (projCostStmtDtlTOs.size() > 0) {
            return projCostStmtDtlTOs;
        } else {
            return null;
        }
    }

    public static List<ProjCostStmtDtlTOCopy> addExistingCostChilds(ProjCostItemEntity projCostItemEntity,
                                                                ProjCostStmtDtlTOCopy projCostStmtDtlTO, Map<Long, ProjCostStmtDtlEntity> projCostStmtMap) {
        List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs = new ArrayList<ProjCostStmtDtlTOCopy>();
        ProjCostStmtDtlTOCopy projectCoststmtChildTO = null;
        if (projCostItemEntity.getProjCostItemEntity() == null) {
            for (ProjCostItemEntity childEntity : projCostItemEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    projectCoststmtChildTO = populateProjExitCostStmts(childEntity, projCostStmtMap);
                    if (CommonUtil.objectNotNull(projectCoststmtChildTO)) {
                        projCostStmtDtlTOs.add(projectCoststmtChildTO);
                    }
                }
            }
        } else {
            projCostStmtDtlTO.setParentId(projCostItemEntity.getProjCostItemEntity().getId());
            for (ProjCostItemEntity childEntity : projCostItemEntity.getChildEntities()) {
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    projectCoststmtChildTO = populateProjExitCostStmts(childEntity, projCostStmtMap);
                    if (CommonUtil.objectNotNull(projectCoststmtChildTO)) {
                        projCostStmtDtlTOs.add(projectCoststmtChildTO);
                    }
                }
            }
        }
        if (projCostStmtDtlTOs.size() > 0) {
            return projCostStmtDtlTOs;
        } else {
            return null;
        }
    }
}
