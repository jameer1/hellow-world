package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.TaxPaymentDetailsTO;

public class PaymentReceiverResp extends AppResp {

    private static final long serialVersionUID = 3374748929525368092L;
    private List<TaxPaymentDetailsTO> taxPaymentDetailsTOs = null;

    public PaymentReceiverResp() {
        taxPaymentDetailsTOs = new ArrayList<TaxPaymentDetailsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<TaxPaymentDetailsTO> getTaxPaymentDetailsTOs() {
        return taxPaymentDetailsTOs;
    }

    public void setTaxPaymentDetailsTOs(List<TaxPaymentDetailsTO> taxPaymentDetailsTOs) {
        this.taxPaymentDetailsTOs = taxPaymentDetailsTOs;
    }

}
