package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class ProjMaterialClassDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> projMaterialClassIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public ProjMaterialClassDelReq() {
    }

    public List<Long> getProjMaterialClassIds() {
        return projMaterialClassIds;
    }

    public void setProjMaterialClassIds(List<Long> projMaterialClassIds) {
        this.projMaterialClassIds = projMaterialClassIds;
    }

}
