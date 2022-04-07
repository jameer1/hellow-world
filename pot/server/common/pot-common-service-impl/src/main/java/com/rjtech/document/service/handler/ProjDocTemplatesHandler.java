package com.rjtech.document.service.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.document.dto.TemplateCategoriesTO;
import com.rjtech.document.dto.TemplatesTO;
import com.rjtech.document.dto.FormsTO;
import com.rjtech.document.dto.WorkflowTO;
import com.rjtech.document.dto.ProjectTemplatesProposalTO;

import com.rjtech.document.model.TemplateCategoriesEntity;
import com.rjtech.document.model.CategoriesMstrEntity;
import com.rjtech.document.model.SampleTemplatesEntity;
import com.rjtech.document.model.CentralTemplatesEntity;
import com.rjtech.document.model.ProjectTemplatesEntity;
import com.rjtech.document.model.ProjectFormsEntity;
import com.rjtech.document.model.WorkFlowStagesEntity;
import com.rjtech.document.model.ProjectTemplatesProposalEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.eps.model.ProjMstrEntity;

import com.rjtech.document.repository.TemplateCategoriesRepository;
import com.rjtech.document.repository.SampleTemplatesRepository;
import com.rjtech.document.repository.CentralTemplatesRepository;
import com.rjtech.document.repository.ProjectTemplatesRepository;
import com.rjtech.document.repository.WorkFlowStagesRepository;
import com.rjtech.document.service.impl.ProjDocumentServiceImpl;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

import com.rjtech.document.req.TemplateCategorySaveReq;
import com.rjtech.document.req.TemplateSaveReq;
import com.rjtech.document.req.ProjectFormsGetReq;
import com.rjtech.common.repository.LoginRepository;

public class ProjDocTemplatesHandler {
	private static final Logger log = LoggerFactory.getLogger(ProjDocTemplatesHandler.class);
	
	public static TemplateCategoriesTO convertTemplateCategoriesEntityToPOJO( TemplateCategoriesEntity templateCategoriesEntity, 
			TemplateCategoriesRepository templateCategoriesRepository ) {
    	
		TemplateCategoriesTO templateCategoriesTO = new TemplateCategoriesTO();
		CategoriesMstrEntity categoriesMstrEntity = templateCategoriesEntity.getCategoryMstrId();
       
		templateCategoriesTO.setCategoryId( templateCategoriesEntity.getCategoryId() );
		templateCategoriesTO.setCategoryName( templateCategoriesEntity.getCategoryName() );
		templateCategoriesTO.setColorCode( templateCategoriesEntity.getColorCode() );
		templateCategoriesTO.setCategoryMstrId( categoriesMstrEntity.getCategoryMstrId() );
		templateCategoriesTO.setMstrCategoryName( categoriesMstrEntity.getTemplateCategoryName() );
        
        return templateCategoriesTO;
    }
    
	public static TemplateCategoriesEntity convertPOJOToTemplateCategoriesEntity( TemplateCategorySaveReq templateCategorySaveReq , UserMstrEntity userMstrEntity )
	{
		CategoriesMstrEntity categoriesMstrEntity = new CategoriesMstrEntity();
		categoriesMstrEntity.setCategoryMstrId( templateCategorySaveReq.getCategoryMstrId() );
		
		userMstrEntity.setUserId( templateCategorySaveReq.getCreatedBy() );
		
		TemplateCategoriesEntity templateCategoriesEntity = new TemplateCategoriesEntity();
		templateCategoriesEntity.setCategoryName( templateCategorySaveReq.getCategoryName() );
		templateCategoriesEntity.setCategoryMstrId( categoriesMstrEntity );
		templateCategoriesEntity.setColorCode( templateCategorySaveReq.getColorCode() );
		templateCategoriesEntity.setCreatedBy( userMstrEntity );
		templateCategoriesEntity.setCrmId( templateCategorySaveReq.getCrmId() );
		return templateCategoriesEntity;
	}
	
