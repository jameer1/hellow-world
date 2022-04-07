package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.NonRegularPayAllowanceTO;
import com.rjtech.finance.model.CodeTypesEntity;
import com.rjtech.finance.model.NonRegularPayAllowanceEntity;

public class NonRegularAllowanceHandler {

    public static NonRegularPayAllowanceTO convertNonRegularMstrEntityToPOJO(
            NonRegularPayAllowanceEntity nonRegularPayMstrEntity) {
        NonRegularPayAllowanceTO nonRegularPayMstrTO = new NonRegularPayAllowanceTO();
        nonRegularPayMstrTO.setId(nonRegularPayMstrEntity.getId());

        if (CommonUtil.objectNotNull(nonRegularPayMstrEntity.getCodeTypesEntity())) {
            nonRegularPayMstrTO.setCodeTypeCountryProvisionId(nonRegularPayMstrEntity.getCodeTypesEntity().getId());
        }

        nonRegularPayMstrTO.setCode(nonRegularPayMstrEntity.getCode());
        nonRegularPayMstrTO.setName(nonRegularPayMstrEntity.getName());
        nonRegularPayMstrTO.setComments(nonRegularPayMstrEntity.getComments());
        nonRegularPayMstrTO.setTaxStatus(nonRegularPayMstrEntity.getTaxStatus());
        nonRegularPayMstrTO.setTaxType(nonRegularPayMstrEntity.getTaxType());

        nonRegularPayMstrTO.setStatus(nonRegularPayMstrEntity.getStatus());

        return nonRegularPayMstrTO;
    }

    public static List<NonRegularPayAllowanceEntity> convertPOJOToEntity(
            List<NonRegularPayAllowanceTO> nonRegularPayAllowanceTOs) {
        List<NonRegularPayAllowanceEntity> nonRegularPayAllowanceEntities = new ArrayList<NonRegularPayAllowanceEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        NonRegularPayAllowanceEntity nonRegularPayAllowanceEntity = null;
        for (NonRegularPayAllowanceTO nonRegularPayAllowanceTO : nonRegularPayAllowanceTOs) {
            nonRegularPayAllowanceEntity = convertPOJOToEntity(nonRegularPayAllowanceTO);
            nonRegularPayAllowanceEntities.add(nonRegularPayAllowanceEntity);
        }
        return nonRegularPayAllowanceEntities;

    }

    public static NonRegularPayAllowanceEntity convertPOJOToEntity(NonRegularPayAllowanceTO nonRegularPayAllowanceTO) {
        NonRegularPayAllowanceEntity nonRegularPayAllowanceEntity;
        nonRegularPayAllowanceEntity = new NonRegularPayAllowanceEntity();
        if (CommonUtil.isNonBlankLong(nonRegularPayAllowanceTO.getId())) {
            nonRegularPayAllowanceEntity.setId(nonRegularPayAllowanceTO.getId());
        }
        if (CommonUtil.isNonBlankLong(nonRegularPayAllowanceTO.getCodeTypeCountryProvisionId())) {
            CodeTypesEntity codeTypesEntity = new CodeTypesEntity();
            codeTypesEntity.setId(nonRegularPayAllowanceTO.getCodeTypeCountryProvisionId());
            nonRegularPayAllowanceEntity.setCodeTypesEntity(codeTypesEntity);
        }

        nonRegularPayAllowanceEntity.setCode(nonRegularPayAllowanceTO.getCode());
        nonRegularPayAllowanceEntity.setName(nonRegularPayAllowanceTO.getName());
        nonRegularPayAllowanceEntity.setComments(nonRegularPayAllowanceTO.getComments());
        nonRegularPayAllowanceEntity.setTaxStatus(nonRegularPayAllowanceTO.getNonRegularLabelKeyTO().getName());
        nonRegularPayAllowanceEntity.setTaxType(nonRegularPayAllowanceTO.getNonRegularLabelKeyTO().getName());

        nonRegularPayAllowanceEntity.setStatus(nonRegularPayAllowanceTO.getStatus());
        return nonRegularPayAllowanceEntity;
    }

}
