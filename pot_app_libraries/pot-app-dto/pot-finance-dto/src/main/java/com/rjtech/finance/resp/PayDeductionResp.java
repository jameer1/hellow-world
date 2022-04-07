package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.PayDeductionTO;

public class PayDeductionResp extends AppResp {

    private static final long serialVersionUID = -4070399965677073282L;

    private List<PayDeductionTO> payDeductionTOs = null;

    public PayDeductionResp() {
        payDeductionTOs = new ArrayList<PayDeductionTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PayDeductionTO> getPayDeductionTOs() {
        return payDeductionTOs;
    }

    public void setPayDeductionTOs(List<PayDeductionTO> payDeductionTOs) {
        this.payDeductionTOs = payDeductionTOs;
    }

}