	public static TemplatesTO convertSampleTemplatesEntityToPOJO( SampleTemplatesEntity sampleTemplatesEntity , 
			SampleTemplatesRepository sampleTemplatesRepository )
	{
		TemplatesTO templatesTO = new TemplatesTO();
		TemplateCategoriesEntity templateCategoriesEntity = sampleTemplatesEntity.getTemplateCategoryId();
		CategoriesMstrEntity categoriesMstrEntity = sampleTemplatesEntity.getCategoryMstrId();
		UserMstrEntity userMstrEntity = sampleTemplatesEntity.getCreatedBy();
		
		templatesTO.setTemplateId( sampleTemplatesEntity.getTemplateId() );
		templatesTO.setTemplateName( sampleTemplatesEntity.getTemplateName() );
		templatesTO.setFormName( sampleTemplatesEntity.getFormName() );
		templatesTO.setVersionStatus( sampleTemplatesEntity.getVersionStatus() );
		templatesTO.setStatus( sampleTemplatesEntity.getStatus() );
		templatesTO.setTemplateJson( sampleTemplatesEntity.getTemplateJson() );
		templatesTO.setTemplateCategoryId( templateCategoriesEntity.getCategoryId() );
		templatesTO.setCategoryMstrId( categoriesMstrEntity.getCategoryMstrId() );
		templatesTO.setCreatedOn( sampleTemplatesEntity.getCreatedOn() );
		templatesTO.setCreatedBy( userMstrEntity.getDisplayName() );
		templatesTO.setTemplCategoryname( templateCategoriesEntity.getCategoryName() );
		templatesTO.setTemplateCode( sampleTemplatesEntity.getTemplateCode() );
		templatesTO.setTemplateType( sampleTemplatesEntity.getTemplateType() );	
		templatesTO.setTemplateVersion( sampleTemplatesEntity.getTemplateVersion() );
		if( sampleTemplatesEntity.getInternalApproverUserId() != null )
		{
			templatesTO.setInternalApprovedBy( sampleTemplatesEntity.getInternalApproverUserId().getUserId() );
		}
		if( sampleTemplatesEntity.getExternalApproverUserId() != null )
		{
			templatesTO.setExternalApprovedBy( sampleTemplatesEntity.getExternalApproverUserId().getUserId() );
		}
		templatesTO.setInternalApprovalStatus( sampleTemplatesEntity.getInternalApprovalStatus() );
		templatesTO.setExternalApprovalStatus( sampleTemplatesEntity.getExternalApprovalStatus() );
		templatesTO.setIsInternalApproved( sampleTemplatesEntity.getIsInternalApproved() );
		templatesTO.setIsExternalApproved( sampleTemplatesEntity.getIsExternalApproved() );
		templatesTO.setIsExternalApprovedRequired( sampleTemplatesEntity.getIsExternalApprovalRequired() );
		templatesTO.setFormsCount( sampleTemplatesEntity.getFormsCount() );
		
		WorkFlowStagesEntity workflowStagesEntity = sampleTemplatesEntity.getWorkflowStagesId();
		if( workflowStagesEntity != null )
		{
			templatesTO.setWorkflowStageId( sampleTemplatesEntity.getWorkflowStagesId().getWorkflowId() );
		}		
		
		return templatesTO;
	}
	
	public static TemplatesTO convertCentralTemplatesEntityToPOJO( CentralTemplatesEntity centralTemplatesEntity , 
			CentralTemplatesRepository centralTemplatesRepository )
	{
		TemplatesTO templatesTO = new TemplatesTO();
		TemplateCategoriesEntity templateCategoriesEntity = centralTemplatesEntity.getTemplateCategoryId();
		
		UserMstrEntity userMstrEntity = centralTemplatesEntity.getCreatedBy();
		UserMstrEntity internalUserMstrEntity = centralTemplatesEntity.getInternalApprovedBy();
		UserMstrEntity externalUserMstrEntity = centralTemplatesEntity.getExternalApprovedBy();
		CategoriesMstrEntity categoriesMstrEntity = new CategoriesMstrEntity();
		ClientRegEntity clientRegEntity = centralTemplatesEntity.getCrmId();
		
		templatesTO.setTemplateId( centralTemplatesEntity.getTemplateId() );
		templatesTO.setTemplateName( centralTemplatesEntity.getTemplateName() );
		templatesTO.setFormName( centralTemplatesEntity.getFormName() );
		templatesTO.setVersionStatus( centralTemplatesEntity.getVersionStatus() );
		templatesTO.setStatus( centralTemplatesEntity.getStatus() );
		templatesTO.setTemplateJson( centralTemplatesEntity.getTemplateJson() );
		templatesTO.setTemplateCategoryId( templateCategoriesEntity.getCategoryId() );
		templatesTO.setCrmId( clientRegEntity.getClientId() );
		templatesTO.setCrmName( clientRegEntity.getName() );
		templatesTO.setCreatedOn( centralTemplatesEntity.getCreatedOn() );
		templatesTO.setCreatedBy( userMstrEntity.getDisplayName() );
		templatesTO.setInternalApprovalStatus( centralTemplatesEntity.getInternalApprovalStatus() );
		templatesTO.setExternalApprovalStatus( centralTemplatesEntity.getExternalApprovalStatus() );
		templatesTO.setIsInternalApproved( centralTemplatesEntity.getIsInternalApproved() );
		templatesTO.setIsExternalApproved( centralTemplatesEntity.getIsExternalApproved() );
		templatesTO.setTemplateType( centralTemplatesEntity.getTemplateType() );
		templatesTO.setTemplateCode( centralTemplatesEntity.getTemplateCode() );
		templatesTO.setTemplCategoryname( templateCategoriesEntity.getCategoryName() );		
		templatesTO.setCategoryMstrId( centralTemplatesEntity.getCategoryMstrId().getCategoryMstrId() );
		templatesTO.setFormsCount( centralTemplatesEntity.getFormsCount() );
		
		if( centralTemplatesEntity.getInternalApprovedBy() == null )
		{
			templatesTO.setInternalApprovedBy( 0L );
		}
		else
		{
			templatesTO.setInternalApprovedBy( internalUserMstrEntity.getUserId() );
		}
		
		if( centralTemplatesEntity.getExternalApprovedBy() == null )
		{
			templatesTO.setExternalApprovedBy( 0L );
		}
		else
		{
			templatesTO.setExternalApprovedBy( externalUserMstrEntity.getUserId() );
		}
		
		templatesTO.setIsExternalApprovedRequired( centralTemplatesEntity.getIsExternalApprovedRequired() );
		WorkFlowStagesEntity workflowStagesEntity = centralTemplatesEntity.getWorkflowStagesId();
		if( workflowStagesEntity != null )
		{
			templatesTO.setWorkflowStageId( centralTemplatesEntity.getWorkflowStagesId().getWorkflowId() );
		}
		
		return templatesTO;
	}
	
