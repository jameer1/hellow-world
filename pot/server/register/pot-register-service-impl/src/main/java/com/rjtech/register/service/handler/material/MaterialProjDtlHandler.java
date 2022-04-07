package com.rjtech.register.service.handler.material;

import java.math.BigDecimal;

import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.repository.CompanyRepository;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsMaterialCmpEntity;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
//import com.rjtech.procurement.model.PreContractEntityCopy;
//import com.rjtech.procurement.model.PreContractsMaterialCmpEntityCopy;
//import com.rjtech.procurement.model.PreContractsMaterialDtlEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceCmpEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceDtlEntityCopy;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;
import com.rjtech.procurement.repository.PrecontractMaterialCmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractServiceCmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractServiceRepositoryCopy;
import com.rjtech.procurement.repository.PurchaseOrderRepositoryCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.material.dto.MaterialProjDtlTO;
import com.rjtech.register.material.dto.MaterialProjSchItemTO;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.repository.material.CopyMaterialClassRepository;
import com.rjtech.register.repository.material.PrecontractMaterialRepositoryCopy;
import com.rjtech.register.service.handler.plant.PlantProjPOHandler;

public class MaterialProjDtlHandler {

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
        PreContractsMaterialCmpEntity preContractMatCmpEntity = entity.getPreContractsMaterialCmpEntity();
        if (preContractMatCmpEntity != null) {
            PreContractsMaterialDtlEntity preContractMaterialDtlEntity = preContractMatCmpEntity
                    .getPreContractsMaterialDtlEntity();
            // SCH material
            if (preContractMaterialDtlEntity != null) {
                PreContractEntity preContractEntity = preContractMaterialDtlEntity.getPreContractEntity();
                purchaseSchLabelKeyTO.setCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                        + preContractEntity.getProjId().getCode() + "-" + preContractEntity.getId() + "-"
                        + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc() + "-"
                        + AppUtils.formatNumberToString(preContractMaterialDtlEntity.getId()));

                PreContractsMaterialCmpEntity preContractsMaterialCmpEntity = entity
                        .getPreContractsMaterialCmpEntity();
                if (CommonUtil.objectNotNull(preContractsMaterialCmpEntity))
                    purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_ITEM_CMP_ID,
                            String.valueOf(preContractsMaterialCmpEntity.getId()));
                purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCUREMENT_MASTER_TYPE, "M");
            }
        }

        // SCH Service
        PreContractsServiceDtlEntity preContractsServiceDtlEntity = entity.getPreContractsServiceDtlEntity();
        if (preContractsServiceDtlEntity != null) {
            purchaseSchLabelKeyTO.setCode(PlantProjPOHandler.generateServiceCode(preContractsServiceDtlEntity));
            PreContractsServiceCmpEntity preContractsServiceCmpEntity = entity
                    .getPreContractsServiceCmpEntityCopy();
            if (CommonUtil.objectNotNull(preContractsServiceCmpEntity))
                purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.SCH_ITEM_CMP_ID,
                        String.valueOf(preContractsServiceCmpEntity.getId()));
            purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PROCUREMENT_MASTER_TYPE, "S");
        }

        purchaseSchLabelKeyTO.setName(entity.getPreContractMaterialName());

        MaterialClassMstrEntity materialClassMstrEntity = entity.getMaterialClassId();
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CLASS_ID,
                String.valueOf(materialClassMstrEntity.getId()));
        purchaseSchLabelKeyTO.getDisplayNamesMap().put("purClassName",
                String.valueOf(materialClassMstrEntity.getName()));
        purchaseSchLabelKeyTO.getDisplayNamesMap().put("purClassCode",
                String.valueOf(materialClassMstrEntity.getCode()));
        if (materialClassMstrEntity.getMeasurmentMstrEntity() != null)
            purchaseSchLabelKeyTO.getDisplayNamesMap().put("purClassUnitOfMeasure",
                String.valueOf(materialClassMstrEntity.getMeasurmentMstrEntity().getName()));
        PurchaseOrderEntity purchaseOrderEntity = entity.getPurchaseId();
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_ID,
                String.valueOf(purchaseOrderEntity.getId()));
        PreContractEntity preContractEntity = purchaseOrderEntity.getPreContractsCmpEntity().getPreContractEntity();
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.PUR_CODE,
                ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + entity.getProjId().getCode() + "-"
                        + preContractEntity.getId() + "-" + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-"
                        + purchaseOrderEntity.getId());
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.QTY, String.valueOf(entity.getQuantity()));
        BigDecimal remQuantity = new BigDecimal(0);
        if (entity.getPreContractsMaterialCmpEntity() != null && entity.getPreContractsMaterialCmpEntity().getRecievedQty() != null)
            remQuantity = new BigDecimal(entity.getPreContractsMaterialCmpEntity().getQuantity())
                    .subtract(new BigDecimal(entity.getPreContractsMaterialCmpEntity().getRecievedQty()));
        else if (entity.getPreContractsMaterialCmpEntity() != null)
            remQuantity = new BigDecimal(entity.getPreContractsMaterialCmpEntity().getQuantity().doubleValue());
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.REM_QTY, String.valueOf(remQuantity));
        CompanyMstrEntity companyMstr = entity.getCompanyMstrEntity();
        if (CommonUtil.objectNotNull(companyMstr)) {
            purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.CMP_ID, String.valueOf(companyMstr.getId()));
            purchaseSchLabelKeyTO.getDisplayNamesMap().put("cmpName", companyMstr.getName());
            purchaseSchLabelKeyTO.getDisplayNamesMap().put("cmpCode", companyMstr.getCode());
        }
        purchaseSchLabelKeyTO.getDisplayNamesMap().put(CommonConstants.UNIT_OF_RATE, String.valueOf(entity.getRate()));
    }

    public static void copyEntityTOEntity(MaterialProjDtlEntity existEntity, MaterialProjDtlEntity newEntity) {
        newEntity.setPreContractMterialId(existEntity.getPreContractMterialId());
        newEntity.setPreContractMaterialName(existEntity.getPreContractMaterialName());
        newEntity.setMaterialClassId(existEntity.getMaterialClassId());
        newEntity.setPurchaseId(existEntity.getPurchaseId());
        newEntity.setQuantity(existEntity.getQuantity());
        newEntity.setCompanyMstrEntity(existEntity.getCompanyMstrEntity());
        newEntity.setPreContractsMaterialCmpEntity(existEntity.getPreContractsMaterialCmpEntity());
        newEntity.setRate(existEntity.getRate());
        newEntity.setSupplyDeliveryDate(existEntity.getSupplyDeliveryDate());
        newEntity.setComments(existEntity.getComments());
        newEntity.setStatus(existEntity.getStatus());

    }

    public static void getSchItem(MaterialProjDtlEntity entity, MaterialProjSchItemTO schItem) {
        schItem.setSchItemId(entity.getId());
        ProjMstrEntity projMstrEntity = entity.getProjId();
        if (null != projMstrEntity) {
            schItem.setProjId(projMstrEntity.getProjectId());
            schItem.setProjName(projMstrEntity.getProjName());
        }
        LabelKeyTO purchaseSchLabelKeyTO = new LabelKeyTO();
        getSchLabelKeyTO(entity, purchaseSchLabelKeyTO);
        schItem.setPurchaseSchLabelKeyTO(purchaseSchLabelKeyTO);
        schItem.setStatus(entity.getStatus());

    }

    public static MaterialProjDtlEntity convertPOJOToEntity(MaterialProjDtlTO materialProjDtlTO,
            EPSProjRepository epsProjRepository, PrecontractMaterialRepositoryCopy precontractMaterialRepository,
            CopyMaterialClassRepository copyMaterialClassRepository,
            PurchaseOrderRepositoryCopy copyPurchaseOrderRepository, CompanyRepository companyRepository,
            PrecontractMaterialCmpRepositoryCopy precontractMaterialCmpRepository,
            PrecontractServiceCmpRepositoryCopy precontractServiceCmpRepository,
            PrecontractServiceRepositoryCopy precontractServiceRepository) {
        MaterialProjDtlEntity entity = new MaterialProjDtlEntity();
        if (CommonUtil.isNonBlankLong(materialProjDtlTO.getId())) {
            entity.setId(materialProjDtlTO.getId());
        }
        ProjMstrEntity projMstrEntity = epsProjRepository.findOne(materialProjDtlTO.getProjId());
        if (null != projMstrEntity) {
            entity.setProjId(projMstrEntity);
        }
        if (CommonUtil.objectNotNull(materialProjDtlTO.getPurchaseSchLabelKeyTO())) {

            String cmpId = materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.SCH_ITEM_CMP_ID);
            String schItemProcureType = materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.PROCUREMENT_MASTER_TYPE);
            if (CommonUtil.isNotBlankStr(cmpId)) {
                if (schItemProcureType.equals("M")) {
                    PreContractsMaterialCmpEntity preContractsMaterialCmpEntity = precontractMaterialCmpRepository
                            .findOne(Long.valueOf(cmpId));
                    entity.setPreContractsMaterialCmpEntity(preContractsMaterialCmpEntity);
                    PreContractsMaterialDtlEntity copyPreContractsMaterialDtlEntity = precontractMaterialRepository
                            .findOne(materialProjDtlTO.getPurchaseSchLabelKeyTO().getId());
                    entity.setPreContractMterialId(copyPreContractsMaterialDtlEntity);
                } else if (schItemProcureType.equals("S")) {
                    entity.setPreContractsServiceCmpEntityCopy(
                            precontractServiceCmpRepository.findOne(Long.valueOf(cmpId)));
                    entity.setPreContractsServiceDtlEntity(
                            precontractServiceRepository.findOne(materialProjDtlTO.getPurchaseSchLabelKeyTO().getId()));
                }

            }

            entity.setPreContractMaterialName(materialProjDtlTO.getPurchaseSchLabelKeyTO().getName());
            entity.setPreContractMaterialSchId(materialProjDtlTO.getPurchaseSchLabelKeyTO().getId());
            entity.setPreContractMaterialSchCode(materialProjDtlTO.getPurchaseSchLabelKeyTO().getCode());
            if (CommonUtil.isNotBlankStr(materialProjDtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.PUR_CLASS_ID))) {
                MaterialClassMstrEntity materialClassMstrEntity = copyMaterialClassRepository
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
                PurchaseOrderEntity copyPurchaseOrderEntity = copyPurchaseOrderRepository.findOne(Long.valueOf(
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
