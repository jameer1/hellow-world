package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProcurementApprTO;

public class ProjProcureApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ProjProcurementApprTO> projProcurementApprTOs = new ArrayList<ProjProcurementApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjProcurementApprTO> getProjProcurementApprTOs() {
        return projProcurementApprTOs;
    }

    public void setProjProcurementApprTOs(List<ProjProcurementApprTO> projProcurementApprTOs) {
        this.projProcurementApprTOs = projProcurementApprTOs;
    }

}
