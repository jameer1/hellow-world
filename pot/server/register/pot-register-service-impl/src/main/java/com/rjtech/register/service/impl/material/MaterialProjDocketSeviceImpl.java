package com.rjtech.register.service.impl.material;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.register.material.dto.MaterialProjDocketTO;
import com.rjtech.register.material.dto.MaterialProjSchItemTO;
import com.rjtech.register.material.model.MaterialProjDocketEntity;
import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntity;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.material.model.MaterialSchLocCountEntity;
import com.rjtech.register.material.model.MaterialTransferReqApprDtlEntity;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.register.material.req.MaterialProjDocketSaveReq;
import com.rjtech.register.material.resp.MaterialProjDocketResp;
import com.rjtech.register.material.resp.MaterialSchItemsResp;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.repository.material.CopyProjStoreStockRepository;
import com.rjtech.register.repository.material.MaterialDockSchItemRepository;
import com.rjtech.register.repository.material.MaterialProjDocketRepository;
import com.rjtech.register.repository.material.MaterialProjRepository;
import com.rjtech.register.repository.material.MaterialSchLocCountRepository;
import com.rjtech.register.repository.material.MaterialTransReqApprDtlRepository;
import com.rjtech.register.repository.material.MaterialTransferReqApprProcRepository;
import com.rjtech.register.service.handler.material.MaterialProjDocketHandler;
import com.rjtech.register.service.handler.material.MaterialProjDocketSchItemsHandler;
import com.rjtech.register.service.handler.material.MaterialProjDtlHandler;
import com.rjtech.register.service.material.MaterialProjDocketSevice;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "materialProjDocketSevice")
@RJSService(modulecode = "materialProjDocketSevice")
@Transactional
public class MaterialProjDocketSeviceImpl implements MaterialProjDocketSevice {

    @Autowired
    private MaterialProjDocketRepository materialProjDocketRepository;

    @Autowired
    private MaterialDockSchItemRepository materialDockSchItemRepository;

    @Autowired
    private MaterialSchLocCountRepository materialSchLocCountRepository;

    @Autowired
    private MaterialProjRepository materialProjRepository;

    @Autowired
    private MaterialTransferReqApprProcRepository materialTransferReqApprProcRepository;

    @Autowired
    private EPSProjService epsProjService;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CopyProjStoreStockRepository projStoreStockRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    @Autowired
    private MaterialTransReqApprDtlRepository materialTransReqApprDtlRepository;
    
    public MaterialProjDocketResp getMaterialProjDockets(MaterialFilterReq materialGetReq) {
        MaterialProjDocketResp materialProjDocketResp = new MaterialProjDocketResp();

        Date fromDate;
        Date toDate;
        if (CommonUtil.isNotBlankStr(materialGetReq.getFromDate())
                && CommonUtil.isNotBlankStr(materialGetReq.getToDate())) {
            fromDate = CommonUtil.convertStringToDate(materialGetReq.getFromDate());
            toDate = CommonUtil.convertStringToDate(materialGetReq.getToDate());

        } else {
            toDate = new Date();
            fromDate = CommonUtil.substarctInputMonths(toDate, -1);
            materialProjDocketResp.setFromDate(CommonUtil.convertDateToString(fromDate));
            materialProjDocketResp.setToDate(CommonUtil.convertDateToString(toDate));
        }
        List<Long> projIds = null;
        if (CommonUtil.isListHasData(materialGetReq.getProjList())) {
            projIds = materialGetReq.getProjList();
        } else {
            projIds = epsProjService.getUserProjIds();
        }
        List<MaterialProjDocketEntity> materialProjDocketEntities = materialProjDocketRepository
                .getMaterialProjDocketsByFilter(projIds, fromDate, toDate);

        if (CommonUtil.isListHasData(materialProjDocketEntities)) {
            for (MaterialProjDocketEntity materialProjDocketEntity : materialProjDocketEntities) {
                MaterialProjDocketTO materialProjDocketTO = null;
                materialProjDocketTO = MaterialProjDocketHandler.convertEntityToPOJO(materialProjDocketEntity);
                for (MaterialProjDocketSchItemsEntity materialProjDocketSchItemsEntity : materialProjDocketEntity
                        .getMaterialProjDocketSchItemsEntities()) {
                    materialProjDocketTO.getMaterialProjSchItemTOs().add(
                            MaterialProjDocketSchItemsHandler.convertEntityToPOJO(materialProjDocketSchItemsEntity));
                }
                materialProjDocketResp.getMaterialProjDocketTOs().add(materialProjDocketTO);
            }
        }
        return materialProjDocketResp;
    }

