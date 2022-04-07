package com.rjtech.register.service.impl.emp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PreContractsEmpCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
/*import com.rjtech.procurement.model.PreContractsCmpEntityCopy;
import com.rjtech.procurement.model.PreContractsEmpCmpEntityCopy;
import com.rjtech.procurement.model.PreContractsServiceCmpEntityCopy;
import com.rjtech.procurement.model.PurchaseOrderEntityCopy;*/
import com.rjtech.procurement.repository.PrecontractEmpCmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractEmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractServiceCmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractServiceRepositoryCopy;
import com.rjtech.procurement.repository.PurchaseOrderRepositoryCopy;
import com.rjtech.projectlib.model.ProjEmpClassMstrEntity;
//import com.rjtech.projectlib.model.ProjEmpClassMstrEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjEmpClassRepositoryCopy;
import com.rjtech.register.emp.dto.EmpEnrollmentDtlTO;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.model.EmpEnrollmentDtlEntity;
import com.rjtech.register.emp.model.EmpProjRegisterPODtlEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
import com.rjtech.register.emp.req.EmpEnrollmentGetReq;
import com.rjtech.register.emp.req.EmpEnrollmentSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegistersSaveReq;
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpEnrollmentResp;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;
import com.rjtech.register.repository.emp.EmpEnrollmentRepository;
import com.rjtech.register.repository.emp.EmpProjRegisterRepository;
import com.rjtech.register.repository.emp.EmpProjectPODtlRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpEnrollmentService;
import com.rjtech.register.service.handler.emp.EmpEnrollmentDtlHandler;
import com.rjtech.register.service.handler.emp.EmpRegisterDtlHandler;
import com.rjtech.register.service.handler.emp.ProjEmpRegisterPODtlHandler;
import com.rjtech.register.utils.RegisterCommonUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;
import com.rjtech.projsettings.repository.ProjGeneralRepositoryCopy;
//import com.rjtech.projsettings.model.ProjGeneralMstrEntityCopy;

import com.rjtech.register.constans.RegisterConstants;

@Service(value = "empEnrollmentService")
@RJSService(modulecode = "empEnrollmentService")
@Transactional
public class EmpEnrollmentServiceImpl implements EmpEnrollmentService {

    private static final Logger log = LoggerFactory.getLogger(EmpEnrollmentServiceImpl.class);

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private EmpEnrollmentRepository empEnrollmentRepository;

    @Autowired
    private EmpProjRegisterRepository projEmpRegisterRepository;

    @Autowired
    private EmpProjectPODtlRepository regEmpProjectPODtlRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private EmpClassRepository empClassRepository;

    @Autowired
    private PurchaseOrderRepositoryCopy purchaseOrderRepository;

    @Autowired
    private PrecontractEmpRepositoryCopy precontractEmpRepository;

    @Autowired
    private PrecontractEmpCmpRepositoryCopy precontractEmpCmpRepository;

    @Autowired
    private ProjEmpClassRepositoryCopy projEmpClassRepository;

    @Autowired
    private PrecontractServiceCmpRepositoryCopy precontractServiceCmpRepository;

    @Autowired
    private PrecontractServiceRepositoryCopy precontractServiceRepository;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepo;
    
    @Autowired
    private ProjGeneralRepositoryCopy projGeneralRepositoryCopy;

