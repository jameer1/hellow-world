package com.rjtech.procurement.service.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.math.BigDecimal;
import java.math.MathContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjtech.centrallib.model.ProcureCatgDtlEntity;
import com.rjtech.centrallib.model.ServiceMstrEntity;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.repository.ServiceRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.procurement.dto.PreContractServiceCmpTO;
import com.rjtech.procurement.dto.PreContractServiceDtlTO;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;
import com.rjtech.procurement.resp.PreContractServiceResp;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.copy.ProjStoreStockMstrEntityCopy;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;
//import com.rjtech.projsettings.model.copy.ProjCostStmtDtlEntityCopy;
import com.rjtech.projsettings.repository.copy.ProjCostStatementsRepositoryCopy;

//@Service
public class PrecontractServicesHandler {
	
	/*
	 * @Autowired private StockRepository stockRepository;
	 * 
	 * @Autowired private ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy;
	 */

    public static List<PreContractServiceDtlTO> populatePreContractServices(Map<Long, Boolean> companyMap,
            List<PreContractsServiceDtlEntity> preContractsServiceDtlEntities, boolean addExternal) {
        List<PreContractServiceDtlTO> preContractServiceDtlTOs = new ArrayList<>();
        PreContractServiceDtlTO preContractServiceDtlTO = null;
        for (PreContractsServiceDtlEntity preContractsServiceDtlEntity : preContractsServiceDtlEntities) {
            if (StatusCodes.ACTIVE.getValue().equals(preContractsServiceDtlEntity.getStatus())) {
                preContractServiceDtlTO = convertServiceDtlEntityToPOJO(preContractsServiceDtlEntity);
                if (addExternal) {
                    addServiceCompanies(companyMap, preContractServiceDtlTO, preContractsServiceDtlEntity);
                }
                preContractServiceDtlTOs.add(preContractServiceDtlTO);
            }
        }
        return preContractServiceDtlTOs;
    }

    private static void addServiceCompanies(Map<Long, Boolean> companyMap,
            PreContractServiceDtlTO preContractServiceDtlTO,
            PreContractsServiceDtlEntity preContractsServiceDtlEntity) {
        List<PreContractsServiceCmpEntity> preContractsServiceCmpEntities;
        Map<Long, Boolean> existingCompanyMap = new HashMap<>();
        preContractsServiceCmpEntities = preContractsServiceDtlEntity.getPreContractsServiceCmpEntities();
        PreContractServiceCmpTO preContractServiceCmpTO = null;
        if (CommonUtil.isListHasData(preContractsServiceCmpEntities)) {
            for (PreContractsServiceCmpEntity preContractsServiceCmpEntity : preContractsServiceCmpEntities) {
                if (companyMap != null
                        && companyMap.containsKey(preContractsServiceCmpEntity.getPreContractsCmpEntity().getId())) {
                    preContractServiceCmpTO = convertServiceCmpEntityToPOJO(preContractsServiceCmpEntity);
                    existingCompanyMap.put(preContractsServiceCmpEntity.getPreContractsCmpEntity().getId(), true);
                    preContractServiceDtlTO.getPreContractServiceCmpTOs().add(preContractServiceCmpTO);
                }
            }
        }
        for (Entry<Long, Boolean> preContractCmpTO : companyMap.entrySet()) {
            if (!existingCompanyMap.containsKey(preContractCmpTO.getKey())) {
                preContractServiceCmpTO = new PreContractServiceCmpTO();
                preContractServiceCmpTO.setPreContractCmpId(preContractCmpTO.getKey());
                preContractServiceCmpTO.setServiceDtlId(preContractServiceDtlTO.getId());
                preContractServiceCmpTO.setVersion(1);
                preContractServiceCmpTO.setStatus(StatusCodes.ACTIVE.getValue());
                preContractServiceDtlTO.getPreContractServiceCmpTOs().add(preContractServiceCmpTO);
            }
        }
    }

