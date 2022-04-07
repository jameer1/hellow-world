package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ResourceCurveTO;

public class ProjResourceCurveResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 3416164925166721209L;
    private List<ResourceCurveTO> projResourceCurveTOs = null;

    public ProjResourceCurveResp() {
        projResourceCurveTOs = new ArrayList<ResourceCurveTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ResourceCurveTO> getProjResourceCurveTOs() {
        return projResourceCurveTOs;
    }

    public void setProjResourceCurveTOs(List<ResourceCurveTO> projResourceCurveTOs) {
        this.projResourceCurveTOs = projResourceCurveTOs;
    }

}
