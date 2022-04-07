package com.rjtech.finance.resp;

import java.util.HashMap;
import java.util.Map;

public class TaxCodeCountryProviSionRespMap {

    Map<String, Integer> taxCodeCountryUniqueCodeMap = new HashMap<String, Integer>();

    public Map<String, Integer> getTaxCodeCountryUniqueCodeMap() {
        return taxCodeCountryUniqueCodeMap;
    }

    public void setTaxCodeCountryUniqueCodeMap(Map<String, Integer> taxCodeCountryUniqueCodeMap) {
        this.taxCodeCountryUniqueCodeMap = taxCodeCountryUniqueCodeMap;
    }

}
