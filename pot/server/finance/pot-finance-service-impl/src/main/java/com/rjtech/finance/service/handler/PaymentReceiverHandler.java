package com.rjtech.finance.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.TaxPaymentDetailsTO;
import com.rjtech.finance.model.CodeTypesEntity;
import com.rjtech.finance.model.TaxPaymentReceiverEntity;

public class PaymentReceiverHandler {

    public static TaxPaymentDetailsTO convertEntityToPOJO(TaxPaymentReceiverEntity taxPaymentReceiverEntity) {
        TaxPaymentDetailsTO paymentDetailsTO = new TaxPaymentDetailsTO();
        paymentDetailsTO.setId(taxPaymentReceiverEntity.getId());

        if (CommonUtil.objectNotNull(taxPaymentReceiverEntity.getCodeTypesEntity())) {
            paymentDetailsTO.setCodeTypeCountryProvisionId(taxPaymentReceiverEntity.getCodeTypesEntity().getId());
        }
        paymentDetailsTO.setTaxCode(taxPaymentReceiverEntity.getTaxCode());
        paymentDetailsTO.setTaxDesc(taxPaymentReceiverEntity.getTaxDesc());
        paymentDetailsTO.setBankCode(taxPaymentReceiverEntity.getBankCode());
        paymentDetailsTO.setDeptName(taxPaymentReceiverEntity.getDeptName());
        paymentDetailsTO.setRegAddr(taxPaymentReceiverEntity.getRegAddr());
        paymentDetailsTO.setAccNumber(taxPaymentReceiverEntity.getAccNumber());
        paymentDetailsTO.setBankName(taxPaymentReceiverEntity.getBankName());

        paymentDetailsTO.setStatus(taxPaymentReceiverEntity.getStatus());
        return paymentDetailsTO;
    }

    public static List<TaxPaymentReceiverEntity> convertPOJOToEntity(List<TaxPaymentDetailsTO> paymentDetailsTOs) {
        List<TaxPaymentReceiverEntity> paymentReceiverEntities = new ArrayList<TaxPaymentReceiverEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        TaxPaymentReceiverEntity paymentReceiverEntity = null;
        for (TaxPaymentDetailsTO paymentDetailsTO : paymentDetailsTOs) {
            paymentReceiverEntity = new TaxPaymentReceiverEntity();
            if (CommonUtil.isNonBlankLong(paymentDetailsTO.getId())) {
                paymentReceiverEntity.setId(paymentDetailsTO.getId());

            }

            if (CommonUtil.isNonBlankLong(paymentDetailsTO.getCodeTypeCountryProvisionId())) {
                CodeTypesEntity codeTypesEntity = new CodeTypesEntity();
                codeTypesEntity.setId(paymentDetailsTO.getCodeTypeCountryProvisionId());
                paymentReceiverEntity.setCodeTypesEntity(codeTypesEntity);
            }
            paymentReceiverEntity.setTaxCode(paymentDetailsTO.getTaxCode());
            paymentReceiverEntity.setTaxDesc(paymentDetailsTO.getTaxDesc());
            paymentReceiverEntity.setBankCode(paymentDetailsTO.getBankCode());
            paymentReceiverEntity.setDeptName(paymentDetailsTO.getDeptName());
            paymentReceiverEntity.setRegAddr(paymentDetailsTO.getRegAddr());
            paymentReceiverEntity.setAccNumber(paymentDetailsTO.getAccNumber());
            paymentReceiverEntity.setBankName(paymentDetailsTO.getBankName());

            paymentReceiverEntity.setStatus(paymentDetailsTO.getStatus());
            paymentReceiverEntities.add(paymentReceiverEntity);
        }
        return paymentReceiverEntities;

    }

}
