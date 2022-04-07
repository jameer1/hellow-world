package com.rjtech.register.service.impl.plant;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.aws.common.s3.file.service.AswS3FileService;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
//import com.rjtech.procurement.model.PreContractsCmpEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceCmpEntityCopy;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;
import com.rjtech.procurement.repository.PrecontractServiceCmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractServiceRepositoryCopy;
import com.rjtech.procurement.repository.PurchaseOrderRepositoryCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.constans.AwsS3FileKeyConstants;
import com.rjtech.register.plant.dto.PlantPODocketDtlTO;
import com.rjtech.register.plant.dto.PlantProjPODtlTO;
import com.rjtech.register.plant.model.PlantPODocketDtlEntity;
import com.rjtech.register.plant.model.PlantProjPODtlEntity;
import com.rjtech.register.plant.model.PlantRegProjEntity;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.register.plant.model.PreContractsPlantCmpEntityCopy;
import com.rjtech.register.plant.req.PlantProjPODeliverySaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantProjPODeliveryResp;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.repository.plant.PlantDocketRepository;
import com.rjtech.register.repository.plant.PlantProjPORepository;
import com.rjtech.register.repository.plant.PlantRegProjRepository;
import com.rjtech.register.repository.plant.PlantRegisterRepository;
import com.rjtech.register.repository.plant.PrecontractPlantCmpRepositoryCopy;
import com.rjtech.register.repository.plant.PrecontractPlantRepositoryCopy;
import com.rjtech.register.service.handler.plant.PlantPODocketHandler;
import com.rjtech.register.service.handler.plant.PlantProjPOHandler;
import com.rjtech.register.service.handler.plant.PlantRegProjDtlHandler;
import com.rjtech.register.service.plant.PlantPOService;
import com.rjtech.register.util.RegisterUtil;
import com.rjtech.register.utils.RegisterCommonUtils;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.common.utils.UploadUtil;

@Service(value = "plantPOService")
@Transactional
public class PlantPOServiceImpl implements PlantPOService {

    private static final Logger log = LoggerFactory.getLogger(PlantPOServiceImpl.class);

    @Autowired
    private PlantRegisterRepository plantRegisterDtlRepository;

    @Autowired
    private PlantRegProjRepository plantRegProjRepository;

    @Autowired
    private PlantProjPORepository plantProjPORepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private PurchaseOrderRepositoryCopy purchaseOrderRepository;

    @Autowired
    private PrecontractPlantRepositoryCopy preContractsPlantRepository;

    @Autowired
    private PrecontractPlantCmpRepositoryCopy preContractPlantCmpRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private AswS3FileService aswS3FileService;

    @Autowired
    private PlantDocketRepository plantDocketRepository;

    @Autowired
    private PrecontractServiceCmpRepositoryCopy precontractServiceCmpRepository;

    @Autowired
    private PrecontractServiceRepositoryCopy precontractServiceRepository;
    
    @Autowired
    private UploadUtil uploadUtil;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    public PlantProjPODeliveryResp getPlantProjectPODtls(PlantProjectDtlGetReq plantProjectDtlGetReq) {
        PlantProjPODeliveryResp resp = new PlantProjPODeliveryResp();
        if (CommonUtil.isNonBlankLong(plantProjectDtlGetReq.getPlantId())) {
            PlantRegProjEntity plantRegProjEntity;
            if (plantProjectDtlGetReq.getPlantProjPODtlId() != null)
                plantRegProjEntity = plantRegProjRepository.findPlantPOProject(plantProjectDtlGetReq.getPlantId(),
                        plantProjectDtlGetReq.getPlantProjPODtlId(), plantProjectDtlGetReq.getStatus());
            else
            {
            	System.out.println("else condition getPlantProjPODtlId from getPlantProjectPODtls function"+plantProjectDtlGetReq.getPlantId()+"->"+plantProjectDtlGetReq.getPlantProjPODtlId());
            	int count = plantRegProjRepository.findPlantPOProjectCnt(plantProjectDtlGetReq.getPlantId(),
                        plantProjectDtlGetReq.getStatus());
            	System.out.println("count:"+count);
            	plantRegProjEntity = plantRegProjRepository.findPlantPOProject(plantProjectDtlGetReq.getPlantId(),
                        plantProjectDtlGetReq.getStatus());
            }
                
            if (CommonUtil.objectNotNull(plantRegProjEntity)) {
            	System.out.println("objectnotnull plantRegProjEntity");
                PlantProjPODtlTO plantProjPODtlTO = null;
                PlantProjPODtlEntity plantProjPODtlEntity = plantRegProjEntity.getPlantProjPODtlEntity();
                if (CommonUtil.objectNotNull(plantProjPODtlEntity)) {
                	System.out.println("objectnotnull plantProjPODtlEntity");
                    plantProjPODtlTO = PlantProjPOHandler.convertEntityToPOJO(plantProjPODtlEntity);
                    resp.setPlantProjPODtlTO(plantProjPODtlTO);
                    plantProjPODtlTO.setId(plantRegProjEntity.getId());
                    plantProjPODtlTO.setProjId(
                            (plantRegProjEntity.getProjId() != null) ? plantRegProjEntity.getProjId().getProjectId()
                                    : null);
                    plantProjPODtlTO.setPlantRegProjTO(PlantRegProjDtlHandler.convertEntityToPOJO(plantRegProjEntity));
                    plantProjPODtlTO
                            .setCommissionDate(CommonUtil.convertDateToString(plantRegProjEntity.getCommissionDate()));

                    List<PlantPODocketDtlEntity> plantPODocketDtlEntities = plantProjPODtlEntity
                            .getPlantPODocketDtlEntities();
                    if (CommonUtil.isListHasData(plantPODocketDtlEntities)) {
                        BigDecimal docketCumulate = new BigDecimal(0);
                        for (PlantPODocketDtlEntity plantDocketDtlEntity : plantPODocketDtlEntities) {
                            PlantPODocketDtlTO plantDocketDtlTO = PlantPODocketHandler
                                    .convertEntityToPOJO(plantDocketDtlEntity);
                            if(plantDocketDtlTO.getFileName() != null) {
                            	plantProjPODtlTO.setDocName(plantDocketDtlEntity.getDocumentFileName());
                            	plantProjPODtlTO.setDocId(plantDocketDtlEntity.getId());
                            }                           
                            plantProjPODtlTO.setOdoMeter(plantDocketDtlEntity.getOdoMeter());  
                            docketCumulate = docketCumulate.add(plantDocketDtlTO.getQuantity());
                            resp.getPlantProjPODtlTO().getPlantDocketDtlTOs().add(plantDocketDtlTO);
                        }
                        plantProjPODtlTO.setCumulative(docketCumulate);
                        plantProjPODtlTO.setQuantity(new BigDecimal(1));
                    }
                }
            }
        }
        return resp;
    }

