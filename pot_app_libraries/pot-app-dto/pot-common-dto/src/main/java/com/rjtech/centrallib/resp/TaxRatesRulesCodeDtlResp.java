package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.common.dto.TaxRatesRulesCodeDtlCodeTo;
import com.rjtech.common.resp.AppResp;

public class TaxRatesRulesCodeDtlResp extends AppResp {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<TaxRatesRulesCodeDtlCodeTo> taxRatesRulesCodeDtlCodeTos = new ArrayList<TaxRatesRulesCodeDtlCodeTo>();
    public List<TaxRatesRulesCodeDtlCodeTo> getTaxRatesRulesCodeDtlCodeTos() {
        return taxRatesRulesCodeDtlCodeTos;
    }
    public void setTaxRatesRulesCodeDtlCodeTos(List<TaxRatesRulesCodeDtlCodeTo> taxRatesRulesCodeDtlCodeTos) {
        this.taxRatesRulesCodeDtlCodeTos = taxRatesRulesCodeDtlCodeTos;
    }

}
