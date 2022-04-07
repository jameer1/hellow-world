package com.rjtech.procurement.service.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.WorkFlowStatus;
import com.rjtech.procurement.dto.*;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractReqApprEntity;
import com.rjtech.procurement.model.PreContractsCmpEntity;
import com.rjtech.procurement.model.PurchaseOrderDetailsEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
import com.rjtech.procurement.repository.PreContractCmpRepository;
import com.rjtech.procurement.repository.PurchaseOrderDetailsRepository;
import com.rjtech.procurement.repository.PurchaseOrderRepository;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class PurchaseOrderHandler {

    public static List<PurchaseOrderEntity> createPurchaseOrder(PreContractTO contractTO,
                                                                Map<Long, Long> approvedCompanyMap, PreContractCmpRepository preContractCmpRepository,
                                                                PurchaseOrderDetailsTO poDetailsTO, PurchaseOrderDetailsRepository poDetailRepository, Date poStartDate,
                                                                Date poFinishDate, String poProcureType, EPSProjRepository epsProjRepository,
                                                                LoginRepository loginRepository) {

        List<PurchaseOrderEntity> purchaseOrderEntities = new ArrayList<>();
        PurchaseOrderEntity purchaseOrderEntity = null;
        List<PreContractCmpTO> preContractCmpTOs =contractTO.getPreContractCmpTOs();

        for (PreContractCmpTO contractCmpTO : preContractCmpTOs) {
            if (approvedCompanyMap != null && approvedCompanyMap.get(contractCmpTO.getId()) != null) {
                PreContractsCmpEntity preContractsCmpEntity = preContractCmpRepository.findOne(contractCmpTO.getId());
                purchaseOrderEntity = populatePurchaseOrderEntity(contractTO, preContractsCmpEntity, epsProjRepository,
                        loginRepository);
                purchaseOrderEntity.setProcureType(poProcureType);
                if (CommonUtil.isNotBlankDate(poStartDate))
                    purchaseOrderEntity.setStartDate(poStartDate);

                if (CommonUtil.isNotBlankDate(poFinishDate))
                    purchaseOrderEntity.setFinsihDate(poFinishDate);

                if (poDetailsTO != null)
                    purchaseOrderEntity.setPoDetailsEntity(
                            PurchaseOrderDetailsHandler.convertPOJOToEntity(poDetailsTO, poDetailRepository));
                purchaseOrderEntities.add(purchaseOrderEntity);
                contractCmpTO.setCmpStatus(WorkFlowStatus.APPROVED.getValue());
            } else {
                contractCmpTO.setCmpStatus(WorkFlowStatus.REJECTED.getValue());
            }
        }
        return purchaseOrderEntities;
    }

    public static PurchaseOrderEntity populatePurchaseOrderEntity(PreContractTO contractTO,
                                                                  PreContractsCmpEntity preContractsCmpEntity, EPSProjRepository epsProjRepository,
                                                                  LoginRepository loginRepository) {
        PurchaseOrderEntity purchaseOrderEntity;
        purchaseOrderEntity = new PurchaseOrderEntity();
        purchaseOrderEntity.setProjId(epsProjRepository.findOne(contractTO.getProjId()));
        purchaseOrderEntity.setApprUserId(loginRepository.findOne(AppUserUtils.getUserId()));
        purchaseOrderEntity.setStatus(StatusCodes.ACTIVE.getValue());
        purchaseOrderEntity.setPreContractsCmpEntity(preContractsCmpEntity);
        return purchaseOrderEntity;
    }

    public static PurchaseOrderEntity populatePurchaseOrderEntityRepeat(ProcurementPoRepeatpoTO contractTO,
                                                                        PreContractsCmpEntity preContractsCmpEntity, EPSProjRepository epsProjRepository,
                                                                        LoginRepository loginRepository) {
        PurchaseOrderEntity purchaseOrderEntity;
        purchaseOrderEntity = new PurchaseOrderEntity();
        purchaseOrderEntity.setProjId(epsProjRepository.findOne(contractTO.getProjId()));
        purchaseOrderEntity.setApprUserId(loginRepository.findOne(AppUserUtils.getUserId()));
        purchaseOrderEntity.setStatus(StatusCodes.ACTIVE.getValue());
        purchaseOrderEntity.setPreContractsCmpEntity(preContractsCmpEntity);
        return purchaseOrderEntity;
    }

    public static PurchaseOrderEntity convertPOJOToEntity(PurchaseOrderTO pruchaseOrderTO,
                                                          PurchaseOrderDetailsRepository poDetailRepository, PurchaseOrderRepository purchaseOrderRepository,
                                                          EPSProjRepository epsProjRepository, LoginRepository loginRepository) {
        PurchaseOrderEntity pruchaseOrderEntity = new PurchaseOrderEntity();
        if (CommonUtil.isNonBlankLong(pruchaseOrderTO.getId())) {
            pruchaseOrderEntity.setId(pruchaseOrderTO.getId());
        }
        if (AppUtils.isNotNull(pruchaseOrderTO.getParentId()))
            pruchaseOrderEntity.setParentId(purchaseOrderRepository.findOne(pruchaseOrderTO.getParentId()));

        PreContractsCmpEntity preContractsCmpEntity = new PreContractsCmpEntity();
        preContractsCmpEntity.setId(pruchaseOrderTO.getPreContractCmpTO().getId());
        pruchaseOrderEntity.setPreContractsCmpEntity(preContractsCmpEntity);
        pruchaseOrderEntity.setProjId(epsProjRepository.findOne(pruchaseOrderTO.getProjId()));
        pruchaseOrderEntity.setProcureType(pruchaseOrderTO.getProcureType());
        pruchaseOrderEntity.setApprUserId(loginRepository.findOne(pruchaseOrderTO.getApprUserId()));
        pruchaseOrderEntity.setAmount(pruchaseOrderTO.getAmount());
        pruchaseOrderEntity.setPaymentInDays(pruchaseOrderTO.getPaymentInDays());
        pruchaseOrderEntity.setStartDate(CommonUtil.convertStringToDate(pruchaseOrderTO.getStartDate()));
        pruchaseOrderEntity.setFinsihDate(CommonUtil.convertStringToDate(pruchaseOrderTO.getFinsihDate()));

        pruchaseOrderEntity.setStatus(pruchaseOrderTO.getStatus());

        if (pruchaseOrderTO.getPoDetailsTO() != null) {
            PurchaseOrderDetailsEntity poDetailsEntity = PurchaseOrderDetailsHandler
                    .convertPOJOToEntity(pruchaseOrderTO.getPoDetailsTO(), poDetailRepository);
            pruchaseOrderEntity.setPoDetailsEntity(poDetailsEntity);
        }

        return pruchaseOrderEntity;
    }

    public static PurchaseOrderTO convertEntityToPOJO(PurchaseOrderEntity pruchaseOrderEntity) {
        PurchaseOrderTO pruchaseOrderTO = new PurchaseOrderTO();

        pruchaseOrderTO.setId(pruchaseOrderEntity.getId());
        boolean isRepeatPO = false;
        PurchaseOrderEntity parent = pruchaseOrderEntity.getParentId();
        if (AppUtils.isNotNull(parent))
        {
            pruchaseOrderTO.setParentId(parent.getId());
            pruchaseOrderTO.setPoParentCode(generatePurchaseOrderCode(parent,false));
            isRepeatPO = true;
        }
        pruchaseOrderTO.setCode(generatePurchaseOrderCode(pruchaseOrderEntity,isRepeatPO));

        pruchaseOrderTO.setPreContractCmpTO(
                PrecontractCmpHandler.convertEntityToPOJO(pruchaseOrderEntity.getPreContractsCmpEntity()));
        pruchaseOrderTO.setProjId(pruchaseOrderEntity.getProjId().getProjectId());
        pruchaseOrderTO.setProcureType(pruchaseOrderEntity.getProcureType());
        pruchaseOrderTO.setCompleteProcureType(pruchaseOrderEntity.getProcureType());
        UserMstrEntity apprUser = pruchaseOrderEntity.getApprUserId();
        if (AppUtils.isNotNull(apprUser)) {
            pruchaseOrderTO.setApprUserId(apprUser.getUserId());
            pruchaseOrderTO.setApprUserCode(apprUser.getEmpCode());
            pruchaseOrderTO.setApprUserName(apprUser.getDisplayName());
        }
        pruchaseOrderTO.setAmount(pruchaseOrderEntity.getAmount());
        pruchaseOrderTO.setPaymentInDays(pruchaseOrderEntity.getPaymentInDays());
        pruchaseOrderTO.setStartDate(CommonUtil.convertDateToString(pruchaseOrderEntity.getStartDate()));
        pruchaseOrderTO.setFinsihDate(CommonUtil.convertDateToString(pruchaseOrderEntity.getFinsihDate()));
        pruchaseOrderTO.setCreatedOn(pruchaseOrderEntity.getCreatedOn());
        if (pruchaseOrderEntity.getPoDetailsEntity() != null) {
            PurchaseOrderDetailsTO poDetailsTO = PurchaseOrderDetailsHandler
                    .convertEntityToPOJO(pruchaseOrderEntity.getPoDetailsEntity());
            pruchaseOrderTO.setPoDetailsTO(poDetailsTO);
        }

        pruchaseOrderTO.setStatus(pruchaseOrderEntity.getStatus());
        return pruchaseOrderTO;
    }

    public static PreContractTO populatePreContractPurchaseOrder(PreContractEntity preContractEntity) {
        PreContractTO precontractTO = PrecontractHandler.convertPreContractEntityTOPOJO(preContractEntity);
        precontractTO.setCurrencyCode(preContractEntity.getCurrencyCode());

        PreContractReqApprTO preContractReqApprTO = null;
        for (PreContractReqApprEntity preContractReqApprEntity : preContractEntity.getPreContractReqApprEntities()) {
            if (preContractReqApprEntity.isLatest()) {
                preContractReqApprTO = PrecontractReqApprHandler.convertRequestEntityToPOJO(preContractReqApprEntity);
                precontractTO.setPreContractReqApprTO(preContractReqApprTO);
            }
        }
        return precontractTO;
    }

    public static String generatePurchaseOrderCode(PurchaseOrderEntity pruchaseOrderEntity, boolean isRepeatPO) {
        PreContractEntity preContractEntity = pruchaseOrderEntity.getPreContractsCmpEntity().getPreContractEntity();
        return (ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-" + preContractEntity.getProjId().getCode() + "-"
                + preContractEntity.getId() + "-" + ModuleCodesPrefixes.PURCHASE_ORDER.getDesc() + "-"+
                (isRepeatPO==true?"REPEAT-":"")+
                + pruchaseOrderEntity.getId());
    }

}