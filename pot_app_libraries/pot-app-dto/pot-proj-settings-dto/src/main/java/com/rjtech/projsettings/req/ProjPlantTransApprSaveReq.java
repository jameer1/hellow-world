package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPlantTransApprTO;

public class ProjPlantTransApprSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 8427059012836877591L;
    private List<ProjPlantTransApprTO> projPlantTransApprTOs = new ArrayList<ProjPlantTransApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjPlantTransApprTO> getProjPlantTransApprTOs() {
        return projPlantTransApprTOs;
    }

    public void setProjPlantTransApprTOs(List<ProjPlantTransApprTO> projPlantTransApprTOs) {
        this.projPlantTransApprTOs = projPlantTransApprTOs;
    }

}
