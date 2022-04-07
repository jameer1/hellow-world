package com.rjtech.document.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.document.dto.ProjDocFileTO;
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
import com.rjtech.document.req.TemplatesGetReq;
import com.rjtech.document.req.TemplateCategorySaveReq;
import com.rjtech.document.req.ProjectFormsGetReq;
import com.rjtech.document.req.TemplateSaveReq;
import com.rjtech.document.req.TransferTemplatesGetReq;
import com.rjtech.document.req.TemplateProposalReq;

import com.rjtech.document.resp.TemplateCategoriesResp;
import com.rjtech.document.resp.TemplatesResp;
import com.rjtech.document.resp.ProjectFormsResp;
import com.rjtech.document.resp.TemplatesProposalResp;
import java.util.List; 

public interface ProjDocumentService {

    ProjDocFolderResp getProjDoc(ProjDocGetReq projDocGetReq);
    
    ProjDocFolderResp getProjDocFolders(ProjDocGetReq projDocGetReq);

    ProjDocFileResp getProjDocFilesByFolder(ProjDocGetReq projDocGetReq) throws IOException;

    ProjDocFolderPermissionResp getProjDocFolderPermissions(ProjDocGetReq projDocGetReq);

    void saveProjDocFolders(ProjDocFolderSaveReq projDocFolderSaveReq);

    void saveProjDocFilesByFolder(MultipartFile[] files, ProjDocFileSaveReq projDocFileSaveReq) throws IOException;

    void saveProjDocFolderPermissions(ProjDocFolderPermissionSaveReq projDocFolderPermissionSaveReq);

    ProjDocFolderPermissionResp getFolderPermissions(PermissionReq permissionReq);

    ProjDocFileTO getProjDocFile(Long fileId) throws IOException;

    void projDocDeleteFile(ProjDocDeleteReq projDocDeleteReq);

    void projDocDeleteFolder(ProjDocFolderDeactiveReq projDocFolderDeactiveReq);
    
    TemplateCategoriesResp getTemplateCategories(TemplateCategoriesReq templateCategoriesReq);
    
    TemplateCategoriesResp saveTemplateCategory( TemplateCategorySaveReq templateCategorySaveReq );
    
    void updateTemplateCategory( TemplateCategoriesReq templateCategoriesReq );
    
    TemplatesResp getSampleTemplates( TemplatesGetReq templatesGetReq );
    
    TemplatesResp getCentralTemplates( TemplatesGetReq templatesGetReq );
    
    TemplatesResp getProjectTemplates( TemplatesGetReq templatesGetReq );
    
    ProjectFormsResp getProjectForms( ProjectFormsGetReq projectFormsGetReq );
    
    TemplatesResp saveNewTemplate( TemplateSaveReq templateSaveReq );
    
    void updateTemplate( TemplateSaveReq templateSaveReq );
    
    TemplatesResp cloneTemplate( TemplateSaveReq templateSaveReq );
    
    TemplatesResp searchProjectTemplates( TemplatesGetReq templatesGetReq );
    
    ProjectFormsResp createProjectForm( ProjectFormsGetReq projectFormsGetReq );
    
    TemplatesResp templateApproval( TemplateSaveReq templateSaveReq );
    
    TemplatesResp transferFromTemplates( TransferTemplatesGetReq transferTemplatesGetReq );
    
    Long getSampleTemplsLastInsertedId( TemplatesGetReq templatesGetReq );
    
    Long getCentralTemplsLastInsertedId( TemplatesGetReq templatesGetReq );
    
    Long getProjTemplsLastInsertedId( TemplatesGetReq templatesGetReq );    
    
    TemplatesProposalResp saveProjectTemplatesProposal( TemplateProposalReq templateProposalReq );
    
    TemplatesProposalResp getProposalList( TemplateProposalReq TemplateProposalReq );

    ProjDocFileTO downloadProjectDocs( Long recordId ) throws IOException;
    
    ProjDocFileResp getProjDocsByProjectId( ProjDocGetReq projDocGetReq ) throws IOException;
    
    ProjectFormsResp updateProjectForm( ProjectFormsGetReq projectFormsGetReq );
    
    TemplatesResp workflowSubmitForApproval( TemplateSaveReq templateSaveReq );
    
    TemplatesResp workflowTemplateApproval( TemplateSaveReq templateSaveReq );
}
