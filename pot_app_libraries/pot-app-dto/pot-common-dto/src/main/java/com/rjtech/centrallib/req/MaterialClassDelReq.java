package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class MaterialClassDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> materialIds = new ArrayList<Long>(5);

    public List<Long> getMaterialIds() {
        return materialIds;
    }

    public void setMaterialIds(List<Long> materialIds) {
        this.materialIds = materialIds;
    }

}