    public static List<PreContractsServiceDtlEntity> populatePreContractServiceEntities(
            List<PreContractServiceDtlTO> preContractServiceDtlTOs, boolean version, boolean addExternal,
            ProcureCatgRepository procureCatgRepository,
            ProjCostStatementsRepositoryCopy projCostStatementsRepositoryCopy, ServiceRepository serviceRepository, StockRepository stockRepository, 
            ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy, ProjSOWItemRepositoryCopy projSOWItemRepositoryCopy) {
        List<PreContractsServiceDtlEntity> preContractsServiceDtlEntities = new ArrayList<>();
        PreContractsServiceDtlEntity preContractsServiceDtlEntity = null;
        for (PreContractServiceDtlTO preContractServiceDtlTO : preContractServiceDtlTOs) {
            preContractsServiceDtlEntity = convertServicePOJOToEntity(preContractServiceDtlTO, procureCatgRepository,
                    projCostStatementsRepositoryCopy, serviceRepository,stockRepository, projStoreStockRepositoryCopy, projSOWItemRepositoryCopy);
            if (addExternal) {
                PreContractsServiceCmpEntity preContractsServiceCmpEntity = null;
                for (PreContractServiceCmpTO preContractServiceCmpTO : preContractServiceDtlTO
                        .getPreContractServiceCmpTOs()) {
                    preContractsServiceCmpEntity = convertServiceCmpPOJOToEntity(preContractServiceCmpTO, version);
                    preContractsServiceDtlEntity.getPreContractsServiceCmpEntities().add(preContractsServiceCmpEntity);
                }
            }
            preContractsServiceDtlEntities.add(preContractsServiceDtlEntity);
        }
        return preContractsServiceDtlEntities;
    }

    public static PreContractsServiceDtlEntity convertServicePOJOToEntity(
            PreContractServiceDtlTO preContractServiceDtlTO, ProcureCatgRepository procureCatgRepository,
            ProjCostStatementsRepositoryCopy projCostStatementsRepositoryCopy, ServiceRepository serviceRepository,
            StockRepository stockRepository, ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy,
            ProjSOWItemRepositoryCopy projSOWItemRepositoryCopy) {
        PreContractsServiceDtlEntity preContractsServiceDtlEntity = new PreContractsServiceDtlEntity();
        if (CommonUtil.isNonBlankLong(preContractServiceDtlTO.getId())) {
            preContractsServiceDtlEntity.setId(preContractServiceDtlTO.getId());
        }

        PreContractEntity preContractEntity = new PreContractEntity();
        preContractEntity.setId(preContractServiceDtlTO.getPreContractId());
        preContractsServiceDtlEntity.setPreContractEntity(preContractEntity);

        preContractsServiceDtlEntity.setDesc(preContractServiceDtlTO.getItemDesc());
        preContractsServiceDtlEntity.setLatest(Boolean.TRUE);
        preContractsServiceDtlEntity.setQuantity(preContractServiceDtlTO.getQuantity());
        preContractsServiceDtlEntity
                .setStartDate(CommonUtil.convertStringToDate(preContractServiceDtlTO.getStartDate()));
        preContractsServiceDtlEntity
                .setFinishDate(CommonUtil.convertStringToDate(preContractServiceDtlTO.getFinishDate()));

        preContractsServiceDtlEntity
                .setProcureSubCatgId(procureCatgRepository.findOne(preContractServiceDtlTO.getProcureSubCatgId()));

        preContractsServiceDtlEntity.setEstimateCost(preContractServiceDtlTO.getEstimateCost());

        preContractsServiceDtlEntity.setDeliveryPlace(preContractServiceDtlTO.getDeliveryPlace());
        preContractsServiceDtlEntity.setProjcostStatement(
                projCostStatementsRepositoryCopy.findOne(preContractServiceDtlTO.getProjCostLabelKey().getId()));
        preContractsServiceDtlEntity
                .setServiceId(serviceRepository.findOne(preContractServiceDtlTO.getProjServiceLabelKey().getId()));
        
        preContractsServiceDtlEntity.setStatus(preContractServiceDtlTO.getStatus());
        
        
        if (preContractServiceDtlTO.getStoreLabelKey() != null && stockRepository != null 
                && CommonUtil.isNonBlankLong(preContractServiceDtlTO.getStoreLabelKey().getId())) {
        	preContractsServiceDtlEntity
                    .setStoreId(stockRepository.findOne(preContractServiceDtlTO.getStoreLabelKey().getId()));
        }
        
        if (preContractServiceDtlTO.getProjStoreLabelKey() != null && projStoreStockRepositoryCopy != null
                && CommonUtil.isNonBlankLong(preContractServiceDtlTO.getProjStoreLabelKey().getId())) {
            ProjStoreStockMstrEntity preContract = projStoreStockRepositoryCopy
                    .findOne(preContractServiceDtlTO.getProjStoreLabelKey().getId());
            preContractsServiceDtlEntity.setProjStoreId(preContract);
        }
        
        if (preContractServiceDtlTO.getSowItemLabelKey() != null && projSOWItemRepositoryCopy != null 
                && CommonUtil.isNonBlankLong(preContractServiceDtlTO.getSowItemLabelKey().getId())) {
        	preContractsServiceDtlEntity.setProjSOWId(projSOWItemRepositoryCopy.findOne(preContractServiceDtlTO.getSowItemLabelKey().getId()));
        }
        
        return preContractsServiceDtlEntity;
    }

