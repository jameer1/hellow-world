package com.rjtech.projschedule.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjScheduleBaseLineTO;
import com.rjtech.projschedule.dto.ProjScheduleSOWTO;

public class ProjScheduleSOWSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4787172210978834529L;

    private List<ProjScheduleSOWTO> projScheduleSOWTOs = new ArrayList<ProjScheduleSOWTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private ProjScheduleBaseLineTO projScheduleBaseLineTO = new ProjScheduleBaseLineTO();

    public List<ProjScheduleSOWTO> getProjScheduleSOWTOs() {
        return projScheduleSOWTOs;
    }

    public void setProjScheduleSOWTOs(List<ProjScheduleSOWTO> projScheduleSOWTOs) {
        this.projScheduleSOWTOs = projScheduleSOWTOs;
    }

    public ProjScheduleBaseLineTO getProjScheduleBaseLineTO() {
        return projScheduleBaseLineTO;
    }

    public void setProjScheduleBaseLineTO(ProjScheduleBaseLineTO projScheduleBaseLineTO) {
        this.projScheduleBaseLineTO = projScheduleBaseLineTO;
    }

}
