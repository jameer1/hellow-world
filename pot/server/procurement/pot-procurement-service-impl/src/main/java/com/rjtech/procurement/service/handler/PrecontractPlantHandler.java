package com.rjtech.procurement.service.handler;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.math.BigDecimal;
import java.math.MathContext;


import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.repository.PlantClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.procurement.dto.PreContractPlantCmpTO;
import com.rjtech.procurement.dto.PreContractPlantDtlTO;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PreContractsPlantCmpEntity;
import com.rjtech.procurement.model.PreContractsPlantDtlEntity;
import com.rjtech.procurement.resp.PreContractPlantResp;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.copy.ProjStoreStockMstrEntityCopy;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;
//import com.rjtech.projsettings.model.copy.ProjCostStmtDtlEntityCopy;
import com.rjtech.projsettings.repository.copy.ProjCostStatementsRepositoryCopy;

public class PrecontractPlantHandler {

    private PrecontractPlantHandler() {

    }

    public static List<PreContractPlantDtlTO> populatePreContractPlants(Map<Long, Boolean> companyMap,
            List<PreContractsPlantDtlEntity> preContractsPlantDtlEntities, boolean addExternal) {
        List<PreContractPlantDtlTO> preContractPlantDtlTOs = new ArrayList<>();
        PreContractPlantDtlTO preContractPlantDtlTO = null;
        for (PreContractsPlantDtlEntity preContractsPlantDtlEntity : preContractsPlantDtlEntities) {
            if (StatusCodes.ACTIVE.getValue().equals(preContractsPlantDtlEntity.getStatus())) {
                preContractPlantDtlTO = convertPlantDtlEntityToPOJO(preContractsPlantDtlEntity);
                if (addExternal) {
                    addPlantCompanies(companyMap, preContractPlantDtlTO, preContractsPlantDtlEntity);
                }
                preContractPlantDtlTOs.add(preContractPlantDtlTO);
            }
        }
        return preContractPlantDtlTOs;
    }

    private static void addPlantCompanies(Map<Long, Boolean> companyMap, PreContractPlantDtlTO preContractPlantDtlTO,
            PreContractsPlantDtlEntity preContractsPlantDtlEntity) {
        List<PreContractsPlantCmpEntity> preContractsPlantCmpEntities;
        Map<Long, Boolean> existingCompanyMap = new HashMap<>();
        preContractsPlantCmpEntities = preContractsPlantDtlEntity.getPreContractsPlantCmpEntities();
        PreContractPlantCmpTO preContractPlantCmpTO = null;
        if (CommonUtil.isListHasData(preContractsPlantCmpEntities)) {
            for (PreContractsPlantCmpEntity preContractsPlantCmpEntity : preContractsPlantCmpEntities) {
                if (companyMap != null
                        && companyMap.containsKey(preContractsPlantCmpEntity.getPreContractsCmpEntity().getId())) {
                    preContractPlantCmpTO = convertPlantCmpEntityToPOJO(preContractsPlantCmpEntity);
                    existingCompanyMap.put(preContractsPlantCmpEntity.getPreContractsCmpEntity().getId(), true);
                    preContractPlantDtlTO.getPreContractPlantCmpTOs().add(preContractPlantCmpTO);
                }
            }
        }
        for (Entry<Long, Boolean> preContractCmpTO : companyMap.entrySet()) {
            if (!existingCompanyMap.containsKey(preContractCmpTO.getKey())) {
                preContractPlantCmpTO = new PreContractPlantCmpTO();
                preContractPlantCmpTO.setPreContractCmpId(preContractCmpTO.getKey());
                preContractPlantCmpTO.setPrePlantDtlId(preContractPlantDtlTO.getId());
                preContractPlantCmpTO.setVersion(1);
                preContractPlantCmpTO.setStatus(StatusCodes.ACTIVE.getValue());
                preContractPlantDtlTO.getPreContractPlantCmpTOs().add(preContractPlantCmpTO);
            }
        }
    }

