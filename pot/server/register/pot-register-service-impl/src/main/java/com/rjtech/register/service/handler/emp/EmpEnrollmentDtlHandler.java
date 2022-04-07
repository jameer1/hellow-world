package com.rjtech.register.service.handler.emp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsEmpCmpEntity;
import com.rjtech.procurement.model.PreContractsEmpDtlEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
/*import com.rjtech.procurement.model.PreContractEntityCopy;
import com.rjtech.procurement.model.PreContractsEmpCmpEntityCopy;
import com.rjtech.procurement.model.PreContractsEmpDtlEntityCopy;
import com.rjtech.procurement.model.PreContractsServiceCmpEntityCopy;
import com.rjtech.procurement.model.PreContractsServiceDtlEntityCopy;
import com.rjtech.procurement.model.PurchaseOrderEntityCopy;*/
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.emp.dto.EmpEnrollmentDtlTO;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.model.EmpEnrollmentDtlEntity;
import com.rjtech.register.emp.model.EmpProjRegisterPODtlEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.impl.emp.EmployeAssignStatus;
import com.rjtech.register.utils.RegisterCommonUtils;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.common.utils.UploadUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.common.model.UserMstrEntity;

public class EmpEnrollmentDtlHandler {

    private static final Logger log = LoggerFactory.getLogger(EmpEnrollmentDtlHandler.class);
    
    public static final String FILE_DIRECTORY = "E://pavani/Downloads";

