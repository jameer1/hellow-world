package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjEmpTransTO;

public class ProjEmpTransResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -3119883154685839546L;
    private List<ProjEmpTransTO> projEmpTransTOs = null;

    public ProjEmpTransResp() {
        projEmpTransTOs = new ArrayList<ProjEmpTransTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjEmpTransTO> getProjEmpTransTOs() {
        return projEmpTransTOs;
    }

    public void setProjEmpTransTOs(List<ProjEmpTransTO> projEmpTransTOs) {
        this.projEmpTransTOs = projEmpTransTOs;
    }

}
