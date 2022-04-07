package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.LongTermLeasingDtlTO;

public class LongTermLeasingDeactiveReq extends LongTermLeasingDtlTO {

    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> longTermLeasingIds = new ArrayList<Long>();

    public List<Long> getLongTermLeasingIds() {
        return longTermLeasingIds;
    }

    public void setLongTermLeasingIds(List<Long> longTermLeasingIds) {
        this.longTermLeasingIds = longTermLeasingIds;
    }

}
