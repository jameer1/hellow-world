package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.MeasurmentMstrEntity;
import com.rjtech.centrallib.repository.MeasurementRepository;
import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ProjSOEItemTO;
import com.rjtech.projectlib.dto.ProjSOETrackTO;
import com.rjtech.projectlib.model.ProjSOEActivityLogEntity;
import com.rjtech.projectlib.model.ProjSOEItemEntity;
import com.rjtech.projectlib.model.ProjSOETrackRecordsEntity;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjSOEActivityLogRepository;
import com.rjtech.projectlib.repository.ProjSOEItemRepository;
import com.rjtech.projectlib.repository.ProjSOETrackRepository;
import com.rjtech.projectlib.req.ProjSOEItemSaveReq;
import com.rjtech.projectlib.req.ProjSOETrackSaveReq;
import com.rjtech.role.model.UserRoleMapEntityCopy;
import com.rjtech.user.repository.UserRoleMapRepositoryCpy;
public class ProjSOEItemHandler {

    public static ProjSOEItemTO populateProjSOEITems(ProjSOEItemEntity projSOEItemEntity, boolean addChild, UserRoleMapRepositoryCpy userRoleMapRepository) {
        ProjSOEItemTO projSOEItemTO = null;
        if (CommonUtil.objectNotNull(projSOEItemEntity)) {
        	System.out.println(projSOEItemEntity);
            projSOEItemTO = new ProjSOEItemTO();
            projSOEItemTO.setId(projSOEItemEntity.getId());
            projSOEItemTO.setCode(projSOEItemEntity.getCode());
            projSOEItemTO.setName(projSOEItemEntity.getName());
            projSOEItemTO.setProjId(projSOEItemEntity.getProjMstrEntity().getProjectId());
            projSOEItemTO.setManHours(projSOEItemEntity.getManHours());
            
            if (CommonUtil.objectNotNull(projSOEItemEntity.getMeasurmentMstrEntity())) {
                projSOEItemTO.setMeasureId(projSOEItemEntity.getMeasurmentMstrEntity().getId());
                projSOEItemTO.setMeasureUnitTO(
                        MeasurementHandler.convertMeasurePOJOFromEnity(projSOEItemEntity.getMeasurmentMstrEntity()));
            }
            projSOEItemTO.setQuantity(projSOEItemEntity.getQuantity());
            projSOEItemTO.setComments(projSOEItemEntity.getComments());

            projSOEItemTO.setItem(projSOEItemEntity.isItem());
            if (CommonUtil.objectNotNull(projSOEItemEntity.getProjSOEItemEntity())) {
                projSOEItemTO.setParentId(projSOEItemEntity.getProjSOEItemEntity().getId());
                projSOEItemTO.setParentName(projSOEItemEntity.getProjSOEItemEntity().getName());
            }
            
            projSOEItemTO.setStatus( projSOEItemEntity.getStatus() );
           projSOEItemTO.setSoeStatus( projSOEItemEntity.getSoeItemStatus() ); //updated code for return functionality
			/*
			 * if(projSOEItemEntity.getSoeItemStatus().equals(
			 * "SUBMITTED_FOR_INTERNAL_APPROVAL")) {
			 * projSOEItemTO.setSoeStatus("RETURNED_FROM_INTERNAL_APPROVER"); }else {
			 * projSOEItemTO.setSoeStatus("RETURNED_FROM_EXTERNAL_APPROVER"); }
			 */
            projSOEItemTO.setInternalApprovalStatus( projSOEItemEntity.getInternalApprovalStatus() );
            projSOEItemTO.setExternalApprovalStatus( projSOEItemEntity.getExternalApprovalStatus() );
            //projSOEItemTO.setExternalApprovalStatus( projSOEItemEntity.getExternalApprovalStatus() );
            projSOEItemTO.setIsExternalApprovalRequired( projSOEItemEntity.getIsExternalApprovalRequired() );
            if( projSOEItemEntity.getInternalApproverUserId() != null )
    		{
            	if(projSOEItemEntity.getSoeItemStatus().equals(
      				  "SUBMITTED_FOR_INTERNAL_APPROVAL") || projSOEItemEntity.getSoeItemStatus().equals(
      	      				  "SUBMITTED_FOR_EXTERNAL_APPROVAL") || projSOEItemEntity.getSoeItemStatus().equals(
      	            				  "INTERNAL_APPROVED") || projSOEItemEntity.getSoeItemStatus().equals(
      	      	            				  "RETURNED_FROM_INTERNAL_APPROVER") || projSOEItemEntity.getSoeItemStatus().equals(
      	      	      	            				  "RETURNED_FROM_EXTERNAL_APPROVER")) {
      				  projSOEItemTO.setSubmittedDate(projSOEItemEntity.getUpdatedOn());
      				  }
            	projSOEItemTO.setInternalApprovalUserId( projSOEItemEntity.getInternalApproverUserId().getUserId() );
            	if( userRoleMapRepository != null )
            	{
            		UserRoleMapEntityCopy userRoleMapEntity = userRoleMapRepository.findUserRolesByUserId( projSOEItemEntity.getInternalApproverUserId().getUserId() );
                	projSOEItemTO.setInternalApproverUserRoleId( userRoleMapEntity.getRoleMstr().getRoleId().intValue() );
            	}            	
    		}
    		if( projSOEItemEntity.getExternalApproverUserId() != null )
    		{
    			projSOEItemTO.setExternalApprovalUserId( projSOEItemEntity.getExternalApproverUserId().getUserId() );
    			//projSOEItemTO.setInternalApproverUserRoleId( userRoleMapEntity.getRoleMstr().getRoleId().intValue() );
    			if( userRoleMapRepository != null )
    			{
    				UserRoleMapEntityCopy intUserRoleMapEntity = userRoleMapRepository.findUserRolesByUserId( projSOEItemEntity.getExternalApproverUserId().getUserId() );
        	        projSOEItemTO.setExternalApproverUserRoleId( intUserRoleMapEntity.getRoleMstr().getRoleId().intValue() );
    			}    			
    		}
    		if( projSOEItemEntity.getOriginatorUserId() != null )
    		{
    			projSOEItemTO.setOriginatorUserId( projSOEItemEntity.getOriginatorUserId().getUserId() );
    			if( userRoleMapRepository != null )
    			{
    				UserRoleMapEntityCopy extUserRoleMapEntity = userRoleMapRepository.findUserRolesByUserId( projSOEItemEntity.getOriginatorUserId().getUserId() );
        	    	projSOEItemTO.setOriginatorUserRoleId( extUserRoleMapEntity.getRoleMstr().getRoleId().intValue() );
    			}    			
    			//projSOEItemTO.setOriginatorUserRoleId( projSOEItemEntity.getOriginatorUserId().getUserId() );
    		}
    		projSOEItemTO.setInternalApproverComments( projSOEItemEntity.getInternalApproverComments() );
    		projSOEItemTO.setExternalApproverComments( projSOEItemEntity.getExternalApproverComments() );
    		projSOEItemTO.setIsItemReturned( projSOEItemEntity.getIsItemReturned() );    		
    		projSOEItemTO.setSoeItemStatus( projSOEItemEntity.getSoeItemStatus() );    		
    		//projSOEItemTO.setAllowEdit( allowEdit );
            if (addChild) {
                projSOEItemTO.getChildSOEItemTOs().addAll( addChilds( projSOEItemEntity, projSOEItemTO, addChild, userRoleMapRepository ) );
            }
        }
        return projSOEItemTO;
    }
    
