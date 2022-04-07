package com.rjtech.projsettings.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
//import com.rjtech.projectlib.model.ProjCostItemEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepositoryCopy;
import com.rjtech.projsettings.dto.ProjCostBudgetTO;
import com.rjtech.projsettings.dto.ProjCostStmtDtlTO;
import com.rjtech.projsettings.model.ProjCostBudgetEntity;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;
import com.rjtech.projsettings.req.ProjCostStatementsSaveReq;

public class ProjCostStmtDtlHandler {

    public static ProjCostStmtDtlTO convertCostEntityToPOJO(ProjCostStmtDtlEntity projCostStmtDtlEntity,
            ProjCostStmtDtlTO projCostStmtDtlTO) {
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
            for (ProjCostBudgetEntity projCostBudgetEntity : projCostStmtDtlEntity.getProjCostBudgetEntites()) {
                ProjCostBudgetTO costBudget = ProjCostBudgetHandler.convertEntityToPOJO(projCostBudgetEntity);
                Long budgetType = projCostBudgetEntity.getBudgetType();
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

                projCostStmtDtlTO.getProjCostBudgetTOs()
                        .add(ProjCostBudgetHandler.convertEntityToPOJO(projCostBudgetEntity));
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
        projCostStmtDtlTO.setItemStatus( projCostStmtDtlEntity.getItemStatus() );
        if( projCostStmtDtlEntity.getApproverUserId() != null )
        {
        	projCostStmtDtlTO.setApproverUserId( projCostStmtDtlEntity.getApproverUserId().getUserId() );
        	projCostStmtDtlTO.setApproverComments( projCostStmtDtlEntity.getApproverComments() );
        }
        if( projCostStmtDtlEntity.getOriginatorUserId() != null )
        {
        	projCostStmtDtlTO.setOriginatorUserId( projCostStmtDtlEntity.getOriginatorUserId().getUserId() );
        }
        if( projCostStmtDtlEntity.getReturnedByUserId() != null )
        {
        	projCostStmtDtlTO.setReturnedByUserId( projCostStmtDtlEntity.getReturnedByUserId().getUserId() );
        	projCostStmtDtlTO.setReturnedComments( projCostStmtDtlEntity.getReturnedComments() );
        }
        projCostStmtDtlTO.setIsItemReturned( projCostStmtDtlEntity.getIsItemReturned() );

        return projCostStmtDtlTO;
    }

    public static ProjCostStmtDtlTO convertEntityToPOJO(ProjCostStmtDtlEntity projCostStmtDtlEntity) {
        ProjCostStmtDtlTO projCostStmtDtlTO = new ProjCostStmtDtlTO();
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

    public static ProjCostStmtDtlTO convertEntityToPOJO(ProjCostItemEntity projCostItemEntity) {
        ProjCostStmtDtlTO projCostStmtDtlTO = new ProjCostStmtDtlTO();
        System.out.println("convertEntityToPOJO function:"+projCostItemEntity.getId());
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

    public static List<ProjCostStmtDtlEntity> populateCostStatementEntities(
            ProjCostStatementsSaveReq projCostStatementsSaveReq, ProjCostItemRepositoryCopy projCostItemRepository,
            EPSProjRepository epsProjRepository) {
        List<ProjCostStmtDtlEntity> entities = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjCostStmtDtlTO projCostStmtDtlTO : projCostStatementsSaveReq.getProjCostStmtDtlTOs()) {
            convertCostPOJOToEntity(entities, projCostStmtDtlTO, projCostItemRepository, epsProjRepository);
        }
        return entities;
    }

    public static void convertCostPOJOToEntity(List<ProjCostStmtDtlEntity> projCostStmtDtlEntities,
            ProjCostStmtDtlTO projCostStmtDtlTO, ProjCostItemRepositoryCopy projCostItemRepository,
            EPSProjRepository epsProjRepository) {
    	System.out.println("projCostStmtDtlTO.isItem() " + projCostStmtDtlTO.isItem());
        if (projCostStmtDtlTO.isItem()) {
            projCostStmtDtlEntities
                    .add(convertPOJOToEntity(projCostStmtDtlTO, projCostItemRepository, epsProjRepository));
        }
        System.out.println("projCostStmtDtlTO.getProjCostStmtDtlTOs().size() " + projCostStmtDtlTO.getProjCostStmtDtlTOs().size());
        for (ProjCostStmtDtlTO childTO : projCostStmtDtlTO.getProjCostStmtDtlTOs()) {
            convertCostPOJOToEntity(projCostStmtDtlEntities, childTO, projCostItemRepository, epsProjRepository);
        }
    }

    public static ProjCostStmtDtlEntity convertPOJOToEntity(ProjCostStmtDtlTO projCostStmtDtlTO,
            ProjCostItemRepositoryCopy projCostItemRepository, EPSProjRepository epsProjRepository) {
    	System.out.println("------------------------convertPOJOToEntity-----------------------");
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
        //projCostStmtDtlEntity.setStatus(projCostStmtDtlTO.getStatus()); //commented as we will activate after approval
        projCostStmtDtlEntity.setStatus( StatusCodes.DEFAULT.getValue() );
        projCostStmtDtlEntity.setNotes(projCostStmtDtlTO.getNotes());

        projCostStmtDtlEntity.setProjCostBudgetEntites(
                ProjCostBudgetHandler.populateCostBudgetEntities(projCostStmtDtlTO, projCostStmtDtlEntity));
        
        if( CommonUtil.isBlankLong( projCostStmtDtlTO.getId() ) ) {
            projCostStmtDtlEntity.setItemStatus( "DRAFT" );
        }
        return projCostStmtDtlEntity;
    }

    public static ProjCostStmtDtlTO populateProjCostStmts(ProjCostItemEntity projCostItemEntity,
            Map<Long, ProjCostStmtDtlEntity> projCostStmtMap) {
        ProjCostStmtDtlTO projCostStmtDtlTO = null;
        ProjMstrEntity project = projCostItemEntity.getProjId();
        System.out.print("StatusCodes.ACTIVE.getValue() " + StatusCodes.ACTIVE.getValue());
        System.out.print("projCostItemEntity.getItem() " + projCostItemEntity.getItem());
        System.out.print("StatusCodes.ACTIVE.getValue().intValue() " + StatusCodes.ACTIVE.getValue().intValue());
        System.out.print("projCostItemEntity.getStatus().intValue() " + projCostItemEntity.getStatus().intValue());
        if (StatusCodes.ACTIVE.getValue().equals(projCostItemEntity.getItem())) {
            if ( StatusCodes.ACTIVE.getValue().intValue() == projCostItemEntity.getStatus().intValue() || StatusCodes.DEFAULT.getValue().intValue() == projCostItemEntity.getStatus().intValue() ) {
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
            projCostStmtDtlTO = new ProjCostStmtDtlTO();
            projCostStmtDtlTO.setId(projCostItemEntity.getId());
            ProjCostStmtDtlEntity projCostStmtDtlEntity = projCostStmtMap.get( projCostItemEntity.getId() );
            System.out.println("if condition"+projCostItemEntity.getId());
            System.out.println(projCostStmtDtlEntity);
            //projCostStmtDtlTO.setCostId(projCostStmtMap.get());
            if (project != null)
                projCostStmtDtlTO.setProjId(project.getProjectId());
            projCostStmtDtlTO.setName(projCostItemEntity.getName());
            projCostStmtDtlTO.setCode(projCostItemEntity.getCode());
            projCostStmtDtlTO.setItem(false);
            projCostStmtDtlTO.setItemStatus(projCostItemEntity.getItemStatus());
            projCostStmtDtlTO.setStatus(projCostItemEntity.getStatus());
        }
        if (CommonUtil.objectNotNull(projCostStmtDtlTO)) {
            List<ProjCostStmtDtlTO> childTOs = addChilds(projCostItemEntity, projCostStmtDtlTO, projCostStmtMap);
            if (childTOs != null && childTOs.size() > 0) {
                projCostStmtDtlTO.getProjCostStmtDtlTOs().addAll(childTOs);
            }
        }
        return projCostStmtDtlTO;
    }

    public static ProjCostStmtDtlTO populateProjExitCostStmts(ProjCostItemEntity projCostItemEntity,
            Map<Long, ProjCostStmtDtlEntity> projCostStmtMap) {
        ProjCostStmtDtlTO projCostStmtDtlTO = null;
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
            projCostStmtDtlTO = new ProjCostStmtDtlTO();
            projCostStmtDtlTO.setId(projCostItemEntity.getId());
            projCostStmtDtlTO.setName(projCostItemEntity.getName());
            projCostStmtDtlTO.setCode(projCostItemEntity.getCode());
            if (project != null)
                projCostStmtDtlTO.setProjId(project.getProjectId());
            projCostStmtDtlTO.setItem(false);

            projCostStmtDtlTO.setStatus(projCostItemEntity.getStatus());
        }
        if (CommonUtil.objectNotNull(projCostStmtDtlTO)) {
            List<ProjCostStmtDtlTO> childTOs = addExistingCostChilds(projCostItemEntity, projCostStmtDtlTO,
                    projCostStmtMap);
            if (childTOs != null && childTOs.size() > 0) {
                projCostStmtDtlTO.getProjCostStmtDtlTOs().addAll(childTOs);
            }
        }
        return projCostStmtDtlTO;
    }

    public static List<ProjCostStmtDtlTO> addChilds(ProjCostItemEntity projCostItemEntity,
            ProjCostStmtDtlTO projCostStmtDtlTO, Map<Long, ProjCostStmtDtlEntity> projCostStmtMap) {
        List<ProjCostStmtDtlTO> projCostStmtDtlTOs = new ArrayList<ProjCostStmtDtlTO>();
        ProjCostStmtDtlTO projectCoststmtChildTO = null;
        if (projCostItemEntity.getProjCostItemEntity() == null) {
        	System.out.println("if condition of addChilds function");
        	System.out.println(projCostItemEntity.getChildEntities().size());
            for (ProjCostItemEntity childEntity : projCostItemEntity.getChildEntities()) {
            	System.out.println(childEntity);
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue() || StatusCodes.DEFAULT.getValue().intValue() == childEntity.getStatus().intValue()) {
                    projectCoststmtChildTO = populateProjCostStmts(childEntity, projCostStmtMap);
                    if (CommonUtil.objectNotNull(projectCoststmtChildTO)) {
                        projCostStmtDtlTOs.add(projectCoststmtChildTO);
                    }
                }
            }
        } else {
        	System.out.println("else condition of addChilds function");
        	System.out.println(projCostItemEntity.getChildEntities().size());
            projCostStmtDtlTO.setParentId(projCostItemEntity.getProjCostItemEntity().getId());
            for (ProjCostItemEntity childEntity : projCostItemEntity.getChildEntities()) {
            	System.out.println(childEntity);
                if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue() || StatusCodes.DEFAULT.getValue().intValue() == childEntity.getStatus().intValue()) {
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

    public static List<ProjCostStmtDtlTO> addExistingCostChilds(ProjCostItemEntity projCostItemEntity,
            ProjCostStmtDtlTO projCostStmtDtlTO, Map<Long, ProjCostStmtDtlEntity> projCostStmtMap) {
        List<ProjCostStmtDtlTO> projCostStmtDtlTOs = new ArrayList<ProjCostStmtDtlTO>();
        ProjCostStmtDtlTO projectCoststmtChildTO = null;
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
