package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rjtech.centrallib.model.CostMstrEntity;
import com.rjtech.centrallib.repository.CostCodeRepository;
import com.rjtech.centrallib.service.handler.CostCodeHandler;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjCostItemTO;
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.req.ProjCostItemSaveReq;

public class ProjCostItemHandler {

    public static ProjCostItemTO populateProjCostITems(ProjCostItemEntity projCostCodeItemEntity, boolean addChild) {
        ProjCostItemTO projCostCodeItemTO = null;
        if (CommonUtil.objectNotNull(projCostCodeItemEntity)) {
            projCostCodeItemTO = new ProjCostItemTO();
            projCostCodeItemTO.setId(projCostCodeItemEntity.getId());
            projCostCodeItemTO.setCode(projCostCodeItemEntity.getCode());
            projCostCodeItemTO.setName(projCostCodeItemEntity.getName());
            projCostCodeItemTO.setComments(projCostCodeItemEntity.getComments());

            projCostCodeItemTO.setItemParent(projCostCodeItemEntity.isItemParent());

            if (CommonConstants.ITEM_VALUE == projCostCodeItemEntity.getItem()) {
                projCostCodeItemTO.setItem(true);
            } else {
                projCostCodeItemTO.setItem(false);
            }
            if (CommonUtil.isNotBlankDate(projCostCodeItemEntity.getStartDate())) {
                projCostCodeItemTO.setStartDate(CommonUtil.convertDateToString(projCostCodeItemEntity.getStartDate()));
            }
            if (CommonUtil.isNotBlankDate(projCostCodeItemEntity.getFinishDate())) {
                projCostCodeItemTO
                        .setFinishDate(CommonUtil.convertDateToString(projCostCodeItemEntity.getFinishDate()));
            }
            projCostCodeItemTO.setWorkdairyEntry(projCostCodeItemEntity.isWorkDairyStatus());
            ProjMstrEntity project = projCostCodeItemEntity.getProjId();
            if (project != null)
                projCostCodeItemTO.setProjId(project.getProjectId());
            ProjCostItemEntity parent = projCostCodeItemEntity.getProjCostItemEntity();
            if (parent != null)
                projCostCodeItemTO.setParentId(parent.getId());
            CostMstrEntity costMstr = projCostCodeItemEntity.getCostMstrEntity();
            if (costMstr != null)
                projCostCodeItemTO.setCostId(costMstr.getId());
            if (CommonUtil.objectNotNull(projCostCodeItemEntity.getCostMstrEntity())) {
                projCostCodeItemTO
                        .setCostCodeTO(CostCodeHandler.convertEntityToPOJO(projCostCodeItemEntity.getCostMstrEntity()));
            }

            if (CommonUtil.objectNotNull(projCostCodeItemEntity.getProjCostItemEntity())) {
                projCostCodeItemTO.setParentName(projCostCodeItemEntity.getProjCostItemEntity().getName());
                projCostCodeItemTO.setParentCode(projCostCodeItemEntity.getProjCostItemEntity().getCode());
            }
            projCostCodeItemTO.setStatus(projCostCodeItemEntity.getStatus());
            projCostCodeItemTO.setCostCodeItemStatus( projCostCodeItemEntity.getItemStatus() );
            if( projCostCodeItemEntity.getApproverUserId() != null )
            {
            	projCostCodeItemTO.setApproverUserId( projCostCodeItemEntity.getApproverUserId().getUserId() );
            }
            if( projCostCodeItemEntity.getOriginatorUserId() != null )
            {
            	projCostCodeItemTO.setOriginatorUserId( projCostCodeItemEntity.getOriginatorUserId().getUserId() );
            }
            if( projCostCodeItemEntity.getReturnedUserId() != null )
            {
            	projCostCodeItemTO.setReturnedByUserId( projCostCodeItemEntity.getReturnedUserId().getUserId() );
            }
            projCostCodeItemTO.setReturnComments( projCostCodeItemEntity.getReturnComments() );
            projCostCodeItemTO.setApproverComments( projCostCodeItemEntity.getApproverComments() );
            projCostCodeItemTO.setIsItemReturned( projCostCodeItemEntity.getIsItemReturned() );
            if (addChild) {
                projCostCodeItemTO.getProjCostCodeItemTOs().addAll(addChilds(projCostCodeItemEntity, addChild));
            }
        }
        return projCostCodeItemTO;
    }

