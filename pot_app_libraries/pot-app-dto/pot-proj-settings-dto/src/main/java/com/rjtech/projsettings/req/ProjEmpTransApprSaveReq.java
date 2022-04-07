package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjEmpTransApprTO;

public class ProjEmpTransApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -3578673376736121284L;
    private List<ProjEmpTransApprTO> projEmpTransApprTOs = new ArrayList<ProjEmpTransApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjEmpTransApprTO> getProjEmpTransApprTOs() {
        return projEmpTransApprTOs;
    }

    public void setProjEmpTransApprTOs(List<ProjEmpTransApprTO> projEmpTransApprTOs) {
        this.projEmpTransApprTOs = projEmpTransApprTOs;
    }

}
