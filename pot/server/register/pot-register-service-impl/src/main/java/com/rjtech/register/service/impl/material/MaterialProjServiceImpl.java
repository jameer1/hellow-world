package com.rjtech.register.service.impl.material;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.aws.common.s3.file.service.AswS3FileService;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.constants.AwsS3FileKeyConstants;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.procurement.model.PreContractsMaterialCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
//import com.rjtech.procurement.model.PreContractsMaterialCmpEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceCmpEntityCopy;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;
import com.rjtech.procurement.repository.PrecontractMaterialCmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractServiceCmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractServiceRepositoryCopy;
import com.rjtech.procurement.repository.PurchaseOrderRepositoryCopy;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjMaterialClassRepository;
import com.rjtech.register.material.dto.MaterialPODeliveryDocketTO;
import com.rjtech.register.material.dto.MaterialProjDtlTO;
import com.rjtech.register.material.dto.MaterialProjSchItemTO;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.material.model.MaterialProjLedgerEntity;
import com.rjtech.register.material.model.MaterialSchLocCountEntity;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.register.material.req.MaterialProjSaveReq;
import com.rjtech.register.material.req.MaterialSchItemsReq;
import com.rjtech.register.material.resp.MaterialDeliveryDocketResp;
import com.rjtech.register.material.resp.MaterialProjResp;
import com.rjtech.register.material.resp.MaterialSchItemsResp;
import com.rjtech.register.repository.material.CopyMaterialClassRepository;
import com.rjtech.register.repository.material.CopyProjStoreStockRepository;
import com.rjtech.register.repository.material.MaterialDeliveryDocketRepository;
import com.rjtech.register.repository.material.MaterialProjLegderRepository;
import com.rjtech.register.repository.material.MaterialProjRepository;
import com.rjtech.register.repository.material.MaterialSchLocCountRepository;
import com.rjtech.register.repository.material.PrecontractMaterialRepositoryCopy;
import com.rjtech.register.service.handler.material.MaterialPODeliveryDocketHandler;
import com.rjtech.register.service.handler.material.MaterialProjDtlHandler;
import com.rjtech.register.service.handler.material.ProjMaterialLedgerHandler;
import com.rjtech.register.service.material.MaterialProjService;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.common.utils.UploadUtil;

@Service(value = "materialProjService")
@RJSService(modulecode = "materialProjService")
@Transactional
public class MaterialProjServiceImpl implements MaterialProjService {

    private static final Logger log = LoggerFactory.getLogger(MaterialProjServiceImpl.class);

    @Autowired
    private MaterialProjRepository materialProjRepository;

    @Autowired
    private MaterialProjLegderRepository materialProjLegderRepository;

    @Autowired
    private MaterialDeliveryDocketRepository materialDeliveryDocketRepository;

    @Autowired
    private MaterialSchLocCountRepository materialSchLocCountRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CopyProjStoreStockRepository projStoreStockRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private PrecontractMaterialRepositoryCopy precontractMaterialRepository;

    @Autowired
    private CopyMaterialClassRepository copyMaterialClassRepository;

    @Autowired
    private PurchaseOrderRepositoryCopy copyPurchaseOrderRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProjMaterialClassRepository projMaterialClassRespository;

    @Autowired
    private PrecontractMaterialCmpRepositoryCopy precontractMaterialCmpRepository;

    @Autowired
    private PrecontractServiceCmpRepositoryCopy precontractServiceCmpRepository;

    @Autowired
    private PrecontractServiceRepositoryCopy precontractServiceRepository;

    @Autowired
    private AswS3FileService awsAswS3FileService;
    
    @Autowired
    private ProjDocFolderRepository projDocFolderRepository;
    
    @Autowired
    private ProjDocFileRepository projDocFileRepository;
    

    public MaterialProjResp getProjMaterialSchItems(MaterialGetReq materialGetReq) {
        List<MaterialProjDtlEntity> materialProjDtlEntities = materialProjRepository
                .getProjMaterialSchItems(materialGetReq.getProjId(), materialGetReq.getStatus());
        MaterialProjResp materialProjResp = new MaterialProjResp();
        if (CommonUtil.isListHasData(materialProjDtlEntities)) {
            for (MaterialProjDtlEntity materialProjDtlEntity : materialProjDtlEntities) {
                materialProjResp.getMaterialProjDtlTOs()
                        .add(MaterialProjDtlHandler.convertEntityToPOJO(materialProjDtlEntity));
            }
        }
        return materialProjResp;
    }

