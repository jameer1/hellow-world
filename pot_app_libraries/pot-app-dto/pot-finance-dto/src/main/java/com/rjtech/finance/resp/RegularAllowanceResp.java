package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.RegularPayAllowanceTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class RegularAllowanceResp extends AppResp {

    private static final long serialVersionUID = -7904071126544818868L;

    private List<RegularPayAllowanceTO> regularPayAllowanceTOs = null;

    public RegularAllowanceResp() {
        regularPayAllowanceTOs = new ArrayList<RegularPayAllowanceTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<RegularPayAllowanceTO> getRegularPayAllowanceTOs() {
        return regularPayAllowanceTOs;
    }

    public void setRegularPayAllowanceTOs(List<RegularPayAllowanceTO> regularPayAllowanceTOs) {
        this.regularPayAllowanceTOs = regularPayAllowanceTOs;
    }

}
