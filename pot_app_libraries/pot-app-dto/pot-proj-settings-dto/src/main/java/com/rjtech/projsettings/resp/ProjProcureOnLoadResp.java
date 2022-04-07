package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPrecontractTO;
import com.rjtech.projsettings.dto.ProjProcurementTO;

public class ProjProcureOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 4599339206685867622L;
    private List<ProjPrecontractTO> precontractTO = null;
    private List<ProjProcurementTO> projProcurementTOs = null;

    public ProjProcureOnLoadResp() {
        precontractTO = new ArrayList<ProjPrecontractTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projProcurementTOs = new ArrayList<ProjProcurementTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjPrecontractTO> getPrecontractTO() {
        return precontractTO;
    }

    public void setPrecontractTO(List<ProjPrecontractTO> precontractTO) {
        this.precontractTO = precontractTO;
    }

    public List<ProjProcurementTO> getProjProcurementTOs() {
        return projProcurementTOs;
    }

    public void setProjProcurementTOs(List<ProjProcurementTO> projProcurementTOs) {
        this.projProcurementTOs = projProcurementTOs;
    }

}
