package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjEstimateTO;

public class ProjEstimateResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -187824831647801020L;
    private List<ProjEstimateTO> projEstimateTOs = null;

    public ProjEstimateResp() {
        projEstimateTOs = new ArrayList<ProjEstimateTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjEstimateTO> getProjEstimateTOs() {
        return projEstimateTOs;
    }

    public void setProjEstimateTOs(List<ProjEstimateTO> projEstimateTOs) {
        this.projEstimateTOs = projEstimateTOs;
    }

}
