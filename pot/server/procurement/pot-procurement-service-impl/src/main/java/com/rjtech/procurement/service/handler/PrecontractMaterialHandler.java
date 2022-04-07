package com.rjtech.procurement.service.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.math.BigDecimal;
import java.math.MathContext;


import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.MaterialClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.procurement.dto.PreContractMaterialCmpTO;
import com.rjtech.procurement.dto.PreContractMaterialDtlTO;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PreContractsMaterialCmpEntity;
import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;
import com.rjtech.procurement.resp.PreContractMaterialResp;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.copy.ProjStoreStockMstrEntityCopy;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;
//import com.rjtech.projsettings.model.copy.ProjCostStmtDtlEntityCopy;
import com.rjtech.projsettings.repository.copy.ProjCostStatementsRepositoryCopy;

public class PrecontractMaterialHandler {

    public static List<PreContractMaterialDtlTO> populatePreContractMaterials(Map<Long, Boolean> companyMap,
            List<PreContractsMaterialDtlEntity> preContractsMaterialDtlEntities, boolean addExternal) {
        List<PreContractMaterialDtlTO> preContractMaterialDtlTOs = new ArrayList<>();
        PreContractMaterialDtlTO preContractMaterialDtlTO = null;
        for (PreContractsMaterialDtlEntity preContractsMaterialDtlEntity : preContractsMaterialDtlEntities) {
            if (StatusCodes.ACTIVE.getValue().equals(preContractsMaterialDtlEntity.getStatus())) {
                preContractMaterialDtlTO = convertMaterialDtlEntityToPOJO(preContractsMaterialDtlEntity);
                if (addExternal) {
                    addMaterialCompanies(companyMap, preContractMaterialDtlTO, preContractsMaterialDtlEntity);
                }
                preContractMaterialDtlTOs.add(preContractMaterialDtlTO);
            }

        }
        return preContractMaterialDtlTOs;
    }

    private static void addMaterialCompanies(Map<Long, Boolean> companyMap,
            PreContractMaterialDtlTO preContractMaterialDtlTO,
            PreContractsMaterialDtlEntity preContractsMaterialDtlEntity) {
        List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities;
        Map<Long, Boolean> existingCompanyMap = new HashMap<>();
        preContractsMaterialCmpEntities = preContractsMaterialDtlEntity.getPreContractsMaterialCmpEntities();
        PreContractMaterialCmpTO preContractMaterialCmpTO = null;
        if (CommonUtil.isListHasData(preContractsMaterialCmpEntities)) {
            for (PreContractsMaterialCmpEntity preContractsMaterialCmpEntity : preContractsMaterialCmpEntities) {
                if (companyMap != null
                        && companyMap.containsKey(preContractsMaterialCmpEntity.getPreContractsCmpEntity().getId())) {
                    preContractMaterialCmpTO = convertMaterialCmpEntityToPOJO(preContractsMaterialCmpEntity);
                    existingCompanyMap.put(preContractsMaterialCmpEntity.getPreContractsCmpEntity().getId(), true);
                    preContractMaterialDtlTO.getPreContractMaterialCmpTOs().add(preContractMaterialCmpTO);
                }
            }
        }
        for (Entry<Long, Boolean> preContractCmpTO : companyMap.entrySet()) {
            if (!existingCompanyMap.containsKey(preContractCmpTO.getKey())) {
                preContractMaterialCmpTO = new PreContractMaterialCmpTO();
                preContractMaterialCmpTO.setPreContractCmpId(preContractCmpTO.getKey());
                preContractMaterialCmpTO.setPreContractMaterialId(preContractMaterialDtlTO.getId());
                preContractMaterialCmpTO.setVersion(1);
                preContractMaterialCmpTO.setStatus(StatusCodes.ACTIVE.getValue());
                preContractMaterialDtlTO.getPreContractMaterialCmpTOs().add(preContractMaterialCmpTO);
            }
        }
    }

