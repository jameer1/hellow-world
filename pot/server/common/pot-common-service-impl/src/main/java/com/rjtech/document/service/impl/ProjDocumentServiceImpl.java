package com.rjtech.document.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjtech.common.constants.AwsS3FileKeyConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.document.dto.ProjDocFolderPermissionTO;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFolderPermissionEntity;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.document.repository.ProjDocFolderPermissionRepository;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocumentFolderPermissions;
import com.rjtech.document.req.PermissionReq;
import com.rjtech.document.req.ProjDocDeleteReq;
import com.rjtech.document.req.ProjDocFileSaveReq;
import com.rjtech.document.req.ProjDocFolderDeactiveReq;
import com.rjtech.document.req.ProjDocFolderPermissionSaveReq;
import com.rjtech.document.req.ProjDocFolderSaveReq;
import com.rjtech.document.req.ProjDocGetReq;
import com.rjtech.document.resp.ProjDocFileResp;
import com.rjtech.document.resp.ProjDocFolderPermissionResp;
import com.rjtech.document.resp.ProjDocFolderResp;
import com.rjtech.document.service.ProjDocumentService;
import com.rjtech.document.service.handler.ProjDocFileHandler;
import com.rjtech.document.service.handler.ProjDocFolderHandler;
import com.rjtech.document.service.handler.ProjDocFolderPermissionHandler;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

/* template imports */
import com.rjtech.document.repository.TemplateCategoriesRepository;
import com.rjtech.document.repository.SampleTemplatesRepository;
import com.rjtech.document.repository.CentralTemplatesRepository;
import com.rjtech.document.repository.ProjectTemplatesRepository;
import com.rjtech.document.repository.ProjectFormsRepository;
import com.rjtech.document.repository.CategoriesMstrRepository;
import com.rjtech.document.repository.WorkFlowStagesRepository;
import com.rjtech.document.repository.ProjectTemplatesProposalRepository;

import com.rjtech.document.model.TemplateCategoriesEntity;
import com.rjtech.document.model.SampleTemplatesEntity;
import com.rjtech.document.model.CentralTemplatesEntity;
import com.rjtech.document.model.ProjectTemplatesEntity;
import com.rjtech.document.model.ProjectFormsEntity;
import com.rjtech.document.model.CategoriesMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.document.model.WorkFlowStagesEntity;
import com.rjtech.document.model.ProjectTemplatesProposalEntity;

import com.rjtech.document.req.TemplateCategoriesReq;
import com.rjtech.document.req.TemplateSaveReq;
import com.rjtech.document.req.TemplateCategorySaveReq;
import com.rjtech.document.req.TemplatesGetReq;
import com.rjtech.document.req.ProjectFormsGetReq;
import com.rjtech.document.req.TransferTemplatesGetReq;
import com.rjtech.document.req.TemplateProposalReq;

import com.rjtech.document.resp.TemplateCategoriesResp;
import com.rjtech.document.resp.TemplatesResp;
import com.rjtech.document.resp.ProjectFormsResp;
import com.rjtech.document.resp.TemplatesProposalResp;
import com.rjtech.document.service.handler.ProjDocTemplatesHandler;
import com.rjtech.document.dto.TemplatesTO;
import com.rjtech.document.dto.ProjectTemplatesProposalTO;
import org.springframework.core.io.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import com.rjtech.constants.ApplicationConstants;
import java.io.File;
import com.rjtech.common.utils.UploadUtil;

@Service(value = "projDocumentService")
@Transactional
public class ProjDocumentServiceImpl implements ProjDocumentService {
	private static final Logger log = LoggerFactory.getLogger(ProjDocumentServiceImpl.class);
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;

    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    @Autowired
    private ProjDocFolderPermissionRepository projDocFolderPermissionRepository;

    @Autowired
    private ProjDocumentFolderPermissions projDocumentFolderPermissions;

    @Autowired
    private LoginRepository loginRepo;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private ProjDocFileHandler projDocFileHandler;
    
    @Autowired
    private TemplateCategoriesRepository templateCategoriesRepository;
    
    @Autowired
    private SampleTemplatesRepository sampleTemplatesRepository;   
    
    @Autowired
    private CentralTemplatesRepository centralTemplatesRepository;
    
    @Autowired
    private ProjectTemplatesRepository projectTemplatesRepository;
    
    @Autowired
    private ProjectFormsRepository projectFormsRepository;
    
    @Autowired
    private CategoriesMstrRepository categoriesMstrRepository;
    
    @Autowired
    private WorkFlowStagesRepository workFlowStagesRepository;
    
    @Autowired
    private ProjectTemplatesProposalRepository projectTemplatesProposalRepository;
            
    public ProjDocFolderPermissionResp getProjDocFolderPermissions(ProjDocGetReq projDocGetReq) {
        ProjDocFolderPermissionResp projDocFolderPermissionResp = new ProjDocFolderPermissionResp();
        Map<Long, ProjDocFolderPermissionTO> projUserClassMap = new HashMap<Long, ProjDocFolderPermissionTO>();
        List<ProjDocFolderPermissionEntity> projDocFolderPermissionEntities = projDocFolderPermissionRepository
                .findProjDocFolderPermissions(projDocGetReq.getId(), projDocGetReq.getStatus());

        for (ProjDocFolderPermissionEntity projDocFolderPermissionEntity : projDocFolderPermissionEntities) {
            projUserClassMap.put(projDocFolderPermissionEntity.getUserId().getUserId(),
                    ProjDocFolderPermissionHandler.convertEntityToPOJO(projDocFolderPermissionEntity));
        }
        projDocFolderPermissionResp.setProjUserClassMap(projUserClassMap);
        return projDocFolderPermissionResp;
    }

    public ProjDocFolderPermissionResp getFolderPermissions(PermissionReq permissionReq) {
        ProjDocFolderPermissionResp response = new ProjDocFolderPermissionResp();
     //   List<LabelKeyTO> resp = projDocumentFolderPermissions.getFolderPermissions(AppUserUtils.getUserId());
        List<ProjDocFolderPermissionEntity> permissions = projDocFolderPermissionRepository
                .findProjDocFolderPermissionsOfUser(AppUserUtils.getUserId());
        List<ProjDocFolderPermissionTO> permissionsPojo = new ArrayList();
        for (ProjDocFolderPermissionEntity dbPer : permissions) {
            permissionsPojo.add(ProjDocFolderPermissionHandler.convertEntityToPOJO(dbPer));
        }
        response.setProjDocFolderPermissionTOs(permissionsPojo);
      //  response.setLabelKeyTOsmap(resp);
        return response;
    }

    public ProjDocFolderResp getProjDoc(ProjDocGetReq projDocGetReq) {
    	
    	ProjDocFolderResp projDocFolderResp = new ProjDocFolderResp();
        List<ProjDocFolderEntity> projDocFolderEntities = projDocFolderRepository
                .findProjDocFolders(projDocGetReq.getStatus());  
            
        for (ProjDocFolderEntity projDocFolderEntity : projDocFolderEntities) {
            projDocFolderResp.getProjDocFolderTOs().add(ProjDocFolderHandler.convertEntityToPOJO(projDocFolderEntity,
                    true, projDocGetReq.getProjId(), projDocFolderRepository));
        }
       
        return projDocFolderResp;
    }
    
    public ProjDocFolderResp getProjDocFolders(ProjDocGetReq projDocGetReq) {
    	    	
        ProjDocFolderResp projDocFolderResp = new ProjDocFolderResp();
        List<ProjDocFolderEntity> projDocFolderEntities = projDocFolderRepository
                .findProjDocFolders(projDocGetReq.getStatus());
                
        for (ProjDocFolderEntity projDocFolderEntity : projDocFolderEntities) {
            projDocFolderResp.getProjDocFolderTOs().add(ProjDocFolderHandler.convertEntityToPOJOdocument(projDocFolderEntity,
                    true, projDocGetReq.getProjId(), projDocFolderRepository,projDocFolderPermissionRepository,AppUserUtils.getUserId(),projDocGetReq.getStatus()));
        }
        
        return projDocFolderResp;
    }

    public ProjDocFileResp getProjDocFilesByFolder(ProjDocGetReq projDocGetReq) throws IOException {
        ProjDocFileResp projDocFileResp = new ProjDocFileResp();
       List<ProjDocFileEntity> projDocFileEntities = projDocFileRepository.findProjDocFiles(projDocGetReq.getId(),
                projDocGetReq.getStatus());
        for (ProjDocFileEntity projDocFileEntity : projDocFileEntities) {
            projDocFileResp.getProjDocFileTOs().add(projDocFileHandler.convertEntityToPOJO(projDocFileEntity, false));
        }
        return projDocFileResp;
    }

    public void saveProjDocFolders(ProjDocFolderSaveReq projDocFolderSaveReq) {
        projDocFolderRepository.save(ProjDocFolderHandler.populateEntitiesFromPOJO(
                projDocFolderSaveReq.getProjDocFolderTOs(), projDocFolderRepository, epsProjRepository));
    }

    /*public void saveProjDocFilesByFolder(MultipartFile[] files, ProjDocFileSaveReq projDocFileSaveReq)
            throws IOException {
        for (ProjDocFileTO projDocFileTO : projDocFileSaveReq.getProjDocFileTOs()) {
            // We are passing multiple files as an array, to find which file is belongs to
            // which object based on fileName
            for (MultipartFile file : files) {
                if (file.getOriginalFilename().equalsIgnoreCase(projDocFileTO.getName())) {
                    projDocFileTO.setFileContent(file.getBytes());
                    break;
                }
            }
            projDocFileTO.setFolderId(projDocFileSaveReq.getFolderId());
            ProjDocFileEntity savedEntity = projDocFileRepository
                    .save(projDocFileHandler.convertPOJOToEntity(projDocFileTO));
            projDocFileHandler.checkAndUpdateFileTOAwsS3(savedEntity, projDocFileTO.getMultipartFile(),
                    AwsS3FileKeyConstants.PROJ_DOCUMENT);

        }
    }*/
    
