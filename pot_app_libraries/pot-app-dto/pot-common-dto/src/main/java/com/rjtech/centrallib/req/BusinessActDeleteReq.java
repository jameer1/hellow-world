package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class BusinessActDeleteReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> BusinessActIds = new ArrayList<Long>(5);

    public List<Long> getBusinessActIds() {
        return BusinessActIds;
    }

    public void setBusinessActIds(List<Long> businessActIds) {
        BusinessActIds = businessActIds;
    }

}
