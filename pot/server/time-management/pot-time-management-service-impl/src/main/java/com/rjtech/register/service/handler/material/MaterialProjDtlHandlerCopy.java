package com.rjtech.register.service.handler.material;

import java.math.BigDecimal;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractEntity;
//import com.rjtech.procurement.model.PreContractEntityCopy;
import com.rjtech.procurement.model.PreContractsMaterialCmpEntity;
//import com.rjtech.procurement.model.PreContractsMaterialCmpEntityCopy;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;
//import com.rjtech.procurement.model.PreContractsMaterialDtlEntityCopy;
import com.rjtech.procurement.model.PurchaseOrderEntity;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;
import com.rjtech.procurement.repository.PrecontractMaterialCmpRepositoryCopy;
import com.rjtech.procurement.repository.PurchaseOrderRepositoryCopy;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.material.dto.MaterialProjDtlTO;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
//import com.rjtech.register.material.model.MaterialPODeliveryDocketEntityCopy;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
//import com.rjtech.register.material.model.MaterialProjDtlEntityCopy;
import com.rjtech.register.repository.material.PrecontractMaterialRepositoryCopy;

public class MaterialProjDtlHandlerCopy {

    public static MaterialProjDtlTO convertEntityToPOJO(MaterialProjDtlEntity entity) {
        MaterialProjDtlTO materialProjectDtlTO = new MaterialProjDtlTO();

        materialProjectDtlTO.setId(entity.getId());
        ProjMstrEntity projMstrEntity = entity.getProjId();
        if (null != projMstrEntity) {
            materialProjectDtlTO.setProjId(projMstrEntity.getProjectId());
        }
        LabelKeyTO purchaseSchLabelKeyTO = new LabelKeyTO();

        getSchLabelKeyTO(entity, purchaseSchLabelKeyTO);

        materialProjectDtlTO.setPurchaseSchLabelKeyTO(purchaseSchLabelKeyTO);
        if (CommonUtil.objectNotNull(entity.getSupplyDeliveryDate())) {
            materialProjectDtlTO.setSupplyDeliveryDate(CommonUtil.convertDateToString(entity.getSupplyDeliveryDate()));
        }
        materialProjectDtlTO.setComments(entity.getComments());
        materialProjectDtlTO.setStatus(entity.getStatus());

        return materialProjectDtlTO;
    }

    private static void getSchLabelKeyTO(MaterialProjDtlEntity entity, LabelKeyTO purchaseSchLabelKeyTO) {
        PreContractsMaterialDtlEntity copyPreContractsMaterialDtlEntity = entity.getPreContractMterialId();
        if (null != copyPreContractsMaterialDtlEntity) {
            purchaseSchLabelKeyTO.setId(copyPreContractsMaterialDtlEntity.getId());
        }

        PreContractsMaterialDtlEntity preContractMaterialDtlEntity = entity.getPreContractsMaterialCmpEntity()
                .getPreContractsMaterialDtlEntity();
        PreContractEntity preContractEntity = preContractMaterialDtlEntity.getPreContractEntity();
        purchaseSchLabelKeyTO.setCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                + preContractEntity.getProjId().getCode() + "-" + preContractEntity.getId() + "-"
                + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc() + "-"
                + AppUtils.formatNumberToString(preContractMaterialDtlEntity.getId()));
        purchaseSchLabelKeyTO.setName(entity.getPreContractMaterialName());