    public static void convertEntityToPOJO(EmpEnrollmentDtlEntity empEnrollmentEntity,
            EmpEnrollmentDtlTO enrollmentDTO) {

        enrollmentDTO.setId(empEnrollmentEntity.getId());
        if (CommonUtil.objectNotNull(empEnrollmentEntity.getContractDocument())) {
            enrollmentDTO.setContractDocumentFileName(empEnrollmentEntity.getContractDocumentFileName());
            enrollmentDTO.setContractDocumentFileType(empEnrollmentEntity.getContractDocumentFileType());
        }
        if (CommonUtil.objectNotNull(empEnrollmentEntity.getEmpRegisterDtlEntity())) {
            if (CommonUtil.objectNotNull(empEnrollmentEntity.getEmpRegisterDtlEntity().getContractDate())) {
                enrollmentDTO.setContractDate(CommonUtil
                        .convertDateToString(empEnrollmentEntity.getEmpRegisterDtlEntity().getContractDate()));
            }
            enrollmentDTO.setContractNumber(empEnrollmentEntity.getEmpRegisterDtlEntity().getContractNumber());
            enrollmentDTO.setEmpLocation(empEnrollmentEntity.getEmpLocation());
            enrollmentDTO.setEffectiveFrom(CommonUtil.convertDateToString(empEnrollmentEntity.getEffectiveFrom()));
            enrollmentDTO.getEmpLabelKeyTO().setId(empEnrollmentEntity.getEmpRegisterDtlEntity().getId());
            enrollmentDTO.getEmpLabelKeyTO().setCode(empEnrollmentEntity.getEmpRegisterDtlEntity().getCode());
            enrollmentDTO.getEmpLabelKeyTO()
                    .setName(CommonUtil.appendTwoString(empEnrollmentEntity.getEmpRegisterDtlEntity().getFirstName(),
                            empEnrollmentEntity.getEmpRegisterDtlEntity().getLastName(), null));
        }

        if (CommonUtil.objectNotNull(empEnrollmentEntity.getProjEmpRigisterEntity())) {
            enrollmentDTO.setProjEmpId(empEnrollmentEntity.getProjEmpRigisterEntity().getId());
            if (CommonUtil.objectNotNull(empEnrollmentEntity.getProjEmpRigisterEntity().getProjMstrEntity())) {
                enrollmentDTO
                        .setProjId(empEnrollmentEntity.getProjEmpRigisterEntity().getProjMstrEntity().getProjectId());
                enrollmentDTO.setName(empEnrollmentEntity.getProjEmpRigisterEntity().getProjMstrEntity().getProjName());
                if (CommonUtil.objectNotNull(empEnrollmentEntity.getProjEmpRigisterEntity().getProjMstrEntity()
                        .getParentProjectMstrEntity()))
                    enrollmentDTO.setParentName(empEnrollmentEntity.getProjEmpRigisterEntity().getProjMstrEntity()
                            .getParentProjectMstrEntity().getProjName());
            }

            enrollmentDTO.getLocationLabelKeyTO()
                    .setCode(empEnrollmentEntity.getProjEmpRigisterEntity().getEnrollmentLoc());
            enrollmentDTO.setProjEmpRegisterTO(
                    convertMobilizationDateEntityTO(empEnrollmentEntity.getProjEmpRigisterEntity()));
            EmpProjRegisterPODtlEntity empProjRegisterPODtlEntity = empEnrollmentEntity.getProjEmpRigisterEntity()
                    .getEmpProjRegisterPODtlEntity();
            if (CommonUtil.objectNotNull(empProjRegisterPODtlEntity)) {
                enrollmentDTO.setAssignStatus(empEnrollmentEntity.getProjEmpRigisterEntity().getAssignStatus());
                enrollmentDTO.getProjectPOTO().setProjPOId(empProjRegisterPODtlEntity.getId());
                PurchaseOrderEntity purchaseOrder = empProjRegisterPODtlEntity.getPurchaseOrderEntity();
                if (CommonUtil.objectNotNull(purchaseOrder)) {
                    enrollmentDTO.getProjectPOTO().getPurchaseLabelKeyTO().setId(purchaseOrder.getId());
                    PreContractEntity preContract = purchaseOrder.getPreContractsCmpEntity().getPreContractEntity();
                    enrollmentDTO.getProjectPOTO().getPurchaseLabelKeyTO()
                            .setCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                                    + preContract.getProjId().getCode() + "-" + preContract.getId() + "-"
                                    + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-" + purchaseOrder.getId());
                }
                // Pre contract EMP
                PreContractsEmpDtlEntity empDtl = empProjRegisterPODtlEntity.getPreContractsEmpDtlEntity();
                if (CommonUtil.objectNotNull(empDtl)) {
                    enrollmentDTO.getProjectPOTO().getPurchaseSchLabelKeyTO().setId(empDtl.getId());
                    PreContractsEmpCmpEntity preContractsEmpCmpEntity = empProjRegisterPODtlEntity
                            .getPreContractsEmpCmpEntity();
                    if (CommonUtil.objectNotNull(preContractsEmpCmpEntity)) {
                        enrollmentDTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                                .put(CommonConstants.SCH_ITEM_CMP_ID, String.valueOf(preContractsEmpCmpEntity.getId()));
                    }
                    enrollmentDTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                            .put(CommonConstants.PROCUREMENT_MASTER_TYPE, "E");
                }

                // Pre contract Service
                PreContractsServiceDtlEntity serviceDtl = empProjRegisterPODtlEntity
                        .getPreContractsServiceDtlEntity();
                if (CommonUtil.objectNotNull(serviceDtl)) {
                    enrollmentDTO.getProjectPOTO().getPurchaseSchLabelKeyTO().setId(serviceDtl.getId());
                    PreContractsServiceCmpEntity preContractsSerCmpEntity = empProjRegisterPODtlEntity
                            .getPreContractsServiceCmpEntity();
                    if (CommonUtil.objectNotNull(preContractsSerCmpEntity)) {
                        enrollmentDTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                                .put(CommonConstants.SCH_ITEM_CMP_ID, String.valueOf(preContractsSerCmpEntity.getId()));
                    }
                    enrollmentDTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                            .put(CommonConstants.PROCUREMENT_MASTER_TYPE, "S");
                }

                enrollmentDTO.getProjectPOTO().getPurchaseSchLabelKeyTO()
                        .setCode(empEnrollmentEntity.getProjEmpRigisterEntity().getEmpProjRegisterPODtlEntity().getPurchaseSchItemCode());
                enrollmentDTO.getProjectPOTO().getPurchaseSchLabelKeyTO()
                        .setName(empProjRegisterPODtlEntity.getPurchaseItemDesc());

            }

        }

        if (CommonUtil.objectNotNull(empEnrollmentEntity.getReportManagerEmpEntity())) {
            enrollmentDTO.getReportingManagerLabelKeyTO()
                    .setId(empEnrollmentEntity.getReportManagerEmpEntity().getId());
            enrollmentDTO.getReportingManagerLabelKeyTO()
                    .setCode(empEnrollmentEntity.getReportManagerEmpEntity().getCode());
            enrollmentDTO.getReportingManagerLabelKeyTO()
                    .setName(CommonUtil.appendTwoString(empEnrollmentEntity.getReportManagerEmpEntity().getFirstName(),
                            empEnrollmentEntity.getReportManagerEmpEntity().getLastName(), null));

        }
        if (CommonUtil.objectNotNull(empEnrollmentEntity.getEmpClassMstrEntity())) {
            enrollmentDTO.setEmpClassId(empEnrollmentEntity.getEmpClassMstrEntity().getId());
            enrollmentDTO.setEmpClassName(empEnrollmentEntity.getEmpClassMstrEntity().getName());
        }
        enrollmentDTO.setEmpCategoryTypeId(empEnrollmentEntity.getEmpCategoryTypeId());
        enrollmentDTO.setId(empEnrollmentEntity.getId());
        enrollmentDTO.setLatest(empEnrollmentEntity.getIsLatest());
    }

