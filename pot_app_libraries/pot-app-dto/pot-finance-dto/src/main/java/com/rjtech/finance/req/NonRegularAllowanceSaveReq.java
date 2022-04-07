package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.NonRegularPayAllowanceTO;
import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class NonRegularAllowanceSaveReq extends ClientTO {

    private static final long serialVersionUID = -1458355337339581791L;
    private List<NonRegularPayAllowanceTO> nonRegularPayAllowanceTOs = new ArrayList<NonRegularPayAllowanceTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<NonRegularPayAllowanceTO> getNonRegularPayAllowanceTOs() {
        return nonRegularPayAllowanceTOs;
    }

    public void setNonRegularPayAllowanceTOs(List<NonRegularPayAllowanceTO> nonRegularPayAllowanceTOs) {
        this.nonRegularPayAllowanceTOs = nonRegularPayAllowanceTOs;
    }

}