    public MaterialProjResp getMaterialSchItemsByPurchaseOrder(MaterialGetReq materialGetReq) {
        List<MaterialProjDtlEntity> materialProjDtlEntities = materialProjRepository
                .findMaterialSchItemsByPurchaseOrder(materialGetReq.getPurId(), materialGetReq.getStatus());
        MaterialProjResp materialProjResp = new MaterialProjResp();
        if (CommonUtil.isListHasData(materialProjDtlEntities)) {
            for (MaterialProjDtlEntity materialProjDtlEntity : materialProjDtlEntities) {
                MaterialProjDtlTO materialProjDtlTO = MaterialProjDtlHandler.convertEntityToPOJO(materialProjDtlEntity);
                if (CommonUtil.isListHasData(materialProjDtlEntity.getMaterialSchLocCountEntities())) {
                    MaterialSchLocCountEntity materialSchLocCountEntity = materialProjDtlEntity
                            .getMaterialSchLocCountEntities().get(0);
                    StockMstrEntity stockMstrEntity = materialSchLocCountEntity.getStockId();
                    if (null != stockMstrEntity) {
                        materialProjDtlTO.setStockId(stockMstrEntity.getId());
                    }
                    ProjStoreStockMstrEntity projStoreStockMstrEntity = materialSchLocCountEntity.getProjStockId();
                    if (null != projStoreStockMstrEntity) {
                        materialProjDtlTO.setProjStockId(projStoreStockMstrEntity.getId());
                    }
                    materialProjDtlTO.setSchLocId(materialSchLocCountEntity.getId());
                    if (CommonUtil.isNotBlankStr(materialSchLocCountEntity.getStockCode())) {
                        materialProjDtlTO.setDeliveryPlace(materialSchLocCountEntity.getStockCode());
                    } else {
                        materialProjDtlTO.setDeliveryPlace(materialSchLocCountEntity.getProjStockCode());
                    }
                }
                materialProjResp.getMaterialProjDtlTOs().add(materialProjDtlTO);
            }
        }
        return materialProjResp;
    }

    public MaterialProjResp getProjMaterialSchDetails(MaterialFilterReq materialGetReq) {
        MaterialProjResp materialProjResp = new MaterialProjResp();
        Date fromDate;
        Date toDate;
        if (CommonUtil.isNotBlankStr(materialGetReq.getFromDate())
                && CommonUtil.isNotBlankStr(materialGetReq.getToDate())) {
            fromDate = CommonUtil.convertStringToDate(materialGetReq.getFromDate());
            toDate = CommonUtil.convertStringToDate(materialGetReq.getToDate());

        } else {
            toDate = new Date();
            fromDate = CommonUtil.substarctInputMonths(toDate, -1);
            materialProjResp.setFromDate(CommonUtil.convertDateToString(fromDate));
            materialProjResp.setToDate(CommonUtil.convertDateToString(toDate));
        }
        List<MaterialProjDtlEntity> materialProjDtlEntities = materialProjRepository
                .getProjMaterialSchItemsByFilter(materialGetReq.getProjList(), fromDate, toDate);
        if (CommonUtil.isListHasData(materialProjDtlEntities)) {
            getMaterialProjResp(materialProjDtlEntities, materialProjResp);
        }
        return materialProjResp;

    }

