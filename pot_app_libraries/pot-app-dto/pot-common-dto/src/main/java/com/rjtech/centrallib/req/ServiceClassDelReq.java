package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class ServiceClassDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> serviceClassIds = new ArrayList<Long>(5);

    public List<Long> getServiceClassIds() {
        return serviceClassIds;
    }

    public void setServiceClassIds(List<Long> serviceClassIds) {
        this.serviceClassIds = serviceClassIds;
    }

}
