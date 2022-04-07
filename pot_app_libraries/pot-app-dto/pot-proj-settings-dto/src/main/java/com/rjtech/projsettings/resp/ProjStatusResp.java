package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjStatusTO;

public class ProjStatusResp extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -599446878617832560L;
    private List<ProjStatusTO> projStatusTOs = null;

    public ProjStatusResp() {
        projStatusTOs = new ArrayList<ProjStatusTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

    public List<ProjStatusTO> getProjStatusTOs() {
        return projStatusTOs;
    }

    public void setProjStatusTOs(List<ProjStatusTO> projStatusTOs) {
        this.projStatusTOs = projStatusTOs;
    }

}