	public static TemplatesTO convertProjectTemplatesEntityToPOJO( ProjectTemplatesEntity projectTemplatesEntity , 
			ProjectTemplatesRepository projectTemplatesRepository )
	{
		TemplatesTO templatesTO = new TemplatesTO();
		TemplateCategoriesEntity templateCategoriesEntity = projectTemplatesEntity.getTemplateCategoryId();
		UserMstrEntity userMstrEntity = projectTemplatesEntity.getCreatedBy();
		UserMstrEntity internalUserMstrEntity = projectTemplatesEntity.getInternalApprovedBy();
		UserMstrEntity externalUserMstrEntity = projectTemplatesEntity.getExternalApprovedBy();
		ProjMstrEntity projMstrEntity = projectTemplatesEntity.getProjId();
		CategoriesMstrEntity categoriesMstrEntity = projectTemplatesEntity.getCategoryMstrId();
		
		templatesTO.setTemplateId( projectTemplatesEntity.getTemplateId() );
		templatesTO.setTemplateName( projectTemplatesEntity.getTemplateName() );
		templatesTO.setFormName( projectTemplatesEntity.getFormName() );
		templatesTO.setVersionStatus( projectTemplatesEntity.getVersionStatus() );
		templatesTO.setStatus( projectTemplatesEntity.getStatus() );
		templatesTO.setTemplateJson( projectTemplatesEntity.getTemplateJson() );
		templatesTO.setTemplateCategoryId( templateCategoriesEntity.getCategoryId() );
		templatesTO.setProjectId( projMstrEntity.getProjectId() );
		templatesTO.setCategoryMstrId( categoriesMstrEntity.getCategoryMstrId() );
		templatesTO.setTemplateType( projectTemplatesEntity.getTemplateType() );
		templatesTO.setFormsCount( projectTemplatesEntity.getFormsCount() );
		templatesTO.setCreatedOn( projectTemplatesEntity.getCreatedOn() );
		templatesTO.setCreatedBy( userMstrEntity.getDisplayName() );
		templatesTO.setTemplCategoryname( templateCategoriesEntity.getCategoryName() );
		templatesTO.setInternalApprovalStatus( projectTemplatesEntity.getInternalApprovalStatus() );
		templatesTO.setExternalApprovalStatus( projectTemplatesEntity.getExternalApprovalStatus() );
		templatesTO.setIsInternalApproved( projectTemplatesEntity.getIsInternalApproved() );
		templatesTO.setIsExternalApproved( projectTemplatesEntity.getIsExternalApproved() );
		templatesTO.setTemplateType( projectTemplatesEntity.getTemplateType() );
		templatesTO.setTemplateCode( projectTemplatesEntity.getTemplateCode() );
		templatesTO.setFromSource( projectTemplatesEntity.getFromSource() );
		//templatesTO.setCrmId( projMstrEntity.getClientId().getClientId() );
		//templatesTO.setCrmName( projMstrEntity.getClientId().getName() );
		
		if( projectTemplatesEntity.getInternalApprovedBy() == null )
		{
			templatesTO.setInternalApprovedBy( 0L );
		}
		else
		{
			templatesTO.setInternalApprovedBy( internalUserMstrEntity.getUserId() );
		}
		
		if( projectTemplatesEntity.getExternalApprovedBy() == null )
		{
			templatesTO.setExternalApprovedBy( 0L );
		}
		else
		{
			templatesTO.setExternalApprovedBy( externalUserMstrEntity.getUserId() );
		}
		templatesTO.setIsExternalApprovalRequired( projectTemplatesEntity.getIsExternalApprovalRequired() );
		WorkFlowStagesEntity workflowStagesEntity = projectTemplatesEntity.getWorkflowStagesId();
		if( workflowStagesEntity != null )
		{
			templatesTO.setWorkflowStageId( projectTemplatesEntity.getWorkflowStagesId().getWorkflowId() );
		}
		return templatesTO;
	}
	
	public static FormsTO convertProjectFormsEntityToPOJO( ProjectFormsEntity projectFormsEntity )
	{
		FormsTO formsTO = new FormsTO();
		TemplateCategoriesEntity templateCategoriesEntity = projectFormsEntity.getTemplateCategoryId();
		UserMstrEntity userMstrEntity = projectFormsEntity.getCreatedBy();
		ProjMstrEntity projMstrEntity = projectFormsEntity.getProjectId();
		ProjectTemplatesEntity projectTemplatesEntity = projectFormsEntity.getProjectTemplateId();
		
		formsTO.setFormId( projectFormsEntity.getFormId() );
		formsTO.setFormName( projectFormsEntity.getFormName() );		
		formsTO.setStatus( projectFormsEntity.getStatus() );		
		formsTO.setFormJson( projectFormsEntity.getFormJson() );
		formsTO.setTemplateJson( projectFormsEntity.getTemplateJson() );
		formsTO.setFormType( projectFormsEntity.getFormType() );		
		formsTO.setTemplateCategoryId( templateCategoriesEntity.getCategoryId() );		
		formsTO.setProjectId( projMstrEntity.getProjectId() );		
		formsTO.setProjectTemplateId( projectTemplatesEntity.getTemplateId() );		
		formsTO.setCreatedOn( projectFormsEntity.getCreatedOn() );
		formsTO.setCreatedBy( userMstrEntity.getDisplayName() );
		formsTO.setFormVersion( projectFormsEntity.getFormVersion() );
		formsTO.setFromSource( projectTemplatesEntity.getFromSource() );
		
		return formsTO;
	}
	
