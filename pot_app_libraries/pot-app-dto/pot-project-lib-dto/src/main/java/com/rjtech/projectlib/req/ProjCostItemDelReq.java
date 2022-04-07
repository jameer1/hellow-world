package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjCostItemDelReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<Long> projCostItemIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjCostItemIds() {
        return projCostItemIds;
    }

    public void setProjCostItemIds(List<Long> projCostItemIds) {
        this.projCostItemIds = projCostItemIds;
    }

}
