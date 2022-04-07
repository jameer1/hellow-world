package com.rjtech.common.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.FinancePeriodPayCyclesTO;


public class PayCyclesResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<FinancePeriodPayCyclesTO> financePeriodPayCyclesTOs = new ArrayList<FinancePeriodPayCyclesTO>(
            5);

    public List<FinancePeriodPayCyclesTO> getFinancePeriodPayCyclesTOs() {
        return financePeriodPayCyclesTOs;
    }

    public void setFinancePeriodPayCyclesTOs(List<FinancePeriodPayCyclesTO> financePeriodPayCyclesTOs) {
        this.financePeriodPayCyclesTOs = financePeriodPayCyclesTOs;
    }
}