    public static ProjSOETrackTO populateProjSOETrackRecords(ProjSOETrackRecordsEntity projSOETrackRecordsEntity, boolean addChild, UserRoleMapRepositoryCpy userRoleMapRepository) {
    	ProjSOETrackTO projSOETrackTO = null;
        if (CommonUtil.objectNotNull(projSOETrackRecordsEntity)) {
        	System.out.println(projSOETrackRecordsEntity);
        	projSOETrackTO = new ProjSOETrackTO();
        	projSOETrackTO.setId(projSOETrackRecordsEntity.getId());
        	projSOETrackTO.setCode(projSOETrackRecordsEntity.getCode());
        	projSOETrackTO.setName(projSOETrackRecordsEntity.getName());
        	projSOETrackTO.setProjId(projSOETrackRecordsEntity.getProjMstrEntity().getProjectId());
        	projSOETrackTO.setManHours(projSOETrackRecordsEntity.getManHours());
            
            if (CommonUtil.objectNotNull(projSOETrackRecordsEntity.getMeasurmentMstrEntity())) {
            	projSOETrackTO.setMeasureId(projSOETrackRecordsEntity.getMeasurmentMstrEntity().getId());
            	projSOETrackTO.setMeasureUnitTO(
                        MeasurementHandler.convertMeasurePOJOFromEnity(projSOETrackRecordsEntity.getMeasurmentMstrEntity()));
            }
            projSOETrackTO.setQuantity(projSOETrackRecordsEntity.getQuantity());
            projSOETrackTO.setComments(projSOETrackRecordsEntity.getComments());

            projSOETrackTO.setItem(projSOETrackRecordsEntity.isItem());
            if (CommonUtil.objectNotNull(projSOETrackRecordsEntity.getProjSOETrackRecordsEntity())) {
            	projSOETrackTO.setParentId(projSOETrackRecordsEntity.getProjSOETrackRecordsEntity().getId());
            	projSOETrackTO.setParentName(projSOETrackRecordsEntity.getProjSOETrackRecordsEntity().getName());
            }
            
            projSOETrackTO.setStatus( projSOETrackRecordsEntity.getStatus() );
            projSOETrackTO.setSoeStatus( projSOETrackRecordsEntity.getSoeItemStatus() ); //updated code for return functionality
	 
    		projSOETrackTO.setInternalApproverComments( projSOETrackRecordsEntity.getInternalApproverComments() );
    		projSOETrackTO.setExternalApproverComments( projSOETrackRecordsEntity.getExternalApproverComments() );
    		projSOETrackTO.setIsItemReturned( projSOETrackRecordsEntity.getIsItemReturned() );    		
    		projSOETrackTO.setSoeItemStatus( projSOETrackRecordsEntity.getSoeItemStatus() );    		
            if (addChild) {           
            	projSOETrackTO.getChildSOEItemTOs().addAll(addChildsTrackRecords(projSOETrackRecordsEntity, projSOETrackTO, addChild, userRoleMapRepository ) );
            }
        }
        return projSOETrackTO;
    }
    
