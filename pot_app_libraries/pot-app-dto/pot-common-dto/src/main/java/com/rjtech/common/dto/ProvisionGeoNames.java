package com.rjtech.common.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProvisionGeoNames implements Serializable {

    private static final long serialVersionUID = -2783172528497452820L;

    private List<ProvisionTO> geonames;

    public List<ProvisionTO> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<ProvisionTO> geonames) {
        this.geonames = geonames;
    }

}
