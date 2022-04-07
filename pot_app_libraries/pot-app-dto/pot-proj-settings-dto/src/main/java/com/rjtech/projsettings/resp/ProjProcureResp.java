package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjProcurementTO;

public class ProjProcureResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<ProjProcurementTO> projProcurementTOs = null;

    public ProjProcureResp() {
        projProcurementTOs = new ArrayList<ProjProcurementTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjProcurementTO> getProjProcurementTOs() {
        return projProcurementTOs;
    }

    public void setProjProcurementTOs(List<ProjProcurementTO> projProcurementTOs) {
        this.projProcurementTOs = projProcurementTOs;
    }

}
