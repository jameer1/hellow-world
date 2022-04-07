package com.rjtech.common.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.common.dto.ResourceCurveTO;


public class ResourceCurveSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private List<ResourceCurveTO> resourceCurveTOs = new ArrayList<ResourceCurveTO>(
            5);

    public List<ResourceCurveTO> getResourceCurveTOs() {
        return resourceCurveTOs;
    }

    public void setResourceCurveTOs(List<ResourceCurveTO> resourceCurveTOs) {
        this.resourceCurveTOs = resourceCurveTOs;
    }

}