    public static void convertPOJOTOEntity(EmpEnrollmentDtlEntity empEnrollmentEntity, EmpEnrollmentDtlTO enrollmentDTO,
            EmpRegisterRepository empRegisterRepository, EmpClassRepository empClassRepository, String[] folder_info, String[] extras, ProjDocFileEntity projDocFileEntity, MultipartFile file) {
    	//ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
        if (CommonUtil.objectNotNull(enrollmentDTO.getEmpLabelKeyTO())
                && CommonUtil.isNonBlankLong(enrollmentDTO.getEmpLabelKeyTO().getId())) {
            EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository
                    .findOne(enrollmentDTO.getEmpLabelKeyTO().getId());
            empEnrollmentEntity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        }
        if (CommonUtil.isNonBlankLong(enrollmentDTO.getEmpClassId())) {
            EmpClassMstrEntity empClassMstrEntity = empClassRepository.findOne(enrollmentDTO.getEmpClassId());
            empEnrollmentEntity.setEmpClassMstrEntity(empClassMstrEntity);
        }
        empEnrollmentEntity.setEffectiveTo(CommonUtil.convertStringToDate(enrollmentDTO.getEffectiveTo()));
        empEnrollmentEntity.setStatus(StatusCodes.ACTIVE.getValue());
        if (CommonUtil.objectNotNull(enrollmentDTO.getReportingManagerLabelKeyTO())
                && CommonUtil.isNonBlankLong(enrollmentDTO.getReportingManagerLabelKeyTO().getId())) {
            EmpRegisterDtlEntity reportManagerEmpEntity = empRegisterRepository
                    .findOne(enrollmentDTO.getReportingManagerLabelKeyTO().getId());
            empEnrollmentEntity.setReportManagerEmpEntity(reportManagerEmpEntity);
        }
        empEnrollmentEntity.setIsLatest(RegisterCommonUtils.IS_LATEST_Y);
        if (CommonUtil.isNotBlankStr(enrollmentDTO.getContractDate())) {
            empEnrollmentEntity.setContractDate(CommonUtil.convertStringToDate(enrollmentDTO.getContractDate()));
        }
        if (CommonUtil.objectNotNull(file)) {
            try {
                empEnrollmentEntity.setContractDocument(file.getBytes());
                empEnrollmentEntity.setContractDocumentFileName(file.getOriginalFilename());
                empEnrollmentEntity.setContractDocumentFileType(file.getContentType());
                uploadEmpDocsToServer( enrollmentDTO, folder_info, extras, file );
            } catch (IOException e) {
                log.error("IOException while reading the file ", e);
            }
        }
        empEnrollmentEntity.setContractNumber(enrollmentDTO.getContractNumber());
        empEnrollmentEntity.setEmpLocation(enrollmentDTO.getEmpLocation());
        empEnrollmentEntity.setEffectiveFrom(CommonUtil.convertStringToDate(enrollmentDTO.getEffectiveFrom()));
        empEnrollmentEntity.setProjDocFileEntity( projDocFileEntity );
    }