    public static PreContractServiceResp populatePreContractServiceResp(
            List<PreContractsServiceDtlEntity> preContractsServiceDtlEntities) {
        PreContractServiceResp preContractServiceResp = new PreContractServiceResp();
        for (PreContractsServiceDtlEntity preContractsServiceDtlEntity : preContractsServiceDtlEntities) {
            preContractServiceResp.getPreContractServiceDtlTOs()
                    .add(convertServiceDtlEntityToPOJO(preContractsServiceDtlEntity));
        }
        return preContractServiceResp;
    }

    public static PreContractServiceDtlTO convertServiceDtlEntityToPOJO(
            PreContractsServiceDtlEntity preContractsServiceDtlEntity) {
        PreContractServiceDtlTO preContractServiceDtlTO = new PreContractServiceDtlTO();
        preContractServiceDtlTO.setId(preContractsServiceDtlEntity.getId());
        preContractServiceDtlTO.setPreContractId(preContractsServiceDtlEntity.getPreContractEntity().getId());
        PreContractEntity preContractEntity = preContractsServiceDtlEntity.getPreContractEntity();
        preContractServiceDtlTO.setItemCode(generateCode(preContractsServiceDtlEntity));
        preContractServiceDtlTO.setItemDesc(preContractsServiceDtlEntity.getDesc());
        preContractServiceDtlTO.setCostCode(preContractsServiceDtlEntity.getProjcostStatement().getProjCostItemEntity().getCode());
        preContractServiceDtlTO.setCostCodeDesc(preContractsServiceDtlEntity.getProjcostStatement().getProjCostItemEntity().getName());
        preContractServiceDtlTO.setDeliveryPlace(preContractsServiceDtlEntity.getDeliveryPlace());
        preContractServiceDtlTO.setLatest(preContractsServiceDtlEntity.getLatest());
        preContractServiceDtlTO.setQuantity(preContractsServiceDtlEntity.getQuantity());
        preContractServiceDtlTO.setEstimateServiceCost(preContractsServiceDtlEntity.getEstimateCost().multiply(BigDecimal.valueOf(preContractsServiceDtlEntity.getQuantity())));
        preContractServiceDtlTO
                .setStartDate(CommonUtil.convertDateToString(preContractsServiceDtlEntity.getStartDate()));
        preContractServiceDtlTO
                .setFinishDate(CommonUtil.convertDateToString(preContractsServiceDtlEntity.getFinishDate()));

        ProcureCatgDtlEntity procureCat = preContractsServiceDtlEntity.getProcureSubCatgId();
        if (procureCat != null)
            preContractServiceDtlTO.setProcureSubCatgId(procureCat.getId());

        preContractServiceDtlTO.setEstimateCost(preContractsServiceDtlEntity.getEstimateCost());

        ServiceMstrEntity service = preContractsServiceDtlEntity.getServiceId();
        if (service != null) {
            LabelKeyTO projServiceLabelKey = new LabelKeyTO();
            projServiceLabelKey.setId(service.getId());
            preContractServiceDtlTO.setProjServiceLabelKey(projServiceLabelKey);
        }

        preContractServiceDtlTO.setDeliveryPlace(preContractsServiceDtlEntity.getDeliveryPlace());

        ProjCostStmtDtlEntity costStmt = preContractsServiceDtlEntity.getProjcostStatement();
        if (costStmt != null) {
            LabelKeyTO projCostLabelKey = new LabelKeyTO();
            projCostLabelKey.setId( costStmt.getId() );
            projCostLabelKey.setName( costStmt.getProjCostItemEntity().getName() );
            preContractServiceDtlTO.setProjCostLabelKey(projCostLabelKey);
        }

        preContractServiceDtlTO.setStatus(preContractsServiceDtlEntity.getStatus());
        
        
        LabelKeyTO storeLabelKey = new LabelKeyTO();
        if(preContractsServiceDtlEntity.getStoreId() != null && preContractsServiceDtlEntity.getStoreId().getId() != null) {
          storeLabelKey.setId(preContractsServiceDtlEntity.getStoreId().getId());
        }
        preContractServiceDtlTO.setStoreLabelKey(storeLabelKey);
        
        LabelKeyTO projstoreLabelKey = new LabelKeyTO();
        if(preContractsServiceDtlEntity.getProjStoreId() != null && preContractsServiceDtlEntity.getProjStoreId().getId() != null) {
        	projstoreLabelKey.setId(preContractsServiceDtlEntity.getProjStoreId().getId());
        }
        preContractServiceDtlTO.setProjStoreLabelKey(projstoreLabelKey);
        
        
        LabelKeyTO projSOWLabelKey = new LabelKeyTO();
        if(preContractsServiceDtlEntity.getProjSOWId() != null && preContractsServiceDtlEntity.getProjSOWId().getId() != null) {
        	projSOWLabelKey.setId(preContractsServiceDtlEntity.getProjSOWId().getId());
        }
        preContractServiceDtlTO.setSowItemLabelKey(projSOWLabelKey);
        

        return preContractServiceDtlTO;
    }