        MaterialClassMstrEntity materialClassMstrEntity = entity.getMaterialClassId();
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CLASS_ID,
                String.valueOf(materialClassMstrEntity.getId()));
        purchaseSchLabelKeyTO.getDisplayNamesMap().put("purClassName",
                String.valueOf(materialClassMstrEntity.getName()));
        purchaseSchLabelKeyTO.getDisplayNamesMap().put("purClassCode",
                String.valueOf(materialClassMstrEntity.getCode()));
        purchaseSchLabelKeyTO.getDisplayNamesMap().put("purClassUnitOfMeasure",
                String.valueOf(materialClassMstrEntity.getMeasurmentMstrEntity().getName()));
        PurchaseOrderEntity purchaseOrderEntity = entity.getPurchaseId();
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_ID,
                String.valueOf(purchaseOrderEntity.getId()));
        preContractEntity = purchaseOrderEntity.getPreContractsCmpEntity().getPreContractEntity();
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CODE,
                ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + entity.getProjId().getCode() + "-"
                        + preContractEntity.getId() + "-" + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-"
                        + purchaseOrderEntity.getId());
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.QTY, String.valueOf(entity.getQuantity()));
        BigDecimal remQuantity = entity.getQuantity();
        for (MaterialPODeliveryDocketEntity docketEntity : entity.getMaterialPODeliveryDocketEntities()) {
            remQuantity = remQuantity.subtract(docketEntity.getReceivedQty());
        }
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.REM_QTY, String.valueOf(remQuantity));
        StockMstrEntity stockMstr = preContractMaterialDtlEntity.getStoreId();
        ProjStoreStockMstrEntity projStoreStock = preContractMaterialDtlEntity.getProjStoreId();
        if (stockMstr != null) {
            purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.STOCK_ID, stockMstr.getId().toString());
        } else if (projStoreStock != null) {
            purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJ_STOCK_ID,
                    projStoreStock.getId().toString());
        }
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.DELIVERY_PLACE,
                ((stockMstr == null) ? projStoreStock.getDesc() : stockMstr.getName()));

        CompanyMstrEntity companyMstr = entity.getCompanyMstrEntity();
        if (CommonUtil.objectNotNull(companyMstr)) {
            purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_ID, String.valueOf(companyMstr.getId()));
            purchaseSchLabelKeyTO.getDisplayNamesMap().put("cmpName", companyMstr.getName());
            purchaseSchLabelKeyTO.getDisplayNamesMap().put("cmpCode", companyMstr.getCode());
        }
        if (CommonUtil.objectNotNull(entity.getPreContractsMaterialCmpEntity()))
            purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_ITEM_CMP_ID,
                    String.valueOf(entity.getPreContractsMaterialCmpEntity().getId()));
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.UNIT_OF_RATE, String.valueOf(entity.getRate()));
    }

    public static MaterialProjDtlEntity convertPOJOToEntity(MaterialProjDtlTO materialProjDtlTO,
            EPSProjRepository epsProjRepository, PrecontractMaterialRepositoryCopy precontractMaterialRepository,
            MaterialClassRepository materialClassRepository, PurchaseOrderRepositoryCopy purchaseOrderRepository,
            CompanyRepository companyRepository,
            PrecontractMaterialCmpRepositoryCopy precontractMaterialCmpRepository) {
        MaterialProjDtlEntity entity = new MaterialProjDtlEntity();
        if (CommonUtil.isNonBlankLong(materialProjDtlTO.getId())) {
            entity.setId(materialProjDtlTO.getId());
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(materialProjDtlTO.getProjId());
        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }
        if (CommonUtil.objectNotNull(materialProjDtlTO.getPurchaseSchLabelKeyTO())) {
            PreContractsMaterialDtlEntity copyPreContractsMaterialDtlEntity = precontractMaterialRepository
                    .findOne(materialProjDtlTO.getPurchaseSchLabelKeyTO().getId());
            if (null != copyPreContractsMaterialDtlEntity) {
                entity.setPreContractMterialId(copyPreContractsMaterialDtlEntity);
            }
            entity.setPreContractMaterialName(materialProjDtlTO.getPurchaseSchLabelKeyTO().getName());
            entity.setPreContractMaterialSchId(materialProjDtlTO.getPurchaseSchLabelKeyTO().getId());
            entity.setPreContractMaterialSchCode(materialProjDtlTO.getPurchaseSchLabelKeyTO().getCode());
            if (CommonUtil.isNotBlankStr(materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.PUR_CLASS_ID))) {
                MaterialClassMstrEntity materialClassMstrEntity = materialClassRepository
                        .findOne(Long.valueOf(materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                                .get(CommonConstants.PUR_CLASS_ID)));
                if (null != materialClassMstrEntity) {
                    entity.setMaterialClassId(materialClassMstrEntity);
                }
            }

            if (CommonUtil.isNotBlankStr(
                    materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.PUR_ID))
                    && CommonUtil.isNotBlankStr(materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                            .get(CommonConstants.PUR_CODE))) {
                PurchaseOrderEntity copyPurchaseOrderEntity = purchaseOrderRepository.findOne(Long.valueOf(
                        materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.PUR_ID)));
                if (null != copyPurchaseOrderEntity) {
                    entity.setPurchaseId(copyPurchaseOrderEntity);
                }
            }

            if (CommonUtil.isNotBlankStr(
                    materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.QTY))) {
                entity.setQuantity(new BigDecimal(Double.valueOf(
                        materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.QTY))));
            }

            if (CommonUtil.isNotBlankStr(
                    materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.CMP_ID))) {
                CompanyMstrEntity companyMstrEntity = companyRepository.findOne(Long.valueOf(
                        materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.CMP_ID)));
                entity.setCompanyMstrEntity(companyMstrEntity);
            }
            if (CommonUtil.isNotBlankStr(materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.SCH_ITEM_CMP_ID))) {
                PreContractsMaterialCmpEntity preContractsMaterialCmpEntity = precontractMaterialCmpRepository
                        .findOne(Long.valueOf(materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                                .get(CommonConstants.SCH_ITEM_CMP_ID)));
                entity.setPreContractsMaterialCmpEntity(preContractsMaterialCmpEntity);
            }

            if (CommonUtil.isNotBlankStr(materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.UNIT_OF_RATE))) {
                entity.setRate(new BigDecimal(materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                        .get(CommonConstants.UNIT_OF_RATE)));
            }

        }

        if (CommonUtil.isNotBlankStr(materialProjDtlTO.getSupplyDeliveryDate())) {
            entity.setSupplyDeliveryDate(CommonUtil.convertStringToDate(materialProjDtlTO.getSupplyDeliveryDate()));
        }
        entity.setComments(materialProjDtlTO.getComments());
        entity.setStatus(materialProjDtlTO.getStatus());

        return entity;
    }
}
