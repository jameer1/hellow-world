package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.SalesRecordsDtlTO;

public class SalesRecordsDeactiveReq extends SalesRecordsDtlTO {

    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> salesRecordsIds = new ArrayList<Long>();

    public List<Long> getSalesRecordsIds() {
        return salesRecordsIds;
    }

    public void setSalesRecordsIds(List<Long> salesRecordsIds) {
        this.salesRecordsIds = salesRecordsIds;
    }

}
