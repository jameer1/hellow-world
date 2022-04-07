package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.PayDeductionTO;
import com.rjtech.finance.model.CodeTypesEntity;
import com.rjtech.finance.model.PayDeductionEntity;

public class PayDeductionHandler {

    public static PayDeductionTO convertEntityToPOJO(PayDeductionEntity deductionEntity) {
        PayDeductionTO payDeductionTO = new PayDeductionTO();
        payDeductionTO.setId(deductionEntity.getId());
        if (CommonUtil.objectNotNull(deductionEntity.getCodeTypesEntity())) {
            payDeductionTO.setCodeTypeCountryProvisionId(deductionEntity.getCodeTypesEntity().getId());
        }
        payDeductionTO.setTaxStatus(deductionEntity.getTaxStatus());
        payDeductionTO.setCode(deductionEntity.getCode());
        payDeductionTO.setName(deductionEntity.getName());
        payDeductionTO.setComments(deductionEntity.getComments());

        payDeductionTO.setStatus(deductionEntity.getStatus());
        return payDeductionTO;
    }

    public static List<PayDeductionEntity> convertPOJOToEntity(List<PayDeductionTO> payDeductionTOs) {
        List<PayDeductionEntity> payDeductionEntities = new ArrayList<PayDeductionEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        PayDeductionEntity payDeductionEntity = null;
        for (PayDeductionTO deductionTO : payDeductionTOs) {
            payDeductionEntity = convertPOJOToEntity(deductionTO);
            payDeductionEntities.add(payDeductionEntity);
        }
        return payDeductionEntities;

    }

    private static PayDeductionEntity convertPOJOToEntity(PayDeductionTO payDeductionTO) {
        PayDeductionEntity payDeductionEntity;
        payDeductionEntity = new PayDeductionEntity();
        if (CommonUtil.isNonBlankLong(payDeductionTO.getId())) {
            payDeductionEntity.setId(payDeductionTO.getId());
        }

        if (CommonUtil.isNonBlankLong(payDeductionTO.getCodeTypeCountryProvisionId())) {
            CodeTypesEntity codeTypesEntity = new CodeTypesEntity();
            codeTypesEntity.setId(payDeductionTO.getCodeTypeCountryProvisionId());
            payDeductionEntity.setCodeTypesEntity(codeTypesEntity);
        }

        payDeductionEntity.setTaxStatus(payDeductionTO.getTaxStatus());
        payDeductionEntity.setCode(payDeductionTO.getCode());
        payDeductionEntity.setName(payDeductionTO.getName());
        payDeductionEntity.setComments(payDeductionTO.getComments());

        payDeductionEntity.setStatus(payDeductionTO.getStatus());
        return payDeductionEntity;
    }

}
