package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.MortgagePaymentDtlTO;

public class MortgagePaymentResp extends AppResp {
    private static final long serialVersionUID = 1L;

    private List<MortgagePaymentDtlTO> mortgageValueDtlTOs = new ArrayList<MortgagePaymentDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<MortgagePaymentDtlTO> getMortgageValueDtlTOs() {
        return mortgageValueDtlTOs;
    }

    public void setMortgageValueDtlTOs(List<MortgagePaymentDtlTO> mortgageValueDtlTOs) {
        this.mortgageValueDtlTOs = mortgageValueDtlTOs;
    }

}