    public MaterialProjDocketTO saveMaterialProjDocket(MaterialProjDocketSaveReq materialProjDocketSaveReq) {
        MaterialProjDocketTO materialProjDocketTO = materialProjDocketSaveReq.getMaterialProjDocketTO();
        materialProjDocketTO.setExist(false);

        Map<Long, MaterialSchLocCountEntity> schLocCountMap = new HashMap<Long, MaterialSchLocCountEntity>();

        Long projStockId = (materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap()
                .get(CommonConstants.PROJ_STOCK_SPM_ID) == null) ? 0 : Long.valueOf(materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap()
                .get(CommonConstants.PROJ_STOCK_SPM_ID));

        Long stockId = (materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap().get(CommonConstants.STOCK_SM_ID) == null) ? 0 : Long.valueOf(materialProjDocketTO.getFromProjLabelkeyTO().getDisplayNamesMap()
                .get(CommonConstants.STOCK_SM_ID));

        // the below code is used to validate project docket issue qty with
        // ledger qty

        MaterialProjSchItemValidateBO validateTO = validateSchItem(materialProjDocketTO.getMaterialProjSchItemTOs(),
                stockId, projStockId);
        // Any Schitem qty issue we are not proceeding to update at ledger level
        if (validateTO.getAnyIssueExistItem()) {
            materialProjDocketTO.setExist(true);
            return materialProjDocketTO;
        }
        schLocCountMap = validateTO.getSchLocCountMap();
        MaterialProjDocketEntity materialProjDocketEntity = new MaterialProjDocketEntity();
        materialProjDocketEntity = MaterialProjDocketHandler.convertPOJOToEntity(materialProjDocketTO,
                epsProjRepository, stockRepository, projStoreStockRepository, empRegisterRepository);
        List<MaterialProjDtlEntity> materialProjDtlEntities = null;
        List<MaterialSchLocCountEntity> materialSchLocCountEntities = new ArrayList<MaterialSchLocCountEntity>();

        boolean isInternalDocketIssue = materialProjDocketTO.getFromProjLabelkeyTO().getId()
                .equals(materialProjDocketTO.getToProjLabelkeyTO().getId()) ? true : false;

        for (MaterialProjSchItemTO materialProjSchItemTO : materialProjDocketTO.getMaterialProjSchItemTOs()) {
            MaterialProjSchItemEntityBO schEntityBO = populateProjDocketEntity(materialProjSchItemTO, schLocCountMap,
                    isInternalDocketIssue, materialProjDocketTO.getToProjLabelkeyTO(),
                    materialProjDocketTO.getProjdocketNum());
            if (CommonUtil.objectNotNull(schEntityBO.getSchLocCountEntity())) {
                materialSchLocCountEntities.add(schEntityBO.getSchLocCountEntity());
            }
            MaterialProjDocketSchItemsEntity entity = new MaterialProjDocketSchItemsEntity();
            MaterialProjDocketSchItemsHandler.convertPOJOToEntity(entity, materialProjSchItemTO,
                    materialProjRepository);
            if ( !isInternalDocketIssue ) {
                materialProjDtlEntities = new ArrayList<MaterialProjDtlEntity>();
                if ( schEntityBO.getToProjmaterialProjDtlEntity() != null ) {
                    MaterialProjDtlEntity mprojDtlEntity = new MaterialProjDtlEntity();
                    mprojDtlEntity.setId(schEntityBO.getToProjmaterialProjDtlEntity().getId());
                    entity.setToProjMaterialProjDtlEntity(mprojDtlEntity);
                    materialProjDtlEntities.add(schEntityBO.getToProjmaterialProjDtlEntity());
                }else {
                    entity.setToProjMaterialProjDtlEntity(schEntityBO.getToProjmaterialProjDtlEntity());
                    /* Already schEntityBO.getToProjmaterialProjDtlEntity() proved to be null. No point in setting below 3 properties
                    schEntityBO.getToProjmaterialProjDtlEntity().getMaterialProjLedgerEntities().get(0).setMaterialProjDocketSchItemsEntity(entity);
                    schEntityBO.getToProjmaterialProjDtlEntity().getMaterialProjLedgerEntities().get(0).setDocketType(CommonConstants.PROJ_DOCKET_TYPE);
                    materialProjDtlEntities.add(schEntityBO.getToProjmaterialProjDtlEntity());
                    */
                }
            }
            entity.setMaterialProjDocketEntity(materialProjDocketEntity);
            materialProjDocketEntity.getMaterialProjDocketSchItemsEntities().add(entity);
        }
        if (!isInternalDocketIssue) {
            materialProjRepository.save(materialProjDtlEntities);
        }
        materialProjDocketRepository.save(materialProjDocketEntity);
        materialSchLocCountRepository.save(materialSchLocCountEntities);
        if (materialProjDocketTO.getNotifyLabelKeyTO().getId() != null) {
            updateMaterialTransReqDtl(materialProjDocketTO);
        }
        return materialProjDocketTO;
    }

