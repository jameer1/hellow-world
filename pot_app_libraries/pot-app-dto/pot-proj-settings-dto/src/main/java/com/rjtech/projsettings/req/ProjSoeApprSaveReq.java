package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjSoeApprTO;

public class ProjSoeApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -643526727935618346L;
    
    private List<ProjSoeApprTO> projSoeApprTOs = new ArrayList<ProjSoeApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjSoeApprTO> getProjSoeApprTOs() {
        return projSoeApprTOs;
    }

    public void setProjSoeApprTOs(List<ProjSoeApprTO> projSoeApprTOs) {
        this.projSoeApprTOs = projSoeApprTOs;
    }

}
