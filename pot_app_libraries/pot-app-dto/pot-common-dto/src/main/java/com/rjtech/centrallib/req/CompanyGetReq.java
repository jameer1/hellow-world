package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;

public class CompanyGetReq extends ClientTO {
    private static final long serialVersionUID = 29500848620797L;

    private Long cmpId;
    private List<Long> cmpIds = new ArrayList<Long>();

    public Long getCmpId() {
        return cmpId;
    }

    public void setCmpId(Long cmpId) {
        this.cmpId = cmpId;
    }

    public List<Long> getCmpIds() {
        return cmpIds;
    }

    public void setCmpIds(List<Long> cmpIds) {
        this.cmpIds = cmpIds;
    }

}