    private void updateMaterialTransReqDtl(MaterialProjDocketTO materialProjDocketTO) {
        List<MaterialProjSchItemTO> materialProjSchItems = materialProjDocketTO.getMaterialProjSchItemTOs();
        materialProjSchItems.forEach((materialProjSch) -> {
            MaterialTransferReqApprDtlEntity materialTransferReqApprDtlEntity = null;
            materialTransferReqApprDtlEntity = materialTransReqApprDtlRepository.findOne(materialProjSch.getMaterialTransReqId());
            if( materialTransferReqApprDtlEntity != null ){
                materialTransferReqApprDtlEntity.setAvailableQty( materialTransferReqApprDtlEntity.getAvailableQty().min(materialProjSch.getIssueQty()) );
                materialTransReqApprDtlRepository.save(materialTransferReqApprDtlEntity);
            }
        });
    }

    private MaterialProjSchItemValidateBO validateSchItem(List<MaterialProjSchItemTO> materialProjSchItemTOList,
            Long stockId, Long projStockId) {
        MaterialProjSchItemValidateBO validateTO = new MaterialProjSchItemValidateBO();
        for (MaterialProjSchItemTO materialProjSchItemTO : materialProjSchItemTOList) {
            MaterialSchLocCountEntity schLocCountEntity = getSchItemCountEntityByLocation(
                    materialProjSchItemTO.getSchItemId(), stockId, projStockId);
            if (CommonUtil.objectNotNull(schLocCountEntity)) {
                double avilableBalance = schLocCountEntity.getAvlQty().doubleValue();
                double requestQty = materialProjSchItemTO.getIssueQty().doubleValue();
                if (avilableBalance >= requestQty) {
                    materialProjSchItemTO.setIssueExist(false);
                } else {
                    materialProjSchItemTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().put(CommonConstants.QTY,
                            schLocCountEntity.getAvlQty().toString());
                    validateTO.setAnyIssueExistItem(true);
                    materialProjSchItemTO.setIssueExist(true);
                }
                MaterialProjDtlEntity materialProjDtlEntity = schLocCountEntity.getMaterialProjDtlEntity();
                if (null != materialProjDtlEntity) {
                    validateTO.getSchLocCountMap().put(materialProjDtlEntity.getId(), schLocCountEntity);
                }
            }
        }
        return validateTO;
    }

