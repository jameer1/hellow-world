package com.rjtech.register.service.handler.plant;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
//import com.rjtech.procurement.model.PreContractEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceCmpEntityCopy;
//import com.rjtech.procurement.model.PreContractsServiceDtlEntityCopy;
//import com.rjtech.procurement.model.PurchaseOrderEntityCopy;
import com.rjtech.procurement.repository.PrecontractServiceCmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractServiceRepositoryCopy;
import com.rjtech.procurement.repository.PurchaseOrderRepositoryCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.plant.dto.PlantProjPODtlTO;
import com.rjtech.register.plant.model.PlantProjPODtlEntity;
import com.rjtech.register.plant.model.PreContractsPlantCmpEntityCopy;
import com.rjtech.register.plant.model.PreContractsPlantDtlEntityCopy;
import com.rjtech.register.repository.plant.PrecontractPlantCmpRepositoryCopy;
import com.rjtech.register.repository.plant.PrecontractPlantRepositoryCopy;

public class PlantProjPOHandler {

    public static PlantProjPODtlTO convertEntityToPOJO(PlantProjPODtlEntity plantProjPODtlEntity) {
        PlantProjPODtlTO plantProjPODtlTO = new PlantProjPODtlTO();
        PurchaseOrderEntity purchaseOrderEntity = plantProjPODtlEntity.getPurchaseTypeId();
        plantProjPODtlTO.getPurchaseLabelKeyTO().setId(purchaseOrderEntity.getId());
        PreContractEntity preContractEntity = purchaseOrderEntity.getPreContractsCmpEntity().getPreContractEntity();
        plantProjPODtlTO.getPurchaseLabelKeyTO()
                .setCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                        + preContractEntity.getProjId().getCode() + "-" + preContractEntity.getId() + "-"
                        + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-" + purchaseOrderEntity.getId());
        // Plant schItem
        PreContractsPlantDtlEntityCopy preContractsPlantDtlEntity = plantProjPODtlEntity.getPurchasePlantTypeId();
        if (preContractsPlantDtlEntity != null) {
        	System.out.println("Plant schItem block");
            plantProjPODtlTO.getPurchaseSchLabelKeyTO().setId(preContractsPlantDtlEntity.getId());
            plantProjPODtlTO.getPurchaseSchLabelKeyTO().setCode(generatePlantCode(preContractsPlantDtlEntity));
            plantProjPODtlTO.getPurchaseSchLabelKeyTO().setName(preContractsPlantDtlEntity.getDesc());
            System.out.println("preContractsPlantDtlEntity.getId():" + preContractsPlantDtlEntity.getId());
            System.out.println("plantProjPODtlEntity.getScheduleCompanyPlantId(): "
                    + plantProjPODtlEntity.getScheduleCompanyPlantId());
            PreContractsPlantCmpEntityCopy company = plantProjPODtlEntity.getScheduleCompanyPlantId();
            if (company != null) {
                plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().put(CommonConstants.SCH_CMP_ID,
                        String.valueOf(company.getId()));
            }
            plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .put(CommonConstants.PROCUREMENT_MASTER_TYPE, "P");
        }

        // Service schItem
        PreContractsServiceDtlEntity preContractsServiceDtlEntity = plantProjPODtlEntity
                .getPreContractsServiceDtlEntity();
        if (preContractsServiceDtlEntity != null) {
        	System.out.println("Service schItem block");
            plantProjPODtlTO.getPurchaseSchLabelKeyTO().setId(preContractsServiceDtlEntity.getId());
            plantProjPODtlTO.getPurchaseSchLabelKeyTO().setCode(generateServiceCode(preContractsServiceDtlEntity));
            plantProjPODtlTO.getPurchaseSchLabelKeyTO().setName(preContractsServiceDtlEntity.getDesc());

            PreContractsServiceCmpEntity company = plantProjPODtlEntity.getPreContractsServiceCmpEntity();
            if (company != null)
                plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().put(CommonConstants.SCH_CMP_ID,
                        String.valueOf(company.getId()));
            plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .put(CommonConstants.PROCUREMENT_MASTER_TYPE, "S");
        }

        plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().put(CommonConstants.QTY,
                String.valueOf(plantProjPODtlEntity.getActualItems()));

        plantProjPODtlTO.setProjPOId(plantProjPODtlEntity.getId());
        return plantProjPODtlTO;
    }

