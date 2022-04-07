package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class FinanceTaxDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4601704740889675968L;
    private List<Long> taxIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getTaxIds() {
        return taxIds;
    }

    public void setTaxIds(List<Long> taxIds) {
        this.taxIds = taxIds;
    }

}
