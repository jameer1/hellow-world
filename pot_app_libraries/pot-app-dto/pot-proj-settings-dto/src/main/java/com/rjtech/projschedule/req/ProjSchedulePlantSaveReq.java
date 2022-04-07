package com.rjtech.projschedule.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjScheduleBaseLineTO;
import com.rjtech.projschedule.dto.ProjSchedulePlantTO;

public class ProjSchedulePlantSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4787172210978834529L;

    private List<ProjSchedulePlantTO> projSchedulePlantTOs = new ArrayList<ProjSchedulePlantTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private ProjScheduleBaseLineTO projScheduleBaseLineTO = new ProjScheduleBaseLineTO();

    public List<ProjSchedulePlantTO> getProjSchedulePlantTOs() {
        return projSchedulePlantTOs;
    }

    public void setProjSchedulePlantTOs(List<ProjSchedulePlantTO> projSchedulePlantTOs) {
        this.projSchedulePlantTOs = projSchedulePlantTOs;
    }

    public ProjScheduleBaseLineTO getProjScheduleBaseLineTO() {
        return projScheduleBaseLineTO;
    }

    public void setProjScheduleBaseLineTO(ProjScheduleBaseLineTO projScheduleBaseLineTO) {
        this.projScheduleBaseLineTO = projScheduleBaseLineTO;
    }

}
