package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjAttendenceTO;

public class ProjAttendenceSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6568761515047591126L;
    private List<ProjAttendenceTO> projAttendenceTOs = new ArrayList<ProjAttendenceTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjAttendenceTO> getProjAttendenceTOs() {
        return projAttendenceTOs;
    }

    public void setProjAttendenceTOs(List<ProjAttendenceTO> projAttendenceTOs) {
        this.projAttendenceTOs = projAttendenceTOs;
    }

}
