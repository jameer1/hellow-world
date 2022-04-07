package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjSORItemTO;
import com.rjtech.projectlib.model.ProjSORItemEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.req.ProjSORItemSaveReq;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.role.model.UserRoleMapEntityCopy;
import com.rjtech.user.repository.UserRoleMapRepositoryCpy;
import com.rjtech.projectlib.model.ProjSORActivityLogEntity;
import com.rjtech.projectlib.repository.ProjSORItemRepository;
import com.rjtech.projectlib.repository.*;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.projectlib.model.ProjSORTrackRecordsEntity;
import com.rjtech.projectlib.dto.ProjSORTrackTO;
public class ProjSORItemHandler {

    public static ProjSORItemTO populateSORITems(ProjSORItemEntity projSORItemEntity, boolean addChild) {
    	
        ProjSORItemTO projSORItemTO = null;
        if (CommonUtil.objectNotNull(projSORItemEntity)) {
            projSORItemTO = new ProjSORItemTO();
            projSORItemTO.setId(projSORItemEntity.getId());
            projSORItemTO.setCode(projSORItemEntity.getCode());
            projSORItemTO.setName(projSORItemEntity.getName());
            projSORItemTO.setProjId(projSORItemEntity.getProjMstrEntity().getProjectId());

            if (CommonUtil.objectNotNull(projSORItemEntity.getMeasurmentMstrEntity())) {
                projSORItemTO.setMeasureId(projSORItemEntity.getMeasurmentMstrEntity().getId());
                projSORItemTO.setMeasureUnitTO(
                        MeasurementHandler.convertMeasurePOJOFromEnity(projSORItemEntity.getMeasurmentMstrEntity()));
            }

            projSORItemTO.setQuantity(projSORItemEntity.getQuantity());
            projSORItemTO.setManPowerHrs(projSORItemEntity.getManPowerHrs());

            projSORItemTO.setComments(projSORItemEntity.getComments());

            if (projSORItemEntity.getTotalRateIfNoSubCategory() == null) {
            	projSORItemTO.setLabourRate(projSORItemEntity.getLabourRate());
            	projSORItemTO.setPlantRate(projSORItemEntity.getPlantRate());
            	projSORItemTO.setMaterialRate(projSORItemEntity.getMaterialRate());
            	projSORItemTO.setOthersRate(projSORItemEntity.getOthersRate());
            } else {
            	projSORItemTO.setTotalRateIfNoSubCategory(projSORItemEntity.getTotalRateIfNoSubCategory());
            }
            projSORItemTO.setItem(projSORItemEntity.isItem());
            if (CommonUtil.objectNotNull(projSORItemEntity.getProjSORItemEntity())) {
                projSORItemTO.setParentId(projSORItemEntity.getProjSORItemEntity().getId());
                projSORItemTO.setParentName(projSORItemEntity.getProjSORItemEntity().getName());
            }
            projSORItemTO.setStatus(projSORItemEntity.getStatus());
           projSORItemTO.setSorStatus( projSORItemEntity.getSorItemStatus() );
		/*
		 * if(projSORItemEntity.getSorItemStatus().equals(
		 * "SUBMITTED_FOR_INTERNAL_APPROVAL")) {
		 * projSORItemTO.setSorStatus("RETURNED_FROM_INTERNAL_APPROVER"); }else {
		 * projSORItemTO.setSorStatus("RETURNED_FROM_EXTERNAL_APPROVER"); }
		 */
            
            projSORItemTO.setInternalApprovalStatus( projSORItemEntity.getInternalApprovalStatus() );
            projSORItemTO.setExternalApprovalStatus( projSORItemEntity.getExternalApprovalStatus() );
            projSORItemTO.setIsExternalApprovalRequired( projSORItemEntity.getIsExternalApprovalRequired() );
            if( projSORItemEntity.getInternalApproverUserId() != null )
    		{
            	projSORItemTO.setInternalApprovalUserId( projSORItemEntity.getInternalApproverUserId().getUserId() );
    		}
    		if( projSORItemEntity.getExternalApproverUserId() != null )
    		{
    			projSORItemTO.setExternalApprovalUserId( projSORItemEntity.getExternalApproverUserId().getUserId() );
    		}
    		projSORItemTO.setInternalApproverComments( projSORItemEntity.getInternalApproverComments() );
    		projSORItemTO.setExternalApproverComments( projSORItemEntity.getExternalApproverComments() );
    		
            if (addChild) {
                projSORItemTO.getChildSORItemTOs().addAll(addChilds(projSORItemEntity, projSORItemTO, addChild));
            }
        }
        return projSORItemTO;
    }