    public EmpEnrollmentResp getEmpEnrollments(EmpEnrollmentGetReq empEnrollmentGetReq) {
        EmpEnrollmentResp resp = new EmpEnrollmentResp();
        boolean activeProjExist = false;
        EmpRegisterDtlEntity registerDtlEntity = empEnrollmentRepository
                .getEmpEnrollments(empEnrollmentGetReq.getEmpId(), StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isListHasData(registerDtlEntity.getProjEmpRigisterEntities())) {
            for (EmpProjRigisterEntity empProjRigisterEntity : registerDtlEntity.getProjEmpRigisterEntities()) {

                if (RegisterCommonUtils.IS_LATEST_Y.equalsIgnoreCase(empProjRigisterEntity.getIsLatest())) {
                    activeProjExist = true;
                }

                for (EmpEnrollmentDtlEntity empEnrollmentEntity : empProjRigisterEntity.getEmpEnrollmentDtlEntities()) {
                    EmpEnrollmentDtlTO enrollmentDTO = new EmpEnrollmentDtlTO();
                    EmpEnrollmentDtlHandler.convertEntityToPOJO(empEnrollmentEntity, enrollmentDTO);
                    resp.getEmpEnrollmentDtlTOs().add(enrollmentDTO);
                }
            }

            if (!activeProjExist) {
                EmpEnrollmentDtlTO enrollmentDTO = new EmpEnrollmentDtlTO();
                getEmpEnrollmentDtlTO(enrollmentDTO, registerDtlEntity);
                resp.getEmpEnrollmentDtlTOs().add(enrollmentDTO);
            }
        } else {
            EmpEnrollmentDtlTO enrollmentDTO = new EmpEnrollmentDtlTO();
            getEmpEnrollmentDtlTO(enrollmentDTO, registerDtlEntity);
            resp.getEmpEnrollmentDtlTOs().add(enrollmentDTO);

        }

        resp.setEmpRegisterDtlTO(EmpRegisterDtlHandler.convertEntityToPOJO(registerDtlEntity));
        return resp;
    }

    private void getEmpEnrollmentDtlTO(EmpEnrollmentDtlTO enrollmentDTO, EmpRegisterDtlEntity registerDtlEntity) {
        enrollmentDTO.getEmpLabelKeyTO().setId(registerDtlEntity.getId());
        enrollmentDTO.getEmpLabelKeyTO().setCode(registerDtlEntity.getCode());
        enrollmentDTO.setLatest(RegisterCommonUtils.IS_LATEST_Y);
        enrollmentDTO.setAssignStatus(RegisterCommonUtils.PLANT_PO_ASSIGN_STATUS_NO);
        enrollmentDTO.getEmpLabelKeyTO().setName(
                CommonUtil.appendTwoString(registerDtlEntity.getFirstName(), registerDtlEntity.getLastName(), null));
    }