    private MaterialProjSchItemEntityBO populateProjDocketEntity(MaterialProjSchItemTO materialProjSchItemTO,
            Map<Long, MaterialSchLocCountEntity> schLocCountMap, Boolean isInternalDocketIssue,
            LabelKeyTO toProjLabelkeyTO, String issueDocketRef) {
        MaterialProjSchItemEntityBO schEntityBO = new MaterialProjSchItemEntityBO();

        MaterialSchLocCountEntity schLocCountEntity = schLocCountMap.get(materialProjSchItemTO.getSchItemId());
        double avilableBalance = schLocCountEntity.getAvlQty().doubleValue();
        double requestQty = materialProjSchItemTO.getIssueQty().doubleValue();

        if (avilableBalance >= requestQty) {
            if (!isInternalDocketIssue) {
                Long preContractCmpId = Long.valueOf(materialProjSchItemTO.getPurchaseSchLabelKeyTO()
                        .getDisplayNamesMap().get(CommonConstants.SCH_ITEM_CMP_ID));

                List<MaterialProjDtlEntity> materialProjDtlEntity = materialProjRepository.getProjMaterialSchItem(
                        toProjLabelkeyTO.getId(), preContractCmpId, StatusCodes.ACTIVE.getValue());
                if (materialProjDtlEntity != null && materialProjDtlEntity.size() > 0) {
                    for (MaterialProjDtlEntity toMaterialProjDtlEntity : materialProjDtlEntity) {
                        schEntityBO.setToProjmaterialProjDtlEntity(toMaterialProjDtlEntity);
                    }
                } else {
                    List<MaterialProjDtlEntity> fromMaterialProjDtlEntityList = materialProjRepository
                            .getProjMaterialSchItem(materialProjSchItemTO.getProjId(), preContractCmpId,
                                    StatusCodes.ACTIVE.getValue());
                    for (MaterialProjDtlEntity fromMaterialProjDtlEntity : fromMaterialProjDtlEntityList) {
                        MaterialProjDtlEntity toMaterialProjDtlEntity = new MaterialProjDtlEntity();

                        MaterialProjDtlHandler.copyEntityTOEntity(fromMaterialProjDtlEntity, toMaterialProjDtlEntity);
                        toMaterialProjDtlEntity.setId(null);
                        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(toProjLabelkeyTO.getId());
                        if (null != projMstrEntity) {
                            toMaterialProjDtlEntity.setProjId(projMstrEntity);
                        }
                        schEntityBO.setToProjmaterialProjDtlEntity(toMaterialProjDtlEntity);
                    }
                }
            }
            schLocCountEntity.setAvlQty(schLocCountEntity.getAvlQty().subtract(materialProjSchItemTO.getIssueQty()));
            schEntityBO.setSchLocCountEntity(schLocCountEntity);
        }
        return schEntityBO;
    }

    private MaterialSchLocCountEntity getSchItemCountEntityByLocation(Long schItemId, Long stockId, Long projStockId) {
        MaterialSchLocCountEntity materialSchLocCountEntity = null;
        if (stockId > 0) {
            materialSchLocCountEntity = materialSchLocCountRepository.findByMatSchIdAndStockId(schItemId, stockId);
        }

        if (projStockId > 0) {
            materialSchLocCountEntity = materialSchLocCountRepository.findByMatSchIdAndProjStockId(schItemId,
                    projStockId);
        }
        return materialSchLocCountEntity;

    }

    public MaterialSchItemsResp getMaterialSchItemsByProjDocket(MaterialGetReq materialGetReq) {
        MaterialSchItemsResp materialSchItemsResp = new MaterialSchItemsResp();
        List<MaterialProjDocketSchItemsEntity> materialProjDocketEntities = materialDockSchItemRepository
                .findMaterialDocSchItemsByDocket(materialGetReq.getDocketId(), materialGetReq.getStatus());
        for (MaterialProjDocketSchItemsEntity materialProjDocketSchItemsEntity : materialProjDocketEntities) {
            materialSchItemsResp.getMaterialProjSchItemTOs()
                    .add(MaterialProjDocketSchItemsHandler.convertEntityToPOJO(materialProjDocketSchItemsEntity));
        }
        return materialSchItemsResp;
    }