    // This function is to deal SOR Items with approver and originator validations
    public static ProjSORItemTO convertSORItemsToPOJO( ProjSORItemEntity projSORItemEntity, boolean addChild, UserRoleMapRepositoryCpy userRoleMapRepository ) {
    	System.out.println("convertSORItemsToPOJO function from ProjSORItemHandler class");
    	ProjSORItemTO projSORItemTO = null;
        if (CommonUtil.objectNotNull(projSORItemEntity)) {
            projSORItemTO = new ProjSORItemTO();
            projSORItemTO.setId(projSORItemEntity.getId());
            projSORItemTO.setCode(projSORItemEntity.getCode());
            projSORItemTO.setName(projSORItemEntity.getName());
            projSORItemTO.setProjId(projSORItemEntity.getProjMstrEntity().getProjectId());

            if (CommonUtil.objectNotNull(projSORItemEntity.getMeasurmentMstrEntity())) {
                projSORItemTO.setMeasureId(projSORItemEntity.getMeasurmentMstrEntity().getId());
                projSORItemTO.setMeasureUnitTO(
                        MeasurementHandler.convertMeasurePOJOFromEnity(projSORItemEntity.getMeasurmentMstrEntity()));
            }

            projSORItemTO.setQuantity(projSORItemEntity.getQuantity());
            projSORItemTO.setManPowerHrs(projSORItemEntity.getManPowerHrs());

            projSORItemTO.setComments(projSORItemEntity.getComments());

            if (projSORItemEntity.getTotalRateIfNoSubCategory() == null) {
            	projSORItemTO.setLabourRate(projSORItemEntity.getLabourRate());
            	projSORItemTO.setPlantRate(projSORItemEntity.getPlantRate());
            	projSORItemTO.setMaterialRate(projSORItemEntity.getMaterialRate());
            	projSORItemTO.setOthersRate(projSORItemEntity.getOthersRate());
            } else {
            	projSORItemTO.setTotalRateIfNoSubCategory(projSORItemEntity.getTotalRateIfNoSubCategory());
            }
            projSORItemTO.setItem(projSORItemEntity.isItem());
            if (CommonUtil.objectNotNull(projSORItemEntity.getProjSORItemEntity())) {
                projSORItemTO.setParentId(projSORItemEntity.getProjSORItemEntity().getId());
                projSORItemTO.setParentName(projSORItemEntity.getProjSORItemEntity().getName());
            }
            projSORItemTO.setStatus(projSORItemEntity.getStatus());
            projSORItemTO.setSorStatus( projSORItemEntity.getSorItemStatus() );
            projSORItemTO.setInternalApprovalStatus( projSORItemEntity.getInternalApprovalStatus() );
            projSORItemTO.setExternalApprovalStatus( projSORItemEntity.getExternalApprovalStatus() );
            projSORItemTO.setIsExternalApprovalRequired( projSORItemEntity.getIsExternalApprovalRequired() );
            if( projSORItemEntity.isItem() )
            {
            	System.out.println("convertSORItemsToPOJO function of isitem condition");
            	Long loggedInUser = AppUserUtils.getUserId();
            	Boolean isUserOriginator = false;
            	Boolean isUserInternalApprover = false;
            	Boolean isUserExternalApprover = false;
            	if( loggedInUser != null )
        		{
        			Long loggedInUserRoleId = AppUserUtils.getRoleIds().get(0);
        			System.out.println("loggedInUserRoleId:"+loggedInUserRoleId);
        			if( projSORItemEntity.getOriginatorUserId() != null && projSORItemEntity.getOriginatorUserId().getUserId().compareTo( loggedInUser ) == 0 )
        			{
        				isUserOriginator = true;				
        			}
        			if( projSORItemEntity.getInternalApproverUserId() != null ) {
        			//else if( projSORItemEntity.getInternalApproverUserId() != null ) {
        				System.out.println("elseif condition of internalapproveruserid");
        				UserRoleMapEntityCopy userRoleMapEntity = userRoleMapRepository.findUserRolesByUserId( projSORItemEntity.getInternalApproverUserId().getUserId() );
        				if( userRoleMapEntity.getRoleMstr().getRoleId().compareTo( loggedInUserRoleId ) == 0 )
        				{
        					System.out.println("if condition of rolemstr");
        					isUserInternalApprover = true;
        				}
        			}
        			if( projSORItemEntity.getExternalApproverUserId() != null ) {
        				//isUserInternalApprover = true;
        				System.out.println("elseif condition of externalapproveruserid");
        				UserRoleMapEntityCopy userRoleMapEntity = userRoleMapRepository.findUserRolesByUserId( projSORItemEntity.getExternalApproverUserId().getUserId() );
        				if( userRoleMapEntity.getRoleMstr().getRoleId().compareTo( loggedInUserRoleId ) == 0 )
        				{
        					System.out.println("if condition of rolemstr");
        					isUserExternalApprover = true;
        				}
        			}
        			projSORItemTO.setIsUserOriginator( isUserOriginator );
        			projSORItemTO.setIsUserInternalApprover( isUserInternalApprover );
        			projSORItemTO.setIsUserExternalApprover( isUserExternalApprover );
        		}
            }
            if( projSORItemEntity.getInternalApproverUserId() != null )
    		{
            	projSORItemTO.setInternalApprovalUserId( projSORItemEntity.getInternalApproverUserId().getUserId() );
    		}
    		if( projSORItemEntity.getExternalApproverUserId() != null )
    		{
    			projSORItemTO.setExternalApprovalUserId( projSORItemEntity.getExternalApproverUserId().getUserId() );
    		}
    		if( projSORItemEntity.getOriginatorUserId() != null )
    		{
    			projSORItemTO.setOriginatorUserId( projSORItemEntity.getOriginatorUserId().getUserId() );
    		}
    		projSORItemTO.setInternalApproverComments( projSORItemEntity.getInternalApproverComments() );
    		projSORItemTO.setExternalApproverComments( projSORItemEntity.getExternalApproverComments() );
    		projSORItemTO.setIsItemReturned( projSORItemEntity.getIsItemReturned() );    		
    		projSORItemTO.setItemStatus( projSORItemEntity.getSorItemStatus() ); 
    		//projSORItemTO.setIsItemReturned( projSORItemEntity.getIsItemReturned() );
    		
            if (addChild) {
                projSORItemTO.getChildSORItemTOs().addAll(addChildren( projSORItemEntity, projSORItemTO, addChild, userRoleMapRepository ));
            }
        }
        return projSORItemTO;
    }
    
