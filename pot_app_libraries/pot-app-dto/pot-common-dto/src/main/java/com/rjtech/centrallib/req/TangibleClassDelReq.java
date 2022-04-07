package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class TangibleClassDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> tangibleIds = new ArrayList<Long>(5);

    public List<Long> getTangibleIds() {
        return tangibleIds;
    }

    public void setTangibleIds(List<Long> tangibleIds) {
        this.tangibleIds = tangibleIds;
    }

}