    public static List<PreContractsMaterialDtlEntity> populatePreContractMaterialEntities(
            List<PreContractMaterialDtlTO> preContractMaterialDtlTOs, boolean version, boolean addExternal,
            ProcureCatgRepository procureCatgRepository, StockRepository stockRepository,
            ProjCostStatementsRepositoryCopy projCostStatementsRepositoryCopy,
            ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy,
            MaterialClassRepository materialClassRepository) {
        List<PreContractsMaterialDtlEntity> preContractsMaterialDtlEntities = new ArrayList<>();
        PreContractsMaterialDtlEntity preContractsMaterialDtlEntity = null;
        for (PreContractMaterialDtlTO preContractMaterialDtlTO : preContractMaterialDtlTOs) {
            preContractsMaterialDtlEntity = convertMaterialPOJOToEntity(preContractMaterialDtlTO, procureCatgRepository,
                    stockRepository, projCostStatementsRepositoryCopy, projStoreStockRepositoryCopy,
                    materialClassRepository);
            if (addExternal) {
                PreContractsMaterialCmpEntity preContractsMaterialCmpEntity = null;
                for (PreContractMaterialCmpTO preContractMaterialCmpTO : preContractMaterialDtlTO
                        .getPreContractMaterialCmpTOs()) {
                    preContractsMaterialCmpEntity = convertCmpMaterialPOJOToEntity(preContractMaterialCmpTO, version);
                    preContractsMaterialDtlEntity.getPreContractsMaterialCmpEntities()
                            .add(preContractsMaterialCmpEntity);
                }
            }
            preContractsMaterialDtlEntities.add(preContractsMaterialDtlEntity);
        }
        return preContractsMaterialDtlEntities;
    }

    public static PreContractsMaterialDtlEntity convertMaterialPOJOToEntity(
            PreContractMaterialDtlTO preContractMaterialDtlTO, ProcureCatgRepository procureCatgRepository,
            StockRepository stockRepository, ProjCostStatementsRepositoryCopy projCostStatementsRepositoryCopy,
            ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy,
            MaterialClassRepository materialClassRepository) {
        PreContractsMaterialDtlEntity preContractsMaterialDtlEntity = new PreContractsMaterialDtlEntity();
        if (CommonUtil.isNonBlankLong(preContractMaterialDtlTO.getId())) {
            preContractsMaterialDtlEntity.setId(preContractMaterialDtlTO.getId());
        }

        PreContractEntity preContractEntity = new PreContractEntity();
        preContractEntity.setId(preContractMaterialDtlTO.getPreContractId());
        preContractsMaterialDtlEntity.setPreContractEntity(preContractEntity);
        preContractsMaterialDtlEntity.setDesc(preContractMaterialDtlTO.getItemDesc());
        preContractsMaterialDtlEntity.setLatest(Boolean.TRUE);
        preContractsMaterialDtlEntity.setQuantity(preContractMaterialDtlTO.getQuantity());
        preContractsMaterialDtlEntity
                .setStartDate(CommonUtil.convertStringToDate(preContractMaterialDtlTO.getStartDate()));
        preContractsMaterialDtlEntity
                .setFinishDate(CommonUtil.convertStringToDate(preContractMaterialDtlTO.getFinishDate()));

        preContractsMaterialDtlEntity.setEstimateCost(preContractMaterialDtlTO.getEstimateCost());
        preContractsMaterialDtlEntity
                .setProcureSubCatgId(procureCatgRepository.findOne(preContractMaterialDtlTO.getProcureSubCatgId()));
        if (preContractMaterialDtlTO.getStoreLabelKey() != null
                && CommonUtil.isNonBlankLong(preContractMaterialDtlTO.getStoreLabelKey().getId())) {
            preContractsMaterialDtlEntity
                    .setStoreId(stockRepository.findOne(preContractMaterialDtlTO.getStoreLabelKey().getId()));
        }
        preContractsMaterialDtlEntity.setProjcostStatement(
                projCostStatementsRepositoryCopy.findOne(preContractMaterialDtlTO.getProjCostLabelKey().getId()));
        if (preContractMaterialDtlTO.getProjStoreLabelKey() != null
                && CommonUtil.isNonBlankLong(preContractMaterialDtlTO.getProjStoreLabelKey().getId())) {
            ProjStoreStockMstrEntity preContract = projStoreStockRepositoryCopy
                    .findOne(preContractMaterialDtlTO.getProjStoreLabelKey().getId());
            preContractsMaterialDtlEntity.setProjStoreId(preContract);
        }
        preContractsMaterialDtlEntity.setMaterialId(
                materialClassRepository.findOne(preContractMaterialDtlTO.getProjMaterialLabelKey().getId()));

        preContractsMaterialDtlEntity.setStatus(preContractMaterialDtlTO.getStatus());
        return preContractsMaterialDtlEntity;
    }