    private void getMaterialProjResp(List<MaterialProjDtlEntity> materialProjDtlEntities,
            MaterialProjResp materialProjResp) {
        for (MaterialProjDtlEntity materialProjDtlEntity : materialProjDtlEntities) {
            MaterialProjDtlTO materialProjDtlTO = MaterialProjDtlHandler.convertEntityToPOJO(materialProjDtlEntity);
            if (CommonUtil.isListHasData(materialProjDtlEntity.getMaterialPODeliveryDocketEntities())) {
                BigDecimal creditQty = new BigDecimal(0);
                for (MaterialPODeliveryDocketEntity materialPODeliveryDocketEntity : materialProjDtlEntity
                        .getMaterialPODeliveryDocketEntities()) {
                    materialProjDtlTO.getMaterialPODeliveryDocketTOs()
                            .add(MaterialPODeliveryDocketHandler.convertEntityToPOJO(materialPODeliveryDocketEntity));
                    creditQty = creditQty.add(materialPODeliveryDocketEntity.getReceivedQty());
                    materialProjDtlTO.setCumulativeQty(creditQty);
                }
            }
            materialProjResp.getMaterialProjDtlTOs().add(materialProjDtlTO);
        }

    }

    public MaterialDeliveryDocketResp getMaterialDeliveryDockets(MaterialGetReq materialGetReq) {
        MaterialDeliveryDocketResp materialDeliveryDocketResp = new MaterialDeliveryDocketResp();
        List<MaterialPODeliveryDocketEntity> materialPODeliveryDocketEntities = materialDeliveryDocketRepository
                .findMaterialDeliveryDocketDetails(materialGetReq.getMaterialId(), materialGetReq.getStatus());
        if (CommonUtil.isListHasData(materialPODeliveryDocketEntities)) {
            for (MaterialPODeliveryDocketEntity materialPODeliveryDocketEntity : materialPODeliveryDocketEntities) {
                materialDeliveryDocketResp.getMaterialPODeliveryDocketTOs()
                        .add(MaterialPODeliveryDocketHandler.convertEntityToPOJO(materialPODeliveryDocketEntity));
            }
        }
        return materialDeliveryDocketResp;
    }

    public void saveProjMaterialSchItems(MaterialProjSaveReq materialProjSaveReq) {
        MaterialProjDtlEntity materialProjDtlEntity = null;

        List<MaterialProjDtlEntity> materialProjDtlEntities = new ArrayList<MaterialProjDtlEntity>();
        for (MaterialProjDtlTO materialProjDtlTO : materialProjSaveReq.getMaterialProjDtlTOs()) {
            materialProjDtlEntity = MaterialProjDtlHandler.convertPOJOToEntity(materialProjDtlTO, epsProjRepository,
                    precontractMaterialRepository, copyMaterialClassRepository, copyPurchaseOrderRepository,
                    companyRepository, precontractMaterialCmpRepository, precontractServiceCmpRepository,
                    precontractServiceRepository);
            if (CommonUtil.isBlankLong(materialProjDtlTO.getId())) {
                MaterialSchLocCountEntity materialSchLocCountEntity = new MaterialSchLocCountEntity();
                materialSchLocCountEntity.setMaterialProjDtlEntity(materialProjDtlEntity);
                if (materialProjDtlTO.getStockId() != null) {
                    StockMstrEntity stockMstrEntity = stockRepository.findOne(materialProjDtlTO.getStockId());
                    materialSchLocCountEntity.setStockId(stockMstrEntity);
                }
                if (materialProjDtlTO.getProjStockId() != null) {
                    ProjStoreStockMstrEntity projStoreStockMstrEntity = projStoreStockRepository
                            .findOne(materialProjDtlTO.getProjStockId());
                    materialSchLocCountEntity.setProjStockId(projStoreStockMstrEntity);
                }
                materialSchLocCountEntity.setStatus(StatusCodes.ACTIVE.getValue());
                if (CommonUtil.isNonBlankLong(materialProjDtlTO.getStockId())) {
                    materialSchLocCountEntity.setStockCode(materialProjDtlTO.getDeliveryPlace());
                } else {
                    materialSchLocCountEntity.setProjStockCode(materialProjDtlTO.getDeliveryPlace());
                }
                materialSchLocCountEntity.setBaseLocation(true);
                materialProjDtlEntity.getMaterialSchLocCountEntities().add(materialSchLocCountEntity);
            }
            materialProjDtlEntities.add(materialProjDtlEntity);
        }
        materialProjRepository.save(materialProjDtlEntities);
    }