    public static List<ProjSOETrackTO> addChildsTrackRecords(ProjSOETrackRecordsEntity projSOETrackRecordsEntity, ProjSOETrackTO projSOETrackTO,
            boolean addChild, UserRoleMapRepositoryCpy userRoleMapRepository) {
        List<ProjSOETrackTO> childSOETOs = new ArrayList<ProjSOETrackTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        if (CommonUtil.objectNotNull(projSOETrackRecordsEntity.getChildEntities())) {    
            for (ProjSOETrackRecordsEntity childEntity : projSOETrackRecordsEntity.getChildEntities()) {
		if (StatusCodes.DEACIVATE.getValue().intValue() != childEntity.getStatus().intValue()
				&& StatusCodes.DELETE.getValue().intValue() != childEntity.getStatus().intValue()) {
			childSOETOs.add(populateProjSOETrackRecords(childEntity, addChild, userRoleMapRepository));
		}
		 
            }
        }
        return childSOETOs;
    }

    public static List<ProjSOEItemTO> addChilds(ProjSOEItemEntity projSOEItemEntity, ProjSOEItemTO projSOEItemTO,
            boolean addChild, UserRoleMapRepositoryCpy userRoleMapRepository) {
        List<ProjSOEItemTO> childSOETOs = new ArrayList<ProjSOEItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        if (CommonUtil.objectNotNull(projSOEItemEntity.getChildEntities())) {        	
            for (ProjSOEItemEntity childEntity : projSOEItemEntity.getChildEntities()) {
                /*if ( StatusCodes.ACTIVE.getValue().intValue() == childEntity.getStatus().intValue() ) {                	
                    childSOETOs.add(populateProjSOEITems(childEntity, addChild, loggedInUser));
                }*/
            	if ( StatusCodes.DEACIVATE.getValue().intValue() != childEntity.getStatus().intValue() && StatusCodes.DELETE.getValue().intValue() != childEntity.getStatus().intValue() ) {                	
                    childSOETOs.add(populateProjSOEITems(childEntity, addChild, userRoleMapRepository));
                }
            }
        }
        return childSOETOs;
    }

