package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjPrecontractTO;
import com.rjtech.projsettings.dto.ProjEmpTransTO;

public class ProjEmpTransOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 3090008444461084513L;
    private List<ProjPrecontractTO> precontractTOs = null;
    private List<ProjEmpTransTO> projEmpTransTOs = null;

    public List<ProjEmpTransTO> getProjEmpTransTOs() {
        return projEmpTransTOs;
    }

    public void setProjEmpTransTOs(List<ProjEmpTransTO> projEmpTransTOs) {
        this.projEmpTransTOs = projEmpTransTOs;
    }

    public ProjEmpTransOnLoadResp() {
        precontractTOs = new ArrayList<ProjPrecontractTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjPrecontractTO> getPrecontractTOs() {
        return precontractTOs;
    }

    public void setPrecontractTOs(List<ProjPrecontractTO> precontractTOs) {
        this.precontractTOs = precontractTOs;
    }

}
