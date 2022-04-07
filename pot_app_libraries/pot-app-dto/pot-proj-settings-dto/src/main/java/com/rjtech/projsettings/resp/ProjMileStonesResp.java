package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjStatusMilestonesTO;

public class ProjMileStonesResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -8948952345715844954L;
    /**
     * 
     */
    private List<ProjStatusMilestonesTO> projMileStonesTOs = null;

    public List<ProjStatusMilestonesTO> getProjMileStonesTOs() {
        return projMileStonesTOs;
    }

    public void setProjMileStonesTOs(List<ProjStatusMilestonesTO> projMileStonesTOs) {
        this.projMileStonesTOs = projMileStonesTOs;
    }

    public ProjMileStonesResp() {
        projMileStonesTOs = new ArrayList<ProjStatusMilestonesTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

}
