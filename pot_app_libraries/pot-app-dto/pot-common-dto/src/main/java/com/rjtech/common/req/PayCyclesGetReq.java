package com.rjtech.common.req;

import com.rjtech.common.dto.ClientTO;

public class PayCyclesGetReq extends ClientTO {

    private static final long serialVersionUID = 1L;

    private String paycycleId;

    public String getPaycycleId() {
        return paycycleId;
    }

    public void setPaycycleId(String paycycleId) {
        this.paycycleId = paycycleId;
    }
}
