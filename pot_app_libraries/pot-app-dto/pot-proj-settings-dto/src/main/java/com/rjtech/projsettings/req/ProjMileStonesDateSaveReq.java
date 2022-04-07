package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjStatusMilestonesTO;
import com.rjtech.projsettings.dto.ProjectDateTO;

public class ProjMileStonesDateSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 443359243602281505L;

    List<ProjStatusMilestonesTO> projMileStonesTOs = new ArrayList<ProjStatusMilestonesTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjStatusMilestonesTO> getProjMileStonesTOs() {
        return projMileStonesTOs;
    }

    public void setProjMileStonesTOs(List<ProjStatusMilestonesTO> projMileStonesTOs) {
        this.projMileStonesTOs = projMileStonesTOs;
    }

}
