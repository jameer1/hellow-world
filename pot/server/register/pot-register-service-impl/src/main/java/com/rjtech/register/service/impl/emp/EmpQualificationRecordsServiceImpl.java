package com.rjtech.register.service.impl.emp;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.rjtech.rjs.core.annotations.RJSService;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.register.emp.dto.EmpQualificationRecordsTO;
import com.rjtech.register.emp.model.EmpQualificationRecordsEntity;
import com.rjtech.register.emp.req.EmpQualificationRecordsSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpQualificationRecordsResp;
import com.rjtech.register.repository.emp.EmpQualificationRecordsRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpQualificationRecordsService;
import com.rjtech.register.service.handler.emp.EmpQualificationRecordsHandler;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.constants.ApplicationConstants;

@Service(value = "empQualificationRecordsService")
@RJSService(modulecode = "empQualificationRecordsService")
@Transactional
public class EmpQualificationRecordsServiceImpl implements EmpQualificationRecordsService {

    @Autowired
    private EmpQualificationRecordsRepository empQualificationRecordsRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private EmpProjRegisterRepository empProjRegisterRepository;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    public EmpQualificationRecordsResp getEmpQualificationRecords( ProjEmpRegisterGetReq projEmpRegisterGetReq ) {
        EmpQualificationRecordsResp empQualificationRecordsResp = new EmpQualificationRecordsResp();
        System.out.println("emp id:"+projEmpRegisterGetReq.getEmpId()+"->"+projEmpRegisterGetReq.getStatus());
        List<EmpQualificationRecordsEntity> empQualificationRecordsEntities = empQualificationRecordsRepository
                .findEmpQualificationRecords( projEmpRegisterGetReq.getEmpId(), projEmpRegisterGetReq.getStatus() );
        /*List<EmpQualificationRecordsEntity> empQualificationRecordsEntities = empQualificationRecordsRepository
                .findEmpQualificationRecords();*/
        System.out.println("size:"+empQualificationRecordsEntities.size());
        for( EmpQualificationRecordsEntity empQualificationRecordsEntity : empQualificationRecordsEntities ) {
        	System.out.println(empQualificationRecordsEntity);
        	empQualificationRecordsResp.getEmpQualificationRecordsTOs()
                    .add( EmpQualificationRecordsHandler.convertQualRecordsEntityToPOJO( empQualificationRecordsEntity ) );
        }
        //System.out.println(empQualificationRecordsRepository.findEmpQualificationRecords());
        System.out.println("response records size:"+empQualificationRecordsResp.getEmpQualificationRecordsTOs().size());
        return empQualificationRecordsResp;
    }
    
    public void saveEmpQualificationRecords( MultipartFile[] files, EmpQualificationRecordsSaveReq empQualificationRecordsSaveReq ) throws IOException {
    	System.out.println("saveEmpQualificationRecords function from EmpQualificationRecordsServiceImpl");
    	System.out.println(empQualificationRecordsSaveReq);
        EmpQualificationRecordsEntity empQualificationRecordsEntity = null;
        
        System.out.println("Folder category:"+empQualificationRecordsSaveReq.getFolderCategory());
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( empQualificationRecordsSaveReq.getFolderCategory() );
        //ProjDocFolderEntity projDocFolderEntity = null;
        String folder_path = folder.getUploadFolder();
        
        boolean update = false;
        for( EmpQualificationRecordsTO empQualificationRecordsTO : empQualificationRecordsSaveReq.getEmpQualificationRecordsTOs() ) {
        	empQualificationRecordsEntity = new EmpQualificationRecordsEntity();
			// We are passing multiple files as an array, to find which file is belongs to
			// which object based on fileName and index
			Integer fileIndex = empQualificationRecordsTO.getFileObjectIndex();
			if (fileIndex != null
					&& files[fileIndex].getOriginalFilename().equalsIgnoreCase( empQualificationRecordsTO.getName() ) ) {
				//System.out.println("File Name:"+preContractDocsTO.getName()+" projd id:"+preContractDocsTO.getProjId());				
				//preContractDocsTO.setProjDocFileTO(projDocFileTO);
			}

			empQualificationRecordsRepository.save( EmpQualificationRecordsHandler
					.convertEmpQualRecordsPOJOToEntity( files[fileIndex], empQualificationRecordsTO, projDocFileRepository, empRegisterRepository, empProjRegisterRepository, folder, empQualificationRecordsSaveReq.getUserId() ) );			
		}
    }
}
