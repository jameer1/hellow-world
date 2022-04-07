package com.rjtech.register.service.impl.emp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.rjtech.rjs.core.annotations.RJSService;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.register.service.emp.EmpDocsCommonService;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.register.repository.emp.EmpDocumentsRepository;
import com.rjtech.register.emp.model.EmpDocumentsEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.dto.EmpDocumentsTO;

@Service(value = "empDocsCommonService")
@RJSService(modulecode = "empDocsCommonService")
@Transactional
public class EmpDocsCommonServiceImpl implements EmpDocsCommonService {

    private static final Logger log = LoggerFactory.getLogger(EmpDocsCommonServiceImpl.class);

    @Autowired
	private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
	private UploadUtil uploadUtil;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;
    
    @Autowired
    private EmpDocumentsRepository empDocumentsRepository;

    public void saveEmployeeDocs( MultipartFile[] files, EmpRegisterReq empRegisterReq ) throws IOException {
		ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( empRegisterReq.getFolderCategory() );		
		String parent_folder_path = folder.getUploadFolder();
		System.out.println("UploadFolder:"+folder.getUploadFolder());
		System.out.println("projdocfolder entity id:"+folder.getId());
		
		System.out.println(folder);
		System.out.println("category name:"+empRegisterReq.getFolderCategory());
		System.out.println("empRegisterReq:");
		System.out.println(empRegisterReq);
		
		ProjDocFileEntity projDocFileEntity = null;
		EmpDocumentsEntity empDocumentsEntity = null;		
		EmpRegisterDtlEntity empRegisterDtlEntity = new EmpRegisterDtlEntity();	
		empRegisterDtlEntity.setId( empRegisterReq.getEmpId() );
		EmpProjRigisterEntity empProjRigisterEntity = new EmpProjRigisterEntity();	
		empProjRigisterEntity.setId( empRegisterReq.getEmpProjId() );
		
		for( ProjDocFileTO projDocFileTO : empRegisterReq.getProjDocFileTOs() ) {
			// We are passing multiple files as an array, to find which file is belongs to
			// which object based on fileName and index
			Integer fileIndex = projDocFileTO.getFileObjectIndex();
			if ( fileIndex != null && files[fileIndex].getOriginalFilename().equalsIgnoreCase( projDocFileTO.getName() ) ) {
				ProjDocFolderEntity projDocFolderEntity = new ProjDocFolderEntity();
				projDocFileEntity = new ProjDocFileEntity();
				empDocumentsEntity = new EmpDocumentsEntity();
				
				ProjMstrEntity projMstrEntity = new ProjMstrEntity();
				projMstrEntity.setProjectId( empRegisterReq.getProjId() );
				
				projDocFolderEntity.setId( folder.getId() );
				projDocFileEntity.setFolderId( projDocFolderEntity );			
				projDocFileEntity.setName( projDocFileTO.getName() );
				projDocFileEntity.setVersion("1");
				projDocFileEntity.setFileType(projDocFileTO.getFileType());
				projDocFileEntity.setFileSize(String.valueOf(projDocFileTO.getFileSize()));
				projDocFileEntity.setStatus(1);
				projDocFileEntity.setDescription(projDocFileTO.getDescription());
				projDocFileEntity.setProjectId( projMstrEntity );				
				projDocFileEntity.setFolderPath( "/"+String.valueOf( empRegisterReq.getProjId() )+"/"+String.valueOf( empRegisterReq.getEmpId() ) );				
				projDocFileTO.setMultipartFile( files[fileIndex] );
			}
			
			if(projDocFileEntity != null) {
				ProjDocFileEntity resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
				if(projDocFileTO.getEmpDocumentId() != null) {
					empDocumentsEntity.setId( projDocFileTO.getEmpDocumentId() );
				}
				empDocumentsEntity.setDocumentCategory( empRegisterReq.getFolderCategory() );
				empDocumentsEntity.setStatus( 1 );
				empDocumentsEntity.setProjDocFileEntity( resProjDocFileEntity );
				empDocumentsEntity.setEmpRegisterDtlEntity( empRegisterDtlEntity );
				empDocumentsEntity.setEmpProjRigisterEntity( empProjRigisterEntity );
				empDocumentsRepository.save( empDocumentsEntity );	
				if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
				{
					// Upload employee docs to server		        
			        String[] extras = { String.valueOf(empRegisterReq.getProjId()), String.valueOf(empRegisterReq.getEmpId()) };
			        if (projDocFileTO != null) {
			        	System.out.println("if condition of upload");
			        	uploadUtil.uploadFile(projDocFileTO.getMultipartFile(), empRegisterReq.getFolderCategory(), parent_folder_path, extras );
			        }
				}
			}			
			
		}
    }
    