    public static PreContractMaterialResp populatePreContractMaterialResp(
            List<PreContractsMaterialDtlEntity> preContractsMaterialDtlEntities) {
        PreContractMaterialResp preContractMaterialResp = new PreContractMaterialResp();
        for (PreContractsMaterialDtlEntity preContractsMaterialDtlEntity : preContractsMaterialDtlEntities) {
            preContractMaterialResp.getPreContractMaterialDtlTOs()
                    .add(convertMaterialDtlEntityToPOJO(preContractsMaterialDtlEntity));
        }
        return preContractMaterialResp;
    }

    public static PreContractMaterialDtlTO convertMaterialDtlEntityToPOJO(
            PreContractsMaterialDtlEntity preContractsMaterialDtlEntity) {
        PreContractMaterialDtlTO preContractMaterialDtlTO = new PreContractMaterialDtlTO();
        preContractMaterialDtlTO.setId(preContractsMaterialDtlEntity.getId());
        preContractMaterialDtlTO.setPreContractId(preContractsMaterialDtlEntity.getPreContractEntity().getId());
        PreContractEntity preContractEntity = preContractsMaterialDtlEntity.getPreContractEntity();
        preContractMaterialDtlTO.setItemCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                + preContractEntity.getProjId().getCode() + "-" + preContractEntity.getId() + "-"
                + ModuleCodesPrefixes.PRE_CONTRACT_MATERIAL_SCH_PREFIX.getDesc() + "-"
                + AppUtils.formatNumberToString(preContractsMaterialDtlEntity.getId()));
        preContractMaterialDtlTO.setItemDesc(preContractsMaterialDtlEntity.getDesc());
        preContractMaterialDtlTO.setCostCode(preContractsMaterialDtlEntity.getProjcostStatement().getProjCostItemEntity().getCode());
        preContractMaterialDtlTO.setCostCodeDesc(preContractsMaterialDtlEntity.getProjcostStatement().getProjCostItemEntity().getName());
        preContractMaterialDtlTO.setLatest(preContractsMaterialDtlEntity.getLatest());
        preContractMaterialDtlTO.setQuantity(preContractsMaterialDtlEntity.getQuantity());
        preContractMaterialDtlTO.setEstimateMatreialCost(preContractsMaterialDtlEntity.getEstimateCost().multiply(BigDecimal.valueOf(preContractsMaterialDtlEntity.getQuantity())));
        if (preContractsMaterialDtlEntity.getStoreId() != null) {
        preContractMaterialDtlTO.setDeliveryPlace(preContractsMaterialDtlEntity.getStoreId().getName()); 
        }
        preContractMaterialDtlTO
                .setStartDate(CommonUtil.convertDateToString(preContractsMaterialDtlEntity.getStartDate()));
        preContractMaterialDtlTO
                .setFinishDate(CommonUtil.convertDateToString(preContractsMaterialDtlEntity.getFinishDate()));
        preContractMaterialDtlTO.setEstimateCost(preContractsMaterialDtlEntity.getEstimateCost());

