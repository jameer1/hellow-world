package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.TaxRatesRulesCodeDtlCodeTo;
import com.rjtech.centrallib.model.TaxRatesRulesCodeDtlEntity;
import com.rjtech.centrallib.resp.TaxRatesRulesCodeDtlResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.centrallib.req.TaxRatesRulesCodeDtlSaveReq;


public class TaxRatesRulesCodeDtlHandler {

    public static TaxRatesRulesCodeDtlResp convertEntityToPOJO(List<TaxRatesRulesCodeDtlEntity> entities) {
        TaxRatesRulesCodeDtlResp taxRatesRulesCodeDtlResp = new TaxRatesRulesCodeDtlResp();
        TaxRatesRulesCodeDtlCodeTo taxRatesRulesCodeDtlCodeTo = null;
        for (TaxRatesRulesCodeDtlEntity taxRatesRulesCodeDtlEntity : entities) {
            taxRatesRulesCodeDtlCodeTo = convertTaxRatesRulesCodeDtlPOJOFromEnity(taxRatesRulesCodeDtlEntity);
            taxRatesRulesCodeDtlResp.getTaxRatesRulesCodeDtlCodeTos().add(taxRatesRulesCodeDtlCodeTo);
        }
        return taxRatesRulesCodeDtlResp;
    }

    public static TaxRatesRulesCodeDtlCodeTo convertTaxRatesRulesCodeDtlPOJOFromEnity(TaxRatesRulesCodeDtlEntity taxRatesRulesCodeDtlEntity) {

        TaxRatesRulesCodeDtlCodeTo taxRatesRulesCodeDtlCodeTo = new TaxRatesRulesCodeDtlCodeTo();
        if (CommonUtil.objectNotNull(taxRatesRulesCodeDtlEntity)) {
            taxRatesRulesCodeDtlCodeTo.setId(taxRatesRulesCodeDtlEntity.getId());
            taxRatesRulesCodeDtlCodeTo.setNotes(taxRatesRulesCodeDtlEntity.getNotes());
            taxRatesRulesCodeDtlCodeTo.setType(taxRatesRulesCodeDtlEntity.getType());
            taxRatesRulesCodeDtlCodeTo.setMinRange(taxRatesRulesCodeDtlEntity.getMinRange());
            taxRatesRulesCodeDtlCodeTo.setMaxRange(taxRatesRulesCodeDtlEntity.getMaxRange());
            taxRatesRulesCodeDtlCodeTo.setTaxAmount(taxRatesRulesCodeDtlEntity.getTaxAmount());
			taxRatesRulesCodeDtlCodeTo.setTaxPercentage(taxRatesRulesCodeDtlEntity.getTaxPercentage());
            taxRatesRulesCodeDtlCodeTo.setStatus(taxRatesRulesCodeDtlEntity.getStatus());
        }
        return taxRatesRulesCodeDtlCodeTo;
    }

    public static TaxRatesRulesCodeDtlEntity convertPOJOToEntity(TaxRatesRulesCodeDtlSaveReq taxRatesRulesCodeDtlSaveReq) {
      
        TaxRatesRulesCodeDtlEntity taxRatesRulesCodeDtlEntity = null;
        	//System.out.println("====convertPOJOToEntity====TaxRatesRulesCodeDtlHandler============");
            taxRatesRulesCodeDtlEntity = new TaxRatesRulesCodeDtlEntity();
            taxRatesRulesCodeDtlEntity.setId(taxRatesRulesCodeDtlSaveReq.getId());
            taxRatesRulesCodeDtlEntity.setNotes(taxRatesRulesCodeDtlSaveReq.getNotes());
           // System.out.println("====convertPOJOToEntity====TaxRatesRulesCodeDtlHandler=======taxRatesRulesCodeDtlCodeTo.getNotes()="+taxRatesRulesCodeDtlSaveReq.getNotes());
            taxRatesRulesCodeDtlEntity.setType(taxRatesRulesCodeDtlSaveReq.getType());
            //System.out.println("====convertPOJOToEntity====TaxRatesRulesCodeDtlHandler=======taxRatesRulesCodeDtlCodeTo.getType()="+taxRatesRulesCodeDtlSaveReq.getType());
            taxRatesRulesCodeDtlEntity.setMinRange(taxRatesRulesCodeDtlSaveReq.getMinRange());
            taxRatesRulesCodeDtlEntity.setMaxRange(taxRatesRulesCodeDtlSaveReq.getMaxRange());
            //System.out.println("====convertPOJOToEntity====TaxRatesRulesCodeDtlHandler=======taxRatesRulesCodeDtlCodeTo.getMaxRange()="+taxRatesRulesCodeDtlSaveReq.getMaxRange());
            taxRatesRulesCodeDtlEntity.setTaxAmount(taxRatesRulesCodeDtlSaveReq.getTaxAmount());
            //System.out.println("====convertPOJOToEntity====TaxRatesRulesCodeDtlHandler=======taxRatesRulesCodeDtlCodeTo.getTaxAmount()="+taxRatesRulesCodeDtlSaveReq.getTaxAmount());
			taxRatesRulesCodeDtlEntity.setTaxPercentage(taxRatesRulesCodeDtlSaveReq.getTaxPercentage());
            taxRatesRulesCodeDtlEntity.setStatus(taxRatesRulesCodeDtlSaveReq.getStatus());
            //System.out.println("====convertPOJOToEntity====TaxRatesRulesCodeDtlHandler=======taxRatesRulesCodeDtlCodeTo.getStatus()="+taxRatesRulesCodeDtlSaveReq.getStatus());
        return taxRatesRulesCodeDtlEntity;
    }

}
