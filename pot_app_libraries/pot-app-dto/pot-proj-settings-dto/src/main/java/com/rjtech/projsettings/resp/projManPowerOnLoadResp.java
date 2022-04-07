package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjEmpClassTO;
import com.rjtech.projsettings.dto.ProjManpowerTO;

public class projManPowerOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 4327823583182823743L;
    private List<ProjEmpClassTO> projEmpClassTOs = null;
    private ProjManpowerTO projManpowerTO = null;
    private List<ProjManpowerTO> projManpowerTOs = null;

    public projManPowerOnLoadResp() {
        projEmpClassTOs = new ArrayList<ProjEmpClassTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projManpowerTO = new ProjManpowerTO();
        projManpowerTOs = new ArrayList<ProjManpowerTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjEmpClassTO> getProjEmpClassTOs() {
        return projEmpClassTOs;
    }

    public void setProjEmpClassTOs(List<ProjEmpClassTO> projEmpClassTOs) {
        this.projEmpClassTOs = projEmpClassTOs;
    }

    public ProjManpowerTO getProjManpowerTO() {
        return projManpowerTO;
    }

    public void setProjManpowerTO(ProjManpowerTO projManpowerTO) {
        this.projManpowerTO = projManpowerTO;
    }

    public List<ProjManpowerTO> getProjManpowerTOs() {
        return projManpowerTOs;
    }

    public void setProjManpowerTOs(List<ProjManpowerTO> projManpowerTOs) {
        this.projManpowerTOs = projManpowerTOs;
    }

}