    public static String generateCode(PreContractsServiceDtlEntity preContractsServiceDtlEntity) {
        PreContractEntity precontract = preContractsServiceDtlEntity.getPreContractEntity();
        return ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + precontract.getProjId().getCode() + "-"
                + precontract.getId() + "-" + ModuleCodesPrefixes.PRE_CONTRACT_SERVICE_SCH_PREFIX.getDesc() + "-"
                + AppUtils.formatNumberToString(preContractsServiceDtlEntity.getId());
    }

    public static List<PreContractServiceCmpTO> populatePreContractCmpServices(
            List<PreContractsServiceCmpEntity> serviceCmpEntities) {
        List<PreContractServiceCmpTO> preContractServiceCmpTOs = new ArrayList<>();
        for (PreContractsServiceCmpEntity preContractsServiceCmpEntity : serviceCmpEntities) {
            preContractServiceCmpTOs.add(convertServiceCmpEntityToPOJO(preContractsServiceCmpEntity));
        }
        return preContractServiceCmpTOs;
    }

    public static PreContractServiceCmpTO convertServiceCmpEntityToPOJO(
            PreContractsServiceCmpEntity preContractsServiceCmpEntity) {
        PreContractServiceCmpTO contractServiceCmpTO = new PreContractServiceCmpTO();

        contractServiceCmpTO.setId(preContractsServiceCmpEntity.getId());

        contractServiceCmpTO.setRate(preContractsServiceCmpEntity.getRate());
        contractServiceCmpTO.setVersion(preContractsServiceCmpEntity.getVersion());
        if (CommonUtil.isNonBlankInteger(preContractsServiceCmpEntity.getQuantity())) {
            contractServiceCmpTO.setQuantity(preContractsServiceCmpEntity.getQuantity());
            contractServiceCmpTO.setApproveFlag(true);
        }
        contractServiceCmpTO.setPreContractCmpId(preContractsServiceCmpEntity.getPreContractsCmpEntity().getId());
        contractServiceCmpTO.setServiceDtlId(preContractsServiceCmpEntity.getPreContractsServiceDtlEntity().getId());
        contractServiceCmpTO.setComments(preContractsServiceCmpEntity.getComments());

        contractServiceCmpTO.setStatus(preContractsServiceCmpEntity.getStatus());

        return contractServiceCmpTO;
    }

