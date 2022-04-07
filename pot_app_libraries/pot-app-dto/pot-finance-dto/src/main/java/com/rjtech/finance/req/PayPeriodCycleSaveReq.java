package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.FinancePeriodPayCyclesTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.PayRollCycleTO;

public class PayPeriodCycleSaveReq extends ClientTO {

    private static final long serialVersionUID = 4476996781997116196L;
    private List<PayRollCycleTO> payRollCycleTOs = new ArrayList<PayRollCycleTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private List<FinancePeriodPayCyclesTO> financePeriodPayCyclesTOs = new ArrayList<FinancePeriodPayCyclesTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<FinancePeriodPayCyclesTO> getFinancePeriodPayCyclesTOs() {
        return financePeriodPayCyclesTOs;
    }

    public void setFinancePeriodPayCyclesTOs(List<FinancePeriodPayCyclesTO> financePeriodPayCyclesTOs) {
        this.financePeriodPayCyclesTOs = financePeriodPayCyclesTOs;
    }

    public List<PayRollCycleTO> getPayRollCycleTOs() {
        return payRollCycleTOs;
    }

    public void setPayRollCycleTOs(List<PayRollCycleTO> payRollCycleTOs) {
        this.payRollCycleTOs = payRollCycleTOs;
    }

}