    public MaterialProjDocketResp getMaterialProjDocketsByDockType(MaterialGetReq materialGetReq) {
        MaterialProjDocketResp materialProjDocketResp = new MaterialProjDocketResp();
        List<MaterialProjDocketEntity> materialProjDocketEntities = null;
        if(materialGetReq.getSourceType().equals(CommonConstants.SM)) {
        	materialProjDocketEntities = materialProjDocketRepository
                    .findMaterialGeneratedProjDockets(materialGetReq.getProjId(), materialGetReq.getSourceType(), "Generated",
                            materialGetReq.getStatus(), CommonUtil.convertStringToDate(materialGetReq.getWorkDairyDate()));	
        }
        //As per requirement i will create this condition STOCK PILE taken in central library
        if(materialGetReq.getSourceType().equals(CommonConstants.SPM)) {   
        	materialProjDocketEntities = materialProjDocketRepository
                    .findMaterialGeneratedProjDocketsSPMType(materialGetReq.getProjId(), "Generated",
                            materialGetReq.getStatus(), CommonUtil.convertStringToDate(materialGetReq.getWorkDairyDate()));	
        } 
        for (MaterialProjDocketEntity MaterialProjDocketEntity : materialProjDocketEntities) {
            materialProjDocketResp.getMaterialProjDocketTOs()
                    .add(MaterialProjDocketHandler.convertEntityToPOJO(MaterialProjDocketEntity));
        }
        return materialProjDocketResp;
    }

    public void saveMaterialProjDocketDraft(MaterialProjDocketSaveReq materialProjDocketSaveReq) {

        MaterialProjDocketTO materialProjDocketTO = materialProjDocketSaveReq.getMaterialProjDocketTO();
        MaterialProjDocketEntity materialProjDocketEntity = new MaterialProjDocketEntity();
        materialProjDocketEntity = MaterialProjDocketHandler.convertPOJOToEntity(materialProjDocketTO,
                epsProjRepository, stockRepository, projStoreStockRepository, empRegisterRepository);

        for (MaterialProjSchItemTO materialProjSchItemTO : materialProjDocketTO.getMaterialProjSchItemTOs()) {
            MaterialProjDocketSchItemsEntity entity = new MaterialProjDocketSchItemsEntity();
            MaterialProjDocketSchItemsHandler.convertPOJOToEntity(entity, materialProjSchItemTO,
                    materialProjRepository);
            entity.setMaterialProjDocketEntity(materialProjDocketEntity);
            materialProjDocketEntity.getMaterialProjDocketSchItemsEntities().add(entity);
        }
        materialProjDocketRepository.save(materialProjDocketEntity);

    }

    public LabelKeyTOResp getMaterialSchDetailsForTransfer(MaterialGetReq materialGetReq) {
        LabelKeyTOResp labelKeyTOResp = new LabelKeyTOResp();
        List<LabelKeyTO> labelKeyTOs = materialTransferReqApprProcRepository
                .getMaterialSchDetailsForTransfer(materialGetReq.getNotifyId());
        labelKeyTOResp.setLabelKeyTOs(labelKeyTOs);
        return labelKeyTOResp;
    }
    
    //This function fetch the material project docekts based on project id
    public MaterialProjDocketResp getMaterialProjDocketsByProjectId( MaterialGetReq materialGetReq ) {
        MaterialProjDocketResp materialProjDocketResp = new MaterialProjDocketResp();        
        List<MaterialProjDocketEntity> materialProjDocketEntities = materialProjDocketRepository.getMaterialProjDocketsByFilter( materialGetReq.getProjId() );

        if( CommonUtil.isListHasData( materialProjDocketEntities ) ) {
            for( MaterialProjDocketEntity materialProjDocketEntity : materialProjDocketEntities) {
                MaterialProjDocketTO materialProjDocketTO = null;
                materialProjDocketTO = MaterialProjDocketHandler.convertEntityToPOJO( materialProjDocketEntity );
                for (MaterialProjDocketSchItemsEntity materialProjDocketSchItemsEntity : materialProjDocketEntity.getMaterialProjDocketSchItemsEntities()) {
                    materialProjDocketTO.getMaterialProjSchItemTOs().add( MaterialProjDocketSchItemsHandler.convertEntityToPOJO( materialProjDocketSchItemsEntity ) );
                }
                materialProjDocketResp.getMaterialProjDocketTOs().add(materialProjDocketTO);
            }
        }
        return materialProjDocketResp;
    }
}