	public static ProjectFormsEntity convertPOJOToProjectFormsEntity( ProjectFormsGetReq projectFormsGetReq , ProjectFormsEntity projectFormsEntity )
	{
		TemplateCategoriesEntity templateCategoriesEntity = new TemplateCategoriesEntity();
		templateCategoriesEntity.setCategoryId( projectFormsGetReq.getTemplateCategoryId() );
		ProjectTemplatesEntity projectTemplatesEntity = new ProjectTemplatesEntity();
		projectTemplatesEntity.setTemplateId( projectFormsGetReq.getProjectTemplateId() );
		UserMstrEntity userMstrEntity = new UserMstrEntity();
		userMstrEntity.setUserId( projectFormsGetReq.getCreatedBy() );
		ProjMstrEntity projMstrEntity = new ProjMstrEntity();
		projMstrEntity.setProjectId( projectFormsGetReq.getProjectId() );
		
		if( projectFormsGetReq.getMode().equals("CREATE") )
		{
			projectFormsEntity.setFormName( projectTemplatesEntity.getFormName() );		
			projectFormsEntity.setStatus( projectFormsGetReq.getStatus() );		
			projectFormsEntity.setTemplateJson( projectFormsGetReq.getTemplateJson() );
			projectFormsEntity.setFormType( projectFormsGetReq.getFormType() );	
			projectFormsEntity.setTemplateCategoryId( templateCategoriesEntity );		
			projectFormsEntity.setProjectId( projMstrEntity );		
			projectFormsEntity.setProjectTemplateId( projectTemplatesEntity );
			projectFormsEntity.setCreatedBy( userMstrEntity );
		}
		else
		{
			projectFormsEntity.setFormId( projectFormsGetReq.getFormId() );
		}
		projectFormsEntity.setFormJson( projectFormsGetReq.getFormJson() );
		projectFormsEntity.setFormVersion( projectFormsGetReq.getFormVersion() );
		
		return projectFormsEntity;
	}
	
	public static SampleTemplatesEntity convertPOJOToTemplatesEntity( TemplateSaveReq templateSaveReq , UserMstrEntity userMstrEntity, String categoryMstrName, WorkFlowStagesRepository workFlowStagesRepository )
	{
		CategoriesMstrEntity categoriesMstrEntity = new CategoriesMstrEntity();
		categoriesMstrEntity.setCategoryMstrId( templateSaveReq.getCategoryMstrId() );
		
		TemplateCategoriesEntity templateCategoriesEntity = new TemplateCategoriesEntity();
		templateCategoriesEntity.setCategoryId( templateSaveReq.getTemplateCategoryId() );
		
		//UserMstrEntity userMstrEntity = new UserMstrEntity();
		userMstrEntity.setUserId( templateSaveReq.getCreatedBy() );
		
		SampleTemplatesEntity sampleTemplatesEntity = new SampleTemplatesEntity();	
		WorkFlowStagesEntity workFlowStagesEntity = new WorkFlowStagesEntity();
		WorkFlowStagesEntity resWorkFlowStagesEntity = new WorkFlowStagesEntity();
		//String categoryMstrName = "Sample Templates"; // should make dynamic
		
		sampleTemplatesEntity.setTemplateName( templateSaveReq.getTemplateName() );
		sampleTemplatesEntity.setFormName( templateSaveReq.getFormName() );
		sampleTemplatesEntity.setTemplateType( templateSaveReq.getTemplateType() );		
		sampleTemplatesEntity.setTemplateCategoryId( templateCategoriesEntity );
		sampleTemplatesEntity.setCategoryMstrId( categoriesMstrEntity );
		sampleTemplatesEntity.setCreatedBy( userMstrEntity );
		sampleTemplatesEntity.setStatus( templateSaveReq.getStatus() );
		sampleTemplatesEntity.setVersionStatus( templateSaveReq.getVersionStatus() );
		sampleTemplatesEntity.setFormsCount( templateSaveReq.getFormsCount() );
		sampleTemplatesEntity.setTemplateType( templateSaveReq.getTemplateType() );
		sampleTemplatesEntity.setTemplateCode( templateSaveReq.getTemplateCode() );
		if( templateSaveReq.getMode() != null )
		{
			sampleTemplatesEntity.setTemplateJson( templateSaveReq.getTemplateJson() );			
		}
		if( templateSaveReq.getTemplateType().toUpperCase().equals("WORKFLOW") )
		{
			resWorkFlowStagesEntity = workFlowStagesRepository.save( convertPOJOToWorkFlowEntity( templateSaveReq , workFlowStagesEntity , categoryMstrName , "NEW" ) );
			sampleTemplatesEntity.setWorkflowStagesId( resWorkFlowStagesEntity );
		}
		return sampleTemplatesEntity;
	}
	
