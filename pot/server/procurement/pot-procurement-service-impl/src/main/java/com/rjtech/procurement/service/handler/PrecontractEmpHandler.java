package com.rjtech.procurement.service.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.rjtech.centrallib.repository.EmpClassRepository;
import com.rjtech.centrallib.repository.ProcureCatgRepository;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.procurement.dto.PreContractEmpDtlTO;
import com.rjtech.procurement.dto.PreContractsEmpCmpTO;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PreContractsEmpCmpEntity;
import com.rjtech.procurement.model.PreContractsEmpDtlEntity;
import com.rjtech.procurement.resp.PreContractEmpResp;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.projsettings.model.ProjCostStmtDtlEntity;
//import com.rjtech.projsettings.model.copy.ProjCostStmtDtlEntityCopy;
//import com.rjtech.projectlib.model.copy.ProjStoreStockMstrEntityCopy;
//import com.rjtech.projectlib.repository.ProjStoreStockRepositoryCopy;
import com.rjtech.projsettings.repository.copy.ProjCostStatementsRepositoryCopy;

public class PrecontractEmpHandler {

    public static PreContractEmpResp populatePreContractResp(
            List<PreContractsEmpDtlEntity> preContractsEmpDtlEntities) {
        PreContractEmpResp preContractEmpResp = new PreContractEmpResp();
        for (PreContractsEmpDtlEntity preContractsEmpDtlEntity : preContractsEmpDtlEntities) {
            preContractEmpResp.getPreContractEmpDtlTOs().add(convertEmpEntityToPOJO(preContractsEmpDtlEntity));
        }
        return preContractEmpResp;
    }

    public static PreContractEmpResp populateExeternalPreContractResp(
            List<PreContractsEmpDtlEntity> preContractsEmpDtlEntities) {
        PreContractEmpResp preContractEmpResp = new PreContractEmpResp();
        for (PreContractsEmpDtlEntity preContractsEmpDtlEntity : preContractsEmpDtlEntities) {
            preContractEmpResp.getPreContractEmpDtlTOs().add(convertEmpEntityToPOJO(preContractsEmpDtlEntity));
        }
        return preContractEmpResp;
    }

    public static List<PreContractEmpDtlTO> populatePreContractEmpTypes(Map<Long, Boolean> companyMap,
            List<PreContractsEmpDtlEntity> preContractsEmpDtlEntities, boolean addExternal) {
        List<PreContractEmpDtlTO> preContractEmpDtlTOs = new ArrayList<PreContractEmpDtlTO>();
        PreContractEmpDtlTO preContractEmpDtlTO = null;
        for (PreContractsEmpDtlEntity preContractsEmpDtlEntity : preContractsEmpDtlEntities) {
            if (StatusCodes.ACTIVE.getValue().equals(preContractsEmpDtlEntity.getStatus())) {
                preContractEmpDtlTO = convertEmpEntityToPOJO(preContractsEmpDtlEntity);
                if (addExternal) {
                    addEmpCompanies(companyMap, preContractEmpDtlTO, preContractsEmpDtlEntity);
                }
                preContractEmpDtlTOs.add(preContractEmpDtlTO);
            }
        }
        return preContractEmpDtlTOs;

    }

    public static void addEmpCompanies(Map<Long, Boolean> companyMap, PreContractEmpDtlTO preContractEmpDtlTO,
            PreContractsEmpDtlEntity preContractsEmpDtlEntity) {
        List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities;
        Map<Long, Boolean> existingCompanyMap = new HashMap<Long, Boolean>();
        preContractsEmpCmpEntities = preContractsEmpDtlEntity.getPreContractsEmpCmpEntities();
        PreContractsEmpCmpTO preContractsEmpCmpTO = null;
        if (CommonUtil.isListHasData(preContractsEmpCmpEntities)) {
            for (PreContractsEmpCmpEntity preContractsEmpCmpEntity : preContractsEmpCmpEntities) {
                if (companyMap != null
                        && companyMap.containsKey(preContractsEmpCmpEntity.getPreContractsCmpEntity().getId())) {
                    preContractsEmpCmpTO = convertCmpEmpEntityToPOJO(preContractsEmpCmpEntity);
                    existingCompanyMap.put(preContractsEmpCmpEntity.getPreContractsCmpEntity().getId(), true);
                    preContractEmpDtlTO.getPreContractsEmpCmpTOs().add(preContractsEmpCmpTO);
                }
            }
        }
        for (Entry<Long, Boolean> preContractCmpTO : companyMap.entrySet()) {
            if (!existingCompanyMap.containsKey(preContractCmpTO.getKey())) {
                preContractsEmpCmpTO = new PreContractsEmpCmpTO();
                preContractsEmpCmpTO.setPreContractCmpId(preContractCmpTO.getKey());
                preContractsEmpCmpTO.setPreContractEmpDtlId(preContractEmpDtlTO.getId());
                preContractsEmpCmpTO.setVersion(1);
                preContractsEmpCmpTO.setStatus(StatusCodes.ACTIVE.getValue());
                preContractEmpDtlTO.getPreContractsEmpCmpTOs().add(preContractsEmpCmpTO);
            }
        }
    }

