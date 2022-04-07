package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjMaterialTransApprTO;

public class ProjMaterialTransApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1711225045965559185L;
    private List<ProjMaterialTransApprTO> projMaterialTransApprTOs = new ArrayList<ProjMaterialTransApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjMaterialTransApprTO> getProjMaterialTransApprTOs() {
        return projMaterialTransApprTOs;
    }

    public void setProjMaterialTransApprTOs(List<ProjMaterialTransApprTO> projMaterialTransApprTOs) {
        this.projMaterialTransApprTOs = projMaterialTransApprTOs;
    }

}
