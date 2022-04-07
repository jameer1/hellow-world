package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.ServiceTaxTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class ServiceTaxResp extends AppResp {

    private static final long serialVersionUID = -7187428070689540114L;
    private List<ServiceTaxTO> serviceTaxTOs = null;

    public ServiceTaxResp() {
        serviceTaxTOs = new ArrayList<ServiceTaxTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ServiceTaxTO> getServiceTaxTOs() {
        return serviceTaxTOs;
    }

    public void setServiceTaxTOs(List<ServiceTaxTO> serviceTaxTOs) {
        this.serviceTaxTOs = serviceTaxTOs;
    }

}
