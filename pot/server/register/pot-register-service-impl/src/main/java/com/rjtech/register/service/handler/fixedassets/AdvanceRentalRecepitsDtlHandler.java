package com.rjtech.register.service.handler.fixedassets;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.fixedassets.dto.AdvanceRentalRecepitsDtlTO;
import com.rjtech.register.fixedassets.model.AdvanceRentalRecepitsDtlEntity;

public class AdvanceRentalRecepitsDtlHandler {   

    public static AdvanceRentalRecepitsDtlTO convertEntityToPOJO(AdvanceRentalRecepitsDtlEntity entity) {
        AdvanceRentalRecepitsDtlTO advanceRentalRecepitsDtlTO = new AdvanceRentalRecepitsDtlTO();
        advanceRentalRecepitsDtlTO.setId(entity.getId());
        advanceRentalRecepitsDtlTO.setGivenDate(CommonUtil.convertDateToString(entity.getGivenDate()));
        advanceRentalRecepitsDtlTO.setCurrency(entity.getCurrency());
        advanceRentalRecepitsDtlTO.setModeOfPayment(entity.getModeOfPayment());
        advanceRentalRecepitsDtlTO.setAmountPaid(entity.getAmountPaid());
        advanceRentalRecepitsDtlTO.setNote(entity.getNote());
        advanceRentalRecepitsDtlTO.setReceiptNumber(entity.getReceiptNumber());
        return advanceRentalRecepitsDtlTO;
    }
    
    public static AdvanceRentalRecepitsDtlEntity convertPOJOTOEntity(AdvanceRentalRecepitsDtlEntity entity, AdvanceRentalRecepitsDtlTO advanceRentalRecepitsDtlTO) {
        if (CommonUtil.isNonBlankLong(advanceRentalRecepitsDtlTO.getId())) {
            entity.setId(advanceRentalRecepitsDtlTO.getId());
        }
        entity.setGivenDate(CommonUtil.convertStringToDate(advanceRentalRecepitsDtlTO.getGivenDate()));
        entity.setCurrency(advanceRentalRecepitsDtlTO.getCurrency());
        entity.setModeOfPayment(advanceRentalRecepitsDtlTO.getModeOfPayment());
        entity.setAmountPaid(advanceRentalRecepitsDtlTO.getAmountPaid());
        entity.setNote(advanceRentalRecepitsDtlTO.getNote());
        entity.setReceiptNumber(advanceRentalRecepitsDtlTO.getReceiptNumber());
        entity.setStatus(StatusCodes.ACTIVE.getValue());
        return entity;
    }
}
