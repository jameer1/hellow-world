package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjSOWItemTO;
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projectlib.model.ProjSOEItemEntity;
import com.rjtech.projectlib.model.ProjSORItemEntity;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.req.ProjSOWItemSaveReq;
import com.rjtech.projschedule.repository.ScheduleActivityDataRepository;
import com.rjtech.centrallib.model.TangibleClassificationEntity;
import com.rjtech.centrallib.service.handler.TangibleClassHandler;

public class ProjSOWItemHandler {

    public static ProjSOWItemTO populateSOWITems(ProjSOEItemEntity projSOEItemEntity,
            Map<Long, ProjSOWItemEntity> projSOWItemMap, ScheduleActivityDataRepository scheduleActivityDataRepository) {
        ProjSOWItemTO projSOWItemTO = null;
        ProjSOWItemEntity projSOWItemEntity = null;
        if (projSOEItemEntity.isItem()) {
            if (projSOWItemMap.get(projSOEItemEntity.getId()) != null) {
                projSOWItemEntity = projSOWItemMap.get(projSOEItemEntity.getId());
                if (StatusCodes.ACTIVE.getValue().intValue() == projSOWItemEntity.getStatus().intValue()) {
                    projSOWItemTO = new ProjSOWItemTO();
                    projSOWItemTO.setProjCostItemTO(ProjCostItemHandler
                            .populateProjCostITems(projSOWItemEntity.getProjCostItemEntity(), false));
                    projSOWItemTO.setProjSORItemTO(
                            ProjSORItemHandler.populateSORITems(projSOWItemEntity.getProjSORItemEntity(), false));
                    projSOWItemTO.setProjSOEItemTO(
                            ProjSOEItemHandler.populateProjSOEITems(projSOWItemEntity.getProjSOEItemEntity(), false, null));
                    projSOWItemTO.setTangibleClassTO(TangibleClassHandler.populateTangibleItems(projSOWItemEntity.getTangibleClassificationEntity(), false));

                    projSOWItemTO.setId(projSOWItemEntity.getId());
                    projSOWItemTO.setCode(projSOWItemEntity.getCode());
                    projSOWItemTO.setName(projSOWItemEntity.getName());
                    projSOWItemTO.setManHours(projSOEItemEntity.getManHours());

                    if (CommonUtil.objectNotNull(projSOWItemEntity.getProjCostItemEntity()))
                        projSOWItemTO.setProjCostId(projSOWItemEntity.getProjCostItemEntity().getId());

                    if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSORItemEntity()))
                        projSOWItemTO.setSorId(projSOWItemEntity.getProjSORItemEntity().getId());

                    if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity()))
                        projSOWItemTO.setSoeId(projSOWItemEntity.getProjSOEItemEntity().getId());
                    
                    if (CommonUtil.objectNotNull(projSOWItemEntity.getTangibleClassificationEntity()))
                        projSOWItemTO.setTangibleItemId(projSOWItemEntity.getTangibleClassificationEntity().getId());

                    projSOWItemTO.setItem(projSOWItemEntity.isItem());
                    if (CommonUtil.isNotBlankDate(projSOWItemEntity.getStartDate())) {
                        projSOWItemTO.setStartDate(CommonUtil.convertDateToString(projSOWItemEntity.getStartDate()));
                    }
                    if (CommonUtil.isNotBlankDate(projSOWItemEntity.getFinishDate())) {
                        projSOWItemTO.setFinishDate(CommonUtil.convertDateToString(projSOWItemEntity.getFinishDate()));
                    }
                    if (CommonUtil.isNotBlankDate(projSOWItemEntity.getActualFinishDate())) {
                        projSOWItemTO.setActualFinishDate(
                                CommonUtil.convertDateToString(projSOWItemEntity.getActualFinishDate()));
                    }
                    projSOWItemTO.setComments(projSOWItemEntity.getComments());