    public void saveProjDocFilesByFolder(MultipartFile[] files, ProjDocFileSaveReq projDocFileSaveReq)
            throws IOException {
    	String folder_category = projDocFileSaveReq.getFolderCategory();
    	UploadUtil uploadUtil = null;
        for (ProjDocFileTO projDocFileTO : projDocFileSaveReq.getProjDocFileTOs()) {
            // We are passing multiple files as an array, to find which file is belongs to which object based on fileName
        	Integer fileIndex = projDocFileTO.getFileObjectIndex();
            System.out.println("Index"+fileIndex);
            uploadUtil = new UploadUtil();
          //  String upload_folder = "Other Documents";
        	projDocFileTO.setFolderPath( "/" + String.valueOf( projDocFileSaveReq.getProjId() ) + "/" + String.valueOf( projDocFileSaveReq.getCreatedBy() ) + "/" + String.valueOf( projDocFileSaveReq.getFolderId() ) );
        //	String extras[] = { String.valueOf( projDocFileSaveReq.getProjId() ), String.valueOf( projDocFileSaveReq.getCreatedBy() ), String.valueOf( projDocFileSaveReq.getFolderId() ) };
			if( fileIndex != null && files[fileIndex].getOriginalFilename().equalsIgnoreCase( projDocFileTO.getName() ) ) {
				//System.out.println("File Name:"+preContractDocsTO.getName()+" projd id:"+preContractDocsTO.getProjId());
				System.out.println("If condition of fileIndex");
				ProjDocFolderEntity folder = projDocFolderRepository.findOne( projDocFileSaveReq.getFolderId() );
				projDocFileTO.setMultipartFile( files[fileIndex] );
				String upload_folder = "Other Documents";
				if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
            	{
					System.out.println("If condition of upload file");
					System.out.println(folder);
					System.out.println(files[fileIndex].getOriginalFilename()+"->"+files[fileIndex].getContentType()+"->"+files[fileIndex].getSize());
    	        	System.out.println( "/" + String.valueOf( projDocFileSaveReq.getProjId() ) + "/" + String.valueOf( projDocFileSaveReq.getCreatedBy() ) + "/" + String.valueOf( projDocFileSaveReq.getFolderId() ) );
    	        	projDocFileTO.setFolderPath( "/" + String.valueOf( projDocFileSaveReq.getProjId() ) + "/" + String.valueOf( projDocFileSaveReq.getCreatedBy() ) + "/" + String.valueOf( projDocFileSaveReq.getFolderId() ) );
    	        	String extras[] = { String.valueOf( projDocFileSaveReq.getProjId() ), String.valueOf( projDocFileSaveReq.getCreatedBy() ), String.valueOf( projDocFileSaveReq.getFolderId() ) };
    	        	if( folder.getUploadFolder() != null)
    	        	{
    	        		upload_folder = folder.getUploadFolder();
    	        	}
    	        	System.out.println("upload_folder:"+upload_folder);
    				uploadUtil.uploadFile( files[fileIndex], folder.getName(), upload_folder, extras );
            	}								
			}
			projDocFileTO.setProjId( projDocFileSaveReq.getProjId() );
			projDocFileTO.setFromSource("Other Documents");
            projDocFileTO.setFolderId( projDocFileSaveReq.getFolderId() );
            projDocFileTO.setStatus( 1 );
            ProjDocFileEntity savedEntity = projDocFileRepository.save(projDocFileHandler.convertPOJOToEntity(projDocFileTO));  
            if( ApplicationConstants.UPLOAD_FILE_TO.equals("AWS") )
			{
				projDocFileHandler.checkAndUpdateFileTOAwsS3(savedEntity, projDocFileTO.getMultipartFile(),
	                    AwsS3FileKeyConstants.PROJ_DOCUMENT);
			}
        }
    }

    public void saveProjDocFolderPermissions(ProjDocFolderPermissionSaveReq projDocFolderPermissionSaveReq) {
        ProjDocFolderEntity docFolderEntity = projDocFolderRepository
                .findOne(projDocFolderPermissionSaveReq.getFolderId());
        List<ProjDocFolderPermissionEntity> folderPerms = docFolderEntity.getFolderPermissions();
        for (ProjDocFolderPermissionTO projDocFolderPermissionTO : projDocFolderPermissionSaveReq
                .getProjDocFolderPermissionTOs()) {
            ProjDocFolderPermissionEntity existing = null;
            for (ProjDocFolderPermissionEntity folderPerm : folderPerms) {            	
                if (folderPerm.getUserId().getUserId().longValue() == projDocFolderPermissionTO.getUserId().longValue()
                        && folderPerm.getFolder().getId().longValue() == projDocFolderPermissionTO.getFolderId().longValue() && folderPerm.getProjectEntity().getProjectId().longValue() == projDocFolderPermissionTO.getProjId().longValue() && folderPerm.getProjectEntity().getProjectId() != null ) {
                    existing = folderPerm;
                    break;
                }            	
            }
            if (existing == null) {
                existing = new ProjDocFolderPermissionEntity();
                folderPerms.add(existing);
            }
            ProjDocFolderPermissionHandler.convertPOJOToEntity(projDocFolderPermissionTO, projDocFolderRepository,
                    loginRepo, existing);
        }
    }

    public ProjDocFileTO getProjDocFile(Long fileId) throws IOException {
        return projDocFileHandler.convertEntityToPOJO(projDocFileRepository.findOne(fileId), true);
    }

    public void projDocDeleteFile(ProjDocDeleteReq projDocDeleteReq) {
        for (Long fileId : projDocDeleteReq.getFileIds()) {
            projDocFileRepository.delete(fileId);
        }
    }

    public void projDocDeleteFolder(ProjDocFolderDeactiveReq projDocFolderDeactiveReq) {
        projDocFolderRepository.deactivateProjDocFolders(projDocFolderDeactiveReq.getPojDocFolderIds(),
                StatusCodes.DEACIVATE.getValue());
    }
    
    // This function to retrieve the template categories
    public TemplateCategoriesResp getTemplateCategories( TemplateCategoriesReq templateCategoriesReq )
    {
    	TemplateCategoriesResp templateCategoriesResp = new TemplateCategoriesResp();
        List<TemplateCategoriesEntity> templateCategoriesEntities = templateCategoriesRepository.findByCategoryMstrId( templateCategoriesReq.getCategoryMstrId() );
        System.out.println("This is from getTemplateCategories from ProjDocumentServiceImpl class");
        for( TemplateCategoriesEntity templateCategoriesEntity : templateCategoriesEntities ) {
        	System.out.println(templateCategoriesEntity);
        	templateCategoriesResp.getTemplateCategoriesTOs().add( ProjDocTemplatesHandler.convertTemplateCategoriesEntityToPOJO( templateCategoriesEntity, templateCategoriesRepository ) );
        }
       
        return templateCategoriesResp;
    }
    
    // This is function to save the template category
    public TemplateCategoriesResp saveTemplateCategory( TemplateCategorySaveReq templateCategorySaveReq )
    {
    	System.out.println("from ProjDocumentServiceImpl class : TemplateCategorySaveReq:");
    	System.out.println( templateCategorySaveReq );
    	TemplateCategoriesResp templateCategoriesResp = new TemplateCategoriesResp();
		UserMstrEntity userMstrEntity = new UserMstrEntity();
    	CategoriesMstrEntity categoriesMstrEntity = categoriesMstrRepository.findByCategoryMstrId( templateCategorySaveReq.getCategoryMstrId() );    	
    	String categoryMstrName = categoriesMstrEntity.getTemplateCategoryName();    	
    	System.out.println(categoryMstrName);
    	TemplateCategoriesEntity templateCategoriesEntity = new TemplateCategoriesEntity();
    	TemplateCategoriesEntity cTemplateCategoriesEntity = new TemplateCategoriesEntity();
    	
    	if( categoryMstrName.equals("Sample Templates") )
    	{
    		System.out.println("if condition");
    		templateCategoriesEntity = templateCategoriesRepository.save( ProjDocTemplatesHandler.convertPOJOToTemplateCategoriesEntity( templateCategorySaveReq , userMstrEntity ) ); 
    		templateCategoriesResp.getTemplateCategoriesTOs().add( ProjDocTemplatesHandler.convertTemplateCategoriesEntityToPOJO( templateCategoriesEntity, templateCategoriesRepository ) );
    	}
    	else if( categoryMstrName.equals("Central Templates") )
    	{
    		System.out.println("else if condition");
    		cTemplateCategoriesEntity = templateCategoriesRepository.save( ProjDocTemplatesHandler.convertPOJOToTemplateCategoriesEntity( templateCategorySaveReq , userMstrEntity ) );
    		
    		int category_mstr_id = 3;
    		Integer intobj = category_mstr_id;
    		TemplateCategorySaveReq templatePCategorySaveReq = new TemplateCategorySaveReq();
    		templatePCategorySaveReq.setCategoryName( templateCategorySaveReq.getCategoryName() );
    		templatePCategorySaveReq.setColorCode( templateCategorySaveReq.getColorCode() );
    		templatePCategorySaveReq.setCategoryMstrId(intobj);
    		templatePCategorySaveReq.setCreatedBy( templateCategorySaveReq.getCreatedBy() );
    		templatePCategorySaveReq.setCrmId( templateCategorySaveReq.getCrmId() );
    		templateCategoriesEntity = templateCategoriesRepository.save( ProjDocTemplatesHandler.convertPOJOToTemplateCategoriesEntity( templatePCategorySaveReq , userMstrEntity ) );
    		templateCategoriesResp.getTemplateCategoriesTOs().add( ProjDocTemplatesHandler.convertTemplateCategoriesEntityToPOJO( cTemplateCategoriesEntity, templateCategoriesRepository ) );
    	}
    	System.out.print("TemplateCategoriesEntity after saving:");
    	System.out.println(templateCategoriesEntity);
    	
    	return templateCategoriesResp;
    }    
    