    public void saveProjMaterialSchDocketDetails(MultipartFile[] files, MaterialProjSaveReq materialProjSaveReq) {
    	String master_folder = materialProjSaveReq.getFolderCategory();
    	ProjDocFolderEntity folder = projDocFolderRepository.findByNameAndProjId( master_folder );
    	System.out.println("material id:"+materialProjSaveReq.getMaterialId()+" purid:"+materialProjSaveReq.getPurOrderId()+" schItemId:"+materialProjSaveReq.getSchItemId());
    	String dir_path = folder.getUploadFolder();
    	String upload_path = "/"+materialProjSaveReq.getMaterialId()+"/"+materialProjSaveReq.getPurOrderId()+"/"+materialProjSaveReq.getSchItemId();
        for (MaterialProjDtlTO materialProjDtlTO : materialProjSaveReq.getMaterialProjDtlTOs()) {
            MaterialProjDtlEntity materialProjDtlEntity = materialProjRepository
                    .save(populateProjMaterialEntity(materialProjDtlTO,folder,materialProjSaveReq.getUserId(),upload_path));            
            materialProjDtlEntity.getMaterialPODeliveryDocketEntities().forEach(materialPoDock -> {
            	System.out.println(materialPoDock.getId());
            	//setting asset id
            	materialProjDtlEntity.setAssetType(materialProjSaveReq.getAssetType());
                if (materialPoDock.getFileName() != null) {
                    try {
                    	/*if( ApplicationConstant.UPLOAD_FILE_TO.equals("LOCAL") )
                    	{
                    		String extras[] = { String.valueOf( projMstrEntity.getProjectId() ), String.valueOf( materialPoDock.getId() ) };
                    		System.out.println("if block");
                    		//uploadFile( file, String master_folder, String dir_path, String[] extras )
                    	}
                    	else
                    	{*/
                    		checkAndUpdateFileTOAwsS3(materialPoDock, addFileDetailsToTo(files, materialPoDock),
                                    AwsS3FileKeyConstants.DELIVERY_SUPPLY_DOC, materialProjSaveReq.getPurOrderId(), materialProjSaveReq.getSchItemId());
                    	//}                        
                    } catch (IOException e) {
                        log.error("Could not save file", e);
                    }
                }
            });
            if (CommonUtil.objectNotNull(files) && files.length != 0) {
                materialProjRepository.save(materialProjDtlEntity);
            }
        }
    }

    public void uploadFileToAwsS3(MaterialPODeliveryDocketEntity materialProjDtlEntity, MultipartFile multipartFile,
            String uniqueKeyPrefix) throws IOException {
        // Upload file to AWS S3
        String fileKey = awsAswS3FileService.uploadFile(multipartFile,
                uniqueKeyPrefix + "_" + materialProjDtlEntity.getId());
        materialProjDtlEntity.setFileKey(fileKey);
    }

    public void checkAndUpdateFileTOAwsS3(MaterialPODeliveryDocketEntity materialProjDtlEntity,
            MultipartFile multipartFile, String uniqueKeyPrefix, Long purOrderId, Long schItemId ) throws IOException {
        String uniqueKey = materialProjDtlEntity.getFileKey();
        if (uniqueKey != null) {        	
            updateExistingFileToAwsS3(uniqueKey, multipartFile);
        } else {
        	if( ApplicationConstants.UPLOAD_FILE_TO.equals("LOCAL") )
        	{
        		UploadUtil uploadUtil = new UploadUtil();
        		System.out.println("if block of upload");
        		System.out.println("file folder path:"+materialProjDtlEntity.getProjDocFileEntity().getFolderId().getUploadFolder());
        		String dir_path = materialProjDtlEntity.getProjDocFileEntity().getFolderId().getUploadFolder();
        		System.out.println(materialProjDtlEntity.getMaterialProjDtlEntity().getProjId().getProjectId()+"->"+materialProjDtlEntity.getMaterialProjDtlEntity().getId()+"->"+purOrderId+"->"+schItemId);
        		String extras[] = { String.valueOf( materialProjDtlEntity.getMaterialProjDtlEntity().getProjId().getProjectId() ), String.valueOf( materialProjDtlEntity.getMaterialProjDtlEntity().getId() ), String.valueOf( purOrderId ), String.valueOf( schItemId ) };
        		if( multipartFile != null )
        		{
        			uploadUtil.uploadFile( multipartFile, "Materials", dir_path, extras );
        		}
        	}
        	else
        	{
        		uploadFileToAwsS3(materialProjDtlEntity, multipartFile, uniqueKeyPrefix);
        	}            
        }

    }

