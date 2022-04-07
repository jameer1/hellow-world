package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.PayDeductionTO;
import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class PayDeductionSaveReq extends ClientTO {

    private static final long serialVersionUID = -3741553025468649464L;
    private List<PayDeductionTO> payDeductionTOs = new ArrayList<PayDeductionTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<PayDeductionTO> getPayDeductionTOs() {
        return payDeductionTOs;
    }

    public void setPayDeductionTOs(List<PayDeductionTO> payDeductionTOs) {
        this.payDeductionTOs = payDeductionTOs;
    }

}
