package com.rjtech.register.service.impl.emp;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.register.emp.dto.EmpMedicalHistoryTO;
import com.rjtech.register.emp.model.EmpMedicalHistoryEntity;
import com.rjtech.register.emp.req.EmpMedicalHistorySaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpMedicalHistoryResp;
import com.rjtech.register.repository.emp.EmpMedicalHistoryRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpMedicalHistoryService;
import com.rjtech.register.service.handler.emp.EmpMedicalHistoryHandler;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.constants.ApplicationConstants;

@Service(value = "empMedicalHistoryService")
@RJSService(modulecode = "empMedicalHistoryService")
@Transactional
public class EmpMedicalHistoryServiceImpl implements EmpMedicalHistoryService {

    @Autowired
    private EmpMedicalHistoryRepository empMedicalHistoryRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private EmpProjRegisterRepository empProjRegisterRepository;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    public EmpMedicalHistoryResp getEmpMedicalHistory( ProjEmpRegisterGetReq projEmpRegisterGetReq ) {
        EmpMedicalHistoryResp empMedicalHistoryResp = new EmpMedicalHistoryResp();
        List<EmpMedicalHistoryEntity> empMedicalHistoryEntites = empMedicalHistoryRepository
                .findEmpMedicalHistory(projEmpRegisterGetReq.getEmpId(), projEmpRegisterGetReq.getStatus());
        for (EmpMedicalHistoryEntity empMedicalHistoryEntity : empMedicalHistoryEntites) {
            empMedicalHistoryResp.getEmpMedicalHistoryTOs()
                    .add(EmpMedicalHistoryHandler.convertEntityToPOJO(empMedicalHistoryEntity));
        }
        return empMedicalHistoryResp;
    }

    /*public void saveEmpMedicalHistory(EmpMedicalHistorySaveReq empMedicalHistorySaveReq) {
    	System.out.println("saveEmpMedicalHistory function");
        EmpMedicalHistoryEntity empMedicalHistoryEntity = null;
        List<EmpMedicalHistoryEntity> empMedicalHistoryEntities = new ArrayList<>();
        for (EmpMedicalHistoryTO empMedicalHistoryTO : empMedicalHistorySaveReq.getEmpMedicalHistoryTOs()) {
            empMedicalHistoryEntity = EmpMedicalHistoryHandler.convertPOJOToEntity(empMedicalHistoryTO,
                    empRegisterRepository, empProjRegisterRepository);
            empMedicalHistoryEntities.add(empMedicalHistoryEntity);
        }
        List<EmpMedicalHistoryEntity> savedEntites = empMedicalHistoryRepository.save(empMedicalHistoryEntities);

        for (EmpMedicalHistoryEntity savedEntity : savedEntites) {
            if (CommonUtil.isBlankStr(savedEntity.getRecordId())) {
                String code = ModuleCodesPrefixes.MEDICAL_DOCUMENT.getDesc() + "-" + empMedicalHistorySaveReq.getCode()
                        + "-" + savedEntity.getId();
                savedEntity.setRecordId(code);
            }
        }
    }*/
    public void saveEmpMedicalHistory( MultipartFile[] files, EmpMedicalHistorySaveReq empMedicalHistorySaveReq ) throws IOException {
    	System.out.println("saveEmpMedicalHistory function from EmpMedicalHistoryServiceImpl");
    	System.out.println(empMedicalHistorySaveReq);
        EmpMedicalHistoryEntity empMedicalHistoryEntity = null;
        ProjDocFileEntity projDocFileEntity = null;
        ProjDocFileEntity resProjDocFileEntity = null;
        
        System.out.println("Folder category:"+empMedicalHistorySaveReq.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( empMedicalHistorySaveReq.getFolderCategory() );
        ProjDocFolderEntity projDocFolderEntity = null;
        UserMstrEntity userMstrEntity = null;
        String folder_path = folder.getUploadFolder();
        
        
        List<EmpMedicalHistoryEntity> empMedicalHistoryEntities = new ArrayList<>();
        boolean update = false;
        for( EmpMedicalHistoryTO empMedicalHistoryTO : empMedicalHistorySaveReq.getEmpMedicalHistoryTOs() ) {
        	Integer fileIndex = empMedicalHistoryTO.getFileObjectIndex();
        	UploadUtil uploadUtil = new UploadUtil();
        	userMstrEntity = new UserMstrEntity();
        	userMstrEntity.setUserId( empMedicalHistorySaveReq.getUserId() );
        	
			if ( fileIndex != null
					&& files[fileIndex].getOriginalFilename().equalsIgnoreCase( empMedicalHistoryTO.getFileName() ) ) {
								
				projDocFileEntity = new ProjDocFileEntity();
				projDocFolderEntity = new ProjDocFolderEntity();
				EmpProjRigisterEntity empProjRigisterEntity =  empProjRegisterRepository.findOne(empMedicalHistoryTO.getEmpProjId());
				projDocFolderEntity.setId( folder.getId() );
				
				projDocFileEntity.setName( empMedicalHistoryTO.getFileName() );
				projDocFileEntity.setFolderId( projDocFolderEntity );
				projDocFileEntity.setFileSize( uploadUtil.bytesIntoHumanReadable( files[fileIndex].getSize() ) );
				projDocFileEntity.setFileType( files[fileIndex].getContentType() );
				projDocFileEntity.setProjectId( empProjRigisterEntity.getProjMstrEntity() );		
				projDocFileEntity.setCreatedBy( userMstrEntity );
				projDocFileEntity.setUpdatedBy( userMstrEntity );
				projDocFileEntity.setVersion("1");
				projDocFileEntity.setFolderPath( "/"+String.valueOf( empProjRigisterEntity.getProjMstrEntity().getProjectId() )+"/"+ String.valueOf( empMedicalHistoryTO.getEmpRegId() ) );
				projDocFileEntity.setStatus( 1 );
				resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
				
				if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
				{
					System.out.println("if block of upload condition");
					String extras[] = { String.valueOf( empProjRigisterEntity.getProjMstrEntity().getProjectId() ), String.valueOf( empMedicalHistoryTO.getEmpRegId() ) };
					// Upload pre-contract docs to server				
					uploadUtil.uploadFile( files[fileIndex], empMedicalHistorySaveReq.getFolderCategory(), folder_path, extras );
				}
			}
            empMedicalHistoryEntity = EmpMedicalHistoryHandler.convertPOJOToEntity( empMedicalHistoryTO,
                    empRegisterRepository, empProjRegisterRepository, resProjDocFileEntity );
            empMedicalHistoryEntities.add(empMedicalHistoryEntity);
        }
        List<EmpMedicalHistoryEntity> savedEntites = empMedicalHistoryRepository.save( empMedicalHistoryEntities );

        for (EmpMedicalHistoryEntity savedEntity : savedEntites) {
            if (CommonUtil.isBlankStr(savedEntity.getRecordId())) {
                String code = ModuleCodesPrefixes.MEDICAL_DOCUMENT.getDesc() + "-" + empMedicalHistorySaveReq.getCode()
                        + "-" + savedEntity.getId();
                savedEntity.setRecordId(code);
            }
        }
    }
}