    public static ProjSORTrackTO convertSORTrackDetailsToPOJO( ProjSORTrackRecordsEntity projSORTrackRecordsEntity, boolean addChild, UserRoleMapRepositoryCpy userRoleMapRepository ) {
    	System.out.println("convertSORItemsToPOJO function from ProjSORItemHandler class");
    	ProjSORTrackTO projSORTrackTO = null;
        if (CommonUtil.objectNotNull(projSORTrackRecordsEntity)) {
        	projSORTrackTO = new ProjSORTrackTO();
        	projSORTrackTO.setId(projSORTrackRecordsEntity.getId());
        	projSORTrackTO.setCode(projSORTrackRecordsEntity.getCode());
        	projSORTrackTO.setName(projSORTrackRecordsEntity.getName());
        	projSORTrackTO.setProjId(projSORTrackRecordsEntity.getProjMstrEntity().getProjectId());

            if (CommonUtil.objectNotNull(projSORTrackRecordsEntity.getMeasurmentMstrEntity())) {
            	projSORTrackTO.setMeasureId(projSORTrackRecordsEntity.getMeasurmentMstrEntity().getId());
            	projSORTrackTO.setMeasureUnitTO(
                        MeasurementHandler.convertMeasurePOJOFromEnity(projSORTrackRecordsEntity.getMeasurmentMstrEntity()));
            }

            projSORTrackTO.setQuantity(projSORTrackRecordsEntity.getQuantity());
            projSORTrackTO.setManPowerHrs(projSORTrackRecordsEntity.getManPowerHrs());

            projSORTrackTO.setComments(projSORTrackRecordsEntity.getComments());

            if (projSORTrackRecordsEntity.getTotalRateIfNoSubCategory() == null) {
            	projSORTrackTO.setLabourRate(projSORTrackRecordsEntity.getLabourRate());
            	projSORTrackTO.setPlantRate(projSORTrackRecordsEntity.getPlantRate());
            	projSORTrackTO.setMaterialRate(projSORTrackRecordsEntity.getMaterialRate());
            	projSORTrackTO.setOthersRate(projSORTrackRecordsEntity.getOthersRate());
            } else {
            	projSORTrackTO.setTotalRateIfNoSubCategory(projSORTrackRecordsEntity.getTotalRateIfNoSubCategory());
            }
            projSORTrackTO.setItem(projSORTrackRecordsEntity.isItem());
            if (CommonUtil.objectNotNull(projSORTrackRecordsEntity.getProjSORTrackRecordsEntity())) {
            	projSORTrackTO.setParentId(projSORTrackRecordsEntity.getProjSORTrackRecordsEntity().getId());
            	projSORTrackTO.setParentName(projSORTrackRecordsEntity.getProjSORTrackRecordsEntity().getName());
            }
            projSORTrackTO.setStatus(projSORTrackRecordsEntity.getStatus());
            projSORTrackTO.setSorStatus( projSORTrackRecordsEntity.getSorItemStatus() );   		
            projSORTrackTO.setItemStatus( projSORTrackRecordsEntity.getSorItemStatus() ); 
    		
            if (addChild) {
            	projSORTrackTO.getChildSORItemTOs().addAll(addSTRChildren( projSORTrackRecordsEntity, projSORTrackTO, addChild, userRoleMapRepository ));
            }
        }
        return projSORTrackTO;
    }
    
