package com.rjtech.finance.req;

import com.rjtech.common.dto.ClientTO;

public class PayPeriodCycleGetReq extends ClientTO {

    private static final long serialVersionUID = -6800762129794986516L;

    private String payRollCycle;

    public String getPayRollCycle() {
        return payRollCycle;
    }

    public void setPayRollCycle(String payRollCycle) {
        this.payRollCycle = payRollCycle;
    }

}