    public static List<ProjCostItemTO> addChilds(ProjCostItemEntity projCostCodeItemEntity, boolean addChild) {
        List<ProjCostItemTO> childSOETOs = new ArrayList<ProjCostItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (ProjCostItemEntity childEntity : projCostCodeItemEntity.getChildEntities()) {
            /*if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                childSOETOs.add(populateProjCostITems(childEntity, addChild));
            }*/
        	if( StatusCodes.DEACIVATE.getValue().intValue() != childEntity.getStatus().intValue() && StatusCodes.DELETE.getValue().intValue() != childEntity.getStatus().intValue() ) {
                childSOETOs.add(populateProjCostITems(childEntity, addChild));
            }
        }
        return childSOETOs;
    }

    public static List<ProjCostItemEntity> populateEntitisFromPOJO(ProjCostItemSaveReq projCostCodeEItemSaveReq,
            EPSProjRepository epsProjRepository, CostCodeRepository costCodeRepository) {

        List<ProjCostItemEntity> entities = new ArrayList<ProjCostItemEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjCostItemEntity entity = null;
        for (ProjCostItemTO projCostCodeItemTO : projCostCodeEItemSaveReq.getProjCostItemTOs()) {
            entity = new ProjCostItemEntity();
            converPOJOToEntity(entity, projCostCodeItemTO, epsProjRepository, costCodeRepository);
            entities.add(entity);
        }
        return entities;
    }

    private static ProjCostItemEntity converPOJOToEntity(ProjCostItemEntity projCostCodeItemEntity,
            ProjCostItemTO projCostCodeItemTO, EPSProjRepository epsProjRepository,
            CostCodeRepository costCodeRepository) {
        if (CommonUtil.isNonBlankLong(projCostCodeItemTO.getId())) {
            projCostCodeItemEntity.setId(projCostCodeItemTO.getId());
        }
        projCostCodeItemEntity.setCode(projCostCodeItemTO.getCode());
        projCostCodeItemEntity.setName(projCostCodeItemTO.getName());
        projCostCodeItemEntity.setItemParent(projCostCodeItemTO.isItemParent());

        if (CommonUtil.isNonBlankLong(projCostCodeItemTO.getParentId())) {
            ProjCostItemEntity parentEntity = new ProjCostItemEntity();
            parentEntity.setId(projCostCodeItemTO.getParentId());
            projCostCodeItemEntity.setProjCostItemEntity(parentEntity);
        }

        if (projCostCodeItemTO.isItem()) {
            projCostCodeItemEntity.setItem(CommonConstants.ITEM_VALUE);
        } else {
            projCostCodeItemEntity.setItem(CommonConstants.GROUP_VALUE);
        }
        projCostCodeItemEntity.setProjId(epsProjRepository.findOne(projCostCodeItemTO.getProjId()));
        Long costMstrId = projCostCodeItemTO.getCostId();
        if (CommonUtil.isNonBlankLong(costMstrId))
            projCostCodeItemEntity.setCostMstrEntity(costCodeRepository.findOne(costMstrId));

        if (CommonUtil.isNotBlankStr(projCostCodeItemTO.getStartDate())) {
            projCostCodeItemEntity.setStartDate(CommonUtil.convertStringToDate(projCostCodeItemTO.getStartDate()));
        }
        if (CommonUtil.isNotBlankStr(projCostCodeItemTO.getFinishDate())) {
            projCostCodeItemEntity.setFinishDate(CommonUtil.convertStringToDate(projCostCodeItemTO.getFinishDate()));
        }

        projCostCodeItemEntity.setWorkDairyStatus(projCostCodeItemTO.isWorkdairyEntry());
        projCostCodeItemEntity.setComments(projCostCodeItemTO.getComments());

        //projCostCodeItemEntity.setStatus(projCostCodeItemTO.getStatus()); // commented this line as the status will be marked as active once the approval processs completed
        String ccs_item_status = ( CommonUtil.isNonBlankLong( projCostCodeItemTO.getId() ) ) ? projCostCodeItemTO.getCostCodeItemStatus() : "DRAFT";
        //Integer ccs_item_returned = ( CommonUtil.isNonBlankLong( projCostCodeItemTO.getId() ) ) ? projCostCodeItemTO.getIsItemReturned() : 0;
        projCostCodeItemEntity.setStatus( StatusCodes.DEFAULT.getValue() );
        projCostCodeItemEntity.setItemStatus( ccs_item_status );
        projCostCodeItemEntity.setIsItemReturned( 0 );
        
        ProjCostItemEntity childEntity = null;
        for (ProjCostItemTO childTO : projCostCodeItemTO.getProjCostCodeItemTOs()) {
            childEntity = new ProjCostItemEntity();
            childEntity.setProjCostItemEntity(projCostCodeItemEntity);
            projCostCodeItemEntity.getChildEntities()
                    .add(converPOJOToEntity(childEntity, childTO, epsProjRepository, costCodeRepository));
        }
        return projCostCodeItemEntity;
    }

