package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class MaterialClassesDeactivateReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<Long> MaterialClassIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getMaterialClassIds() {
        return MaterialClassIds;
    }

    public void setMaterialClassIds(List<Long> materialClassIds) {
        MaterialClassIds = materialClassIds;
    }

}
