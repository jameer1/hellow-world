package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProgressClaimTO;

public class ProjProgressClaimResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -8619855551138091433L;
    private List<ProjProgressClaimTO> projProgressClaimTOs = null;

    public ProjProgressClaimResp() {
        projProgressClaimTOs = new ArrayList<ProjProgressClaimTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjProgressClaimTO> getProjProgressClaimTOs() {
        return projProgressClaimTOs;
    }

    public void setProjProgressClaimTOs(List<ProjProgressClaimTO> projProgressClaimTOs) {
        this.projProgressClaimTOs = projProgressClaimTOs;
    }

}
