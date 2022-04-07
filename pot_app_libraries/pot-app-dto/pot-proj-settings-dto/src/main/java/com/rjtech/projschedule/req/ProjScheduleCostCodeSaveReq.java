package com.rjtech.projschedule.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjScheduleBaseLineTO;
import com.rjtech.projschedule.dto.ProjScheduleCostCodeTO;

public class ProjScheduleCostCodeSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 4787172210978834529L;

    private List<ProjScheduleCostCodeTO> projScheduleCostCodeTOs = new ArrayList<ProjScheduleCostCodeTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private ProjScheduleBaseLineTO projScheduleBaseLineTO = new ProjScheduleBaseLineTO();

    public List<ProjScheduleCostCodeTO> getProjScheduleCostCodeTOs() {
        return projScheduleCostCodeTOs;
    }

    public void setProjScheduleCostCodeTOs(List<ProjScheduleCostCodeTO> projScheduleCostCodeTOs) {
        this.projScheduleCostCodeTOs = projScheduleCostCodeTOs;
    }

    public ProjScheduleBaseLineTO getProjScheduleBaseLineTO() {
        return projScheduleBaseLineTO;
    }

    public void setProjScheduleBaseLineTO(ProjScheduleBaseLineTO projScheduleBaseLineTO) {
        this.projScheduleBaseLineTO = projScheduleBaseLineTO;
    }

}
