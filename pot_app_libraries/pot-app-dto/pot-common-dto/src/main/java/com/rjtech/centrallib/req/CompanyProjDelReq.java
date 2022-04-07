package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class CompanyProjDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> cmpProjIds = new ArrayList<Long>(5);

    public List<Long> getCmpProjIds() {
        return cmpProjIds;
    }

    public void setCmpProjIds(List<Long> cmpProjIds) {
        this.cmpProjIds = cmpProjIds;
    }

}
