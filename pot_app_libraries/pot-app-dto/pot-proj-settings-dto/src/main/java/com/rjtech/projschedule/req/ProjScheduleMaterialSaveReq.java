package com.rjtech.projschedule.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjScheduleBaseLineTO;
import com.rjtech.projschedule.dto.ProjScheduleMaterialTO;

public class ProjScheduleMaterialSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4787172210978834529L;

    private List<ProjScheduleMaterialTO> projScheduleMaterialTOs = new ArrayList<ProjScheduleMaterialTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private ProjScheduleBaseLineTO projScheduleBaseLineTO = new ProjScheduleBaseLineTO();

    public List<ProjScheduleMaterialTO> getProjScheduleMaterialTOs() {
        return projScheduleMaterialTOs;
    }

    public void setProjScheduleMaterialTOs(List<ProjScheduleMaterialTO> projScheduleMaterialTOs) {
        this.projScheduleMaterialTOs = projScheduleMaterialTOs;
    }

    public ProjScheduleBaseLineTO getProjScheduleBaseLineTO() {
        return projScheduleBaseLineTO;
    }

    public void setProjScheduleBaseLineTO(ProjScheduleBaseLineTO projScheduleBaseLineTO) {
        this.projScheduleBaseLineTO = projScheduleBaseLineTO;
    }

}