                    projSOWItemTO
                            .setMeasureId(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity().getId());
                    projSOWItemTO.setMeasureUnitTO(MeasurementHandler.convertMeasurePOJOFromEnity(
                            projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity()));

                    projSOWItemTO.setQuantity(projSOWItemEntity.getProjSOEItemEntity().getQuantity());
                    projSOWItemTO.setActualQty(projSOWItemEntity.getActualQty());
                    projSOWItemTO.setRevisedQty(projSOWItemEntity.getRevisedQty());
                    if(projSOWItemEntity.getRevisedQty()!=null && projSOWItemEntity.getActualQty()!=null) {
                    	projSOWItemTO.setBalQty(projSOWItemEntity.getRevisedQty().subtract(projSOWItemEntity.getActualQty()));
                    }else if(projSOWItemEntity.getActualQty()!=null) {
                    	projSOWItemTO.setBalQty(projSOWItemEntity.getActualQty().negate());
                    }
                    projSOWItemTO.setProjId(projSOWItemEntity.getProjMstrEntity().getProjectId());

                    projSOWItemTO.setStatus(projSOWItemEntity.getStatus());
                    projSOWItemTO.setMinStartDateOfBaseline(scheduleActivityDataRepository.findMinimumStartDateOfBaselineBy(projSOWItemTO.getProjId(), projSOWItemTO.getId()));
                    projSOWItemTO.setMaxFinishDateOfBaseline(scheduleActivityDataRepository.findMaximumFinishDateOfBaselineBy(projSOWItemTO.getProjId(), projSOWItemTO.getId()));
                }

            }
        } else {
            projSOWItemTO = new ProjSOWItemTO();
            projSOWItemTO.setId(projSOEItemEntity.getId());
            projSOWItemTO.setCode(projSOEItemEntity.getCode());
            projSOWItemTO.setName(projSOEItemEntity.getName());
            projSOWItemTO.setItem(projSOEItemEntity.isItem());
            projSOWItemTO.setProjId(projSOEItemEntity.getProjMstrEntity().getProjectId());
            projSOWItemTO.setStatus(projSOEItemEntity.getStatus());
        }
        if (CommonUtil.objectNotNull(projSOWItemTO)) {
        	System.out.println("if condition");
        	List<ProjSOWItemTO> childSOWItems = addChilds(projSOEItemEntity, projSOWItemTO, projSOWItemMap, scheduleActivityDataRepository);
        	System.out.println(childSOWItems.size());
        	if( childSOWItems.size() != 0 )
        	{
        		projSOWItemTO.getChildSOWItemTOs().addAll(childSOWItems);
        	}
        }
        return projSOWItemTO;
    }

    public static List<ProjSOWItemTO> addChilds(ProjSOEItemEntity projSOEItemEntity, ProjSOWItemTO projSOWItemTO,
            Map<Long, ProjSOWItemEntity> projSOWItemMap, ScheduleActivityDataRepository scheduleActivityDataRepository) {
        List<ProjSOWItemTO> childSOWTOs = new ArrayList<ProjSOWItemTO>();
        ProjSOWItemTO childSowItemTO = null;
        System.out.println("addChilds function of ProjSOWItemHandler class");
        //System.out.println(projSOEItemEntity.getChildEntities().size());
        if (CommonUtil.objectNotNull(projSOEItemEntity.getChildEntities())) {
        	System.out.println("child soeitementity size:"+projSOEItemEntity.getChildEntities().size());
            for (ProjSOEItemEntity childEntity : projSOEItemEntity.getChildEntities()) {
                childSowItemTO = populateSOWITems(childEntity, projSOWItemMap, scheduleActivityDataRepository);
                System.out.println(childEntity);
                if (CommonUtil.objectNotNull(childSowItemTO)) { 
                    childSOWTOs.add(childSowItemTO);
                }
            }
            System.out.println("end addChilds function of ProjSOWItemHandler class");
        }
        return childSOWTOs;
    }

    public static ProjSOWItemTO populateSOWITems(ProjSOEItemEntity projSOEItemEntity,
            ProjSOWItemEntity projSOWItemEntity, boolean addChild) {
        ProjSOWItemTO projSOWItemTO = null;
        ProjSOEItemEntity parentSoeItem = projSOEItemEntity.getProjSOEItemEntity();
        if (projSOEItemEntity.isItem()) {
            if (StatusCodes.ACTIVE.getValue().intValue() == projSOWItemEntity.getStatus().intValue()) {
                projSOWItemTO = new ProjSOWItemTO();
                projSOWItemTO.setProjCostItemTO(
                        ProjCostItemHandler.populateProjCostITems(projSOWItemEntity.getProjCostItemEntity(), false));
                projSOWItemTO.setProjSORItemTO(
                        ProjSORItemHandler.populateSORITems(projSOWItemEntity.getProjSORItemEntity(), false));
                projSOWItemTO.setProjSOEItemTO(
                        ProjSOEItemHandler.populateProjSOEITems(projSOWItemEntity.getProjSOEItemEntity(), false, null));
                projSOWItemTO.setId(projSOWItemEntity.getId());
                projSOWItemTO.setCode(projSOWItemEntity.getCode());
                projSOWItemTO.setName(projSOWItemEntity.getName());

                if (CommonUtil.objectNotNull(projSOWItemEntity.getProjCostItemEntity()))
                    projSOWItemTO.setProjCostId(projSOWItemEntity.getProjCostItemEntity().getId());

                if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSORItemEntity()))
                    projSOWItemTO.setSorId(projSOWItemEntity.getProjSORItemEntity().getId());

                if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity()))
                    projSOWItemTO.setSoeId(projSOWItemEntity.getProjSOEItemEntity().getId());
                
                if (CommonUtil.objectNotNull(projSOWItemEntity.getTangibleClassificationEntity()))
                    projSOWItemTO.setTangibleItemId(projSOWItemEntity.getTangibleClassificationEntity().getId());

                projSOWItemTO.setItem(projSOWItemEntity.isItem());
                if (CommonUtil.isNotBlankDate(projSOWItemEntity.getStartDate())) {
                    projSOWItemTO.setStartDate(CommonUtil.convertDateToString(projSOWItemEntity.getStartDate()));
                }
                if (CommonUtil.isNotBlankDate(projSOWItemEntity.getFinishDate())) {
                    projSOWItemTO.setFinishDate(CommonUtil.convertDateToString(projSOWItemEntity.getFinishDate()));
                }
                if (CommonUtil.isNotBlankDate(projSOWItemEntity.getActualFinishDate())) {
                    projSOWItemTO.setActualFinishDate(
                            CommonUtil.convertDateToString(projSOWItemEntity.getActualFinishDate()));
                }
                projSOWItemTO.setComments(projSOWItemEntity.getComments());

                projSOWItemTO.setMeasureId(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity().getId());
                projSOWItemTO.setMeasureUnitTO(MeasurementHandler.convertMeasurePOJOFromEnity(
                        projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity()));

                projSOWItemTO.setQuantity(projSOWItemEntity.getProjSOEItemEntity().getQuantity());
                projSOWItemTO.setActualQty(projSOWItemEntity.getActualQty());
                projSOWItemTO.setRevisedQty(projSOWItemEntity.getRevisedQty());
                projSOWItemTO.setProjId(projSOWItemEntity.getProjMstrEntity().getProjectId());
                if (parentSoeItem != null) {
                    projSOWItemTO.setParentId(parentSoeItem.getId());
                    projSOWItemTO.setParentCode(parentSoeItem.getCode());
                    projSOWItemTO.setParentName(parentSoeItem.getName());
                }
                projSOWItemTO.setStatus(projSOWItemEntity.getStatus());

            }
        } else {
            projSOWItemTO = new ProjSOWItemTO();
            projSOWItemTO.setId(projSOEItemEntity.getId());
            projSOWItemTO.setCode(projSOEItemEntity.getCode());
            projSOWItemTO.setName(projSOEItemEntity.getName());
            projSOWItemTO.setItem(projSOEItemEntity.isItem());
            projSOWItemTO.setProjId(projSOEItemEntity.getProjMstrEntity().getProjectId());
            projSOWItemTO.setStatus(projSOEItemEntity.getStatus());
            if (parentSoeItem != null) {
                projSOWItemTO.setParentId(parentSoeItem.getId());
                projSOWItemTO.setParentCode(parentSoeItem.getCode());
                projSOWItemTO.setParentName(parentSoeItem.getName());
            }
        }
        if (CommonUtil.objectNotNull(projSOWItemTO) && addChild) {
            projSOWItemTO.getChildSOWItemTOs().addAll(addChilds(projSOEItemEntity, projSOWItemTO, projSOWItemEntity));
        }
        return projSOWItemTO;
    }

    public static List<ProjSOWItemTO> addChilds(ProjSOEItemEntity projSOEItemEntity, ProjSOWItemTO projSOWItemTO,
            ProjSOWItemEntity projSOWItemMap) {
        List<ProjSOWItemTO> childSOWTOs = new ArrayList<ProjSOWItemTO>();
        ProjSOWItemTO childSowItemTO = null;
        if (CommonUtil.objectNotNull(projSOEItemEntity.getChildEntities())) {
            for (ProjSOEItemEntity childEntity : projSOEItemEntity.getChildEntities()) {
                childSowItemTO = populateSOWITems(childEntity, projSOWItemMap, true);
                if (CommonUtil.objectNotNull(childSowItemTO)) {
                    childSOWTOs.add(childSowItemTO);
                }
            }
        }
        return childSOWTOs;
    }

    public static List<ProjSOWItemEntity> populateEntitisFromPOJO(ProjSOWItemSaveReq projSOWItemSaveReq,
            EPSProjRepository projRepository) {
        List<ProjSOWItemEntity> entities = new ArrayList<ProjSOWItemEntity>();
        ProjSOWItemEntity entity = null;
        for (ProjSOWItemTO projSOWItemTO : projSOWItemSaveReq.getProjSOWItemTOs()) {
            entity = new ProjSOWItemEntity();
            converProjPOJOToEntity(entities, entity, projSOWItemTO, projRepository);
        }
        return entities;
    }

    private static ProjSOWItemEntity converProjPOJOToEntity(List<ProjSOWItemEntity> entities,
            ProjSOWItemEntity projSOWItemEntity, ProjSOWItemTO projSOWItemTO, EPSProjRepository projRepository) {
        if (projSOWItemTO.isItem() && CommonUtil.isNonBlankLong(projSOWItemTO.getId())) {
            projSOWItemEntity.setId(projSOWItemTO.getId());
            projSOWItemEntity.setCode(projSOWItemTO.getCode());
            projSOWItemEntity.setName(projSOWItemTO.getName());
            projSOWItemEntity.setItem(projSOWItemTO.isItem());

            ProjMstrEntity proj = projRepository.findOne(projSOWItemTO.getProjId());
            projSOWItemEntity.setProjMstrEntity(proj);

            Long costId = projSOWItemTO.getProjCostId();
            if (costId != null) {
                ProjCostItemEntity costItemEntity = new ProjCostItemEntity();
                costItemEntity.setId(costId);
                projSOWItemEntity.setProjCostItemEntity(costItemEntity);
            }

            Long sorId = projSOWItemTO.getSorId();
            if (sorId != null) {
                ProjSORItemEntity projSORItemEntity = new ProjSORItemEntity();
                projSORItemEntity.setId(sorId);
                projSOWItemEntity.setProjSORItemEntity(projSORItemEntity);
            }

            Long soeId = projSOWItemTO.getSoeId();
            if (soeId != null) {
                ProjSOEItemEntity projSOEItemEntity = new ProjSOEItemEntity();
                projSOEItemEntity.setId(soeId);
                projSOWItemEntity.setProjSOEItemEntity(projSOEItemEntity);
            }
            
            Long tangibleItemId = projSOWItemTO.getTangibleItemId();
            if (tangibleItemId != null) {
            	TangibleClassificationEntity tangibleClassificationEntity = new TangibleClassificationEntity();
            	tangibleClassificationEntity.setId(tangibleItemId);
            	projSOWItemEntity.setTangibleClassificationEntity(tangibleClassificationEntity);
            }

            projSOWItemEntity.setStartDate(CommonUtil.convertStringToDate(projSOWItemTO.getStartDate()));
            projSOWItemEntity.setFinishDate(CommonUtil.convertStringToDate(projSOWItemTO.getFinishDate()));
            projSOWItemEntity.setActualFinishDate(CommonUtil.convertStringToDate(projSOWItemTO.getActualFinishDate()));

            projSOWItemEntity.setComments(projSOWItemTO.getComments());

            projSOWItemEntity.setActualQty(projSOWItemTO.getActualQty());
            projSOWItemEntity.setRevisedQty(projSOWItemTO.getRevisedQty());
            projSOWItemEntity.setStatus(projSOWItemTO.getStatus());
            entities.add(projSOWItemEntity);
        }
        ProjSOWItemEntity childEntity = null;
        for (ProjSOWItemTO childTO : projSOWItemTO.getChildSOWItemTOs()) {
            childEntity = new ProjSOWItemEntity();
            converProjPOJOToEntity(entities, childEntity, childTO, projRepository);
        }
        return projSOWItemEntity;
    }

    public static ProjSOWItemTO convertEntityToPOJO(ProjSOWItemEntity projSOWItemEntity) {

        ProjSOWItemTO projSOWItemTO = new ProjSOWItemTO();
        projSOWItemTO.setItem(projSOWItemEntity.isItem());
        projSOWItemTO.setProjCostItemTO(
                ProjCostItemHandler.populateProjCostITems(projSOWItemEntity.getProjCostItemEntity(), false));
        projSOWItemTO
                .setProjSORItemTO(ProjSORItemHandler.populateSORITems(projSOWItemEntity.getProjSORItemEntity(), false));
        projSOWItemTO.setProjSOEItemTO(
                ProjSOEItemHandler.populateProjSOEITems(projSOWItemEntity.getProjSOEItemEntity(), false, null));

        projSOWItemTO.setId(projSOWItemEntity.getId());

        projSOWItemTO.setCode(projSOWItemEntity.getCode());
        projSOWItemTO.setName(projSOWItemEntity.getName());
        projSOWItemTO.setSowId(projSOWItemEntity.getId());

        if (CommonUtil.objectNotNull(projSOWItemEntity.getProjCostItemEntity()))
            projSOWItemTO.setProjCostId(projSOWItemEntity.getProjCostItemEntity().getId());
        if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSORItemEntity()))
            projSOWItemTO.setSorId(projSOWItemEntity.getProjSORItemEntity().getId());
        if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity()))
            projSOWItemTO.setSoeId(projSOWItemEntity.getProjSOEItemEntity().getId());

        if (CommonUtil.isNotBlankDate(projSOWItemEntity.getStartDate())) {
            projSOWItemTO.setStartDate(CommonUtil.convertDateToString(projSOWItemEntity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(projSOWItemEntity.getFinishDate())) {
            projSOWItemTO.setFinishDate(CommonUtil.convertDateToString(projSOWItemEntity.getFinishDate()));
        }

        if (CommonUtil.isNotBlankDate(projSOWItemEntity.getActualFinishDate())) {
            projSOWItemTO.setActualFinishDate(CommonUtil.convertDateToString(projSOWItemEntity.getActualFinishDate()));
        }

        projSOWItemTO.setComments(projSOWItemEntity.getComments());

        if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity())
                && CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity())) {
            projSOWItemTO.setMeasureId(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity().getId());
            projSOWItemTO.setMeasureUnitTO(MeasurementHandler
                    .convertMeasurePOJOFromEnity(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity()));
        }

        if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity())) {
            projSOWItemTO.setOriginalQty(projSOWItemEntity.getProjSOEItemEntity().getQuantity());
            projSOWItemTO.setQuantity(projSOWItemEntity.getProjSOEItemEntity().getQuantity());
        }

        projSOWItemTO.setActualQty(projSOWItemEntity.getActualQty());
        projSOWItemTO.setRevisedQty(projSOWItemEntity.getRevisedQty());
        projSOWItemTO.setProjId(projSOWItemEntity.getProjMstrEntity().getProjectId());

        projSOWItemTO.setStatus(projSOWItemEntity.getStatus());
        return projSOWItemTO;
    }

    public static ProjSOWItemTO populateAllSOWITems(ProjSOEItemEntity projSOEItemEntity,
            Map<Long, ProjSOWItemEntity> projSOWItemMap, ScheduleActivityDataRepository scheduleActivityDataRepository) {
        ProjSOWItemTO projSOWItemTO = null;
        ProjSOWItemEntity projSOWItemEntity = null;
        if (projSOEItemEntity.isItem()) {
            if (projSOWItemMap.get(projSOEItemEntity.getId()) != null) {
                projSOWItemEntity = projSOWItemMap.get(projSOEItemEntity.getId());
                if( ( StatusCodes.ACTIVE.getValue().intValue() == projSOWItemEntity.getStatus().intValue() || StatusCodes.DEFAULT.getValue().intValue() == projSOWItemEntity.getStatus().intValue() ) ) {
                    projSOWItemTO = new ProjSOWItemTO();
                    projSOWItemTO.setProjCostItemTO(ProjCostItemHandler.populateProjCostITems(projSOWItemEntity.getProjCostItemEntity(), false));
                    projSOWItemTO.setProjSORItemTO(ProjSORItemHandler.populateSORITems(projSOWItemEntity.getProjSORItemEntity(), false));
                    projSOWItemTO.setProjSOEItemTO(ProjSOEItemHandler.populateProjSOEITems(projSOWItemEntity.getProjSOEItemEntity(), false, null));
                    projSOWItemTO.setTangibleClassTO(TangibleClassHandler.populateTangibleItems(projSOWItemEntity.getTangibleClassificationEntity(), false));

                    projSOWItemTO.setId(projSOWItemEntity.getId());
                    projSOWItemTO.setCode(projSOWItemEntity.getCode());
                    projSOWItemTO.setName(projSOWItemEntity.getName());
                    projSOWItemTO.setManHours(projSOEItemEntity.getManHours());

                    if (CommonUtil.objectNotNull(projSOWItemEntity.getProjCostItemEntity()))
                        projSOWItemTO.setProjCostId(projSOWItemEntity.getProjCostItemEntity().getId());

                    if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSORItemEntity()))
                        projSOWItemTO.setSorId(projSOWItemEntity.getProjSORItemEntity().getId());

                    if (CommonUtil.objectNotNull(projSOWItemEntity.getProjSOEItemEntity()))
                        projSOWItemTO.setSoeId(projSOWItemEntity.getProjSOEItemEntity().getId());
                    
                    if (CommonUtil.objectNotNull(projSOWItemEntity.getTangibleClassificationEntity()))
                        projSOWItemTO.setTangibleItemId(projSOWItemEntity.getTangibleClassificationEntity().getId());

                    projSOWItemTO.setItem(projSOWItemEntity.isItem());
                    if (CommonUtil.isNotBlankDate(projSOWItemEntity.getStartDate())) {
                        projSOWItemTO.setStartDate(CommonUtil.convertDateToString(projSOWItemEntity.getStartDate()));
                    }
                    if (CommonUtil.isNotBlankDate(projSOWItemEntity.getFinishDate())) {
                        projSOWItemTO.setFinishDate(CommonUtil.convertDateToString(projSOWItemEntity.getFinishDate()));
                    }
                    if (CommonUtil.isNotBlankDate(projSOWItemEntity.getActualFinishDate())) {
                        projSOWItemTO.setActualFinishDate(CommonUtil.convertDateToString(projSOWItemEntity.getActualFinishDate()));
                    }
                    projSOWItemTO.setComments(projSOWItemEntity.getComments());

                    projSOWItemTO.setMeasureId(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity().getId());
                    projSOWItemTO.setMeasureUnitTO(MeasurementHandler.convertMeasurePOJOFromEnity(projSOWItemEntity.getProjSOEItemEntity().getMeasurmentMstrEntity()));

                    projSOWItemTO.setQuantity(projSOWItemEntity.getProjSOEItemEntity().getQuantity());
                    projSOWItemTO.setActualQty(projSOWItemEntity.getActualQty());
                    projSOWItemTO.setRevisedQty(projSOWItemEntity.getRevisedQty());
                    projSOWItemTO.setProjId(projSOWItemEntity.getProjMstrEntity().getProjectId());

                    projSOWItemTO.setStatus(projSOWItemEntity.getStatus());
                    projSOWItemTO.setMinStartDateOfBaseline(scheduleActivityDataRepository.findMinimumStartDateOfBaselineBy(projSOWItemTO.getProjId(), projSOWItemTO.getId()));
                    projSOWItemTO.setMaxFinishDateOfBaseline(scheduleActivityDataRepository.findMaximumFinishDateOfBaselineBy(projSOWItemTO.getProjId(), projSOWItemTO.getId()));
                }

            }
        } else {
            projSOWItemTO = new ProjSOWItemTO();
            projSOWItemTO.setId(projSOEItemEntity.getId());
            projSOWItemTO.setCode(projSOEItemEntity.getCode());
            projSOWItemTO.setName(projSOEItemEntity.getName());
            projSOWItemTO.setItem(projSOEItemEntity.isItem());
            projSOWItemTO.setProjId(projSOEItemEntity.getProjMstrEntity().getProjectId());
            projSOWItemTO.setStatus(projSOEItemEntity.getStatus());
        }
        if (CommonUtil.objectNotNull(projSOWItemTO)) {
        	System.out.println("if condition");
        	List<ProjSOWItemTO> childSOWItems = addAllChilds(projSOEItemEntity, projSOWItemTO, projSOWItemMap, scheduleActivityDataRepository);
        	System.out.println(childSOWItems.size());
        	if( childSOWItems.size() != 0 )
        	{
        		projSOWItemTO.getChildSOWItemTOs().addAll(childSOWItems);
        	}
        }
        return projSOWItemTO;
    }
    
    public static List<ProjSOWItemTO> addAllChilds(ProjSOEItemEntity projSOEItemEntity, ProjSOWItemTO projSOWItemTO,
            Map<Long, ProjSOWItemEntity> projSOWItemMap, ScheduleActivityDataRepository scheduleActivityDataRepository) {
        List<ProjSOWItemTO> childSOWTOs = new ArrayList<ProjSOWItemTO>();
        ProjSOWItemTO childSowItemTO = null;
        System.out.println("addAllChilds function of ProjSOWItemHandler class");
        //System.out.println(projSOEItemEntity.getChildEntities().size());
        if (CommonUtil.objectNotNull(projSOEItemEntity.getChildEntities())) {
        	System.out.println("child soeitementity size:"+projSOEItemEntity.getChildEntities().size());
            for (ProjSOEItemEntity childEntity : projSOEItemEntity.getChildEntities()) {
                childSowItemTO = populateAllSOWITems(childEntity, projSOWItemMap, scheduleActivityDataRepository);
                System.out.println(childEntity);
                if (CommonUtil.objectNotNull(childSowItemTO)) { 
                    childSOWTOs.add(childSowItemTO);
                }
            }
            System.out.println("end addAllChilds function of ProjSOWItemHandler class");
        }
        return childSOWTOs;
    }
}
