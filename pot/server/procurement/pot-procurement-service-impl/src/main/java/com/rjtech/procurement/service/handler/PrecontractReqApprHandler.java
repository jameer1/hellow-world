package com.rjtech.procurement.service.handler;

import java.util.Date;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.procurement.dto.PreContractReqApprTO;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PreContractReqApprEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class PrecontractReqApprHandler {

    public static PreContractReqApprTO convertRequestEntityToPOJO(PreContractReqApprEntity preContractReqApprEntity) {
        PreContractReqApprTO preContractReqApprTO = new PreContractReqApprTO();

        preContractReqApprTO.setId(preContractReqApprEntity.getId());
        preContractReqApprTO.setPreContractId(preContractReqApprEntity.getPreContractEntity().getId());
        PreContractEntity preContractEntity = preContractReqApprEntity.getPreContractEntity();

        preContractReqApprTO.setReqCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                + preContractEntity.getProjId().getCode() + "-" + preContractEntity.getId() + "-"
                + ModuleCodesPrefixes.PRE_CONTRACT_REQ_PREFIX.getDesc() + "-" + preContractReqApprEntity.getId());
        Integer precontractStatus = preContractEntity.getPreContarctStatus();
        if (precontractStatus != null && precontractStatus.equals(5)) {
            preContractReqApprTO.setApprCode(ModuleCodesPrefixes.PRE_CONTRACT_PREFIX.getDesc() + "-"
                    + preContractEntity.getProjId().getCode() + "-" + preContractEntity.getId() + "-"
                    + ModuleCodesPrefixes.PRE_CONTRACT_APPR_PREFIX.getDesc() + "-" + preContractReqApprEntity.getId());
        }

        preContractReqApprTO.setLatest(preContractReqApprEntity.isLatest());

        LabelKeyTO reqUserLabelkeyTO = new LabelKeyTO();
        UserMstrEntity requester = preContractReqApprEntity.getReqUserId();
        reqUserLabelkeyTO.setId(requester.getUserId());
        reqUserLabelkeyTO.setCode(requester.getEmpCode());
        preContractReqApprTO.setReqUserLabelkeyTO(reqUserLabelkeyTO);

        LabelKeyTO apprUserLabelkeyTO = new LabelKeyTO();
        UserMstrEntity approver = preContractReqApprEntity.getApprUserId();
        apprUserLabelkeyTO.setId(approver.getUserId());
        apprUserLabelkeyTO.setCode(approver.getEmpCode());
        preContractReqApprTO.setApprUserLabelkeyTO(apprUserLabelkeyTO);
        if (CommonUtil.isNotBlankDate(preContractReqApprEntity.getReqDate())) {
            preContractReqApprTO.setReqDate(CommonUtil.convertDateToString(preContractReqApprEntity.getReqDate()));
        }
        if (CommonUtil.isNotBlankDate(preContractReqApprEntity.getApprDate())) {
            preContractReqApprTO.setApprDate(CommonUtil.convertDateToString(preContractReqApprEntity.getApprDate()));
        }
        preContractReqApprTO.setReqComments(preContractReqApprEntity.getReqcomments());
        preContractReqApprTO.setApprComments(preContractReqApprEntity.getApprComments());
        preContractReqApprTO.setNotes(preContractReqApprEntity.getNotes());

        preContractReqApprEntity.setStatus(preContractReqApprTO.getStatus());

        return preContractReqApprTO;
    }

    public static PreContractReqApprEntity convertReqApprovalPOJOToEntity(PreContractReqApprTO preContractReqApprTO,
            LoginRepository loginRepository) {
        PreContractReqApprEntity preContractReqApprEntity = new PreContractReqApprEntity();
        if (CommonUtil.objectNotNull(preContractReqApprTO.getId())) {
            preContractReqApprEntity.setId(preContractReqApprTO.getId());
        }
        preContractReqApprEntity.setLatest(preContractReqApprTO.isLatest());
        preContractReqApprEntity.setReqUserId(loginRepository.findOne(AppUserUtils.getUserId()));
        preContractReqApprEntity
                .setApprUserId(loginRepository.findOne(preContractReqApprTO.getApprUserLabelkeyTO().getId()));
        preContractReqApprEntity.setNotes(preContractReqApprTO.getNotes());

        if (preContractReqApprTO.getStatus() != null && preContractReqApprTO.getStatus() > 0) {
            preContractReqApprEntity.setStatus(preContractReqApprTO.getStatus());
        } else {
            preContractReqApprEntity.setStatus(StatusCodes.ACTIVE.getValue());
        }
        preContractReqApprEntity.setReqDate(new Date());
        preContractReqApprEntity.setReqcomments(preContractReqApprTO.getReqComments());
        preContractReqApprEntity.setApprComments(preContractReqApprTO.getApprComments());
        return preContractReqApprEntity;
    }

}
