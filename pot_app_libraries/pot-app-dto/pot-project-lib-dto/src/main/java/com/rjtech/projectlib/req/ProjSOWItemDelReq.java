package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjSOWItemDelReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<Long> projSOWItemIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjSOWItemIds() {
        return projSOWItemIds;
    }

    public void setProjSOWItemIds(List<Long> projSOWItemIds) {
        this.projSOWItemIds = projSOWItemIds;
    }

}
