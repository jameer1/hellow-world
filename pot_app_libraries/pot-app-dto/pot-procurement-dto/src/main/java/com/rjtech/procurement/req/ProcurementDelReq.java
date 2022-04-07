package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProcurementDelReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<Long> contractIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private String itemType = null;

    public List<Long> getContractIds() {
        return contractIds;
    }

    public void setContractIds(List<Long> contractIds) {
        this.contractIds = contractIds;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

}
