package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjAttendceApprTO;

public class ProjAttendenceApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -3154733576092330812L;
    private List<ProjAttendceApprTO> projAttendceApprTOs = new ArrayList<ProjAttendceApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjAttendceApprTO> getProjAttendceApprTOs() {
        return projAttendceApprTOs;
    }

    public void setProjAttendceApprTOs(List<ProjAttendceApprTO> projAttendceApprTOs) {
        this.projAttendceApprTOs = projAttendceApprTOs;
    }

}