    @Override
    public PlantProjPODeliveryResp savePlantProjectPODtls(PlantProjPODeliverySaveReq plantProjectPOSaveReq,
                MultipartFile[] files ) throws IOException {
        PlantProjPODtlTO plantProjPODtlTO = plantProjectPOSaveReq.getPlantProjPODtlTO();
        BigDecimal qty = new BigDecimal(1);
        BigDecimal docketCumulate = new BigDecimal(0);
        
        System.out.println("savePlantProjectPODtls function from PlantPOServiceImpl class");
        System.out.println(plantProjPODtlTO.getPurchaseSchLabelKeyTO().getId());
        System.out.println(plantProjPODtlTO.getPurchaseLabelKeyTO().getId());
        System.out.println(plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.SCH_CMP_ID));
        System.out.println(plantProjectPOSaveReq);
        
        PlantProjPODtlEntity plantProjPODtlEntity = plantProjPORepository.getPlantProjPODetails(
                plantProjectPOSaveReq.getProjId(), plantProjPODtlTO.getPurchaseLabelKeyTO().getId(),
                plantProjPODtlTO.getPurchaseSchLabelKeyTO().getId(), Long.valueOf(plantProjPODtlTO
                        .getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.SCH_CMP_ID)));
        /*PlantProjPODtlEntity plantProjPODtlEntity = plantProjPORepository.getPlantProjPODetails(
                plantProjectPOSaveReq.getProjId(), plantProjPODtlTO.getPurchaseLabelKeyTO().getId(),
                plantProjPODtlTO.getPurchaseSchLabelKeyTO().getId());*/
        if (CommonUtil.objectNullCheck(plantProjPODtlEntity)) {
        	System.out.println("if condition of objectnullcheck");
            plantProjPODtlEntity = PlantProjPOHandler.convertPOJOToEntity(plantProjPODtlTO, epsProjRepository,
                    purchaseOrderRepository, preContractsPlantRepository, preContractPlantCmpRepository,
                    precontractServiceCmpRepository, precontractServiceRepository, plantProjectPOSaveReq.getMode());
        }
        else
        {
        	System.out.println("else condition of objectnullcheck");
        }

        boolean fileUpdate = false;
        List<PlantPODocketDtlEntity> plantPODocketDtlEntities = plantProjPODtlEntity.getPlantPODocketDtlEntities();
        PlantProjPODeliveryResp plantProjPODeliveryResp = new PlantProjPODeliveryResp();
        String folder_category = plantProjectPOSaveReq.getFolderCategory();
        if (CommonUtil.isListHasData(plantProjectPOSaveReq.getPlantProjPODtlTO().getPlantDocketDtlTOs())) {
        	
        	ProjMstrEntity projMstrEntity = new ProjMstrEntity();
        	projMstrEntity.setProjectId( plantProjPODtlTO.getProjId() );
        	int i = 0;
            for (PlantPODocketDtlTO plantDocketDtlTO : plantProjectPOSaveReq.getPlantProjPODtlTO()
                    .getPlantDocketDtlTOs()) {
                PlantPODocketDtlEntity plantPODocketDtlEntity = null;
                ProjDocFileEntity projDocFileEntity = null;
                ProjDocFileEntity resProjDocFileEntity = null;
                UploadUtil uploadUtil = new UploadUtil();
                //if (CommonUtil.objectNullCheck(plantDocketDtlTO.getId())) {
                System.out.println("i:"+i);
                System.out.println(plantDocketDtlTO);
                /*if( plantDocketDtlTO.getId() != null ) {
                    System.out.println("else condition plantDocketDtlTO objectNullCheck");
                    System.out.println("docket id:"+plantDocketDtlTO.getId());
                    //plantPODocketDtlEntity = new PlantPODocketDtlEntity();
                    plantPODocketDtlEntity = PlantPODocketHandler.convertPOJOToEntity(plantDocketDtlTO,
                            empRegisterRepository);
                    plantPODocketDtlEntity.setPlantProjPODtlEntity(plantProjPODtlEntity);
                }
                else
                {
                	fileUpdate = true;
                    System.out.println("if condition plantDocketDtlTO objectNullCheck");
                    plantPODocketDtlEntity = PlantPODocketHandler.convertPOJOToEntity(plantDocketDtlTO,
                            empRegisterRepository);
                    plantPODocketDtlEntity.setPlantProjPODtlEntity(plantProjPODtlEntity);
                }*/
                Integer fileIndex = plantDocketDtlTO.getFileObjectIndex();
                if(plantProjPODtlTO.getPlantDocketDtlTOs().size() > 1 && plantProjPODtlTO.getProjPOId() != null) {
                       	plantPODocketDtlEntity = PlantPODocketHandler.convertPOJOToEntity(plantDocketDtlTO,
                                    empRegisterRepository);
                       	PlantProjPODtlEntity plantProjPODtlEntitys =  plantProjPORepository.findOne(plantProjPODtlTO.getProjPOId());
                       	plantPODocketDtlEntity.setPlantProjPODtlEntity(plantProjPODtlEntitys);     
                       	plantDocketRepository.save(plantPODocketDtlEntity);
                       	if(plantDocketDtlTO.getId() != null) {
                       		if(plantDocketDtlTO.getFileObjectIndex() != null) {
                           		PlantPODocketDtlEntity	plantPODocketDtlEntiteys = plantDocketRepository.findOne(plantDocketDtlTO.getId());		
                           	 fileIndex = plantDocketDtlTO.getFileObjectIndex();
                           	 if(files != null) {
                           		savePlantDocuments(plantDocketDtlTO,plantPODocketDtlEntiteys,files,plantProjectPOSaveReq,projMstrEntity,plantProjPODtlTO); 
                           	 }
                             
                           }
                       	}else
                        
                        if(plantDocketDtlTO.getId() == null) {
                        	if(plantDocketDtlTO.getFileObjectIndex() != null) {
                        		savePlantDocuments(plantDocketDtlTO,plantPODocketDtlEntity,files,plantProjectPOSaveReq,projMstrEntity,plantProjPODtlTO);
                           }
                        }
                        
                }
                if(plantProjPODtlTO.getPlantDocketDtlTOs().size() == 1) {
                	plantPODocketDtlEntity = PlantPODocketHandler.convertPOJOToEntity(plantDocketDtlTO,
                            empRegisterRepository);
                    plantPODocketDtlEntity.setPlantProjPODtlEntity(plantProjPODtlEntity);
                    plantPODocketDtlEntities.add(plantPODocketDtlEntity);
                    if(plantDocketDtlTO.getId() != null) {
                    	 if(plantDocketDtlTO.getFileObjectIndex() != null) {
                        		PlantPODocketDtlEntity	plantPODocketDtlEntiteys = plantDocketRepository.findOne(plantDocketDtlTO.getId());	
                        	 fileIndex = plantDocketDtlTO.getFileObjectIndex();
                  //        plantPODocketDtlEntiteys.setDocumentFileName(files[fileIndex].getOriginalFilename());
                          savePlantDocuments(plantDocketDtlTO,plantPODocketDtlEntiteys,files,plantProjectPOSaveReq,projMstrEntity,plantProjPODtlTO);
                    }                  
                      }else              
                    if(plantDocketDtlTO.getId() == null) {
                    	 savePlantDocuments(plantDocketDtlTO,plantPODocketDtlEntity,files,plantProjectPOSaveReq,projMstrEntity,plantProjPODtlTO);
                    }
                    
                } 
                if(plantProjPODtlTO.getPlantDocketDtlTOs().size() > 1 && plantProjPODtlTO.getProjPOId() == null) {
                	plantPODocketDtlEntity = PlantPODocketHandler.convertPOJOToEntity(plantDocketDtlTO,
                            empRegisterRepository);
                    plantPODocketDtlEntity.setPlantProjPODtlEntity(plantProjPODtlEntity);
                    plantPODocketDtlEntities.add(plantPODocketDtlEntity);
              //      plantPODocketDtlEntity.setProjDocFile( resProjDocFileEntity );
                    if(plantDocketDtlTO.getId() == null) {
                    	savePlantDocuments(plantDocketDtlTO,plantPODocketDtlEntity,files,plantProjectPOSaveReq,projMstrEntity,plantProjPODtlTO);
                    }else
                    if(plantDocketDtlTO.getId() != null) {
                    	if(plantDocketDtlTO.getFileObjectIndex() != null) {
                       		PlantPODocketDtlEntity	plantPODocketDtlEntiteys = plantDocketRepository.findOne(plantDocketDtlTO.getId());	
                       	 fileIndex = plantDocketDtlTO.getFileObjectIndex();
                  //       plantPODocketDtlEntiteys.setDocKey(files[fileIndex].getOriginalFilename());
                    //     plantPODocketDtlEntiteys.setProjDocFile(resProjDocFileEntity);
                         savePlantDocuments(plantDocketDtlTO,plantPODocketDtlEntiteys,files,plantProjectPOSaveReq,projMstrEntity,plantProjPODtlTO);
                       }
                    }
                    
                }
             //   Integer fileIndex = plantDocketDtlTO.getFileObjectIndex();
                docketCumulate = docketCumulate.add(plantDocketDtlTO.getQuantity());
          //      plantPODocketDtlEntities.add(plantPODocketDtlEntity);
         //       plantDocketRepository.save( plantPODocketDtlEntity );
                i++;
            }
            plantProjPODtlEntity.setCumulative(docketCumulate);
            plantProjPODtlEntity.getPlantPODocketDtlEntities().addAll(plantPODocketDtlEntities);
        }
        String big = docketCumulate.toString();
        System.out.println("big290  "+big);
     	 for(PlantPODocketDtlTO plantPODocketDtlTOs :plantProjectPOSaveReq.getPlantProjPODtlTO().getPlantDocketDtlTOs()) {
     		if(plantPODocketDtlTOs.getDeliveryType().equals("Part") && big.equals("1.00")) {
    			PlantRegisterDtlEntity plantRegisterDtlEntity = plantRegisterDtlRepository
                        .findPlantByPlantId(plantProjectPOSaveReq.getPlantId(), plantProjectPOSaveReq.getStatus());
    			if(CommonUtil.objectNullCheck(plantRegisterDtlEntity.getProjMstrEntity())) {
    				ProjMstrEntity projMstrEntity = epsProjRepository.findOne(plantProjPODtlTO.getProjId());
    				plantRegisterDtlEntity.setProjMstrEntity(projMstrEntity);
    				PlantRegProjEntity plantRegProjEntites = plantRegProjRepository.findOne(plantProjectPOSaveReq.getPlantId());
    			//	plantRegProjEntites.setIsLatest(RegisterCommonUtils.IS_LATEST_Y);
    				plantRegisterDtlRepository.save(plantRegisterDtlEntity);
    			}
    		}
     	 }
		
		 
        if(plantProjPODtlTO.getPlantDocketDtlTOs().size() == 1 || plantProjPODtlTO.getPlantDocketDtlTOs().size() > 1 && plantProjPODtlTO.getProjPOId() == null) {
        	 if (CommonUtil.isBlankLong(plantProjPODtlEntity.getId())) {
             	System.out.println("if block of getId function");
                 PlantRegisterDtlEntity plantRegisterDtlEntity = plantRegisterDtlRepository
                         .findPlantByPlantId(plantProjectPOSaveReq.getPlantId(), plantProjectPOSaveReq.getStatus());
                 for(PlantPODocketDtlTO plantPODocketDtlTO :plantProjectPOSaveReq.getPlantProjPODtlTO().getPlantDocketDtlTOs()) {
                 	if(plantPODocketDtlTO.getDeliveryType().equals("Full")) {
                 		 if (CommonUtil.objectNullCheck(plantRegisterDtlEntity.getProjMstrEntity())) {
                             ProjMstrEntity projMstrEntity = epsProjRepository.findOne(plantProjPODtlTO.getProjId());
                             plantRegisterDtlEntity.setProjMstrEntity(projMstrEntity);
                             plantRegisterDtlRepository.save(plantRegisterDtlEntity);
                         }
                 	}
			 
                 }
                
                 List<PlantRegProjEntity> plantRegProjEntities = new ArrayList<>();
                 PlantRegProjEntity plantRegProjEntity = new PlantRegProjEntity();
                 plantRegProjEntity.setProjId(plantProjPODtlEntity.getProjId());
                 plantRegProjEntity.setPlantRegisterDtlEntity(plantRegisterDtlEntity);
                 plantRegProjEntity.setStatus(plantProjPODtlEntity.getStatus());
                 if (CommonUtil.isNotBlankStr(plantProjPODtlTO.getCommissionDate())) {
                     plantRegProjEntity
                             .setCommissionDate(CommonUtil.convertStringToDate(plantProjPODtlTO.getCommissionDate()));
                     plantRegProjEntity.setPlantDeliveryStatus(RegisterUtil.PROJECT_PO_STATUS_COMPLETED);
                 } else {
                     plantRegProjEntity.setPlantDeliveryStatus(RegisterUtil.PROJECT_PO_STATUS_PARTIALLY_DELIVERY);
                 }
                 plantRegProjEntity.setPlantProjPODtlEntity(plantProjPODtlEntity);
                 for(PlantPODocketDtlTO plantPODocketDtlTOS :plantProjectPOSaveReq.getPlantProjPODtlTO().getPlantDocketDtlTOs()) {
                	 plantRegProjEntity.setOdoMeter(plantPODocketDtlTOS.getOdoMeter());
                 }  
                 plantRegProjEntity.setDeploymentId(1L);
                 plantRegProjEntity.setIsLatest(RegisterCommonUtils.IS_LATEST_Y);
                 plantRegProjEntities.add(plantRegProjEntity);
                 plantProjPODtlEntity.setPlantRegProjEntities(plantRegProjEntities);
                 plantRegProjEntity.setPlantProjPODtlEntity(plantProjPODtlEntity);
             } else if (RegisterUtil.PROJECT_PO_STATUS_PARTIALLY_DELIVERY
                     .equalsIgnoreCase(plantProjPODtlTO.getDeliveryStatus())) {
             	System.out.println("else block of getId function");
                 if (CommonUtil.isNotBlankStr(plantProjPODtlTO.getCommissionDate())) {
                     PlantRegProjEntity plantRegProjEntity = plantRegProjRepository
                             .findPlantPOProject(plantProjectPOSaveReq.getPlantId(), plantProjectPOSaveReq.getStatus());
                     plantRegProjEntity
                             .setCommissionDate(CommonUtil.convertStringToDate(plantProjPODtlTO.getCommissionDate()));
                     plantRegProjEntity.setPlantDeliveryStatus(RegisterUtil.PROJECT_PO_STATUS_COMPLETED);
                 }
             }
        }
        PlantProjPODtlEntity savedDtlEntity = null;
        if(plantProjPODtlTO.getPlantDocketDtlTOs().size() == 1 && plantProjPODtlTO.getProjPOId() == null) {
        	 savedDtlEntity = plantProjPORepository.save(plantProjPODtlEntity);
        }
        if(plantProjPODtlTO.getPlantDocketDtlTOs().size() > 1 && plantProjPODtlTO.getProjPOId() == null) {
       	 savedDtlEntity = plantProjPORepository.save(plantProjPODtlEntity);
       }
        
        /*if (file != null && fileUpdate) {
            for (PlantPODocketDtlEntity savedPlantPO : savedDtlEntity.getPlantPODocketDtlEntities()) {
                if (savedPlantPO.getDocKey() == null) {
                    // TODO it might fail on multiple, need to revisit this
                    try {                    	
                        String uniqueKey = AwsS3FileKeyConstants.PLANT_DOCKET_DTL + savedPlantPO.getId();
                        String keyString = aswS3FileService.uploadFile(file, uniqueKey);
                        savedPlantPO.setDocumentFileName(file.getOriginalFilename());
                        savedPlantPO.setDocumentFileType(file.getContentType());
                        savedPlantPO.setDocKey(keyString);
                    } catch (IOException e) {
                        log.error("Exception occurred while uploading the file to s3 - ", e);
                    }
                }
            }
        }*/
        
        // Update data in procurement
        int compareqty = qty.compareTo(docketCumulate);
        String schItemProcureType = plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                .get(CommonConstants.PROCUREMENT_MASTER_TYPE);
        if (compareqty == 0 && CommonUtil.isNotBlankStr(plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                .get(CommonConstants.SCH_ITEM_CMP_ID))) {
            Long cecId = Long.parseLong(plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.SCH_ITEM_CMP_ID));
            if (schItemProcureType.equals("P")) {
                PreContractsPlantCmpEntityCopy cmpEntityCopy = preContractPlantCmpRepository.findOne(cecId);

                // Add quantity
                if (cmpEntityCopy.getReceivedQty() != null)
                    cmpEntityCopy.setReceivedQty(cmpEntityCopy.getReceivedQty() + 1);
                else
                    cmpEntityCopy.setReceivedQty(1);

                if ((cmpEntityCopy.getQuantity() - cmpEntityCopy.getReceivedQty()) <= 0) {
                    cmpEntityCopy.setComplete(true);
                }

                if (cmpEntityCopy.isComplete()) {
                    PreContractsCmpEntity preContractsCmpEntity = cmpEntityCopy.getPreContractsCmpEntity();
                    completePurchaseOrder(schItemProcureType, preContractsCmpEntity);
                }

            } else if (schItemProcureType.equals("S")) {
                PreContractsServiceCmpEntity cmpEntityCopy = precontractServiceCmpRepository.findOne(cecId);

                // Add quantity
                if (cmpEntityCopy.getReceivedQuantity() != null)
                    cmpEntityCopy.setReceivedQuantity(cmpEntityCopy.getReceivedQuantity() + 1);
                else
                    cmpEntityCopy.setReceivedQuantity(1);

                if ((cmpEntityCopy.getQuantity() - cmpEntityCopy.getReceivedQuantity()) <= 0) {
                    cmpEntityCopy.setComplete(true);
                }

                if (cmpEntityCopy.isComplete()) {
                    PreContractsCmpEntity preContractsCmpEntity = cmpEntityCopy.getPreContractsCmpEntity();
                    completePurchaseOrder(schItemProcureType, preContractsCmpEntity);
                }

            }

        }
        
        /*for (PlantPODocketDtlEntity savedPlantPO : savedDtlEntity.getPlantPODocketDtlEntities()) {                       	
        	//String uniqueKey = AwsS3FileKeyConstants.PLANT_DOCKET_DTL + savedPlantPO.getId();
        	plantProjPODeliveryResp.getPlantProjPODtlTO().add( PlantProjPOHandler.convertEntityToPOJO( savedPlantPO ) );
        }*/
        if(plantProjPODtlTO.getPlantDocketDtlTOs().size() == 1 && plantProjPODtlTO.getProjPOId() == null) {
        	plantProjPODeliveryResp.getPlantProjPODtlTOs().add( PlantProjPOHandler.convertEntityToPOJO( savedDtlEntity ) );
        }
        if(plantProjPODtlTO.getPlantDocketDtlTOs().size() > 1 && plantProjPODtlTO.getProjPOId() == null) {
        	 plantProjPODeliveryResp.getPlantProjPODtlTOs().add( PlantProjPOHandler.convertEntityToPOJO( savedDtlEntity ) );
          }
       
        //return savedDtlEntity;
        return plantProjPODeliveryResp;
    }
    
	
	public void savePlantDocuments(PlantPODocketDtlTO plantDocketDtlTO,PlantPODocketDtlEntity plantPODocketDtlEntity,MultipartFile[] files, PlantProjPODeliverySaveReq plantProjectPOSaveReq, ProjMstrEntity projMstrEntity, PlantProjPODtlTO plantProjPODtlTO) throws IOException{
		Integer fileIndex = plantDocketDtlTO.getFileObjectIndex();
		String folder_category = plantProjectPOSaveReq.getFolderCategory();
		 ProjDocFileEntity projDocFileEntity = null;
         ProjDocFileEntity resProjDocFileEntity = null;
		if( fileIndex != null && files[fileIndex].getOriginalFilename().equalsIgnoreCase( plantDocketDtlTO.getFileName() ) ) {
				//System.out.println("File Name:"+preContractDocsTO.getName()+" projd id:"+preContractDocsTO.getProjId());
				System.out.println("If condition of fileIndex");
		//		fileUpdate = true;
				ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( folder_category );
				projDocFileEntity = new ProjDocFileEntity();
				resProjDocFileEntity = new ProjDocFileEntity();
				if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
         	{
					System.out.println("If condition of upload file");
					System.out.println(folder);
					System.out.println(files[fileIndex].getOriginalFilename()+"->"+files[fileIndex].getContentType()+"->"+files[fileIndex].getSize());
 				projDocFileEntity.setFolderId( folder );
 	        	projDocFileEntity.setName( files[fileIndex].getOriginalFilename() );
 	        	projDocFileEntity.setVersion( String.valueOf( 1 ) );
 	        	projDocFileEntity.setFileType( files[fileIndex].getContentType() );
 	        	projDocFileEntity.setFileSize( UploadUtil.bytesIntoHumanReadable( files[fileIndex].getSize() ) );
 	        	projDocFileEntity.setStatus( 1 );
 	        	projDocFileEntity.setProjectId( projMstrEntity );
 	        	System.out.println("/" + String.valueOf( plantProjPODtlTO.getProjId() ) + "/" + String.valueOf( plantProjectPOSaveReq.getPlantId() ) + "/" + String.valueOf( plantProjPODtlTO.getPurchaseLabelKeyTO().getId() ) + "/" + String.valueOf( plantProjPODtlTO.getPurchaseSchLabelKeyTO().getId() ) );
 	        	projDocFileEntity.setFolderPath( "/" + String.valueOf( plantProjPODtlTO.getProjId() ) + "/" + String.valueOf( plantProjectPOSaveReq.getPlantId() ) + "/" + String.valueOf( plantProjPODtlTO.getPurchaseLabelKeyTO().getId() ) + "/" + String.valueOf( plantProjPODtlTO.getPurchaseSchLabelKeyTO().getId() ) );
 				resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
 				System.out.println("resProjDocFileEntity278 "+resProjDocFileEntity);
         	}
				String extras[] = { String.valueOf( plantProjPODtlTO.getProjId() ), String.valueOf( plantProjectPOSaveReq.getPlantId() ), String.valueOf( plantProjPODtlTO.getPurchaseLabelKeyTO().getId() ), String.valueOf( plantProjPODtlTO.getPurchaseSchLabelKeyTO().getId() ) };
				//String[] extras = { String.valueOf( plantDocketDtlTO.getProjId() ), String.valueOf( plantDocketDtlTO.getPlantId() ) };
				uploadUtil.uploadFile( files[fileIndex], folder.getName(), folder.getUploadFolder(), extras );	
				plantPODocketDtlEntity.setProjDocFile( resProjDocFileEntity );
				plantPODocketDtlEntity.setDocumentFileName(resProjDocFileEntity.getName());
			}
    }
    /*@Override
    public PlantProjPODtlEntity savePlantProjectPODtls(PlantProjPODeliverySaveReq plantProjectPOSaveReq,
            MultipartFile file) {
        PlantProjPODtlTO plantProjPODtlTO = plantProjectPOSaveReq.getPlantProjPODtlTO();
        BigDecimal qty = new BigDecimal(1);
        BigDecimal docketCumulate = new BigDecimal(0);

        PlantProjPODtlEntity plantProjPODtlEntity = plantProjPORepository.getPlantProjPODetails(
                plantProjectPOSaveReq.getProjId(), plantProjPODtlTO.getPurchaseLabelKeyTO().getId(),
                plantProjPODtlTO.getPurchaseSchLabelKeyTO().getId(), Long.valueOf(plantProjPODtlTO
                        .getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.SCH_CMP_ID)));
        if (CommonUtil.objectNullCheck(plantProjPODtlEntity)) {
            plantProjPODtlEntity = PlantProjPOHandler.convertPOJOToEntity(plantProjPODtlTO, epsProjRepository,
                    purchaseOrderRepository, preContractsPlantRepository, preContractPlantCmpRepository,
                    precontractServiceCmpRepository, precontractServiceRepository);
        }

        boolean fileUpdate = false;
        List<PlantPODocketDtlEntity> plantPODocketDtlEntities = plantProjPODtlEntity.getPlantPODocketDtlEntities();

        if (CommonUtil.isListHasData(plantProjectPOSaveReq.getPlantProjPODtlTO().getPlantDocketDtlTOs())) {
            for (PlantPODocketDtlTO plantDocketDtlTO : plantProjectPOSaveReq.getPlantProjPODtlTO()
                    .getPlantDocketDtlTOs()) {
                PlantPODocketDtlEntity plantPODocketDtlEntity = null;
                if (CommonUtil.objectNullCheck(plantDocketDtlTO.getId())) {
                    fileUpdate = true;
                    plantPODocketDtlEntity = PlantPODocketHandler.convertPOJOToEntity(plantDocketDtlTO,
                            empRegisterRepository);
                    plantPODocketDtlEntity.setPlantProjPODtlEntity(plantProjPODtlEntity);
                }
                docketCumulate = docketCumulate.add(plantDocketDtlTO.getQuantity());
                plantPODocketDtlEntities.add(plantPODocketDtlEntity);
            }
            plantProjPODtlEntity.setCumulative(docketCumulate);
            plantProjPODtlEntity.getPlantPODocketDtlEntities().addAll(plantPODocketDtlEntities);
        }

        if (CommonUtil.isBlankLong(plantProjPODtlEntity.getId())) {
            PlantRegisterDtlEntity plantRegisterDtlEntity = plantRegisterDtlRepository
                    .findPlantByPlantId(plantProjectPOSaveReq.getPlantId(), plantProjectPOSaveReq.getStatus());
            if (CommonUtil.objectNullCheck(plantRegisterDtlEntity.getProjMstrEntity())) {
                ProjMstrEntity projMstrEntity = epsProjRepository.findOne(plantProjPODtlTO.getProjId());
                plantRegisterDtlEntity.setProjMstrEntity(projMstrEntity);
                plantRegisterDtlRepository.save(plantRegisterDtlEntity);
            }
            List<PlantRegProjEntity> plantRegProjEntities = new ArrayList<>();
            PlantRegProjEntity plantRegProjEntity = new PlantRegProjEntity();
            plantRegProjEntity.setProjId(plantProjPODtlEntity.getProjId());
            plantRegProjEntity.setPlantRegisterDtlEntity(plantRegisterDtlEntity);
            plantRegProjEntity.setStatus(plantProjPODtlEntity.getStatus());
            if (CommonUtil.isNotBlankStr(plantProjPODtlTO.getCommissionDate())) {
                plantRegProjEntity
                        .setCommissionDate(CommonUtil.convertStringToDate(plantProjPODtlTO.getCommissionDate()));
                plantRegProjEntity.setPlantDeliveryStatus(RegisterUtil.PROJECT_PO_STATUS_COMPLETED);
            } else {
                plantRegProjEntity.setPlantDeliveryStatus(RegisterUtil.PROJECT_PO_STATUS_PARTIALLY_DELIVERY);
            }
            plantRegProjEntity.setPlantProjPODtlEntity(plantProjPODtlEntity);
            plantRegProjEntity.setOdoMeter(plantProjPODtlTO.getOdoMeter());
            plantRegProjEntity.setDeploymentId(1L);
            plantRegProjEntity.setIsLatest(RegisterCommonUtils.IS_LATEST_Y);
            plantRegProjEntities.add(plantRegProjEntity);
            plantProjPODtlEntity.setPlantRegProjEntities(plantRegProjEntities);
            plantRegProjEntity.setPlantProjPODtlEntity(plantProjPODtlEntity);
        } else if (RegisterUtil.PROJECT_PO_STATUS_PARTIALLY_DELIVERY
                .equalsIgnoreCase(plantProjPODtlTO.getDeliveryStatus())) {
            if (CommonUtil.isNotBlankStr(plantProjPODtlTO.getCommissionDate())) {
                PlantRegProjEntity plantRegProjEntity = plantRegProjRepository
                        .findPlantPOProject(plantProjectPOSaveReq.getPlantId(), plantProjectPOSaveReq.getStatus());
                plantRegProjEntity
                        .setCommissionDate(CommonUtil.convertStringToDate(plantProjPODtlTO.getCommissionDate()));
                plantRegProjEntity.setPlantDeliveryStatus(RegisterUtil.PROJECT_PO_STATUS_COMPLETED);
            }
        }
        PlantProjPODtlEntity savedDtlEntity = plantProjPORepository.save(plantProjPODtlEntity);

        if (file != null && fileUpdate) {
            for (PlantPODocketDtlEntity savedPlantPO : savedDtlEntity.getPlantPODocketDtlEntities()) {
                if (savedPlantPO.getDocKey() == null) {
                    // TODO it might fail on multiple, need to revisit this
                    try {
                        String uniqueKey = AwsS3FileKeyConstants.PLANT_DOCKET_DTL + savedPlantPO.getId();
                        String keyString = aswS3FileService.uploadFile(file, uniqueKey);
                        savedPlantPO.setDocumentFileName(file.getOriginalFilename());
                        savedPlantPO.setDocumentFileType(file.getContentType());
                        savedPlantPO.setDocKey(keyString);
                    } catch (IOException e) {
                        log.error("Exception occurred while uploading the file to s3 - ", e);
                    }
                }
            }
        }

        // Update data in procurement
        int compareqty = qty.compareTo(docketCumulate);
        String schItemProcureType = plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                .get(CommonConstants.PROCUREMENT_MASTER_TYPE);
        if (compareqty == 0 && CommonUtil.isNotBlankStr(plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                .get(CommonConstants.SCH_ITEM_CMP_ID))) {
            Long cecId = Long.parseLong(plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.SCH_ITEM_CMP_ID));
            if (schItemProcureType.equals("P")) {
                PreContractsPlantCmpEntityCopy cmpEntityCopy = preContractPlantCmpRepository.findOne(cecId);

                // Add quantity
                if (cmpEntityCopy.getReceivedQty() != null)
                    cmpEntityCopy.setReceivedQty(cmpEntityCopy.getReceivedQty() + 1);
                else
                    cmpEntityCopy.setReceivedQty(1);

                if ((cmpEntityCopy.getQuantity() - cmpEntityCopy.getReceivedQty()) <= 0) {
                    cmpEntityCopy.setComplete(true);
                }

                if (cmpEntityCopy.isComplete()) {
                    PreContractsCmpEntityCopy preContractsCmpEntity = cmpEntityCopy.getPreContractsCmpEntity();
                    completePurchaseOrder(schItemProcureType, preContractsCmpEntity);
                }

            } else if (schItemProcureType.equals("S")) {
                PreContractsServiceCmpEntityCopy cmpEntityCopy = precontractServiceCmpRepository.findOne(cecId);

                // Add quantity
                if (cmpEntityCopy.getReceivedQuantity() != null)
                    cmpEntityCopy.setReceivedQuantity(cmpEntityCopy.getReceivedQuantity() + 1);
                else
                    cmpEntityCopy.setReceivedQuantity(1);

                if ((cmpEntityCopy.getQuantity() - cmpEntityCopy.getReceivedQuantity()) <= 0) {
                    cmpEntityCopy.setComplete(true);
                }

                if (cmpEntityCopy.isComplete()) {
                    PreContractsCmpEntityCopy preContractsCmpEntity = cmpEntityCopy.getPreContractsCmpEntity();
                    completePurchaseOrder(schItemProcureType, preContractsCmpEntity);
                }

            }

        }
        return savedDtlEntity;
    }*/

    private void completePurchaseOrder(String schItemProcureType, PreContractsCmpEntity preContractsCmpEntity) {
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
                    if (complete.equals(schItemProcureType)) {
                        alreadyCompleted = true;
                        break;
                    }
                }
            }

            // If procureType is not added, add it
            if (!alreadyCompleted) {
                if (completeProcureType != null
                        && completeProcureType.substring(completeProcureType.length() - 1).equals("#")) {
                    completeProcureType = completeProcureType.concat(schItemProcureType);
                    purchaseOrderEntityCopy.setCompleteProcureType(completeProcureType);
                } else {
                    purchaseOrderEntityCopy.setCompleteProcureType(schItemProcureType);
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

    @Override
    public PlantPODocketDtlTO downloadPlantDocment(long plantPOId) {
        PlantPODocketDtlEntity entity = plantDocketRepository.findOne(plantPOId);
        String docKey = entity.getDocKey();
        PlantPODocketDtlTO docketDtlTO = new PlantPODocketDtlTO();
        try {
            byte[] file = aswS3FileService.downloadFile(docKey);
            docketDtlTO.setDocContent(file);
            docketDtlTO.setDocName(entity.getDocumentFileName());
            docketDtlTO.setDocType(entity.getDocumentFileType());
        } catch (IOException e) {
            log.error("Exception occurred while uploading the file to s3 - ", e);
        }
        return docketDtlTO;
    }

}
