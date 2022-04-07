package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProgressClaimApprTO;

public class ProjProgressClaimApprResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ProjProgressClaimApprTO> projProgressClaimApprTOs = null;

    public ProjProgressClaimApprResp() {
        projProgressClaimApprTOs = new ArrayList<ProjProgressClaimApprTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjProgressClaimApprTO> getProjProgressClaimApprTOs() {
        return projProgressClaimApprTOs;
    }

    public void setProjProgressClaimApprTOs(List<ProjProgressClaimApprTO> projProgressClaimApprTOs) {
        this.projProgressClaimApprTOs = projProgressClaimApprTOs;
    }

}
