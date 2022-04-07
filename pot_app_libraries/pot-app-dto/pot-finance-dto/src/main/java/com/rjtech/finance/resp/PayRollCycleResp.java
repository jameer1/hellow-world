package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.PayRollCycleTO;

public class PayRollCycleResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 629147984088618486L;
    private List<PayRollCycleTO> payRollCycleTOs = null;

    public PayRollCycleResp() {
        payRollCycleTOs = new ArrayList<PayRollCycleTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PayRollCycleTO> getPayRollCycleTOs() {
        return payRollCycleTOs;
    }

    public void setPayRollCycleTOs(List<PayRollCycleTO> payRollCycleTOs) {
        this.payRollCycleTOs = payRollCycleTOs;
    }

}