    public static PreContractPlantResp getPreContractPlantTOs(
            List<PreContractsPlantDtlEntity> contractsPlantDtlEntities, boolean addExternal) {
        PreContractPlantResp preContractPlantResp = new PreContractPlantResp();
        PreContractPlantDtlTO preContractPlantDtlTO = null;
        List<PreContractsPlantCmpEntity> contractsPlantCmpEntities = null;
        for (PreContractsPlantDtlEntity contractsPlantDtlEntity : contractsPlantDtlEntities) {
            preContractPlantDtlTO = convertPlantDtlEntityToPOJO(contractsPlantDtlEntity);
            if (addExternal) {
                contractsPlantCmpEntities = contractsPlantDtlEntity.getPreContractsPlantCmpEntities();
                PreContractPlantCmpTO preContractPlantCmpTO = null;
                if (CommonUtil.isListHasData(contractsPlantCmpEntities)) {
                    for (PreContractsPlantCmpEntity preContractsPlantCmpEntity : contractsPlantCmpEntities) {
                        preContractPlantCmpTO = convertPlantCmpEntityToPOJO(preContractsPlantCmpEntity);
                        preContractPlantDtlTO.getPreContractPlantCmpTOs().add(preContractPlantCmpTO);
                    }
                }
            }
            preContractPlantResp.getPreContractPlantDtlTOs().add(preContractPlantDtlTO);
        }
        return preContractPlantResp;
    }

    public static List<PreContractsPlantDtlEntity> populatePreContractPlantEntities(
            List<PreContractPlantDtlTO> contractPlantDtlTOs, boolean version, boolean addExternal,
            ProcureCatgRepository procureCatgRepository, ProjCostStatementsRepositoryCopy projCostItemRepositoryCopy,
            PlantClassRepository plantClassRepository, StockRepository stockRepository,
            ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy) {
        List<PreContractsPlantDtlEntity> contractsPlantDtlEntities = new ArrayList<>();
        PreContractsPlantDtlEntity preContractsPlantDtlEntity = null;
        for (PreContractPlantDtlTO preContractPlantDtlTO : contractPlantDtlTOs) {
            preContractsPlantDtlEntity = convertPlantDtlPOJOToEntity(preContractPlantDtlTO, procureCatgRepository,
                    projCostItemRepositoryCopy, plantClassRepository, stockRepository,projStoreStockRepositoryCopy);
            if (addExternal) {
                PreContractsPlantCmpEntity preContractsPlantCmpEntity = null;
                for (PreContractPlantCmpTO preContractPlantCmpTO : preContractPlantDtlTO.getPreContractPlantCmpTOs()) {
                    preContractsPlantCmpEntity = convertPlantCmpPOJOToEntity(preContractPlantCmpTO, version);
                    preContractsPlantDtlEntity.getPreContractsPlantCmpEntities().add(preContractsPlantCmpEntity);
                }
            }
            contractsPlantDtlEntities.add(preContractsPlantDtlEntity);
        }
        return contractsPlantDtlEntities;
    }

