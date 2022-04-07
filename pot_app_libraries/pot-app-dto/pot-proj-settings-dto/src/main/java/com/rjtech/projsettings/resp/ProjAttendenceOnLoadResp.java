package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCrewTO;
import com.rjtech.projsettings.dto.ProjAttendenceTO;

public class ProjAttendenceOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ProjCrewTO> projCrewTOs = null;
    private List<ProjAttendenceTO> projAttendenceTOs = null;

    public ProjAttendenceOnLoadResp() {
        projCrewTOs = new ArrayList<ProjCrewTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projAttendenceTOs = new ArrayList<ProjAttendenceTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjCrewTO> getProjCrewTOs() {
        return projCrewTOs;
    }

    public void setProjCrewTOs(List<ProjCrewTO> projCrewTOs) {
        this.projCrewTOs = projCrewTOs;
    }

    public List<ProjAttendenceTO> getProjAttendenceTOs() {
        return projAttendenceTOs;
    }

    public void setProjAttendenceTOs(List<ProjAttendenceTO> projAttendenceTOs) {
        this.projAttendenceTOs = projAttendenceTOs;
    }

}
