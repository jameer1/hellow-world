package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.ServiceTaxTO;

public class ServiceTaxSaveReq extends ClientTO {

    private static final long serialVersionUID = -3919944497758405947L;
    private List<ServiceTaxTO> serviceTaxTOs = new ArrayList<ServiceTaxTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ServiceTaxTO> getServiceTaxTOs() {
        return serviceTaxTOs;
    }

    public void setServiceTaxTOs(List<ServiceTaxTO> serviceTaxTOs) {
        this.serviceTaxTOs = serviceTaxTOs;
    }

}