    // This is function to update the template category
    public void updateTemplateCategory( TemplateCategoriesReq templateCategoriesReq )
    {
    	System.out.println("from ProjDocumentServiceImpl class : updateTemplateCategory: TemplateCategoriesReq");
    	System.out.println( templateCategoriesReq );
		UserMstrEntity userMstrEntity = new UserMstrEntity();
		TemplateCategoriesResp templateCategoriesResp = new TemplateCategoriesResp();
		TemplateCategoriesEntity templateCategoriesEntity = new TemplateCategoriesEntity();
		CategoriesMstrEntity categoriesMstrEntity = categoriesMstrRepository.findByCategoryMstrId( templateCategoriesReq.getCategoryMstrId() );    	
    	String categoryMstrName = categoriesMstrEntity.getTemplateCategoryName();    	
    	System.out.println(categoryMstrName);
    	
    	if( categoryMstrName.equals("Sample Templates") )
    	{
    		System.out.print("This is the result after updating the template category for sample templates");
    		//System.out.println(templateCategoriesRepository.updateTemplateCategory( templateCategoriesReq.getCategoryId() , templateCategoriesReq.getCategoryName() ));
    		templateCategoriesRepository.updateTemplateCategory( templateCategoriesReq.getCategoryId() , templateCategoriesReq.getCategoryName() );
    		//templateCategoriesResp.getTemplateCategoriesTOs().add( ProjDocTemplatesHandler.convertTemplateCategoriesEntityToPOJO( templateCategoriesEntity, templateCategoriesRepository ) );
    	}
    	else if( categoryMstrName.equals("Central Templates") )
    	{
    		TemplateCategoriesReq templatePCategoriesReq = new TemplateCategoriesReq();
    		
    		templateCategoriesEntity = templateCategoriesRepository.findCategoriesByCategoryId( templateCategoriesReq.getCategoryId() );
    		
    		String ccategory_name = templateCategoriesEntity.getCategoryName();
    		List<TemplateCategoriesEntity> templatePCategoriesEntities = templateCategoriesRepository.findPCategoriesByCategoryName( ccategory_name );
    		Long pcategory_id = 0L;
    		boolean is_project_templates = false;
    		for( TemplateCategoriesEntity templatePCategoriesEntity : templatePCategoriesEntities ) {
            	System.out.println(templatePCategoriesEntity);
            	CategoriesMstrEntity categoriesMstrEntity1 = templatePCategoriesEntity.getCategoryMstrId();
            	String categoryMasterName = categoriesMstrEntity1.getTemplateCategoryName();
            	if( categoryMasterName.equals("Project Templates") )
            	{
            		System.out.println("This is from if condition of the for loop");
            		is_project_templates = true;
            		pcategory_id = templatePCategoriesEntity.getCategoryId();
            		System.out.println("pcategory_id:"+pcategory_id);
            	}
            	//templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertCentralTemplatesEntityToPOJO( centralTemplatesEntity , centralTemplatesRepository ) );
            }
    		templateCategoriesRepository.updateTemplateCategory( templateCategoriesReq.getCategoryId() , templateCategoriesReq.getCategoryName() );
    		if( is_project_templates == true )
    		{
    			System.out.println("If the condition of project_templates");
    			templateCategoriesRepository.updateTemplateCategory( pcategory_id , templateCategoriesReq.getCategoryName() );
    		}
    		//templateCategoriesResp.getTemplateCategoriesTOs().add( ProjDocTemplatesHandler.convertTemplateCategoriesEntityToPOJO( templateCategoriesEntity, templateCategoriesRepository ) );
    	}
    	//return templateCategoriesResp;
    }
    