    public static void convertProjEmpEntity(EmpProjRigisterEntity empProjRigisterEntity,
            EmpRegisterDtlTO empRegisterDtlTO, EmpEnrollmentDtlTO enrollmentDTO,
            EmpRegisterRepository empRegisterRepository, EPSProjRepository epsProjRepository,
            EmpClassRepository empClassRepository) {
        EmpRegisterDtlEntity empRegisterDtlEntity = empRegisterRepository.findOne(empRegisterDtlTO.getId());
        empProjRigisterEntity.setEmpRegisterDtlEntity(empRegisterDtlEntity);
        if (CommonUtil.isNonBlankLong(enrollmentDTO.getProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(enrollmentDTO.getProjId());
            empProjRigisterEntity.setProjMstrEntity(projMstrEntity);
        }
        if (CommonUtil.isNonBlankLong(enrollmentDTO.getEmpClassId())) {
            EmpClassMstrEntity empClassMstrEntity = empClassRepository.findOne(enrollmentDTO.getEmpClassId());
            empProjRigisterEntity.setEmpClassMstrEntity(empClassMstrEntity);
        }
        empProjRigisterEntity.setStatus(enrollmentDTO.getStatus());
        empProjRigisterEntity.setEnrollmentDate(CommonUtil.convertStringToDate(enrollmentDTO.getEffectiveFrom()));
        empProjRigisterEntity.setEnrollmentLoc(enrollmentDTO.getLocationLabelKeyTO().getName());
        empProjRigisterEntity.setAssignStatus(EmployeAssignStatus.EMPLOYEE_NOT_ASSIGNED);
        empProjRigisterEntity.setStatus(StatusCodes.ACTIVE.getValue());
        empProjRigisterEntity.setIsLatest(RegisterCommonUtils.IS_LATEST_Y);

    }

    public static ProjEmpRegisterTO convertMobilizationDateEntityTO(EmpProjRigisterEntity projEmpEntity) {
        ProjEmpRegisterTO projEmpRegisterTO = new ProjEmpRegisterTO();
        projEmpRegisterTO.setId(projEmpEntity.getId());
        if (CommonUtil.objectNotNull(projEmpEntity.getEmpRegisterDtlEntity())) {
            projEmpRegisterTO.setEmpId(projEmpEntity.getEmpRegisterDtlEntity().getId());
        }
        if (CommonUtil.objectNotNull(projEmpEntity.getEmpClassMstrEntity())) {
            projEmpRegisterTO.setEmpClassifyId(projEmpEntity.getEmpClassMstrEntity().getId());
            projEmpRegisterTO.setEmpClassifyName(projEmpEntity.getEmpClassMstrEntity().getName());
        }
        if (CommonUtil.isNotBlankDate(projEmpEntity.getMobilaizationDate())) {
            projEmpRegisterTO
                    .setMobilaizationDate(CommonUtil.convertDateToString(projEmpEntity.getMobilaizationDate()));
        }
        if (CommonUtil.isNotBlankDate(projEmpEntity.getExpectedDemobilaizationDate())) {
            projEmpRegisterTO.setExpecteddeMobilaizationDate(
                    CommonUtil.convertDateToString(projEmpEntity.getExpectedDemobilaizationDate()));
        }
        if (CommonUtil.objectNotNull(projEmpEntity.getProjMstrEntity())) {
            ProjMstrEntity proj = projEmpEntity.getProjMstrEntity();
            projEmpRegisterTO.setProjId(proj.getProjectId());
            projEmpRegisterTO.setName(proj.getProjName());
            if (CommonUtil.objectNotNull(proj.getParentProjectMstrEntity()))
                projEmpRegisterTO.setParentName(proj.getParentProjectMstrEntity().getProjName());
        }
        projEmpRegisterTO
                .setDeMobilaizationDate(CommonUtil.convertDateToString(projEmpEntity.getDeMobilaizationDate()));
        if(CommonUtil.objectNotNull(projEmpEntity.getDemobilizationStatus())&& projEmpEntity.getDemobilizationStatus().equals("On Transfer")) {
        	projEmpRegisterTO.setEmpStatus("Current Employee");
        }else if(CommonUtil.objectNotNull(projEmpEntity.getDemobilizationStatus()) && projEmpEntity.getDemobilizationStatus().equals("Relieved")) {
        	projEmpRegisterTO.setEmpStatus("Former Employee");
        }
        projEmpRegisterTO.setEnrollmentDate(CommonUtil.convertDateToString(projEmpEntity.getEnrollmentDate()));
        projEmpRegisterTO.setNotes(projEmpEntity.getNotes());
        projEmpRegisterTO.setWorkType(projEmpEntity.getWorkType());
        projEmpRegisterTO.setLatest(projEmpEntity.getIsLatest());
        projEmpRegisterTO.setDeploymentId(projEmpEntity.getDeploymentId());
        projEmpRegisterTO.setTaxFileNum(projEmpEntity.getTaxFileNum());

        return projEmpRegisterTO;
    }

    public static void convertProjEmpRegisterTOEntity(EmpProjRigisterEntity projEmpEntity,
            ProjEmpRegisterTO projEmpRegisterTO) {
        if (CommonUtil.isNotBlankStr(projEmpRegisterTO.getMobilaizationDate())) {
            projEmpEntity
                    .setMobilaizationDate(CommonUtil.convertStringToDate(projEmpRegisterTO.getMobilaizationDate()));
            projEmpEntity.setAssignStatus(EmployeAssignStatus.EMPLOYEE_ASSIGNED);
        }
        if (CommonUtil.isNotBlankStr(projEmpRegisterTO.getExpecteddeMobilaizationDate())) {
            projEmpEntity.setExpectedDemobilaizationDate(
                    CommonUtil.convertStringToDate(projEmpRegisterTO.getExpecteddeMobilaizationDate()));
        }
        if (CommonUtil.isNotBlankStr(projEmpRegisterTO.getDeMobilaizationDate())) {
            projEmpEntity
                    .setDeMobilaizationDate(CommonUtil.convertStringToDate(projEmpRegisterTO.getDeMobilaizationDate()));
        }
        projEmpEntity.setDemobilizationStatus(projEmpRegisterTO.getEmpStatus());
        projEmpEntity.setTaxFileNum(projEmpRegisterTO.getTaxFileNum());
        projEmpEntity.setNotes(projEmpRegisterTO.getNotes());
        projEmpEntity.setWorkType(projEmpRegisterTO.getWorkType());
    }
    
    public static void uploadEmpDocsToServer( EmpEnrollmentDtlTO empEnrollmentDtlTO , String[] folder_info, String[] extras, MultipartFile file ) throws IOException
    {
    	UploadUtil uploadUtil = new UploadUtil();
    	System.out.println("uploadEmpDocsToServer:");
    	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
    	{
    		String folder_category = folder_info[0];
    		String dir_path = folder_info[1];    	
    		uploadUtil.uploadFile( file, folder_category, dir_path, extras );
    	}
    }
    
    public static ProjDocFileEntity convertPOJOToProjDocFileEntity( ProjDocFileTO projDocFileTO ) {
    	
    	ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
    	
    	ProjDocFolderEntity projDocFolderEntity = new ProjDocFolderEntity();
    	projDocFolderEntity.setId( projDocFileTO.getFolderId() );
    	
    	ProjMstrEntity projMstrEntity = new ProjMstrEntity();
    	projMstrEntity.setProjectId( projDocFileTO.getProjId() );
    	
    	UserMstrEntity userMstrEntity = new UserMstrEntity();
    	userMstrEntity.setUserId( projDocFileTO.getUserId() );
    	
    	projDocFileEntity.setName( projDocFileTO.getName() );
		//projDocFileTO.setVersion(preContractCmpDocsTO.getVersion());
    	projDocFileEntity.setFileType( projDocFileTO.getFileType() );
    	projDocFileEntity.setFileSize( projDocFileTO.getFileSize() );
    	projDocFileEntity.setStatus( projDocFileTO.getStatus() );
    	projDocFileEntity.setProjectId( projMstrEntity );
    	projDocFileEntity.setFolderId( projDocFolderEntity );
    	projDocFileEntity.setFolderPath( projDocFileTO.getFolderPath() );
    	projDocFileEntity.setCreatedBy( userMstrEntity );
    	projDocFileEntity.setUpdatedBy( userMstrEntity );
    	projDocFileEntity.setVersion( "1" );
        return projDocFileEntity;
    }
}
