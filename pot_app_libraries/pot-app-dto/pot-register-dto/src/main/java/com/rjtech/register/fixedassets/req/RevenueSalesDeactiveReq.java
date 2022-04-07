package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.RevenueSalesDtlTO;

public class RevenueSalesDeactiveReq extends RevenueSalesDtlTO {

    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> revenueSalesIds = new ArrayList<Long>();

    public List<Long> getRevenueSalesIds() {
        return revenueSalesIds;
    }

    public void setRevenueSalesIds(List<Long> revenueSalesIds) {
        this.revenueSalesIds = revenueSalesIds;
    }

}