    public TemplatesResp getSampleTemplates( TemplatesGetReq templatesGetReq )
    {
    	TemplatesResp templatesResp = new TemplatesResp();
    	System.out.println(templatesGetReq);
    	List<SampleTemplatesEntity> sampleTemplatesEntities = null;
    	if( templatesGetReq.getTemplateStatus() != null )
    	{
    		System.out.println("if condition get template status");
    		sampleTemplatesEntities = sampleTemplatesRepository.findTemplatesByStatus( templatesGetReq.getCategoryMstrId(), templatesGetReq.getTemplateStatus() );
    	}
    	else
    	{
    		System.out.println("else condition get template status");
    		sampleTemplatesEntities = sampleTemplatesRepository.findTemplatesByCategory( templatesGetReq.getTemplateCategoryId() , templatesGetReq.getCategoryMstrId() );
    	}
        
        System.out.println("This is from getSampleTemplates from ProjDocumentServiceImpl class");
        for( SampleTemplatesEntity sampleTemplatesEntity : sampleTemplatesEntities ) {
        	System.out.println(sampleTemplatesEntity);
        	templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertSampleTemplatesEntityToPOJO( sampleTemplatesEntity , sampleTemplatesRepository ) );
        	if( sampleTemplatesEntity.getTemplateType().toUpperCase().equals("WORKFLOW") )
        	{
        		TemplateCategoriesEntity templateCategoriesEntity = sampleTemplatesEntity.getTemplateCategoryId();
        		CategoriesMstrEntity categoriesMstrEntity = sampleTemplatesEntity.getCategoryMstrId();
        		
        		//WorkFlowStagesEntity workFlowStagesEntity = workFlowStagesRepository.getStagesBySampleTemplId( sampleTemplatesEntity.getTemplateId() , templateCategoriesEntity.getCategoryId() , categoriesMstrEntity.getCategoryMstrId() );
        		WorkFlowStagesEntity workFlowStagesEntity = sampleTemplatesEntity.getWorkflowStagesId();
        		if( workFlowStagesEntity != null )
        		{
        			templatesResp.getWorkflowTOs().add( ProjDocTemplatesHandler.convertWorkFlowEntityToPOJO( workFlowStagesEntity , categoriesMstrEntity.getTemplateCategoryName() ) );
        		}        		
        	}
        }   
        return templatesResp;
    }
    
    public TemplatesResp getCentralTemplates( TemplatesGetReq templatesGetReq )
    {
    	TemplatesResp templatesResp = new TemplatesResp();
    	List<CentralTemplatesEntity> centralTemplatesEntities = null;
    	if( templatesGetReq.getTemplateStatus() != null )
    	{
    		System.out.println("if condition get template status");
    		centralTemplatesEntities = centralTemplatesRepository.findTemplatesByStatus( templatesGetReq.getCategoryMstrId(), templatesGetReq.getTemplateStatus() );
    	}
    	else
    	{
    		System.out.println("else condition get template status");
    		centralTemplatesEntities = centralTemplatesRepository.findTemplatesByCategory( templatesGetReq.getTemplateCategoryId(), templatesGetReq.getCrmId() );
    	}
        //centralTemplatesRepository.findTemplatesByCategory( templatesGetReq.getTemplateCategoryId() , templatesGetReq.getCrmId() );
        System.out.println("This is from getCentralTemplates from ProjDocumentServiceImpl class");
        System.out.println(templatesGetReq);
        for( CentralTemplatesEntity centralTemplatesEntity : centralTemplatesEntities ) {
        	System.out.println(centralTemplatesEntity);
        	templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertCentralTemplatesEntityToPOJO( centralTemplatesEntity , centralTemplatesRepository ) );
        	if( centralTemplatesEntity.getTemplateType().toUpperCase().equals("WORKFLOW") )
        	{
        		TemplateCategoriesEntity templateCategoriesEntity = centralTemplatesEntity.getTemplateCategoryId();
        		CategoriesMstrEntity categoriesMstrEntity = centralTemplatesEntity.getCategoryMstrId();
        		
        		//WorkFlowStagesEntity workFlowStagesEntity = workFlowStagesRepository.getStagesByCentralTemplId( centralTemplatesEntity.getTemplateId() , templateCategoriesEntity.getCategoryId() , categoriesMstrEntity.getCategoryMstrId() );
        		WorkFlowStagesEntity workFlowStagesEntity = centralTemplatesEntity.getWorkflowStagesId();
        		if( workFlowStagesEntity != null )
        		{
        			templatesResp.getWorkflowTOs().add( ProjDocTemplatesHandler.convertWorkFlowEntityToPOJO( workFlowStagesEntity , categoriesMstrEntity.getTemplateCategoryName() ) );
        		}
        		//templatesResp.getWorkflowTOs().add( ProjDocTemplatesHandler.convertWorkFlowEntityToPOJO( workFlowStagesEntity , categoriesMstrEntity.getTemplateCategoryName() ) );
        	}
        }       
        return templatesResp;
    }
    
    public TemplatesResp getProjectTemplates( TemplatesGetReq templatesGetReq )
    {
    	TemplatesResp templatesResp = new TemplatesResp();
        List<ProjectTemplatesEntity> projectTemplatesEntities = projectTemplatesRepository.findTemplatesByCategory( templatesGetReq.getTemplateCategoryId() );
        System.out.println("This is from getProjectTemplates from ProjDocumentServiceImpl class");
        for( ProjectTemplatesEntity projectTemplatesEntity : projectTemplatesEntities ) {
        	System.out.println(projectTemplatesEntity);
        	templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertProjectTemplatesEntityToPOJO( projectTemplatesEntity , projectTemplatesRepository ) );
        	if( projectTemplatesEntity.getTemplateType().toUpperCase().equals("WORKFLOW") )
        	{
        		TemplateCategoriesEntity templateCategoriesEntity = projectTemplatesEntity.getTemplateCategoryId();
        		CategoriesMstrEntity categoriesMstrEntity = projectTemplatesEntity.getCategoryMstrId();
        		
        		//WorkFlowStagesEntity workFlowStagesEntity = workFlowStagesRepository.getStagesByProjectTemplId( projectTemplatesEntity.getTemplateId() , templateCategoriesEntity.getCategoryId() , categoriesMstrEntity.getCategoryMstrId() );
        		WorkFlowStagesEntity workFlowStagesEntity = projectTemplatesEntity.getWorkflowStagesId();
        		if( workFlowStagesEntity != null )
        		{
        			templatesResp.getWorkflowTOs().add( ProjDocTemplatesHandler.convertWorkFlowEntityToPOJO( workFlowStagesEntity , categoriesMstrEntity.getTemplateCategoryName() ) );
        		}
        		//templatesResp.getWorkflowTOs().add( ProjDocTemplatesHandler.convertWorkFlowEntityToPOJO( workFlowStagesEntity , categoriesMstrEntity.getTemplateCategoryName() ) );
        	}
        }       
        return templatesResp;
    }
    
    public ProjectFormsResp getProjectForms( ProjectFormsGetReq projectFormsGetReq )
    {
    	ProjectFormsResp projectFormsResp = new ProjectFormsResp();
    	System.out.println(projectFormsGetReq);
        List<ProjectFormsEntity> projectFormsEntities = projectFormsRepository.findFormsByProject( projectFormsGetReq.getProjectId() , projectFormsGetReq.getTemplateCategoryId() , projectFormsGetReq.getFormType() , projectFormsGetReq.getProjectTemplateId() );
        System.out.println("This is from getProjectForms from ProjDocumentServiceImpl class");
        for( ProjectFormsEntity projectFormsEntity : projectFormsEntities ) {
        	System.out.println(projectFormsEntity);
        	projectFormsResp.getFormsTOs().add( ProjDocTemplatesHandler.convertProjectFormsEntityToPOJO( projectFormsEntity ) );
        }       
        return projectFormsResp;
    }
    
    public TemplatesResp saveNewTemplate( TemplateSaveReq templateSaveReq ) {
    	System.out.print("TempalteSaveReq data from saveNewTempale function:");
    	System.out.println(templateSaveReq);
    	UserMstrEntity userMstrEntity = new UserMstrEntity();
    	TemplatesResp templatesResp = new TemplatesResp();
    	SampleTemplatesEntity sampleTemplatesEntity = new SampleTemplatesEntity();
    	CentralTemplatesEntity centralTemplatesEntity = new CentralTemplatesEntity();
    	ProjectTemplatesEntity projectTemplatesEntity = new ProjectTemplatesEntity();    	
    	
    	System.out.println("This is from saveNewTemplate from ProjDocumentServiceImpl class");
    	CategoriesMstrEntity categoriesMstrEntity = categoriesMstrRepository.findByCategoryMstrId( templateSaveReq.getCategoryMstrId() );
    	System.out.println(categoriesMstrEntity.getTemplateCategoryName());
    	String categoryMstrName = categoriesMstrEntity.getTemplateCategoryName();
    	WorkFlowStagesEntity workFlowStagesEntity = new WorkFlowStagesEntity();
    	
    	/*if( templateSaveReq.getTemplateType().toUpperCase().equals("WORKFLOW") && templateSaveReq.getMode().toUpperCase().equals("CREATETEMPLATECOPY") )
		{
			workFlowStagesEntity = workFlowStagesRepository.save( ProjDocTemplatesHandler.convertPOJOToWorkFlowEntity( templateSaveReq , workFlowStagesEntity , categoryMstrName , "NEW" ) );
			templatesResp.getWorkflowTOs().add( ProjDocTemplatesHandler.convertWorkFlowEntityToPOJO( workFlowStagesEntity , categoryMstrName ) );
		}*/
    	
    	if( categoryMstrName.equals("Sample Templates") )
    	{
    		System.out.println("Sample Templates condition");
    		sampleTemplatesEntity = sampleTemplatesRepository.save( ProjDocTemplatesHandler.convertPOJOToTemplatesEntity( templateSaveReq, userMstrEntity, categoriesMstrEntity.getTemplateCategoryName(), workFlowStagesRepository ) );
    		System.out.println(sampleTemplatesEntity);
    		templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertSampleTemplatesEntityToPOJO( sampleTemplatesEntity , sampleTemplatesRepository ) );
    		templateSaveReq.setTemplateId( sampleTemplatesEntity.getTemplateId() );
    	}
    	else if( categoryMstrName.equals("Central Templates") )
    	{
    		System.out.println("Central Templates condition");
    		centralTemplatesEntity = centralTemplatesRepository.save( ProjDocTemplatesHandler.convertPOJOToCTemplatesEntity( templateSaveReq , userMstrEntity, categoriesMstrEntity.getTemplateCategoryName(), workFlowStagesRepository ) );
    		System.out.println(centralTemplatesEntity);
    		templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertCentralTemplatesEntityToPOJO( centralTemplatesEntity , centralTemplatesRepository ) );
    		templateSaveReq.setTemplateId( centralTemplatesEntity.getTemplateId() );
    	}
    	else if( categoryMstrName.equals("Project Templates") )
    	{
    		System.out.println("Project Templates condition");    		
    		projectTemplatesEntity = projectTemplatesRepository.save( ProjDocTemplatesHandler.convertPOJOToPTemplatesEntity( templateSaveReq , userMstrEntity, categoriesMstrEntity.getTemplateCategoryName(), workFlowStagesRepository ) );
    		System.out.println(projectTemplatesEntity);
    		templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertProjectTemplatesEntityToPOJO( projectTemplatesEntity , projectTemplatesRepository ) );
    		templateSaveReq.setTemplateId( projectTemplatesEntity.getTemplateId() );
    	}    	
    	
    	return templatesResp;
    }
    
    public void updateTemplate( TemplateSaveReq templateSaveReq ) {
    	UserMstrEntity userMstrEntity = new UserMstrEntity();
    	CategoriesMstrEntity categoriesMstrEntity = categoriesMstrRepository.findByCategoryMstrId( templateSaveReq.getCategoryMstrId() );
    	String categoryMstrName = categoriesMstrEntity.getTemplateCategoryName();
    	System.out.println("This is from updateTemplate function of ProjDocumentServiceImpl class:"+categoryMstrName);
    	System.out.println(templateSaveReq);
    	WorkFlowStagesEntity workFlowStagesEntity = new WorkFlowStagesEntity();
    	String template_status = "";
    	if( templateSaveReq.getTemplateType().toUpperCase().equals("WORKFLOW") )
		{
			workFlowStagesEntity = workFlowStagesRepository.save( ProjDocTemplatesHandler.convertPOJOToWorkFlowEntity( templateSaveReq , workFlowStagesEntity , categoryMstrName , "NEW" ) );			
		}
    	
    	if( categoryMstrName.equals("Sample Templates") )
    	{
    		sampleTemplatesRepository.updateTemplateJsonById( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateJson() );
    	}
    	else if( categoryMstrName.equals("Central Templates") )
    	{
    		System.out.println("else if condition of Central Templates:"+categoryMstrName);
    		centralTemplatesRepository.updateTemplateJsonById( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateJson() );
    		//templateCategoriesResp.getTemplateCategoriesTOs().add( ProjDocTemplatesHandler.convertTemplateCategoriesEntityToPOJO( templateCategoriesEntity, templateCategoriesRepository ) );
    	}
    	else if( categoryMstrName.equals("Project Templates") )
    	{
    		System.out.println("else if condition of Project Templates:"+categoryMstrName);
    		projectTemplatesRepository.updateTemplateJsonById( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateJson() );
    		//templateCategoriesResp.getTemplateCategoriesTOs().add( ProjDocTemplatesHandler.convertTemplateCategoriesEntityToPOJO( templateCategoriesEntity, templateCategoriesRepository ) );
    	}
    } 
    
    public TemplatesResp cloneTemplate( TemplateSaveReq templateSaveReq ) {
    	TemplatesResp templatesResp = new TemplatesResp();
    	System.out.println(templateSaveReq);
    	
    	CategoriesMstrEntity categoriesMstrEntity = categoriesMstrRepository.findByCategoryMstrId( templateSaveReq.getCategoryMstrId() );
    	String categoryMstrName = categoriesMstrEntity.getTemplateCategoryName();
    	WorkFlowStagesEntity workFlowStagesEntity = new WorkFlowStagesEntity();
    	
    	System.out.println("This is from cloneTemplate of ProjDocumentServiceImpl class");    
    	System.out.println(categoryMstrName);
    	String templateType = "";
    	String templateStatus = "";
    	
    	/*if( templateStatus.equals("DRAFT") && templateType.equals("WORKFLOW") )
    	{
    		if( categoryMstrName.equals("Sample Templates") )
    		{
    			//workFlowStagesEntity = workFlowStagesRepository.getStagesBySampleTemplId( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateCategoryId() , templateSaveReq.getCategoryMstrId() );
    			workFlowStagesEntity = 
    		}
    		else if( categoryMstrName.equals("Central Templates") )
    		{
    			//workFlowStagesEntity = workFlowStagesRepository.getStagesByCentralTemplId( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateCategoryId() , templateSaveReq.getCategoryMstrId() );
    		}
    		else if( categoryMstrName.equals("Project Templates") )
    		{
    			//workFlowStagesEntity = workFlowStagesRepository.getStagesByProjectTemplId( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateCategoryId() , templateSaveReq.getCategoryMstrId() );
    		}    		
    		//workFlowStagesRepository.getStagesByCentralTemplId( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateCategoryId() , templateSaveReq.getCategoryMstrId() );
    		if( workFlowStagesEntity != null )
    		{
    			System.out.println("workflowstages entity inside the else condition of the workflow template type");    				   				
    			WorkFlowStagesEntity resWorkFlowStagesEntity = workFlowStagesRepository.save( ProjDocTemplatesHandler.convertPOJOToWorkFlowEntity( templateSaveReq , workFlowStagesEntity , categoryMstrName , "EDIT" ) );
    			System.out.println(resWorkFlowStagesEntity); 
    			int stages_count = 0;
    			if( resWorkFlowStagesEntity.getStage1Field() != null && resWorkFlowStagesEntity.getStage1Status() != null ) {
    				stages_count = stages_count+1;
    			}
    			if( resWorkFlowStagesEntity.getStage2Field() != null && resWorkFlowStagesEntity.getStage2Status() != null ) {
    				stages_count = stages_count+1;
    			}
    			if( resWorkFlowStagesEntity.getStage3Field() != null && resWorkFlowStagesEntity.getStage3Status() != null ) {
    				stages_count = stages_count+1;
    			}
    			if( resWorkFlowStagesEntity.getStage4Field() != null && resWorkFlowStagesEntity.getStage4Status() != null ) {
    				stages_count = stages_count+1;
    			}
    			if( resWorkFlowStagesEntity.getStage5Field() != null && resWorkFlowStagesEntity.getStage5Status() != null ) {
    				stages_count = stages_count+1;
    			}
    			if( resWorkFlowStagesEntity.getStage6Field() != null && resWorkFlowStagesEntity.getStage6Status() != null ) {
    				stages_count = stages_count+1;
    			}
    			if( stages_count == workFlowStagesEntity.getStagesCount() ) {
    				if( categoryMstrName.equals("Sample Templates") )
    				{
    					sampleTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId() , "READY_TO_USE" );
    				}
    				else if( categoryMstrName.equals("Central Templates") ) {
    					centralTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId() , "DRAFT" );
    				}
    				else if( categoryMstrName.equals("Project Templates") ) {
    					projectTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId() , "DRAFT" );
    				}
    			}
    			templatesResp.getWorkflowTOs().add( ProjDocTemplatesHandler.convertWorkFlowEntityToPOJO( workFlowStagesEntity , categoryMstrName ) );
    		}			
    	}*/
    	if( categoryMstrName.equals("Sample Templates") )
    	{
    		SampleTemplatesEntity cloneSampleTemplatesEntity = new SampleTemplatesEntity();
    		SampleTemplatesEntity sampleTemplatesEntity = sampleTemplatesRepository.findTemplatesByTemplateId( templateSaveReq.getTemplateId() );
    		SampleTemplatesEntity resSampleTemplatesEntity = new SampleTemplatesEntity();
    		templateStatus = sampleTemplatesEntity.getStatus();
    		if( sampleTemplatesEntity.getStatus().equals("DRAFT") )
    		{
    			System.out.println("DRAFT status condition");
    			sampleTemplatesRepository.updateTemplateJsonById( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateJson() );
    			templateType = sampleTemplatesEntity.getTemplateType();
    			//sampleTemplatesRepository.updateTemplateJsonById( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateJson() );   			
    			templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertSampleTemplatesEntityToPOJO( sampleTemplatesEntity , sampleTemplatesRepository ) );
    		}
    		else
    		{
    			System.out.println("else DRAFT status condition");
    			cloneSampleTemplatesEntity.setCategoryMstrId( sampleTemplatesEntity.getCategoryMstrId() );    	
            	cloneSampleTemplatesEntity.setTemplateCategoryId( sampleTemplatesEntity.getTemplateCategoryId() );
            	cloneSampleTemplatesEntity.setTemplateName( sampleTemplatesEntity.getTemplateName() );    	
            	cloneSampleTemplatesEntity.setFormName( sampleTemplatesEntity.getFormName() );
            	cloneSampleTemplatesEntity.setTemplateType( sampleTemplatesEntity.getTemplateType() );    	
            	cloneSampleTemplatesEntity.setTemplateJson( templateSaveReq.getTemplateJson() );
            	cloneSampleTemplatesEntity.setStatus( "DRAFT" );
            	cloneSampleTemplatesEntity.setFormsCount( templateSaveReq.getFormsCount() );
            	if( sampleTemplatesEntity.getStatus().equals("READY_TO_USE") )
            	{
            		int version_status = sampleTemplatesEntity.getVersionStatus()+1;
            		Integer versionObj = version_status;
            		cloneSampleTemplatesEntity.setVersionStatus( versionObj );
            		sampleTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId() , "SUPERSEDED" );
            	}
            	
            	if( sampleTemplatesEntity.getTemplateType().equals("WORKFLOW") )
            	{
            		WorkFlowStagesEntity resWorkFlowStagesEntity = workFlowStagesRepository.save( ProjDocTemplatesHandler.convertPOJOToWorkFlowEntity( templateSaveReq , workFlowStagesEntity , categoryMstrName , "NEW" ) );
            		cloneSampleTemplatesEntity.setWorkflowStagesId( resWorkFlowStagesEntity );
            	}
            	cloneSampleTemplatesEntity.setCreatedBy( sampleTemplatesEntity.getCreatedBy() );
            	resSampleTemplatesEntity = sampleTemplatesRepository.save( cloneSampleTemplatesEntity );
            	templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertSampleTemplatesEntityToPOJO( resSampleTemplatesEntity , sampleTemplatesRepository ) );
    		}
    	}
    	else if( categoryMstrName.equals("Central Templates") )
    	{
    		CentralTemplatesEntity cloneCentralTemplatesEntity = new CentralTemplatesEntity();
    		CentralTemplatesEntity centralTemplatesEntity = centralTemplatesRepository.findTemplatesByTemplateId( templateSaveReq.getTemplateId() );
    		ClientRegEntity clientRegEntity = new ClientRegEntity();
    		clientRegEntity.setClientId( templateSaveReq.getCrmId() );
    		templateStatus = centralTemplatesEntity.getStatus();
    		
    		if( centralTemplatesEntity.getStatus().equals("DRAFT") )
    		{
    			templateType = centralTemplatesEntity.getTemplateType();
    			centralTemplatesRepository.updateTemplateJsonById( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateJson() ); 
    			/*if( centralTemplatesEntity.getTemplateType().equals("TIMELINE") )
    			{
    				centralTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId() , "INPROGRESS" );
    			}*/
    			templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertCentralTemplatesEntityToPOJO( centralTemplatesEntity , centralTemplatesRepository ) );
    		}
    		else
    		{
	    		cloneCentralTemplatesEntity.setCategoryMstrId( centralTemplatesEntity.getCategoryMstrId() );    	
	    		cloneCentralTemplatesEntity.setTemplateCategoryId( centralTemplatesEntity.getTemplateCategoryId() );
	    		cloneCentralTemplatesEntity.setTemplateName( centralTemplatesEntity.getTemplateName() );    	
	    		cloneCentralTemplatesEntity.setFormName( centralTemplatesEntity.getFormName() );
	    		cloneCentralTemplatesEntity.setTemplateType( centralTemplatesEntity.getTemplateType() );    	
	    		cloneCentralTemplatesEntity.setTemplateJson( templateSaveReq.getTemplateJson() );
	    		cloneCentralTemplatesEntity.setCrmId( clientRegEntity );
	    		cloneCentralTemplatesEntity.setStatus( "DRAFT" );
	    		cloneCentralTemplatesEntity.setFormsCount( templateSaveReq.getFormsCount() );
	        	if( centralTemplatesEntity.getStatus().equals("READY_TO_USE") )
	        	{
	        		int version_status = centralTemplatesEntity.getVersionStatus()+1;
	        		Integer versionObj = version_status;
	        		cloneCentralTemplatesEntity.setVersionStatus( versionObj );
	        		centralTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId() , "SUPERSEDED" );
	        	}
	        	
	        	if( centralTemplatesEntity.getTemplateType().equals("WORKFLOW") )
            	{
            		WorkFlowStagesEntity resWorkFlowStagesEntity = workFlowStagesRepository.save( ProjDocTemplatesHandler.convertPOJOToWorkFlowEntity( templateSaveReq , workFlowStagesEntity , categoryMstrName , "NEW" ) );
            		cloneCentralTemplatesEntity.setWorkflowStagesId( resWorkFlowStagesEntity );
            	}
	        	cloneCentralTemplatesEntity.setCreatedBy( centralTemplatesEntity.getCreatedBy() );
	        	CentralTemplatesEntity resCentralTemplatesEntity = centralTemplatesRepository.save( cloneCentralTemplatesEntity );        	
	        	templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertCentralTemplatesEntityToPOJO( resCentralTemplatesEntity , centralTemplatesRepository ) );
    		}
    	}
    	else if( categoryMstrName.equals("Project Templates") )
    	{
    		ProjectTemplatesEntity cloneProjectTemplatesEntity = new ProjectTemplatesEntity();
    		ProjectTemplatesEntity projectTemplatesEntity = projectTemplatesRepository.findTemplatesByTemplateId( templateSaveReq.getTemplateId() );
    		
    		ProjMstrEntity projMstrEntity = new ProjMstrEntity();
    		projMstrEntity.setProjectId( templateSaveReq.getProjId() );
    		
    		ClientRegEntity clientRegEntity = new ClientRegEntity();
    		clientRegEntity.setClientId( templateSaveReq.getCrmId() );
    		templateStatus = projectTemplatesEntity.getStatus();
    		
    		if( projectTemplatesEntity.getStatus().equals("DRAFT") )
    		{
    			templateType = projectTemplatesEntity.getTemplateType();
    			projectTemplatesRepository.updateTemplateJsonById( templateSaveReq.getTemplateId() , templateSaveReq.getTemplateJson() ); 
    			/*if( projectTemplatesEntity.getTemplateType().equals("TIMELINE") )
    			{
    				projectTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId() , "INPROGRESS" );
    			}*/  			
    			templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertProjectTemplatesEntityToPOJO( projectTemplatesEntity , projectTemplatesRepository ) );
    			if( projectTemplatesEntity.getTemplateType().equals("WORKFLOW") )
            	{
    				workFlowStagesEntity.setWorkflowId( projectTemplatesEntity.getWorkflowStagesId().getWorkflowId() );
            		WorkFlowStagesEntity resWorkFlowStagesEntity = workFlowStagesRepository.save( ProjDocTemplatesHandler.convertPOJOToWorkFlowEntity( templateSaveReq , workFlowStagesEntity , categoryMstrName , "NEW" ) );
            		//cloneProjectTemplatesEntity.setWorkflowStagesId( resWorkFlowStagesEntity );
            	}
    		}
    		else 
    		{
	    		cloneProjectTemplatesEntity.setCategoryMstrId( projectTemplatesEntity.getCategoryMstrId() );
	    		cloneProjectTemplatesEntity.setTemplateCategoryId( projectTemplatesEntity.getTemplateCategoryId() );
	    		cloneProjectTemplatesEntity.setTemplateName( projectTemplatesEntity.getTemplateName() );    	
	    		cloneProjectTemplatesEntity.setFormName( projectTemplatesEntity.getFormName() );
	    		cloneProjectTemplatesEntity.setTemplateType( projectTemplatesEntity.getTemplateType() );    	
	    		cloneProjectTemplatesEntity.setTemplateJson( templateSaveReq.getTemplateJson() );
	    		cloneProjectTemplatesEntity.setCrmId( projectTemplatesEntity.getCrmId() );
	    		cloneProjectTemplatesEntity.setProjId( projMstrEntity );
	    		cloneProjectTemplatesEntity.setStatus( "DRAFT" );
	    		cloneProjectTemplatesEntity.setFormsCount( templateSaveReq.getFormsCount() );
	        	if( projectTemplatesEntity.getStatus().equals("READY_TO_USE") )
	        	{
	        		int version_status = projectTemplatesEntity.getVersionStatus()+1;
	        		Integer versionObj = version_status;
	        		cloneProjectTemplatesEntity.setVersionStatus( versionObj );
	        		projectTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId() , "SUPERSEDED" );
	        	}
	        	if( projectTemplatesEntity.getTemplateType().equals("WORKFLOW") )
            	{
            		WorkFlowStagesEntity resWorkFlowStagesEntity = workFlowStagesRepository.save( ProjDocTemplatesHandler.convertPOJOToWorkFlowEntity( templateSaveReq , workFlowStagesEntity , categoryMstrName , "NEW" ) );
            		cloneProjectTemplatesEntity.setWorkflowStagesId( resWorkFlowStagesEntity );
            	}
	        	cloneProjectTemplatesEntity.setCreatedBy( projectTemplatesEntity.getCreatedBy() );
	        	ProjectTemplatesEntity resProjectTemplatesEntity = projectTemplatesRepository.save( cloneProjectTemplatesEntity );
	        	
	        	templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertProjectTemplatesEntityToPOJO( resProjectTemplatesEntity , projectTemplatesRepository ) );
    		}
    	}    	
        return templatesResp;
    }
    
    public TemplatesResp templateApproval( TemplateSaveReq templateSaveReq ) {
    	TemplatesResp templatesResp = new TemplatesResp();
    	System.out.print("TemplateSaveReq from templateApproval");
    	System.out.println(templateSaveReq);
    	CategoriesMstrEntity categoriesMstrEntity = categoriesMstrRepository.findByCategoryMstrId( templateSaveReq.getCategoryMstrId() );
    	String categoryMstrName = categoriesMstrEntity.getTemplateCategoryName();
    	
    	String approvalStatus = "";
    	char isApproved = 'N';
    	String approvalType = "";
    	CentralTemplatesEntity centralTemplatesEntity = null;
    	ProjectTemplatesEntity projectTemplatesEntity = null;
    	SampleTemplatesEntity sampleTemplatesEntity = null;
    	
    	if( templateSaveReq.getStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL") || templateSaveReq.getStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL") )
    	{
    		approvalStatus = "SUBMITTED";
    		isApproved = 'N';
    		approvalType = templateSaveReq.getStatus().equals("SUBMIT_FOR_EXTERNAL_APPROVAL") ? "EXTERNAL" : "INTERNAL";
    	}
		else
		{
			approvalStatus = "APPROVED";
			isApproved = 'Y';
			approvalType = templateSaveReq.getStatus().equals("EXTERNAL_APPROVAL") ? "EXTERNAL" : "INTERNAL";
		}
    	String status = "";
    	int version_status = 0;
    	char isExternalApprovalRequired = 'N';
    	if( categoryMstrName.equals("Project Templates") )
    	{
    		projectTemplatesEntity = projectTemplatesRepository.findTemplatesByTemplateId( templateSaveReq.getTemplateId() );
    		if( approvalType.equals("INTERNAL") )
    		{
    			System.out.println("if condition of INTERNAL block of Project Templates");
    			//projectTemplatesRepository.updateTemplateInternalApprovalStatusById( templateSaveReq.getTemplateId() , approvalStatus , Character.valueOf(isApproved) , templateSaveReq.getCreatedBy() );    			
    			if( templateSaveReq.getStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL") )
    			{
    				status = "SUBMITTED_FOR_INTERNAL_APPROVAL";
    				isExternalApprovalRequired = templateSaveReq.getIsExternalApprovedRequired();
    			}
    			else
    			{
    				status = ( templateSaveReq.getIsExternalApprovedRequired() == 'N' ) ? "READY_TO_USE" : "INTERNAL_APPROVED";
    				isExternalApprovalRequired = templateSaveReq.getIsExternalApprovedRequired();
    			}
    			version_status = projectTemplatesEntity.getVersionStatus();
    			projectTemplatesRepository.updateTemplateInternalApprovalStatusById( templateSaveReq.getTemplateId() , approvalStatus , Character.valueOf(isApproved) , version_status , Character.valueOf(isExternalApprovalRequired) , status , templateSaveReq.getCreatedBy() );
    		}
    		else
    		{
    			System.out.println("else condition of INTERNAL block of Project Templates");
    			status = approvalStatus.equals("SUBMITTED") ? "SUBMITTED_FOR_EXTERNAL_APPROVAL" : "READY_TO_USE";
    			version_status = projectTemplatesEntity.getVersionStatus();
    			projectTemplatesRepository.updateTemplateExternalApprovalStatusById( templateSaveReq.getTemplateId() , approvalStatus , status , version_status , Character.valueOf(isApproved) , templateSaveReq.getCreatedBy() );
    		}
    		ProjectTemplatesEntity resProjectTemplatesEntity = projectTemplatesRepository.findOne( templateSaveReq.getTemplateId() );
    		templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertProjectTemplatesEntityToPOJO( resProjectTemplatesEntity, projectTemplatesRepository ) );
    	}
    	else if( categoryMstrName.equals("Central Templates") )
    	{
    		centralTemplatesEntity = centralTemplatesRepository.findTemplatesByTemplateId( templateSaveReq.getTemplateId() );
    		if( approvalType.equals("INTERNAL") )
    		{
    			System.out.println("if condition of INTERNAL block of Central Templates");
    			//centralTemplatesRepository.updateTemplateInternalApprovalStatusById( templateSaveReq.getTemplateId() , approvalStatus , Character.valueOf(isApproved) , templateSaveReq.getCreatedBy() );
    			if( templateSaveReq.getStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL") )
    			{
    				System.out.println("if condition of SUBMIT_FOR_INTERNAL_APPROVAL block");
    				//status = centralTemplatesEntity.getStatus();
    				status = "SUBMITTED_FOR_INTERNAL_APPROVAL";
    			}
    			else
    			{
    				System.out.println("else condition of SUBMIT_FOR_INTERNAL_APPROVAL block");
    				status = ( templateSaveReq.getIsExternalApprovedRequired() == 'N' ) ? "READY_TO_USE" : "INTERNAL_APPROVED";
    				//version_status = ( centralTemplatesEntity.getIsExternalApprovedRequired() == 'N' ) ? centralTemplatesEntity.getVersionStatus()+1 : centralTemplatesEntity.getVersionStatus();
    				isExternalApprovalRequired = templateSaveReq.getIsExternalApprovedRequired();
    			}
    			version_status = centralTemplatesEntity.getVersionStatus();
    			System.out.println("status:"+status+" version_status:"+version_status+" isExternalApprovalRequired:"+isExternalApprovalRequired+" isApproved:"+isApproved+" approvalStatus:"+approvalStatus);
    			//updateTemplateInternalApprovalStatusById( @Param("templateId") Long templateId , @Param("status") String approvalStatus , @Param("isApproved") Character isApproved , @Param("versionStatus") Integer version_status , @Param("isExternalApprovalRequired") Character isExternalApprovalRequired , @Param("status") String status , @Param("approvedBy") Long approvedBy );
    			centralTemplatesRepository.updateTemplateInternalApprovalStatusById( templateSaveReq.getTemplateId() , approvalStatus , Character.valueOf(isApproved) , version_status , Character.valueOf(isExternalApprovalRequired) , status , templateSaveReq.getCreatedBy() );    			
    		}
    		else
    		{
    			System.out.println("else condition of INTERNAL block from Central Templates block");
    			status = approvalStatus.equals("SUBMITTED") ? "SUBMITTED_FOR_EXTERNAL_APPROVAL" : "READY_TO_USE";
    			version_status = centralTemplatesEntity.getVersionStatus();
    			System.out.println("status:"+status+" version_status:"+version_status+" isExternalApprovalRequired:"+isExternalApprovalRequired+" isApproved:"+isApproved+" approvalStatus:"+approvalStatus);
    			centralTemplatesRepository.updateTemplateExternalApprovalStatusById( templateSaveReq.getTemplateId() , approvalStatus , status , version_status , Character.valueOf(isApproved) , templateSaveReq.getCreatedBy() );
    		}
    		CentralTemplatesEntity resCentralTemplatesEntity = centralTemplatesRepository.findOne( templateSaveReq.getTemplateId() );
    		templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertCentralTemplatesEntityToPOJO( resCentralTemplatesEntity, centralTemplatesRepository ) );
    	}
    	else if( categoryMstrName.equals("Sample Templates") )
    	{
    		sampleTemplatesEntity = sampleTemplatesRepository.findTemplatesByTemplateId( templateSaveReq.getTemplateId() );
    		if( approvalType.equals("INTERNAL") )
    		{
    			System.out.println("if condition of INTERNAL block of Sample Templates");
    			if( templateSaveReq.getStatus().equals("SUBMIT_FOR_INTERNAL_APPROVAL") )
    			{
    				System.out.println("if condition of SUBMIT_FOR_INTERNAL_APPROVAL block");
    				status = "SUBMITTED_FOR_INTERNAL_APPROVAL";
    				//sampleTemplatesRepository.updateTemplateInternalApprovalStatusById( templateSaveReq.getTemplateId() , approvalStatus , Character.valueOf(isApproved) , Character.valueOf(isExternalApprovalRequired) , status , templateSaveReq.getCreatedBy() );
    			}
    			else
    			{
    				System.out.println("else condition of SUBMIT_FOR_INTERNAL_APPROVAL block");
    				status = ( templateSaveReq.getIsExternalApprovedRequired() == 'N' ) ? "READY_TO_USE" : "INTERNAL_APPROVED";
    				isExternalApprovalRequired = templateSaveReq.getIsExternalApprovedRequired();
    			}
    			version_status = sampleTemplatesEntity.getVersionStatus();
    			sampleTemplatesRepository.updateTemplateInternalApprovalStatusById( templateSaveReq.getTemplateId() , approvalStatus , Character.valueOf(isApproved) , Character.valueOf(isExternalApprovalRequired) , status , templateSaveReq.getCreatedBy() );    			
    			System.out.println("status:"+status+" version_status:"+version_status+" isExternalApprovalRequired:"+isExternalApprovalRequired+" isApproved:"+isApproved+" approvalStatus:"+approvalStatus);    			    			
    		}
    		else
    		{
    			System.out.println("else condition of INTERNAL block from Sample Templates block");
    			status = approvalStatus.equals("SUBMITTED") ? "SUBMITTED_FOR_EXTERNAL_APPROVAL" : "READY_TO_USE";
    			version_status = sampleTemplatesEntity.getVersionStatus();
    			System.out.println("status:"+status+" version_status:"+version_status+" isExternalApprovalRequired:"+isExternalApprovalRequired+" isApproved:"+isApproved+" approvalStatus:"+approvalStatus);
    			sampleTemplatesRepository.updateTemplateExternalApprovalStatusById( templateSaveReq.getTemplateId() , approvalStatus , status , Character.valueOf(isApproved) , templateSaveReq.getCreatedBy() );
    		}
    		SampleTemplatesEntity resSampleTemplatesEntity = sampleTemplatesRepository.findOne( templateSaveReq.getTemplateId() );
    		templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertSampleTemplatesEntityToPOJO( resSampleTemplatesEntity, sampleTemplatesRepository ) );
    		//sample.updateTemplateApprovalStatusById( templateSaveReq.getTemplateId() , approvalStatus , status , version_status , Character.valueOf(isApproved) , templateSaveReq.getCreatedBy() );
    	}
    	return templatesResp;
    }
    
    public TemplatesResp searchProjectTemplates( TemplatesGetReq templatesGetReq ) {
    	TemplatesResp templatesResp = new TemplatesResp();
    	System.out.println("templatesGetReq from searchProjectTemplates");
    	System.out.println(templatesGetReq);
    	System.out.println("project id:"+templatesGetReq.getProjId());
		List<ProjectTemplatesEntity> projectTemplatesEntities = projectTemplatesRepository.findProjectTemplatesByProjId( templatesGetReq.getProjId() , templatesGetReq.getCrmId() );
        System.out.println("This is from searchProjectTemplates from ProjDocumentServiceImpl class");
        for( ProjectTemplatesEntity projectTemplatesEntity : projectTemplatesEntities ) {
        	System.out.println(projectTemplatesEntity);
        	templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertProjectTemplatesEntityToPOJO( projectTemplatesEntity , projectTemplatesRepository ) );
        }
		
    	return templatesResp;
    }
    
    public ProjectFormsResp createProjectForm( ProjectFormsGetReq projectFormsGetReq ) {
    	System.out.println("This is from createProjectForm function");
    	ProjectFormsResp projectFormsResp = new ProjectFormsResp();
    	ProjectFormsEntity projectFormsEntity = new ProjectFormsEntity();
    	
    	projectFormsEntity = projectFormsRepository.save( ProjDocTemplatesHandler.convertPOJOToProjectFormsEntity( projectFormsGetReq , projectFormsEntity ) );
    	if( projectFormsEntity.getProjectTemplateId() != null )
    	{
    		//ProjectTemplatesEntity projTemplatesEntity = projectFormsEntity.getProjectTemplateId();
    		ProjectTemplatesEntity projTemplatesEntity = projectTemplatesRepository.findOne(projectFormsEntity.getProjectTemplateId().getTemplateId());
    		System.out.println(projTemplatesEntity);
    		if( projTemplatesEntity.getFromSource().equals("From Sample Templates") )
    		{
    			SampleTemplatesEntity sampleTemplatesEntity = sampleTemplatesRepository.findOne( projTemplatesEntity.getSampleTemplateId().getTemplateId() );
    			Integer stempls_forms_cnt_obj = projTemplatesEntity.getSampleTemplateId().getFormsCount();
    			int stempls_cnt = stempls_forms_cnt_obj.intValue()+1;
    			sampleTemplatesRepository.updateSampleTemplatesFormsCnt( projTemplatesEntity.getSampleTemplateId().getTemplateId(), stempls_cnt );
    		}
    		else if( projTemplatesEntity.getFromSource().equals("From Central Templates") )
    		{
    			CentralTemplatesEntity centralTemplatesEntity = centralTemplatesRepository.findOne( projTemplatesEntity.getCentralTemplateId().getTemplateId() );
    			Integer ctempls_forms_cnt_obj = centralTemplatesEntity.getFormsCount();
    			int ctempls_cnt = ctempls_forms_cnt_obj.intValue()+1;
    			centralTemplatesRepository.updateCentralTemplatesFormsCnt( projTemplatesEntity.getCentralTemplateId().getTemplateId(), ctempls_cnt );
    		}
    		else
    		{
    			Integer ptempls_forms_cnt_obj = projTemplatesEntity.getFormsCount();
    			int ptempls_cnt = ptempls_forms_cnt_obj.intValue()+1;
    			projectTemplatesRepository.updateProjectTemplatesFormsCnt( projTemplatesEntity.getTemplateId(), ptempls_cnt );
    		}
    	}
    	
    	projectFormsResp.getFormsTOs().add( ProjDocTemplatesHandler.convertProjectFormsEntityToPOJO( projectFormsEntity ) );
    	return projectFormsResp;
    }
    
    public TemplatesResp transferFromTemplates( TransferTemplatesGetReq transferTemplatesGetReq )
    {
    	String transferFrom = transferTemplatesGetReq.getTransferFrom();
    	//ProjectTemplatesEntity projectTemplatesEntity = projectTemplatesRepository.findTemplatesByTemplateId( templateSaveReq.getTemplateId() );
    	ProjectTemplatesEntity projectTemplatesEntity = null;
    	ProjectTemplatesEntity resProjectTemplatesEntity = null;
    	CentralTemplatesEntity centralTemplatesEntity = null;
    	CentralTemplatesEntity resCentralTemplatesEntity = null;
    	TemplatesResp templatesResp = new TemplatesResp();
    	int templates_count = 0;
		
		UserMstrEntity userMstrEntity = new UserMstrEntity();
		userMstrEntity.setUserId( transferTemplatesGetReq.getUserId() );
		
		ProjMstrEntity projMstrEntity = null;
		
		CategoriesMstrEntity categoriesMstrEntity = new CategoriesMstrEntity();
		categoriesMstrEntity.setCategoryMstrId( transferTemplatesGetReq.getCategoryMstrId() );
		System.out.println("transferTemplates function");
    	for( TemplatesTO templatesTO : transferTemplatesGetReq.getTemplatesTOs() )
    	{
    		System.out.println(templatesTO.getTemplateId());
    		if( transferFrom.equals("From Projects") )
    		{    			
    			System.out.println("if condition From Projects");
    			projectTemplatesEntity = new ProjectTemplatesEntity();
    			centralTemplatesEntity = new CentralTemplatesEntity();
    			
    			projectTemplatesEntity = projectTemplatesRepository.findTemplatesByTemplateId( templatesTO.getTemplateId() );
    			System.out.println(projectTemplatesEntity);
    			System.out.println(projectTemplatesEntity.getTemplateCategoryId().getCategoryName()+"=>"+transferTemplatesGetReq.getCategoryMstrId());
    			TemplateCategoriesEntity templateCategoriesEntity = templateCategoriesRepository.findCategoriesByCategoryNameAndMstrId( projectTemplatesEntity.getTemplateCategoryId().getCategoryName(), transferTemplatesGetReq.getCategoryMstrId() );
    			centralTemplatesEntity.setTemplateName( projectTemplatesEntity.getTemplateName() );
    			centralTemplatesEntity.setFormName( projectTemplatesEntity.getFormName() );
    			centralTemplatesEntity.setTemplateCategoryId( templateCategoriesEntity );
    			centralTemplatesEntity.setCrmId( projectTemplatesEntity.getCrmId() );
    			centralTemplatesEntity.setTemplateType( projectTemplatesEntity.getTemplateType() );
    			centralTemplatesEntity.setTemplateJson( projectTemplatesEntity.getTemplateJson() );
    			centralTemplatesEntity.setStatus( "DRAFT" );
    			centralTemplatesEntity.setVersionStatus( 1 );
    			centralTemplatesEntity.setCreatedBy( userMstrEntity );
    			centralTemplatesEntity.setCategoryMstrId( categoriesMstrEntity );
    			centralTemplatesRepository.save( centralTemplatesEntity );
    			//System.out.println(resCentralTemplatesEntity);
    		}
    		else
    		{
    			projMstrEntity = new ProjMstrEntity();
    			projMstrEntity.setProjectId( transferTemplatesGetReq.getProjectId() );    			
    			System.out.println("else condition");
    			projectTemplatesEntity = new ProjectTemplatesEntity();
    			centralTemplatesEntity = new CentralTemplatesEntity();
    			
    			centralTemplatesEntity = centralTemplatesRepository.findTemplatesByTemplateId( templatesTO.getTemplateId() );
    			TemplateCategoriesEntity templateCategoriesEntity = templateCategoriesRepository.findCategoriesByCategoryNameAndMstrId( centralTemplatesEntity.getTemplateCategoryId().getCategoryName(), transferTemplatesGetReq.getCategoryMstrId() );    			
    			projectTemplatesEntity.setTemplateName( centralTemplatesEntity.getTemplateName() );
    			projectTemplatesEntity.setFormName( centralTemplatesEntity.getFormName() );
    			projectTemplatesEntity.setTemplateCategoryId( templateCategoriesEntity );
    			projectTemplatesEntity.setCrmId( centralTemplatesEntity.getCrmId() );
    			projectTemplatesEntity.setTemplateType( centralTemplatesEntity.getTemplateType() );
    			projectTemplatesEntity.setTemplateJson( centralTemplatesEntity.getTemplateJson() );
    			projectTemplatesEntity.setStatus( "DRAFT" );
    			projectTemplatesEntity.setVersionStatus( 1 );
    			projectTemplatesEntity.setProjId( projMstrEntity );
    			projectTemplatesEntity.setCreatedBy( userMstrEntity );
    			projectTemplatesEntity.setCategoryMstrId( categoriesMstrEntity );
    			projectTemplatesRepository.save( projectTemplatesEntity );
    		}
    		templates_count++;
    	}
    	//return templates_count;
    	System.out.println("Templates Count:"+templates_count);
    	templatesResp.setTemplatesCount(templates_count);
    	return templatesResp;
    }
    
    public Long getSampleTemplsLastInsertedId( TemplatesGetReq templatesGetReq )
    {
    	Long sample_template_id = sampleTemplatesRepository.findLastInsertedTemplateId();
    	System.out.println("sample templates last inserted id:"+sample_template_id);
    	return sample_template_id;
    }
    
    public Long getCentralTemplsLastInsertedId( TemplatesGetReq templatesGetReq )
    {
    	return centralTemplatesRepository.findLastInsertedTemplateId() != null ? centralTemplatesRepository.findLastInsertedTemplateId() : 1L;
    }
    
    public Long getProjTemplsLastInsertedId( TemplatesGetReq templatesGetReq )
    {
    	return projectTemplatesRepository.findLastInsertedTemplateId() !=null ? projectTemplatesRepository.findLastInsertedTemplateId() : 1L;
    }
    
    public TemplatesProposalResp saveProjectTemplatesProposal( TemplateProposalReq templateProposalReq )
    {
    	ProjectTemplatesProposalEntity projectTemplatesProposalEntity = null;
    	TemplatesProposalResp templatesProposalResp = new TemplatesProposalResp();
    			
    	int templates_count = 0;
    	
    	List<ProjectTemplatesProposalTO> projectTemplatesProposalTOs = templateProposalReq.getProjectTemplatesProposalTOs();
    	for( ProjectTemplatesProposalTO projectTemplatesProposalTO : projectTemplatesProposalTOs )
    	{	
    		projectTemplatesProposalEntity = new ProjectTemplatesProposalEntity();
    		ProjectTemplatesEntity projectTemplatesEntity = projectTemplatesRepository.findTemplatesByTemplateId( projectTemplatesProposalTO.getProjectTemplateId() );
    		projectTemplatesEntity.setTemplateId( projectTemplatesProposalTO.getProjectTemplateId() );
    		
    		UserMstrEntity proposerUserMstrEntity = new UserMstrEntity();
        	proposerUserMstrEntity.setUserId( templateProposalReq.getProposerUserId() );
        	
        	UserMstrEntity reviewerUserMstrEntity = new UserMstrEntity();
        	reviewerUserMstrEntity.setUserId( templateProposalReq.getReviewerUserId() );
    		
    		ProjMstrEntity projMstrEntity = new ProjMstrEntity();
    		projMstrEntity.setProjectId( templateProposalReq.getFromProjectId() );
    		
    		ClientRegEntity clientRegEntity = new ClientRegEntity();
    		clientRegEntity.setClientId( templateProposalReq.getCrmId() );
    		
    		System.out.println(projectTemplatesEntity.getTemplateId());			
			projectTemplatesProposalEntity.setProjectTemplateId( projectTemplatesEntity );
			projectTemplatesProposalEntity.setCrmId( clientRegEntity );
			projectTemplatesProposalEntity.setProposerUserId( proposerUserMstrEntity );
			projectTemplatesProposalEntity.setReviewerUserId( reviewerUserMstrEntity );
			projectTemplatesProposalEntity.setFromProjectId( projMstrEntity );
			projectTemplatesProposalEntity.setStatus( "INITIATED" );
			
			projectTemplatesProposalRepository.save( projectTemplatesProposalEntity );
    		templates_count++;
    	}
    	templatesProposalResp.setTemplatesCount( templates_count );
    	return templatesProposalResp;
    }
    
    public TemplatesProposalResp getProposalList( TemplateProposalReq templateProposalReq )
    {
    	TemplatesProposalResp templatesProposalResp = new TemplatesProposalResp();
    	
    	List<ProjectTemplatesProposalEntity> projectTemplatesProposalEntities = projectTemplatesProposalRepository.findProposalsByCrmId( templateProposalReq.getCrmId() );
        System.out.println("This is from getProjectTemplatesProposal from ProjDocumentServiceImpl class");
        for( ProjectTemplatesProposalEntity projectTemplatesProposalEntity : projectTemplatesProposalEntities ) {
        	System.out.println(projectTemplatesProposalEntity);
        	templatesProposalResp.getProjectTemplatesProposalTOs().add( ProjDocTemplatesHandler.convertProjectTemplatesProposalEntityToPOJO( projectTemplatesProposalEntity ) );
        }
        
    	return templatesProposalResp;
    }
    // This is to download the documents under the Project Documents module
    public ProjDocFileTO downloadProjectDocs( Long recordId ) throws IOException {
    	ProjDocFileResp projDocFileResp = new ProjDocFileResp();
		ProjDocFileTO projDocFileTO = new ProjDocFileTO();
		int status = 1;
		ProjDocFileEntity projDocFileEntity = projDocFileRepository.findProjectDocFilesById( recordId, status );
        byte[] fileBytes = null;        
        System.out.println(projDocFileEntity);
        String upload_path = ApplicationConstants.FILE_DIRECTORY + "/" + projDocFileEntity.getFolderId().getUploadFolder() + projDocFileEntity.getFolderPath() + "/" +projDocFileEntity.getName();
        System.out.println(upload_path);
        projDocFileTO.setFileContent( fileBytes );
        projDocFileTO.setFolderPath( upload_path );
        projDocFileTO.setFileType( projDocFileEntity.getFileType() );        
        projDocFileTO.setName( projDocFileEntity.getName() );
        projDocFileTO.setFileSize( projDocFileEntity.getFileSize() );
        return projDocFileTO;
    }
    
    public ProjDocFileResp getProjDocsByProjectId( ProjDocGetReq projDocGetReq ) throws IOException {
    	ProjDocFileResp projDocFileResp = new ProjDocFileResp();
    	System.out.println(projDocGetReq.getProjId()+"->"+projDocGetReq.getFolderCategory());
    	List<ProjDocFileEntity> projDocFileEntities = new ArrayList<ProjDocFileEntity>();
    	if( projDocGetReq.getFolderCategory().equals("Client Registration") )
    	{
    		ProjMstrEntity projMstrEntity = epsProjRepository.findOne( projDocGetReq.getProjId() );
    		Long proj_file_id = projMstrEntity.getClientId().getProjDocFileEntity().getId();
    		System.out.println("Proj File Id:"+proj_file_id);
    		projDocFileEntities.add( projDocFileRepository.findOne( proj_file_id ) );
    	}
    	else
    	{
    		projDocFileEntities = projDocFileRepository.findProjDocsByProjectIdAndFolder( projDocGetReq.getProjId(), projDocGetReq.getFolderCategory() );
    	}    	
    	System.out.println("result:"+projDocFileEntities.size());
    	for( ProjDocFileEntity projDocFileEntity : projDocFileEntities ) {
    		projDocFileResp.getProjDocFileTOs().add( projDocFileHandler.convertEntityToPOJO( projDocFileEntity, false ) );
    	}
    	return projDocFileResp;
    }
    
    public ProjectFormsResp updateProjectForm( ProjectFormsGetReq projectFormsGetReq ) {
    	System.out.println("This is from updateProjectForm function");
    	ProjectFormsResp projectFormsResp = new ProjectFormsResp();
    	
    	projectFormsRepository.updateProjFormById( projectFormsGetReq.getFormId(), projectFormsGetReq.getFormVersion(), projectFormsGetReq.getFormJson(), projectFormsGetReq.getStatus() );
    	ProjectFormsEntity projectFormsEntity = projectFormsRepository.findProjFormById( projectFormsGetReq.getFormId() );
    	projectFormsResp.getFormsTOs().add( ProjDocTemplatesHandler.convertProjectFormsEntityToPOJO( projectFormsEntity ) );
    	return projectFormsResp;
    }
    
    public TemplatesResp workflowSubmitForApproval( TemplateSaveReq templateSaveReq ) {
    	System.out.println("This is from workflowSubmitForApproval function");
    	TemplatesResp templatesResp = new TemplatesResp();
    	WorkFlowStagesEntity workFlowStagesEntity = null;
    	//WorkFlowStagesEntity workFlowStagesEntityData = null;
    	boolean workflow_status = templateSaveReq.getIsFinalApproval();
    	String status = "PENDING";
    	String approverComments = "";
    	String stageFieldJson = templateSaveReq.getStageFieldJson();
    	CategoriesMstrEntity categoriesMstrEntity = categoriesMstrRepository.findByCategoryMstrId( templateSaveReq.getCategoryMstrId() );
    	    	
    	if( templateSaveReq.getApprovalStageName().equals("STAGE1") )
    	{
    		status = "STAGE1_APPROVAL_SUBMITTED";
    		workFlowStagesRepository.updateWorkflowStage1SubmitApproval( templateSaveReq.getWorkflowId(), "SUBMITTED", templateSaveReq.getApproverUserId(), stageFieldJson, status );
    	}
    	else if( templateSaveReq.getApprovalStageName().equals("STAGE2") )
    	{
    		status = "STAGE2_APPROVAL_SUBMITTED";    		
    		workFlowStagesRepository.updateWorkflowStage2SubmitApproval( templateSaveReq.getWorkflowId(), "SUBMITTED", templateSaveReq.getApproverUserId(), stageFieldJson, status );
    	}
    	else if( templateSaveReq.getApprovalStageName().equals("STAGE3") )
    	{
    		status = "STAGE3_APPROVAL_SUBMITTED";
    		workFlowStagesRepository.updateWorkflowStage3SubmitApproval( templateSaveReq.getWorkflowId(), "SUBMITTED", templateSaveReq.getApproverUserId(), stageFieldJson, status );
    	}
    	else if( templateSaveReq.getApprovalStageName().equals("STAGE4") )
    	{
    		status = "STAGE4_APPROVAL_SUBMITTED";
    		workFlowStagesRepository.updateWorkflowStage4SubmitApproval( templateSaveReq.getWorkflowId(), "SUBMITTED", templateSaveReq.getApproverUserId(), stageFieldJson, status );
    	}
    	else if( templateSaveReq.getApprovalStageName().equals("STAGE5") )
    	{
    		status = "STAGE5_APPROVAL_SUBMITTED";
    		workFlowStagesRepository.updateWorkflowStage5SubmitApproval( templateSaveReq.getWorkflowId(), "SUBMITTED", templateSaveReq.getApproverUserId(), stageFieldJson, status );
    	}
    	//templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertProjectFormsEntityToPOJO( projectFormsEntity ) );
    	workFlowStagesEntity = workFlowStagesRepository.findOne( templateSaveReq.getWorkflowId() );
    	templatesResp.getWorkflowTOs().add( ProjDocTemplatesHandler.convertWorkFlowEntityToPOJO( workFlowStagesEntity, categoriesMstrEntity.getTemplateCategoryName() ) );
    	return templatesResp;
    }
    
    public TemplatesResp workflowTemplateApproval( TemplateSaveReq templateSaveReq ) {
    	System.out.println("This is from workflowTemplateApproval function");
    	TemplatesResp templatesResp = new TemplatesResp();
    	WorkFlowStagesEntity workFlowStagesEntity = null;
    	//WorkFlowStagesEntity workFlowStagesEntityData = null;
    	boolean workflow_status = templateSaveReq.getIsFinalApproval();
    	String status = "PENDING";
    	String approverComments = templateSaveReq.getApproverComments();
    	    	
    	if( templateSaveReq.getApprovalStageName().equals("STAGE1") )
    	{
    		status = !workflow_status ? "STAGE1_APPROVED" : "APPROVED";
    		workFlowStagesRepository.updateWorkflowStage1Approval( templateSaveReq.getWorkflowId(), "APPROVED", approverComments, status );
    	}
    	else if( templateSaveReq.getApprovalStageName().equals("STAGE2") )
    	{
    		status = !workflow_status ? "STAGE2_APPROVED" : "APPROVED";
    		workFlowStagesRepository.updateWorkflowStage2Approval( templateSaveReq.getWorkflowId(), "APPROVED", approverComments, status );
    	}
    	else if( templateSaveReq.getApprovalStageName().equals("STAGE3") )
    	{
    		status = !workflow_status ? "STAGE3_APPROVED" : "APPROVED";
    		workFlowStagesRepository.updateWorkflowStage3Approval( templateSaveReq.getWorkflowId(), "APPROVED", approverComments, status );
    	}
    	else if( templateSaveReq.getApprovalStageName().equals("STAGE4") )
    	{
    		status = !workflow_status ? "STAGE4_APPROVED" : "APPROVED";
    		workFlowStagesRepository.updateWorkflowStage4Approval( templateSaveReq.getWorkflowId(), "APPROVED", approverComments, status );
    	}
    	else if( templateSaveReq.getApprovalStageName().equals("STAGE5") )
    	{
    		status = !workflow_status ? "STAGE5_APPROVED" : "APPROVED";
    		workFlowStagesRepository.updateWorkflowStage5Approval( templateSaveReq.getWorkflowId(), "APPROVED", approverComments, status );
    	}
    	//templatesResp.getTemplatesTOs().add( ProjDocTemplatesHandler.convertProjectFormsEntityToPOJO( projectFormsEntity ) );
    	CategoriesMstrEntity categoriesMstrEntity = categoriesMstrRepository.findByCategoryMstrId( templateSaveReq.getCategoryMstrId() );
    	if( workflow_status )
    	{
    		System.out.println("workflow status condition"); 		
    		if( categoriesMstrEntity.getTemplateCategoryName().equals("Sample Templates") )
    		{
    			System.out.println("sample templates condition");
    			sampleTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId(), "WF_STAGES_APPROVED" );
    		}
    		else if( categoriesMstrEntity.getTemplateCategoryName().equals("Central Templates") )
    		{
    			System.out.println("central template condition");
    			centralTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId(), "WF_STAGES_APPROVED" );
    		}
    		else
    		{
    			System.out.println("project templates condition");
    			projectTemplatesRepository.updateTemplateStatusById( templateSaveReq.getTemplateId(), "WF_STAGES_APPROVED" );
    		}    		
    	}
    	workFlowStagesEntity = workFlowStagesRepository.findOne( templateSaveReq.getWorkflowId() );
    	templatesResp.getWorkflowTOs().add( ProjDocTemplatesHandler.convertWorkFlowEntityToPOJO( workFlowStagesEntity, categoriesMstrEntity.getTemplateCategoryName() ) );
    	return templatesResp;
    }
}
