package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.EPSProjectTO;
import com.rjtech.common.resp.AppResp;

public class ProjEPSOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private EPSProjectTO ePSProjectTO = null;
    private List<EPSProjectTO> ePSProjectTOs = null;

    public ProjEPSOnLoadResp() {
        ePSProjectTO = new EPSProjectTO();
        ePSProjectTOs = new ArrayList<EPSProjectTO>();
    }

    public EPSProjectTO getePSProjectTO() {
        return ePSProjectTO;
    }

    public void setePSProjectTO(EPSProjectTO ePSProjectTO) {
        this.ePSProjectTO = ePSProjectTO;
    }

    public List<EPSProjectTO> getePSProjectTOs() {
        return ePSProjectTOs;
    }

    public void setePSProjectTOs(List<EPSProjectTO> ePSProjectTOs) {
        this.ePSProjectTOs = ePSProjectTOs;
    }

}
