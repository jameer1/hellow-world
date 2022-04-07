package com.rjtech.common.service.handler;

import com.rjtech.common.dto.ProvisionTO;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.CountryProvisionEntity;
import com.rjtech.common.utils.CommonUtil;

public class CountryProvisionHandler {

    public static ProvisionTO convertEntityToPOJO(CountryProvisionEntity entity) {
        ProvisionTO provisionTO = new ProvisionTO();
        provisionTO.setId(entity.getId());
        provisionTO.setCode(entity.getCode());
        provisionTO.setName(entity.getName());
        ClientRegEntity client = entity.getClientId();
        if (client != null)
            provisionTO.setClientId(client.getClientId());
        if (CommonUtil.isNotBlankStr(CommonUtil.convertDateToString(entity.getStartDate()))) {
            provisionTO.setStartDate(CommonUtil.convertDateToString(entity.getStartDate()));
        }
        if (CommonUtil.isNotBlankStr(CommonUtil.convertDateToString(entity.getFinishDate()))) {
            provisionTO.setFinishDate(CommonUtil.convertDateToString(entity.getFinishDate()));
        }
        return provisionTO;
    }

    public static CountryProvisionEntity convertPOJOToEntity(ProvisionTO provisionTO) {

        CountryProvisionEntity entity = new CountryProvisionEntity();
        if (CommonUtil.isNonBlankLong(provisionTO.getId())) {
            entity.setId(provisionTO.getId());
        }
        entity.setCode(provisionTO.getCode());
        entity.setName(provisionTO.getName());
        if (CommonUtil.isNotBlankDate(CommonUtil.convertStringToDate(provisionTO.getStartDate()))) {
            entity.setStartDate(CommonUtil.convertStringToDate(provisionTO.getStartDate()));
        }
        if (CommonUtil.isNotBlankDate(CommonUtil.convertStringToDate(provisionTO.getFinishDate()))) {
            entity.setFinishDate(CommonUtil.convertStringToDate(provisionTO.getFinishDate()));
        }
        return entity;
    }

}
