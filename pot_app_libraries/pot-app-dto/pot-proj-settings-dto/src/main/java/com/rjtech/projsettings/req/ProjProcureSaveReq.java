package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProcurementTO;

public class ProjProcureSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ProjProcurementTO> projProcurementTOs = new ArrayList<ProjProcurementTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjProcurementTO> getProjProcurementTOs() {
        return projProcurementTOs;
    }

    public void setProjProcurementTOs(List<ProjProcurementTO> projProcurementTOs) {
        this.projProcurementTOs = projProcurementTOs;
    }

}