        ProcureCatgDtlEntity procureSubCat = preContractsMaterialDtlEntity.getProcureSubCatgId();
        if (procureSubCat != null)
            preContractMaterialDtlTO.setProcureSubCatgId(procureSubCat.getId());

        MaterialClassMstrEntity material = preContractsMaterialDtlEntity.getMaterialId();
        if (material != null) {
            LabelKeyTO projMaterialLabelKey = new LabelKeyTO();
            projMaterialLabelKey.setId(material.getId());
            preContractMaterialDtlTO.setProjMaterialLabelKey(projMaterialLabelKey);
        }

        StockMstrEntity stock = preContractsMaterialDtlEntity.getStoreId();
        if (stock != null) {
            LabelKeyTO storeLabelKey = new LabelKeyTO();
            storeLabelKey.setId(stock.getId());
            preContractMaterialDtlTO.setStoreLabelKey(storeLabelKey);
        }

        ProjStoreStockMstrEntity storeStock = preContractsMaterialDtlEntity.getProjStoreId();
        if (storeStock != null) {
            LabelKeyTO projStoreLabelKey = new LabelKeyTO();
            projStoreLabelKey.setId(storeStock.getId());
            preContractMaterialDtlTO.setProjStoreLabelKey(projStoreLabelKey);
        }

        ProjCostStmtDtlEntity costStmt = preContractsMaterialDtlEntity.getProjcostStatement();
        if (costStmt != null) {
            LabelKeyTO projCostLabelKey = new LabelKeyTO();
            projCostLabelKey.setId(costStmt.getId());
            projCostLabelKey.setCode( costStmt.getProjCostItemEntity().getCode() );
            projCostLabelKey.setName( costStmt.getProjCostItemEntity().getName() );
            preContractMaterialDtlTO.setProjCostLabelKey(projCostLabelKey);
        }

