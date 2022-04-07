package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.RegularPayAllowanceTO;
import com.rjtech.finance.model.CodeTypesEntity;
import com.rjtech.finance.model.RegularPayAllowanceEntity;

public class RegularAllowanceHandler {

    public static RegularPayAllowanceTO convertEntityToPOJO(RegularPayAllowanceEntity regularPayAllowanceEntity) {
        RegularPayAllowanceTO regularPayAllowanceTO = new RegularPayAllowanceTO();
        regularPayAllowanceTO.setId(regularPayAllowanceEntity.getId());

        if (CommonUtil.objectNotNull(regularPayAllowanceEntity.getCodeTypesEntity())) {
            regularPayAllowanceTO.setCodeTypeCountryProvisionId(regularPayAllowanceEntity.getCodeTypesEntity().getId());
        }
        regularPayAllowanceTO.setCode(regularPayAllowanceEntity.getCode());
        regularPayAllowanceTO.setName(regularPayAllowanceEntity.getName());
        regularPayAllowanceTO.setComments(regularPayAllowanceEntity.getComments());
        regularPayAllowanceTO.setTaxStatus(regularPayAllowanceEntity.getTaxStatus());

        regularPayAllowanceTO.setStatus(regularPayAllowanceEntity.getStatus());
        return regularPayAllowanceTO;
    }

    public static List<RegularPayAllowanceEntity> convertPOJOToEntity(
            List<RegularPayAllowanceTO> regularPayAllowanceTOs) {
        List<RegularPayAllowanceEntity> regularPayAllowanceEntities = new ArrayList<RegularPayAllowanceEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        RegularPayAllowanceEntity regularPayAllowanceEntity = null;
        for (RegularPayAllowanceTO regularPayAllowanceTO : regularPayAllowanceTOs) {
            regularPayAllowanceEntity = convertPOJOToEntity(regularPayAllowanceTO);
            regularPayAllowanceEntities.add(regularPayAllowanceEntity);
        }
        return regularPayAllowanceEntities;

    }

    public static RegularPayAllowanceEntity convertPOJOToEntity(RegularPayAllowanceTO regularPayAllowanceTO) {
        RegularPayAllowanceEntity regularPayAllowanceEntity = new RegularPayAllowanceEntity();
        if (CommonUtil.isNonBlankLong(regularPayAllowanceTO.getId())) {
            regularPayAllowanceEntity.setId(regularPayAllowanceTO.getId());
        }

        if (CommonUtil.isNonBlankLong(regularPayAllowanceTO.getCodeTypeCountryProvisionId())) {
            CodeTypesEntity codeTypesEntity = new CodeTypesEntity();
            codeTypesEntity.setId(regularPayAllowanceTO.getCodeTypeCountryProvisionId());
            regularPayAllowanceEntity.setCodeTypesEntity(codeTypesEntity);
        }

        regularPayAllowanceEntity.setCode(regularPayAllowanceTO.getCode());
        regularPayAllowanceEntity.setName(regularPayAllowanceTO.getName());
        regularPayAllowanceEntity.setComments(regularPayAllowanceTO.getComments());
        regularPayAllowanceEntity.setTaxStatus(regularPayAllowanceTO.getRegularLabelKeyTO().getName());

        regularPayAllowanceEntity.setStatus(regularPayAllowanceTO.getStatus());
        return regularPayAllowanceEntity;
    }

}
