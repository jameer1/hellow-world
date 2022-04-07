package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjMaterialDelReq extends ProjectTO {

    private static final long serialVersionUID = -6671175298681215590L;
    private List<Long> projMaterialClassIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getProjMaterialClassIds() {
        return projMaterialClassIds;
    }

    public void setProjMaterialClassIds(List<Long> projMaterialClassIds) {
        this.projMaterialClassIds = projMaterialClassIds;
    }

}
