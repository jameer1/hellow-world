package com.rjtech.finance.req;

import com.rjtech.common.dto.ProjectTO;

public class FinanceTaxCodesGetReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private String financeType;

    public String getFinanceType() {
        return financeType;
    }

    public void setFinanceType(String financeType) {
        this.financeType = financeType;
    }

}
