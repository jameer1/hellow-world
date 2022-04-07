package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjSORItemDelReq extends ProjectTO {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<Long> projSORItemIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjSORItemIds() {
        return projSORItemIds;
    }

    public void setProjSORItemIds(List<Long> projSORItemIds) {
        this.projSORItemIds = projSORItemIds;
    }

}