    public static PreContractEmpResp getPreContractEmpTOs(List<PreContractsEmpDtlEntity> preContractsEmpDtlEntities,
            boolean addExternal) {
        PreContractEmpResp preContractEmpResp = new PreContractEmpResp();
        PreContractEmpDtlTO preContractEmpDtlTO = null;
        List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities = null;
        for (PreContractsEmpDtlEntity preContractsEmpDtlEntity : preContractsEmpDtlEntities) {
            if (StatusCodes.ACTIVE.getValue().equals(preContractsEmpDtlEntity.getStatus())) {
                preContractEmpDtlTO = convertEmpEntityToPOJO(preContractsEmpDtlEntity);
                if (addExternal) {
                    preContractsEmpCmpEntities = preContractsEmpDtlEntity.getPreContractsEmpCmpEntities();
                    PreContractsEmpCmpTO preContractsEmpCmpTO = null;
                    if (CommonUtil.isListHasData(preContractsEmpCmpEntities)) {
                        for (PreContractsEmpCmpEntity preContractsEmpCmpEntity : preContractsEmpCmpEntities) {
                            preContractsEmpCmpTO = convertCmpEmpEntityToPOJO(preContractsEmpCmpEntity);
                            preContractEmpDtlTO.getPreContractsEmpCmpTOs().add(preContractsEmpCmpTO);
                        }
                    }
                }
                preContractEmpResp.getPreContractEmpDtlTOs().add(preContractEmpDtlTO);
            }
        }
        return preContractEmpResp;
    }

    public static List<PreContractsEmpDtlEntity> populatePreContractsEmpDtlEntities(
            List<PreContractEmpDtlTO> preContractEmpDtlTOs, boolean version, boolean addExternal,
            ProjCostStatementsRepositoryCopy projCostStatementsRepositoryCopy,
            ProcureCatgRepository procureCatgRepository, EmpClassRepository empClassRepository, StockRepository stockRepository,
            ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy) {
        List<PreContractsEmpDtlEntity> contractsEmpDtlEntities = new ArrayList<PreContractsEmpDtlEntity>();
        PreContractsEmpDtlEntity preContractsEmpDtlEntity = null;
        PreContractsEmpCmpEntity preContractsEmpCmpEntity = null;
        for (PreContractEmpDtlTO contractEmpDtlTO : preContractEmpDtlTOs) {
            preContractsEmpDtlEntity = convertEmpPOJOToEntity(contractEmpDtlTO, procureCatgRepository,
                    empClassRepository, projCostStatementsRepositoryCopy, stockRepository, projStoreStockRepositoryCopy);
            if (addExternal) {
                for (PreContractsEmpCmpTO preContractsEmpCmpTO : contractEmpDtlTO.getPreContractsEmpCmpTOs()) {
                    preContractsEmpCmpEntity = convertCmpEmpPOJOToEntity(preContractsEmpCmpTO, version);
                    preContractsEmpDtlEntity.getPreContractsEmpCmpEntities().add(preContractsEmpCmpEntity);
                }
            }
            contractsEmpDtlEntities.add(preContractsEmpDtlEntity);
        }
        return contractsEmpDtlEntities;
    }

