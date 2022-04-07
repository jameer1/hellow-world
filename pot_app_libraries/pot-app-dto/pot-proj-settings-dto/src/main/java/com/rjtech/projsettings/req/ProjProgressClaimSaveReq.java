package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProgressClaimTO;

public class ProjProgressClaimSaveReq extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private List<ProjProgressClaimTO> projProgressClaimTOs = new ArrayList<ProjProgressClaimTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjProgressClaimTO> getProjProgressClaimTOs() {
        return projProgressClaimTOs;
    }

    public void setProjProgressClaimTOs(List<ProjProgressClaimTO> projProgressClaimTOs) {
        this.projProgressClaimTOs = projProgressClaimTOs;
    }

}
