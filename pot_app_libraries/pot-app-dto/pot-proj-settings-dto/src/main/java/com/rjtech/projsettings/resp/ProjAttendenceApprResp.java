package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjAttendceApprTO;

public class ProjAttendenceApprResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 2872460880381019815L;

    private List<ProjAttendceApprTO> projAttendceApprTOs = null;

    public ProjAttendenceApprResp() {
        projAttendceApprTOs = new ArrayList<ProjAttendceApprTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjAttendceApprTO> getProjAttendceApprTOs() {
        return projAttendceApprTOs;
    }

    public void setProjAttendceApprTOs(List<ProjAttendceApprTO> projAttendceApprTOs) {
        this.projAttendceApprTOs = projAttendceApprTOs;
    }

}
