package com.rjtech.procurement.service.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.math.BigDecimal;
import java.math.MathContext;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.procurement.dto.PrecontractSowCmpTO;
import com.rjtech.procurement.dto.PrecontractSowDtlTO;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PrecontractSowCmpEntity;
import com.rjtech.procurement.model.PrecontractSowDtlEntity;
import com.rjtech.procurement.resp.PreContractSowResp;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
//import com.rjtech.projectlib.model.copy.ProjSOWItemEntityCopy;
import com.rjtech.projectlib.repository.ProjSOWItemRepositoryCopy;

public class PrecontractSowDtlHandler {

    public static PreContractSowResp populatePreContractResp(List<PrecontractSowDtlEntity> precontractSowDtlEntites) {
        PreContractSowResp preContractSowResp = new PreContractSowResp();
        for (PrecontractSowDtlEntity precontractSowDtlEntity : precontractSowDtlEntites) {
            preContractSowResp.getPrecontractSowDtlTOs().add(convertSowDtlEntityToPOJO(precontractSowDtlEntity));
        }

        return preContractSowResp;
    }

    public static PreContractSowResp populateExeternalPreContractResp(
            List<PrecontractSowDtlEntity> precontractSowDtlEntites) {
        PreContractSowResp preContractSowResp = new PreContractSowResp();

        for (PrecontractSowDtlEntity precontractSowDtlEntity : precontractSowDtlEntites) {
            preContractSowResp.getPrecontractSowDtlTOs().add(convertSowDtlEntityToPOJO(precontractSowDtlEntity));
        }
        return preContractSowResp;
    }

    public static List<PrecontractSowDtlTO> populatePreContractSowTypes(Map<Long, Boolean> companyMap,
            List<PrecontractSowDtlEntity> precontractSowDtlEntites, boolean addExternal) {
        List<PrecontractSowDtlTO> precontractSowDtlTOs = new ArrayList<>();
        PrecontractSowDtlTO precontractSowDtlTO = null;
        for (PrecontractSowDtlEntity precontractSowDtlEntity : precontractSowDtlEntites) {
            if (StatusCodes.ACTIVE.getValue().equals(precontractSowDtlEntity.getStatus())) {
                precontractSowDtlTO = convertSowDtlEntityToPOJO(precontractSowDtlEntity);
                if (addExternal) {
                    addEmpCompanies(companyMap, precontractSowDtlTO, precontractSowDtlEntity);
                }
                precontractSowDtlTOs.add(precontractSowDtlTO);
            }
        }
        return precontractSowDtlTOs;

    }

    public static void addEmpCompanies(Map<Long, Boolean> companyMap, PrecontractSowDtlTO precontractSowDtlTO,
            PrecontractSowDtlEntity precontractSowDtlEntity) {
        List<PrecontractSowCmpEntity> precontractSowCmpEntites = null;
        Map<Long, Boolean> existingCompanyMap = new HashMap<>();
        precontractSowCmpEntites = precontractSowDtlEntity.getPrecontractSowCmpEntites();
        PrecontractSowCmpTO precontractSowCmpTO = null;
        if (CommonUtil.isListHasData(precontractSowCmpEntites)) {

            for (PrecontractSowCmpEntity precontractSowCmpEntity : precontractSowCmpEntites) {
                if (companyMap != null
                        && companyMap.containsKey(precontractSowCmpEntity.getPreContractsCmpEntity().getId())) {
                    precontractSowCmpTO = convertSowCmpEntityToPOJO(precontractSowCmpEntity);
                    existingCompanyMap.put(precontractSowCmpEntity.getPreContractsCmpEntity().getId(), true);
                    precontractSowDtlTO.getPrecontractSowCmpTOs().add(precontractSowCmpTO);
                }
            }
        }

        for (Entry<Long, Boolean> preContractCmpTO : companyMap.entrySet()) {
            if (!existingCompanyMap.containsKey(preContractCmpTO.getKey())) {
                precontractSowCmpTO = new PrecontractSowCmpTO();
                precontractSowCmpTO.setPreContractCmpId(preContractCmpTO.getKey());
                precontractSowCmpTO.setPreContractSowDtlId(precontractSowDtlTO.getId());
                precontractSowCmpTO.setVersion(1);
                precontractSowCmpTO.setStatus(StatusCodes.ACTIVE.getValue());
                precontractSowDtlTO.getPrecontractSowCmpTOs().add(precontractSowCmpTO);
            }
        }
    }

