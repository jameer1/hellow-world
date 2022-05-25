package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.service.handler.MeasurementHandler;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.EPSProjectTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.ProjManpowerTO;
import com.rjtech.projectlib.dto.ChangeOrderTO;
import com.rjtech.projectlib.model.ChangeOrderMapEntity;
import com.rjtech.projectlib.model.ChangeOrderMstrEntity;
import com.rjtech.projectlib.repository.ChangeOrderMapRepository;
//import com.rjtech.projectlib.repository.ProjManpowerRepositoryCopy;
import com.rjtech.projectlib.req.ProjSaveReq;
import com.rjtech.projschedule.repository.ProjManpowerRepositoryCopy;
import com.rjtech.projsettings.model.ProjManpowerEntity;
import com.rjtech.projsettings.service.handler.ProjManpowerHandler;

public class ProjLibServiceHandler {

    public static EPSProjectTO populateProjectTO(ProjMstrEntity projectMstrEntity) {
        EPSProjectTO epsProjectTO = new EPSProjectTO();
        List<EPSProjectTO> childProjs = new ArrayList<EPSProjectTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        epsProjectTO.setProjId(projectMstrEntity.getProjectId());
        epsProjectTO.setClientId(projectMstrEntity.getClientId().getClientId());
        epsProjectTO.setProjCode(projectMstrEntity.getProjName());
        epsProjectTO.setProjName(projectMstrEntity.getCode());
        epsProjectTO.setProj(projectMstrEntity.isProj());
        epsProjectTO.setStatus(projectMstrEntity.getStatus());
        addChildProjects(projectMstrEntity, epsProjectTO, childProjs);
        epsProjectTO.setChildProjs(childProjs);
        return epsProjectTO;
    }

    private static void addChildProjects(ProjMstrEntity projectMstrEntity, EPSProjectTO projectTO,
            List<EPSProjectTO> childProjs) {
        ProjMstrEntity parentProj = projectMstrEntity.getParentProjectMstrEntity();
        if (CommonUtil.objectNotNull(parentProj)) {
            projectTO.setProj(false);
        }
        for (ProjMstrEntity childProjMstr : projectMstrEntity.getChildEntities()) {
            childProjs.add(populateProjectTO(childProjMstr));
        }
    }

    public static List<ProjMstrEntity> populateEntitisFromPOJO(ProjSaveReq projSaveReq) {
        List<ProjMstrEntity> entities = new ArrayList<ProjMstrEntity>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProjMstrEntity entity = null;
        ProjMstrEntity childEntity = null;
        for (EPSProjectTO epsProjectTO : projSaveReq.getProjs()) {
            entity = new ProjMstrEntity();
            converProjPOJOToEntity(entity, childEntity, epsProjectTO);
            entities.add(entity);
        }
        return entities;
    }

    private static ProjMstrEntity converProjPOJOToEntity(ProjMstrEntity projectMstrEntity, ProjMstrEntity childEntity,
            EPSProjectTO projectTO) {
        if (CommonUtil.isNonBlankLong(projectTO.getProjId())) {
            projectMstrEntity.setProjectId(projectTO.getProjId());
        }
        projectMstrEntity.setProjName(projectTO.getProjName());
        projectMstrEntity.setProj(projectTO.isProj());
        projectMstrEntity.setStatus(projectTO.getStatus());

        for (EPSProjectTO childTO : projectTO.getChildProjs()) {
            childEntity = new ProjMstrEntity();
            projectMstrEntity.getChildEntities().add(converProjPOJOToEntity(projectMstrEntity, childEntity, childTO));
        }
        return projectMstrEntity;
    }