    public static PreContractsPlantDtlEntity convertPlantDtlPOJOToEntity(PreContractPlantDtlTO preContractPlantDtlTO,
            ProcureCatgRepository procureCatgRepository,
            ProjCostStatementsRepositoryCopy projCostStatementsRepositoryCopy,
            PlantClassRepository plantClassRepository, StockRepository stockRepository,
            ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy) {
        PreContractsPlantDtlEntity preContractsPlantDtlEntity = new PreContractsPlantDtlEntity();
        if (CommonUtil.isNonBlankLong(preContractPlantDtlTO.getId())) {
            preContractsPlantDtlEntity.setId(preContractPlantDtlTO.getId());
        }
        PreContractEntity preContractEntity = new PreContractEntity();
        preContractEntity.setId(preContractPlantDtlTO.getPreContractId());
        preContractsPlantDtlEntity.setPreContractEntity(preContractEntity);

        preContractsPlantDtlEntity.setDesc(preContractPlantDtlTO.getItemDesc());
        preContractsPlantDtlEntity.setLatest(Boolean.TRUE);
        preContractsPlantDtlEntity.setQuantity(preContractPlantDtlTO.getQuantity());
        preContractsPlantDtlEntity.setStartDate(CommonUtil.convertStringToDate(preContractPlantDtlTO.getStartDate()));
        preContractsPlantDtlEntity.setFinishDate(CommonUtil.convertStringToDate(preContractPlantDtlTO.getFinishDate()));
        preContractsPlantDtlEntity.setUnitMeasure(preContractPlantDtlTO.getUnitMeasure());

        preContractsPlantDtlEntity
                .setProcureSubCatgId(procureCatgRepository.findOne(preContractPlantDtlTO.getProcureSubCatgId()));

        preContractsPlantDtlEntity.setEstimateCost(preContractPlantDtlTO.getEstimateCost());
        preContractsPlantDtlEntity.setDeliveryPlace(preContractPlantDtlTO.getDeliveryPlace());
        preContractsPlantDtlEntity.setProjcostStatement(
                projCostStatementsRepositoryCopy.findOne(preContractPlantDtlTO.getProjCostLabelKey().getId()));
        preContractsPlantDtlEntity
                .setPlantId(plantClassRepository.findOne(preContractPlantDtlTO.getProjPlantLabelKey().getId()));

        preContractsPlantDtlEntity.setStatus(preContractPlantDtlTO.getStatus());
        
        
        if (preContractPlantDtlTO.getStoreLabelKey() != null && stockRepository != null 
                && CommonUtil.isNonBlankLong(preContractPlantDtlTO.getStoreLabelKey().getId())) {
        	preContractsPlantDtlEntity
                    .setStoreId(stockRepository.findOne(preContractPlantDtlTO.getStoreLabelKey().getId()));
        }
        
        if (preContractPlantDtlTO.getProjStoreLabelKey() != null && projStoreStockRepositoryCopy != null
                && CommonUtil.isNonBlankLong(preContractPlantDtlTO.getProjStoreLabelKey().getId())) {
            ProjStoreStockMstrEntity preContract = projStoreStockRepositoryCopy
                    .findOne(preContractPlantDtlTO.getProjStoreLabelKey().getId());
            preContractsPlantDtlEntity.setProjStoreId(preContract);
        }
        return preContractsPlantDtlEntity;
    }

    public static PreContractPlantResp populatePreContractPlantResp(
            List<PreContractsPlantDtlEntity> preContractsPlantDtlEntities) {
        PreContractPlantResp preContractPlantResp = new PreContractPlantResp();
        for (PreContractsPlantDtlEntity preContractsPlantDtlEntity : preContractsPlantDtlEntities) {
            if (StatusCodes.ACTIVE.getValue().equals(preContractsPlantDtlEntity.getStatus())) {
                preContractPlantResp.getPreContractPlantDtlTOs()
                        .add(convertPlantDtlEntityToPOJO(preContractsPlantDtlEntity));
            }
        }
        return preContractPlantResp;
    }

