package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.ProvidentFundTO;
import com.rjtech.finance.model.CodeTypesEntity;
import com.rjtech.finance.model.ProvidentFundEntity;

public class ProvidentFundHandler {

    public static ProvidentFundTO convertEntityToPOJO(ProvidentFundEntity providentFundEntity) {
        ProvidentFundTO providentFundTO = new ProvidentFundTO();
        providentFundTO.setId(providentFundEntity.getId());
        if (CommonUtil.objectNotNull(providentFundEntity.getCodeTypesEntity())) {
            providentFundTO.setCodeTypeCountryProvisionId(providentFundEntity.getCodeTypesEntity().getId());
        }

        providentFundTO.setTaxStatus(providentFundEntity.getTaxStatus());
        providentFundTO.setCode(providentFundEntity.getCode());
        providentFundTO.setName(providentFundEntity.getName());
        providentFundTO.setComments(providentFundEntity.getComments());
        providentFundTO.setCreditDate(providentFundEntity.getCreditDate());
        providentFundTO.setCreditCycle(providentFundEntity.getCreditCycle());
        providentFundTO.setTaxStatus(providentFundEntity.getTaxStatus());

        providentFundTO.setStatus(providentFundEntity.getStatus());
        return providentFundTO;
    }

    public static List<ProvidentFundEntity> convertPOJOToEntity(List<ProvidentFundTO> providentFundTOs) {
        List<ProvidentFundEntity> providentFundEntities = new ArrayList<ProvidentFundEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        ProvidentFundEntity providentFundEntity = null;
        for (ProvidentFundTO providentFundTO : providentFundTOs) {
            providentFundEntity = new ProvidentFundEntity();
            convertPOJOToEntity(providentFundEntity, providentFundTO);
            providentFundEntities.add(providentFundEntity);
        }
        return providentFundEntities;

    }

    public static void convertPOJOToEntity(ProvidentFundEntity providentFundEntity, ProvidentFundTO providentFundTO) {
        if (CommonUtil.isNonBlankLong(providentFundTO.getId())) {
            providentFundEntity.setId(providentFundTO.getId());
        }

        if (CommonUtil.isNonBlankLong(providentFundTO.getCodeTypeCountryProvisionId())) {
            CodeTypesEntity codeTypesEntity = new CodeTypesEntity();
            codeTypesEntity.setId(providentFundTO.getCodeTypeCountryProvisionId());
            providentFundEntity.setCodeTypesEntity(codeTypesEntity);
        }
        providentFundEntity.setTaxStatus(providentFundTO.getSuperFundLabelKeyTO().getName());
        providentFundEntity.setCode(providentFundTO.getCode());
        providentFundEntity.setName(providentFundTO.getName());
        providentFundEntity.setComments(providentFundTO.getComments());
        providentFundEntity.setCreditDate(providentFundTO.getCreditDate());
        providentFundEntity.setCreditCycle(providentFundTO.getCreditCycleLabelKeyTO().getName());

        providentFundEntity.setStatus(providentFundTO.getStatus());
    }

}