	public static CentralTemplatesEntity convertPOJOToCTemplatesEntity( TemplateSaveReq templateSaveReq , UserMstrEntity userMstrEntity, String categoryMstrName, WorkFlowStagesRepository workFlowStagesRepository )
	{
		CategoriesMstrEntity categoriesMstrEntity = new CategoriesMstrEntity();
		categoriesMstrEntity.setCategoryMstrId( templateSaveReq.getCategoryMstrId() );
		
		TemplateCategoriesEntity templateCategoriesEntity = new TemplateCategoriesEntity();
		templateCategoriesEntity.setCategoryId( templateSaveReq.getTemplateCategoryId() );
		
		ClientRegEntity clientRegEntity = new ClientRegEntity();
		clientRegEntity.setClientId( templateSaveReq.getCrmId() );
				
		userMstrEntity.setUserId( templateSaveReq.getCreatedBy() );
		
		CentralTemplatesEntity centralTemplatesEntity = new CentralTemplatesEntity();	
		WorkFlowStagesEntity workFlowStagesEntity = new WorkFlowStagesEntity();
		WorkFlowStagesEntity resWorkFlowStagesEntity = new WorkFlowStagesEntity();
		
		centralTemplatesEntity.setTemplateName( templateSaveReq.getTemplateName() );
		centralTemplatesEntity.setFormName( templateSaveReq.getFormName() );
		centralTemplatesEntity.setTemplateType( templateSaveReq.getTemplateType() );		
		centralTemplatesEntity.setTemplateCategoryId( templateCategoriesEntity );
		centralTemplatesEntity.setCategoryMstrId( categoriesMstrEntity );
		centralTemplatesEntity.setCrmId( clientRegEntity );
		centralTemplatesEntity.setCreatedBy( userMstrEntity );
		centralTemplatesEntity.setStatus( templateSaveReq.getStatus() );
		centralTemplatesEntity.setVersionStatus( templateSaveReq.getVersionStatus() );
		centralTemplatesEntity.setFormsCount( templateSaveReq.getFormsCount() );
		centralTemplatesEntity.setTemplateCode( templateSaveReq.getTemplateCode() );
		centralTemplatesEntity.setTemplateType( templateSaveReq.getTemplateType() );
		if( templateSaveReq.getMode() != null )
		{
			centralTemplatesEntity.setTemplateJson( templateSaveReq.getTemplateJson() );			
		}
		if( templateSaveReq.getTemplateType().toUpperCase().equals("WORKFLOW") )
		{
			resWorkFlowStagesEntity = workFlowStagesRepository.save( convertPOJOToWorkFlowEntity( templateSaveReq , workFlowStagesEntity , categoryMstrName , "NEW" ) );
			centralTemplatesEntity.setWorkflowStagesId( resWorkFlowStagesEntity );
		}
		
		return centralTemplatesEntity;
	}
	
	public static ProjectTemplatesEntity convertPOJOToPTemplatesEntity( TemplateSaveReq templateSaveReq , UserMstrEntity userMstrEntity, String categoryMstrName, WorkFlowStagesRepository workFlowStagesRepository )
	{
		System.out.println("convertPOJOToPTemplatesEntity function");
		System.out.println(templateSaveReq);
		CategoriesMstrEntity categoriesMstrEntity = new CategoriesMstrEntity();
		categoriesMstrEntity.setCategoryMstrId( templateSaveReq.getCategoryMstrId() );
		
		TemplateCategoriesEntity templateCategoriesEntity = new TemplateCategoriesEntity();
		templateCategoriesEntity.setCategoryId( templateSaveReq.getTemplateCategoryId() );
		
		ClientRegEntity clientRegEntity = new ClientRegEntity();
		clientRegEntity.setClientId( templateSaveReq.getCrmId() );
				
		userMstrEntity.setUserId( templateSaveReq.getCreatedBy() );
		
		ProjMstrEntity projMstrEntity = new ProjMstrEntity();
		projMstrEntity.setProjectId( templateSaveReq.getProjId() );
		
		ProjectTemplatesEntity projectTemplatesEntity = new ProjectTemplatesEntity();
		WorkFlowStagesEntity workFlowStagesEntity = new WorkFlowStagesEntity();
		WorkFlowStagesEntity resWorkFlowStagesEntity = new WorkFlowStagesEntity();
		
		projectTemplatesEntity.setTemplateName( templateSaveReq.getTemplateName() );
		projectTemplatesEntity.setFormName( templateSaveReq.getFormName() );
		projectTemplatesEntity.setTemplateType( templateSaveReq.getTemplateType() );		
		projectTemplatesEntity.setTemplateCategoryId( templateCategoriesEntity );
		projectTemplatesEntity.setCategoryMstrId( categoriesMstrEntity );
		projectTemplatesEntity.setCreatedBy( userMstrEntity );
		projectTemplatesEntity.setCrmId( clientRegEntity );
		projectTemplatesEntity.setProjId( projMstrEntity );
		projectTemplatesEntity.setStatus( templateSaveReq.getStatus() );
		projectTemplatesEntity.setVersionStatus( templateSaveReq.getVersionStatus() );
		projectTemplatesEntity.setFormsCount( templateSaveReq.getFormsCount() );
		projectTemplatesEntity.setIsNew( templateSaveReq.getIsNew() );
		projectTemplatesEntity.setTemplateCode( templateSaveReq.getTemplateCode() );
		projectTemplatesEntity.setFromSource( templateSaveReq.getFromSource() );
		if( templateSaveReq.getFromSource() != null )
		{
			if( templateSaveReq.getFromSource().equals("From Sample Templates") )
			{
				SampleTemplatesEntity sampleTemplatesEntity = new SampleTemplatesEntity();
				sampleTemplatesEntity.setTemplateId(templateSaveReq.getTemplateId());
				projectTemplatesEntity.setSampleTemplateId( sampleTemplatesEntity );
			}
			if( templateSaveReq.getFromSource().equals("From Central Templates") )
			{
				CentralTemplatesEntity centralTemplatesEntity = new CentralTemplatesEntity();
				centralTemplatesEntity.setTemplateId(templateSaveReq.getTemplateId());
				projectTemplatesEntity.setCentralTemplateId( centralTemplatesEntity );
			}
		}		
		if( templateSaveReq.getTemplateJson() != null )
		{
			projectTemplatesEntity.setTemplateJson( templateSaveReq.getTemplateJson() );
		}
		if( templateSaveReq.getTemplateType().toUpperCase().equals("WORKFLOW") )
		{
			resWorkFlowStagesEntity = workFlowStagesRepository.save( convertPOJOToWorkFlowEntity( templateSaveReq , workFlowStagesEntity , categoryMstrName , "NEW" ) );
			projectTemplatesEntity.setWorkflowStagesId( resWorkFlowStagesEntity );
		}
		
		return projectTemplatesEntity;
	}
	
