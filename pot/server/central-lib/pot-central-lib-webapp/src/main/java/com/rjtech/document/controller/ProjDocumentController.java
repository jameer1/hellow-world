package com.rjtech.document.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.document.constants.ProjDocumentURLConstants;
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
import com.rjtech.document.service.ProjDocumentService;

import com.rjtech.document.req.TemplateCategoriesReq;
import com.rjtech.document.req.TemplatesGetReq;
import com.rjtech.document.req.TemplateSaveReq;
import com.rjtech.document.req.ProjectFormsGetReq;
import com.rjtech.document.req.TemplateCategorySaveReq;
import com.rjtech.document.resp.TemplateCategoriesResp;
import com.rjtech.document.resp.TemplatesResp;
import com.rjtech.document.resp.ProjectFormsResp;
import com.rjtech.document.resp.TemplatesProposalResp;
import com.rjtech.document.req.TransferTemplatesGetReq;
import com.rjtech.document.req.TemplateProposalReq;

@RestController
@RequestMapping(ProjDocumentURLConstants.DOCUMENT_PARH_URL)
public class ProjDocumentController {

    @Autowired
    private ProjDocumentService projDocumentService;

    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_DOC, method = RequestMethod.POST)
    public ProjDocFolderResp getProjDoc(@RequestBody ProjDocGetReq projDocGetReq) {
        ProjDocFolderResp projDocFolderResp = new ProjDocFolderResp();
        projDocFolderResp.getProjDocFolderTOs()
                .addAll(projDocumentService.getProjDoc(projDocGetReq).getProjDocFolderTOs());
        return projDocFolderResp;
    }

    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_DOC_FOLDERS, method = RequestMethod.POST)
    public ProjDocFolderResp getProjDocFolders(@RequestBody ProjDocGetReq projDocGetReq) {
        ProjDocFolderResp projDocFolderResp = new ProjDocFolderResp();
        projDocFolderResp.getProjDocFolderTOs()
                .addAll(projDocumentService.getProjDocFolders(projDocGetReq).getProjDocFolderTOs());
        return projDocFolderResp;
    }

    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_DOC_FILES_BY_FOLDER, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFileResp> getProjDocFilesByFolder(@RequestBody ProjDocGetReq projDocGetReq)
            throws IOException {
        return new ResponseEntity<ProjDocFileResp>(projDocumentService.getProjDocFilesByFolder(projDocGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_DOC_FOLDER_PERMISSIONS, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderPermissionResp> getProjDocFolderPermissions(
            @RequestBody ProjDocGetReq projDocGetReq) {
        return new ResponseEntity<ProjDocFolderPermissionResp>(
                projDocumentService.getProjDocFolderPermissions(projDocGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.SAVE_PROJ_DOC_FOLDERS, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderResp> saveProjDocFolders(
            @RequestBody ProjDocFolderSaveReq projDocFolderSaveReq) {
        projDocumentService.saveProjDocFolders(projDocFolderSaveReq);
        ProjDocGetReq projDocGetReq = new ProjDocGetReq();
        projDocGetReq.setProjId(projDocFolderSaveReq.getProjId());
        projDocGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjDocFolderResp projDocFolderResp = projDocumentService.getProjDocFolders(projDocGetReq);
        projDocFolderResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjDocFolderResp>(projDocFolderResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.SAVE_PROJ_DOC_FILES_BY_FOLDER, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFileResp> saveProjDocFilesByFolder(MultipartFile[] files, String projDocFileSaveReqStr)
            throws IOException {
        ProjDocFileSaveReq projDocFileSaveReq = AppUtils.fromJson(projDocFileSaveReqStr, ProjDocFileSaveReq.class);
        projDocumentService.saveProjDocFilesByFolder(files, projDocFileSaveReq);
        ProjDocGetReq projDocGetReq = new ProjDocGetReq();
        projDocGetReq.setId(projDocFileSaveReq.getFolderId());
        projDocGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjDocFileResp projDocFileResp = projDocumentService.getProjDocFilesByFolder(projDocGetReq);
        projDocFileResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjDocFileResp>(projDocFileResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.SAVE_PROJ_DOC_FOLDER_PERMISSIONS, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderPermissionResp> saveProjDocFolderPermissions(
            @RequestBody ProjDocFolderPermissionSaveReq projDocFolderPermissionSaveReq) {
        projDocumentService.saveProjDocFolderPermissions(projDocFolderPermissionSaveReq);
        ProjDocGetReq projDocGetReq = new ProjDocGetReq();
        projDocGetReq.setId(projDocFolderPermissionSaveReq.getFolderId());
        projDocGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        ProjDocFolderPermissionResp projDocFolderPermissionResp = projDocumentService
                .getProjDocFolderPermissions(projDocGetReq);
        projDocFolderPermissionResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<ProjDocFolderPermissionResp>(projDocFolderPermissionResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.GET_FOLDER_PERMISSIONS, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderPermissionResp> getFolderPermissions(PermissionReq permissionReq) {
        ProjDocFolderPermissionResp resp = projDocumentService.getFolderPermissions(permissionReq);

        return new ResponseEntity<ProjDocFolderPermissionResp>(resp, HttpStatus.OK);
    }

    @GetMapping(value = ProjDocumentURLConstants.PROJ_DOC_DOWNLOAD_FILE)
    public ResponseEntity<byte[]> projDocDownloadFile(@RequestParam("fileId") Long fileId) throws IOException {
        ProjDocFileTO fileTo = projDocumentService.getProjDocFile(fileId);
        byte[] documentBody = fileTo.getFileContent();
        HttpHeaders header = new HttpHeaders();
        if (fileTo.getFileType() == null) {
            header.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        } else {
            header.set(HttpHeaders.CONTENT_TYPE, fileTo.getFileType());
        }
        header.set(HttpHeaders.CONTENT_DISPOSITION, "filename=" + fileTo.getName().replaceAll("[,;]", "_"));
        header.setContentLength(documentBody.length);
        return new ResponseEntity<>(documentBody, header, HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.PROJ_DOC_DELETE_FILE, method = RequestMethod.POST)
    public ResponseEntity<Void> projDocDeleteFile(@RequestBody ProjDocDeleteReq projDocDeleteReq) {
        projDocumentService.projDocDeleteFile(projDocDeleteReq);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = ProjDocumentURLConstants.PROJ_DOC_DELETE_FOLDER, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderResp> projDocDeleteFolder(
            @RequestBody ProjDocFolderDeactiveReq projDocFolderDeactiveReq) {
        projDocumentService.projDocDeleteFolder(projDocFolderDeactiveReq);
        ProjDocFolderResp projDocFolderResp = new ProjDocFolderResp();
        ProjDocGetReq projDocGetReq = new ProjDocGetReq();
        projDocGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projDocGetReq.setProjId(projDocFolderDeactiveReq.getProjId());
        projDocFolderResp = getProjDocFolders(projDocGetReq);
        projDocFolderResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<ProjDocFolderResp>(projDocFolderResp, HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_TEMPLATE_CATEGORIES, method = RequestMethod.POST)
    public TemplateCategoriesResp getTemplateCategories(@RequestBody TemplateCategoriesReq templateCategoriesReq) {
    	TemplateCategoriesResp templateCategoriesResp = new TemplateCategoriesResp();
    	templateCategoriesResp.getTemplateCategoriesTOs().addAll(projDocumentService.getTemplateCategories(templateCategoriesReq).getTemplateCategoriesTOs());
        return templateCategoriesResp;    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.SAVE_TEMPLATE_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<TemplateCategoriesResp> saveTemplateCategory( @RequestBody TemplateCategorySaveReq templateCategorySaveReq ) {
    	TemplateCategoriesResp templateCategoriesResp = new TemplateCategoriesResp();
    	templateCategoriesResp = projDocumentService.saveTemplateCategory( templateCategorySaveReq );
        return new ResponseEntity<TemplateCategoriesResp>( templateCategoriesResp , HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.UPDATE_TEMPLATE_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<TemplateCategoriesResp> updateTemplateCategory(@RequestBody TemplateCategoriesReq templateCategoriesReq) {
    	TemplateCategoriesResp templateCategoriesResp = new TemplateCategoriesResp();
    	projDocumentService.updateTemplateCategory( templateCategoriesReq );
        return new ResponseEntity<TemplateCategoriesResp>( templateCategoriesResp , HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_SAMPLE_TEMPLATES, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> getSampleTemplates( @RequestBody TemplatesGetReq templatesGetReq ) {
    	System.out.println("This is from getSampleTemplates function of MWProjDocumentController class");		
        return new ResponseEntity<TemplatesResp>( projDocumentService.getSampleTemplates( templatesGetReq ),
                HttpStatus.OK );    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_CENTRAL_TEMPLATES, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> getCentralTemplates( @RequestBody TemplatesGetReq templatesGetReq ) {
    	System.out.println("This is from getCentralTemplates function of MWProjDocumentController class");		
        return new ResponseEntity<TemplatesResp>( projDocumentService.getCentralTemplates( templatesGetReq ),
                HttpStatus.OK );    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJECT_TEMPLATES, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> getProjectTemplates( @RequestBody TemplatesGetReq templatesGetReq ) {
    	System.out.println("This is from getProjectTemplates function of MWProjDocumentController class");		
        return new ResponseEntity<TemplatesResp>( projDocumentService.getProjectTemplates( templatesGetReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJECT_FORMS, method = RequestMethod.POST)
    public ResponseEntity<ProjectFormsResp> getProjectForms( @RequestBody ProjectFormsGetReq projectFormsGetReq ) {
    	System.out.println("This is from getProjectForms function of ProjDocumentController class");		
        return new ResponseEntity<ProjectFormsResp>( projDocumentService.getProjectForms( projectFormsGetReq ),
                HttpStatus.OK );    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.SAVE_TEMPLATE, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> saveNewTemplate( @RequestBody TemplateSaveReq templateSaveReq ) {
    	System.out.println("This is from saveNewTemplate function of ProjDocumentController class");    	
    	TemplatesResp templatesResp = projDocumentService.saveNewTemplate( templateSaveReq );
        return new ResponseEntity<TemplatesResp>( templatesResp , HttpStatus.OK );    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.UPDATE_TEMPLATE, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> updateTemplate( @RequestBody TemplateSaveReq templateSaveReq ) {
    	System.out.println("This is from updateTemplate function of ProjDocumentController class");    	
    	projDocumentService.updateTemplate( templateSaveReq );
    	
    	TemplatesGetReq templatesGetReq = new TemplatesGetReq();
    	templatesGetReq.setTemplateCategoryId( templateSaveReq.getTemplateCategoryId() );
    	templatesGetReq.setCategoryMstrId( templateSaveReq.getCategoryMstrId() );
    	
    	TemplatesResp templatesResp = projDocumentService.getSampleTemplates( templatesGetReq );
    	templatesResp.cloneAppResp( CommonUtil.getSaveAppResp() );
        return new ResponseEntity<TemplatesResp>( templatesResp , HttpStatus.OK );    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.CLONE_TEMPLATE, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> cloneTemplate( @RequestBody TemplateSaveReq templateSaveReq ) {
    	System.out.println("This is from cloneTemplate function of ProjDocumentController class");
    	TemplatesResp templatesResp = projDocumentService.cloneTemplate( templateSaveReq );
        return new ResponseEntity<TemplatesResp>( templatesResp , HttpStatus.OK );    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.SEARCH_PROJECT_TEMPLATES, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> searchProjectTemplates( @RequestBody TemplatesGetReq templatesGetReq ) {
    	System.out.println("This is from searchProjectTemplates function of ProjDocumentController class");
    	TemplatesResp templatesResp = projDocumentService.searchProjectTemplates( templatesGetReq );
        return new ResponseEntity<TemplatesResp>( templatesResp , HttpStatus.OK );    	
    }
    
    // This is the create project form function
    @RequestMapping(value = ProjDocumentURLConstants.CREATE_PROJ_FORM, method = RequestMethod.POST)
    public ResponseEntity<ProjectFormsResp> createProjectForm( @RequestBody ProjectFormsGetReq projectFormsGetReq ) {
    	System.out.println("This is from createProjectForm function of ProjDocumentController class");
    	ProjectFormsResp projectFormsResp = projDocumentService.createProjectForm( projectFormsGetReq );
        return new ResponseEntity<ProjectFormsResp>( projectFormsResp , HttpStatus.OK );	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.TEMPLATE_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> templateApproval( @RequestBody TemplateSaveReq templateSaveReq ) {
    	System.out.println("This is from templateApproval function of ProjDocumentController class");
        return new ResponseEntity<TemplatesResp>( projDocumentService.templateApproval( templateSaveReq ), HttpStatus.OK );	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.TRANSFER_TEMPLATES, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> transferFromTemplates( @RequestBody TransferTemplatesGetReq transferTemplatesGetReq ) {
    	System.out.println("This is from transferFromTemplates function of ProjDocumentController class");
    	TemplatesResp templatesResp = projDocumentService.transferFromTemplates( transferTemplatesGetReq );
        return new ResponseEntity<TemplatesResp>( templatesResp , HttpStatus.OK );	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_SAMPLE_TEMPLS_LAST_INSERTED_ID, method = RequestMethod.POST)
    public ResponseEntity<Long> getSampleTemplsLastInsertedId( @RequestBody TemplatesGetReq templatesGetReq ) {
    	System.out.println("This is from getSampleTemplsLastInsertedId function of ProjDocumentController class");
        return new ResponseEntity<Long>( projDocumentService.getSampleTemplsLastInsertedId( templatesGetReq ), HttpStatus.OK );	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_CENTRAL_TEMPLS_LAST_INSERTED_ID, method = RequestMethod.POST)
    public ResponseEntity<Long> getCentralTemplsLastInsertedId( @RequestBody TemplatesGetReq templatesGetReq ) {
    	System.out.println("This is from getCentralTemplsLastInsertedId function of ProjDocumentController class");
        return new ResponseEntity<Long>( projDocumentService.getCentralTemplsLastInsertedId( templatesGetReq ) , HttpStatus.OK );	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_TEMPLS_LAST_INSERTED_ID, method = RequestMethod.POST)
    public ResponseEntity<Long> getProjTemplsLastInsertedId( @RequestBody TemplatesGetReq templatesGetReq ) {
    	System.out.println("This is from getProjTemplsLastInsertedId function of ProjDocumentController class");
        return new ResponseEntity<Long>( projDocumentService.getProjTemplsLastInsertedId( templatesGetReq ) , HttpStatus.OK );	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.SAVE_PROJECT_TEMPLATES_PROPOSAL, method = RequestMethod.POST)
    public ResponseEntity<TemplatesProposalResp> saveProjectTemplatesProposal( @RequestBody TemplateProposalReq templateProposalReq ) {
    	System.out.println("This is from saveProjectTemplatesProposal function of ProjDocumentController class");
    	TemplatesProposalResp templatesProposalResp = projDocumentService.saveProjectTemplatesProposal( templateProposalReq );
        return new ResponseEntity<TemplatesProposalResp>( templatesProposalResp , HttpStatus.OK );	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROPOSAL_LIST, method = RequestMethod.POST)
    public ResponseEntity<TemplatesProposalResp> getProposalList( @RequestBody TemplateProposalReq templateProposalReq ) {
    	System.out.println("This is from getProposalList function of ProjDocumentController class");
    	TemplatesProposalResp templatesProposalResp = projDocumentService.getProposalList( templateProposalReq );
        return new ResponseEntity<TemplatesProposalResp>( templatesProposalResp , HttpStatus.OK );	
    }
    
    @GetMapping(value = ProjDocumentURLConstants.DOWNLOAD_PROJECT_DOCS)
    public String[] downloadProjectDocs( @RequestParam("recordId") Long recordId ) throws IOException {
    	ProjDocFileTO fileTo = projDocumentService.downloadProjectDocs( recordId );
    	String file_info[] = { fileTo.getName(), fileTo.getFileSize(), fileTo.getFileType(), fileTo.getFolderPath() };
    	return file_info;
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_DOCUMENTS_BY_PROJ_ID, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFileResp> getProjDocsByProjectId( @RequestBody ProjDocGetReq projDocGetReq ) throws IOException {
    	System.out.println("This is from getProjDocsByProjectId function of ProjDocumentController class");
    	ProjDocFileResp projDocFileResp = projDocumentService.getProjDocsByProjectId( projDocGetReq );
        return new ResponseEntity<ProjDocFileResp>( projDocFileResp , HttpStatus.OK );	
    } 
    
    @RequestMapping(value = ProjDocumentURLConstants.UPDATE_PROJ_FORM, method = RequestMethod.POST)
    public ResponseEntity<ProjectFormsResp> updateProjectForm( @RequestBody ProjectFormsGetReq projectFormsGetReq ) {
    	System.out.println("This is from updateProjectForm function of ProjDocumentController class");
    	ProjectFormsResp projectFormsResp = projDocumentService.updateProjectForm( projectFormsGetReq );
        return new ResponseEntity<ProjectFormsResp>( projectFormsResp , HttpStatus.OK );	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.WORKFLOW_SUBMIT_FOR_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> workflowSubmitForApproval( @RequestBody TemplateSaveReq templateSaveReq ) {
    	System.out.println("This is from workflowSubmitForApproval function of ProjDocumentController class");
    	TemplatesResp templatesResp = projDocumentService.workflowSubmitForApproval( templateSaveReq );
        return new ResponseEntity<TemplatesResp>( templatesResp , HttpStatus.OK );	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.WORKFLOW_TEMPLATE_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> workflowTemplateApproval( @RequestBody TemplateSaveReq templateSaveReq ) {
    	System.out.println("This is from workflowTemplateApproval function of ProjDocumentController class");
    	TemplatesResp templatesResp = projDocumentService.workflowTemplateApproval( templateSaveReq );
        return new ResponseEntity<TemplatesResp>( templatesResp , HttpStatus.OK );	
    }
}