    public static List<ProjSOEItemEntity> populateEntitisFromPOJO(ProjSOEItemSaveReq projSOEItemSaveReq,
            EPSProjRepository projRepository, MeasurementRepository measurementRepository, ProjSOEActivityLogRepository projSOEActivityLogRepository, ProjSOEItemRepository projSOEItemRepository, Long loggedInUser) {
        List<ProjSOEItemEntity> entities = new ArrayList<ProjSOEItemEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjSOEItemEntity entity = null;
        for (ProjSOEItemTO projSOEItemTO : projSOEItemSaveReq.getProjSOEItemTOs()) {
        	List<ProjSOEItemEntity> entitis = projSOEItemRepository.findAllSOEDetails(projSOEItemTO.getProjId());
            entity = new ProjSOEItemEntity();
            ProjSOEItemEntity projSOEItemEntityResult = converProjPOJOToEntity(entity, projSOEItemTO, projRepository, measurementRepository, projSOEActivityLogRepository, projSOEItemRepository, loggedInUser);            
            String activity_type = "";
        	String description = "";     	  
            if(projSOEItemTO.isExpand() && projSOEItemSaveReq.getActionType().equals("Add") && entitis.size() == 0) {
            	ProjSOEActivityLogEntity projSOEActivityLogEntity = new ProjSOEActivityLogEntity();
                List<ProjSOEActivityLogEntity> projSOEActivityLogEntityList = new ArrayList<ProjSOEActivityLogEntity>();
                UserMstrEntity fromUser = new UserMstrEntity();
                fromUser.setUserId( loggedInUser );
                  description = "Created SOE Item";
         	      activity_type = "CREATED_SOE_ITEM";
        		  projSOEActivityLogEntity.setProjSOEItemEntity(projSOEItemEntityResult);
        		  projSOEActivityLogEntity.setActivityType(activity_type);
        		  projSOEActivityLogEntity.setDescription(description);
        		  projSOEActivityLogEntity.setFromUser(fromUser);
        		  projSOEActivityLogEntityList.add(projSOEActivityLogEntity);
        		  projSOEItemEntityResult.setProjSOEActivityLogEntities(projSOEActivityLogEntityList);
            }
            
            entities.add(entity);
        }
        return entities;
    }
    
    public static List<ProjSOETrackRecordsEntity> populateEntitesFromPOJO(ProjSOETrackSaveReq projSOETrackSaveReq,
            EPSProjRepository projRepository, MeasurementRepository measurementRepository, ProjSOEItemRepository projSOEItemRepository, Long loggedInUser, ProjSOETrackRepository projSOETrackRepository) {
        List<ProjSOETrackRecordsEntity> entities = new ArrayList<ProjSOETrackRecordsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjSOETrackRecordsEntity entity = null;
        for (ProjSOETrackTO projSOETrackTO : projSOETrackSaveReq.getProjSOETrackTOs()) {
            entity = new ProjSOETrackRecordsEntity();
            ProjSOETrackRecordsEntity projSOETrackRecordsEntityResult = convertProjTrackPOJOToEntity(projSOETrackSaveReq, entity, projSOETrackTO, projRepository, measurementRepository, projSOEItemRepository, loggedInUser, projSOETrackRepository);            
            entities.add(entity);
        }
        return entities;
    }
    
