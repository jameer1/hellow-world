package com.rjtech.register.service.handler.emp;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.emp.dto.EmpQualificationRecordsTO;
import com.rjtech.register.emp.model.EmpQualificationRecordsEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.constans.RegisterConstants;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.register.emp.req.EmpQualificationRecordsSaveReq;
import com.rjtech.register.emp.resp.EmpQualificationRecordsResp;

public class EmpQualificationRecordsHandler {

	public static EmpQualificationRecordsTO convertQualRecordsEntityToPOJO( EmpQualificationRecordsEntity entity ) {
		EmpQualificationRecordsTO empQualificationRecordsTO = new EmpQualificationRecordsTO();
		
		if( CommonUtil.isNotBlankDate( entity.getExpiryDate() ) ) {
			empQualificationRecordsTO.setExpiryDate( CommonUtil.convertDateToString( entity.getExpiryDate() ) );
        }
		
		if( CommonUtil.isNotBlankDate( entity.getDateOfIssue() ) ) {
			empQualificationRecordsTO.setDateOfIssue( CommonUtil.convertDateToString( entity.getDateOfIssue() ) );
        }
		if( CommonUtil.isNotBlankDate( entity.getNewTrainingDueDate() ) ) {
			empQualificationRecordsTO.setNewTrainingDueDate( CommonUtil.convertDateToString( entity.getNewTrainingDueDate() ) );
        }
		empQualificationRecordsTO.setId( entity.getId() );
		empQualificationRecordsTO.setCode( entity.getCode() );
		empQualificationRecordsTO.setInstituteName( entity.getInstituteName() );
		empQualificationRecordsTO.setInstituteAddress( entity.getInstituteAddress() );
		empQualificationRecordsTO.setRecordType( entity.getRecordType() );
		empQualificationRecordsTO.setInstituteContactDetails( entity.getInstituteContactDetails() );
		empQualificationRecordsTO.setCertificateNumber( entity.getCertificateNumber() );
		empQualificationRecordsTO.setIsRenewalRequired( entity.getIsRenewalRequired() );
		empQualificationRecordsTO.setFileName( entity.getFileName() );
		
		return empQualificationRecordsTO;
	}
	
    public static EmpQualificationRecordsEntity convertEmpQualRecordsPOJOToEntity( MultipartFile file, EmpQualificationRecordsTO empQualificationRecordsTO, ProjDocFileRepository projDocFileRepository
    		, EmpRegisterRepository empRegisterRepository, EmpProjRegisterRepository empProjRegisterRepository, ProjDocFolderEntity folder, Long userId ) throws IOException {
        EmpQualificationRecordsEntity entity = new EmpQualificationRecordsEntity();
        ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
        ProjDocFileEntity resProjDocFileEntity = new ProjDocFileEntity();
        
        UserMstrEntity userMstrEntity = new UserMstrEntity();
        userMstrEntity.setUserId( userId );
        
        ProjMstrEntity projMstrEntity = new ProjMstrEntity();
        Long project_id = 0L;
        projMstrEntity.setProjectId( empQualificationRecordsTO.getProjId() );

        if ( CommonUtil.isNonBlankLong( empQualificationRecordsTO.getId() ) ) {
            entity.setId( empQualificationRecordsTO.getId() );
        }
        if ( CommonUtil.isNonBlankLong( empQualificationRecordsTO.getEmpRegId() ) ) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository.findOne( empQualificationRecordsTO.getEmpRegId() );
            entity.setEmpRegisterDtlEntity( empRegisterDtlEntity );            
        }
        UploadUtil uploadUtil = new UploadUtil();

        if( file != null )
        {
        	projDocFileEntity.setFolderId( folder );
			//projDocFileTO.setMultipartFile( files[fileIndex]);
        	projDocFileEntity.setName( empQualificationRecordsTO.getFileName() );
        	projDocFileEntity.setVersion( String.valueOf( 1 ) );
        	projDocFileEntity.setFileType( file.getContentType() );
        	projDocFileEntity.setFileSize( UploadUtil.bytesIntoHumanReadable( file.getSize() ) );
        	projDocFileEntity.setStatus( 1 );
        	projDocFileEntity.setVersion( "1" );
			//projDocFileTO.setDescription(preContractDocsTO.getDescription());
        	projDocFileEntity.setProjectId( projMstrEntity );
        	projDocFileEntity.setFolderPath( "/" + String.valueOf( empQualificationRecordsTO.getProjId() ) + "/" + String.valueOf( empQualificationRecordsTO.getEmpRegId() ) );
			resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
			
			String[] extras = { String.valueOf( empQualificationRecordsTO.getProjId() ), String.valueOf( empQualificationRecordsTO.getEmpRegId() ) };
			uploadUtil.uploadFile( file, folder.getName(), folder.getUploadFolder(), extras );
        }
        if( empQualificationRecordsTO.getId() != null )
        {
        	entity.setId( empQualificationRecordsTO.getId() );
        }
        entity.setCode( generateCode( empQualificationRecordsTO.getProjId(), empQualificationRecordsTO.getEmpRegId(), folder.getId() ) );
        entity.setInstituteName( empQualificationRecordsTO.getInstituteName() );
        entity.setInstituteAddress( empQualificationRecordsTO.getInstituteAddress() );
        entity.setInstituteContactDetails( empQualificationRecordsTO.getInstituteContactDetails() );
        entity.setCertificateNumber( empQualificationRecordsTO.getCertificateNumber() );
        entity.setFileName( empQualificationRecordsTO.getFileName() );
        entity.setRecordType( empQualificationRecordsTO.getRecordType() );
        entity.setIsRenewalRequired( empQualificationRecordsTO.getIsRenewalRequired() );
        entity.setCreatedBy( userMstrEntity );
        entity.setProjDocFileEntity( resProjDocFileEntity );
        entity.setProjMstrEntity( projMstrEntity );
        entity.setStatus( empQualificationRecordsTO.getStatus() );
        if ( CommonUtil.isNotBlankStr( empQualificationRecordsTO.getDateOfIssue() ) ) {
            entity.setDateOfIssue( CommonUtil.convertStringToDate( empQualificationRecordsTO.getDateOfIssue() ) );
        }
        if ( CommonUtil.isNotBlankStr( empQualificationRecordsTO.getExpiryDate() ) ) {
            entity.setExpiryDate( CommonUtil.convertStringToDate( empQualificationRecordsTO.getExpiryDate() ) );
        }
        if ( CommonUtil.isNotBlankStr( empQualificationRecordsTO.getNewTrainingDueDate() ) ) {
            entity.setNewTrainingDueDate( CommonUtil.convertStringToDate( empQualificationRecordsTO.getNewTrainingDueDate() ) );
        }
        return entity;
    }
    
    public static String generateCode( Long projId, Long reg_id, Long folder_id ) {
        //ProjDocFolderEntity folder = projDocFileEntity.getFolderId();
        if (projId != null)
        	return ( ModuleCodesPrefixes.PROJ_DOC_PREFIX.getDesc() + "-" + AppUtils.formatNumberToString( projId ) + "-" + AppUtils.formatNumberToString( folder_id ) + "-" + AppUtils.formatNumberToString( reg_id ) );
        else
            return null;
    }
}
