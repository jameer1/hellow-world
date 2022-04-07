package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.CountryProvinceCodeTo;


public class CountryProvinceCodeSaveReq extends CountryProvinceCodeTo {

    /**
     * 
     */
    private static final long serialVersionUID = -1944315930381133121L;
    
    private List<CountryProvinceCodeTo> countryProvinceCodeTos = new ArrayList<CountryProvinceCodeTo>(5);

    public List<CountryProvinceCodeTo> getCountryProvinceCodeTos() {
        return countryProvinceCodeTos;
    }

    public void setCountryProvinceCodeTos(List<CountryProvinceCodeTo> countryProvinceCodeTos) {
        this.countryProvinceCodeTos = countryProvinceCodeTos;
    }

    

}
