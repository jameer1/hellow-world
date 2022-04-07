package com.rjtech.common.resp;

import java.util.List;

import com.rjtech.common.dto.CountryInfoTO;

public class CountryInfoResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<CountryInfoTO> countryInfoTOs;

    public List<CountryInfoTO> getCountryInfoTOs() {
        return countryInfoTOs;
    }

    public void setCountryInfoTOs(List<CountryInfoTO> countryInfoTOs) {
        this.countryInfoTOs = countryInfoTOs;
    }

}
