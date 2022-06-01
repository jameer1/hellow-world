package com.rjtech.projectlib.service.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.EPSProjectTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.dto.ChangeOrderTO;
import com.rjtech.projectlib.dto.CoMaterialTO;
import com.rjtech.projectlib.dto.CoProjCostTO;
import com.rjtech.projectlib.dto.CoProjManpowerTO;
import com.rjtech.projectlib.dto.CoProjPlantTO;
import com.rjtech.projectlib.dto.CoProjSOWTO;
import com.rjtech.projectlib.model.ChangeOrderMstrEntity;
import com.rjtech.projectlib.model.ChangeOrderSOWEntity;
//import com.rjtech.projectlib.repository.ProjManpowerRepositoryCopy;
import com.rjtech.projectlib.req.ProjSaveReq;
import com.rjtech.projectlib.resp.ChangeOrderResp;
import com.rjtech.projsettings.model.COProjCostBudgetEntity;
import com.rjtech.projsettings.model.COProjManpowerEntity;
import com.rjtech.projsettings.model.COProjectMaterialBudgetEntity;
import com.rjtech.projsettings.model.COProjectPlantsDtlEntity;

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

    public static ChangeOrderTO convertChangeOrderEntityToPOJO( ChangeOrderMstrEntity changeOrderMstrEntity )
    {
    	
    	ChangeOrderTO changeOrderTO = new ChangeOrderTO();
    	    	
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
		
		
		return changeOrderTO;
    }
    
    public static CoProjManpowerTO convertCOManpowerPOJO( COProjManpowerEntity entity )
    {
    	CoProjManpowerTO projManpowerTO = new CoProjManpowerTO();
        BeanUtils.copyProperties(entity, projManpowerTO);
        return projManpowerTO;        
    }
    
    public static CoProjPlantTO convertCOPlantPOJO( COProjectPlantsDtlEntity entity )
    {
    	CoProjPlantTO coProjPlantTO = new CoProjPlantTO();
        BeanUtils.copyProperties(entity, coProjPlantTO);
        return coProjPlantTO;        
    }
    
    public static CoMaterialTO convertCOMaterialBudgetPOJO( COProjectMaterialBudgetEntity entity )
    {
    	CoMaterialTO coMaterialTO = new CoMaterialTO();
        BeanUtils.copyProperties(entity, coMaterialTO);
        return coMaterialTO;        
    }
    
    public static CoProjSOWTO convertCOSOWPOJO( ChangeOrderSOWEntity entity )
    {
    	CoProjSOWTO coProjSOWTO = new CoProjSOWTO();
        BeanUtils.copyProperties(entity, coProjSOWTO);
        return coProjSOWTO;        
    }
    
    public static CoProjCostTO convertCOCostBudgetPOJO( COProjCostBudgetEntity entity )
    {
    	CoProjCostTO coProjCostTO = new CoProjCostTO();
        BeanUtils.copyProperties(entity, coProjCostTO);
        return coProjCostTO;        
    }
}
