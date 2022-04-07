package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.RegularPayAllowanceTO;

public class RegularAllowanceSaveReq extends ClientTO {

    private static final long serialVersionUID = 3724774077206435208L;
    private List<RegularPayAllowanceTO> regularPayAllowanceTOs = new ArrayList<RegularPayAllowanceTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<RegularPayAllowanceTO> getRegularPayAllowanceTOs() {
        return regularPayAllowanceTOs;
    }

    public void setRegularPayAllowanceTOs(List<RegularPayAllowanceTO> regularPayAllowanceTOs) {
        this.regularPayAllowanceTOs = regularPayAllowanceTOs;
    }

}