    public static List<ProjSORTrackTO> addSTRChildren(ProjSORTrackRecordsEntity projSORTrackRecordsEntity, ProjSORTrackTO projSORTrackTO,
            boolean addChild, UserRoleMapRepositoryCpy userRoleMapRepository ) {
        List<ProjSORTrackTO> childSORTOs = new ArrayList<ProjSORTrackTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        if (CommonUtil.objectNotNull(projSORTrackRecordsEntity.getChildEntities())) {
            for (ProjSORTrackRecordsEntity childEntity : projSORTrackRecordsEntity.getChildEntities()) {
            	if ( StatusCodes.DEACIVATE.getValue().intValue() != childEntity.getStatus().intValue() && StatusCodes.DELETE.getValue().intValue() != childEntity.getStatus().intValue() ) {                	
            		childSORTOs.add(convertSORTrackDetailsToPOJO(childEntity, addChild, userRoleMapRepository));
                }
            }
        }
        return childSORTOs;
    }
    
    public static List<ProjSORItemTO> addChildren(ProjSORItemEntity projSORItemEntity, ProjSORItemTO projSORItemTO,
            boolean addChild, UserRoleMapRepositoryCpy userRoleMapRepository ) {
        List<ProjSORItemTO> childSORTOs = new ArrayList<ProjSORItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        if (CommonUtil.objectNotNull(projSORItemEntity.getChildEntities())) {
            for (ProjSORItemEntity childEntity : projSORItemEntity.getChildEntities()) {
                /*if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childSORTOs.add(convertSORItemsToPOJO(childEntity, addChild, userRoleMapRepository));
                }*/
            	if ( StatusCodes.DEACIVATE.getValue().intValue() != childEntity.getStatus().intValue() && StatusCodes.DELETE.getValue().intValue() != childEntity.getStatus().intValue() ) {                	
            		childSORTOs.add(convertSORItemsToPOJO(childEntity, addChild, userRoleMapRepository));
                }
            }
        }
        return childSORTOs;
    }
    
