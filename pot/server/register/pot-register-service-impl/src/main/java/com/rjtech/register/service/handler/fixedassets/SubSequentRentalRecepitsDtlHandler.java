package com.rjtech.register.service.handler.fixedassets;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.fixedassets.dto.SubSequentRentalRecepitsDtlTO;
import com.rjtech.register.fixedassets.model.SubSequentRentalRecepitsDtlEntity;

public class SubSequentRentalRecepitsDtlHandler { 
    
    public static SubSequentRentalRecepitsDtlTO convertEntityToPOJO(SubSequentRentalRecepitsDtlEntity entity) {
        SubSequentRentalRecepitsDtlTO subSequentRentalRecepitsDtlTO = new SubSequentRentalRecepitsDtlTO();
        subSequentRentalRecepitsDtlTO.setId(entity.getId());
        subSequentRentalRecepitsDtlTO.setGivenDate(CommonUtil.convertDateToString(entity.getGivenDate()));
        subSequentRentalRecepitsDtlTO.setCurrency(entity.getCurrency());
        subSequentRentalRecepitsDtlTO.setModeOfPayment(entity.getModeOfPayment());
        subSequentRentalRecepitsDtlTO.setAmountPaid(entity.getAmountPaid());
        subSequentRentalRecepitsDtlTO.setNote(entity.getNote());
        return subSequentRentalRecepitsDtlTO;
    }
   
    public static SubSequentRentalRecepitsDtlEntity convertPOJOTOEntity(SubSequentRentalRecepitsDtlEntity entity, SubSequentRentalRecepitsDtlTO subSequentRentalRecepitsDtlTO) {
        if (CommonUtil.isNonBlankLong(subSequentRentalRecepitsDtlTO.getId())) {
            entity.setId(subSequentRentalRecepitsDtlTO.getId());
        }
        entity.setGivenDate(CommonUtil.convertStringToDate(subSequentRentalRecepitsDtlTO.getGivenDate()));
        entity.setCurrency(subSequentRentalRecepitsDtlTO.getCurrency());
        entity.setModeOfPayment(subSequentRentalRecepitsDtlTO.getModeOfPayment());
        entity.setAmountPaid(subSequentRentalRecepitsDtlTO.getAmountPaid());
        entity.setNote(subSequentRentalRecepitsDtlTO.getNote());
        entity.setStatus(StatusCodes.ACTIVE.getValue());
        return entity;
    }

   

}