    private static ProjSOETrackRecordsEntity convertProjTrackPOJOToEntity(ProjSOETrackSaveReq projSOETrackSaveReq, ProjSOETrackRecordsEntity projSOETrackRecordsEntity,
    		ProjSOETrackTO projSOETrackTO, EPSProjRepository projRepository,
            MeasurementRepository measurementRepository, ProjSOEItemRepository projSOEItemRepository, Long loggedInUser, ProjSOETrackRepository projSOETrackRepository) {
        if (CommonUtil.isNonBlankLong(projSOETrackTO.getId())) {
        	projSOETrackRecordsEntity.setId(projSOETrackTO.getId());
        }
        
        String soe_item_status = (CommonUtil.isNonBlankLong( projSOETrackTO.getId() ) ) ? projSOETrackTO.getSoeItemStatus() : "DRAFT";
        Integer soe_item_returned = ( CommonUtil.isNonBlankLong( projSOETrackTO.getId() ) ) ? projSOETrackTO.getIsItemReturned() : 0;
        if(soe_item_status.equals("DRAFT")) {
        	soe_item_status = "Created SOE Item";
        } else
        if(soe_item_status.equals("SUBMITTED_FOR_EXTERNAL_APPROVAL")) {
        	soe_item_status = "Submitted for External Approval";
        	System.out.println("soe_item_status287 "+soe_item_status);
        } else
        if(soe_item_status.equals("SUBMITTED_FOR_INTERNAL_APPROVAL")) {
        	soe_item_status = "Submitted for Internal Approval";
        	System.out.println("soe_item_status291 "+soe_item_status);
        } else
        if(soe_item_status.equals("INTERNAL_APPROVED")) {
        	soe_item_status = "Approved Internally";
        	System.out.println("soe_item_status295 "+soe_item_status);
        } else if(soe_item_status.equals("APPROVED INTERNALLY")) {
        	soe_item_status = "Approved Internally";
        }else
        if(soe_item_status.equals("EXTERNAL_APPROVED")) {
        	soe_item_status = "Approved Externally";
        	System.out.println("soe_item_status299 "+soe_item_status);
        }else
        	if(soe_item_status.equals("RETURNED_FROM_INTERNAL_APPROVER")) {
        		soe_item_status = "Returned with comments from Internal Approver";
        	} else
        	 if(soe_item_status.equals("RETURNED_FROM_EXTERNAL_APPROVER")) {
        		 soe_item_status = "Returned with comments from External Approver"; 
        	 }
          if(CommonUtil.objectNotNull(projSOETrackSaveReq.getResubmitStatus())) {
        	  if(projSOETrackSaveReq.getResubmitStatus().equals("Resubmitted For Internal Approval")) {
     			    soe_item_status = "Resubmitted For Internal Approval";  
     		  }else
     			  if(projSOETrackSaveReq.getResubmitStatus().equals("Resubmitted For External Approval")) {
     				 soe_item_status = "Resubmitted For External Approval";   
     			  }
          }
         		 
        projSOETrackRecordsEntity.setSoeItemStatus(soe_item_status);
        
        projSOETrackRecordsEntity.setIsItemReturned( 0 );
        
        projSOETrackRecordsEntity.setCode(projSOETrackTO.getCode());
        projSOETrackRecordsEntity.setName(projSOETrackTO.getName());

        projSOETrackRecordsEntity.setQuantity(projSOETrackTO.getQuantity());
        projSOETrackRecordsEntity.setManHours(projSOETrackTO.getManHours());
        projSOETrackRecordsEntity.setComments(projSOETrackTO.getComments());

        ProjMstrEntity proj = projRepository.findOne(projSOETrackTO.getProjId());

        projSOETrackRecordsEntity.setProjMstrEntity(proj);

        if (CommonUtil.isNonBlankLong(projSOETrackTO.getParentId())) {
            ProjSOEItemEntity parentEntity = new ProjSOEItemEntity();
            parentEntity.setId(projSOETrackTO.getParentId());
        }        
        if (projSOETrackTO.isItem()) {
            MeasurmentMstrEntity measureEntity = measurementRepository.findOne(projSOETrackTO.getMeasureId());  
            projSOETrackRecordsEntity.setMeasurmentMstrEntity(measureEntity);
            String activity_type = "";
        	String description = "";
        	
            String code = null;
            projSOETrackRecordsEntity.setItem(projSOETrackTO.isItem());
            UserMstrEntity fromUser = new UserMstrEntity();
            fromUser.setUserId( loggedInUser );     
		 
        } else {
        	projSOETrackRecordsEntity.setItem(false);
        }
       
        //projSOEItemEntity.setStatus(projSOEItemTO.getStatus()); // setting status as active while creating the soe items
        projSOETrackRecordsEntity.setStatus( StatusCodes.DEFAULT.getValue() ); //setting status as default while creating the soe items since the status will be activated when approved
        ProjSOETrackRecordsEntity childEntity = null;
        for (ProjSOETrackTO childTO : projSOETrackTO.getChildSOEItemTOs()) {
            childEntity = new ProjSOETrackRecordsEntity();
            childEntity.setProjSOETrackRecordsEntity(projSOETrackRecordsEntity);
              projSOETrackRecordsEntity.getChildEntities()
                    .add(convertProjTrackPOJOToEntity(projSOETrackSaveReq,childEntity, childTO, projRepository, measurementRepository, projSOEItemRepository, loggedInUser, projSOETrackRepository));    
              
			
        }
        return projSOETrackRecordsEntity;
    }