    public static List<ProjSORItemTO> addChilds(ProjSORItemEntity projSORItemEntity, ProjSORItemTO projSORItemTO,
            boolean addChild) {
        List<ProjSORItemTO> childSORTOs = new ArrayList<ProjSORItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

        if (CommonUtil.objectNotNull(projSORItemEntity.getChildEntities())) {
            for (ProjSORItemEntity childEntity : projSORItemEntity.getChildEntities()) {
                /*if (StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue()) {
                    childSORTOs.add(populateSORITems(childEntity, addChild));
                }*/
                if ( StatusCodes.DEACIVATE.getValue().intValue() != childEntity.getStatus().intValue() && StatusCodes.DELETE.getValue().intValue() != childEntity.getStatus().intValue() ) {
                    childSORTOs.add(populateSORITems(childEntity, addChild));
                }
            }
        }
        return childSORTOs;
    }
    
    public static List<ProjSORTrackRecordsEntity> populateEntitiesFromPOJO(List<ProjSORItemTO> projSORItemTOs,
            EPSProjRepository projRepository, MeasurementRepository measurementRepository,ProjSORItemRepositoryCopy projSORItemRepositoryCopy, String sorStatus) {
        List<ProjSORTrackRecordsEntity> entities = new ArrayList<ProjSORTrackRecordsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjSORTrackRecordsEntity entity = null;
        for (ProjSORItemTO projSOEItemTO : projSORItemTOs) {
       // 	 List<ProjSORItemEntity> projSORItemEntites = projSORItemRepositoryCopy.findAllSORDetails(projSOEItemTO.getProjId());
        	System.out.println("projSOEItemTO297 "+projSOEItemTO.getId());
            entity = new ProjSORTrackRecordsEntity();
            convertSORTRPOJOToEntity(entity, projSOEItemTO, projRepository, measurementRepository, sorStatus);		 
            entities.add(entity);
        }
        return entities;
    }

    public static List<ProjSORItemEntity> populateEntitisFromPOJO(ProjSORItemSaveReq projSORItemSaveReq,
            EPSProjRepository projRepository, MeasurementRepository measurementRepository,ProjSORItemRepositoryCopy projSORItemRepositoryCopy) {
        List<ProjSORItemEntity> entities = new ArrayList<ProjSORItemEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjSORItemEntity entity = null;
        for (ProjSORItemTO projSOEItemTO : projSORItemSaveReq.getProjSORItemTOs()) {
        	 List<ProjSORItemEntity> projSORItemEntites = projSORItemRepositoryCopy.findAllSORDetails(projSOEItemTO.getProjId());
            entity = new ProjSORItemEntity();
            converPOJOToEntity(entity, projSOEItemTO, projRepository, measurementRepository);
			  if(projSORItemEntites.size() == 0) {
				  ProjSORActivityLogEntity projSORActivityLogEntity = new ProjSORActivityLogEntity();	
				  List<ProjSORActivityLogEntity> projSORActivityLogEntites = new ArrayList<ProjSORActivityLogEntity>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
					projSORActivityLogEntity.setProjSORItemEntity(entity);
					UserMstrEntity fromUserEntity = new UserMstrEntity();
					fromUserEntity.setUserId(AppUserUtils.getUserId());
					projSORActivityLogEntity.setDescription("Created SOR Item");
					projSORActivityLogEntity.setFromUser(fromUserEntity);
					projSORActivityLogEntity.setToUser(entity.getCreatedBy());
					projSORActivityLogEntity.setUserComments(entity.getComments());
					projSORActivityLogEntity.setActivityType("CREATED SOR ITEM");
					projSORActivityLogEntites.add(projSORActivityLogEntity); 
					entity.setProjSORActivityLogEntites(projSORActivityLogEntites);
			  }		 
            entities.add(entity);
        }
        return entities;
    }

