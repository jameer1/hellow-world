package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjEstimateMstrTO;

public class ProjEstimateMstrResp extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6184172091267795316L;
    private List<ProjEstimateMstrTO> projEstimateMstrTOs = null;

    public ProjEstimateMstrResp() {
        projEstimateMstrTOs = new ArrayList<ProjEstimateMstrTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjEstimateMstrTO> getProjEstimateMstrTOs() {
        return projEstimateMstrTOs;
    }

    public void setProjEstimateMstrTOs(List<ProjEstimateMstrTO> projEstimateMstrTOs) {
        this.projEstimateMstrTOs = projEstimateMstrTOs;
    }

}