    public static PreContractSowResp getPreContractSowTOs(List<PrecontractSowDtlEntity> precontractSowDtlEntites,
            boolean addExternal) {
        PreContractSowResp preContractSowResp = new PreContractSowResp();
        PrecontractSowDtlTO precontractSowDtlTO = null;
        List<PrecontractSowCmpEntity> precontractSowCmpEntites = null;
        for (PrecontractSowDtlEntity precontractSowDtlEntity : precontractSowDtlEntites) {
            if (StatusCodes.ACTIVE.getValue().equals(precontractSowDtlEntity.getStatus())) {
                precontractSowDtlTO = convertSowDtlEntityToPOJO(precontractSowDtlEntity);
                if (addExternal) {
                    precontractSowCmpEntites = precontractSowDtlEntity.getPrecontractSowCmpEntites();
                    PrecontractSowCmpTO precontractSowCmpTO = null;
                    if (CommonUtil.isListHasData(precontractSowCmpEntites)) {
                        for (PrecontractSowCmpEntity precontractSowCmpEntity : precontractSowCmpEntites) {
                            precontractSowCmpTO = convertSowCmpEntityToPOJO(precontractSowCmpEntity);
                            precontractSowDtlTO.getPrecontractSowCmpTOs().add(precontractSowCmpTO);
                        }
                    }
                }
                preContractSowResp.getPrecontractSowDtlTOs().add(precontractSowDtlTO);
            }
        }
        return preContractSowResp;
    }

    public static List<PrecontractSowDtlEntity> populatePreContractsSowDtlEntities(
            List<PrecontractSowDtlTO> precontractSowDtlTOs, boolean version, boolean addExternal,
            ProjSOWItemRepositoryCopy projSOWItemRepositoryCopy) {
        List<PrecontractSowDtlEntity> precontractSowDtlEntites = new ArrayList<>();
        PrecontractSowDtlEntity precontractSowDtlEntity = null;
        PrecontractSowCmpEntity precontractSowCmpEntity = null;
        for (PrecontractSowDtlTO precontractSowDtlTO : precontractSowDtlTOs) {
            precontractSowDtlEntity = convertSowDtlPOJOToEntity(precontractSowDtlTO, projSOWItemRepositoryCopy);
            if (addExternal) {
                for (PrecontractSowCmpTO precontractSowCmpTO : precontractSowDtlTO.getPrecontractSowCmpTOs()) {
                    precontractSowCmpEntity = convertSowCmpPOJOToEntity(precontractSowCmpTO, version);
                    precontractSowDtlEntity.getPrecontractSowCmpEntites().add(precontractSowCmpEntity);
                }
            }
            precontractSowDtlEntites.add(precontractSowDtlEntity);
        }
        return precontractSowDtlEntites;
    }