    private static ProjSORItemEntity converPOJOToEntity(ProjSORItemEntity projSORItemEntity,
            ProjSORItemTO projSORItemTO, EPSProjRepository projRepository,
            MeasurementRepository measurementRepository) {
        if (CommonUtil.isNonBlankLong(projSORItemTO.getId())) {
            projSORItemEntity.setId(projSORItemTO.getId());
        }
        projSORItemEntity.setCode(projSORItemTO.getCode());
        projSORItemEntity.setName(projSORItemTO.getName());

        if (CommonUtil.isNonBlankLong(projSORItemTO.getParentId())) {
            ProjSORItemEntity parentEntity = new ProjSORItemEntity();
            parentEntity.setId(projSORItemTO.getParentId());
            projSORItemEntity.setProjSORItemEntity(parentEntity);
        }

        ProjMstrEntity proj = projRepository.findOne(projSORItemTO.getProjId());
        projSORItemEntity.setProjMstrEntity(proj);

        if (CommonUtil.isNonBlankLong(projSORItemTO.getMeasureId())) {
            MeasurmentMstrEntity measure = measurementRepository.findOne(projSORItemTO.getMeasureId());
            projSORItemEntity.setMeasurmentMstrEntity(measure);
        }

        projSORItemEntity.setComments(projSORItemTO.getComments());
        projSORItemEntity.setQuantity(projSORItemTO.getQuantity());

        projSORItemEntity.setManPowerHrs(projSORItemTO.getManPowerHrs());
        projSORItemEntity.setComments(projSORItemTO.getComments());

        if (projSORItemTO.getTotalRateIfNoSubCategory() == null) {
        	projSORItemEntity.setLabourRate(projSORItemTO.getLabourRate());
        	projSORItemEntity.setPlantRate(projSORItemTO.getPlantRate());
        	projSORItemEntity.setMaterialRate(projSORItemTO.getMaterialRate());
        	projSORItemEntity.setOthersRate(projSORItemTO.getOthersRate());
        } else {
        	projSORItemEntity.setTotalRateIfNoSubCategory(projSORItemTO.getTotalRateIfNoSubCategory());
        }

        projSORItemEntity.setItem(projSORItemTO.isItem());

        //projSORItemEntity.setStatus(projSORItemTO.getStatus()); //while creating the sor items setting the status as active
        projSORItemEntity.setStatus( StatusCodes.DEFAULT.getValue() ); // the sor item should not be activated till finalized i.e., till approved finally        
        
        if( CommonUtil.isBlankLong( projSORItemTO.getId() ) )
        {
        	projSORItemEntity.setSorItemStatus( "DRAFT" );
        }
        else
        {
        	projSORItemEntity.setSorItemStatus( projSORItemTO.getSorStatus() );
        }
        projSORItemEntity.setIsItemReturned( 0 );
        
        ProjSORItemEntity childEntity = null;
        for (ProjSORItemTO childTO : projSORItemTO.getChildSORItemTOs()) {
            childEntity = new ProjSORItemEntity();
            childEntity.setProjSORItemEntity(projSORItemEntity);
            projSORItemEntity.getChildEntities()
                    .add(converPOJOToEntity(childEntity, childTO, projRepository, measurementRepository));
        }
        return projSORItemEntity;
    }
    
