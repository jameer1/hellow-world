package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projsettings.dto.ProjLeaveApprTO;

public class ProjLeaveApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7889377083586677720L;
    private List<ProjLeaveApprTO> projLeaveApprTOs = new ArrayList<ProjLeaveApprTO>();

    public List<ProjLeaveApprTO> getProjLeaveApprTOs() {
        return projLeaveApprTOs;
    }

    public void setProjLeaveApprTOs(List<ProjLeaveApprTO> projLeaveApprTOs) {
        this.projLeaveApprTOs = projLeaveApprTOs;
    }

}
