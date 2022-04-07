package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjSOEItemDelReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<Long> projSOEItemIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjSOEItemIds() {
        return projSOEItemIds;
    }

    public void setProjSOEItemIds(List<Long> projSOEItemIds) {
        this.projSOEItemIds = projSOEItemIds;
    }

}
