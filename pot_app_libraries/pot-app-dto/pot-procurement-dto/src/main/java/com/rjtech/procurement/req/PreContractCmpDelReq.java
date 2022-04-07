package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class PreContractCmpDelReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -8404986530316536049L;
    private List<Long> preContractCmpIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getPreContractCmpIds() {
        return preContractCmpIds;
    }

    public void setPreContractCmpIds(List<Long> preContractCmpIds) {
        this.preContractCmpIds = preContractCmpIds;
    }

}
