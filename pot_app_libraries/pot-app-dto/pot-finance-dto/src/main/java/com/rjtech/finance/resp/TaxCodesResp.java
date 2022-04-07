package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.TaxCodesTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class TaxCodesResp extends AppResp {

    private static final long serialVersionUID = 5228532470619377619L;

    private List<TaxCodesTO> taxCodesTOs = null;

    public TaxCodesResp() {
        taxCodesTOs = new ArrayList<TaxCodesTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<TaxCodesTO> getTaxCodesTOs() {
        return taxCodesTOs;
    }

    public void setTaxCodesTOs(List<TaxCodesTO> taxCodesTOs) {
        this.taxCodesTOs = taxCodesTOs;
    }

}