        preContractMaterialDtlTO.setStatus(preContractsMaterialDtlEntity.getStatus());
        return preContractMaterialDtlTO;
    }

    public static List<PreContractMaterialCmpTO> populatePreContractCmpMaterials(
            List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities) {
        List<PreContractMaterialCmpTO> preContractMaterialCmpTOs = new ArrayList<>();
        for (PreContractsMaterialCmpEntity preContractsMaterialCmpEntity : preContractsMaterialCmpEntities) {
            preContractMaterialCmpTOs.add(convertMaterialCmpEntityToPOJO(preContractsMaterialCmpEntity));
        }
        return preContractMaterialCmpTOs;
    }

    public static PreContractMaterialCmpTO convertMaterialCmpEntityToPOJO(
            PreContractsMaterialCmpEntity preContractsMaterialCmpEntity) {
        PreContractMaterialCmpTO contractMaterialCmpTO = new PreContractMaterialCmpTO();

        contractMaterialCmpTO.setId(preContractsMaterialCmpEntity.getId());

        contractMaterialCmpTO.setRate(preContractsMaterialCmpEntity.getRate());
        contractMaterialCmpTO.setVersion(preContractsMaterialCmpEntity.getVersion());
        if (CommonUtil.isNonBlankInteger(preContractsMaterialCmpEntity.getQuantity())) {
            contractMaterialCmpTO.setQuantity(preContractsMaterialCmpEntity.getQuantity());
            contractMaterialCmpTO.setApproveFlag(true);
        }
        contractMaterialCmpTO.setPreContractCmpId(preContractsMaterialCmpEntity.getPreContractsCmpEntity().getId());
        contractMaterialCmpTO
                .setPreContractMaterialId(preContractsMaterialCmpEntity.getPreContractsMaterialDtlEntity().getId());
        contractMaterialCmpTO.setComments(preContractsMaterialCmpEntity.getComments());

        contractMaterialCmpTO.setStatus(preContractsMaterialCmpEntity.getStatus());
        return contractMaterialCmpTO;
    }

    public static List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities(
            List<PreContractMaterialCmpTO> preContractMaterialCmpTOs, boolean version) {
        List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities = new ArrayList<>();
        for (PreContractMaterialCmpTO contractMaterialCmpTO : preContractMaterialCmpTOs) {
            preContractsMaterialCmpEntities.add(convertCmpMaterialPOJOToEntity(contractMaterialCmpTO, version));
        }
        return preContractsMaterialCmpEntities;
    }

    public static PreContractsMaterialCmpEntity convertCmpMaterialPOJOToEntity(
            PreContractMaterialCmpTO contractMaterialCmpTO, boolean version) {
        PreContractsMaterialCmpEntity preContractsMaterialCmpEntity = new PreContractsMaterialCmpEntity();
        if (!version && CommonUtil.isNonBlankLong(contractMaterialCmpTO.getId())) {
            preContractsMaterialCmpEntity.setId(contractMaterialCmpTO.getId());
        }

        PreContractsMaterialDtlEntity preContractsMaterialDtlEntity = new PreContractsMaterialDtlEntity();
        preContractsMaterialDtlEntity.setId(contractMaterialCmpTO.getPreContractMaterialId());
        preContractsMaterialCmpEntity.setPreContractsMaterialDtlEntity(preContractsMaterialDtlEntity);

        PreContractsCmpEntity preContractsCmpEntity = new PreContractsCmpEntity();
        preContractsCmpEntity.setId(contractMaterialCmpTO.getPreContractCmpId());
        preContractsMaterialCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
        preContractsMaterialCmpEntity.setQuantity(contractMaterialCmpTO.getQuantity());
        preContractsMaterialCmpEntity.setRate(contractMaterialCmpTO.getRate());
        preContractsMaterialCmpEntity.setComments(contractMaterialCmpTO.getComments());

        if (version) {
            preContractsMaterialCmpEntity.setVersion(contractMaterialCmpTO.getVersion() + 1);
        } else {
            preContractsMaterialCmpEntity.setVersion(contractMaterialCmpTO.getVersion());

        }

        preContractsMaterialCmpEntity.setStatus(contractMaterialCmpTO.getStatus());
        return preContractsMaterialCmpEntity;
    }

    public static PreContractMaterialResp getPreContractMaterialTOs(
            List<PreContractsMaterialDtlEntity> preContractsServiceDtlEntities, boolean addExternal) {
        PreContractMaterialResp preContractServiceResp = new PreContractMaterialResp();
        PreContractMaterialDtlTO preContractMaterialDtlTO = null;
        List<PreContractsMaterialCmpEntity> preContractsMaterialCmpEntities = null;
        for (PreContractsMaterialDtlEntity preContractsMaterialDtlEntity : preContractsServiceDtlEntities) {
            if (StatusCodes.ACTIVE.getValue().equals(preContractsMaterialDtlEntity.getStatus())) {
                preContractMaterialDtlTO = convertMaterialDtlEntityToPOJO(preContractsMaterialDtlEntity);
                if (addExternal) {
                    preContractsMaterialCmpEntities = preContractsMaterialDtlEntity
                            .getPreContractsMaterialCmpEntities();
                    PreContractMaterialCmpTO preContractMaterialCmpTO = null;
                    if (CommonUtil.isListHasData(preContractsMaterialCmpEntities)) {
                        for (PreContractsMaterialCmpEntity preContractsMaterialCmpEntity : preContractsMaterialCmpEntities) {
                            preContractMaterialCmpTO = convertMaterialCmpEntityToPOJO(preContractsMaterialCmpEntity);
                            preContractMaterialDtlTO.getPreContractMaterialCmpTOs().add(preContractMaterialCmpTO);
                        }
                    }
                }
                preContractServiceResp.getPreContractMaterialDtlTOs().add(preContractMaterialDtlTO);
            }
        }
        return preContractServiceResp;
    }

}