    public static ProjCostItemTO convertCostItemEntityToPOJO(ProjCostItemEntity projCostCodeItemEntity) {
        ProjCostItemTO projCostCodeItemTO = new ProjCostItemTO();
        projCostCodeItemTO.setId(projCostCodeItemEntity.getId());
        projCostCodeItemTO.setCode(projCostCodeItemEntity.getCode());
        projCostCodeItemTO.setName(projCostCodeItemEntity.getName());
        projCostCodeItemTO.setItem(true);
        return projCostCodeItemTO;
    }

    public static LabelKeyTO convertCostItemEntityTOLabelKeyTO(ProjCostItemEntity projCostCodeItemEntity) {
        LabelKeyTO labelKeyTO = new LabelKeyTO();
        labelKeyTO.setId(projCostCodeItemEntity.getId());
        labelKeyTO.setCode(projCostCodeItemEntity.getCode());
        labelKeyTO.setName(projCostCodeItemEntity.getName());

        return labelKeyTO;
    }

    public static boolean checkCostEntityUsedInOtherModule(ProjCostItemTO projCostCodeItemTo, boolean checkChilds,
            Set<Long> usedCostIds) {
        boolean usedInOtherModules = false;
        if (usedCostIds.contains(projCostCodeItemTo.getId())) {
            projCostCodeItemTo.setUsedInOtherModule(true);
            usedInOtherModules = true;
        }
        if (checkChilds) {
            for (ProjCostItemTO child : projCostCodeItemTo.getProjCostCodeItemTOs()) {
                boolean childUsedInOtherModule = checkCostEntityUsedInOtherModule(child, true, usedCostIds);
                // if any one child is used in other module then consider parent is also used in other module.
                if (childUsedInOtherModule) {
                    usedInOtherModules = true;
                    projCostCodeItemTo.setUsedInOtherModule(true);
                }
            }

        }
        return usedInOtherModules;

    }

    /**
     * This method is to convert hibernate given long list to actual java.lang.Long
     * list.
     * 
     * @param longList hibernate provided longs
     * @return actual long set.
     */
    public static Set<Long> convertHibernateIntegerListToLong(List<Long> longList) {
        Set<Long> usedCostIdSet = new HashSet();
        // Don't remove below logic, the logic is not upto the standards but we don't have any other way.
        //Manually create a new set, hibernate is giving List of Integers not the list of Long.
        Number number;
        for (int index = 0; index < longList.size(); index++) {
            number = longList.get(index);
            if (number instanceof Long) {
                usedCostIdSet.add(number.longValue());
            } else if (number instanceof Integer) {
                usedCostIdSet.add(Long.valueOf(number.intValue()));
            }
        }
        return usedCostIdSet;

    }

}
