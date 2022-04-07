package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.NonRegularPayAllowanceTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class NonRegularAllowanceResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 6607595049137082890L;
    private List<NonRegularPayAllowanceTO> nonRegularPayAllowanceTOs = null;

    public NonRegularAllowanceResp() {
        nonRegularPayAllowanceTOs = new ArrayList<NonRegularPayAllowanceTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

    public List<NonRegularPayAllowanceTO> getNonRegularPayAllowanceTOs() {
        return nonRegularPayAllowanceTOs;
    }

    public void setNonRegularPayAllowanceTOs(List<NonRegularPayAllowanceTO> nonRegularPayAllowanceTOs) {
        this.nonRegularPayAllowanceTOs = nonRegularPayAllowanceTOs;
    }

}
