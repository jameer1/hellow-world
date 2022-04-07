package com.rjtech.projschedule.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjScheduleBaseLineTO;
import com.rjtech.projschedule.dto.ProjScheduleManPowerTO;

public class ProjScheduleManPowerSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4787172210978834529L;

    private List<ProjScheduleManPowerTO> projScheduleManPowerTOs = new ArrayList<ProjScheduleManPowerTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private ProjScheduleBaseLineTO projScheduleBaseLineTO = new ProjScheduleBaseLineTO();

    public List<ProjScheduleManPowerTO> getProjScheduleManPowerTOs() {
        return projScheduleManPowerTOs;
    }

    public void setProjScheduleManPowerTOs(List<ProjScheduleManPowerTO> projScheduleManPowerTOs) {
        this.projScheduleManPowerTOs = projScheduleManPowerTOs;
    }

    public ProjScheduleBaseLineTO getProjScheduleBaseLineTO() {
        return projScheduleBaseLineTO;
    }

    public void setProjScheduleBaseLineTO(ProjScheduleBaseLineTO projScheduleBaseLineTO) {
        this.projScheduleBaseLineTO = projScheduleBaseLineTO;
    }

}