    public void saveEmpEnrollments(EmpEnrollmentSaveReq empEnrollmentSaveReq, MultipartFile file) {
        List<EmpEnrollmentDtlTO> empEnrollmentDtlTOList = empEnrollmentSaveReq.getEmpEnrollmentDtlTO();
        EmpRegisterDtlTO empRegisterDtlTO = empEnrollmentSaveReq.getEmpRegisterDtlTO();
        Boolean updateRequiredInProcuremnt = false;
        EmpEnrollmentDtlTO enrollmentForPO = null;
        Long empClassId = null;
        System.out.println("saveEmpEnrollments function");
        String extras[] = { String.valueOf( empEnrollmentSaveReq.getCrmId() ), String.valueOf( empRegisterDtlTO.getProjId() ), String.valueOf( empRegisterDtlTO.getId() ) };
        String folder_name = empEnrollmentSaveReq.getCategory();
        
        ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( folder_name );		
		String dir_path = folder.getUploadFolder();
		
		String[] folder_info = { empEnrollmentSaveReq.getCategory(), dir_path };
		ProjDocFileEntity projDocFileEntity = null;
		String folder_location = "";
		for(int i=0;i<extras.length;i++)
		{
			folder_location = folder_location + "/" +extras[i];
		}
		UploadUtil uploadUtil = new UploadUtil();
		Long userId = empEnrollmentSaveReq.getUserId();
        for (EmpEnrollmentDtlTO empEnrollmentDtlTO : empEnrollmentDtlTOList) {
            if (RegisterCommonUtils.IS_LATEST_Y.equalsIgnoreCase(empEnrollmentDtlTO.getLatest())) {
            	System.out.println("if condition of latest");
                List<EmpProjRigisterEntity> empProjRigisterEntities = new ArrayList<>();
                empClassId = empEnrollmentDtlTO.getEmpClassId();
                
                ProjDocFileTO projDocFileTO = new ProjDocFileTO();
				projDocFileTO.setFolderId( folder.getId() );
				projDocFileTO.setMultipartFile( file );
				projDocFileTO.setName( file.getOriginalFilename() );
				projDocFileTO.setFileType( file.getContentType() );				
				projDocFileTO.setStatus( 1 );
				projDocFileTO.setFileSize( uploadUtil.bytesIntoHumanReadable( file.getSize() ) );
				projDocFileTO.setProjId( empEnrollmentDtlTO.getProjId() );
				projDocFileTO.setFolderPath( folder_location );
				projDocFileTO.setUserId( userId );
				
                projDocFileEntity = projDocFileRepo.save( EmpEnrollmentDtlHandler.convertPOJOToProjDocFileEntity( projDocFileTO ) );
                Long projDocFileId = projDocFileEntity.getId();
                System.out.println("ProjDocFile entity id:"+projDocFileId);
				
                if (CommonUtil.isNonBlankLong(empEnrollmentDtlTO.getId())) {
                	System.out.println("if condition of getId");
                    EmpProjRigisterEntity existprojEmpRigisterEntity = null;

                    existprojEmpRigisterEntity = projEmpRegisterRepository
                            .findLatestEmployeDeployment(empRegisterDtlTO.getId());
                    EmpEnrollmentDtlEntity existempEnrollmentDtlEntity = existprojEmpRigisterEntity
                            .getEmpEnrollmentDtlEntities().get(0);

                    if (CommonUtil.objectNotNull(existprojEmpRigisterEntity.getProjMstrEntity())
                            && !existprojEmpRigisterEntity.getProjMstrEntity().getProjectId()
                                    .equals(empEnrollmentDtlTO.getProjId())) {
                    	System.out.println("if condition of if condition of getId");
                        existprojEmpRigisterEntity.setIsLatest(RegisterCommonUtils.IS_LATEST_N);
                        existprojEmpRigisterEntity.setDeMobilaizationDate(CommonUtil.addNumberOfDaysInputDate(
                                CommonUtil.convertStringToDate(empEnrollmentDtlTO.getEffectiveFrom()), -1));
                        existprojEmpRigisterEntity.getEmpEnrollmentDtlEntities().get(0)
                                .setIsLatest(RegisterCommonUtils.IS_LATEST_N);
                        EmpEnrollmentProcessBO processBO = getEmpEnrollmentPromostionEntity(empEnrollmentDtlTO,
                                empRegisterDtlTO, folder_info, extras, projDocFileEntity, file);
                        processBO.getProjEmpRigisterEntity().setDeploymentId(1L);
                        processBO.getProjEmpRigisterEntity().getEmpEnrollmentDtlEntities()
                                .add(processBO.getEmpEnrollmentDtlEntity());
                        long deployementId = processBO.getProjEmpRigisterEntity().getDeploymentId().longValue();
                        deployementId = deployementId + 1;
                        processBO.getProjEmpRigisterEntity().setDeploymentId(deployementId);
                        empProjRigisterEntities.add(processBO.getProjEmpRigisterEntity());
                        projEmpRegisterRepository.save(empProjRigisterEntities);

                    } else {
                    	System.out.println("else conditon of if condition of getId");
                        if (CommonUtil.objectNotNull(existempEnrollmentDtlEntity.getEmpClassMstrEntity())
                                && !existempEnrollmentDtlEntity.getEmpClassMstrEntity().getId()
                                        .equals(empEnrollmentDtlTO.getEmpClassId())) {
                        	System.out.println("if of else conditon of if condition of getId");
                        	EmpProjRigisterEntity empProjRigisterEntity = projEmpRegisterRepository.findLatestEmployeDeployment(empRegisterDtlTO.getId());
                            existempEnrollmentDtlEntity.setIsLatest(RegisterCommonUtils.IS_LATEST_N);
                            EmpEnrollmentDtlEntity empEnrollmentDtlEntity = new EmpEnrollmentDtlEntity();
                            EmpEnrollmentDtlHandler.convertPOJOTOEntity(empEnrollmentDtlEntity, empEnrollmentDtlTO,
                                    empRegisterRepository, empClassRepository, folder_info, extras, projDocFileEntity, file);
                            EmpProjRigisterEntity projEmpRegister = new EmpProjRigisterEntity();
                            projEmpRegister.setEnrollmentDate(
                                    CommonUtil.convertStringToDate(empEnrollmentDtlTO.getEffectiveFrom()));
                            cloneEmpToNewProj(empProjRigisterEntity, empEnrollmentDtlTO, empEnrollmentDtlTO.getProjId());
                            projEmpRegister.setId(existprojEmpRigisterEntity.getId());
                            empEnrollmentDtlEntity.setProjEmpRigisterEntity(projEmpRegister);
                            empEnrollmentRepository.save(empEnrollmentDtlEntity);

                        } else {
                        	System.out.println("else of else conditon of if condition of getId");
                            if (CommonUtil.objectNotNull(empEnrollmentDtlTO.getReportingManagerLabelKeyTO())
                                    && CommonUtil.isNonBlankLong(
                                            empEnrollmentDtlTO.getReportingManagerLabelKeyTO().getId())) {
                                EmpRegisterDtlEntity reportingManager = empRegisterRepository
                                        .findOne(empEnrollmentDtlTO.getReportingManagerLabelKeyTO().getId());
                                existempEnrollmentDtlEntity.setReportManagerEmpEntity(reportingManager);
                            }
                            existempEnrollmentDtlEntity.setContractNumber(empEnrollmentDtlTO.getContractNumber());
                            existempEnrollmentDtlEntity.setEmpLocation(empEnrollmentDtlTO.getEmpLocation());
                            existempEnrollmentDtlEntity.setEffectiveFrom(
                                    CommonUtil.convertStringToDate(empEnrollmentDtlTO.getEffectiveFrom()));
                            if (CommonUtil.isNotBlankStr(empEnrollmentDtlTO.getContractDate())) {
                                existempEnrollmentDtlEntity.setContractDate(
                                        CommonUtil.convertStringToDate(empEnrollmentDtlTO.getContractDate()));
                            }
                            if (CommonUtil.objectNotNull(file)) {
                                try {
                                    existempEnrollmentDtlEntity.setContractDocument(file.getBytes());
                                    existempEnrollmentDtlEntity.setContractDocumentFileName(file.getOriginalFilename());
                                    existempEnrollmentDtlEntity.setContractDocumentFileType(file.getContentType());
                                    existempEnrollmentDtlEntity.setProjDocFileEntity( projDocFileEntity );
                                    EmpEnrollmentDtlHandler.uploadEmpDocsToServer( empEnrollmentDtlTO, folder_info, extras, file );
                                } catch(IOException e) {
                                    log.error("IOException while reading file ", e);
                                }
                            }
                        }
                        existprojEmpRigisterEntity
                                .setEnrollmentLoc(empEnrollmentDtlTO.getLocationLabelKeyTO().getName());

                    }
                } else {
                	System.out.println("else condition of getId");
                    EmpProjRegisterPODtlEntity empProjRegisterPODtlEntity = getProjEmpRegisterPODtlEntity(
                            empEnrollmentDtlTO);

                    enrollmentForPO = empEnrollmentDtlTO;
                    updateRequiredInProcuremnt = true;
                    EmpEnrollmentProcessBO processBO = getEmpEnrollmentPromostionEntity(empEnrollmentDtlTO,
                            empRegisterDtlTO, folder_info, extras, projDocFileEntity, file);

                    processBO.getProjEmpRigisterEntity().setDeploymentId(new Long(1));

                    processBO.getProjEmpRigisterEntity().getEmpEnrollmentDtlEntities()
                            .add(processBO.getEmpEnrollmentDtlEntity());

                    processBO.getProjEmpRigisterEntity().setEmpProjRegisterPODtlEntity(empProjRegisterPODtlEntity);
                    empProjRigisterEntities.add(processBO.getProjEmpRigisterEntity());

                    if (CommonUtil.isBlankLong(empEnrollmentDtlTO.getProjectPOTO().getPurchaseLabelKeyTO().getId())) {
                        projEmpRegisterRepository.save(empProjRigisterEntities);
                    } else if (CommonUtil.isBlankLong(empProjRegisterPODtlEntity.getId())) {
                        empProjRegisterPODtlEntity.setEmpProjRigisterEntities(empProjRigisterEntities);
                        regEmpProjectPODtlRepository.save(empProjRegisterPODtlEntity);
                    } else {
                        EmpProjRegisterPODtlEntity newProjPO = new EmpProjRegisterPODtlEntity();
                        newProjPO.setId(empProjRegisterPODtlEntity.getId());
                        processBO.getProjEmpRigisterEntity().setEmpProjRegisterPODtlEntity(newProjPO);
                        projEmpRegisterRepository.save(empProjRigisterEntities);
                    }

                }

                /*
                 * The following details emp contract details
                 */

                EmpRegisterDtlEntity empRegisterEntity = null;
                empRegisterEntity = empRegisterRepository.findOne(empEnrollmentSaveReq.getEmpRegisterDtlTO().getId());
                if (CommonUtil.isNonBlankLong(empEnrollmentDtlTO.getProjId())) {
                    ProjMstrEntity projMstrEntity = epsProjRepository.findOne(empEnrollmentDtlTO.getProjId());
                    empRegisterEntity.setProjMstrEntity(projMstrEntity);
                }
                if (CommonUtil.isNonBlankLong(empClassId)) {
                    EmpClassMstrEntity empClassMstrEntity = empClassRepository.findOne(empClassId);
                    empRegisterEntity.setEmpClassMstrEntity(empClassMstrEntity);
                }
                if (CommonUtil.isNotBlankStr(empEnrollmentDtlTO.getContractDate())) {
                    empRegisterEntity
                            .setContractDate(CommonUtil.convertStringToDate(empEnrollmentDtlTO.getContractDate()));
                }

                empRegisterEntity.setContractNumber(empEnrollmentDtlTO.getContractNumber());

                empRegisterRepository.save(empRegisterEntity);

            }

        }
            String strSchCmpId = null;

            if( enrollmentForPO != null ){
              strSchCmpId = enrollmentForPO.getProjectPOTO().getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.SCH_ITEM_CMP_ID);
            }

