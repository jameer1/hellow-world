package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjEstimateTO;

public class ProjEstimateSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7189938587483453640L;
    private List<ProjEstimateTO> projEstimateTOs = new ArrayList<ProjEstimateTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjEstimateTO> getProjEstimateTOs() {
        return projEstimateTOs;
    }

    public void setProjEstimateTOs(List<ProjEstimateTO> projEstimateTOs) {
        this.projEstimateTOs = projEstimateTOs;
    }

}
