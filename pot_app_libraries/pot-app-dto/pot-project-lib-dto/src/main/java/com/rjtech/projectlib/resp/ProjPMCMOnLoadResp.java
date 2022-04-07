package com.rjtech.projectlib.resp;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjPMCMItemTO;

import java.util.ArrayList;
import java.util.List;

public class ProjPMCMOnLoadResp {

    private ProjPMCMItemTO projSORItemTO = null;

    private List<ProjPMCMItemTO> projSORItemTOs = null;

    public ProjPMCMOnLoadResp() {
        projSORItemTO = new ProjPMCMItemTO();
        projSORItemTOs = new ArrayList<ProjPMCMItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public ProjPMCMItemTO getProjSORItemTO() {
        return projSORItemTO;
    }

    public void setProjSORItemTO(ProjPMCMItemTO projSORItemTO) {
        this.projSORItemTO = projSORItemTO;
    }

    public List<ProjPMCMItemTO> getProjSORItemTOs() {
        return projSORItemTOs;
    }

    public void setProjSORItemTOs(List<ProjPMCMItemTO> projSORItemTOs) {
        this.projSORItemTOs = projSORItemTOs;
    }
}