    public static PreContractsEmpDtlEntity convertEmpPOJOToEntity(PreContractEmpDtlTO contractEmpDtlTO,
            ProcureCatgRepository procureCatgRepository, EmpClassRepository empClassRepository,
            ProjCostStatementsRepositoryCopy projCostStatementsRepositoryCopy, StockRepository stockRepository,
            ProjStoreStockRepositoryCopy projStoreStockRepositoryCopy) {
        PreContractsEmpDtlEntity preContractsEmpDtlEntity = new PreContractsEmpDtlEntity();
        if (CommonUtil.isNonBlankLong(contractEmpDtlTO.getId())) {
            preContractsEmpDtlEntity.setId(contractEmpDtlTO.getId());
        }

        PreContractEntity preContractEntity = new PreContractEntity();
        preContractEntity.setId(contractEmpDtlTO.getPreContractId());
        preContractsEmpDtlEntity.setPreContractEntity(preContractEntity);

        preContractsEmpDtlEntity.setDesc(contractEmpDtlTO.getItemDesc());
        preContractsEmpDtlEntity.setLatest(Boolean.TRUE);
        preContractsEmpDtlEntity.setQuantity(contractEmpDtlTO.getQuantity());

        preContractsEmpDtlEntity.setStartDate(CommonUtil.convertStringToDate(contractEmpDtlTO.getStartDate()));
        preContractsEmpDtlEntity.setFinishDate(CommonUtil.convertStringToDate(contractEmpDtlTO.getFinishDate()));
        preContractsEmpDtlEntity
                .setProcureSubCatgId(procureCatgRepository.findOne(contractEmpDtlTO.getProcureSubCatgId()));

        preContractsEmpDtlEntity.setEstimateCost(contractEmpDtlTO.getEstimateCost());
        System.out.print("converEmpPOJOToEntity function from PrecontractEmpHandler");
        System.out.println(contractEmpDtlTO.getProjCostLabelKey().getId());
        ProjCostStmtDtlEntity costStmtData = projCostStatementsRepositoryCopy.findOne(contractEmpDtlTO.getProjCostLabelKey().getId());
        System.out.println(costStmtData);

        preContractsEmpDtlEntity.setEstimateCost(contractEmpDtlTO.getEstimateCost());

        preContractsEmpDtlEntity.setCostStatement(
                projCostStatementsRepositoryCopy.findOne(contractEmpDtlTO.getProjCostLabelKey().getId()));

        preContractsEmpDtlEntity.setDeliveryPlace(contractEmpDtlTO.getDeliveryPlace());
        preContractsEmpDtlEntity.setUnitMeasure(contractEmpDtlTO.getUnitMeasure());
        preContractsEmpDtlEntity
                .setProjEmpClassId(empClassRepository.findOne(contractEmpDtlTO.getProjEmpLabelKey().getId()));

        preContractsEmpDtlEntity.setStatus(contractEmpDtlTO.getStatus());
        
        if (contractEmpDtlTO.getStoreLabelKey() != null && stockRepository != null 
                && CommonUtil.isNonBlankLong(contractEmpDtlTO.getStoreLabelKey().getId())) {
        	preContractsEmpDtlEntity
                    .setStoreId(stockRepository.findOne(contractEmpDtlTO.getStoreLabelKey().getId()));
        }
        
        if (contractEmpDtlTO.getProjStoreLabelKey() != null && projStoreStockRepositoryCopy != null
                && CommonUtil.isNonBlankLong(contractEmpDtlTO.getProjStoreLabelKey().getId())) {
            ProjStoreStockMstrEntity preContract = projStoreStockRepositoryCopy
                    .findOne(contractEmpDtlTO.getProjStoreLabelKey().getId());
            preContractsEmpDtlEntity.setProjStoreId(preContract);
        }
        
        return preContractsEmpDtlEntity;
    }

