package com.rjtech.finance.req;

import com.rjtech.common.dto.ClientTO;

public class FinanceTaxGetReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4601704740889675968L;
    private Long taxId;

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

}
