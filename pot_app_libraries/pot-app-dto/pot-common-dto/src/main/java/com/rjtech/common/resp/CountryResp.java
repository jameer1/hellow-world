package com.rjtech.common.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.CountryTO;

public class CountryResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<CountryTO> countryTOs = null;

    public CountryResp() {
        countryTOs = new ArrayList<CountryTO>();
    }

    public List<CountryTO> getCountryTOs() {
        return countryTOs;
    }

}