    public static PrecontractSowDtlTO convertSowDtlEntityToPOJO(PrecontractSowDtlEntity entity) {

        PrecontractSowDtlTO precontractSowDtlTO = new PrecontractSowDtlTO();

        precontractSowDtlTO.setId(entity.getId());
        precontractSowDtlTO.setPreContractId(entity.getPreContractEntity().getId());
        ProjSOWItemEntity sow = entity.getSowId();
        if (sow != null)
            precontractSowDtlTO.setSowId(sow.getId());
        PreContractEntity preContractEntity = entity.getPreContractEntity();
        precontractSowDtlTO.setItemCode(
                ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + preContractEntity.getProjId().getCode() + "-"
                        + preContractEntity.getId() + "-" + ModuleCodesPrefixes.PRE_CONTRACT_SOW_SCH_PREFIX.getDesc()
                        + "-" + AppUtils.formatNumberToString(entity.getId()));
        precontractSowDtlTO.setItemDesc(entity.getItemDesc());
        precontractSowDtlTO.setCostCode(entity.getSowId().getProjCostItemEntity().getCode());
        precontractSowDtlTO.setCostCodeDesc(entity.getSowId().getProjCostItemEntity().getName());
        precontractSowDtlTO.setQuantity(Long.valueOf(entity.getQuantity()));
        precontractSowDtlTO.setLatest(entity.getLatest());
        precontractSowDtlTO.setEstimateCost(entity.getEstimateCost());
        precontractSowDtlTO.setDeliveryPlace(entity.getDeliveryPlace());
        precontractSowDtlTO.setEstimateSowCost(entity.getEstimateCost().multiply(BigDecimal.valueOf(entity.getQuantity())));
        if (CommonUtil.isNotBlankDate(entity.getStartDate())) {
            precontractSowDtlTO.setStartDate(CommonUtil.convertDateToString(entity.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(entity.getFinishDate())) {
            precontractSowDtlTO.setFinishDate(CommonUtil.convertDateToString(entity.getFinishDate()));
        }

        precontractSowDtlTO.setStatus(entity.getStatus());
        return precontractSowDtlTO;
    }

    public static List<PrecontractSowDtlEntity> convertSowDtlSPOJOToEntites(
            List<PrecontractSowDtlTO> precontractSowDtlTOs, ProjSOWItemRepositoryCopy projSOWItemRepositoryCopy) {
        List<PrecontractSowDtlEntity> precontractSowDtlEntites = new ArrayList<>();

        for (PrecontractSowDtlTO precontractSowDtlTO : precontractSowDtlTOs) {
            precontractSowDtlEntites.add(convertSowDtlPOJOToEntity(precontractSowDtlTO, projSOWItemRepositoryCopy));
        }

        return precontractSowDtlEntites;
    }

    public static PrecontractSowDtlEntity convertSowDtlPOJOToEntity(PrecontractSowDtlTO precontractSowDtlTO,
            ProjSOWItemRepositoryCopy projSOWItemRepositoryCopy) {
        PrecontractSowDtlEntity entity = new PrecontractSowDtlEntity();

        entity.setId(precontractSowDtlTO.getId());
        PreContractEntity preContractEntity = new PreContractEntity();
        preContractEntity.setId(precontractSowDtlTO.getPreContractId());
        entity.setPreContractEntity(preContractEntity);
        entity.setSowId(projSOWItemRepositoryCopy.findOne(precontractSowDtlTO.getSowId()));
        entity.setItemDesc(precontractSowDtlTO.getItemDesc());
        entity.setQuantity((int) (long) precontractSowDtlTO.getQuantity());
        entity.setLatest(Boolean.TRUE);
        entity.setEstimateCost(precontractSowDtlTO.getEstimateCost());
        entity.setDeliveryPlace(precontractSowDtlTO.getDeliveryPlace());

        if (CommonUtil.isNotBlankStr(precontractSowDtlTO.getStartDate())) {
            entity.setStartDate(CommonUtil.convertStringToDate(precontractSowDtlTO.getStartDate()));
        }
        if (CommonUtil.isNotBlankStr(precontractSowDtlTO.getFinishDate())) {
            entity.setFinishDate(CommonUtil.convertStringToDate(precontractSowDtlTO.getFinishDate()));
        }
        entity.setStatus(precontractSowDtlTO.getStatus());
        return entity;
    }

    public static List<PrecontractSowCmpTO> populatePreContractCmpSows(
            List<PrecontractSowCmpEntity> precontractSowCmpEntites) {
        List<PrecontractSowCmpTO> precontractSowCmpTOs = new ArrayList<>();
        for (PrecontractSowCmpEntity precontractSowCmpEntity : precontractSowCmpEntites) {
            precontractSowCmpTOs.add(convertSowCmpEntityToPOJO(precontractSowCmpEntity));
        }
        return precontractSowCmpTOs;
    }

    public static List<PrecontractSowCmpEntity> preContractsSowCmpEntities(
            List<PrecontractSowCmpTO> precontractSowCmpTOs, boolean version) {
        List<PrecontractSowCmpEntity> precontractSowCmpEntites = new ArrayList<>();

        for (PrecontractSowCmpTO precontractSowCmpTO : precontractSowCmpTOs) {
            precontractSowCmpEntites.add(convertSowCmpPOJOToEntity(precontractSowCmpTO, version));
        }
        return precontractSowCmpEntites;
    }

    public static PrecontractSowCmpTO convertSowCmpEntityToPOJO(PrecontractSowCmpEntity entity) {
        PrecontractSowCmpTO precontractSowCmpTO = new PrecontractSowCmpTO();

        precontractSowCmpTO.setId(entity.getId());
        precontractSowCmpTO.setRate(entity.getRate());
        precontractSowCmpTO.setVersion(entity.getVersion());
        precontractSowCmpTO.setQuantity(entity.getQuantity());
        precontractSowCmpTO.setComments(entity.getComments());

        if (CommonUtil.isNonBlankLong(entity.getQuantity())) {
            precontractSowCmpTO.setQuantity(entity.getQuantity());
            precontractSowCmpTO.setApproveFlag(true);
        }
        if (CommonUtil.objectNotNull(entity.getPreContractsCmpEntity())) {
            precontractSowCmpTO.setPreContractSowDtlId(entity.getPrecontractSowDtlEntity().getId());
        }
        if (CommonUtil.objectNotNull(entity.getPrecontractSowDtlEntity())) {
            precontractSowCmpTO.setPreContractCmpId(entity.getPreContractsCmpEntity().getId());
        }
        precontractSowCmpTO.setStatus(entity.getStatus());
        return precontractSowCmpTO;
    }

    public static PrecontractSowCmpEntity convertSowCmpPOJOToEntity(PrecontractSowCmpTO precontractSowCmpTO,
            boolean version) {
        PrecontractSowCmpEntity entity = new PrecontractSowCmpEntity();

        if (!version && CommonUtil.isNonBlankLong(precontractSowCmpTO.getId())) {
            entity.setId(precontractSowCmpTO.getId());
        }
        PrecontractSowDtlEntity precontractSowDtlEntity = new PrecontractSowDtlEntity();
        precontractSowDtlEntity.setId(precontractSowCmpTO.getPreContractSowDtlId());
        entity.setPrecontractSowDtlEntity(precontractSowDtlEntity);

        PreContractsCmpEntity preContractsCmpEntity = new PreContractsCmpEntity();
        preContractsCmpEntity.setId(precontractSowCmpTO.getPreContractCmpId());
        entity.setPreContractsCmpEntity(preContractsCmpEntity);
        entity.setQuantity(precontractSowCmpTO.getQuantity());
        entity.setRate(precontractSowCmpTO.getRate());
        entity.setComments(precontractSowCmpTO.getComments());

        if (version) {
            entity.setVersion(precontractSowCmpTO.getVersion() + 1);
        } else {
            entity.setVersion(precontractSowCmpTO.getVersion());
        }
        entity.setStatus(precontractSowCmpTO.getStatus());
        return entity;
    }
}
