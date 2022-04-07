package com.rjtech.common.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryGeoNames {

    private List<CountryInfoTO> geonames;

    public List<CountryInfoTO> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<CountryInfoTO> geonames) {
        this.geonames = geonames;
    }

}
