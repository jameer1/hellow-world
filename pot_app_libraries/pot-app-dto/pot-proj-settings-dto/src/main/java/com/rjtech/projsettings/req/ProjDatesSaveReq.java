package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjectDateTO;

public class ProjDatesSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 443359243602281505L;

    List<ProjectDateTO> projDatesTOs = new ArrayList<ProjectDateTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjectDateTO> getProjDatesTOs() {
        return projDatesTOs;
    }

    public void setProjDatesTOs(List<ProjectDateTO> projDatesTOs) {
        this.projDatesTOs = projDatesTOs;
    }

}
