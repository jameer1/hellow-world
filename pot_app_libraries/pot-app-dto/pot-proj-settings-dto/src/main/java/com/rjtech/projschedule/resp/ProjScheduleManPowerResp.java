package com.rjtech.projschedule.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projschedule.dto.ProjScheduleBaseLineTO;
import com.rjtech.projschedule.dto.ProjScheduleManPowerTO;

public class ProjScheduleManPowerResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6428639300155396451L;
    private List<ProjScheduleManPowerTO> projScheduleManPowerTOs = null;
    private ProjScheduleBaseLineTO projScheduleBaseLineTO = new ProjScheduleBaseLineTO();
    private List<LabelKeyTO> labelKeyTOs = null;

    public ProjScheduleManPowerResp() {
        projScheduleManPowerTOs = new ArrayList<ProjScheduleManPowerTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        labelKeyTOs = new ArrayList<LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjScheduleManPowerTO> getProjScheduleManPowerTOs() {
        return projScheduleManPowerTOs;
    }

    public void setProjScheduleManPowerTOs(List<ProjScheduleManPowerTO> projScheduleManPowerTOs) {
        this.projScheduleManPowerTOs = projScheduleManPowerTOs;
    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

    public ProjScheduleBaseLineTO getProjScheduleBaseLineTO() {
        return projScheduleBaseLineTO;
    }

    public void setProjScheduleBaseLineTO(ProjScheduleBaseLineTO projScheduleBaseLineTO) {
        this.projScheduleBaseLineTO = projScheduleBaseLineTO;
    }

}
