package com.rjtech.projschedule.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjScheduleBaseLineTO;

public class ProjScheduleBaseLineResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6428639300155396451L;
    private List<ProjScheduleBaseLineTO> projScheduleBaseLineTOs = null;

    public ProjScheduleBaseLineResp() {
        projScheduleBaseLineTOs = new ArrayList<ProjScheduleBaseLineTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjScheduleBaseLineTO> getProjScheduleBaseLineTOs() {
        return projScheduleBaseLineTOs;
    }

    public void setProjScheduleBaseLineTOs(List<ProjScheduleBaseLineTO> projScheduleBaseLineTOs) {
        this.projScheduleBaseLineTOs = projScheduleBaseLineTOs;
    }

}
