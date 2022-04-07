package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.PayRollCycleTO;
import com.rjtech.finance.model.PayRollEntity;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class PayPeriodCycleHandler {

    public static PayRollCycleTO convertEntityToPOJO(PayRollEntity payRollEntity) {
        PayRollCycleTO payRollCycleTO = new PayRollCycleTO();
        payRollCycleTO.setId(payRollEntity.getId());
        payRollCycleTO.setPayRollCycle(payRollEntity.getPayRollCycle());
        payRollCycleTO.setSelectYear(payRollEntity.getSelectYear());

        payRollCycleTO.setStatus(payRollEntity.getStatus());
        return payRollCycleTO;
    }

    public static List<PayRollEntity> convertPOJOToEntity(List<PayRollCycleTO> payRollCycleTOs,
            ClientRegRepository clientRegRepository) {

        List<PayRollEntity> payRollEntities = new ArrayList<PayRollEntity>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        PayRollEntity payRollEntity = null;
        for (PayRollCycleTO payRollCycleTO : payRollCycleTOs) {
            payRollEntity = new PayRollEntity();
            if (CommonUtil.isNonBlankLong(payRollCycleTO.getId())) {
                payRollEntity.setId(payRollCycleTO.getId());
            }
            payRollEntity.setPayRollCycle(payRollCycleTO.getPayRollCycle());
            payRollEntity.setSelectYear(payRollCycleTO.getSelectYear());
            if (CommonUtil.isNonBlankLong(payRollCycleTO.getClientId())) {
                payRollEntity.setClientId(clientRegRepository.findOne(payRollCycleTO.getClientId()));
            } else {
                payRollEntity.setClientId(clientRegRepository.findOne(AppUserUtils.getClientId()));
            }

            payRollEntity.setStatus(payRollCycleTO.getStatus());
            payRollEntities.add(payRollEntity);
        }
        return payRollEntities;

    }

}