    public static PreContractPlantDtlTO convertPlantDtlEntityToPOJO(
            PreContractsPlantDtlEntity preContractsPlantDtlEntity) {
        PreContractPlantDtlTO preContractPlantDtlTO = new PreContractPlantDtlTO();
        preContractPlantDtlTO.setId(preContractsPlantDtlEntity.getId());
        preContractPlantDtlTO.setPreContractId(preContractsPlantDtlEntity.getPreContractEntity().getId());
        preContractPlantDtlTO.setItemCode(generatePlantCode(preContractsPlantDtlEntity));
        preContractPlantDtlTO.setItemDesc(preContractsPlantDtlEntity.getDesc());
        preContractPlantDtlTO.setCostCode(preContractsPlantDtlEntity.getProjcostStatement().getProjCostItemEntity().getCode());
        preContractPlantDtlTO.setCostCodeDesc(preContractsPlantDtlEntity.getProjcostStatement().getProjCostItemEntity().getName());
        preContractPlantDtlTO.setDeliveryPlace(preContractsPlantDtlEntity.getDeliveryPlace());
        preContractPlantDtlTO.setLatest(preContractsPlantDtlEntity.getLatest());
        preContractPlantDtlTO.setQuantity(preContractsPlantDtlEntity.getQuantity());
        preContractPlantDtlTO.setEstimatePlantBudget(preContractsPlantDtlEntity.getEstimateCost().multiply(BigDecimal.valueOf(preContractsPlantDtlEntity.getQuantity())));
        preContractPlantDtlTO.setStartDate(CommonUtil.convertDateToString(preContractsPlantDtlEntity.getStartDate()));
        preContractPlantDtlTO.setFinishDate(CommonUtil.convertDateToString(preContractsPlantDtlEntity.getFinishDate()));
        preContractPlantDtlTO.setUnitMeasure(preContractsPlantDtlEntity.getUnitMeasure());
        ProcureCatgDtlEntity procureCat = preContractsPlantDtlEntity.getProcureSubCatgId();
        if (procureCat != null)
            preContractPlantDtlTO.setProcureSubCatgId(procureCat.getId());

        preContractPlantDtlTO.setEstimateCost(preContractsPlantDtlEntity.getEstimateCost());

        PlantMstrEntity plant = preContractsPlantDtlEntity.getPlantId();
        if (plant != null) {
            LabelKeyTO projPlantLabelKey = new LabelKeyTO();
            projPlantLabelKey.setId(plant.getId());
            preContractPlantDtlTO.setProjPlantLabelKey(projPlantLabelKey);
        }

        preContractPlantDtlTO.setDeliveryPlace(preContractsPlantDtlEntity.getDeliveryPlace());

        ProjCostStmtDtlEntity costStmt = preContractsPlantDtlEntity.getProjcostStatement();
        if (costStmt != null) {
            LabelKeyTO projCostLabelKey = new LabelKeyTO();
            projCostLabelKey.setId( costStmt.getId() );
            projCostLabelKey.setName( costStmt.getProjCostItemEntity().getName() );
            preContractPlantDtlTO.setProjCostLabelKey(projCostLabelKey);
        }

        preContractPlantDtlTO.setStatus(preContractsPlantDtlEntity.getStatus());
        
        
        LabelKeyTO storeLabelKey = new LabelKeyTO();
        if(preContractsPlantDtlEntity.getStoreId() != null && preContractsPlantDtlEntity.getStoreId().getId() != null) {
          storeLabelKey.setId(preContractsPlantDtlEntity.getStoreId().getId());
        }
        preContractPlantDtlTO.setStoreLabelKey(storeLabelKey);
        
        LabelKeyTO projstoreLabelKey = new LabelKeyTO();
        if(preContractsPlantDtlEntity.getProjStoreId() != null && preContractsPlantDtlEntity.getProjStoreId().getId() != null) {
        	projstoreLabelKey.setId(preContractsPlantDtlEntity.getProjStoreId().getId());
        }
        preContractPlantDtlTO.setProjStoreLabelKey(projstoreLabelKey);
        return preContractPlantDtlTO;
    }

    public static List<PreContractPlantCmpTO> populatePreContractCmpPlants(
            List<PreContractsPlantCmpEntity> preContractsPlantCmpEntities) {
        List<PreContractPlantCmpTO> preContractPlantCmpTOs = new ArrayList<>();
        for (PreContractsPlantCmpEntity preContractsPlantCmpEntity : preContractsPlantCmpEntities) {
            preContractPlantCmpTOs.add(convertPlantCmpEntityToPOJO(preContractsPlantCmpEntity));
        }
        return preContractPlantCmpTOs;
    }

