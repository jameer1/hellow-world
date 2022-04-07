package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjectDateTO;

public class ProjDatesResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -8948952345715844954L;
    /**
     * 
     */
    private List<ProjectDateTO> projDatesTOs = null;

    public List<ProjectDateTO> getProjDatesTOs() {
        return projDatesTOs;
    }

    public void setProjDatesTOs(List<ProjectDateTO> projDatesTOs) {
        this.projDatesTOs = projDatesTOs;
    }

    public ProjDatesResp() {
        projDatesTOs = new ArrayList<ProjectDateTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

}
