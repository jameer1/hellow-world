package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class BusinessCatgDeleteReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> BusinessCatgIds = new ArrayList<Long>(5);

    public List<Long> getBusinessCatgIds() {
        return BusinessCatgIds;
    }

    public void setBusinessCatgIds(List<Long> businessCatgIds) {
        BusinessCatgIds = businessCatgIds;
    }

}
