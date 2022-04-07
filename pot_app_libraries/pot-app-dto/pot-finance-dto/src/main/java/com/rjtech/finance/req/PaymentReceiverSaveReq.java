package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.TaxPaymentDetailsTO;

public class PaymentReceiverSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -2956379839314135189L;
    private List<TaxPaymentDetailsTO> taxPaymentDetailsTOs = new ArrayList<TaxPaymentDetailsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<TaxPaymentDetailsTO> getTaxPaymentDetailsTOs() {
        return taxPaymentDetailsTOs;
    }

    public void setTaxPaymentDetailsTOs(List<TaxPaymentDetailsTO> taxPaymentDetailsTOs) {
        this.taxPaymentDetailsTOs = taxPaymentDetailsTOs;
    }

}