    public static ChangeOrderTO convertChangeOrderEntityToPOJO( ChangeOrderMstrEntity changeOrderMstrEntity, ChangeOrderMapRepository changeOrderMapRepo, ProjManpowerRepositoryCopy projManpowerRepo )
    {
    	ChangeOrderTO changeOrderTO = new ChangeOrderTO();
    	System.out.println("changeordermstr entity id:"+changeOrderMstrEntity.getId());
    	//List<ChangeOrderMapEntity> changeOrderMapEntities = changeOrderMapRepo.findCoManpowerDetails( changeOrderMstrEntity.getId() );    	
    	//System.out.println("changeordermap entities size:"+changeOrderMapEntities.size());
    	List<ProjManpowerTO> projManpowerTOList = new ArrayList<>();
    	    	
    	changeOrderTO.setId( changeOrderMstrEntity.getId() );
		changeOrderTO.setContractType( changeOrderMstrEntity.getContractType() );
		//changeOrderTO.setCode( generateChangeOrderCode( changeOrderMstrEntity.getProjectId().getProjectId(), changeOrderMstrEntity.getId() ) );
		changeOrderTO.setProjId( changeOrderMstrEntity.getProjectId().getProjectId() );
		changeOrderTO.setCode( changeOrderMstrEntity.getProjectId().getCode() );
		changeOrderTO.setName( changeOrderMstrEntity.getProjectId().getProjName() );
		changeOrderTO.setDescription( changeOrderMstrEntity.getDescription() );
		changeOrderTO.setCreatedBy( changeOrderMstrEntity.getCreatedBy().getUserId() );
		changeOrderTO.setApprovalStatus( changeOrderMstrEntity.getApprovalStatus() );
		changeOrderTO.setReasonForChange( changeOrderMstrEntity.getReasonForChange() );
		changeOrderTO.setStatus( changeOrderMstrEntity.getStatus() );
		changeOrderTO.setCreatedOn( changeOrderMstrEntity.getCreatedOn() );
		changeOrderTO.setCoCode( changeOrderMstrEntity.getCode() );
		changeOrderTO.setIsExternalApprovalRequired( changeOrderMstrEntity.getIsExternalApprovalRequired() );
		
		if( CommonUtil.objectNotNull( changeOrderMstrEntity.getInternalApproverUserId() ) )
		{
			System.out.println("if block of internalApproverUserId");
			LabelKeyTO internalApproverDetails = new LabelKeyTO();
	    	
			UserMstrEntity internalApproverUserDetails = changeOrderMstrEntity.getInternalApproverUserId();
			internalApproverDetails.setId( internalApproverUserDetails.getUserId() );
			internalApproverDetails.setEmail( internalApproverUserDetails.getEmail() );
			internalApproverDetails.setCode( internalApproverUserDetails.getEmpCode() );
			Map<String, String> intApproverDisplayNamesMap = new HashMap<String, String>();
			intApproverDisplayNamesMap.put( CommonConstants.DISPLAY_NAME , internalApproverUserDetails.getDisplayName() );
			internalApproverDetails.setDisplayNamesMap( intApproverDisplayNamesMap );
			
			changeOrderTO.setInternalApproverLabelKeyTO( internalApproverDetails );
		}
		if( CommonUtil.objectNotNull( changeOrderMstrEntity.getExternalApproverUserId() ) )
		{
			System.out.println("if block of externalApproverUserId");
	    	LabelKeyTO externalApproverDetails = new LabelKeyTO();
	    	
			UserMstrEntity externalApproverUserDetails = changeOrderMstrEntity.getExternalApproverUserId();
			externalApproverDetails.setId( externalApproverUserDetails.getUserId() );
			externalApproverDetails.setEmail( externalApproverUserDetails.getEmail() );
			externalApproverDetails.setCode( externalApproverUserDetails.getEmpCode() );
			Map<String, String> extApproverDisplayNamesMap = new HashMap<String, String>();
			extApproverDisplayNamesMap.put( CommonConstants.DISPLAY_NAME , externalApproverUserDetails.getDisplayName() );
			externalApproverDetails.setDisplayNamesMap( extApproverDisplayNamesMap );
			
			changeOrderTO.setExternalApproverLabelKeyTO( externalApproverDetails );
		}
		if( CommonUtil.objectNotNull( changeOrderMstrEntity.getOriginatorUserId() ) )
		{
			System.out.println("if block of originatorUserId");
	    	LabelKeyTO requestorDetails = new LabelKeyTO();
	    	
			UserMstrEntity requestorUserDetails = changeOrderMstrEntity.getOriginatorUserId();
			requestorDetails.setId( requestorUserDetails.getUserId() );
			requestorDetails.setEmail( requestorUserDetails.getEmail() );
			requestorDetails.setCode( requestorUserDetails.getEmpCode() );
			Map<String, String> displayNamesMap = new HashMap<String, String>();
			displayNamesMap.put( CommonConstants.DISPLAY_NAME , requestorUserDetails.getDisplayName() );
			requestorDetails.setDisplayNamesMap( displayNamesMap );
			
			changeOrderTO.setRequestorLabelKeyTO( requestorDetails );
		}
		/*for( ChangeOrderMapEntity changeOrderMapEntity : changeOrderMapEntities )
		{
			System.out.print("changeordermapentity data:");
			System.out.println(changeOrderMapEntity.getManpowerId());
			projManpowerTOList.add( convertCOMapEntityToManpowerPOJO( changeOrderMapEntity.getManpowerId() ) );
		}
		System.out.println("size projmanpowerto:"+projManpowerTOList.size());*/
		//List<ProjManpowerTO> projManpowerTOList = ProjManpowerHandler.convertEntityToPOJO( projManpowerRepo.findOne( changeOrderMapEntity.getManpowerId() ) );
		changeOrderTO.setProjManpowerTOs( projManpowerTOList );
		return changeOrderTO;
    }
    
    public static ProjManpowerTO convertCOMapEntityToManpowerPOJO( ProjManpowerEntity entity )
    {
        ProjManpowerTO projManpowerTO = new ProjManpowerTO();
        projManpowerTO.setId(entity.getId());
        if (CommonUtil.objectNotNull(entity.getProjMstrEntity()))
            projManpowerTO.setProjId(entity.getProjMstrEntity().getProjectId());

        projManpowerTO.setOriginalQty( entity.getOriginalQty() );
        projManpowerTO.setRevisedQty( entity.getRevisedQty() );
        projManpowerTO.setEstimateComplete( entity.getEstimateComplete() );
        projManpowerTO.setProjEmpCategory( entity.getProjEmpCategory() );
        if (CommonUtil.isNotBlankDate(entity.getStartDate())) {
            projManpowerTO.setStartDate(CommonUtil.convertDateToString(entity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getFinishDate())) {
            projManpowerTO.setFinishDate(CommonUtil.convertDateToString(entity.getFinishDate()));
        }
        EmpClassMstrEntity empClass = entity.getEmpClassMstrEntity();
        if( CommonUtil.objectNotNull( empClass ) ) {
            projManpowerTO.setEmpClassId( empClass.getId() );
            projManpowerTO.setEmpClassTO( ProjManpowerHandler.convertEmpClassEntityToPOJO( empClass ) );
        }
        if( CommonUtil.objectNotNull( entity.getMeasurmentMstrEntity() ) ) {
            projManpowerTO.setMeasureId( entity.getMeasurmentMstrEntity().getId() );
            projManpowerTO.setMeasureUnitTO( MeasurementHandler.convertMeasurePOJOFromEnity( entity.getMeasurmentMstrEntity() ) );
        }

        projManpowerTO.setStatus(entity.getStatus());
        projManpowerTO.setItemStatus( entity.getItemStatus() );            
        return projManpowerTO;        
    }
}
