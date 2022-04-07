package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.UnitPayRateTO;
import com.rjtech.finance.model.UnitPayRateEntity;
import com.rjtech.finance.resp.UnitPayRateResp;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class UnitPayRateHandler {

    public static UnitPayRateResp convertEntityToPOJO(List<UnitPayRateEntity> unitPayRateEntities) {
        UnitPayRateResp unitPayRateResp = new UnitPayRateResp();
        UnitPayRateTO unitPayRateTO = null;
        for (UnitPayRateEntity unitPayRateEntity : unitPayRateEntities) {
            unitPayRateTO = convertUnitPayFromEntity(unitPayRateEntity);
            unitPayRateResp.getUnitPayRateTOs().add(unitPayRateTO);
        }
        return unitPayRateResp;

    }

    private static UnitPayRateTO convertUnitPayFromEntity(UnitPayRateEntity unitPayRateEntity) {
        UnitPayRateTO unitPayRateTO = new UnitPayRateTO();
        unitPayRateTO.setId(unitPayRateEntity.getId());
        unitPayRateTO.setCode(unitPayRateEntity.getCode());
        unitPayRateTO.setName(unitPayRateEntity.getName());
        unitPayRateTO.setNotes(unitPayRateEntity.getNotes());
        unitPayRateTO.setClientId(unitPayRateEntity.getClientId().getClientId());

        unitPayRateTO.setStatus(unitPayRateEntity.getStatus());
        return unitPayRateTO;
    }

    public static List<UnitPayRateEntity> convertPOJOToEntity(List<UnitPayRateTO> unitPayRateTOs,
            ClientRegRepository clientRegRepository) {

        List<UnitPayRateEntity> unitPayRateEntities = new ArrayList<UnitPayRateEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        UnitPayRateEntity unitPayRateEntity = null;
        for (UnitPayRateTO unitPayRateTO : unitPayRateTOs) {
            unitPayRateEntity = new UnitPayRateEntity();
            if (CommonUtil.isNonBlankLong(unitPayRateTO.getId())) {
                unitPayRateEntity.setId(unitPayRateTO.getId());
            }
            unitPayRateEntity.setCode(unitPayRateTO.getCode());
            unitPayRateEntity.setName(unitPayRateTO.getName());
            unitPayRateEntity.setNotes(unitPayRateTO.getNotes());
            if (CommonUtil.isNonBlankLong(unitPayRateTO.getClientId())) {
                unitPayRateEntity.setClientId(clientRegRepository.findOne(unitPayRateTO.getClientId()));
            } else {
                unitPayRateEntity.setClientId(clientRegRepository.findOne(AppUserUtils.getClientId()));
            }
            unitPayRateEntity.setStatus(unitPayRateTO.getStatus());
            unitPayRateEntities.add(unitPayRateEntity);
        }
        return unitPayRateEntities;

    }

}