    private static ProjSOEItemEntity converProjPOJOToEntity(ProjSOEItemEntity projSOEItemEntity,
            ProjSOEItemTO projSOEItemTO, EPSProjRepository projRepository,
            MeasurementRepository measurementRepository, ProjSOEActivityLogRepository projSOEActivityLogRepository, ProjSOEItemRepository projSOEItemRepository, Long loggedInUser) {
    	
        if (CommonUtil.isNonBlankLong(projSOEItemTO.getId())) {
            projSOEItemEntity.setId(projSOEItemTO.getId());
        }
        String soe_item_status = ( CommonUtil.isNonBlankLong( projSOEItemTO.getId() ) ) ? projSOEItemTO.getSoeItemStatus() : "DRAFT";
        Integer soe_item_returned = ( CommonUtil.isNonBlankLong( projSOEItemTO.getId() ) ) ? projSOEItemTO.getIsItemReturned() : 0;
        projSOEItemEntity.setSoeItemStatus( soe_item_status );
        projSOEItemEntity.setIsItemReturned( 0 );
        
        projSOEItemEntity.setCode(projSOEItemTO.getCode());
        projSOEItemEntity.setName(projSOEItemTO.getName());

        projSOEItemEntity.setQuantity(projSOEItemTO.getQuantity());
        projSOEItemEntity.setManHours(projSOEItemTO.getManHours());
        projSOEItemEntity.setComments(projSOEItemTO.getComments());

        ProjMstrEntity proj = projRepository.findOne(projSOEItemTO.getProjId());

        projSOEItemEntity.setProjMstrEntity(proj);

        if (CommonUtil.isNonBlankLong(projSOEItemTO.getParentId())) {
            ProjSOEItemEntity parentEntity = new ProjSOEItemEntity();
            parentEntity.setId(projSOEItemTO.getParentId());
            projSOEItemEntity.setProjSOEItemEntity(parentEntity);
        }        
        
        if (projSOEItemTO.isItem()) {
            MeasurmentMstrEntity measureEntity = measurementRepository.findOne(projSOEItemTO.getMeasureId());   
            projSOEItemEntity.setMeasurmentMstrEntity(measureEntity);
            String code = null;
            projSOEItemEntity.setItem(projSOEItemTO.isItem());
            
            if (CommonUtil.isBlankLong(projSOEItemTO.getId())) {
            	System.out.println("if condition of isitem");            	
                List<ProjSOWItemEntity> projSOWItemEntityList = new ArrayList<ProjSOWItemEntity>();
                ProjSOWItemEntity projSOWItemEntity = new ProjSOWItemEntity();
                projSOWItemEntity.setProjSOEItemEntity(projSOEItemEntity);

                code = projSOEItemEntity.getCode();
                System.out.println(code);
                StringBuilder myName = new StringBuilder(code);
                myName.setCharAt(2, 'W');
                code = myName.toString();
                projSOWItemEntity.setCode(code);
                projSOWItemEntity.setName(projSOEItemEntity.getName());
                projSOWItemEntity.setProjMstrEntity(projSOEItemEntity.getProjMstrEntity());
                projSOWItemEntity.setComments(projSOEItemEntity.getComments());
                projSOWItemEntity.setItem(projSOEItemEntity.isItem());
                //projSOWItemEntity.setStatus(StatusCodes.ACTIVE.getValue());
                projSOWItemEntity.setStatus( StatusCodes.DEFAULT.getValue() );
                projSOWItemEntityList.add(projSOWItemEntity);
                projSOEItemEntity.setProjSOWItemEntities(projSOWItemEntityList);
                //projSOEItemEntity.setSoeItemStatus("DRAFT");
                
            	System.out.println( projSOEItemEntity );
            	System.out.println( projSOEItemTO );
            	//String description = ( projSOEItemEntity.getManHours() != projSOEItemTO.getManHours() && projSOEItemEntity.getQuantity() != projSOEItemTO.getQuantity() ) ? "Changed the ManHours and Quantity" : "";
            	
            }
            else
            {
            	System.out.println("else condition of isitem");
            	/*if(!CommonUtil.isNonBlankLong(projSOEItemTO.getId()))
            	{
            		System.out.println("if condition");
            		projSOEItemEntity.setSoeItemStatus("DRAFT");
            	}
            	else
            	{
            		System.out.println("else condition");
            		ProjSOEItemEntity projectSOEItemEntity = projSOEItemRepository.findOne( projSOEItemTO.getId() );
            		projSOEItemEntity.setSoeItemStatus( projectSOEItemEntity.getSoeItemStatus() );
            	}*/
            	//description = ( projSOEItemTO.getManHours() != projSOEItemEntity.getManHours() && projSOEItemTO.getQuantity() != projSOEItemEntity.getQuantity() ) ? "Changed the ManHours and Quantity" : "change values";
				/*
				 * if( projSOEItemEntity.getManHours().compareTo( projSOEItemTO.getManHours() )
				 * != 0 ) { System.out.println("if condition of getManHours"); description =
				 * "Changed the Man Hours from "+projSOEItemTO.getManHours()+
				 * " to "+projSOEItemEntity.getManHours();
				 * projSOEActivityLogEntity.setFromManHrsValue( projSOEItemTO.getManHours() );
				 * projSOEActivityLogEntity.setToManHrsValue( projSOEItemEntity.getManHours() );
				 * }
				 */
				/*
				 * if( projSOEItemEntity.getQuantity().compareTo( projSOEItemTO.getQuantity() )
				 * != 0 ) { System.out.println("if condition of getQuantity"); description =
				 * "Changed the Quantity from "+projSOEItemTO.getQuantity()+
				 * " to "+projSOEItemEntity.getQuantity();
				 * projSOEActivityLogEntity.setFromEstimationValue(
				 * projSOEItemTO.getQuantity().longValue() );
				 * projSOEActivityLogEntity.setToEstimationValue(
				 * projSOEItemEntity.getQuantity().longValue() ); }
				 */
            //	activity_type = "CHANGED_VALUES";   
            	//projSOEActivityLogRepository.save( projSOEActivityLogEntity );
            }
               
     
            //projSOEActivityLogRepository.save( projSOEActivityLogEntity );
        } else {
            projSOEItemEntity.setItem(false);
        }
        //projSOEItemEntity.setStatus(projSOEItemTO.getStatus()); // setting status as active while creating the soe items
        projSOEItemEntity.setStatus( StatusCodes.DEFAULT.getValue() ); //setting status as default while creating the soe items since the status will be activated when approved
        ProjSOEItemEntity childEntity = null;
        for (ProjSOEItemTO childTO : projSOEItemTO.getChildSOEItemTOs()) {
            childEntity = new ProjSOEItemEntity();
            childEntity.setProjSOEItemEntity(projSOEItemEntity);
            projSOEItemEntity.getChildEntities()
                    .add(converProjPOJOToEntity(childEntity, childTO, projRepository, measurementRepository, projSOEActivityLogRepository, projSOEItemRepository, loggedInUser));
        }
        return projSOEItemEntity;
    }

}
