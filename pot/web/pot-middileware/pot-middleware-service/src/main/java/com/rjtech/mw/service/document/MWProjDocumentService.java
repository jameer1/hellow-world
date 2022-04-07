package com.rjtech.mw.service.document;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;

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

import com.rjtech.document.req.TemplateCategoriesReq;
import com.rjtech.document.resp.TemplateCategoriesResp;
import com.rjtech.document.req.TemplateCategorySaveReq;
import com.rjtech.document.req.TemplatesGetReq;
import com.rjtech.document.req.ProjectFormsGetReq;
import com.rjtech.document.req.TemplateSaveReq;
import com.rjtech.document.req.TransferTemplatesGetReq;
import com.rjtech.document.req.TemplateProposalReq;
import com.rjtech.document.resp.TemplatesResp;
import com.rjtech.document.resp.ProjectFormsResp;
import com.rjtech.document.resp.TemplatesProposalResp;


public interface MWProjDocumentService {

	ProjDocFolderResp getProjDoc(ProjDocGetReq projDocGetReq);
	
    ProjDocFolderResp getProjDocFolders(ProjDocGetReq projDocGetReq);

    ProjDocFileResp getProjDocFilesByFolder(ProjDocGetReq projDocGetReq);

    ProjDocFolderPermissionResp getProjDocFolderPermissions(ProjDocGetReq projDocGetReq);

    ProjDocFolderResp saveProjDocFolders(ProjDocFolderSaveReq projDocFolderSaveReq);

    ProjDocFileResp saveProjDocFilesByFolder(MultipartFile[] files, ProjDocFileSaveReq projDocFileSaveReq);

    ProjDocFolderPermissionResp saveProjDocFolderPermissions(
            ProjDocFolderPermissionSaveReq projDocFolderPermissionSaveReq);

    ProjDocFolderPermissionResp getFolderPermissions(PermissionReq permissionReq);

    ResponseEntity<byte[]> getProjDocFile(Long fileId);

    ResponseEntity<Void> projDocDeleteFile(ProjDocDeleteReq projDocDeleteReq);

    ProjDocFolderResp projDocDeleteFolder(ProjDocFolderDeactiveReq projDocFolderDeactiveReq);
    
    TemplateCategoriesResp getTemplateCategories(TemplateCategoriesReq templateCategoriesReq);
    
    TemplateCategoriesResp saveTemplateCategory( TemplateCategorySaveReq templateCategorySaveReq );
    
    TemplateCategoriesResp updateTemplateCategory( TemplateCategoriesReq templateCategoriesReq );
    
    TemplatesResp getSampleTemplates( TemplatesGetReq templatesGetReq );
    
    TemplatesResp getCentralTemplates( TemplatesGetReq templatesGetReq );
    
    TemplatesResp getProjectTemplates( TemplatesGetReq templatesGetReq );
    
    ProjectFormsResp getProjectForms( ProjectFormsGetReq projectFormsGetReq );
    
    TemplatesResp saveNewTemplate( TemplateSaveReq templateSaveReq );
    
    TemplatesResp updateTemplate( TemplateSaveReq templateSaveReq );
    
    TemplatesResp cloneTemplate( TemplateSaveReq templateSaveReq );
    
    TemplatesResp searchProjectTemplates( TemplatesGetReq templatesGetReq );
    
    ProjectFormsResp createProjectForm( ProjectFormsGetReq projectFormsGetReq );
    
    TemplatesResp templateApproval( TemplateSaveReq templateSaveReq );
    
    TemplatesResp transferFromTemplates( TransferTemplatesGetReq transferTemplatesGetReq );
    
    Long getSampleTemplsLastInsertedId( TemplatesGetReq templatesGetReq );
    
    Long getCentralTemplsLastInsertedId( TemplatesGetReq templatesGetReq );
    
    Long getProjTemplsLastInsertedId( TemplatesGetReq templatesGetReq );
    
    TemplatesProposalResp saveProjectTemplatesProposal( TemplateProposalReq templateProposalReq );
    
    TemplatesProposalResp getProposalList( TemplateProposalReq templateProposalReq );
        
    ResponseEntity<ByteArrayResource> downloadProjectDocs( Long recordId );
    
    ProjDocFileResp getProjDocumentsByProjectId( ProjDocGetReq projDocGetReq );
    
    ProjectFormsResp updateProjectForm( ProjectFormsGetReq projectFormsGetReq );
    
    TemplatesResp workflowSubmitForApproval( TemplateSaveReq templateSaveReq );
    
    TemplatesResp workflowTemplateApproval( TemplateSaveReq templateSaveReq );
}
