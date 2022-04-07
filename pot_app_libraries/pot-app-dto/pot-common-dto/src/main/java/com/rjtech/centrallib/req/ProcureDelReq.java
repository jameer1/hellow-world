package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class ProcureDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> procurementIds = new ArrayList<Long>(5);

    public List<Long> getProcurementIds() {
        return procurementIds;
    }

    public void setProcurementIds(List<Long> procurementIds) {
        this.procurementIds = procurementIds;
    }

}