	public static WorkFlowStagesEntity convertPOJOToWorkFlowEntity( TemplateSaveReq templateSaveReq , WorkFlowStagesEntity workFlowStagesEntity , String categoryMstrName , String mode )
	{
		if( mode.equals("NEW") )
		{
			CategoriesMstrEntity categoriesMstrEntity = new CategoriesMstrEntity();
			categoriesMstrEntity.setCategoryMstrId( templateSaveReq.getCategoryMstrId() );
			
			TemplateCategoriesEntity templateCategoriesEntity = new TemplateCategoriesEntity();
			templateCategoriesEntity.setCategoryId( templateSaveReq.getTemplateCategoryId() );
			
			//workFlowStagesEntity.setTemplateCategoryId( templateCategoriesEntity );
			//workFlowStagesEntity.setCategoryMstrId( categoriesMstrEntity );
			if( categoryMstrName.equals("Sample Templates") )
			{
				SampleTemplatesEntity sampleTemplatesEntity = new SampleTemplatesEntity();
				sampleTemplatesEntity.setTemplateId( templateSaveReq.getTemplateId() );
				//workFlowStagesEntity.setSampleTemplateId( sampleTemplatesEntity );
			}
			else if( categoryMstrName.equals("Central Templates") )
			{
				CentralTemplatesEntity centralTemplatesEntity = new CentralTemplatesEntity();
				centralTemplatesEntity.setTemplateId( templateSaveReq.getTemplateId() );
				//workFlowStagesEntity.setCentralTemplateId( centralTemplatesEntity );
			}
			else
			{
				ProjectTemplatesEntity projectTemplatesEntity = new ProjectTemplatesEntity();
				projectTemplatesEntity.setTemplateId( templateSaveReq.getTemplateId() );
				//workFlowStagesEntity.setProjectTemplateId( projectTemplatesEntity );
			}
		}
		if( templateSaveReq.getTemplateType().equals("WORKFLOW") )
		{
			if( !templateSaveReq.getIsFinalApproval() )
			{
				if( templateSaveReq.getStageFieldJson() != null && templateSaveReq.getApprovalStageName().equals("STAGE1") )
				{
					workFlowStagesEntity.setStage1Name( templateSaveReq.getStageFieldJson() );
					workFlowStagesEntity.setStatus( "STAGE1_APPROVAL_SUBMITTED" );
					workFlowStagesEntity.setStage1Status( "SUBMITTED" );
					UserMstrEntity userMstrEntity = new UserMstrEntity();
					userMstrEntity.setUserId( templateSaveReq.getApproverUserId() );
					workFlowStagesEntity.setStage1ApproverId( userMstrEntity );
				}
			}					
		}
		return workFlowStagesEntity;
	}
	
