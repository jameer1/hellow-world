package com.rjtech.centrallib.req;

import com.rjtech.common.dto.FinanceCenterRecordTo;

public class FinanceCenterSaveReq extends FinanceCenterRecordTo {

    /**
     * 
     */
    private static final long serialVersionUID = -1944315930381133121L;
    
     FinanceCenterRecordTo financeCenterRecordTo = new FinanceCenterRecordTo();

    public FinanceCenterRecordTo getFinanceCenterRecordTo() {
        return financeCenterRecordTo;
    }

    public void setFinanceCenterRecordTo(FinanceCenterRecordTo financeCenterRecordTo) {
        this.financeCenterRecordTo = financeCenterRecordTo;
    }

    

}