    public EmpRegisterResp getEmployeeDocs( EmpRegisterReq empRegisterReq ) {
    	EmpRegisterResp empRegisterResp = new EmpRegisterResp();
    	List<EmpDocumentsEntity> empDocumentsEntities = empDocumentsRepository.findEmpDocuments( empRegisterReq.getEmpId(), empRegisterReq.getEmpProjId(), 1, empRegisterReq.getFolderCategory() );
    	System.out.println("empDocumentsEntities size:"+empDocumentsEntities.size());
    	System.out.println("empRegisterReq:"+empRegisterReq.getEmpId()+","+empRegisterReq.getEmpProjId());
    	
    	for( EmpDocumentsEntity empDocumentsEntity : empDocumentsEntities ) {
    		ProjDocFileEntity empProjDocFileEntity = empDocumentsEntity.getProjDocFileEntity();    		
    		System.out.println("empProjDocFIleEntity:");
    		System.out.println(empProjDocFileEntity);
    		ProjDocFileEntity projDocFileEntity = projDocFileRepository.findOne( empProjDocFileEntity.getId() );
    		
    		//ProjDocFolderEntity projDocFolderEntity = new ProjDocFolderEntity();
    		ProjDocFileTO projDocFileTO = new ProjDocFileTO();
    		//EmpDocumentsTO empDocumentsTO = new EmpDocumentsTO();
    		
            projDocFileTO.setId( projDocFileEntity.getId() );
            //projDocFileTO.setCode( generateCode(projDocFileEntity) );
            projDocFileTO.setName( projDocFileEntity.getName() );
            projDocFileTO.setFileType( projDocFileEntity.getFileType() );
            projDocFileTO.setFileSize( projDocFileEntity.getFileSize() );
            projDocFileTO.setVersion( projDocFileEntity.getVersion() );
            projDocFileTO.setUpdatedOn( projDocFileEntity.getUpdatedOn() );
            projDocFileTO.setCreatedOn( projDocFileEntity.getCreatedOn() );
            projDocFileTO.setUpdatedBy( projDocFileEntity.getUpdatedBy().getUserName() );
            projDocFileTO.setFileStatus( projDocFileEntity.getFileStatus() );
            projDocFileTO.setStatus( projDocFileEntity.getStatus() );
            projDocFileTO.setFolderId( projDocFileEntity.getFolderId().getId() );
            projDocFileTO.setCreatedBy( projDocFileEntity.getCreatedBy().getUserName() );
            projDocFileTO.setCategory( projDocFileEntity.getCategory() );
            projDocFileTO.setDescription( projDocFileEntity.getDescription() );
            projDocFileTO.setEmpDocumentId( empDocumentsEntity.getId() );
            
            //empDocumentsTO.setDocumentId( empDocumentsEntity.getId() );
            //empDocumentsTO.setProjDocFileTO( projDocFileTO );
            
            empRegisterResp.getProjDocFileTOs().add( projDocFileTO );
            //empRegisterResp.getEmpDocumentsTOs.add( empDocumentsTO );
    	}
    	return empRegisterResp;
    }
}