    public static PreContractEmpDtlTO convertEmpEntityToPOJO(PreContractsEmpDtlEntity preContractsEmpDtlEntity) {
    	System.out.println("convertEmpEntityToPOJO function PrecontractEmpHandler class");
        PreContractEmpDtlTO preContractEmpDtlTO = new PreContractEmpDtlTO();
        preContractEmpDtlTO.setId(preContractsEmpDtlEntity.getId());
        preContractEmpDtlTO.setPreContractId(preContractsEmpDtlEntity.getPreContractEntity().getId());
        preContractEmpDtlTO.setItemCode(generateCode(preContractsEmpDtlEntity));
        preContractEmpDtlTO.setItemDesc(preContractsEmpDtlEntity.getDesc());
        preContractEmpDtlTO.setCostCode(preContractsEmpDtlEntity.getCostStatement().getProjCostItemEntity().getCode());
        preContractEmpDtlTO.setCostCodeDesc(preContractsEmpDtlEntity.getCostStatement().getProjCostItemEntity().getName());
        preContractEmpDtlTO.setLatest(preContractsEmpDtlEntity.getLatest());
        preContractEmpDtlTO.setDeliveryPlace(preContractsEmpDtlEntity.getDeliveryPlace());
        preContractEmpDtlTO.setQuantity(preContractsEmpDtlEntity.getQuantity());
        preContractEmpDtlTO.setEstimateEmpBudget(preContractsEmpDtlEntity.getEstimateCost().multiply(BigDecimal.valueOf(preContractsEmpDtlEntity.getQuantity())));
        preContractEmpDtlTO.setStartDate(CommonUtil.convertDateToString(preContractsEmpDtlEntity.getStartDate()));
        preContractEmpDtlTO.setFinishDate(CommonUtil.convertDateToString(preContractsEmpDtlEntity.getFinishDate()));
        preContractEmpDtlTO.setEstimateCost(preContractsEmpDtlEntity.getEstimateCost());
        preContractEmpDtlTO.setUnitMeasure(preContractsEmpDtlEntity.getUnitMeasure());
        // preContractEmpDtlTO.setEmpCatgId(preContractsEmpDtlEntity.getEmpCatgId());
 
        preContractEmpDtlTO.setProcureSubCatgId(preContractsEmpDtlEntity.getProcureSubCatgId().getId());

        LabelKeyTO projEmpLabelKey = new LabelKeyTO();
        projEmpLabelKey.setId(preContractsEmpDtlEntity.getProjEmpClassId().getId());
        preContractEmpDtlTO.setProjEmpLabelKey(projEmpLabelKey);

        preContractEmpDtlTO.setDeliveryPlace(preContractsEmpDtlEntity.getDeliveryPlace());

        LabelKeyTO projCostLabelKey = new LabelKeyTO();
        System.out.println(preContractsEmpDtlEntity.getCostStatement().getId()+"->"+preContractsEmpDtlEntity.getCostStatement().getProjCostItemEntity().getCode());
        projCostLabelKey.setId( preContractsEmpDtlEntity.getCostStatement().getId() );
        projCostLabelKey.setCode( preContractsEmpDtlEntity.getCostStatement().getProjCostItemEntity().getCode() );
        projCostLabelKey.setName( preContractsEmpDtlEntity.getCostStatement().getProjCostItemEntity().getName()) ;
        preContractEmpDtlTO.setProjCostLabelKey(projCostLabelKey);

        preContractEmpDtlTO.setStatus(preContractsEmpDtlEntity.getStatus());
        
        LabelKeyTO storeLabelKey = new LabelKeyTO();
        if(preContractsEmpDtlEntity.getStoreId() != null && preContractsEmpDtlEntity.getStoreId().getId() != null) {
          storeLabelKey.setId(preContractsEmpDtlEntity.getStoreId().getId());
        }
        preContractEmpDtlTO.setStoreLabelKey(storeLabelKey);
        
        LabelKeyTO projstoreLabelKey = new LabelKeyTO();
        if(preContractsEmpDtlEntity.getProjStoreId() != null && preContractsEmpDtlEntity.getProjStoreId().getId() != null) {
        	projstoreLabelKey.setId(preContractsEmpDtlEntity.getProjStoreId().getId());
        }
        preContractEmpDtlTO.setProjStoreLabelKey(projstoreLabelKey);

        return preContractEmpDtlTO;
    }

    public static List<PreContractsEmpCmpTO> populatePreContractCmpEmps(
            List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities) {
        List<PreContractsEmpCmpTO> preContractsEmpCmpTOs = new ArrayList<PreContractsEmpCmpTO>();
        for (PreContractsEmpCmpEntity preContractsEmpCmpEntity : preContractsEmpCmpEntities) {
            preContractsEmpCmpTOs.add(convertCmpEmpEntityToPOJO(preContractsEmpCmpEntity));
        }
        return preContractsEmpCmpTOs;
    }

