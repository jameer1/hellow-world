package com.rjtech.common.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;


public class ResourceCurvesDeactivateReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -4287656828700438515L;

    List<Long> resourceCurveIds = new ArrayList<Long>(5);

    public List<Long> getResourceCurveIds() {
        return resourceCurveIds;
    }

    public void setResourceCurveIds(List<Long> resourceCurveIds) {
        this.resourceCurveIds = resourceCurveIds;
    }

}
