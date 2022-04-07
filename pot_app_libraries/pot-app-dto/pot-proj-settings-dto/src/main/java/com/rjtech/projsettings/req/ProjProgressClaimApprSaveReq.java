package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProgressClaimApprTO;

public class ProjProgressClaimApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 341245626461397994L;
    private List<ProjProgressClaimApprTO> projProgressClaimApprTOs = new ArrayList<ProjProgressClaimApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjProgressClaimApprTO> getProjProgressClaimApprTOs() {
        return projProgressClaimApprTOs;
    }

    public void setProjProgressClaimApprTOs(List<ProjProgressClaimApprTO> projProgressClaimApprTOs) {
        this.projProgressClaimApprTOs = projProgressClaimApprTOs;
    }

}
