package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.TaxCodesTO;

public class TaxCodesSaveReq extends ClientTO {

    private static final long serialVersionUID = -4819561725479979123L;
    private List<TaxCodesTO> taxCodesTOs = new ArrayList<TaxCodesTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<TaxCodesTO> getTaxCodesTOs() {
        return taxCodesTOs;
    }

    public void setTaxCodesTOs(List<TaxCodesTO> taxCodesTOs) {
        this.taxCodesTOs = taxCodesTOs;
    }

}