    public void updateExistingFileToAwsS3(String uniqueKey, MultipartFile multipartFile) throws IOException {
        awsAswS3FileService.updateExistingFile(multipartFile, uniqueKey);
    }

    private MultipartFile addFileDetailsToTo(MultipartFile[] files, MaterialPODeliveryDocketEntity materialProjDtlTO) {
        for (MultipartFile file : files) {
            if (file.getOriginalFilename().equals(materialProjDtlTO.getFileName())
                    && file.getSize() == Long.valueOf(materialProjDtlTO.getFileSize())
                    && file.getContentType().equals(materialProjDtlTO.getFileType())) {
                return file;
            }
        }
        return null;
    }

    public MaterialProjDtlEntity populateProjMaterialEntity( MaterialProjDtlTO materialProjDtlTO, ProjDocFolderEntity projDocFolderEntity, Long userId, String upload_path ) {
        MaterialProjDtlEntity materialProjDtlEntity = MaterialProjDtlHandler.convertPOJOToEntity(materialProjDtlTO,
                epsProjRepository, precontractMaterialRepository, copyMaterialClassRepository,
                copyPurchaseOrderRepository, companyRepository, precontractMaterialCmpRepository,
                precontractServiceCmpRepository, precontractServiceRepository);

        BigDecimal creditQty = new BigDecimal(0);
        MaterialPODeliveryDocketEntity toAssignLedger = null;

        StringBuffer deliveryDocketNumber = new StringBuffer();
        BigDecimal stockQty = new BigDecimal(0);
        String upload_folder = "/"+materialProjDtlTO.getProjId()+upload_path;
        for (MaterialPODeliveryDocketTO materialPODeliveryDocketTO : materialProjDtlTO
                .getMaterialPODeliveryDocketTOs()) {
            MaterialPODeliveryDocketEntity materialPODeliveryDocketEntity;
            materialPODeliveryDocketEntity = MaterialPODeliveryDocketHandler
                    .convertPOJOToEntity( materialPODeliveryDocketTO, stockRepository, projStoreStockRepository, projDocFolderEntity, projDocFileRepository, userId, materialProjDtlTO.getProjId(), upload_folder );
            materialPODeliveryDocketEntity.setMaterialProjDtlEntity(materialProjDtlEntity);
            materialProjDtlEntity.getMaterialPODeliveryDocketEntities().add(materialPODeliveryDocketEntity);
            if (CommonUtil.isBlankLong(materialPODeliveryDocketTO.getId())) {
                toAssignLedger = materialPODeliveryDocketEntity;
                if (!CommonUtil.isBlankBigDecimal(materialPODeliveryDocketTO.getReceivedQty())) {
                    creditQty = creditQty.add(materialPODeliveryDocketTO.getReceivedQty());
                    deliveryDocketNumber.append(materialPODeliveryDocketTO.getDocketNumber());
                    deliveryDocketNumber.append(",");
                }
                stockQty = stockQty.add(materialPODeliveryDocketEntity.getReceivedQty());
            }
        }
        MaterialSchLocCountEntity materialSchLocCountEntity = materialSchLocCountRepository
                .findOne(materialProjDtlTO.getSchLocId());
        materialSchLocCountEntity
                .setAvlQty(CommonUtil.ifNullGetDefaultValue(materialSchLocCountEntity.getAvlQty()).add(stockQty));
        materialSchLocCountEntity
                .setTotalQty(CommonUtil.ifNullGetDefaultValue(materialSchLocCountEntity.getTotalQty()).add(stockQty));
        generateProjMaterialLedger(materialProjDtlEntity, materialProjDtlTO.getId(), creditQty,
                deliveryDocketNumber.toString(), toAssignLedger);

        if (creditQty.compareTo(BigDecimal.ZERO) > 0) {
            updateMaterialPo(Long.valueOf(materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.SCH_ITEM_CMP_ID)), "M", creditQty.doubleValue());
        }
        return materialProjDtlEntity;
    }

    private void updateMaterialPo(Long schCmpId, String type, double receviedQty) {
        if (type.equals("M")) {
            PreContractsMaterialCmpEntity preContractsMaterialCmp = precontractMaterialCmpRepository
                    .findOne(schCmpId);
            Long preReceivedQty = preContractsMaterialCmp.getRecievedQty();
            preContractsMaterialCmp.setRecievedQty(
                    (preReceivedQty == null) ? (long) (0 + receviedQty) : (long) (preReceivedQty + receviedQty));

            preContractsMaterialCmp.setComplete(
                    ((preContractsMaterialCmp.getQuantity() - preContractsMaterialCmp.getRecievedQty()) == 0));

            preContractsMaterialCmp = precontractMaterialCmpRepository.save(preContractsMaterialCmp);
            updatePo(copyPurchaseOrderRepository
                    .findByPreContractsCmpEntity(preContractsMaterialCmp.getPreContractsCmpEntity()), type);
        } else if (type.equals("S")) {
            PreContractsServiceCmpEntity preContractsServiceCmpEntity = precontractServiceCmpRepository
                    .findOne(schCmpId);
            Integer preReceivedQty = preContractsServiceCmpEntity.getReceivedQuantity();
            preContractsServiceCmpEntity.setReceivedQuantity(
                    (preReceivedQty == null) ? (int) (0 + receviedQty) : (int) (preReceivedQty + receviedQty));

            preContractsServiceCmpEntity.setComplete(((preContractsServiceCmpEntity.getQuantity()
                    - preContractsServiceCmpEntity.getReceivedQuantity()) == 0));

            preContractsServiceCmpEntity = precontractServiceCmpRepository.save(preContractsServiceCmpEntity);
            updatePo(copyPurchaseOrderRepository
                    .findByPreContractsCmpEntity(preContractsServiceCmpEntity.getPreContractsCmpEntity()), type);
        }

    }

    private void updatePo(PurchaseOrderEntity purchaseOrderEntityCopy, String procureType) {
        String existingType = purchaseOrderEntityCopy.getProcureType();
        if (CommonUtil.isNotBlankStr(existingType)) {
            String completeProcureType = purchaseOrderEntityCopy.getCompleteProcureType();
            boolean alreadyCompleted = false;
            String[] completeProcureTypes = null;
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

    private void generateProjMaterialLedger(MaterialProjDtlEntity materialProjDtlEntity, Long schId,
            BigDecimal creditQty, String issueDocketRef, MaterialPODeliveryDocketEntity toAssignLedger) {
        MaterialProjLedgerEntity existProjLedgerEntity = null;
        if (CommonUtil.isNonBlankLong(schId)) {
            existProjLedgerEntity = materialProjLegderRepository.findLatestMaterialProjLedger(schId,
                    StatusCodes.ACTIVE.getValue());
        }
        MaterialProjLedgerEntity materialProjLedgerEntity = null;
        materialProjLedgerEntity = ProjMaterialLedgerHandler.generateProjMaterialLedgerCredit(existProjLedgerEntity,
                creditQty, issueDocketRef);
        materialProjLedgerEntity.setDocketType(CommonConstants.DELIVERY_DOCKET_TYPE);
        materialProjLedgerEntity.setMaterialPODeliveryDocketEntity(toAssignLedger);
        materialProjLedgerEntity.setMaterialProjDtlEntity(materialProjDtlEntity);
        materialProjDtlEntity.getMaterialProjLedgerEntities().add(materialProjLedgerEntity);
    }

    public MaterialSchItemsResp getMaterialSchItemsByProjectAndLoc(MaterialSchItemsReq req) {
        MaterialSchItemsResp resp = null;
        List<MaterialProjDtlEntity> materialProjDtlEntities = null;
        Long projId = null;
        projId = req.getReqProjLabelKeyTO().getId();
        String stockId = req.getReqProjLabelKeyTO().getDisplayNamesMap().get(CommonConstants.STOCK_ID);
        String projStockId = req.getReqProjLabelKeyTO().getDisplayNamesMap().get(CommonConstants.PROJ_STOCK_ID);
        String locationValue = null;
        if (CommonUtil.isNotBlankStr(stockId)) {
            locationValue = stockId;
        } else {
            locationValue = projStockId;
        }

        if (CommonConstants.PROJ_MATERIAL_DOCKET_TYPE_INTERNAL.equalsIgnoreCase(req.getMaterialDockReqType())) {
            List<MaterialClassMstrEntity> restrictedMaterials = projMaterialClassRespository
                    .getInternalProjMaterialMstrEntiti(projId, 1);
            if (req.getMaterialClassList() == null || !req.getMaterialClassList().isEmpty()) {
                if (restrictedMaterials.size() == 0)
                    materialProjDtlEntities = materialProjRepository.getProjectLocSchItemsForProjDocket(projId,
                            Long.valueOf(locationValue));
                else
                    materialProjDtlEntities = materialProjRepository.getProjectLocSchItemsForProjDocket(projId,
                            Long.valueOf(locationValue), restrictedMaterials);
            } else
                materialProjDtlEntities = materialProjRepository.getProjectLocSchItemsForProjDocket(projId,
                        req.getMaterialClassList(), Long.valueOf(locationValue));
        } else if (CommonConstants.PROJ_MATERIAL_DOCKET_TYPE_EXTERNAL.equalsIgnoreCase(req.getMaterialDockReqType())) {
            List<MaterialClassMstrEntity> restrictedMaterials = projMaterialClassRespository
                    .getExternalProjMaterialMstrEntity(projId, 1);
            if (req.getMaterialClassList() == null || !req.getMaterialClassList().isEmpty()) {
                if (restrictedMaterials.size() == 0)
                    materialProjDtlEntities = materialProjRepository.getProjectLocSchItemsForTransfer(projId,
                            Long.valueOf(locationValue));
                else
                    materialProjDtlEntities = materialProjRepository.getProjectLocSchItemsForTransfer(projId,
                            Long.valueOf(locationValue), restrictedMaterials);
            } else
                materialProjDtlEntities = materialProjRepository.getProjectLocSchItemsForTransfer(projId,
                        req.getMaterialClassList(), Long.valueOf(locationValue));
        }

        resp = getSchItems(materialProjDtlEntities, stockId, projStockId);
        return resp;
    }

    private MaterialSchItemsResp getSchItems(List<MaterialProjDtlEntity> materialProjDtlEntities, String stockId,
            String projStockId) {
        MaterialSchItemsResp resp = new MaterialSchItemsResp();

        if (CommonUtil.isListHasData(materialProjDtlEntities)) {
            for (MaterialProjDtlEntity materialProjDtlEntity : materialProjDtlEntities) {
                MaterialProjSchItemTO materialProjSchItemTO = new MaterialProjSchItemTO();
                MaterialProjDtlHandler.getSchItem(materialProjDtlEntity, materialProjSchItemTO);
                for (MaterialSchLocCountEntity materialSchLocCountEntity : materialProjDtlEntity
                        .getMaterialSchLocCountEntities()) {

                    if ( CommonUtil.isNotBlankStr(stockId) && CommonUtil.objectNotNull(materialSchLocCountEntity.getStockId()) ) {
                        if (materialSchLocCountEntity.getStockId().getId().toString().equals(stockId)) {
                            materialProjSchItemTO.setCurrentAvaiableQty(materialSchLocCountEntity.getAvlQty());
                            materialProjSchItemTO.setTotalQty(materialSchLocCountEntity.getTotalQty());
                            break;
                        }
                    } else if ( CommonUtil.isNotBlankStr(projStockId) && CommonUtil.objectNotNull(materialSchLocCountEntity.getProjStockId()) ) {
                        if (materialSchLocCountEntity.getProjStockId().getId().toString().equals(projStockId)) {
                            materialProjSchItemTO.setCurrentAvaiableQty(materialSchLocCountEntity.getAvlQty());
                            materialProjSchItemTO.setTotalQty(materialSchLocCountEntity.getTotalQty());
                            break;
                        }
                    }
                }
                resp.getMaterialProjSchItemTOs().add(materialProjSchItemTO);
            }
        }
        return resp;

    }

}