    private static ProjSORTrackRecordsEntity convertSORTRPOJOToEntity(ProjSORTrackRecordsEntity projSORTrackRecordsEntity,
            ProjSORItemTO projSORItemTO, EPSProjRepository projRepository,
            MeasurementRepository measurementRepository, String sorStatus) {
        	 
        projSORTrackRecordsEntity.setCode(projSORItemTO.getCode());
        projSORTrackRecordsEntity.setName(projSORItemTO.getName());
       

        ProjMstrEntity proj = projRepository.findOne(projSORItemTO.getProjId());
        projSORTrackRecordsEntity.setProjMstrEntity(proj);

        if (CommonUtil.isNonBlankLong(projSORItemTO.getMeasureId())) {
            MeasurmentMstrEntity measure = measurementRepository.findOne(projSORItemTO.getMeasureId());
            projSORTrackRecordsEntity.setMeasurmentMstrEntity(measure);
        }

        projSORTrackRecordsEntity.setComments(projSORItemTO.getComments());
        projSORTrackRecordsEntity.setQuantity(projSORItemTO.getQuantity());

        projSORTrackRecordsEntity.setManPowerHrs(projSORItemTO.getManPowerHrs());
        projSORTrackRecordsEntity.setComments(projSORItemTO.getComments());

        if (projSORItemTO.getTotalRateIfNoSubCategory() == null) {
        	projSORTrackRecordsEntity.setLabourRate(projSORItemTO.getLabourRate());
        	projSORTrackRecordsEntity.setPlantRate(projSORItemTO.getPlantRate());
        	projSORTrackRecordsEntity.setMaterialRate(projSORItemTO.getMaterialRate());
        	projSORTrackRecordsEntity.setOthersRate(projSORItemTO.getOthersRate());
        } else {
        	projSORTrackRecordsEntity.setTotalRateIfNoSubCategory(projSORItemTO.getTotalRateIfNoSubCategory());
        }

        projSORTrackRecordsEntity.setItem(projSORItemTO.isItem());

        //projSORItemEntity.setStatus(projSORItemTO.getStatus()); //while creating the sor items setting the status as active
        projSORTrackRecordsEntity.setStatus( StatusCodes.DEFAULT.getValue() ); // the sor item should not be activated till finalized i.e., till approved finally        
        
        if( CommonUtil.isBlankLong( projSORItemTO.getId() ) )
        {
        	projSORTrackRecordsEntity.setSorItemStatus("Created SOR Item");
        }
        else
        	if(sorStatus.equals("SUBMIT_FOR_EXTERNAL_APPROVAL"))
        {
        	projSORTrackRecordsEntity.setSorItemStatus( "Submitted for External Approval" );
        }else
		if (sorStatus.equals("SUBMIT_FOR_INTERNAL_APPROVAL")) {
			projSORTrackRecordsEntity.setSorItemStatus("Submitted for Internal Approval");
		}else
		if(sorStatus.equals("INTERNAL_APPROVAL")) {
			projSORTrackRecordsEntity.setSorItemStatus("Approved Internally");	
		}else
		if(sorStatus.equals("EXTERNAL_APPROVAL")) {
			projSORTrackRecordsEntity.setSorItemStatus("Approved Externally");	
		}else
		if(sorStatus.equals("Returned with comments from Internal Approver")) {
			projSORTrackRecordsEntity.setSorItemStatus("Returned with comments from Internal Approver");	
		}else
		if(sorStatus.equals("Returned with comments from External Approver")) {
			projSORTrackRecordsEntity.setSorItemStatus("Returned with comments from External Approver");
		}     	
   //     projSORItemEntity.setIsItemReturned( 0 );
        System.out.println("projSORItemTO.getSorStatus()444 "+projSORItemTO.getSorStatus());
        ProjSORTrackRecordsEntity childEntity = null;
        for (ProjSORItemTO childTO : projSORItemTO.getChildSORItemTOs()) {
            childEntity = new ProjSORTrackRecordsEntity();
            childEntity.setProjSORTrackRecordsEntity(projSORTrackRecordsEntity);
            projSORTrackRecordsEntity.getChildEntities()
                    .add(convertSORTRPOJOToEntity(childEntity, childTO, projRepository, measurementRepository, sorStatus));
        }
        return projSORTrackRecordsEntity;
    }

}
