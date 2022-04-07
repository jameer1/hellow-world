package com.rjtech.mw.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.req.ProjUsersReq;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.ActionCodes;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.ModuleCodes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.document.constants.ProjDocumentURLConstants;
import com.rjtech.document.dto.ProjDocFolderPermissionTO;
import com.rjtech.document.req.PermissionReq;
import com.rjtech.document.req.ProjDocDeleteReq;
import com.rjtech.document.req.ProjDocFileSaveReq;
import com.rjtech.document.req.ProjDocFolderDeactiveReq;
import com.rjtech.document.req.ProjDocFolderPermissionSaveReq;
import com.rjtech.document.req.ProjDocFolderSaveReq;
import com.rjtech.document.req.ProjDocGetReq;
import com.rjtech.document.req.TransferTemplatesGetReq;
import com.rjtech.document.resp.ProjDocFileResp;
import com.rjtech.document.resp.ProjDocFolderPermissionResp;
import com.rjtech.document.resp.ProjDocFolderResp;
import com.rjtech.mw.service.common.MWCommonService;
import com.rjtech.mw.service.document.MWProjDocumentService;

import com.rjtech.document.req.TemplateCategoriesReq;
import com.rjtech.document.req.TemplatesGetReq;
import com.rjtech.document.req.ProjectFormsGetReq;
import com.rjtech.document.req.TemplateCategorySaveReq;
import com.rjtech.document.req.TemplateSaveReq;
import com.rjtech.document.req.TemplateProposalReq;

import com.rjtech.document.resp.TemplateCategoriesResp;
import com.rjtech.document.resp.TemplatesResp;
import com.rjtech.document.resp.ProjectFormsResp;
import com.rjtech.document.resp.TemplatesProposalResp;

@RestController
@RequestMapping(ProjDocumentURLConstants.DOCUMENT_PARH_URL)
public class MWProjDocumentController {

    @Autowired
    private MWProjDocumentService mwProjDocumentService;

