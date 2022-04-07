package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.TaxRatesRulesCodeDtlCodeTo;


public class TaxRatesRulesCodeDtlSaveReq extends TaxRatesRulesCodeDtlCodeTo {

    /**
     * 
     */
    private static final long serialVersionUID = -1944315930381133121L;
    
    private List<TaxRatesRulesCodeDtlCodeTo> taxRatesRulesCodeDtlCodeTos = new ArrayList<TaxRatesRulesCodeDtlCodeTo>(5);
    private TaxRatesRulesCodeDtlCodeTo taxRatesRulesCodeDtlCodeTo = new TaxRatesRulesCodeDtlCodeTo();
    
    public List<TaxRatesRulesCodeDtlCodeTo> getTaxRatesRulesCodeDtlCodeTos() {
        return taxRatesRulesCodeDtlCodeTos;
    }

    public void setTaxRatesRulesCodeDtlCodeTos(List<TaxRatesRulesCodeDtlCodeTo> taxRatesRulesCodeDtlCodeTos) {
        this.taxRatesRulesCodeDtlCodeTos = taxRatesRulesCodeDtlCodeTos;
    }
    
    public TaxRatesRulesCodeDtlCodeTo getTaxRatesRulesCodeDtlCodeTo() {
        return taxRatesRulesCodeDtlCodeTo;
    }

    public void setTaxRatesRulesCodeDtlCodeTo(TaxRatesRulesCodeDtlCodeTo taxRatesRulesCodeDtlCodeTo) {
        this.taxRatesRulesCodeDtlCodeTo = taxRatesRulesCodeDtlCodeTo;
    }

    

}