    public static List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities(
            List<PreContractsEmpCmpTO> preContractsEmpCmpTOs, boolean version) {
        List<PreContractsEmpCmpEntity> preContractsEmpCmpEntities = new ArrayList<PreContractsEmpCmpEntity>();
        for (PreContractsEmpCmpTO contractsEmpCmpTO : preContractsEmpCmpTOs) {
            preContractsEmpCmpEntities.add(convertCmpEmpPOJOToEntity(contractsEmpCmpTO, version));
        }
        return preContractsEmpCmpEntities;
    }

    public static PreContractsEmpCmpEntity convertCmpEmpPOJOToEntity(PreContractsEmpCmpTO contractsEmpCmpTO,
            boolean version) {
        PreContractsEmpCmpEntity preContractsEmpCmpEntity = new PreContractsEmpCmpEntity();
        if (!version && CommonUtil.isNonBlankLong(contractsEmpCmpTO.getId())) {
            preContractsEmpCmpEntity.setId(contractsEmpCmpTO.getId());
        }

        PreContractsEmpDtlEntity preContractsEmpDtlEntity = new PreContractsEmpDtlEntity();
        preContractsEmpDtlEntity.setId(contractsEmpCmpTO.getPreContractEmpDtlId());
        preContractsEmpCmpEntity.setContractsEmpDtlEntity(preContractsEmpDtlEntity);

        PreContractsCmpEntity preContractsCmpEntity = new PreContractsCmpEntity();
        preContractsCmpEntity.setId(contractsEmpCmpTO.getPreContractCmpId());
        preContractsEmpCmpEntity.setPreContractsCmpEntity(preContractsCmpEntity);
        preContractsEmpCmpEntity.setQuantity(contractsEmpCmpTO.getQuantity());
        preContractsEmpCmpEntity.setRate(contractsEmpCmpTO.getRate());
        preContractsEmpCmpEntity.setComments(contractsEmpCmpTO.getComments());

        if (version) {
            preContractsEmpCmpEntity.setVersion(contractsEmpCmpTO.getVersion() + 1);
        } else {
            preContractsEmpCmpEntity.setVersion(contractsEmpCmpTO.getVersion());

        }

        preContractsEmpCmpEntity.setStatus(contractsEmpCmpTO.getStatus());
        return preContractsEmpCmpEntity;
    }

    public static PreContractsEmpCmpTO convertCmpEmpEntityToPOJO(PreContractsEmpCmpEntity preContractsEmpCmpEntity) {
        PreContractsEmpCmpTO contractsEmpCmpTO = new PreContractsEmpCmpTO();

        contractsEmpCmpTO.setId(preContractsEmpCmpEntity.getId());

        contractsEmpCmpTO.setRate(preContractsEmpCmpEntity.getRate());
        if (CommonUtil.isNonBlankInteger(preContractsEmpCmpEntity.getQuantity())) {
            contractsEmpCmpTO.setQuantity(preContractsEmpCmpEntity.getQuantity());
            contractsEmpCmpTO.setApproveFlag(true);
        }
        contractsEmpCmpTO.setVersion(preContractsEmpCmpEntity.getVersion());
        contractsEmpCmpTO.setComments(preContractsEmpCmpEntity.getComments());
        contractsEmpCmpTO.setQuantity(preContractsEmpCmpEntity.getQuantity());
        contractsEmpCmpTO.setRate(preContractsEmpCmpEntity.getRate());
        contractsEmpCmpTO.setPreContractCmpId(preContractsEmpCmpEntity.getPreContractsCmpEntity().getId());
        contractsEmpCmpTO.setPreContractEmpDtlId(preContractsEmpCmpEntity.getContractsEmpDtlEntity().getId());

        contractsEmpCmpTO.setStatus(preContractsEmpCmpEntity.getStatus());
        return contractsEmpCmpTO;
    }

    public static String generateCode(PreContractsEmpDtlEntity preContractsEmpDtlEntity) {
        PreContractEntity preContractEntity = preContractsEmpDtlEntity.getPreContractEntity();
        return ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + preContractEntity.getProjId().getCode() + "-"
                + preContractEntity.getId() + "-" + ModuleCodesPrefixes.PRE_CONTRACT_MAN_SCH_PREFIX.getDesc() + "-"
                + AppUtils.formatNumberToString(preContractsEmpDtlEntity.getId());
    }

}