    private static PreContractPlantCmpTO convertPlantCmpEntityToPOJO(
            PreContractsPlantCmpEntity preContractsPlantCmpEntity) {
        PreContractPlantCmpTO contractPlantCmpTO = new PreContractPlantCmpTO();

        contractPlantCmpTO.setId(preContractsPlantCmpEntity.getId());

        contractPlantCmpTO.setRate(preContractsPlantCmpEntity.getRate());
        contractPlantCmpTO.setVersion(preContractsPlantCmpEntity.getVersion());
        if (CommonUtil.isNonBlankInteger(preContractsPlantCmpEntity.getQuantity())) {
            contractPlantCmpTO.setQuantity(preContractsPlantCmpEntity.getQuantity());
            contractPlantCmpTO.setApproveFlag(true);
        }

        contractPlantCmpTO.setPreContractCmpId(preContractsPlantCmpEntity.getPreContractsCmpEntity().getId());
        contractPlantCmpTO.setPrePlantDtlId(preContractsPlantCmpEntity.getPreContractsPlantDtlEntity().getId());
        contractPlantCmpTO.setComments(preContractsPlantCmpEntity.getComments());

        contractPlantCmpTO.setStatus(preContractsPlantCmpEntity.getStatus());
        return contractPlantCmpTO;
    }

    public static List<PreContractsPlantCmpEntity> preContractsPlantCmpEntities(
            List<PreContractPlantCmpTO> preContractPlantCmpTOs, boolean version) {
        List<PreContractsPlantCmpEntity> preContractsPlantCmpEntities = new ArrayList<>();
        for (PreContractPlantCmpTO contractPlantCmpTO : preContractPlantCmpTOs) {
            preContractsPlantCmpEntities.add(convertPlantCmpPOJOToEntity(contractPlantCmpTO, version));
        }
        return preContractsPlantCmpEntities;
    }

    public static PreContractsPlantCmpEntity convertPlantCmpPOJOToEntity(PreContractPlantCmpTO contractPlantCmpTO,
            boolean version) {
        PreContractsPlantCmpEntity preContractsPlantCmpEntity = new PreContractsPlantCmpEntity();
        if (CommonUtil.isNonBlankLong(contractPlantCmpTO.getId())) {
            preContractsPlantCmpEntity.setId(contractPlantCmpTO.getId());
        }

        PreContractsPlantDtlEntity preContractsPlantDtlEntity = new PreContractsPlantDtlEntity();
        preContractsPlantDtlEntity.setId(contractPlantCmpTO.getPrePlantDtlId());
        preContractsPlantCmpEntity.setPreContractsPlantDtlEntity(preContractsPlantDtlEntity);

        PreContractsCmpEntity preContractsCmpEntity = new PreContractsCmpEntity();
        preContractsCmpEntity.setId(contractPlantCmpTO.getPreContractCmpId());
        preContractsPlantCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
        preContractsPlantCmpEntity.setQuantity(contractPlantCmpTO.getQuantity());
        preContractsPlantCmpEntity.setRate(contractPlantCmpTO.getRate());
        preContractsPlantCmpEntity.setComments(contractPlantCmpTO.getComments());

        if (version) {
            preContractsPlantCmpEntity.setVersion(contractPlantCmpTO.getVersion() + 1);
        } else {
            preContractsPlantCmpEntity.setVersion(contractPlantCmpTO.getVersion());

        }

        preContractsPlantCmpEntity.setStatus(contractPlantCmpTO.getStatus());
        return preContractsPlantCmpEntity;
    }

    public static String generatePlantCode(PreContractsPlantDtlEntity preContractsPlantDtlEntity) {
        PreContractEntity preContractEntity = preContractsPlantDtlEntity.getPreContractEntity();
        return ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + preContractEntity.getProjId().getCode() + "-"
                + preContractEntity.getId() + "-" + ModuleCodesPrefixes.PRE_CONTRACT_PLANT_SCH_PREFIX.getDesc() + "-"
                + preContractsPlantDtlEntity.getId();
    }

}