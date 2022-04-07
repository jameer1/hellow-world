package com.rjtech.common.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.CountryTO;

public class CountrySaveReq extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<CountryTO> countryTOs = new ArrayList<CountryTO>();

    public List<CountryTO> getCountryTOs() {
        return countryTOs;
    }

    public void setCountryTOs(List<CountryTO> countryTOs) {
        this.countryTOs = countryTOs;
    }

}