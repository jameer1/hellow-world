package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class ProcureCatgDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> procureCatgIds = new ArrayList<Long>(5);

    public List<Long> getProcureCatgIds() {
        return procureCatgIds;
    }

    public void setProcureCatgIds(List<Long> procureCatgIds) {
        this.procureCatgIds = procureCatgIds;
    }

}