    public static List<PreContractsServiceCmpEntity> serviceCmpEntities(
            List<PreContractServiceCmpTO> preContractServiceCmpTOs, boolean version) {
        List<PreContractsServiceCmpEntity> preContractsPlantCmpEntities = new ArrayList<>();
        for (PreContractServiceCmpTO contractServiceCmpTO : preContractServiceCmpTOs) {
            preContractsPlantCmpEntities.add(convertServiceCmpPOJOToEntity(contractServiceCmpTO, version));
        }
        return preContractsPlantCmpEntities;
    }

    public static PreContractsServiceCmpEntity convertServiceCmpPOJOToEntity(
            PreContractServiceCmpTO contractServiceCmpTO, boolean version) {

        PreContractsServiceCmpEntity preContractsServiceCmpEntity = new PreContractsServiceCmpEntity();
        if (!version && CommonUtil.isNonBlankLong(contractServiceCmpTO.getId())) {
            preContractsServiceCmpEntity.setId(contractServiceCmpTO.getId());
        }

        PreContractsServiceDtlEntity preContractsServiceDtlEntity = new PreContractsServiceDtlEntity();
        preContractsServiceDtlEntity.setId(contractServiceCmpTO.getServiceDtlId());
        preContractsServiceCmpEntity.setPreContractsServiceDtlEntity(preContractsServiceDtlEntity);

        PreContractsCmpEntity preContractsCmpEntity = new PreContractsCmpEntity();

        preContractsCmpEntity.setId(contractServiceCmpTO.getPreContractCmpId());
        preContractsServiceCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
        preContractsServiceCmpEntity.setQuantity(contractServiceCmpTO.getQuantity());
        preContractsServiceCmpEntity.setRate(contractServiceCmpTO.getRate());
        preContractsServiceCmpEntity.setComments(contractServiceCmpTO.getComments());

        if (version) {
            preContractsServiceCmpEntity.setVersion(contractServiceCmpTO.getVersion() + 1);
        } else {
            preContractsServiceCmpEntity.setVersion(contractServiceCmpTO.getVersion());

        }

        preContractsServiceCmpEntity.setStatus(contractServiceCmpTO.getStatus());
        return preContractsServiceCmpEntity;
    }

    public static PreContractServiceResp getPreContractServiceTOs(
            List<PreContractsServiceDtlEntity> preContractsServiceDtlEntities, boolean addExternal) {
        PreContractServiceResp preContractServiceResp = new PreContractServiceResp();
        PreContractServiceDtlTO preContractServiceDtlTO = null;
        List<PreContractsServiceCmpEntity> preContractsServiceCmpEntities = null;
        for (PreContractsServiceDtlEntity preContractsServiceDtlEntity : preContractsServiceDtlEntities) {
            if (StatusCodes.ACTIVE.getValue().equals(preContractsServiceDtlEntity.getStatus())) {
                preContractServiceDtlTO = convertServiceDtlEntityToPOJO(preContractsServiceDtlEntity);
                if (addExternal) {
                    preContractsServiceCmpEntities = preContractsServiceDtlEntity.getPreContractsServiceCmpEntities();
                    PreContractServiceCmpTO preContractServiceCmpTO = null;
                    if (CommonUtil.isListHasData(preContractsServiceCmpEntities)) {
                        for (PreContractsServiceCmpEntity preContractsServiceCmpEntity : preContractsServiceCmpEntities) {

                            preContractServiceCmpTO = convertServiceCmpEntityToPOJO(preContractsServiceCmpEntity);
                            preContractServiceDtlTO.getPreContractServiceCmpTOs().add(preContractServiceCmpTO);
                        }
                    }
                }
                preContractServiceResp.getPreContractServiceDtlTOs().add(preContractServiceDtlTO);
            }
        }
        return preContractServiceResp;
    }

}