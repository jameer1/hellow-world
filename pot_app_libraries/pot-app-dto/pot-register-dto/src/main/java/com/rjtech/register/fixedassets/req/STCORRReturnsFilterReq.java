package com.rjtech.register.fixedassets.req;

import com.rjtech.common.dto.ClientTO;

public class STCORRReturnsFilterReq extends ClientTO {

    private static final long serialVersionUID = -6483281368154481696L;
    private String fromDate;
    private String toDate;
    private Long fixedAssetid;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Long getFixedAssetid() {
        return fixedAssetid;
    }

    public void setFixedAssetid(Long fixedAssetid) {
        this.fixedAssetid = fixedAssetid;
    }
}