    public static PlantProjPODtlEntity convertPOJOToEntity(PlantProjPODtlTO plantProjPODtlTO,
            EPSProjRepository epsProjRepository, PurchaseOrderRepositoryCopy purchaseOrderRepository,
            PrecontractPlantRepositoryCopy preContractsPlantRepository,
            PrecontractPlantCmpRepositoryCopy preContractPlantCmpRepository,
            PrecontractServiceCmpRepositoryCopy precontractServiceCmpRepository,
            PrecontractServiceRepositoryCopy precontractServiceRepository, String mode) {
        PlantProjPODtlEntity entity = new PlantProjPODtlEntity();
        entity.setProjId(
                (plantProjPODtlTO.getProjId() != null) ? epsProjRepository.findOne(plantProjPODtlTO.getProjId())
                        : null);

        if (CommonUtil.objectNotNull(plantProjPODtlTO.getPurchaseLabelKeyTO())) {
            entity.setPurchaseTypeId(purchaseOrderRepository.findOne(plantProjPODtlTO.getPurchaseLabelKeyTO().getId()));
        }
        System.out.println("convertPOJOToEntity function of Plant Proj PO Handler class");
        Long itemId = plantProjPODtlTO.getPurchaseSchLabelKeyTO().getId();
        System.out.println(itemId);
        if (CommonUtil.objectNotNull(itemId)) {
            Long cmpId = plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get( CommonConstants.SCH_CMP_ID ) != null ? Long.valueOf( plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get( CommonConstants.SCH_CMP_ID ) ) : 0L;
            System.out.println(cmpId);
            String schItemType = plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.PROCUREMENT_MASTER_TYPE);
            //Long schItemCmpId = Long.valueOf( plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get( CommonConstants.SCH_ITEM_CMP_ID ) );
            System.out.println("schItemType:"+schItemType);
            if (schItemType.equals("P")) {
            	System.out.println("P condition");
                entity.setScheduleCompanyPlantId(preContractPlantCmpRepository.findOne(cmpId));
                entity.setPurchasePlantTypeId(preContractsPlantRepository.findOne(itemId));
            } else if (schItemType.equals("S")) {
            	System.out.println("S condition");
            	Long schItemCmpId = ( mode == "Add" ) ? Long.valueOf( plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get( CommonConstants.SCH_ITEM_CMP_ID ) ) : Long.valueOf( plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get( CommonConstants.SCH_CMP_ID ) ) ;
                entity.setPreContractsServiceCmpEntity(precontractServiceCmpRepository.findOne(schItemCmpId));
                entity.setPreContractsServiceDtlEntity(precontractServiceRepository.findOne(itemId));
            }
            entity.setActualItems(new BigDecimal(
                    plantProjPODtlTO.getPurchaseSchLabelKeyTO().getDisplayNamesMap().get(CommonConstants.QTY))
                            .setScale(2, RoundingMode.HALF_UP));
        }
        if (CommonUtil.isBlankBigDecimal(plantProjPODtlTO.getUsedItems())) {
            entity.setUsedItems(plantProjPODtlTO.getUsedItems());
        }
        entity.setCumulative(plantProjPODtlTO.getCumulative());
        entity.setStatus(plantProjPODtlTO.getStatus());
        entity.setSchdItemId(plantProjPODtlTO.getPurchaseSchLabelKeyTO().getId());
        entity.setSchdItemCode(plantProjPODtlTO.getPurchaseSchLabelKeyTO().getCode());
        return entity;
    }

    public static String generatePlantCode(PreContractsPlantDtlEntityCopy preContractsPlantDtlEntity) {
        PreContractEntity preContractEntity = preContractsPlantDtlEntity.getPreContractEntity();
        return ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + preContractEntity.getProjId().getCode() + "-"
                + preContractEntity.getId() + "-" + ModuleCodesPrefixes.PRE_CONTRACT_PLANT_SCH_PREFIX.getDesc() + "-"
                + preContractsPlantDtlEntity.getId();
    }

    public static String generateServiceCode(PreContractsServiceDtlEntity preContractsServiceDtlEntity) {
        PreContractEntity preContractEntity = preContractsServiceDtlEntity.getPreContractEntity();
        return ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + preContractEntity.getProjId().getCode() + "-"
                + preContractEntity.getId() + "-" + ModuleCodesPrefixes.PRE_CONTRACT_SERVICE_SCH_PREFIX.getDesc() + "-"
                + preContractsServiceDtlEntity.getId();
    }

}
