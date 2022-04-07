package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.LongTermLeaseActualRetrunsDtlTO;

public class LongTermLeaseActualRetrunsDeleteReq extends LongTermLeaseActualRetrunsDtlTO {

    private static final long serialVersionUID = -4543819922890869538L;
    
    private List<Long> longTermLeaseAcutalIds = new ArrayList<Long>();
    

    public List<Long> getLongTermLeaseAcutalIds() {
        return longTermLeaseAcutalIds;
    }

    public void setLongTermLeaseAcutalIds(List<Long> longTermLeaseAcutalIds) {
        this.longTermLeaseAcutalIds = longTermLeaseAcutalIds;
    }

}
