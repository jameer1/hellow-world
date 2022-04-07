package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class PayPeriodCycleDelReq extends ClientTO {

    private static final long serialVersionUID = -7252835744141909230L;
    private List<Long> payPeriodIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getPayPeriodIds() {
        return payPeriodIds;
    }

    public void setPayPeriodIds(List<Long> payPeriodIds) {
        this.payPeriodIds = payPeriodIds;
    }

}