	/*public static WorkFlowStagesEntity convertPOJOToWorkFlowEntity( TemplateSaveReq templateSaveReq , WorkFlowStagesEntity workFlowStagesEntity , String categoryMstrName , String mode )
	{
		if( mode.equals("NEW") )
		{
			CategoriesMstrEntity categoriesMstrEntity = new CategoriesMstrEntity();
			categoriesMstrEntity.setCategoryMstrId( templateSaveReq.getCategoryMstrId() );
			
			TemplateCategoriesEntity templateCategoriesEntity = new TemplateCategoriesEntity();
			templateCategoriesEntity.setCategoryId( templateSaveReq.getTemplateCategoryId() );
			
			workFlowStagesEntity.setTemplateCategoryId( templateCategoriesEntity );
			workFlowStagesEntity.setCategoryMstrId( categoriesMstrEntity );
			if( categoryMstrName.equals("Sample Templates") )
			{
				SampleTemplatesEntity sampleTemplatesEntity = new SampleTemplatesEntity();
				sampleTemplatesEntity.setTemplateId( templateSaveReq.getTemplateId() );
				workFlowStagesEntity.setSampleTemplateId( sampleTemplatesEntity );
			}
			else if( categoryMstrName.equals("Central Templates") )
			{
				CentralTemplatesEntity centralTemplatesEntity = new CentralTemplatesEntity();
				centralTemplatesEntity.setTemplateId( templateSaveReq.getTemplateId() );
				workFlowStagesEntity.setCentralTemplateId( centralTemplatesEntity );
			}
			else
			{
				ProjectTemplatesEntity projectTemplatesEntity = new ProjectTemplatesEntity();
				projectTemplatesEntity.setTemplateId( templateSaveReq.getTemplateId() );
				workFlowStagesEntity.setProjectTemplateId( projectTemplatesEntity );
			}
		}
		if( templateSaveReq.getTemplateType().equals("WORKFLOW") )
		{
			if( mode.equals("NEW") )
			{
				if( templateSaveReq.getStage1Name() != null && !templateSaveReq.getStage1Name().isEmpty() )
				{
					workFlowStagesEntity.setStage1Name( templateSaveReq.getStage1Name() );
				}
				if( templateSaveReq.getStage2Name() != null && !templateSaveReq.getStage2Name().isEmpty() )
				{
					workFlowStagesEntity.setStage2Name( templateSaveReq.getStage2Name() );
				}
				if( templateSaveReq.getStage3Name() != null && !templateSaveReq.getStage3Name().isEmpty() )
				{
					workFlowStagesEntity.setStage3Name( templateSaveReq.getStage3Name() );
				}
				if( templateSaveReq.getStage4Name() != null && !templateSaveReq.getStage4Name().isEmpty() )
				{
					workFlowStagesEntity.setStage4Name( templateSaveReq.getStage4Name() );
				}
				if( templateSaveReq.getStage5Name() != null && !templateSaveReq.getStage5Name().isEmpty() )
				{
					workFlowStagesEntity.setStage5Name( templateSaveReq.getStage5Name() );
				}
				if( templateSaveReq.getStage6Name() != null && !templateSaveReq.getStage6Name().isEmpty() )
				{
					workFlowStagesEntity.setStage6Name( templateSaveReq.getStage6Name() );
				}
				if( templateSaveReq.getStage1Field() != null && !templateSaveReq.getStage1Field().isEmpty() )
				{
					workFlowStagesEntity.setStage1Field( templateSaveReq.getStage1Field() );
					workFlowStagesEntity.setStage1Status( "APPROVED" );
				}
				workFlowStagesEntity.setStagesCount( templateSaveReq.getStagesCount() );
			}
			else
			{
				if( templateSaveReq.getStage2Field() != null && !templateSaveReq.getStage2Field().isEmpty() && workFlowStagesEntity.getStage2Field() == null )
				{
					System.out.println("stage2field");
					workFlowStagesEntity.setStage2Field( templateSaveReq.getStage2Field() );
					workFlowStagesEntity.setStage2Status( "APPROVED" );
				}
				if( templateSaveReq.getStage3Field() != null && !templateSaveReq.getStage3Field().isEmpty() && workFlowStagesEntity.getStage3Field() == null )
				{
					System.out.println("stage3field");
					workFlowStagesEntity.setStage3Field( templateSaveReq.getStage3Field() );
					workFlowStagesEntity.setStage3Status( "APPROVED" );
				}
				if( templateSaveReq.getStage4Field() != null && !templateSaveReq.getStage4Field().isEmpty() && workFlowStagesEntity.getStage3Field() == null )
				{
					System.out.println("stage4field");
					workFlowStagesEntity.setStage4Field( templateSaveReq.getStage4Field() );
					workFlowStagesEntity.setStage4Status( "APPROVED" );
				}
				if( templateSaveReq.getStage5Field() != null && !templateSaveReq.getStage5Field().isEmpty() && workFlowStagesEntity.getStage3Field() == null )
				{
					System.out.println("stage5field");
					workFlowStagesEntity.setStage5Field( templateSaveReq.getStage5Field() );
					workFlowStagesEntity.setStage5Status( "APPROVED" );
				}
				if( templateSaveReq.getStage6Field() != null && !templateSaveReq.getStage6Field().isEmpty() && workFlowStagesEntity.getStage3Field() == null )
				{
					System.out.println("stage6field");
					workFlowStagesEntity.setStage6Field( templateSaveReq.getStage6Field() );
					workFlowStagesEntity.setStage6Status( "APPROVED" );
				}
			}
		}
		return workFlowStagesEntity;
	}*/
	
