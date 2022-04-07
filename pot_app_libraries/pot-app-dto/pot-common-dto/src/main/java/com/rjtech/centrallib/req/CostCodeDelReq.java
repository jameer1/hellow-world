package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class CostCodeDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> costCodeIds = new ArrayList<Long>(5);

    public List<Long> getCostCodeIds() {
        return costCodeIds;
    }

    public void setCostCodeIds(List<Long> costCodeIds) {
        this.costCodeIds = costCodeIds;
    }

}
