package com.rjtech.common.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ResourceCurveTO;


public class ResourceCurveResp extends AppResp {

    private static final long serialVersionUID = 3416164925166721209L;
    private List<ResourceCurveTO> projResourceCurveTOs = null;

    public ResourceCurveResp() {
        projResourceCurveTOs = new ArrayList<ResourceCurveTO>(5);
    }

    public List<ResourceCurveTO> getProjResourceCurveTOs() {
        return projResourceCurveTOs;
    }

    public void setProjResourceCurveTOs(List<ResourceCurveTO> projResourceCurveTOs) {
        this.projResourceCurveTOs = projResourceCurveTOs;
    }

}