	public static WorkflowTO convertWorkFlowEntityToPOJO( WorkFlowStagesEntity workFlowStagesEntity , String categoryMstrName )
	{
		WorkflowTO workflowTO = new WorkflowTO();
		System.out.println("This is from convertWorkFlowEntityToPOJO function");
		System.out.println(workFlowStagesEntity);
		//CategoriesMstrEntity categoriesMstrEntity = workFlowStagesEntity.getCategoryMstrId();
		//TemplateCategoriesEntity templateCategoriesEntity = workFlowStagesEntity.getTemplateCategoryId();
		
		/*workflowTO.setTemplateCategoryId( templateCategoriesEntity.getCategoryId() );
		workflowTO.setCategoryMstrId( categoriesMstrEntity.getCategoryMstrId() );
		if( categoryMstrName.equals("Sample Templates") )
		{
			SampleTemplatesEntity sampleTemplatesEntity = workFlowStagesEntity.getSampleTemplateId();
			workflowTO.setSampleTemplateId( sampleTemplatesEntity.getTemplateId() );
		}
		else if( categoryMstrName.equals("Central Templates") )
		{
			CentralTemplatesEntity centralTemplatesEntity = workFlowStagesEntity.getCentralTemplateId();
			workflowTO.setCentralTemplateId(  centralTemplatesEntity.getTemplateId() );
		}
		else
		{
			ProjectTemplatesEntity projectTemplatesEntity = workFlowStagesEntity.getProjectTemplateId();
			workflowTO.setProjectTemplateId(  projectTemplatesEntity.getTemplateId() );
		}*/
		workflowTO.setWorkflowId( workFlowStagesEntity.getWorkflowId() );
		workflowTO.setStage1Name( workFlowStagesEntity.getStage1Name() );
		workflowTO.setStage1Status( workFlowStagesEntity.getStage1Status() );
		if( workFlowStagesEntity.getStage1ApproverId() != null )
		{
			workflowTO.setStage1ApproverId( workFlowStagesEntity.getStage1ApproverId().getUserId() );
		}		
		
		workflowTO.setStage2Name( workFlowStagesEntity.getStage2Name() );		
		workflowTO.setStage2Status( workFlowStagesEntity.getStage2Status() );
		if( workFlowStagesEntity.getStage2ApproverId() != null )
		{
			workflowTO.setStage2ApproverId( workFlowStagesEntity.getStage2ApproverId().getUserId() );
		}
		
		workflowTO.setStage3Name( workFlowStagesEntity.getStage3Name() );
		workflowTO.setStage3Status( workFlowStagesEntity.getStage3Status() );
		if( workFlowStagesEntity.getStage3ApproverId() != null )
		{
			workflowTO.setStage3ApproverId( workFlowStagesEntity.getStage3ApproverId().getUserId() );
		}
		
		workflowTO.setStage4Name( workFlowStagesEntity.getStage4Name() );
		workflowTO.setStage4Status( workFlowStagesEntity.getStage4Status() );
		if( workFlowStagesEntity.getStage4ApproverId() != null )
		{
			workflowTO.setStage4ApproverId( workFlowStagesEntity.getStage4ApproverId().getUserId() );
		}
		
		workflowTO.setStage5Name( workFlowStagesEntity.getStage5Name() );
		workflowTO.setStage5Status( workFlowStagesEntity.getStage5Status() );
		if( workFlowStagesEntity.getStage5ApproverId() != null )
		{
			workflowTO.setStage5ApproverId( workFlowStagesEntity.getStage5ApproverId().getUserId() );
		}
		
		workflowTO.setStage6Name( workFlowStagesEntity.getStage6Name() );
		workflowTO.setStage6Status( workFlowStagesEntity.getStage6Status() );
		
		
		workflowTO.setStagesCount( workFlowStagesEntity.getStagesCount() );
		workflowTO.setWorkflowStatus( workFlowStagesEntity.getStatus() );
		System.out.println(workflowTO);
		return workflowTO;
	}
	
	public static ProjectTemplatesProposalTO convertProjectTemplatesProposalEntityToPOJO( ProjectTemplatesProposalEntity projectTemplatesProposalEntity )
	{
		ProjectTemplatesProposalTO projectTemplatesProposalTO = new ProjectTemplatesProposalTO();
		
		ProjMstrEntity projMstrEntity = projectTemplatesProposalEntity.getFromProjectId();
		UserMstrEntity proposerUserMstrEntity = projectTemplatesProposalEntity.getProposerUserId();
		UserMstrEntity reviewerUserMstrEntity = projectTemplatesProposalEntity.getReviewerUserId();
		ClientRegEntity clientRegEntity = projectTemplatesProposalEntity.getCrmId();
		ProjectTemplatesEntity projectTemplatesEntity = projectTemplatesProposalEntity.getProjectTemplateId();
		
		projectTemplatesProposalTO.setProjectTemplateId( projectTemplatesEntity.getTemplateId() );
		projectTemplatesProposalTO.setProposerUserId( proposerUserMstrEntity.getUserId() );
		projectTemplatesProposalTO.setProposerUserDisplayName( proposerUserMstrEntity.getDisplayName() );
		projectTemplatesProposalTO.setReviewerUserId( reviewerUserMstrEntity.getUserId() );
		projectTemplatesProposalTO.setProposalDate( projectTemplatesProposalEntity.getProposalDate() );
		projectTemplatesProposalTO.setFromProjectId( projMstrEntity.getProjectId() );
		projectTemplatesProposalTO.setCrmId( clientRegEntity.getClientId() );
		projectTemplatesProposalTO.setReviewerComments( projectTemplatesProposalEntity.getReviewerComments() );
		projectTemplatesProposalTO.setAllowViewEdit( projectTemplatesProposalEntity.getAllowViewEdit() );
		projectTemplatesProposalTO.setAllowIntoCentralTemplates( projectTemplatesProposalEntity.getAllowIntoCentralTemplate() );
		projectTemplatesProposalTO.setStatus( projectTemplatesProposalEntity.getStatus() );
		projectTemplatesProposalTO.setProjectName( projMstrEntity.getProjName() );
		projectTemplatesProposalTO.setProposalId( projectTemplatesProposalEntity.getProposalId() );
		projectTemplatesProposalTO.setTemplateName( projectTemplatesEntity.getTemplateName() );
		projectTemplatesProposalTO.setTemplCategoryName( projectTemplatesEntity.getTemplateCategoryId().getCategoryName() );
		projectTemplatesProposalTO.setTemplateVersion( projectTemplatesEntity.getVersionStatus() );
		
		return projectTemplatesProposalTO;
	}
}