            if (updateRequiredInProcuremnt && strSchCmpId != null) {
                String schItemProcureType = enrollmentForPO.getProjectPOTO().getPurchaseSchLabelKeyTO()
                        .getDisplayNamesMap().get(CommonConstants.PROCUREMENT_MASTER_TYPE);
                Long schItemCmpId = Long.parseLong(strSchCmpId);
                if (CommonUtil.isBlankLong(schItemCmpId)) {
                    if (schItemProcureType.equals("E")) {
                        PreContractsEmpCmpEntity cmpEntityCopy = precontractEmpCmpRepository.findOne(schItemCmpId);
                        // Add quantity
                        if (cmpEntityCopy.getReceivedQty() != null)
                            cmpEntityCopy.setReceivedQty(cmpEntityCopy.getReceivedQty() + 1);
                        else
                            cmpEntityCopy.setReceivedQty(1);

                        if ((cmpEntityCopy.getQuantity() - cmpEntityCopy.getReceivedQty()) <= 0) {
                            cmpEntityCopy.setComplete(true);
                        }

                        if (cmpEntityCopy.isComplete()) {
                            completePurchaseOrder(schItemProcureType, cmpEntityCopy.getPreContractsCmpEntity());
                        }
                    } else if (schItemProcureType.equals("S")) {
                        PreContractsServiceCmpEntity cmpEntityCopy = precontractServiceCmpRepository
                                .findOne(schItemCmpId);

                        // Add quantity
                        if (cmpEntityCopy.getReceivedQuantity() != null)
                            cmpEntityCopy.setReceivedQuantity(cmpEntityCopy.getReceivedQuantity() + 1);
                        else
                            cmpEntityCopy.setReceivedQuantity(1);

                        if ((cmpEntityCopy.getQuantity() - cmpEntityCopy.getReceivedQuantity()) <= 0) {
                            cmpEntityCopy.setComplete(true);
                        }

                        if (cmpEntityCopy.isComplete()) {
                            completePurchaseOrder(schItemProcureType, cmpEntityCopy.getPreContractsCmpEntity());
                        }
                    }

                }
            }
    }

    private void completePurchaseOrder(String procureType, PreContractsCmpEntity preContractsCmpEntity) {
        PurchaseOrderEntity purchaseOrderEntityCopy = purchaseOrderRepository
                .findByPreContractsCmpEntity(preContractsCmpEntity);

        String existingType = purchaseOrderEntityCopy.getProcureType();
        if (CommonUtil.isNotBlankStr(existingType)) {
            String completeProcureType = purchaseOrderEntityCopy.getCompleteProcureType();
            String[] completeProcureTypes;
            boolean alreadyCompleted = false;
            if (completeProcureType != null) {
                completeProcureTypes = completeProcureType.split("#");
                // Checking if procureType is already added in completeProcureTypes
                for (String complete : completeProcureTypes) {
                    if (complete.equals(procureType)) {
                        alreadyCompleted = true;
                        break;
                    }
                }
            }
            // If procureType is not added, add it
            if (!alreadyCompleted) {
                if (completeProcureType != null
                        && completeProcureType.substring(completeProcureType.length() - 1).equals("#")) {
                    completeProcureType = completeProcureType.concat(procureType);
                    purchaseOrderEntityCopy.setCompleteProcureType(completeProcureType);
                } else {
                    purchaseOrderEntityCopy.setCompleteProcureType(procureType);
                }
            }

            // Set purchase order as completed, if all types are completed
            String[] existingTypes = existingType.split("#");
            completeProcureType = purchaseOrderEntityCopy.getCompleteProcureType();
            completeProcureTypes = completeProcureType.split("#");

            boolean completedPO = CommonUtil.compareStringArrays(existingTypes, completeProcureTypes);
            purchaseOrderEntityCopy.setDelivered(completedPO);

        }
    }

    private EmpEnrollmentProcessBO getEmpEnrollmentPromostionEntity(EmpEnrollmentDtlTO empEnrollmentDtlTO,
            EmpRegisterDtlTO empRegisterDtlTO, String[] folder_info, String[] extras, ProjDocFileEntity projDocFileEntity, MultipartFile file) {
        EmpEnrollmentProcessBO processBO = new EmpEnrollmentProcessBO();
        EmpEnrollmentDtlHandler.convertPOJOTOEntity(processBO.getEmpEnrollmentDtlEntity(), empEnrollmentDtlTO,
                empRegisterRepository, empClassRepository, folder_info, extras, projDocFileEntity, file);
        EmpEnrollmentDtlHandler.convertProjEmpEntity(processBO.getProjEmpRigisterEntity(), empRegisterDtlTO,
                empEnrollmentDtlTO, empRegisterRepository, epsProjRepository, empClassRepository);

        processBO.getEmpEnrollmentDtlEntity().setProjEmpRigisterEntity(processBO.getProjEmpRigisterEntity());

        return processBO;
    }

    //from empEnrollement to empservicehistory adding record.
    private void cloneEmpToNewProj(EmpProjRigisterEntity oldEntity, EmpEnrollmentDtlTO empEnrollmentDtlTO, Long ProjId ) {
    	log.info("private void cloneEmpToNewProj");
    	
    	Long count = 0L;
    	Long temp = 0L;
    	List<EmpProjRigisterEntity> empProjRigisterEntitiy = projEmpRegisterRepository.findProjEmpRigisters(oldEntity.getEmpRegisterDtlEntity().getId());
    	for(EmpProjRigisterEntity empProjRigisterEntities : empProjRigisterEntitiy) {
    		 count = count+1; //for every iteration count increases 
    		 temp = count;
    	}
  
    	EmpProjRigisterEntity newEntity = new EmpProjRigisterEntity();

    	EmpProjRigisterEntity empProjRigisters = projEmpRegisterRepository.findEmpClassNames(temp, oldEntity.getEmpRegisterDtlEntity().getId());
    	if(CommonUtil.isNonBlankLong(empEnrollmentDtlTO.getEmpClassId())) {
		  EmpClassMstrEntity empClassMstrEntity = empClassRepository.findOne(empEnrollmentDtlTO.getEmpClassId());
		  	newEntity.setEmpClassMstrEntity(empClassMstrEntity);
		 } 
		 
    	newEntity.setTaxFileNum(oldEntity.getTaxFileNum());
    	newEntity.setStatus(StatusCodes.ACTIVE.getValue());
    	newEntity.setDeploymentId(oldEntity.getDeploymentId() + count);
    	newEntity.setIsLatest(RegisterConstants.SET_IS_LATEST);
    	newEntity.setEmpRegisterDtlEntity(oldEntity.getEmpRegisterDtlEntity());
    //	newEntity.setMobilaizationDate(oldEntity.getMobilaizationDate());
    	//setting the latest project Id
    	newEntity.setMobilaizationDate(empProjRigisters.getMobilaizationDate());
    	newEntity.setEnrollmentDate(oldEntity.getEnrollmentDate());
    	
    	
//		if(CommonUtil.isNonBlankLong(ProjId)) { 
//			ProjMstrEntity projMstrEntity = epsProjRepository.findOne(ProjId);
//			newEntity.setProjMstrEntity(projMstrEntity);
//			} setting the latest project Id
    	if(CommonUtil.isNonBlankLong(empProjRigisters.getProjMstrEntity().getProjectId())) {
    		ProjMstrEntity projMstrEntity = epsProjRepository.findOne(empProjRigisters.getProjMstrEntity().getProjectId());
    		newEntity.setProjMstrEntity(projMstrEntity);
    	}
		projEmpRegisterRepository.save(newEntity);
    	projEmpRegisterRepository.updateExistingEmpProjStatus( oldEntity.getId() ); 
 
    }
    private EmpProjRegisterPODtlEntity getProjEmpRegisterPODtlEntity(EmpEnrollmentDtlTO empEnrollmentDtlTO) {
        /*
         * The following code is used to increment cumulative qty in project PO
         */
        EmpProjRegisterPODtlEntity projEmpPODtlEntity = null;

        if (CommonUtil.isNonBlankLong(empEnrollmentDtlTO.getProjectPOTO().getPurchaseLabelKeyTO().getId())
                && CommonUtil.isNonBlankLong(empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getId())) {

            projEmpPODtlEntity = regEmpProjectPODtlRepository.getProjectPObyProjIdAndSchIdAndCompanySchId(
                    empEnrollmentDtlTO.getProjId(), empEnrollmentDtlTO.getProjectPOTO().getPurchaseLabelKeyTO().getId(),
                    empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getId(),
                    Long.valueOf(empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                            .get(CommonConstants.SCH_CMP_ID)));

            if (!CommonUtil.objectNotNull(projEmpPODtlEntity)) {
                projEmpPODtlEntity = new EmpProjRegisterPODtlEntity();
                ProjEmpRegisterPODtlHandler.convertEmpPOPOJOToEntity(empEnrollmentDtlTO, projEmpPODtlEntity,
                        epsProjRepository, purchaseOrderRepository, precontractEmpRepository,
                        precontractEmpCmpRepository, precontractServiceCmpRepository, precontractServiceRepository);
                projEmpPODtlEntity.setCumulative(new Long(1));
            }

            if (CommonUtil.isBlankLong(empEnrollmentDtlTO.getId())
                    && CommonUtil.isNonBlankLong(projEmpPODtlEntity.getId())) {
                Long qtyCumulate = new Long(0);
                if (CommonUtil.isNonBlankLong(projEmpPODtlEntity.getCumulative())) {
                    qtyCumulate = projEmpPODtlEntity.getCumulative();
                }
                qtyCumulate = qtyCumulate.longValue() + 1;
                projEmpPODtlEntity.setCumulative(qtyCumulate);
            }

        }

        return projEmpPODtlEntity;

    }

    public EmpServiceHistoryResp getEmpServiceHistory(ProjEmpServiceHistoryReq projEmpServiceHistoryReq) {
        EmpServiceHistoryResp resp = new EmpServiceHistoryResp();
        List<EmpProjRigisterEntity> empProjRigisterEntities = projEmpRegisterRepository
                .findProjEmpRigisters(projEmpServiceHistoryReq.getEmpId());

        if (CommonUtil.isListHasData(empProjRigisterEntities)) {
            for (EmpProjRigisterEntity empProjRigisterEntity : empProjRigisterEntities) {
            	List<ProjGeneralMstrEntity> projGeneralMstrEntityCopy = projGeneralRepositoryCopy.getGeneralValues(empProjRigisterEntity.getProjMstrEntity().getProjectId(),1);
                ProjEmpRegisterTO registerTO = EmpEnrollmentDtlHandler
                        .convertMobilizationDateEntityTO(empProjRigisterEntity);
                registerTO.setProfitCentreId(projGeneralMstrEntityCopy.get(0).getProfitCentreEntity().getName());
                registerTO.setFinanceCentreId(projGeneralMstrEntityCopy.get(0).getFinanceCentre());
                if (CommonUtil.objectNotNull(empProjRigisterEntity.getProjMstrEntity())
                        && CommonUtil.objectNotNull(empProjRigisterEntity.getEmpClassMstrEntity())) {
                    ProjEmpClassMstrEntity projEmpClassMstrEntity = projEmpClassRepository.getUserProjEmpClasses(
                            empProjRigisterEntity.getProjMstrEntity().getProjectId(),
                            empProjRigisterEntity.getEmpClassMstrEntity().getId(), StatusCodes.ACTIVE.getValue());
                    if (CommonUtil.objectNotNull(projEmpClassMstrEntity)) {
                        registerTO.setEmpContractName(projEmpClassMstrEntity.getTradeContrName());
                        registerTO.setEmpWorkUnionName(projEmpClassMstrEntity.getUnionName());
                        registerTO.setEmpCatgName(projEmpClassMstrEntity.getProjEmpCategory());
                    }
                }
                resp.getProjEmpRegisterTOs().add(registerTO);
            }
        }
        return resp;

    }

    public EmpServiceHistoryResp getEmpLatestServiceHistory(ProjEmpServiceHistoryReq projEmpServiceHistoryReq) {
        EmpServiceHistoryResp resp = new EmpServiceHistoryResp();
        EmpProjRigisterEntity empProjRigisterEntity = projEmpRegisterRepository
                .findLatestEmployeDeployment(projEmpServiceHistoryReq.getEmpId());
        if (CommonUtil.objectNotNull(empProjRigisterEntity)) {
            resp.getProjEmpRegisterTOs()
                    .add(EmpEnrollmentDtlHandler.convertMobilizationDateEntityTO(empProjRigisterEntity));
        }
        return resp;

    }

    public void saveEmpServiceHistory(ProjEmpRegistersSaveReq projEmpRegistersSaveReq) {
        ProjEmpRegisterTO projEmpRegisterTO = projEmpRegistersSaveReq.getProjEmpRegisterTO();
        System.out.println("Project Id: "+projEmpRegisterTO.getProjId()+"===="+projEmpRegisterTO.getEmpId());
        EmpProjRigisterEntity projEmpEntity = projEmpRegisterRepository
                .getProjEmpEntityByProjandEmp(projEmpRegisterTO.getProjId(), projEmpRegisterTO.getEmpId());
        EmpEnrollmentDtlHandler.convertProjEmpRegisterTOEntity(projEmpEntity, projEmpRegisterTO);
    }

    public EmpEnrollmentDtlTO getEmpEnrollment(long enrollId) {
    	System.out.println("getEmpEnrollment function:"+enrollId);
        EmpEnrollmentDtlEntity entity = empEnrollmentRepository.findOne(enrollId);
        EmpEnrollmentDtlTO enrollmentDTO = new EmpEnrollmentDtlTO();
        EmpEnrollmentDtlHandler.convertEntityToPOJO( entity, enrollmentDTO );
        enrollmentDTO.setContractDocument(entity.getContractDocument());
        enrollmentDTO.setContractDocumentFileType( entity.getContractDocumentFileType() );
        enrollmentDTO.setContractDocumentFileName( entity.getContractDocumentFileName() );
        if( entity.getProjDocFileEntity() != null )
        {
        	System.out.println("if block of getProjDocFileEntity");
        	enrollmentDTO.setContractDocumentFileSize( entity.getProjDocFileEntity().getFileSize() );
        	System.out.println( "doc file folder path:"+entity.getProjDocFileEntity().getFolderPath() );
        	System.out.println( "doc folder path:"+entity.getProjDocFileEntity().getFolderId().getUploadFolder() );
        	String file_path = entity.getProjDocFileEntity().getFolderId().getUploadFolder()+entity.getProjDocFileEntity().getFolderPath();
        	enrollmentDTO.setUploadFullPath( file_path );
        }        
        return enrollmentDTO;
    }

}