    @Autowired
    private MWCommonService mwCommonService;    
   

    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_DOC, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderResp> getProjDoc(@RequestBody ProjDocGetReq projDocGetReq) {
        return new ResponseEntity<ProjDocFolderResp>(mwProjDocumentService.getProjDoc(projDocGetReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_DOC_FOLDERS, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderResp> getProjDocFolders(@RequestBody ProjDocGetReq projDocGetReq) {
        return new ResponseEntity<ProjDocFolderResp>(mwProjDocumentService.getProjDocFolders(projDocGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_DOC_FILES_BY_FOLDER, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFileResp> getProjDocFilesByFolder(@RequestBody ProjDocGetReq projDocGetReq) {
        return new ResponseEntity<ProjDocFileResp>(mwProjDocumentService.getProjDocFilesByFolder(projDocGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_DOC_FOLDER_PERMISSIONS, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderPermissionResp> getProjDocFolderPermissions(
            @RequestBody ProjDocGetReq projDocGetReq) {
        ProjDocFolderPermissionResp projDocFolderPermissionResp = mwProjDocumentService
                .getProjDocFolderPermissions(projDocGetReq);
        ProjUsersReq projUsersReq = new ProjUsersReq();
        projUsersReq.setModuleCode(ModuleCodes.DOCUMENT.getDesc());
        projUsersReq.setActionCode(ActionCodes.VIEW.getDesc());
        projUsersReq.setStatus(projDocGetReq.getStatus());
        projUsersReq.setProjId(projDocGetReq.getProjId());
        projUsersReq.setPermission(projDocGetReq.getPermission());
        ProjDocFolderPermissionTO projDocFolderPermissionTO = null;
        LabelKeyTOResp labelKeyTOResp = mwCommonService.getAllProjUsers(projUsersReq);
        for (LabelKeyTO labelKeyTO : labelKeyTOResp.getLabelKeyTOs()) {
            if (projDocFolderPermissionResp.getProjUserClassMap() != null
                    && projDocFolderPermissionResp.getProjUserClassMap().get(labelKeyTO.getId()) != null) {
                projDocFolderPermissionTO = projDocFolderPermissionResp.getProjUserClassMap().get(labelKeyTO.getId());
                projDocFolderPermissionTO.setLabelKeyTO(labelKeyTO);
            } else {
                projDocFolderPermissionTO = new ProjDocFolderPermissionTO();
                projDocFolderPermissionTO.setUserId(labelKeyTO.getId());
                projDocFolderPermissionTO.setLabelKeyTO(labelKeyTO);
                projDocFolderPermissionTO.setFolderId(projDocGetReq.getId());
                projDocFolderPermissionTO.setStatus(StatusCodes.ACTIVE.getValue());
            }
            projDocFolderPermissionResp.getProjDocFolderPermissionTOs().add(projDocFolderPermissionTO);
        }
        return new ResponseEntity<ProjDocFolderPermissionResp>(projDocFolderPermissionResp, HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.SAVE_PROJ_DOC_FOLDERS, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderResp> saveProjDocFolders(
            @RequestBody ProjDocFolderSaveReq projDocFolderSaveReq) {
        return new ResponseEntity<ProjDocFolderResp>(mwProjDocumentService.saveProjDocFolders(projDocFolderSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.SAVE_PROJ_DOC_FILES_BY_FOLDER, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFileResp> saveProjDocFilesByFolder(MultipartFile[] files,
            String projDocFileSaveReqStr) {
        ProjDocFileSaveReq projDocFileSaveReq = AppUtils.fromJson(projDocFileSaveReqStr, ProjDocFileSaveReq.class);
        return new ResponseEntity<ProjDocFileResp>(
                mwProjDocumentService.saveProjDocFilesByFolder(files, projDocFileSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.SAVE_PROJ_DOC_FOLDER_PERMISSIONS, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderPermissionResp> saveProjDocFolderPermissions(
            @RequestBody ProjDocFolderPermissionSaveReq projDocFolderPermissionSaveReq) {
        return new ResponseEntity<ProjDocFolderPermissionResp>(
                mwProjDocumentService.saveProjDocFolderPermissions(projDocFolderPermissionSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = ProjDocumentURLConstants.GET_FOLDER_PERMISSIONS, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderPermissionResp> getFolderPermissions(@RequestBody PermissionReq permissionReq) {
        return new ResponseEntity<ProjDocFolderPermissionResp>(
                mwProjDocumentService.getFolderPermissions(permissionReq), HttpStatus.OK);
    }

    @GetMapping(value = ProjDocumentURLConstants.PROJ_DOC_DOWNLOAD_FILE)
    public ResponseEntity<byte[]> projDocDownloadFile(@RequestParam("fileId") Long fileId) {
        return mwProjDocumentService.getProjDocFile(fileId);
    }

    @RequestMapping(value = ProjDocumentURLConstants.PROJ_DOC_DELETE_FILE, method = RequestMethod.POST)
    public ResponseEntity<Void> projDocDeleteFile(@RequestBody ProjDocDeleteReq projDocDeleteReq) {
        ProjDocDeleteReq projDocDeleteReqTwo = AppUtils.fromJson(AppUtils.toJson(projDocDeleteReq),
                ProjDocDeleteReq.class);
        mwProjDocumentService.projDocDeleteFile(projDocDeleteReqTwo);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
       
    @RequestMapping(value = ProjDocumentURLConstants.PROJ_DOC_DELETE_FOLDER, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFolderResp> projDocDeleteFolder(@RequestBody ProjDocFolderDeactiveReq projDocFolderDeactiveReq) {
        return new ResponseEntity<ProjDocFolderResp>(
                mwProjDocumentService.projDocDeleteFolder(projDocFolderDeactiveReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_TEMPLATE_CATEGORIES, method = RequestMethod.POST)
    public ResponseEntity<TemplateCategoriesResp> getTemplateCategories( @RequestBody TemplateCategoriesReq templateCategoriesReq ) {
    	System.out.println("This is from getTemplateCategories function of MWProjDocumentController class");		
        return new ResponseEntity<TemplateCategoriesResp>( mwProjDocumentService.getTemplateCategories( templateCategoriesReq ),
                HttpStatus.OK );    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.SAVE_TEMPLATE_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<TemplateCategoriesResp> saveTemplateCategory( @RequestBody TemplateCategorySaveReq templateCategorySaveReq ) {
    	System.out.println("This is from saveTemplateCategory function of MWProjDocumentController class");		
        return new ResponseEntity<TemplateCategoriesResp>( mwProjDocumentService.saveTemplateCategory( templateCategorySaveReq ),
                HttpStatus.OK );    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.UPDATE_TEMPLATE_CATEGORY, method = RequestMethod.POST)
    public ResponseEntity<TemplateCategoriesResp> updateTemplateCategory( @RequestBody TemplateCategoriesReq templateCategoriesReq ) {
    	System.out.println("This is from saveTemplateCategory function of MWProjDocumentController class");		
        return new ResponseEntity<TemplateCategoriesResp>( mwProjDocumentService.updateTemplateCategory( templateCategoriesReq ),
                HttpStatus.OK );    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_SAMPLE_TEMPLATES, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> getSampleTemplates(@RequestBody TemplatesGetReq templatesGetReq) {
    	System.out.println("This is from getSampleTemplates function of MWProjDocumentController class");		
        return new ResponseEntity<TemplatesResp>( mwProjDocumentService.getSampleTemplates( templatesGetReq ),
                HttpStatus.OK );    	
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_CENTRAL_TEMPLATES, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> getCentralTemplates(@RequestBody TemplatesGetReq templatesGetReq) {
    	System.out.println("This is from getCentralTemplates function of MWProjDocumentController class");		
        return new ResponseEntity<TemplatesResp>( mwProjDocumentService.getCentralTemplates( templatesGetReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJECT_TEMPLATES, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> getProjectTemplates(@RequestBody TemplatesGetReq templatesGetReq) {
    	System.out.println("This is from getProjectTemplates function of MWProjDocumentController class");		
        return new ResponseEntity<TemplatesResp>( mwProjDocumentService.getProjectTemplates( templatesGetReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJECT_FORMS, method = RequestMethod.POST)
    public ResponseEntity<ProjectFormsResp> getProjectForms(@RequestBody ProjectFormsGetReq projectFormsGetReq) {
    	System.out.println("This is from getProjectForms function of MWProjDocumentController class");		
        return new ResponseEntity<ProjectFormsResp>( mwProjDocumentService.getProjectForms( projectFormsGetReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.SAVE_TEMPLATE, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> saveNewTemplate( @RequestBody TemplateSaveReq templateSaveReq ) {	
        return new ResponseEntity<TemplatesResp>( mwProjDocumentService.saveNewTemplate( templateSaveReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.UPDATE_TEMPLATE, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> updateTemplate( @RequestBody TemplateSaveReq templateSaveReq ) {	
        return new ResponseEntity<TemplatesResp>( mwProjDocumentService.updateTemplate( templateSaveReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.CLONE_TEMPLATE, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> cloneTemplate( @RequestBody TemplateSaveReq templateSaveReq ) {	
        return new ResponseEntity<TemplatesResp>( mwProjDocumentService.cloneTemplate( templateSaveReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.SEARCH_PROJECT_TEMPLATES, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> searchProjectTemplates( @RequestBody TemplatesGetReq templatesGetReq ) {	
        return new ResponseEntity<TemplatesResp>( mwProjDocumentService.searchProjectTemplates( templatesGetReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.CREATE_PROJ_FORM, method = RequestMethod.POST)
    public ResponseEntity<ProjectFormsResp> createProjectForm( @RequestBody ProjectFormsGetReq projectFormsGetReq ) {	
        return new ResponseEntity<ProjectFormsResp>( mwProjDocumentService.createProjectForm( projectFormsGetReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.TEMPLATE_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> templateApproval( @RequestBody TemplateSaveReq templateSaveReq ) {
        return new ResponseEntity<TemplatesResp>( mwProjDocumentService.templateApproval( templateSaveReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.TRANSFER_TEMPLATES, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> transferFromTemplates( @RequestBody TransferTemplatesGetReq transferTemplatesGetReq ) {	
    	mwProjDocumentService.transferFromTemplates( transferTemplatesGetReq );
        return new ResponseEntity<TemplatesResp>( HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_SAMPLE_TEMPLS_LAST_INSERTED_ID, method = RequestMethod.POST)
    public ResponseEntity<Long> getSampleTemplsLastInsertedId( @RequestBody TemplatesGetReq templatesGetReq ) {
    	Long templateId = mwProjDocumentService.getSampleTemplsLastInsertedId( templatesGetReq );
        return new ResponseEntity<Long>( templateId , HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_CENTRAL_TEMPLS_LAST_INSERTED_ID, method = RequestMethod.POST)
    public ResponseEntity<Long> getCentralTemplsLastInsertedId( @RequestBody TemplatesGetReq templatesGetReq ) {
        return new ResponseEntity<Long>( mwProjDocumentService.getCentralTemplsLastInsertedId( templatesGetReq ) , HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_TEMPLS_LAST_INSERTED_ID, method = RequestMethod.POST)
    public ResponseEntity<Long> getProjectTemplsLastInsertedId( @RequestBody TemplatesGetReq templatesGetReq ) {
        return new ResponseEntity<Long>( mwProjDocumentService.getProjTemplsLastInsertedId( templatesGetReq ) , HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.SAVE_PROJECT_TEMPLATES_PROPOSAL, method = RequestMethod.POST)
    public ResponseEntity<TemplatesProposalResp> saveProjectTemplatesProposal( @RequestBody TemplateProposalReq templateProposalReq ) {
    	System.out.println("This is from saveProjectTemplatesProposal function of ProjDocumentController class");
    	return new ResponseEntity<TemplatesProposalResp>( mwProjDocumentService.saveProjectTemplatesProposal( templateProposalReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.GET_PROPOSAL_LIST, method = RequestMethod.POST)
    public ResponseEntity<TemplatesProposalResp> getProposalList( @RequestBody TemplateProposalReq templateProposalReq ) {
    	System.out.println("This is from getProposalList function of ProjDocumentController class");
    	return new ResponseEntity<TemplatesProposalResp>( mwProjDocumentService.getProposalList( templateProposalReq ), HttpStatus.OK );
    }
    
    @GetMapping(value = ProjDocumentURLConstants.DOWNLOAD_PROJECT_DOCS)
    public ResponseEntity<ByteArrayResource> downloadProjectDocs( @RequestParam("recordId") Long recordId ) {
        return mwProjDocumentService.downloadProjectDocs( recordId );
    }

    @RequestMapping(value = ProjDocumentURLConstants.GET_PROJ_DOCUMENTS_BY_PROJ_ID, method = RequestMethod.POST)
    public ResponseEntity<ProjDocFileResp> getProjDocumentsByProjectId( @RequestBody ProjDocGetReq projDocGetReq ) {
    	System.out.println("This is from getProjDocumentsByProjectId function of MWProjDocumentController class");
    	return new ResponseEntity<ProjDocFileResp>( mwProjDocumentService.getProjDocumentsByProjectId( projDocGetReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.UPDATE_PROJ_FORM, method = RequestMethod.POST)
    public ResponseEntity<ProjectFormsResp> updateProjectForm( @RequestBody ProjectFormsGetReq projectFormsGetReq ) {
        return new ResponseEntity<ProjectFormsResp>( mwProjDocumentService.updateProjectForm( projectFormsGetReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.WORKFLOW_SUBMIT_FOR_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> workflowSubmitForApproval( @RequestBody TemplateSaveReq templateSaveReq ) {
        return new ResponseEntity<TemplatesResp>( mwProjDocumentService.workflowSubmitForApproval( templateSaveReq ),
                HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjDocumentURLConstants.WORKFLOW_TEMPLATE_APPROVAL, method = RequestMethod.POST)
    public ResponseEntity<TemplatesResp> workflowTemplateApproval( @RequestBody TemplateSaveReq templateSaveReq ) {
    	return new ResponseEntity<TemplatesResp>( mwProjDocumentService.workflowTemplateApproval( templateSaveReq ),
                HttpStatus.OK );
    }
}