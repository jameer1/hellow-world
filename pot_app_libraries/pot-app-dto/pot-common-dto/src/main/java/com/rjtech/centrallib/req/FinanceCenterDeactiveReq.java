package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.FinanceCenterRecordTo;

public class FinanceCenterDeactiveReq extends FinanceCenterRecordTo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<Long> financeCenterIds = new ArrayList<Long>();
    public List<Long> getFinanceCenterIds() {
        return financeCenterIds;
    }
    public void setFinanceCenterIds(List<Long> financeCenterIds) {
        this.financeCenterIds = financeCenterIds;
    }

}
