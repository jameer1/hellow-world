package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjLeaveRequestTO;

public class ProjLeaveRequestSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6568761515047591126L;
    private List<ProjLeaveRequestTO> projLeaveRequestTOs = new ArrayList<ProjLeaveRequestTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjLeaveRequestTO> getProjLeaveRequestTOs() {
        return projLeaveRequestTOs;
    }

    public void setProjLeaveRequestTOs(List<ProjLeaveRequestTO> projLeaveRequestTOs) {
        this.projLeaveRequestTOs = projLeaveRequestTOs;
    }

}
