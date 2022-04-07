package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.MortgagePaymentDtlTO;

public class MortgageePaymentsDeactiveReq extends MortgagePaymentDtlTO {

    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> mortagageePaymentsIds = new ArrayList<Long>();

    public List<Long> getMortagageePaymentsIds() {
        return mortagageePaymentsIds;
    }

    public void setMortagageePaymentsIds(List<Long> mortagageePaymentsIds) {
        this.mortagageePaymentsIds = mortagageePaymentsIds;
    }

}
