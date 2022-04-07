package com.rjtech.mw.service.impl.document;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.document.constants.ProjDocumentURLConstants;
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
import com.rjtech.mw.service.document.MWProjDocumentService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

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
import org.springframework.core.io.ByteArrayResource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.io.IOException;

@Service(value = "mwProjDocumentService")
@RJSService(modulecode = "mwProjDocumentService")
@Transactional
public class MWProjDocumentServiceImpl extends RestConfigServiceImpl implements MWProjDocumentService {

	 public ProjDocFolderResp getProjDoc(ProjDocGetReq projDocGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_PROJ_DOC);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projDocGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjDocFolderResp.class);
	}
	 
    public ProjDocFolderResp getProjDocFolders(ProjDocGetReq projDocGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_PROJ_DOC_FOLDERS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projDocGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjDocFolderResp.class);
    }

    public ProjDocFileResp getProjDocFilesByFolder(ProjDocGetReq projDocGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_PROJ_DOC_FILES_BY_FOLDER);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projDocGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjDocFileResp.class);

    }

    public ProjDocFolderPermissionResp getProjDocFolderPermissions(ProjDocGetReq projDocGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_PROJ_DOC_FOLDER_PERMISSIONS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projDocGetReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjDocFolderPermissionResp.class);

    }

    public ProjDocFolderResp saveProjDocFolders(ProjDocFolderSaveReq projDocFolderSaveReq) {

        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.SAVE_PROJ_DOC_FOLDERS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projDocFolderSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjDocFolderResp.class);

    }

    public ProjDocFileResp saveProjDocFilesByFolder(MultipartFile[] files, ProjDocFileSaveReq projDocFileSaveReq) {

        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.SAVE_PROJ_DOC_FILES_BY_FOLDER);
        strResponse = constructPOSTRestTemplateWithMultipartFiles(url, files, AppUtils.toJson(projDocFileSaveReq),
                "projDocFileSaveReqStr");
        return AppUtils.fromJson(strResponse.getBody(), ProjDocFileResp.class);

    }

    public ProjDocFolderPermissionResp saveProjDocFolderPermissions(
            ProjDocFolderPermissionSaveReq projDocFolderPermissionSaveReq) {

        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.SAVE_PROJ_DOC_FOLDER_PERMISSIONS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projDocFolderPermissionSaveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjDocFolderPermissionResp.class);

    }

    public ProjDocFolderPermissionResp getFolderPermissions(PermissionReq permissionReq) {

        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_FOLDER_PERMISSIONS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(permissionReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjDocFolderPermissionResp.class);

    }

    public ResponseEntity<byte[]> getProjDocFile(Long fileId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("fileId", fileId);
        String url = AppUtils.getUrl(
                getCentralLibExchangeUrl(
                        ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.PROJ_DOC_DOWNLOAD_FILE),
                paramsMap);
        return constructGETRestTemplate(url, byte[].class);
    }

    public ResponseEntity<Void> projDocDeleteFile(ProjDocDeleteReq projDocDeleteReq) {
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.PROJ_DOC_DELETE_FILE);
        ResponseEntity<Void> response = constructPOSTRestTemplate(url, AppUtils.toJson(projDocDeleteReq), Void.class);
        return response;
    }    
   

    public ProjDocFolderResp projDocDeleteFolder(ProjDocFolderDeactiveReq projDocFolderDeactiveReq) {  
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.PROJ_DOC_DELETE_FOLDER);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(projDocFolderDeactiveReq));
        return AppUtils.fromJson(strResponse.getBody(), ProjDocFolderResp.class);        
    }
        
    public TemplateCategoriesResp getTemplateCategories( TemplateCategoriesReq templateCategoriesReq ) {
    	System.out.println("This is from getTemplateCategories function");
    	ResponseEntity<String> strResponse = null;
    	String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_TEMPLATE_CATEGORIES);
    	System.out.println("This is from MWProjDocumentServiceImpl class and url is:"+url);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(templateCategoriesReq));
        return AppUtils.fromJson(strResponse.getBody(), TemplateCategoriesResp.class);
    }
    
    public TemplateCategoriesResp saveTemplateCategory( TemplateCategorySaveReq templateCategorySaveReq )
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.SAVE_TEMPLATE_CATEGORY);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(templateCategorySaveReq));
        return AppUtils.fromJson(strResponse.getBody(), TemplateCategoriesResp.class);
    }
    
    public TemplateCategoriesResp updateTemplateCategory( TemplateCategoriesReq templateCategoriesReq) {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.UPDATE_TEMPLATE_CATEGORY);
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templateCategoriesReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplateCategoriesResp.class );
    }
    
    public TemplatesResp getSampleTemplates( TemplatesGetReq templatesGetReq )
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_SAMPLE_TEMPLATES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(templatesGetReq));
        return AppUtils.fromJson(strResponse.getBody(), TemplatesResp.class);
    }
    
    public TemplatesResp getCentralTemplates( TemplatesGetReq templatesGetReq )
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_CENTRAL_TEMPLATES );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templatesGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesResp.class );
    }
    
    public TemplatesResp getProjectTemplates( TemplatesGetReq templatesGetReq )
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_PROJECT_TEMPLATES );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templatesGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesResp.class );
    }
    
    public ProjectFormsResp getProjectForms( ProjectFormsGetReq projectFormsGetReq )
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_PROJECT_FORMS );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projectFormsGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ProjectFormsResp.class );
    }
    public TemplatesResp saveNewTemplate( TemplateSaveReq templateSaveReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.SAVE_TEMPLATE );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templateSaveReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesResp.class );
    }
    
    public TemplatesResp updateTemplate( TemplateSaveReq templateSaveReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.UPDATE_TEMPLATE );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templateSaveReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesResp.class );
    }
    
    public TemplatesResp cloneTemplate( TemplateSaveReq templateSaveReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.CLONE_TEMPLATE );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templateSaveReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesResp.class );
    }
    
    public TemplatesResp searchProjectTemplates( TemplatesGetReq templatesGetReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.SEARCH_PROJECT_TEMPLATES );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templatesGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesResp.class );
    }
    
    public ProjectFormsResp createProjectForm( ProjectFormsGetReq projectFormsGetReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.CREATE_PROJ_FORM );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projectFormsGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ProjectFormsResp.class );
    }
    
    public TemplatesResp templateApproval( TemplateSaveReq templateSaveReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.TEMPLATE_APPROVAL );
        //ResponseEntity<Void> response = constructPOSTRestTemplate( url, AppUtils.toJson( templateSaveReq ) , Void.class );
        //return response;
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templateSaveReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesResp.class );
    }
    
    public TemplatesResp transferFromTemplates( TransferTemplatesGetReq transferTemplatesGetReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.TRANSFER_TEMPLATES );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( transferTemplatesGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesResp.class );
    }
    
    public Long getSampleTemplsLastInsertedId( TemplatesGetReq templatesGetReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_SAMPLE_TEMPLS_LAST_INSERTED_ID );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templatesGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), Long.class );
    }
    
    public Long getCentralTemplsLastInsertedId( TemplatesGetReq templatesGetReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_CENTRAL_TEMPLS_LAST_INSERTED_ID );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templatesGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), Long.class );
    }
    
    public Long getProjTemplsLastInsertedId( TemplatesGetReq templatesGetReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_PROJ_TEMPLS_LAST_INSERTED_ID );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templatesGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), Long.class );
    }
    
    public TemplatesProposalResp saveProjectTemplatesProposal( TemplateProposalReq templateProposalReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.SAVE_PROJECT_TEMPLATES_PROPOSAL );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templateProposalReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesProposalResp.class );
    }
    
    public TemplatesProposalResp getProposalList( TemplateProposalReq templateProposalReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_PROPOSAL_LIST );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templateProposalReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesProposalResp.class );
    }
    
    public ResponseEntity<ByteArrayResource> downloadProjectDocs( Long recordId ) {
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
	    paramsMap.put("recordId", recordId);
	    ResponseEntity<String[]> response = null;
	    String url = AppUtils.getUrl(
	            getCentralLibExchangeUrl(
	                    ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.DOWNLOAD_PROJECT_DOCS),
	            paramsMap);
	    response = constructGETRestTemplate( url, String[].class );
	    String type="";
	    Path fileName = null;
	    String[] res = response.getBody();
	    String fileBasePath = res[3];
        byte[] fileBytes = null;
        try
        {
        	Path path = Paths.get(fileBasePath);
    	    fileBytes = Files.readAllBytes(path);    	    
    	    type = Files.probeContentType(path);
            fileName = path.getFileName();             
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
	    return ResponseEntity.ok().contentType(MediaType.parseMediaType(type))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(fileBytes));
    }
    
    public ProjDocFileResp getProjDocumentsByProjectId( ProjDocGetReq projDocGetReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(
                ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.GET_PROJ_DOCUMENTS_BY_PROJ_ID );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projDocGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ProjDocFileResp.class );
    }
    
    public ProjectFormsResp updateProjectForm( ProjectFormsGetReq projectFormsGetReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl( ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.UPDATE_PROJ_FORM );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( projectFormsGetReq ) );
        return AppUtils.fromJson( strResponse.getBody(), ProjectFormsResp.class );
    }
    
    public TemplatesResp workflowSubmitForApproval( TemplateSaveReq templateSaveReq ) 
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl( ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.WORKFLOW_SUBMIT_FOR_APPROVAL );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templateSaveReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesResp.class );
    }
    
    public TemplatesResp workflowTemplateApproval( TemplateSaveReq templateSaveReq )
    {
    	ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl( ProjDocumentURLConstants.DOCUMENT_PARH_URL + ProjDocumentURLConstants.WORKFLOW_TEMPLATE_APPROVAL );
        strResponse = constructPOSTRestTemplate( url, AppUtils.toJson( templateSaveReq ) );
        return AppUtils.fromJson( strResponse.getBody(), TemplatesResp.class );
    }
}
